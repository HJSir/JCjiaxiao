package com.example.jian.jcjiaxiao.entity;

import java.io.Serializable;

/**
 * Created by jian on 2017/4/26.
 */

public class coursebean implements Serializable {
    String c_name;
    String c_number;
    String c_typecar;
    String c_typecourse;
    String c_starttime;
    String c_endtime;
    String c_statues;
    String c_do;
    String c_id;
    public String c_comment;
    String c_commenthref;

    public String getC_commenthref() {
        return c_commenthref;
    }

    public void setC_commenthref(String c_commenthref) {
        this.c_commenthref = c_commenthref;
    }

    public String getC_comment() {
        return c_comment;
    }

    public void setC_comment(String c_comment) {
        this.c_comment = c_comment;
    }

    String href;

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getC_id() {
        return c_id;
    }

    public void setC_id(String c_id) {
        this.c_id = c_id;
    }

    public String getC_name() {
        return c_name;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
    }

    public String getC_do() {
        return c_do;
    }

    public void setC_do(String c_do) {
        this.c_do = c_do;
    }

    public String getC_number() {
        return c_number;
    }

    public void setC_number(String c_number) {
        this.c_number = c_number;
    }

    public String getC_typecar() {
        return c_typecar;
    }

    public void setC_typecar(String c_typecar) {
        this.c_typecar = c_typecar;
    }

    public String getC_typecourse() {
        return c_typecourse;
    }

    public void setC_typecourse(String c_typecourse) {
        this.c_typecourse = c_typecourse;
    }

    public String getC_starttime() {
        return c_starttime;
    }

    public void setC_starttime(String c_starttime) {
        this.c_starttime = c_starttime;
    }

    public String getC_endtime() {
        return c_endtime;
    }

    public void setC_endtime(String c_endtime) {
        this.c_endtime = c_endtime;
    }

    public String getC_statues() {
        return c_statues;
    }

    public void setC_statues(String c_statues) {
        this.c_statues = c_statues;
    }
}
