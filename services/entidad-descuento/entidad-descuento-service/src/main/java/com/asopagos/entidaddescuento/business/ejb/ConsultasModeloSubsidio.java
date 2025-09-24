package com.asopagos.entidaddescuento.business.ejb;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.entidaddescuento.business.interfaces.IConsultasModeloSubsidio;
import com.asopagos.entidaddescuento.constants.NamedQueriesConstants;
import com.asopagos.entidaddescuento.dto.ArchivoEntidadDescuentoSubsidioPignoradoModeloDTO;
import com.asopagos.entidades.subsidiomonetario.liquidacion.ArchivoEntidadDescuentoSubsidioPignorado;
import com.asopagos.enumeraciones.subsidiomonetario.entidadDescuento.EstadoCargaArchivoDescuentoEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.TechnicalException;

/**
 * <b>Descripcion:</b> Clase que representa la gestion de las operaciones en el modelo de datos Subsidio para el servicio que gestionar la
 * informacción de trazabilidad de archivos de entidades de descuento<br/>
 * <b>Módulo:</b> Asopagos - HU440<br/>
 *
 * @author <a href="mailto:rlopez@heinsohn.com.co"> Roy López Cardona</a>
 */
@Stateless
public class ConsultasModeloSubsidio implements IConsultasModeloSubsidio, Serializable{

    private static final long serialVersionUID = 1L;

