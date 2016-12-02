package com.xiaoyuan54.child.edu.app.bean.story;

import java.io.Serializable;

/**
 * StoryGroup
 * @author L.QING
 */
public class StoryGroup implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * id
	 */
	private String id;
	/**
	 * storyId
	 */
	private Integer storyId;
	/**
	 * title
	 */
	private String title;
	/**
	 * subTitle
	 */
	private String subTitle;
	/**
	 * author
	 */
	private String author;
	/**
	 * coverImg
	 */
	private String coverImg;
	/**
	 * categoryId
	 */
	private Integer categoryId;
	/**
	 * sort
	 */
	private Integer sort;
	/**
	 * publicDate
	 */
	private java.util.Date publicDate;
	/**
	 * sourceUrl
	 */
	private String sourceUrl;
	/**
	 * 词歌位置
	 */
	private String lyricUrl;
	/**
	 * note
	 */
	private String note;
	/**
	 * tags
	 */
	private String tags;
	/**
	 * 否是已经开放 Y;N
	 */
	private String open;
	/**
	 * 用于续集的故事
	 */
	private java.util.Date openDate;
	/**
	 * 是否有效 Y,N
	 */
	private String valid;
	
	public StoryGroup(){
	}

	public StoryGroup(
			String id
	){
		this.id = id;
	}


	/**
	 * 获取id
	 * @return java.lang.Integer
	 */
	public String getId() {
		return this.id;
	}
	
	/**
	 * 设置id
	 * @param id
	 * @type java.lang.Integer
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * 获取storyId
	 * @return java.lang.Integer
	 */
	public Integer getStoryId() {
		return this.storyId;
	}
	
	/**
	 * 设置storyId
	 * @param storyId
	 * @type java.lang.Integer
	 */
	public void setStoryId(Integer storyId) {
		this.storyId = storyId;
	}
	
	/**
	 * 获取title
	 * @return java.lang.String
	 */
	public String getTitle() {
		return this.title;
	}
	
	/**
	 * 设置title
	 * @param title
	 * @type java.lang.String
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * 获取subTitle
	 * @return java.lang.String
	 */
	public String getSubTitle() {
		return this.subTitle;
	}
	
	/**
	 * 设置subTitle
	 * @param subTitle
	 * @type java.lang.String
	 */
	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}
	
	/**
	 * 获取author
	 * @return java.lang.String
	 */
	public String getAuthor() {
		return this.author;
	}
	
	/**
	 * 设置author
	 * @param author
	 * @type java.lang.String
	 */
	public void setAuthor(String author) {
		this.author = author;
	}
	
	/**
	 * 获取coverImg
	 * @return java.lang.String
	 */
	public String getCoverImg() {
		return this.coverImg;
	}
	
	/**
	 * 设置coverImg
	 * @param coverImg
	 * @type java.lang.String
	 */
	public void setCoverImg(String coverImg) {
		this.coverImg = coverImg;
	}
	
	/**
	 * 获取categoryId
	 * @return java.lang.Integer
	 */
	public Integer getCategoryId() {
		return this.categoryId;
	}
	
	/**
	 * 设置categoryId
	 * @param categoryId
	 * @type java.lang.Integer
	 */
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	
	/**
	 * 获取sort
	 * @return java.lang.Integer
	 */
	public Integer getSort() {
		return this.sort;
	}
	
	/**
	 * 设置sort
	 * @param sort
	 * @type java.lang.Integer
	 */
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	/**
	 * 获取publicDate
	 * @return java.util.Date
	 */
	public java.util.Date getPublicDate() {
		return this.publicDate;
	}
	
	/**
	 * 设置publicDate
	 * @param publicDate
	 * @type java.util.Date
	 */
	public void setPublicDate(java.util.Date publicDate) {
		this.publicDate = publicDate;
	}
	
	/**
	 * 获取sourceUrl
	 * @return java.lang.String
	 */
	public String getSourceUrl() {
		return this.sourceUrl;
	}
	
	/**
	 * 设置sourceUrl
	 * @param sourceUrl
	 * @type java.lang.String
	 */
	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}
	
	/**
	 * 获取词歌位置
	 * @return java.lang.String
	 */
	public String getLyricUrl() {
		return this.lyricUrl;
	}
	
	/**
	 * 设置词歌位置
	 * @param lyricUrl
	 * @type java.lang.String
	 */
	public void setLyricUrl(String lyricUrl) {
		this.lyricUrl = lyricUrl;
	}
	
	/**
	 * 获取note
	 * @return java.lang.String
	 */
	public String getNote() {
		return this.note;
	}
	
	/**
	 * 设置note
	 * @param note
	 * @type java.lang.String
	 */
	public void setNote(String note) {
		this.note = note;
	}
	
	/**
	 * 获取tags
	 * @return java.lang.String
	 */
	public String getTags() {
		return this.tags;
	}
	
	/**
	 * 设置tags
	 * @param tags
	 * @type java.lang.String
	 */
	public void setTags(String tags) {
		this.tags = tags;
	}
	
	/**
	 * 获取否是已经开放 Y;N
	 * @return java.lang.String
	 */
	public String getOpen() {
		return this.open;
	}
	
	/**
	 * 设置否是已经开放 Y;N
	 * @param open
	 * @type java.lang.String
	 */
	public void setOpen(String open) {
		this.open = open;
	}
	
	/**
	 * 获取用于续集的故事
	 * @return java.util.Date
	 */
	public java.util.Date getOpenDate() {
		return this.openDate;
	}
	
	/**
	 * 设置用于续集的故事
	 * @param openDate
	 * @type java.util.Date
	 */
	public void setOpenDate(java.util.Date openDate) {
		this.openDate = openDate;
	}
	
	/**
	 * 获取是否有效 Y,N
	 * @return java.lang.String
	 */
	public String getValid() {
		return this.valid;
	}
	
	/**
	 * 设置是否有效 Y,N
	 * @param valid
	 * @type java.lang.String
	 */
	public void setValid(String valid) {
		this.valid = valid;
	}
	
}