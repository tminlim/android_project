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
		database = context.openOrCreateDatabase("LocalDATA",
				SQLiteDatabase.CREATE_IF_NECESSARY, null);
		try {
			String sql = "CREATE TABLE IF NOT EXISTS Articles("
					+ "ArticleNumber integer not null Primary KEY, "
					+ "Title text not null, "
					+ "Content text not null, "
					+ "filename text); ";
			
			database.execSQL(sql);
		} catch (Exception e) {
			
			Log.e("test", "CREATE TABLE FAILED! -" + e);
			e.printStackTrace();
		}
	}


	/*
	
	{"list":
	
	
	[
	{"id":10,"title":"haha","contents":"들어가라.","filename":null,"comments":[{"id":45,"contents":"afasdgasg"},
	{"id":50,"contents":"3333"},{"id":51,"contents":"66666"},{"id":52,"contents":"555555"},
	{"id":53,"contents":"555555"},{"id":54,"contents":"eee"},{"id":55,"contents":"eee"},
	{"id":56,"contents":"33333"},{"id":57,"contents":"33333"},{"id":58,"contents":"33333"},{"id":59,"contents":"33333"},{"id":60,"contents":"33333"},{"id":61,"contents":"33333"},{"id":62,"contents":"3333"},{"id":63,"contents":"sddsd"},{"id":64,"contents":"test"},{"id":65,"contents":"testtest"},{"id":66,"contents":"fdsa"},{"id":72,"contents":"ㅁㄴㅇㄹㅁㄴㅇㄹㅁㄴㅇㄹ"},{"id":73,"contents":"ㅁㄴㅇㄹㄴㅇㄹ"}]},{"id":11,"title":"haha","contents":"test","filename":null,"comments":[]},{"id":13,"title":"j","contents":"j","filename":null,"comments":[{"id":29,"contents":"hjgjhgjghgjh"},{"id":30,"contents":"hbjhvhcfkhg"},{"id":31,"contents":""}]},{"id":14,"title":"dd","contents":"ddd","filename":null,"comments":[{"id":49,"contents":"dsdsd"}]},{"id":15,"title":"dd","contents":"dddsfsdf","filename":null,"comments":[{"id":32,"contents":""},{"id":33,"contents":"sadfsadfsdaf"},{"id":34,"contents":"sdafasf"},{"id":67,"contents":"테스트"},{"id":68,"contents":"ㄴㅇㄹㅎㄴㅇㄹㅎㄴㅇㄹㅎ"},{"id":69,"contents":"테스트"},{"id":70,"contents":"아아아"},{"id":71,"contents":"ㅁㄴㅇㄹㅁㄴㅇㄹㅁㄴㅇㄹ"}]},{"id":16,"title":"343434","contents":"wetrwerwqerweq","filename":"wonderful0.jpg","comments":[{"id":44,"contents":"wfwrwetwetq"}]},{"id":18,"title":"test3","contents":"testtstata satast as  as as ta","filename":"sunshine1.jpg","comments":[{"id":43,"contents":"testt3"}]},{"id":19,"title":"test4","contents":"test4","filename":"3399a10dcec70cf1e791241bf31edfce.jpg","comments":[]},{"id":20,"title":"rfrfrf","contents":"rfrfrfr","filename":"lovely-package-you-smell7-e1328336061793.jpg","comments":[]},{"id":21,"title":"65636236","contents":"wetwertwetwye","filename":"greenbrae1.jpg","comments":[{"id":47,"contents":"33333333"},{"id":48,"contents":"fffffffff"}]},{"id":22,"title":"ff","contents":"gg","filename":null,"comments":[]},{"id":24,"title":"444","contents":"4444","filename":null,"comments":[]},{"id":25,"title":"333","contents":"333","filename":null,"comments":[]},{"id":26,"title":"6666","contents":"6666","filename":"photo2.jpg","comments":[]},{"id":27,"title":"","contents":"","filename":null,"comments":[]}]}
	
	*/
	/*
[
 {"id":10,"title":"haha","contents":"들어가라.","filename":"sdd"},
 {"id":10,"title":"haha","contents":"들어가라.","filename":"sdd"},
 {"id":10,"title":"haha","contents":"들어가라.","filename":"sdd"},
 {"id":10,"title":"haha","contents":"들어가라.","filename":"sdd"},
 {"id":13,"title":"j","contents":"j","filename":"asdddd"}
]
 */
	public void insertJsonData(String jsonData) {
	
		jsonData = jsonData.replace("{\"list\":", "");
		jsonData = jsonData.replace("}]}]}", "}]}]");
		
		
		Log.i("test", "jsonData:" + jsonData );
		int articleNumber;
		String title;
		String contents;
		String filename;
		
		FileDownloader fileDownloader = new FileDownloader(context);
		
		
		
		
		try {
			
			/*
			[
			 {"id":10,"title":"haha","contents":"들어가라.","filename":"sdd"},
			 {"id":10,"title":"haha","contents":"들어가라.","filename":"sdd"},
			 {"id":10,"title":"haha","contents":"들어가라.","filename":"sdd"},
			 {"id":10,"title":"haha","contents":"들어가라.","filename":"sdd"},
			 {"id":13,"title":"j","contents":"j","filename":"asdddd"}
			]
			 */
			
			JSONArray jArr = new JSONArray(jsonData);
			
			for (int i = 0; i < jArr.length(); i++) 
			{
				
				/*
				
				 {"id":10,"title":"haha","contents":"들어가라.","filename":"sdd"}
				
				 */
				JSONObject jObj = jArr.getJSONObject(i);
				
				articleNumber = jObj.getInt("id");
				title = jObj.getString("title");
				contents = jObj.getString("contents");
				filename = jObj.getString("filename");
				
				
				
				Log.i("test", "!!!ArticleNumber:" +articleNumber +"Title:" +title);
				
				String sql = "INSERT INTO Articles(ArticleNumber, Title, Content, filename)"
						+ "VALUES("+ articleNumber +",'"+title+"','"+contents+"','"+filename+"');";
				try {
					database.execSQL(sql);					
				} catch (Exception e) {
					Log.e("test", "DB ERR!-"+e);
					e.printStackTrace();
				}
				fileDownloader.downFile("http://10.73.38.167:8080/images/" + filename, filename);
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
		String contents;
		String filename;
		
		try {
			String sql = "SELECT * FROM Articles;";
			Cursor cursor = database.rawQuery(sql, null);
			
			while (cursor.moveToNext()) {
				articleNumber = cursor.getInt(0);
				title = cursor.getString(1);
				contents = cursor.getString(2);
				filename = cursor.getString(3);
				
				articleList.add( new Article(articleNumber, title, contents,filename));
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
		String content;
		String filename;
		
		
		try{
			String sql = "SELECT * FROM Articles WHERE id = "+articleNumber+";";
			Cursor cursor = database.rawQuery(sql, null);
			
			cursor.moveToNext(); 
			articleNumber = cursor.getInt(0);
			title = cursor.getString(1);
			content = cursor.getString(2);
			filename = cursor.getString(3);
			
			article = new Article(articleNumber, title, content, filename);
			
			cursor.close();
		}
		catch (Exception e) {
			Log.e("getArticleBuNumber", e.getMessage());
		}
		return article;
	}
	
	
}
