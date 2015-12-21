package com.qf.lingshixiaomaio.customView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.qf.lingshixiaomaio.R;
import com.qf.lingshixiaomaio.activity.MoreCommentsActivity;
import com.qf.lingshixiaomaio.adapter.ItemCommentsAdapter;
import com.qf.lingshixiaomaio.model.CommentsEntity;
import com.qf.lingshixiaomaio.model.Infos;
import com.qf.lingshixiaomaio.model.ItemDetailEntity;
import com.qf.lingshixiaomaio.util.JsonUtils;

import org.json.JSONObject;

import java.util.List;


/**
 * 美味详情评论以及美味信息页面
 * 
 * @author ZL
 * 
 */
public class ItemDetailCommentsView extends LinearLayout {

	private List<ItemDetailEntity> list_info;// 美味信息集合
	private List<ItemDetailEntity> list_comments;// 美味评论总数据集合

	private ListView listView_item_detail_comments;// 评论的listView
	private ItemCommentsAdapter itemCommentsAdapter;// 评论的适配器
	private RequestQueue mQueue;// 请求

	private TextView tv_comments_total_num;// 评论总条数
	private LinearLayout ll_item_detail_arguments_container;// 美味信息的容器
	private Button btn_more_arguments;// 查看更多评论按钮

	private int[] infoID = { R.id.info0, R.id.info1, R.id.info2, R.id.info3 };// 美味信息布局id

	public ItemDetailCommentsView(Context context) {
		super(context);
		intiView();
	}

	private void intiView() {
		LayoutInflater.from(getContext()).inflate(
				R.layout.view_item_detail_comments, this, true);

		mQueue = Volley.newRequestQueue(getContext());

		// 初始化ListView
		listView_item_detail_comments = (ListView) findViewById(R.id.listView_item_detail_comments);
		// 初始化适配器
		itemCommentsAdapter = new ItemCommentsAdapter(getContext(),
				R.layout.view_comments_item, mQueue);
		listView_item_detail_comments.setAdapter(itemCommentsAdapter);

		// 初始评论总标题
		tv_comments_total_num = (TextView) findViewById(R.id.tv_comments_total_num);
		// 美味信息的容器
		ll_item_detail_arguments_container = (LinearLayout) findViewById(R.id.ll_item_detail_arguments_container);
		// 更多评论按钮
		btn_more_arguments = (Button) findViewById(R.id.btn_more_arguments);
		// 监听按钮的点击跳转事件
		btn_more_arguments.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//跳转到评论页
				Intent intent = new Intent(getContext(),
						MoreCommentsActivity.class);
				//将商品的id传递过去
				intent.putExtra("id", list_head.get(0).getId());
				getContext().startActivity(intent);
			}
		});
	}

	//头布局的数据
	private List<ItemDetailEntity> list_head;
	
	// 加载数据
	public void loadData(JSONObject response) {
		// 将数据解析成对象
		list_info = JsonUtils.parseItemDetailInfoJson(response);// 美味信息数据
		list_comments = JsonUtils.parseItemDetailCommentsJson(response);// 评论总数据
		List<CommentsEntity> list_sub_comments = list_comments.get(0)
				.getList_Comments();// 评论子数据

		// 这是拿头布局的数据原来用（主要为了得到type和guide_type）
		list_head = JsonUtils.parseItemDetailHeadJson(response);

		itemCommentsAdapter.setDatas(list_sub_comments);// 设置数据给适配器

		// 设置数据给控件
		tv_comments_total_num.setText("喵亲口碑("
				+ list_comments.get(0).getTotal_num() + ")");

		// 判断类型是否满足条件
		if (list_head.get(0).getTypes() == 2 && list_head.get(0).getGuide_type() == 0) {
			// 设置布局可见
			ll_item_detail_arguments_container.setVisibility(View.VISIBLE);
			// 给布局里面控件设置数据
			// 美味信息内容的集合
			List<Infos> infos_list = list_info.get(0).getInfos_list();
			for (int i = 0; i < infos_list.size(); i++) {
				TextView textView = (TextView) findViewById(infoID[i]);
				textView.setText(infos_list.get(i).getKey() + " : "
						+ infos_list.get(i).getValue());
			}
		}

	}
}
