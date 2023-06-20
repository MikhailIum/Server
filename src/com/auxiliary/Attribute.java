package com.auxiliary;

public class Attribute {
    String name;
    String type;

    String additionalParams;

    public Attribute(String name, String type, String additionalParams){
        this.name = name;
        this.type = type;
        this.additionalParams = additionalParams;
    }

    public static Attribute createAttribute(String name, String type){
        return new Attribute(name, type, null);
    }

    public static Attribute createAttribute(String name, String type, String additionalParams){
        return new Attribute(name, type, additionalParams);
    }
}
