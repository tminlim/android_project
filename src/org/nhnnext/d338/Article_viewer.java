package org.nhnnext.d338;

import java.io.IOException;
import java.io.InputStream;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

public class Article_viewer extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_article_viewer);
		
		TextView tvTitle = (TextView)findViewById(R.id.view_article_textView_title);
		TextView tvWriter = (TextView)findViewById(R.id.view_article_textView_writer);

		TextView tvWriterDate = (TextView)findViewById(R.id.view_article_textView_write_time);
		TextView tvContent = (TextView)findViewById(R.id.view_article_textView_content);
		ImageView ivImage = (ImageView)findViewById(R.id.view_article_imageView_photo);
		
		String articleNumber = getIntent().getExtras().getString("ArticleNumber");
		
		Dao dao = new Dao(getApplicationContext());
		Article article = dao.getArticleByArticleNumber(Integer.parseInt(articleNumber));
		
		tvTitle.setText(article.getTitle());
		tvWriter.setText(article.getWriter());
		tvContent.setText(article.getContent());
		tvWriterDate.setText(article.getWriteDate());
		
		try {
			InputStream ims = getApplicationContext().getAssets().open(article.getImgName());
			Drawable d = Drawable.createFromStream(ims, null);
			ivImage.setImageDrawable(d);
		} 
		catch(IOException e) {
			Log.e("ERROR", "ERROR:"+e);
		}
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.article_viewer, menu);
//		return true;
//	}

}
