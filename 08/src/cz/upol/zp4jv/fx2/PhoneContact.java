package cz.upol.zp4jv.fx2;

public class PhoneContact {
	
	private final String name;
	private final String phone;
	
	public PhoneContact(String name, String phone) {
		super();
		this.name = name;
		this.phone = phone;
	}
	public String getName() {
		return name;
	}
	public String getPhone() {
		return phone;
	}
	@Override
	public String toString() {
		return name + " " + phone;
	}
}
