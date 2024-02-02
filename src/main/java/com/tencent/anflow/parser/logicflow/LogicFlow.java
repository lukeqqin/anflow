package com.tencent.anflow.parser.logicflow;

import lombok.Data;

import java.util.List;

/**
 * logicFlow转化后的json数据格式
 */
@Data
public class LogicFlow {
    private List<LfNode> nodes;
    private List<LfEdge> edges;
}
