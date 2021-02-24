package net.sf.minuteproject.adf.utils;

import java.util.ArrayList;
import java.util.List;

public class TreeItem {
    private List children;
    private Boolean hasChildren;
    private Object node;
    
    public TreeItem(Object node){
        this.node=node;
    }

    public void setChildren(List children) {
        this.children = children;
    }

    public List getChildren() {
        return children;
    }

    public void setHasChildren(Boolean hasChildren) {
        this.hasChildren = hasChildren;
    }

    public Boolean getHasChildren() {
        return hasChildren;
    }

    public void setNode(Object node) {
        this.node = node;
    }

    public Object getNode() {
        return node;
    }
}
