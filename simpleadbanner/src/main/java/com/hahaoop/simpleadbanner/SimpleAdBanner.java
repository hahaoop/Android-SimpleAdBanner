package com.hahaoop.simpleadbanner;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * SimpleAdBanner
 * @author hahaoop
 */
public class SimpleAdBanner<T> extends RelativeLayout{

    private BannerViewPager viewPager;
    private LinearLayout indicators;
    private BannerAdapter adapter;
    private Drawable[] indicatorsRes = new Drawable[2];

    public SimpleAdBanner(Context context){
        this(context,null);
    }

    public SimpleAdBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SimpleAdBanner);
        int intervalTime = array.getInteger(R.styleable.SimpleAdBanner_intervalTime,2000);
        boolean isLoop = array.getBoolean(R.styleable.SimpleAdBanner_isLoop,true);
        array.recycle();
        View view = LayoutInflater.from(context).inflate(R.layout.ad_switch_layout,this,false);
        viewPager = (BannerViewPager) view.findViewById(R.id.viewpager);
        viewPager.setIntervalTime(intervalTime);
        viewPager.setLooping(isLoop);
        indicators = (LinearLayout) view.findViewById(R.id.indicators);
        addView(view);
    }

    public void setPages(List<T> pages,UpdateUI updateUI){
        adapter = new BannerAdapter(getContext(),viewPager,pages);
        if(updateUI == null){
            throw new RuntimeException("updateUI can not be null");
        }
        adapter.setUpdateUI(updateUI);
        viewPager.setAdapter(adapter);
    }

    public void setPages(List<T> pages, UpdateUI updateUI, int[] indicatorsRes){
        setIndicatorsRes(indicatorsRes);
        setIndicators(pages.size());
        adapter = new BannerAdapter(getContext(),viewPager,pages);
        if(updateUI == null){
            throw new RuntimeException("updateUI can not be null");
        }
        adapter.setUpdateUI(updateUI);
        viewPager.setAdapter(adapter);
    }

    public void setIndicatorsRes(int[] indicators){
        if(indicators!=null){
            indicatorsRes[0] = getResources().getDrawable(indicators[0]);
            indicatorsRes[1] = getResources().getDrawable(indicators[1]);
        } else{
            indicatorsRes[0] = getResources().getDrawable(R.drawable.indicator_normal);
            indicatorsRes[1] = getResources().getDrawable(R.drawable.indicator_selected);
        }
    }

    private void setIndicators(int pageSize){
        if(indicators.getChildCount()>0){
            indicators.removeAllViews();
        }
        List<ImageView> indicateImages = new ArrayList<>();
        if(pageSize>1){
            for(int i=0;i<pageSize;i++){
                ImageView indicator = new ImageView(getContext());
                indicator.setPadding(5,0,5,0);
                if(i==0){
                    indicator.setImageDrawable(indicatorsRes[1]);
                } else {
                    indicator.setImageDrawable(indicatorsRes[0]);
                }
                indicateImages.add(indicator);
                indicators.addView(indicator);
            }
        }
        viewPager.setIndicators(indicateImages,indicatorsRes);
    }

    public void notifyDataSetChanged(int dataSize){
        setIndicators(dataSize);
        viewPager.resetBanner();
        adapter.notifyDataSetChanged();
    }

    public void setOnBannerItemClickListener(@NonNull BannerAdapter.OnBannerItemClickListener onBannerItemClickListener){
        if(adapter==null){
            throw new RuntimeException("can't set OnBannerItemClickListener,should setPages() first");
        }
        adapter.setOnBannerItemClickListener(onBannerItemClickListener);
    }

    public void setIndicatorPosition(int position){
        LayoutParams params = (LayoutParams) indicators.getLayoutParams();
        if(position== IndicatorPosition.LEFT){
            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT,RelativeLayout.TRUE);
        } else if(position== IndicatorPosition.RIGHT){
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,RelativeLayout.TRUE);
        } else {
            params.addRule(RelativeLayout.CENTER_HORIZONTAL,RelativeLayout.TRUE);
        }
        indicators.setLayoutParams(params);
    }

    public interface IndicatorPosition{
        int LEFT=-1;
        int CENTER=0;
        int RIGHT=1;
    }

    public void setIntervalTime(int intervalTime){
        viewPager.setIntervalTime(intervalTime);
    }

    public void setLooping(boolean isLoop){
        viewPager.setLooping(isLoop);
    }

    public void stopBanner(){
        viewPager.stopBanner();
    }

    public void startBanner(){
        viewPager.startBanner();
    }

}
