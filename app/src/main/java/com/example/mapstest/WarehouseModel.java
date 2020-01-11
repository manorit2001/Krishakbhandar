package com.example.mapstest;

public class WarehouseModel {
    String _name;
    String _dist;
    int _id;
    int _image;
    String _loc;

    public WarehouseModel(String s, String s1, Integer integer, Integer integer1,String loc) {
        this._name=s;
        this._dist=s1;
        this._id=integer;
        this._image=integer1;
        this._loc=loc;
    }

    public void set_loc(String _loc) {
        this._loc = _loc;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_loc() {
        return _loc;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int get_image() {
        return _image;
    }

    public String get_dist() {
        return _dist;
    }

    public String get_name() {
        return _name;
    }
}
