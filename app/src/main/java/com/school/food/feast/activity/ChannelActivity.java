package com.school.food.feast.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.school.food.feast.R;
import com.school.food.feast.activity.base.CommonHeadPanelActivity;

public class ChannelActivity extends CommonHeadPanelActivity implements View.OnClickListener{
    ImageView cxm_iv,dxm_iv,llx_iv,xp_iv,hls_iv,lgj_iv,hzd_iv
            ,hzw_iv,yyys_iv,hhmc_iv,gg_iv,bfg_iv,hhlr_iv,xael_iv,ysys_iv, hgd_iv,ky_iv;
    private Context mContext;
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
        dxm_iv = (ImageView) findViewById(R.id.dxm_iv);
        llx_iv = (ImageView) findViewById(R.id.llx_iv);
        xp_iv = (ImageView) findViewById(R.id.xp_iv);
        hls_iv = (ImageView) findViewById(R.id.hls_iv);
        lgj_iv = (ImageView) findViewById(R.id.lgj_iv);
        hzd_iv = (ImageView) findViewById(R.id.hzd_iv);
        hzw_iv = (ImageView) findViewById(R.id.hzw_iv);
        yyys_iv = (ImageView) findViewById(R.id.yyys_iv);
        hhmc_iv = (ImageView) findViewById(R.id.hhmc_iv);
        gg_iv = (ImageView) findViewById(R.id.gg_iv);
        bfg_iv = (ImageView) findViewById(R.id.bfg_iv);
        hhlr_iv = (ImageView) findViewById(R.id.hhlr_iv);
        xael_iv = (ImageView) findViewById(R.id.xael_iv);
        ysys_iv = (ImageView) findViewById(R.id.ysys_iv);
        hgd_iv = (ImageView) findViewById(R.id.hgd_iv);
        ky_iv = (ImageView) findViewById(R.id.ky_iv);
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
    public void onClick(View v) {

    }
}
