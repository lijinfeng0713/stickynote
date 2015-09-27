package com.lijinfeng.stickynote.activity;

import com.lijinfeng.stickynote.R;
import com.lijinfeng.stickynote.dao.NoteDao;
import com.lijinfeng.stickynote.model.Note;

import android.app.Activity;
import android.content.Intent;
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
	private NoteDao noteDao;
	private int id;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.details);
		
		//从布局中获取控件
		btnBack = (Button)findViewById(R.id.btn_back);
		//btnConfirm = (Button)findViewById(R.id.details_btn_confirm);
		btnDetailsConfirm = (Button)findViewById(R.id.details_confirm);
		detailsTime = (TextView)findViewById(R.id.details_time);
		detailsTitle = (EditText)findViewById(R.id.ed_details_title);
		detailsContent = (EditText)findViewById(R.id.ed_details_content);
		
		//将按钮加入到监听事件中
		btnBack.setOnClickListener(this);
		btnDetailsConfirm.setOnClickListener(this);
		
		Intent getIntent = getIntent();
		id = Integer.parseInt(getIntent.getStringExtra("detailsId"));
		noteDao = new NoteDao (this);
		
		//根据id显示便利贴详情
		detailsTime.setText(noteDao.findNote(id).getTime());
		detailsTitle.setText(noteDao.findNote(id).getTitle());
		detailsContent.setText(noteDao.findNote(id).getContent());
		
		
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back:
			super.onBackPressed();
			break;
		case R.id.details_confirm:
			Note note = noteDao.findNote(id);
			note.setTitle(detailsTitle.getText().toString());
			note.setContent(detailsContent.getText().toString());
			noteDao.updateNote(note);
			Intent intent = new Intent();
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.setClass(DetailsActivity.this,MainActivity.class);
			startActivity(intent);
			finish();
			break;	
		default:
			break;
		}
	}
}
