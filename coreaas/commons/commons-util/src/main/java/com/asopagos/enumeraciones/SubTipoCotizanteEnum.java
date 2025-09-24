package com.asopagos.enumeraciones;

/**
 * <b>Descripcion:</b> Enumeración en la que se listan los subtipos de cotizante definidos en el 
 * Decreto 2388 de 2016 <br/>
 * <b>Módulo:</b> ArchivosPILAService - HU 391<br/>
 *
 * @author  <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */

public enum SubTipoCotizanteEnum {
    SUBTIPO_00 (0, "No aplica un subtipo de cotizante"),
    SUBTIPO_01 (1, "Dependiente pensionado por vejez, jubilación o invalidez activo"),
    SUBTIPO_02 (2, "Independiente pensionado por vejez, jubilación o invalidez activo"),
    SUBTIPO_03 (3, "Cotizante no obligado a cotización a pensiones por edad"),
    SUBTIPO_04 (4, "Cotizante con requisitos cumplidos para pensión"),
    SUBTIPO_05 (5, "Cotizante a quien se le ha reconocido indemnización sustitutiva o devolución de saldos"),
    SUBTIPO_06 (6, "Cotizante perteneciente a un régimen de exceptuado de pensiones a entidades autorizadas para recibir aportes "
            + "exclusivamente de un grupo de sus propios trabajadores"),
    SUBTIPO_09 (9, "Cotizante pensionado con mesada superior a 25 SMLMV"),
    SUBTIPO_10 (10, "Residente en el exterior afiliado voluntario al Sistema General de Pensiones y/o afiliado facultativo al sistema "
            + "de Subsidio Familiar"),
    SUBTIPO_11 (11, "Conductores del servicio público de transporte terrestre individual de pasajeros en vehículo taxi"),
    SUBTIPO_12 (12, "Conductores del servicio público de transporte automotor individual de pasajeros en vehículos taxi. No obligado a "
            + "cotizar pensión");

    
    /** Código */
    private Integer codigo;
    
    /** Descripción del tipo */
    private String descripcion;
    
    /** Constructor de la enumeración */
    private SubTipoCotizanteEnum (Integer codigo, String descripcion){
        this.codigo = codigo;
        this.descripcion = descripcion;
    }
    
    /**
     * Método para obtener una entrada de enumeración de subtipo de entidad a partir de un código
     * 
     * @param codigo
     *        Código consultado
     * @return <b>SubTipoCotizanteEnum</b>
     *         Entrada de enumeración correspondiente al código ingresado
     * */
    public static SubTipoCotizanteEnum obtenerSubTipoCotizante(Integer codigo){
        for (SubTipoCotizanteEnum tipo : SubTipoCotizanteEnum.values()) {
            if(tipo.getCodigo().compareTo(codigo) == 0){
                return tipo;
            }
        }
        return null;
    }

    /**
     * @return the codigo
     */
    public Integer getCodigo() {
        return codigo;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }
}
