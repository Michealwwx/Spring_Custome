package com.reign.service.impl;

import com.reign.core.annotations.ServiceAnnotation;
import com.reign.service.MyService;

/**
 * @ClassName: MyServiceImpl
 * @Description: impl
 * @Author: wuwx
 * @Date: 2019-08-27 14:27
 **/
@ServiceAnnotation
public class MyServiceImpl implements MyService {
    public String get(String name) {
        return "My Name Is "+name;
    }
}
