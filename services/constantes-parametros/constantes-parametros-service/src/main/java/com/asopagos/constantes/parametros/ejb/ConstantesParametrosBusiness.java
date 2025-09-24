package com.asopagos.constantes.parametros.ejb;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.asopagos.cache.CacheManager;
import com.asopagos.constantes.parametros.constants.NamedQueriesConstants;
import com.asopagos.constantes.parametros.dto.AreaCajaCompensacionDTO;
import com.asopagos.constantes.parametros.dto.ConstantesCajaCompensacionDTO;
import com.asopagos.constantes.parametros.dto.ConstantesParametroDTO;
import com.asopagos.constantes.parametros.service.ConstantesParametrosService;
import com.asopagos.constants.ConstantesSistemaConstants;
import com.asopagos.constants.ConsultasDinamicasConstants;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.constants.ParametrosSistemaConstants;
import com.asopagos.usuarios.clients.ValidarCredencialesUsuario;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.dto.ConexionOperadorInformacionDTO;
import com.asopagos.entidades.ccf.core.AreaCajaCompensacion;
import com.asopagos.entidades.ccf.core.Beneficio;
import com.asopagos.entidades.ccf.core.OperadorInformacion;
import com.asopagos.entidades.ccf.core.OperadorInformacionCcf;
import com.asopagos.entidades.ccf.general.ConexionOperadorInformacion;
import com.asopagos.entidades.ccf.general.Constante;
import com.asopagos.entidades.ccf.general.Parametro;
import com.asopagos.entidades.transversal.core.CajaCompensacion;
import com.asopagos.entidades.auditoria.ThreadContext;
import com.asopagos.enumeraciones.afiliaciones.TipoBeneficioEnum;
import com.asopagos.enumeraciones.core.SubCategoriaParametroEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.jpa.JPAUtils;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.ParametroInvalidoExcepcion;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.util.DesEncrypter;
import com.asopagos.constantes.parametros.dto.ConstantesValorUVTDTO;
import co.com.heinsohn.lion.common.enums.Protocolo;
import com.asopagos.constantes.parametros.dto.ParametrizacionGapsDTO;
import com.asopagos.entidades.transversal.core.ParametrizacionGaps;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import com.asopagos.entidades.ccf.general.ParametrizacionValorUVT;
import com.asopagos.constantes.parametros.dto.ConstantesValorUVTDTO;
import javax.persistence.TypedQuery;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.asopagos.entidades.ccf.general.ModificacionConstanteDummy;
import com.asopagos.entidades.auditoria.ThreadContext;
import com.asopagos.usuarios.clients.ValidarCredencialesUsuario;
import com.asopagos.rest.security.dto.UserDTO;

/**
 * 
 */
@Stateless
public class ConstantesParametrosBusiness implements ConstantesParametrosService {

    private final String FORMATO_DUMMY = "yyyy-MM-dd";

    /**
     * Referencia a la unidad de persistencia del servicio
     */
    @PersistenceContext(unitName = "constantes_parametros_PU")
    private EntityManager entityManager;

    /**
     * Referencia al logger
     */
    private final ILogger logger = LogManager.getLogger(ConstantesParametrosBusiness.class);

    /**
     * <b>Descripción</b>Método empleado para establecer una constante de la
     * caja <code>usuario es el usuario que se va a autenticar</code><br/>
     *
     * @param inDTO
     *        objeto con los valores de los datos de las constantes
     */
    @Override
    public void establecerConstantesCaja(ConstantesCajaCompensacionDTO inDTO) {

        //ParametrizacionRemote parametrizacionBean = ResourceLocator.lookupEJBReference(ParametrizacionRemote.class);

        if (inDTO.getNombre() != null && !inDTO.getNombre().trim().equals("")) {
            // Se actualiza el nombre de la CCF
            actualizarParametros(ParametrosSistemaConstants.NOMBRE_CCF, inDTO.getNombre());
        }

        if (inDTO.getIdentificadorLogo() != null && !inDTO.getIdentificadorLogo().trim().equals("")) {
            // Se actualiza el identificador del logo de la CCF
            actualizarParametros(ParametrosSistemaConstants.LOGO_DE_LA_CCF, inDTO.getIdentificadorLogo());
        }

        if (inDTO.getIdDeparatamento() != null && !inDTO.getIdDeparatamento().trim().equals("")) {
            // Se actualiza el identificador del Departamento de la CCF
            actualizarParametros(ParametrosSistemaConstants.DEPARTAMENTO_CCF, inDTO.getIdDeparatamento());
        }

        if (inDTO.getIdMunicipio() != null && !inDTO.getIdMunicipio().trim().equals("")) {
            // Se actualiza el identificador del Municipio de la CCF
            actualizarParametros(ParametrosSistemaConstants.CIUDAD_CCF, inDTO.getIdMunicipio());
        }

        if (inDTO.getDireccion() != null && !inDTO.getDireccion().trim().equals("")) {
            // Se actualiza la Dirección de la CCF
            actualizarParametros(ParametrosSistemaConstants.DIRECCION_CCF, inDTO.getDireccion());
        }

        if (inDTO.getTelefono() != null && !inDTO.getTelefono().trim().equals("")) {
            // Se actualiza el Teléfono de la CCF
            actualizarParametros(ParametrosSistemaConstants.TELEFONO_CCF, inDTO.getTelefono());
        }

        if (inDTO.getTipoIdentificacion() != null && inDTO.getTipoIdentificacion().toString().trim().equals("")) {
            // Se actualiza el tipo de identificación de la CCF
            actualizarParametros(ParametrosSistemaConstants.TIPO_ID_CCF, inDTO.getTipoIdentificacion().toString());
        }

        if (inDTO.getNumeroIdentificacion() != null && !inDTO.getNumeroIdentificacion().trim().equals("")) {
            // Se actualiza el número de identificación de la CCF
            actualizarParametros(ParametrosSistemaConstants.NUMERO_ID_CCF, inDTO.getNumeroIdentificacion());
        }

        if (inDTO.getPaginaWeb() != null && !inDTO.getPaginaWeb().trim().equals("")) {
            // Se actualiza el campo de página web de la CCF
            actualizarParametros(ParametrosSistemaConstants.WEB_CCF, inDTO.getPaginaWeb());
        }

        if (inDTO.getLogoSuperServicios() != null && !inDTO.getLogoSuperServicios().trim().equals("")) {
            // Se actualiza el logotipo de la Superintendencia de Servicios
            actualizarParametros(ParametrosSistemaConstants.LOGO_SUPERSUBSIDIO, inDTO.getLogoSuperServicios());
        }

        if (inDTO.getFirmaResponsableEnvioComunicadoCaja() != null && !inDTO.getFirmaResponsableEnvioComunicadoCaja().trim().equals("")) {
            // Se actualiza la Imagen de la firma del responsable del envío del
            // comunicado en la caja
            actualizarParametros(ParametrosSistemaConstants.FIRMA_RESPONSABLE_CCF, inDTO.getFirmaResponsableEnvioComunicadoCaja());
        }

        if (inDTO.getResponsableEnvioComunicadoCaja() != null && !inDTO.getResponsableEnvioComunicadoCaja().trim().equals("")) {
            // Se actualiza el nombre del responsable del envío del comunicado
            // en la caja
            actualizarParametros(ParametrosSistemaConstants.RESPONSABLE_CCF, inDTO.getResponsableEnvioComunicadoCaja());
        }

        if (inDTO.getCargoResponsableEnvioComunicadoCaja() != null && !inDTO.getCargoResponsableEnvioComunicadoCaja().trim().equals("")) {
            // Se actualiza el cargo del responsable del envío del comunicado en
            // la caja
            actualizarParametros(ParametrosSistemaConstants.CARGO_RESPONSABLE_CCF, inDTO.getCargoResponsableEnvioComunicadoCaja());
        }
        sincronizarParametrosYConstantes();
    }

