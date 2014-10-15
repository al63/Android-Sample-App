package com.sharethrough.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.sharethrough.sdk.Test;

public class MyActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        ((TextView) findViewById(R.id.text)).setText(Test.MESSAGE);

        ListView menu = (ListView) findViewById(R.id.menu);
        menu.setAdapter(new ArrayAdapter<String>(this, R.layout.menu_item, R.id.text, new String[]{"Basic"}));
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
