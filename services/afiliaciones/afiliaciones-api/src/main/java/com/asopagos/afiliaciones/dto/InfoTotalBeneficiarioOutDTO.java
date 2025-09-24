package com.asopagos.afiliaciones.dto;

import java.io.Serializable;
import java.util.List;

import com.asopagos.enumeraciones.afiliaciones.MotivoDesafiliacionBeneficiarioEnum;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.personas.CategoriaPersonaEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.EstadoCivilEnum;
import com.asopagos.enumeraciones.personas.GeneroEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoBeneficiarioEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
//@JsonInclude(Include.NON_EMPTY) 71509
public class InfoTotalBeneficiarioOutDTO implements Serializable{


    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
    * Tipo de beneficiario
    */
    private TipoBeneficiarioEnum tipoBeneficiario;

    /**
    * Tipo de identificación del beneficiario
    */
    private TipoIdentificacionEnum tipoID;

    /**
    * Número de identificación del beneficiario
    */
    private String identificacion;

    /**
    * Primer Nombre del beneficiario
    */
    private String primerNombre;

    /**
    * Segundo nombre del beneficiario
    */
    private String segundoNombre;

    /**
    * Primer Apellido del beneficiario
    */
    private String primerApellido;

    /**
    * Segundo Apellido del beneficiario
    */
    private String segundoApellido;

    /**
     * Nombre completo del beneficiario
     */
    private String nombreCompleto;
    
    /**
    * Fecha de nacimiento del beneficiario
    */
    private String fechaNacimiento;

    /**
     * Edad del beneficiario 
     */
    private int edad;
    
    /**
    * Indica la fecha de fallecimiento del beneficiario
    */
    private String fechaFallecido;

    /**
    * Estado Civil del beneficiario
    */
    // private EstadoCivilEnum estadoCivil;
    private String estadoCivil;

    /**
    * Género del beneficiario
    */
    private GeneroEnum genero;

    /**
    * Dirección física principal del beneficiario
    */
    private String direccionResidencia;

    /**
    * Código DANE del Municipio de ubicación del beneficiario
    */
    private String municipioCodigo;

    /**
    * Nombre del municipio relacionado al codigo dane
    */
    private String municipioNombre;

    /**
    * Código DANE del departamento de ubicación del beneficiario
    */
    private String departamentoCodigo;

    /**
    * Nombre del departamento relacionado al codigo DANE
    */
    private String departamentoNombre;

    /**
    * Código postal definido para la ubicación del beneficiario
    */
    private String codigoPostal;

    /**
    * Indicativo del teléfono fijo + número fijo del beneficiario
    */
    private String telefonoFijo;

    /**
    * Número telefónico del celular del beneficiario
    */
    private String celular;

    /**
    * Correo electrónico del beneficiario
    */
    private String correoElectronico;

    /**
    * Autorización envio correo electrónico
    */
    private Boolean autorizacionEnvioEmail;

    /**
    * Autorización datos personales
    */
    private Boolean autorizacionDatosPersonales;

    /**
    * Código de la caja de compensacion Familiar
    */
    private String codigoCCF;

    /**
    * Categoría del beneficiario (Categoría actual para la persona consultada)
    */
    private CategoriaPersonaEnum categoria;

    /**
    * Clasificación Actual de la persona
    */
    private ClasificacionEnum clasificacion;

    /**
    * Estado Actual de afiliación de la persona (Activo - Inactivo)
    */
    private EstadoAfiliadoEnum estadoAfiliacion;

    /**
    * Numero indicador de grupo familiar
    */
    private String grupoFamiliar;

    /**
    * Fecha de afiliacion del beneficiario
    */
    private String fechaAfiliacionCCF;

    /**
    * Fecha de retiro del beneficiario
    */
    private String fechaRetiro;

    /**
    * Motivo de la desafiliación de la persona
    */
    private MotivoDesafiliacionBeneficiarioEnum motivoDesafiliacion;

    /**
    * Inhabilitado para recibir subsidio (trabajador dependiente)
    */
    private Boolean inhabilitadoSubsidio;

    /**
    * Fecha en la que la CCF realizó el último pago de cuota monetaria a la persona
    */
    private String ultimoPagoCuotaMonetaria;

    /**
    * Beneficiario  Campo tipo flag que indica si el beneficiario tiene o no condicion de invalidez
    */
    private Boolean condicionInvalidez;

