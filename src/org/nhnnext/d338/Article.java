package org.nhnnext.d338;

public class Article {
	private int articleNumber;
	private String title;
	private String writer;
	private String id;
	private String content;
	private String writeDate;
	private String imgName;
	
	public Article(int articleNumber, String title, String writer, String id, String content, String writeDate, String imgName) {
		this.articleNumber = articleNumber;
		this.title = title;
		this.writer = writer;
		this.id = id;
		this.content = content;
		this.writeDate = writeDate;
		this.imgName = imgName;
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

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getWriteDate() {
		return writeDate;
	}

	public void setWriteDate(String writeDate) {
		this.writeDate = writeDate;
	}

	public String getImgName() {
		return imgName;
	}

	public void setImgName(String imgName) {
		this.imgName = imgName;
	}
	
}
