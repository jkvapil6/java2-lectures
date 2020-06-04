package cz.upol.zp4jv.reflect;

import java.io.*;

public class Main {

    public static void main(String[] args) throws Exception {

        String filename = "emp.xml";

        Employee e = new Employee();

        e.setAge(42);
        e.setName("John Doe");
        e.setSalary(66666);

        System.out.println("Serializing: " + e.toString());

        Serializer.serialize(e, filename);

        System.out.println("Deserializing from file " + filename + "...");
        InputStream input = new FileInputStream(filename);

        Employee emp = (Employee)Deserializer.deserialize(Employee.class, input);
        System.out.println(emp.toString());
    }
}