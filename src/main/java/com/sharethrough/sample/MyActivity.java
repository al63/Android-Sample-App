package com.sharethrough.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MyActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Thread.currentThread().setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable ex) {
                Log.wtf("MEMORY", "uncaught", ex);
            }
        });

        setContentView(R.layout.main_activity);

        ListView menu = (ListView) findViewById(R.id.menu);

        menu.setAdapter(new ArrayAdapter<>(this, R.layout.menu_item, R.id.text, new String[]{
                "Sharethrough Sample App"}));
        menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(MyActivity.this, BasicActivity.class));
                        break;
                }
            }
        });
    }
}
