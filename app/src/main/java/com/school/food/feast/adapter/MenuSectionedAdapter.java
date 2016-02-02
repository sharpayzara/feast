package com.school.food.feast.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.school.food.feast.R;
import com.school.food.feast.activity.MenuChooseActivity;
import com.school.food.feast.entity.Dish;
import com.school.food.feast.entity.PreOrder;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuSectionedAdapter extends SectionedBaseAdapter {

    private Context mContext;
    private List<String> leftList;
    private Map<Integer,List<Dish>> rightMap;
    private Map<String,Map<Integer,PreOrder>> preOrderMap;

    public MenuSectionedAdapter(Context context, List<String> leftList,Map<Integer,List<Dish>> rightMap,Map<String,Map<Integer,PreOrder>> preOrderMap){
        this.mContext = context;
        this.leftList = leftList;
        this.rightMap = rightMap;
        this.preOrderMap = preOrderMap;
    }

    @Override
    public Object getItem(int section, int position) {
        return rightMap.get(section).get(position);
    }

    @Override
    public long getItemId(int section, int position) {
        return position;
    }

    @Override
    public int getSectionCount() {
        return leftList.size();
    }

    @Override
    public int getCountForSection(int section) {
        return rightMap.get(section).size();
    }

    @Override
    public View getItemView(final int section, final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflator = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView =  inflator.inflate(R.layout.menu_list_item, null);
            viewHolder = new ViewHolder();
            viewHolder.add_tv = (ImageView) convertView.findViewById(R.id.add_tv);
            viewHolder.subtract_tv = (ImageView) convertView.findViewById(R.id.subtract_tv);
            viewHolder.dish_name = (TextView) convertView.findViewById(R.id.dish_name);
            viewHolder.dish_value= (TextView) convertView.findViewById(R.id.dish_value);
            viewHolder.check_num = (TextView) convertView.findViewById(R.id.check_num);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.dish_name.setText(rightMap.get(section).get(position).getDishName());
        viewHolder.dish_value.setText("￥" + rightMap.get(section).get(position).getDishValue()+"");

        if(preOrderMap.containsKey(section+"-"+position) && preOrderMap.get(section+"-"+position).containsKey(position)){
            viewHolder.check_num.setText(preOrderMap.get(section+"-"+position).get(position).getDishNum()+"");
            viewHolder.check_num.setVisibility(View.VISIBLE);
            viewHolder.subtract_tv.setVisibility(View.VISIBLE);
        }else{
            viewHolder.check_num.setText("");
            viewHolder.check_num.setVisibility(View.GONE);
            viewHolder.subtract_tv.setVisibility(View.GONE);
        }
        viewHolder.add_tv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.check_num.setVisibility(View.VISIBLE);
                viewHolder.subtract_tv.setVisibility(View.VISIBLE);
                if(TextUtils.isEmpty(viewHolder.check_num.getText().toString())){
                    viewHolder.check_num.setText(1+"");
                    Map orderMap = new HashMap();
                    PreOrder prder = new PreOrder(rightMap.get(section).get(position).getDishName(),
                            rightMap.get(section).get(position).getDishValue(),
                            Integer.parseInt(viewHolder.check_num.getText().toString()));
                    orderMap.put(position,prder);
                    preOrderMap.put(section+"-"+position,orderMap);
                }else{
                    viewHolder.check_num.setText((Integer.parseInt(viewHolder.check_num.getText().toString())+1)+"");
                    preOrderMap.get(section+"-"+position).get(position).setDishNum(Integer.parseInt(viewHolder.check_num.getText().toString()));
                }
                ((MenuChooseActivity)mContext).calculateTotalMoney();
            }
        });
        viewHolder.subtract_tv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viewHolder.check_num.getText().toString().equals("1")){
                    viewHolder.subtract_tv.setVisibility(View.GONE);
                    viewHolder.check_num.setVisibility(View.GONE);
                    viewHolder.check_num.setText("");
                    preOrderMap.get(section+"-"+position).remove(position);
                }else{
                    viewHolder.check_num.setText((Integer.parseInt(viewHolder.check_num.getText().toString())-1)+"");
                    preOrderMap.get(section+"-"+position).get(position).setDishNum(Integer.parseInt(viewHolder.check_num.getText().toString()));
                }
                ((MenuChooseActivity)mContext).calculateTotalMoney();
            }
        });
        return convertView;
    }

   @Override
    public View getSectionHeaderView(int section, View convertView, ViewGroup parent) {
        LinearLayout layout = null;
        if (convertView == null) {
            LayoutInflater inflator = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = (LinearLayout) inflator.inflate(R.layout.header_item, null);
        } else {
            layout = (LinearLayout) convertView;
        }
        layout.setClickable(false);
        ((TextView) layout.findViewById(R.id.textItem)).setText(leftList.get(section));
        return layout;
    }

    static class ViewHolder{
        TextView dish_name;
        TextView dish_value;
        ImageView subtract_tv;
        ImageView add_tv;
        TextView check_num;
    }

}
