package org.csu.mavendemo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author: Guanyu-Cai
 * @Date: 03/08/2020
 */
@Controller
public class DemoController {
    @GetMapping("/hello")
    @ResponseBody
    public String hello() {
        return "welcome to OUOStore";
    }
}
