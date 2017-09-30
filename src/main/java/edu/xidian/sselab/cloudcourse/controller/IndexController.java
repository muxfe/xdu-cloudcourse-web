package edu.xidian.sselab.cloudcourse.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexController {
    
    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("title", "项目介绍");
        return "index";
    }
    
    @GetMapping("bmap")
    public String mapview(Model model) {
        model.addAttribute("title", "百度地图示例");
        return "bmap";
    }
    
}
