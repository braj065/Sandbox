package com.amzn.model.crawlerx.commpacks;

import com.amazon.webservices.awsecommerceservice._2013_08_01.ItemSearchResponse;

public class NullResponseHolder extends ResponseHolder{
    private long currentTimeSec;
    public NullResponseHolder(ItemSearchResponse itemSearch) {
	super(itemSearch);
	currentTimeSec=System.currentTimeMillis()/1000;
    }

}
