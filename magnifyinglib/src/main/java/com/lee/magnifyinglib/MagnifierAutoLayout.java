package com.lee.magnifyinglib;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lee.magnifyinglib.utils.BitmapHelper;


/**
 * @Package: com.lee.magnifyingapp.magni.MagnifierLayout
 * @ClassName: MagnifierLayout
 * @Description: java类作用描述
 * @Author: 李嘉伦
 * @CreateDate: 2021/12/12 21:15
 * @Email: 474152966@qq，com
 */
public class MagnifierAutoLayout extends RelativeLayout {
    private Bitmap mBitmap;
    private Paint mPaintShadow;
    private long mShowTime = 0;//用于判断显示放大镜的时间
    private boolean mIsShowMagnifier = false;//是否显示放大镜
    private Path mPath;//用于裁剪画布的路径
    private float mShowMagnifierX = 0;//显示放大镜的X坐标
    private float mShowMagnifierY = 0;//显示放大镜的Y坐标

    private float mX = 0;
    private float mY = 0;


    private Paint paint;
    private Bitmap scaledBitmap;
    private Bitmap cutBitmap;
    private Bitmap roundBitmap;
    private MagnifierBuilder builder;

    public MagnifierAutoLayout(@NonNull Context context) {
        this(context, null);
    }

    public MagnifierAutoLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MagnifierAutoLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        init();
    }


    public void setMagnifierBuilder(MagnifierBuilder magnifierBuilder) {
        this.builder = magnifierBuilder;
        init();
    }


    private void init() {
        //绘制放大镜边缘投影的画笔
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(builder.getStrokeWidth());

        mPaintShadow = new Paint(Paint.ANTI_ALIAS_FLAG);
        //设置放大镜边缘的投影
        mPaintShadow.setShadowLayer(20, 6, 6, Color.BLACK);
        //绘制Bitmap的画笔
        mPath = new Path();

    }


    @Override
    protected void dispatchDraw(Canvas canvas) {
        if (mIsShowMagnifier && builder != null) {
            //创建整个布局内容的Bitmap


            float magnifierWidthAndHeight = builder.getMagnifierRadius() * 2 / builder.getScaleRate();
            //计算出该裁剪的区域(就是使手指所在的点在要裁剪的Bitmap的中央)，并进行边界值处理(开始裁剪的X点不能小于0和大于Bitmap的宽度，并且X点的位置加上要裁剪的宽度不能大于Bitmap的宽度，Y点也是一样)
            int cutX = Math.max((int) (mShowMagnifierX - builder.getMagnifierRadius() - magnifierWidthAndHeight / 2), 0);
            int cutY = Math.min(Math.max((int) (mShowMagnifierY + builder.getMagnifierRadius() - magnifierWidthAndHeight / 2), 0), mBitmap.getHeight());
            //适配边界值
            int cutWidth = magnifierWidthAndHeight + cutX > mBitmap.getWidth() ? mBitmap.getWidth() - cutX : (int) magnifierWidthAndHeight;
            int cutHeight = magnifierWidthAndHeight + cutY > mBitmap.getHeight() ? mBitmap.getHeight() - cutY : (int) magnifierWidthAndHeight;


            if (cutWidth <= 0 || cutHeight == 0) {
                return;
            }
            cutBitmap = Bitmap.createBitmap(mBitmap, cutX, cutY, cutWidth, cutHeight);
            //将裁剪出来的区域放大
            scaledBitmap = Bitmap.createScaledBitmap(cutBitmap, (int) (cutBitmap.getWidth() * builder.getScaleRate()), (int) (cutBitmap.getHeight() * builder.getScaleRate()), true);
            //绘制放大后的Bitmap，由于Bitmap的缩放是从左上点开始的因此还要根据缩放的比例进行相应的偏移展示
            roundBitmap = BitmapHelper.GetRoundedCornerBitmap(scaledBitmap);


            RectF rectf;
            RectF rectF = new RectF(builder.getLeftSpace(), builder.getTopSpace(), roundBitmap.getWidth() + builder.getLeftSpace(), roundBitmap.getHeight() + builder.getTopSpace());
            //判断是否在 rectF 范围
            float x = mX;
            float y = mY;
            float max = 0;
            if (builder.getShouldAutoMoveMagnifier()) {
                if (x >= rectF.left && x <= rectF.right && y >= rectF.top && y <= rectF.bottom) {
                    //移动到左边
                    max = (getWidth() - roundBitmap.getWidth() - builder.getLeftSpace() * 2);
                    rectf = new RectF(builder.getLeftSpace() + (getWidth() - roundBitmap.getWidth() - builder.getLeftSpace() * 2), builder.getTopSpace(), roundBitmap.getWidth() + builder.getLeftSpace() + max, roundBitmap.getHeight() + builder.getTopSpace());
                } else {
                    rectf = new RectF(builder.getLeftSpace(), builder.getTopSpace(), roundBitmap.getWidth() + builder.getLeftSpace(), roundBitmap.getHeight() + builder.getTopSpace());

                }
            } else {
                rectf = new RectF(builder.getLeftSpace(), builder.getTopSpace(), roundBitmap.getWidth() + builder.getLeftSpace(), roundBitmap.getHeight() + builder.getTopSpace());

            }


            canvas.drawRoundRect(rectf, 15, 15, paint);
            canvas.save();
            canvas.drawBitmap(roundBitmap, builder.getLeftSpace() + max, builder.getTopSpace(), null);
            canvas.restore();

        } else {

            super.dispatchDraw(canvas);
        }
    }


    public void setTouch(MotionEvent event, ViewGroup viewGroup) {
        release();
        BitmapHelper.recycler(mBitmap);


        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mShowTime = System.currentTimeMillis();
                mShowMagnifierX = event.getX() + builder.getMagnifierRadius();
                mShowMagnifierY = event.getY() - builder.getMagnifierRadius();
                mX = event.getX();
                mY = event.getY();

                break;
            case MotionEvent.ACTION_MOVE:
                mIsShowMagnifier = true;
                mShowMagnifierX = event.getX() + builder.getMagnifierRadius();
                mShowMagnifierY = event.getY() - builder.getMagnifierRadius();

                mX = event.getX();
                mY = event.getY();
                //重绘
//                postInvalidate();
                break;
            case MotionEvent.ACTION_UP:
                mIsShowMagnifier = false;

                break;
        }

        if (!BitmapHelper.isNotEmpty(mBitmap)) {
            mBitmap = Bitmap.createBitmap(viewGroup.getWidth(), (int) (viewGroup.getHeight()), Bitmap.Config.ARGB_4444);
            Canvas canvas = new Canvas(mBitmap);

            canvas.clipRect(0, mShowMagnifierY, viewGroup.getWidth(), mShowMagnifierY + builder.getTopSpace());
            viewGroup.draw(canvas);
        }
        postInvalidate();

    }

    public void release() {
        BitmapHelper.recycler(cutBitmap, scaledBitmap, roundBitmap, mBitmap);
    }

}
