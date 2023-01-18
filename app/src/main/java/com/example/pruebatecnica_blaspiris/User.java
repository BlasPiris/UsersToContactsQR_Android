package com.example.pruebatecnica_blaspiris;

import android.annotation.SuppressLint;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;

//USER CLASS
public class User implements Serializable {

    String titleName;
    String name;
    String surname;
    String email;
    String birthday;
    String address;
    String age;
    String phone;
    String phone2;
    String gender;
    String photo;
    String city;
    String state;
    String postCode;
    String country;

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthday() {
        return birthday;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setBirthday(String birthday) {
        Instant instant = Instant.parse( birthday );
        java.util.Date date = java.util.Date.from( instant );
       @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        this.birthday = dateFormat.format(date);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getNameSurname() {
        return this.getName()+" "+this.getSurname();
    }


    public String getAllName() {
        return this.getTitleName()+" "+this.getName()+" "+this.getSurname();
    }

    public String getAllCity(){
        return this.getCity()+", "+this.getState()+" ( "+this.getPostCode()+" ) ";
    }

    public String getDateAge() {
        return this.getBirthday()+" ( "+this.getAge()+" years )";
    }
}
