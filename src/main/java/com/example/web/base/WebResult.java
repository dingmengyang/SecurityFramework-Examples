package com.example.web.base;

import java.util.Collection;
import java.util.List;

public class WebResult {

    private int code;
    private String msg;
    private int count;
    private Object data;

    public static WebResult exceptionWebResult(int code,String msg){
        WebResult webResult=new WebResult();
        webResult.setCode(code);
        webResult.setMsg(msg);
        return webResult;
    }

    public static WebResult errorWebResult(String msg){
        WebResult webResult=new WebResult();
        webResult.setCode(Code.ERROR.getValue());
        webResult.setMsg(msg);
        return webResult;
    }

    public static WebResult successWebResult(Object data){
        WebResult webResult=new WebResult();
        webResult.setCode(Code.SUCCESS.getValue());
        webResult.setData(data);
        if (data instanceof Collection){
            webResult.setCount(((Collection)data).size());
        }
        return webResult;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    enum Code{
        SUCCESS(0),
        ERROR(1);

        private int value;

        Code(int i) {
            this.value=i;
        }

        public int getValue(){
            return this.value;
        }
    }
}
