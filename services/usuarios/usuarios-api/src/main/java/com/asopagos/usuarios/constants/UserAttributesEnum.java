package com.asopagos.usuarios.constants;

public enum UserAttributesEnum {

    SEDE("sede"), DEPENDENCIA("dependencia"), EMAIL("email"), TELEFONO("telefono"), DEBE_CREAR_PREGUNTAS(
            "debeCrearPreguntas"), TIPO_IDENTIFICACION("tipoIdentificacion"), NUM_IDENTIFICACION("numIdentificacion"), TEMPORAL(
                    "temporal"), FECHA_INICIO_TEMPORAL("fechaInicio"), FECHA_FIN_TEMPORAL(
                            "fechaFin"), DEBE_ACEPTAR_TERMINOS("debeAceptarTerminos"), FECHA_FIN_CONTRATO(
                                    "fechaFinContrato"), SEGUNDO_NOMBRE("segundoNombre"), SEGUNDO_APELLIDO(
                                            "segundoApellido"), FECHA_INACTIVACION("fechaInactivacion"), CIUDAD_SEDE("ciudadSede"),
                                                FECHA_CREACION("fechaCreacion"), USUARIO_CREADO_POR("creadoPor"), FECHA_MODIFICACION("fechaModificacion"), USUARIO_MODIFICADO_POR("modificadoPor"),
                                                RAZON_SOCIAL("razonSocial"), NOMBRE_CONVENIO("nombreConvenio"), ID_CONVENIO("idConvenio"), MEDIO_PAGO("medioPago"), ESTADO_CONVENIO("estadoConvenio"), TIPO_USUARIO("tipoUsuario");

    /**
     * Nombre del atributo
     */
    private String nombre;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    private UserAttributesEnum(String nombre) {
        this.nombre = nombre;
    }

}
