package com.example.mapstest.data.model;

public class UserPass {
    int _id;
    String _email;
    String _password;
    String _type;
    public UserPass(){}
    public UserPass(int id,String email,String pass,String type)
    {
        this._id=id;
        this._email=email;
        this._type=type;
        this._password=pass;
    }

    public int get_id() {
        return _id;
    }

    public String get_type() {
        return _type;
    }

    public void set_type(String _type) {
        this._type = _type;
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
