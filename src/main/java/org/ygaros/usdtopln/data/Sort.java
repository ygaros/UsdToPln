package org.ygaros.usdtopln.data;

public enum Sort {
    ASC("asc"),
    DESC("desc");

    private final String value;

    private Sort(String value){
        this.value = value;
    }
    public String getValue(){
        return value;
    }
}
