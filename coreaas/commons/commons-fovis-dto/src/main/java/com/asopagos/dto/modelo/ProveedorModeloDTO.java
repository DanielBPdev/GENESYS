package com.asopagos.dto.modelo;

import java.io.Serializable;
import java.util.List;
import com.asopagos.entidades.ccf.fovis.Oferente;
import com.asopagos.entidades.ccf.fovis.Proveedor;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.entidades.ccf.personas.Empresa;
import com.asopagos.enumeraciones.fovis.EstadoOferenteEnum;
import com.asopagos.enumeraciones.personas.TipoCuentaEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * <b>Descripción: </b> DTO que representa la información de un registro de la
 * tabla <code>Proveedor</code> <br/>
 * @author linam
 */
public class ProveedorModeloDTO implements Serializable {

    /**
     * Serial
     */
    private static final long serialVersionUID = -2895211143685611451L;

    /**
     * Identificador único, llave primaria
     */
    private Long idOferente;

    /**
     * Asociación de la Empresa con el Oferente
     */
    private EmpresaModeloDTO empresa;

    /**
     * Asociación de la Persona con el Oferente
     */
    private PersonaModeloDTO persona;
    
    /**
     * Representante Legal asociado a la empresa.
     */
    private PersonaModeloDTO representanteLegal;

    /**
     * Entidad bancaria en donde se encuentra la cuenta
     */
    private BancoModeloDTO banco;

    /**
     * Tipo de cuenta asociada al Medio Transferencia
     */
    private TipoCuentaEnum tipoCuenta;

    /**
     * Numero de Cuenta asociada al Medio Transferencia.
     */
    private String numeroCuenta;
    /**
     * Tipo Identificación del Titular de la Cuenta
     */
    private TipoIdentificacionEnum tipoIdentificacionTitular;
    /**
     * Numero Identificación del Titular de la Cuenta
     */
    private String numeroIdentificacionTitular;

    /**
     * Nombre del Titular de la Cuenta
     */
    private String nombreTitularCuenta;

    /**
     * Numero Identificación del Titular de la Cuenta
     */
    private Short digitoVerificacionTitular;

    /**
     * Lista de documentos soporte que se asociaran al Oferente
     */
    private List<DocumentoSoporteModeloDTO> listaDocumentosSoporte;

    /**
     * Estado del oferente
     */
    private EstadoOferenteEnum estado;

    /**
     * Tiene cuenta bancaria el oferente
     */
    private Boolean cuentaBancaria;

    
    /**
     * Si corresponde a concepto
    */
    private long concepto;
    
    /**
     * Si corresponde a proveedor
     */
    private String tipoTable;


    /**
     * Constructor especifico
     * @param proveedor
     *        Informacion oferente
     * @param persona
     *        Informacion persona
     * @param empresa
     *        Informacion empresa
     */
    public ProveedorModeloDTO(Proveedor proveedor, Persona persona, Empresa empresa) {
        super();
        if (proveedor == null) {
            proveedor = new Proveedor();
        }
        convertToDTO(persona, empresa, proveedor);
    }

    /**
     * Constructor especifico
     * @param proveedor
     *        Informacion proveedor
     * @param persona
     *        Informacion persona
     */
    public ProveedorModeloDTO(Proveedor proveedor, Persona persona) {
        super();
        if (proveedor == null) {
            proveedor = new Proveedor();
        }
        convertToDTO(persona, null, proveedor);
    }

    /**
     * Constructor por defecto
     */
    public ProveedorModeloDTO() {
        super();
    }

