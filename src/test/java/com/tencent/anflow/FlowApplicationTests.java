package com.tencent.anflow;


import java.sql.SQLException;

import com.yomahub.liteflow.core.FlowExecutor;
import com.yomahub.liteflow.flow.LiteflowResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FlowApplicationTests {

    @Autowired
    private FlowExecutor flowExecutor;

    @Test
    void contextLoads() throws SQLException {
        LiteflowResponse response = flowExecutor.execute2Resp("chain1", "arg");
        System.out.println(response);

    }

}


