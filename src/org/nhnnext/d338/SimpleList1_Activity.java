package org.nhnnext.d338;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.devspark.sidenavigation.ISideNavigationCallback;
import com.devspark.sidenavigation.SideNavigationView;
import com.devspark.sidenavigation.SideNavigationView.Mode;

//public class SimpleList1_Activity extends ListActivity implements OnClickListener, OnItemClickListener{

public class SimpleList1_Activity extends Activity implements OnClickListener,
		OnItemClickListener {

	private SideNavigationView sideNavigationView;

	private ListView mainListView1;
	private ArrayList<Article> articleList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_simple_list1_);

		sideNavigationView = (SideNavigationView) findViewById(R.id.side_navigation_view);
		sideNavigationView.setMenuItems(R.menu.side_menu);
		sideNavigationView.setMenuClickCallback(sideNavigationCallback);
		sideNavigationView.setMode(Mode.LEFT);
		Log.i("check in", "nullpointer");

		// Button buWrite = (Button)findViewById(R.id.main_button_write);
		// Button buRefresh = (Button)findViewById(R.id.main_button_refresh);
		// //
		// buWrite.setOnClickListener(this);
		// buRefresh.setOnClickListener(this);

		mainListView1 = (ListView) findViewById(R.id.listview_simplelist1);

		refreshData();
		listVew();

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	protected void onResume() {
		super.onResume();

		refreshData();
		listVew();

	};

	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void listVew() {
		// TODO Auto-generated method stub
		Dao dao = new Dao(getApplicationContext());
		articleList = dao.getArticleList();

		CustomAdapter customAdapter = new CustomAdapter(this,
				R.layout.customlist_row, articleList);
		mainListView1.setAdapter(customAdapter);
		mainListView1.setOnItemClickListener(this);

	}

	private final Handler handler = new Handler();

	private void refreshData() {
		new Thread() {
			// TODO Auto-generated method stub
			public void run() {
				Proxy proxy = new Proxy();
				String jsonData = proxy.getJSON();

				Dao dao = new Dao(getApplicationContext());
				dao.insertJsonData(jsonData);

				handler.post(new Runnable() {

					@Override
					public void run() {
						listVew();
					}
				});
			}
		}.start();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// case R.id.main_button_write:
		// Intent writeActivityIntent = new Intent(this, WriteActivity.class);
		// startActivity(writeActivityIntent);
		// break;
		// case R.id.main_button_refresh:
		// refreshData();
		// break;
		}

	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view,
			int position, long id) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, Article_viewer.class);

		intent.putExtra("ArticleNumber", articleList.get(position)
				.getArticleNumber() + "");
		startActivity(intent);
	}

	ISideNavigationCallback sideNavigationCallback = new ISideNavigationCallback() {
		@Override
		public void onSideNavigationItemClick(int itemId) {
			String text = "";
			switch (itemId) {
			case R.id.side_navigation_menu_add:
				text = "add";
				break;
			case R.id.side_navigation_menu_call:
				text = "call";
				break;
			case R.id.side_navigation_menu_camera:
				text = "camera";
				break;
			case R.id.side_navigation_menu_delete:
				text = "delete";
				break;
			default:
				text = "";
			}
			Toast.makeText(getApplicationContext(), "side menu:" + text,
					Toast.LENGTH_SHORT).show();
		}
	};

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		String text = "";
		boolean viewflag = false;

		switch (item.getItemId()) {

		case R.id.action_write:
			Intent writeIntent = new Intent(this, WriteActivity.class);
			startActivity(writeIntent);
			break;
		case R.id.action_refresh:
			Intent refresh = new Intent(this, SimpleList1_Activity.class);
			startActivity(refresh);
			break;
//		case R.id.action_item1:
//			text = "Action item, with text, dosplayed if room exists";
//			break;
//
//		case R.id.action_item2:
//			text = "Action item, icon only, always displayed";
//			break;
//
		case R.id.action_item3:
			text = "Normal menu item";
			break;
		case android.R.id.home:
			sideNavigationView.toggleMenu();
			break;

		default:
			return super.onOptionsItemSelected(item);
		}
		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
		return true;
	}

}
