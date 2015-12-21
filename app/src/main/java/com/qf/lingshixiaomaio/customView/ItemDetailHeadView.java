package com.qf.lingshixiaomaio.customView;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.qf.lingshixiaomaio.R;
import com.qf.lingshixiaomaio.activity.DiscountActivity;
import com.qf.lingshixiaomaio.model.ItemDetailEntity;
import com.qf.lingshixiaomaio.model.Tags;
import com.qf.lingshixiaomaio.util.CollectionDBHelper;
import com.qf.lingshixiaomaio.util.JsonUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



/**
 * 美味详情头部View
 * 
 * @author ZL
 * 
 */
public class ItemDetailHeadView extends LinearLayout implements
		OnPageChangeListener, OnClickListener {
	private ViewPager viewpager_item_detail_top;
	private List<ItemDetailEntity> list_item_detail;// 存放美味详情头布局的数据
	private List<String> list_image_url;// 存放美味详情头布局ViewPager图片地址的数据
	private List<NetworkImageView> list_image;// 存放美味详情头布局ViewPager图片的数据
	// 布局里面的控件
	private TextView tv_item_detail_title;// 商品标题
	private TextView tv_item_detail_current;// 当前价格
	private TextView tv_item_detail_prime;// 超市价格
	private TextView tv_item_detail_sold_num;// 已卖件数
	private TextView tv_total_num;// 顶部图片总页数
	private TextView tv_current_num;// 顶部图片当前页码
	private TimeTextView tv_item_detail_server_time;// 剩余时间
	private LinearLayout item_detail_server_time_container;// 剩余时间父容器
	private Button btn_item_detail_collect;// 收藏按钮
	private LinearLayout item_detail_tags_title_container;// 存放tags_title的动态布局

	public static boolean isLoadFinished;// 判断是否下载完成的boolean值

	// 底部优惠布局控件
	private RelativeLayout rl_activity_container;// 优惠的父布局
	private TextView tv_activity_icon_title;// 优惠标签
	private TextView tv_activity_title;// 优惠内容
	
	private boolean isExist;// 判断数据是否已经在数据库的变量

	public ItemDetailHeadView(Context context) {
		super(context);
		initView();
	}

	// 加载数据
	public void loadData(JSONObject response) {

		// 将JSON数据解析成对象
		list_item_detail = JsonUtils.parseItemDetailHeadJson(response);
		list_image_url = list_item_detail.get(0).getImage_list();
		list_image = initImageList(list_image_url);

		viewpager_item_detail_top.setAdapter(new MyPageAdapter(list_image));// 设置图片适配器

		// 设置布局里面的View的内容
		tv_total_num.setText(list_image.size() + "");
		tv_item_detail_title.setText(list_item_detail.get(0).getTitle());
		tv_item_detail_current.setText("¥ "
				+ list_item_detail.get(0).getCurrent());
		tv_item_detail_prime
				.setText(" ¥ " + list_item_detail.get(0).getPrime());
		tv_item_detail_prime.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

		//获得时间的毫秒数
		long total_time = list_item_detail.get(0).getServer_time();
		//将时间的毫秒数转为时分秒格式
		long days = total_time / (1000 * 60 * 60 * 24);
		long hours = (total_time % (1000 * 60 * 60 * 24))
				/ (1000 * 60 * 60);
		long minutes = (total_time % (1000 * 60 * 60)) / (1000 * 60);
		long seconds = (total_time % (1000 * 60)) / 1000;
		//设置时间
		tv_item_detail_server_time.setTime(days + "", hours + "", minutes + "", seconds + "");

		// 通过type和guide_type的值来判断是否显示隐藏的布局
		if ((list_item_detail.get(0).getTypes() == 2 && list_item_detail.get(0)
				.getGuide_type() == 0)
				|| (list_item_detail.get(0).getTypes() == 0 && list_item_detail
						.get(0).getGuide_type() == 0)) {
			// 显示隐藏的布局
			item_detail_server_time_container.setVisibility(View.VISIBLE);
		}

		List<Tags> tag_list = list_item_detail.get(0).getTag_list();// 存放标签数据的集合
		// 根据tag_list的数据来动态加载标签里面的数据
		for (int i = 0; i < tag_list.size(); i++) {
			TextView textView = new TextView(getContext());
			textView.setText(tag_list.get(i).getTitle());
			textView.setBackgroundColor(Color.parseColor(tag_list.get(i)
					.getColor()));
			textView.setTextColor(Color.parseColor("#FFFFFF"));
			textView.setPadding(10, 10, 10, 10);
			// 如果没有数据，则不显示
			if (tag_list.get(i).getTitle().equals("")) {
				textView.setVisibility(View.GONE);
			}
			item_detail_tags_title_container.addView(textView);
		}
		// 给控制设值
		tv_item_detail_sold_num.setText("已售 ："
				+ list_item_detail.get(0).getSold_num() + "件");

		tv_activity_icon_title.setText("优惠");
		tv_activity_title.setText("满68元包邮");

		// 下载完成改变状态
		isLoadFinished = true;

	}

	private void initView() {
		LayoutInflater.from(getContext()).inflate(
				R.layout.view_item_detail_headview, this, true);

		mQueue = Volley.newRequestQueue(getContext());
		imageLoader = new ImageLoader(mQueue, imageCache);

		// 初始化布局里面的控件
		tv_item_detail_title = (TextView) findViewById(R.id.tv_item_detail_title);
		tv_item_detail_current = (TextView) findViewById(R.id.tv_item_detail_current);
		tv_item_detail_prime = (TextView) findViewById(R.id.tv_item_detail_prime);
		tv_item_detail_sold_num = (TextView) findViewById(R.id.tv_item_detail_sold_num);
		tv_total_num = (TextView) findViewById(R.id.total_num);
		tv_current_num = (TextView) findViewById(R.id.current_num);
		tv_item_detail_server_time = (TimeTextView) findViewById(R.id.tv_item_detail_server_time);
		item_detail_tags_title_container = (LinearLayout) findViewById(R.id.item_detail_tags_title_container);
		item_detail_server_time_container = (LinearLayout) findViewById(R.id.item_detail_server_time_container);
		btn_item_detail_collect = (Button) findViewById(R.id.btn_item_detail_collect);
		btn_item_detail_collect.setOnClickListener(this);// 监听点击收藏事件

		// 初始化优惠布局里面的控件
		tv_activity_icon_title = (TextView) findViewById(R.id.tv_activity_icon_title);
		tv_activity_title = (TextView) findViewById(R.id.tv_activity_title);
		rl_activity_container = (RelativeLayout) findViewById(R.id.rl_activity_container);
		// 监听优惠布局的点击事件
		rl_activity_container.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 跳转到优惠活动布局
				Intent intent = new Intent(getContext(), DiscountActivity.class);

				// 将action属性下的type和info传递过去
				intent.putExtra("action_info", list_item_detail.get(0)
						.getAction_info());
				intent.putExtra("action_type", list_item_detail.get(0)
						.getAction_type());

				getContext().startActivity(intent);

			}
		});

		viewpager_item_detail_top = (ViewPager) findViewById(R.id.viewpager_item_detail_top);
		viewpager_item_detail_top.setOnPageChangeListener(this);// 监听viewPager滑动事件

		isExist = false;
		isLoadFinished = false;
	}

	// 初始化图片集合
	private List<NetworkImageView> initImageList(List<String> list_image_url) {
		List<NetworkImageView> list_image = new ArrayList<NetworkImageView>();
		for (int i = 0; i < list_image_url.size(); i++) {
			String image_url = list_image_url.get(i);
			NetworkImageView imageView = new NetworkImageView(getContext());
			imageView.setImageUrl(image_url, imageLoader);
			imageView.setScaleType(ScaleType.CENTER_CROP);
			list_image.add(imageView);
		}
		return list_image;
	}

	// ViewPager适配器
	class MyPageAdapter extends PagerAdapter {
		private List<NetworkImageView> list_image;

		public MyPageAdapter(List<NetworkImageView> list_image) {
			this.list_image = list_image;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list_image.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(list_image.get(position));
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(list_image.get(position));
			return list_image.get(position);
		}

	}

	private HashMap<String, Bitmap> bitmapContainer = new HashMap<String, Bitmap>();
	private RequestQueue mQueue;
	// 用来加载图片的工具类
	private ImageLoader imageLoader;
	// 存放图片的缓存
	private ImageCache imageCache = new ImageCache() {

		@Override
		public void putBitmap(String url, Bitmap bitmap) {
			bitmapContainer.put(url, bitmap);
		}

		@Override
		public Bitmap getBitmap(String url) {
			return bitmapContainer.get(url);
		}
	};

	// viewPager滑动事件
	@Override
	public void onPageScrollStateChanged(int arg0) {
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	@Override
	public void onPageSelected(int position) {
		// 设置当前显示图片的下标
		tv_current_num.setText("" + (position + 1));
	}

	// 图片地址
	private String image_url;

	public void getImage_url(String url) {
		image_url = url;
	}

	// 监听收藏按钮的点击事件
	@Override
	public void onClick(View v) {
		insertData();
	}


	// 将数据插入数据库
	private void insertData() {
		CollectionDBHelper helper = new CollectionDBHelper(getContext());
		ContentValues values = new ContentValues();
		values.put("id", list_item_detail.get(0).getId());
		values.put("title", list_item_detail.get(0).getTitle());
		values.put("img_url", image_url);
		/*
		 * values.put("current", list_item_detail.get(0).getCurrent());
		 * values.put("prime", list_item_detail.get(0).getPrime());
		 */
		SQLiteDatabase db = helper.getWritableDatabase();
		String sql = "select id from goodses";
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
			Toast.makeText(getContext(), "已收藏过啦  w(ﾟДﾟ)w ", Toast.LENGTH_SHORT)
					.show();
		} else {
			long res = helper.insertDataGoodses(db, values);
			if (res > 0) {
				Toast.makeText(getContext(), "收藏成功，喵", Toast.LENGTH_SHORT)
						.show();
				isExist = true;
			}
		}
	}
}
