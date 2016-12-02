package com.xiaoyuan54.child.edu.app.bean.story;

import java.io.Serializable;

/**
 * Story
 * @author L.QING
 */
public class Story implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * id
	 */
	private String id;
	/**
	 * domainId
	 */
	private Integer domainId;
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
	 * playTimes
	 */
	private Integer playTimes;
	/**
	 * favorTimes
	 */
	private Integer favorTimes;
	/**
	 * sort
	 */
	private Integer sort;
	/**
	 * publicDate
	 */
	private java.util.Date publicDate;
	/**
	 * storyNums
	 */
	private Integer storyNums;
	/**
	 * storyType
	 */
	private Integer storyType;
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
	 * 是否有效 Y,N
	 */
	private String valid;
	
	public Story(){
	}

	public Story(
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
	 * 获取domainId
	 * @return java.lang.Integer
	 */
	public Integer getDomainId() {
		return this.domainId;
	}
	
	/**
	 * 设置domainId
	 * @param domainId
	 * @type java.lang.Integer
	 */
	public void setDomainId(Integer domainId) {
		this.domainId = domainId;
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
	 * 获取playTimes
	 * @return java.lang.Integer
	 */
	public Integer getPlayTimes() {
		return this.playTimes;
	}
	
	/**
	 * 设置playTimes
	 * @param playTimes
	 * @type java.lang.Integer
	 */
	public void setPlayTimes(Integer playTimes) {
		this.playTimes = playTimes;
	}
	
	/**
	 * 获取favorTimes
	 * @return java.lang.Integer
	 */
	public Integer getFavorTimes() {
		return this.favorTimes;
	}
	
	/**
	 * 设置favorTimes
	 * @param favorTimes
	 * @type java.lang.Integer
	 */
	public void setFavorTimes(Integer favorTimes) {
		this.favorTimes = favorTimes;
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
	 * 获取storyNums
	 * @return java.lang.Integer
	 */
	public Integer getStoryNums() {
		return this.storyNums;
	}
	
	/**
	 * 设置storyNums
	 * @param storyNums
	 * @type java.lang.Integer
	 */
	public void setStoryNums(Integer storyNums) {
		this.storyNums = storyNums;
	}
	
	/**
	 * 获取storyType
	 * @return java.lang.Integer
	 */
	public Integer getStoryType() {
		return this.storyType;
	}
	
	/**
	 * 设置storyType
	 * @param storyType
	 * @type java.lang.Integer
	 */
	public void setStoryType(Integer storyType) {
		this.storyType = storyType;
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