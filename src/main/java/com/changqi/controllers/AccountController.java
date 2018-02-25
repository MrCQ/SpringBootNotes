package com.changqi.controllers;

import com.changqi.exceptions.MyException;
import com.changqi.mappers.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@CacheConfig(cacheNames = "accounts")
public class AccountController {
    @Autowired
    AccountMapper accountMapper;

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

    @RequestMapping("/get")
    @Cacheable
    public Object getByName(@RequestParam("name") String name) {
        System.out.println("若下面没出现“无缓存的时候调用”字样且能打印出数据表示测试成功");
        return accountMapper.getAccountByName(name);
    }
}
