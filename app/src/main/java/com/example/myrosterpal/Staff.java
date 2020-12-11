package com.example.myrosterpal;

import org.parceler.Parcel;

//Code adapted from IS4447 lecture by Michael Gleeson - 03/12/20

@Parcel
public class Staff {
    private String key;
    private String firstName;
    private String lastName;
    private int age;

    public Staff(){

    }

    public Staff(String firstName, String lastName, int age){
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    //Maps model to firebase
    @Override
    public boolean equals(Object object){
        if(object == null)
            return false;
        if(!Staff.class.isAssignableFrom(object.getClass()))
            return false;
        final Staff staff = (Staff)object;
        return staff.getKey().equals(key);
    }
}
