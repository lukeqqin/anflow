package com.tencent.anflow.parser.logicflow;

import com.tencent.anflow.parser.core.ELNode;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * logicFlow解析上下文
 */
@Data
public class LfParseContext {

    /**
     * 节点Id -> 节点的多个边
     * sourceId-> List<EdgeEntity>
     */
    Map<String, List<LfEdge>> sourceNodeToEdgesMap;
    Map<String, List<LfEdge>> targetNodeToEdgesMap;

    /**
     * 所有的节点
     */
    Map<String, LfNode> nodeMap;

    /**
     * 所有的when节点
     */
    Map<String, LfNode> whenNodeMap;


    /**
     * 最外层的when
     */
    Set<LfNode> outermostLayerWhen;

    /**
     * 待转化的Map, 父when -> 子when节点List
     */
    Map<String, Set<String>> whenWaitProcessNestingMap = new HashMap<>();

    /**
     * 已转化好的Map when 节点id -》 ELNode节点
     */
    Map<String, List<ELNode>> whenCompleteElNodeMap = new HashMap<>();

    List<WhenGroupTrees> whenGroupTreesList = new ArrayList<>();
}
