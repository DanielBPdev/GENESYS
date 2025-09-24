package com.asopagos.novedades.personas.web.load.validator;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.asopagos.constants.ArchivoMultipleCampoConstants;
import com.asopagos.constants.ExpresionesRegularesConstants;
import com.asopagos.dto.ResultadoHallazgosValidacionArchivoDTO;
import com.asopagos.entidades.transversal.core.Departamento;
import com.asopagos.entidades.transversal.core.Municipio;
import com.asopagos.enumeraciones.personas.TipoMedioDePagoEnum;
import com.asopagos.novedades.constants.ArchivoCampoNovedadConstante;
import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.validator.LineValidator;
import com.asopagos.personas.clients.ObtenerNombrePais;

/**
 * <b>Descripcion:</b> Clase que valida la estructura del archivo de empleadores<br/>
 * <b>Módulo:</b> Asopagos - HU-498<br/>
 *
 * @author <a href="mailto:jocorrea@heinsohn.com.co">jocorrea</a>
 */

public class ActualizacionInformacionAfiliadosLineValidator extends LineValidator {

    private static final String CLASS_NAME = ActualizacionInformacionAfiliadosLineValidator.class.getSimpleName();

    private List<ResultadoHallazgosValidacionArchivoDTO> lstHallazgos;

