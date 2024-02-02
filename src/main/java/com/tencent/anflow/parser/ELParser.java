package com.tencent.anflow.parser;

import com.tencent.anflow.parser.core.ELNode;

/**
 * 表达式解析器
 */
public interface ELParser {
    /**
     * 解析提取ELNode
     *
     * @return ELNode
     */
    ELNode extractElNode();
}
