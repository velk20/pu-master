package com.fmi.master.system;

import com.fmi.master.entities.ControllerMeta;
import com.fmi.master.entities.RequestInfo;
import com.fmi.master.steriotypes.Autowired;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class HttpProcessor {
    private final ApplicationLoader appLoader = ApplicationLoader.getInstance();

    public String executeController(RequestInfo httpRequest)
            throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String httpMethod = httpRequest.getHttpMethod();
        String httpRequestEndpoint = httpRequest.getHttpEndpoint();
        ControllerMeta controllerMethodReference = null;

        for (RequestInfo requestInfoControllerMetaEntry : this.appLoader.getControllerLookupTable().keySet()) {

            if(requestInfoControllerMetaEntry.isProcessable(httpMethod, httpRequestEndpoint)){
                controllerMethodReference = this.appLoader.getControllerLookupTable()
                        .get(requestInfoControllerMetaEntry);
                httpRequest.setPathVariables(requestInfoControllerMetaEntry.getPathVariables());
                break;
            }
        }

        if(controllerMethodReference == null) {
            return "Internal Server Error";
        }

        Class<?> clazz = controllerMethodReference.getClassReference();
        String methodName = controllerMethodReference.getMethodName();
        Class<?>[] methodSignature = this.buildMethodParameterTypes(controllerMethodReference);
        Object[] arguments = this.buildMethodArguments(controllerMethodReference, httpRequest);

        var controllerInstance = clazz.getDeclaredConstructor().newInstance();

        processAutowiredServices(controllerInstance);


        return (String) clazz
                .getMethod(methodName, methodSignature)
                .invoke(controllerInstance, arguments);
    }

    private void processAutowiredServices(Object rootInstance) throws IllegalAccessException {
        //DI Impl
        Field[] fieldCollection = rootInstance.getClass().getDeclaredFields();
        for (Field field : fieldCollection) {
            if (field.isAnnotationPresent(Autowired.class)) {
                Autowired autowiredAnnotation = field.getAnnotation(Autowired.class);
                boolean isSingleton = autowiredAnnotation.isSingleton();


                field.setAccessible(true);
                Class<?> injectableMaterial = field.getType();
                Object injectableInstance = this.appLoader.getInjectable(injectableMaterial, isSingleton);

                field.set(rootInstance, injectableInstance);

                processAutowiredServices(injectableInstance);
            }
        }
    }

    private Class<?>[] buildMethodParameterTypes(ControllerMeta controllerMeta){
        Map<Integer, String> pathVariableIndex = controllerMeta.getPathVariableIndex();
        Class<?>[] parameterTypes = new Class<?>[pathVariableIndex.size()];

        for (Integer i : pathVariableIndex.keySet()) {
            parameterTypes[i] = int.class;

        }

        return parameterTypes;
    }


    private Object[] buildMethodArguments(ControllerMeta controllerMeta, RequestInfo requestInfo){
        Map<Integer, String> pathVariableIndex = controllerMeta.getPathVariableIndex();
        Map<String, String> pathVariables = requestInfo.getPathVariables();
        Object[] arguments = new Object[pathVariableIndex.size()];

        for (Integer index : pathVariableIndex.keySet()) {
            String variableName = pathVariableIndex.get(index);
            String variableValue = pathVariables.get(variableName);

            if (variableValue != null) {
                arguments[index] = Integer.parseInt(variableValue);
            }
        }

        return arguments;
    }
}
