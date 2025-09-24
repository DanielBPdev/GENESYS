package com.asopagos.common.services.locator;

import com.asopagos.common.services.enums.ServiceLocatorType;

/**
 * Factory method para instanciar una implementación de {@link ServiceLocator}
 * @author jroa
 *
 */
public class ServicesLocatorFactory {

    protected static ServiceLocator instance;

    private ServicesLocatorFactory() {
    }

    /**
     * Dependiendo de la propiedad del sistema servicelocator.type se instancia la implementación de
     * service locator que se requiera.
     * @return Instancia de {@link ServiceLocator}, {@link SimpleServicesLocator} por defecto.
     */
    public static ServiceLocator getServiceLocator() {
        if (instance == null) {
            String type = System.getProperty("servicelocator.type");
            ServiceLocatorType serviceLocatorType = ServiceLocatorType.SIMPLE;
            if (type != null) {
                serviceLocatorType = ServiceLocatorType.valueOf(type);
            }
            switch (serviceLocatorType) {
                case OPENSHIFT:
                    instance = new OpenshiftServicesLocator();
                    break;
                case KUBERNETES:
                    instance = new KubernetesServicesLocator();
                    break;
                case SIMPLE:
                default:
                    instance = new SimpleServicesLocator();
            }
        }
        return instance;
    }
}
