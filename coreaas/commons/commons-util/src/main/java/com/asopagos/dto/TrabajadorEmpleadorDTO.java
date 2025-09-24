package com.asopagos.dto;

import java.util.Date;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.util.PersonasUtils;

public class TrabajadorEmpleadorDTO implements Comparable<TrabajadorEmpleadorDTO>{

    private TipoIdentificacionEnum tipoIdentificacion;

    private String numeroIdentificacion;

    private String nombre;

    private Date fechaIngreso;

    private Date fechaRetiro;

    private EstadoAfiliadoEnum estadoAfiliadoEnum;

    private String nombreSucursalEmpleador;

    private Boolean afiliable;

    //	@NotNull(groups = { GrupoCreacion.class, GrupoActualizacion.class })
    private Long idRolAfiliado;

    private Date fechaNacimiento;

    /* Se agregan campos necesarios para proceso de Novedades */
    /**
     * Primer nombre del trabajador.
     */
    private String primerNombre;
    /**
     * Segundo nombre del trabajador.
     */
    private String segundoNombre;
    /**
     * Primer apellido del trabajador.
     */
    private String primerApellido;
    /**
     * Segundo apellido del trabajador.
     */
    private String segundoApellido;
    /**
     * Id de la sucursal empleador.
     */
    private Long idSucursalEmpleador;
    /**
     * Codigo sucursal del empleador.
     */
    private String codigoSucursalEmpleador;
    /**
     * Id de la persona.
     */
    private Long idPersona;
    /**
     * Id de la empresa
     */
    private Long idEmpresa;

    private String totalRegistros;

    public TrabajadorEmpleadorDTO() {
    }

    public TrabajadorEmpleadorDTO(EstadoAfiliadoEnum estadoAfiliado, Date fechaIngreso, Date fechaRetiro, Date fechaNacimiento,
            Persona personaAfiliado, String nombreComercial, Long idRolAfiliado, Long idEmpresa) {
        super();
        this.setAfiliable(!EstadoAfiliadoEnum.ACTIVO.equals(estadoAfiliado));
        this.setFechaIngreso(fechaIngreso);
        this.setFechaRetiro(fechaRetiro);
        this.setFechaNacimiento(fechaNacimiento);
        this.setNombre(PersonasUtils.obtenerNombreORazonSocial(personaAfiliado));
        this.setNombreSucursalEmpleador(nombreComercial);
        this.setNumeroIdentificacion(personaAfiliado.getNumeroIdentificacion());
        this.setTipoIdentificacion(personaAfiliado.getTipoIdentificacion());
        this.setIdRolAfiliado(idRolAfiliado);
        this.setIdPersona(personaAfiliado.getIdPersona());
        this.setIdEmpresa(idEmpresa);
        // Se agregan los nombres
        this.setPrimerApellido(personaAfiliado.getPrimerApellido());
        this.setPrimerNombre(personaAfiliado.getPrimerNombre());
        this.setSegundoApellido(personaAfiliado.getSegundoApellido());
        this.setSegundoNombre(personaAfiliado.getSegundoNombre());
    }
    
    public TrabajadorEmpleadorDTO(Persona personaAfiliado, String nombreComercial, Long idEmpresa){
        super();
        this.setNombre(PersonasUtils.obtenerNombreORazonSocial(personaAfiliado));
        this.setNumeroIdentificacion(personaAfiliado.getNumeroIdentificacion());
        this.setTipoIdentificacion(personaAfiliado.getTipoIdentificacion());
        this.setIdPersona(personaAfiliado.getIdPersona());
        this.setNombreSucursalEmpleador(nombreComercial);
        this.setIdEmpresa(idEmpresa);
        this.setAfiliable(Boolean.TRUE); // en este punto siempre es No Formalizado Sin Afiliación con Aportes
        // Se agregan los nombres
        this.setPrimerApellido(personaAfiliado.getPrimerApellido());
        this.setPrimerNombre(personaAfiliado.getPrimerNombre());
        this.setSegundoApellido(personaAfiliado.getSegundoApellido());
        this.setSegundoNombre(personaAfiliado.getSegundoNombre());
    }

