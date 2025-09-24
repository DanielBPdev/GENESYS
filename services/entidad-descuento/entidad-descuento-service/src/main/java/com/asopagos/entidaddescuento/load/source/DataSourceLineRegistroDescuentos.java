package com.asopagos.entidaddescuento.load.source;

import java.util.List;
import javax.persistence.EntityManager;
import com.asopagos.entidaddescuento.business.interfaces.EntityManagerEntidadDescuentoPersistenceLocal;
import com.asopagos.entidaddescuento.constants.NamedQueriesConstants;
import com.asopagos.entidaddescuento.dto.ArchivoEntidadDescuentoGeneradoDTO;
import com.asopagos.entidaddescuento.persist.EntidadDescuentoPersistLine;
import com.asopagos.enumeraciones.subsidiomonetario.entidadDescuento.ResultadoPignoracionSubsidioMonetarioEnum;
import com.asopagos.locator.ResourceLocator;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import co.com.heinsohn.lion.filegenerator.dto.QueryFilterInDTO;
import co.com.heinsohn.lion.filegenerator.ejb.ILineDataSource;
import co.com.heinsohn.lion.filegenerator.exception.FileGeneratorException;

/**
 * <b>Descripcion:</b> Clase que se encarga de obtener la información fuente para la generación de archivos de descuento<br/>
 * <b>Módulo:</b> Asopagos - HU 432<br/>
 *
 * @author <a href="mailto:rlopez@heinsohn.com.co"> Roy López Cardona</a>
 */
public class DataSourceLineRegistroDescuentos implements ILineDataSource {

    /**
     * Referencia al logger
     */
    final ILogger logger = LogManager.getLogger(EntidadDescuentoPersistLine.class);
    
    /**
     * (non-Javadoc)
     * @see co.com.heinsohn.lion.filegenerator.ejb.ILineDataSource#getData(co.com.heinsohn.lion.filegenerator.dto.QueryFilterInDTO, int,
     *      int, javax.persistence.EntityManager)
     */
    @Override
    public List<Object> getData(QueryFilterInDTO arg0, int arg1, int arg2, EntityManager arg3) throws FileGeneratorException {
        logger.info("Inicia getData(List<LineArgumentDTO>, EntityManager)");
            
        EntityManager em = obtenerEntitySubsidio();
        
        List<ArchivoEntidadDescuentoGeneradoDTO> archivosGenerar = em
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_REGISTROS_ID_TRAZABILIDAD, ArchivoEntidadDescuentoGeneradoDTO.class)
                .setParameter("numeroRadicado", ((ArchivoEntidadDescuentoFilterDTO) arg0).getNumeroRadicacion())
                .setParameter("idEntidadDescuento", ((ArchivoEntidadDescuentoFilterDTO) arg0).getIdEntidadDescuento())
                .setParameter("resultado", ResultadoPignoracionSubsidioMonetarioEnum.APLICADO)
                .getResultList();
        
        logger.info("Finaliza getData");
        return (List) archivosGenerar;
    }
    
    /**
     * Método encargado de obtener una instancia del EntityManager direccionado al modelo de datos de Subsidio
     * @return <b>EntityManager</b>
     *         Instancia del EntityManager direccionado al modelo de datos de Subsidio
     */
    private EntityManager obtenerEntitySubsidio() {
        EntityManagerEntidadDescuentoPersistenceLocal emSubsidio = ResourceLocator
                .lookupEJBReference(EntityManagerEntidadDescuentoPersistenceLocal.class);
        return emSubsidio.getEntityManager();
    }

}
