package com.example.mapstest;

public class WarehouseModel {
    String _name;
    String _dist;
    int _id;
    int _image;

    public WarehouseModel(String s, String s1, Integer integer, Integer integer1) {
        this._name=s;
        this._dist=s1;
        this._id=integer;
        this._image=integer1;
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
