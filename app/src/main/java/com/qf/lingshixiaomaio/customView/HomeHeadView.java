package com.qf.lingshixiaomaio.customView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.qf.lingshixiaomaio.R;
import com.qf.lingshixiaomaio.activity.GoodsListActivity;
import com.qf.lingshixiaomaio.model.HomeHeadEntity;
import com.qf.lingshixiaomaio.util.JsonUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class HomeHeadView extends RelativeLayout implements
		OnPageChangeListener {

	private ViewPager homeViewPager;
	private List<HomeHeadEntity> data_list; // 存放头布局数据的集合
	private List<ImageView> images_list;
	private LinearLayout linearLayout_dot;// 底部小圆点的父布局

	public HomeHeadView(Context context) {
		super(context);
		initView();
		startThread();// 开启线程使ViewPage实现滑动
	}

	private void initView() {
		mQueue = Volley.newRequestQueue(getContext());
		imageLoader = new ImageLoader(mQueue, imageCache);

		LayoutInflater.from(getContext()).inflate(R.layout.home_headview, this,
				true);
		linearLayout_dot = (LinearLayout) findViewById(R.id.home_layout_dot);

		data_list = new ArrayList<HomeHeadEntity>();
		images_list = new ArrayList<ImageView>();
		homeViewPager = (ViewPager) findViewById(R.id.home_viewPage);
		homeViewPager.setOnPageChangeListener(this);
	}

	// 加载数据
	public void loadData(JSONObject response) {
		//将JSON数据解析成对象
		data_list = JsonUtils.parseHomeViewPagerJson(response);
		
		images_list = getViewPageData(data_list);
		initImageView(images_list);
		MyPageAdapter adapter = new MyPageAdapter(images_list);
		homeViewPager.setAdapter(adapter);

	}

	// 通过实体数据集合获得ViewPager的数据源
	public List<ImageView> getViewPageData(List<HomeHeadEntity> data) {
		List<ImageView> images_list = null;
		if (data != null && data.size() > 0) {
			images_list = new ArrayList<ImageView>();
			for (int i = 0; i < data.size(); i++) {
				NetworkImageView imageView = new NetworkImageView(getContext());
				imageView.setImageUrl(data.get(i).getImg_url(), imageLoader);
				imageView.setScaleType(ScaleType.CENTER_CROP);
				images_list.add(imageView);
			}
		}
		return images_list;
	}

	// 初始化导航栏图片（小点）
	private void initImageView(List<ImageView> images_list2) {
		for (int i = 0; i < images_list2.size(); i++) {
			ImageView imageView = new ImageView(getContext());
			if (i == 0) {
				imageView.setImageResource(R.drawable.icon_dot_black);
			} else {
				imageView.setImageResource(R.drawable.icon_dot_white);
			}
			linearLayout_dot.addView(imageView);
		}
	}

	// ViewPage图片适配器
	class MyPageAdapter extends PagerAdapter {

		private List<ImageView> images_list;

		public MyPageAdapter(List<ImageView> images_list) {
			this.images_list = images_list;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return images_list.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(images_list.get(position));
		}

		@Override
		public Object instantiateItem(ViewGroup container, final int position) {

			// 设置ViewPager里面的item的点击事件
			NetworkImageView imageView = (NetworkImageView) images_list
					.get(position);
			imageView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(getContext(), GoodsListActivity.class);
					String info = data_list.get(position).getInfo();
					int type = data_list.get(position).getType();
					intent.putExtra("info", info);
					intent.putExtra("type", type);
					getContext().startActivity(intent);
					
				}
			});

			container.addView(images_list.get(position));
			return images_list.get(position);
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

	// ViewPage滑动事件
	@Override
	public void onPageScrollStateChanged(int arg0) {
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	@Override
	public void onPageSelected(int position) {
		// 设置小圆点的颜色
		for (int i = 0; i < linearLayout_dot.getChildCount(); i++) {
			((ImageView) linearLayout_dot.getChildAt(i))
					.setImageResource(R.drawable.icon_dot_white);
		}
		((ImageView) linearLayout_dot.getChildAt(position))
				.setImageResource(R.drawable.icon_dot_black);
	}

	private Handler handler = new Handler() {
	};

	// 控制ViewPager自动滑动
	public void startThread() {
		new Thread(new Runnable() {
			int index = 0;

			@Override
			public void run() {
				while (true) {
					handler.post(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							homeViewPager.setCurrentItem(index);
							index++;
							if (index >= linearLayout_dot.getChildCount()) {
								index = 0;
							}
						}
					});
					try {
						Thread.sleep(4000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

}