    /**Entity Manager Subsidio*/
    @PersistenceContext(unitName="subsidioStaging_PU")
    private EntityManager entityManagerSubsidio;

    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ConsultasModeloCore.class);

    /**
     * (non-Javadoc)
     * @see com.asopagos.entidaddescuento.business.interfaces.IConsultasModeloSubsidio#registrarTrazabilidadArchivoDescuentos(com.asopagos.entidaddescuento.dto.ArchivoEntidadDescuentoSubsidioPignoradoModeloDTO)
     */
    @Override
    public Long registrarTrazabilidadArchivoDescuentos(ArchivoEntidadDescuentoSubsidioPignoradoModeloDTO informarcionTrazabilidadDTO) {
        String firmaServicio = "ConsultasModeloSubsidio.buscarProximoCodigoEntidadDescuento(EntidadDescuentoModeloDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        ArchivoEntidadDescuentoSubsidioPignorado informacionTrazabilidad = informarcionTrazabilidadDTO.convertToEntity();
        try {
            entityManagerSubsidio.persist(informacionTrazabilidad);
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        return informacionTrazabilidad.getIdArchivoEntidadDescuentoSubsidioMonetario();
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.entidaddescuento.business.interfaces.IConsultasModeloSubsidio#actualizarTrazabilidadArchivoDescuentos(com.asopagos.entidaddescuento.dto.ArchivoEntidadDescuentoSubsidioPignoradoModeloDTO)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Long actualizarTrazabilidadArchivoDescuentos(ArchivoEntidadDescuentoSubsidioPignoradoModeloDTO informarcionTrazabilidadDTO) {
        String firmaServicio = "ConsultasModeloSubsidio.actualizarTrazabilidadArchivoDescuentos(EntidadDescuentoModeloDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        ArchivoEntidadDescuentoSubsidioPignorado informacionTrazabilidad = null;
        try {
            informacionTrazabilidad = entityManagerSubsidio
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_ARCHIVO_ENTIDAD_DESCUENTO_SUBSIDIO_PIGNORADO_POR_ID,
                            ArchivoEntidadDescuentoSubsidioPignorado.class)
                    .setParameter("idInformacionTrazabilidad", informarcionTrazabilidadDTO.getIdArchivoEntidadDescuentoSubsidioMonetario())
                    .getSingleResult();

            informacionTrazabilidad.setEstadoCarga(informarcionTrazabilidadDTO.getEstadoCarga());
            informacionTrazabilidad.setFechaCargue(informarcionTrazabilidadDTO.getFechaCargue());
            if (informarcionTrazabilidadDTO.getEstadoCarga().equals(EstadoCargaArchivoDescuentoEnum.ANULADO)) {
                informacionTrazabilidad.setCausalAnulacion(informarcionTrazabilidadDTO.getCausalAnulacion());
            }
            entityManagerSubsidio.merge(informacionTrazabilidad);
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        return informacionTrazabilidad.getIdArchivoEntidadDescuentoSubsidioMonetario();
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.entidaddescuento.business.interfaces.IConsultasModeloSubsidio#obtenerInformacionTrazabilidad()
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<ArchivoEntidadDescuentoSubsidioPignoradoModeloDTO> obtenerInformacionTrazabilidad(List<String> nombresArchivos,
                                                                                                  List<Long> identificadoresEntidades) {
        String firmaServicio = "ConsultasModeloSubsidio.obtenerInformacionTrazabilidad()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        List<ArchivoEntidadDescuentoSubsidioPignoradoModeloDTO> archivosEntidadDescuentoDTO = new ArrayList<>();

        try {
            if(nombresArchivos == null || nombresArchivos.isEmpty()) {
                nombresArchivos = new ArrayList<>();
                nombresArchivos.add("-");
            }
            List<ArchivoEntidadDescuentoSubsidioPignorado> archivosEntidadDescuento = entityManagerSubsidio
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_ARCHIVO_ENTIDADES_DESCUENTO_ESTADO_CARGADO_PROCESADO,
                            ArchivoEntidadDescuentoSubsidioPignorado.class)
                    .setParameter("listaNombres", nombresArchivos).setParameter("listaActivas", identificadoresEntidades).getResultList();

            for (ArchivoEntidadDescuentoSubsidioPignorado archivoEntidadDescuentoSubsidioPignorado : archivosEntidadDescuento) {
                ArchivoEntidadDescuentoSubsidioPignoradoModeloDTO archivoDTO = new ArchivoEntidadDescuentoSubsidioPignoradoModeloDTO();
                archivoDTO.convertToDTO(archivoEntidadDescuentoSubsidioPignorado);
                archivosEntidadDescuentoDTO.add(archivoDTO);
            }
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return archivosEntidadDescuentoDTO;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.entidaddescuento.business.interfaces.IConsultasModeloSubsidio#obtenerInformacionTrazabilidadId(java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public ArchivoEntidadDescuentoSubsidioPignoradoModeloDTO obtenerInformacionTrazabilidadId(Long idTrazabilidad) {
        String firmaServicio = "ConsultasModeloSubsidio.obtenerInformacionTrazabilidadArchivoDescuento(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        ArchivoEntidadDescuentoSubsidioPignoradoModeloDTO informacionTrazabilidadDTO = new ArchivoEntidadDescuentoSubsidioPignoradoModeloDTO();
        try {
            ArchivoEntidadDescuentoSubsidioPignorado informacionTrazabilidad = entityManagerSubsidio
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_ARCHIVO_ENTIDAD_DESCUENTO_ID,
                            ArchivoEntidadDescuentoSubsidioPignorado.class)
                    .setParameter("idTrazabilidad", idTrazabilidad).getSingleResult();

            informacionTrazabilidadDTO.convertToDTO(informacionTrazabilidad);

        } catch (NoResultException e) {
            return null;
        } catch (NonUniqueResultException e) {
            logger.error("Ocurrió un error inesperado", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_MAS_DE_UN_UNICO_RECURSO);
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        return informacionTrazabilidadDTO;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.entidaddescuento.business.interfaces.IConsultasModeloSubsidio#obtenerIdentificadoresTrazabilidadRadicacion(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Long> obtenerEntidadesDescuentoRadicacion(String numeroRadicacion) {
        String firmaServicio = "ConsultasModeloSubsidio.obtenerEntidadesDescuentoRadicacion(String)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        List<Long> retorno = new ArrayList<>();
        try {
            List<BigInteger> entidadesDescuento = entityManagerSubsidio
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_ENTIDADESDESCUENTO_NUMERO_RADICACION)
                    .setParameter("numeroRadicacion", numeroRadicacion).getResultList();

            if (entidadesDescuento != null && !entidadesDescuento.isEmpty()) {
                for (BigInteger idEntidadDescuento : entidadesDescuento) {
                    retorno.add(idEntidadDescuento.longValue());
                }
            }
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        return retorno;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloSubsidio#actualizarArchivosDescuentoLiquidacionCancelada(java.lang.String)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void actualizarArchivosDescuentoLiquidacionCancelada(String numeroRadicacion) {
        String firmaMetodo = "ConsultasModeloSubsidio.actualizarArchivosDescuentoLiquidacionCancelada(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        try {
            entityManagerSubsidio.createNamedQuery(NamedQueriesConstants.ACTUALIZAR_SALDOS_PENDIENTE_DIFERENTE_LIQUIDACION)
                    .setParameter("numeroRadicado", numeroRadicacion).executeUpdate();

            entityManagerSubsidio.createNamedQuery(NamedQueriesConstants.ELIMINAR_REZAGOS_PIGNORAR_LIQUIDACION_CANCELADA)
                    .setParameter("numeroRadicado", numeroRadicacion).executeUpdate();

            entityManagerSubsidio.createNamedQuery(NamedQueriesConstants.ACTUALIZAR_VALORES_PIGNORAR_LIQUIDACION_CANCELADA)
                    .setParameter("numeroRadicado", numeroRadicacion).executeUpdate();

            entityManagerSubsidio.createNamedQuery(NamedQueriesConstants.ACTUALIZAR_ARCHIVO_DESCUENTO_LIQUIDACION_CANCELADA)
                    .setParameter("numeroRadicado", numeroRadicacion).executeUpdate();

        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.subsidiomonetario.business.interfaces.IConsultasModeloSubsidio#ejecutarActualizacionArchivosDescuento(java.lang.long,java.lang.int)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Long ejecutarActualizacionArchivosDescuento(Long idArchivo, Long codigoEntidad) {
        String firmaMetodo = "ConsultasModeloSubsidio.ejecutarActualizacionArchivosDescuento(Long, Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        StoredProcedureQuery query = entityManagerSubsidio
                .createNamedStoredProcedureQuery(NamedQueriesConstants.PROCEDURE_ACTUALIZAR_DESCUENTOS_NUEVO_ARCHIVO)
                .setParameter("idArchivo", idArchivo)
                .setParameter("codigoEntidad", codigoEntidad);

        query.execute();

        Long result = (Long) query.getOutputParameterValue("out");

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);

        return result;
    }

    @Override
    public String consultarArchivosDescuentoPorNombre(String nombreArchivo){
        String firmaMetodo = "ConsultasModeloSubsidio.consultarArchivosDescuentoPorNombre(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        List<String> nombre = null;
        try {
            nombre = entityManagerSubsidio
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_ARCHIVO_ENTIDAD_DESCUENTO_SUBSIDIO_PIGNORADO_POR_NOMBRE)
                    .setParameter("nombreArchivo", nombreArchivo)
                    .getResultList();
            logger.info("existe nombre "+nombre);
        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        logger.info("lista nombre "+nombre);
        return nombre.isEmpty() ? null : nombre.get(0);
    }
}
