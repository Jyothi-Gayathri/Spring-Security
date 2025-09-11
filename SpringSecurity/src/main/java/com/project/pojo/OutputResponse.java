package com.project.pojo;

public class OutputResponse {

	
	private String status;
	private Object output;
	private Object message;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Object getOutput() {
		return output;
	}
	public void setOutput(Object output) {
		this.output = output;
	}
	
	public Object getMessage() {
		return message;
	}
	public void setMessage(Object message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "OutputResponse [status=" + status + ", output=" + output + ", message=" + message + "]";
	}
	
	
}