    /**
    * Indica si el beneficiario tipo hijo es estudiante de programa en institución para el trabajo y desarrollo humano.
    */
    private Boolean estudianteTrabajoDesarrolloHumano;

    /**
    * Fecha de creacion de la solicitud de la afiliacion
    */
    private String fechaCreacionRegistro;

    /**
    * Usuario creacion de la solicitud de la afiliacion
    */
    private String usuarioCreacionRegistro;

    /**
    * Tipo de afiliado (Empleador, Trabajador_Dependiente, Trabajador_Independiente, Pensionado, No Afiliado)
    */
    private TipoAfiliadoEnum tipoAfiliado;

    /**
    * Tipo de identificación de la persona
    */
    private TipoIdentificacionEnum tipoIDAfiliado;

    /**
    * Afiliado  Número de identificación de la persona
    */
    private String identificacionAfiliado;

    /**
    * Primer Nombre del afiliado
    */
    private String primerNombreAfiliado;

    /**
    * Segundo nombre del afiliado
    */
    private String segundoNombreAfiliado;

    /**
    * Primer Apellido del afiliado
    */
    private String primerApellidoAfiliado;

    /**
    * Segundo Apellido del afiliado
    */
    private String segundoApellidoAfiliado;

    /**
    * Fecha de vencimiento del certificado de escolaridad vigente
    */
    private String fechaVencimientoCertificado;

    private List<CertificadoEscolaridadOutDTO> DTOArregloCertificadoEscolaridad; 
    
