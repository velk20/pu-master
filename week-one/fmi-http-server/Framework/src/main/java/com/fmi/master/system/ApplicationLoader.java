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
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

public class ApplicationLoader {
    private static ApplicationLoader instance = null;

    private ClassLoader classLoader = ClassLoader.getSystemClassLoader();
    private Map<RequestInfo, ControllerMeta> controllerLookupTable = new HashMap<>();


    private ApplicationLoader() {
    }

    public static ApplicationLoader getInstance() {
        if (instance == null) {
            instance = new ApplicationLoader();
        }
        return instance;
    }

    public Map<RequestInfo, ControllerMeta> getControllerLookupTable() {
        return controllerLookupTable;
    }

    public ControllerMeta getControllerMeta(RequestInfo requestInfo) {
        return controllerLookupTable.get(requestInfo);
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
                Map<Integer, String> pathVariableIndex = new HashMap<>();
                int counter = 0;

                GetMapping annotation = method.getAnnotation(GetMapping.class);
                String methodEndpoint = annotation.value();
                String methodName = method.getName();

                Parameter[] methodParameters = method.getParameters();
                for (Parameter methodParameter : methodParameters) {
                    if (methodParameter.isAnnotationPresent(PathVariable.class)) {
                        Class<?> type = methodParameter.getType();

                        PathVariable pathVariable = methodParameter.getAnnotation(PathVariable.class);
                        pathVariableIndex.put(counter++, pathVariable.value());
                    }
                }
                this.controllerLookupTable.put(
                        new RequestInfo("GET", methodEndpoint),
                        new ControllerMeta(clazz, methodName));
            }

            parsePostController(clazz, method);

            parsePutController(clazz, method);

            parseDeleteController(clazz, method);
        }
    }

    private void parsePostController(Class<?> clazz, Method method) {
        if (method.isAnnotationPresent(PostMapping.class)) {
            PostMapping annotation = method.getAnnotation(PostMapping.class);
            String methodEndpoint = annotation.value();
            String methodName = method.getName();

            this.controllerLookupTable.put(new RequestInfo("POST", methodEndpoint), new ControllerMeta(clazz, methodName));
        }
    }

    private void parsePutController(Class<?> clazz, Method method) {
        if (method.isAnnotationPresent(PutMapping.class)) {
            PutMapping annotation = method.getAnnotation(PutMapping.class);
            String methodEndpoint = annotation.value();
            String methodName = method.getName();

            this.controllerLookupTable.put(new RequestInfo("PUT", methodEndpoint), new ControllerMeta(clazz, methodName));
        }
    }

    private void parseDeleteController(Class<?> clazz, Method method) {
        if (method.isAnnotationPresent(DeleteMapping.class)) {
            DeleteMapping annotation = method.getAnnotation(DeleteMapping.class);
            String methodEndpoint = annotation.value();
            String methodName = method.getName();

            this.controllerLookupTable.put(new RequestInfo("DELETE", methodEndpoint), new ControllerMeta(clazz, methodName));
        }
    }


}
