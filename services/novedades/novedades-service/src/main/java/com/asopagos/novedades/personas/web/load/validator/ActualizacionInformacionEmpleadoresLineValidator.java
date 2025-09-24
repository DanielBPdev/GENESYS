package com.asopagos.novedades.personas.web.load.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.asopagos.constants.ArchivoMultipleCampoConstants;
import com.asopagos.constants.ExpresionesRegularesConstants;
import com.asopagos.dto.ResultadoHallazgosValidacionArchivoDTO;
import com.asopagos.entidades.transversal.core.CodigoCIIU;
import com.asopagos.entidades.transversal.core.Departamento;
import com.asopagos.entidades.transversal.core.Municipio;
import com.asopagos.novedades.constants.ArchivoCampoNovedadConstante;
import com.asopagos.util.GetValueUtil;
import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.validator.LineValidator;

/**
 * <b>Descripcion:</b> Clase que valida la estructura del archivo de empleadores<br/>
 * <b>Módulo:</b> Asopagos - HU-241<br/>
 *
 * @author <a href="mailto:jocorrea@heinsohn.com.co">jocorrea</a>
 */

public class ActualizacionInformacionEmpleadoresLineValidator extends LineValidator {

    /**
     * Listado de hallazgos
     */
    private List<ResultadoHallazgosValidacionArchivoDTO> lstHallazgos;

    /**
     * Método constructor de la clase
     */
    public ActualizacionInformacionEmpleadoresLineValidator() {
        super();
    }

