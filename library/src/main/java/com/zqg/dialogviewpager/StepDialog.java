package com.zqg.dialogviewpager;

import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;

import static com.zqg.dialogviewpager.R.id.viewpager;

/**
 * Created by zhuqiguang on 17/2/23.
 */
public class StepDialog extends DialogFragment {
    public int[] mImages;
    private ArrayList<View> pageViews;
    private int mPosition;
    private boolean isDragging;
    private ViewPager.PageTransformer mPageTransformer;
    public static StepDialog instance = null;
    private boolean mIsCancel;
    private boolean mIsTransparent;
    private OnCancelListener listener;

    public StepDialog() {
    }

    public static StepDialog getInstance() {
        if (instance == null) {
            instance = new StepDialog();
        }
        return instance;
    }

    /**
     * 设置图片
     *
     * @param images
     * @return
     */
    public StepDialog setImages(int[] images) {
        mImages = images;
        return this;
    }

    /**
     * 设置ViewPager切换动画方式
     *
     * @param pageTransformer
     * @return
     */
    public StepDialog setPageTransformer(ViewPager.PageTransformer pageTransformer) {
        mPageTransformer = pageTransformer;
        return this;
    }


    public StepDialog show(android.app.FragmentManager fragmentManager) {
        if (instance != null) {
            instance.show(fragmentManager, "ZqgDialogFragment");
        }
        return this;
    }

    /**
     * 点击四周是否取消dialog,默认取消
     *
     * @param isCancel
     * @return
     */
    public StepDialog setCanceledOnTouchOutside(boolean isCancel) {
        mIsCancel = isCancel;
        return this;
    }

    /**
     * 设置背景四周是否透明,调用时需要放到show方法后面
     *
     * @param isTransparent
     * @return
     */
    public StepDialog setOutsideIsTransparent(boolean isTransparent) {
        mIsTransparent = isTransparent;
        return this;
    }

    public StepDialog dissmiss() {
        getDialog().dismiss();
        return this;
    }

    /**
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Fresco.initialize(getActivity());
        pageViews = new ArrayList<>();
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().setCanceledOnTouchOutside(mIsCancel);
        View view = inflater.inflate(R.layout.fragment_dialog, container);
        final ViewPager viewPager = (ViewPager) view.findViewById(viewpager);
        CircleIndicator indicator = (CircleIndicator) view.findViewById(R.id.indicator);
        for (int image : mImages) {
            View inflate = inflater.inflate(R.layout.guide_pager_four, null);
            ImageView imageView = (ImageView) inflate.findViewById(R.id.iv_item_guide_img);
            Uri uri = new Uri.Builder().scheme("res").path(String.valueOf(image)).build();
            imageView.setImageURI(uri);
            pageViews.add(imageView);
        }
        viewPager.setPageTransformer(true, mPageTransformer);
        viewPager.setAdapter(new ZqgPagerAdapter());
        indicator.setViewPager(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mPosition = position;

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        isDragging = true;
                        break;
                    case ViewPager.SCROLL_STATE_SETTLING:
                        isDragging = false;
                        break;
                    case ViewPager.SCROLL_STATE_IDLE:
                        if (listener != null) listener.onCancel(mPosition);
                        if (mPosition == pageViews.size() - 1 && isDragging) {
                            getDialog().dismiss();
                        }
                        break;
                }

            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mIsTransparent) {
            Window window = getDialog().getWindow();
            WindowManager.LayoutParams windowParams = window.getAttributes();
            windowParams.dimAmount = 0.0f;
            window.setAttributes(windowParams);
        }
    }

    public interface OnCancelListener {
        void onCancel(int position);
    }

    public StepDialog setOnCancelListener(OnCancelListener listener) {
        this.listener = listener;
        return this;
    }

    class ZqgPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return pageViews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(pageViews.get(position));
            return pageViews.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(pageViews.get(position));
        }
    }

}
