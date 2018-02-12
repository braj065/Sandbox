package com.amzn.model.crawler.crawl;

import com.amazon.webservices.awsecommerceservice._2013_08_01.ItemSearch;
import com.amazon.webservices.awsecommerceservice._2013_08_01.ItemSearchResponse;
import com.amzn.model.crawler.AWSEStub.AWESProperty;
import com.amzn.model.crawler.AWSEStub.StubFactory;
import com.amzn.model.crawler.reqRespParams.RequestParams;
import com.amzn.model.crawler.sortParamBased.AbstractSortApplied;

public class BatchExecutor {
    private ItemSearchResponse resp=null;
    private RequestParams requestParams=null;
    private AbstractSortApplied batchSearchParams=null;
    
    public BatchExecutor(AbstractSortApplied batchSearchParams, RequestParams reqParams){
	this.batchSearchParams=batchSearchParams;
	this.requestParams=reqParams;
    }
    
    public void loadSearchReq(){
	requestParams.itemSearchReq.setRequest(requestParams.itemSrchParamsReqArr);
	requestParams.itemSearchReq.setAssociateTag(AWESProperty.Value.ASSOCIATE_TAG.getString());
	try{
	resp=StubFactory.getStubInstance().itemSearch(requestParams.itemSearchReq);
	}catch(Exception e){
	    e.printStackTrace();
	    System.out.println(requestParams.nodeToBeCrawled.toString());
	}
    }
    
    public ItemSearchResponse getResponse(){
	return resp;
    }
    
    public void validateResponsePagesCount(){
	batchSearchParams.validateResponsePagesCount(resp.getItems());
    }
    
    
}
