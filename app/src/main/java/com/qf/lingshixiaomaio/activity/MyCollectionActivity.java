package com.qf.lingshixiaomaio.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.qf.lingshixiaomaio.R;
import com.qf.lingshixiaomaio.fragment.GoodsesCollectionFragment;
import com.qf.lingshixiaomaio.fragment.SubjectCollectionFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的收藏
 * @author JoshuaJan
 * 
 */
public class MyCollectionActivity extends FragmentActivity implements
		OnPageChangeListener, OnClickListener {
	private ViewPager vp;
	private TextView tv_mycollection_goodses, tv_mycollection_subject;
	private List<android.support.v4.app.Fragment> list_fragment;
	private Button btn_mycollection_back, btn_mycollection_edit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mycollection);
		init();
		initViewPager();
	}

	private void init() {
		vp = (ViewPager) findViewById(R.id.vp_mycollection);
		tv_mycollection_goodses = (TextView) findViewById(R.id.tv_mycollection_goodses);
		tv_mycollection_goodses.setOnClickListener(this);
		
		tv_mycollection_subject = (TextView) findViewById(R.id.tv_mycollection_subject);
		tv_mycollection_subject.setOnClickListener(this);
		btn_mycollection_back = (Button) findViewById(R.id.btn_mycollection_back);
		btn_mycollection_back.setOnClickListener(this);
		btn_mycollection_edit = (Button) findViewById(R.id.btn_mycollection_edit);
		btn_mycollection_edit.setOnClickListener(this);
	}

	private void initViewPager() {
		list_fragment = new ArrayList<android.support.v4.app.Fragment>();
		list_fragment.add(GoodsesCollectionFragment
				.getGoodsesCollectionFragment());
		list_fragment.add(SubjectCollectionFragment
				.getSubjectCollectionFragment());
		vp.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
		vp.setOnPageChangeListener(this);
	}

	class MyPagerAdapter extends FragmentPagerAdapter {
		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public int getCount() {
			return list_fragment.size();
		}

		@Override
		public android.support.v4.app.Fragment getItem(int arg0) {
			return list_fragment.get(arg0);
		}

	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int arg0) {
		switch (arg0) {
		case 0:
			tv_mycollection_goodses.setBackgroundResource(R.drawable.bg_lable1);
			tv_mycollection_goodses.setTextColor(getResources().getColor(
					R.color.white));
			tv_mycollection_subject.setBackgroundResource(R.drawable.bg_lable2);
			tv_mycollection_subject.setTextColor(getResources().getColor(
					R.color.maincolor));
			break;
		case 1:
			tv_mycollection_goodses.setBackgroundResource(R.drawable.bg_lable5);
			tv_mycollection_goodses.setTextColor(getResources().getColor(
					R.color.maincolor));
			tv_mycollection_subject.setBackgroundResource(R.drawable.bg_lable4);
			tv_mycollection_subject.setTextColor(getResources().getColor(
					R.color.white));
			break;

		default:
			break;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_mycollection_back:
			onBackPressed();
			break;
		case R.id.btn_mycollection_edit:
			break;
		case R.id.tv_mycollection_goodses:
			vp.setCurrentItem(0);
			break;
		case R.id.tv_mycollection_subject:
			vp.setCurrentItem(1);
			break;
		default:
			break;
		}
	}
}