    /**
     * <b>Descripción</b>Método que se encarga de obtener las constantes
     * parametro de una caja de compensación familiar
     *
     * * @return ConstantesCajaCompensacionDTO objeto con las constantes
     * obtenidas
     *
     */
    /*
     * (non-Javadoc)
     * 
     * @see
     * com.asopagos.constantes.parametros.service.ConstantesParametrosService#
     * consultarConstantesCaja()
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public ConstantesCajaCompensacionDTO consultarConstantesCaja() {

        ConstantesCajaCompensacionDTO cajaCompensacionDTO = new ConstantesCajaCompensacionDTO();

        Map<String, String> parametros = CacheManager.consultarParametros();
        if (parametros != null) {
            for (Map.Entry<String, String> parametro : parametros.entrySet()) {
                if (parametro.getKey().equals(ParametrosSistemaConstants.NOMBRE_CCF)) {
                    cajaCompensacionDTO.setNombre(parametro.getValue());
                }

                if (parametro.getKey().equals(ParametrosSistemaConstants.LOGO_DE_LA_CCF)) {
                    cajaCompensacionDTO.setIdentificadorLogo(parametro.getValue());
                }

                if (parametro.getKey().equals(ParametrosSistemaConstants.DEPARTAMENTO_CCF)) {
                    cajaCompensacionDTO.setIdDeparatamento(parametro.getValue());
                }

                if (parametro.getKey().equals(ParametrosSistemaConstants.CIUDAD_CCF)) {
                    cajaCompensacionDTO.setIdMunicipio(parametro.getValue());
                }

                if (parametro.getKey().equals(ParametrosSistemaConstants.DIRECCION_CCF)) {
                    cajaCompensacionDTO.setDireccion(parametro.getValue());
                }

                if (parametro.getKey().equals(ParametrosSistemaConstants.TELEFONO_CCF)) {
                    cajaCompensacionDTO.setTelefono(parametro.getValue());
                }

                if (parametro.getKey().equals(ParametrosSistemaConstants.TIPO_ID_CCF)) {
                    cajaCompensacionDTO.setTipoIdentificacion(TipoIdentificacionEnum.obtnerTiposIdentificacionEnum(parametro.getValue()));
                }

                if (parametro.getKey().equals(ParametrosSistemaConstants.NUMERO_ID_CCF)) {
                    cajaCompensacionDTO.setNumeroIdentificacion(parametro.getValue());
                }

                if (parametro.getKey().equals(ParametrosSistemaConstants.WEB_CCF)) {
                    cajaCompensacionDTO.setPaginaWeb(parametro.getValue());
                }

                if (parametro.getKey().equals(ParametrosSistemaConstants.LOGO_SUPERSUBSIDIO)) {
                    cajaCompensacionDTO.setLogoSuperServicios(parametro.getValue());
                }

                if (parametro.getKey().equals(ParametrosSistemaConstants.FIRMA_RESPONSABLE_CCF)) {
                    cajaCompensacionDTO.setFirmaResponsableEnvioComunicadoCaja(parametro.getValue());
                }

                if (parametro.getKey().equals(ParametrosSistemaConstants.RESPONSABLE_CCF)) {
                    cajaCompensacionDTO.setResponsableEnvioComunicadoCaja(parametro.getValue());
                }

                if (parametro.getKey().equals(ParametrosSistemaConstants.CARGO_RESPONSABLE_CCF)) {
                    cajaCompensacionDTO.setCargoResponsableEnvioComunicadoCaja(parametro.getValue());
                }

                if (parametro.getKey().equals(ParametrosSistemaConstants.PARAM_CADUCIDAD_LINK)) {
                    cajaCompensacionDTO.setTiempoCaducidadLinkEmpresasWeb(parametro.getValue());
                }
                if (parametro.getKey().equals(ParametrosSistemaConstants.PARAM_CADUCIDAD_LINK_123)) {
                    cajaCompensacionDTO.setTiempoCaducidadLinkIndPensWeb(parametro.getValue());
                }
                if (parametro.getKey().equals(ParametrosSistemaConstants.FIRMA_RESPONSABLE_AFILIACION_EMPRESAS)) {
                    cajaCompensacionDTO.setFirmaRespAfiEmpresas(parametro.getValue());
                }
                if (parametro.getKey().equals(ParametrosSistemaConstants.FIRMA_RESPONSABLE_AFILIACION_PERSONAS)) {
                    cajaCompensacionDTO.setFirmaRespAfiPersonas(parametro.getValue());
                }
                if (parametro.getKey().equals(ParametrosSistemaConstants.CORREO_CCF)) {
                    cajaCompensacionDTO.setCorreoCaja(parametro.getValue());
                }

                if (parametro.getKey().equals(ParametrosSistemaConstants.FIRMA_GERENTE_FINANCIERA)){
                    cajaCompensacionDTO.setFirmagerentecomercial(parametro.getValue());
                }
                
                if (parametro.getKey().equals(ParametrosSistemaConstants.FIRMA_GERENTE_COMERCIAL)){
                    cajaCompensacionDTO.setFirmagerentecomercial(parametro.getValue());
                }

                if (parametro.getKey().equals(ParametrosSistemaConstants.FIRMA_SECRETARIA_GENERAL)){
                    cajaCompensacionDTO.setFirmasecretariageneral(parametro.getValue());
                }
                
                if (parametro.getKey().equals(ParametrosSistemaConstants.FIRMA_DIRECTOR_ADMINISTRATIVO_PRINCIPAL)){
                    cajaCompensacionDTO.setFirmaDirectorAdminppalCCF(parametro.getValue());
                }

                if (parametro.getKey().equals(ParametrosSistemaConstants.FIRMA_DIRECTOR_ADMINISTRATIVO_SEGUNDO_SUPLENTE)){
                    cajaCompensacionDTO.setFirmaDirectorAdminsplCCF(parametro.getValue());
                }
            }
        }
        return cajaCompensacionDTO;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.asopagos.constantes.parametros.service.ConstantesParametrosService#
     * sincronizarParametrosYConstantes()
     */
    @Override
    public void sincronizarParametrosYConstantes() {
        CacheManager.sincronizarParametrosYConstantes();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.asopagos.constantes.parametros.service.ConstantesParametrosService#
     * consultarDependenciasCCF()
     */
    @Override
    public List<AreaCajaCompensacionDTO> consultarDependenciasCCF(Short idDependencia) {
        List<AreaCajaCompensacionDTO> listado = new ArrayList<>();
        if (idDependencia != null) {
            AreaCajaCompensacion area = entityManager
                    .createNamedQuery(NamedQueriesConstants.BUSCAR_DEPENDENCIAS_ID, AreaCajaCompensacion.class)
                    .setParameter("idAreaCajaCompensacion", idDependencia).getSingleResult();
            listado.add(AreaCajaCompensacionDTO.convertToDTO(area));
            return listado;
        }
        else {
            List<AreaCajaCompensacion> areas = entityManager
                    .createNamedQuery(NamedQueriesConstants.BUSCAR_DEPENDENCIAS, AreaCajaCompensacion.class).getResultList();
            if (areas != null && !areas.isEmpty()) {
                for (AreaCajaCompensacion areaCajaCompensacion : areas) {
                    listado.add(AreaCajaCompensacionDTO.convertToDTO(areaCajaCompensacion));
                }
                return listado;
            }
        }
        return null;
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.constantes.parametros.service.ConstantesParametrosService#consultarSMMLV()
     */
    public Long consultarSMMLV() {
        try {
            logger.debug("Inicia consultarSMMLV()");
            if (CacheManager.getParametro(ParametrosSistemaConstants.SMMLV) == null) {
                logger.debug("Finaliza consultarSMMLV()");
                throw new TechnicalException(MensajesGeneralConstants.ERROR_RECURSO_NO_ENCONTRADO);
            }
            else {
                Long smmlv = (Long) CacheManager.getParametro(ParametrosSistemaConstants.SMMLV);
                logger.debug("Finaliza de forma exitosa consultarSMMLV()");
                return smmlv;
            }
        } catch (NumberFormatException e) {
            logger.error("No es valido el formato del atributo SMMLV", e);
            logger.debug("Finaliza consultarSMMLV()");
            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_RECURSO_INCORRECTO);
        }
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.constantes.parametros.service.ConstantesParametrosService#consultarConstantesParametros()
     */
    public Map<String, String> consultarConstantesParametros() {
        logger.debug("Inicia consultarConstantesParametros()");
        List<String> listaConstantes = new ArrayList<String>();
        List<String> listaParametros = new ArrayList<String>();
        // Constante
        listaConstantes.add(ConstantesSistemaConstants.BPMS_BUSINESS_CENTRAL_ENDPOINT);
        listaConstantes.add(ConstantesSistemaConstants.BPMS_PROCESS_AFIL_DEP_WEB_DEPLOYMENTID);
        listaConstantes.add(ConstantesSistemaConstants.BPMS_PROCESS_AFIL_EMP_WEB_DEPLOYMENTID);
        listaConstantes.add(ConstantesSistemaConstants.BPMS_PROCESS_AFIL_IND_WEB_DEPLOYMENTID);
        listaConstantes.add(ConstantesSistemaConstants.BPMS_PROCESS_AFIL_PERS_PRES_DEPLOYMENTID);
        listaConstantes.add(ConstantesSistemaConstants.BPMS_PROCESS_CORRECCION_APORTES_DEPLOYMENTID);
        listaConstantes.add(ConstantesSistemaConstants.BPMS_PROCESS_DEPLOYMENT_ID);
        listaConstantes.add(ConstantesSistemaConstants.BPMS_PROCESS_DEVOLUCION_APORTES_DEPLOYMENTID);
        listaConstantes.add(ConstantesSistemaConstants.BPMS_PROCESS_FISCALIZACION_CARTERA_DEPLOYMENTID);
        listaConstantes.add(ConstantesSistemaConstants.BPMS_PROCESS_GESTION_PREVENTIVA_CARTERA_DEPLOYMENTID);
        listaConstantes.add(ConstantesSistemaConstants.BPMS_PROCESS_NOVE_DEP_WEB_DEPLOYMENTID);
        listaConstantes.add(ConstantesSistemaConstants.BPMS_PROCESS_NOVE_EMP_PRES_DEPLOYMENTID);
        listaConstantes.add(ConstantesSistemaConstants.BPMS_PROCESS_NOVE_EMP_WEB_DEPLOYMENTID);
        listaConstantes.add(ConstantesSistemaConstants.BPMS_PROCESS_NOVE_PER_PRES_DEPLOYMENTID);
        listaConstantes.add(ConstantesSistemaConstants.BPMS_PROCESS_NOVE_PER_WEB_DEPLOYMENTID);
        listaConstantes.add(ConstantesSistemaConstants.BPMS_PROCESS_NOVEDADES_ARCHIVOS_ACTUALIZACION_DEPLOYMENTID);
        listaConstantes.add(ConstantesSistemaConstants.BPMS_PROCESS_PAGO_MAN_APOR_DEPLOYMENTID);
        listaConstantes.add(ConstantesSistemaConstants.BPMS_PROCESS_POST_FOVIS_PREC_DEPLOYMENTID);
        listaConstantes.add(ConstantesSistemaConstants.BPMS_PROCESS_POST_FOVIS_WEB_DEPLOYMENTID);
        listaConstantes.add(ConstantesSistemaConstants.BPMS_PROCESS_SUBSIDIO_MONETARIO_MASIVO_DEPLOYMENTID);
        listaConstantes.add(ConstantesSistemaConstants.CAJA_COMPENSACION_CODIGO);
        listaConstantes.add(ConstantesSistemaConstants.CAJA_COMPENSACION_ID);
        listaConstantes.add(ConstantesSistemaConstants.CAJA_COMPENSACION_SITE);
        listaConstantes.add(ConstantesSistemaConstants.CANCELAR_SOLICITUD);
        listaConstantes.add(ConstantesSistemaConstants.CANCELAR_SOLICITUD_PERSONAS);
        listaConstantes.add(ConstantesSistemaConstants.DESENCRYPTER_KEY);
        listaConstantes.add(ConstantesSistemaConstants.ECM_HOST);
        listaConstantes.add(ConstantesSistemaConstants.ECM_PASSWORD);
        listaConstantes.add(ConstantesSistemaConstants.ECM_USERNAME);
        listaConstantes.add(ConstantesSistemaConstants.FILE_DEFINITION_ID_AFILIACION_MULTIPLES);
        listaConstantes.add(ConstantesSistemaConstants.FILE_DEFINITION_ID_ARCHIVO_SUPERVIVENCIA_ENCONTRADO_RNEC);
        listaConstantes.add(ConstantesSistemaConstants.FILE_DEFINITION_ID_ARCHIVO_SUPERVIVENCIA_NO_ENCONTRADO_RNEC);
        listaConstantes.add(ConstantesSistemaConstants.FILE_DEFINITION_ID_ENTIDADES_DESCUENTO);
        listaConstantes.add(ConstantesSistemaConstants.FILE_DEFINITION_ID_FOVIS_CARGUE_CRUCE_AFILIADOS);
        listaConstantes.add(ConstantesSistemaConstants.FILE_DEFINITION_ID_FOVIS_CARGUE_CRUCE_BENEFICAIRIO);
        listaConstantes.add(ConstantesSistemaConstants.FILE_DEFINITION_ID_FOVIS_CARGUE_CRUCE_CATANT);
        listaConstantes.add(ConstantesSistemaConstants.FILE_DEFINITION_ID_FOVIS_CARGUE_CRUCE_CATBOG);
        listaConstantes.add(ConstantesSistemaConstants.FILE_DEFINITION_ID_FOVIS_CARGUE_CRUCE_CATCALI);
        listaConstantes.add(ConstantesSistemaConstants.FILE_DEFINITION_ID_FOVIS_CARGUE_CRUCE_CATMED);
        listaConstantes.add(ConstantesSistemaConstants.FILE_DEFINITION_ID_FOVIS_CARGUE_CRUCE_CEDULAS);
        listaConstantes.add(ConstantesSistemaConstants.FILE_DEFINITION_ID_FOVIS_CARGUE_CRUCE_FECHAS_CORTE);
        listaConstantes.add(ConstantesSistemaConstants.FILE_DEFINITION_ID_FOVIS_CARGUE_CRUCE_IGAC);
        listaConstantes.add(ConstantesSistemaConstants.FILE_DEFINITION_ID_FOVIS_CARGUE_CRUCE_NUEVO_HOGAR);
        listaConstantes.add(ConstantesSistemaConstants.FILE_DEFINITION_ID_FOVIS_CARGUE_CRUCE_REUNIDOS);
        listaConstantes.add(ConstantesSistemaConstants.FILE_DEFINITION_ID_FOVIS_CARGUE_CRUCE_SISBEN);
        listaConstantes.add(ConstantesSistemaConstants.FILE_DEFINITION_ID_FOVIS_CARGUE_CRUCE_UNIDOS);
        listaConstantes.add(ConstantesSistemaConstants.FILE_DEFINITION_ID_NOVEDAD_CARGUE_ACTUALIZACION_AFILIADO);
        listaConstantes.add(ConstantesSistemaConstants.FILE_DEFINITION_ID_NOVEDAD_CARGUE_ACTUALIZACION_BENEFICIARIO);
        listaConstantes.add(ConstantesSistemaConstants.FILE_DEFINITION_ID_NOVEDAD_CARGUE_ACTUALIZACION_EMPLEADOR);
        listaConstantes.add(ConstantesSistemaConstants.FILE_DEFINITION_ID_NOVEDAD_CARGUE_INFO_CERTIFICADO_ESCOLAR);
        listaConstantes.add(ConstantesSistemaConstants.FILE_DEFINITION_ID_NOVEDAD_CARGUE_INFO_PENSIONADO);
        listaConstantes.add(ConstantesSistemaConstants.FILE_DEFINITION_ID_NOVEDADES_MULTIPLES);
        listaConstantes.add(ConstantesSistemaConstants.FILE_DEFINITION_ID_PAGO_MANUAL_APORTES);
        listaConstantes.add(ConstantesSistemaConstants.PILA_ARCHIVO_FINANCIERO);
        listaConstantes.add(ConstantesSistemaConstants.PILA_DETALLE_INDEPENDIENTE_DEPENDIENTE);
        listaConstantes.add(ConstantesSistemaConstants.PILA_DETALLE_PENSIONADO);
        listaConstantes.add(ConstantesSistemaConstants.PILA_INFORMACION_INDEPENDIENTE_DEPENDIENTE);
        listaConstantes.add(ConstantesSistemaConstants.PILA_INFORMACION_PENSIONADO);
        listaConstantes.add(ConstantesSistemaConstants.IDM_CLIENT_WEB_CLIENT_ID);
        listaConstantes.add(ConstantesSistemaConstants.IDM_CLIENT_WEB_CLIENT_SECRET);
        listaConstantes.add(ConstantesSistemaConstants.IDM_CLIENT_WEB_DOMAIN_NAME);
        listaConstantes.add(ConstantesSistemaConstants.IDM_INTEGRATION_WEB_CLIENT_ID);
        listaConstantes.add(ConstantesSistemaConstants.IDM_INTEGRATION_WEB_CLIENT_SECRET);
        listaConstantes.add(ConstantesSistemaConstants.IDM_INTEGRATION_WEB_DOMAIN_NAME);
        listaConstantes.add(ConstantesSistemaConstants.IDM_INTEGRATION_WEB_PUBLIC_CLIENT_ID);
        listaConstantes.add(ConstantesSistemaConstants.IDM_INTEGRATION_WEB_PUBLIC_CLIENT_SECRET);
        listaConstantes.add(ConstantesSistemaConstants.IDM_SERVER_URL);
        listaConstantes.add(ConstantesSistemaConstants.KEYCLOAK_ENDPOINT);
        listaConstantes.add(ConstantesSistemaConstants.SEC_INITIAL_CHARACTERS_PASSWORD);
        listaConstantes.add(ConstantesSistemaConstants.SERVICIOS_PUERTO);
        listaConstantes.add(ConstantesSistemaConstants.SERVICIOS_SERVIDOR);
        listaConstantes.add(ConstantesSistemaConstants.FILE_DEFINITION_ID_NOVEDAD_CARGUE_INFO_EMPLEADOR);
        listaConstantes.add(ConstantesSistemaConstants.FILE_DEFINITION_ID_NOVEDAD_CARGUE_INFO_PERSONA);
        listaConstantes.add(ConstantesSistemaConstants.FILE_DEFINITION_ID_NOVEDAD_CARGUE_INFO_CCF);
        listaConstantes.add(ConstantesSistemaConstants.FILE_DEFINITION_ID_NOVEDAD_CARGUE_CAMBIO_TIPO_NUMERO_DOCUMENTO_PERSONAS_MASIVO_AFILIADO);
        listaConstantes.add(ConstantesSistemaConstants.FILE_DEFINITION_ID_NOVEDAD_CARGUE_CAMBIO_TIPO_NUMERO_DOCUMENTO_PERSONAS_MASIVO_BENEFICIARIO);

        // Parametro
        listaParametros.add(ParametrosSistemaConstants.PARAM_CADUCIDAD_FORMULARIO);
        listaParametros.add(ParametrosSistemaConstants._112_IMPRIMIR_FORMULARIO_TIMER);
        listaParametros.add(ParametrosSistemaConstants._122_CORREGIR_INFORMACION_TIMER);
        listaParametros.add(ParametrosSistemaConstants.PARAM_CADUCIDAD_LINK_123);
        listaParametros.add(ParametrosSistemaConstants.PARAM_CADUCIDAD_FORMULARIO_123);
        listaParametros.add(ParametrosSistemaConstants.ACEPTA_DERECHOS_ACCESO);
        listaParametros.add(ParametrosSistemaConstants.ACEPTACION_HIJOS_19_22_CON_ESTUDIO_CERTIFICADO);
        listaParametros.add(ParametrosSistemaConstants.ARCHIVO_PLANTILLA_EJEMPLO_EXCEL_CARGUE_MASIVO_122);
        listaParametros.add(ParametrosSistemaConstants.ARCHIVO_PLANTILLA_EXCEL_CARGUE_MASIVO_122);
        listaParametros.add(ParametrosSistemaConstants.CAJA_COMPENSACION_DEPTO_ID);
        listaParametros.add(ParametrosSistemaConstants.CAJA_COMPENSACION_MEDIO_PAGO_PREFERENTE);
        listaParametros.add(ParametrosSistemaConstants.CAJA_COMPENSACION_MUNI_ID);
        listaParametros.add(ParametrosSistemaConstants.CAJA_COMPENSACION_NUMERO_SOLICITUD_PRE_IMPRESO);
        listaParametros.add(ParametrosSistemaConstants.CANT_SMLMV_SAN_ANDRES_HOGAR);
        listaParametros.add(ParametrosSistemaConstants.CANT_SMLMV_SAN_ANDRES_SFV);
        listaParametros.add(ParametrosSistemaConstants.CANT_SMLMV_MEJORAMIENTO_SFV);
        listaParametros.add(ParametrosSistemaConstants.CATEGORIA_CAMBIO_BENEFICIARIO);
        listaParametros.add(ParametrosSistemaConstants.CIUDAD_CCF);
        listaParametros.add(ParametrosSistemaConstants.DEPARTAMENTO_CCF);
        listaParametros.add(ParametrosSistemaConstants.DIAS_NOTIFICACION_EXPIRACION_CONTRASENA);
        listaParametros.add(ParametrosSistemaConstants.DIRECCION_CCF);
        listaParametros.add(ParametrosSistemaConstants.EDAD_CAMBIO_CATEGORIA_BENEFICIARIO);
        listaParametros.add(ParametrosSistemaConstants.EDAD_RETIRO_BENEFICIARIO);
        listaParametros.add(ParametrosSistemaConstants.FIRMA_RESPONSABLE_CCF);
        listaParametros.add(ParametrosSistemaConstants.FTP_ARCHIVOS_DESCUENTO_CONTRASENA);
        listaParametros.add(ParametrosSistemaConstants.FTP_ARCHIVOS_DESCUENTO_NOMBRE_HOST);
        listaParametros.add(ParametrosSistemaConstants.FTP_ARCHIVOS_DESCUENTO_NOMBRE_USUARIO);
        listaParametros.add(ParametrosSistemaConstants.FTP_ARCHIVOS_DESCUENTO_PUERTO);
        listaParametros.add(ParametrosSistemaConstants.FTP_ARCHIVOS_DESCUENTO_URL_ARCHIVOS);
        listaParametros.add(ParametrosSistemaConstants.LOGO_DE_LA_CCF);
        listaParametros.add(ParametrosSistemaConstants.MEMBRETE_PIE_DE_PAGINA_DE_LA_CCF);
        listaParametros.add(ParametrosSistemaConstants.MEMBRETE_ENCABEZADO_DE_LA_CCF);
        listaParametros.add(ParametrosSistemaConstants.REGISTRO_APORTES_FUTURO);
        listaParametros.add(ParametrosSistemaConstants.LOGO_SUPERSUBSIDIO);
        listaParametros.add(ParametrosSistemaConstants.MAIL_SMTP_AUTH);
        listaParametros.add(ParametrosSistemaConstants.MAIL_SMTP_FROM);
        listaParametros.add(ParametrosSistemaConstants.MAIL_SMTP_HOST);
        listaParametros.add(ParametrosSistemaConstants.MAIL_SMTP_PASSWORD);
        listaParametros.add(ParametrosSistemaConstants.MAIL_SMTP_PORT);
        listaParametros.add(ParametrosSistemaConstants.MAIL_SMTP_SENDPARTIAL);
        listaParametros.add(ParametrosSistemaConstants.MAIL_SMTP_SSL);
        listaParametros.add(ParametrosSistemaConstants.MAIL_SMTP_USER);
        listaParametros.add(ParametrosSistemaConstants.MARGEN_TOLERANCIA_DIFERENCIA_MORA_APORTE_PILA);
        listaParametros.add(ParametrosSistemaConstants.MESES_MORA_DESAFILIACION);
        listaParametros.add(ParametrosSistemaConstants.MULTIPLICADOR_VALOR_MINIMO_SALARIO_INTEGRAL);
        listaParametros.add(ParametrosSistemaConstants.NIT_CATASTRO_ANTIOQUIA);
        listaParametros.add(ParametrosSistemaConstants.NIT_CATASTRO_BOGOTA);
        listaParametros.add(ParametrosSistemaConstants.NIT_CATASTRO_CALI);
        listaParametros.add(ParametrosSistemaConstants.NIT_CATASTRO_MEDELLIN);
        listaParametros.add(ParametrosSistemaConstants.NIT_IGAC);
        listaParametros.add(ParametrosSistemaConstants.NOMBRE_CCF);
        listaParametros.add(ParametrosSistemaConstants.NUMERO_ID_CCF);
        listaParametros.add(ParametrosSistemaConstants.NUMERO_REGISTROS_LECTURA_AFILIACION_MULTIPLE);
        listaParametros.add(ParametrosSistemaConstants.REINTENTOS_PERSISTENCIA_CONTENIDO);
        listaParametros.add(ParametrosSistemaConstants.RESPONSABLE_CCF);
        listaParametros.add(ParametrosSistemaConstants.RESPONSABLE_NOVEDADES_CCF);
        listaParametros.add(ParametrosSistemaConstants.SMMLV);
        listaParametros.add(ParametrosSistemaConstants.TARIFA_BASE_EMPLEADOR);
        listaParametros.add(ParametrosSistemaConstants.TELEFONO_CCF);
        listaParametros.add(ParametrosSistemaConstants.TIEMPO_DESISTIR_CARGA_MULTIPLE_NOVEDADES);
        listaParametros.add(ParametrosSistemaConstants.TIEMPO_EXPIRACION_ENLACE);
        listaParametros.add(ParametrosSistemaConstants.TIEMPO_INACTIVAR_CUENTA);
        listaParametros.add(ParametrosSistemaConstants.TIEMPO_INACTIVAR_EMPLEADOR);
        listaParametros.add(ParametrosSistemaConstants.TIEMPO_MINIMO_PLANILLA);
        listaParametros.add(ParametrosSistemaConstants.TIEMPO_REINTEGRO);
        listaParametros.add(ParametrosSistemaConstants.TIPO_ID_CCF);
        listaParametros.add(ParametrosSistemaConstants.WEB_CCF);
        listaParametros.add(ParametrosSistemaConstants.ARCHIVO_PLANTILLA_EXCEL_CARGUE_MASIVO_NOVEDADES_449);
        listaParametros.add(ParametrosSistemaConstants.TIEMPO_REINTEGRO_AFILIADO);
        listaParametros.add(ParametrosSistemaConstants.TAMANO_ARCHIVOS_LISTA_CHEQUEO_EN_MEGABYTES);
        listaParametros.add(ParametrosSistemaConstants.GENERAR_FORMULARIO_PRESENCIAL);
        listaParametros.add(ParametrosSistemaConstants.FIRMA_RESPONSABLE_AFILIACION_EMPRESAS);
        listaParametros.add(ParametrosSistemaConstants.FIRMA_RESPONSABLE_AFILIACION_PERSONAS);
        listaParametros.add(ParametrosSistemaConstants.FIRMA_RESPONSABLE_PROCESO_CARTERA);
        listaParametros.add(ParametrosSistemaConstants.FIRMA_RESPONSABLE_APORTES_CCF);
        listaParametros.add(ParametrosSistemaConstants.FIRMA_GERENTE_FINANCIERA);
        listaParametros.add(ParametrosSistemaConstants.FIRMA_GERENTE_COMERCIAL);
        listaParametros.add(ParametrosSistemaConstants.FIRMA_SECRETARIA_GENERAL);
        listaParametros.add(ParametrosSistemaConstants.FIRMA_DIRECTOR_ADMINISTRATIVO_PRINCIPAL);
        listaParametros.add(ParametrosSistemaConstants.FIRMA_DIRECTOR_ADMINISTRATIVO_SEGUNDO_SUPLENTE);
        listaParametros.add(ParametrosSistemaConstants.TIEMPO_INACTIVAR_USUARIO_KEYCLOAK);
        listaParametros.add(ParametrosSistemaConstants.TIEMPO_INICIO_SESION_USUARIO_KEYCLOAK);
        listaParametros.add(ParametrosSistemaConstants.JWT_SECRET);
        listaParametros.add(ParametrosSistemaConstants.JWT_EXPIRATION_MS);
        listaParametros.add(ParametrosSistemaConstants.FRONTEND_RESET_PASSWORD_URL);
        

        List<Constante> query = entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_CONSTANTES, Constante.class)
                .setParameter("listaConstantes", listaConstantes).getResultList();

        List<Parametro> query2 = entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_PARAMETROS, Parametro.class)
                .setParameter("listaParametros", listaParametros).getResultList();

        Map<String, String> constantesParametros = new HashMap<String, String>();
        if (query != null && !query.isEmpty()) {
            for (Constante constante : query) {
                constantesParametros.put(constante.getNombre(), constante.getValor());
            }
        }
        if (query2 != null && !query2.isEmpty()) {
            for (Parametro parametro : query2) {
                constantesParametros.put(parametro.getNombre(), parametro.getValor());
            }
        }

        listaParametros.add(ParametrosSistemaConstants.FECHA_FIN_LEY_1429);
        listaParametros.add(ParametrosSistemaConstants.FECHA_FIN_LEY_590);
        listaParametros.add(ParametrosSistemaConstants.FECHA_INICIO_LEY_1429);

        List<TipoBeneficioEnum> listaParametrosBeneficio = new ArrayList<>();
        listaParametrosBeneficio.add(TipoBeneficioEnum.LEY_1429);
        listaParametrosBeneficio.add(TipoBeneficioEnum.LEY_590);

        List<Beneficio> query3 = entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_BENEFICIOS, Beneficio.class)
                .setParameter("listaParametrosBeneficio", listaParametrosBeneficio).getResultList();
        if (query3 != null && !query3.isEmpty()) {
            for (Beneficio beneficio : query3) {
                if (TipoBeneficioEnum.LEY_1429.equals(beneficio.getTipoBeneficio())) {
                    constantesParametros.put(ParametrosSistemaConstants.FECHA_FIN_LEY_1429,
                            String.valueOf(beneficio.getFechaVigenciaFin().getTime()));
                    constantesParametros.put(ParametrosSistemaConstants.FECHA_INICIO_LEY_1429,
                            String.valueOf(beneficio.getFechaVigenciaInicio().getTime()));
                }
                else if (TipoBeneficioEnum.LEY_590.equals(beneficio.getTipoBeneficio())) {
                    if (beneficio.getFechaVigenciaFin() != null) {
                        constantesParametros.put(ParametrosSistemaConstants.FECHA_FIN_LEY_590,
                                String.valueOf(beneficio.getFechaVigenciaFin().getTime()));
                    }
                }
            }
        }

