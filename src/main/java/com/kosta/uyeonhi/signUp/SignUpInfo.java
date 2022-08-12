package com.kosta.uyeonhi.signUp;

import java.sql.Date;
import java.util.ArrayList;

public class SignUpInfo {
	private String uid;
	private String upassword;
	private Date birth;
	private String email;
	private Gender gender;
	private String hogam;
	private String uname;
	private String nickname;
	private String phone;
	private String kakao;
	private String mbti;
	
	public String getMbti() {
		return mbti;
	}
	public void setMbti(String mbti) {
		this.mbti = mbti;
	}
	private ArrayList<Long> mfList = new ArrayList<>();
	private ArrayList<Long> mhList = new ArrayList<>();
	private ArrayList<Long> miList = new ArrayList<>();
	private ArrayList<Long> fList = new ArrayList<>();
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getUpassword() {
		return upassword;
	}
	public void setUpassword(String upassword) {
		this.upassword = upassword;
	}
	public Date getBirth() {
		return birth;
	}
	public void setBirth(Date birth) {
		this.birth = birth;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	public String getHogam() {
		return hogam;
	}
	public void setHogam(String hogam) {
		this.hogam = hogam;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getKakao() {
		return kakao;
	}
	public void setKakao(String kakao2) {
		this.kakao = kakao2;
	}
	public ArrayList<Long> getMfList() {
		return mfList;
	}
	public void setMfList(ArrayList<Long> mfList) {
		this.mfList = mfList;
	}
	public ArrayList<Long> getMhList() {
		return mhList;
	}
	public void setMhList(ArrayList<Long> mhList) {
		this.mhList = mhList;
	}
	public ArrayList<Long> getMiList() {
		return miList;
	}
	public void setMiList(ArrayList<Long> miList) {
		this.miList = miList;
	}
	public ArrayList<Long> getfList() {
		return fList;
	}
	public void setfList(ArrayList<Long> fList) {
		this.fList = fList;
	}
	public ArrayList<Long> gethList() {
		return hList;
	}
	public void sethList(ArrayList<Long> hList) {
		this.hList = hList;
	}
	public ArrayList<Long> getiList() {
		return iList;
	}
	public void setiList(ArrayList<Long> iList) {
		this.iList = iList;
	}
	private ArrayList<Long> hList = new ArrayList<>();
	private ArrayList<Long> iList = new ArrayList<>();
}
