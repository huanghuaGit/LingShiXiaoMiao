package com.qf.lingshixiaomaio.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.NetworkImageView;
import com.qf.lingshixiaomaio.R;
import com.qf.lingshixiaomaio.model.HomeTabEntity;

import java.util.HashMap;
import java.util.Map;


public class HomeTabAdpater extends MyBaseAdapter<HomeTabEntity> {

	public HomeTabAdpater(Context context, Map<Integer, Integer> resMap) {
		super(context, resMap);
		// TODO Auto-generated constructor stub
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

	public HomeTabAdpater(Context context, Map<Integer, Integer> resMap,
			RequestQueue queue) {
		super(context, resMap);
		mQueue = queue;
		imageLoader = new ImageLoader(mQueue, imageCache);
	}

	@Override
	public void bindData(ViewHolder vh, HomeTabEntity data) {
		NetworkImageView imageView = (NetworkImageView) vh
				.getView(R.id.image_tab);
		imageView.setImageUrl(data.getImg_url(), imageLoader);
		TextView tv_tab_title = (TextView) vh.getView(R.id.tv_tab_title);
		tv_tab_title.setText(data.getTitle());
		TextView tv_tab_desc = (TextView) vh.getView(R.id.tv_tab_desc);
		tv_tab_desc.setText(data.getDesc());
	}

}
