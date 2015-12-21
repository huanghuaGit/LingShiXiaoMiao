package com.qf.lingshixiaomaio.activity;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.qf.lingshixiaomaio.R;
import com.qf.lingshixiaomaio.adapter.SubjectDetailListAdapter;
import com.qf.lingshixiaomaio.application.MyApplication;
import com.qf.lingshixiaomaio.customView.SubjectDetailHead;
import com.qf.lingshixiaomaio.model.Subject_Detail;
import com.qf.lingshixiaomaio.model.Subject_Detail_Goodses;
import com.qf.lingshixiaomaio.util.CollectionDBHelper;
import com.qf.lingshixiaomaio.util.Constants;
import com.qf.lingshixiaomaio.util.JsonUtils;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;

import java.util.List;



/**
 * 专题详情
 * 
 * @author JoshuaJan
 * 
 */
public class SubjectDetaliActivity extends Activity implements
		OnScrollListener, OnClickListener, OnItemClickListener {
	private MyJsonResponseListener mListener = new MyJsonResponseListener();
	private Myurl_webResponseListener url_webListener = new Myurl_webResponseListener();
	private List<Subject_Detail> list_detail;
	private List<Subject_Detail_Goodses> list_good;
	private TextView tv_subject_detail_hotindex, tv_subject_detail_sharenum;
	private ListView lv_subject_info_detail;
	private SubjectDetailListAdapter adapter;
	private SubjectDetailHead view_head = null;
	private LinearLayout ll_subject_detail_footer;
	private Boolean isBottom;
	private Button btn_subject_detail_back;
	private Boolean isExist;
	private PopupWindow popupWindow;
	private UMSocialService mController = UMServiceFactory
			.getUMSocialService("com.umeng.share");
	private String url_web;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_subject_detail);
		init();
		loadData();
	}

	private void init() {
		lv_subject_info_detail = (ListView) findViewById(R.id.lv_subject_info_detail);
		tv_subject_detail_hotindex = (TextView) findViewById(R.id.tv_subject_detail_hotindex);
		tv_subject_detail_sharenum = (TextView) findViewById(R.id.tv_subject_detail_sharenum);
		tv_subject_detail_hotindex.setOnClickListener(this);
		tv_subject_detail_sharenum.setOnClickListener(this);
		ll_subject_detail_footer = (LinearLayout) findViewById(R.id.ll_subject_detail_footer);
		btn_subject_detail_back = (Button) findViewById(R.id.btn_subject_detail_back);
		btn_subject_detail_back.setOnClickListener(this);
		lv_subject_info_detail.setOnScrollListener(this);
		lv_subject_info_detail.setOnItemClickListener(this);
		
		isExist = false;
	}

	private void loadData() {
		Intent intent = getIntent();
		int subject_id = intent.getIntExtra("subject_id", 0);
		Log.i("SubjectDetaliActivity", subject_id + "");
		String url = String.format(Constants.URL.SUBJECT_DETAIL, subject_id);
		String url_web = String.format(Constants.URL.SUBJECT_DETAIL_WEB,
				subject_id);
		MyApplication.requestString(url, mListener, mListener);
		MyApplication.requestString(url_web, url_webListener, url_webListener);
	}

	private class Myurl_webResponseListener implements Listener<String>,
			ErrorListener {

		@Override
		public void onErrorResponse(VolleyError error) {

		}

		@Override
		public void onResponse(String response) {
			url_web = JsonUtils.parseSubjectDetialWebJson(response.toString());
		}

	}

	private class MyJsonResponseListener implements Listener<String>,
			ErrorListener {

		@Override
		public void onErrorResponse(VolleyError error) {
		}

		@Override
		public void onResponse(String response) {
			list_detail = JsonUtils.parseSubjectDetialJson(response.toString());
			list_good = JsonUtils.parseSubjectDetialGoodJson(response
					.toString());
			view_head = new SubjectDetailHead(SubjectDetaliActivity.this,
					list_detail);
			lv_subject_info_detail.addHeaderView(view_head);
			if (response != null && list_detail.size() > 0) {
				tv_subject_detail_sharenum.setText(list_detail.get(0)
						.getShare_num() + "");
				tv_subject_detail_hotindex.setText(list_detail.get(0)
						.getHotindex() + "");
			}
			if (list_good.size() > 0) {
				adapter = new SubjectDetailListAdapter(
						SubjectDetaliActivity.this,
						R.layout.item_subject_detail_list);
				adapter.setDatas(list_good);
				lv_subject_info_detail.setAdapter(adapter);
			}
			// 设置分享内容
			mController.setShareContent(list_detail.get(0).getTitle());
			mController.setShareMedia(new UMImage(SubjectDetaliActivity.this,
					list_detail.get(0).getImg_url()));
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if (scrollState == OnScrollListener.SCROLL_STATE_IDLE && isBottom) {
			ll_subject_detail_footer.setVisibility(View.GONE);
		}
		if (!isBottom) {
			ll_subject_detail_footer.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		isBottom = firstVisibleItem + visibleItemCount == totalItemCount;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_subject_detail_back:
			onBackPressed();
			break;
		case R.id.tv_subject_detail_hotindex:
			// 收藏到数据库
			insertData();
			break;
		case R.id.tv_subject_detail_sharenum:
			sharePopWindow();
			break;
		case R.id.btn_share_wb:
			startShare(SHARE_MEDIA.SINA);
			break;
		case R.id.btn_share_wx:
			startShare(SHARE_MEDIA.WEIXIN);
			break;
		case R.id.btn_share_py:
			startShare(SHARE_MEDIA.WEIXIN_CIRCLE);
			break;
		case R.id.btn_share_qq:
			// QQShareContent qqShareContent = new QQShareContent();
			// qqShareContent.setShareMedia(new UMImage(this, list_detail.get(0)
			// .getImg_url()));
			// qqShareContent.setTargetUrl(url_web);
			// qqShareContent.setTitle(list_detail.get(0).getTitle());
			// mController.setShareMedia(qqShareContent);
			startShare(SHARE_MEDIA.QQ);
			break;
		case R.id.btn_share_kj:
			startShare(SHARE_MEDIA.QZONE);
			break;
		case R.id.btn_share_copy:
			ClipboardManager cmb = (ClipboardManager) this
					.getSystemService(this.CLIPBOARD_SERVICE);
			cmb.setText(url_web.trim());
			Toast.makeText(this, "已复制到剪贴板", Toast.LENGTH_SHORT).show();
			break;
		case R.id.btn_share_cancel:
			popupWindow.dismiss();
			break;
		default:

			break;
		}
	}

	private void startShare(SHARE_MEDIA share_media) {
		mController.directShare(this, share_media, new SnsPostListener() {

			@Override
			public void onStart() {

			}

			@Override
			public void onComplete(SHARE_MEDIA arg0, int arg1,
					SocializeEntity arg2) {
				Toast.makeText(SubjectDetaliActivity.this, "分享完成",
						Toast.LENGTH_SHORT).show();
			}
		});
	}

	//有盟分享
	private void sharePopWindow() {
		View popwindow_view = getLayoutInflater().inflate(
				R.layout.popwindow_share, null);
		popupWindow = new PopupWindow(popwindow_view,
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		popupWindow.showAtLocation(popwindow_view, Gravity.BOTTOM, 0, 0);
		Button btn_share_wb, btn_share_wx, btn_share_py, btn_share_qq, btn_share_kj, btn_share_copy, btn_share_cancel;
		btn_share_wb = (Button) popwindow_view.findViewById(R.id.btn_share_wb);
		btn_share_wx = (Button) popwindow_view.findViewById(R.id.btn_share_wx);
		btn_share_py = (Button) popwindow_view.findViewById(R.id.btn_share_py);
		btn_share_qq = (Button) popwindow_view.findViewById(R.id.btn_share_qq);
		btn_share_kj = (Button) popwindow_view.findViewById(R.id.btn_share_kj);
		btn_share_copy = (Button) popwindow_view
				.findViewById(R.id.btn_share_copy);
		btn_share_cancel = (Button) popwindow_view
				.findViewById(R.id.btn_share_cancel);
		btn_share_wb.setOnClickListener(this);
		btn_share_wx.setOnClickListener(this);
		btn_share_py.setOnClickListener(this);
		btn_share_qq.setOnClickListener(this);
		btn_share_kj.setOnClickListener(this);
		btn_share_copy.setOnClickListener(this);
		btn_share_cancel.setOnClickListener(this);
		String appID = "wx967daebe835fbeac";
		String appSecret = "5fa9e68ca3970e87a1f83e563c8dcbce";
		// 添加微信平台
		UMWXHandler wxHandler = new UMWXHandler(this, appID, appSecret);
		wxHandler.addToSocialSDK();
		// 添加微信朋友圈
		UMWXHandler wxCircleHandler = new UMWXHandler(this, appID, appSecret);
		wxCircleHandler.setToCircle(true);
		wxCircleHandler.addToSocialSDK();
		UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(this, "100424468",
				"c7394704798a158208a74ab60104f0ba");
		qqSsoHandler.addToSocialSDK();
		QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(this,
				"100424468", "c7394704798a158208a74ab60104f0ba");
		qZoneSsoHandler.addToSocialSDK();

	}

	//将数据插入到数据库
	private void insertData() {
		CollectionDBHelper helper = new CollectionDBHelper(this);
		ContentValues values = new ContentValues();
		values.put("id", list_detail.get(0).getId());
		values.put("title", list_detail.get(0).getTitle());
		values.put("img_url", list_detail.get(0).getImg_url());
		values.put("hotindex", list_detail.get(0).getHotindex());
		SQLiteDatabase db = helper.getWritableDatabase();
		String sql = "select id from subject";
		Cursor selectCursor = helper.selectCursor(sql, null);
		while (selectCursor.moveToNext()) {
			int id = selectCursor.getInt(selectCursor.getColumnIndex("id"));
			if (id == list_detail.get(0).getId()) {
				isExist = true;
				break;
			}
			selectCursor.moveToNext();
		}
		if (isExist) {
			Toast.makeText(getApplicationContext(), "已收藏过啦  w(ﾟДﾟ)w ",
					Toast.LENGTH_SHORT).show();
		} else {
			long res = helper.insertDataSubject(db, values);
			if (res > 0) {
				Toast.makeText(getApplicationContext(), "收藏成功，喵",
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if (position - 1 >= 0) {
			Intent intent = new Intent(this, ItemDetialActivity.class);
			intent.putExtra("id", list_good.get(position - 1).getId());
			Log.i("id", list_good.get(position - 1).getId() + "");
			startActivity(intent);
		}
	}
}
