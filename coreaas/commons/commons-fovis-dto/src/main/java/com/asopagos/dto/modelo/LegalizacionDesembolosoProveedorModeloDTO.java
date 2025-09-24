package com.asopagos.dto.modelo;

import java.io.Serializable;
import com.asopagos.entidades.ccf.fovis.Proveedor;
import com.asopagos.entidades.ccf.fovis.LegalizacionDesembolosoProveedor;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <b>Descripción: </b> DTO que representa la información de un registro de la
 * tabla <code>Proveedor</code> <br/>
 * @author linam
 */

/**
 *
 * @author linam
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class LegalizacionDesembolosoProveedorModeloDTO implements Serializable {

    /**
     * Serial
     */
    private static final long serialVersionUID = -2895211143685611451L;

    /**
     * Identificador único, llave primaria
     */
    private Long idlegalizacionDesembolosoProveedor;

    /**
     * Asociación de la Empresa con el Oferente
     */
    private Long idProveedor;

    /**
     * Asociación de la Persona con el Oferente
     */
    private Long sldId;


    /**
     * Identificador del Banco asociado al Medio Transferencia.
     */
    private Long idPersona;


    /**
     * Numero de Cuenta asociada al Medio Transferencia.
     */
    private String numeroRadicacion;
    
    
    /**
     * Tipo Identificación del Titular de la Cuenta
     */
    private BigDecimal valorADesembolsar;

       /**
     * Identificador del Banco asociado al Medio Transferencia.
     */
    private Double porcentajeASembolsar;
    
    /**
     * Fecha limite de pago del desembolso
     */
    private Date fecha;
    
    
        /**
     * Asociación del proveedor
     */
    private ProveedorModeloDTO proveedor;
    
   
   /**
     * Asociación del porcentajePago
     */
    private Double porcentajePago;
    
   
   /**
     * Asociación del porcentajePago
     */
    private BigDecimal valorADesembolsarPago;
    
    
    /**
     * Asociación del metodo de pago
     */
    private String metodoPagoDesembolso;
    
    
    
        /**
     * Constructor especifico
     * @param proveedor
     *        Informacion oferente
     * @param legalizacionDesembolosoProveedor
     *        Informacion LegalizacionDesembolosoProveedor
     */
    public LegalizacionDesembolosoProveedorModeloDTO(LegalizacionDesembolosoProveedor legalizacionDesembolosoProveedor, Proveedor proveedor) {
        super();
        if (legalizacionDesembolosoProveedor == null) {
            legalizacionDesembolosoProveedor = new LegalizacionDesembolosoProveedor();
        }
        convertToDTO(legalizacionDesembolosoProveedor,proveedor);
    }

    
    /**
     * Constructor por defecto
     */
    public LegalizacionDesembolosoProveedorModeloDTO() {
        super();
    }
    
    
   /**
     * Convierte la información enviada por parametro en formato del DTO
     * @param proveedor
     *        Informacion proveedor
     * @param legalizacionDesembolosoProveedor
     */
    public void convertToDTO(LegalizacionDesembolosoProveedor legalizacionDesembolosoProveedor, Proveedor proveedor ) {
        this.setIdlegalizacionDesembolosoProveedor(legalizacionDesembolosoProveedor.getIdlegalizacionDesembolosoProveedor());
        this.setIdProveedor(legalizacionDesembolosoProveedor.getIdProveedor());
        this.setIdPersona(legalizacionDesembolosoProveedor.getIdPersona());
        this.setSldId(legalizacionDesembolosoProveedor.getSldId());
        this.setNumeroRadicacion(legalizacionDesembolosoProveedor.getNumeroRadicacion());
        this.setValorADesembolsar(legalizacionDesembolosoProveedor.getValorADesembolsar());
        this.setPorcentajeASembolsar(legalizacionDesembolosoProveedor.getPorcentajeASembolsar());
        this.setFecha(legalizacionDesembolosoProveedor.getFecha());
        this.setValorADesembolsarPago(legalizacionDesembolosoProveedor.getValorADesembolsarPago());
	this.setPorcentajePago(legalizacionDesembolosoProveedor.getPorcentajePago());
        this.setMetodoPagoDesembolso(legalizacionDesembolosoProveedor.getMetodoPagoDesembolso());
        // Info Persona
        ProveedorModeloDTO proveedorModeloDTO = null;
        if (proveedor != null) {
            proveedorModeloDTO = new ProveedorModeloDTO(proveedor,null,null);
        }
        else if (legalizacionDesembolosoProveedor.getIdProveedor()!= null) {
            proveedorModeloDTO = new ProveedorModeloDTO();
            proveedorModeloDTO.setIdOferente(proveedor.getIdPersona());
        }
        this.setProveedor(proveedorModeloDTO);  
    }
    
    
    /**
     * Método que convierte el DTO a una entidad <code>proveedor</code>
     * 
     * @return La entidad <code>proveedor</code> equivalente
     */
    public LegalizacionDesembolosoProveedor convertToEntity() {
        LegalizacionDesembolosoProveedor legalizacionDesembolosoProveedor = new LegalizacionDesembolosoProveedor();
        legalizacionDesembolosoProveedor.setIdlegalizacionDesembolosoProveedor(this.getIdlegalizacionDesembolosoProveedor());
        legalizacionDesembolosoProveedor.setIdPersona(this.getIdPersona());
        legalizacionDesembolosoProveedor.setSldId(this.getSldId());
        legalizacionDesembolosoProveedor.setNumeroRadicacion(this.getNumeroRadicacion());
        legalizacionDesembolosoProveedor.setValorADesembolsar(this.getValorADesembolsar());
        legalizacionDesembolosoProveedor.setPorcentajeASembolsar(this.getPorcentajeASembolsar());
        legalizacionDesembolosoProveedor.setFecha(this.getFecha());
        legalizacionDesembolosoProveedor.setIdProveedor(this.getIdProveedor());
	legalizacionDesembolosoProveedor.setValorADesembolsarPago(this.getValorADesembolsarPago());
	legalizacionDesembolosoProveedor.setPorcentajePago(this.getPorcentajePago());
        legalizacionDesembolosoProveedor.setMetodoPagoDesembolso(this.getMetodoPagoDesembolso());
        // Info PROVEEDOR
        //if (this.getProveedor()!= null) {
          //  legalizacionDesembolosoProveedor.setIdProveedor(this.getProveedor().getIdOferente());
       // }
        
        return legalizacionDesembolosoProveedor;
    }

    public Long getIdlegalizacionDesembolosoProveedor() {
        return idlegalizacionDesembolosoProveedor;
    }

    public void setIdlegalizacionDesembolosoProveedor(Long idlegalizacionDesembolosoProveedor) {
        this.idlegalizacionDesembolosoProveedor = idlegalizacionDesembolosoProveedor;
    }

    public Long getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(Long idProveedor) {
        this.idProveedor = idProveedor;
    }

    public Long getSldId() {
        return sldId;
    }

    public void setSldId(Long sldId) {
        this.sldId = sldId;
    }

    public Long getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Long idPersona) {
        this.idPersona = idPersona;
    }

    public String getNumeroRadicacion() {
        return numeroRadicacion;
    }

    public void setNumeroRadicacion(String numeroRadicacion) {
        this.numeroRadicacion = numeroRadicacion;
    }

    public BigDecimal getValorADesembolsar() {
        return valorADesembolsar;
    }

    public void setValorADesembolsar(BigDecimal valorADesembolsar) {
        this.valorADesembolsar = valorADesembolsar;
    }

    public Double getPorcentajeASembolsar() {
        return porcentajeASembolsar;
    }

    public void setPorcentajeASembolsar(Double porcentajeASembolsar) {
        this.porcentajeASembolsar = porcentajeASembolsar;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public ProveedorModeloDTO getProveedor() {
        return proveedor;
    }

    public void setProveedor(ProveedorModeloDTO proveedor) {
        this.proveedor = proveedor;
    }

    public Double getPorcentajePago() {
        return porcentajePago;
    }

    public void setPorcentajePago(Double porcentajePago) {
        this.porcentajePago = porcentajePago;
    }

    public BigDecimal getValorADesembolsarPago() {
        return valorADesembolsarPago;
    }

    public void setValorADesembolsarPago(BigDecimal valorADesembolsarPago) {
        this.valorADesembolsarPago = valorADesembolsarPago;
    }


    public String getMetodoPagoDesembolso() {
        return metodoPagoDesembolso;
    }

    public void setMetodoPagoDesembolso(String metodoPagoDesembolso) {
        this.metodoPagoDesembolso = metodoPagoDesembolso;
    }


    
}
