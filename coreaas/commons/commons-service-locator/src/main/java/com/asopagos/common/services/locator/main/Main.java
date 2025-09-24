package com.asopagos.common.services.locator.main;

import com.asopagos.common.services.locator.ServiceLocator;
import com.asopagos.common.services.locator.ServicesLocatorFactory;
import com.asopagos.common.services.locator.datamodel.ServiceEntry;
import com.asopagos.common.services.locator.excepcion.ServiceNotFoundException;


public class Main {

	public static void main(String[] args) {
		ServiceLocator locator=ServicesLocatorFactory.getServiceLocator();
		String serviceName="TerminarTarea";
		try {
			ServiceEntry service=locator.getServiceEntry(serviceName);
			System.out.println("Service environment:"+ service.getEndpoint());
			System.out.println("Service endpoint:"+ service.getPath());
		} catch (ServiceNotFoundException e) {
			e.printStackTrace();
		}

	}

}
