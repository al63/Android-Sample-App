package com.sharethrough.sample;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import com.sharethrough.sdk.Test;

public class MyActivity extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_my);

    ((TextView)findViewById(R.id.text)).setText(Test.MESSAGE);
  }
}
