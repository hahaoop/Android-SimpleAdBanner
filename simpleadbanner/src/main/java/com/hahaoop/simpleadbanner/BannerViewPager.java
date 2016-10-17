package com.hahaoop.simpleadbanner;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;


/**
 * CarouselViewPager
 * @author hahaoop
 */
public class BannerViewPager extends ViewPager {
    private static final int MSG_AUTO_DISPLAY = 1;
    private int intervalTime = 2000;
    private boolean isLoop = true;

    private int index;
    private boolean turning; // prevent sent repeat handler message
    private Drawable[] indicatorRes;
    private List<ImageView> indicators = new ArrayList<>();
    private Handler handler;

    private OnPageChangeListener onPageChangeListener = new OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            int size = indicators.size();
            if(size==0){
                return;
            }
            int realPosition = position % size;
            for(int i=0;i<size;i++){
                if(i==realPosition){
                    indicators.get(i).setImageDrawable(indicatorRes[1]);
                } else{
                    indicators.get(i).setImageDrawable(indicatorRes[0]);
                }
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if(isLoop){
                if (ViewPager.SCROLL_STATE_DRAGGING == state) {
                    handler.removeMessages(MSG_AUTO_DISPLAY);
                } else if (ViewPager.SCROLL_STATE_IDLE == state) {
                    sendStartMsg();
                }
            }
        }
    };

    public BannerViewPager(Context context){
        this(context,null);
    }

    public BannerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        addOnPageChangeListener(onPageChangeListener);
        handler = new Handler(context.getMainLooper()){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case MSG_AUTO_DISPLAY:
                        if(msg.obj!=null){
                            index = (int) msg.obj;
                            setCurrentItem(++index);
                        } else{
                            setCurrentItem(BannerAdapter.MAX_VALUE/2+1);
                        }
                        break;
                    default:
                        break;
                }
            }
        };
        handler.sendEmptyMessageDelayed(MSG_AUTO_DISPLAY,intervalTime);
    }

    public void setIndicators(List<ImageView> indicators,Drawable[] res){
        this.indicators = indicators;
        this.indicatorRes = res;
    }

    public void stopBanner(){
        if(isLoop){
            turning = true;
            handler.removeMessages(MSG_AUTO_DISPLAY);
        }
    }

    public void startBanner(){
        if(isLoop){
            if(turning){
                sendStartMsg();
            }
        }
    }

    public void resetBanner(){
        handler.removeMessages(MSG_AUTO_DISPLAY);
        Message msg = Message.obtain();
        msg.what = MSG_AUTO_DISPLAY;
        msg.obj = BannerAdapter.MAX_VALUE/2;
        handler.sendMessageDelayed(msg, 300);
    }

    private void sendStartMsg(){
        Message msg = Message.obtain();
        msg.what = MSG_AUTO_DISPLAY;
        msg.obj = getCurrentItem();
        handler.sendMessageDelayed(msg, intervalTime);
    }

    public void setIntervalTime(int intervalTime){
        this.intervalTime = intervalTime;
    }

    public void setLooping(boolean isLoop){
        this.isLoop = isLoop;
        if(!isLoop){
            handler.removeMessages(MSG_AUTO_DISPLAY);
        }
    }
}
