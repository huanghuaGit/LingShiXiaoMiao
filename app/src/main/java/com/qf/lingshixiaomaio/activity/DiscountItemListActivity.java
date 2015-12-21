package com.qf.lingshixiaomaio.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.qf.lingshixiaomaio.R;
import com.qf.lingshixiaomaio.adapter.ItemListAdapter;
import com.qf.lingshixiaomaio.application.MyApplication;
import com.qf.lingshixiaomaio.model.ItemListEntity;
import com.qf.lingshixiaomaio.util.Constants;
import com.qf.lingshixiaomaio.util.JsonUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;



/**
 * 点击商品促销与特殊优惠页面后条转过来的商品列表
 * 
 * @author ZL
 * 
 */
public class DiscountItemListActivity extends Activity implements
		OnRefreshListener2<GridView>, OnItemClickListener {
	private PullToRefreshGridView gridView_discount;// 商品列表的gridview

	private MyApplication myApplication;
	private List<ItemListEntity> list_discount_item;// 打折商品信息的集合
	private List<ItemListEntity> list_total;// 总的list
	private ItemListAdapter discountItemListAdapter;// 打折商品列表的适配器
	private int pg_cur = 1;// 当前页码数

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_discount_item_list);
		initView();
		loadData();
	}

	// 加载数据
	private void loadData() {
		String url = String
				.format(Constants.URL.URL_DISCOUNT_ITEM_LIST, pg_cur);
		myApplication.downLoadJson(url, listener, listener);
	}

	private MyDiscountListener listener = new MyDiscountListener();

	class MyDiscountListener implements Listener<JSONObject>, ErrorListener {

		@Override
		public void onErrorResponse(VolleyError error) {
			try {
				Toast.makeText(DiscountItemListActivity.this, "加载网络超时，请检查网络",
						Toast.LENGTH_SHORT).show();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		@Override
		public void onResponse(JSONObject response) {
			// 通知控件加载完成
			gridView_discount.onRefreshComplete();

			list_discount_item = JsonUtils.parseDiscountItemListJson(response);
			// 将数据源设置给适配器
			discountItemListAdapter.addDatas(list_discount_item);

			list_total.addAll(list_discount_item);
		}

	}

	private void initView() {
		list_total = new ArrayList<ItemListEntity>();

		myApplication = (MyApplication) getApplication();

		// 初始化gridView属性
		gridView_discount = (PullToRefreshGridView) findViewById(R.id.gridView_discount_list);
		gridView_discount.setMode(Mode.BOTH);
		gridView_discount.setOnRefreshListener(this); // 设置listView上拉和下拉的监听事件
		gridView_discount.setOnItemClickListener(this);// 监听listView的点击跳转事件

		// 初始化适配器
		discountItemListAdapter = new ItemListAdapter(this,
				R.layout.goodslist_item, myApplication.getRequestQueue());

		gridView_discount.setAdapter(discountItemListAdapter);
	}

	/*
	 * gridView的上拉加载和下拉刷新的方法
	 */
	@Override
	public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
		// 下拉刷新

	}

	private Handler handler = new Handler();

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
		// 上拉加载下一页数据
		Toast.makeText(this, "开始刷新...", Toast.LENGTH_SHORT).show();
		new Thread() {
			public void run() {
				handler.post(new Runnable() {
					@Override
					public void run() {
						// 加载上拉数据
						loadNextPageData();
					}
				});
			};
		}.start();
	}

	// 加载下一页数据
	public void loadNextPageData() {
		pg_cur++;
		loadData();
	}

	// 监听点击跳转到详情页的方法
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// 通过status的值来判断是否售完
		if (list_total.get(position).getStatus() == 1) {
			// 如果已经售完
			view.setClickable(false);// 设置此商品不可点击
		} else {
			// 如果还没售完,则跳转到详情页
			Intent intent = new Intent(this, ItemDetialActivity.class);
			// 将对应的id传递过去
			intent.putExtra("id", list_total.get(position).getId());
			startActivity(intent);
		}

	}
}