    /**
     * (non-Javadoc)
     *
     * @see co.com.heinsohn.lion.fileprocessing.fileloader.validator.LineValidator#validate(co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO)
     */
    @SuppressWarnings("unchecked")
    @Override
    public void validate(LineArgumentDTO arguments) throws FileProcessingException {
        lstHallazgos = new ArrayList<ResultadoHallazgosValidacionArchivoDTO>();
        Map<String, Object> line = arguments.getLineValues();
        try {
            Long lineNumber = arguments.getLineNumber();
            // Se valida el campo No 1 - Tipo de identificación del autorizado a reportar (empleador)
            FieldValidatorUtil.validateTipoIdentificacion(lstHallazgos, line, lineNumber,
                ArchivoCampoNovedadConstante.TIPO_IDENTIFICACION_EMPLEADOR,
                ArchivoCampoNovedadConstante.TIPO_IDENTIFICACION_EMPLEADOR_MSG, ArchivoMultipleCampoConstants.LONGITUD_2_CARACTERES,
                true, true, false);

            // Se valida el campo No 2 - Número de identificación del autorizado a reportar (empleador)
            FieldValidatorUtil.validate(lstHallazgos, line, lineNumber, ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_EMPLEADOR,
                ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_EMPLEADOR_MSG, ArchivoMultipleCampoConstants.LONGITUD_17_CARACTERES,
                null, null, true, false);

            // Se valida el campo No 3 - Digito de verificación
            FieldValidatorUtil.validate(lstHallazgos, line, lineNumber, ArchivoCampoNovedadConstante.DIGITO_VERIFICACION_EMPLEADOR,
                ArchivoCampoNovedadConstante.DIGITO_VERIFICACION_EMPLEADOR_MSG, ArchivoMultipleCampoConstants.LONGITUD_1_CARACTER, null,
                ExpresionesRegularesConstants.UNO_O_MAS_DIGITOS, false, false);

            // Se valida el campo No 4 - Razón social del autorizado a reportar(empleador)
            FieldValidatorUtil.validate(lstHallazgos, line, lineNumber, ArchivoCampoNovedadConstante.RAZON_SOCIAL_EMPLEADOR,
                ArchivoCampoNovedadConstante.RAZON_SOCIAL_EMPLEADOR, ArchivoMultipleCampoConstants.LONGITUD_200_CARACTERES, null,
                null, true, false);

            // Se valida el campo No 5 - Naturaleza jurídica
            FieldValidatorUtil.validate(lstHallazgos, line, lineNumber, ArchivoCampoNovedadConstante.NATURALEZA_JURIDICA_EMPLEADOR,
                ArchivoCampoNovedadConstante.NATURALEZA_JURIDICA_EMPLEADOR_MSG, ArchivoMultipleCampoConstants.LONGITUD_1_CARACTER,
                FieldValidatorUtil.ENUM_NATURALEZA_JURIDICA, ExpresionesRegularesConstants.UNO_O_MAS_DIGITOS, true, false);

            // Se valida el campo No 6 - Tipo de identificación del representante legal
            FieldValidatorUtil.validateTipoIdentificacion(lstHallazgos, line, lineNumber,
                ArchivoCampoNovedadConstante.TIPO_IDENTIFICACION_REPRE_LEGAL_EMPLEADOR,
                ArchivoCampoNovedadConstante.TIPO_IDENTIFICACION_REPRE_LEGAL_EMPLEADOR_MSG,
                ArchivoMultipleCampoConstants.LONGITUD_2_CARACTERES, true, false, true);

            // Se valida el campo No 7 - Número de identificación del representante legal
            FieldValidatorUtil.validate(lstHallazgos, line, lineNumber,
                ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_REPRE_LEGAL_EMPLEADOR,
                ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_REPRE_LEGAL_EMPLEADOR_MSG,
                ArchivoMultipleCampoConstants.LONGITUD_17_CARACTERES, null, null, true, false);

            // Se valida el campo No 8 - Correo electrónico del representante legal
            FieldValidatorUtil.validate(lstHallazgos, line, lineNumber,
                ArchivoCampoNovedadConstante.CORREO_ELECTRONICO_REPRE_LEGAL_EMPLEADOR,
                ArchivoCampoNovedadConstante.CORREO_ELECTRONICO_REPRE_LEGAL_EMPLEADOR_MSG,
                ArchivoMultipleCampoConstants.LONGITUD_60_CARACTERES, null,
                ExpresionesRegularesConstants.EXPRESION_REGULAR_VALIDA_EMAIL, true, false);

            // Se valida el campo No 9 - Teléfono de contacto del representante legal
            FieldValidatorUtil.validate(lstHallazgos, line, lineNumber, ArchivoCampoNovedadConstante.TELEFONO_REPRE_LEGAL_EMPLEADOR,
                ArchivoCampoNovedadConstante.TELEFONO_REPRE_LEGAL_EMPLEADOR_MSG, ArchivoMultipleCampoConstants.LONGITUD_17_CARACTERES,
                null, ExpresionesRegularesConstants.VALOR_NUMERICO, true, false);

            // Se valida el campo No 10 - Código del departamento de ubicación de la empresa
            Departamento departamento = (Departamento) FieldValidatorUtil.validateDepartamentoMunicipio(lstHallazgos, arguments, line,
                lineNumber, ArchivoCampoNovedadConstante.CODIGO_DEPARTEMANETO_EMPLEADOR,
                ArchivoCampoNovedadConstante.CODIGO_DEPARTEMANETO_EMPLEADOR_MSG, ArchivoMultipleCampoConstants.LONGITUD_2_CARACTERES,
                true, true, false);

            // Se valida el campo No 11 - Código del municipio de ubicación de la empresa
            Municipio municipio = (Municipio) FieldValidatorUtil.validateDepartamentoMunicipio(lstHallazgos, arguments, line, lineNumber,
                ArchivoCampoNovedadConstante.CODIGO_MUNICIPIO_EMPLEADOR, ArchivoCampoNovedadConstante.CODIGO_MUNICIPIO_EMPLEADOR_MSG,
                ArchivoMultipleCampoConstants.LONGITUD_5_CARACTERES, true, false, true);

            if (departamento != null && municipio != null) {
                if (!municipio.getIdDepartamento().equals(departamento.getIdDepartamento())) {
                    lstHallazgos
                        .add(FieldValidatorUtil.crearHallazgo(lineNumber, ArchivoCampoNovedadConstante.CODIGO_MUNICIPIO_EMPLEADOR_MSG,
                            ArchivoMultipleCampoConstants.MENSAJE_ERROR_DEPARTAMENTO_MUNICIPIO));
                }
            }
            // Se valida el campo No 12 - Dirección de ubicación de la empresa
            FieldValidatorUtil.validate(lstHallazgos, line, lineNumber, ArchivoCampoNovedadConstante.DIRECCION_EMPLEADOR,
                ArchivoCampoNovedadConstante.DIRECCION_EMPLEADOR_MSG, ArchivoMultipleCampoConstants.LONGITUD_40_CARACTERES, null, null,
                false, false);

            // Se valida el campo No 13 - Fecha de renovación de la Cámara de Comercio
            FieldValidatorUtil.validate(lstHallazgos, line, lineNumber,
                ArchivoCampoNovedadConstante.FECHA_RENOVACION_CAMARA_COMERCIO_EMPLEADOR,
                ArchivoCampoNovedadConstante.FECHA_RENOVACION_CAMARA_COMERCIO_EMPLEADOR_MSG,
                ArchivoMultipleCampoConstants.LONGITUD_10_CARACTERES, null, null, false, true);

            // Se valida el campo No 14 - Número de empleados
            FieldValidatorUtil.validate(lstHallazgos, line, lineNumber, ArchivoCampoNovedadConstante.NUMERO_EMPLEADOS_EMPLEADOR,
                ArchivoCampoNovedadConstante.NUMERO_EMPLEADOS_EMPLEADOR_MSG, ArchivoMultipleCampoConstants.LONGITUD_5_CARACTERES, null,
                ExpresionesRegularesConstants.UNO_O_MAS_DIGITOS, true, false);

            // Se valida el campo No 15 - Actividad económica principal
            validarCampoActividadEconomica(arguments, line, lineNumber,
                ArchivoCampoNovedadConstante.ACTIVIDAD_ECONOMICA_PRINCIPAL_EMPLEADOR,
                ArchivoCampoNovedadConstante.ACTIVIDAD_ECONOMICA_PRINCIPAL_EMPLEADOR_MSG,
                ArchivoMultipleCampoConstants.LONGITUD_4_CARACTERES, true);

            // Se valida el campo No 16 - Actividad económica secundaria
            validarCampoActividadEconomica(arguments, line, lineNumber,
                ArchivoCampoNovedadConstante.ACTIVIDADA_ECONOMICA_SECUNDARIA_EMPLEADOR,
                ArchivoCampoNovedadConstante.ACTIVIDADA_ECONOMICA_SECUNDARIA_EMPLEADOR_MSG,
                ArchivoMultipleCampoConstants.LONGITUD_4_CARACTERES, false);

            // Se valida el campo No 17 - Fecha de constitución de la empresa
            FieldValidatorUtil.validate(lstHallazgos, line, lineNumber, ArchivoCampoNovedadConstante.FECHA_CONSTITUCION_EMPLEADOR,
                ArchivoCampoNovedadConstante.FECHA_CONSTITUCION_EMPLEADOR_MSG, ArchivoMultipleCampoConstants.LONGITUD_10_CARACTERES,
                null, null, true, true);

            // Se valida el campo No #18 - email 1 oficina principal
            FieldValidatorUtil.validate(
                lstHallazgos,
                line,
                lineNumber,
                ArchivoCampoNovedadConstante.EMAIL_1_OFICINA_PRINCIPAL,
                ArchivoCampoNovedadConstante.EMAIL_1_OFICINA_PRINCIPAL_MSG,
                ArchivoMultipleCampoConstants.LONGITUD_60_CARACTERES,
                null,
                ExpresionesRegularesConstants.EXPRESION_REGULAR_VALIDA_EMAIL,
                false,
                false);
            // Se valida el campo No #19 - email 2 Envío correspondencia
            FieldValidatorUtil.validate(
                lstHallazgos,
                line,
                lineNumber,
                ArchivoCampoNovedadConstante.EMAIL_2_ENVIO_DE_CORRESPONDENCIA,
                ArchivoCampoNovedadConstante.EMAIL_2_ENVIO_DE_CORRESPONDENCIA_MSG,
                ArchivoMultipleCampoConstants.LONGITUD_60_CARACTERES,
                null,
                ExpresionesRegularesConstants.EXPRESION_REGULAR_VALIDA_EMAIL,
                false,
                false);
            // Se valida el campo No #20 - email 3 notificación judicial
            FieldValidatorUtil.validate(
                lstHallazgos,
                line,
                lineNumber,
                ArchivoCampoNovedadConstante.EMAIL_3_NOTIFICACION_JUDICIAL,
                ArchivoCampoNovedadConstante.EMAIL_3_NOTIFICACION_JUDICIAL_MSG,
                ArchivoMultipleCampoConstants.LONGITUD_60_CARACTERES,
                null,
                ExpresionesRegularesConstants.EXPRESION_REGULAR_VALIDA_EMAIL,
                false,
                false);
            // Se valida el campo No #21 - telefono 1 oficina principal
            FieldValidatorUtil.validate(
                lstHallazgos,
                line,
                lineNumber,
                ArchivoCampoNovedadConstante.TELEFONO_1_OFICINA_PRINCIPAL,
                ArchivoCampoNovedadConstante.TELEFONO_1_OFICINA_PRINCIPAL_MSG,
                ArchivoMultipleCampoConstants.LONGITUD_10_CARACTERES,
                null,
                ExpresionesRegularesConstants.TELEFONO_FIJO,
                false,
                false);
            // Se valida el campo No #22 - telefono 2 envio de correspondencia
            FieldValidatorUtil.validate(
                lstHallazgos,
                line,
                lineNumber,
                ArchivoCampoNovedadConstante.TELEFONO_2_ENVIO_DE_CORRESPONDENCIA,
                ArchivoCampoNovedadConstante.TELEFONO_2_ENVIO_DE_CORRESPONDENCIA_MSG,
                ArchivoMultipleCampoConstants.LONGITUD_10_CARACTERES,
                null,
                ExpresionesRegularesConstants.TELEFONO_FIJO,
                false,
                false);
            // Se valida el campo No #23 - telefono 3 notificación judicial
            FieldValidatorUtil.validate(
                lstHallazgos,
                line,
                lineNumber,
                ArchivoCampoNovedadConstante.TELEFONO_3_NOTIFICACION_JUDICIAL,
                ArchivoCampoNovedadConstante.TELEFONO_3_NOTIFICACION_JUDICIAL_MSG,
                ArchivoMultipleCampoConstants.LONGITUD_7_CARACTERES,
                null,
                ExpresionesRegularesConstants.TELEFONO_FIJO,
                false,
                false);
            // Se valida el campo No #24 - celular
            FieldValidatorUtil.validate(
                lstHallazgos,
                line,
                lineNumber,
                ArchivoCampoNovedadConstante.CELULAR_EMPLEADOR,
                ArchivoCampoNovedadConstante.CELULAR_EMPLEADOR_MSG,
                ArchivoMultipleCampoConstants.LONGITUD_10_CARACTERES,
                null,
                ExpresionesRegularesConstants.TELEFONO_CELULAR,
                false,
                false);
            // Se valida el campo No #25 - medio de pago para subsidio monetario
            FieldValidatorUtil.validate(
                lstHallazgos,
                line,
                lineNumber,
                ArchivoCampoNovedadConstante.MEDIO_DE_PAGO_PARA_SUBSIDIO_MONETARIO_EMPLEADOR,
                ArchivoCampoNovedadConstante.MEDIO_DE_PAGO_PARA_SUBSIDIO_MONETARIO_EMPLEADOR_MSG,
                ArchivoMultipleCampoConstants.LONGITUD_20_CARACTERES,
                4,
                null,
                false,
                false);


        } finally {
            ((List<Long>) arguments.getContext().get(ArchivoMultipleCampoConstants.TOTAL_REGISTRO)).add(1L);
            ((List<ResultadoHallazgosValidacionArchivoDTO>) arguments.getContext().get(ArchivoMultipleCampoConstants.LISTA_HALLAZGOS))
                .addAll(lstHallazgos);
            if (!lstHallazgos.isEmpty()) {
                ((List<Long>) arguments.getContext().get(ArchivoMultipleCampoConstants.TOTAL_REGISTRO_ERRORES)).add(1L);
            }
        }
    }

