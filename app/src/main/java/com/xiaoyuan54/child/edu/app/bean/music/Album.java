package com.xiaoyuan54.child.edu.app.bean.music;

import java.io.Serializable;

/**
 * Album
 * @author L.QING
 */
public class Album implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;

	private String title;

	private String subTitle;

	private String singer;

	private String coverImg;

	private Integer categoryId;

	private Integer playTimes=0;

	private Integer downTimes=0;

	private Integer favorTimes=0;

	private Integer sort;

	private java.util.Date publicDate;

	private Integer songNum=0;

	private Integer fromCode;

	private String fromName;

	private String note;

	private String tags;

	private String valid;
	
	public Album(){
	}

	public Album(
		Integer id
	){
		this.id = id;
	}


	public Integer getKey() {
		return this.id;
	}


	public Integer getId() {
		return this.id;
	}
	

	public void setId(Integer id) {
		this.id = id;
	}
	

	public String getTitle() {
		return this.title;
	}
	

	public void setTitle(String title) {
		this.title = title;
	}
	

	public String getSubTitle() {
		return this.subTitle;
	}
	

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}


	public String getSinger() {
		return this.singer;
	}
	

	public void setSinger(String singer) {
		this.singer = singer;
	}
	

	public String getCoverImg() {
		return this.coverImg;
	}
	

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
	 * 获取downTimes
	 * @return java.lang.Integer
	 */
	public Integer getDownTimes() {
		return this.downTimes;
	}
	
	/**
	 * 设置downTimes
	 * @param downTimes
	 * @type java.lang.Integer
	 */
	public void setDownTimes(Integer downTimes) {
		this.downTimes = downTimes;
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
	 * 获取songNum
	 * @return java.lang.Integer
	 */
	public Integer getSongNum() {
		return this.songNum;
	}
	
	/**
	 * 设置songNum
	 * @param songNum
	 * @type java.lang.Integer
	 */
	public void setSongNum(Integer songNum) {
		this.songNum = songNum;
	}
	
	/**
	 * 获取fromCode
	 * @return java.lang.Integer
	 */
	public Integer getFromCode() {
		return this.fromCode;
	}
	
	/**
	 * 设置fromCode
	 * @param fromCode
	 * @type java.lang.Integer
	 */
	public void setFromCode(Integer fromCode) {
		this.fromCode = fromCode;
	}
	
	/**
	 * 获取fromName
	 * @return java.lang.String
	 */
	public String getFromName() {
		return this.fromName;
	}
	
	/**
	 * 设置fromName
	 * @param fromName
	 * @type java.lang.String
	 */
	public void setFromName(String fromName) {
		this.fromName = fromName;
	}
	

	public String getNote() {
		return this.note;
	}
	

	public void setNote(String note) {
		this.note = note;
	}
	

	public String getTags() {
		return this.tags;
	}
	

	public void setTags(String tags) {
		this.tags = tags;
	}
	

	public String getValid() {
		return this.valid;
	}
	

	public void setValid(String valid) {
		this.valid = valid;
	}
	
}