package com.lijinfeng.stickynote.activity;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.lijinfeng.stickynote.R;
import com.lijinfeng.stickynote.dao.NoteDao;
import com.lijinfeng.stickynote.model.Note;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class AddNoteActivity extends Activity implements OnClickListener {

	private NoteDao noteDao;
	private Button btnConfirm = null;
	private Button btnCancel = null;
	private EditText editTitle = null;
	private EditText editContent = null;
	private String title;
	private String content;
	private String time;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_note);
		
		//main = new MainActivity();
		
		btnConfirm = (Button)findViewById(R.id.btn_confirm);
		btnCancel = (Button)findViewById(R.id.btn_cancel);
		editTitle = (EditText)findViewById(R.id.ed_title);
		editContent = (EditText)findViewById(R.id.ed_content);
		
		btnConfirm.setOnClickListener(this);
		btnCancel.setOnClickListener(this);

	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_confirm:
			noteDao = new NoteDao (this);
			title = editTitle.getText().toString();
			content = editContent.getText().toString();
			SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd");       
			Date curDate = new Date(System.currentTimeMillis());//获取当前时间       
			time = formatter.format(curDate); 
			if("".equals(title) || "".equals(content) ) {
				return;
			}
			Note note =new Note (title,content,time);
			noteDao.addNote(note);
			Intent intent = new Intent();
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.setClass(AddNoteActivity.this,MainActivity.class);
			startActivity(intent);
			finish();
			break;

		default:
			break;
		}
	}
}
