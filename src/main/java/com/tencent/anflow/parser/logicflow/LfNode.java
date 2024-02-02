package com.tencent.anflow.parser.logicflow;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * logicFlow节点
 */
@Data
public class LfNode {
    private String id;
    private String type;
    private Integer x;
    private Integer y;
    private Map<String, Object> properties;
    private TextEntity text;
    private List<String> children;

    /**
     * 组件文本域
     */
    @Data
    public static class TextEntity {
        private Integer x;
        private Integer y;
        private String value;
    }
}
