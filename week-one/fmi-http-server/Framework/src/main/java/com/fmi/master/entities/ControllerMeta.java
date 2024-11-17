package com.fmi.master.entities;

import java.util.HashMap;
import java.util.Map;

public class ControllerMeta {
    private Class<?> classReference;
    private String methodName;
    private Map<Integer, String> pathVariableIndex = new HashMap<>();

    public ControllerMeta(Class<?> classReference, String methodName) {
        this.classReference = classReference;
        this.methodName = methodName;
    }

    public ControllerMeta(Class<?> classReference, String methodName, Map<Integer, String> pathVariableIndex) {
        this.classReference = classReference;
        this.methodName = methodName;
        this.pathVariableIndex = pathVariableIndex;
    }

    public Map<Integer,String> getPathVariableIndex() {
        return pathVariableIndex;
    }

    public Class<?> getClassReference() {
        return classReference;
    }

    public ControllerMeta setClassReference(Class<?> classReference) {
        this.classReference = classReference;
        return this;
    }

    public String getMethodName() {
        return methodName;
    }

    public ControllerMeta setMethodName(String methodName) {
        this.methodName = methodName;
        return this;
    }
}
