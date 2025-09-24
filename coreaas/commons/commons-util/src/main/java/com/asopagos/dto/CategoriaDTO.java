package com.asopagos.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.entidades.ccf.personas.Categoria;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.personas.CategoriaPersonaEnum;
import com.asopagos.enumeraciones.personas.MotivoCambioCategoriaEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoBeneficiarioEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * <b>Descripcion:</b> DTO que representa la categoria de un afiliado o un beneficiario <br/>
 * <b>Módulo:</b> Asopagos - HU - TRA-526<br/>
 *
 * @author  <a href="mailto:clmarin@heinsohn.com.co"> clmarin</a>
 */

@XmlRootElement
public class CategoriaDTO implements Serializable{
    
    private Long idCategoria;
    
    private TipoIdentificacionEnum tipoIdentificacion;
    
    private String numeroIdentificacion;

    private MotivoCambioCategoriaEnum motivoCambioCategoria;
    
    private TipoAfiliadoEnum tipoAfiliado;

    private CategoriaPersonaEnum categoriaPersona;

    private TipoBeneficiarioEnum tipoBeneficiario;

    private ClasificacionEnum clasificacionAfiliado;

    private BigDecimal totalIngresoMesada;

    private Date fechaCambioCategoria;

    private Boolean afiliadoPrincipal;
    
    private Long idAfiliado;

    private Long idBeneficiario;

    private CategoriaPersonaEnum tarifUVTPersona;
    
    

 
	

	public CategoriaDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CategoriaDTO(CategoriaDTO cat) {
		super();
		this.idCategoria = cat.getIdCategoria();
		this.tipoIdentificacion = cat.getTipoIdentificacion();
		this.numeroIdentificacion = cat.getNumeroIdentificacion();
		this.motivoCambioCategoria = cat.getMotivoCambioCategoria();
		this.tipoAfiliado = cat.getTipoAfiliado();
		this.categoriaPersona = cat.getCategoriaPersona();
		this.tipoBeneficiario = cat.getTipoBeneficiario();
		this.clasificacionAfiliado = cat.getClasificacionAfiliado();
		this.totalIngresoMesada = cat.getTotalIngresoMesada();
		this.fechaCambioCategoria = cat.getFechaCambioCategoria();
		this.afiliadoPrincipal = cat.getAfiliadoPrincipal();
		this.idAfiliado = cat.getIdAfiliado();
		this.idBeneficiario = cat.getIdBeneficiario();
        this.tarifUVTPersona = cat.getTarifUVTPersona();
	}

	public static CategoriaDTO convertCategoriaToDTO(Categoria categoria) {
        CategoriaDTO categoriaDTO = new CategoriaDTO();
        categoriaDTO.setIdCategoria(categoria.getIdCategoria());
        categoriaDTO.setTipoAfiliado(categoria.getTipoAfiliado());
        categoriaDTO.setCategoriaPersona(categoria.getCategoriaPersona());
        categoriaDTO.setTipoBeneficiario(categoria.getTipoBeneficiario());
        categoriaDTO.setClasificacionAfiliado(categoria.getClasificacionAfiliado());
        categoriaDTO.setTotalIngresoMesada(categoria.getTotalIngresoMesada());
        categoriaDTO.setFechaCambioCategoria(categoria.getFechaCambioCategoria());
        categoriaDTO.setMotivoCambioCategoria(categoria.getMotivoCambioCategoria());
        categoriaDTO.setAfiliadoPrincipal(categoria.getAfiliadoPrincipal());
        categoriaDTO.setIdAfiliado(categoria.getIdAfiliado());
        categoriaDTO.setIdBeneficiario(categoria.getIdBeneficiario());
        categoriaDTO.setTarifUVTPersona(categoria.getTarifUVTPersona());
        return categoriaDTO;
    }
    
    public static Categoria convertCategoriaDTO(CategoriaDTO categoriaDTO){
        Categoria categoria = new Categoria();
        categoria.setIdCategoria(categoriaDTO.getIdCategoria());
        categoria.setTipoAfiliado(categoriaDTO.getTipoAfiliado());
        categoria.setCategoriaPersona(categoriaDTO.getCategoriaPersona());
        categoria.setTipoBeneficiario(categoriaDTO.getTipoBeneficiario());
        categoria.setClasificacionAfiliado(categoriaDTO.getClasificacionAfiliado());
        categoria.setTotalIngresoMesada(categoriaDTO.getTotalIngresoMesada());
        categoria.setFechaCambioCategoria(categoriaDTO.getFechaCambioCategoria());
        categoria.setMotivoCambioCategoria(categoriaDTO.getMotivoCambioCategoria());
        categoria.setAfiliadoPrincipal(categoriaDTO.getAfiliadoPrincipal());
        categoria.setIdAfiliado(categoriaDTO.getIdAfiliado());
        categoria.setIdBeneficiario(categoriaDTO.getIdBeneficiario());
        categoria.setTarifUVTPersona(categoriaDTO.getTarifUVTPersona());
        return categoria;
    }
    
