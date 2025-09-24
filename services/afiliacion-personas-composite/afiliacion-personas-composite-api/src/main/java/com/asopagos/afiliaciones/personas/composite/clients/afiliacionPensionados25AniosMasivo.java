package com.asopagos.afiliaciones.personas.composite.clients;
import com.asopagos.afiliaciones.personas.composite.dto.ListasPensionadosDTO;
import com.asopagos.dto.CargueArchivoActualizacionDTO;
import com.asopagos.dto.ResultadoArchivo25AniosDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

public class afiliacionPensionados25AniosMasivo extends ServiceClient{
    private ListasPensionadosDTO listas;
    // // private String idEmpleador;
	// private List<Afiliado25AniosExistenteDTO> listaExistentes;

 	public afiliacionPensionados25AniosMasivo (){
 		super();
		 this.listas=listas;
		// // this.idEmpleador=idEmpleador;
		// this.listaExistentes=listaExistentes;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(listas == null ? null : Entity.json(listas));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}

	

    public ListasPensionadosDTO getListas() {
        return this.listas;
    }

    public void setListas(ListasPensionadosDTO listas) {
        this.listas = listas;
    }


}