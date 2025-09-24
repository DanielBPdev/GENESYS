package com.asopagos.clienteanibol.service;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import com.asopagos.clienteanibol.dto.ResultadoAnibolDTO;
import com.asopagos.clienteanibol.dto.ResultadoProcesamientoDTO;
import com.asopagos.clienteanibol.dto.SaldoTarjetaDTO;
import com.asopagos.clienteanibol.dto.TarjetaDTO;
import com.asopagos.clienteanibol.dto.ResultadoDispersionAnibolDTO;

@Path("anibol")
@Consumes({ "application/json; charset=UTF-8" })
@Produces({ "application/json; charset=UTF-8" })
public interface ClienteAnibolService {

    @GET
    @Path("consultaTarjetaActiva")
    public TarjetaDTO consultarTarjetaActiva(@QueryParam(value = "tipoId") String tipoId, @QueryParam(value = "id") String id);

    @POST
    @Path("abonoSaldoTarjeta")
    public ResultadoAnibolDTO abonarSaldoTarjetas(List<SaldoTarjetaDTO> saldoTarjetasDTO);
    
    @POST
    @Path("descuentoSaldoTarjeta")
    public ResultadoAnibolDTO descontarSaldoTarjetas(List<SaldoTarjetaDTO> saldoTarjetasDTO);

    @GET
    @Path("consultaEstadoProcesamiento")
    public List<ResultadoProcesamientoDTO> consultarEstadoProcesamiento(@QueryParam(value = "idProceso") String idProceso);
    
    @GET
    @Path("consultaEstadoProcesamientoPrescripcion")
    public void consultarEstadoProcesamientoPrescripcion(@QueryParam(value = "idProceso") String idProceso);

    @GET
    @Path("consultarEstadoProcesamientoV2")
    public ResultadoDispersionAnibolDTO consultarEstadoProcesamientoV2(@QueryParam(value = "idProceso") 	String idProceso);

}