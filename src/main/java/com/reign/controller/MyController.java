package com.reign.controller;

import com.reign.core.annotations.AutowireAnnotation;
import com.reign.core.annotations.ControllerAnnotation;
import com.reign.core.annotations.RequestMappingAnnotation;
import com.reign.core.annotations.RequestParamAnnotation;
import com.reign.service.MyService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName: ControllerAnnotation
 * @Description: TODO
 * @Author: wuwx
 * @Date: 2019-08-27 13:33
 **/
@ControllerAnnotation
@RequestMappingAnnotation("/demo")
public class MyController {

    @AutowireAnnotation
    private MyService myService;

    @RequestMappingAnnotation("/query")
    public void query(HttpServletRequest req, HttpServletResponse rep,
                      @RequestParamAnnotation("name") String name){
        String result = myService.get(name);
        try {
            rep.getWriter().write(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
