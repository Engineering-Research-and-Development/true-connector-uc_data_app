package de.fraunhofer.iese.ids.ucapp.model;

import io.swagger.annotations.ApiModelProperty;

public class IdsMsgTarget {
	@ApiModelProperty(example = "Anwendung A")
	String name;
	@ApiModelProperty(example = "http://ziel-app")
	String appUri;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAppUri() {
		return appUri;
	}
	public void setAppUri(String appUri) {
		this.appUri = appUri;
	}

}
