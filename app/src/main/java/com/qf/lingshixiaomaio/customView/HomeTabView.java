package com.qf.lingshixiaomaio.customView;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.qf.lingshixiaomaio.R;
import com.qf.lingshixiaomaio.activity.GoodsListActivity;
import com.qf.lingshixiaomaio.adapter.HomeTabAdpater;
import com.qf.lingshixiaomaio.model.HomeTabEntity;
import com.qf.lingshixiaomaio.util.JsonUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HomeTabView extends LinearLayout implements OnItemClickListener{

	private GridView home_gridView;
	private HomeTabAdpater homeTabAdpater;
	private RequestQueue mQueue;
	private List<HomeTabEntity> home_tab_list;

	public HomeTabView(Context context) {
		super(context);
		initView();
	}

	public HomeTabView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();

	}

	private void initView() {
		mQueue = Volley.newRequestQueue(getContext());
		LayoutInflater.from(getContext()).inflate(R.layout.home_tabview, this,
				true);
		home_tab_list = new ArrayList<HomeTabEntity>();
		home_gridView = (GridView) findViewById(R.id.gridview);
		home_gridView.setOnItemClickListener(this);
	}

	public void loadData(JSONObject response) {
		home_tab_list = JsonUtils.parseHomeTabJson(response, home_tab_list);

		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		map.put(0, R.layout.home_tab_item);
		homeTabAdpater = new HomeTabAdpater(getContext(), map, mQueue);
		homeTabAdpater.setDatas(home_tab_list);
		home_gridView.setAdapter(homeTabAdpater);

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent = new Intent(getContext(), GoodsListActivity.class);
		intent.putExtra("id", home_tab_list.get(position).getId());
		intent.putExtra("title", home_tab_list.get(position).getTitle());
		getContext().startActivity(intent);
	}

}
