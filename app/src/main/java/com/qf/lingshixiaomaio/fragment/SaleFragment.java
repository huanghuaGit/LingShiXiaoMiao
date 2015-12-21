package com.qf.lingshixiaomaio.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.qf.lingshixiaomaio.R;
import com.qf.lingshixiaomaio.activity.ItemDetialActivity;
import com.qf.lingshixiaomaio.activity.ShoppingCarActivity;
import com.qf.lingshixiaomaio.adapter.ItemListAdapter;
import com.qf.lingshixiaomaio.application.MyApplication;
import com.qf.lingshixiaomaio.customView.LoadingView;
import com.qf.lingshixiaomaio.model.ItemListEntity;
import com.qf.lingshixiaomaio.util.Constants;
import com.qf.lingshixiaomaio.util.JsonUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;



/**
 * 特卖页面
 * 
 * @author ZL
 * 
 */
public class SaleFragment extends Fragment implements
		OnRefreshListener2<GridView>, OnItemClickListener, OnClickListener {

	private View view;
	private TextView tv_itemList_top_title;// 特卖商品列表顶部的标题
	private PullToRefreshGridView item_list_gridView; // 商品列表的gridView
	private List<ItemListEntity> itemList_list;// 存放商品实体的集合
	private List<ItemListEntity> list_total;// 总的list
	private ItemListAdapter itemListAdapter; // 商品列表的适配器
	private MyApplication myApplication;
	private int pg_cur = 1; // 当前数据的页码数

	private ImageView image_shopping_car;// 购物车

	// 下载中显示的动画布局
	private LoadingView loadingView;

	public static SaleFragment saleFragment;

	// 写一个方法将SaleFragment的对象返回出去
	public static SaleFragment getFragment() {
		if (saleFragment == null) {
			saleFragment = new SaleFragment();
		}
		return saleFragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// 重用之前的布局
		view = inflater.inflate(R.layout.activity_home_viewpager_goodslist,
				container, false);
		initView();
		loadData();
		return view;
	}

	// 加载数据
	private void loadData() {
		String url = String.format(Constants.URL.URL_SALE, pg_cur);
		myApplication.downLoadJson(url, mySaleListener, mySaleListener);
	}

	private MySaleListener mySaleListener = new MySaleListener();

	class MySaleListener implements Listener<JSONObject>, ErrorListener {

		@Override
		public void onErrorResponse(VolleyError error) {
			try {
				Toast.makeText(getActivity(), "加载网络超时，请检查你的网络连接是否正常",
						Toast.LENGTH_SHORT).show();
			} catch (Exception e) {
			}
		}

		@Override
		public void onResponse(JSONObject response) {

			// 通知控件加载完成
			item_list_gridView.onRefreshComplete();

			loadingView.setVisibility(View.GONE);
			loadingView.stopLoading();

			itemList_list = JsonUtils.parseItemListJson(response);

			itemListAdapter.addDatas(itemList_list);

			list_total.addAll(itemList_list);
		}

	}

	private void initView() {
		list_total = new ArrayList<ItemListEntity>();
		image_shopping_car = (ImageView) view
				.findViewById(R.id.item_list_shopping_car);
		image_shopping_car.setOnClickListener(this);

		loadingView = (LoadingView) view
				.findViewById(R.id.view_salefragment_loading);
		loadingView.startLoading();// 开始动画

		tv_itemList_top_title = (TextView) view
				.findViewById(R.id.tv_item_list_top_title);
		tv_itemList_top_title.setText("今日特卖");// 设置标题

		myApplication = (MyApplication) (getActivity().getApplication());

		// 初始化gridView
		item_list_gridView = (PullToRefreshGridView) view
				.findViewById(R.id.item_list_gridView);
		item_list_gridView.setMode(Mode.BOTH);
		item_list_gridView.setOnRefreshListener(this);// 设置gridView上拉和下拉的监听事件
		item_list_gridView.setOnItemClickListener(this);// 监听特卖商品的点击事件

		// 初始化适配器
		itemListAdapter = new ItemListAdapter(getActivity(),
				R.layout.goodslist_item, myApplication.getRequestQueue());

		item_list_gridView.setAdapter(itemListAdapter);
	}

	/**
	 * 下拉刷新 上拉加载的监听方法
	 */
	@Override
	public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
		// 下拉刷新
	}

	private Handler handler = new Handler();

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
		// 上拉加载
		Toast.makeText(getActivity(), "开始刷新...", Toast.LENGTH_SHORT).show();
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
	private void loadNextPageData() {
		pg_cur++;
		loadData();
	}

	// 点击商品跳转到美味详情页面的方法
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Toast.makeText(getActivity(), list_total.get(position).getId() + "", 0)
				.show();
		// 将所点击的商品的id传递过去，跳转到美味详情页面
		Intent intent = new Intent(getActivity(), ItemDetialActivity.class);
		intent.putExtra("id", list_total.get(position).getId());
		intent.putExtra("image_url", list_total.get(position).getImg_url());
		startActivity(intent);

	}

	// 跳转到购物车
	@Override
	public void onClick(View v) {
		Intent intent = new Intent(getActivity(), ShoppingCarActivity.class);
		startActivity(intent);
	}
}
