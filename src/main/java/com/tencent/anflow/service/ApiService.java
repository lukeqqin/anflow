package com.tencent.anflow.service;

import com.yomahub.liteflow.core.FlowExecutor;
import com.yomahub.liteflow.flow.LiteflowResponse;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ApiService {
    @Resource
    FlowExecutor flowExecutor;

    public void ok(){
        LiteflowResponse liteflowResponse = flowExecutor.execute2Resp("chain3", "arg");
    }
}
