package com.xiao.pharmacy.core.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @Author: xiaoxiongwen
 * @Date: 2023/09/14/16:35
 * @Description:
 */
//@Controller
@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("getData")
    public String getData(){
        return "sueccess";
    }

}
