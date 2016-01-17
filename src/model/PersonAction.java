package model;

public class PersonAction {
	private String userId;
	private String catId;
	private String brandId;
	private String action;
	private int userClass;
	public int getUserClass() {
		return userClass;
	}
	public void setUserClass(int userClass) {
		this.userClass = userClass;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getCatId() {
		return catId;
	}
	public void setCatId(String catId) {
		this.catId = catId;
	}
	public String getBrandId() {
		return brandId;
	}
	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	
}
