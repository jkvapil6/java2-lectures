package cz.upol.zp4jv.fx1;

public class PhoneContact {
	
	private final String name;
	private final String phoneNum;
	
	public PhoneContact(String name, String phoneNum) {
		super();
		this.name = name;
		this.phoneNum = phoneNum;
	}
	
	public String getName() {
		return name;
	}
	
	public String getPhoneNum() {
		return phoneNum;
	}

	@Override
	public String toString() {
		return name + " -> " + phoneNum;
	}
}
