package com.asopagos.entidaddescuento.persist;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.asopagos.dto.ResultadoHallazgosValidacionArchivoDTO;
import com.asopagos.entidaddescuento.business.interfaces.EntityManagerEntidadDescuentoPersistenceLocal;
import com.asopagos.entidaddescuento.constants.CamposArchivoConstants;
import com.asopagos.entidaddescuento.constants.NamedQueriesConstants;
import com.asopagos.entidades.subsidiomonetario.liquidacion.SubsidioMonetarioValorPignorado;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.personas.TipoMedioDePagoEnum;
import com.asopagos.enumeraciones.subsidiomonetario.entidadDescuento.OrigenRegistroPignoracionEnum;
import com.asopagos.locator.ResourceLocator;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;

import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.loader.IPersistLine;

/**
 * <b>Descripcion:</b> Clase que contiene la implementación requerida para realizar la persistencia de la información
 * obtenida del archivo de descuentos
 * <br/>
 * <b>Módulo:</b> Asopagos - HU 311-432<br/>
 *
 * @author <a href="mailto:rlopez@heinsohn.com.co"> Roy López Cardona</a>
 */
public class EntidadDescuentoPersistLine implements IPersistLine {
    
    /**
     * Referencia al logger
     */
    final ILogger logger = LogManager.getLogger(EntidadDescuentoPersistLine.class);
    
    /**
     * Lista de identificadores asociados a los registros del archivo
     */
    private List<Long> identificadoresRegistros = new ArrayList<>();

    /**
     * (non-Javadoc)
     * @see co.com.heinsohn.lion.fileprocessing.fileloader.loader.IPersistLine#persistLine(java.util.List, javax.persistence.EntityManager)
     */
    @SuppressWarnings("unchecked")
	@Override
    public void persistLine(List<LineArgumentDTO> arg0, EntityManager emCore) throws FileProcessingException {
        logger.info("Inicia persistLine(List<LineArgumentDTO>, EntityManager)");
        
        EntityManager em = obtenerEntitySubsidio();

        for (LineArgumentDTO lineArgumentDTO : arg0) {
            logger.warn("en el for--> "+ lineArgumentDTO);
            try {
            	
            	 List<ResultadoHallazgosValidacionArchivoDTO> hallazgos = ((List<ResultadoHallazgosValidacionArchivoDTO>) lineArgumentDTO
                         .getContext().get(CamposArchivoConstants.LISTA_HALLAZGOS));

                 boolean errorLinea = false;

                 for (int i = 0; i < hallazgos.size(); i++) {
                     logger.debug("hallazgos"+hallazgos.get(i));

                     if (lineArgumentDTO.getLineNumber() == hallazgos.get(i).getNumeroLinea().longValue()) {
                         errorLinea = true;
                         break;
                     }

                 }

                if (!errorLinea) {
                	 Map<String, Object> values = lineArgumentDTO.getLineValues();

                     SubsidioMonetarioValorPignorado valorPignorado = new SubsidioMonetarioValorPignorado();
                     valorPignorado.setIdArchivoEntidadDescuentoSubsidioPignorado(
                             (Long) lineArgumentDTO.getContext().get(CamposArchivoConstants.ID_TRAZABILIDAD_ARCHIVO_DESCUENTO));
                     valorPignorado.setTipoIdentificacionTrabajador(TipoIdentificacionEnum
                             .obtenerTiposIdentificacionPILAEnum(values.get(CamposArchivoConstants.TIPO_IDENTIFICACION_TRABAJADOR).toString()));
                     valorPignorado
                             .setNumeroIdentificacionTrabajador(values.get(CamposArchivoConstants.NUMERO_IDENTIFICACION_TRABAJADOR).toString());
                     valorPignorado.setNombreTrabajador(values.get(CamposArchivoConstants.NOMBRE_TRABAJADOR).toString());
                     valorPignorado.setTipoIdentificacionAdministrador(values.get(CamposArchivoConstants.TIPO_IDENTIFICACION_ADMINISTRADOR) != null ? TipoIdentificacionEnum.obtenerTiposIdentificacionPILAEnum(
                             values.get(CamposArchivoConstants.TIPO_IDENTIFICACION_ADMINISTRADOR).toString()) : null);
                     valorPignorado.setNumeroIdentificacionAdministrador(values.get(CamposArchivoConstants.NUMERO_IDENTIFICACION_ADMINISTRADOR) != null ?
                             values.get(CamposArchivoConstants.NUMERO_IDENTIFICACION_ADMINISTRADOR).toString() : null);
                     valorPignorado.setCodigoGrupoFamiliar(
                             ((Integer) Integer.parseInt(values.get(CamposArchivoConstants.CODIGO_GRUPO_FAMILIAR).toString())).byteValue());
                     valorPignorado.setValorPignorar((BigDecimal) values.get(CamposArchivoConstants.MONTO_DESCUENTO));
                     valorPignorado.setOrigenRegistro(OrigenRegistroPignoracionEnum.CARGUE_ARCHIVO);
                     valorPignorado.setCodigoReferencia(values.get(CamposArchivoConstants.CODIGO_REFERENCIA) != null ? values.get(CamposArchivoConstants.CODIGO_REFERENCIA).toString() : null);
                     
                     //Se consulta el medio de pago asociado al Administrador de subsidio y grupo Familiar.
                     if (valorPignorado.getTipoIdentificacionAdministrador() != null && 
                    		 valorPignorado.getNumeroIdentificacionAdministrador() != null && !valorPignorado.getNumeroIdentificacionAdministrador().isEmpty()) {
                    	 List<String> medioPagoActivo = emCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_MEDIOPAGO_ADMINSUBSIDIOGRUPO)
                    	 	.setParameter("perNumeroIdentificacion", valorPignorado.getNumeroIdentificacionAdministrador())
                    	 	.setParameter("perTipoIdentificacion", valorPignorado.getTipoIdentificacionAdministrador().name())
                    	 	.setParameter("numeroGrupoFamiliar", valorPignorado.getCodigoGrupoFamiliar()).getResultList();
                    	 
                    	 if (medioPagoActivo != null && !medioPagoActivo.isEmpty()) {
                    		 TipoMedioDePagoEnum medioPago = TipoMedioDePagoEnum.obtenerTipoMedioDePagoEnum(medioPagoActivo.get(0));
                    		 valorPignorado.setMedioDePago(medioPago);
                    	 }
                     }

                     em.persist(valorPignorado);
                     identificadoresRegistros.add(valorPignorado.getIdSubsidioMonetarioValorPignorado());
                     em.flush();
                }
            } catch (Exception e) {
                FileProcessingException e1 = new FileProcessingException("Error persistiendo los valores a pignorar", e);
                e1.setLineArgumentDTO(lineArgumentDTO);
                throw e;
            }
        }
        logger.info("finaliza");
    }
    
    /**
     * (non-Javadoc)
     * @see co.com.heinsohn.lion.fileprocessing.fileloader.loader.IPersistLine#setRollback(javax.persistence.EntityManager)
     */
    @Override
    public void setRollback(EntityManager arg0) throws FileProcessingException {
        EntityManager em = obtenerEntitySubsidio();
        try {
            Query query = em.createNamedQuery(NamedQueriesConstants.ELIMINAR_REGISTROS_ARCHIVO_DESCUENTOS).setParameter("myIds",
                    identificadoresRegistros);
            int count = query.executeUpdate();
            System.out.println("*** Registros Eliminados a la Entidad SubsidioMonetarioValorPignorado" + count);
        } catch (Exception e) {
            System.out.println(e);
        }
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
