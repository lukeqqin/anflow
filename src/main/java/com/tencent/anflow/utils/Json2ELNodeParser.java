package com.tencent.anflow.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tencent.anflow.parser.core.ELNode;
import com.tencent.anflow.parser.logicflow.LogicFlow;

import java.io.IOException;

public class Json2ELNodeParser {
    ObjectMapper objectMapper;
    private String jsonStr;

    public Json2ELNodeParser(String jsonStr) {
        this.jsonStr = jsonStr;
        this.objectMapper = new ObjectMapper();
    }

    public ELNode parse() {
        try {
            return objectMapper.readValue(this.jsonStr, ELNode.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public LogicFlow parseLogicFlow() {
        try {
            return objectMapper.readValue(this.jsonStr, LogicFlow.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
