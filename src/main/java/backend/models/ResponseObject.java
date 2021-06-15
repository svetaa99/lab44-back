package backend.models;

public class ResponseObject {
	
	private Object retObj;
	private int status;
	private String message;
	
	public ResponseObject() {
		
	}
	
	public ResponseObject(int status, String message) {
		super();
		this.status = status;
		this.message = message;
	}

	public ResponseObject(Object retObj, int status, String message) {
		super();
		this.retObj = retObj;
		this.status = status;
		this.message = message;
	}

	public Object getRetObj() {
		return retObj;
	}

	public void setRetObj(Object retObj) {
		this.retObj = retObj;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	

}
