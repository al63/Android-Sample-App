Android-Sample-App
==================

An Android application illustrating how to integrate the Sharethrough SDK to show in-feed native ads.


## Getting started##

1. Clone the sample app repository to your computer
2. Follow the below Build Instructions
4. Connect your Android device by USB
5. Load app onto your device, e.g. `./gradlew installDebug`
6. On your Android device, find the icon for this app and press it


### Build Instructions
#### Android Studio using Gradle Instructions

Copy the .aar file from [here](https://s3.amazonaws.com/str-android-sdk/sharethrough-android-sdk.aar) to a folder named "libs" in your project
and add the following to your build.gradle:

```Groovy
repositories {
        flatDir {
                dirs 'libs'
        }
}

dependencies {
        compile(name:'sharethrough-android-sdk.aar', ext:'aar')
}
```

Now follow the Common Instructions below to put Ads into your app

#### Eclipse using the ADT Plugin
1. Copy the zip file from [here](https://s3.amazonaws.com/str-android-sdk/sharethrough-android-sdk.zip) and unzip it to a new folder.
2. From Eclipse, File -> Import and select Android. Choose Existing Android Code into Workspace.
3. Browse to where you unzipped Sharethrough_Android_SDK and click Finish.
4. Go to your application's Project -> Properties and press the Add... button to add the Sharethrough_Android_SDK as a library to your app project
5. Add the following to your `AndroidManifest.xml` if it's not already there:
```XML
  <uses-permission android:name="android.permission.INTERNET" />
  <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```
Now follow the Common Instructions below to put Ads into your app

### Common instructions for integrating ads into your app

Add Google Play Services to your app via the instructions [here](https://developer.android.com/google/play-services/setup.html).

First, create a layout (e.g., `ad.xml`) for showing ads with the following fields:

1. `TextView` title
2. `TextView` description (optional)
3. `TextView` advertiser
4. `FrameLayout` thumbnail
5. `ImageView` optout_icon
6. `ImageView` brand_logo

for example:

```XML
<?xml version="1.0" encoding="utf-8"?>

<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
        android:layout_width="match_parent" android:layout_height="match_parent">
        <TextView android:id="@+id/title"
                android:layout_width="wrap_content" android:layout_height="wrap_content"/>
<!-- optional -->
        <TextView android:id="@+id/description"
                android:layout_width="wrap_content" android:layout_height="wrap_content"/>

        <TextView android:text="Sponsored by "
                android:layout_width="wrap_content" android:layout_height="wrap_content"/>
        <TextView android:id="@+id/advertiser"
                android:layout_width="wrap_content" android:layout_height="wrap_content"/>

        <FrameLayout android:id="@+id/thumbnail"
                android:layout_width="wrap_content" android:layout_height="wrap_content"/>
        <ImageView android:id="@+id/optout_icon"
                android:layout_width="wrap_content" android:layout_height="wrap_content"/>
        <ImageView android:id="@+id/brand_logo"
                android:layout_width="wrap_content" android:layout_height="wrap_content"/>
</LinearLayout>
```

#####to place an ad directly:#####
use `BasicAdView` in your activity's layout XML:
```XML
<com.sharethrough.sdk.BasicAdView android:id="@+id/sharethrough_ad"
        android:layout_width="match_parent" android:layout_height="wrap_content"/>
```

and in your activity's Java code:
```Java
import com.sharethrough.sdk.BasicAdView;
//...
Sharethrough sharethrough = new Sharethrough(getContext(), YOUR_SHARETHROUGH_PLACEMENT_KEY);
BasicAdView adView = (BasicAdView)findViewById(R.id.sharethrough_ad);
// make sure to set adView's visibility to GONE in case no ads are available
adView.setVisibility(View.GONE);
adView.prepareWithResourceIds(R.layout.ad, R.id.title, R.id.description, R.id.advertiser, R.id.thumbnail, R.id.optout_icon, R.id.brand_logo);
// or, if you don't want to show Description text
adView.prepareWithResourceIds(R.layout.ad, R.id.title, -1, R.id.advertiser, R.id.thumbnail, R.id.optout_icon, R.id.brand_logo);

// register callbacks to hide or show adView based on ads availability
sharethrough.setOnStatusChangeListener(new Sharethrough.OnStatusChangeListener() {
    @Override
    public void newAdsToShow() {
        adView.setVisibility(View.VISIBLE);
    }

    @Override
    public void noAdsToShow() {
        adView.setVisibility(View.GONE);
    }
});

sharethrough.putCreativeIntoAdView(adView);
```

#####or to place ads in a `ListView` or other `AdapterView`:#####
```Java
import com.sharethrough.sdk.Sharethrough;
import com.sharethrough.sdk.SharethroughListAdapter;
// create your own ListAdapter myListAdapter = ...
// and your own ListView myListView = findViewById(R.id.my_list);
Sharethrough sharethrough = new Sharethrough(getContext(), YOUR_SHARETHROUGH_PLACEMENT_KEY);
SharethroughListAdapter sharethroughListAdapter = new SharethroughListAdapter(getContext(), myListAdapter, sharethough, R.layout.ad, R.id.title, R.id.description, R.id.advertiser, R.id.thumbnail, R.id.optout_icon, R.id.brand_logo);
// or, if you don't want to show Description text
SharethroughListAdapter sharethroughListAdapter = new SharethroughListAdapter(getContext(), myListAdapter, R.layout.ad, R.id.title, -1, R.id.advertiser, R.id.thumbnail, R.id.optout_icon, R.id.brand_logo);


myListView.setAdapter(sharethroughListAdapter);
// optionally:
myListView.setOnItemClickListener(sharethroughAdapter.createOnItemClickListener(myItemClickListener));
myListView.setOnItemLongClickListener(sharethroughAdapter.createOnItemLongClickListener(myItemLongClickListener));
myListView.setOnItemSelectedListener(sharethroughAdapter.createOnItemSelectListener(myItemSelectedListener));
```
