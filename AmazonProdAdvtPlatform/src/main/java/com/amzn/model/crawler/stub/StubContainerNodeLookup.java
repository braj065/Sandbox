package com.amzn.model.crawler.stub;

import java.rmi.RemoteException;

import com.amazon.webservices.awsecommerceservice._2013_08_01.BrowseNodeLookup;
import com.amzn.model.crawler.commpacks.ResponseHolder;
import com.amzn.model.crawler.commpacks.requests.IRequestHolder;

import pack.test.SignedRequestsHelper;

public class StubContainerNodeLookup extends AbsStubContainer{
    protected static final AbsStubContainer NODELOOKUPINSTANCE = new StubContainerNodeLookup();
    public static final String OPERATION="BrowseNodeLookup";
    
    private StubContainerNodeLookup(){
	super();
    }

    @Override
    protected void addStubOperation(String timeStamp) throws Exception {
	stub._getServiceClient().addHeader(getChild("Signature", "http://security.amazonaws.com/doc/2007-01-01/", "aws",
		    SignedRequestsHelper.getInstance(AWESProperty.Value.AWS_SECRET_KEY.getString())
		    .sign(OPERATION, timeStamp)));	
    }
    
    @Override
    public ResponseHolder executeOperation(IRequestHolder searchReq) throws RemoteException {
	return new ResponseHolder(stub.browseNodeLookup((BrowseNodeLookup)searchReq.getReqContainer()));
    }
}
