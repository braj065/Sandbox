package com.amzn.model.nodes;

import java.util.List;
import java.util.Map;

import com.amzn.model.nodes.nodeEntity.INodeStats;
import com.amzn.model.nodes.nodeEntity.ldapNodeEntity.AbstractLdapNodeStats;

public abstract class AbstractNode implements INode{
    
    protected Map<INode, Boolean> childNodes;
    protected INode child;
    protected AbstractLdapNodeStats currentLdapChild;
    
    public INode getChild(){
	return child;
    }
    
    public void notifyObserver(){
	interpretChild();
    }
    
    @Override
    public void register(INode observer) {
	// TODO Auto-generated method stub
	
    }
    @Override
    public INode getObserver() {
	// TODO Auto-generated method stub
	return null;
    }
}
