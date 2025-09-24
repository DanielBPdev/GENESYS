package com.asopagos.common.services.locator;

import com.asopagos.common.services.locator.datamodel.Environment;
import com.asopagos.common.services.locator.datamodel.Service;
import com.asopagos.common.services.locator.datamodel.ServiceEntry;
import com.asopagos.common.services.locator.excepcion.ServiceNotFoundException;

public class SimpleServicesLocator extends ServiceLocator {

    @Override
    public ServiceEntry getServiceEntry(String serviceName) throws ServiceNotFoundException {
        
        ServiceEntry entry = new ServiceEntry();
                // Determinar servidor y puerto del ambiente
        String environment = System.getProperty(ENVIRONMENT);
        Environment env = mapEnvironments.get(environment);
        if (environment != null) {
            if (env != null) {
                entry.setEndpoint(env.getEndpoint());
            } else {
                entry.setEndpoint(DEFAULT_ENDPOINT);
            }
        } else {
            entry.setEndpoint(DEFAULT_ENDPOINT);
        }
        
        // Determinar path del servicio
        Service service = mapServices.get(serviceName.toLowerCase());        
        if (service != null) {
            entry.setPath(service.getPath());
        } else {
            throw new ServiceNotFoundException("El servicio: " + serviceName
                    + " no se encuentra definido en el archivo " + SERVICES_XML_PATH);
        }
        return entry;
    }

}
