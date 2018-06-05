package com.example.backfire.myapp.bean;

/**
 * name category tag 共用bean
 * Created by backfire on 2017/11/17.
 */

public class DateBean {

    public DateBean(String name, String param){
        this.name = name;
        this.param = param;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    private String name;
    private String param;


}
