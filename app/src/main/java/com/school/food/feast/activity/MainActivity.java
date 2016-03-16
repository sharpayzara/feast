package com.school.food.feast.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.school.food.feast.R;
import com.school.food.feast.entity.Order;
import com.school.food.feast.entity.UserOrder;
import com.school.food.feast.fragment.HomeFragment;
import com.school.food.feast.fragment.MineFragment;
import com.school.food.feast.fragment.OrderFragment;
import com.school.food.feast.layout.BottomControlPanel;
import com.school.food.feast.layout.HeadControlPanel;
import com.school.food.feast.util.Constant;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.update.BmobUpdateAgent;

public class MainActivity extends FragmentActivity implements BottomControlPanel.BottomPanelCallback {
    BottomControlPanel bottomPanel = null;
    HeadControlPanel headPanel = null;
    public long exitTime;
    private FragmentManager fragmentManager = null;
    private FragmentTransaction fragmentTransaction = null;

    public static String currFragTag = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        exitTime = System.currentTimeMillis();
        initUI();
        fragmentManager = getSupportFragmentManager();
        setDefaultFirstFragment(Constant.FRAGMENT_FLAG_HOME);
        checkUpdate();
    }

    private void checkUpdate() {
        BmobUpdateAgent.update(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
    private void initUI(){
        bottomPanel = (BottomControlPanel)findViewById(R.id.bottom_layout);
        if(bottomPanel != null){
            bottomPanel.initBottomPanel();
            bottomPanel.setBottomCallback(this);
        }
        headPanel = (HeadControlPanel)findViewById(R.id.head_layout);
        if(headPanel != null){
            headPanel.initHeadPanel();
        }
    }

    /*
     * 处理BottomControlPanel的回调
     */
    @Override
    public void onBottomPanelClick(int itemId) {
        // TODO Auto-generated method stub
        String tag = "";
        if((itemId & Constant.BTN_FLAG_HOME) != 0){
            tag = Constant.FRAGMENT_FLAG_HOME;
        }else if((itemId & Constant.BTN_FLAG_ORDER) != 0){
            tag = Constant.FRAGMENT_FLAG_ORDER;
        }else if((itemId & Constant.BTN_FLAG_MINE) != 0){
            tag = Constant.FRAGMENT_FLAG_MINE;
        }
        setTabSelection(tag); //切换Fragment
        headPanel.setMiddleTitle(tag);//切换标题
    }

    private void setDefaultFirstFragment(String tag){
        Log.i("yan", "setDefaultFirstFragment enter... currFragTag = " + currFragTag);
        setTabSelection(tag);
        bottomPanel.defaultBtnChecked();
        Log.i("yan", "setDefaultFirstFragment exit...");
    }

    private void commitTransactions(String tag){
        if (fragmentTransaction != null && !fragmentTransaction.isEmpty()) {
            fragmentTransaction.commit();
            currFragTag = tag;
            fragmentTransaction = null;
        }
    }

    private FragmentTransaction ensureTransaction(){
        if(fragmentTransaction == null){
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

        }
        return fragmentTransaction;

    }

    private void attachFragment(int layout, Fragment f, String tag){
        if(f != null){
            if(f.isDetached()){
                ensureTransaction();
                fragmentTransaction.attach(f);

            }else if(!f.isAdded()){
                ensureTransaction();
                fragmentTransaction.add(layout, f, tag);
            }
        }
    }

    private Fragment getFragment(String tag){

        Fragment f = fragmentManager.findFragmentByTag(tag);

        if(f == null){
            if(tag.equals(Constant.FRAGMENT_FLAG_HOME)){
                f = new HomeFragment();
            }else if(tag.equals(Constant.FRAGMENT_FLAG_ORDER)){
                f = new OrderFragment();
            }else if(tag.equals(Constant.FRAGMENT_FLAG_MINE)){
                f = new MineFragment();
            }
        }
        return f;

    }
    private void detachFragment(Fragment f){

        if(f != null && !f.isDetached()){
            ensureTransaction();
            fragmentTransaction.detach(f);
        }
    }
    /**切换fragment
     * @param tag
     */
    private  void switchFragment(String tag){
        if(TextUtils.equals(tag, currFragTag)){
            return;
        }
        if(tag.equals(Constant.FRAGMENT_FLAG_HOME)){
            headPanel.setTitlePanelColor(Color.rgb(254, 77, 61));
            headPanel.setTitlePanelTextColor(Color.rgb(255, 255, 255));
        }else if(tag.equals(Constant.FRAGMENT_FLAG_ORDER)){
            headPanel.setTitlePanelColor(Color.rgb(255, 255, 255));
            headPanel.setTitlePanelTextColor(Color.rgb(0, 0, 0));
        }else if(tag.equals(Constant.FRAGMENT_FLAG_MINE)){
            headPanel.setTitlePanelColor(Color.rgb(254,77,61));
            headPanel.setTitlePanelTextColor(Color.rgb(255, 255, 255));
        }
        headPanel.getmRightTitle().setVisibility(View.INVISIBLE);
        //把上一个fragment detach掉
        if(currFragTag != null && !currFragTag.equals("")){
            detachFragment(getFragment(currFragTag));
        }
        attachFragment(R.id.fragment_content, getFragment(tag), tag);
       /* if(currFragTag == null || currFragTag.equals("")){
            fragmentTransaction.add(R.id.fragment_content, getFragment(tag), tag);
        }else{
            if (!getFragment(tag).isAdded()) { // 先判断是否被add过
                fragmentTransaction.hide(getFragment(currFragTag)).add(R.id.fragment_content, getFragment(tag),tag); // 隐藏当前的fragment，add下一个到Activity中
            } else {
                fragmentTransaction.hide(getFragment(currFragTag)).show(getFragment(tag)); // 隐藏当前的fragment，显示下一个
            }
        }*/

      //  mContent = to;
        commitTransactions( tag);
    }

    /**设置选中的Tag
     * @param tag
     */
    public  void setTabSelection(String tag) {
        // 开启一个Fragment事务
        fragmentTransaction = fragmentManager.beginTransaction();
        switchFragment(tag);

    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment f = fragmentManager.findFragmentByTag(currFragTag);
        /*然后在碎片中调用重写的onActivityResult方法*/
        f.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
