package com.qf.lingshixiaomaio.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.qf.lingshixiaomaio.R;
import com.qf.lingshixiaomaio.adapter.ShoppingCarItemAdapter;
import com.qf.lingshixiaomaio.application.MyApplication;
import com.qf.lingshixiaomaio.model.ItemDetailEntity;
import com.qf.lingshixiaomaio.util.CollectionDBHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 购物车页面
 * 
 * @author ZL
 * 
 */
public class ShoppingCarActivity extends Activity implements
		OnItemClickListener, OnItemLongClickListener,
		android.view.View.OnClickListener {

	private ListView listView_shopping_car;
	private List<Map<String, Object>> list;
	private List<ItemDetailEntity> list_data; // 存放实体类的集合
	private ShoppingCarItemAdapter adapter;
	private MyApplication myApplication;
	private Cursor cursor;

	// 控件
	private ImageView image_all_select;// 全选
	private TextView tv_total_price;// 合计价钱

	private Button btn_submit;// 提交按钮

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shoppingcar);
		initView();
	}

	CollectionDBHelper helper;

	private void initView() {
		myApplication = (MyApplication) getApplication();

		btn_submit = (Button) findViewById(R.id.btn_submit);
		btn_submit.setOnClickListener(this);
		// 总价钱
		tv_total_price = (TextView) findViewById(R.id.tv_total_price);

		listView_shopping_car = (ListView) findViewById(R.id.listView_shopping_car);
		listView_shopping_car.setOnItemClickListener(this);
		listView_shopping_car.setOnItemLongClickListener(this);

		helper = new CollectionDBHelper(ShoppingCarActivity.this);
		String sql = "select * from shoppingCar";
		cursor = helper.selectCursor(sql, null);
		list = helper.cursorToList(cursor);
		list_data = new ArrayList<ItemDetailEntity>();
		for (int i = 0; i < list.size(); i++) {
			String title = (String) list.get(i).get("title");
			String image_url = (String) list.get(i).get("img_url");
			float current = (Float) list.get(i).get("current");
			float prime = (Float) list.get(i).get("prime");
			int select_type = 0;
			ItemDetailEntity itemDetailEntity = new ItemDetailEntity(image_url,
					title, current, prime, select_type);
			list_data.add(itemDetailEntity);
		}

		adapter = new ShoppingCarItemAdapter(this, R.layout.shopping_car_item,
				myApplication.getRequestQueue());
		listView_shopping_car.setAdapter(adapter);
		adapter.addDatas(list_data);

		// 全选按钮
		/*
		 * image_all_select = (ImageView)
		 * findViewById(R.id.imageView_all_select);
		 * image_all_select.setOnClickListener(new View.OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { double total_price = 0; if
		 * (!isclick) { image_all_select
		 * .setImageResource(R.drawable.btn_cart_select_selected); for (int i =
		 * 0; i < list_data.size(); i++) { ImageView image_select = (ImageView)
		 * listView_shopping_car .getChildAt(i).findViewById(
		 * R.id.imageView_select); image_select
		 * .setImageResource(R.drawable.btn_cart_select_selected);
		 * 
		 * if (list_data.get(i).getSelect_type() == 1) { total_price +=
		 * list_data.get(i).getCurrent(); } } tv_total_price.setText(total_price
		 * + ""); isclick = true; } else { image_all_select
		 * .setImageResource(R.drawable.btn_delete_normal); for (int i = 0; i <
		 * list_data.size(); i++) { ImageView image_select = (ImageView)
		 * listView_shopping_car .getChildAt(i).findViewById(
		 * R.id.imageView_select); image_select
		 * .setImageResource(R.drawable.btn_delete_normal);
		 * 
		 * if (list_data.get(i).getSelect_type() == 1) { total_price +=
		 * list_data.get(i).getCurrent(); } } tv_total_price.setText(total_price
		 * + ""); isclick = false; }
		 * 
		 * } });
		 */
	}

	// 长按删除商品事件
	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			final int position, long id) {
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("提示");
		builder.setMessage("在购物车中删除该商品？");
		builder.setPositiveButton("确定", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// 从list中删除该商品
				list_data.remove(position);
				adapter.setDatas(list_data);

				// 从数据库中删除该商品
				helper.deleteDataShoppingCar("title=?",
						new String[] { list_data.get(position).getTitle() });

			}
		});
		builder.setNegativeButton("取消", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				builder.create().dismiss();
			}
		});
		builder.create().show();

		return false;
	}

	// 点击选中商品事件
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		ImageView image = (ImageView) parent.getChildAt(position).findViewById(
				R.id.imageView_select);
		if (list_data.get(position).getSelect_type() == 0) {
			image.setImageResource(R.drawable.btn_cart_select_selected);
			list_data.get(position).setSelect_type(1);
		} else if (list_data.get(position).getSelect_type() == 1) {
			image.setImageResource(R.drawable.btn_delete_normal);
			list_data.get(position).setSelect_type(0);
		}

		// 设置总价钱
		double total_price = 0;

		for (int i = 0; i < list_data.size(); i++) {
			/*
			 * // 全部选中 if (list_data.get(i).getSelect_type() == 1) {
			 * image_all_select
			 * .setImageResource(R.drawable.btn_cart_select_selected); } else {
			 * image_all_select.setImageResource(R.drawable.btn_delete_normal);
			 * }
			 */
			// 设置总价钱
			if (list_data.get(i).getSelect_type() == 1) {
				total_price += list_data.get(i).getCurrent();
			}
		}
		tv_total_price.setText("¥ " + Math.round(total_price * 100) / 100 + "");

	}

	// private boolean isclick = false;// 判断全选按钮是否可用

	// 提交事件
	@Override
	public void onClick(View v) {
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("提示");
		builder.setMessage("亲，你还没登陆呢~喵");
		builder.setPositiveButton("确定", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				builder.create().dismiss();
			}
		});
		builder.create().show();
	}

}
