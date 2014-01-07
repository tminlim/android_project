package org.nhnnext.d338;

public class Article {
	private int articleNumber;
	private String title;
	private String contents;
	private String filename;
	
	public Article(int articleNumber, String title, String content, String imgName) {
		this.articleNumber = articleNumber;
		this.title = title;
		this.contents = content;
		this.filename = imgName;
	}

	public int getArticleNumber() {
		return articleNumber;
	}

	public void setArticleNumber(int articleNumber) {
		this.articleNumber = articleNumber;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return contents;
	}

	public void setContent(String content) {
		this.contents = content;
	}

	public String getImgName() {
		return filename;
	}

	public void setImgName(String imgName) {
		this.filename = imgName;
	}
	
}
