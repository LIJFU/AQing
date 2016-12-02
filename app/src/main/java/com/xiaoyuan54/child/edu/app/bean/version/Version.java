package com.xiaoyuan54.child.edu.app.bean.version;

import com.xiaoyuan54.child.edu.app.db.annotation.Column;
import com.xiaoyuan54.child.edu.app.db.annotation.DType;
import com.xiaoyuan54.child.edu.app.db.annotation.JType;
import com.xiaoyuan54.child.edu.app.db.annotation.PK;
import com.xiaoyuan54.child.edu.app.db.annotation.Table;

import java.io.Serializable;

/**
 * Version
 * @author L.QING
 */

@Table(name="version")
public class Version implements Serializable {

	@PK
	private Integer id;

	@Column(name = "app_code",dbType = DType.INTEGER,javaType = JType.INTEGER)
	private Integer appCode;
	/**
	 * 应用类型 (apk;ios)
	 */
	@Column(name = "app_type")
	private String appType;


	@Column(name = "version_code",dbType = DType.INTEGER,javaType = JType.INTEGER)
	private Integer versionCode;


	@Column(name = "version_name")
	private String versionName;
	/**
	 * fileSize
	 */
	@Column(name = "file_size",dbType = DType.INTEGER,javaType = JType.LONG)
	private Long fileSize;
	/**
	 * versionNote
	 */
	@Column(name = "version_note")
	private String versionNote;
	/**
	 * download url
	 */
	@Column(name = "download")
	private String download;
	/**
	 * 强制更新(Y;N)
	 */
	@Column(name = "force_update")
	private String forceUpdate;
	/**
	 * createTime
	 */
	@Column(name = "create_time")
	private String createTime;

	/**
	 * 当前已下载的位置
	 */
	@Column(name = "range_size",dbType = DType.INTEGER,javaType = JType.LONG)
	private Long rangeSize;

	/**
	 * 是否已经下载完成 1：完成
	 */
	@Column(name = "finish",dbType = DType.INTEGER,javaType = JType.INTEGER)
	private Integer finish ;

	public Version(){
	}

	public Version(
		Integer id
	){
		this.id = id;
	}

	/**
	 * 获取id
	 * @return java.lang.Integer
	 */
	public Integer getId() {
		return this.id;
	}
	
	/**
	 * 设置id
	 * @param id
	 * @type java.lang.Integer
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	
	/**
	 * 获取应用编号
	 * @return java.lang.Integer
	 */
	public Integer getAppCode() {
		return this.appCode;
	}
	
	/**
	 * 设置应用编号
	 * @param appCode
	 * @type java.lang.Integer
	 */
	public void setAppCode(Integer appCode) {
		this.appCode = appCode;
	}
	
	/**
	 * 获取应用类型 (apk;ios)
	 * @return java.lang.String
	 */
	public String getAppType() {
		return this.appType;
	}
	
	/**
	 * 设置应用类型 (apk;ios)
	 * @param appType
	 * @type java.lang.String
	 */
	public void setAppType(String appType) {
		this.appType = appType;
	}
	
	/**
	 * 获取versionCode
	 * @return java.lang.Integer
	 */
	public Integer getVersionCode() {
		return this.versionCode;
	}
	
	/**
	 * 设置versionCode
	 * @param versionCode
	 * @type java.lang.Integer
	 */
	public void setVersionCode(Integer versionCode) {
		this.versionCode = versionCode;
	}
	
	/**
	 * 获取versionName
	 * @return java.lang.String
	 */
	public String getVersionName() {
		return this.versionName;
	}
	
	/**
	 * 设置versionName
	 * @param versionName
	 * @type java.lang.String
	 */
	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}
	
	/**
	 * 获取fileSize
	 * @return java.lang.Long
	 */
	public Long getFileSize() {
		return this.fileSize;
	}
	
	/**
	 * 设置fileSize
	 * @param fileSize
	 * @type java.lang.Long
	 */
	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}
	
	/**
	 * 获取versionNote
	 * @return java.lang.String
	 */
	public String getVersionNote() {
		return this.versionNote;
	}
	
	/**
	 * 设置versionNote
	 * @param versionNote
	 * @type java.lang.String
	 */
	public void setVersionNote(String versionNote) {
		this.versionNote = versionNote;
	}
	
	/**
	 * 获取download
	 * @return java.lang.String
	 */
	public String getDownload() {
		return this.download;
	}
	
	/**
	 * 设置download
	 * @param download
	 * @type java.lang.String
	 */
	public void setDownload(String download) {
		this.download = download;
	}
	
	/**
	 * 获取强制更新(Y;N)
	 * @return java.lang.String
	 */
	public String getForceUpdate() {
		return this.forceUpdate;
	}
	
	/**
	 * 设置强制更新(Y;N)
	 * @param forceUpdate
	 * @type java.lang.String
	 */
	public void setForceUpdate(String forceUpdate) {
		this.forceUpdate = forceUpdate;
	}


	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}


	public Long getRangeSize() {
		return rangeSize;
	}

	public void setRangeSize(Long rangeSize) {
		this.rangeSize = rangeSize;
	}

	public Integer getFinish() {
		return finish;
	}

	public void setFinish(Integer finish) {
		this.finish = finish;
	}
}