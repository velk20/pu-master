package com.fmi.master.entities;

public class ControllerMeta {
    private Class<?> classReference;
    private String methodName;

    public ControllerMeta(Class<?> classReference, String methodName) {
        this.classReference = classReference;
        this.methodName = methodName;
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
