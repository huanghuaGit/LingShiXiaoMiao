package com.qf.lingshixiaomaio.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 单类型万能适配器
 * @author JoshuaJan
 *
 * @param <T>
 */
public abstract class MyBaseAdapterSingle<T> extends BaseAdapter {

	private Context context;
	private List<T> datas;
	private int resId;
	
	public MyBaseAdapterSingle(Context context,int resId){
		this.context=context;
		this.resId=resId;
		datas=new ArrayList<T>();
	}
	public void setDatas(List<T> list){
		this.datas=list;
		notifyDataSetChanged();
	}
	public void addDatas(List<T> list){
		this.datas.addAll(list);
		notifyDataSetChanged();
	}
	
	public void clearDatas(){
		this.datas.clear();
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		return datas.size();
	}

	@Override
	public Object getItem(int position) {
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder vh = null;
		if(convertView != null){
			vh = (ViewHolder)convertView.getTag();
		} else {
			convertView = View.inflate(context, resId, null);
			vh = new ViewHolder(convertView);
			convertView.setTag(vh);
		}
		//将数据设置到控件中
		bindData(vh, datas.get(position));
		return convertView;
	}
	
	/**
	 * 子类必须实现方法，将数据设置到控件中
	 * @param vh
	 * @param data
	 */
	public abstract void bindData(ViewHolder vh,T data);
	
	public class ViewHolder{
		private Map<Integer, View> map = new HashMap<Integer, View>();
		private View layoutView;
		ViewHolder(View layoutView){
			this.layoutView=layoutView;
		}
		
		public View getView(int id){
			View view = null;
			if (map.containsKey(id)) {
				view=map.get(id);
			}else {
				view=layoutView.findViewById(id);
				map.put(id, view);
			}
			return view;
		}
	}

}
