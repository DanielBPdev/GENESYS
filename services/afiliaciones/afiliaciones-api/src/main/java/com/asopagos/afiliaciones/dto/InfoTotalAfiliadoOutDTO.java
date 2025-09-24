package com.asopagos.afiliaciones.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import com.asopagos.enumeraciones.afiliaciones.MotivoDesafiliacionAfiliadoEnum;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.personas.CategoriaPersonaEnum;
import com.asopagos.enumeraciones.personas.ClaseIndependienteEnum;
import com.asopagos.enumeraciones.personas.ClaseTrabajadorEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.EstadoCivilEnum;
import com.asopagos.enumeraciones.personas.GeneroEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
//@JsonInclude(Include.NON_EMPTY)
public class InfoTotalAfiliadoOutDTO implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    /**
    * Tipo de afiliado (Dependiente, Independiente, Pensionado)
    */
    private TipoAfiliadoEnum tipoAfiliado;
    
    /**
    * define que tipo a que tipo de independiente pertenece el afiliado ('TAXISTA', 'VOLUNTARIO', 'MIGRANTE_RETORNADO', 'MIGRANTE', 'INDEPENDIENTE_REGULAR')
    */
    private ClaseIndependienteEnum claseIndependiente;
    
    /**
    * define la modalidad de trabdjor a la que pertenece el dependiente ('TRABAJADOR_POR_DIAS', 'REGULAR', 'SERVICIO_DOMESTICO', 'MADRE_COMUNITARIA')
    */
    private ClaseTrabajadorEnum claseTrabajador;
    
    /**
    * Indica si la persona consultada es el afiliado principal o no.
    */
    private Boolean afiliadoPrincipal;
    
    /**
    * Tipo de identificación del afiliado
    */
    private TipoIdentificacionEnum tipoID;
    
    /**
    * Número de identificación del afiliado
    */
    private String identificacion;
    
    /**
    * Primer Nombre del afiliado
    */
    private String primerNombre;
    
    /**
    * Segundo nombre del afiliado
    */
    private String segundoNombre;
    
    /**
    * Primer Apellido del afiliado
    */
    private String primerApellido;
    
    /**
    * Segundo Apellido del afiliado
    */
    private String segundoApellido;
    
    /**
     * Nombre completo del afiliado 
     */
    private String nombreCompleto;
    
    /**
    * Fecha de nacimiento del afiliado
    */
    private String fechaNacimiento;
    
    /**
     * Edad del afiliado
     */
    private int edad;
    
    /**
    * Indica la fecha de fallecimiento del afiliado
    */
    private String fechaFallecido;
    
    /**
    * Estado Civil del afiliado
    */
    private EstadoCivilEnum estadoCivil;
    
    /**
    * Género del afiliado
    */
    private GeneroEnum genero;
    
    /**
    * Cantidad de hijos del afiliado
    */
    private Short numeroHijos;
    
    /**
     * Indica si el afiliado tiene o no invalidez reportada
     */
    private Boolean marcaCondicionInvalidez;
    
    /**
    * Dirección física principal del afiliado
    */
    private String direccionResidencia;
    
    /**
    * Indica si el afiliado habita en casa propia(propia= S , otra =N)
    */
    private Boolean habitaCasaPropia;
    
    /**
    * Código DANE del Municipio de ubicación del afiliado
    */
    private String municipioCodigo;
    
    /**
    * Nombre DANE del Municipio de ubicación del afiliado
    */
    private String municipioNombre;
    
    /**
    * Código DANE del departamento de ubicación del afiliado
    */
    private String departamentoCodigo;
    
    /**
    * Nombre DANE del departamento de ubicación del afiliado
    */
    private String departamentoNombre;
    
    /**
    * Código postal definido para la ubicación del afiliado
    */
    private String codigoPostal;
    
    /**
    * Indicativo del teléfono fijo + número fijo del afiliado
    */
    private String telefonoFijo;
    
    /**
    * Número telefónico del celular del afiliado
    */
    private String celular;
    
    /**
    * Correo electrónico del afiliado
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
    * Valor de la mesada salarial de la persona pagado por una empresa
    */
    private int salario;
    
    /**
    * Porcentaje de aporte (independiente)
    */
    private BigDecimal porcentajeAporte;
    
    /**
    * Cargo que desempeña el afiliado en la empresa
    */
    private String cargo;
    
    /**
    * Categoría de la persona (Categoría actual para la persona consultada)
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
    * Fecha en la que la persona ingresó a la empresa
    */
    private String fechaIngresoEmpresa;
    
    /**
    * Fecha en la que se realizó la afiliación a la CCF
    */
    private String fechaAfiliacionCCF;
    
    /**
    * Fecha de retiro de la empresa y desafiliación de la CCF
    */
    private String fechaRetiro;
    
    /**
    * Motivo de la desafiliación de la persona
    */
    private MotivoDesafiliacionAfiliadoEnum motivoDesafiliacion;
    
    /**
    * Formato YYYY-MM indica el último periodo de pago de aportes registrado en la CCF
    */
    private String ultimoPeriodoPagoAportes;
    
    /**
    * Inhabilitado para recibir subsidio (trabajador dependiente)
    */
    private String inhabilitadoSubsidio;
    
    /**
    * Número de la tarjeta Multiservicios de la persona
    */
    private String numeroTarjeta;
    
    /**
    * Fecha en la que la CCF realizó el último pago de cuota monetaria a la persona
    */
    private String ultimoPagoCuotaMonetaria;
    
    /**
    * Fecha de expedición del número de documento de la persona
    */
    private String fechaExpedicionDocumento;
    
    /**
    * Nivel educativo de la persona
    */
    private String nivelEducativo;
    
    /**
    * indica el grado en el cual se encuentra el afiliado dentro del nivel educativo correspondiente.
    */
    private String gradoAcademico;
    
    /**
    * indica si el afiliado es cabeza de familia
    */
    private Boolean cabezahogar;
    
    /**
    * Horas laboradas al mes por afiliado
    */
    private Short horaslaboradasMes;
    
    /**
    * Fecha de creación del registro en la solicitud de afiliación
    */
    private String fechaCreacionRegistro;
    
    /**
    * Usuario que gestiona la solicitud de afiliación
    */
    private String usuarioGestionRegistro;
    
    /**
    * Tipo de identificación del empleador
    */
    private TipoIdentificacionEnum tipoIdentificacionEmpleador;
    
    /**
    * Numero de identificacion del empleador
    */
    private String numeroIdentificacionEmpleador;
    
    /**
    * Dígito de verificación del NIT para empleadores
    */
    private String digitoVerificacion;
    
    /**
    * Nombre o Razón social del empleador
    */
    private String nombreEmpleador;
    
    /**
    * Sucursal del empleador asociada al afiliado
    */
    private String sucursalEmpleador;
    
    /**
    * Nombre de la sucursal del empleador asociada al afiliado
    */
    private String nombreSucursalEmpleador;

    /**
     * 
     */
    public InfoTotalAfiliadoOutDTO() {
    }
    
    /**
     * @param tipoAfiliado
     * @param claseIndependiente
     * @param claseTrabajador
     * @param afiliadoPrincipal
     * @param tipoID
     * @param identificacion
     * @param primerNombre
     * @param segundoNombre
     * @param primerApellido
     * @param segundoApellido
     * @param fechaNacimiento
     * @param fechaFallecido
     * @param estadoCivil
     * @param genero
     * @param numeroHijos
     * @param direccionResidencia
     * @param habitaCasaPropia
     * @param municipioCodigo
     * @param municipioNombre
     * @param departamentoCodigo
     * @param departamentoNombre
     * @param codigoPostal
     * @param telefonoFijo
     * @param celular
     * @param correoElectronico
     * @param autorizacionEnvioEmail
     * @param autorizacionDatosPersonales
     * @param codigoCCF
     * @param salario
     * @param porcentajeAporte
     * @param cargo
     * @param categoria
     * @param clasificacion
     * @param estadoAfiliacion
     * @param fechaIngresoEmpresa
     * @param fechaAfiliacionCCF
     * @param fechaRetiro
     * @param motivoDesafiliacion
     * @param ultimoPeriodoPagoAportes
     * @param inhabilitadoSubsidio
     * @param numeroTarjeta
     * @param ultimoPagoCuotaMonetaria
     * @param fechaExpedicionDocumento
     * @param nivelEducativo
     * @param gradoAcademico
     * @param cabezahogar
     * @param horaslaboradasMes
     * @param fechaCreacionRegistro
     * @param usuarioGestionRegistro
     * @param tipoIdentificacionEmpleador
     * @param numeroIdentificacionEmpleador
     * @param digitoVerificacion
     * @param nombreEmpleador
     * @param sucursalEmpleador
     * @param nombreSucursalEmpleador
     */
    public InfoTotalAfiliadoOutDTO(TipoAfiliadoEnum tipoAfiliado, ClaseIndependienteEnum claseIndependiente,
            ClaseTrabajadorEnum claseTrabajador, Boolean afiliadoPrincipal, TipoIdentificacionEnum tipoID, String identificacion,
            String primerNombre, String segundoNombre, String primerApellido, String segundoApellido, String fechaNacimiento,
            String fechaFallecido, EstadoCivilEnum estadoCivil, GeneroEnum genero, Short numeroHijos, String direccionResidencia,
            Boolean habitaCasaPropia, String municipioCodigo, String municipioNombre, String departamentoCodigo, String departamentoNombre,
            String codigoPostal, String telefonoFijo, String celular, String correoElectronico, Boolean autorizacionEnvioEmail,
            Boolean autorizacionDatosPersonales, String codigoCCF, int salario, BigDecimal porcentajeAporte, String cargo,
            CategoriaPersonaEnum categoria, ClasificacionEnum clasificacion, EstadoAfiliadoEnum estadoAfiliacion,
            String fechaIngresoEmpresa, String fechaAfiliacionCCF, String fechaRetiro, MotivoDesafiliacionAfiliadoEnum motivoDesafiliacion,
            String ultimoPeriodoPagoAportes, String inhabilitadoSubsidio, String numeroTarjeta, String ultimoPagoCuotaMonetaria,
            String fechaExpedicionDocumento, String nivelEducativo, String gradoAcademico, Boolean cabezahogar, Short horaslaboradasMes,
            String fechaCreacionRegistro, String usuarioGestionRegistro, TipoIdentificacionEnum tipoIdentificacionEmpleador,
            String numeroIdentificacionEmpleador, String digitoVerificacion, String nombreEmpleador, String sucursalEmpleador,
            String nombreSucursalEmpleador) {
        this.tipoAfiliado = tipoAfiliado;
        this.claseIndependiente = claseIndependiente;
        this.claseTrabajador = claseTrabajador;
        this.afiliadoPrincipal = afiliadoPrincipal;
        this.tipoID = tipoID;
        this.identificacion = identificacion;
        this.primerNombre = primerNombre;
        this.segundoNombre = segundoNombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaFallecido = fechaFallecido;
        this.estadoCivil = estadoCivil;
        this.genero = genero;
        this.numeroHijos = numeroHijos;
        this.direccionResidencia = direccionResidencia;
        this.habitaCasaPropia = habitaCasaPropia;
        this.municipioCodigo = municipioCodigo;
        this.municipioNombre = municipioNombre;
        this.departamentoCodigo = departamentoCodigo;
        this.departamentoNombre = departamentoNombre;
        this.codigoPostal = codigoPostal;
        this.telefonoFijo = telefonoFijo;
        this.celular = celular;
        this.correoElectronico = correoElectronico;
        this.autorizacionEnvioEmail = autorizacionEnvioEmail;
        this.autorizacionDatosPersonales = autorizacionDatosPersonales;
        this.codigoCCF = codigoCCF;
        this.salario = salario;
        this.porcentajeAporte = porcentajeAporte;
        this.cargo = cargo;
        this.categoria = categoria;
        this.clasificacion = clasificacion;
        this.estadoAfiliacion = estadoAfiliacion;
        this.fechaIngresoEmpresa = fechaIngresoEmpresa;
        this.fechaAfiliacionCCF = fechaAfiliacionCCF;
        this.fechaRetiro = fechaRetiro;
        this.motivoDesafiliacion = motivoDesafiliacion;
        this.ultimoPeriodoPagoAportes = ultimoPeriodoPagoAportes;
        this.inhabilitadoSubsidio = inhabilitadoSubsidio;
        this.numeroTarjeta = numeroTarjeta;
        this.ultimoPagoCuotaMonetaria = ultimoPagoCuotaMonetaria;
        this.fechaExpedicionDocumento = fechaExpedicionDocumento;
        this.nivelEducativo = nivelEducativo;
        this.gradoAcademico = gradoAcademico;
        this.cabezahogar = cabezahogar;
        this.horaslaboradasMes = horaslaboradasMes;
        this.fechaCreacionRegistro = fechaCreacionRegistro;
        this.usuarioGestionRegistro = usuarioGestionRegistro;
        this.tipoIdentificacionEmpleador = tipoIdentificacionEmpleador;
        this.numeroIdentificacionEmpleador = numeroIdentificacionEmpleador;
        this.digitoVerificacion = digitoVerificacion;
        this.nombreEmpleador = nombreEmpleador;
        this.sucursalEmpleador = sucursalEmpleador;
        this.nombreSucursalEmpleador = nombreSucursalEmpleador;
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
     * @return the claseIndependiente
     */
    public ClaseIndependienteEnum getClaseIndependiente() {
        return claseIndependiente;
    }

    /**
     * @param claseIndependiente the claseIndependiente to set
     */
    public void setClaseIndependiente(ClaseIndependienteEnum claseIndependiente) {
        this.claseIndependiente = claseIndependiente;
    }

    /**
     * @return the claseTrabajador
     */
    public ClaseTrabajadorEnum getClaseTrabajador() {
        return claseTrabajador;
    }

    /**
     * @param claseTrabajador the claseTrabajador to set
     */
    public void setClaseTrabajador(ClaseTrabajadorEnum claseTrabajador) {
        this.claseTrabajador = claseTrabajador;
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
    public EstadoCivilEnum getEstadoCivil() {
        return estadoCivil;
    }

    /**
     * @param estadoCivil the estadoCivil to set
     */
    public void setEstadoCivil(EstadoCivilEnum estadoCivil) {
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
     * @return the numeroHijos
     */
    public Short getNumeroHijos() {
        return numeroHijos;
    }

    /**
     * @param numeroHijos the numeroHijos to set
     */
    public void setNumeroHijos(Short numeroHijos) {
        this.numeroHijos = numeroHijos;
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
     * @return the habitaCasaPropia
     */
    public Boolean getHabitaCasaPropia() {
        return habitaCasaPropia;
    }

    /**
     * @param habitaCasaPropia the habitaCasaPropia to set
     */
    public void setHabitaCasaPropia(Boolean habitaCasaPropia) {
        this.habitaCasaPropia = habitaCasaPropia;
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
     * @return the salario
     */
    public int getSalario() {
        return salario;
    }

    /**
     * @param salario the salario to set
     */
    public void setSalario(int salario) {
        this.salario = salario;
    }

    /**
     * @return the porcentajeAporte
     */
    public BigDecimal getPorcentajeAporte() {
        return porcentajeAporte;
    }

    /**
     * @param porcentajeAporte the porcentajeAporte to set
     */
    public void setPorcentajeAporte(BigDecimal porcentajeAporte) {
        this.porcentajeAporte = porcentajeAporte;
    }

    /**
     * @return the cargo
     */
    public String getCargo() {
        return cargo;
    }

    /**
     * @param cargo the cargo to set
     */
    public void setCargo(String cargo) {
        this.cargo = cargo;
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
     * @return the fechaIngresoEmpresa
     */
    public String getFechaIngresoEmpresa() {
        return fechaIngresoEmpresa;
    }

    /**
     * @param fechaIngresoEmpresa the fechaIngresoEmpresa to set
     */
    public void setFechaIngresoEmpresa(String fechaIngresoEmpresa) {
        this.fechaIngresoEmpresa = fechaIngresoEmpresa;
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
    public MotivoDesafiliacionAfiliadoEnum getMotivoDesafiliacion() {
        return motivoDesafiliacion;
    }

    /**
     * @param motivoDesafiliacion the motivoDesafiliacion to set
     */
    public void setMotivoDesafiliacion(MotivoDesafiliacionAfiliadoEnum motivoDesafiliacion) {
        this.motivoDesafiliacion = motivoDesafiliacion;
    }

    /**
     * @return the ultimoPeriodoPagoAportes
     */
    public String getUltimoPeriodoPagoAportes() {
        return ultimoPeriodoPagoAportes;
    }

    /**
     * @param ultimoPeriodoPagoAportes the ultimoPeriodoPagoAportes to set
     */
    public void setUltimoPeriodoPagoAportes(String ultimoPeriodoPagoAportes) {
        this.ultimoPeriodoPagoAportes = ultimoPeriodoPagoAportes;
    }

    /**
     * @return the inhabilitadoSubsidio
     */
    public String getInhabilitadoSubsidio() {
        return inhabilitadoSubsidio;
    }

    /**
     * @param inhabilitadoSubsidio the inhabilitadoSubsidio to set
     */
    public void setInhabilitadoSubsidio(String inhabilitadoSubsidio) {
        this.inhabilitadoSubsidio = inhabilitadoSubsidio;
    }

    /**
     * @return the numeroTarjeta
     */
    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    /**
     * @param numeroTarjeta the numeroTarjeta to set
     */
    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
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
     * @return the fechaExpedicionDocumento
     */
    public String getFechaExpedicionDocumento() {
        return fechaExpedicionDocumento;
    }

    /**
     * @param fechaExpedicionDocumento the fechaExpedicionDocumento to set
     */
    public void setFechaExpedicionDocumento(String fechaExpedicionDocumento) {
        this.fechaExpedicionDocumento = fechaExpedicionDocumento;
    }

    /**
     * @return the nivelEducativo
     */
    public String getNivelEducativo() {
        return nivelEducativo;
    }

    /**
     * @param nivelEducativo the nivelEducativo to set
     */
    public void setNivelEducativo(String nivelEducativo) {
        this.nivelEducativo = nivelEducativo;
    }

    /**
     * @return the gradoAcademico
     */
    public String getGradoAcademico() {
        return gradoAcademico;
    }

    /**
     * @param gradoAcademico the gradoAcademico to set
     */
    public void setGradoAcademico(String gradoAcademico) {
        this.gradoAcademico = gradoAcademico;
    }

    /**
     * @return the cabezahogar
     */
    public Boolean getCabezahogar() {
        return cabezahogar;
    }

    /**
     * @param cabezahogar the cabezahogar to set
     */
    public void setCabezahogar(Boolean cabezahogar) {
        this.cabezahogar = cabezahogar;
    }

    /**
     * @return the horaslaboradasMes
     */
    public Short getHoraslaboradasMes() {
        return horaslaboradasMes;
    }

    /**
     * @param horaslaboradasMes the horaslaboradasMes to set
     */
    public void setHoraslaboradasMes(Short horaslaboradasMes) {
        this.horaslaboradasMes = horaslaboradasMes;
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
     * @return the usuarioGestionRegistro
     */
    public String getUsuarioGestionRegistro() {
        return usuarioGestionRegistro;
    }

    /**
     * @param usuarioGestionRegistro the usuarioGestionRegistro to set
     */
    public void setUsuarioGestionRegistro(String usuarioGestionRegistro) {
        this.usuarioGestionRegistro = usuarioGestionRegistro;
    }

    /**
     * @return the tipoIdentificacionEmpleador
     */
    public TipoIdentificacionEnum getTipoIdentificacionEmpleador() {
        return tipoIdentificacionEmpleador;
    }

    /**
     * @param tipoIdentificacionEmpleador the tipoIdentificacionEmpleador to set
     */
    public void setTipoIdentificacionEmpleador(TipoIdentificacionEnum tipoIdentificacionEmpleador) {
        this.tipoIdentificacionEmpleador = tipoIdentificacionEmpleador;
    }

    /**
     * @return the numeroIdentificacionEmpleador
     */
    public String getNumeroIdentificacionEmpleador() {
        return numeroIdentificacionEmpleador;
    }

    /**
     * @param numeroIdentificacionEmpleador the numeroIdentificacionEmpleador to set
     */
    public void setNumeroIdentificacionEmpleador(String numeroIdentificacionEmpleador) {
        this.numeroIdentificacionEmpleador = numeroIdentificacionEmpleador;
    }

    /**
     * @return the digitoVerificacion
     */
    public String getDigitoVerificacion() {
        return digitoVerificacion;
    }

    /**
     * @param digitoVerificacion the digitoVerificacion to set
     */
    public void setDigitoVerificacion(String digitoVerificacion) {
        this.digitoVerificacion = digitoVerificacion;
    }

    /**
     * @return the nombreEmpleador
     */
    public String getNombreEmpleador() {
        return nombreEmpleador;
    }

    /**
     * @param nombreEmpleador the nombreEmpleador to set
     */
    public void setNombreEmpleador(String nombreEmpleador) {
        this.nombreEmpleador = nombreEmpleador;
    }

    /**
     * @return the sucursalEmpleador
     */
    public String getSucursalEmpleador() {
        return sucursalEmpleador;
    }

    /**
     * @param sucursalEmpleador the sucursalEmpleador to set
     */
    public void setSucursalEmpleador(String sucursalEmpleador) {
        this.sucursalEmpleador = sucursalEmpleador;
    }

    /**
     * @return the nombreSucursalEmpleador
     */
    public String getNombreSucursalEmpleador() {
        return nombreSucursalEmpleador;
    }

    /**
     * @param nombreSucursalEmpleador the nombreSucursalEmpleador to set
     */
    public void setNombreSucursalEmpleador(String nombreSucursalEmpleador) {
        this.nombreSucursalEmpleador = nombreSucursalEmpleador;
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
	 * @return the marcaCondicionInvalidez
	 */
	public Boolean getMarcaCondicionInvalidez() {
		return marcaCondicionInvalidez;
	}

	/**
	 * @param marcaCondicionInvalidez the marcaCondicionInvalidez to set
	 */
	public void setMarcaCondicionInvalidez(Boolean marcaCondicionInvalidez) {
		this.marcaCondicionInvalidez = marcaCondicionInvalidez;
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

    /*@Override
    public String toString() {
        return "{" +
            " tipoBeneficiario='" + getTipoBeneficiario() + "'" +
            ", tipoID='" + getTipoID() + "'" +
            ", identificacion='" + getIdentificacion() + "'" +
            ", primerNombre='" + getPrimerNombre() + "'" +
            ", segundoNombre='" + getSegundoNombre() + "'" +
            ", primerApellido='" + getPrimerApellido() + "'" +
            ", segundoApellido='" + getSegundoApellido() + "'" +
            ", nombreCompleto='" + getNombreCompleto() + "'" +
            ", fechaNacimiento='" + getFechaNacimiento() + "'" +
            ", edad='" + getEdad() + "'" +
            ", fechaFallecido='" + getFechaFallecido() + "'" +
            ", estadoCivil='" + getEstadoCivil() + "'" +
            ", genero='" + getGenero() + "'" +
            ", direccionResidencia='" + getDireccionResidencia() + "'" +
            ", municipioCodigo='" + getMunicipioCodigo() + "'" +
            ", municipioNombre='" + getMunicipioNombre() + "'" +
            ", departamentoCodigo='" + getDepartamentoCodigo() + "'" +
            ", departamentoNombre='" + getDepartamentoNombre() + "'" +
            ", codigoPostal='" + getCodigoPostal() + "'" +
            ", telefonoFijo='" + getTelefonoFijo() + "'" +
            ", celular='" + getCelular() + "'" +
            ", correoElectronico='" + getCorreoElectronico() + "'" +
            ", codigoCCF='" + getCodigoCCF() + "'" +
            ", categoria='" + getCategoria() + "'" +
            ", clasificacion='" + getClasificacion() + "'" +
            ", estadoAfiliacion='" + getEstadoAfiliacion() + "'" +
            ", grupoFamiliar='" + getGrupoFamiliar() + "'" +
            ", fechaAfiliacionCCF='" + getFechaAfiliacionCCF() + "'" +
            ", fechaRetiro='" + getFechaRetiro() + "'" +
            ", motivoDesafiliacion='" + getMotivoDesafiliacion() + "'" +
            ", inhabilitadoSubsidio='" + this.inhabilitadoSubsidio + "'" +
            ", ultimoPagoCuotaMonetaria='" + getUltimoPagoCuotaMonetaria() + "'" +
            ", condicionInvalidez='" + this.condicionInvalidez + "'" +
            ", fechaCreacionRegistro='" + getFechaCreacionRegistro() + "'" +
            ", usuarioCreacionRegistro='" + getUsuarioCreacionRegistro() + "'" +
            ", tipoAfiliado='" + getTipoAfiliado() + "'" +
            ", tipoIDAfiliado='" + getTipoIDAfiliado() + "'" +
            ", identificacionAfiliado='" + getIdentificacionAfiliado() + "'" +
            ", primerNombreAfiliado='" + getPrimerNombreAfiliado() + "'" +
            ", segundoNombreAfiliado='" + getSegundoNombreAfiliado() + "'" +
            ", primerApellidoAfiliado='" + getPrimerApellidoAfiliado() + "'" +
            ", segundoApellidoAfiliado='" + getSegundoApellidoAfiliado() + "'" +
            ", fechaVencimientoCertificado='" + getFechaVencimientoCertificado() + "'" +
            ", DTOArregloCertificadoEscolaridad='" + getDTOArregloCertificadoEscolaridad() + "'" +
            "}";
    } */
}
