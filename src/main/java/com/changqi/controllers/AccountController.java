package com.changqi.controllers;

import com.changqi.exceptions.MyException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AccountController {
    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/json")
    public Object json() {
        Map<String, Integer> map = new HashMap<>();
        map.put("a",1);
        map.put("b",2);
        map.put("c",3);
        return map;
    }

    @RequestMapping("/err")
    public String error() throws Exception{
        throw new MyException("error test");
    }
}
