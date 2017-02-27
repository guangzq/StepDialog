# StepDialog
一行代码实现ofo app首次注册时的步骤控件

# Preview
gif

# Gradle
```
dependencies {
    compile 'compile 'com.zqg:library:1.0.0'
}
```

# Usage
```
StepDialog.getInstance()
                .setImages(new int[]{R.drawable.new_user_guide_1, R.drawable.new_user_guide_2, R.drawable.new_user_guide_3, R.drawable.new_user_guide_4})
                .show(getFragmentManager());
```

# API
| API           | 释义           |
| ------------- |:-------------:|
| setPageTransformer      | ViewPager切换动画方式，分为DepthPageTransformer和ZoomOutPageTransformer两种方式 |
|   setCanceledOnTouchOutside    | 点击四周是否取消dialog,默认取消      | 
| setOutsideIsTransparent | 设置背景四周是否透明,调用时需要放到show方法后面      |
