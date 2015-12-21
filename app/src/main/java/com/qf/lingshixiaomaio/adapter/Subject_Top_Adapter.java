package com.qf.lingshixiaomaio.adapter;

import android.content.Context;

import com.android.volley.toolbox.NetworkImageView;
import com.qf.lingshixiaomaio.R;
import com.qf.lingshixiaomaio.application.MyApplication;
import com.qf.lingshixiaomaio.model.Subject_Top;


/**
 * 专题页头部适配器
 * @author JoshuaJan
 *
 */
public class Subject_Top_Adapter extends MyBaseAdapterSingle<Subject_Top> {

	public Subject_Top_Adapter(Context context, int resId) {
		super(context, resId);
	}

	@Override
	public void bindData(ViewHolder vh, Subject_Top data) {
		NetworkImageView image = (NetworkImageView) vh
				.getView(R.id.iv_subject_top_item);
		image.setImageResource(R.drawable.default_project612_306);
		MyApplication.setImageUrl(data.getImg_url(), image);
	}

}
