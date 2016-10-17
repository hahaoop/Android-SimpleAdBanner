package com.hahaoop.simpleadbanner;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * BannerAdapter
 * @author hahaoop
 */
public class BannerAdapter<T> extends PagerAdapter {

    public static final int MAX_VALUE = 2520*20000;  //2520 is the common multiple number for 1-9
    private boolean isRemove;  //the child view of container is removed

    private Context mContext;
    private ViewPager viewPager;
    private List<T> pages;
    private HashMap<Integer, ImageView> views = new HashMap<>(10, 0.75f);
    private UpdateUI updateUI;
    private OnBannerItemClickListener onBannerItemClickListener;

    public BannerAdapter(Context context, BannerViewPager viewPager, List<T> pages){
        this.mContext = context;
        this.viewPager = viewPager;
        if(pages==null){
            this.pages = new ArrayList<>();
        } else{
            this.pages = pages;
        }
    }

    public void setUpdateUI(@NonNull UpdateUI updateUI){
        this.updateUI = updateUI;
    }

    public void setOnBannerItemClickListener(@NonNull OnBannerItemClickListener onBannerItemClickListener){
        this.onBannerItemClickListener = onBannerItemClickListener;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int size = pages.size();
        if (size == 0) {
            return null;
        } else {
            //because viewpager need load previously,if the page's size < 3,
            // it will can't become loops banner
            if(pages.size()==2){
                size = size*2;
            }
            int viewsPostion = position % size;
            final int itemPosition;
//            if (views.get(viewsPostion) == null) {
//                ImageView view = new ImageView(mContext);
//                view.setScaleType(ImageView.ScaleType.FIT_XY);
//                if(pages.size()==2){
//                    itemPosition = viewsPostion%2;
//                }
//                else {
//                    itemPosition = viewsPostion;
//                }
//                updateUI.updateUI(view,itemPosition);
//                view.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if(onBannerItemClickListener!=null)
//                            onBannerItemClickListener.onBannerItemClick(itemPosition);
//                    }
//                });
//                views.put(viewsPostion, view);
//            }
//            ImageView img = views.get(viewsPostion);
//            for(int i=0;i<container.getChildCount();i++){
//                if(container.getChildAt(i)==img){
//                    container.removeView(img);
//                    isRemove = true;
//                }
//            }
//            container.addView(img);
//            return img;

            ImageView view = new ImageView(mContext);
            view.setScaleType(ImageView.ScaleType.FIT_XY);
            if(pages.size()==2){
                itemPosition = viewsPostion%2;
            }
            else {
                itemPosition = viewsPostion;
            }
            updateUI.updateUI(view,itemPosition);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onBannerItemClickListener!=null)
                        onBannerItemClickListener.onBannerItemClick(itemPosition);
                }
            });
            container.addView(view);
            return view;
        }
    }

    @Override
    public void startUpdate(ViewGroup container) {
        int position = viewPager.getCurrentItem();
        if (position == 0) {
            if(pages.size()>1)
                viewPager.setCurrentItem(MAX_VALUE / 2, false);
        }
        isRemove = false;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if(!isRemove){
            container.removeView((View) object);
        }
    }

    @Override
    public int getCount() {
        int size = pages.size();
        if(size==0){
            return 0;
        } else if(size==1){
            return 1;
        } else{
            return MAX_VALUE;
        }
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public interface OnBannerItemClickListener{
        void onBannerItemClick(int position);
    }

}