package com.xiaoyuan54.child.edu.app.bean.user;

import com.xiaoyuan54.child.edu.app.db.annotation.Column;
import com.xiaoyuan54.child.edu.app.db.annotation.DType;
import com.xiaoyuan54.child.edu.app.db.annotation.JType;
import com.xiaoyuan54.child.edu.app.db.annotation.PK;
import com.xiaoyuan54.child.edu.app.db.annotation.Table;

import java.io.Serializable;

/**
 * User
 * @author L.QING
 */
@Table(name = "user")
public class User implements Serializable {

	@PK
	private Integer id;

	@Column(name = "user_name")
	private String userName;

	@Column(name = "nick_name")
	private String nickName;
	/**
	 * 性别,1--男，0--女
	 */
	@Column(name = "gender",dbType = DType.INTEGER,javaType = JType.INTEGER)
	private Integer gender;
	/**
	 * 密码
	 */
	@Column(name = "password")
	private String password;
	/**
	 * 手机
	 */
	@Column(name = "mobile")
	private String mobile;
	/**
	 * 头像URL
	 */
	@Column(name = "header_img")
	private String headerImg;
	/**
	 * 生日
	 */
	@Column(name = "birthday")
	private String birthday;
	/**
	 * 积分
	 */
	@Column(name = "score",dbType = DType.INTEGER,javaType = JType.INTEGER)
	private Integer score;
	/**
	 * 户账金币
	 */
	@Column(name = "coin",dbType = DType.INTEGER,javaType = JType.INTEGER)
	private Integer coin;
	/**
	 * 等级
	 */
	@Column(name = "level",dbType = DType.INTEGER,javaType = JType.INTEGER)
	private Integer level;
	/**
	 * vip会员(Y;N)
	 */
	@Column(name="vip")
	private String vip;


	@Column(name="email")
	private String email;
	/**
	 * 省份
	 */
	@Column(name="province")
	private String province;
	/**
	 * 城市
	 */
	@Column(name="city")
	private String city;
	/**
	 * 地址
	 */
	@Column(name="address")
	private String address;


	@Column(name="wechat_uuid")
	private String wechatUuid;


	@Column(name="wechat_openid")
	private String wechatOpenid;


	@Column(name="qq_openid")
	private String qqOpenid;


	@Column(name="token")
	private String token;

	@Column(name="invite_code")
	private String inviteCode;
	/**
	 * 用户标签
	 */
	@Column(name="user_tag")
	private String userTag;


	public User(){
	}