    /**
     * Constructor usado en la transformación de resultados de consulta
     * @param tipoIdentificacion
     *        Cadena de texto con el tipo de identificación del trabajador
     * @param numeroIdentificacion
     *        Numero de identificacion del trabajador
     * @param nombre
     *        Razon social del trabajador
     * @param estadoAfiliadoEnum
     *        Cadena de texto con el estado del afiliado
     * @param nombreSucursalEmpleador
     *        Nombre comercial empresa
     * @param primerNombre
     *        Primer nombre trabajador
     * @param segundoNombre
     *        Segundo nombre trabajador
     * @param primerApellido
     *        Primer apellido trabajador
     * @param segundoApellido
     *        Segundo apellido trabajador
     * @param idPersona
     *        Identificador persona trabajador
     * @param idEmpresa
     *        Identificador empresa
     */
    public TrabajadorEmpleadorDTO(String tipoIdentificacion, String numeroIdentificacion, String nombre, String estadoAfiliadoEnum,
            String nombreSucursalEmpleador, String primerNombre, String segundoNombre, String primerApellido, String segundoApellido,
            Long idPersona, Long idEmpresa, Date fechaIngreso, Date fechaRetiro) {
        super();
        if (tipoIdentificacion != null) {
            this.tipoIdentificacion = TipoIdentificacionEnum.valueOf(tipoIdentificacion);
        }
        this.numeroIdentificacion = numeroIdentificacion;
        this.nombre = nombre;
        if (estadoAfiliadoEnum != null) {
            this.estadoAfiliadoEnum = EstadoAfiliadoEnum.valueOf(estadoAfiliadoEnum);
            this.setAfiliable(!EstadoAfiliadoEnum.ACTIVO.equals(this.estadoAfiliadoEnum));
        }
        this.nombreSucursalEmpleador = nombreSucursalEmpleador;
        this.primerNombre = primerNombre;
        this.segundoNombre = segundoNombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.idPersona = idPersona;
        this.idEmpresa = idEmpresa;
        this.fechaIngreso = fechaIngreso;
        this.fechaRetiro = fechaRetiro;
    }

        public TrabajadorEmpleadorDTO(String tipoIdentificacion, String numeroIdentificacion, String nombre, String estadoAfiliadoEnum,
            String nombreSucursalEmpleador, String primerNombre, String segundoNombre, String primerApellido, String segundoApellido,String totalRegistros,
            Long idPersona, Long idEmpresa, Date fechaIngreso, Date fechaRetiro) {
        super();
        if (tipoIdentificacion != null) {
            this.tipoIdentificacion = TipoIdentificacionEnum.valueOf(tipoIdentificacion);
        }
        this.numeroIdentificacion = numeroIdentificacion;
        this.nombre = nombre;
        if (estadoAfiliadoEnum != null) {
            this.estadoAfiliadoEnum = EstadoAfiliadoEnum.valueOf(estadoAfiliadoEnum);
            this.setAfiliable(!EstadoAfiliadoEnum.ACTIVO.equals(this.estadoAfiliadoEnum));
        }
        this.nombreSucursalEmpleador = nombreSucursalEmpleador;
        this.primerNombre = primerNombre;
        this.segundoNombre = segundoNombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.idPersona = idPersona;
        this.idEmpresa = idEmpresa;
        this.fechaIngreso = fechaIngreso;
        this.fechaRetiro = fechaRetiro;
        this.totalRegistros = totalRegistros;
    }

    /**
     * @return the tipoIdentificacion
     */
    public TipoIdentificacionEnum getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    /**
     * @param tipoIdentificacion
     *        the tipoIdentificacion to set
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
     * @param numeroIdentificacion
     *        the numeroIdentificacion to set
     */
    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
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
     * @return the fechaIngreso
     */
    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    /**
     * @param fechaIngreso
     *        the fechaIngreso to set
     */
    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    /**
     * @return the fechaRetiro
     */
    public Date getFechaRetiro() {
        return fechaRetiro;
    }

    /**
     * @param fechaRetiro
     *        the fechaRetiro to set
     */
    public void setFechaRetiro(Date fechaRetiro) {
        this.fechaRetiro = fechaRetiro;
    }

    /**
     * @return the estadoAfiliadoEnum
     */
    public EstadoAfiliadoEnum getEstadoAfiliadoEnum() {
        return estadoAfiliadoEnum;
    }

    /**
     * @param estadoAfiliadoEnum
     *        the estadoAfiliadoEnum to set
     */
    public void setEstadoAfiliadoEnum(EstadoAfiliadoEnum estadoAfiliadoEnum) {
        this.estadoAfiliadoEnum = estadoAfiliadoEnum;
    }

    /**
     * @return the nombreSucursalEmpleador
     */
    public String getNombreSucursalEmpleador() {
        return nombreSucursalEmpleador;
    }

