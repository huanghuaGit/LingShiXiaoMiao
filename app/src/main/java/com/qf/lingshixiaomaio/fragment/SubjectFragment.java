package com.qf.lingshixiaomaio.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.qf.lingshixiaomaio.R;
import com.qf.lingshixiaomaio.activity.ShoppingCarActivity;
import com.qf.lingshixiaomaio.activity.SubjectDetaliActivity;
import com.qf.lingshixiaomaio.adapter.Subject_Info_Adapter;
import com.qf.lingshixiaomaio.application.MyApplication;
import com.qf.lingshixiaomaio.customView.LoadingView;
import com.qf.lingshixiaomaio.customView.SubjectTopView;
import com.qf.lingshixiaomaio.model.Subject_Info;
import com.qf.lingshixiaomaio.util.Constants;
import com.qf.lingshixiaomaio.util.JsonUtils;

import java.util.ArrayList;
import java.util.List;



/**
 * 专题内容Fragment
 * 
 * @author JoshuaJan
 * 
 */
public class SubjectFragment extends Fragment implements OnItemClickListener,
		OnRefreshListener2<ListView>, OnClickListener {
	private static final String TAG = "SubjectFragment";
	private static SubjectFragment subjectFragment;
	private List<Subject_Info> list_info;
	private View view;
	private PullToRefreshListView listView;
	private SubjectTopView topView;
	private MyJsonResponseListener mListener = new MyJsonResponseListener();
	private Subject_Info_Adapter adapter;
	private int pg_cur;
	private boolean isFirstLoad;
	private boolean isFresh;
	private LoadingView loadingView;
	private Button btn_shopping_car;// 购物车

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_subject, container, false);
		init();
		loadData();
		return view;
	}

	// 获得焦点时开始加载动画
	@Override
	public void onResume() {
		super.onResume();
		loadingView.startLoading();
	}

	private void init() {
		btn_shopping_car = (Button) view.findViewById(R.id.btn_shopping_car);
		btn_shopping_car.setOnClickListener(this);
		pg_cur = 1;
		isFirstLoad = true;
		adapter = new Subject_Info_Adapter(getActivity(),
				R.layout.item_subject_info);
		list_info = new ArrayList<Subject_Info>();
		loadingView = (LoadingView) view
				.findViewById(R.id.view_fragment_subject_loading);
		listView = (PullToRefreshListView) view
				.findViewById(R.id.listview_subject);
		// ILoadingLayout ill = listView.getLoadingLayoutProxy();
		listView.setMode(Mode.BOTH);
		listView.setOnItemClickListener(this);
		listView.setOnRefreshListener(this);
		topView = new SubjectTopView(getActivity());
		listView.getRefreshableView().addHeaderView(topView);
	}

	public static SubjectFragment getFragment() {
		if (subjectFragment == null) {
			subjectFragment = new SubjectFragment();
		}
		return subjectFragment;
	}

	private void loadData() {
		if (isFresh) {
			pg_cur = 1;
		}
		String url = String.format(Constants.URL.SUBJECT_INFO, pg_cur);
		MyApplication.requestString(url, mListener, mListener);
	}

	private class MyJsonResponseListener implements Listener<String>,
			ErrorListener {

		@Override
		public void onErrorResponse(VolleyError error) {
			try {
				Toast.makeText(getActivity(), "加载网络超时，请检查你的网络连接是否正常",
						Toast.LENGTH_SHORT).show();
			} catch (Exception e) {

			}
		}

		@Override
		public void onResponse(String response) {
			// 下载成功就停止动画
			Log.i(TAG, "下载成功");
			list_info = JsonUtils.parseSubjectInfoJson(response.toString());
			Log.i(TAG, list_info + "");
			loadingView.stopLoading();
			loadingView.setVisibility(View.GONE);
			if (isFirstLoad) {
				adapter.setDatas(list_info);
				listView.setAdapter(adapter);
			} else {
				if (isFresh) {

					adapter.setDatas(list_info);
				}
				adapter.addDatas(list_info);
			}
			listView.onRefreshComplete();
		}

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent intent = new Intent(getActivity(), SubjectDetaliActivity.class);
		intent.putExtra("subject_id", list_info.get(arg2 - 2).getId());
		startActivity(intent);

	}

	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
		Toast.makeText(getActivity(), "刷新中...", Toast.LENGTH_SHORT).show();
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				handler.post(new Runnable() {
					@Override
					public void run() {
						isFresh = true;
						isFirstLoad = false;
						loadData();
					}
				});
			}
		}).start();

	}

	private Handler handler = new Handler();

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		Toast.makeText(getActivity(), "加载中...", Toast.LENGTH_SHORT).show();
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				handler.post(new Runnable() {
					@Override
					public void run() {
						isFirstLoad = false;
						pg_cur++;
						loadData();
					}
				});
			}
		}).start();
	}

	// 跳转到购物车
	@Override
	public void onClick(View v) {
		Intent intent = new Intent(getActivity(), ShoppingCarActivity.class);
		startActivity(intent);
	}

}
