package model;

public class Address {
	private int streetNb;
	private String streetName;
	private String appNb;
	private String city;
	private String province;
	private String posteCode;
	
	public Address(int streetNb, String streetName, String appNb, String city, String province, String posteCode) {
		this.streetNb = streetNb;
		this.streetName = streetName;
		this.appNb = appNb;
		this.city = city;
		this.province = province;
		this.posteCode = posteCode;
	}
	
	public int getStreetNb() {
		return streetNb;
	}
	public void setStreetNb(int streetNb) {
		this.streetNb = streetNb;
	}
	public String getStreetName() {
		return streetName;
	}
	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}
	public String getAppNb() {
		return appNb;
	}
	public void setAppNb(String appNb) {
		this.appNb = appNb;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getPosteCode() {
		return posteCode;
	}
	public void setPosteCode(String posteCode) {
		this.posteCode = posteCode;
	}
}
