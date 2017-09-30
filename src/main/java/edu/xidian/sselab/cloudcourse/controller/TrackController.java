package edu.xidian.sselab.cloudcourse.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/track")
public class TrackController {
    
    @GetMapping("")
    public String track(Model model) {
        model.addAttribute("title", "轨迹重现");
        model.addAttribute("hint", "此为加分项，动手能力强的同学可以尝试完成。");
        return "track";
    }
    
}
