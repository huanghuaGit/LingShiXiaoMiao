package com.qf.lingshixiaomaio.adapter;

import android.content.Context;
import android.widget.TextView;

import com.qf.lingshixiaomaio.R;
import com.qf.lingshixiaomaio.model.HomeTabSelectEntity;


//tabView的商品列表的弹出选择布局的适配器 
public class HomeTabSelectAdapter extends
		MyBaseAdapterSingle<HomeTabSelectEntity> {

	public HomeTabSelectAdapter(Context context, int resId) {
		super(context, resId);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void bindData(ViewHolder vh, HomeTabSelectEntity data) {
		TextView tv_tabView_select = (TextView) vh
				.getView(R.id.tv_tabView_select);
		tv_tabView_select.setText(data.getTitle());
	}

}
