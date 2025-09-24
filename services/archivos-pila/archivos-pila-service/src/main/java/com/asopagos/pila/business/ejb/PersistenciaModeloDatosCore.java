package com.asopagos.pila.business.ejb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.dto.modelo.DescuentoInteresMoraModeloDTO;
import com.asopagos.entidades.ccf.aportes.DescuentoInteresMora;
import com.asopagos.entidades.ccf.aportes.ListasBlancasAportantes;
import com.asopagos.entidades.ccf.core.Banco;
import com.asopagos.entidades.ccf.core.Beneficio;
import com.asopagos.entidades.ccf.personas.Empresa;
import com.asopagos.entidades.ccf.personas.EntidadPagadora;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.enumeraciones.MensajesFTPErrorComunesEnum;
import com.asopagos.enumeraciones.afiliaciones.TipoBeneficioEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.business.interfaces.IPersistenciaModeloDatosCore;
import com.asopagos.pila.constants.MensajesPersistenciaDatosEnum;
import com.asopagos.pila.constants.NamedQueriesConstants;
import com.asopagos.pila.util.ErrorFuncionalValidacionException;
import com.asopagos.rest.exception.TechnicalException;
import co.com.heinsohn.lion.fileprocessing.fileloader.FieldDefinitionLoad;
import com.asopagos.entidades.ccf.afiliaciones.PreRegistroEmpresaDesCentralizada;

