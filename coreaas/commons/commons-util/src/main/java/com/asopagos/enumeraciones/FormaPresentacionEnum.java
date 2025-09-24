package com.asopagos.enumeraciones;

import javax.xml.bind.annotation.XmlEnum;

/**
 * <b>Descripción:</b> Enumeración con las posibles formas de presentación del aporte.
 * <b>Historia de Usuario: </b>HU-212-482 Gestionar solicitud pago manual aportes. 
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 */
@XmlEnum
public enum FormaPresentacionEnum {

	UNICO("U","Único"),
	SUCURSAL("S","Sucursal");

	/**
	 * Codigo
	 */
	private String codigo;
	/**
	 * Variable para la descripción de la enumeración.
	 */
    private String descripcion;

    /**
     * Constructor del la enumeración.
     * @param codigo de la enumeración.
     * @param descripcion de la enumeración.
     */
    private FormaPresentacionEnum(String codigo,String descripcion) {
        this.codigo = codigo; 
        this.descripcion = descripcion;
    }

	/**
     * Método que retorna el valor de codigo.
     * @return valor de codigo.
     */
    public String getCodigo() {
        return codigo;
    }
    /**
	 * Método que retorna el valor de descripcion.
	 * @return valor de descripcion.
	 */
	public String getDescripcion() {
		return descripcion;
	}
}