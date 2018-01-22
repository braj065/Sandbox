package com.amzn.model.constants;

public class Property {
    private final static String slash=System.getProperty("file.separator");
    
    public static String getFilePathFromLdap(String fileName){
	return Value.RSCFOLDER.getString()+slash+
		Value.ROOTPACK.getString()+slash+Value.NODES.getString()+slash+
		Value.LDAPCHILDS.getString()+slash+
		fileName;
    }
    
    public enum Value{
	HOME("home", System.getProperty("user.dir")),
	BIN("bin", Property.Value.HOME.getString()+slash+"bin"),
	JAVAFOLDER("src/main/java",Value.HOME.getString()+slash+"src"+slash+"main"+slash+"java"),
	RSCFOLDER("src/main/resources",Value.HOME.getString()+slash+"src"+slash+"main"+slash+"resources"),
	ROOTPACK("com/amzn","com"+slash+"amzn"),
	NODES("model/nodes", "model"+slash+"nodes"),
	LDAPCHILDS("ldapchilds","ldapchilds"),
	ROOT_CATEGORIES("HighestRootNodeIds.properties", Property.Value.BIN.getString()+slash+
		Property.Value.ROOTPACK.getString()+slash+
		Property.Value.NODES.getString()+slash+"HighestRootNodeIds.properties"),
	WRITEROOT_CATEGORIES("WriteHighestRootNodeIds.properties", 
		Property.Value.RSCFOLDER.getString()+slash+Property.Value.ROOTPACK.getString()+
		slash+Property.Value.NODES.getString()+slash+"HighestRootNodeIds.properties");
	
	private final String property, defaultValue;
	private String value;
	private Value(String property, String defaultVal){
	    this.property=property;
	    this.defaultValue=defaultVal;
	}
	
	public String getString(){
	    if(value==null){
		if(defaultValue==null)
		    value=System.getProperty(property);
		else
		    value=System.getProperty(property, defaultValue);
	    }
	    return value;
	}
	
    }
    
}
