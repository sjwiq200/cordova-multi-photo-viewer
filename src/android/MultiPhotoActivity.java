package com.sjwiq200.plugin.multiphotoviewer;

import com.sjwiq200.cordova.photoviewer.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.LayoutInflater;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.ArrayList;

public class MultiPhotoActivity extends Activity {
    public static ArrayList source;

    RequestOptions requestOptions;

    public CustomPagerAdapter customPagerAdapter = null;
    public ViewPager viewPager = null;
    public TextView pageText = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getApplication().getResources().getIdentifier("activity_multi_photo", "layout", getApplication().getPackageName()));
        initImageView();
    }

    public void initImageView() {
        this.requestOptions = new RequestOptions();
        this.requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true);

        if (customPagerAdapter == null) {
            this.customPagerAdapter = new CustomPagerAdapter(this);
        }

        this.viewPager = findViewById(getApplication().getResources().getIdentifier("viewPager", "id", getApplication().getPackageName() ));
        this.viewPager.setAdapter(customPagerAdapter);

        this.pageText = findViewById(getApplication().getResources().getIdentifier("pageText", "id", getApplication().getPackageName() ));
        String text = "1/" + this.source.size();
        this.pageText.setText(text);

        this.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ///페이지 변경 체크
//                binding.setIsLastPage((imageList.size() != 0 && (position + 1 == imageList.size())));
                pageText.setText((position + 1)+"/" + source.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });



    }


    /***
     * sub class
     */




    class CustomPagerAdapter extends PagerAdapter {

        Context mContext;
        LayoutInflater mLayoutInflater;


        public CustomPagerAdapter(Context context) {
            mContext = context;
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }


        @Override
        public int getCount() {
            return source.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View itemView = mLayoutInflater.inflate(R.layout.layout_viewpager_childview_photoview, container, false);
            PhotoView imageView = (PhotoView) itemView.findViewById(R.id.imageView);
            imageView.setMaximumScale(10.0f);

            final PhotoView imageView2 = imageView;

            container.addView(itemView);

            PhotoViewer.callbackContext.success("tgqtqtq :: "+ source.get(position));
            Glide.with(mContext).load(source.get(position))
                    .into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
//                            if (resource.getIntrinsicWidth() > resource.getIntrinsicHeight()) {
//                                ///가로가 세로보다 큰경우
//                                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
//                            } else {
//                                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//                            }
//                            Glide.with(mContext).load(resource).apply(requestOptions2.override(imageSize.width, imageSize.height)).into(imageView);
                            Glide.with(mContext).load(resource).apply(requestOptions).into(imageView2);
                        }
                    });
            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((RelativeLayout) object);
        }
    }
}