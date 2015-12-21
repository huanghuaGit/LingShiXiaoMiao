package com.qf.lingshixiaomaio.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.qf.lingshixiaomaio.R;


/**商品促销与特殊优惠页面
 * @author ZL
 *
 */
public class DiscountActivity extends Activity implements OnClickListener {

	private RelativeLayout rl_action_discount_container;// 优惠活动栏父布局
	private ImageView image_back;//回退键
	
	private Intent intent;
	//ItemDetailHeadView传递过来的参数
	private int action_type;
	private String action_info;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_discount);
		initView();
	}

	private void initView() {
		intent = getIntent();
		action_type = intent.getIntExtra("action_type", 0);
		action_info = intent.getStringExtra("action_info");
		
		rl_action_discount_container = (RelativeLayout) findViewById(R.id.rl_action_discount_container);
		//监听父容器的点击跳转事件
		rl_action_discount_container.setOnClickListener(this);
		image_back = (ImageView)findViewById(R.id.image_discount_back);
		image_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//回到上一个界面
				DiscountActivity.this.finish();
			}
		});
	}

	@Override
	public void onClick(View v) {
		//跳转到商品列表
		Intent intent = new Intent(this, DiscountItemListActivity.class);
		intent.putExtra("action_info", action_info);
		startActivity(intent);
	}
}
