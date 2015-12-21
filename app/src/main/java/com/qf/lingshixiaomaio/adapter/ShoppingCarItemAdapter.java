package com.qf.lingshixiaomaio.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.NetworkImageView;
import com.qf.lingshixiaomaio.R;
import com.qf.lingshixiaomaio.model.ItemDetailEntity;

import java.util.HashMap;

/**
 * 购物车商品适配器
 * 
 * @author zl
 * 
 */
public class ShoppingCarItemAdapter extends
		MyBaseAdapterSingle<ItemDetailEntity> {

	public ShoppingCarItemAdapter(Context context, int resId) {
		super(context, resId);
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

	public ShoppingCarItemAdapter(Context context, int resId, RequestQueue queue) {
		super(context, resId);
		mQueue = queue;
		imageLoader = new ImageLoader(mQueue, imageCache);
	}

	@Override
	public void bindData(ViewHolder vh, ItemDetailEntity data) {
		NetworkImageView imageView = (NetworkImageView) vh
				.getView(R.id.imageView_item);
		imageView.setImageUrl(data.getImage_url(), imageLoader);
		if (data.getImage_url() == null) {
			imageView.setImageResource(R.drawable.default_bg_150_150);
		}
		TextView tv_title = (TextView) vh.getView(R.id.tv_title);
		tv_title.setText(data.getTitle());
		TextView tv_current = (TextView) vh.getView(R.id.tv_current);
		tv_current.setText("¥ " + Math.round(data.getCurrent() * 100) / 100);
		TextView tv_prime = (TextView) vh.getView(R.id.tv_prime);
		tv_prime.setText("¥ " + Math.round(data.getPrime() * 100) / 100);
		tv_prime.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
	}

}
