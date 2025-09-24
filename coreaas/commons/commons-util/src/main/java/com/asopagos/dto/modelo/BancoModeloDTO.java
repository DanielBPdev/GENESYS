package com.asopagos.dto.modelo;

import java.io.Serializable;
import com.asopagos.entidades.ccf.core.Banco;

/**
 * DTO con los datos de un que representa a un Operador Financiero
 *
 * @author Ricardo Hernandez Cediel <hhernandez@heinsohn.com.co>
 *
 */
public class BancoModeloDTO implements Serializable {

    /**
     * Código identificador de llave primaria del banco u operador financiero
     */
    private Long id;

    /**
     * Código del banco según la Superintendencia Financiera reportado
     * en archivo PILA OF
     */
    private String codigoPILA;

    /**
     * Nombre del banco
     */
    private String nombre;

    /**
     * Indicador para saber sí el banco se usa para medio de pago
     */
    private Boolean medioPago;

    /**
     * Código del banco 
     */
    private String codigo;
    /**
     * NIT del banco
     */
    private String nit;


    /**
     * @param id
     * @param codigoPILA
     * @param nombre
     */
    public BancoModeloDTO(Long id, String codigoPILA, String nombre) {
        super();
        this.id = id;
        this.codigoPILA = codigoPILA;
        this.nombre = nombre;
    }

    /**
     * @param id
     * @param codigoPILA
     * @param nombre
     * @param codigo
     */
    public BancoModeloDTO(Long id, String codigoPILA, String nombre, String codigo) {
        super();
        this.id = id;
        this.codigoPILA = codigoPILA;
        this.nombre = nombre;
        this.codigo = codigo;
    }

    /**
     * Constructor
     */
    public BancoModeloDTO() {
        super();
    }

    /**
     * Método encargado de convertir de DTO a Entidad.
     * @return entidad convertida.
     */
    public Banco convertToEntity() {
        Banco banco = new Banco();
        banco.setId(this.getId());
        banco.setCodigoPILA(this.getCodigoPILA());
        banco.setNombre(this.getNombre());
        banco.setMedioPago(this.getMedioPago());
        banco.setCodigo(this.getCodigo());
        if(this.getNit() != null){
            banco.setNit(this.getNit());
        }
        return banco;
    }

    /**
     * Método encargado de convertir de Entidad a DTO.
     * @param registroDetallado
     *        entidad a convertir.
     */
    public void convertToDTO(Banco banco) {
        this.setId(banco.getId());
        this.setCodigoPILA(banco.getCodigoPILA());
        this.setNombre(banco.getNombre());
        this.setMedioPago(banco.getMedioPago());
        this.setCodigo(banco.getCodigo());
        if(banco.getNit() != null){
            this.setNit(banco.getNit());
        }
    }

    /**
     * Método encargado de copiar un DTO a una Entidad.
     * @param banco
     *        entidad previamente consultado.
     * @return Banco
     *         modificado con los datos del DTO.
     */
    public Banco copyDTOToEntiy(Banco banco) {
        if (this.getId() != null) {
            banco.setId(this.getId());
        }
        if (this.getCodigoPILA() != null) {
            banco.setCodigoPILA(this.getCodigoPILA());
        }
        if (this.getNombre() != null) {
            banco.setNombre(this.getNombre());
        }
        if (this.getMedioPago() != null) {
            banco.setMedioPago(this.getMedioPago());
        }
        if (this.getCodigo() != null) {
            banco.setCodigo(this.getCodigo());
        }
        return banco;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     *        the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the codigoPILA
     */
    public String getCodigoPILA() {
        return codigoPILA;
    }

    /**
     * @param codigoPILA
     *        the codigoPILA to set
     */
    public void setCodigoPILA(String codigoPILA) {
        this.codigoPILA = codigoPILA;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre
     *        the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the medioPago
     */
    public Boolean getMedioPago() {
        return medioPago;
    }

    /**
     * @param medioPago
     *        the medioPago to set
     */
    public void setMedioPago(Boolean medioPago) {
        this.medioPago = medioPago;
    }

    /**
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @param codigo
     *        the codigo to set
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    @Override
    public String toString() {
        return "BancoModeloDTO{" +
                "id=" + id +
                ", codigoPILA='" + codigoPILA + '\'' +
                ", nombre='" + nombre + '\'' +
                ", medioPago=" + medioPago +
                ", codigo='" + codigo + '\'' +
                ", nit='" + nit + '\'' +
                '}';
    }
}
