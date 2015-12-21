package com.qf.lingshixiaomaio.activity;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.qf.lingshixiaomaio.R;
import com.qf.lingshixiaomaio.fragment.HomeFragment;
import com.qf.lingshixiaomaio.fragment.MineFragment;
import com.qf.lingshixiaomaio.fragment.SaleFragment;
import com.qf.lingshixiaomaio.fragment.SubjectFragment;


public class MainActivity extends Activity implements OnCheckedChangeListener {
	private RadioGroup radioGroup; // 底部导航栏

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
	}

	private void initView() {
		radioGroup = (RadioGroup) findViewById(R.id.home_rg_tab);
		radioGroup.setOnCheckedChangeListener(this);
		radioGroup.getChildAt(0).performClick();// 模拟第一个子的点击事件
	}

	/**
	 * RadioGroup的监听方法
	 */
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.home_btn_home:	
			fragmentManager(HomeFragment.getFragment());
			break;

		case R.id.home_btn_sale:
			fragmentManager(SaleFragment.getFragment());
			break;

		case R.id.home_btn_subject:
			fragmentManager(SubjectFragment.getFragment());
			break;

		case R.id.home_btn_mine:
			fragmentManager(MineFragment.getFragment());
			break;
		}
	}

	// 替换fragment的方法
	private void fragmentManager(Fragment fragment) {
		getFragmentManager().beginTransaction()
				.replace(R.id.home_layout_container, fragment).commit();
	}
	
	
	//按两次退出程序
	private long mExitTime;
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==KeyEvent.KEYCODE_BACK){
			if((System.currentTimeMillis()-mExitTime)>2000){
				Toast.makeText(this, "退出零食喵喵吗？", Toast.LENGTH_SHORT).show();
				mExitTime=System.currentTimeMillis();
			}
			else{
				finish();
			}
			return true;
			
		}
		return super.onKeyDown(keyCode, event);
	}
}
