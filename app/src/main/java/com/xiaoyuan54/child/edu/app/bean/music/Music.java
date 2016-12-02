package com.xiaoyuan54.child.edu.app.bean.music;

import java.io.Serializable;
import java.util.Date;

/**
 * Music
 * @author L.QING
 */
public class Music implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String title;
	private String subTitle;
	private String singer;
	private String coverImg;
	private int categoryId;
	private int playTimes;
	private int downTimes;
	private int likeTimes;
	private int sort;
	private Date publicDate;
	private String songHref;
	private String lyricHref;
	private int fromCode;
	private String fromName;
	private String note;
	private String tags;

	private int duration;



	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public String getSinger() {
		return singer;
	}

	public void setSinger(String singer) {
		this.singer = singer;
	}

	public String getCoverImg() {
		return coverImg;
	}

	public void setCoverImg(String coverImg) {
		this.coverImg = coverImg;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public int getPlayTimes() {
		return playTimes;
	}

	public void setPlayTimes(int playTimes) {
		this.playTimes = playTimes;
	}

	public int getDownTimes() {
		return downTimes;
	}

	public void setDownTimes(int downTimes) {
		this.downTimes = downTimes;
	}

	public int getLikeTimes() {
		return likeTimes;
	}

	public void setLikeTimes(int likeTimes) {
		this.likeTimes = likeTimes;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public Date getPublicDate() {
		return publicDate;
	}

	public void setPublicDate(Date publicDate) {
		this.publicDate = publicDate;
	}

	public String getSongHref() {
		return songHref;
	}

	public void setSongHref(String songHref) {
		this.songHref = songHref;
	}

	public String getLyricHref() {
		return lyricHref;
	}

	public void setLyricHref(String lyricHref) {
		this.lyricHref = lyricHref;
	}

	public int getFromCode() {
		return fromCode;
	}

	public void setFromCode(int fromCode) {
		this.fromCode = fromCode;
	}

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}
}