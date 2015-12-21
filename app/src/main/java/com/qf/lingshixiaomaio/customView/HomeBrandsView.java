package com.qf.lingshixiaomaio.customView;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.qf.lingshixiaomaio.R;
import com.qf.lingshixiaomaio.activity.GoodsListActivity;
import com.qf.lingshixiaomaio.adapter.HomeBrandsAdapter;
import com.qf.lingshixiaomaio.model.HomeCenterBrandsEntity;
import com.qf.lingshixiaomaio.util.JsonUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeBrandsView extends LinearLayout implements OnItemClickListener {

	public HomeBrandsView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	private ListView home_centerbrands_listView; // 显示数据的listView
	private List<HomeCenterBrandsEntity> home_centerbrands_list; // 用于存放数据的list
	private HomeBrandsAdapter homeBrandsAdapter;
	private RequestQueue mQueue;

	public HomeBrandsView(Context context) {
		super(context);
		initView();
	}

	private void initView() {
		mQueue = Volley.newRequestQueue(getContext());
		LayoutInflater.from(getContext()).inflate(
				R.layout.home_centerbrandsview, this, true);
		home_centerbrands_listView = (ListView) findViewById(R.id.home_brands_listView);
		home_centerbrands_listView.setOnItemClickListener(this);
	}

	public void loadData(JSONObject response) {

		home_centerbrands_list = JsonUtils.parseHomeCenterBrandsJson(response);

		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		map.put(0, R.layout.home_centerbrands_item);
		homeBrandsAdapter = new HomeBrandsAdapter(getContext(), map, mQueue);
		homeBrandsAdapter.setDatas(home_centerbrands_list);
		home_centerbrands_listView.setAdapter(homeBrandsAdapter);
	}

	//点击brands的item，将相应的属性数据传出去
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent = new Intent(getContext(), GoodsListActivity.class);
		intent.putExtra("brands_id", home_centerbrands_list.get(position)
				.getId());
		intent.putExtra("brands_title", home_centerbrands_list.get(position)
				.getTitle());
		intent.putExtra("brands_image_url", home_centerbrands_list.get(position)
				.getImg_url());
		intent.putExtra("brands_time", home_centerbrands_list.get(position)
				.getTime());
		getContext().startActivity(intent);
	}
}
