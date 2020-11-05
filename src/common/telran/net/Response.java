package common.telran.net;

import java.io.Serializable;

public class Response implements Serializable{

	
	private static final long serialVersionUID = 1L;
	public TcpResponseCode code;
	public Serializable responseData;
	public Response(TcpResponseCode code, Serializable responseData) {
		super();
		this.code = code;
		this.responseData = responseData;
	}
	

}
