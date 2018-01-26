package com.amzn.model.nodes.ldapchilds;

import java.util.HashMap;
import java.util.Map;

import com.amzn.model.nodes.INode;
import com.amzn.model.nodes.nodeEntity.INodeStats;

public abstract class AbstractLdapChild implements INode{
    
    protected Map<INode, Boolean> children;
    protected Boolean toBeScrapped;
    protected String parentCategory;
    protected INode observer;
    
    public AbstractLdapChild(String ldapName, Boolean toBeScrapped){
	children=new HashMap<INode, Boolean>();
	this.parentCategory=ldapName;
	this.toBeScrapped=toBeScrapped;	
    }
    
    public INode getObserver(){
	return observer;
    }
    
    @Override
    public void notifyObserver(INodeStats stats) {
	// TODO Auto-generated method stub
	
    }
}
