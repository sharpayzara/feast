package com.school.food.feast.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import com.school.food.feast.R;
import com.school.food.feast.activity.base.CommonHeadPanelActivity;
import com.school.food.feast.adapter.MenuSectionedAdapter;
import com.school.food.feast.adapter.MenuTypeListAdapter;
import com.school.food.feast.entity.BusinessEntity;
import com.school.food.feast.entity.Dish;
import com.school.food.feast.entity.Menu;
import com.school.food.feast.entity.MenuType;
import com.school.food.feast.entity.PreOrder;
import com.school.food.feast.widget.PinnedHeaderListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

public class MenuChooseActivity extends CommonHeadPanelActivity implements View.OnClickListener{

    private boolean isScroll = true;
    private ListView left_listView;
    private BusinessEntity chooseBusiness;
    private Menu menu;
    MenuSectionedAdapter sectionedAdapter;
    private List<MenuType> menuTypeList;
    private List leftList;
    private Map<Integer,List<Dish>> rightMap;
    private Map<String,Map<Integer,PreOrder>> preOrderMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_menu);
        super.onCreate(savedInstanceState);
        chooseBusiness = (BusinessEntity) getIntent().getSerializableExtra("chooseEntity");
        initUI();
        initData();
    }

    private void initData() {
        BmobQuery<Menu> bmobQuery = new BmobQuery<Menu>();
        bmobQuery.addWhereEqualTo("seqId",chooseBusiness.getId());
        bmobQuery.findObjects(this, new FindListener<Menu>() {
            @Override
            public void onSuccess(List<Menu> list) {
                if(list.size() > 0){
                    menu = list.get(0);
                    menuTypeList = menu.getMenuType();
                    for(int i = 0;i < menuTypeList.size(); i++){
                        leftList.add(menuTypeList.get(i).getTypeName());
                        rightMap.put(i,menuTypeList.get(i).getDish());
                    }
                    sectionedAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    private void initUI() {
        chooseBusiness = (BusinessEntity) getIntent().getSerializableExtra("chooseEntity");
        setHeadTitle(chooseBusiness.getName());
        showBackBtn();
        leftList = new ArrayList();
        rightMap = new HashMap<>();
        preOrderMap = new HashMap<>();
        final PinnedHeaderListView right_listview = (PinnedHeaderListView) findViewById(R.id.pinnedListView);
        LayoutInflater inflator = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        sectionedAdapter = new MenuSectionedAdapter(this, leftList, rightMap,preOrderMap);
        right_listview.setAdapter(sectionedAdapter);
        left_listView = (ListView) findViewById(R.id.left_listview);
        left_listView.setAdapter(new MenuTypeListAdapter(this,leftList));
        left_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int position,long arg3) {
                isScroll = false;
                for (int i = 0; i < left_listView.getChildCount(); i++)
                {
                    if (i == position)
                    {
                        left_listView.getChildAt(i).setBackgroundColor(Color.rgb(255, 255, 255));
                    } else
                    {
                        left_listView.getChildAt(i).setBackgroundColor(getResources().getColor(R.color.menu_type_color));
                    }
                }

                int rightSection = 0;
                for(int i=0;i<position;i++){
                    rightSection += sectionedAdapter.getCountForSection(i)+1;
                }
                right_listview.setSelection(rightSection);

            }

        });

        right_listview.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView arg0, int arg1) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                if(isScroll){
                    for (int i = 0; i < left_listView.getChildCount(); i++)
                    {

                        if (i == sectionedAdapter.getSectionForPosition(firstVisibleItem))
                        {
                            left_listView.getChildAt(i).setBackgroundColor(
                                    Color.rgb(255, 255, 255));
                        } else
                        {
                            left_listView.getChildAt(i).setBackgroundColor(getResources().getColor(R.color.menu_type_color));

                        }
                    }

                }else{
                    isScroll = true;
                }
            }
        });
    }

    @Override
    public void onClick(View v) {

    }

}
