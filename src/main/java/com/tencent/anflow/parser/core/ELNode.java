package com.tencent.anflow.parser.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * liteflow表达式节点
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ELNode {

    /**
     * 类型,必填
     *
     * @see ELTypeEnum
     */
    String type;
    /**
     * 名字, type 是elType 时，必填
     *
     * @see ELNameEnum
     */
    String name;
    /**
     * 条件节点id
     */
    String conditionNodeId;
    /**
     * 别名节点id
     */
    String aliasNodeId;
    /**
     * 数据
     */
    String data;
    /**
     * 标签tag
     */
    String tag;
    /**
     * 节点id, type 是 idType 时，必填
     */
    String nodeId;
    /**
     * 子节点, type 是elType 时，必填
     */
    List<ELNode> child = new ArrayList<>();

    /**
     * elname枚举
     * 支持 THEN, SWITCH, IF, WHEN
     * 不支持 FOR, WHILE, BREAK
     */
    public enum ELNameEnum {
        COMMON, THEN, WHEN, SWITCH, IF, FOR, WHILE, BREAK;
    }

    public enum ELTypeEnum {
        idType, elType;
    }

    public static ELNode initThenNode() {
        ELNode elNode = new ELNode();
        elNode.setType(ELTypeEnum.elType.name());
        elNode.setName(ELNameEnum.THEN.name());
        return elNode;
    }

    public static ELNode initWhenNode() {
        ELNode elNode = new ELNode();
        elNode.setType(ELTypeEnum.elType.name());
        elNode.setName(ELNameEnum.WHEN.name());
        return elNode;
    }

    /**
     * 添加孩子节点
     *
     * @param node 节点
     */
    public void addChild(ELNode node) {
        child.add(node);
    }

    /**
     * 生成EL表达式字符串
     *
     * @return {@link String}
     * @throws ExpressLanguageParseException 表达语言解析异常
     */
    public String generateEl() throws ExpressLanguageParseException {
        return this.getElString(this);
    }

    /**
     * 验证
     *
     * @param elNode el节点
     * @throws ExpressLanguageParseException 表达语言解析异常
     */
    public void validate(ELNode elNode) throws ExpressLanguageParseException {
        String type = elNode.getType();
        String name = elNode.getName();
        String nodeId = elNode.getNodeId();
        String conditionNodeId = elNode.getConditionNodeId();
        List<ELNode> children = elNode.getChild();
        if (StringUtils.isEmpty(type)) {
            throw new ExpressLanguageParseException("type 不能为空");
        }
        if (ELTypeEnum.idType.name().equals(type) && StringUtils.isEmpty(nodeId)) {
            throw new ExpressLanguageParseException("type 是 idType ，必填节点id");
        }

        if (ELTypeEnum.elType.name().equals(type)) {
            if (CollectionUtils.isEmpty(children)) {
                throw new ExpressLanguageParseException("type 是 el表达式 时，必填子节点");
            }
            if (StringUtils.isEmpty(name)) {
                throw new ExpressLanguageParseException("type 是 el表达式 时，必填 name");
            }
            List<String> supportName = Arrays.asList(ELNameEnum.THEN.name(), ELNameEnum.SWITCH.name(), ELNameEnum.IF.name(), ELNameEnum.WHEN.name());
            if (!supportName.contains(name)) {
                throw new ExpressLanguageParseException("仅支持 THEN/SWITCH/IF/WHEN");
            }
            if (ELNameEnum.SWITCH.name().equals(name) || ELNameEnum.IF.name().equals(name)) {
                if (StringUtils.isEmpty(conditionNodeId)) {
                    throw new ExpressLanguageParseException("SWITCH/IF 组件里需要 conditionNodeId ");
                }
            }
        }
    }

    /**
     * 生成EL表达式字符串
     *
     * @param elNode el节点
     * @return {@link String}
     * @throws ExpressLanguageParseException 表达语言解析异常
     */
    private String getElString(ELNode elNode) throws ExpressLanguageParseException {
        String name = elNode.getName();
        String conditionNodeId = elNode.getConditionNodeId();
        String aliasNodeId = elNode.getAliasNodeId();
        String data = elNode.getData();
        String nodeId = elNode.getNodeId();
        String type = elNode.getType();
        String tag = elNode.getTag();
        List<ELNode> children = elNode.getChild();
        // 校验参数
        this.validate(elNode);
        String elStr = nodeId;
        if (ELTypeEnum.elType.name().equals(type)) {
            //遍历子节点，获取EL字符串
            List<String> list = new ArrayList<>();
            for (ELNode child : children) {
                //校验子节点
                this.validate(child);
                String elString;
                if (ELTypeEnum.elType.name().equals(child.getType())) {
                    elString = this.getElString(child);
                } else {
                    elString = this.doWithSuffix(child.getNodeId(), child.getAliasNodeId(), child.getData(), child.getTag(), child.getName());
                }
                if (!StringUtils.isEmpty(elString)) {
                    list.add(elString);
                }
            }
            elStr = this.elOperate(name, conditionNodeId, list, tag);
        }

        return this.doWithSuffix(elStr, aliasNodeId, data, tag, name);
    }

    /**
     * 根据不同el操作,拼接EL需要的字符串
     *
     * @param name            名字
     * @param conditionNodeId 条件节点id
     * @param params          参数个数
     * @return {@link String}
     */
    private String elOperate(String name, String conditionNodeId, List<String> params, String tag) {

        String elStr = "";
        if (CollectionUtils.isEmpty(params)) {
            return elStr;
        }
        String join = String.join(",", params);

        if (ELNameEnum.THEN.name().equals(name) || ELNameEnum.WHEN.name().equals(name)) {
            elStr = String.format("%s(%s)", name, join);
        }
        if (ELNameEnum.SWITCH.name().equals(name)) {
            elStr = String.format("%s(%s).TO(%s)", name, conditionNodeId, join);
        }
        if (ELNameEnum.IF.name().equals(name)) {
            if (!StringUtils.isEmpty(tag)) {
                conditionNodeId = String.format("%s.tag(\"%s\")", conditionNodeId, tag);
            }
            elStr = String.format("%s(%s,%s)", name, conditionNodeId, join);
        }
        return elStr;
    }


    /**
     * 后缀处理，id和data
     *
     * @param elString    el字符串
     * @param aliasNodeId 别名节点id
     * @param data        数据
     * @return {@link String}
     */
    private String doWithSuffix(String elString, String aliasNodeId, String data, String tag, String name) {
        if (StringUtils.isEmpty(elString)) {
            return "";
        }
        if (!StringUtils.isEmpty(data)) {
            elString = String.format("%s.data(%s)", elString, data);
        }
        if (!StringUtils.isEmpty(tag) && !ELNameEnum.IF.name().equals(name)) {
            elString = String.format("%s.tag(\"%s\")", elString, tag);
        }
        if (!StringUtils.isEmpty(aliasNodeId)) {
            elString = String.format("%s.id(\"%s\")", elString, aliasNodeId);
        }
        return elString;
    }

}
