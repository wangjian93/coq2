package com.ivo.common.enums;

import lombok.Getter;

/**
 * 机种阶段枚举
 * @author wj
 * @version 1.0
 */
@Getter
public enum StageEnum {

    NPRB(0, "NPRB"),
    Design(1, "DESIGN"),
    EVT(2, "EVT"),
    DVT(3, "DVT"),
    PVT(4, "PVT"),
    MP(5, "MP");

    private int sort;
    private String stage;
    public static final String[] STAGES = {"NPRB", "DESIGN", "EVT", "DVT", "PVT", "MP"};

    StageEnum(int sort, String stage) {
        this.sort = sort;
        this.stage = stage;
    }
}
