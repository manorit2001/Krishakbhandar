package com.example.mapstest.data.model;

public class UserPass {
    int _id;
    String _email;
    String _password;
    public UserPass(){}
    public UserPass(int id,String email,String pass)
    {
        this._id=id;
        this._email=email;
        this._password=pass;
    }

    public int get_id() {
        return _id;
    }

    public void set_email(String _email) {
        this._email = _email;
    }

    public String get_password() {
        return _password;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_email() {
        return _email;
    }

    public void set_password(String _password) {
        this._password = _password;
    }
}
