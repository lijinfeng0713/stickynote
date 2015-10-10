package com.lijinfeng.stickynote.activity;

import java.util.ArrayList;
import java.util.HashMap;

import com.lijinfeng.stickynote.R;
import com.lijinfeng.stickynote.dao.NoteDao;
import com.lijinfeng.stickynote.util.GetLastDateTime;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class SearchActivity extends Activity implements OnClickListener {
	
	private Button btnBack ;
	private TextView searchTitle ;
    private ListView list;
	SimpleAdapter listItemAdapter;
	private NoteDao noteDao;
	ArrayList<HashMap<String, Object>> listData;  
	GetLastDateTime getLastDateTime = new GetLastDateTime();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		
		btnBack = (Button)findViewById(R.id.search_btn_back);
		btnBack.setOnClickListener(this);
		searchTitle = (TextView)findViewById(R.id.activity_search_title);
		
		noteDao = new NoteDao (this);
		Intent getIntent=getIntent();
		String condition = getIntent.getStringExtra("condition");
		if(condition.equals("all")) {
			listData = noteDao.getAllData();
			searchTitle.setText("�鿴ȫ��");
			String time = getLastDateTime.lastWeek();
			System.out.println("------------------------------------------------");
			System.out.println(time);
		} 
		if(condition.equals("within_week")) {
			listData = noteDao.getDataByCondition(getLastDateTime.lastWeek());
			searchTitle.setText("���һ��");
		}
		if(condition.equals("within_month")) {
			listData = noteDao.getDataByCondition(getLastDateTime.lastMonth());
			searchTitle.setText("���һ��");
		}
		
		list = (ListView) findViewById(R.id.search_items);  
	    listItemAdapter = new SimpleAdapter(SearchActivity.this,  
	    		   listData,// ����Դ  
	               R.layout.item,// ListItem��XMLʵ��  
	               // ��̬������item��Ӧ������  
	               new String[] { "title", "content", "time" },  
	               new int[] { R.id.title, R.id.content, R.id.time });  
	    list.setAdapter(listItemAdapter);
	    if(listData.isEmpty()) {
	    	Toast.makeText(getApplicationContext(), "����ؼ�¼��",  
                    Toast.LENGTH_LONG).show();
	    }
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.search_btn_back:
			super.onBackPressed();
			break;
		default:
			break;
		}
	}
}
