package com.qf.lingshixiaomaio.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 多Item的自定义Adapter封装 -- 强制条件 T中一定要有一个名字叫做type的属性
 * @author Ken
 *
 * @param <T> 
 */
public abstract class MyBaseAdapter<T> extends BaseAdapter {
	private Context context;
	private List<T> datas;

	private Map<Integer, Integer> resMap;// type _ R.layout.id
	
	public MyBaseAdapter(Context context, Map<Integer, Integer> resMap){
		this.context = context;
		this.resMap = resMap;
		datas = new ArrayList<T>();
	}
	
	@Override
	public int getItemViewType(int position) {
		T t = datas.get(position);
		Class c = t.getClass();
		int type = 0;
		try {
			Field field = c.getDeclaredField("type");
			field.setAccessible(true);
			type = Integer.parseInt("" + field.get(t));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return type;
	}

	@Override
	public int getViewTypeCount() {
		return resMap.size();
	}

	public void setDatas(List<T> datas){
		this.datas = datas;
		notifyDataSetChanged();
	}
	
	public void addDatas(List<T> datas){
		this.datas.addAll(datas);
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
			convertView = View.inflate(context, resMap.get(getItemViewType(position)), null);
			vh = new ViewHolder(convertView);
			convertView.setTag(vh);
		}
		
		//设置数据到布局控件中
		bindData(vh, datas.get(position));
		return convertView;
	}
	
	public abstract void bindData(ViewHolder vh, T data);
	
	class ViewHolder{
		private Map<Integer, View> map = new HashMap<Integer, View>();
		private View layoutView;
		ViewHolder(View layoutView){
			this.layoutView = layoutView;
		}
		
		public View getView(int id){
			View sView = null;
			if(map.containsKey(id)){
				sView = map.get(id);
			} else {
				sView = layoutView.findViewById(id);
				map.put(id, sView);
			}
			return sView;
		}
	}
}
