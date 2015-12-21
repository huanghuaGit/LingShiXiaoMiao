package com.qf.lingshixiaomaio.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.NetworkImageView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.qf.lingshixiaomaio.R;
import com.qf.lingshixiaomaio.adapter.HomeTabSelectAdapter;
import com.qf.lingshixiaomaio.adapter.ItemListAdapter;
import com.qf.lingshixiaomaio.application.MyApplication;
import com.qf.lingshixiaomaio.customView.LoadingView;
import com.qf.lingshixiaomaio.customView.TimeTextView;
import com.qf.lingshixiaomaio.model.HomeTabSelectEntity;
import com.qf.lingshixiaomaio.model.ItemListEntity;
import com.qf.lingshixiaomaio.util.Constants;
import com.qf.lingshixiaomaio.util.JsonUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;



//点击首页ViewPager后的商品列表页面
public class GoodsListActivity extends Activity implements
		OnRefreshListener2<GridView>, OnClickListener, OnItemClickListener {
	private List<ItemListEntity> itemList_list;// 存放商品实体的集合
	private List<ItemListEntity> list_total;// 总的list
	private ItemListAdapter itemListAdapter;// 商品列表的适配器
	private PullToRefreshGridView item_list_gridView; // 商品列表的gridView
	private int pg_cur = 1; // 当前页码数
	private String url;
	private Intent intent; // 接收从别处传过来的意图

	// centerBrandsView里item商品列表的头部布局控件
	private NetworkImageView image_brands_goods_list_top;
	private TextView tv_brands_goods_list_title;
	private TimeTextView tv_brands_goods_list_time;// 剩余时间

	private String info; // 由首页的ViewPager点击后传递过来的参数
	private int type;

	private int tabview_item_id;// 由首页的tabView点击后传递过来的参数
	private Button btn_top_title;// tabView里item列表顶部标题按钮
	private LinearLayout ll_tabview_item_select; // tabView下拉选择框布局
	private GridView tabview_item_select_gridView; // tabView下拉选择框布局的子布局
	private ImageView image_shopping_car;// 购物车

	private int brands_id;// 由首页的centerBrnadsView点击后传递过来的参数
	private String brands_title;
	private String brands_image_url;
	private long brands_time;

	private String brands_special_info; // 由首页的brands_Special_View点击后传递过来的参数

	// 下载中显示的动画布局
	private LoadingView loadingView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initLayout();

	}

	// 根据info值来初始化不同的布局
	private void initLayout() {
		intent = getIntent();
		// 由首页的ViewPager点击后传递过来的参数
		info = intent.getStringExtra("info");
		type = intent.getIntExtra("type", 0);

		// 由首页的tabView点击后传递过来的参数
		tabview_item_id = intent.getIntExtra("id", 0);

		// 由首页的centerBrnadsView点击后传递过来的参数
		brands_id = intent.getIntExtra("brands_id", 0);
		brands_title = intent.getStringExtra("brands_title");
		brands_image_url = intent.getStringExtra("brands_image_url");
		brands_time = intent.getLongExtra("brands_time", 0);

		// 由首页的brands_Special_View点击后传递过来的参数
		brands_special_info = intent.getStringExtra("brands_special_info");

		// 使用商品列表布局
		setContentView(R.layout.activity_home_viewpager_goodslist);
		initView();
		loadData();

		// 跳转到商品详情页
		if (type == 3) {
			this.finish();// 先删除掉原来的布局
			Intent intent = new Intent(this, ItemDetialActivity.class);
			// 将商品的id传递过去
			intent.putExtra("id", Integer.parseInt(info));
			startActivity(intent);
		}
		if (type == 5) {// 使用webView显示抢红包页面
			setContentView(R.layout.activity_home_brandspecialview_webview);
			WebView webView = (WebView) findViewById(R.id.home_brandspecial_webview);

			// 设置这个两个属性避免跳转到浏览器
			webView.setWebChromeClient(new WebChromeClient() {
				@Override
				public void onProgressChanged(WebView view, int newProgress) {
					super.onProgressChanged(view, newProgress);
				}

				@Override
				public boolean onJsAlert(WebView view, String url,
						String message, JsResult result) {
					return super.onJsAlert(view, url, message, result);
				}
			});
			webView.setWebViewClient(new WebViewClient() {
				@Override
				public boolean shouldOverrideUrlLoading(WebView view, String url) {
					view.loadUrl(url);
					return true;
				}
			});

			webView.loadUrl(brands_special_info);
		}
		if (tabview_item_id != 0) {// 使用tabView的商品列表布局
			setContentView(R.layout.activity_home_tabview_goodslist);
			initView();
			initTabItemView();
			loadData();
		}
		if (brands_id != 0) {// 使用centerBrandsView的商品列表的布局
			setContentView(R.layout.activity_home_centerbrandsview_goodslist);
			initView();
			initCenterBrandsViewGoods();
			loadData();
		}

	}

	// 初始化centerBrands的item商品列表的布局控件
	private void initCenterBrandsViewGoods() {
		image_brands_goods_list_top = (NetworkImageView) findViewById(R.id.image_brands_goods_list_top);
		tv_brands_goods_list_title = (TextView) findViewById(R.id.tv_brands_goods_list_title);
		tv_brands_goods_list_time = (TimeTextView) findViewById(R.id.tv_brands_goods_list_time);

		MyApplication
				.setImageUrl(brands_image_url, image_brands_goods_list_top);
		image_brands_goods_list_top.setScaleType(ScaleType.CENTER_CROP);

		tv_brands_goods_list_title.setText(brands_title);

		// 将毫秒转为时分秒
		long days = brands_time / (1000 * 60 * 60 * 24);
		long hours = (brands_time % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
		long minutes = (brands_time % (1000 * 60 * 60)) / (1000 * 60);
		long seconds = (brands_time % (1000 * 60)) / 1000;
		//给倒计时的textView设置数据
		tv_brands_goods_list_time.setTime(days + "", hours + "", minutes + "",
				seconds + "");
	}

	// 初始化tabView里item列表的布局
	private HomeTabSelectAdapter homeTabSelectAdapter;// tabView里的商品选择器的适配器
	private List<HomeTabSelectEntity> list_select;// tabView里的商品选择器的数据集合
	private int sub_id;// tabView的商品列表的分类的id

	private void initTabItemView() {
		// 跳转到购物车
		image_shopping_car = (ImageView) findViewById(R.id.item_list_shopping_car);
		goToShoppingCar(image_shopping_car);

		// 初始化tabView里item列表的总标题
		btn_top_title = (Button) findViewById(R.id.btn_tabview_item_top_title);
		btn_top_title.setText(intent.getStringExtra("title"));
		btn_top_title.setOnClickListener(this);// 监听tabview里item列表的总标题的点击事件

		// tabView里商品列表弹出的选择框的布局
		ll_tabview_item_select = (LinearLayout) findViewById(R.id.home_tabview_goodlist_select_layout);
		// tabView里商品列表弹出的选择框的子布局
		tabview_item_select_gridView = (GridView) findViewById(R.id.home_tabview_goodlist_select_gridview);

		// 设置适配器
		homeTabSelectAdapter = new HomeTabSelectAdapter(this,
				R.layout.activity_home_tabview_goods_select_item);
		loadTabViewItemSelectData();// 加载数据给适配器
		// 监听tabView的商品选择器的点击事件
		tabview_item_select_gridView
				.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {

						// 设置被选择的标签的字体颜色
						TextView textView;
						for (int i = 0; i < parent.getChildCount(); i++) {
							textView = (TextView) parent.getChildAt(i)
									.findViewById(R.id.tv_tabView_select);
							textView.setTextColor(Color.BLACK);
						}
						textView = (TextView) parent.getChildAt(position)
								.findViewById(R.id.tv_tabView_select);
						textView.setTextColor(Color.RED);

						// 更改标题
						btn_top_title.setText(list_select.get(position)
								.getTitle());

						sub_id = list_select.get(position).getId();// 获得当前点击的sub_id
						ll_tabview_item_select.setVisibility(View.GONE);// 设置选择框的属性为隐藏
						flag = false;// flag是用来控制弹出选择框的显示与隐藏的

						itemListAdapter.clearDatas();// 清空适配器里面的数据

						loadData();// 重新加载数据
					}
				});

	}

	// 下载tabView的商品选择器的数据
	private void loadTabViewItemSelectData() {
		String url = String.format(Constants.URL.URL_HOME_TABVIEW_ITEM_SELECT,
				tabview_item_id);
		((MyApplication) getApplication()).downLoadJson(url, listener2,
				listener2);
	}

	private MyListener listener2 = new MyListener();

	class MyListener implements Listener<JSONObject>, ErrorListener {

		@Override
		public void onErrorResponse(VolleyError error) {
		}

		@Override
		public void onResponse(JSONObject response) {
			// 将下载好的数据解析成对象
			list_select = JsonUtils.parseHomeTabSelectEntityJson(response);
			homeTabSelectAdapter.setDatas(list_select);
			tabview_item_select_gridView.setAdapter(homeTabSelectAdapter);
		}

	}

	// 初始化gridView
	private void initView() {
		list_total = new ArrayList<ItemListEntity>();

		loadingView = (LoadingView) findViewById(R.id.view_salefragment_loading);
		loadingView.startLoading();// 开始动画

		item_list_gridView = (PullToRefreshGridView) findViewById(R.id.item_list_gridView);
		item_list_gridView.setMode(Mode.BOTH);
		item_list_gridView.setOnRefreshListener(this);// 监听上拉加载，下拉刷新的事件
		item_list_gridView.setOnItemClickListener(this);// 监听点击商品进入美味详情页面的事件

		// 初始化适配器
		itemListAdapter = new ItemListAdapter(this, R.layout.goodslist_item,
				((MyApplication) getApplication()).getRequestQueue());
		item_list_gridView.setAdapter(itemListAdapter);
	}

	// 加载数据
	private void loadData() {
		url = String.format(Constants.URL.URL_HOME_VIEWPAGE_ITEM_LIST, pg_cur,
				info);
		if (tabview_item_id != 0) {
			url = String.format(Constants.URL.URL_HOME_TABVIEW_ITEM_LIST,
					pg_cur, sub_id, tabview_item_id);
		}

		if (brands_id != 0) {
			url = String.format(Constants.URL.URL_HOME_BRANDSVIEW_ITEM_LIST,
					brands_id, pg_cur);
		}

		if (brands_special_info != null && !brands_special_info.equals("")) {
			url = String.format(
					Constants.URL.URL_HOME_BRANDSPECIALVIEW_ITEM_LIST, pg_cur,
					brands_special_info);
		}

		((MyApplication) getApplication()).downLoadJson(url, josnListener,
				josnListener);
	}

	private MyJosnListener josnListener = new MyJosnListener();

	class MyJosnListener implements Listener<JSONObject>, ErrorListener {

		@Override
		public void onErrorResponse(VolleyError error) {
			Toast.makeText(GoodsListActivity.this, "加载网络超时，请检查你的网络连接是否正常",
					Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onResponse(JSONObject response) {
			// 通知控件加载完成
			item_list_gridView.onRefreshComplete();

			// 下载完成就隐藏动画和停止动画
			loadingView.setVisibility(View.GONE);
			loadingView.stopLoading();

			// 将数据解析成对象(根据不同情况解析不同JSON数据)
			if (brands_id != 0) {
				itemList_list = JsonUtils
						.parseCenterBrandsViewListJson(response);
			} else {
				itemList_list = JsonUtils.parseItemListJson(response);
			}
			itemListAdapter.addDatas(itemList_list);

			list_total.addAll(itemList_list);
		}

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

	private boolean flag;

	// 监听tabView里item列表的总标题的点击事件
	@Override
	public void onClick(View v) {
		// 点击按钮控制选择框的显示和隐藏
		if (!flag) {
			ll_tabview_item_select.setVisibility(View.VISIBLE);
			flag = true;

		} else {
			ll_tabview_item_select.setVisibility(View.GONE);
			flag = false;
		}
	}

	// 跳转到购物车
	public void goToShoppingCar(ImageView imageView) {
		imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(GoodsListActivity.this,
						ShoppingCarActivity.class);
				startActivity(intent);
			}
		});
	}

	// 监听点击商品进入美味详情页面的事件
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// 将所点击的商品的id传递过去，跳转到美味详情页面
		Toast.makeText(this, list_total.get(position).getId() + "", 0).show();
		Intent intent = new Intent(this, ItemDetialActivity.class);
		intent.putExtra("id", list_total.get(position).getId());
		intent.putExtra("image_url", list_total.get(position).getImg_url());
		startActivity(intent);
	}
}
