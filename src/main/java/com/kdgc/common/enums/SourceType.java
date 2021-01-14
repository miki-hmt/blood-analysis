package com.kdgc.common.enums;

/**
 * @author jczhou
 * @description 资源类型
 * @date 2020/7/20
 **/
public enum SourceType {

    /**
     * JSON资源
     */
    JSON("10", "JSON"),

    /**
     * EXCEL资源
     */
    EXCEL("11", "EXCEL"),

    /**
     * CSV资源
     */
    CSV("12", "CSV"),

    /**
     * 接口资源
     */
    INTEFACE("20", "接口"),

    /**
     * 库表资源
     */
    DB_TABLE("30", "表");

    private String type;

    private String typeName;

    SourceType(String type, String typeName) {
        this.type = type;
        this.typeName = typeName;
    }

    public String getType() {
        return type;
    }

    public String getTypeName() {
        return typeName;
    }

    public static String getNameByType(String type){
        for(SourceType st : SourceType.values()){
            if(type.equals(st.getType())){
                return st.getTypeName();
            }
        }
        return "未知";
    }


}
