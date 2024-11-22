package model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Person {
    public int personId;
    public int userId;
    public String name;
    public int age;
    public float weight;
    public float height;
    public float bmi;

    //public ArrayList<>

    public Person(int personId, String name, int age, float weight, float height){
        this.personId = personId;
        this.userId = personId;
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.bmi = bmi;
    }

    public Person(){

    }

    @Override
    public String toString() {
        return " Name = '" + name + '\'' +
                ", Age = " + age +
                ", Weight = " + weight +
                ", Height = " + height+
                '}';
    }


    public float getBmi() {
        return bmi;
    }

    public void setBmi(float bmi){this.bmi =bmi;}

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }
}
