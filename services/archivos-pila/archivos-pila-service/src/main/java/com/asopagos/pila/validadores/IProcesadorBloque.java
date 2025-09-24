package com.asopagos.pila.validadores;

import java.util.Map;

public interface IProcesadorBloque {

    
    public static final String VERSION_CLIENT_SEPARATOR = "_";
    
    public static final String RESULTADO_VALIDACION = "Resultado de validación";

    public static final String ESTADO_ARCHIVO = "Estado respuesta Validación";

    public static final String ESTADO_PAREJA = "Estado comparación pareja archivos";

    public static final String INICIAR_VALIDACION = "Iniciar Bloque Validación";
    
    public static final String CONTEXTO = "contextoValidacion";

    public static final String DTO_COMPONENTE = "DTO componente lectura";

    public static final String ACCION_ARCHIVO = "Accion respuesta Validación";

    public static final String ESTADO_ARCHIVO_B6 = "Estado respuesta Validación para el B6 OF";

    public static final String USUARIO_NO_IDENTIFICADO = "USUARIO_NO_IDENTIFICADO";

    public static final String SECUENCIA_LOG_ERRORES = "Sec_PilaErrorValidacionLog";

    public static final String INDICES_OI_RELACIONADOS_OF = "indices_OI_OF";
    
    public static final String BLOQUE_SIGUIENTE = "bloqueSiguiente";
    
    
    
    public static final String INDICE_PLANILLA_KEY    = "indicePlanilla";
    public static final String ARCHIVO_PILA_KEY       = "archivoPILA";
    public static final String USUARIO_KEY            = "usuario";
    public static final String ID_DOCUMENTO_KEY       = "idDocumentoParam";
    public static final String RESPUESTA_GENERAL_KEY  = "respuestaGeneral";
    public static final String LISTA_INDICES_OI_KEY   = "indicesOI";
    public static final String VALIDA_BLOQUE_CERO_KEY = "validaBloqueCero";
    
    
    public void procesar(Map<String, Object> parametros, Integer intento);
    
}
