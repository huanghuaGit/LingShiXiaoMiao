package com.qf.lingshixiaomaio.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.qf.lingshixiaomaio.R;
import com.qf.lingshixiaomaio.activity.SubjectDetaliActivity;
import com.qf.lingshixiaomaio.adapter.Subject_Info_Adapter;
import com.qf.lingshixiaomaio.model.Subject_Info;
import com.qf.lingshixiaomaio.util.CollectionDBHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SubjectCollectionFragment extends Fragment implements
		OnItemClickListener {
	private static SubjectCollectionFragment subjectCollectionFragment;
	private View view;
	private ListView lv_mycollection_subject;
	private Cursor cursor;
	private List<Map<String, Object>> list;
	private List<Subject_Info> list_info;
	private Subject_Info_Adapter adapter;

	public static android.support.v4.app.Fragment getSubjectCollectionFragment() {
		if (subjectCollectionFragment == null) {
			subjectCollectionFragment = new SubjectCollectionFragment();
		}
		return subjectCollectionFragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_mycollection_subject, null,
				false);
		init();
		return view;
	}

	private void init() {
		lv_mycollection_subject = (ListView) view
				.findViewById(R.id.lv_mycollection_subject);
		lv_mycollection_subject.setOnItemClickListener(this);
		CollectionDBHelper helper = new CollectionDBHelper(getActivity());
		String sql = "select * from subject";
		cursor = helper.selectCursor(sql, null);
		list = helper.cursorToList(cursor);
		list_info = new ArrayList<Subject_Info>();
		for (int i = 0; i < list.size(); i++) {
			int id = (Integer) list.get(i).get("id");
			String title = (String) list.get(i).get("title");
			int hotindex = (Integer) list.get(i).get("hotindex");
			String img_url = (String) list.get(i).get("img_url");
			Subject_Info sub = new Subject_Info(id, null, title, img_url, 0, 0,
					hotindex, 0);
			list_info.add(sub);
		}
		adapter = new Subject_Info_Adapter(getActivity(),
				R.layout.item_subject_info);
		adapter.setDatas(list_info);
		lv_mycollection_subject.setAdapter(adapter);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent = new Intent(getActivity(), SubjectDetaliActivity.class);
		intent.putExtra("subject_id", list_info.get(position).getId());
		startActivity(intent);
	}
}
