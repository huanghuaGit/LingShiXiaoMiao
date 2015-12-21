package com.qf.lingshixiaomaio.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.qf.lingshixiaomaio.R;
import com.qf.lingshixiaomaio.adapter.Subject_Info_Adapter;
import com.qf.lingshixiaomaio.application.MyApplication;
import com.qf.lingshixiaomaio.model.Subject_Info;
import com.qf.lingshixiaomaio.util.Constants;
import com.qf.lingshixiaomaio.util.JsonUtils;

import java.util.List;


/**
 * 专题列表
 * @author JoshuaJan
 *
 */
public class SubjectListAcitvity extends Activity implements OnClickListener, OnItemClickListener {
	private ListView lv_list;
	private List<Subject_Info> list_info;
	private MyJsonResponseListener mListener = new MyJsonResponseListener();
	private Subject_Info_Adapter adapter;
	private Button btn_back;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_subject_list);
		init();
	}

	private void init() {
		btn_back = (Button) findViewById(R.id.btn_subject_list_back);
		btn_back.setOnClickListener(this);
		lv_list = (ListView) findViewById(R.id.lv_subject_list);
		lv_list.setOnItemClickListener(this);
		Intent intent = getIntent();
		int info = intent.getIntExtra("info", 0);
		String url = String.format(Constants.URL.SUBJECT_LIST, info);
		loadData(url);
	}

	private void loadData(String url) {
		MyApplication.requestString(url, mListener, mListener);
	}

	private class MyJsonResponseListener implements Listener<String>,
			ErrorListener {

		@Override
		public void onErrorResponse(VolleyError error) {

		}

		@Override
		public void onResponse(String response) {
			list_info = JsonUtils.parseSubjectInfoJson(response);
			adapter = new Subject_Info_Adapter(SubjectListAcitvity.this,
					R.layout.item_subject_info);
			adapter.setDatas(list_info);
			lv_list.setAdapter(adapter);
		}

	}
	
	@Override
	public void onClick(View v) {
		onBackPressed();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {	
		Intent intent = new Intent(this, SubjectDetaliActivity.class);
		intent.putExtra("subject_id", list_info.get(position).getId());
		startActivity(intent);
	}
}
