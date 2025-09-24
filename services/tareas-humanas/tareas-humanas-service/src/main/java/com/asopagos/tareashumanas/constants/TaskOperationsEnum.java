package com.asopagos.tareashumanas.constants;

public enum TaskOperationsEnum {
	CLAIM("claim"), START("start"), COMPLETE("complete"), SUSPEND("suspend"), RESUME("resume"), ACTIVATE("activate");
	
	private TaskOperationsEnum(String operationName) {
		this.operationName=operationName;
	}
	
	private String operationName;
	
	public String getOperationName() {
		return operationName;
	}
}
