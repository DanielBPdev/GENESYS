package com.asopagos.validaciones.ejb;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.ResultadoValidacionEnum;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.ValidacionCoreEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.validaciones.constants.ConstantesValidaciones;
import com.asopagos.validaciones.constants.NamedQueriesConstants;

public abstract class ValidadorAbstract implements ValidadorCore {

    /**
     * Constante que contiene el separador -.
     */
    protected static final String SEPARADOR = "-";

    /**
     * Entity manager para la clase validador.
     */
    protected EntityManager entityManager;

    /**
     * Bundle que contiene los mensajes de validación.
     */
    protected ResourceBundle myResources = ResourceBundle.getBundle(ConstantesValidaciones.NOMBRE_BUNDLE_MENSAJES);

    /**
     * Referencia al logger
     */
    protected ILogger logger = LogManager.getLogger(ValidadorAbstract.class);

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.validaciones.api.ValidadorCore#setEntityManager(javax.
     * persistence.EntityManager)
     */
    @Override
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Método encargado de crear un item de validación con su respectiva información.
     * @param detalle
     *        descripción del resultado.
     * @param resultado
     *        puede ser aprobado o no aprobado
     * @param validacion
     *        que indica a que validador pertenece.
     * @param tipoExcepcion
     *        tipo de excepción.
     * @return validacion instanciada.
     */
    public ValidacionDTO crearValidacion(String detalle, ResultadoValidacionEnum resultado, ValidacionCoreEnum validacion,
            TipoExcepcionFuncionalEnum tipoExcepcion) {
        ValidacionDTO resultadoValidacion = new ValidacionDTO();
        resultadoValidacion.setDetalle(detalle);
        resultadoValidacion.setResultado(resultado);
        resultadoValidacion.setValidacion(validacion);
        resultadoValidacion.setTipoExcepcion(tipoExcepcion);
        return resultadoValidacion;
    }

    /**
     * Método encargado de crear un item de validacion con la información del beneficiario
     * @param detalle
     *        Descripción del resultado.
     * @param resultado
     *        Puede ser aprobado o no aprobado
     * @param validacion
     *        Indica a que validador pertenece.
     * @param tipoExcepcion
     *        Tipo de excepción generado
     * @param idBeneficiario
     *        Identificador del beneficiario evaluado
     * @return validacion instanciada.
     */
    public ValidacionDTO crearValidacion(String detalle, ResultadoValidacionEnum resultado, ValidacionCoreEnum validacion,
            TipoExcepcionFuncionalEnum tipoExcepcion, List<Long> idBeneficiario) {
        ValidacionDTO va = crearValidacion(detalle, resultado, validacion, tipoExcepcion);
        va.setIdBeneficiario(idBeneficiario);
        return va;
    }

    /**
     * Método encargado de crear el mensaje exitoso.
     * @param validacion
     *        validacion a aprobar.
     * @return validacion instanciada.
     */
    public ValidacionDTO crearMensajeExitoso(ValidacionCoreEnum validacion) {
        return crearValidacion(null, ResultadoValidacionEnum.APROBADA, validacion, null);
    }

    /**
     * Método encargado de crear el mensaje de no evaludado (utilizado cuando por alguna razon no se puede evaluar)
     * 
     * @param key
     *        Llave del mensaje no evaluado
     * @param validacion
     *        Indica el validador ejecutado
     * @param tipoExcepcion
     *        Tipo de excepciòn generado
     * @return validacion instanciada.
     */
    public ValidacionDTO crearMensajeNoEvaluado(String key, ValidacionCoreEnum validacion, TipoExcepcionFuncionalEnum tipoExcepcion) {
        return crearValidacion(myResources.getString(ConstantesValidaciones.KEY_MENSAJE_NO_EV) + SEPARADOR + myResources.getString(key),
                ResultadoValidacionEnum.NO_EVALUADA, validacion, tipoExcepcion);
    }

    /**
     * Método encargado de validar la completitud de los datos de validación
     * @param datosValidacion
     *        Mapa de datos de validación
     * @param camposNecesariosValidacion
     *        Lista de datos necesarios para la validación
     */
    public void verificarDatosValidacion(Map<String, String> datosValidacion, List<String> camposNecesariosValidacion) {
        String msg = "Datos para ejecución de validación no enviados";
        if (datosValidacion == null || datosValidacion.isEmpty() || camposNecesariosValidacion == null
                || camposNecesariosValidacion.isEmpty()) {
            logger.debug(msg);
            throw new TechnicalException(msg);
        }

        for (String campo : camposNecesariosValidacion) {
            if (!datosValidacion.containsKey(campo) || datosValidacion.get(campo) == null) {
                logger.debug(msg + SEPARADOR + campo);
                throw new TechnicalException(msg + SEPARADOR + campo);
            }
        }
    }

    // Métodos comunes validadores Fovis
    /**
     * Verifica y recupera el identificador de postulación de los datos de validación
     * @param datosValidacion
     *        Mapa de datos de validación
     * @return Numero identificador de postulación
     */
    public Long getIdPostulacion(Map<String, String> datosValidacion) {
        List<String> camposValidacion = new ArrayList<>();
        camposValidacion.add(ConstantesValidaciones.ID_POSTULACION);
        verificarDatosValidacion(datosValidacion, camposValidacion);

        return Long.parseLong(datosValidacion.get(ConstantesValidaciones.ID_POSTULACION));
    }

    /**
     * Consulta la cantidad de integrantes por tipo asociados a una postulacion
     * @param idPostulacion
     *        Identificador de postulación
     * @param tipoIntegrante
     *        Tipo de integrante
     * @return Cantidad de integrantes asociados a la postulación
     */
    public Integer consultarCantidadIntegrantesPostulacion(Long idPostulacion, ClasificacionEnum tipoIntegrante) {
        return (Integer) entityManager.createNamedQuery(NamedQueriesConstants.CONTAR_TIPO_INTEGRANTES_HOGAR)
                .setParameter(ConstantesValidaciones.TIPO_INTEGRANTE_HOGAR, ClasificacionEnum.ABUELO_HOGAR)
                .setParameter(ConstantesValidaciones.ID_POSTULACION, idPostulacion).getSingleResult();
    }

    /**
     * Verifica los datos de validación e identifica si la postulación se encuentra en algún estado indicado
     * @param datosValidacion
     *        Mapa de datos de validación
     * @param estadosValidos
     *        Lista de estados validos
     * @return <code>true</code> si la postulación se encuentra en algún estado
     */
    public Boolean verificarEstadoHogarPostulacion(Map<String, String> datosValidacion, List<String> estadosValidos) {
        Long idPostulacion = getIdPostulacion(datosValidacion);
        try {
            entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_ESTADO_HOGAR)
                    .setParameter(ConstantesValidaciones.ESTADOS_HOGAR, estadosValidos)
                    .setParameter(ConstantesValidaciones.ID_POSTULACION, idPostulacion).getSingleResult();
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }

    /**
     * Verifica los datos de validación e identifica si existe el tipo de integrante enviado asociado a la postulación
     * @param datosValidacion
     *        Mapa de datos de validación
     * @param listTiposIntegrante
     *        Lista de tipos integrante que se espera haya registrados
     * @return <code>true</code> si existe al menos un tipo de integrante asociado a la posutlación
     */
    public Boolean verificarRegistroIntegrantePostulacion(Map<String, String> datosValidacion, List<String> listTiposIntegrante) {
        Long idPostulacion = getIdPostulacion(datosValidacion);
        try {
            entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_HOGAR_REGISTRA_TIPO_INTEGRANTE)
                    .setParameter(ConstantesValidaciones.TIPO_INTEGRANTE_HOGAR, listTiposIntegrante)
                    .setParameter(ConstantesValidaciones.ID_POSTULACION, idPostulacion).getSingleResult();

            return true;
        } catch (NonUniqueResultException e) {
            return true;
        } catch (NoResultException nre) {
            return false;
        }
    }
}
