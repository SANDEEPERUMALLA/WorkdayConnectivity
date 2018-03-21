package com.workday.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.ArrayList;
import java.util.List;

public class MetaNode {
    public List<MetaNode> nodeList;

    public List<MetaNode> getNodeList() {
        return nodeList;
    }

    public void setNodeList(List<MetaNode> nodeList) {
        this.nodeList = nodeList;
    }

    public void setMetaName(String metaName) {
        this.metaName = metaName;
    }

    private String metaName;

    private String metaType;

    public MetaNode(String metaName, String metaType) {

        this.metaName = metaName;
        this.metaType = metaType;
        this.nodeList = new ArrayList<>();

    }

    public String getMetaName() {
        return this.metaName;
    }

    public String getMetaType() {
        return metaType;
    }

    public void setMetaType(String metaType) {
        this.metaType = metaType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE).append("MetaName", metaName).append("MetaType", metaType).append("has", this.nodeList, true).toString();
    }
}