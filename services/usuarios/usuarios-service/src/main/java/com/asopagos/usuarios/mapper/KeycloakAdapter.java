package com.asopagos.usuarios.mapper;

import org.keycloak.admin.client.Keycloak;

public class KeycloakAdapter {
	
	private Keycloak kc=null;
	
	private String realm=null;
	
	public KeycloakAdapter(){
		
	}

	public KeycloakAdapter(Keycloak kc, String realm) {
		super();
		this.kc = kc;
		this.realm = realm;
	}

	public Keycloak getKc() {
		return kc;
	}

	public void setKc(Keycloak kc) {
		this.kc = kc;
	}

	public String getRealm() {
		return realm;
	}

	public void setRealm(String realm) {
		this.realm = realm;
	}
	
	

}
