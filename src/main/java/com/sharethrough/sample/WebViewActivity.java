package com.sharethrough.sample;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import com.sharethrough.sample.R;

public class WebViewActivity extends Activity {

    private WebView webView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mt_web_view);

        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(getIntent().getExtras().getString("link"));
    }

}
