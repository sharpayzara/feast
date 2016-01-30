package com.school.food.feast.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.school.food.feast.R;
import com.school.food.feast.activity.base.CommonHeadPanelActivity;
import com.school.food.feast.entity.BusinessEntity;
import com.school.food.feast.entity.Dish;
import com.school.food.feast.entity.Menu;
import com.school.food.feast.entity.MenuType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class ChannelActivity extends CommonHeadPanelActivity implements View.OnClickListener{
    ImageView cxm_iv,dxm_iv,llx_iv,xp_iv,hls_iv,lgj_iv,hzd_iv
            ,hzw_iv,yyys_iv,hhmc_iv,gg_iv,bfg_iv,hhlr_iv,xael_iv,ysys_iv, hgd_iv,ky_iv;
    private Context mContext;
    private Menu menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_channel);
        super.onCreate(savedInstanceState);
        mContext = this;
        initUI();
    }

    private void initUI() {
        setHeadTitle("商家选择");
        showBackBtn();
        cxm_iv = (ImageView) findViewById(R.id.cxm_iv);
        cxm_iv.setTag(new BusinessEntity(1,"丑小面"));
        dxm_iv = (ImageView) findViewById(R.id.dxm_iv);
        dxm_iv.setTag(new BusinessEntity(2,"刀削面"));
        llx_iv = (ImageView) findViewById(R.id.llx_iv);
        llx_iv.setTag(new BusinessEntity(3,"粒粒香"));
        xp_iv = (ImageView) findViewById(R.id.xp_iv);
        xp_iv.setTag(new BusinessEntity(4,"小铺"));
        hls_iv = (ImageView) findViewById(R.id.hls_iv);
        hls_iv.setTag(new BusinessEntity(5,"华莱仕"));
        lgj_iv = (ImageView) findViewById(R.id.lgj_iv);
        lgj_iv.setTag(new BusinessEntity(6,"老顾家"));
        hzd_iv = (ImageView) findViewById(R.id.hzd_iv);
        hzd_iv.setTag(new BusinessEntity(7,"好粥道"));
        yyys_iv = (ImageView) findViewById(R.id.yyys_iv);
        yyys_iv.setTag(new BusinessEntity(8,"渝友意思"));
        hzw_iv = (ImageView) findViewById(R.id.hzw_iv);
        hzw_iv.setTag(new BusinessEntity(9,"好知味"));
        hhmc_iv = (ImageView) findViewById(R.id.hhmc_iv);
        hhmc_iv.setTag(new BusinessEntity(10,"哈哈帽菜"));
        gg_iv = (ImageView) findViewById(R.id.gg_iv);
        gg_iv.setTag(new BusinessEntity(11,"干锅"));
        bfg_iv = (ImageView) findViewById(R.id.bfg_iv);
        gg_iv.setTag(new BusinessEntity(12,"避风港"));
        hhlr_iv = (ImageView) findViewById(R.id.hhlr_iv);
        hhlr_iv.setTag(new BusinessEntity(13,"回味卤肉"));
        xael_iv = (ImageView) findViewById(R.id.xael_iv);
        xael_iv.setTag(new BusinessEntity(14,"新奥尔良"));
        ysys_iv = (ImageView) findViewById(R.id.ysys_iv);
        ysys_iv.setTag(new BusinessEntity(15,"一勺一勺"));
        hgd_iv = (ImageView) findViewById(R.id.hgd_iv);
        hgd_iv.setTag(new BusinessEntity(16,"火锅店"));
        ky_iv = (ImageView) findViewById(R.id.ky_iv);
        ky_iv.setTag(new BusinessEntity(17,"烤鱼"));
        cxm_iv.setOnClickListener(this);
        dxm_iv.setOnClickListener(this);
        llx_iv.setOnClickListener(this);
        xp_iv.setOnClickListener(this);
        hls_iv.setOnClickListener(this);
        lgj_iv.setOnClickListener(this);
        hzd_iv.setOnClickListener(this);
        hzw_iv.setOnClickListener(this);
        yyys_iv.setOnClickListener(this);
        hhmc_iv.setOnClickListener(this);
        gg_iv.setOnClickListener(this);
        bfg_iv.setOnClickListener(this);
        hhlr_iv.setOnClickListener(this);
        xael_iv.setOnClickListener(this);
        ysys_iv.setOnClickListener(this);
        hgd_iv.setOnClickListener(this);
        ky_iv.setOnClickListener(this);
    }

    @Override
    public void onClick(final View v) {
        Intent intent = new Intent(ChannelActivity.this,MenuChooseActivity.class);
        intent.putExtra("chooseEntity",(Serializable) v.getTag());
        startActivity(intent);


      /*  final Menu menu = new Menu();
        menu.setSeqId(1);
        menu.setBusinessName("丑小面");
        MenuType type = new MenuType();
        type.setTypeName("小面");
        Dish dish = new Dish();
        dish.setDishName("杂酱面");
        dish.setDishValue(12.5);
        List list2 = new ArrayList<Dish>();
        list2.add(dish);

        type.setDish(list2);
        List list1 = new ArrayList<MenuType>();
        list1.add(type);

        menu.setMenuType(list1);
        menu.save(this, new SaveListener() {
            @Override
            public void onSuccess() {
                Log.e("success", "success");
            }

            @Override
            public void onFailure(int i, String s) {
                Log.e("fail", "fail");
            }
        });*/
    }
}
