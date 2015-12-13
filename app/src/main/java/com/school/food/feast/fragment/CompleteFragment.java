package com.school.food.feast.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.school.food.feast.R;

public class CompleteFragment extends Fragment {

	private TextView tv_text;
	private Button login_btn;
	private View root;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		root  = inflater.inflate(R.layout.unlogin_fragment, null);
		return root;
	}

}
