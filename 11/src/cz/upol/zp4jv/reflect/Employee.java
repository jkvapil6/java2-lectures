package cz.upol.zp4jv.reflect;

/**
 * Trida reprezentujici zamestnance
 */

@Serialize(as = "emp")
@Deserialize(as = "emp")
class Employee {

	private String name;
	private int age;
	private double salary;

	public Employee() { }

	@Deserialize(as = "name")
	public void setName(String name) {
		this.name = name;
	}

	@Serialize(as = "name")
	public String getName() {
		return this.name;
	}

	@Deserialize(as = "age")
	public void setAge(int age) {
		this.age = age;
	}

	@Serialize(as = "age")
	public int getAge() {
		return this.age;
	}

	@Deserialize(as = "income")
	public void setSalary(double salary) {
		this.salary = salary;
	}

	@Serialize(as = "income")
	public double getSalary() {
		return this.salary;
	}

	public String toString() {
		return "Employee [name=" + name + ", age=" + age + ", salary=" + salary + "]";
	}
}
