package common.telran.net;

import java.io.Serializable;

public class Request implements Serializable {

	
	private static final long serialVersionUID = 1L;
	public String requestType;
	public Serializable requestData;
	public Request(String requestType, Serializable requestData) {
		super();
		this.requestType = requestType;
		this.requestData = requestData;
	}
	

}
