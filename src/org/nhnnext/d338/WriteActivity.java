package org.nhnnext.d338;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.provider.Settings;
import android.provider.Settings.Secure;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class WriteActivity extends Activity implements OnClickListener {
	private EditText etWriter;
	private EditText etTitle;
	private EditText etContent;
	private ImageButton ibPhoto;
	private Button buUpload;

	private String filePath;
	private String fileNames="";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_write);

		etWriter = (EditText) findViewById(R.id.writer);
		etTitle = (EditText) findViewById(R.id.writeTitle);
		etContent = (EditText) findViewById(R.id.writeContent);

		ibPhoto = (ImageButton) findViewById(R.id.writeImg);
		ibPhoto.setOnClickListener(this);
		buUpload = (Button) findViewById(R.id.btnSend);
		buUpload.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.write, menu);
		return true;
	}

	private static final int REQUEST_PHOTO_ALBUM = 1;

	private ProgressDialog progressDialog;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.writeImg:

			Intent intent = new Intent(Intent.ACTION_PICK);

			intent.setType(Images.Media.CONTENT_TYPE);
			intent.setData(Images.Media.EXTERNAL_CONTENT_URI);
			startActivityForResult(intent, REQUEST_PHOTO_ALBUM);

			break;
		case R.id.btnSend:
			final Handler handler = new Handler();
			
			new Thread() {
				public void run() {
					handler.post(new Runnable() {
						
						@Override
						public void run() {
							progressDialog = ProgressDialog.show( WriteActivity.this, "", "uploading");
						}
					});
					
					String ID = Secure.getString(getApplicationContext()
							.getContentResolver(), Settings.Secure.ANDROID_ID);
					String DATE = new SimpleDateFormat("yyyy-MM-dd HH:MM",
							Locale.KOREA).format(new Date());

					Article article = new Article(0, etTitle.getText()
							.toString(), 
							etContent.getText().toString(), fileNames);
					
					ProxyUp proxyUp = new ProxyUp();
					proxyUp.uploadArticle(article, filePath);
					
					handler.post(new Runnable() {
						
						@Override
						public void run() {
							progressDialog.cancel();
							finish();
						}
					});
				}
			}.start();
			break;
		}
		// TODO Auto-generated method stub

	}



	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		try {
			if (requestCode == REQUEST_PHOTO_ALBUM) {
				Uri uri = getRealPathUri(data.getData());
				filePath = uri.toString();
				fileNames = uri.getLastPathSegment();

				Bitmap bitmap = BitmapFactory.decodeFile(filePath);
				ibPhoto.setImageBitmap(bitmap);
			}

		} catch (Exception e) {
			Log.e("test", "onActivityResult error:" + e);
		}
	}

	private Uri getRealPathUri(Uri uri) {
		Uri filePathUri = uri;
		if (uri.getScheme().toString().compareTo("content") == 0) {
			Cursor cursor = getApplicationContext().getContentResolver().query(
					uri, null, null, null, null);
			if (cursor.moveToFirst()) {
				int column_index = cursor
						.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
				filePathUri = Uri.parse(cursor.getString(column_index));
			}
		}
		return filePathUri;
	}
}