    /**
     * Convierte la información enviada por parametro en formato del DTO
     * @param persona
     *        Informacion persona
     * @param empresa
     *        Informacion empresa
     * @param proveedor
     *        Informacion proveedor
     */
    public void convertToDTO(Persona persona, Empresa empresa, Proveedor proveedor) {
        this.setCuentaBancaria(proveedor.getCuentaBancaria());
        this.setDigitoVerificacionTitular(proveedor.getDigitoVerificacionTitular());
        this.setEstado(proveedor.getEstado());
        this.setIdOferente(proveedor.getIdOferente());
        this.setNombreTitularCuenta(proveedor.getNombreTitularCuenta());
        this.setNumeroCuenta(proveedor.getNumeroCuenta());
        this.setNumeroIdentificacionTitular(proveedor.getNumeroIdentificacionTitular());
        this.setTipoCuenta(proveedor.getTipoCuenta());
        this.setTipoIdentificacionTitular(proveedor.getTipoIdentificacionTitular());
        this.setConcepto(proveedor.getConcepto() != null ? proveedor.getConcepto() : 0L);
        this.setTipoTable("");
        // Info Persona
        PersonaModeloDTO personaModeloDTO = null;
        if (persona != null) {
            personaModeloDTO = new PersonaModeloDTO(persona);
        }
        else if (proveedor.getIdPersona() != null) {
            personaModeloDTO = new PersonaModeloDTO();
            personaModeloDTO.setIdPersona(proveedor.getIdPersona());
        }
        this.setPersona(personaModeloDTO);
        // Info Empresa
        EmpresaModeloDTO empresaModeloDTO = null;
        if (empresa != null) {
            empresaModeloDTO = new EmpresaModeloDTO(empresa);
        }
        else if (proveedor.getIdEmpresa() != null) {
            empresaModeloDTO = new EmpresaModeloDTO();
            empresaModeloDTO.setIdEmpresa(proveedor.getIdEmpresa());
        }
        this.setEmpresa(empresaModeloDTO);
        // Info Banco
        BancoModeloDTO bancoModeloDTO = null;
        if (proveedor.getIdBanco() != null) {
            bancoModeloDTO = new BancoModeloDTO();
            bancoModeloDTO.setId(proveedor.getIdBanco());
        }
        this.setBanco(bancoModeloDTO);
    }

    /**
     * Método que convierte el DTO a una entidad <code>proveedor</code>
     * 
     * @return La entidad <code>proveedor</code> equivalente
     */
    public Proveedor convertToEntity() {
        Proveedor proveedor = new Proveedor();
        proveedor.setCuentaBancaria(this.getCuentaBancaria());
        proveedor.setConcepto(this.getConcepto());
        proveedor.setDigitoVerificacionTitular(this.getDigitoVerificacionTitular());
        proveedor.setEstado(this.getEstado());
        proveedor.setIdOferente(this.getIdOferente());
        proveedor.setNombreTitularCuenta(this.getNombreTitularCuenta());
        proveedor.setNumeroCuenta(this.getNumeroCuenta());
        proveedor.setNumeroIdentificacionTitular(this.getNumeroIdentificacionTitular());
        proveedor.setTipoCuenta(this.getTipoCuenta());
        proveedor.setTipoIdentificacionTitular(this.getTipoIdentificacionTitular());
        // Info emprea
        if (this.getEmpresa() != null) {
            proveedor.setIdEmpresa(this.getEmpresa().getIdEmpresa());
        }
        // Info persona
        if (this.getPersona() != null) {
            proveedor.setIdPersona(this.getPersona().getIdPersona());
        } else if (this.getEmpresa().getIdPersona() != null) {
        	proveedor.setIdPersona(this.getEmpresa().getIdPersona());
        }
        // Info banco
        if (this.getBanco() != null) {
            proveedor.setIdBanco(this.getBanco().getId());
        }
        return proveedor;
    }

    /**
     * Obtiene el valor de idOferente
     * 
     * @return El valor de idOferente
     */
    public Long getIdOferente() {
        return idOferente;
    }

    /**
     * Establece el valor de idOferente
     * 
     * @param idOferente
     *        El valor de idOferente por asignar
     */
    public void setIdOferente(Long idOferente) {
        this.idOferente = idOferente;
    }

    /**
     * Obtiene el valor de empresa
     * 
     * @return El valor de empresa
     */
    public EmpresaModeloDTO getEmpresa() {
        return empresa;
    }

    /**
     * Establece el valor de empresa
     * 
     * @param empresa
     *        El valor de empresa por asignar
     */
    public void setEmpresa(EmpresaModeloDTO empresa) {
        this.empresa = empresa;
    }

    /**
     * Obtiene el valor de persona
     * 
     * @return El valor de persona
     */
    public PersonaModeloDTO getPersona() {
        return persona;
    }

    /**
     * Establece el valor de persona
     * 
     * @param persona
     *        El valor de persona por asignar
     */
    public void setPersona(PersonaModeloDTO persona) {
        this.persona = persona;
    }

