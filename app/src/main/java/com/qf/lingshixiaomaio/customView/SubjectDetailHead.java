package com.qf.lingshixiaomaio.customView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.qf.lingshixiaomaio.R;
import com.qf.lingshixiaomaio.application.MyApplication;
import com.qf.lingshixiaomaio.model.Subject_Detail;

import java.util.List;


/**
 * 专题详情头部
 * @author JoshuaJan
 *
 */
public class SubjectDetailHead extends LinearLayout {
	private TextView tv_subject_info_detail_title, tv_subject_info_detail_desc;
	private NetworkImageView iv_subject_info_detail;
	private List<Subject_Detail> list_detail;

	public SubjectDetailHead(Context context, List<Subject_Detail> list_detail) {
		super(context);
		this.list_detail = list_detail;
		init();
	}

	public SubjectDetailHead(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	private void init() {
		LayoutInflater inflater = LayoutInflater.from(getContext());
		inflater.inflate(R.layout.view_subject_detail_head, this, true);
		tv_subject_info_detail_title = (TextView) findViewById(R.id.tv_subject_info_detail_title);
		tv_subject_info_detail_desc = (TextView) findViewById(R.id.tv_subject_info_detail_desc);
		iv_subject_info_detail = (NetworkImageView) findViewById(R.id.iv_subject_info_detail);
		tv_subject_info_detail_title.setText(list_detail.get(0).getTitle());
		if (iv_subject_info_detail != null) {
			MyApplication.setImageUrl(list_detail.get(0).getImg_url(),
					iv_subject_info_detail);
		}
		tv_subject_info_detail_desc.setText(list_detail.get(0).getDesc());
	}

}
