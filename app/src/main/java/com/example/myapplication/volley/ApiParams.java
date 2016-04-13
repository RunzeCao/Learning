package com.example.myapplication.volley;

import java.util.HashMap;

/**
 * Created by 123 on 2016/4/13.
 */
public class ApiParams extends HashMap<String,String> {

    public ApiParams with(String key,String value){
        put(key,value);
        return this;
    }
}
