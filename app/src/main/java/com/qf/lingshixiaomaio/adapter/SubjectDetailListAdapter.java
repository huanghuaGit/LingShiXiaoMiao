package com.qf.lingshixiaomaio.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.qf.lingshixiaomaio.R;
import com.qf.lingshixiaomaio.application.MyApplication;
import com.qf.lingshixiaomaio.model.Subject_Detail_Goodses;

/**
 * 专题详情LIST适配器
 * @author JoshuaJan
 *
 */
public class SubjectDetailListAdapter extends
		MyBaseAdapterSingle<Subject_Detail_Goodses> {

	public SubjectDetailListAdapter(Context context, int resId) {
		super(context, resId);
	}

	@Override
	public void bindData(ViewHolder vh, Subject_Detail_Goodses data) {
		TextView title = (TextView) vh.getView(R.id.tv_subject_detail_item_title);
		TextView desc = (TextView) vh.getView(R.id.tv_subject_detail_item_desc);
		NetworkImageView image =  (NetworkImageView) vh.getView(R.id.iv_subject_detail_item);
		TextView current = (TextView) vh.getView(R.id.tv_subject_detail_item_current);
		TextView prime = (TextView) vh.getView(R.id.tv_subject_detail_item_prime);
		title.setText(data.getTitle());
		desc.setText(data.getDesc());
		current.setText("¥"+data.getCurrent()+"");
		prime.setText("¥"+data.getPrime()+"");
		prime.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG);
		MyApplication.setImageUrl(data.getImg_url(), image);
		
	}

}
