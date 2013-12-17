package org.nhnnext.d338;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {
	
	
	private Button button1;
	private Button button2;
	private Button button3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		button1 = (Button) findViewById(R.id.button1);
		button2 = (Button) findViewById(R.id.button2);
		button3 = (Button) findViewById(R.id.button3);

		button1.setOnClickListener(this);
		button2.setOnClickListener(this);
		button3.setOnClickListener(this);
		
		Dao dao = new Dao(getApplicationContext());
		String testJsonData = dao.getJsonTestData();
		dao.insertJsonData(testJsonData);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.button1:
			Intent intentType1 = new Intent(this, SimpleList1_Activity.class);
			startActivity(intentType1);
			break;
		case R.id.button2:
			Intent intentType2 = new Intent(this, SimpleList2_Activity.class);
			startActivity(intentType2);
			break;
		}
	}

}
