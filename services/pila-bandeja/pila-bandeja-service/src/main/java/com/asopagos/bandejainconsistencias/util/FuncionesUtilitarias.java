package com.asopagos.bandejainconsistencias.util;

import java.util.GregorianCalendar;
import com.asopagos.enumeraciones.pila.CamposNombreArchivoEnum;
import com.asopagos.enumeraciones.pila.TipoArchivoPilaEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;

/**
 * <b>Descripcion:</b> Clase que presenta funciones utilitarias para la gestión de bandeja 
 * de inconsistencias de PILA<br/>
 * <b>Módulo:</b> Asopagos - HU-211-392 <br/>
 *
 * @author  <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */

public class FuncionesUtilitarias {
    // referencia al logger
    private static final ILogger logger = LogManager.getLogger(FuncionesUtilitarias.class);
    
    // constructor privado para ocultar el constructor por defecto de java
    private FuncionesUtilitarias(){}


    /**
     * Función para obtener el valor de un campo del nombre de un archivo PILA
     * @param tipoArchivo
     *        Enumeración del tipo de archivo
     * @param campoSolicitado
     *        Enumeración del campo solicitado
     * @param nombreArchivo
     *        Nombre del archivo
     * @return Valor del campo solicitado o null
     */
    public static Object obtenerCampoNombreArchivo(TipoArchivoPilaEnum tipoArchivo, CamposNombreArchivoEnum campoSolicitado,
            String nombreArchivo) {

        logger.debug("Inicia obtenerCampoNombreArchivo(TipoArchivoPilaEnum, CamposNombreArchivoEnum, String)");

        Object result = null;
        String[] nombreSeparado = nombreArchivo.split("_");
        String[] fechaSeparada = null;
        try {
            if (TipoArchivoPilaEnum.ARCHIVO_OF.equals(tipoArchivo)) {
                switch (campoSolicitado) {
                    case FECHA_RECAUDO_OF:
                        fechaSeparada = nombreSeparado[0].split("-");

                        int anio = Integer.parseInt(fechaSeparada[0]);
                        int mes = Integer.parseInt(fechaSeparada[1]) - 1;
                        int dia = Integer.parseInt(fechaSeparada[2]);

                        result = new GregorianCalendar(anio, mes, dia);
                        break;
                    case CODIGO_ADMINISTRADORA_OF:
                        result = nombreSeparado[1];
                        break;
                    case CODIGO_BANCO_OF:
                        result = nombreSeparado[2];
                        break;
                    case TIPO_ARCHIVO_OF:
                        result = nombreSeparado[3];
                        break;
                    default:
                        break;
                }
            }
            else {
                // casos de Operador de información
                switch (campoSolicitado) {
                    case FECHA_PAGO_OI:
                        fechaSeparada = nombreSeparado[0].split("-");

                        int anio = Integer.parseInt(fechaSeparada[0]);
                        int mes = Integer.parseInt(fechaSeparada[1]) - 1;
                        int dia = Integer.parseInt(fechaSeparada[2]);

                        result = new GregorianCalendar(anio, mes, dia);
                        break;
                    case MODALIDAD_PLANILLA_OI:
                        result = nombreSeparado[1];
                        break;
                    case NUMERO_PLANILLA_OI:
                        result = Long.parseLong(nombreSeparado[2]);
                        break;
                    case TIPO_DOCUMENTO_APORTANTE_OI:
                        result = nombreSeparado[3];
                        break;
                    case IDENTIFICACION_APORTANTE_OI:
                        result = nombreSeparado[4];
                        break;
                    case CODIGO_ADMINISTRADORA_OI:
                        result = nombreSeparado[5];
                        break;
                    case CODIGO_OPERADOR_OI:
                        result = nombreSeparado[6];
                        break;
                    case TIPO_ARCHIVO_OI:
                        result = nombreSeparado[7];
                        break;
                    case PERIODO_PAGO_OI:
                        result = nombreSeparado[8].split("\\.")[0];
                        break;
                    default:
                        break;
                }
            }
        } catch (Exception e) {
            result = null;
        }

        logger.debug("Finaliza obtenerCampoNombreArchivo(TipoArchivoPilaEnum, CamposNombreArchivoEnum, String)");
        return result;
    }
}
