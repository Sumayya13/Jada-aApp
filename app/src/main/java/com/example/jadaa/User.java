package com.example.jadaa;

public class User {
    private String email;
    private String password;
    private String fullName;
    private String phone;
    private String uid;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }



    public User() {
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String email, String password, String fullName, String phone) {
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.phone = phone;


    }
    public User(String email, String password, String fullName, String phone,String uid) {
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.phone = phone;
        this.uid = uid ;

    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhone() {
        return phone;
    }


}
