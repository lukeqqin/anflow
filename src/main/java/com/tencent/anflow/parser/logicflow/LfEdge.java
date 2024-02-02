package com.tencent.anflow.parser.logicflow;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * logicFlow连线
 */
@Data
public class LfEdge {
    private String id;
    private String type;
    private String sourceNodeId;
    private String targetNodeId;
    private LfPoint startPoint;
    private LfPoint endPoint;
    private List<LfPoint> pointsList;
    private Map<String, Object> properties;
    private TextEntity text;
    private String sourceAnchorId;
    private String targetAnchorId;

    /**
     * 组件文本域
     */
    @Data
    public static class TextEntity {
        private String value;
        private Integer x;
        private Integer y;
    }
}
