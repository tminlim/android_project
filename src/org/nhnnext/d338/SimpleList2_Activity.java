package org.nhnnext.d338;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class SimpleList2_Activity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_simple_list2_);

		ListView listview = (ListView) findViewById(R.id.SimpleList2_View);
		ArrayList<HashMap<String, String>> hashMap1 = new ArrayList<HashMap<String, String>>(
				2);

		for (int i = 0; i < 10; i++) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("title", i + "번째 ");
			map.put("title2", i + "번째 ");
			hashMap1.add(map);
		}
		String[] from = { "title", "title2" };
		int[] to = { android.R.id.text1, android.R.id.text2 };
		SimpleAdapter simpleAdapter2 = new SimpleAdapter(this, hashMap1,
				android.R.layout.simple_list_item_2, from, to);

		listview.setAdapter(simpleAdapter2);

	}
}