    /**
     * Obtiene el valor de estado
     * 
     * @return El valor de estado
     */
    public EstadoOferenteEnum getEstado() {
        return estado;
    }

    /**
     * Establece el valor de estado
     * 
     * @param estado
     *        El valor de estado por asignar
     */
    public void setEstado(EstadoOferenteEnum estado) {
        this.estado = estado;
    }

    /**
     * @return the tipoCuenta
     */
    public TipoCuentaEnum getTipoCuenta() {
        return tipoCuenta;
    }

    /**
     * @param tipoCuenta
     *        the tipoCuenta to set
     */
    public void setTipoCuenta(TipoCuentaEnum tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    /**
     * @return the numeroCuenta
     */
    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    /**
     * @param numeroCuenta
     *        the numeroCuenta to set
     */
    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    /**
     * @return the tipoIdentificacionTitular
     */
    public TipoIdentificacionEnum getTipoIdentificacionTitular() {
        return tipoIdentificacionTitular;
    }

    /**
     * @param tipoIdentificacionTitular
     *        the tipoIdentificacionTitular to set
     */
    public void setTipoIdentificacionTitular(TipoIdentificacionEnum tipoIdentificacionTitular) {
        this.tipoIdentificacionTitular = tipoIdentificacionTitular;
    }

    /**
     * @return the numeroIdentificacionTitular
     */
    public String getNumeroIdentificacionTitular() {
        return numeroIdentificacionTitular;
    }

    /**
     * @param numeroIdentificacionTitular
     *        the numeroIdentificacionTitular to set
     */
    public void setNumeroIdentificacionTitular(String numeroIdentificacionTitular) {
        this.numeroIdentificacionTitular = numeroIdentificacionTitular;
    }

    /**
     * @return the nombreTitularCuenta
     */
    public String getNombreTitularCuenta() {
        return nombreTitularCuenta;
    }

    /**
     * @param nombreTitularCuenta
     *        the nombreTitularCuenta to set
     */
    public void setNombreTitularCuenta(String nombreTitularCuenta) {
        this.nombreTitularCuenta = nombreTitularCuenta;
    }

    /**
     * @return the banco
     */
    public BancoModeloDTO getBanco() {
        return banco;
    }

    /**
     * @param banco
     *        the banco to set
     */
    public void setBanco(BancoModeloDTO banco) {
        this.banco = banco;
    }

    /**
     * @return the digitoVerificacionTitular
     */
    public Short getDigitoVerificacionTitular() {
        return digitoVerificacionTitular;
    }

    /**
     * @param digitoVerificacionTitular
     *        the digitoVerificacionTitular to set
     */
    public void setDigitoVerificacionTitular(Short digitoVerificacionTitular) {
        this.digitoVerificacionTitular = digitoVerificacionTitular;
    }

    /**
     * @return the listaDocumentosSoporte
     */
    public List<DocumentoSoporteModeloDTO> getListaDocumentosSoporte() {
        return listaDocumentosSoporte;
    }

    /**
     * @param listaDocumentosSoporte
     *        the listaDocumentosSoporte to set
     */
    public void setListaDocumentosSoporte(List<DocumentoSoporteModeloDTO> listaDocumentosSoporte) {
        this.listaDocumentosSoporte = listaDocumentosSoporte;
    }

    /**
     * @return the cuentaBancaria
     */
    public Boolean getCuentaBancaria() {
        return cuentaBancaria;
    }

    /**
     * @param cuentaBancaria
     *        the cuentaBancaria to set
     */
    public void setCuentaBancaria(Boolean cuentaBancaria) {
        this.cuentaBancaria = cuentaBancaria;
    }

    
    /**
     * @return the concepto
     */
    public long getConcepto() {
        return concepto;
    }
    
    /**
     * @param concepto
     *        the concepto to set
     */
    public void setConcepto(long concepto) {
        this.concepto = concepto;
    }
      
    /**
     * @return the representanteLegal
     */
    public PersonaModeloDTO getRepresentanteLegal() {
        return representanteLegal;
    }

    /**
     * @param representanteLegal the representanteLegal to set
     */
    public void setRepresentanteLegal(PersonaModeloDTO representanteLegal) {
        this.representanteLegal = representanteLegal;
    }

    public String getTipoTable() {
        return tipoTable;
    }

    public void setTipoTable(String tipoTable) {
        this.tipoTable = tipoTable;
    }
    
}