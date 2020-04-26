package rest;

import java.util.Arrays;

public class User {

    private String name;
    private int age;
    private Chear chear;
    private int [] ar;

    public User(String name, int age) {
        this.name = name;
        this.age = age;
        chear = new Chear(1, 2);
        ar = new int[5];
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", chear=" + chear +
                ", ar=" + Arrays.toString(ar) +
                '}';
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
