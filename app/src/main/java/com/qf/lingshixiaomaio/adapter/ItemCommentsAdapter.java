package com.qf.lingshixiaomaio.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.NetworkImageView;
import com.qf.lingshixiaomaio.R;
import com.qf.lingshixiaomaio.model.CommentsEntity;

import java.util.HashMap;


 
/**美味详情评论适配器
 * @author Administrator
 *
 */
public class ItemCommentsAdapter extends MyBaseAdapterSingle<CommentsEntity> {

	public ItemCommentsAdapter(Context context, int resId) {
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

	public ItemCommentsAdapter(Context context, int resId, RequestQueue queue) {
		super(context, resId);
		mQueue = queue;
		imageLoader = new ImageLoader(mQueue, imageCache);
	}

	@Override
	public void bindData(ViewHolder vh, CommentsEntity data) {
		NetworkImageView imageView = (NetworkImageView) vh
				.getView(R.id.image_user);
		imageView.setImageUrl(data.getComments_img_url(), imageLoader);
		imageView.setScaleType(ScaleType.CENTER_CROP);
		TextView tv_user_nickname = (TextView) vh
				.getView(R.id.tv_user_nickname);
		tv_user_nickname.setText(data.getNickname());
		TextView tv_content = (TextView) vh.getView(R.id.tv_content);
		tv_content.setText(data.getContent());
	}

}
