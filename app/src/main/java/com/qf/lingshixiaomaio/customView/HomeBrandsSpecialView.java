package com.qf.lingshixiaomaio.customView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.qf.lingshixiaomaio.R;
import com.qf.lingshixiaomaio.activity.GoodsListActivity;
import com.qf.lingshixiaomaio.model.HomeBrandsSpecialEntity;
import com.qf.lingshixiaomaio.util.JsonUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class HomeBrandsSpecialView extends LinearLayout implements
		OnClickListener {
	private NetworkImageView image_home_special_body,
			image_home_special_lingshi, image_home_special_breakfast,
			image_home_special_jingxuan;
	private List<HomeBrandsSpecialEntity> home_brandsSpecial_list;

	public HomeBrandsSpecialView(Context context) {
		super(context);
		initView();
	}

	private void initView() {
		mQueue = Volley.newRequestQueue(getContext());
		imageLoader = new ImageLoader(mQueue, imageCache);

		LayoutInflater.from(getContext()).inflate(
				R.layout.home_brands_specials_view, this, true);

		home_brandsSpecial_list = new ArrayList<HomeBrandsSpecialEntity>();

		image_home_special_body = (NetworkImageView)findViewById(R.id.image_home_brands_special_body);
		image_home_special_lingshi = (NetworkImageView)findViewById(R.id.image_home_brands_special_lingshi);
		image_home_special_breakfast = (NetworkImageView)findViewById(R.id.image_home_brands_special_breakfast);
		image_home_special_jingxuan = (NetworkImageView)findViewById(R.id.image_home_brands_special_jingxuan);
		// 监听图片点击事件
		image_home_special_body.setOnClickListener(this);
		image_home_special_lingshi.setOnClickListener(this);
		image_home_special_breakfast.setOnClickListener(this);
		image_home_special_jingxuan.setOnClickListener(this);
	}

	public void loadData(JSONObject response) {
		// 解析图片数据
		home_brandsSpecial_list = JsonUtils.parseHomeBrandsSpecialJson(
				response, home_brandsSpecial_list);
		// 下载图片
		image_home_special_body.setImageUrl(home_brandsSpecial_list.get(0)
				.getImg_url(), imageLoader);
		image_home_special_lingshi.setImageUrl(home_brandsSpecial_list.get(1)
				.getImg_url(), imageLoader);
		image_home_special_breakfast.setImageUrl(home_brandsSpecial_list.get(2)
				.getImg_url(), imageLoader);
		image_home_special_jingxuan.setImageUrl(home_brandsSpecial_list.get(3)
				.getImg_url(), imageLoader);
		image_home_special_body.setScaleType(ScaleType.CENTER_CROP);
		image_home_special_lingshi.setScaleType(ScaleType.CENTER_CROP);
		image_home_special_breakfast.setScaleType(ScaleType.CENTER_CROP);
		image_home_special_jingxuan.setScaleType(ScaleType.CENTER_CROP);
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

	
	private int type;
	private String info;
	
	//图片点击事件
	@Override
	public void onClick(View v) {
		//跳转到商品列表页面
		Intent intent = new Intent(getContext(), GoodsListActivity.class);
		switch (v.getId()) {
		case R.id.image_home_brands_special_body:
			type = home_brandsSpecial_list.get(0).getType();
			info = home_brandsSpecial_list.get(0).getInfo();
			break;

		case R.id.image_home_brands_special_lingshi:
			type = home_brandsSpecial_list.get(1).getType();
			info = home_brandsSpecial_list.get(1).getInfo();
			break;

		case R.id.image_home_brands_special_breakfast:
			type = home_brandsSpecial_list.get(2).getType();
			info = home_brandsSpecial_list.get(2).getInfo();
			break;

		case R.id.image_home_brands_special_jingxuan:
			type = home_brandsSpecial_list.get(3).getType();
			info = home_brandsSpecial_list.get(3).getInfo();
			break;
		}
		intent.putExtra("type", type);
		intent.putExtra("brands_special_info", info);
		getContext().startActivity(intent);
	}
}
