package com.userservice.dto;

import java.util.ArrayList;

public class AdConfig {
    private ArrayList<String> safeFlags;
    private ArrayList<Object> highRiskFlags;
    private ArrayList<String> unsafeFlags;
    private ArrayList<Object> wallUnsafeFlags;
    private boolean showsAds;
    private int showAdLevel;
    private ArrayList<String> safe_flags;
    private ArrayList<Object> high_risk_flags;
    private ArrayList<String> unsafe_flags;
    private ArrayList<Object> wall_unsafe_flags;
    private boolean show_ads;
    private int show_ad_level;
    private int nsfw_score;
	public ArrayList<String> getSafeFlags() {
		return safeFlags;
	}
	public void setSafeFlags(ArrayList<String> safeFlags) {
		this.safeFlags = safeFlags;
	}
	public ArrayList<Object> getHighRiskFlags() {
		return highRiskFlags;
	}
	public void setHighRiskFlags(ArrayList<Object> highRiskFlags) {
		this.highRiskFlags = highRiskFlags;
	}
	public ArrayList<String> getUnsafeFlags() {
		return unsafeFlags;
	}
	public void setUnsafeFlags(ArrayList<String> unsafeFlags) {
		this.unsafeFlags = unsafeFlags;
	}
	public ArrayList<Object> getWallUnsafeFlags() {
		return wallUnsafeFlags;
	}
	public void setWallUnsafeFlags(ArrayList<Object> wallUnsafeFlags) {
		this.wallUnsafeFlags = wallUnsafeFlags;
	}
	public boolean isShowsAds() {
		return showsAds;
	}
	public void setShowsAds(boolean showsAds) {
		this.showsAds = showsAds;
	}
	public int getShowAdLevel() {
		return showAdLevel;
	}
	public void setShowAdLevel(int showAdLevel) {
		this.showAdLevel = showAdLevel;
	}
	public ArrayList<String> getSafe_flags() {
		return safe_flags;
	}
	public void setSafe_flags(ArrayList<String> safe_flags) {
		this.safe_flags = safe_flags;
	}
	public ArrayList<Object> getHigh_risk_flags() {
		return high_risk_flags;
	}
	public void setHigh_risk_flags(ArrayList<Object> high_risk_flags) {
		this.high_risk_flags = high_risk_flags;
	}
	public ArrayList<String> getUnsafe_flags() {
		return unsafe_flags;
	}
	public void setUnsafe_flags(ArrayList<String> unsafe_flags) {
		this.unsafe_flags = unsafe_flags;
	}
	public ArrayList<Object> getWall_unsafe_flags() {
		return wall_unsafe_flags;
	}
	public void setWall_unsafe_flags(ArrayList<Object> wall_unsafe_flags) {
		this.wall_unsafe_flags = wall_unsafe_flags;
	}
	public boolean isShow_ads() {
		return show_ads;
	}
	public void setShow_ads(boolean show_ads) {
		this.show_ads = show_ads;
	}
	public int getShow_ad_level() {
		return show_ad_level;
	}
	public void setShow_ad_level(int show_ad_level) {
		this.show_ad_level = show_ad_level;
	}
	public int getNsfw_score() {
		return nsfw_score;
	}
	public void setNsfw_score(int nsfw_score) {
		this.nsfw_score = nsfw_score;
	}
    
    
}
