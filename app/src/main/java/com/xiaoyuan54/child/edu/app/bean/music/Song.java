package com.xiaoyuan54.child.edu.app.bean.music;

import com.xiaoyuan54.child.edu.app.db.annotation.Column;
import com.xiaoyuan54.child.edu.app.db.annotation.DType;
import com.xiaoyuan54.child.edu.app.db.annotation.JType;
import com.xiaoyuan54.child.edu.app.db.annotation.PK;
import com.xiaoyuan54.child.edu.app.db.annotation.Table;

import java.io.Serializable;

/**
 * Song
 * @author L.QING
 */

@Table(name = "song")
public class Song implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@PK
	private String id;

	@Column(name = "title")
	private String title;

	@Column(name = "sub_title")
	private String subTitle;

	@Column(name = "singer")
	private String singer;

	@Column(name = "cover_img")
	private String coverImg;

	@Column(name = "category_id")
	private Integer categoryId;

	@Column(name = "play_times",dbType = DType.INTEGER,javaType = JType.INTEGER)
	private Integer playTimes;

	@Column(name = "down_times",dbType = DType.INTEGER,javaType = JType.INTEGER)
	private Integer downTimes;

	@Column(name = "sort",dbType = DType.INTEGER,javaType = JType.INTEGER)
	private Integer sort;

	@Column(name = "public_date")
	private String publicDate;

	@Column(name = "song_url")
	private String songUrl;
	/**
	 * 词歌位置
	 */
	@Column(name = "lyric_url")
	private String lyricUrl;

	@Column(name = "from_code",dbType = DType.INTEGER,javaType = JType.INTEGER)
	private Integer fromCode;

	@Column(name = "from_name")
	private String fromName;

	@Column(name = "note")
	private String note;

	@Column(name = "tags")
	private String tags;
	/**
	 * 是否有效 Y,N
	 */
	@Column(name = "valid")
	private String valid;

	private long duration;

	public Song(){
	}

	public Song(
		String id
	){
		this.id = id;
	}


	public String getId() {
		return this.id;
	}

	public void setId(String id) {
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
	
	public Integer getCategoryId() {
		return this.categoryId;
	}
	
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	
	public Integer getPlayTimes() {
		return this.playTimes;
	}

	public void setPlayTimes(Integer playTimes) {
		this.playTimes = playTimes;
	}

	public Integer getDownTimes() {
		return this.downTimes;
	}
	
	public void setDownTimes(Integer downTimes) {
		this.downTimes = downTimes;
	}

	public Integer getSort() {
		return this.sort;
	}
	
	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}


	public String getPublicDate() {
		return publicDate;
	}

	public void setPublicDate(String publicDate) {
		this.publicDate = publicDate;
	}

	public String getSongUrl() {
		return songUrl;
	}

	public void setSongUrl(String songUrl) {
		this.songUrl = songUrl;
	}

	public String getLyricUrl() {
		return lyricUrl;
	}

	public void setLyricUrl(String lyricUrl) {
		this.lyricUrl = lyricUrl;
	}

	public Integer getFromCode() {
		return this.fromCode;
	}
	
	public void setFromCode(Integer fromCode) {
		this.fromCode = fromCode;
	}
	
	public String getFromName() {
		return this.fromName;
	}

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

	@Override
	public boolean equals(Object o)
	{
		if(!(o instanceof Song))
		{
			return false;
		}
		Song song = (Song)o;
		if(null!=title&&title.equals(song.getTitle()))
		{
			if(null!=singer)
			{
				return singer.equals(song.getSinger());
			}
			return true;
		}
		return false;
	}
}