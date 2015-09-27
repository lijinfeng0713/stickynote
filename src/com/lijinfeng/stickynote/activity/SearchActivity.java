package com.lijinfeng.stickynote.activity;

import java.util.ArrayList;
import java.util.HashMap;

import com.lijinfeng.stickynote.R;
import com.lijinfeng.stickynote.dao.NoteDao;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class SearchActivity extends Activity {private Button btnAdd = null;
	
    private ListView list;
	SimpleAdapter listItemAdapter;
	private NoteDao noteDao;
	private SQLiteDatabase db;
	ArrayList<HashMap<String, Object>> listData;  
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		
		noteDao = new NoteDao (this);
		Intent getIntent=getIntent();
		String fromTime = getIntent.getStringExtra("fromTime");
		if(fromTime.equals("all")) {
			listData = noteDao.getAllData();
		} else {
			listData = noteDao.getDataByCondition(fromTime);
		}
		
		list = (ListView) findViewById(R.id.search_items);  
	    listItemAdapter = new SimpleAdapter(SearchActivity.this,  
	    		   listData,// 数据源  
	               R.layout.item,// ListItem的XML实现  
	               // 动态数组与item对应的子项  
	               new String[] { "title", "content", "time" },  
	               new int[] { R.id.title, R.id.content, R.id.time });  
	    list.setAdapter(listItemAdapter);
	}
}
