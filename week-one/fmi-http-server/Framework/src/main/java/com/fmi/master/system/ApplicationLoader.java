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
    private Map<Class<?>, Object> injectableLookupTable = new HashMap<>();

    private ApplicationLoader() {
    }

    public static ApplicationLoader getInstance() {
        if (instance == null) {
            instance = new ApplicationLoader();
        }
        return instance;
    }

    public Object getInjectable(Class<?> clazz, boolean isSingleton) {
        if (isSingleton) {
            return getInjectable(clazz);
        }

        if (this.injectableLookupTable.containsKey(clazz)) {
            return this.getInjectableInstance(clazz);
        }

        return null;
    }

    public Object getInjectable(Class<?> clazz) {

        Object resultInstance = this.injectableLookupTable.get(clazz);

        if (resultInstance == null) {
            resultInstance = getInjectableInstance(clazz);
            injectableLookupTable.put(clazz, resultInstance);
        }

        return resultInstance;
    }

    private Object getInjectableInstance(Class<?> clazz) {
        Object resultInstance;
        try {
            resultInstance = clazz.getDeclaredConstructor().newInstance();
            return resultInstance;
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<Class<?>, Object> getInjectableLookupTable() {
        return injectableLookupTable;
    }

    public ApplicationLoader setInjectableLookupTable(Map<Class<?>, Object> injectableLookupTable) {
        this.injectableLookupTable = injectableLookupTable;
        return this;
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

        if (clazz.isAnnotationPresent(Injectable.class)) {
            parseInjectable(clazz);
        }
    }

    private void parseInjectable(Class<?> clazz) {
        this.injectableLookupTable.put(clazz, null);
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

                Parameter[] parameters = method.getParameters();
                for (int i = 0; i < parameters.length; i++) {
                    var parameter = parameters[i];
                    // get type here

                    if (parameter.isAnnotationPresent(PathVariable.class)) {
                        var pathVariable = parameter.getAnnotation(PathVariable.class);
                        // object here value, type
                        pathVariableIndex.put(i, pathVariable.value());
                    }
                }
                this.controllerLookupTable.put(
                        new RequestInfo("GET", methodEndpoint),
                        new ControllerMeta(clazz, methodName, pathVariableIndex));
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
