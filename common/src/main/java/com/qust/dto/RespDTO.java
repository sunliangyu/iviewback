package com.qust.dto;

import java.io.Serializable;

public class RespDTO<T> implements Serializable{


    public int code = 0;
    public String error = "";

    @Override
    public String toString() {
        return "RespDTO{" +
                "code=" + code +
                ", error='" + error + '\'' +
                '}';
    }
}
