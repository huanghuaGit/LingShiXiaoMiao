package com.qf.lingshixiaomaio.customView;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.qf.lingshixiaomaio.R;

public class LoadingView extends FrameLayout {
	public LoadingView(Context context) {
		super(context, null);
	}

	public LoadingView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private static AnimationDrawable ad;
	private ImageView iv;

	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.anim_loading, this,
				true);
		iv = (ImageView) findViewById(R.id.iv_anim_loading);
		ad = (AnimationDrawable) iv.getBackground();
	}

	public void startLoading() {
		this.setVisibility(VISIBLE);
		ad.start();
	}

	public void stopLoading() {
		this.setVisibility(GONE);
		ad.stop();
	}

}
