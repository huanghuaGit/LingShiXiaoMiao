package com.qf.lingshixiaomaio.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.qf.lingshixiaomaio.R;
import com.qf.lingshixiaomaio.adapter.MyBaseAdapterSingle;

import java.util.ArrayList;
import java.util.List;


public class SearchActivity extends Activity implements OnEditorActionListener,
		TextWatcher, OnClickListener, OnItemClickListener, OnItemSelectedListener {
	private EditText ed_search;
	private TextView tv_search_cancel;
	private Boolean isNull = true;
	private ImageView btn_search_back;
	private String keyword;
	private ListView lv_search;
	private List<String> list_history;
	private MyBaseAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		init();
	}

	private void init() {
		lv_search = (ListView) findViewById(R.id.lv_search);
		adapter = new MyBaseAdapter(this, R.layout.item_search_history);
		list_history = new ArrayList<String>();
		adapter.setDatas(list_history);
		lv_search.setAdapter(adapter);
		lv_search.setOnItemClickListener(this);
		lv_search.setOnItemSelectedListener(this);
		tv_search_cancel = (TextView) findViewById(R.id.tv_search_cancel);
		tv_search_cancel.setOnClickListener(this);
		btn_search_back = (ImageView) findViewById(R.id.btn_search_back);
		btn_search_back.setOnClickListener(this);
		ed_search = (EditText) findViewById(R.id.ed_search);
		ed_search.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
		ed_search.setOnEditorActionListener(this);
		ed_search.addTextChangedListener(this);
	}

	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		if (actionId == EditorInfo.IME_ACTION_SEARCH
				|| (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {

			if (ed_search.getText().toString().equals("")) {
				Toast.makeText(this, "请输入你喜欢的零食，喵", Toast.LENGTH_SHORT).show();
			} else {
				list_history.add(keyword);
				adapter.notifyDataSetChanged();
				startSearch();
			}

			return true;
		}
		return false;
	}

	private void startSearch() {
		Intent intent = new Intent(this, SearchResultActivity.class);
		intent.putExtra("keyword", keyword);
		Toast.makeText(this, keyword, Toast.LENGTH_SHORT).show();
		startActivity(intent);
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		keyword = s.toString();
	}

	@Override
	public void afterTextChanged(Editable s) {
		if (!ed_search.getText().toString().equals("")) {
			isNull = false;
			tv_search_cancel.setText("搜索");
		} else {
			isNull = true;
			tv_search_cancel.setText("取消");
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_search_back:
			onBackPressed();
			break;
		case R.id.tv_search_cancel:
			if (isNull) {
				onBackPressed();
			} else {
				list_history.add(keyword);
				adapter.notifyDataSetChanged();
				startSearch();
			}
			break;

		default:
			break;
		}
	}

	class MyBaseAdapter extends MyBaseAdapterSingle<String> {

		public MyBaseAdapter(Context context, int resId) {
			super(context, resId);
		}

		@Override
		public void bindData(MyBaseAdapterSingle.ViewHolder vh,
				String data) {
			TextView tv = (TextView) vh.getView(R.id.tv_item_search_histroy);
			tv.setText(data);
			Button btn = (Button) vh
					.getView(R.id.btn_item_search_histroy_cancel);
		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, final int position,
			long id) {
		Toast.makeText(this, position + "", Toast.LENGTH_SHORT).show();
		Button btn_cancel = (Button) view
				.findViewById(R.id.btn_item_search_histroy_cancel);
		TextView tv = (TextView) view.findViewById(R.id.tv_item_search_histroy);
		Toast.makeText(this, tv.getText().toString(), Toast.LENGTH_SHORT)
				.show();
		btn_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				list_history.remove(position);
				adapter.notifyDataSetChanged();
			}
		});
		keyword = tv.getText().toString();
		startSearch();
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		parent.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS); 
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		parent.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS); 
		
	}
}
