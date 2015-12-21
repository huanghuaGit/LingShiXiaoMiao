package com.qf.lingshixiaomaio.adapter;

import android.content.Context;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.qf.lingshixiaomaio.R;
import com.qf.lingshixiaomaio.application.MyApplication;
import com.qf.lingshixiaomaio.model.Subject_Info;


/**
 * 专题页LIST适配器
 * @author JoshuaJan
 *
 */
public class Subject_Info_Adapter extends MyBaseAdapterSingle<Subject_Info> {

	public Subject_Info_Adapter(Context context, int resId) {
		super(context, resId);
	}


	@Override
	public void bindData(ViewHolder vh, Subject_Info data) {
		NetworkImageView image = (NetworkImageView) vh
				.getView(R.id.iv_subject_info_item);
		TextView hotindex = (TextView) vh.getView(R.id.tv_subject_info_item);
		image.setImageResource(R.drawable.default_project612_306);
		MyApplication.setImageUrl(data.getImg_url(), image);
		hotindex.setText(data.getHotindex() + "");
		TextView title = (TextView) vh.getView(R.id.tv_subject_info_item_title);
		title.setText(data.getTitle());
	}

}
