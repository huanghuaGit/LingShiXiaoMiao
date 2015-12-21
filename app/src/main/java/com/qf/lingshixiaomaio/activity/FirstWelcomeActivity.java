package com.qf.lingshixiaomaio.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.qf.lingshixiaomaio.R;
import com.qf.lingshixiaomaio.application.MyApplication;

import java.util.ArrayList;
import java.util.List;


/**
 * 应用的第一次启动的展示
 * 
 * @author JoshuaJan
 * 
 */
public class FirstWelcomeActivity extends Activity implements OnClickListener {
	private ViewPager vp_first_welcome;
	private int[] images_id = { R.drawable.bg_guide_1, R.drawable.bg_guide_2,
			R.drawable.bg_guide_3, R.drawable.bg_guide_4 };
	private List<ImageView> images;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_first_welcome);
		init();
		initViewPager();
	}

	private void init() {
		// 设置为不是第一次进入应用
		MyApplication.putBoolean("isFirst", false);
		vp_first_welcome = (ViewPager) findViewById(R.id.vp_first_welcome);
	}

	private void initViewPager() {
		images = new ArrayList<ImageView>();
		for (int i = 0; i < images_id.length; i++) {
			ImageView imageView = new ImageView(this);
			imageView.setImageResource(images_id[i]);
			images.add(imageView);
		}
		images.get(images_id.length-1).setOnClickListener(this);
		vp_first_welcome.setAdapter(new MyPagerAdapter());
		

	}
	class MyPagerAdapter extends PagerAdapter{

		@Override
		public int getCount() {
			return images.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0==arg1;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(images.get(position));
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(images.get(position));
			return images.get(position);
		}
		
	}
	@Override
	public void onClick(View v) {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		finish();
	}
}