    /**
     * @return the idCategoria
     */
    public Long getIdCategoria() {
        return idCategoria;
    }

    /**
     * @param idCategoria the idCategoria to set
     */
    public void setIdCategoria(Long idCategoria) {
        this.idCategoria = idCategoria;
    }

    /**
     * @return the tipoIdentificacion
     */
    public TipoIdentificacionEnum getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    /**
     * @param tipoIdentificacion the tipoIdentificacion to set
     */
    public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    /**
     * @return the numeroIdentificacion
     */
    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    /**
     * @param numeroIdentificacion the numeroIdentificacion to set
     */
    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    /**
     * @return the motivoCambioCategoria
     */
    public MotivoCambioCategoriaEnum getMotivoCambioCategoria() {
        return motivoCambioCategoria;
    }

    /**
     * @param motivoCambioCategoria the motivoCambioCategoria to set
     */
    public void setMotivoCambioCategoria(MotivoCambioCategoriaEnum motivoCambioCategoria) {
        this.motivoCambioCategoria = motivoCambioCategoria;
    }

    /**
     * @return the tipoAfiliado
     */
    public TipoAfiliadoEnum getTipoAfiliado() {
        return tipoAfiliado;
    }

    /**
     * @param tipoAfiliado the tipoAfiliado to set
     */
    public void setTipoAfiliado(TipoAfiliadoEnum tipoAfiliado) {
        this.tipoAfiliado = tipoAfiliado;
    }

    /**
     * @return the categoriaPersona
     */
    public CategoriaPersonaEnum getCategoriaPersona() {
        return categoriaPersona;
    }

    /**
     * @param categoriaPersona the categoriaPersona to set
     */
    public void setCategoriaPersona(CategoriaPersonaEnum categoriaPersona) {
        this.categoriaPersona = categoriaPersona;
    }

    /**
     * @return the tipoBeneficiario
     */
    public TipoBeneficiarioEnum getTipoBeneficiario() {
        return tipoBeneficiario;
    }

    /**
     * @param tipoBeneficiario the tipoBeneficiario to set
     */
    public void setTipoBeneficiario(TipoBeneficiarioEnum tipoBeneficiario) {
        this.tipoBeneficiario = tipoBeneficiario;
    }

    /**
     * @return the clasificacionAfiliado
     */
    public ClasificacionEnum getClasificacionAfiliado() {
        return clasificacionAfiliado;
    }

    /**
     * @param clasificacionAfiliado the clasificacionAfiliado to set
     */
    public void setClasificacionAfiliado(ClasificacionEnum clasificacionAfiliado) {
        this.clasificacionAfiliado = clasificacionAfiliado;
    }

    /**
     * @return the totalIngresoMesada
     */
    public BigDecimal getTotalIngresoMesada() {
        return totalIngresoMesada;
    }

    /**
     * @param totalIngresoMesada the totalIngresoMesada to set
     */
    public void setTotalIngresoMesada(BigDecimal totalIngresoMesada) {
        this.totalIngresoMesada = totalIngresoMesada;
    }

    /**
     * @return the fechaCambioCategoria
     */
    public Date getFechaCambioCategoria() {
        return fechaCambioCategoria;
    }

    /**
     * @param fechaCambioCategoria the fechaCambioCategoria to set
     */
    public void setFechaCambioCategoria(Date fechaCambioCategoria) {
        this.fechaCambioCategoria = fechaCambioCategoria;
    }

    /**
     * @return the afiliadoPrincipal
     */
    public Boolean getAfiliadoPrincipal() {
        return afiliadoPrincipal;
    }

    /**
     * @param afiliadoPrincipal the afiliadoPrincipal to set
     */
    public void setAfiliadoPrincipal(Boolean afiliadoPrincipal) {
        this.afiliadoPrincipal = afiliadoPrincipal;
    }

    /**
     * @return the idAfiliado
     */
    public Long getIdAfiliado() {
        return idAfiliado;
    }

    /**
     * @param idAfiliado the idAfiliado to set
     */
    public void setIdAfiliado(Long idAfiliado) {
        this.idAfiliado = idAfiliado;
    }

    /**
     * @return the idBeneficiario
     */
    public Long getIdBeneficiario() {
        return idBeneficiario;
    }

    /**
     * @param idBeneficiario the idBeneficiario to set
     */
    public void setIdBeneficiario(Long idBeneficiario) {
        this.idBeneficiario = idBeneficiario;
    }   

     /**
     * @return the tarifUVTPersona
     */
    public CategoriaPersonaEnum getTarifUVTPersona() {
        return this.tarifUVTPersona;
    }

    /**
     * @param tarifUVTPersona the tarifUVTPersona to set
     */
    public void setTarifUVTPersona(CategoriaPersonaEnum tarifUVTPersona) {
        this.tarifUVTPersona = tarifUVTPersona;
    }

}
