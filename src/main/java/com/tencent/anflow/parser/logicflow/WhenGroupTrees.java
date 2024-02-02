package com.tencent.anflow.parser.logicflow;

import com.tencent.anflow.parser.core.ELNode;
import lombok.Data;

import java.util.List;

/**
 * liteflow并行编排抽象树
 */
@Data
public class WhenGroupTrees {
    /**
     * when Group 节点
     */
    String whenGroupNodeId;
//
//    /**
//     * 非本when组，外部节点
//     */
//    String externalNodeId;

    /**
     * when中有多棵树
     */
    List<ELNode> elNodeList;

}
