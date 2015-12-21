package com.qf.lingshixiaomaio.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.qf.lingshixiaomaio.R;
import com.qf.lingshixiaomaio.application.MyApplication;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 欢迎页
 * 
 * @author JoshuaJan
 * 
 */
public class WelcomeActivity extends Activity {
	private Boolean isFirst;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				isFirst = MyApplication.getBoolean("isFirst");
				if (isFirst) {
					Intent intent = new Intent(WelcomeActivity.this,
							FirstWelcomeActivity.class);
					startActivity(intent);
					finish();
				} else {
					Intent intent = new Intent(WelcomeActivity.this,
							MainActivity.class);
					startActivity(intent);
					finish();
				}
			}
		}, 4000);
	}
}
