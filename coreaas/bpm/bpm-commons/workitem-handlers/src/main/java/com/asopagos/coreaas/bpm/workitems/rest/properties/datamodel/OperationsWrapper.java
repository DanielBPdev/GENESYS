package com.asopagos.coreaas.bpm.workitems.rest.properties.datamodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "operation")
public class OperationsWrapper implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private List<Operation> operation;

	public List<Operation> getOperation() {
		return operation;
	}

	public void setOperation(List<Operation> operations) {
		this.operation = operations;
	}

	public void addOperation(Operation o) {
		if(operation==null){
			operation = new ArrayList<Operation>();
		}
		operation.add(o);
	}
}
