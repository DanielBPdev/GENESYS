package com.asopagos.reportes.service;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import com.asopagos.enumeraciones.reportes.FormatoReporteEnum;
import com.asopagos.enumeraciones.reportes.ReporteNormativoEnum;
import com.asopagos.reportes.dto.GeneracionReporteNormativoDTO;
import com.asopagos.reportes.dto.ResultadoReporteDTO;

/**
 * <b>Descripción:</b> Interface que define las funciones para la generación de
 * reportes normativos
 * 
 * @author
 */
@Path("reportesNormativos")
@Consumes("application/json; charset=UTF-8")
@Produces("application/json; charset=UTF-8")
public interface ReportesNormativosService {

    /**
     * Método que genera la información de un reporte normativo y también el
     * archivo en el formato especificado
     * 
     * @param generacionReporteDTO
     *        DTO con la información de entrada
     * @param formatoReporte
     * @return La cantidad de registros que se generan en el reporte
     */
    @POST
    @Path("exportar")
    @Produces({ MediaType.MULTIPART_FORM_DATA, MediaType.APPLICATION_JSON })
    public Response exportarReporteNormativo(GeneracionReporteNormativoDTO generacionReporteDTO,
            @QueryParam("formatoReporte") FormatoReporteEnum formatoReporte);

    /**
     * Metodo que permite visualizar la información de resumen para los resultados de los reportes.
     * 
     * @param generacionReporteDTO
     *        <code>GeneracionReporteNormativoDTO</code>
     *        DTO con los filtros necesarios para la generación de los resultados del reporte
     * @return DTO con el resumen de la información de la generación del reporte.
     */
    @POST
    @Path("/generarResultadosReporte")
    public ResultadoReporteDTO generarResultadosReporte(@NotNull GeneracionReporteNormativoDTO generacionReporteDTO);

    /**
     * Metodo que verifica si se genero o no un reporte normativo en un mismo periodo.
     * @param fechaInicio
     *        <code>Long</code>
     *        Fecha del periodo o fecha inicial en la cual se generara el reporte.
     * @param fechaFin
     *        <code>Long</code>
     *        Fecha final en el cual se genera el reporte.
     * @param reporteNormativo
     *        <code>ReporteNormativoEnum</code>
     *        Tipo de reporte normativo que se desea generar.
     * @return True si hay un reporte eventualmente generado para ese periodo, False de lo contrario.
     */
    @GET
    @Path("/verificarReportePeriodo/{tipoReporteNormativo}")
    public Boolean verificarReportePeriodo(@NotNull @QueryParam("fechaInicio") Long fechaInicio, @NotNull @QueryParam("fechaFinal") Long fechaFin,
            @NotNull @PathParam("tipoReporteNormativo") ReporteNormativoEnum reporteNormativo);

    /**
     * Metodo que lista todos los reportes normativos oficiales de un reporte especifico
     * @param generacionReporteDTO
     *        <code>GeneracionReporteNormativoDTO</code>
     *        DTO que contiene los filtros necesarios para obtener los registros de los reportes oficiales
     * @return Lista de los reportes oficiales de un tipo de reporte normativo en especifico.
     */
    @POST
    @Path("/consultarHistoricosReportesOficiales")
    public List<GeneracionReporteNormativoDTO> consultarHistoricosReportesOficiales(
            @NotNull GeneracionReporteNormativoDTO generacionReporteDTO, @Context UriInfo uri, @Context HttpServletResponse response);

}
