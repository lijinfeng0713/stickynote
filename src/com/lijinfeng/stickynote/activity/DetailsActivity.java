package com.lijinfeng.stickynote.activity;

import com.lijinfeng.stickynote.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class DetailsActivity extends Activity implements OnClickListener{

	private Button btnBack=null;
	private Button btnDetailsConfirm=null;
	private TextView detailsTime;
	private EditText detailsTitle;
	private EditText detailsContent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.details);
		
		btnBack = (Button)findViewById(R.id.btn_back);
		//btnConfirm = (Button)findViewById(R.id.details_btn_confirm);
		btnDetailsConfirm = (Button)findViewById(R.id.details_confirm);
		detailsTime = (TextView)findViewById(R.id.details_time);
		detailsTitle = (EditText)findViewById(R.id.ed_details_title);
		detailsContent = (EditText)findViewById(R.id.ed_details_content);
		
		btnBack.setOnClickListener(this);
		btnDetailsConfirm.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back:
			super.onBackPressed();
			break;

		default:
			break;
		}
	}
}
