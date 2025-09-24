package com.asopagos.common.services.locator;

import com.asopagos.common.services.locator.datamodel.Service;
import com.asopagos.common.services.locator.datamodel.ServiceEntry;
import com.asopagos.common.services.locator.excepcion.ServiceNotFoundException;

public class KubernetesServicesLocator extends ServiceLocator {

    @Override
    /**
     * Se obtiene el nombre del context root configurado en el atributo path del archivo de configuración 
     * {@link ServiceLocator#SERVICES_XML_PATH}, este debe coincidir con el nombre del servicio creado 
     * en Kubernetes de la siguiente forma EjemploService en el archivo y ejemplo-service en Kubernetes, 
     * es por esto que se cambia las mayúsculas por un guión (-) y minúscula.
     */
    public ServiceEntry getServiceEntry(String serviceName) throws ServiceNotFoundException {
        ServiceEntry entry=new ServiceEntry();
        String path="";
        Service service = mapServices.get(serviceName.toLowerCase());
        if (service != null) {
            //determinar path
            path=service.getPath();
            if(path!=null){
                entry.setPath(path);
                ///determinar endpoint
                String contextPath = path.substring(0, path.indexOf('/'));
                String kubernetesName = contextPath.replaceAll("([A-Z])", "-$1").toLowerCase();
                entry.setEndpoint("http://"+kubernetesName.substring(1));
            }
        }else{
            throw new ServiceNotFoundException("El servicio: "+ serviceName+
                    " no se encuentra definido en el archivo "+ SERVICES_XML_PATH);
        }
        return entry;
    }
}
