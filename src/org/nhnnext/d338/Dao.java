package org.nhnnext.d338;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class Dao {
	private Context context;
	private SQLiteDatabase database;

	public Dao(Context context) 
	{
		this.context = context;
//aaaaaa
		database = context.openOrCreateDatabase("LocalDATA",
				SQLiteDatabase.CREATE_IF_NECESSARY, null);
		try {
			String sql = "CREATE TABLE IF NOT EXISTS Articles("
					+ "ID integer primary key autoincrement, "
					+ "ArticleNumber integer not null, "
					+ "Title text not null, "
					+ "WriterName text not null, "
					+ "WriteID text not null, "
					+ "Content text not null, "
					+ "WriteDate text not null, " 
					+ "ImgName); ";
			
			
			Log.i("table", sql);
			
			
			database.execSQL(sql);
		} catch (Exception e) {
			
			Log.e("test", "CREATE TABLE FAILED! -" + e);
			e.printStackTrace();
		}
	}


	public void insertJsonData(String jsonData) {

		int articleNumber;
		String title;
		String writer;
		String id;
		String content;
		String writeDate;
		String imgName;
		
		FileDownloader fileDownloader = new FileDownloader(context);
		
		try {
			JSONArray jArr = new JSONArray(jsonData);
			
			for (int i = 0; i < jArr.length(); i++) 
			{
				JSONObject jObj = jArr.getJSONObject(i);
				
				articleNumber = jObj.getInt("ArticleNumber");
				title = jObj.getString("Title");
				writer = jObj.getString("Writer");
				id = jObj.getString("Id");
				content = jObj.getString("Content");
				writeDate = jObj.getString("WriteDate");
				imgName = jObj.getString("ImgName");
				
				Log.i("test", "ArticleNumber:" +articleNumber +"Title:" +title);
				
				String sql = "INSERT INTO Articles(ArticleNumber, Title, WriterName, WriteID, Content, WriteDate, ImgName)"
						+ "VALUES("+ articleNumber +",'"+title+"','"
						+writer+"','"+id+"','"+content+"','"+writeDate+"','"+imgName+"');";
				try {
					database.execSQL(sql);					
				} catch (Exception e) {
					Log.e("test", "DB ERR!-"+e);
					e.printStackTrace();
				}
				fileDownloader.downFile("http://10.73.44.93/~stu11/image/" + imgName, imgName);
			}
		}	catch(JSONException e) {
			Log.e("test", "JSON ERR! -"+e.getMessage());
			e.printStackTrace();
		}
	}
	
	public ArrayList<Article> getArticleList() {
		ArrayList<Article> articleList = new ArrayList<Article>();
		
		int articleNumber;
		String title;
		String writer;
		String id;
		String content;
		String writeDate;
		String imgName;
		
		try {
			String sql = "SELECT * FROM Articles;";
			Cursor cursor = database.rawQuery(sql, null);
			
			while (cursor.moveToNext()) {
				articleNumber = cursor.getInt(1);
				title = cursor.getString(2);
				writer = cursor.getString(3);
				id = cursor.getString(4);
				content = cursor.getString(5);
				writeDate = cursor.getString(6);
				imgName = cursor.getString(7);
				
				articleList.add( new Article(articleNumber, title, writer,id, content, writeDate, imgName));
				} 
				cursor.close();
		}
		catch (Exception e) {
			Log.e("ArticelList", e.getMessage());
		}
		return articleList;
	}
	
	public Article getArticleByArticleNumber(int articleNumber) {
		Article article = null;
		
//		int articleNumber;
		String title;
		String writer;
		String id;
		String content;
		String writeDate;
		String imgName;
		
		
		try{
			String sql = "SELECT * FROM Articles WHERE ArticleNumber = "+articleNumber+";";
			Cursor cursor = database.rawQuery(sql, null);
			
			cursor.moveToNext(); 
			articleNumber = cursor.getInt(1);
			title = cursor.getString(2);
			writer = cursor.getString(3);
			id = cursor.getString(4);
			content = cursor.getString(5);
			writeDate = cursor.getString(6);
			imgName = cursor.getString(7);
			
			article = new Article(articleNumber, title, writer, id, content, writeDate, imgName);
			
			cursor.close();
		}
		catch (Exception e) {
			Log.e("getArticleBuNumber", e.getMessage());
		}
		return article;
	}
	
	
}