    /**
     * Valida la actividad economica de acuerdo a los registros existentes en la Base de datos
     *
     * @param arguments        Informacion del archivo leido
     * @param line             Informacion de linea del archivo leido
     * @param lineNumber       Numero de la linea del archivo
     * @param nombreCampo      Nombre del campo a validar
     * @param constanteMensaje Nombre del campo para mensajes de error
     * @param longitudCampo    Indica la longitud maxima permitida del campo
     * @param required         Indica si el campo es requerido
     */
    @SuppressWarnings("unchecked")
    private void validarCampoActividadEconomica(LineArgumentDTO arguments, Map<String, Object> line, Long lineNumber, String nombreCampo,
                                                String constanteMensaje, Integer longitudCampo, Boolean isRequired) {
        if (!FieldValidatorUtil.validateNotNull(line, nombreCampo)) {
            FieldValidatorUtil.createMessageRequired(lstHallazgos, lineNumber, constanteMensaje, isRequired);
            return;
        }

        // Se obtiene el valor del campo
        String valorCampo = (String) line.get(nombreCampo);

        // Se verifica que este dentro de los parametros de longitud
        if (!FieldValidatorUtil.validateLength(valorCampo, longitudCampo)) {
            lstHallazgos.add(FieldValidatorUtil.crearHallazgo(lineNumber, constanteMensaje,
                ArchivoMultipleCampoConstants.MENSAJE_ERROR_VALOR_EXCEDE_TAMANIO + longitudCampo));
            return;
        }

        try {
            List<CodigoCIIU> listaCodigosCIIU = (List<CodigoCIIU>) arguments.getContext()
                .get(ArchivoMultipleCampoConstants.LISTA_CODIGO_CIIU);
            CodigoCIIU codigo = GetValueUtil.getCodigoCIIU(listaCodigosCIIU, valorCampo);
            if (codigo == null) {
                lstHallazgos.add(FieldValidatorUtil.crearHallazgo(lineNumber, constanteMensaje,
                    ArchivoMultipleCampoConstants.MENSAJE_ERROR_VALOR_PERMITIDO));
            }
        } catch (Exception e) {
            lstHallazgos.add(FieldValidatorUtil.crearHallazgo(lineNumber, constanteMensaje,
                ArchivoMultipleCampoConstants.MENSAJE_ERROR_VALOR_PERMITIDO));
        }
    }


}