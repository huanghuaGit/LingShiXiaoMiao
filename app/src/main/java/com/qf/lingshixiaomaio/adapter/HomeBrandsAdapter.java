package com.qf.lingshixiaomaio.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.NetworkImageView;
import com.qf.lingshixiaomaio.R;
import com.qf.lingshixiaomaio.customView.TimeTextView;
import com.qf.lingshixiaomaio.model.HomeCenterBrandsEntity;

import java.util.HashMap;
import java.util.Map;



public class HomeBrandsAdapter extends MyBaseAdapter<HomeCenterBrandsEntity> {

	public HomeBrandsAdapter(Context context, Map<Integer, Integer> resMap) {
		super(context, resMap);
	}

	private HashMap<String, Bitmap> bitmapContainer = new HashMap<String, Bitmap>();

	private RequestQueue mQueue;

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

	// 用来加载图片的工具类
	private ImageLoader imageLoader;

	public HomeBrandsAdapter(Context context, Map<Integer, Integer> resMap,
			RequestQueue queue) {
		super(context, resMap);
		mQueue = queue;
		imageLoader = new ImageLoader(mQueue, imageCache);
	}

	@Override
	public void bindData(ViewHolder vh, HomeCenterBrandsEntity data) {
		NetworkImageView imageView = (NetworkImageView) vh
				.getView(R.id.image_brands);
		imageView.setImageUrl(data.getImg_url(), imageLoader);

		TextView tv_discount = (TextView) vh.getView(R.id.tv_discount);
		tv_discount.setText(data.getDiscount());
		TextView tv_title = (TextView) vh.getView(R.id.tv_title);
		tv_title.setText(data.getTitle());

		// 倒计时自定义textView
		TimeTextView tv_time = (TimeTextView) vh.getView(R.id.tv_time);
		// 将毫秒转为时分秒
		long days = data.getTime() / (1000 * 60 * 60 * 24);
		long hours = (data.getTime() % (1000 * 60 * 60 * 24))
				/ (1000 * 60 * 60);
		long minutes = (data.getTime() % (1000 * 60 * 60)) / (1000 * 60);
		long seconds = (data.getTime() % (1000 * 60)) / 1000;

		tv_time.setTime(days + "", hours + "", minutes + "", seconds + "");

	}
}
