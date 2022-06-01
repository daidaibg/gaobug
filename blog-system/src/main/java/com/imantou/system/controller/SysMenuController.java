package com.imantou.system.controller;

import com.imantou.response.ResponseWrapped;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("menu")
public class SysMenuController {

    @GetMapping("nav")
    public ResponseWrapped<Object> nav() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("menuList", "");
        result.put("permissions", "");
        return ResponseWrapped.success(result);
    }

}
