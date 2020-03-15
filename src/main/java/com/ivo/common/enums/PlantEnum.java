package com.ivo.common.enums;

/**
 * 工厂类型枚举
 * @author wj
 * @version 1.0
 */
public enum  PlantEnum {

    Array("ARRAY", "阵列厂"),
    Cell("CELL", "面板厂"),
    Lcm("LCM", "模组厂"),
    Lcm1("LCM1", "模组1"),
    Lcm2("LCM2", "模组2");

    private String plant;
    private String description;

    PlantEnum(String plant, String description) {
        this.plant = plant;
        this.description = description;
    }

    public String getPlant() {
        return plant;
    }

    public String getDescription() {
        return description;
    }
}
