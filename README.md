# MagnifyingLayout（放大镜）
## 自定义Layout 实现布局跟随手指移动放大局部布局效果、布局内支持任意View

![device-2021-12-22-103424](https://user-images.githubusercontent.com/70507884/147026581-1c3d5fdb-0511-4f74-bca1-a4f6d72ce6a3.gif)


使用步骤:

## 集成


**Step1:** 
项目根目录build文件中配置jitpack maven
```
 repositories {
        maven { url 'https://jitpack.io' }
    }
```

**Step2:**
app目录build文件中配置引用
```
    implementation 'com.github.ljlstudio:MagnifyingLayout:Tag' ..tag为最新release 版本（1.0.1）
```

## 代码中使用


**Step1**
布局中使用MagnifierAutolayout
```

    <com.lee.magnifyinglib.MagnifierAutoLayout
        android:id="@+id/magnifier"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
```

**Step2**
创建builder设置放大镜参数并使用
```

MagnifierAutoLayout magnifierAutoLayout = findViewById(R.id.magnifier);
 
 MagnifierBuilder magnifierBuilder = new MagnifierBuilder(this)
                .widthMagnifierRadius(DisplayUtil.dip2px(this, 50))
                .widthMagnifierScaleRate(1.3f)
                .widthMagnifierShouldAutoMoveMagnifier(true)
                .widthMagnifierStrokeWidth(DisplayUtil.dip2px(this, 5))
                .widthMagnifierLeftSpace(DisplayUtil.dip2px(this, 10))
                .widthMagnifierTopSpace(DisplayUtil.dip2px(this, 100));
                
 magnifierAutoLayout.setMagnifierBuilder(magnifierBuilder);

```

**Step3**
在需要手势的地方传递MotionEvent以及需要放大的布局给MagnifierAutoLayout
```
 @Override
    public boolean onTouch(View v, MotionEvent event) {
        magnifierAutoLayout.setTouch(event, touchLayout);
        return true;

    }
```
## 未完待续。。后续会增加更多支持

