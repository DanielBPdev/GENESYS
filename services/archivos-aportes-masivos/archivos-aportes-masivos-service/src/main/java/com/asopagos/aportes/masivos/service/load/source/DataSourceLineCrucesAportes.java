package com.asopagos.aportes.masivos.service.load.source;

import java.util.List;
import javax.persistence.EntityManager;

import com.asopagos.aportes.masivos.dto.CruceAportesSalidaDTO;
import com.asopagos.aportes.masivos.service.constants.NamedQueriesConstants;
import com.asopagos.locator.ResourceLocator;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import co.com.heinsohn.lion.filegenerator.dto.QueryFilterInDTO;
import co.com.heinsohn.lion.filegenerator.ejb.ILineDataSource;
import co.com.heinsohn.lion.filegenerator.exception.FileGeneratorException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import javax.persistence.StoredProcedureQuery;
import java.util.ArrayList;
import com.asopagos.aportes.masivos.service.business.interfaces.EntityManagerCorePersistenceLocal;
import java.lang.reflect.Type;
/**
 * <b>Descripcion:</b> Clase que se encarga de obtener la información fuente para la generación de archivos de descuento<br/>
 * <b>Módulo:</b> Asopagos - HU 432<br/>
 *
 * @author <a href="mailto:rlopez@heinsohn.com.co"> Roy López Cardona</a>
 */
public class DataSourceLineCrucesAportes implements ILineDataSource {

    /**
     * Referencia al logger
     */
    final ILogger logger = LogManager.getLogger(DataSourceLineCrucesAportes.class);
    
    /**
     * (non-Javadoc)
     * @see co.com.heinsohn.lion.filegenerator.ejb.ILineDataSource#getData(co.com.heinsohn.lion.filegenerator.dto.QueryFilterInDTO, int,
     *      int, javax.persistence.EntityManager)
     */
 
    public List<Object> getData(QueryFilterInDTO arg0, int arg1, int arg2, EntityManager em) throws FileGeneratorException {
        logger.info("Inicia getData(List<LineArgumentDTO>, EntityManager)");
            
        Gson gsonEntrada = new GsonBuilder().create();
        EntityManager entityManager = obtenerEntityCore();
        List<CruceAportesSalidaDTO> cruce = new ArrayList<CruceAportesSalidaDTO>();
        CruceAportesSalidaDTO cruceAportesSalidaDTO = new CruceAportesSalidaDTO();
        cruceAportesSalidaDTO.setTipoIdentificacion("Tipo identificación del Aportante");
        cruceAportesSalidaDTO.setNumeroIdentificacion("Número identificación del Aportante");
        cruceAportesSalidaDTO.setNombre("Nombre/Razón social");
        cruceAportesSalidaDTO.setTipoAportante("Tipo aportante");
        cruceAportesSalidaDTO.setEstadoCCF("Estado en CCF");
        cruceAportesSalidaDTO.setTipoAfiliacionCCF("Tipo de Afiliación en CCF");
        cruceAportesSalidaDTO.setFechaRetiro("Fecha de retiro");

        cruce.add(cruceAportesSalidaDTO);
        String parametrosJson = gsonEntrada.toJson(((ArchivoCruceAportesFilterDTO) arg0).getAportantes());
        logger.info("Inicia getData(List<LineArgumentDTO>, EntityManager) "+parametrosJson);

        StoredProcedureQuery archivosGenerar = entityManager
                .createNamedStoredProcedureQuery(NamedQueriesConstants.ASP_JsonCruceAportes)
                .setParameter("Aportes", parametrosJson);
        archivosGenerar.execute();
        Gson gson = new GsonBuilder().create();
        Type listType = new TypeToken<List<CruceAportesSalidaDTO>>(){}.getType();
        cruce.addAll(gson.fromJson((String)archivosGenerar.getSingleResult(), listType));
        logger.info("Finaliza getData");
        return  (List) cruce;
    }
    
    /**
     * Método encargado de obtener una instancia del EntityManager direccionado al modelo de datos de Subsidio
     * @return <b>EntityManager</b>
     *         Instancia del EntityManager direccionado al modelo de datos de Subsidio
     */
    private EntityManager obtenerEntityCore() {
        EntityManagerCorePersistenceLocal emCore = ResourceLocator.lookupEJBReference(EntityManagerCorePersistenceLocal.class);
        return emCore.getEntityManager();
    }

}