    /**
     * @param nombreSucursalEmpleador
     *        the nombreSucursalEmpleador to set
     */
    public void setNombreSucursalEmpleador(String nombreSucursalEmpleador) {
        this.nombreSucursalEmpleador = nombreSucursalEmpleador;
    }

    /**
     * @return the afiliable
     */
    public Boolean getAfiliable() {
        return afiliable;
    }

    /**
     * @param afiliable
     *        the afiliable to set
     */
    public void setAfiliable(Boolean afiliable) {
        this.afiliable = afiliable;
    }

    /**
     * @return the idRolAfiliado
     */
    public Long getIdRolAfiliado() {
        return idRolAfiliado;
    }

    /**
     * @param idRolAfiliado
     *        the idRolAfiliado to set
     */
    public void setIdRolAfiliado(Long idRolAfiliado) {
        this.idRolAfiliado = idRolAfiliado;
    }

    /**
     * @return the fechaNacimiento
     */
    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    /**
     * @param fechaNacimiento
     *        the fechaNacimiento to set
     */
    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    /**
     * Método que retorna el valor de primerNombre.
     * @return valor de primerNombre.
     */
    public String getPrimerNombre() {
        return primerNombre;
    }

    /**
     * Método encargado de modificar el valor de primerNombre.
     * @param valor
     *        para modificar primerNombre.
     */
    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    /**
     * Método que retorna el valor de segundoNombre.
     * @return valor de segundoNombre.
     */
    public String getSegundoNombre() {
        return segundoNombre;
    }

    /**
     * Método encargado de modificar el valor de segundoNombre.
     * @param valor
     *        para modificar segundoNombre.
     */
    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    /**
     * Método que retorna el valor de primerApellido.
     * @return valor de primerApellido.
     */
    public String getPrimerApellido() {
        return primerApellido;
    }

    /**
     * Método encargado de modificar el valor de primerApellido.
     * @param valor
     *        para modificar primerApellido.
     */
    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    /**
     * Método que retorna el valor de segundoApellido.
     * @return valor de segundoApellido.
     */
    public String getSegundoApellido() {
        return segundoApellido;
    }

    /**
     * Método encargado de modificar el valor de segundoApellido.
     * @param valor
     *        para modificar segundoApellido.
     */
    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    /**
     * Método que retorna el valor de idSucursalEmpleador.
     * @return valor de idSucursalEmpleador.
     */
    public Long getIdSucursalEmpleador() {
        return idSucursalEmpleador;
    }

    /**
     * Método encargado de modificar el valor de idSucursalEmpleador.
     * @param valor
     *        para modificar idSucursalEmpleador.
     */
    public void setIdSucursalEmpleador(Long idSucursalEmpleador) {
        this.idSucursalEmpleador = idSucursalEmpleador;
    }

    /**
     * Método que retorna el valor de codigoSucursalEmpleador.
     * @return valor de codigoSucursalEmpleador.
     */
    public String getCodigoSucursalEmpleador() {
        return codigoSucursalEmpleador;
    }

    /**
     * Método encargado de modificar el valor de codigoSucursalEmpleador.
     * @param valor
     *        para modificar codigoSucursalEmpleador.
     */
    public void setCodigoSucursalEmpleador(String codigoSucursalEmpleador) {
        this.codigoSucursalEmpleador = codigoSucursalEmpleador;
    }

    /**
     * Método que retorna el valor de idPersona.
     * @return valor de idPersona.
     */
    public Long getIdPersona() {
        return idPersona;
    }

    /**
     * Método encargado de modificar el valor de idPersona.
     * @param valor
     *        para modificar idPersona.
     */
    public void setIdPersona(Long idPersona) {
        this.idPersona = idPersona;
    }

    /**
     * @return the idEmpresa
     */
    public Long getIdEmpresa() {
        return idEmpresa;
    }

    /**
     * @param idEmpresa
     *        the idEmpresa to set
     */
    public void setIdEmpresa(Long idEmpresa) {
        this.idEmpresa = idEmpresa;
    }
        /**
     * @return the totalRegistros
     */
    public String getTotalRegistros() {
        return totalRegistros;
    }

    /**
     * @param totalRegistros the totalRegistros to set
     */
    public void setTotalRegistros(String totalRegistros) {
        this.totalRegistros = totalRegistros;
    }  

	@Override
	public int compareTo(TrabajadorEmpleadorDTO o) {
		if(this.nombre != null){
			return this.nombre.compareToIgnoreCase(o.getNombre());
		}
		else{
			return 0;
		}
		
	}

}
