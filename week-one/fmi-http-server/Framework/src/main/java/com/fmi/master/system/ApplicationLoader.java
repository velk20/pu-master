package com.fmi.master.system;

import com.fmi.master.entities.ControllerMeta;
import com.fmi.master.entities.RequestInfo;
import com.fmi.master.steriotypes.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ApplicationLoader {
    private ClassLoader classLoader = ClassLoader.getSystemClassLoader();
    private Map<RequestInfo, ControllerMeta> controllerLookupTable = new HashMap<>();

    public String executeController(String httpMethod, String httpEndpoint)
            throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
       ControllerMeta controllerMethodReference = this.controllerLookupTable.get(new RequestInfo(httpMethod, httpEndpoint));

        if(controllerMethodReference == null) {
            return "";
        }

        Class<?> clazz = controllerMethodReference.getClassReference();
        String methodName = controllerMethodReference.getMethodName();

        var controllerInstance = clazz.getDeclaredConstructor().newInstance();
        return (String) clazz.getMethod(methodName).invoke(controllerInstance);
    }


    // 1. Search the code and find all annotations
    // searching for classes from the packages
    public void findAllClasses(String packageName) throws IOException, ClassNotFoundException {
        InputStream classLoaderStream = this.classLoader.getResourceAsStream(packageName.replace('.', '/'));
        BufferedReader classReader = new BufferedReader(new InputStreamReader(classLoaderStream));

        String packageReference = "";
        while ((packageReference = classReader.readLine()) != null) {

            if (!packageReference.contains(".class")) {
                findAllClasses(packageName + "." + packageReference);
                continue;
            }

            if (packageReference.contains(".class")) {
                classParser(packageReference, packageName);
            }
        }

    }


    private void classParser(String packageReference, String packageName) throws ClassNotFoundException {
        String className = packageReference.replace(".class", "");
        String fullClassName = packageName + "." + className;
        Class<?> clazz = Class.forName(fullClassName);
        if (clazz.isAnnotationPresent(Controller.class)) {
            parseController(clazz);
        }
    }

    private void parseController(Class<?> clazz) {
        Method[] controllerClassMethodCollection = clazz.getMethods();
        for (Method method : controllerClassMethodCollection) {
            if (method.isAnnotationPresent(GetMapping.class)) {
                GetMapping annotation = method.getAnnotation(GetMapping.class);
                String methodEndpoint = annotation.value();
                String methodName = method.getName();

                this.controllerLookupTable.put(new RequestInfo("GET", methodEndpoint), new ControllerMeta(clazz, methodName));
            }

            if (method.isAnnotationPresent(PostMapping.class)) {
                PostMapping annotation = method.getAnnotation(PostMapping.class);
                String methodEndpoint = annotation.value();
                String methodName = method.getName();

                this.controllerLookupTable.put(new RequestInfo("POST", methodEndpoint), new ControllerMeta(clazz, methodName));
            }

            if (method.isAnnotationPresent(PutMapping.class)) {
                PutMapping annotation = method.getAnnotation(PutMapping.class);
                String methodEndpoint = annotation.value();
                String methodName = method.getName();

                this.controllerLookupTable.put(new RequestInfo("PUT", methodEndpoint), new ControllerMeta(clazz, methodName));
            }

            if (method.isAnnotationPresent(DeleteMapping.class)) {
                DeleteMapping annotation = method.getAnnotation(DeleteMapping.class);
                String methodEndpoint = annotation.value();
                String methodName = method.getName();

                this.controllerLookupTable.put(new RequestInfo("DELETE", methodEndpoint), new ControllerMeta(clazz, methodName));
            }
        }
//        Controller annotation = clazz.getAnnotation(Controller.class);
//        String httpEndpoint = annotation.endpoint();
//        String httpMethod = annotation.method();
//
//        this.controllerLookupTable.put(new RequestInfo(httpMethod, httpEndpoint), clazz);
    }


}
