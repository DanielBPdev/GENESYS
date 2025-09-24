package com.asopagos.coreaas.bpm.workitems.rest.properties.datamodel;

import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class PropertiesLoader {

	private static final String SERVICES_XML_PATH = "/services.xml";

	private static PropertiesLoader instance;

	private Map<String, Service> mapServices;
	private Map<String, Operation> mapOperations;

	public static PropertiesLoader getInstance() throws Exception {
		if (instance == null) {
			PropertiesFile file = loadPropertiesFile();
			if (file != null) {
				instance = new PropertiesLoader(file);
			}
		}
		return instance;
	}

	private PropertiesLoader(PropertiesFile file) {
		initMaps(file);
	}

	private void initMaps(PropertiesFile file) {
		mapServices = new LinkedHashMap<String, Service>();
		mapOperations = new LinkedHashMap<String, Operation>();
		for (Service s : file.getServices().getService()) {
			mapServices.put(s.getName(), s);
			for (Operation o : s.getOperations().getOperation()) {
				mapOperations.put(o.getName(), o);
			}
		}
	}

	public Service getService(String name) {
		if (mapServices.containsKey(name)) {
			Service s = mapServices.get(name);
			return s;
		}
		return null;
	}

	public Operation getOperation(String name) {
		if (mapOperations.containsKey(name)) {
			Operation o = mapOperations.get(name);
			return o;
		}
		return null;
	}

	private static PropertiesFile loadPropertiesFile() throws Exception {
		try {
			InputStream in = PropertiesFile.class.getClassLoader().getResourceAsStream(SERVICES_XML_PATH);
			if (in != null) {
				JAXBContext jaxbContext = JAXBContext.newInstance(PropertiesFile.class);
				Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
				PropertiesFile file = (PropertiesFile) jaxbUnmarshaller.unmarshal(in);
				return file;
			}
			return null;
		} catch (JAXBException e) {
			throw new Exception(e.getMessage(), e);
		}
	}

}
