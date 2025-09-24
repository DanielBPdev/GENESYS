package com.asopagos.rest.security.filter;

import java.io.IOException;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;

import org.jboss.resteasy.spi.ResteasyProviderFactory;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.rest.security.enums.HeadersEnum;

public class AddAuditHeaders implements ClientRequestFilter {

	private String ip;
	
	private String txId;

	private String requestId;
	
	
	@Override
	public void filter(ClientRequestContext requestContext) throws IOException {
		addHeader(HeadersEnum.IP_ORIGIN.getName(), ip, requestContext);
		addHeader(HeadersEnum.TX_ID.getName(), txId, requestContext);
		addHeader(HeadersEnum.REQUEST_ID.getName(), requestId, requestContext);
	}

	public AddAuditHeaders(String requestId) {
		UserDTO user=ResteasyProviderFactory.getContextData(UserDTO.class);
		if(user != null){
			this.ip = user.getAuditoria().getIp();
			this.requestId=requestId;
		}
	}
	
	private void addHeader(String name, String value, ClientRequestContext request){
		if(value!=null)
			request.getHeaders().add(name, value);
	}

}
