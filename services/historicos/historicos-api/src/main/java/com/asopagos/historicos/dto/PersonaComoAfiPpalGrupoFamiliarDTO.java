package com.asopagos.historicos.dto;

import com.asopagos.dto.modelo.MedioDePagoModeloDTO;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.personas.TipoMedioDePagoEnum;

public class PersonaComoAfiPpalGrupoFamiliarDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -2197006644462770420L;
    
    //info del admin Subsidio grupo
    private TipoIdentificacionEnum adminTipoIdentificacion;
    private String adminNumeroIdentificacion;
    private String adminPrimerNombre;
    private String adminSegundoNombre;
    private String adminPrimerApellido;
    private String adminSegundoApellido;
    private TipoMedioDePagoEnum medioDePago;
    private Long idGrupoFamiliar;
    
    //info del medio de pago grupo
    private TipoMedioDePagoEnum grupoMedioPago;
    private String grupoSitioPago;
    private Boolean grupoInembargable;   
    
    //info de ubicacion grupo
    private String codigoGrupoFamiliar;
    private Boolean dirIgualAfiPrincipal;
    private Short idMunicipio;
    private Short idDepartamento;
    private String direccion;
    private String codigoPostal;
    private String indicativoTelFijo;
    private String telefonoFijo;
    private String telefonoCelular;
    private String correo;
    private Long fechaInicioAdminSubsidio;
    private Boolean medioPagoActivo;
   private Long idAdministradorSubsidio;
    private MedioDePagoModeloDTO medioDePagoDTO;
    private Boolean tarjetaMultiservicio;

    
    private List<BeneficiarioGrupoFamiliarDTO> beneficiarios;

    /**
     * Constructor por defecto
     */
    public PersonaComoAfiPpalGrupoFamiliarDTO() {
        super();
    }

    /**
     * @param adminTipoIdentificacion
     * @param adminNumeroIdentificacion
     * @param adminPrimerNombre
     * @param adminSegundoNombre
     * @param adminPrimerApellido
     * @param adminSegundoApellido
     * @param medioDePago
     * @param idGrupoFamiliar
     * @param grupoMedioPago
     * @param grupoSitioPago
     * @param grupoInembargable
     * @param codigoGrupoFamiliar
     * @param dirIgualAfiPrincipal
     * @param idMunicipio
     * @param idDepartamento
     * @param direccion
     * @param codigoPostal
     * @param indicativoTelFijo
     * @param telefonoFijo
     * @param telefonoCelular
     * @param correo
     * @param beneficiarios
     */
    public PersonaComoAfiPpalGrupoFamiliarDTO(TipoIdentificacionEnum adminTipoIdentificacion, String adminNumeroIdentificacion,
            String adminPrimerNombre, String adminSegundoNombre, String adminPrimerApellido, String adminSegundoApellido,
            TipoMedioDePagoEnum medioDePago, Long idGrupoFamiliar, TipoMedioDePagoEnum grupoMedioPago, String grupoSitioPago,
            Boolean grupoInembargable, String codigoGrupoFamiliar, Boolean dirIgualAfiPrincipal, Short idMunicipio, Short idDepartamento,
            String direccion, String codigoPostal, String indicativoTelFijo, String telefonoFijo, String telefonoCelular, String correo,
            List<BeneficiarioGrupoFamiliarDTO> beneficiarios,MedioDePagoModeloDTO medioDePagoDTO) {
        this.adminTipoIdentificacion = adminTipoIdentificacion;
        this.adminNumeroIdentificacion = adminNumeroIdentificacion;
        this.adminPrimerNombre = adminPrimerNombre;
        this.adminSegundoNombre = adminSegundoNombre;
        this.adminPrimerApellido = adminPrimerApellido;
        this.adminSegundoApellido = adminSegundoApellido;
        this.medioDePago = medioDePago;
        this.idGrupoFamiliar = idGrupoFamiliar;
        this.grupoMedioPago = grupoMedioPago;
        this.grupoSitioPago = grupoSitioPago;
        this.grupoInembargable = grupoInembargable;
        this.codigoGrupoFamiliar = codigoGrupoFamiliar;
        this.dirIgualAfiPrincipal = dirIgualAfiPrincipal;
        this.idMunicipio = idMunicipio;
        this.idDepartamento = idDepartamento;
        this.direccion = direccion;
        this.codigoPostal = codigoPostal;
        this.indicativoTelFijo = indicativoTelFijo;
        this.telefonoFijo = telefonoFijo;
        this.telefonoCelular = telefonoCelular;
        this.correo = correo;
        this.beneficiarios = beneficiarios;
        this.medioDePagoDTO=medioDePagoDTO;
    }

    /**
     * @param adminTipoIdentificacion
     * @param adminNumeroIdentificacion
     * @param adminPrimerNombre
     * @param adminSegundoNombre
     * @param adminPrimerApellido
     * @param adminSegundoApellido
     * @param medioDePago
     * @param idGrupoFamiliar
     * @param grupoMedioPago
     * @param grupoInembargable
     * @param idMunicipio
     * @param idDepartamento
     * @param direccion
     * @param codigoPostal
     * @param indicativoTelFijo
     * @param telefonoFijo
     * @param telefonoCelular
     * @param correo
     */
    public PersonaComoAfiPpalGrupoFamiliarDTO(TipoIdentificacionEnum adminTipoIdentificacion, String adminNumeroIdentificacion,
            String adminPrimerNombre, String adminSegundoNombre, String adminPrimerApellido, String adminSegundoApellido,
            TipoMedioDePagoEnum medioDePago, Long idGrupoFamiliar, TipoMedioDePagoEnum grupoMedioPago,
            Boolean grupoInembargable, Short idMunicipio, Short idDepartamento,
            String direccion, String codigoPostal, String indicativoTelFijo, String telefonoFijo, String telefonoCelular, String correo) {
        this.adminTipoIdentificacion = adminTipoIdentificacion;
        this.adminNumeroIdentificacion = adminNumeroIdentificacion;
        this.adminPrimerNombre = adminPrimerNombre;
        this.adminSegundoNombre = adminSegundoNombre;
        this.adminPrimerApellido = adminPrimerApellido;
        this.adminSegundoApellido = adminSegundoApellido;
        this.medioDePago = medioDePago;
        this.idGrupoFamiliar = idGrupoFamiliar;
        this.grupoMedioPago = grupoMedioPago;
        this.grupoInembargable = grupoInembargable;
        this.idMunicipio = idMunicipio;
        this.idDepartamento = idDepartamento;
        this.direccion = direccion;
        this.codigoPostal = codigoPostal;
        this.indicativoTelFijo = indicativoTelFijo;
        this.telefonoFijo = telefonoFijo;
        this.telefonoCelular = telefonoCelular;
        this.correo = correo;
    }

    /**
     * Constructor para consulta de grupos familiares
     * @param adminTipoIdentificacion
     *        Tipo identificación del administrador de subsidio
     * @param adminNumeroIdentificacion
     *        Numero identificación del administrador de subsidio
     * @param adminPrimerNombre
     *        Primer nombre administrador de subsidio
     * @param adminSegundoNombre
     *        Segundo nombre administrador de subsidio
     * @param adminPrimerApellido
     *        Primer apellido administrador de subsidio
     * @param adminSegundoApellido
     *        Segundo apellido administrador de subsidio
     * @param medioDePago
     *        Tipo medio de pago administrador de subsidio
     * @param idGrupoFamiliar
     *        Identificador grupo familiar
     * @param grupoSitioPago
     *        Sitio de pago asociado al medio de pago efectivo
     * @param grupoInembargable
     *        Indica si el grupo familiar es inembargable
     * @param dirIgualAfiPrincipal
     *        Indica si la dirección del grupo familiar es la misma del afiliado
     * @param idMunicipio
     *        Identificador del municipio de ubicación grupo familiar
     * @param idDepartamento
     *        Identificador del departamento de ubicación grupo familiar
     * @param direccion
     *        Dirección física de ubicación grupo familiar
     * @param codigoPostal
     *        Código postal de ubicación grupo familiar
     * @param indicativoTelFijo
     *        Indicativo telefono fijo ubicación grupo familiar
     * @param telefonoFijo
     *        Número teléfono fijo ubicación grupo familiar
     * @param telefonoCelular
     *        Número teléfono celular ubicación grupo familiar
     * @param correo
     *        Correo electrónico ubicación grupo familiar
     * @param fechaInicioAdminSubsidio
     *        Fecha inicio relación como administrador subsidio
     * @param medioPagoActivo
     *        Indica si el medio de pago esta activo
     */
    public PersonaComoAfiPpalGrupoFamiliarDTO(String adminTipoIdentificacion, String adminNumeroIdentificacion, String adminPrimerNombre,
            String adminSegundoNombre, String adminPrimerApellido, String adminSegundoApellido, String medioDePago, Long idGrupoFamiliar,
            String grupoSitioPago, Boolean grupoInembargable, Boolean dirIgualAfiPrincipal, Short idMunicipio, Short idDepartamento,
            String direccion, String codigoPostal, String indicativoTelFijo, String telefonoFijo, String telefonoCelular, String correo,
            Date fechaInicioAdminSubsidio, Boolean medioPagoActivo,Long idAdministradorSubsidio) {
        super();
        if (adminTipoIdentificacion != null) {
            this.adminTipoIdentificacion = TipoIdentificacionEnum.valueOf(adminTipoIdentificacion);
        }
        this.adminNumeroIdentificacion = adminNumeroIdentificacion;
        this.adminPrimerNombre = adminPrimerNombre;
        this.adminSegundoNombre = adminSegundoNombre;
        this.adminPrimerApellido = adminPrimerApellido;
        this.adminSegundoApellido = adminSegundoApellido;
        if (medioDePago != null) {
            this.medioDePago = TipoMedioDePagoEnum.valueOf(medioDePago);
            this.grupoMedioPago = TipoMedioDePagoEnum.valueOf(medioDePago);
        }
        if (idGrupoFamiliar != null) {
            this.idGrupoFamiliar = idGrupoFamiliar;
            this.codigoGrupoFamiliar = idGrupoFamiliar.toString();
        }
        this.grupoSitioPago = grupoSitioPago;
        this.grupoInembargable = grupoInembargable;
        this.dirIgualAfiPrincipal = dirIgualAfiPrincipal;
        this.idMunicipio = idMunicipio;
        this.idDepartamento = idDepartamento;
        this.direccion = direccion;
        this.codigoPostal = codigoPostal;
        this.indicativoTelFijo = indicativoTelFijo;
        this.telefonoFijo = telefonoFijo;
        this.telefonoCelular = telefonoCelular;
        this.correo = correo;
        if (fechaInicioAdminSubsidio != null) {
            this.fechaInicioAdminSubsidio = fechaInicioAdminSubsidio.getTime();
        }
        this.medioPagoActivo = medioPagoActivo;
        if(idAdministradorSubsidio!=null){
            this.idAdministradorSubsidio = idAdministradorSubsidio;
        }
    }
    public PersonaComoAfiPpalGrupoFamiliarDTO(String adminTipoIdentificacion, String adminNumeroIdentificacion, String adminPrimerNombre,
            String adminSegundoNombre, String adminPrimerApellido, String adminSegundoApellido, String medioDePago, Long idGrupoFamiliar,
            String grupoSitioPago, Boolean grupoInembargable, Boolean dirIgualAfiPrincipal, Short idMunicipio, Short idDepartamento,
            String direccion, String codigoPostal, String indicativoTelFijo, String telefonoFijo, String telefonoCelular, String correo,
            Date fechaInicioAdminSubsidio, Boolean medioPagoActivo,Long idAdministradorSubsidio, Boolean tarjetaMultiservicio) {
        super();
        if (adminTipoIdentificacion != null) {
            this.adminTipoIdentificacion = TipoIdentificacionEnum.valueOf(adminTipoIdentificacion);
        }
        this.adminNumeroIdentificacion = adminNumeroIdentificacion;
        this.adminPrimerNombre = adminPrimerNombre;
        this.adminSegundoNombre = adminSegundoNombre;
        this.adminPrimerApellido = adminPrimerApellido;
        this.adminSegundoApellido = adminSegundoApellido;
        if (medioDePago != null) {
            this.medioDePago = TipoMedioDePagoEnum.valueOf(medioDePago);
            this.grupoMedioPago = TipoMedioDePagoEnum.valueOf(medioDePago);
        }
        if (idGrupoFamiliar != null) {
            this.idGrupoFamiliar = idGrupoFamiliar;
            this.codigoGrupoFamiliar = idGrupoFamiliar.toString();
        }
        this.grupoSitioPago = grupoSitioPago;
        this.grupoInembargable = grupoInembargable;
        this.dirIgualAfiPrincipal = dirIgualAfiPrincipal;
        this.idMunicipio = idMunicipio;
        this.idDepartamento = idDepartamento;
        this.direccion = direccion;
        this.codigoPostal = codigoPostal;
        this.indicativoTelFijo = indicativoTelFijo;
        this.telefonoFijo = telefonoFijo;
        this.telefonoCelular = telefonoCelular;
        this.correo = correo;
        if (fechaInicioAdminSubsidio != null) {
            this.fechaInicioAdminSubsidio = fechaInicioAdminSubsidio.getTime();
        }
        this.medioPagoActivo = medioPagoActivo;
        if(idAdministradorSubsidio!=null){
            this.idAdministradorSubsidio = idAdministradorSubsidio;
        }
        if(tarjetaMultiservicio!=null){
            this.tarjetaMultiservicio = tarjetaMultiservicio;
        }
    }

    /**
     * @return the adminTipoIdentificacion
     */
    public TipoIdentificacionEnum getAdminTipoIdentificacion() {
        return adminTipoIdentificacion;
    }

    /**
     * @param adminTipoIdentificacion the adminTipoIdentificacion to set
     */
    public void setAdminTipoIdentificacion(TipoIdentificacionEnum adminTipoIdentificacion) {
        this.adminTipoIdentificacion = adminTipoIdentificacion;
    }

    /**
     * @return the adminNumeroIdentificacion
     */
    public String getAdminNumeroIdentificacion() {
        return adminNumeroIdentificacion;
    }

    /**
     * @param adminNumeroIdentificacion the adminNumeroIdentificacion to set
     */
    public void setAdminNumeroIdentificacion(String adminNumeroIdentificacion) {
        this.adminNumeroIdentificacion = adminNumeroIdentificacion;
    }

    /**
     * @return the adminPrimerNombre
     */
    public String getAdminPrimerNombre() {
        return adminPrimerNombre;
    }

    /**
     * @param adminPrimerNombre the adminPrimerNombre to set
     */
    public void setAdminPrimerNombre(String adminPrimerNombre) {
        this.adminPrimerNombre = adminPrimerNombre;
    }

    /**
     * @return the adminSegundoNombre
     */
    public String getAdminSegundoNombre() {
        return adminSegundoNombre;
    }

    /**
     * @param adminSegundoNombre the adminSegundoNombre to set
     */
    public void setAdminSegundoNombre(String adminSegundoNombre) {
        this.adminSegundoNombre = adminSegundoNombre;
    }

    /**
     * @return the adminPrimerApellido
     */
    public String getAdminPrimerApellido() {
        return adminPrimerApellido;
    }

    /**
     * @param adminPrimerApellido the adminPrimerApellido to set
     */
    public void setAdminPrimerApellido(String adminPrimerApellido) {
        this.adminPrimerApellido = adminPrimerApellido;
    }

    /**
     * @return the adminSegundoApellido
     */
    public String getAdminSegundoApellido() {
        return adminSegundoApellido;
    }

    /**
     * @param adminSegundoApellido the adminSegundoApellido to set
     */
    public void setAdminSegundoApellido(String adminSegundoApellido) {
        this.adminSegundoApellido = adminSegundoApellido;
    }

    /**
     * @return the medioDePago
     */
    public TipoMedioDePagoEnum getMedioDePago() {
        return medioDePago;
    }

    /**
     * @param medioDePago the medioDePago to set
     */
    public void setMedioDePago(TipoMedioDePagoEnum medioDePago) {
        this.medioDePago = medioDePago;
    }

    /**
     * @return the idGrupoFamiliar
     */
    public Long getIdGrupoFamiliar() {
        return idGrupoFamiliar;
    }

    /**
     * @param idGrupoFamiliar the idGrupoFamiliar to set
     */
    public void setIdGrupoFamiliar(Long idGrupoFamiliar) {
        this.idGrupoFamiliar = idGrupoFamiliar;
    }

    /**
     * @return the grupoMedioPago
     */
    public TipoMedioDePagoEnum getGrupoMedioPago() {
        return grupoMedioPago;
    }

    /**
     * @param grupoMedioPago the grupoMedioPago to set
     */
    public void setGrupoMedioPago(TipoMedioDePagoEnum grupoMedioPago) {
        this.grupoMedioPago = grupoMedioPago;
    }

    /**
     * @return the grupoSitioPago
     */
    public String getGrupoSitioPago() {
        return grupoSitioPago;
    }

    /**
     * @param grupoSitioPago the grupoSitioPago to set
     */
    public void setGrupoSitioPago(String grupoSitioPago) {
        this.grupoSitioPago = grupoSitioPago;
    }

    /**
     * @return the grupoInembargable
     */
    public Boolean getGrupoInembargable() {
        return grupoInembargable;
    }

    /**
     * @param grupoInembargable the grupoInembargable to set
     */
    public void setGrupoInembargable(Boolean grupoInembargable) {
        this.grupoInembargable = grupoInembargable;
    }

    /**
     * @return the codigoGrupoFamiliar
     */
    public String getCodigoGrupoFamiliar() {
        return codigoGrupoFamiliar;
    }

    /**
     * @param codigoGrupoFamiliar the codigoGrupoFamiliar to set
     */
    public void setCodigoGrupoFamiliar(String codigoGrupoFamiliar) {
        this.codigoGrupoFamiliar = codigoGrupoFamiliar;
    }

    /**
     * @return the dirIgualAfiPrincipal
     */
    public Boolean getDirIgualAfiPrincipal() {
        return dirIgualAfiPrincipal;
    }

    /**
     * @param dirIgualAfiPrincipal the dirIgualAfiPrincipal to set
     */
    public void setDirIgualAfiPrincipal(Boolean dirIgualAfiPrincipal) {
        this.dirIgualAfiPrincipal = dirIgualAfiPrincipal;
    }

    /**
     * @return the idMunicipio
     */
    public Short getIdMunicipio() {
        return idMunicipio;
    }

    /**
     * @param idMunicipio the idMunicipio to set
     */
    public void setIdMunicipio(Short idMunicipio) {
        this.idMunicipio = idMunicipio;
    }

    /**
     * @return the idDepartamento
     */
    public Short getIdDepartamento() {
        return idDepartamento;
    }

    /**
     * @param idDepartamento the idDepartamento to set
     */
    public void setIdDepartamento(Short idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    /**
     * @return the direccion
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * @param direccion the direccion to set
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
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
     * @return the telefonoCelular
     */
    public String getTelefonoCelular() {
        return telefonoCelular;
    }

    /**
     * @param telefonoCelular the telefonoCelular to set
     */
    public void setTelefonoCelular(String telefonoCelular) {
        this.telefonoCelular = telefonoCelular;
    }

    /**
     * @return the correo
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * @param correo the correo to set
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * @return the beneficiarios
     */
    public List<BeneficiarioGrupoFamiliarDTO> getBeneficiarios() {
        return beneficiarios;
    }

    /**
     * @param beneficiarios the beneficiarios to set
     */
    public void setBeneficiarios(List<BeneficiarioGrupoFamiliarDTO> beneficiarios) {
        
        this.beneficiarios = beneficiarios;
    }

    /**
     * @return the indicativoTelFijo
     */
    public String getIndicativoTelFijo() {
        return indicativoTelFijo;
    }

    /**
     * @param indicativoTelFijo the indicativoTelFijo to set
     */
    public void setIndicativoTelFijo(String indicativoTelFijo) {
        this.indicativoTelFijo = indicativoTelFijo;
    }

    /**
     * @return the fechaInicioAdminSubsidio
     */
    public Long getFechaInicioAdminSubsidio() {
        return fechaInicioAdminSubsidio;
    }

    /**
     * @param fechaInicioAdminSubsidio the fechaInicioAdminSubsidio to set
     */
    public void setFechaInicioAdminSubsidio(Long fechaInicioAdminSubsidio) {
        this.fechaInicioAdminSubsidio = fechaInicioAdminSubsidio;
    }

    /**
     * @return the medioPagoActivo
     */
    public Boolean getMedioPagoActivo() {
        return medioPagoActivo;
    }
    /**
     * @param medioPagoActivo the medioPagoActivo to set
     */
    public void setMedioPagoActivo(Boolean medioPagoActivo) {
        this.medioPagoActivo = medioPagoActivo;
    }

    public Long getIdAdministradorSubsidio() {
        return idAdministradorSubsidio;
    }

    public void setIdAdministradorSubsidio(Long idAdministradorSubsidio) {
        this.idAdministradorSubsidio = idAdministradorSubsidio;
    }

    public MedioDePagoModeloDTO getMedioDePagoDTO() {
        return medioDePagoDTO;
    }

    public void setMedioDePagoDTO(MedioDePagoModeloDTO medioDePagoDTO) {
        this.medioDePagoDTO = medioDePagoDTO;
    }
    

    public Boolean getTarjetaMultiservicio() {
        return this.tarjetaMultiservicio;
    }

    public void setTarjetaMultiservicio(Boolean tarjetaMultiservicio) {
        this.tarjetaMultiservicio = tarjetaMultiservicio;
    }
    

    public String obtenerNombreCompleto(){
        StringBuilder nombreCompleto = new StringBuilder();
        
        if(adminPrimerNombre != null){
            nombreCompleto.append(adminPrimerNombre+" ");
        }
        if(adminSegundoNombre != null){
            nombreCompleto.append(adminSegundoNombre+" ");
        }
        if(adminPrimerApellido != null){
           nombreCompleto.append(adminPrimerApellido+" ");
        }
        if(adminSegundoApellido != null){
            nombreCompleto.append(adminSegundoApellido);
         }
        return nombreCompleto.toString();
    }
}
