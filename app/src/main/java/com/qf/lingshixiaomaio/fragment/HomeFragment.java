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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.qf.lingshixiaomaio.R;
import com.qf.lingshixiaomaio.activity.ItemDetialActivity;
import com.qf.lingshixiaomaio.activity.SearchActivity;
import com.qf.lingshixiaomaio.activity.ShoppingCarActivity;
import com.qf.lingshixiaomaio.adapter.HomeDropDownBrandsAdapter;
import com.qf.lingshixiaomaio.application.MyApplication;
import com.qf.lingshixiaomaio.customView.HomeBrandsSpecialView;
import com.qf.lingshixiaomaio.customView.HomeBrandsView;
import com.qf.lingshixiaomaio.customView.HomeHeadView;
import com.qf.lingshixiaomaio.customView.HomeTabView;
import com.qf.lingshixiaomaio.customView.LoadingView;
import com.qf.lingshixiaomaio.model.HomeDropDownBrandsEntity;
import com.qf.lingshixiaomaio.util.Constants;
import com.qf.lingshixiaomaio.util.JsonUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class HomeFragment extends Fragment implements

OnRefreshListener2<ListView>, OnItemClickListener, OnClickListener {

	private PullToRefreshListView homeListView;
	private View view;
	private MyApplication myApplication;
	private List<HomeDropDownBrandsEntity> homeDropDownBrandsList;// 存放首页底部商品的实体数据集合
	private HomeDropDownBrandsAdapter homeDropDownBrandsAdapter;
	private List<HomeDropDownBrandsEntity> list_total;// 总的list

	private ImageView image_shopping_car;// 购物车按钮

	// 添加进来的布局
	private HomeHeadView homeHeadView; // 头部ViewPager布局
	private HomeTabView homeTabView; // 中间导航栏布局
	private HomeBrandsView homeBrandsView; // 导航栏下面品牌特卖的布局
	private HomeBrandsSpecialView homeBrandsSpecialView; // 品牌特卖下面的一个由多个图片组成的特殊类布局

	private int pg_cur = 1;
	private int pg_size = 20;
	private static HomeFragment homeFragment;
	private TextView tv_search;
	// 下载中显示的动画
	private LoadingView loadingView;

	// 写一个方法将HomeFragment的对象返回出去
	public static HomeFragment getFragment() {
		if (homeFragment == null) {
			homeFragment = new HomeFragment();
		}
		return homeFragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.home_fragment, container, false);
		initView();
		loadData();
		loadHeadViewData();
		return view;
	}

	// 加载从外面加进来的头布局的数据
	private void loadHeadViewData() {
		String url = Constants.URL.URL_HOMEPAGE_HEAD;
		// 下载JSON数据
		myApplication.downLoadJson(url, jsonListener, jsonListener);
	}

	private MyJsonListener jsonListener = new MyJsonListener();

	class MyJsonListener implements Listener<JSONObject>, ErrorListener {

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
			loadingView.setVisibility(View.GONE);
			loadingView.stopLoading();
			// 将下载好的JSON数据传到其他View
			homeHeadView.loadData(response);
			homeTabView.loadData(response);
			homeBrandsView.loadData(response);
			homeBrandsSpecialView.loadData(response);
		}
	}

	// 加载本页显示的数据(指好吃到爆下面的数据 )
	private void loadData() {
		String url = String.format(Constants.URL.URL_HOMEPAGE_DROPDOWN, pg_cur,
				pg_size);
		myApplication.downLoadJson(url, dropDownDataListener,
				dropDownDataListener);
	}

	private MyDropDownDataListener dropDownDataListener = new MyDropDownDataListener();

	class MyDropDownDataListener implements Listener<JSONObject>, ErrorListener {

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

			homeDropDownBrandsList = new ArrayList<HomeDropDownBrandsEntity>();
			homeDropDownBrandsList = JsonUtils.parseDropDownHomeBrandsJson(
					response, homeDropDownBrandsList);
			homeDropDownBrandsAdapter.addDatas(homeDropDownBrandsList);

			list_total.addAll(homeDropDownBrandsList);// 将数据添加到总的list里面来

			// 通知控件加载完成
			homeListView.onRefreshComplete();
		}
	}

	private void initView() {

		list_total = new ArrayList<HomeDropDownBrandsEntity>();

		image_shopping_car = (ImageView) view
				.findViewById(R.id.top_shopping_car);

		tv_search = (TextView) view.findViewById(R.id.tv_search);
		tv_search.setOnClickListener(this);

		tv_search = (TextView) view.findViewById(R.id.tv_search);
		tv_search.setOnClickListener(this);

		// 开始动画
		loadingView = (LoadingView) view
				.findViewById(R.id.view_homefragment_loading);
		loadingView.startLoading();
		// 初始化头布局
		homeTabView = new HomeTabView(getActivity());
		homeHeadView = new HomeHeadView(getActivity());
		homeBrandsView = new HomeBrandsView(getActivity());
		homeBrandsSpecialView = new HomeBrandsSpecialView(getActivity());

		// 获取myApplication对象
		myApplication = (MyApplication) (getActivity().getApplication());

		// 初始化首页listView
		homeListView = (PullToRefreshListView) view.findViewById(R.id.lv_home);
		homeListView.setMode(Mode.BOTH);
		homeListView.setOnRefreshListener(this); // 设置listView上拉和下拉的监听事件
		homeListView.setOnItemClickListener(this);// 监听listView的点击跳转事件

		// 设置listView上拉和下拉的属性
		ILoadingLayout ill = homeListView.getLoadingLayoutProxy(true, false);

		// 初始化适配器
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		map.put(0, R.layout.home_dropdown_item);
		homeDropDownBrandsAdapter = new HomeDropDownBrandsAdapter(
				getActivity(), map, myApplication.getRequestQueue());

		// 添加头布局
		homeListView.getRefreshableView().addHeaderView(homeHeadView);
		homeListView.getRefreshableView().addHeaderView(homeTabView);
		homeListView.getRefreshableView().addHeaderView(homeBrandsView);
		homeListView.getRefreshableView().addHeaderView(homeBrandsSpecialView);

		homeListView.setAdapter(homeDropDownBrandsAdapter);// 设置适配器

		// 跳转到购物车
		image_shopping_car.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),
						ShoppingCarActivity.class);
				startActivity(intent);
			}
		});
	}

	/**
	 * 下拉刷新 上拉加载的监听方法
	 */
	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
		// 下拉刷新
		
	}

	private Handler handler = new Handler();

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
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

	/**
	 * 下拉刷新 上拉加载的监听方法
	 */

	// 加载下一页数据
	public void loadNextPageData() {
		pg_cur++;
		pg_size += 20;
		loadData();
	}

	// 监听点击商品进入美味详情页面的事件
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// 将所点击的商品的id传递过去，跳转到美味详情页面
		Intent intent = new Intent(getActivity(), ItemDetialActivity.class);
		// 这里减5是因为listView里面添加了4个头布局
		intent.putExtra("id", list_total.get(position - 5).getId());
		startActivity(intent);
	}

	// 跳转到搜索界面
	@Override
	public void onClick(View v) {
		Intent intent = new Intent(getActivity(), SearchActivity.class);
		startActivity(intent);

	}

}
