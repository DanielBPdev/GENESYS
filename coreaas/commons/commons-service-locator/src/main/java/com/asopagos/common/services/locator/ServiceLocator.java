package com.asopagos.common.services.locator;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import com.asopagos.common.services.locator.datamodel.Environment;
import com.asopagos.common.services.locator.datamodel.Service;
import com.asopagos.common.services.locator.datamodel.ServiceEntry;
import com.asopagos.common.services.locator.datamodel.Services;
import com.asopagos.common.services.locator.excepcion.ServiceNotFoundException;

/**
 * Clase que convierte el archivo services.xml a un objeto para que las implementaciones de esta
 *  obtenga la información necesaria para localizar los servicios.
 * @author jroa
 *
 */
public abstract class ServiceLocator {
    
    /**
     * Representa el ambiente en que se está desplegando, este mismo nombre es una propiedad 
     * de los servicios en el archivo services.xml
     */
    protected static final String ENVIRONMENT = "environment";
    /**
     * Archivo que contiene la información de los servicios
     */
    protected static final String SERVICES_XML_PATH = "/services.xml";
    /**
     * Hostname y puerto por defecto de los servicios
     */
    protected static final String DEFAULT_ENDPOINT="http://localhost:8080";
    /**
     * Objeto en el que queda la información del archivo SERVICES_XML_PATH 
     */
    protected Map<String, Service> mapServices;
    /**
     * Objeto en el que queda la información del archivo SERVICES_XML_PATH 
     */
    protected Map<String, Environment> mapEnvironments;
    
    
    public ServiceLocator() {
        this.init();
    }
    
    /**
     * Carga la información del archivo SERVICES_XML_PATH en la propiedad mapServices
     */
    protected void init() {
        Services file = loadFile();
        if (file == null) {
            throw new IllegalStateException("No se inicializó los datos con el archivo " + SERVICES_XML_PATH);
        }        
        mapServices = new HashMap<>();
        mapEnvironments = new HashMap<>();
        for (Service s : file.getService()) {
            mapServices.put(s.getName().toLowerCase(), s);
        }
        for (Environment e : file.getEnviroment()) {
            mapEnvironments.put(e.getName().toLowerCase(), e);
        }
    }

    /**
     * Lee el archivo SERVICES_XML_PATH y retorna un objeto tipo Services con la información extraída del archivo
     * @return
     */
    protected Services loadFile() {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream(SERVICES_XML_PATH);
        if (in == null) {
            in = this.getClass().getResourceAsStream(SERVICES_XML_PATH);
        }
        Services services = null;
        if (in != null) {
            JAXBContext jaxbContext;
            try {
                jaxbContext = JAXBContext.newInstance(Services.class);
                Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                services = (Services) jaxbUnmarshaller.unmarshal(in);
            } catch (JAXBException e) {
                throw new IllegalStateException("No se pudo cargar la información de servicios desde el archivo " + SERVICES_XML_PATH);
            }
        } else {
            throw new IllegalStateException("No se encontró el archivo " + SERVICES_XML_PATH);
        }
        return services;
    }

    /**
     * Retorna un ServiceEntry con la información de host, puerto y ruta del servicio.
     * @param serviceName
     * @return
     * @throws ServiceNotFoundException
     */
    public abstract ServiceEntry getServiceEntry(String serviceName) throws ServiceNotFoundException;
}
