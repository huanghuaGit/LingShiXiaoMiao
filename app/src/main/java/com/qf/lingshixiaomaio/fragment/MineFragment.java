package com.qf.lingshixiaomaio.fragment;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qf.lingshixiaomaio.R;
import com.qf.lingshixiaomaio.activity.MyCollectionActivity;


public class MineFragment extends Fragment implements OnClickListener {
	private TextView tv_fragment_mine_mycollection, tv_contactcustomerservice;
	private View view;
	private static MineFragment mineFragment;

	public static MineFragment getFragment() {
		if (mineFragment == null) {
			mineFragment = new MineFragment();
		}
		return mineFragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_mine, container, false);
		init();
		return view;
	}

	private void init() {
		tv_fragment_mine_mycollection = (TextView) view
				.findViewById(R.id.tv_fragment_mine_mycollection);
		tv_fragment_mine_mycollection.setOnClickListener(this);
		tv_contactcustomerservice = (TextView) view
				.findViewById(R.id.tv_contactcustomerservice);
		tv_contactcustomerservice.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.tv_fragment_mine_mycollection:
			Intent intent = new Intent(getActivity(),
					MyCollectionActivity.class);
			startActivity(intent);
			break;
		case R.id.tv_contactcustomerservice:
			View view = null;
			Builder builder = new AlertDialog.Builder(getActivity());
			view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_customerservice, null);
			builder.setView(view);
			final AlertDialog dialog = builder.create();
			final TextView tv_dialog_cancle,tv_dialog_call;
			tv_dialog_cancle = (TextView) view.findViewById(R.id.tv_dialog_cancle);
			tv_dialog_call = (TextView) view.findViewById(R.id.tv_dialog_call);
			tv_dialog_cancle.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}
			});
			tv_dialog_call.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent();
					intent.setAction(Intent.ACTION_CALL);
					intent.setData(Uri.parse("tel:010-57236350"));
					startActivity(intent);
				}
			});
			dialog.show();
			break;
		default:
			break;
		}
	}
}
