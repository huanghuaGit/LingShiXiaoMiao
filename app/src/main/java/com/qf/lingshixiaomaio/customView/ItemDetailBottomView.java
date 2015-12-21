package com.qf.lingshixiaomaio.customView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.qf.lingshixiaomaio.R;
import com.qf.lingshixiaomaio.activity.ItemDetialActivity;
import com.qf.lingshixiaomaio.adapter.ItemListAdapter;
import com.qf.lingshixiaomaio.model.ItemListEntity;
import com.qf.lingshixiaomaio.util.JsonUtils;

import org.json.JSONObject;

import java.util.List;

/**
 * 猜你喜欢布局
 * 
 * @author ZL
 * 
 */
public class ItemDetailBottomView extends LinearLayout implements
		OnItemClickListener {

	private GridView gridView_item_detail_guesslove;// 猜你喜欢gridView
	private List<ItemListEntity> list_item_detail_guessLove;// 猜你喜欢数据集合
	private ItemListAdapter itemListAdapter;// 适配器
	private RequestQueue mQueue;// 请求队列

	public ItemDetailBottomView(Context context) {
		super(context);
		initView();
	}

	private void initView() {
		LayoutInflater.from(getContext()).inflate(
				R.layout.view_item_detail_bottom, this, true);

		mQueue = Volley.newRequestQueue(getContext());

		// 初始化gridView
		gridView_item_detail_guesslove = (GridView) findViewById(R.id.gridView_item_detail_guesslove);
		// 初始化适配器
		itemListAdapter = new ItemListAdapter(getContext(),
				R.layout.goodslist_item, mQueue);
		gridView_item_detail_guesslove.setAdapter(itemListAdapter);
		// 监听商品的点击跳转到详情页事件
		gridView_item_detail_guesslove.setOnItemClickListener(this);
	}

	// 加载数据
	public void loadData(JSONObject response) {
		// 将数据解析成对象放进集合中
		list_item_detail_guessLove = JsonUtils
				.parseItemDetailGuessLoveJson(response);
		// 添加数据到适配器
		itemListAdapter.setDatas(list_item_detail_guessLove);
	}

	// 监听商品的点击跳转到详情页事件
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent = new Intent(getContext(), ItemDetialActivity.class);
		// 将商品id传递过去详情页
		intent.putExtra("id", list_item_detail_guessLove.get(position).getId());
		getContext().startActivity(intent);
	}
}
