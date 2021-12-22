package com.lee.magnifyinglib;

import android.content.Context;

import com.lee.magnifyinglib.utils.DisplayUtil;


/**
 * Author : 李嘉伦
 * e-mail : lijialun@angogo.cn
 * date   : 2021/12/179:13
 * Package: com.lee.magnifyingapp.magni
 * desc   :
 */
public class MagnifierBuilder {
    private float mMagnifierRadius;//放大镜的半径
    private float mScaleRate;//放大比例

    private float strokeWidth;//左上角放大镜边大小
    private float leftSpace;//放大镜距离左边距离
    private float topSpace;//放大镜距离上方距离
    private boolean shouldAutoMoveMagnifier;//是否支持触摸到放大镜范围后自动移动边框


    public float getMagnifierRadius() {
        return mMagnifierRadius;
    }

    public float getScaleRate() {
        return mScaleRate;
    }

    public float getStrokeWidth() {
        return strokeWidth;
    }

    public float getLeftSpace() {
        return leftSpace;
    }

    public float getTopSpace() {
        return topSpace;
    }

    public boolean getShouldAutoMoveMagnifier() {
        return shouldAutoMoveMagnifier;
    }

    public MagnifierBuilder(Context context) {
        widthMagnifierRadius(DisplayUtil.dip2px(context, 50));
        widthMagnifierScaleRate(1.3f);
        widthMagnifierStrokeWidth(DisplayUtil.dip2px(context, 5));
        widthMagnifierLeftSpace(DisplayUtil.dip2px(context, 10));
        widthMagnifierTopSpace(DisplayUtil.dip2px(context, 100));
        widthMagnifierShouldAutoMoveMagnifier(false);
    }

    /**
     * 设置是否支持手指触摸到放大镜范围自动移动到右边
     *
     * @param b
     */
    public MagnifierBuilder widthMagnifierShouldAutoMoveMagnifier(boolean b) {
        this.shouldAutoMoveMagnifier = b;
        return this;
    }

    /**
     * 设置距离顶部距离
     *
     * @param topSpace
     */
    public MagnifierBuilder widthMagnifierTopSpace(int topSpace) {
        this.topSpace = topSpace;
        return this;
    }

    /**
     * 设置距离左边距离
     *
     * @param leftSpace
     */
    public MagnifierBuilder widthMagnifierLeftSpace(int leftSpace) {
        this.leftSpace = leftSpace;
        return this;
    }


    /**
     * 设置放大镜边框粗细
     *
     * @param strokeWidth
     * @return
     */
    public MagnifierBuilder widthMagnifierStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
        return this;
    }


    /**
     * 设置放大镜图像缩放比例
     *
     * @param rate
     */
    public MagnifierBuilder widthMagnifierScaleRate(float rate) {
        this.mScaleRate = rate;
        return this;
    }


    /**
     * 设置放大镜半径
     *
     * @param radius
     */
    public MagnifierBuilder widthMagnifierRadius(int radius) {
        mMagnifierRadius = radius;
        return this;
    }


} 