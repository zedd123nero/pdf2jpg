package com.example.pdf2jpg.demos.dto;

import lombok.Data;

/**
 * kafka数据源DTO类
 */

@Data
public class JobDatasourceKafkaDto {

    /**
     * 数据源名称
     */

    private String datasourceName;

    /**
     * brokeList
     */

    private String brokeList;

    /**
     * topic
     */

    private String topic;

    /**
     * group
     */

    private String grp;

    /**
     * remark
     */

    private String remark;

    /**
     * 所属业务域ID
     */

    private Long nodeId;

    /**
     * 所属业务域ID
     */
    private String nodeName;

}