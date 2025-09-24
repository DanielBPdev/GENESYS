package com.asopagos.novedades.constants;

/**
 * 
 */

public class ArchivoCampoNovedadCambioTipoNumeroMasivoConstante {

    // Campos validadores Afiliado
    public static String TIPO_IDENTIFICACION_ACTUAL_AFILIADO = "tipoIdentificacionActualAfiliado";
    public static String NUMERO_IDENTIFICACION_ACTUAL_AFILIADO = "numeroIdentificacionActualAfiliado";
    public static String TIPO_IDENTIFICACION_NUEVO_AFILIADO = "tipoIdentificacionNuevoAfiliado";
    public static String NUMERO_IDENTIFICACION_NUEVO_AFILIADO = "numeroIdentificacionNuevoAfiliado";

    // Campos validadores Beneficiario
    public static String TIPO_IDENTIFICACION_ACTUAL_BENEFICIARIO = "tipoIdentificacionActualBeneficiario";
    public static String NUMERO_IDENTIFICACION_ACTUAL_BENEFICIARIO = "numeroIdentificacionActualBeneficiario";
    public static String TIPO_IDENTIFICACION_NUEVO_BENEFICIARIO = "tipoIdentificacionNuevoBeneficiario";
    public static String NUMERO_IDENTIFICACION_NUEVO_BENEFICIARIO = "numeroIdentificacionNuevoBeneficiario";

    //mensajes 
    public static final String DATOS_FALTANTES_MSG = "Faltan datos obligatorios en el registro";
    public static final String AFILIADO_EXISTE_OTRO_TIPO_MSG = "El afiliado principal existe con más de un tipo de afiliación en el sistema";
    public static final String BENEFICIARIO_EXISTE_OTRO_TIPO_MSG = "El beneficiario no existe únicamente como beneficiario en el sistema";
    public static final String REGISTRAR_TODOS_LOS_CAMPOS_MSG = "Debe registrar todos los campos: Tipo y Número de Identificación actual y nuevo para realizar el cambio";
    public static final String TIPO_NUMERO_ACTUAL_NO_EXISTE_MSG = "El tipo y número de identificación actual no existe";
    public static final String TIPO_NUMERO_NUEVO_COINCIDE_ACTUAL_MSG = "El nuevo tipo y número de identificación coinciden con los ya registrados. No se requiere cambio.";
    public static final String NUMERO_NUEVO_YA_EXISTE_CON_OTRO_TIPO_MSG = "El nuevo número de identificación ya existe con otro tipo de identificación";
    public static final String TIENE_UNA_SOLICITUD_MSG = "Ya tiene una solicitud en proceso";
    public static final String TIENE_UNA_SOLICITUD_FALLECIMIENTO_MSG = "Tiene una solicitud de fallecimiento en proceso";
    public static final String CAMBIO_TI_A_RC_MSG = "No se puede cambiar el tipo de documento de TI a RC";
    public static final String CAMBIO_CC_A_RC_MSG = "No se puede cambiar el tipo de documento de CC a RC";
    public static final String CAMBIO_CC_A_TI_MSG = "No se puede cambiar el tipo de documento de CC a TI";
    public static final String AFILIADO_FALLECIDO_MSG = "El afiliado se encuentra con estado fallecido en el sistema";
    public static final String BENEFICIARIO_FALLECIDO_MSG = "El beneficiario se encuentra con estado fallecido en el sistema";

    //mensajes numero de documento
    public static final String LONGITUD_NUMERO_CC_MSG = "El Cédula de Ciudadanía debe tener máximo 10 dígitos";
    public static final String LONGITUD_NUMERO_CE_MSG = "El Cédula de Extranjería debe tener máximo 16 dígitos";
    public static final String LONGITUD_NUMERO_PA_MSG = "El Pasaporte debe tener máximo 16 caracteres alfanuméricos";
    public static final String LONGITUD_NUMERO_CD_MSG = "El Carné Diplomático debe tener máximo 15 caracteres alfanumericos";
    public static final String LONGITUD_NUMERO_PE_MSG = "El Permiso Especial de Permanencia debe tener máximo 15 caracteres alfanumericos";
    public static final String LONGITUD_NUMERO_SC_MSG = "El Salvoconducto de Permanencia debe tener máximo 16 caracteres alfanuméricos";
    public static final String LONGITUD_NUMERO_PT_MSG = "El Permiso por Proteccón Temporal debe tener máximo 8 dígitos";
    public static final String LONGITUD_NUMERO_RC_MSG = "El Registro Civil debe tener máximo 8, 10 o 11 dígitos";
    public static final String LONGITUD_NUMERO_TI_MSG = "El Tarjeta de Identidad debe tener máximo 10 u 11 dígitos";
     
}
