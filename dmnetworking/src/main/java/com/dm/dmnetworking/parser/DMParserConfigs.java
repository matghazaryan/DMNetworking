package com.dm.dmnetworking.parser;

public class DMParserConfigs<T> {

    private Class<T> aClass;
    private String[] jsonKeyList;

    public DMParserConfigs(final Class<T> aClass, final String... jsonKeyList) {
        this.aClass = aClass;
        this.jsonKeyList = jsonKeyList;
    }

    public Class<T> getAClass() {
        return aClass;
    }

    public String[] getJsonKeyList() {
        return jsonKeyList;
    }
}
