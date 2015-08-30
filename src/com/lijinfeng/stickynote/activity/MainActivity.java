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
import android.view.KeyEvent;
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
	private SQLiteDatabase db;
	ArrayList<HashMap<String, Object>> listData;  

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		noteDao = new NoteDao (this);
		listData = noteDao.getAllData();
	
		btnAdd = (Button)findViewById(R.id.btn_add_note);
		btnAdd.setOnClickListener(this);
		
		 ListView list = (ListView) findViewById(R.id.list_items);  
	     listItemAdapter = new SimpleAdapter(MainActivity.this,  
	    		    listData,// 数据源  
	                R.layout.item,// ListItem的XML实现  
	                // 动态数组与item对应的子项  
	                new String[] { "title", "content", "time" },  
	                new int[] { R.id.title, R.id.content, R.id.time });  
	     list.setAdapter(listItemAdapter);
	     list.setOnCreateContextMenuListener(listviewLongPress);  
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
	
	// 长按事件响应  
    OnCreateContextMenuListener listviewLongPress = new OnCreateContextMenuListener() {  
        @Override  
        public void onCreateContextMenu(ContextMenu menu, View v,  
                ContextMenuInfo menuInfo) {  
            // TODO Auto-generated method stub  
            final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;  
            new AlertDialog.Builder(MainActivity.this)  
                    /* 弹出窗口的最上头文字 */  
                    .setTitle("删除当前数据")  
                    /* 设置弹出窗口的图式 */  
                    .setIcon(android.R.drawable.ic_dialog_info)  
                    /* 设置弹出窗口的信息 */  
                    .setMessage("确定删除当前记录？")  
                    .setPositiveButton("是",  
                            new DialogInterface.OnClickListener() {  
                                public void onClick(  
                                        DialogInterface dialoginterface, int i) {  
                                    // 获取位置索引  
                                    int mListPos = info.position;  
                                    // 获取对应HashMap数据内容  
                                    HashMap<String, Object> map = listData  
                                            .get(mListPos);  
                                    // 获取id  
                                    int id = Integer.valueOf((map.get("id")  
                                            .toString()));  
                                    // 获取数组具体值后,可以对数据进行相关的操作,例如更新数据  
                                    if (noteDao.delete(id)) {  
                                        // 移除listData的数据  
                                        listData.remove(mListPos);  
                                        listItemAdapter.notifyDataSetChanged();  
                                    } else {
                                    	 Toast.makeText(getApplicationContext(), "删除数据库失败",  
                                                 Toast.LENGTH_LONG).show();  
                                    }
                                }  
                            })  
                    .setNegativeButton("否",  
                            new DialogInterface.OnClickListener() {  
                                public void onClick(  
                                        DialogInterface dialoginterface, int i) {  
                                    // 什么也没做  
  
                                }  
                            }).show();  
        }  
    };

//    @Override 
//    public boolean onKeyDown(int keyCode, KeyEvent event) { 
//        if (keyCode == KeyEvent.KEYCODE_BACK) { 
//            this.finish(); 
//            return true; 
//        } 
//        return super.onKeyDown(keyCode, event); 
//    }  
}
