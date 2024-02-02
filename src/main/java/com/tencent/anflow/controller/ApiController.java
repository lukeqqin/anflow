package com.tencent.anflow.controller;

import com.tencent.anflow.parser.core.ELNode;
import com.tencent.anflow.parser.core.ExpressLanguageParseException;
import com.tencent.anflow.parser.ELParser;
import com.tencent.anflow.parser.logicflow.LogicFlow;
import com.tencent.anflow.parser.logicflow.LfParser;
import com.tencent.anflow.service.ApiService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "我的接口")
@RequestMapping("/api")
@RestController
public class ApiController {


    @Autowired
    ApiService apiService;


    @ApiOperation(value = "转化el表达式", notes = "输入ElNode结构")
    @ApiImplicitParam(name = "elNode", value = "节点", required = true)
    @PostMapping("/generateEL")
    public String generateEL(@RequestBody ELNode elNode) {
        String sqlTemplate = null;
        try {
            sqlTemplate = elNode.generateEl();
        } catch (ExpressLanguageParseException e) {
            e.printStackTrace();
        }
        return sqlTemplate;
    }

    @ApiOperation(value = "logicFlow转化el表达式", notes = "输入logicFlow 结构")
    @ApiImplicitParam(name = "logicFlow", value = "节点", required = true)
    @PostMapping("/generateLogicFlowEL")
    public String generateLogicFlowEL(@RequestBody LogicFlow logicFlow) {
        String sqlTemplate = null;
        try {
            ELParser elParser = new LfParser(logicFlow);
            ELNode elNode = elParser.extractElNode();
            sqlTemplate = elNode.generateEl();
        } catch (ExpressLanguageParseException e) {
            e.printStackTrace();
        }
        return sqlTemplate;
    }

}
