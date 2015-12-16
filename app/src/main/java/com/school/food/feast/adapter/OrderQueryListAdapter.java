package com.school.food.feast.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.school.food.feast.R;
import com.school.food.feast.entity.Order;

import java.util.List;


public class OrderQueryListAdapter extends RecyclerView.Adapter<OrderQueryListAdapter.OrderHolder>{
	Context mContext;
	List<Order> list;
	private boolean isUsed = true;

	public OrderQueryListAdapter(Context context,List<Order> list,boolean isUsed) {
		mContext = context;
		this.list = list;
		this.isUsed = isUsed;
	}

	@Override
	public OrderHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		OrderHolder holder = new OrderHolder(LayoutInflater.from(mContext).inflate(R.layout.order_query_item, parent,
				false));
		return holder;
	}

	@Override
	public void onBindViewHolder(OrderHolder holder, int position) {
		holder.totalMoney.setText(list.get(position).getTotalMoney());
		holder.orderNum.setText(list.get(position).getOrderNum());
		holder.dishesDetail.setText(list.get(position).getDishesDetail());
		holder.orderTime.setText(list.get(position).getCreatedAt());
	}

	@Override
	public int getItemCount() {
		return list.size();
	}

	class OrderHolder extends RecyclerView.ViewHolder{
		TextView totalMoney;
		TextView orderNum;
		TextView dishesDetail;
		TextView orderTime;
		Button printOrder;
		public OrderHolder(View view){
			super(view);
			totalMoney = (TextView) view.findViewById(R.id.total_money);
			orderNum = (TextView) view.findViewById(R.id.order_num);
			dishesDetail = (TextView) view.findViewById(R.id.dishes_detail);
			orderTime = (TextView) view.findViewById(R.id.order_time);
			printOrder = (Button) view.findViewById(R.id.print_order);
			if(isUsed){
				printOrder.setVisibility(View.GONE);
			}
		}
	}
}
