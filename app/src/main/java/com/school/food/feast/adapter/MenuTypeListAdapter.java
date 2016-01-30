package com.school.food.feast.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.school.food.feast.R;
import com.school.food.feast.entity.Order;

import java.util.List;


public class MenuTypeListAdapter extends BaseAdapter {
	private Context mContext;
	private List leftList;

	public MenuTypeListAdapter(Context mContext,List leftList){
		this.mContext = mContext;
		this.leftList = leftList;
	}

	@Override
	public int getCount() {
		return leftList.size();
	}

	@Override
	public Object getItem(int position) {
		return leftList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (null == convertView){
			viewHolder = new ViewHolder();
			LayoutInflater mInflater = LayoutInflater.from(mContext);
			convertView = mInflater.inflate(R.layout.menu_type_item, null);
			viewHolder.textItem = (TextView) convertView.findViewById(R.id.textItem);
			convertView.setTag(viewHolder);
		}
		else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.textItem.setText(leftList.get(position).toString());
		return convertView;
	}

	private static class ViewHolder
	{
		TextView textItem;
	}
}
