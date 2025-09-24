package com.asopagos.usuarios.constants;

/**
 * <b>Descripción:</b> Clase que contiene las constantes con los nombres de los
 * NamedQueries del servicio
 * <b>Historia de Usuario:</b> Transversal
 * 
 * @author Luis Arturo Zarate Ayala <lzarate@heinsohn.com.co>
 */
public class NamedQueriesConstants {
    
    /**
     * constante que contiene la consulta de preguntas por estado 
     */
    public static final String PREGUNTA_CONSULTAR_PREGUNTAS_ESTADO = "Pregunta.consultarPreguntasEstado";
    
    /**
     * constante que contiene la consulta de preguntas en general 
     */
    public static final String PREGUNTA_CONSULTAR_PREGUNTA = "Pregunta.consultarPregunta";


    /**
     * GLPI 82800 Consultar Empleador Razon Social 
     */
    public static final String CONSULTAR_RAZON_SOCIAL_EMPLEADOR_POR_NUMERO_IDENTIFICACION = "Consultar.razon.social.empleador.por.numero.documento";

    /**
     * GLPI 82800 Consultar Datos Empleador 
     */
    public static final String CONSULTAR_DATOS_EMPLEADOR_POR_NUMERO_IDENTIFICACION = "Consultar.datos.empleador.por.numero.documento";

    public static final String CONSULTAR_DATOS_EMPLEADOR_POR_NUMERO_IDENTIFICACION_Y_TIPO_IDENTIFICACION = "Consultar.datos.empleador.por.numero.documento.y.tipo.documento";

    /**
     * GLPI 82800 Actualizar Email Empleador Razon Social 
     */
    public static final String ACTUALIZAR_EMPLEADOR_POR_NUMERO_IDENTIFICACION = "Actualizar.email.empleador.por.numero.documento";

    /**
     * GLPI 82800 Insertar Datos Auditoria al actualizar Empleador
     */
    public static final String INSERTAR_DATOS_AUDITORIA_ACTUALIZAR_USUARIO_EMPLEADOR = "Insertar.datos.auditoria.actualizar.usuario.empleador";

    /**
     * GLPI 82800 Consultar Datos Persona 
     */
    public static final String CONSULTAR_DATOS_PERSONA_POR_NUMERO_IDENTIFICACION = "Consultar.datos.persona.por.numero.documento";

    public static final String CONSULTAR_DATOS_PERSONA_POR_NUMERO_IDENTIFICACION_Y_TIPO_IDENTIFICACION = "Consultar.datos.persona.por.numero.documento.y.tipo.documento";

    public static final String CONSULTAR_DATOS_PERSONA_POR_NOMBRES_APELLIDOS = "Consultar.datos.persona.por.nombres.apellidos";

    /**
     * GLPI 82800 Actualizar Email Persona 
     */
    public static final String ACTUALIZAR_PERSONA_POR_NUMERO_IDENTIFICACION = "Actualizar.email.persona.por.numero.documento";

    /**
     * GLPI 82800 Insertar Datos Auditoria al actualizar Persona
     */
    public static final String INSERTAR_DATOS_AUDITORIA_ACTUALIZAR_USUARIO_PERSONA = "Insertar.datos.auditoria.actualizar.usuario.persona";

    public static final String INSERTAR_DATOS_AUDITORIA_ACTUALIZAR_USUARIO_CCF = "Insertar.datos.auditoria.actualizar.usuario.ccf";

    /**
     * GLPI 82800 Consultar Datos Tercero 
     */
    public static final String CONSULTAR_DATOS_TERCERO_POR_NOMBRE_CONVENIO = "Consultar.datos.tercero.por.nombre.convenio";

    public static final String CONSULTAR_DATOS_TERCERO_POR_NOMBRE_EMAIL = "Consultar.datos.tercero.por.nombre.email";

    /**
     * GLPI 82800 Insertar Datos Auditoria al actualizar Persona
     */
    public static final String INSERTAR_DATOS_AUDITORIA_ACTUALIZAR_USUARIO_TERCERO = "Insertar.datos.auditoria.actualizar.usuario.tercero";

    /**
     * GLPI 82800 Actualizar Email Tercero Nombre Convenio
     */
    public static final String ACTUALIZAR_TERCERO_POR_NOMBRE_CONVENIO = "Actualizar.email.tercero.por.nombre.convenio";

    public static final String ACTUALIZAR_TERCERO_POR_NOMBRE_EMAIL = "Actualizar.email.tercero.por.nombre.email";

    /**
     * GLPI 82800 Control de Actividad Usuario en Keycloak
     */
    public static final String CONTAR_ACTIVIDAD_SESION_USUARIO_KEYCLOAK = "Contar.actividad.sesion.usuario.keycloak";
    
    public static final String ACTUALIZAR_ACTIVIDAD_SESION_USUARIO_KEYCLOAK = "Actualizar.actividad.sesion.usuario.keycloak";

    public static final String INSERTAR_ACTIVIDAD_SESION_USUARIO_KEYCLOAK = "Insertar.actividad.sesion.usuario.keycloak";

    public static final String CONSULTAR_ACTIVIDAD_SESION_USUARIO_KEYCLOAK = "Consultar.actividad.sesion.usuario.keycloak";

    public static final String CONSULTAR_DIAS_FESTIVOS = "Consultar.dias.festivos";

}
