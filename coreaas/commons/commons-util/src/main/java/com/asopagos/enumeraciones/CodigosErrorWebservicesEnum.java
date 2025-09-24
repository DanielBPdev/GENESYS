package com.asopagos.enumeraciones;

import java.lang.Long;

public enum CodigosErrorWebservicesEnum {

    PARAMETROS_INCORRECTOS(1L,"Parametros incorrectos para consumo del webservice."),

    NO_EXISTE_EMPLEADOR(2L,"No se encontró información del empleador con los parámetros proporcionados."),
    
    NO_EXISTE_AFILIADO(3L,"No se encontró información del afiliado con los parámetros proporcionados."),
    
    INTERNAL_SERVER_ERROR(4L,"Error interno del servidor."),

    NO_EXISTE_TARJETA(5L,"No se encontró información de la tarjeta con los parámetros proporcionados."),
    
    SIN_APORTES(6L,"No se encontraró información con los parámetros proporcionados."),

    TIPO_PERSONA_NO_ENCONTRADA(6L,"El tipo de persona no se encuentra registrado en el sistema."),
    
    MUNICIPIOS_NO_ENCONTRADOS(7L,"No se encontraron municipios con los parámetros proporcionados."),

    EMPRESA_NO_AFILIABLE(8L,"El proceso de afiliacion de la empresa no se ha podido completar"),

    BENEFICIARIO_NO_AFILIABLE(9L,"El proceso de novedad no se ha podido completar, pues incumple con las validaciones."),

    PAGOS_NO_ENCONTRADOS(10L,"No se encontraron pagos con los parámetros proporcionados."),
    
    EXITO_TOTAL(11L, "Proceso realizado exitosamente"),
    
    ERROR_VALIDACION(12L, "Error en validación de novedades"),
    
    ERROR_RADICACION(13L, "Error en la radicación de la solicitud"),
    
    PROCESO_PARCIAL(14L, "Proceso realizado con errores parciales"),

    INCONSISTENCIA_GENERAL(15L,"Existe una inconsistencia en los datos a procesar, por favor contacte al administrador"),

    EMPRESA_NO_ENCONTRADA(16L,"No se encontró información de la empresa con los parámetros proporcionados."),

    EMPRESA_NO_SE_ENCUENTRA_ACTIVA(17L,"La empresa no se encuentra activa."),
    
    USUARIO_NO_ENCONTRADO(18L,"No se encuentró usuario con los parametros recibidos."),
    
    USUARIO_INACTIVO(19L,"El usuario no se encuentra activo en Genesys."),
    
    TRABAJADOR_NO_AFILIABLE(20L,"El proceso de afiliacion no se ha podido completar, pues incumple con las validaciones."),

    NO_EXISTE_PROCESO(21L,"No se encontro ninguna solicitud asociada al numero de radicado proporcionado."),
    
    EMPLEADOR_NO_EXISTE(22L,"No se encuentra ningun empleador con los datos proporcionados."),

    PAGADOR_PENSION_NO_ENCONTRADO(22L,"No se encontró información del pagador de pensión con los parámetros proporcionados."),
    
    IDENTIFICACION_EXISTENTE_GENESYS(23L,"La identificación a la que intenta actualizar ya exite en Genesys."),

    REQUISITO_NO_PARAMETRIZADO(24L,"El requisito documental no esta parametrizado para este proceso.");
    
    private Long codigoError;
    
    private String mensajeError;

    private CodigosErrorWebservicesEnum(Long codigo,String mensaje){
        this.codigoError = codigo;
        this.mensajeError = mensaje;
    }

    public Long getCodigoError(){
        return this.codigoError;
    }

    public String getMensajeError(){
        return this.mensajeError;
    }
}
