/**
 * 
 */
package com.asopagos.validaciones.fovis.service;

import com.asopagos.validaciones.fovis.dto.HistoricoDTO;

/**
 * Contiene los servicios de almacenamiento de información asociada al procesamiento de validaciones
 * @author Jose Arley Correa Salamanca <jocorrea@heinsohn.com.co>
 *
 */
public interface ValidacionesFovisPersistenceService {

    /**
     * Servicio asíncrono que realiza el registro de las validaciones ejecutadas
     * @param datoHistorico
     *        Información historica de validaciones ejecutadas
     */
    public void persistirHistoricoValidaciones(HistoricoDTO datoHistorico);
}
