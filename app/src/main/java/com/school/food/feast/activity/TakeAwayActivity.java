package com.school.food.feast.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.school.food.feast.R;
import com.school.food.feast.activity.base.CommonHeadPanelActivity;
import com.school.food.feast.entity.TakeAwayPhone;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

public class TakeAwayActivity extends CommonHeadPanelActivity implements View.OnClickListener{
    RelativeLayout cxm_layout,dxm_layout,llx_layout,xp_layout,hls_layout,lgj_layout,hzd_layout
            ,hzw_layout,yyys_layout,hhmc_layout,gg_layout,bfg_layout,hhlr_layout,xael_layout,ysys_layout, hgd_layout,ky_layout;

    List<TakeAwayPhone> phoneList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_waimai);
        super.onCreate(savedInstanceState);
        phoneList = new ArrayList<TakeAwayPhone>();
        initUi();
        queryPhones();
    }

    private void initUi() {
        showBackBtn();
        setHeadTitle("曹操外卖");
        cxm_layout = (RelativeLayout) findViewById(R.id.cxm_layout);
        cxm_layout.setOnClickListener(this);
        dxm_layout = (RelativeLayout) findViewById(R.id.dxm_layout);
        dxm_layout.setOnClickListener(this);
        llx_layout = (RelativeLayout) findViewById(R.id.llx_layout);
        llx_layout.setOnClickListener(this);
        xp_layout = (RelativeLayout) findViewById(R.id.xp_layout);
        xp_layout.setOnClickListener(this);
        hls_layout = (RelativeLayout) findViewById(R.id.hls_layout);
        hls_layout.setOnClickListener(this);
        lgj_layout = (RelativeLayout) findViewById(R.id.lgj_layout);
        lgj_layout.setOnClickListener(this);
        hzd_layout = (RelativeLayout) findViewById(R.id.hzd_layout);
        hzd_layout.setOnClickListener(this);
        hzw_layout = (RelativeLayout) findViewById(R.id.hzw_layout);
        hzw_layout.setOnClickListener(this);
        yyys_layout = (RelativeLayout) findViewById(R.id.yyys_layout);
        yyys_layout.setOnClickListener(this);
        hhmc_layout = (RelativeLayout) findViewById(R.id.hhmc_layout);
        hhmc_layout.setOnClickListener(this);
        gg_layout = (RelativeLayout) findViewById(R.id.gg_layout);
        gg_layout.setOnClickListener(this);
        bfg_layout = (RelativeLayout) findViewById(R.id.bfg_layout);
        bfg_layout.setOnClickListener(this);
        hhlr_layout = (RelativeLayout) findViewById(R.id.hhlr_layout);
        hhlr_layout.setOnClickListener(this);
        xael_layout = (RelativeLayout) findViewById(R.id.xael_layout);
        xael_layout.setOnClickListener(this);
        ysys_layout = (RelativeLayout) findViewById(R.id.ysys_layout);
        ysys_layout.setOnClickListener(this);
        hgd_layout = (RelativeLayout) findViewById(R.id.hgd_layout);
        hgd_layout.setOnClickListener(this);
        ky_layout = (RelativeLayout) findViewById(R.id.ky_layout);
        ky_layout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(phoneList.size() >= 17) {
            switch (v.getId()) {
                case R.id.cxm_layout:
                    testinsertObject(phoneList.get(0).getPhoneNum());
                    break;
                case R.id.dxm_layout:
                    testinsertObject(phoneList.get(1).getPhoneNum());
                    break;
                case R.id.llx_layout:
                    testinsertObject(phoneList.get(2).getPhoneNum());
                    break;
                case R.id.xp_layout:
                    testinsertObject(phoneList.get(3).getPhoneNum());
                    break;
                case R.id.hls_layout:
                    testinsertObject(phoneList.get(4).getPhoneNum());
                    break;
                case R.id.lgj_layout:
                    testinsertObject(phoneList.get(5).getPhoneNum());
                    break;
                case R.id.hzd_layout:
                    testinsertObject(phoneList.get(6).getPhoneNum());
                    break;
                case R.id.hzw_layout:
                    testinsertObject(phoneList.get(7).getPhoneNum());
                    break;
                case R.id.yyys_layout:
                    testinsertObject(phoneList.get(8).getPhoneNum());
                    break;
                case R.id.hhmc_layout:
                    testinsertObject(phoneList.get(9).getPhoneNum());
                    break;
                case R.id.gg_layout:
                    testinsertObject(phoneList.get(10).getPhoneNum());
                    break;
                case R.id.bfg_layout:
                    testinsertObject(phoneList.get(11).getPhoneNum());
                    break;
                case R.id.hhlr_layout:
                    testinsertObject(phoneList.get(12).getPhoneNum());
                    break;
                case R.id.xael_layout:
                    testinsertObject(phoneList.get(13).getPhoneNum());
                    break;
                case R.id.ysys_layout:
                    testinsertObject(phoneList.get(14).getPhoneNum());
                    break;
                case R.id.hgd_layout:
                    testinsertObject(phoneList.get(15).getPhoneNum());
                    break;
                case R.id.ky_layout:
                    testinsertObject(phoneList.get(16).getPhoneNum());
                    break;
            }
        }
    }

    private void testinsertObject(String phoneNum) {
        Intent in2 = new Intent();
        in2.setAction(Intent.ACTION_DIAL);
        String tel = "tel:" + phoneNum;
        in2.setData(Uri.parse(tel));
        startActivity(in2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 0){
            Toast.makeText(this,"获取拨打电话权限失败，请在设置里打开通话权限",Toast.LENGTH_LONG).show();
        }
    }

    private void queryPhones(){
        final BmobQuery<TakeAwayPhone> bmobQuery = new BmobQuery<TakeAwayPhone>();
        bmobQuery.order("seqId");
        //先判断是否有缓存
        boolean isCache = bmobQuery.hasCachedResult(TakeAwayActivity.this,TakeAwayPhone.class);
        if(isCache){
            bmobQuery.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);	// 先从缓存取数据，如果没有的话，再从网络取。
        }else{
            bmobQuery.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);	// 如果没有缓存的话，则先从网络中取
        }
        bmobQuery.findObjects(this, new FindListener<TakeAwayPhone>() {

            @Override
            public void onSuccess(List<TakeAwayPhone> object) {
                // TODO Auto-generated method stub
                phoneList.addAll(object);
            }

            @Override
            public void onError(int code, String msg) {
                // TODO Auto-generated method stub
                toast("网络获取失败，请稍候再试");
            }
        });
    }
}
