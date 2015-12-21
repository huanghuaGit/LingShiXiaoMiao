package com.qf.lingshixiaomaio.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.NetworkImageView;
import com.qf.lingshixiaomaio.R;
import com.qf.lingshixiaomaio.model.HomeDropDownBrandsEntity;

import java.util.HashMap;
import java.util.Map;


public class HomeDropDownBrandsAdapter extends MyBaseAdapter<HomeDropDownBrandsEntity> {

	public HomeDropDownBrandsAdapter(Context context, Map<Integer, Integer> resMap) {
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

	public HomeDropDownBrandsAdapter(Context context, Map<Integer, Integer> resMap,
			RequestQueue queue) {
		super(context, resMap);
		mQueue = queue;
		imageLoader = new ImageLoader(mQueue, imageCache);
	}

	@Override
	public void bindData(ViewHolder vh, HomeDropDownBrandsEntity data) {
			TextView tv_dropDown_title = (TextView) vh
					.getView(R.id.tv_dropDown_title);
			tv_dropDown_title.setText(data.getTitle());
			TextView tv_dropDown_current = (TextView) vh
					.getView(R.id.tv_dropDown_current);
			tv_dropDown_current.setText("¥ " + data.getCurrent());
			NetworkImageView imageView = (NetworkImageView) vh
					.getView(R.id.image_dropDown_brands);
			imageView.setImageResource(R.drawable.default_class_brandteam_640_256);
			imageView.setImageUrl(data.getImg_url(), imageLoader);
		
	}

}