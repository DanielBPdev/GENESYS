package com.asopagos.coreaas.bpm.workitems;

import java.util.Map;
import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemManager;
import com.asopagos.coreaas.bpm.workitems.rest.properties.datamodel.Operation;
import com.asopagos.coreaas.bpm.workitems.rest.properties.datamodel.PropertiesLoader;
import com.asopagos.coreaas.bpm.workitems.rest.properties.datamodel.Service;

public class CoreRestWorkitemHandler extends org.jbpm.process.workitem.rest.RESTWorkItemHandler {

	private static final String CONTENT_TYPE_PARAM = "ContentType";
	private static final String MAP_SUFFIX = "map_";
	private static final String METHOD_PARAM = "Method";
	private static final String URL_PARAM = "Url";
	private static final String OPERATION_NAME_PARAM = "OperationName";
	private static final String SERVICE_NAME_PARAM = "ServiceName";
	private static final int HTTPS_DEF_PORT = 443;
	private static final int HTTP_DEF_PORT = 80;
	private PropertiesLoader loader;

	public CoreRestWorkitemHandler(ClassLoader classLoader) {
		super(classLoader);
	}

	@Override
	public void executeWorkItem(WorkItem item, WorkItemManager manager) {
		try {
			if (item.getParameters().containsKey(SERVICE_NAME_PARAM)
					&& item.getParameters().containsKey(OPERATION_NAME_PARAM)) {
				loader = PropertiesLoader.getInstance();
				String serviceName = (String) item.getParameters().get(SERVICE_NAME_PARAM);
				String operationName = (String) item.getParameters().get(OPERATION_NAME_PARAM);
				Service service = loader.getService(serviceName);
				Operation operation = loader.getOperation(operationName);
				StringBuilder url = new StringBuilder();
				url.append(service.getProtocol());
				url.append("://");
				url.append(service.getHost());
				url.append((service.getPort() == HTTP_DEF_PORT || service.getPort() == HTTPS_DEF_PORT ? ""
						: ":" + service.getPort()));
				url.append(operation.getUrl());
				System.out.println("URL servicio: "+url.toString());
				String strUrl = url.toString();
				for (Map.Entry<String, Object> entry : item.getParameters().entrySet()) {
					if (entry.getKey().startsWith(MAP_SUFFIX)) {
						String key = entry.getKey().split("_")[1];
						strUrl = strUrl.replace("{" + key + "}", entry.getValue().toString());
					}

				}
				if(item.getParameters().containsKey(CONTENT_TYPE_PARAM)){
					String contentType=(String)item.getParameters().get(CONTENT_TYPE_PARAM);
					item.getParameters().put(CONTENT_TYPE_PARAM, contentType);
				}

				item.getParameters().put(URL_PARAM, strUrl);
				item.getParameters().put(METHOD_PARAM, operation.getMethod());
			}
			super.executeWorkItem(item, manager);
		} catch (Exception e) {
			handleException(e);
		}

	}
}
