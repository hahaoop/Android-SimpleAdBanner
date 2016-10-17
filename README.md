# Android-SimpleAdBanner
This is a View for AD switch,you can set property for looping or stop looping,also you can set Indicators or not.
![Alt text](https://github.com/hahaoop/Android-SimpleAdBanner/raw/master/Screenshots/screenshots1.gif)
#### Config in xml
```
	<com.hahaoop.simpleadbanner.SimpleAdBanner
        android:id="@+id/banner"
        android:layout_width="match_parent"
        banner:intervalTime="2000"
        banner:isLoop="true"
        android:layout_height="150dp"/>
```
#### Config in java code
```
	banner.setPages(getDatas(), new UpdateUI() {
        @Override
        public void updateUI(ImageView img, int position) {
            img.setImageDrawable(ds.get(position));
        }
    },new int[]{R.drawable.indicator_normal,R.drawable.indicator_selected});

private List<Drawable> getDatas(){
        ds = new ArrayList<>();
        ds.add(getResources().getDrawable(R.drawable.test1));
        ds.add(getResources().getDrawable(R.drawable.test2));
        ds.add(getResources().getDrawable(R.drawable.test3));
        return ds;
    }
```