package com.asopagos.entidaddescuento.clients;

import java.util.Map;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.GenericType;

import com.asopagos.entidaddescuento.dto.ArchivoEntidadDescuentoSubsidioPignoradoModeloDTO;
import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/entidadDescuento/archivoDescuentos/gestionarTrazabilidad
 */
public class GestionarTrazabilidadArchivoDescuentos extends ServiceClient {

	private ArchivoEntidadDescuentoSubsidioPignoradoModeloDTO informarcionTrazabilidadDTO;

	/** Atributo que almacena los datos resultado del llamado al servicio */
	private Map<String, Object> result;

	public GestionarTrazabilidadArchivoDescuentos(ArchivoEntidadDescuentoSubsidioPignoradoModeloDTO informarcionTrazabilidadDTO){
		super();
		this.informarcionTrazabilidadDTO = informarcionTrazabilidadDTO;
	}

	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		return webTarget.path(path)
				.request(MediaType.APPLICATION_JSON)
				.post(informarcionTrazabilidadDTO == null ? null : Entity.json(informarcionTrazabilidadDTO));
	}

	@Override
	protected void getResultData(Response response) {
		result = response.readEntity(new GenericType<Map<String, Object>>() {});
	}

	/** Retorna el resultado del llamado al servicio */
	public Map<String, Object> getResult() {
		return result;
	}

	public void setInformarcionTrazabilidadDTO(ArchivoEntidadDescuentoSubsidioPignoradoModeloDTO informarcionTrazabilidadDTO){
		this.informarcionTrazabilidadDTO = informarcionTrazabilidadDTO;
	}

	public ArchivoEntidadDescuentoSubsidioPignoradoModeloDTO getInformarcionTrazabilidadDTO(){
		return informarcionTrazabilidadDTO;
	}
}