        logger.debug("Finaliza consultarConstantes()");
        if (!constantesParametros.isEmpty()) {
            return constantesParametros;
        }
        return null;
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.constantes.parametros.service.ConstantesParametrosService#consultarFechaSistema()
     */
    public Date consultarFechaSistema() {
        try {
            return Calendar.getInstance().getTime();
        } catch (Exception e) {
            logger.debug("Finaliza consultarFechaSistema()");
            logger.error("Error técnico", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO);
        }
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.constantes.parametros.service.ConstantesParametrosService#consultarConstantesParametro(java.lang.String,
     *      java.lang.String,
     *      com.asopagos.entidades.ccf.general.SubCategoriaParametroEnum)
     */
    @Override
    public List<ConstantesParametroDTO> consultarConstantesParametro(String nombreParametro, String valorParametro,
            SubCategoriaParametroEnum subCategoria, Boolean cargaInicio) {
        logger.debug(
                "Inicia consultarConstantesParametro(String nombreParametro, String valorParametro,SubCategoriaParametroEnum subCategoria)");
        Map<String, String> fields = new HashMap<>();
        Map<String, Object> values = new HashMap<>();
        List<Parametro> lstPametros = new ArrayList<Parametro>();
        List<ConstantesParametroDTO> lstConstantesParametrosDTO = new ArrayList<ConstantesParametroDTO>();
        if (nombreParametro != null) {
            fields.put("nombre", ConsultasDinamicasConstants.LIKE);
            values.put("nombre", nombreParametro);
        }
        if (valorParametro != null) {
            fields.put("valor", ConsultasDinamicasConstants.IGUAL);
            values.put("valor", valorParametro);
        }
        if (subCategoria != null) {
            fields.put("subCategoriaParametroEnum", ConsultasDinamicasConstants.IGUAL);
            values.put("subCategoriaParametroEnum", subCategoria);
        }
        if (cargaInicio != null) {
            fields.put("cargaInicio", ConsultasDinamicasConstants.IGUAL);
            values.put("cargaInicio", cargaInicio);
        }
        lstPametros = (List<Parametro>) JPAUtils.consultaEntidad(entityManager, Parametro.class, fields, values, Integer.class);
        ConstantesParametroDTO constanteParametroDTO;
        for (Parametro parametro : lstPametros) {
            if(parametro.getVisualizarPantalla().equals(Boolean.TRUE)){
                constanteParametroDTO = new ConstantesParametroDTO();
                constanteParametroDTO.setNombre(parametro.getNombre());
                constanteParametroDTO.setValor(parametro.getValor());
                constanteParametroDTO.setSubCategoriaEnum(parametro.getSubCategoriaParametroEnum());
                constanteParametroDTO.setCategoriaParametroEnum(parametro.getSubCategoriaParametroEnum().getCategoriaEnum());
                constanteParametroDTO.setCargaInicio(parametro.getCargaInicio());
                constanteParametroDTO.setTipoDato(parametro.getTipoDato() != null ? parametro.getTipoDato():null);
                constanteParametroDTO.setVisualizarPantalla(parametro.getVisualizarPantalla());
                lstConstantesParametrosDTO.add(constanteParametroDTO);
                constanteParametroDTO.setDescripcion(parametro.getDescripcion());
            }
        }
        logger.debug(
                "Finaliza consultarConstantesParametro(String nombreParametro, String valorParametro,SubCategoriaParametroEnum subCategoria)");
        return lstConstantesParametrosDTO;
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.asopagos.constantes.parametros.service.ConstantesParametrosService#crearModificarConstanteParametro(
     *      com.asopagos.constantes.parametros.dto.ConstantesParametroDTO)
     */
    @Override
    public void crearModificarConstanteParametro(ConstantesParametroDTO constanteParametroDTO) {
        logger.debug("Inicia crearModificarConstanteParametro(ConstantesParametroDTO)");
        Parametro parametro;
        try {
            parametro = entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_PARAMETROS_POR_NOMBRE, Parametro.class)
                    .setParameter("nombreParametro", constanteParametroDTO.getNombre()).getSingleResult();

            if (constanteParametroDTO.getCargaInicio() != null) {
                parametro.setCargaInicio(constanteParametroDTO.getCargaInicio());
            }
            if (constanteParametroDTO.getValor() != null) {
                parametro.setValor(constanteParametroDTO.getValor());
            }
            if (constanteParametroDTO.getSubCategoriaEnum() != null) {
                parametro.setSubCategoriaParametroEnum(constanteParametroDTO.getSubCategoriaEnum());
            }
            if (constanteParametroDTO.getDescripcion() != null) {
                parametro.setDescripcion(constanteParametroDTO.getDescripcion());
            }
            if (constanteParametroDTO.getTipoDato() != null) {
                parametro.setTipoDato(constanteParametroDTO.getTipoDato());
            }
            if (constanteParametroDTO.getVisualizarPantalla() != null) {
                parametro.setVisualizarPantalla(constanteParametroDTO.getVisualizarPantalla());
            }
            entityManager.merge(parametro);
        } catch (NoResultException e) {
            parametro = new Parametro();
            parametro.setNombre(constanteParametroDTO.getNombre());
            parametro.setCargaInicio(constanteParametroDTO.getCargaInicio());
            parametro.setValor(constanteParametroDTO.getValor());
            parametro.setSubCategoriaParametroEnum(constanteParametroDTO.getSubCategoriaEnum());
            parametro.setDescripcion(constanteParametroDTO.getDescripcion());
            parametro.setTipoDato(constanteParametroDTO.getTipoDato());
            parametro.setVisualizarPantalla(constanteParametroDTO.getVisualizarPantalla());;
            entityManager.persist(parametro);
        }
        CacheManager.sincronizarParametro(parametro.getNombre(), parametro.getValor());
        logger.debug("Finaliza crearModificarConstanteParametro(ConstantesParametroDTO)");
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.constantes.parametros.service.ConstantesParametrosService#obtenerParametro(java.lang.String)
     */
    @Override
    public String obtenerParametro(String key) {
        try {
            return (String) entityManager.createNamedQuery(NamedQueriesConstants.OBTENER_PARAMETRO).setParameter("key", key)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.constantes.parametros.service.ConstantesParametrosService#obtenerConstante(java.lang.String)
     */
    @Override
    public String obtenerConstante(String key) {
        try {
            return (String) entityManager.createNamedQuery(NamedQueriesConstants.OBTENER_CONSTANTE).setParameter("key", key)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Map<String, String> obtenerListadoConstantesParametros() {
        Map<String, String> constantesParametros = new HashMap<>();

        try {
            List<Constante> queryConstante = entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_CONSTANTES_INICIO, Constante.class)
                    .getResultList();

            for (Constante constante : queryConstante) {
                if (constante.getValor() != null) {
                    constantesParametros.put(constante.getNombre(), constante.getValor());
                }
            }

            List<Parametro> queryParametro = entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_PARAMETROS_INICIO, Parametro.class)
                    .getResultList();
            for (Parametro parametro : queryParametro) {
                if (parametro.getValor() != null) {
                    constantesParametros.put(parametro.getNombre(), parametro.getValor());
                }
            }
        } catch (Exception e) {
            return constantesParametros;
        }
        return constantesParametros;
    }

    private void actualizarParametros(String nombreConstante, String valorCostante) {
        Parametro parametro = null;
        try {
            parametro = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_ACTUALIZAR_PARAMETRO, Parametro.class)
                    .setParameter("nombreParametro", nombreConstante).getSingleResult();
        } catch (NoResultException e) {
            parametro = new Parametro();
            parametro.setNombre(nombreConstante);
            parametro.setValor(valorCostante);
            entityManager.persist(parametro);
        } catch (NonUniqueResultException nure) {
            logger.error("hay más de un registro en base de datos para el parámetro" + valorCostante, nure);
            throw nure;
        }
        parametro.setNombre(nombreConstante);
        parametro.setValor(valorCostante);
        entityManager.merge(parametro);
    }

    @Override
    public String probarConsultaConstante(Long iteraciones) {
        logger.debug("Empieza a consultar " + iteraciones + " veces");
        for (int i = 0; i < iteraciones; i++) {
            String constante = entityManager.createNamedQuery(NamedQueriesConstants.OBTENER_CONSTANTE, String.class).setParameter("key", "CAJA_COMPENSACION_CODIGO")
                    .getSingleResult();
            logger.debug("Iteración: " + i + "Valor constante: " + constante);
        }
        logger.debug("Terminado");
        return "Terminado";
    }

    
	@Override
	public List<ConexionOperadorInformacionDTO> consultarConexionOperadorInfo(String nombre, String host, String url,
			Short puerto, Long idOperadorInformacion) {
		
		List<ConexionOperadorInformacionDTO> lista = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_CONEXIONES_OPERADOR_INFORMACION)
					.setParameter("nombre", nombre)
					.setParameter("host", host)
					.setParameter("url", url)
					.setParameter("puerto", puerto != null ? puerto.toString() : puerto)
					.setParameter("idOperadorInformacion", idOperadorInformacion)
					.getResultList();

			if(lista != null && !lista.isEmpty()){
				DesEncrypter desc = DesEncrypter.getInstance();
				
				for (ConexionOperadorInformacionDTO coiDTO : lista) {
					coiDTO.setUsuario(desc.decrypt(coiDTO.getUsuario()));
					coiDTO.setContrasena(desc.decrypt(coiDTO.getContrasena()));
				}
			}
			return lista;
		
		
	}
    
    
	@Override
	public void crearModificarConexionOperadorInfo(ConexionOperadorInformacionDTO conexionOperadorInformacionDTO) {
		logger.debug("Inicia crearModificarConexionOperadorInfo(ConexionOperadorInformacionDTO)");
		ConexionOperadorInformacion conexion;
		OperadorInformacionCcf operadorCcf;

		DesEncrypter desc = DesEncrypter.getInstance();
				
		CajaCompensacion caja = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_CAJA_COMPENSACION, CajaCompensacion.class)
				.setParameter("ccfId", Integer.valueOf(CacheManager.getConstante(ConstantesSistemaConstants.CAJA_COMPENSACION_ID).toString()))
				.getSingleResult();
		
