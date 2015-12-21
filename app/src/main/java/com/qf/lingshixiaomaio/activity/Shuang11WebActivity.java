package com.qf.lingshixiaomaio.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

import com.qf.lingshixiaomaio.R;


public class Shuang11WebActivity extends Activity {
	private WebView webView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_1111web);
		init();
	}
	private void init() {
		webView = (WebView) findViewById(R.id.wv_1111);
		Intent intent = getIntent();
		String url = intent.getStringExtra("info");
		webView.loadUrl(url);
	}
}