	public User(
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
	 * 获取账号
	 * @return java.lang.String
	 */
	public String getUserName() {
		return this.userName;
	}
	
	/**
	 * 设置账号
	 * @param userName
	 * @type java.lang.String
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	/**
	 * 获取昵称
	 * @return java.lang.String
	 */
	public String getNickName() {
		return this.nickName;
	}
	
	/**
	 * 设置昵称
	 * @param nickName
	 * @type java.lang.String
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	/**
	 * 获取性别,1--男，0--女
	 * @return java.lang.Integer
	 */
	public Integer getGender() {
		return this.gender;
	}
	
	/**
	 * 设置性别,1--男，0--女
	 * @param gender
	 * @type java.lang.Integer
	 */
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	
	/**
	 * 获取密码
	 * @return java.lang.String
	 */
	public String getPassword() {
		return this.password;
	}
	
	/**
	 * 设置密码
	 * @param password
	 * @type java.lang.String
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * 获取手机
	 * @return java.lang.String
	 */
	public String getMobile() {
		return this.mobile;
	}
	
	/**
	 * 设置手机
	 * @param mobile
	 * @type java.lang.String
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	/**
	 * 获取头像URL
	 * @return java.lang.String
	 */
	public String getHeaderImg() {
		return this.headerImg;
	}
	
	/**
	 * 设置头像URL
	 * @param headerImg
	 * @type java.lang.String
	 */
	public void setHeaderImg(String headerImg) {
		this.headerImg = headerImg;
	}


	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	/**
	 * 获取积分
	 * @return java.lang.Integer
	 */
	public Integer getScore() {
		return this.score;
	}
	
	/**
	 * 设置积分
	 * @param score
	 * @type java.lang.Integer
	 */
	public void setScore(Integer score) {
		this.score = score;
	}
	
	/**
	 * 获取户账金币
	 * @return java.lang.Integer
	 */
	public Integer getCoin() {
		return this.coin;
	}
	
	/**
	 * 设置户账金币
	 * @param coin
	 * @type java.lang.Integer
	 */
	public void setCoin(Integer coin) {
		this.coin = coin;
	}
	
	/**
	 * 获取等级
	 * @return java.lang.Integer
	 */
	public Integer getLevel() {
		return this.level;
	}
	
	/**
	 * 设置等级
	 * @param level
	 * @type java.lang.Integer
	 */
	public void setLevel(Integer level) {
		this.level = level;
	}
	
	/**
	 * 获取vip会员(Y;N)
	 * @return java.lang.String
	 */
	public String getVip() {
		return this.vip;
	}
	
	/**
	 * 设置vip会员(Y;N)
	 * @param vip
	 * @type java.lang.String
	 */
	public void setVip(String vip) {
		this.vip = vip;
	}
	
	/**
	 * 获取email
	 * @return java.lang.String
	 */
	public String getEmail() {
		return this.email;
	}
	
	/**
	 * 设置email
	 * @param email
	 * @type java.lang.String
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * 获取省份
	 * @return java.lang.String
	 */
	public String getProvince() {
		return this.province;
	}
	
	/**
	 * 设置省份
	 * @param province
	 * @type java.lang.String
	 */
	public void setProvince(String province) {
		this.province = province;
	}
	
	/**
	 * 获取城市
	 * @return java.lang.String
	 */
	public String getCity() {
		return this.city;
	}
	
	/**
	 * 设置城市
	 * @param city
	 * @type java.lang.String
	 */
	public void setCity(String city) {
		this.city = city;
	}
	
	/**
	 * 获取地址
	 * @return java.lang.String
	 */
	public String getAddress() {
		return this.address;
	}
	
	/**
	 * 设置地址
	 * @param address
	 * @type java.lang.String
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	
	/**
	 * 获取wechatUuid
	 * @return java.lang.String
	 */
	public String getWechatUuid() {
		return this.wechatUuid;
	}
	
	/**
	 * 设置wechatUuid
	 * @param wechatUuid
	 * @type java.lang.String
	 */
	public void setWechatUuid(String wechatUuid) {
		this.wechatUuid = wechatUuid;
	}
	
	/**
	 * 获取微信id
	 * @return java.lang.String
	 */
	public String getWechatOpenid() {
		return this.wechatOpenid;
	}
	
	/**
	 * 设置微信id
	 * @param wechatOpenid
	 * @type java.lang.String
	 */
	public void setWechatOpenid(String wechatOpenid) {
		this.wechatOpenid = wechatOpenid;
	}
	
	/**
	 * 获取qqOpenid
	 * @return java.lang.String
	 */
	public String getQqOpenid() {
		return this.qqOpenid;
	}
	
	/**
	 * 设置qqOpenid
	 * @param qqOpenid
	 * @type java.lang.String
	 */
	public void setQqOpenid(String qqOpenid) {
		this.qqOpenid = qqOpenid;
	}
	
	/**
	 * 获取微信api token
	 * @return java.lang.String
	 */
	public String getToken() {
		return this.token;
	}
	
	/**
	 * 设置微信api token
	 * @param token
	 * @type java.lang.String
	 */
	public void setToken(String token) {
		this.token = token;
	}
	
	/**
	 * 获取邀请码
	 * @return java.lang.String
	 */
	public String getInviteCode() {
		return this.inviteCode;
	}
	
	/**
	 * 设置邀请码
	 * @param inviteCode
	 * @type java.lang.String
	 */
	public void setInviteCode(String inviteCode) {
		this.inviteCode = inviteCode;
	}
	
	/**
	 * 获取userTag
	 * @return java.lang.String
	 */
	public String getUserTag() {
		return this.userTag;
	}
	
	/**
	 * 设置userTag
	 * @param userTag
	 * @type java.lang.String
	 */
	public void setUserTag(String userTag) {
		this.userTag = userTag;
	}
	

}