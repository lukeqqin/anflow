package com.tencent.anflow.parser.logicflow;

import com.tencent.anflow.parser.core.ELNode;
import lombok.Getter;

/**
 * logicFlow节点属性 对应liteflow的组件协议
 */
@Getter
public enum LfNodePropertyEnum {
    nodeId,
    nodeTag,
    nodeData,

    /**
     * 对应ELNode.ELName
     *
     * @see ELNode.ELNameEnum
     */
    nodeType,
    conditionNodeId,
    nodeAliasId
}
