package com.asopagos.archivos.business.ejb;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.UriInfo;

import com.asopagos.archivos.business.interfaces.IConsultasModeloCore;
import com.asopagos.archivos.constants.NamedQueriesConstants;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.dto.InformacionArchivoDTO;
import com.asopagos.entidades.ccf.afiliaciones.ItemChequeo;
import com.asopagos.entidades.ccf.archivos.ArchivoAlmacenado;
import com.asopagos.entidades.ccf.archivos.PropietarioArchivo;
import com.asopagos.entidades.ccf.archivos.VersionArchivo;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pagination.QueryBuilder;

import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.util.CalendarUtils;
import com.google.common.collect.ImmutableList;

/**
 * <b>Descripcion:</b> Clase que implementa las funciones para la consulta de información en
 * el modelo de datos Core para el servicio de <code>ArchivosService</code><br/>
 * 
 * <b>Módulo:</b> Asopagos - HU-TRA-444 <br/>
 *
 * @author <a href="mailto:hhernandez@heinsohn.com.co> Ricardo Hernandez Cediel</a>
 */
@Stateless
public class ConsultasModeloCore implements IConsultasModeloCore, Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ConsultasModeloCore.class);

    /** Entity Manager */
    @PersistenceContext(unitName = "archivos_PU")
    private EntityManager entityManagerCore;
    
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public boolean clasificarRequisitoDocumental(InformacionArchivoDTO informacionArchivoDTO){
    	String firmaServicio = "ConsultasModeloCore.clasificarRequisitoDocumental(InformacionArchivoDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        ItemChequeo requisitoDocumental = null;
        boolean success = false; 
        Long idSolicitud = null;
        Long idRequisito = null;
        Long idPersona = null;
        idSolicitud = informacionArchivoDTO.getIdSolicitud();
        idRequisito = informacionArchivoDTO.getIdRequisito();
        idPersona = informacionArchivoDTO.getIdPersona();
		try {
			if(idSolicitud != null && idRequisito != null && idPersona != null){
				requisitoDocumental = buscarRequisitoDocumental(idSolicitud, idRequisito, idPersona);
				if(requisitoDocumental != null) {
					requisitoDocumental.setIdentificadorDocumento(informacionArchivoDTO.getIdentificadorDocumento());
					entityManagerCore.merge(requisitoDocumental);
					success = true;
				}
			}else{
				logger.error(firmaServicio + " :: " +MensajesGeneralConstants.ERROR_TECNICO_INESPERADO 
					+ " :: " + "Datos asociados para buscar el requisito documental "
						+ "son invalidos. El documento no sepuede clasificar" );	
			}
		} catch (Exception e) {
			logger.error(firmaServicio + " :: " +MensajesGeneralConstants.ERROR_TECNICO_INESPERADO 
				+ " :: " + e.getMessage() );
			//throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
    	return success;
    }
    
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public ItemChequeo buscarRequisitoDocumental(Long idSolicitud, Long idRequisito, Long idPersona){
    	String firmaServicio = "ConsultasModeloCore.buscarRequisitoDocumental(String, String, String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        ItemChequeo itemChequeo = null; 
		try {
			itemChequeo = (ItemChequeo) entityManagerCore.createNamedQuery(
				NamedQueriesConstants.BUSCAR_REQUISITO_DOCUMENTAL_POR_ID_SOLICITUD_ID_REQUISITO_ID_PERSONA)
				.setParameter("idSolicitud", idSolicitud)
				.setParameter("idRequisito", idRequisito)
				.setParameter("idPersona", idPersona)
				.getSingleResult();
		} catch (NoResultException noe) {
		} catch (Exception e) {
			logger.error(firmaServicio + " :: " +MensajesGeneralConstants.ERROR_TECNICO_INESPERADO 
				+ " :: " + e.getMessage() );
			//throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
    	return itemChequeo;
    }
    
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
   	public PropietarioArchivo registrarPropietarioArchivo(InformacionArchivoDTO infoFile){
   		String firmaServicio = "ConsultasModeloCore.registrarPropietarioArchivo( InformacionArchivoDTO )";
           logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
           PropietarioArchivo propietario = null;
           try{
           	propietario = new PropietarioArchivo();
   	        propietario.setNumeroIdentificacion(infoFile.getNumeroIdentificacionPropietario());
   	        propietario.setTipoIdentificacion(infoFile.getTipoIdentificacionPropietario());
   	        propietario.setTipoPropietario(infoFile.getTipoPropietario());
   	        entityManagerCore.persist(propietario);
   		} catch (Exception e) {
   			propietario = null;
   			logger.error(firmaServicio + " :: " +MensajesGeneralConstants.ERROR_TECNICO_INESPERADO 
   				+ " :: " + e.getMessage() );
   			//throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
   		}
           logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
           return propietario;
   	}
   	
    @Override
   	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public ArchivoAlmacenado registrarArchivoAlmacenado(Long idPropietario, InformacionArchivoDTO infoFile){
   		String firmaServicio = "ConsultasModeloCore.registrarArchivoAlmacenado( Long, InformacionArchivoDTO )";
           logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
           ArchivoAlmacenado archivo = null;
           try{
           	archivo = new ArchivoAlmacenado();
               archivo.setIdPropietario(idPropietario);
               archivo.setIdRequisito(infoFile.getIdRequisito());
               archivo.setIdSolicitud(infoFile.getIdSolicitud());
               entityManagerCore.persist(archivo);
   		} catch (Exception e) {
   			archivo = null;
   			logger.error(firmaServicio + " :: " +MensajesGeneralConstants.ERROR_TECNICO_INESPERADO 
   				+ " :: " + e.getMessage() );
   			//throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
   		}
           logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
           return archivo;
   	}
   	
    @Override
   	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public VersionArchivo registrarVersionArchivo( Long idArchivoAlmacenado, 
   		String nombreArchivo, String version, InformacionArchivoDTO infoFile ){
   		String firmaServicio = 
   			"ConsultasModeloCore.registrarVersionArchivo( Long, String, String, InformacionArchivoDTO )";
           logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
           VersionArchivo versionArchivo = null;
           try{
           	versionArchivo = new VersionArchivo();
               versionArchivo.setIdArchivoAlmacenado(idArchivoAlmacenado);
               versionArchivo.setFecha(new Date());
               versionArchivo.setIdentificador(nombreArchivo);
               versionArchivo.setVersion(version);//blob.getGeneration().toString()
               //Se trunca la metadata debido a la longitud del campo VersionArchivo.veaMetadata
               int length = infoFile.getJsonMetadata().length();
               if(length > 300){
               	length = 300;
               }
               versionArchivo.setMetadata(infoFile.getJsonMetadata().substring(0, length));
               entityManagerCore.persist(versionArchivo);
   		} catch (Exception e) {
   			versionArchivo = null;
   			logger.error(firmaServicio + " :: " +MensajesGeneralConstants.ERROR_TECNICO_INESPERADO 
   				+ " :: " + e.getMessage() );
   			//throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
   		}
           logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
           return versionArchivo;
   	}
   	
    @Override
   	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Long consultarPropietarioDocumento( InformacionArchivoDTO infoFile ){
   		String firmaServicio = "ConsultasModeloCore.registrarArchivoAlmacenado( Long, InformacionArchivoDTO )";
           logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
           Long idPropietario  = null;
           try{
           	idPropietario = (Long) entityManagerCore.createNamedQuery(
                       NamedQueriesConstants.ARCHIVOS_CONSULTAR_PROPIETARIO_POR_TIPO_ID_NUMERO_ID_TIPO_PROPIETARIO)
                       .setParameter("tipoIdentificacion", infoFile.getTipoIdentificacionPropietario())
                       .setParameter("numeroIdentificacion", infoFile.getNumeroIdentificacionPropietario())
                       .setParameter("tipoPropietario", infoFile.getTipoPropietario())
                       .getSingleResult();

       	} catch (NoResultException noe) {
   		} catch (Exception e) {
   			logger.error(firmaServicio + " :: " +MensajesGeneralConstants.ERROR_TECNICO_INESPERADO 
   				+ " :: " + e.getMessage() );
   			//throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
   		}
           logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
           return idPropietario;
   	}
   	
    @Override
   	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Long consultarRequisitoPropietarioDocumento(Long idPropietario, InformacionArchivoDTO infoFile ){
   		String firmaServicio = "ConsultasModeloCore.registrarArchivoAlmacenado( Long, InformacionArchivoDTO )";
           logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
           Long idArchivoAlmacenado = null;
           try{
       	   idArchivoAlmacenado = (Long) entityManagerCore.createNamedQuery(
                  NamedQueriesConstants.ARCHIVOS_CONSULTAR_VERSION_ARCHIVO)
                  .setParameter("idRequisito", infoFile.getIdRequisito())
                  .setParameter("idPropietario", idPropietario)//TODO que sucede si el idPropietario no existe con anterioridad?
                  .getSingleResult();
       	} catch (NoResultException noe) {
   		} catch (Exception e) {
   			logger.error(firmaServicio + " :: " +MensajesGeneralConstants.ERROR_TECNICO_INESPERADO 
   				+ " :: " + e.getMessage() );
   			//throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
   		}
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return idArchivoAlmacenado;
   	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public boolean eliminarRegistroVersionArchivo(String identificadorArchivo) {
		String firmaServicio = "ConsultasModeloCore.eliminarRegistroVersionArchivo( String )";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
		boolean success = false;
		int rowsUpdated = 0;
		try{
			rowsUpdated = entityManagerCore.createNamedQuery(NamedQueriesConstants.ELIMINAR_VERSION_ARCHIVO_ID)
				.setParameter("identificador", identificadorArchivo).executeUpdate();
			if(rowsUpdated > 0){
			    success = true;   
		    }
		} catch (Exception e) {
			logger.error(firmaServicio + " :: " +MensajesGeneralConstants.ERROR_TECNICO_INESPERADO 
					+ " :: " + e.getMessage() );
			//throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	    logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
	    return success;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public boolean eliminarRegistroVersionArchivo(String identificadorArchivo, String versionDocumento) {
		String firmaServicio = "ConsultasModeloCore.eliminarRegistroVersionArchivo( String, String )";
		logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
		boolean success = false;
		int rowsUpdated = 0;
		try{
			rowsUpdated = entityManagerCore.createNamedQuery(NamedQueriesConstants.ELIMINAR_VERSION_ARCHIVO_ID_VERSION)
                    .setParameter("identificador", identificadorArchivo)
                    .setParameter("version", versionDocumento)
                    .executeUpdate();
			if(rowsUpdated > 0){
			    success = true;   
		    }
		} catch (Exception e) {
			logger.error(firmaServicio + " :: " +MensajesGeneralConstants.ERROR_TECNICO_INESPERADO 
					+ " :: " + e.getMessage() );
			//throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	    logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
	    return success;
	}

	@Override
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Long> consultarRegistrosVersionArchivoPorId(String identificadorArchivo) {
		String firmaServicio = "ConsultasModeloCore.consultarRegistrosVersionArchivoPorId( Long )";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        List<Long> lstIdsVersionArchivo = null;
        try{
        	lstIdsVersionArchivo =  entityManagerCore.createNamedQuery(
        			NamedQueriesConstants.CONSULTAR_ARCHIVO_POR_IDENTIFICADOR)
                    .setParameter("identificador", identificadorArchivo)
                    .getResultList();
    	} catch (NoResultException noe) {
		} catch (Exception e) {
			logger.error(firmaServicio + " :: " +MensajesGeneralConstants.ERROR_TECNICO_INESPERADO 
				+ " :: " + e.getMessage() );
			//throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
     logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
     return lstIdsVersionArchivo;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public boolean eliminarRegistroArchivoAlmacenado(Long idArchivoAlmacenado) {
		String firmaServicio = "ConsultasModeloCore.eliminarRegistroArchivoAlmacenado( String ) 2";
		logger.info(ConstantesComunes.INICIO_LOGGER + firmaServicio);
		boolean success = false;
		int rowsUpdated = 0;
		try{
                    VersionArchivo versionArchivo  = (VersionArchivo)entityManagerCore.createNamedQuery(NamedQueriesConstants.ARCHIVOS_CONSULTAR_VERSION_ARCHIVO_ID_ARCHIVO)
                    .setParameter("idArchivoAlmacenado", idArchivoAlmacenado)
                    .getSingleResult();  
                    
                    logger.info(ConstantesComunes.INICIO_LOGGER + " eliminarRegistroArchivoAlmacenado " + versionArchivo.getIdentificador());
                    logger.info(ConstantesComunes.INICIO_LOGGER + " eliminarRegistroArchivoAlmacenado " + versionArchivo.getIdVersionArchivo());
                    
			rowsUpdated = entityManagerCore.createNamedQuery(NamedQueriesConstants.ARCHIVOS_ELIMINAR_ARCHIVO)
                    .setParameter("idArchivoAlmacenado", idArchivoAlmacenado)
                    .executeUpdate();                
                    
                    if(rowsUpdated > 0){
                        logger.info(ConstantesComunes.INICIO_LOGGER + " ELIMINAR DOC ");
                        rowsUpdated = entityManagerCore.createNamedQuery(NamedQueriesConstants.ELIMINAR_DOCUMENTO_SOPORTE_POR_IDENTIFICADOR)
                        .setParameter("identificacionDocumento", versionArchivo.getIdentificador())
                        .setParameter("version", versionArchivo.getVersion())
                        .executeUpdate(); 
			    success = true;   
		    }
		} catch (Exception e) {
			logger.error(firmaServicio + " :: " +MensajesGeneralConstants.ERROR_TECNICO_INESPERADO 
					+ " :: " + e.getMessage() );
			//throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
		}
	    logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
	    return success;
	}

	@Override
	public List<Object[]> consultarMetadataArchivo(TipoIdentificacionEnum tipoId, String numeroId) {
		System.out.println(tipoId.name());
		return (List<Object[]>) entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_METADATA_RESTANTE_ARCHIVO)
				.setParameter("tipoId", tipoId.name())
				.setParameter("numeroId", numeroId)
				.getResultList();
	}
   
}
