package com.qf.lingshixiaomaio.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.view.View;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.NetworkImageView;
import com.qf.lingshixiaomaio.R;
import com.qf.lingshixiaomaio.model.ItemListEntity;

import java.util.HashMap;


public class ItemListAdapter extends MyBaseAdapterSingle<ItemListEntity> {

	public ItemListAdapter(Context context, int resId) {
		super(context, resId);
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

	public ItemListAdapter(Context context, int resId, RequestQueue queue) {
		super(context, resId);
		mQueue = queue;
		imageLoader = new ImageLoader(mQueue, imageCache);
	}

	@Override
	public void bindData(ViewHolder vh, ItemListEntity data) {
		NetworkImageView imageView = (NetworkImageView) vh
				.getView(R.id.image_item_list);
		//判断商品是否已经卖完，如果卖完，则设置卖完的图片
		if(data.getStatus()==1){//卖完
			imageView.setImageResource(R.drawable.icon_sold);
		}else{		//没卖完
			imageView.setImageUrl(data.getImg_url(), imageLoader);
		}
		imageView.setScaleType(ScaleType.CENTER_CROP);
		
		TextView tv_item_list_title = (TextView) vh
				.getView(R.id.tv_item_list_title);
		tv_item_list_title.setText(data.getTitle());
		TextView tv_item_price_current = (TextView) vh
				.getView(R.id.tv_item_price_current);
		tv_item_price_current.setText("¥ " + data.getCurrent());
		TextView tv_item_price_prime = (TextView) vh
				.getView(R.id.tv_item_price_prime);
		tv_item_price_prime.setText("¥ " + data.getPrime());
		tv_item_price_prime.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
		LinearLayout layout_title = (LinearLayout) vh
				.getView(R.id.layout_title);
		TextView tv_item_tag_title = (TextView) vh
				.getView(R.id.tv_item_tag_title);
		tv_item_tag_title.setText(data.getTag_title());
		if ((data.getTag_title()).equals("")) {
			layout_title.setVisibility(View.GONE);
		}

	}

}
