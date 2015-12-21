package com.qf.lingshixiaomaio.customView;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.qf.lingshixiaomaio.R;
import com.qf.lingshixiaomaio.activity.Shuang11WebActivity;
import com.qf.lingshixiaomaio.activity.SubjectListAcitvity;
import com.qf.lingshixiaomaio.adapter.Subject_Top_Adapter;
import com.qf.lingshixiaomaio.application.MyApplication;
import com.qf.lingshixiaomaio.model.Subject_Top;
import com.qf.lingshixiaomaio.util.Constants;
import com.qf.lingshixiaomaio.util.JsonUtils;

import java.util.List;


/**
 * 专题头部
 * 
 * @author JoshuaJan
 * 
 */
public class SubjectTopView extends GridLayout implements OnItemClickListener {
	private static final String TAG = "SubjectTopView";
	private List<Subject_Top> list_top;
	private GridView mGridView;
	private MyJsonResponseListener mListener = new MyJsonResponseListener();

	public SubjectTopView(Context context) {
		super(context);
		initView();
	}

	public SubjectTopView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SubjectTopView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	private void initView() {
		LayoutInflater.from(getContext()).inflate(R.layout.view_subjetc_top,
				this, true);
		mGridView = (GridView) findViewById(R.id.gv_subject_top);
		mGridView.setOnItemClickListener(this);
		loadData();
	}

	public void loadData() {
		MyApplication.requestString(Constants.URL.SUBJECT_TOP, mListener,
				mListener);
	}

	private class MyJsonResponseListener implements Listener<String>,
			ErrorListener {

		@Override
		public void onErrorResponse(VolleyError error) {
			Toast.makeText(getContext(), "加载网络超时，请检查你的网络连接是否正常",
					Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onResponse(String response) {
			Log.i(TAG, "下载成功");
			list_top = JsonUtils.parseSubjectTopJson(response.toString());
			Log.i(TAG, list_top + "");
			Subject_Top_Adapter adapter = new Subject_Top_Adapter(getContext(),
					R.layout.item_subject_top);
			adapter.setDatas(list_top);
			mGridView.setAdapter(adapter);
		}

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		String info = list_top.get(arg2).getInfo();
		if (info.length() > 3) {
			Intent intent = new Intent(getContext(), Shuang11WebActivity.class);
			intent.putExtra("info", info);
			getContext().startActivity(intent);
		} else {
			Intent intent2 = new Intent(getContext(), SubjectListAcitvity.class);
			int oinfo = Integer.parseInt(info);
			intent2.putExtra("info", oinfo);
			getContext().startActivity(intent2);

		}
	}
}
