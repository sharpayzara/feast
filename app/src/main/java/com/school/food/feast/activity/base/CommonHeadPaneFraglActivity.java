package com.school.food.feast.activity.base;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.school.food.feast.R;
import com.school.food.feast.layout.HeadControlPanel;

/**
 * Created by Administrator on 2015/12/8.
 */
public class CommonHeadPaneFraglActivity extends FragmentActivity {
    LinearLayout backBtn;
    HeadControlPanel headControlPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        headControlPanel = (HeadControlPanel) findViewById(R.id.head_layout);
        backBtn = (LinearLayout) findViewById(R.id.left_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void setHeadTitle(String msg) {
        headControlPanel.setMiddleTitle(msg);
    }

    public void showBackBtn() {
        backBtn.setVisibility(View.VISIBLE);
    }
}
