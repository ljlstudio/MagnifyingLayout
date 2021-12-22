package com.lee.magnifyinglayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Magnifier;

import com.lee.magnifyinglib.MagnifierAutoLayout;
import com.lee.magnifyinglib.MagnifierBuilder;
import com.lee.magnifyinglib.utils.DisplayUtil;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    private MagnifierAutoLayout magnifierAutoLayout;
    private ConstraintLayout touchLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        magnifierAutoLayout = findViewById(R.id.magnifier);

        touchLayout = findViewById(R.id.layout);
        touchLayout.setOnTouchListener(this);


        MagnifierBuilder magnifierBuilder = new MagnifierBuilder(this)
                .widthMagnifierRadius(DisplayUtil.dip2px(this, 50))
                .widthMagnifierScaleRate(1.3f)
                .widthMagnifierShouldAutoMoveMagnifier(true)
                .widthMagnifierStrokeWidth(DisplayUtil.dip2px(this, 5))
                .widthMagnifierLeftSpace(DisplayUtil.dip2px(this, 10))
                .widthMagnifierTopSpace(DisplayUtil.dip2px(this, 100));

        magnifierAutoLayout.setMagnifierBuilder(magnifierBuilder);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        magnifierAutoLayout.setTouch(event, touchLayout);
        return true;

    }
}