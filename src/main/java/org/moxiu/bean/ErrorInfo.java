package org.moxiu.bean;

import java.io.Serializable;

/**
 * Created by MX-092 on 2017/1/17.
 * 三个参数 code,describe state
 */

public class ErrorInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    private String code="-200";
    private String state="-1";
    private String describe;
    //空构造函数
    public ErrorInfo()
    { }
    //带一个参数的构造函数
    public ErrorInfo(String describe)
    {
        super();
        this.describe=describe;
    }

    public String getState() {
        return state;
    }

    public String getCode() {

        return code;
    }

    public String getDescribe() {
        return describe;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    @Override
    public String toString(){
        return "EroorInfo [code="+code+",state="+state+",describe="+describe+"]";
    }
}