/**
 * <b>Descripción:</b> Clase encargada de la implementación de servicios de persistencia con el modelo de datos Core<br>
 * <b>Módulo:</b> ArchivosPILAService - HU 386, 387, 388, 390, 391, 407, 393 <br/>
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 */
@Stateless
public class PersistenciaModeloDatosCore implements IPersistenciaModeloDatosCore, Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Referencia a la unidad de persistencia de core
     */
    @PersistenceContext(unitName = "core_PU")
    private EntityManager entityManagerCore;

    /**
     * Referencia al logger
     */
    private final ILogger logger = LogManager.getLogger(PersistenciaModeloDatosCore.class);

    /** Constante que contiene el mensaje para entidades pagadoras */
    private static final String ENTIDAD_PAGADORA = "entidades pagadoras";
    /** Constante que contiene el mensaje para empresas */
    private static final String EMPRESA = "empresas";
    /** Constante que contiene el mensaje para personas */
    private static final String PERSONA = "personas";
    /** Constante que contiene el mensaje para códigos de departamentos y/o municipios */
    private static final String CODIGO_DEPARTAMENTO_MUNICIPIO = "códigos de departamentos y/o municipios";
    /** Constante que contiene el mensaje para códigos de actividad económica */
    private static final String CODIGOS_CIIU = "códigos de actividad económica";
    /** Constante que contiene el mensaje para definiciones de campo en componente */
    private static final String DEFINICION_CAMPO = "definición del campo en parametrización de componente";
    /** Constante para el mensaje de operador financiero */
    private static final String OPERADOR_FINANCIERO = "Operadores Financieros";
    /** Constante para el mensaje de beneficio de ley */
    private static final String BENEFICIO_LEY = "Beneficio de ley";
    /** Constante que contiene el mensaje para empresas descentralizada */
    private static final String EMPRESADESCENTRALIZADA = "empresas descentralizadas";
    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.pila.business.interfaces.IPersistenciaModeloDatosCoreInterface#consultarEntidadPagadora(com.asopagos.enumeraciones.
     * personas.TipoIdentificacionEnum, java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public EntidadPagadora consultarEntidadPagadora(TipoIdentificacionEnum tipoId, String numeroIdentificacion)
            throws ErrorFuncionalValidacionException {
        logger.debug("Inicia consultarEntidadPagadora(TipoIdentificacionEnum, String)");

        EntidadPagadora result = null;

        try {
            //se consulta una entidad pagadora con su numero y tipo de identificacion
            result = (EntidadPagadora) entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_ENTIDAD_PAGADORA_POR_TIPO_NUMERO_ID)
                    .setParameter("tipoIdentificacion", tipoId).setParameter("numeroIdentificacion", numeroIdentificacion)
                    .getSingleResult();

        } catch (NoResultException e) {
            String mensaje = MensajesFTPErrorComunesEnum.ERROR_CONSULTA_SIN_DATOS.getReadableMessage(ENTIDAD_PAGADORA);

            logger.debug("Finaliza consultarEntidadPagadora(TipoIdentificacionEnum, String) - " + mensaje);

            throw new ErrorFuncionalValidacionException(mensaje, e);
        } catch (Exception e) {
            String mensaje = MensajesFTPErrorComunesEnum.ERROR_GENERAL_DATOS.getReadableMessage(e.getMessage());
            logger.error("Finaliza consultarEntidadPagadora(TipoIdentificacionEnum, String) - " + mensaje);

            throw new TechnicalException(mensaje, e);
        }

        logger.debug("Finaliza consultarEntidadPagadora(TipoIdentificacionEnum, String)");
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.asopagos.pila.business.interfaces.IPersistenciaModeloDatosCoreInterface#consultarEmpleador(com.asopagos.enumeraciones.personas.
     * TipoIdentificacionEnum, java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public Empresa consultarEmpresa(TipoIdentificacionEnum tipoId, String numeroIdentificacion) throws ErrorFuncionalValidacionException {
        logger.debug("Inicia consultarEmpleador(TipoIdentificacionEnum, String)");

        Empresa result = null;

        try {
            //se realiza la busqueda de un empleador por número y tipo de identificacion
            result = (Empresa) entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_EMPRESA_POR_TIPO_NUMERO_ID)
                    .setParameter("tipoIdentificacion", tipoId).setParameter("numeroIdentificacion", numeroIdentificacion)
                    .getSingleResult();

        } catch (NoResultException e) {
            String mensaje = MensajesFTPErrorComunesEnum.ERROR_CONSULTA_SIN_DATOS.getReadableMessage(EMPRESA);

            logger.debug("Finaliza consultarEmpleador(TipoIdentificacionEnum, String) - " + mensaje);

            throw new ErrorFuncionalValidacionException(mensaje, e);
        } catch (Exception e) {
            String mensaje = MensajesFTPErrorComunesEnum.ERROR_GENERAL_DATOS.getReadableMessage(e.getMessage());
            logger.error("Finaliza consultarEmpleador(TipoIdentificacionEnum, String) - " + mensaje);

            throw new TechnicalException(mensaje, e);
        }

        logger.debug("Finaliza consultarEmpleador(TipoIdentificacionEnum, String)");
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.asopagos.pila.business.interfaces.IPersistenciaModeloDatosCoreInterface#consultarPersona(com.asopagos.enumeraciones.personas.
     * TipoIdentificacionEnum, java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public Persona consultarPersona(TipoIdentificacionEnum tipoId, String numeroIdentificacion) throws ErrorFuncionalValidacionException {
        logger.debug("Inicia consultarPersona(TipoIdentificacionEnum, String)");

        Persona result = null;

        try {
            //Se realiza la busqueda de una persona por medio del numero y tipo de identificacion
            result = (Persona) entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_PERSONA_POR_TIPO_NUMERO_ID)
                    .setParameter("tipoIdentificacion", tipoId).setParameter("numeroIdentificacion", numeroIdentificacion)
                    .getSingleResult();

        } catch (NoResultException e) {
            String mensaje = MensajesFTPErrorComunesEnum.ERROR_CONSULTA_SIN_DATOS.getReadableMessage(PERSONA);

            logger.debug("Finaliza consultarPersona(TipoIdentificacionEnum, String) - " + mensaje);

            throw new ErrorFuncionalValidacionException(mensaje, e);
        } catch (Exception e) {
            String mensaje = MensajesFTPErrorComunesEnum.ERROR_GENERAL_DATOS.getReadableMessage(e.getMessage());
            logger.error("Finaliza consultarPersona(TipoIdentificacionEnum, String) - " + mensaje);

            throw new TechnicalException(mensaje, e);
        }

        logger.debug("Finaliza consultarPersona(TipoIdentificacionEnum, String)");
        return result;
    }

     /**
     * Se realiza la busqueda de una empresa descentralizada
     *
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<PreRegistroEmpresaDesCentralizada> consultarEmpresasDescentralizadas() {
        logger.debug("Inicia consultar Empresa Descentralizada");

        List<PreRegistroEmpresaDesCentralizada> result = null;

        try {

            result = entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_EMPRESAS_DESCENTRALIZADA).getResultList();

        } catch (NoResultException e) {
            String mensaje = MensajesFTPErrorComunesEnum.ERROR_CONSULTA_SIN_DATOS.getReadableMessage(EMPRESADESCENTRALIZADA);
            logger.debug("Finaliza consultarEmpresaDescentralizada - " + mensaje);
            throw new TechnicalException(mensaje, e);
        } catch (Exception e) {
            String mensaje = MensajesFTPErrorComunesEnum.ERROR_GENERAL_DATOS.getReadableMessage(e.getMessage());
            logger.error("Finaliza consultarPersona(TipoIdentificacionEnum, String) - " + mensaje);
            throw new TechnicalException(mensaje, e);
        }

        logger.debug("Finaliza consultar Empresa Descentralizada");
        return result;
    }

    @Override
    public PreRegistroEmpresaDesCentralizada consultarEmpresaDescentralizada(String tipoId, String numeroIdentificacion, String nombreSucursalPila, String codigoSucursal) throws ErrorFuncionalValidacionException {
        logger.info("Inicia consultarEmpresaDescentralizada(String tipoId, String numeroIdentificacion, String nombreSucursalPila, String codigoSucursal)");

        PreRegistroEmpresaDesCentralizada result = null;

        try {
            //Se realiza la busqueda de una empresa descentralizada por medio del numero, tipo de identificacion, codigo sucursal y nombre de la sucursal
            result = (PreRegistroEmpresaDesCentralizada) entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_EMPRESA_DESCENTRALIZADA)
                    .setParameter("tipoIdentificacion", tipoId)
                    .setParameter("numeroIdentificacion", numeroIdentificacion)
                    .setParameter("codigoSucursal", codigoSucursal != null ?codigoSucursal:"")
                    .setParameter("nombreSucursalPila", nombreSucursalPila != null ?nombreSucursalPila:"")
                    .getSingleResult();

        } catch (NoResultException e) {
            String mensaje = MensajesFTPErrorComunesEnum.ERROR_CONSULTA_SIN_DATOS.getReadableMessage(EMPRESADESCENTRALIZADA);

            logger.info("Finaliza consultarEmpresaDescentralizada(String tipoId, String numeroId, String nomSucursal, String codSucursal) - " + mensaje);

            throw new ErrorFuncionalValidacionException(mensaje, e);
        } catch (Exception e) {
            String mensaje = MensajesFTPErrorComunesEnum.ERROR_GENERAL_DATOS.getReadableMessage(e.getMessage());
            logger.info("Finaliza consultarEmpresaDescentralizada(String tipoId, String numeroId, String nomSucursal, String codSucursal) - " + mensaje);

            throw new TechnicalException(mensaje, e);
        }

        logger.info("Finaliza consultarEmpresaDescentralizada(String tipoId, String numeroId, String nomSucursal, String codSucursal)");
        return result;

    }

    @Override
    public PreRegistroEmpresaDesCentralizada consultarEmpresaDescentralizada(String numeroIdentificacion, String nombreSucursalPila, String codigoSucursal) throws ErrorFuncionalValidacionException {

        logger.info("Inicia consultarEmpresaDescentralizada(String numeroIdentificacion, String nombreSucursalPila, String codigoSucursal)");
        PreRegistroEmpresaDesCentralizada descentralizada = null;

        try {
            //Se realiza la busqueda de una empresa descentralizada por medio del numero de identificacion, codigo sucursal y nombre de la sucursal
            descentralizada = (PreRegistroEmpresaDesCentralizada) entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_EMPRESA_DESCENTRALIZADA_SIN_TIPO)
                    .setParameter("numeroIdentificacion", numeroIdentificacion)
                    .setParameter("codigoSucursal", codigoSucursal != null ?codigoSucursal:"")
                    .setParameter("nombreSucursalPila", nombreSucursalPila != null ?nombreSucursalPila:"")
                    .getSingleResult();

            logger.info("Esta es la descentralizada " + descentralizada.getCodigoSucursal());
            
        } catch (NoResultException e) {
            String mensaje = MensajesFTPErrorComunesEnum.ERROR_CONSULTA_SIN_DATOS.getReadableMessage(EMPRESADESCENTRALIZADA);

            logger.info("No se encontro descentralizada - " + mensaje);
            return null;
        } catch (Exception e) {
            String mensaje = MensajesFTPErrorComunesEnum.ERROR_GENERAL_DATOS.getReadableMessage(e.getMessage());
            logger.info("Finaliza consultarEmpresaDescentralizada(String numeroId, String nomSucursal, String codSucursal) - " + mensaje);

            throw new TechnicalException(mensaje, e);
        }

        logger.info("Finaliza consultarEmpresaDescentralizada(String numeroId, String nomSucursal, String codSucursal)");
        return descentralizada;

    }
    
    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.pila.business.interfaces.IPersistenciaModeloDatosCoreInterface#consultarCodigosDepartamentos()
     */
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<String> consultarCodigosDepartamentos() {
        logger.debug("Inicia consultarCodigosDepartamentos()");

        List<String> result = null;

        try {
            //se listan todos los departamentos existentes en la bd
            result = entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_CODIGOS_DEPARTAMENTO).getResultList();
        } catch (NoResultException e) {
            String mensaje = MensajesFTPErrorComunesEnum.ERROR_CONSULTA_SIN_DATOS.getReadableMessage(CODIGO_DEPARTAMENTO_MUNICIPIO);

            logger.debug("Finaliza consultarCodigosDepartamentos() - " + mensaje);

            throw new TechnicalException(mensaje, e);
        } catch (Exception e) {
            String mensaje = MensajesFTPErrorComunesEnum.ERROR_GENERAL_DATOS.getReadableMessage(e.getMessage());
            logger.error("Finaliza consultarCodigosDepartamentos() - " + mensaje);

            throw new TechnicalException(mensaje, e);
        }

        logger.debug("Finaliza consultarCodigosDepartamentos()");
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.pila.business.interfaces.IPersistenciaModeloDatosCoreInterface#consultarCodigosMunicipios()
     */
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<String> consultarCodigosMunicipios() {
        logger.debug("Inicia consultarCodigosMunicipios()");

        List<String> result = null;

        try {
            //se listan todos los codigos de los municipios en bd
            result = entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_CODIGOS_MUNICIPIO).getResultList();
        } catch (NoResultException e) {
            String mensaje = MensajesFTPErrorComunesEnum.ERROR_CONSULTA_SIN_DATOS.getReadableMessage(CODIGO_DEPARTAMENTO_MUNICIPIO);

            logger.debug("Finaliza consultarCodigosMunicipios() - " + mensaje);

            throw new TechnicalException(mensaje, e);
        } catch (Exception e) {
            String mensaje = MensajesFTPErrorComunesEnum.ERROR_GENERAL_DATOS.getReadableMessage(e.getMessage());
            logger.error("Finaliza consultarCodigosMunicipios() - " + mensaje);

            throw new TechnicalException(mensaje, e);
        }

        logger.debug("Finaliza consultarCodigosMunicipios()");
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.pila.business.interfaces.IPersistenciaModeloDatosCoreInterface#consultarCodigosCiiu()
     */
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<String> consultarCodigosCiiu() {
        logger.debug("Inicia consultarCodigosCiiu()");

        List<String> result = null;

        try {
            result = entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_CODIGOS_CIIU).getResultList();
        } catch (NoResultException e) {
            String mensaje = MensajesFTPErrorComunesEnum.ERROR_CONSULTA_SIN_DATOS.getReadableMessage(CODIGOS_CIIU);

            logger.debug("Finaliza consultarCodigosCiiu() - " + mensaje);

            throw new TechnicalException(mensaje, e);
        } catch (Exception e) {
            String mensaje = MensajesFTPErrorComunesEnum.ERROR_GENERAL_DATOS.getReadableMessage(e.getMessage());
            logger.error("Finaliza consultarCodigosCiiu() - " + mensaje);

            throw new TechnicalException(mensaje, e);
        }

        logger.debug("Finaliza consultarCodigosCiiu()");
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.business.interfaces.IPersistenciaModeloDatosCore#consultarFieldDefinition(java.util.List)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<FieldDefinitionLoad> consultarFieldDefinition(List<String> llavesCampo) {
        String firmaMetodo = "consultarFieldDefinition(List<String>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<FieldDefinitionLoad> result = new ArrayList<>();

        try {
            // los registros generales se consultan en grupos de 1000 por seguridad
            int inicio = 0;
            int fin = llavesCampo.size() > 1000 ? 1000 : llavesCampo.size();
            while (fin <= llavesCampo.size()) {
                List<String> llavesCampoTemp = llavesCampo.subList(inicio, fin);

                result.addAll(
                        entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_DEFINICION_CAMPO, FieldDefinitionLoad.class)
                                .setParameter("llaveCampo", llavesCampoTemp).getResultList());

                inicio = fin;
                fin++;
                if (fin <= llavesCampo.size()) {
                    fin = fin + 1000 <= llavesCampo.size() ? inicio + 1000 : llavesCampo.size();
                }
            }
        } catch (NoResultException e) {
            logger.debug(firmaMetodo + " :: Sin datos por agregar");
        } catch (Exception e) {
            String mensaje = MensajesFTPErrorComunesEnum.ERROR_GENERAL_DATOS.getReadableMessage(e.getMessage());
            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);

            throw new TechnicalException(mensaje, e);
        }

        if (result.isEmpty()) {
            String mensaje = MensajesFTPErrorComunesEnum.ERROR_CONSULTA_SIN_DATOS.getReadableMessage(DEFINICION_CAMPO);

            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo + " :: " + mensaje);

            throw new TechnicalException(mensaje);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.pila.business.interfaces.PersistenciaDatosValidadoresInterface#consultarOperadoresFinancieros()
     */
    @SuppressWarnings("unchecked")
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Banco> consultarOperadoresFinancieros() throws ErrorFuncionalValidacionException {
        logger.debug("Inicia consultarOperadoresFinancieros()");

        List<Banco> result = null;

        try {
            //se listan todos los operadores financieros
            result = (List<Banco>) entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_OPERADORES_FINANCIEROS)
                    .getResultList();

            logger.debug("Finaliza consultarOperadoresFinancieros()");
        } catch (NoResultException e) {
            String mensaje = MensajesFTPErrorComunesEnum.ERROR_CONSULTA_SIN_DATOS.getReadableMessage(OPERADOR_FINANCIERO);
            logger.debug("Finaliza consultarOperadoresFinancieros() - " + mensaje);
            throw new ErrorFuncionalValidacionException(mensaje, e);
        } catch (Exception e) {
            String mensaje = MensajesPersistenciaDatosEnum.ERROR_LECTURA_OPERADOR_FINANCIERO.getReadableMessage(e.getMessage());
            logger.error("Finaliza consultarOperadoresFinancieros() - " + mensaje);
            throw new TechnicalException(mensaje, e);
        }

        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.business.interfaces.IPersistenciaModeloDatosCore#consultarBeneficio(com.asopagos.enumeraciones.afiliaciones.TipoBeneficioEnum)
     */
    @Override
    public Beneficio consultarBeneficio(TipoBeneficioEnum beneficio) {
        String firmaMetodo = "ConsultaModeloDatosCore.consultarBeneficio(TipoBeneficioEnum)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Beneficio result = null;

        try {
            //se listan todos los operadores financieros
            result = (Beneficio) entityManagerCore.createNamedQuery(NamedQueriesConstants.CONSULTAR_BENEFICIO_LEY)
                    .setParameter("tipoBeneficio", beneficio).getSingleResult();

            logger.debug("Finaliza consultarOperadoresFinancieros()");
        } catch (NoResultException e) {
            String mensaje = MensajesFTPErrorComunesEnum.ERROR_CONSULTA_SIN_DATOS.getReadableMessage(BENEFICIO_LEY);
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo + " - " + mensaje);
            return null;
        } catch (Exception e) {
            String mensaje = MensajesPersistenciaDatosEnum.ERROR_LECTURA_BENEFICIO_LEY.getReadableMessage(e.getMessage());
            logger.error(ConstantesComunes.FIN_LOGGER + firmaMetodo + " - " + mensaje);
            throw new TechnicalException(mensaje, e);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.business.interfaces.IPersistenciaModeloDatosCore#consultarDescuentosInteres()
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<DescuentoInteresMoraModeloDTO> consultarDescuentosInteres() {
        String firmaMetodo = "PersistenciaModeloDatosCore.consultarDescuentosInteres()";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<DescuentoInteresMoraModeloDTO> result = null;

        try {
            List<DescuentoInteresMora> resultEntity = (List<DescuentoInteresMora>) entityManagerCore
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_CASOS_DESCUENTO_MORA).getResultList();

            result = new ArrayList<>();

            for (DescuentoInteresMora descuentoInteresMora : resultEntity) {
                DescuentoInteresMoraModeloDTO dto = new DescuentoInteresMoraModeloDTO();
                dto.convertToDTO(descuentoInteresMora);

                result.add(dto);
            }
        } catch (Exception e) {
            String mensaje = MensajesPersistenciaDatosEnum.ERROR_LECTURA_CASOS_DESCUENTO_MORA.getReadableMessage();
            logger.error(ConstantesComunes.FIN_LOGGER + firmaMetodo + " - " + mensaje);
            throw new TechnicalException(mensaje, e.getCause());
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return result;
    }
    
    /** (non-Javadoc)
     * @see com.asopagos.pila.business.interfaces.IConsultaModeloDatosCore#IPersistenciaModeloDatosCore(java.util.String)
     */
	public ListasBlancasAportantes consultarListasBlancasAportantes(String numeroIdentificacion) {
		try {
			return (ListasBlancasAportantes) entityManagerCore
					.createNamedQuery(NamedQueriesConstants.CONSULTAR_LISTA_BLANCA_PILA_POR_NUMERO_IDENTIFICACION)
					.setParameter("numeroIdentificacionPlanilla", numeroIdentificacion).getSingleResult();
		} catch (NoResultException e) {
			logger.info("aplicarListaBlanca: no existe la datos");
			return null;
		}
	}
}
