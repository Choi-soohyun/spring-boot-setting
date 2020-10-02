package com.example.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
    
    @GetMapping("hello")
    public String hello(Model model) {
        model.addAttribute("data", "Srping!!!");
        // templates 안에 있는 html로 접근, hello.html
        return "hello";
    }

    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam(value="name", required=false) String name, Model model) {
        // required=false -> 파마리터 없어도 된다라는 가이드
        model.addAttribute("name", name);

        // templates 안에 있는 html로 접근, hello-template.html
        return "hello-template"; // View Resolver 에서 view 파일을 찾아준다.
    }

    // http://localhost:8080/hello-spring?name=string!!!!
    @GetMapping("hello-spring")
    @ResponseBody // http 통신에서 header와 body가 있는데, body부에 return 한 값을 직접 넣는다.
    public String helloString(@RequestParam("name") String name) {
        // 경로 : localhost:8080/hello-string?name=zzzzzzzz
        // 개발자도구에서 dom이 어떻게 나오나 확인하기
        // <pre>hello zzzzz</pre> 나온 것을 확인 할 수 있따.
        // 즉, 이걸 쓸일은 거의 없다.
        return "hello " + name;
    }

    @GetMapping("hello-api")
    @ResponseBody // 다 json 방식으로 반환하는 것이 기본이 되었다. xml로 변경도 가능하다.
    public Hello helloApi(@RequestParam("name") String name) {
        Hello hello = new Hello();
        hello.setName(name);

        // ResponseBody가 있으면, ViewResolver가 역할하지 않고
        // HttpMessageconverter가 동작한다.
        // 객체면 JsonConverter(MappingJackson2HttpMessageConverter Class)
        // 일반 문자면 StringConverter(StringHttpMessageConverter) 가 동작을 하여
        // 반환한다.
        return hello;
    }

    static class Hello {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
