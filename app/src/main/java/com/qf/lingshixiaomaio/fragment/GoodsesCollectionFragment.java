package com.qf.lingshixiaomaio.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.qf.lingshixiaomaio.R;
import com.qf.lingshixiaomaio.activity.ItemDetialActivity;
import com.qf.lingshixiaomaio.adapter.ItemCollectionAdapter;
import com.qf.lingshixiaomaio.model.ItemListEntity;
import com.qf.lingshixiaomaio.util.CollectionDBHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 商品收藏Fragment
 * 
 * @author ZL
 * 
 */
public class GoodsesCollectionFragment extends Fragment implements
		OnItemClickListener {

	private static GoodsesCollectionFragment goodsesCollectionFragment;
	private View view;
	private GridView gv_mycollection_item;
	private Cursor cursor;
	private List<Map<String, Object>> list;
	private List<ItemListEntity> list_info;
	private ItemCollectionAdapter adapter;
	private RequestQueue mQueue;

	public static android.support.v4.app.Fragment getGoodsesCollectionFragment() {
		if (goodsesCollectionFragment == null) {
			goodsesCollectionFragment = new GoodsesCollectionFragment();
		}
		return goodsesCollectionFragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_mycollection_goodses, null,
				false);
		init();
		return view;
	}

	private void init() {
		mQueue = Volley.newRequestQueue(getActivity());
		
		gv_mycollection_item = (GridView) view
				.findViewById(R.id.gridView_item_collection);
		gv_mycollection_item.setOnItemClickListener(this);
		
		CollectionDBHelper helper = new CollectionDBHelper(getActivity());
		String sql = "select * from goodses";
		cursor = helper.selectCursor(sql, null);
		list = helper.cursorToList(cursor);
		list_info = new ArrayList<ItemListEntity>();
		for (int i = 0; i < list.size(); i++) {
			int id = (Integer) list.get(i).get("id");
			String title = (String) list.get(i).get("title");
			String img_url = (String) list.get(i).get("img_url");
		/*	Double current = (Double) list.get(i).get("current");
			Double prime = (Double) list.get(i).get("prime");*/
			ItemListEntity itemListEntity = new ItemListEntity(id, title,
					img_url, 0, 0);
			list_info.add(itemListEntity);
		}
		adapter = new ItemCollectionAdapter(getActivity(),
				R.layout.goodslist_item, mQueue);
		adapter.setDatas(list_info);
		gv_mycollection_item.setAdapter(adapter);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		//点击跳转到商品详情
		Intent intent = new Intent(getActivity(),ItemDetialActivity.class);
		intent.putExtra("id", list_info.get(arg2).getId());
		startActivity(intent);
	}

}
