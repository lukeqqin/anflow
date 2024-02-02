package com.tencent.anflow.component;

import com.tencent.anflow.context.PublishActivityRuleContext;
import com.tencent.anflow.projo.BanPublicActivityCmpData;
import com.tencent.anflow.projo.PublicActivityRuleRequestData;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.annotation.LiteflowMethod;
import com.yomahub.liteflow.core.NodeComponent;
import com.yomahub.liteflow.enums.LiteFlowMethodEnum;
import com.yomahub.liteflow.enums.NodeTypeEnum;

@LiteflowComponent
public class CreditCmp {

    /**
     * 获取信用分能力组件
     *
     * @param bindCmp
     */
    @LiteflowMethod(value = LiteFlowMethodEnum.PROCESS, nodeId = "getCreditScore", nodeType = NodeTypeEnum.COMMON)
    public void getCreditScore(NodeComponent bindCmp) {
        PublishActivityRuleContext contextBean = bindCmp.getContextBean(PublishActivityRuleContext.class);
        //请求spi或函数调用获取信用分
        contextBean.setCreditScore(70);
    }

    /**
     * 获取主页创建日能力组件
     *
     * @param bindCmp
     */
    @LiteflowMethod(value = LiteFlowMethodEnum.PROCESS, nodeId = "getGhomeDays", nodeType = NodeTypeEnum.COMMON)
    public void getGhomeDays(NodeComponent bindCmp) {
        PublishActivityRuleContext contextBean = bindCmp.getContextBean(PublishActivityRuleContext.class);
        //请求spi或函数调用获取主页创建日
        contextBean.setGhomeDays(70);
    }

    /**
     * 获取判断是否明星团长能力组件
     *
     * @param bindCmp
     */
    @LiteflowMethod(value = LiteFlowMethodEnum.PROCESS, nodeId = "getIsStarGroup", nodeType = NodeTypeEnum.COMMON)
    public void getIsStarGroup(NodeComponent bindCmp) {
        PublishActivityRuleContext contextBean = bindCmp.getContextBean(PublishActivityRuleContext.class);
        //请求spi或函数调用获取是否明星团长
        contextBean.setStarGroup(true);
    }

    /**
     * 封禁发布活动组件
     *
     * @param bindCmp
     */
    @LiteflowMethod(value = LiteFlowMethodEnum.PROCESS, nodeId = "processBanPublicActivity", nodeType = NodeTypeEnum.COMMON)
    public void processBanPublicActivity(NodeComponent bindCmp) {
        PublicActivityRuleRequestData requestData = (PublicActivityRuleRequestData) bindCmp.getRequestData();
        Long ghId = requestData.getGhId();
        BanPublicActivityCmpData cmpData = bindCmp.getCmpData(BanPublicActivityCmpData.class);
        Integer days = cmpData.getDays();
        //执行封禁发布活动的spi，带上参数
        System.out.println("主页：" + ghId);
        System.out.println("主页封禁发布活动天数：" + days);

    }

    /**
     * 停止走马灯警告
     * @param bindCmp
     */
    @LiteflowMethod(value = LiteFlowMethodEnum.PROCESS, nodeId = "processStopWarning", nodeType = NodeTypeEnum.COMMON)
    public void processStopWarning(NodeComponent bindCmp) {
        PublicActivityRuleRequestData requestData = (PublicActivityRuleRequestData) bindCmp.getRequestData();
        Long ghId = requestData.getGhId();
        System.out.println("主页：" + ghId);
        System.out.println("主页停止走马灯警告");
    }
    /**
     * 开始走马灯警告
     * @param bindCmp
     */
    @LiteflowMethod(value = LiteFlowMethodEnum.PROCESS, nodeId = "processStartWarning", nodeType = NodeTypeEnum.COMMON)
    public void processStartWarning(NodeComponent bindCmp) {
        PublicActivityRuleRequestData requestData = (PublicActivityRuleRequestData) bindCmp.getRequestData();
        Long ghId = requestData.getGhId();
        System.out.println("主页：" + ghId);
        System.out.println("主页开始走马灯警告");
    }


    /**
     * 恢复发布活动组件
     *
     * @param bindCmp
     */
    @LiteflowMethod(value = LiteFlowMethodEnum.PROCESS, nodeId = "processCanPublicActivity", nodeType = NodeTypeEnum.COMMON)
    public void processCanPublicActivity(NodeComponent bindCmp) {
        PublicActivityRuleRequestData requestData = (PublicActivityRuleRequestData) bindCmp.getRequestData();
        Long ghId = requestData.getGhId();
        //执行恢复发布活动的spi
        System.out.println("主页：" + ghId);
        System.out.println("主页恢复发布活动天数");
    }
    /**
     * 判断是否明星团长
     *
     * @param bindCmp
     * @return
     */
    @LiteflowMethod(value = LiteFlowMethodEnum.PROCESS_IF, nodeId = "processStarGroupIF", nodeType = NodeTypeEnum.IF)
    public boolean processStarGroupIF(NodeComponent bindCmp) {
        PublicActivityRuleRequestData requestData = (PublicActivityRuleRequestData) bindCmp.getRequestData();
        Long ghId = requestData.getGhId();
        //spi判断是否明星团长
        return false;
    }







    /**
     * 信用分选择组件 ，脚本化组件
     *
     * @param bindCmp
     * @return
     */
    @LiteflowMethod(value = LiteFlowMethodEnum.PROCESS_SWITCH, nodeId = "processCreditSwitch", nodeType = NodeTypeEnum.SWITCH)
    public String processCreditSwitch(NodeComponent bindCmp) {
        PublishActivityRuleContext contextBean = bindCmp.getContextBean(PublishActivityRuleContext.class);
        Integer creditScore = contextBean.getCreditScore();
        if (creditScore < 70) {
            return "t1";
        } else {
            return "t2";
        }

    }

    /**
     * 主页创建日选择组件 ，脚本化组件
     *
     * @param bindCmp
     * @return
     */
    @LiteflowMethod(value = LiteFlowMethodEnum.PROCESS_SWITCH, nodeId = "processGhomeDaysSwitch", nodeType = NodeTypeEnum.SWITCH)
    public String processGhomeDaysSwitch(NodeComponent bindCmp) {
        PublishActivityRuleContext contextBean = bindCmp.getContextBean(PublishActivityRuleContext.class);
        Integer ghomeDays = contextBean.getGhomeDays();
        if (ghomeDays < 1) {
            return "t1_1";
        } else if (ghomeDays < 90) {
            return "t1_2";
        } else {
            return "t1_3";
        }


    }





}
