package com.lijinfeng.stickynote.activity;

import java.util.ArrayList;
import java.util.HashMap;

import com.lijinfeng.stickynote.R;
import com.lijinfeng.stickynote.custom.CustomDialog;
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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener{

	private Button btnAdd = null;
	private ListView list;
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
		
	    list = (ListView) findViewById(R.id.list_items);  
	     listItemAdapter = new SimpleAdapter(MainActivity.this,  
	    		    listData,// ����Դ  
	                R.layout.item,// ListItem��XMLʵ��  
	                // ��̬������item��Ӧ������  
	                new String[] { "title", "content", "time" },  
	                new int[] { R.id.title, R.id.content, R.id.time });  
	     list.setAdapter(listItemAdapter);
	     list.setOnCreateContextMenuListener(listviewLongPress);  
	     
	     list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				String detailsId = (String) listData.get(position).get("id");
				
				Intent detailsIntent =new Intent();
				detailsIntent.putExtra("detailsId", detailsId);
				detailsIntent.setClass(MainActivity.this,DetailsActivity.class);
				startActivity(detailsIntent);
			}
		});
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
	
	// �����¼���Ӧ  
    OnCreateContextMenuListener listviewLongPress = new OnCreateContextMenuListener() {  
        @Override  
        public void onCreateContextMenu(ContextMenu menu, View v,  
                ContextMenuInfo menuInfo) {  
            // TODO Auto-generated method stub  
            final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;  
  
            CustomDialog.Builder builder = new CustomDialog.Builder(MainActivity.this);
			builder.setTitle("ɾ����ʾ");
			builder.setMessage("�Ƿ�ɾ����ѡ��������");
			builder.setPositiveButton("ɾ��", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					 int mListPos = info.position;  
                   // ��ȡ��ӦHashMap��������  
                   HashMap<String, Object> map = listData  
                           .get(mListPos);  
                   // ��ȡid  
                   int id = Integer.valueOf((map.get("id").toString()));  
                   // ��ȡ�������ֵ��,���Զ����ݽ�����صĲ���,�����������  
                   if (noteDao.delete(id)) {  
                       // �Ƴ�listData������  
                       listData.remove(mListPos);  
                       listItemAdapter.notifyDataSetChanged();  
                   } else {
                   	 Toast.makeText(getApplicationContext(), "ɾ�����ݿ�ʧ��",  
                                Toast.LENGTH_LONG).show();  
                   }
				}
			});

			builder.setNegativeButton("ȡ��",
					new android.content.DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});

			builder.create().show();
        }  
    };

}
