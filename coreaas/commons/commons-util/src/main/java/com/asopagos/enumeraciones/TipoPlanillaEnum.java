package com.asopagos.enumeraciones;

/**
 * <b>Descripcion:</b> Enumeración en la que se listan los tipos de planilla definidos en el 
 * Decreto 2388 de 2016 <br/>
 * <b>Módulo:</b> ArchivosPILAService - HU 391<br/>
 *
 * @author  <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */

public enum TipoPlanillaEnum {
    EMPLEADOS ("E", "Planillas empleados", false, false),
    INDEPENDIENTES_EMPRESAS ("Y", "Planillas independientes empresas", false, false),
    COTIZANTES_NOVEDAD_INGRESO ("A", "Planilla cotizantes con novedad de ingreso", false, false),
    INDEPENDIENTES ("I", "Planilla independientes", false, false),
    SERVICIO_DOMESTICO ("S", "Planilla servicio doméstico", false, false),
    MORA ("M", "Planilla de mora", true, false),
    CORRECIONES ("N", "Planilla de correcciones", true, true),
    MADRES_SUSTITUTAS ("H", "Planilla madres sustitutas", false, false),
    EMPLEADOS_BENEFICIARIOS_SGP ("T", "Planilla empleados entidad beneficiaria del sistema general de participaciones", false, false),
    APORTE_FALTANTE_SGP ("F", "Planilla pago de aporte faltante de una entidad beneficiaria del SGP", false, false),
    SENTENCIA_JUDICIAL ("J", "Planilla para pago de seguridad social en cumplimiento de sentencia judicial", true, true),
    EMPRESA_EN_PROCESO_LIQUIDACION ("X", "Planilla para pago de empresas en proceso de liquidación, reestructuración o en procesos concursales", true, false),
    PAGO_TERCEROS_UGPP ("U", "Planilla de uso exclusivo de la UGPP para pago por terceros", true, true),
    ESTUDIANTES ("K", "Planilla estudiantes", false, false),
    OBLIGACIONES ("O", "Planilla Obligaciones Determinadas por la UGPP", true, true),
	ACUERDOS ("Q", "Planilla Acuerdos de pago realizados por la UGPP", true, true),
    PISO_PROTECCION_SOCIAL("B","Planilla Piso de Protección Social",false, false),
    CONTRIBUCION_SOLIDARIA("D","Planilla Contribución Solidaria",false, false),
    PLANILLA_PARA_PAGO_DEL_CALCULO_ACTUARIAL_POR_OMISION_EN_PENSIONES("Z","Planilla para pago de cálculo actuarial por omisión en pensiones",false, false); 

    
    
    /** Código */
    private final String codigo;
    
    /** Descripción del tipo */
    private final String descripcion;
    
    /** Indica que el tipo de planilla es de corrección */
    private final Boolean esCorreccion;
    
    /** Indica que la planilla debe diligenciar el campo de fecha planilla asociada */
    private final Boolean aplicaFechaPagoAsociada;
    
    /** Constructor de la enumeración */
    private TipoPlanillaEnum (String codigo, String descripcion, Boolean esCorreccion, Boolean aplicaFechaPagoAsociada){
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.esCorreccion = esCorreccion;
        this.aplicaFechaPagoAsociada = aplicaFechaPagoAsociada;
    }
    
    /** 
     * Método para obtener un valor de enumeración a partir de su código
     * 
     * @param codigo
     *        Código de tipo de planilla buscado
     * @return <b>TipoPlanillaEnum</b>
     *         Valor de enumeración que coincide con el código buscado
     */
    public static TipoPlanillaEnum obtenerTipoPlanilla(String codigo){
        for (TipoPlanillaEnum tipo : TipoPlanillaEnum.values()) {
            if(tipo.getCodigo().equals(codigo)){
                return tipo;
            }
        }
        return null;
    }

    /**
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @return the esCorreccion
     */
    public Boolean esCorreccion() {
        return esCorreccion;
    }

    /**
     * @return the aplicaFechaPagoAsociada
     */
    public Boolean aplicaFechaPagoAsociada() {
        return aplicaFechaPagoAsociada;
    }
}