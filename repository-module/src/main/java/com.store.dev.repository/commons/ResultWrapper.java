package com.store.dev.repository.commons;

import java.util.List;


public class ResultWrapper {

    private int status;
    private String message;
    private Object data;

    public ResultWrapper() {
    }

    public ResultWrapper(int status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static ResultWrapper success(Object data){
        return new ResultWrapper(200,"OK",data);
    }


    public static ResultWrapper error(int status,String message){
        return new ResultWrapper(status,message,null);
    }




    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
