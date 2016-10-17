package com.hahaoop.demo;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import com.hahaoop.simpleadbanner.BannerAdapter;
import com.hahaoop.simpleadbanner.SimpleAdBanner;
import com.hahaoop.simpleadbanner.UpdateUI;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SimpleAdBanner banner;
    private List<Drawable> ds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        banner = (SimpleAdBanner) findViewById(R.id.banner);
//        banner.setIndicatorPosition(SimpleAdBanner.IndicatorPosition.RIGHT);

//        banner.setPages(getDatas(), new UpdateUI() {
//            @Override
//            public void updateUI(ImageView img, int position) {
//                img.setImageDrawable(ds.get(position));
//            }
//        });

        banner.setPages(getDatas(), new UpdateUI() {
            @Override
            public void updateUI(ImageView img, int position) {
                img.setImageDrawable(ds.get(position));
            }
        },new int[]{R.drawable.indicator_normal,R.drawable.indicator_selected});

        banner.setOnBannerItemClickListener(new BannerAdapter.OnBannerItemClickListener() {
            @Override
            public void onBannerItemClick(int position) {
                ds.add(getResources().getDrawable(R.drawable.test1));
                banner.notifyDataSetChanged(ds.size());
                Toast.makeText(MainActivity.this,"item"+position,Toast.LENGTH_SHORT).show();
            }
        });

//        banner.setIntervalTime(5000);
//        banner.setLooping(false);
    }

    private List<Drawable> getDatas(){
        ds = new ArrayList<>();
        ds.add(getResources().getDrawable(R.drawable.test1));
        ds.add(getResources().getDrawable(R.drawable.test2));
        ds.add(getResources().getDrawable(R.drawable.test3));
        return ds;
    }
}
