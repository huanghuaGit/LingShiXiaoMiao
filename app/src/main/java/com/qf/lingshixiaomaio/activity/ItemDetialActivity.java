package com.qf.lingshixiaomaio.activity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.qf.lingshixiaomaio.R;
import com.qf.lingshixiaomaio.application.MyApplication;
import com.qf.lingshixiaomaio.customView.ItemDetailBottomView;
import com.qf.lingshixiaomaio.customView.ItemDetailCenterWebView;
import com.qf.lingshixiaomaio.customView.ItemDetailCommentsView;
import com.qf.lingshixiaomaio.customView.ItemDetailHeadView;
import com.qf.lingshixiaomaio.customView.LoadingView;
import com.qf.lingshixiaomaio.model.ItemDetailEntity;
import com.qf.lingshixiaomaio.util.CollectionDBHelper;
import com.qf.lingshixiaomaio.util.Constants;
import com.qf.lingshixiaomaio.util.JsonUtils;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;

import org.json.JSONObject;

import java.util.List;


//美味详情页面

public class ItemDetialActivity extends Activity implements OnClickListener {
	private Intent intent;
	private MyApplication myApplication;
	private int id;// 接收传过来的商品id
	private String image_url;// 接收传过来的商品image_url

	private LinearLayout ll_container; // 存放商品详情数据的容器
	private List<ItemDetailEntity> list_item_detail;// 存放美味详情的数据

	private Button btn_share;// 头部分享的图片按钮
	private Button btn_back;// 头部返回键
	private Button btn_shopping_cart;// 购物车按钮
	private Button btn_addTo_shopping_car;// 添加到购物车

	// 添加进来的布局
	private ItemDetailHeadView itemDetailHeadView;// 美味详情头布局View
	private ItemDetailCenterWebView itemDetailCenterWebView; // 美味详情中间图片布局View
	private ItemDetailCommentsView itemDetailCommentsView;// 美味详情评论View
	private ItemDetailBottomView itemDetailBottomView;// 猜你喜欢View

	// 下载中显示的动画布局
	private LoadingView loadingView;

	private boolean isExist;// 购物车是都有数据