    /**
     * 
     */
    public InfoTotalBeneficiarioOutDTO() {
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
     * @return the tipoID
     */
    public TipoIdentificacionEnum getTipoID() {
        return tipoID;
    }

    /**
     * @param tipoID the tipoID to set
     */
    public void setTipoID(TipoIdentificacionEnum tipoID) {
        this.tipoID = tipoID;
    }

    /**
     * @return the identificacion
     */
    public String getIdentificacion() {
        return identificacion;
    }

    /**
     * @param identificacion the identificacion to set
     */
    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    /**
     * @return the primerNombre
     */
    public String getPrimerNombre() {
        return primerNombre;
    }

    /**
     * @param primerNombre the primerNombre to set
     */
    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    /**
     * @return the segundoNombre
     */
    public String getSegundoNombre() {
        return segundoNombre;
    }

    /**
     * @param segundoNombre the segundoNombre to set
     */
    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    /**
     * @return the primerApellido
     */
    public String getPrimerApellido() {
        return primerApellido;
    }

    /**
     * @param primerApellido the primerApellido to set
     */
    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    /**
     * @return the segundoApellido
     */
    public String getSegundoApellido() {
        return segundoApellido;
    }

    /**
     * @param segundoApellido the segundoApellido to set
     */
    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    /**
     * @return the fechaNacimiento
     */
    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    /**
     * @param fechaNacimiento the fechaNacimiento to set
     */
    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    /**
     * @return the fechaFallecido
     */
    public String getFechaFallecido() {
        return fechaFallecido;
    }

    /**
     * @param fechaFallecido the fechaFallecido to set
     */
    public void setFechaFallecido(String fechaFallecido) {
        this.fechaFallecido = fechaFallecido;
    }

    /**
     * @return the estadoCivil
     */
    // public EstadoCivilEnum getEstadoCivil() {
    //     return estadoCivil;
    // }
    public String getEstadoCivil() {
        return estadoCivil;
    }

    /**
     * @param estadoCivil the estadoCivil to set
     */
    // public void setEstadoCivil(EstadoCivilEnum estadoCivil) {
    //     this.estadoCivil = estadoCivil;
    // }
    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    /**
     * @return the genero
     */
    public GeneroEnum getGenero() {
        return genero;
    }

    /**
     * @param genero the genero to set
     */
    public void setGenero(GeneroEnum genero) {
        this.genero = genero;
    }

    /**
     * @return the direccionResidencia
     */
    public String getDireccionResidencia() {
        return direccionResidencia;
    }

    /**
     * @param direccionResidencia the direccionResidencia to set
     */
    public void setDireccionResidencia(String direccionResidencia) {
        this.direccionResidencia = direccionResidencia;
    }

    /**
     * @return the municipioCodigo
     */
    public String getMunicipioCodigo() {
        return municipioCodigo;
    }

    /**
     * @param municipioCodigo the municipioCodigo to set
     */
    public void setMunicipioCodigo(String municipioCodigo) {
        this.municipioCodigo = municipioCodigo;
    }

    /**
     * @return the municipioNombre
     */
    public String getMunicipioNombre() {
        return municipioNombre;
    }

    /**
     * @param municipioNombre the municipioNombre to set
     */
    public void setMunicipioNombre(String municipioNombre) {
        this.municipioNombre = municipioNombre;
    }

    /**
     * @return the departamentoCodigo
     */
    public String getDepartamentoCodigo() {
        return departamentoCodigo;
    }

    /**
     * @param departamentoCodigo the departamentoCodigo to set
     */
    public void setDepartamentoCodigo(String departamentoCodigo) {
        this.departamentoCodigo = departamentoCodigo;
    }

    /**
     * @return the departamentoNombre
     */
    public String getDepartamentoNombre() {
        return departamentoNombre;
    }

    /**
     * @param departamentoNombre the departamentoNombre to set
     */
    public void setDepartamentoNombre(String departamentoNombre) {
        this.departamentoNombre = departamentoNombre;
    }

    /**
     * @return the codigoPostal
     */
    public String getCodigoPostal() {
        return codigoPostal;
    }

    /**
     * @param codigoPostal the codigoPostal to set
     */
    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    /**
     * @return the telefonoFijo
     */
    public String getTelefonoFijo() {
        return telefonoFijo;
    }

    /**
     * @param telefonoFijo the telefonoFijo to set
     */
    public void setTelefonoFijo(String telefonoFijo) {
        this.telefonoFijo = telefonoFijo;
    }

    /**
     * @return the celular
     */
    public String getCelular() {
        return celular;
    }

    /**
     * @param celular the celular to set
     */
    public void setCelular(String celular) {
        this.celular = celular;
    }

    /**
     * @return the correoElectronico
     */
    public String getCorreoElectronico() {
        return correoElectronico;
    }

    /**
     * @param correoElectronico the correoElectronico to set
     */
    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    /**
     * @return the autorizacionEnvioEmail
     */
    public Boolean getAutorizacionEnvioEmail() {
        return autorizacionEnvioEmail;
    }

    /**
     * @param autorizacionEnvioEmail the autorizacionEnvioEmail to set
     */
    public void setAutorizacionEnvioEmail(Boolean autorizacionEnvioEmail) {
        this.autorizacionEnvioEmail = autorizacionEnvioEmail;
    }

    /**
     * @return the autorizacionDatosPersonales
     */
    public Boolean getAutorizacionDatosPersonales() {
        return autorizacionDatosPersonales;
    }

    /**
     * @param autorizacionDatosPersonales the autorizacionDatosPersonales to set
     */
    public void setAutorizacionDatosPersonales(Boolean autorizacionDatosPersonales) {
        this.autorizacionDatosPersonales = autorizacionDatosPersonales;
    }

    /**
     * @return the codigoCCF
     */
    public String getCodigoCCF() {
        return codigoCCF;
    }

    /**
     * @param codigoCCF the codigoCCF to set
     */
    public void setCodigoCCF(String codigoCCF) {
        this.codigoCCF = codigoCCF;
    }

    /**
     * @return the categoria
     */
    public CategoriaPersonaEnum getCategoria() {
        return categoria;
    }

    /**
     * @param categoria the categoria to set
     */
    public void setCategoria(CategoriaPersonaEnum categoria) {
        this.categoria = categoria;
    }

    /**
     * @return the clasificacion
     */
    public ClasificacionEnum getClasificacion() {
        return clasificacion;
    }

    /**
     * @param clasificacion the clasificacion to set
     */
    public void setClasificacion(ClasificacionEnum clasificacion) {
        this.clasificacion = clasificacion;
    }

    /**
     * @return the estadoAfiliacion
     */
    public EstadoAfiliadoEnum getEstadoAfiliacion() {
        return estadoAfiliacion;
    }

    /**
     * @param estadoAfiliacion the estadoAfiliacion to set
     */
    public void setEstadoAfiliacion(EstadoAfiliadoEnum estadoAfiliacion) {
        this.estadoAfiliacion = estadoAfiliacion;
    }

    /**
     * @return the grupoFamiliar
     */
    public String getGrupoFamiliar() {
        return grupoFamiliar;
    }

    /**
     * @param grupoFamiliar the grupoFamiliar to set
     */
    public void setGrupoFamiliar(String grupoFamiliar) {
        this.grupoFamiliar = grupoFamiliar;
    }

    /**
     * @return the fechaAfiliacionCCF
     */
    public String getFechaAfiliacionCCF() {
        return fechaAfiliacionCCF;
    }

    /**
     * @param fechaAfiliacionCCF the fechaAfiliacionCCF to set
     */
    public void setFechaAfiliacionCCF(String fechaAfiliacionCCF) {
        this.fechaAfiliacionCCF = fechaAfiliacionCCF;
    }

    /**
     * @return the fechaRetiro
     */
    public String getFechaRetiro() {
        return fechaRetiro;
    }

    /**
     * @param fechaRetiro the fechaRetiro to set
     */
    public void setFechaRetiro(String fechaRetiro) {
        this.fechaRetiro = fechaRetiro;
    }

    /**
     * @return the motivoDesafiliacion
     */
    public MotivoDesafiliacionBeneficiarioEnum getMotivoDesafiliacion() {
        return motivoDesafiliacion;
    }

    /**
     * @param motivoDesafiliacion the motivoDesafiliacion to set
     */
    public void setMotivoDesafiliacion(MotivoDesafiliacionBeneficiarioEnum motivoDesafiliacion) {
        this.motivoDesafiliacion = motivoDesafiliacion;
    }

    /**
     * @return the inhabilitadoSubsidio
     */
    public Boolean getInhabilitadoSubsidio() {
        return inhabilitadoSubsidio;
    }

    /**
     * @param inhabilitadoSubsidio the inhabilitadoSubsidio to set
     */
    public void setInhabilitadoSubsidio(Boolean inhabilitadoSubsidio) {
        this.inhabilitadoSubsidio = inhabilitadoSubsidio;
    }

    /**
     * @return the ultimoPagoCuotaMonetaria
     */
    public String getUltimoPagoCuotaMonetaria() {
        return ultimoPagoCuotaMonetaria;
    }

    /**
     * @param ultimoPagoCuotaMonetaria the ultimoPagoCuotaMonetaria to set
     */
    public void setUltimoPagoCuotaMonetaria(String ultimoPagoCuotaMonetaria) {
        this.ultimoPagoCuotaMonetaria = ultimoPagoCuotaMonetaria;
    }

    /**
     * @return the condicionInvalidez
     */
    public Boolean getCondicionInvalidez() {
        return condicionInvalidez;
    }

    /**
     * @param condicionInvalidez the condicionInvalidez to set
     */
    public void setCondicionInvalidez(Boolean condicionInvalidez) {
        this.condicionInvalidez = condicionInvalidez;
    }

    /**
     * @return the estudianteTrabajoDesarrolloHumano
     */
    public Boolean getEstudianteTrabajoDesarrolloHumano() {
        return estudianteTrabajoDesarrolloHumano;
    }

    /**
     * @param estudianteTrabajoDesarrolloHumano the estudianteTrabajoDesarrolloHumano to set
     */
    public void setEstudianteTrabajoDesarrolloHumano(Boolean estudianteTrabajoDesarrolloHumano) {
        this.estudianteTrabajoDesarrolloHumano = estudianteTrabajoDesarrolloHumano;
    }

    /**
     * @return the fechaCreacionRegistro
     */
    public String getFechaCreacionRegistro() {
        return fechaCreacionRegistro;
    }

    /**
     * @param fechaCreacionRegistro the fechaCreacionRegistro to set
     */
    public void setFechaCreacionRegistro(String fechaCreacionRegistro) {
        this.fechaCreacionRegistro = fechaCreacionRegistro;
    }

    /**
     * @return the usuarioCreacionRegistro
     */
    public String getUsuarioCreacionRegistro() {
        return usuarioCreacionRegistro;
    }

    /**
     * @param usuarioCreacionRegistro the usuarioCreacionRegistro to set
     */
    public void setUsuarioCreacionRegistro(String usuarioCreacionRegistro) {
        this.usuarioCreacionRegistro = usuarioCreacionRegistro;
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
     * @return the tipoIDAfiliado
     */
    public TipoIdentificacionEnum getTipoIDAfiliado() {
        return tipoIDAfiliado;
    }

    /**
     * @param tipoIDAfiliado the tipoIDAfiliado to set
     */
    public void setTipoIDAfiliado(TipoIdentificacionEnum tipoIDAfiliado) {
        this.tipoIDAfiliado = tipoIDAfiliado;
    }

    /**
     * @return the identificacionAfiliado
     */
    public String getIdentificacionAfiliado() {
        return identificacionAfiliado;
    }

    /**
     * @param identificacionAfiliado the identificacionAfiliado to set
     */
    public void setIdentificacionAfiliado(String identificacionAfiliado) {
        this.identificacionAfiliado = identificacionAfiliado;
    }

    /**
     * @return the primerNombreAfiliado
     */
    public String getPrimerNombreAfiliado() {
        return primerNombreAfiliado;
    }

    /**
     * @param primerNombreAfiliado the primerNombreAfiliado to set
     */
    public void setPrimerNombreAfiliado(String primerNombreAfiliado) {
        this.primerNombreAfiliado = primerNombreAfiliado;
    }

    /**
     * @return the segundoNombreAfiliado
     */
    public String getSegundoNombreAfiliado() {
        return segundoNombreAfiliado;
    }

    /**
     * @param segundoNombreAfiliado the segundoNombreAfiliado to set
     */
    public void setSegundoNombreAfiliado(String segundoNombreAfiliado) {
        this.segundoNombreAfiliado = segundoNombreAfiliado;
    }

    /**
     * @return the primerApellidoAfiliado
     */
    public String getPrimerApellidoAfiliado() {
        return primerApellidoAfiliado;
    }

    /**
     * @param primerApellidoAfiliado the primerApellidoAfiliado to set
     */
    public void setPrimerApellidoAfiliado(String primerApellidoAfiliado) {
        this.primerApellidoAfiliado = primerApellidoAfiliado;
    }

    /**
     * @return the segundoApellidoAfiliado
     */
    public String getSegundoApellidoAfiliado() {
        return segundoApellidoAfiliado;
    }

    /**
     * @param segundoApellidoAfiliado the segundoApellidoAfiliado to set
     */
    public void setSegundoApellidoAfiliado(String segundoApellidoAfiliado) {
        this.segundoApellidoAfiliado = segundoApellidoAfiliado;
    }

    /**
     * @return the fechaVencimientoCertificado
     */
    public String getFechaVencimientoCertificado() {
        return fechaVencimientoCertificado;
    }

    /**
     * @param fechaVencimientoCertificado the fechaVencimientoCertificado to set
     */
    public void setFechaVencimientoCertificado(String fechaVencimientoCertificado) {
        this.fechaVencimientoCertificado = fechaVencimientoCertificado;
    }

    /**
	 * @return the edad
	 */
	public int getEdad() {
		return edad;
	}

	/**
	 * @param edad the edad to set
	 */
	public void setEdad(int edad) {
		this.edad = edad;
	}
	
	/**
	 * @return the dTOArregloCertificadoEscolaridad
	 */
	public List<CertificadoEscolaridadOutDTO> getDTOArregloCertificadoEscolaridad() {
		return DTOArregloCertificadoEscolaridad;
	}

	/**
	 * @param dTOArregloCertificadoEscolaridad the dTOArregloCertificadoEscolaridad to set
	 */
	public void setDTOArregloCertificadoEscolaridad(List<CertificadoEscolaridadOutDTO> dTOArregloCertificadoEscolaridad) {
		DTOArregloCertificadoEscolaridad = dTOArregloCertificadoEscolaridad;
	}

	/**
	 * @return the nombreCompleto
	 */
	public String getNombreCompleto() {
		return nombreCompleto;
	}

	/**
	 * @param nombreCompleto the nombreCompleto to set
	 */
	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	public void obtenerNombreCompleto(){
		StringBuilder nombreCompleto = new StringBuilder();
		if(this.primerNombre != null && !this.primerNombre.equals(""))
		{
			nombreCompleto.append(this.primerNombre);
		}
		if(this.segundoNombre != null && !this.segundoNombre.equals(""))
		{
			if(nombreCompleto.toString().equals("")){
				nombreCompleto.append(this.segundoNombre);
			}else{
				nombreCompleto.append(" ");
				nombreCompleto.append(this.segundoNombre);
			}
		}
		if(this.primerApellido != null && !this.primerApellido.equals(""))
		{
			nombreCompleto.append(" ");
			nombreCompleto.append(this.primerApellido);
		}
		if(this.segundoApellido != null && !this.segundoApellido.equals(""))
		{
			nombreCompleto.append(" ");
			nombreCompleto.append(this.segundoApellido);
		}
		this.nombreCompleto = nombreCompleto.toString();
	}
}
