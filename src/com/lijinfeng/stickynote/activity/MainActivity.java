package com.lijinfeng.stickynote.activity;

import java.util.ArrayList;
import java.util.HashMap;

import com.lijinfeng.stickynote.R;
import com.lijinfeng.stickynote.dao.NoteDao;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener{

	private Button btnAdd = null;
	SimpleAdapter listItemAdapter;
	private NoteDao noteDao;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		noteDao = new NoteDao (this);
	
		btnAdd = (Button)findViewById(R.id.btn_add_note);
		btnAdd.setOnClickListener(this);
		
		 ListView list = (ListView) findViewById(R.id.list_items);  
	     listItemAdapter = new SimpleAdapter(MainActivity.this,  
	                noteDao.getAllData(),// 数据源  
	                R.layout.item,// ListItem的XML实现  
	                // 动态数组与ImageItem对应的子项  
	                new String[] { "title", "content", "time" },  
	                // ImageItem的XML文件里面的一个ImageView,两个TextView ID  
	                new int[] { R.id.title, R.id.content, R.id.time });  
	     list.setAdapter(listItemAdapter);  
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_add_note:
			Intent intent = new Intent();
			intent.setClass(MainActivity.this, AddNoteActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}
	}
	

}
