package com.qf.lingshixiaomaio.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.qf.lingshixiaomaio.R;
import com.qf.lingshixiaomaio.adapter.ItemListAdapter;
import com.qf.lingshixiaomaio.application.MyApplication;
import com.qf.lingshixiaomaio.customView.LoadingView;
import com.qf.lingshixiaomaio.model.ItemListEntity;
import com.qf.lingshixiaomaio.util.Constants;
import com.qf.lingshixiaomaio.util.JsonUtils;

import org.json.JSONObject;

import java.util.List;


public class SearchResultActivity extends Activity implements OnClickListener, OnItemClickListener {
	private TextView tv_search_result_title;
	private GridView gv_search_result;
	private MyResponseListener mListener = new MyResponseListener();
	private List<ItemListEntity> itemList_list;
	private MyApplication myApplication;
	private ItemListAdapter itemListAdapter;
	private Button btn_search_result_back;
	private LoadingView view_search_result_loading;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_result);
		
		init();
		loadData();
	}

	private void init() {
		myApplication = (MyApplication) (this.getApplication());
		tv_search_result_title = (TextView) findViewById(R.id.tv_search_result_title);
		gv_search_result = (GridView) findViewById(R.id.gv_search_result);
		gv_search_result.setOnItemClickListener(this);
		btn_search_result_back = (Button) findViewById(R.id.btn_search_result_back);
		view_search_result_loading = (LoadingView) findViewById(R.id.view_search_result_loading);
		btn_search_result_back.setOnClickListener(this);
		view_search_result_loading.startLoading();
		itemListAdapter = new ItemListAdapter(this, R.layout.goodslist_item,
				myApplication.getRequestQueue());
		gv_search_result.setAdapter(itemListAdapter);
	}

	private void loadData() {
		Intent intent = getIntent();
		String keyword = intent.getStringExtra("keyword");
		tv_search_result_title.setText(keyword);
		String url = String.format(Constants.URL.SEARCH, keyword);
		myApplication.downLoadJson(url, mListener, mListener);

	}

	private class MyResponseListener implements Listener<JSONObject>,
			ErrorListener {

		@Override
		public void onErrorResponse(VolleyError error) {

		}

		@Override
		public void onResponse(JSONObject response) {
			itemList_list = JsonUtils.parseItemListJson(response);
			itemListAdapter.setDatas(itemList_list);
			view_search_result_loading.setVisibility(View.GONE);
			view_search_result_loading.stopLoading();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_search_result_back:
			onBackPressed();
			break;

		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent = new Intent(this,ItemDetialActivity.class);
		intent.putExtra("id", itemList_list.get(position).getId());
		startActivity(intent);
	}
}
