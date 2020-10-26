package de.fraunhofer.iese.ids.ucapp.model;

import io.swagger.annotations.ApiModelProperty;

public class IdsUseObject {
	@ApiModelProperty(example = "http://mdm-connector.ids.isst.fraunhofer.de/artifact/15")
    public String targetDataUri;
	 
    public IdsMsgTarget msgTarget;
    
    @ApiModelProperty(example = "{\"text\": \"Hallo\", \"wert\": 5}")
    public Object dataObject;
    
    
	public String getTargetDataUri() {
		return targetDataUri;
	}
	public void setTargetDataUri(String targetDataUri) {
		this.targetDataUri = targetDataUri;
	}
	public IdsMsgTarget getMsgTarget() {
		return msgTarget;
	}
	public void setMsgTarget(IdsMsgTarget msgTarget) {
		this.msgTarget = msgTarget;
	}
	public Object getDataObject() {
		return dataObject;
	}
	public void setDataObject(Object dataObject) {
		this.dataObject = dataObject;
	}
    
    

}


