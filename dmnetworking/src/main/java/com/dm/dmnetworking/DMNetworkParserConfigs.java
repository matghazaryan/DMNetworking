package com.dm.dmnetworking;

public final class DMNetworkParserConfigs<T> {

    private Class<T> aClass;
    private String[] jsonKeyList;

    public DMNetworkParserConfigs(final Class<T> aClass, final String... jsonKeyList) {
        this.aClass = aClass;
        this.jsonKeyList = jsonKeyList;
    }

    public DMNetworkParserConfigs(final String... jsonKeyList) {
        this.jsonKeyList = jsonKeyList;
    }

    Class<T> getAClass() {
        return aClass;
    }

    String[] getJsonKeyList() {
        return jsonKeyList;
    }
}
