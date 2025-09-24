package com.asopagos.aportes.masivos.service.business.interfaces;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;
import com.asopagos.aportes.dto.ConsultaPresenciaNovedadesDTO;
import com.asopagos.aportes.dto.CuentaAporteDTO;
import com.asopagos.aportes.dto.RecaudoCotizanteDTO;
import com.asopagos.dto.aportes.NovedadCotizanteDTO;
import com.asopagos.dto.modelo.RegistroDetalladoModeloDTO;
import com.asopagos.dto.modelo.RegistroGeneralModeloDTO;
import com.asopagos.entidades.pila.staging.RegistroDetallado;
import com.asopagos.entidades.pila.staging.RegistroGeneral;
import com.asopagos.entidades.pila.staging.Transaccion;
import com.asopagos.enumeraciones.aportes.TipoCotizanteEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * <b>Descripcion:</b> Interfaz que define las funciones para la consulta de información en
 * el modelo de datos de Staging<br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author <a href="Andres Rey:andres.rey@asopagos.com"> Andres Rey</a>
 */
@Local
public interface IConsultasModeloStaging {

    /**
     * Método encargado de completar la información de una aporte original para el caso de PILA
     * @param registroGeneral 
     * @param aporteOrigi
     * @return a
     */
    public CuentaAporteDTO consultarDatosPlanillaAporte(Long registroGeneral);
    
    /**
     * Método encargado de la consulta de registros generales por listado de IDs
     * @param idsRegistrosGeneral
     * @return
     */
    public Map<Long, RegistroGeneralModeloDTO> consultarRegistrosGeneralesPorListaId(List<Long> idsRegistrosGeneral);

}