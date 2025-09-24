package com.asopagos.afiliaciones.personas.web.composite.enums;

/**
 * <b>Descripción:</b> Enum con los posibles resultados de los servicios de composición del
 * proceso de afiliación de personas WEB
 *
 * @author Luis Arturo Zarate Ayala <a href="mailto:lzarate@heinsohn.com.co"> lzarate@heinsohn.com.co</a>
 */
public enum ResultadoRegistroInformacionContactoEnum {

    AFILIADO_ACTIVO(1, "El afiliado se encuentra activo"),

    REINTEGRO_WEB_ACTIVA(2, "Persona con opción de reintegro y cuenta web activa"),

    REINTEGRO_SIN_WEB_ACTIVA(3, "Persona con opción de reintegro sin cuenta web activa"),

    NUEVA_AFILIACION(4, "Nueva afiliación de la persona"),

    NO_EXITOSA(5, "Validaciones para afiliación de la persona, no exitosas"),

    AFILIACION_EN_PROCESO(6, "El afiliado tiene una solicitud en proceso"),

    AFILIACION_EN_PROCESO_CORREO(7, "El afiliado tiene una solicitud en proceso con correo"),

    TIPO_IDENTIFICACION_DIFERENTE(8, "La persona cambió de tipo identificación"),

    DATOS_IDENTIFICACION_NO_CORRESPONDE(9, "Los datos de identificación de la persona no corresponden en el sistema");
    
    private Integer identificador;
    private String descripcion;

    private ResultadoRegistroInformacionContactoEnum(Integer identificador, String descripcion) {
        this.identificador = identificador;
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Integer getIdentificador() {
        return identificador;
    }
}
