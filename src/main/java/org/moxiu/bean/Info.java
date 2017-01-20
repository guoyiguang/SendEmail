package org.moxiu.bean;

import java.io.Serializable;

/**
 * Created by MX-092 on 2017/1/17.
 *
 */
public class Info implements Serializable {

    private static final long serialVersionUID=1L;
    private String code="200";
    private String state="1";
    private String describe;
    public Info()
    { }
    public Info(String describe){
        super();
        this.describe=describe;
    }
    public String getCode(){return code;}
    public void setCode(String code){
        this.code=code;
    }

    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public String getDescribe() {
        return describe;
    }
    public void setDescribe(String describe) {
        this.describe = describe;
    }
    @Override
    public String toString() {
        return "Info [code=" + code + ", state=" + state + ", describe="
                + describe + "]";
    }

}

