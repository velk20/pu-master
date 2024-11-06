package com.fmi.master.system;

import com.fmi.master.steriotypes.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ApplicationLoader {
    private Map<RequestInfo, Class<?>> controllerLookupTable = new HashMap<>();
    private ClassLoader classLoader = ClassLoader.getSystemClassLoader();

    public String executeController(String httpMethod, String httpEndpoint)
            throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> clazz = this.controllerLookupTable.get(new RequestInfo(httpMethod, httpEndpoint));

        if(clazz == null) {
            return "";
        }

        var controllerInstance = clazz.getDeclaredConstructor().newInstance();
        return (String) clazz.getMethod("index").invoke(controllerInstance);
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
                String className = packageReference.replace(".class", "");
                String fullClassName = packageName + "." + className;
                Class<?> clazz = Class.forName(fullClassName);

                if (clazz.isAnnotationPresent(Controller.class)) {
                    Controller annotation = clazz.getAnnotation(Controller.class);
                    String httpEndpoint = annotation.endpoint();
                    String httpMethod = annotation.method();

                    this.controllerLookupTable.put(new RequestInfo(httpEndpoint, httpMethod), clazz);

                }
            }
        }

    }


    public static class RequestInfo {
        private String httpMethod;
        private String httpEndpoint;

        public RequestInfo(String httpMethod, String httpEndpoint) {
            this.httpMethod = httpMethod;
            this.httpEndpoint = httpEndpoint;
        }

        public String getHttpMethod() {
            return httpMethod;
        }

        public String getHttpEndpoint() {
            return httpEndpoint;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            RequestInfo that = (RequestInfo) o;
            return Objects.equals(httpMethod, that.httpMethod) && Objects.equals(httpEndpoint, that.httpEndpoint);
        }

        @Override
        public int hashCode() {
            return Objects.hash(httpMethod, httpEndpoint);
        }
    }
}
