package com.sharethrough.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.sharethrough.sdk.Test;

import java.io.File;
import java.io.IOException;

public class MyActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Thread.currentThread().setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable ex) {
                try {
                    File hprof = new File("/sdcard/hprof." + System.currentTimeMillis());
                    Log.e("MEMORY", "dumping to " + hprof.getCanonicalPath());
                    Debug.dumpHprofData(hprof.getCanonicalPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        setContentView(R.layout.activity_my);

        ((TextView) findViewById(R.id.text)).setText(Test.MESSAGE);

        ListView menu = (ListView) findViewById(R.id.menu);
        menu.setAdapter(new ArrayAdapter<String>(this, R.layout.menu_item, R.id.text, new String[]{"Basic", "News Feed w/ Basic View", "Card Style w/ Basic View", "News Feed w/ ListAdapter"}));
        menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(MyActivity.this, BasicActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(MyActivity.this, ListAdapterWithBasicViewActivity.Feed.class));
                        break;
                    case 2:
                        startActivity(new Intent(MyActivity.this, ListAdapterWithBasicViewActivity.Card.class));
                        break;
                    case 3:
                        startActivity(new Intent(MyActivity.this, SharethroughListAdapterActivity.class));
                        break;
                }
            }
        });
    }
}
