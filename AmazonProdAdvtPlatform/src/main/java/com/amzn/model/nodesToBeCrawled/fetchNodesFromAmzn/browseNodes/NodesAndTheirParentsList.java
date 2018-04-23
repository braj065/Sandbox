package com.amzn.model.nodesToBeCrawled.fetchNodesFromAmzn.browseNodes;

import java.util.ArrayList;
import java.util.List;

import com.amazon.webservices.awsecommerceservice._2013_08_01.BrowseNode_type0;
import com.amazon.webservices.awsecommerceservice._2013_08_01.Children_type0;
import com.amzn.model.crawler.commpacks.response.IResponseHolder;
import com.amzn.model.crawler.commpacks.response.NodeLookupResponseHolder;
import com.amzn.model.crawler.stub.StubFactory;
import com.amzn.model.nodesToBeCrawled.fetchNodesFromAmzn.request.BrowseNodeRequestContainer;
import com.amzn.model.nodesToBeCrawled.nodeFeedToBeCrawled.nodes.nodeEntity.INodeStats;

public class NodesAndTheirParentsList {
    
    private NodeLookupResponseHolder responseHolder;
    private BrowseNodeRequestContainer reqCon;
    private LeafNodes leafNodeOperations;
    
    public NodesAndTheirParentsList(BrowseNodeRequestContainer req){
	reqCon=req;
    }
    
    public LeafNodes initializeLeafNode(){
	leafNodeOperations=new LeafNodes(
		getRootAncestor(responseHolder.getLookupResp().getBrowseNodes()[0].getBrowseNode()[0]));
	leafNodeOperations.loadPropertyFile();
	return leafNodeOperations;
    }
    
    public void loadChildren(NodeLookupResponseHolder resp){
	responseHolder=resp;
	BrowseNode_type0[] nodeArr=getChildren();
	createChildren(nodeArr);
    }
    
    public Children_type0 ifChildrenPresent(){
	return responseHolder.getLookupResp().getBrowseNodes()[0].getBrowseNode()[0].getChildren();
    }
    
    public BrowseNode_type0[] getChildren(){
	if(ifChildrenPresent()!=null)
	    return ifChildrenPresent().getBrowseNode();
	return null;
    }
    
    public void createChildren(BrowseNode_type0[] children){
	if(children==null){
	    flushFullHeir();	//create leaf child here use the ancestor, nodeid and ancestor-1 to save the property
	    return;
	}
	for(BrowseNode_type0 child:children){
	    NodesAndTheirParentsList currNode=new NodesAndTheirParentsList(reqCon);
	    reqCon=new BrowseNodeRequestContainer();//clone it with clear props
	    reqCon.addBrowseNodeId(child.getBrowseNodeId()).setShared();
	    
	    retryRequetIfFailed(reqCon,5000);
	    /*if(!grandChildrenPresent()){
		FactoryNodes.addToRepo(child.getName()
			, value);
	    }else*/
	    currNode.loadChildren(responseHolder);
		
	}
	String ancestorName=getFullAncestor(responseHolder.getLookupResp().getBrowseNodes()[0].getBrowseNode()[0]);
	String parentName=ancestorName.substring(0, ancestorName.lastIndexOf("."));
	initializeLeafNode();
	leafNodeOperations.writeToPropertyFile(FactoryNodes.getValue(parentName), parentName);
	leafNodeOperations.savePropertyFile();
	FactoryNodes.clearKey(parentName);
    }
    
    public void flushFullHeir(){
	String nodeId=responseHolder.getLookupResp().getBrowseNodes()[0].getBrowseNode()[0].getBrowseNodeId();
	String ancestorName=getFullAncestor(responseHolder.getLookupResp().getBrowseNodes()[0].getBrowseNode()[0]);
	if(ancestorName.contains("Grocery & Gourmet Foods.Products.Cooking & Baking Supplies.Rice, Flour & Pulses.Flours") ||
		ancestorName.equals("Grocery & Gourmet Foods.Products.Cooking & Baking Supplies.Rice, Flour & Pulses.Flours")){
	    System.out.println();
	}
	FactoryNodes.addToRepo(ancestorName.substring(0, ancestorName.lastIndexOf("."))
		, ancestorName+"="+nodeId);	
    }
    
    public String getFullAncestor(BrowseNode_type0 bnode){
	String leafNodeName=bnode.getName();
	
	while(bnode.getAncestors()!=null){
	    bnode=bnode.getAncestors().getBrowseNode()[0];
	    leafNodeName=bnode.getName()+"."+leafNodeName;
	}
	return leafNodeName;
    }
    
    public String getRootAncestor(BrowseNode_type0 bnode){
	BrowseNode_type0 ancestor=bnode;
	while(ancestor.getAncestors()!=null){
	    ancestor=ancestor.getAncestors().getBrowseNode()[0];
	}
	return ancestor.getName();
    }
    
    public void retryRequetIfFailed(BrowseNodeRequestContainer reqCon, long lagTime){
	try{
	    Thread.sleep(2000);
	    responseHolder=(NodeLookupResponseHolder) StubFactory.getStubInstance("BrowseNodeInfo")
		    .executeOperation(reqCon.bnodeLookup);
	    Thread.sleep(1000);
	}catch(Exception e){
	    try {
		Thread.sleep(lagTime+=3000);
		retryRequetIfFailed(reqCon, lagTime);
	    } catch (InterruptedException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	    }
	}
    }

}
