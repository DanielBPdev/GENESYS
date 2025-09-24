package com.asopagos.subsidiomonetario.pagos.business.ejb;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.entidades.subsidiomonetario.pagos.ArchivoRetiroTerceroPagador;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.FunctionalConstraintException;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloSubsidio;
import com.asopagos.subsidiomonetario.pagos.constants.NamedQueriesConstants;
import com.asopagos.subsidiomonetario.pagos.constants.PagosSubsidioMonetarioConstants;
import com.asopagos.subsidiomonetario.pagos.dto.CuentaAdministradorSubsidioDTO;
import com.asopagos.subsidiomonetario.pagos.dto.DescuentoSubsidioAsignadoDTO;
import com.asopagos.subsidiomonetario.pagos.dto.DetalleSubsidioAsignadoProgramadoDTO;
import com.asopagos.util.CalendarUtils;

/**
 * <b>Descripcion:</b> Clase que implementa las funciones para la consulta de información en el modelo de datos Core <br/>
 * <b>Módulo:</b> Asopagos - HU-31-XXX<br/>
 *
 * @author <a href="mailto:hhernandez@heinsohn.com.co"> Ricardo Hernandez Cediel</a>
 * @author <a href="mailto:mosorio@heinsohn.com.co"> Miguel Angel Osorio</a>
 * @author <a href="mailto:rlopez@heinsohn.com.co"> Roy Lader Lopez</a>
 */

@Stateless
public class ConsultasModeloSubsidio implements IConsultasModeloSubsidio, Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ConsultasModeloSubsidio.class);

    /** Entity Manager */
    @PersistenceContext(unitName = "subsidio_PU")
    private EntityManager entityManagerSubsidio;

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.pagos.business.interfaces.IConsultasModeloCore#registrarCargueArchivoTerceroPagador(com.asopagos.entidades.subsidiomonetario.pagos.ArchivoRetiroTerceroPagador)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @Override
    public List<DetalleSubsidioAsignadoProgramadoDTO> consultarFechasProgramadasSubsidioFallecimiento(String numeroRadicado){

        String firmaServicio = "ConsultasModeloCore.registrarCargueArchivoTerceroPagador(ArchivoRetiroTerceroPagador)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        
        List<DetalleSubsidioAsignadoProgramadoDTO> periodosProgramados = new ArrayList<>();
        
        List<Object[]> fechasProgramadas = entityManagerSubsidio
        .createNamedQuery(NamedQueriesConstants.CONSULTAR_FECHAS_PROGRAMADAS_SUB_FALLECIMIENTO)
        .setParameter("numeroRadicado", numeroRadicado).getResultList();
        
        for (Object[] fechaProgramada : fechasProgramadas) {
        	DetalleSubsidioAsignadoProgramadoDTO fecha = new DetalleSubsidioAsignadoProgramadoDTO();
        	fecha.setPeriodoLiquidado((Date)fechaProgramada[0]);
        	fecha.setFechaProgramadaPago((Date)fechaProgramada[1]);
        	periodosProgramados.add(fecha);
		}
      
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);

        return periodosProgramados;
    }   
    
}