package com.asopagos.validaciones.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import com.asopagos.dto.DatosBasicosIdentificacionDTO;

/**
 * <b>Descripción:</b> Interface que define los métodos de negocio relacionados 
 * con la gestión de validaciones para la afiliación de empleadores
 * <b>Historia de Usuario:</b> HU-TRA-059 Digitar datos y verificar requisitos
 * 
 * @author Sergio Briñez <sbrinez@heinsohn.com.co>
 */
@Path("empleadores")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface ValidacionesService {
    
	
    /**
     * Método encargado de validar si un empleador tiene deudas pendientes en la
     * caja de compensación
     * 
     * @param datosBasicosIdentificacionDTO 
     * @return El resultado de la validación
     */	
	@POST
	@Path("validarDeudasPendientes")
    public Boolean validarDeudasPendientes(DatosBasicosIdentificacionDTO datosBasicosIdentificacionDTO);
	
    /**
     * Método encargado de validar si un empleador se encuentra en listas negras
     * 
     * @param datosBasicosIdentificacionDTO 
     * @return El resultado de la validación
     */         
	@POST
	@Path("existeEnListasNegras")
    public Boolean existeEnListasNegras(DatosBasicosIdentificacionDTO datosBasicosIdentificacionDTO);
	
    /**
     * Método encargado de validar si un empleador se encuentra registrado en
     * cámara de comercio
     * 
     * @param datosBasicosIdentificacionDTO 
     * @return El resultado de la validación
     */    
	@POST
	@Path("validarRegistroRUES")
    public Boolean validarRegistroRUES(DatosBasicosIdentificacionDTO datosBasicosIdentificacionDTO);	
    	
    /**
     * Método encargado de validar si un empleador tiene impedimentos GIASS
     * 
     * @param datosBasicosIdentificacionDTO 
     * @return El resultado de la validación
     */    
	@POST
	@Path("validarImpedimentosGIASS")
    public Boolean validarImpedimentosGIASS(DatosBasicosIdentificacionDTO datosBasicosIdentificacionDTO);
	
}
