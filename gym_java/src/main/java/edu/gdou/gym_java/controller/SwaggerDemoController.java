package edu.gdou.gym_java.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@Api
@RestController
public class SwaggerDemoController {
    @ApiOperation(value = "hello world 接口")
    @GetMapping("hello")
    public String hello() {
        return "hello world";
    }

    @ApiOperation(value = "有参接口")
    @PostMapping("demo")
    public String demo(@ApiParam(name = "name", value = "村雨遥", required = true) String name) {
        return "hello," + name;
    }
}

