package com.asopagos.entidaddescuento.business.ejb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.dto.DefinicionCamposCargaDTO;
import com.asopagos.entidaddescuento.business.interfaces.IConsultasModeloCore;
import com.asopagos.entidaddescuento.constants.NamedQueriesConstants;
import com.asopagos.entidaddescuento.dto.ArchivoSalidaDescuentoSubsidioModeloDTO;
import com.asopagos.entidaddescuento.dto.EntidadDescuentoModeloDTO;
import com.asopagos.entidades.subsidiomonetario.entidadDescuento.EntidadDescuento;
import com.asopagos.entidades.subsidiomonetario.pagos.ArchivoSalidaDescuentoSubsidio;
import com.asopagos.enumeraciones.subsidiomonetario.entidadDescuento.TipoEntidadDescuentoEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.FunctionalConstraintException;
import com.asopagos.rest.exception.TechnicalException;

/**
 * <b>Descripcion:</b> Clase que representa la gestion de las operaciones en el modelo de datos Core para el servicio que gestiona las
 * entidades de descuento<br/>
 * <b>Módulo:</b> Asopagos - HU440<br/>
 *
 * @author <a href="mailto:rarboleda@heinsohn.com.co"> rarboleda</a>
 * @author <a href="mailto:rlopez@heinsohn.com.co"> Roy López Cardona</a>
 */

