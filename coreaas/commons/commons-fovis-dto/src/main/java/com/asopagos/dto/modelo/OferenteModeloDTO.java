package com.asopagos.dto.modelo;

import java.io.Serializable;
import java.util.List;
import com.asopagos.entidades.ccf.fovis.Oferente;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.entidades.ccf.personas.Empresa;
import com.asopagos.enumeraciones.fovis.EstadoOferenteEnum;
import com.asopagos.enumeraciones.personas.TipoCuentaEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * <b>Descripción: </b> DTO que representa la información de un registro de la
 * tabla <code>Oferente</code> <br/>
 * <b>Historia de Usuario: </b> HU-023
 * 
 * @author <a href="mailto:fvasquez@heinsohn.com.co">Ferney Alonso Vásquez
 *         Benavides</a>
 */
public class OferenteModeloDTO implements Serializable {

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
     * Constructor especifico
     * @param oferente
     *        Informacion oferente
     * @param persona
     *        Informacion persona
     * @param empresa
     *        Informacion empresa
     */
    public OferenteModeloDTO(Oferente oferente, Persona persona, Empresa empresa) {
        super();
        if (oferente == null) {
            oferente = new Oferente();
        }
        convertToDTO(persona, empresa, oferente);
    }

    /**
     * Constructor especifico
     * @param persona
     *        Informacion persona
     * @param empresa
     *        Informacion empresa
     */
    public OferenteModeloDTO(Oferente oferente, Persona persona) {
        super();
        if (oferente == null) {
            oferente = new Oferente();
        }
        convertToDTO(persona, null, oferente);
    }

    /**
     * Constructor por defecto
     */
    public OferenteModeloDTO() {
        super();
    }

    /**
     * Convierte la información enviada por parametro en formato del DTO
     * @param persona
     *        Informacion persona
     * @param empresa
     *        Informacion empresa
     * @param oferente
     *        Informacion oferente
     */
    public void convertToDTO(Persona persona, Empresa empresa, Oferente oferente) {
        this.setCuentaBancaria(oferente.getCuentaBancaria());
        this.setDigitoVerificacionTitular(oferente.getDigitoVerificacionTitular());
        this.setEstado(oferente.getEstado());
        this.setIdOferente(oferente.getIdOferente());
        this.setNombreTitularCuenta(oferente.getNombreTitularCuenta());
        this.setNumeroCuenta(oferente.getNumeroCuenta());
        this.setNumeroIdentificacionTitular(oferente.getNumeroIdentificacionTitular());
        this.setTipoCuenta(oferente.getTipoCuenta());
        this.setTipoIdentificacionTitular(oferente.getTipoIdentificacionTitular());
        // Info Persona
        PersonaModeloDTO personaModeloDTO = null;
        if (persona != null) {
            personaModeloDTO = new PersonaModeloDTO(persona);
        }
        else if (oferente.getIdPersona() != null) {
            personaModeloDTO = new PersonaModeloDTO();
            personaModeloDTO.setIdPersona(oferente.getIdPersona());
        }
        this.setPersona(personaModeloDTO);
        // Info Empresa
        EmpresaModeloDTO empresaModeloDTO = null;
        if (empresa != null) {
            empresaModeloDTO = new EmpresaModeloDTO(empresa);
        }
        else if (oferente.getIdEmpresa() != null) {
            empresaModeloDTO = new EmpresaModeloDTO();
            empresaModeloDTO.setIdEmpresa(oferente.getIdEmpresa());
        }
        this.setEmpresa(empresaModeloDTO);
        // Info Banco
        BancoModeloDTO bancoModeloDTO = null;
        if (oferente.getIdBanco() != null) {
            bancoModeloDTO = new BancoModeloDTO();
            bancoModeloDTO.setId(oferente.getIdBanco());
        }
        this.setBanco(bancoModeloDTO);
    }

    /**
     * Método que convierte el DTO a una entidad <code>Oferente</code>
     * 
     * @return La entidad <code>Oferente</code> equivalente
     */
    public Oferente convertToEntity() {
        Oferente oferente = new Oferente();
        oferente.setCuentaBancaria(this.getCuentaBancaria());
        oferente.setDigitoVerificacionTitular(this.getDigitoVerificacionTitular());
        oferente.setEstado(this.getEstado());
        oferente.setIdOferente(this.getIdOferente());
        oferente.setNombreTitularCuenta(this.getNombreTitularCuenta());
        oferente.setNumeroCuenta(this.getNumeroCuenta());
        oferente.setNumeroIdentificacionTitular(this.getNumeroIdentificacionTitular());
        oferente.setTipoCuenta(this.getTipoCuenta());
        oferente.setTipoIdentificacionTitular(this.getTipoIdentificacionTitular());
        // Info emprea
        if (this.getEmpresa() != null) {
            oferente.setIdEmpresa(this.getEmpresa().getIdEmpresa());
        }
        // Info persona
        if (this.getPersona() != null) {
            oferente.setIdPersona(this.getPersona().getIdPersona());
        } else if (this.getEmpresa().getIdPersona() != null) {
        	oferente.setIdPersona(this.getEmpresa().getIdPersona());
        }
        // Info banco
        if (this.getBanco() != null) {
            oferente.setIdBanco(this.getBanco().getId());
        }
        return oferente;
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

    @Override
    public String toString() {
        return "OferenteModeloDTO{" +
                "idOferente=" + idOferente +
                ", empresa=" + empresa +
                ", persona=" + persona +
                ", representanteLegal=" + representanteLegal +
                ", banco=" + banco +
                ", tipoCuenta=" + tipoCuenta +
                ", numeroCuenta='" + numeroCuenta + '\'' +
                ", tipoIdentificacionTitular=" + tipoIdentificacionTitular +
                ", numeroIdentificacionTitular='" + numeroIdentificacionTitular + '\'' +
                ", nombreTitularCuenta='" + nombreTitularCuenta + '\'' +
                ", digitoVerificacionTitular=" + digitoVerificacionTitular +
                ", listaDocumentosSoporte=" + listaDocumentosSoporte +
                ", estado=" + estado +
                ", cuentaBancaria=" + cuentaBancaria +
                '}';
    }
}
