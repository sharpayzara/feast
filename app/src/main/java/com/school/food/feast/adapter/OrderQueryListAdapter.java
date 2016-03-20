package com.school.food.feast.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.school.food.feast.R;
import com.school.food.feast.entity.Order;
import com.school.food.feast.entity.User;
import com.school.food.feast.entity.UserOrder;
import com.school.food.feast.fragment.ReflushListener;
import com.school.food.feast.services.UserServices;

import java.sql.Ref;
import java.util.List;

import cn.bmob.v3.listener.UpdateListener;


public class OrderQueryListAdapter extends RecyclerView.Adapter<OrderQueryListAdapter.OrderHolder>{
	Context mContext;
	List<Order> list;
	private boolean isUsed = true;
	ReflushListener listener;

	public OrderQueryListAdapter(Context context, List<Order> list, boolean isUsed, ReflushListener listener) {
		mContext = context;
		this.list = list;
		this.isUsed = isUsed;
		this.listener = listener;
	}

	@Override
	public OrderHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		OrderHolder holder = new OrderHolder(LayoutInflater.from(mContext).inflate(R.layout.order_query_item, parent,
				false));
		return holder;
	}

	@Override
	public void onBindViewHolder(final OrderHolder holder, final int position) {
		holder.totalMoney.setText(list.get(position).getTotalMoney()+"元");
		holder.factTotalMoney.setText(list.get(position).getFactTotalMoney()+"元");
		holder.orderNum.setText(list.get(position).getOrderNum());
		holder.orderTime.setText(list.get(position).getOrderData());
		holder.businessName.setText(list.get(position).getBusinessName());
		holder.unreg.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(mContext).setTitle("退订后的金额会转移到账户余额，确认退订吗？")
						.setIcon(android.R.drawable.ic_dialog_info)
						.setPositiveButton("退订", new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								UserOrder userOrder = new UserOrder();
								userOrder.setUse(true);
								userOrder.update(mContext, list.get(position).getObjectId(), new UpdateListener() {
									@Override
									public void onSuccess() {
										updateAccount(list.get(position).getFactTotalMoney());
										OrderQueryListAdapter.this.notifyDataSetChanged();
										listener.reflush();
									}

									@Override
									public void onFailure(int i, String s) {
										Toast.makeText(mContext, "退款失败，请稍后再试", Toast.LENGTH_SHORT).show();
									}
								});

							}
						})
						.setNegativeButton("返回", new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								// 点击“返回”后的操作,这里不设置没有任何操作
							}
						}).show();
			}
		});
	}

	public void updateAccount(String factTotalMoney){
		final User user = new User();
		user.setAccountMoney(UserServices.getUser(mContext).getAccountMoney() + Double.parseDouble(factTotalMoney));
		String userObjectId = UserServices.getUser(mContext).getObjectId();
		user.update(mContext, userObjectId, new UpdateListener() {

			@Override
			public void onSuccess() {
				Toast.makeText(mContext, "退款成功", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onFailure(int code, String msg) {
			}
		});
	}

	@Override
	public int getItemCount() {
		return list.size();
	}

	class OrderHolder extends RecyclerView.ViewHolder{
		TextView totalMoney,factTotalMoney;
		TextView orderNum;
		TextView businessName;
		TextView orderTime;
		Button unreg;
		public OrderHolder(View view){
			super(view);
			totalMoney = (TextView) view.findViewById(R.id.total_money);
			factTotalMoney = (TextView) view.findViewById(R.id.fact_total_money);
			orderNum = (TextView) view.findViewById(R.id.order_num);
			businessName = (TextView) view.findViewById(R.id.business_name);
			orderTime = (TextView) view.findViewById(R.id.order_time);
			unreg = (Button) view.findViewById(R.id.unreg);
			if(isUsed){
				unreg.setVisibility(View.GONE);
			}
		}
	}
}