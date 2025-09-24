package com.asopagos.seven.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("externalAPI/clienteSeven")
@Consumes({ "application/json; charset=UTF-8" })
@Produces({ "application/json; charset=UTF-8" })
public interface ClienteSevenService {

	@GET
	@Path("ejecutarEsbSeven")
	public void ejecutarEsbSeven();
}
