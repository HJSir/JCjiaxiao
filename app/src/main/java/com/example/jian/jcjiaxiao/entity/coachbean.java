package com.example.jian.jcjiaxiao.entity;

import java.io.Serializable;

/**
 * Created by jian on 2017/4/18.
 */

public class coachbean implements Serializable {
    private String  sid; //序号
    private String  number;//电话号码
    private String name;
    private String plate;//车牌
    private String sex;
    private String status;//状态
    private String price;
    private String xueshi;
    private String href;

    public String getIs() {
        return is;
    }

    public void setIs(String is) {
        this.is = is;
    }

    private String is;
private String comment;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTypeforcourse() {
        return typeforcourse;
    }

    public void setTypeforcourse(String typeforcourse) {
        this.typeforcourse = typeforcourse;
    }

    private String typeforcar;
private String typeforcourse;
    public String getTypeforcar() {
        return typeforcar;
    }

    public void setTypeforcar(String typeforcar) {
        this.typeforcar = typeforcar;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getXueshi() {
        return xueshi;
    }

    public void setXueshi(String xueshi) {
        this.xueshi = xueshi;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
