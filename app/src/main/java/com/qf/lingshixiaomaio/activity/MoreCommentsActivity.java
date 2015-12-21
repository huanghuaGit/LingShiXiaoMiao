package com.qf.lingshixiaomaio.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.qf.lingshixiaomaio.R;
import com.qf.lingshixiaomaio.adapter.ItemCommentsAdapter;
import com.qf.lingshixiaomaio.application.MyApplication;
import com.qf.lingshixiaomaio.model.CommentsEntity;
import com.qf.lingshixiaomaio.model.ItemDetailEntity;
import com.qf.lingshixiaomaio.util.Constants;
import com.qf.lingshixiaomaio.util.JsonUtils;

import org.json.JSONObject;

import java.util.List;


/**
 * 更多评论页
 * 
 * @author ZL
 * 
 */
public class MoreCommentsActivity extends Activity implements OnClickListener {

	private ListView listView_more_comments;
	private Intent intent;// 接收传递过来的意图
	private int id;// 传递过来的商品id
	private MyApplication myApplication;
	private List<ItemDetailEntity> list_data;// 标题和评论数据的总集合
	private ItemCommentsAdapter itemCommentsAdapter;// 评论的适配器
	private RequestQueue mQueue;

	private ImageButton image_btn_comments_back;// 顶部回退键
	private TextView tv_total_comments_nums;// 顶部标题

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_more_comments);
		init();
		loadData();
	}

	private void init() {
		mQueue = Volley.newRequestQueue(this);
		myApplication = (MyApplication) getApplication();
		intent = getIntent();
		id = intent.getIntExtra("id", 0);

		tv_total_comments_nums = (TextView) findViewById(R.id.tv_total_comments_nums);
		image_btn_comments_back = (ImageButton) findViewById(R.id.image_btn_comments_back);
		image_btn_comments_back.setOnClickListener(this);// 监听回退键的点击回退功能

		listView_more_comments = (ListView) findViewById(R.id.listView_more_comments);
		// 初始化适配器
		itemCommentsAdapter = new ItemCommentsAdapter(this,
				R.layout.view_comments_item, mQueue);
		// 给listView设置是适配器
		listView_more_comments.setAdapter(itemCommentsAdapter);
	}

	// 加载数据
	private void loadData() {
		String url = String.format(Constants.URL.URL_MORE_COMMENTS, id);
		myApplication.downLoadJson(url, listener, listener);
	}

	private MyCommentsListener listener = new MyCommentsListener();

	class MyCommentsListener implements Listener<JSONObject>, ErrorListener {

		@Override
		public void onErrorResponse(VolleyError error) {
			Toast.makeText(MoreCommentsActivity.this, "加载数据失败，请检查网络是否正常连接",
					Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onResponse(JSONObject response) {
			// 解析下载好的数据，得到集合
			list_data = JsonUtils.parseItemDetailMoreCommentsJson(response);
			List<CommentsEntity> list_comments = list_data.get(0)
					.getList_Comments();// 评论子数据
			itemCommentsAdapter.setDatas(list_comments);

			// 设置总条数的数据
			tv_total_comments_nums.setText("喵亲口碑("
					+ list_data.get(0).getTotal_num() + ")");
		}

	}

	// 监听回退键的点击回退功能
	@Override
	public void onClick(View v) {
		this.finish();
	}
}
