package org.nhnnext.d338;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

//public class SimpleList1_Activity extends ListActivity implements OnClickListener, OnItemClickListener{
	
public class SimpleList1_Activity  extends Activity implements OnClickListener, OnItemClickListener{

   private ListView mainListView1;
	private ArrayList<Article> articleList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_simple_list1_);
		
		Log.i("check in", "nullpointer");
		
		Button buWrite = (Button)findViewById(R.id.main_button_write);
		Button buRefresh = (Button)findViewById(R.id.main_button_refresh);
//		
		
		mainListView1 = (ListView)findViewById(R.id.listview_simplelist1);
				
		buWrite.setOnClickListener(this);
		buRefresh.setOnClickListener(this);

		Dao dao = new Dao(getApplicationContext());
		String testJsonData = dao.getJsonTestData();
		Log.i("json", testJsonData);
		dao.insertJsonData(testJsonData);

		articleList = dao.getArticleList();
		Log.i("test", "ttt");
		CustomAdapter customAdapter = new CustomAdapter(this, R.layout.customlist_row, articleList);
		mainListView1.setAdapter(customAdapter);
		mainListView1.setOnItemClickListener(this);
		Log.i("test", "ttt");
	}



	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.main_button_write:
			Intent writeActivityIntent = new Intent(this, WriteActivity.class);
			startActivity(writeActivityIntent);
			break;
		}
		
	}



	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, Article_viewer.class);
		
		intent.putExtra("ArticleNumber", articleList.get(position).getArticleNumber()+"");
		startActivity(intent);
		
		
	}
}