	// 友盟
	private UMSocialService mController = UMServiceFactory
			.getUMSocialService("com.umeng.share");

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_goods_details);
		initView();
		loadData();

		setShare();
	}

	private void initView() {
		intent = getIntent();
		id = intent.getIntExtra("id", 0);// 将这个id拼接到美味详情的接口中去
		image_url = intent.getStringExtra("image_url");// 将这个传到ItemDetailHeadView;

		// 得到myApplication对象
		myApplication = (MyApplication) getApplication();

		loadingView = (LoadingView) findViewById(R.id.view_Item_Detail_loading1);
		loadingView.startLoading();// 开始动画

		ll_container = (LinearLayout) findViewById(R.id.ll_item_detail_container);
		btn_share = (Button) findViewById(R.id.image_btn_item_detail_share);
		btn_back = (Button) findViewById(R.id.image_btn_item_detial_back);
		btn_shopping_cart = (Button) findViewById(R.id.image_item_detail_shopping_car);
		btn_addTo_shopping_car = (Button) findViewById(R.id.btn_addTo_shopping_car);
		btn_share.setOnClickListener(this);
		btn_back.setOnClickListener(this);
		btn_shopping_cart.setOnClickListener(this);
		btn_addTo_shopping_car.setOnClickListener(this);

		// 实例化要添加进来的美味详情头布局对象
		itemDetailHeadView = new ItemDetailHeadView(this);
		itemDetailCenterWebView = new ItemDetailCenterWebView(this);
		itemDetailCommentsView = new ItemDetailCommentsView(this);
		itemDetailBottomView = new ItemDetailBottomView(this);

		// 将布局添加进来
		ll_container.addView(itemDetailHeadView, 0);
		ll_container.addView(itemDetailCenterWebView, 1);
		ll_container.addView(itemDetailCommentsView, 2);
		ll_container.addView(itemDetailBottomView, 3);

		// 初始化ScrollView使它一开始加载时滑倒顶部
		ScrollView s = (ScrollView) findViewById(R.id.scrollView_item_detail_container);
		s.scrollTo(10, 10);

		// 将图片地址传到itemDetailHeadView中去
		itemDetailHeadView.getImage_url(image_url);

		isExist = false;
	}

	// 加载数据
	private void loadData() {
		String url = String.format(Constants.URL.URL_ITEM_DETIALS, id);
		myApplication.downLoadJson(url, myJsonListener, myJsonListener);
	}

	private MyJsonListener myJsonListener = new MyJsonListener();

	class MyJsonListener implements Listener<JSONObject>, ErrorListener {

		@Override
		public void onErrorResponse(VolleyError error) {
			Toast.makeText(ItemDetialActivity.this, "加载网络数据出错，请检查你的网络是否连接正常",
					Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onResponse(JSONObject response) {
			// 将下载好的JSON数据传到其他View
			itemDetailHeadView.loadData(response);
			itemDetailCenterWebView.loadData(response);
			itemDetailCommentsView.loadData(response);
			itemDetailBottomView.loadData(response);

			// 判断ItemDetailHeadView是否加载完成的变量
			boolean isItemDetailHeadViewLoadFinish = itemDetailHeadView.isLoadFinished;
			if (isItemDetailHeadViewLoadFinish) {// 加载完成
				loadingView.setVisibility(View.GONE);
				loadingView.stopLoading();
			}

			// 将数据解析成对象
			list_item_detail = JsonUtils.parseItemDetailHeadJson(response);

			// 设置分享内容
			mController.setShareContent(list_item_detail.get(0).getTitle());
			mController.setShareMedia(new UMImage(ItemDetialActivity.this,
					image_url));
		}

	}

	// 将数据插入数据库
	private void insertData() {
		CollectionDBHelper helper = new CollectionDBHelper(this);
		ContentValues values = new ContentValues();
		values.put("title", list_item_detail.get(0).getTitle());
		values.put("img_url", image_url);
		values.put("id", list_item_detail.get(0).getId());

		values.put("current", (float) (list_item_detail.get(0).getCurrent()));
		values.put("prime", (float) (list_item_detail.get(0).getPrime()));

		SQLiteDatabase db = helper.getWritableDatabase();
		String sql = "select id from shoppingCar";
		Cursor selectCursor = helper.selectCursor(sql, null);
		while (selectCursor.moveToNext()) {
			int id = selectCursor.getInt(selectCursor.getColumnIndex("id"));
			if (id == list_item_detail.get(0).getId()) {
				isExist = true;
				Log.i("isExist", isExist + "");
				break;
			}
			selectCursor.moveToNext();
		}
		if (isExist) {
			Toast.makeText(this, "亲，你的购物车已经有该商品了，为了你的钱包着想，请回头是岸~!",
					Toast.LENGTH_SHORT).show();
		} else {
			long res = helper.insertDataShoppingCar(db, values);
			if (res > 0) {
				Toast.makeText(this, "已经成功加入购物车了~喵！", Toast.LENGTH_SHORT)
						.show();
				isExist = true;
			}
		}
	}

	// 有盟分享
	private void setShare() {
		String appID = "wx967daebe835fbeac";
		String appSecret = "5fa9e68ca3970e87a1f83e563c8dcbce";
		// 添加微信平台
		UMWXHandler wxHandler = new UMWXHandler(this, appID, appSecret);
		wxHandler.addToSocialSDK();
		// 添加微信朋友圈
		UMWXHandler wxCircleHandler = new UMWXHandler(this, appID, appSecret);
		wxCircleHandler.setToCircle(true);
		wxCircleHandler.addToSocialSDK();
		UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(this, "100424468",
				"c7394704798a158208a74ab60104f0ba");
		qqSsoHandler.addToSocialSDK();
		QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(this,
				"100424468", "c7394704798a158208a74ab60104f0ba");
		qZoneSsoHandler.addToSocialSDK();

	}

	private void startShare(SHARE_MEDIA share_media) {
		mController.directShare(this, share_media, new SnsPostListener() {

			@Override
			public void onStart() {

			}

			@Override
			public void onComplete(SHARE_MEDIA arg0, int arg1,
					SocializeEntity arg2) {
				Toast.makeText(ItemDetialActivity.this, "分享完成",
						Toast.LENGTH_SHORT).show();
			}
		});
	}

	// 监听imageButton的点击分享事件
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 回退岛上一个activity
		case R.id.image_btn_item_detial_back:
			this.finish();
			break;

		case R.id.image_item_detail_shopping_car:
			// 跳转到购物车页面
			Intent intent = new Intent(ItemDetialActivity.this,
					ShoppingCarActivity.class);
			startActivity(intent);
			break;
		// 分享到其他应用
		case R.id.image_btn_item_detail_share:
			// 设置要出现在分享面板的平台
			mController.getConfig().setPlatforms(SHARE_MEDIA.QQ,
					SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN,
					SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.SINA);

			// 打开分享面板
			mController.openShare(this, false);
			break;
		// 加入购物车
		case R.id.btn_addTo_shopping_car:
			insertData();
			break;
		}
	}
}