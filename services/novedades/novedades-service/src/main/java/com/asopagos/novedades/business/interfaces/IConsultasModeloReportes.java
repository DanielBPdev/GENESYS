package com.asopagos.novedades.business.interfaces;

import java.util.List;
import javax.ejb.Local;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.UriInfo;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.novedades.dto.DatosNovedadEmpleadorPaginadoDTO;
import com.asopagos.novedades.dto.DatosNovedadRegistradaPersonaDTO;
import com.asopagos.novedades.dto.FiltrosDatosNovedadDTO;

/**
 * <b>Descripcion:</b> Interfaz que define las funciones para la consulta de información en
 * el modelo de datos de Reportes <br/>
 *
 * @author Jose Arley Correa Salamanca <jocorrea>
 */
@Local
public interface IConsultasModeloReportes {

    public List<DatosNovedadRegistradaPersonaDTO> consultarNovedadesRegistradasPersona(UriInfo uriInfo, HttpServletResponse response,
            TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion);
    
    public DatosNovedadEmpleadorPaginadoDTO consultarNovedadesEmpleador(FiltrosDatosNovedadDTO filtrosDatosNovedad, UriInfo uri,
            HttpServletResponse response);
}
