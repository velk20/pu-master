package com.fmi.master;

import java.lang.reflect.Field;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws IllegalAccessException {
        Item myItem = new Item();
        Customer customer = new Customer();

        System.out.println(parseObjectToXml(myItem));
        System.out.println(parseObjectToJson(customer));
    }

    public static String parseObjectToJson(Object parsableObject) throws IllegalAccessException {
        Class<?> parsableObjectClass = parsableObject.getClass();
        if (!parsableObjectClass.isAnnotationPresent(JSONEntity.class)) {
            return "{}";
        }

        Field[] fieldCollection = parsableObjectClass.getDeclaredFields();

        StringBuilder jsonBuilder = new StringBuilder().append("{");

        for (Field field : fieldCollection) {

            field.setAccessible(true);

            boolean annotationPresent = field.isAnnotationPresent(JsonField.class);
            if (annotationPresent) {

                boolean holdsedNumericType = holdsNumericType(field);

                jsonBuilder
                        .append(field.getName())
                        .append(":")
                        .append(holdsedNumericType
                                ? field.get(parsableObject)
                                : "\""+field.get(parsableObject)+"\"")
                        .append(field == fieldCollection[fieldCollection.length - 1] ? "" : ",")
                        ;

            }

        }
        jsonBuilder.append("}");
        return jsonBuilder.toString();
    }

    public static String parseObjectToXml(Object parsableObject) throws IllegalAccessException {
        Class<?> parsableObjectClass = parsableObject.getClass();
        Field[] fieldCollection = parsableObjectClass.getDeclaredFields();

        StringBuilder xmlBuilder = new StringBuilder();

        for (Field field : fieldCollection) {

            field.setAccessible(true);
            boolean annotationPresent = field.isAnnotationPresent(Documentable.class);
            if (annotationPresent) {

                Documentable documentableAnnotation = field.getAnnotation(Documentable.class);
                String fieldName = documentableAnnotation.title().equals("_")
                        ? field.getName()
                        : documentableAnnotation.title();

                xmlBuilder.append("<")
                        .append(fieldName)
                        .append(">")
                        .append(field.get(parsableObject))
                        .append("<")
                        .append(fieldName)
                        .append(">");
            }

        }
        return xmlBuilder.toString();

    }

    private static final Set<Class<?>> primitiveNumbers = Stream
            .of(int.class, long.class, float.class,
                    double.class, byte.class, short.class)
            .collect(Collectors.toSet());

    private static boolean isNumericType(Class<?> cls) {
        if (cls.isPrimitive()) {
            return primitiveNumbers.contains(cls);
        } else {
            return Number.class.isAssignableFrom(cls);
        }
    }

    private static boolean holdsNumericType(Field f) {
        return isNumericType(f.getType());
    }
}