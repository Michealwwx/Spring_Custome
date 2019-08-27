package com.reign.core.servlets;

import com.reign.core.annotations.ControllerAnnotation;
import com.reign.core.annotations.ServiceAnnotation;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

/**
 * @ClassName: MyDispatcherServlet
 * @Description: 自定义的分发Servlet
 * @Author: wuwx
 * @Date: 2019-08-27 13:30
 **/
public class MyDispatcherServlet extends HttpServlet {

    //保存application.properties配置文件中的内容；
    private Properties contextConfig = new Properties();

    //存储所有扫描到的class
    private List<String> classList = new ArrayList<>();

    //IOC容器；
    private Map<String, Object> iocMap = new HashMap<>();


    //初始化
    @Override
    public void init(ServletConfig config) throws ServletException {

        //1.通过web.xml中配置的该servlet需要初始化的init-param去加载配置文件；
        doParseConfigFile(config.getInitParameter("contextConfigLocation"));
        //2.获取第一步中配置文件中配置的需要扫描的类
        doScan(contextConfig.getProperty("scanPackage"));
        //3.初始化上一步扫描的类;并加入到容器中；
        doInstanceAndIOC();
        //4.完成依赖注入；
        doAutowired();
        //5.初始化HandlerMapping
        initHandlerMapping();

    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doDispatch();
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }


    private void doDispatch() {

        //6.根据url在HandlerMapping中定位method，使用反射来处理请求

    }


    /**
     * 1.解析servlet配置的初始化参数
     */
    private void doParseConfigFile(String param) {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(param);
        try {
            contextConfig.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 2.扫描配置文件中配置的扫描路径下面的所有类，不管是否有注解修饰；
     *
     * @param scanPackage
     */
    private void doScan(String scanPackage) {
        //获取配置文件中要扫描的包路径；
        URL url = this.getClass().getClassLoader().getResource("/" + scanPackage.replaceAll("\\.", "/"));
        File classPath = new File(url.getFile());
        //获取配置文件中配置的扫描路径下的所有文件夹和文件；
        for (File file : classPath.listFiles()) {
            if (file.isDirectory()) {
                doScan(scanPackage + "." + file.getName());
            } else {
                //处理扫描class文件
                if (!file.getName().endsWith(".class")) continue;
                String className = scanPackage + "." + file.getName().replace(".class", "");
                classList.add(className);
            }
        }
    }


    /**
     * 3.实例化类并将其加入到IOC容器中
     */
    private void doInstanceAndIOC() {
        if (classList.size() == 0) {
            return;
        }
        try {
            for (String className : classList) {
                Class<?> clazz = Class.forName(className);
                //只将注解修饰的类加入到IOC容器中；
                if (clazz.isAnnotationPresent(ControllerAnnotation.class)) {
                    Object instance = clazz.newInstance();
                    //默认springbean首字母小写；
                    className = getLowerCase(clazz.getSimpleName());
                    //判断注解上是否指定了beanName
                    ControllerAnnotation controllerAnnotation = clazz.getAnnotation(ControllerAnnotation.class);
                    if (controllerAnnotation.value() != "") className = controllerAnnotation.value();
                    iocMap.put(className, instance);
                } else if (clazz.isAnnotationPresent(ServiceAnnotation.class)) {
                    Object instance = clazz.newInstance();
                    //默认springbean首字母小写；
                    className = getLowerCase(clazz.getSimpleName());
                    //判断注解上是否指定了beanName
                    ServiceAnnotation controllerAnnotation = clazz.getAnnotation(ServiceAnnotation.class);
                    if (!controllerAnnotation.value().trim().equals("")) className = controllerAnnotation.value();
                    iocMap.put(className, instance);

                    //TODO 需要考虑的问题
                    /**
                     * 1.A，B为接口，C实现了A和B接口，那么注入A接口和C接口
                     * 2.A为接口，B和C都是A接口的实现类，那么注入A接口如何判断具体类型是B还是C
                     */
                    for (Class<?> anInterface : clazz.getInterfaces()) {
                        if (iocMap.containsKey(anInterface.getName())) {
                            throw new Exception("The" + anInterface.getSimpleName() + "is exist!");
                        }
                        //把接口的类型直接当成key
                        iocMap.put(anInterface.getName(), instance);
                    }
                } else {
                    continue;
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    /**
     * 利用小写字母比大写字母的asnic码小32的特性；
     *
     * @param className
     * @return
     */
    public String getLowerCase(String className) {
        char[] chars = className.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }

    /**
     * 4.依赖注入
     */
    private void doAutowired() {


    }

    /**
     * 5.初始化handlerMapping
     */
    private void initHandlerMapping() {
    }


}