		OperadorInformacion operadorInformacion = entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_OPERADOR_INFORMACION, OperadorInformacion.class)
				.setParameter("idOperador", conexionOperadorInformacionDTO.getIdOperadorInformacion())
				.getSingleResult();
		
		operadorCcf = new OperadorInformacionCcf();
		operadorCcf.setOperadorInformacion(operadorInformacion);
		operadorCcf.setEstado(true);
		operadorCcf.setCajaCompensacion(caja);
		
		entityManager.persist(operadorCcf);

    	try {
            conexion = entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_CONEXION_OPERADOR_INFORMACION_POR_ID, ConexionOperadorInformacion.class)
                    .setParameter("idConexion", conexionOperadorInformacionDTO.getId()).getSingleResult();

			if (operadorCcf != null) {
				conexion.setOperadorinformacionCcf(operadorCcf);
			}
			if (conexionOperadorInformacionDTO.getProtocolo() != null) {
				conexion.setProtocolo(conexionOperadorInformacionDTO.getProtocolo());
			}
			if (conexionOperadorInformacionDTO.getUrl() != null) {
				conexion.setUrl(conexionOperadorInformacionDTO.getUrl());
			}
			if (conexionOperadorInformacionDTO.getPuerto() != null) {
				conexion.setPuerto(conexionOperadorInformacionDTO.getPuerto());
			}
			if (conexionOperadorInformacionDTO.getHost() != null) {
				conexion.setHost(conexionOperadorInformacionDTO.getHost());
			}
			if (conexionOperadorInformacionDTO.getUsuario() != null) {
				conexion.setUsuario(desc.encrypt(conexionOperadorInformacionDTO.getUsuario()));
			}
			if (conexionOperadorInformacionDTO.getContrasena() != null) {
				conexion.setContrasena(desc.encrypt(conexionOperadorInformacionDTO.getContrasena()));
			}
			if (conexionOperadorInformacionDTO.getNombre() != null) {
				conexion.setNombre(conexionOperadorInformacionDTO.getNombre());
			}
            entityManager.merge(conexion);
            
        } catch (Exception e) {
        	conexion = new ConexionOperadorInformacion();
			conexion.setOperadorinformacionCcf(operadorCcf);
			conexion.setProtocolo(Protocolo.SFTP);
			conexion.setUrl(conexionOperadorInformacionDTO.getUrl());
			conexion.setPuerto(conexionOperadorInformacionDTO.getPuerto());
			conexion.setHost(conexionOperadorInformacionDTO.getHost());
			conexion.setUsuario(desc.encrypt(conexionOperadorInformacionDTO.getUsuario()));
			conexion.setContrasena(desc.encrypt(conexionOperadorInformacionDTO.getContrasena()));
			conexion.setNombre(conexionOperadorInformacionDTO.getNombre());
            entityManager.persist(conexion);
        }
        logger.debug("Finaliza crearModificarConexionOperadorInfo(ConexionOperadorInformacionDTO)");
	}

	@Override
	public List<OperadorInformacion> consultarOperadoresInformacionInfo() {
		List<OperadorInformacion> lista = (List<OperadorInformacion>) entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_OPERADORES_INFORMACION)
				.getResultList();
		return lista;
	}

	@Override
	public Parametro consultarMargeToleranciaMoraAporte(Boolean isPila) {
		
		try {
			Parametro parametro = (Parametro) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_PARAMETRO_MARGEN_TOLERANCIA_APORTE)
					.setParameter("nombreParametro", isPila ? ParametrosSistemaConstants.MARGEN_TOLERANCIA_DIFERENCIA_MORA_APORTE_PILA : ParametrosSistemaConstants.MARGEN_TOLERANCIA_DIFERENCIA_MORA_APORTE_PAGOS_MANUALES)
					.getSingleResult();
			return parametro;
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public void crearModificarMargeToleranciaMoraAporte(Parametro parametro) {
		
		try {
			Parametro prm = (Parametro) entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_PARAMETRO_MARGEN_TOLERANCIA_APORTE)
					.setParameter("nombreParametro", parametro.getNombre())
					.getSingleResult();
			
			prm.setNombre(parametro.getNombre());
			prm.setDescripcion(parametro.getDescripcion());
			prm.setTipoDato(parametro.getTipoDato());
			prm.setValor(parametro.getValor());
			
			entityManager.merge(prm);
			
		} catch (NoResultException nre) {
			
			if(parametro.getCargaInicio() == null){
				parametro.setCargaInicio(false);
			}
			if(parametro.getSubCategoriaParametroEnum() == null){
				parametro.setSubCategoriaParametroEnum(SubCategoriaParametroEnum.VALOR_GLOBAL_NEGOCIO);
			}
			
			entityManager.persist(parametro);
		}
	}	
    @Override
    public void parametrizacionGapS(ParametrizacionGapsDTO parametrizacionGapsDTO) {

        ParametrizacionGaps parametrizacionGap =  entityManager
        .createNamedQuery(NamedQueriesConstants.CONSULTAR_ESTADO_PARAMETRIZACION_GAPS, ParametrizacionGaps.class)
        .setParameter("parametro", parametrizacionGapsDTO.getNombreParametro())
        .getSingleResult();
       
        String pattern = "MM/dd/yyyy HH:mm:ss";
        DateFormat df = new SimpleDateFormat(pattern);
        String fechaModificacion = df.format(new Date());

        parametrizacionGap.setEstado(parametrizacionGapsDTO.getEstado());
        parametrizacionGap.setFechaModificacion(fechaModificacion);
        entityManager.merge(parametrizacionGap);
    }
    @Override
    public ParametrizacionGaps consultarParametrizacionGaps(String parametro) {
        ParametrizacionGaps parametrizacionGap =  entityManager
        .createNamedQuery(NamedQueriesConstants.CONSULTAR_ESTADO_PARAMETRIZACION_GAPS, ParametrizacionGaps.class)
        .setParameter("parametro", parametro)
        .getSingleResult();
        return parametrizacionGap;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<ConstantesValorUVTDTO> consultarValorUVT() {
        List<ConstantesValorUVTDTO> listado = new ArrayList<>();
        try {
            List<ConstantesValorUVTDTO> constantesValorUVTDTO = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_VALORES_UVT, ConstantesValorUVTDTO.class)
                    .getResultList();
            listado = constantesValorUVTDTO;
        } catch (NoResultException nre) {
			return null;
		}
        return listado;
    }

    @Override
    public void modificarValorUVT(ConstantesValorUVTDTO valorUVT) {
            ParametrizacionValorUVT pvu = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_VALORES_UVT_POR_ID, ParametrizacionValorUVT.class)
				.setParameter("idValorUVT", valorUVT.getIdValorUVT())
					.getSingleResult();
			pvu.setValorUVT(valorUVT.getValorUVT());
			entityManager.merge(pvu);
    }

    @Override
    public Boolean crearValorUVT(ParametrizacionValorUVT valorUVT) {
        try {
            TypedQuery<ParametrizacionValorUVT> query = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_VALORES_UVT_POR_ANIO, ParametrizacionValorUVT.class);
            query.setParameter("anio", valorUVT.getAnio());
            List<ParametrizacionValorUVT> resultList = query.getResultList();

            if (!resultList.isEmpty()) {
                return false;
            }

            entityManager.persist(valorUVT);
            return true;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Response actualizarDummy(String fecha,String user,String pass,UserDTO userDTO){
        SimpleDateFormat format = new SimpleDateFormat(this.FORMATO_DUMMY);
        try{
            Boolean autenticado = null;
            if(userDTO.getNombreUsuario().equals("service-account-clientes_web")){
                logger.info(userDTO.getNombreUsuario());
                userDTO.setNombreUsuario(user);
                ThreadContext contexto = ThreadContext.get();
                contexto.setUserName(user);
                ValidarCredencialesUsuario validarCredencialesUsuario = new ValidarCredencialesUsuario(user, pass);
                validarCredencialesUsuario.execute();
                autenticado = validarCredencialesUsuario.getResult();
            }else{
                autenticado = true;
            }
            if(autenticado){
                if(fecha != null ){
                    try{
                        format.parse(fecha);
                        Constante constanteDummy = (Constante) entityManager.createNamedQuery(NamedQueriesConstants.OBTENER_DUMMY).getSingleResult();
                        constanteDummy.setValor(fecha);
                        entityManager.merge(constanteDummy);
                        ModificacionConstanteDummy mcd = new ModificacionConstanteDummy();
                        mcd.setUsuario(user);
                        mcd.setIdConstante(constanteDummy.getIdConstante());
                        entityManager.merge(mcd);
                        return Response.status(Response.Status.OK).type(MediaType.APPLICATION_JSON).entity("Fecha Dummy actualizada con exito").build();
                    }catch(Exception e ){
                        e.printStackTrace();
                        return Response.status(Response.Status.BAD_REQUEST)
                        .type(MediaType.APPLICATION_JSON).entity("Recuerde que el formato de la fecha Dummy es el siguiente: AAAA-mm-DD")
                        .build();
                    }
                }
                return Response.status(Response.Status.BAD_REQUEST)
                    .type(MediaType.APPLICATION_JSON).entity("La fecha no puede venir en vacio")
                    .build();
            }
            return Response.status(Response.Status.UNAUTHORIZED)
            .type(MediaType.APPLICATION_JSON).entity("Autenticacion fallida")
            .build();
        }catch(Exception e){
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST)
            .type(MediaType.APPLICATION_JSON).entity("Ocurrio un error al actualizar la fecha Dummy contacet a soporte")
            .build();
        }
    }

}
