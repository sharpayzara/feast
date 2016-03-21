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
import com.school.food.feast.entity.UserAccount;
import com.school.food.feast.entity.UserOrder;
import com.school.food.feast.fragment.ReflushListener;
import com.school.food.feast.services.UserServices;

import java.sql.Ref;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.UpdateListener;


public class OrderQueryListAdapter extends RecyclerView.Adapter<OrderQueryListAdapter.OrderHolder>{
	Context mContext;
	List<Order> list;
	private boolean isUsed = true;
	ReflushListener listener;
	boolean isCanTD = true;

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

	private void checkIsTD(final int position){
		BmobQuery<UserOrder> b1 = new BmobQuery<UserOrder>();
		b1.addWhereEqualTo("objectId",list.get(position).getObjectId());
		BmobQuery<UserOrder> b2 = new BmobQuery<UserOrder>();
		b2.addWhereEqualTo("isUnReg", false);
		BmobQuery<UserOrder> b3 = new BmobQuery<UserOrder>();
		b3.addWhereEqualTo("isUse", false);
		List<BmobQuery<UserOrder>> queries = new ArrayList<BmobQuery<UserOrder>>();
		queries.add(b1);
	 	queries.add(b2);
		queries.add(b3);
		BmobQuery<UserOrder> query = new BmobQuery<UserOrder>();
		query.and(queries);
		query.findObjects(mContext, new FindListener<UserOrder>() {
			@Override
			public void onSuccess(final List<UserOrder> lists) {
				if(lists.size() == 0){
					Toast.makeText(mContext,"订单异常，请稍后再试",Toast.LENGTH_SHORT).show();
					isCanTD = true;
					return ;
				}

				UserOrder userOrder = new UserOrder();
				userOrder.setUnReg(true);
				userOrder.update(mContext, lists.get(0).getObjectId(), new UpdateListener() {
					@Override
					public void onSuccess() {
						updateAccount(lists.get(0).getFactTotalMoney());
					}

					@Override
					public void onFailure(int i, String s) {
						isCanTD = true;
						Toast.makeText(mContext, "退款失败，请稍后再试", Toast.LENGTH_SHORT).show();
					}
				});
			}

			@Override
			public void onError(int i, String s) {
				Toast.makeText(mContext,"订单异常，请稍后再试",Toast.LENGTH_SHORT).show();
				isCanTD = true;
				return ;
			}
		});

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
								if(!isCanTD){
									return;
								}
								isCanTD = false;
								checkIsTD(position);
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

	/*public void updateAccount(String factTotalMoney){
		final User user = new User();
		user.setAccountMoney(UserServices.getUser(mContext).getAccountMoney() + Double.parseDouble(factTotalMoney));
		String userObjectId = UserServices.getUser(mContext).getObjectId();
		user.update(mContext, userObjectId, new UpdateListener() {

			@Override
			public void onSuccess() {
				isCanTD = true;
				listener.reflush();
			}

			@Override
			public void onFailure(int code, String msg) {
				isCanTD = true;
			}
		});
	}*/

	public void updateAccount(final String factTotalMoney){
		BmobQuery<UserAccount> bmobQuery = new BmobQuery<UserAccount>();
		bmobQuery.addWhereEqualTo("userPhone", BmobUser.getCurrentUser(mContext).getMobilePhoneNumber());
		bmobQuery.findObjects(mContext, new FindListener<UserAccount>() {
			@Override
			public void onSuccess(List<UserAccount> list) {
				if(list.size() > 0){
					updateUserAccount(list.get(0),factTotalMoney);
				}
			}

			@Override
			public void onError(int i, String s) {
				Toast.makeText(mContext, "获取数据失败，请稍后再试", Toast.LENGTH_SHORT).show();
			}
		});
	}
	public void updateUserAccount(UserAccount userAccount,String factTotalMoney){
		final UserAccount account = new UserAccount();
		account.setAccountMoney(userAccount.getAccountMoney() + Double.parseDouble(factTotalMoney));
		account.update(mContext, userAccount.getObjectId(), new UpdateListener() {
			@Override
			public void onSuccess() {
				isCanTD = true;
				listener.reflush();
			}

			@Override
			public void onFailure(int i, String s) {
				isCanTD = true;
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