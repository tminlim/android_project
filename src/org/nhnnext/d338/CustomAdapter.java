package org.nhnnext.d338;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends ArrayAdapter<Article> {
	private Context context;
	private int layoutResourceId;
	private ArrayList<Article> list;
	
	public CustomAdapter(Context context, int layoutResourceId, ArrayList<Article> list) {
		super(context, layoutResourceId, list);
		this.context = context;
		this.layoutResourceId = layoutResourceId;
		this.list = list;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View row = convertView;
		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);
		}
		
		Article article = list.get(position);
		
		ImageView imageView = (ImageView) row.findViewById(R.id.custom_row_imageView1);  
		TextView tvTitle = (TextView) row.findViewById(R.id.textView555);
		TextView tvContent = (TextView) row.findViewById(R.id.textView2);
			
//		Log.i("image name", article.getImgName() + " p name : " + context.getPackageName());
		tvTitle.setText(article.getTitle());
		tvContent.setText(article.getArticleNumber() + "");
		
		String path_filename = article.getImgName();
		
		if (path_filename!= null) {
			String img_path = context.getFilesDir().getPath() + "/" +path_filename;
			File img_load_pathFile = new File(img_path);
			
			if (img_load_pathFile.exists()) {
				Bitmap bitmap = BitmapFactory.decodeFile(img_path);
				imageView.setImageBitmap(bitmap);
		}
		
		}
////		int resID = context.getResources().getIdentifier(article.getImgName(), "drawable", context.getPackageName());
//		Log.i("image id", ""+resID);
//		imageView.setImageResource(resID);
	
		return row;
	}
}
