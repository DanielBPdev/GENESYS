package com.asopagos.entidades.pagadoras.dto;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import org.apache.commons.lang.StringUtils;
import com.asopagos.cache.CacheManager;
import com.asopagos.constants.ParametrosSistemaConstants;
import com.asopagos.constants.NumerosEnterosConstants;
import com.asopagos.entidades.ccf.afiliaciones.SolicitudAsociacionPersonaEntidadPagadora;
import com.asopagos.entidades.pagadoras.constants.NamedQueriesConstants;
import co.com.heinsohn.lion.filegenerator.dto.QueryFilterInDTO;
import co.com.heinsohn.lion.filegenerator.ejb.ILineDataSource;
import co.com.heinsohn.lion.filegenerator.exception.FileGeneratorException;

/**
 * La siguiente estructura de los registros está dada para el convenio de la 
 * caja de compensación “Confa” y “Colpensiones”.
 * Estructura Solicitud de Alta/Retiro
 * 
 * 
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author <a href="mailto:halzate@heinsohn.com.co"> halzate</a>
 */

public class EstructuraSolicitudAltaRetiroDataLine implements ILineDataSource {

    private List<EstructuraSolicitudAltaRetiroDTO> datos;

    /** (non-Javadoc)
     * @see co.com.heinsohn.lion.filegenerator.ejb.ILineDataSource#getData(co.com.heinsohn.lion.filegenerator.dto.QueryFilterInDTO, int, int, javax.persistence.EntityManager)
     */
    @Override
    public List<Object> getData(QueryFilterInDTO arg0, int arg1, int arg2, EntityManager em) throws FileGeneratorException {
        
        List<SolicitudAsociacionPersonaEntidadPagadora> datosAux = em.createNamedQuery(
        		NamedQueriesConstants.CONSULTAR_DATOS_ARCHIVO_FORMATO_TXT)
        		.setParameter("idEntidadPagadora", ((DatosFiltroConsultaDTO)arg0).getIdEntidadPagadora())
        		.setParameter("consecutivoGestion", ((DatosFiltroConsultaDTO)arg0).getConsecutivoGestion())
        		.getResultList();
        
        datos = new ArrayList<EstructuraSolicitudAltaRetiroDTO>();
        EstructuraSolicitudAltaRetiroDTO d;
        CacheManager.sincronizarParametrosYConstantes();
        String nitEntidadCCF = (String)CacheManager.getParametro(ParametrosSistemaConstants.NUMERO_ID_CCF);
        /* Se recorta el nit por el rago de digito de la tabla FieldDefinition */
        nitEntidadCCF = nitEntidadCCF.substring(0, 9);
        for (SolicitudAsociacionPersonaEntidadPagadora solicitud : datosAux) {
            d = new EstructuraSolicitudAltaRetiroDTO();
            d.setNumeroAfiliacion(StringUtils.leftPad(solicitud.getRolAfiliado().getIdentificadorAnteEntidadPagadora(), NumerosEnterosConstants.DOCE, "0"));
            d.setNroDocumento(StringUtils.leftPad(solicitud.getRolAfiliado().getAfiliado().getPersona().getNumeroIdentificacion(), NumerosEnterosConstants.DOCE, "0"));
            d.setNitDeEntidad(nitEntidadCCF);
            d.setNitDeEntidad(d.getNitDeEntidad().substring(0, 9));
            d.setTipoNovedad(solicitud.getConsecutivo().charAt(NumerosEnterosConstants.TRES)+"");
            datos.add(d);
        }
        return new ArrayList<Object>(datos);
    }
}