    public ActualizacionInformacionAfiliadosLineValidator() {
        super();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void validate(LineArgumentDTO arguments) throws FileProcessingException {
        String methodName = CLASS_NAME + "." + new Object() {}.getClass().getEnclosingMethod().getName() + "... ";
        System.out.println("Inicio del método " + methodName);
        System.out.println();

        lstHallazgos = new ArrayList<ResultadoHallazgosValidacionArchivoDTO>();
        Map<String, Object> line = arguments.getLineValues();

        try {
            Long lineNumber = arguments.getLineNumber();

            // ... (Validaciones de campos)
            // Se valida el campo No 1 - Tipo de identificación del trabajador o cotizante o cabeza de familia
            FieldValidatorUtil.validateTipoIdentificacion(lstHallazgos, line, lineNumber,
                ArchivoCampoNovedadConstante.TIPO_IDENTIFICACION_AFILIADO,
                ArchivoCampoNovedadConstante.TIPO_IDENTIFICACION_AFILIADO_MSG, ArchivoMultipleCampoConstants.LONGITUD_2_CARACTERES,
                true, false, true);
            
            FieldValidatorUtil.validateAfiliado(lstHallazgos, line, lineNumber,
                ArchivoCampoNovedadConstante.TIPO_IDENTIFICACION_AFILIADO,
                ArchivoCampoNovedadConstante.TIPO_IDENTIFICACION_AFILIADO_MSG, ArchivoMultipleCampoConstants.LONGITUD_2_CARACTERES,
                true, false, true, ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_AFILIADO);

            // Se valida el campo No 2 - Número de identificación del trabajador o cotizante o cabeza de familia
            FieldValidatorUtil.validate(lstHallazgos, line, lineNumber, ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_AFILIADO,
                ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_AFILIADO_MSG, ArchivoMultipleCampoConstants.LONGITUD_17_CARACTERES,
                null, null, true, false);

            // Se valida el campo No 3 - Primer apellido del trabajador o cabeza de familia
            FieldValidatorUtil.validate(lstHallazgos, line, lineNumber, ArchivoCampoNovedadConstante.PRIMER_APELLIDO_AFILIADO,
                ArchivoCampoNovedadConstante.PRIMER_APELLIDO_AFILIADO_MSG, ArchivoMultipleCampoConstants.LONGITUD_20_CARACTERES, null,
                null, true, false);

            // Se valida el campo No 4 - Segundo apellido del trabajador o cabeza de familia
            FieldValidatorUtil.validate(lstHallazgos, line, lineNumber, ArchivoCampoNovedadConstante.SEGUNDO_APELLIDO_AFILIADO,
                ArchivoCampoNovedadConstante.SEGUNDO_APELLIDO_AFILIADO_MSG, ArchivoMultipleCampoConstants.LONGITUD_30_CARACTERES, null,
                null, false, false);

            // Se valida el campo No 5 - Primer nombre del trabajador o cabeza de familia
            FieldValidatorUtil.validate(lstHallazgos, line, lineNumber, ArchivoCampoNovedadConstante.PRIMER_NOMBRE_AFILIADO,
                ArchivoCampoNovedadConstante.PRIMER_NOMBRE_AFILIADO_MSG, ArchivoMultipleCampoConstants.LONGITUD_20_CARACTERES, null,
                null, true, false);

            // Se valida el campo No 6 - Segundo nombre del trabajador o cabeza de familia
            FieldValidatorUtil.validate(lstHallazgos, line, lineNumber, ArchivoCampoNovedadConstante.SEGUNDO_NOMBRE_AFILIADO,
                ArchivoCampoNovedadConstante.SEGUNDO_NOMBRE_AFILIADO_MSG, ArchivoMultipleCampoConstants.LONGITUD_30_CARACTERES, null,
                null, false, false);

            // Se valida el campo No 7 - Fecha de nacimiento del trabajador o cabeza de familia
            FieldValidatorUtil.validate(lstHallazgos, line, lineNumber, ArchivoCampoNovedadConstante.FECHA_NACIMIENTO_AFILIADO,
                ArchivoCampoNovedadConstante.FECHA_NACIMIENTO_AFILIADO_MSG, ArchivoMultipleCampoConstants.LONGITUD_10_CARACTERES, null,
                null, true, true);

            // Se valida el campo No 8 - Genero del trabajador o cabeza de familia
            FieldValidatorUtil.validate(lstHallazgos, line, lineNumber, ArchivoCampoNovedadConstante.GENERO_AFILIADO,
                ArchivoCampoNovedadConstante.GENERO_AFILIADO_MSG, ArchivoMultipleCampoConstants.LONGITUD_1_CARACTER,
                FieldValidatorUtil.ENUM_GENERO, null, false, false);

            // Se valida el campo No 9 - Departamento de nacimiento del trabajador o cabeza de familia
            Departamento departamento = (Departamento) FieldValidatorUtil.validateDepartamentoMunicipio(lstHallazgos, arguments, line,
                lineNumber, ArchivoCampoNovedadConstante.CODIGO_DEPARTEMANETO_NACIMIENTO_AFILIADO,
                ArchivoCampoNovedadConstante.CODIGO_DEPARTEMANETO_NACIMIENTO_AFILIADO_MSG,
                ArchivoMultipleCampoConstants.LONGITUD_2_CARACTERES, false, true, false);

            // Se valida el campo No 10 - Municipio de nacimiento del trabajador o cabeza de familia
            Municipio municipio = (Municipio) FieldValidatorUtil.validateDepartamentoMunicipio(lstHallazgos, arguments, line, lineNumber,
                ArchivoCampoNovedadConstante.CODIGO_MUNICIPIO_NACIMIENTO_AFILIADO,
                ArchivoCampoNovedadConstante.CODIGO_MUNICIPIO_NACIMIENTO_AFILIADO_MSG,
                ArchivoMultipleCampoConstants.LONGITUD_5_CARACTERES, false, false, true);

            if (departamento != null && municipio != null) {
                if (!municipio.getIdDepartamento().equals(departamento.getIdDepartamento())) {
                    lstHallazgos
                        .add(FieldValidatorUtil.crearHallazgo(lineNumber, ArchivoCampoNovedadConstante.CODIGO_MUNICIPIO_EMPLEADOR_MSG,
                            ArchivoMultipleCampoConstants.MENSAJE_ERROR_DEPARTAMENTO_MUNICIPIO));
                }
            }

            // Se valida el campo No 11 - Nacionalidad del trabajador o cabeza de familia
            FieldValidatorUtil.validate(lstHallazgos, line, lineNumber, ArchivoCampoNovedadConstante.NACIONALIDAD_AFILIADO,
                ArchivoCampoNovedadConstante.NACIONALIDAD_AFILIADO_MSG, ArchivoMultipleCampoConstants.LONGITUD_4_CARACTERES, null, null,
                false, false);

            // Se valida el campo No 12 - Departamento de expedición del documento de identificación
            departamento = (Departamento) FieldValidatorUtil.validateDepartamentoMunicipio(lstHallazgos, arguments, line, lineNumber,
                ArchivoCampoNovedadConstante.CODIGO_DEPARTEMANETO_EXPEDICION_DOC_AFILIADO,
                ArchivoCampoNovedadConstante.CODIGO_DEPARTEMANETO_EXPEDICION_DOC_AFILIADO_MSG,
                ArchivoMultipleCampoConstants.LONGITUD_2_CARACTERES, false, true, false);

            // Se valida el campo No 13 - Municipio de expedición del documento de identificación
            municipio = (Municipio) FieldValidatorUtil.validateDepartamentoMunicipio(lstHallazgos, arguments, line, lineNumber,
                ArchivoCampoNovedadConstante.CODIGO_MUNICIPIO_EXPEDICION_DOC_AFILIADO,
                ArchivoCampoNovedadConstante.CODIGO_MUNICIPIO_EXPEDICION_DOC_AFILIADO_MSG,
                ArchivoMultipleCampoConstants.LONGITUD_5_CARACTERES, false, false, true);

            if (departamento != null && municipio != null) {
                if (!municipio.getIdDepartamento().equals(departamento.getIdDepartamento())) {
                    lstHallazgos
                        .add(FieldValidatorUtil.crearHallazgo(lineNumber, ArchivoCampoNovedadConstante.CODIGO_MUNICIPIO_EMPLEADOR_MSG,
                            ArchivoMultipleCampoConstants.MENSAJE_ERROR_DEPARTAMENTO_MUNICIPIO));
                }
            }

            // Se valida el campo No 14 - Fecha de expedición del documento de Identificación
            FieldValidatorUtil.validate(lstHallazgos, line, lineNumber, ArchivoCampoNovedadConstante.FECHA_EXPEDICION_DOC_AFILIADO,
                ArchivoCampoNovedadConstante.FECHA_EXPEDICION_DOC_AFILIADO_MSG, ArchivoMultipleCampoConstants.LONGITUD_10_CARACTERES,
                null, null, false, true);

            //Se valida el campo No 15 salario
            FieldValidatorUtil.validate(
                lstHallazgos,
                line,
                lineNumber,
                ArchivoCampoNovedadConstante.SALARIO,
                ArchivoCampoNovedadConstante.SALARIO_MSG,
                ArchivoMultipleCampoConstants.LONGITUD_50_CARACTERES,
                null,
                null,
                false,
                false);

            //Se valida el campo No 16 orientación sexual
            FieldValidatorUtil.validate(
                lstHallazgos,
                line,
                lineNumber,
                ArchivoCampoNovedadConstante.ORIENTACION_SEXUAL,
                ArchivoCampoNovedadConstante.ORIENTACION_SEXUAL_MSG,
                ArchivoMultipleCampoConstants.LONGITUD_50_CARACTERES,
                5,
                null,
                false,
                false);

            //Se valida el campo No 17 factor de vulnerabilidad
            FieldValidatorUtil.validate(
                lstHallazgos,
                line,
                lineNumber,
                ArchivoCampoNovedadConstante.FACTOR_DE_VULNERABILIDAD,
                ArchivoCampoNovedadConstante.FACTOR_DE_VULNERABILIDAD_MSG,
                ArchivoMultipleCampoConstants.LONGITUD_50_CARACTERES,
                6,
                null,
                false,
                false);

            //Se valida el campo No 18 estado civil
            FieldValidatorUtil.validate(
                lstHallazgos,
                line,
                lineNumber,
                ArchivoCampoNovedadConstante.ESTADO_CIVIL,
                ArchivoCampoNovedadConstante.ESTADO_CIVIL_MSG,
                ArchivoMultipleCampoConstants.LONGITUD_30_CARACTERES,
                7,
                null,
                false,
                false);

            //Se valida el campo No 19 pertenencia étnica
            FieldValidatorUtil.validate(
                lstHallazgos,
                line,
                lineNumber,
                ArchivoCampoNovedadConstante.PERTENENCIA_ETNICA,
                ArchivoCampoNovedadConstante.PERTENENCIA_ETNICA_MSG,
                ArchivoMultipleCampoConstants.LONGITUD_50_CARACTERES,
                8,
                null,
                false,
                false);

            //Se valida el campo No 20 pais
            FieldValidatorUtil.validate(
                lstHallazgos,
                line,
                lineNumber,
                ArchivoCampoNovedadConstante.PAIS,
                ArchivoCampoNovedadConstante.PAIS_MSG,
                ArchivoMultipleCampoConstants.LONGITUD_50_CARACTERES,
                9,
                null,
                false,
                false);

            //Se valida el campo No 21 correo electrónico
            FieldValidatorUtil.validate(
                lstHallazgos,
                line,
                lineNumber,
                ArchivoCampoNovedadConstante.EMAIL,
                ArchivoCampoNovedadConstante.EMAIL_MSG,
                ArchivoMultipleCampoConstants.LONGITUD_60_CARACTERES,
                null,
                ExpresionesRegularesConstants.EXPRESION_REGULAR_VALIDA_EMAIL,
                false,
                false);

            //Se valida el campo No 22 celular
            FieldValidatorUtil.validate(
                lstHallazgos,
                line,
                lineNumber,
                ArchivoCampoNovedadConstante.CELULAR_AFILIADO,
                ArchivoCampoNovedadConstante.CELULAR_AFILIADO_MSG,
                ArchivoMultipleCampoConstants.LONGITUD_10_CARACTERES,
                null,
                ExpresionesRegularesConstants.TELEFONO_CELULAR,
                false,
                false);

            //Se valida el campo No 23 Teléfono
            FieldValidatorUtil.validate(
                lstHallazgos,
                line,
                lineNumber,
                ArchivoCampoNovedadConstante.TELEFONO_AFILIADO,
                ArchivoCampoNovedadConstante.TELEFONO_AFILIADO_MSG,
                ArchivoMultipleCampoConstants.LONGITUD_7_CARACTERES,
                null,
                ExpresionesRegularesConstants.TELEFONO_FIJO,
                false,
                false);

            // Validación del TipoMedioDePago Campo No 24 para requerir ciertos campos
            String tipoMedioDePago = (String) line.get(ArchivoCampoNovedadConstante.MEDIO_DE_PAGO_PARA_SUBSIDIO_MONETARIO_AFILIADO);

            if (tipoMedioDePago != null) {
                TipoMedioDePagoEnum tipoMedioDePagoEnum = null;

                try {
                    tipoMedioDePagoEnum = TipoMedioDePagoEnum.valueOf(tipoMedioDePago);

                    // Validaciones específicas para transferencia
                    if (tipoMedioDePagoEnum.equals(TipoMedioDePagoEnum.TRANSFERENCIA)) {
                        //Se valida el campo No 25 tipo cuenta
                        FieldValidatorUtil.validate(
                            lstHallazgos,
                            line,
                            lineNumber,
                            ArchivoCampoNovedadConstante.TIPO_DE_CUENTA,
                            ArchivoCampoNovedadConstante.TIPO_DE_CUENTA_MSG,
                            ArchivoMultipleCampoConstants.LONGITUD_50_CARACTERES,
                            12,
                            null,
                            true,
                            false);

                        //Se valida el campo No 26 numero de cuenta
                        FieldValidatorUtil.validate(
                            lstHallazgos,
                            line,
                            lineNumber,
                            ArchivoCampoNovedadConstante.NUMERO_DE_CUENTA,
                            ArchivoCampoNovedadConstante.NUMERO_DE_CUENTA_MSG,
                            ArchivoMultipleCampoConstants.LONGITUD_20_CARACTERES,
                            null,
                            ExpresionesRegularesConstants.VALOR_NUMERICO,
                            true,
                            false);

                        //Se valida el campo No 27 tipo identificación titular
                        FieldValidatorUtil.validate(
                            lstHallazgos,
                            line,
                            lineNumber,
                            ArchivoCampoNovedadConstante.TIPO_IDENTIFICACION_TITULAR,
                            ArchivoCampoNovedadConstante.TIPO_IDENTIFICACION_TITULAR_MSG,
                            ArchivoMultipleCampoConstants.LONGITUD_50_CARACTERES,
                            13,
                            null,
                            true,
                            false);

                        //Se valida el campo No 28 numero de identificacion del titular
                        FieldValidatorUtil.validate(
                            lstHallazgos,
                            line,
                            lineNumber,
                            ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_TITULAR,
                            ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_TITULAR_MSG,
                            ArchivoMultipleCampoConstants.LONGITUD_20_CARACTERES,
                            null,
                            null,
                            true,
                            false);

                        //Se valida el campo No 29 nombre del titular de la cuenta
                        FieldValidatorUtil.validate(
                            lstHallazgos,
                            line,
                            lineNumber,
                            ArchivoCampoNovedadConstante.NOMBRE_TITULAR_CUENTA,
                            ArchivoCampoNovedadConstante.NOMBRE_TITULAR_CUENTA_MSG,
                            ArchivoMultipleCampoConstants.LONGITUD_100_CARACTERES,
                            null,
                            null,
                            true,
                            false);

                        //Se valida el campo No 30 NIT Banco
                        FieldValidatorUtil.validate(
                            lstHallazgos,
                            line,
                            lineNumber,
                            ArchivoCampoNovedadConstante.NIT_BANCO,
                            ArchivoCampoNovedadConstante.NIT_BANCO_MSG,
                            ArchivoMultipleCampoConstants.LONGITUD_20_CARACTERES,
                            11,
                            ExpresionesRegularesConstants.NIT,
                            true,
                            false);

                    }
                } catch (Exception e) {
                    System.out.println(methodName + "Error al obtener TipoMedioDePagoEnum en Line Validator: " + e);
                    lstHallazgos.add(FieldValidatorUtil.crearHallazgo(lineNumber, ArchivoCampoNovedadConstante.MEDIO_DE_PAGO_PARA_SUBSIDIO_MONETARIO_AFILIADO_MSG, ArchivoMultipleCampoConstants.MENSAJE_ERROR_VALOR_PERMITIDO));
                }

            } 

        } finally {
            // ... (Operaciones finales y manejo de resultados)
            ((List<Long>) arguments.getContext().get(ArchivoMultipleCampoConstants.TOTAL_REGISTRO)).add(1L);
            ((List<ResultadoHallazgosValidacionArchivoDTO>) arguments.getContext().get(ArchivoMultipleCampoConstants.LISTA_HALLAZGOS))
                .addAll(lstHallazgos);
            if (!lstHallazgos.isEmpty()) {
                ((List<Long>) arguments.getContext().get(ArchivoMultipleCampoConstants.TOTAL_REGISTRO_ERRORES)).add(1L);
            }

            System.out.println("Final del método " + methodName);
        }
    }
}
