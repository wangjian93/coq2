package com.ivo.coq.project.entity;

import com.ivo.common.enums.StageEnum;
import com.ivo.common.model.AutoIncreaseEntityModel;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 机种的阶段
 * @author wj
 * @version 1.0
 */
@Entity
@Table(name = "coq_project_stage")
@Setter
@Getter
public class Stage extends AutoIncreaseEntityModel implements Comparable<Stage> {

    /**
     * 机种名
     */
    private String project;

    /**
     * 阶段
     */
    private String stage;

    /**
     * 阶段次数
     */
    private Integer time;

    public Stage() {}

    public Stage(String project, String stage, Integer time) {
        this.project = project.trim().toUpperCase();
        this.stage = stage.trim().toUpperCase();
        this.time = time;
    }

    @Override
    public int compareTo(Stage o) {
        String str1 = compareInt(stage) + stage + time;
        String str2 = compareInt(o.getStage()) + o.getStage() + o.getTime();
        return str1.compareTo(str2);
    }

    /**
     * 比较大小根据StageEnum做判断
     * @param stage 阶段
     * @return
     */
    private static int compareInt(String stage) {
        for(StageEnum stageEnum : StageEnum.values()) {
            if(StringUtils.equalsIgnoreCase(stageEnum.getStage(), stage)) {
                return stageEnum.getSort();
            }
        }
        return 9999 ;
    }
}
