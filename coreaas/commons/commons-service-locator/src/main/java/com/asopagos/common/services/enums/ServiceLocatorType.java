package com.asopagos.common.services.enums;

/**
 * Tipos de implementaciones de Service Locator para obtener la ip, puerto y ruta de los servicios, 
 * configurados en el archivo services.xml
 * @author jroa
 *
 */
public enum ServiceLocatorType {
    /**
     * Usado para instanciar SimpleServicesLocator
     */
    SIMPLE, 
    
    /**
     * Usado para instanciar OpenshiftServicesLocator
     */
    OPENSHIFT,

    /**
     * Usado para instanciar KubernetesServicesLocator
     */
    KUBERNETES;

}