@Stateless
public class ConsultasModeloCore implements IConsultasModeloCore, Serializable {
    private static final long serialVersionUID = 1L;
    
    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ConsultasModeloCore.class);

    /** Entity Manager */
    @PersistenceContext(unitName="entidaddescuento_PU")
    private EntityManager entityManagerCore;
    
    /** Variable de mensaje de error */
    private String mensaje;

    /** (non-Javadoc)
     * @see com.asopagos.entidaddescuento.business.interfaces.IConsultasModeloCore#consultarEntidadDescuento(java.lang.Long, java.lang.String)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<EntidadDescuentoModeloDTO> consultarEntidadDescuento(Long codigo, String nombre) {
        
        String firmaServicio = "ConsultasModeloCore.consultarEntidadDescuento(Long,String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        
        //se declara la lista que almacenara las entidades de edscuento que coincidan con solo el nombre
        List<EntidadDescuentoModeloDTO> lstEntidadesDescuentoModeloDTO = new ArrayList<>();
        
        //se crea la entidad de descuento a ser enviada al servicio
        EntidadDescuentoModeloDTO entidadDescuentoModeloDTO = null;
        
        //se busca la entidad de descuento por codigo y nombre
        if(codigo != null && nombre !=null){
            
            try {
                entidadDescuentoModeloDTO =  entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_ENTIDAD_DESCUENTO_DTO_POR_CODIGO_NOMBRE_EXTERNA, EntidadDescuentoModeloDTO.class)
                        .setParameter("codigoEntidadDescuento", codigo)
                        .setParameter("nombreEntidadDescuento",nombre)
                        .getSingleResult();

            } catch (NoResultException e) {
                
                try {
                    entidadDescuentoModeloDTO =  entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_ENTIDAD_DESCUENTO_DTO_POR_CODIGO_NOMBRE_INTERNA, EntidadDescuentoModeloDTO.class)
                            .setParameter("codigoEntidadDescuento", codigo)
                            .setParameter("nombreEntidadDescuento",nombre)
                            .getSingleResult();

                } catch (NoResultException e1) {
                    System.out.println("noresultexception");
                    //si es nulo se retorna de una vez el objeto
                    return lstEntidadesDescuentoModeloDTO;
                }
                
            } catch (NonUniqueResultException e) {
                System.out.println("NonUniqueResultException");
            }  
            
        }else if(codigo!=null && nombre == null){ //se busca la entidad de descuento solo por código
            
                        
            try {
                entidadDescuentoModeloDTO =  entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_ENTIDAD_DESCUENTO_DTO_POR_CODIGO_EXTERNA, EntidadDescuentoModeloDTO.class)
                        .setParameter("codigoEntidadDescuento", codigo)
                        .getSingleResult();

            } catch (NoResultException e) {
                
                try {
                    entidadDescuentoModeloDTO =  entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_ENTIDAD_DESCUENTO_DTO_POR_CODIGO_INTERNA, EntidadDescuentoModeloDTO.class)
                            .setParameter("codigoEntidadDescuento", codigo)
                            .getSingleResult();

                } catch (NoResultException e1) {
                    System.out.println("noresultexception");
                    return lstEntidadesDescuentoModeloDTO;
                }
                
            } catch (NonUniqueResultException e) {
                System.out.println("NonUniqueResultException");
            } 
            
        }else { //se busca la entidad de descuento solo por nombre
            
            final String PORCENTAJE = "%";

            lstEntidadesDescuentoModeloDTO = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_ENTIDAD_DESCUENTO_DTO_POR_NOMBRE_EXTERNA,
                            EntidadDescuentoModeloDTO.class)
                    .setParameter("nombreEntidadDescuento", PORCENTAJE+nombre+PORCENTAJE).getResultList();
            
            List<EntidadDescuentoModeloDTO>lstEntidadesDescuentoAux = null;
            
            lstEntidadesDescuentoAux = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_ENTIDAD_DESCUENTO_DTO_POR_NOMBRE_INTERNA,
                            EntidadDescuentoModeloDTO.class)
                    .setParameter("nombreEntidadDescuento", PORCENTAJE+nombre+PORCENTAJE).getResultList();

            if (!lstEntidadesDescuentoModeloDTO.isEmpty()) {
                
                if(!lstEntidadesDescuentoAux.isEmpty()){
                    
                    for(EntidadDescuentoModeloDTO entidad: lstEntidadesDescuentoAux){
                        lstEntidadesDescuentoModeloDTO.add(entidad);
                    }
                }
            }else if(!lstEntidadesDescuentoAux.isEmpty()){
                
                lstEntidadesDescuentoModeloDTO = lstEntidadesDescuentoAux;
            }
            
            return lstEntidadesDescuentoModeloDTO;
        }   
        
        if(entidadDescuentoModeloDTO!=null && lstEntidadesDescuentoModeloDTO.isEmpty()){
            lstEntidadesDescuentoModeloDTO = new ArrayList<>();
            lstEntidadesDescuentoModeloDTO.add(entidadDescuentoModeloDTO);
        }
        
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return lstEntidadesDescuentoModeloDTO;
    }
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.entidaddescuento.business.interfaces.IConsultasModeloCore#consultarEntidadDescuentoId(java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public EntidadDescuentoModeloDTO consultarEntidadDescuentoId(Long idEntidadDescuento) {
        String firmaServicio = "ConsultasModeloCore.consultarEntidadDescuentoId(Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        try {
            EntidadDescuentoModeloDTO entidadDescuentoDTO = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_ENTIDAD_DESCUENTO_ID, EntidadDescuentoModeloDTO.class)
                    .setParameter("idEntidadDescuento", idEntidadDescuento).getSingleResult();

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
            return entidadDescuentoDTO;
        } catch (NoResultException e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO);
        } catch (NonUniqueResultException e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_MAS_DE_UN_UNICO_RECURSO);
        } catch (Exception e) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }
    
    /** (non-Javadoc)
     * @see com.asopagos.entidaddescuento.business.interfaces.IConsultasModeloCore#consultarPrioriodadesEntidadesDescuento()
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Long> consultarPrioriodadesEntidadesDescuento() {
        
        String firmaServicio = "ConsultasModeloCore.consultarPrioriodadesEntidadesDescuento()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        
        List<Long> lstPrioridadesExistentes = null;
        
        List<Integer> lista = null;                
        
        //se buscan las entidades de descuento
        try {
            lista =  entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_PRIORIDADES_ASIGNADAS, Integer.class)
                    .getResultList();
        } catch (NoResultException e) {
            
            return lstPrioridadesExistentes;
        }  
        
        lstPrioridadesExistentes = new ArrayList<>();
        
        for (Integer prioridad : lista) {
            
            lstPrioridadesExistentes.add((long) prioridad.intValue());        
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return lstPrioridadesExistentes;
    }

    /** (non-Javadoc)
     * @see com.asopagos.entidaddescuento.business.interfaces.IConsultasModeloCore#consultarEntidadesDescuento()
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<EntidadDescuentoModeloDTO> consultarEntidadesDescuento(Long codigo, String nombre) {

        String firmaServicio = "ConsultasModeloCore.consultarEntidadesDescuento()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
     
        List<EntidadDescuentoModeloDTO> lstEntidadesDescuentoDTO = null;
        List<EntidadDescuentoModeloDTO> lstEntidadesDescuentoDTOInterna = null;
        
        EntidadDescuentoModeloDTO entidadDescuentoModeloDTO = null;
        
        if(codigo == null && nombre == null){     
            
            
            //se buscan las entidades de descuento 
            try {
               lstEntidadesDescuentoDTO = entityManagerCore
                        .createNamedQuery(NamedQueriesConstants.CONSULTAR_ENTIDADES_DESCUENTO_DTO_EXTERNA, EntidadDescuentoModeloDTO.class)
                        .getResultList();
            } catch (NoResultException e) {
                logger.error("ConsultasModeloCore.consultarEntidadesDescuento :: Hubo un error en la consulta");
                lstEntidadesDescuentoDTO = null;
            } 
            
            try {
                
                lstEntidadesDescuentoDTOInterna = entityManagerCore
                        .createNamedQuery(NamedQueriesConstants.CONSULTAR_ENTIDADES_DESCUENTO_DTO_INTERNA, EntidadDescuentoModeloDTO.class)
                        .getResultList();
                
            } catch (NoResultException e) {
                logger.error("ConsultasModeloCore.consultarEntidadesDescuento :: Hubo un error en la consulta");
            }
            
            if(lstEntidadesDescuentoDTOInterna !=null){
               
                if(lstEntidadesDescuentoDTO!=null){
                    for(EntidadDescuentoModeloDTO eddDTO : lstEntidadesDescuentoDTOInterna){
                        //se agregan las entidades de descuento internas a la lista
                        lstEntidadesDescuentoDTO.add(eddDTO);
                    }  
                }else{
                    lstEntidadesDescuentoDTO = lstEntidadesDescuentoDTOInterna;
                }

            }
                        
        }else {           
            
            lstEntidadesDescuentoDTO = consultarEntidadDescuento(codigo, nombre);          

        }
        
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return lstEntidadesDescuentoDTO;
    }
    

    /** (non-Javadoc)
     * @see com.asopagos.entidaddescuento.business.interfaces.IConsultasModeloCore#crearEntidadDescuento(com.asopagos.entidaddescuento.dto.EntidadDescuentoModeloDTO)
     */
    @Override
    public Long crearEntidadDescuento(EntidadDescuentoModeloDTO entidadDescuentoModeloDTO) {

        String firmaServicio = "ConsultasModeloCore.crearEntidadDescuento(EntidadDescuentoModeloDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        EntidadDescuento entidadDesc = null;

        try {
            if (entidadDescuentoModeloDTO.getIdEmpresa() != null) {

                //se busca que la empresa de entidad de descuento externa no exista
                entidadDesc = entityManagerCore
                        .createNamedQuery(NamedQueriesConstants.CONSULTAR_ENTIDAD_DESCUENTO_EXTERNA_POR_ID_EMPRESA, EntidadDescuento.class)
                        .setParameter("empresaId", entidadDescuentoModeloDTO.getIdEmpresa()).getSingleResult();

                //si existe, se propaga el error.
                if (entidadDesc != null) {

                    logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio
                            + ": parámetros no válidos, la entidad de descuento externa ya esta registrada.");
                    throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_RECURSO_YA_ESTA_REGISTRADO);
                }
            }

        } catch (NoResultException e) {
            
            entidadDesc=null;
        }
        
        try {
            if (entidadDescuentoModeloDTO.getNombreEntidad() != null) {

                //se busca que la entidad de descuento interna no exista
                entidadDesc = entityManagerCore
                        .createNamedQuery(NamedQueriesConstants.CONSULTAR_ENTIDAD_DESCUENTO_INTERNA_POR_NOMBRE, EntidadDescuento.class)
                        .setParameter("nombreEntidadDescuento", entidadDescuentoModeloDTO.getNombreEntidad()).getSingleResult();

                //si existe la entidad interna, se propaga el error.
                if (entidadDesc != null) {

                    logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio
                            + ": parámetros no válidos, la entidad de descuento interna ya esta registrada.");
                    throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_RECURSO_YA_ESTA_REGISTRADO);
                }
            }

        } catch (NoResultException e) {
            
            entidadDesc=null;
        }

        Long codigoEntidadDescuento = null;

        //se busca si la prioridad existe
        try {
            Long prioridad = entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_PRIORIDAD, Long.class)
                    .setParameter("prioridad", Integer.parseInt(entidadDescuentoModeloDTO.getPrioridad())).getSingleResult();

        } catch (NoResultException e) {

            EntidadDescuento entidadDescuento = entidadDescuentoModeloDTO.convertToEntity();

            try {
                entidadDescuento.setCodigo(buscarProximoCodigoEntidadDescuento());
                entityManagerCore.persist(entidadDescuento);
            } catch (Exception e2) {
                logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio
                        + ": parámetros no válidos, el código de la entidad de descuento ya esta registrado, unique constraint");
                throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_RECURSO_INCORRECTO);
            }

            codigoEntidadDescuento = entidadDescuento.getCodigo();

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio);
            return codigoEntidadDescuento;
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaServicio + ": parámetros no válidos, prioridad repetida.");
        throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_RECURSO_YA_ESTA_REGISTRADO);
        

    }

    /** (non-Javadoc)
     * @see com.asopagos.entidaddescuento.business.interfaces.IConsultasModeloCore#modificarEntidadDescuento(com.asopagos.entidaddescuento.dto.EntidadDescuentoModeloDTO)
     */
    @Override
    public Long actualizarEntidadDescuento(EntidadDescuentoModeloDTO entidadDescuentoModeloDTO) {

        String firmaServicio = "ConsultasModeloCore.actualizarEntidadDescuento(EntidadDescuentoModeloDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
               
        EntidadDescuento entidadDescuento = null;        
        // se consulta la entidad de descuento registrada en el sistema
        try {
            entidadDescuento =  entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_ENTIDAD_DESCUENTO_POR_ID, EntidadDescuento.class)
                    .setParameter("idEntidad",entidadDescuentoModeloDTO.getIdEntidadDescuento())
                    .getSingleResult();

        } catch (NoResultException e) {
            logger.error("Ocurrió un error inesperado, el id de la entidad de descuento no se encontro para ser actualizada.");
            throw new FunctionalConstraintException(MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO);
        } 
        
        //si es una entidad interna se puede actualizar el nombre
        if(entidadDescuento.getTipo().equals(TipoEntidadDescuentoEnum.INTERNA)){
            entidadDescuento.setNombreEntidad(entidadDescuentoModeloDTO.getNombreEntidad());
        }
       
        // se editan los campos de nombre de contacto, estado y observaciones
        entidadDescuento.setNumeroCelularInterna(entidadDescuentoModeloDTO.getNumeroCelular());
        entidadDescuento.setNombreContacto(entidadDescuentoModeloDTO.getNombreContacto());
        entidadDescuento.setEstado(entidadDescuentoModeloDTO.getEstadoEntidad());
        entidadDescuento.setObservaciones(entidadDescuentoModeloDTO.getObservaciones());
        
        try {
            entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_PRIORIDAD, Long.class)
                    .setParameter("prioridad", entidadDescuentoModeloDTO.getPrioridad()).getSingleResult();
        } catch (Exception e) {
            entidadDescuento.setPrioridad(Integer.parseInt(entidadDescuentoModeloDTO.getPrioridad()));
            try {
                entityManagerCore.merge(entidadDescuento);
            } catch (Exception e2) {
                logger.error("Ocurrió un error inesperado", e2);
                throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
            }
        }
        
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        return entidadDescuento.getIdEntidadDescuento();
        
    }

    /** (non-Javadoc)
     * @see com.asopagos.entidaddescuento.business.interfaces.IConsultasModeloCore#buscarProximoCodigoEntidadDescuento()
     */
    @Override
    public Long buscarProximoCodigoEntidadDescuento() {
        
        String firmaServicio = "ConsultasModeloCore.buscarProximoCodigoEntidadDescuento(EntidadDescuentoModeloDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        
        Long codigo= null;
        
        //se busca si la prioridad existe
       codigo =  entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_CODIGO_VALOR_MAYOR, Long.class)
                    .getSingleResult();
       
       if(codigo == null){
           //si aún no hay registros de entidades de descuento, se le asigna el 1 a la primera entidad de descuento.
           codigo = new Long(1);
       }else{
          
           //se le suma uno al código maximo registrado para ser asignado a la próxima entidad
           codigo++;
           
       }           
        
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        return codigo;
        
    }
    
    /**
     * (non-Javadoc)
     * @see com.asopagos.entidaddescuento.business.interfaces.IConsultasModeloCore#obtenerEntidadesDescuentoActivas()
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Long> obtenerEntidadesDescuentoActivas() {
        String firmaServicio = "ConsultasModeloCore.obtenerInformacionTrazabilidadArchivoDescuento(String)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaServicio);

        List<Long> identificadoresEntidadesDescuento = new ArrayList<>();
        try {
            identificadoresEntidadesDescuento = entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_ENTIDADES_DESCUENTO_ACTIVAS, Long.class).getResultList();

        } catch (Exception e) {
            logger.error("Ocurrió un error inesperado", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
        
        if(identificadoresEntidadesDescuento == null){
            identificadoresEntidadesDescuento = new ArrayList<>();
        }
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaServicio);
        return identificadoresEntidadesDescuento;
    }
    
    
    /**
     * Metodo encargado de consultar los campos del archivo
     * 
     * @param fileLoadedId
     *            identificador del fileLoadedId
     * @return lista de definiciones de campos
     */
    /** (non-Javadoc)
     * @see com.asopagos.entidaddescuento.business.interfaces.IConsultasModeloCore#buscarProximoCodigoEntidadDescuento()
     */
    @Override
    public List<DefinicionCamposCargaDTO> consultarCamposDelArchivo(Long fileLoadedId) {
        try {
            List<DefinicionCamposCargaDTO> campos = (List<DefinicionCamposCargaDTO>) entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.BUSCAR_CAMPOS_ARCHIVO)
                    .setParameter("idFileDefinition", fileLoadedId).getResultList();
            return campos;
        } catch (Exception e) {
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }
    
    /** (non-Javadoc)
     * @see com.asopagos.entidaddescuento.business.interfaces.IConsultasModeloCore#crearRegistroArchivoSalidaDescuento(com.asopagos.entidaddescuento.dto.ArchivoSalidaDescuentoSubsidioModeloDTO)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void crearRegistroArchivoSalidaDescuento(ArchivoSalidaDescuentoSubsidioModeloDTO infoArchivo) {
    	String firmaServicio = "ConsultasModeloCore.crearRegistroArchivoSalidaDescuento(String)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaServicio);
    	try {
           ArchivoSalidaDescuentoSubsidio archivoSalida = infoArchivo.convertToEntity();
           entityManagerCore.persist(archivoSalida);
        } catch (Exception e) {
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    	logger.info(ConstantesComunes.FIN_LOGGER + firmaServicio);
    }
    
    @SuppressWarnings("unchecked")
	@Override
    public String obtenerArchivosSalidaDescuentos(String numeroRadicacion, Long idEntidadDescuento) {
    	String firmaServicio = "ConsultasModeloCore.obtenerArchivosSalidaDescuentos(String)";
        logger.info(ConstantesComunes.INICIO_LOGGER + firmaServicio);
    	String codigoIdentificacionECMSalida  = "";
    	try {
             List<String> codigoIdECMSalida = entityManagerCore
                     .createNamedQuery(NamedQueriesConstants.CONSULTAR_ARCHIVO_SALIDA_DESCUENTO_ENTIDAD_SOLICITUD)
                     .setParameter("numeroRadicacion", numeroRadicacion)
                     .setParameter("idEntidadDescuento", idEntidadDescuento).getResultList();
             if (codigoIdECMSalida != null && !codigoIdECMSalida.isEmpty()) {
            	 codigoIdentificacionECMSalida = codigoIdECMSalida.get(0);
             }
         } catch (Exception e) {
             throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
         }
    	logger.info(ConstantesComunes.FIN_LOGGER + firmaServicio);
        return codigoIdentificacionECMSalida;
    	
    }

}
