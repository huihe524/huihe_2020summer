package com.huihe.book.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huihe.book.model.Book;
import com.sun.deploy.net.HttpResponse;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class TestController {
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test(Model model){
        model.addAttribute("msg", "Hello World阿");
        System.out.println("Hello World");
        return "index";
    }

    @PostMapping("/test1")
    public String test1(Model model){
        model.addAttribute("msg", "Hello World");
        System.out.println("Hello World");
        return "index";
    }

    @GetMapping
    @ResponseBody
    public String test2(@RequestParam("c") String a, Integer b){
        System.out.println(a +" "+ b);
        return "Hello World!阿";
    }

    @GetMapping("/test/{id}")
    @ResponseBody
    public void test3(@PathVariable("id") String id, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setHeader("a", "b");
//        response.sendRedirect("/test");
        request.getRequestDispatcher("/test").forward(request, response);
        System.out.println(id);
//        return "Hello World!";
    }

    @GetMapping("/test4")
    public String test4(){
        return "redirect:/page/index.jsp";
    }

    @GetMapping("/test5")
    public String test5(){
        return "forward:/page/index.jsp";
    }

    @GetMapping("/test6")
    @ResponseBody
    public Book test6() {
         return new Book(1, "三体2", "刘慈欣",
                 "科幻", "每个文明都是带枪的猎人");
    }
}
