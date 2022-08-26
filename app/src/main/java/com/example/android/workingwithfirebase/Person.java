package com.example.android.workingwithfirebase;

import java.util.Objects;

public class Person {
    private String uId = "";
    private String name = "";
    private int age = 0;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
    Person(){}

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public String getUId() {
        return uId;
    }

    public void setUId(String uId) {
        this.uId = uId;
    }

  /*  @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return age == person.age && Objects.equals(uId, person.uId) && Objects.equals(name, person.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uId, name, age);
    }

*/

    @Override
    public boolean equals(Object o) {

        if (o instanceof Person) {
            Person person = (Person) o;
            return this.uId.equals(person.getUId());
        } else {
            return false;
        }
    }

}
