package com.asopagos.dto.modelo;

import java.io.Serializable;
import com.asopagos.entidades.ccf.core.Banco;
import com.asopagos.entidades.ccf.personas.MedioCheque;
import com.asopagos.entidades.ccf.personas.MedioConsignacion;
import com.asopagos.entidades.ccf.personas.MedioDePago;
import com.asopagos.entidades.ccf.personas.MedioEfectivo;
import com.asopagos.entidades.ccf.personas.MedioTarjeta;
import com.asopagos.entidades.ccf.personas.MedioTransferencia;
import com.asopagos.enumeraciones.personas.EstadoTarjetaMultiserviciosEnum;
import com.asopagos.enumeraciones.personas.SolicitudTarjetaEnum;
import com.asopagos.enumeraciones.personas.TipoCuentaEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.personas.TipoMedioDePagoEnum;

/**
 * DTO con los datos del Modelo de Medio de Pago
 * 
 * @author Fabian López <flopez@heinsohn.com.co>
 *
 */
public class MedioDePagoModeloDTO implements Serializable {

    private static final long serialVersionUID = -402978826438170616L;

    /**
	 * Código identificador de llave primaria del medio de pago.
	 */
	private Long idMedioDePago;

	/**
	 * Tipo de medio de Pago.
	 */
	private TipoMedioDePagoEnum tipoMedioDePago;

	/**
	 * Medio de Pago EFECTIVO Bandera si el medio de Pago es Efectivo.
	 */
	private Boolean efectivo;

	/**
	 * Medio de Pago EFECTIVO Identificador de la sede de la CCF
	 */
	private Long sede;

	/**
	 * Medio de Pago TARJETA Numero de Tarjeta del Medio Tarjeta.
	 */
	private String numeroTarjeta;

	/**
	 * Medio de Pago TARJETA Identifica si dispone de tarjeta el Medio Tarjeta
	 */
	private Boolean disponeTarjeta;

	/**
	 * Medio de Pago TARJETA Estado de Tarjeta asociada al Medio Tarjeta
	 */
	private EstadoTarjetaMultiserviciosEnum estadoTarjetaMultiservicios;

	/**
	 * Medio de Pago TARJETA Solicitud Tarjeta asociada al Medio Tarjeta.
	 */
	private SolicitudTarjetaEnum solicitudTarjeta;
	
    /**
     * Determina si hay cobro adicional
     */
    private Boolean cobroJudicial;

    /**
     * Informacion relacionada al cobro judicial
     */
    private String infoRelacionadaCobroJudicial;


	/**
	 * Medio de Pago TRANSFERENCIA Identificador del Banco asociado al Medio
	 * Transferencia.
	 */
	private Long idBanco;
	
    /**
     * Medio de Pago TRANSFERENCIA Nombre del Banco asociado al Medio
     * Transferencia.
     */
	private String nombreBanco;
	
    /**
     * Medio de Pago TRANSFERENCIA Código del Banco asociado al Medio
     * Transferencia.
     */
	private String codigoBanco;

	/**
	 * Medio de Pago TRANSFERENCIA Tipo de cuenta asociada al Medio
	 * Transferencia
	 */
	private TipoCuentaEnum tipoCuenta;

	/**
	 * Medio de Pago TRANSFERENCIA Numero de Cuenta asociada al Medio
	 * Transferencia.
	 */
	private String numeroCuenta;
	/**
	 * Medio de Pago TRANSFERENCIA Tipo Identificación del Titular de la Cuenta
	 */
	private TipoIdentificacionEnum tipoIdentificacionTitular;

	/**
	 * Medio de Pago TRANSFERENCIA Numero Identificación del Titular de la
	 * Cuenta
	 */
	private String numeroIdentificacionTitular;

	/**
	 * Medio de Pago TRANSFERENCIA Numero Identificación del Titular de la
	 * Cuenta
	 */
	private Short digitoVerificacionTitular;

	/**
	 * Medio de Pago TRANSFERENCIA Nombre del Titular de la Cuenta
	 */
	private String nombreTitularCuenta;

	/**
	 * Datos del Administrador subsidio
	 */
	private PersonaModeloDTO admonSubsidio;

	/**
	 * Datos del Afiliado Principal.
	 */
	private PersonaModeloDTO persona;

	/**
	 * Identificador del Grupo Familiar
	 */
	private Long idGrupoFamiliar;

	/**
	 * Identifica si el Afiliado es el mismo Administrador del Subsidio.
	 */
	private Boolean afiliadoEsAdministradorSubsidio;

	/**
	 * Relacion del Grupo Familiar al Administrador del Subsidio.
	 */
	private Short idRelacionGrupoFamiliar;

	/**
	 * Referencia al Sitio de Pago
	 */
	private Long sitioPago;

	/**
	 * Referencia al objeto banco
	 */
	private BancoModeloDTO bancoModeloDTO;

	private Boolean tarjetaMultiservicio;
        
	private String ctaoficinajuzgado;
        
	private String cdgoficinajuzgado;
        
	private String nmboficinajuzgado;
        
	private String nroradicadojuzgado;
	
	private String nrodeloficiojudicial;

	private String tipoSolicitud;

	private Long idMEdioDePagoActualizar;

	private Boolean seleccionado;

	private String nombreSitioPago;

	private String marcaAfiliacionMasiva;

	public Boolean isSeleccionado() {
		return this.seleccionado;
	}

	public Boolean getSeleccionado() {
		return this.seleccionado;
	}

	public void setSeleccionado(Boolean seleccionado) {
		this.seleccionado = seleccionado;
	}
 
        
        public String getCdgoficinajuzgado() {
            return cdgoficinajuzgado;
        }

        public void setCdgoficinajuzgado(String cdgoficinajuzgado) {
            this.cdgoficinajuzgado = cdgoficinajuzgado;
        }
        
        public String getNmboficinajuzgado() {
            return nmboficinajuzgado;
        }

        public void setNmboficinajuzgado(String nmboficinajuzgado) {
            this.nmboficinajuzgado = nmboficinajuzgado;
        }
        
        public String getCtaoficinajuzgado() {
            return ctaoficinajuzgado;
        }

        public void setCtaoficinajuzgado(String ctaoficinajuzgado) {
            this.ctaoficinajuzgado = ctaoficinajuzgado;
        }
        
        public String getNroradicadojuzgado() {
            return nroradicadojuzgado;
        }

        public void setNroradicadojuzgado(String nroradicadojuzgado) {
            this.nroradicadojuzgado = nroradicadojuzgado;
        }

        public String getNrodeloficiojudicial() {
            return nrodeloficiojudicial;
        }

        public void setNrodeloficiojudicial(String nrodeloficiojudicial) {
            this.nrodeloficiojudicial = nrodeloficiojudicial;
        }

	public MedioDePagoModeloDTO() {
		super();
	}
	
    /**
     * Constructor que inicializa la clase DTO con el medio de pago banco
     * @param medioDePago
     *        entidad del medio de pago de transferencia
     * @param banco
     *        entidad del banco
     */
    public MedioDePagoModeloDTO(MedioTransferencia medioDePago, Banco banco) {
        convertToDTO(medioDePago);
        this.setNombreBanco(banco.getNombre());
        this.setCodigoBanco(banco.getCodigo());
    }

	// constructor de DTO para mapear consulta de medios a trasladar (tarjeta)
	public MedioDePagoModeloDTO(Long idMedioDePago , String numeroTarjeta , Boolean seleccionado ) {
		this.idMedioDePago = idMedioDePago;
		this.numeroTarjeta = numeroTarjeta;
		this.seleccionado = seleccionado;
    }

	// constructor de DTO para mapear consulta de medios a trasladar (transferencia)
	public MedioDePagoModeloDTO(Long idMedioDePago , String codigoBanco , String nombreBanco, 
		String tipoCuenta, String numeroCuenta, String tipoIdentificacionTitular, String numeroIdentificacionTitular, 
		String nombreTitular, Boolean seleccionado ) {
		this.idMedioDePago = idMedioDePago;
		this.codigoBanco = codigoBanco;
		this.nombreBanco = nombreBanco;
		this.tipoCuenta = TipoCuentaEnum.valueOf(tipoCuenta);
		this.numeroCuenta = numeroCuenta;
		this.tipoIdentificacionTitular = TipoIdentificacionEnum.valueOf(tipoIdentificacionTitular);
		this.numeroIdentificacionTitular = numeroIdentificacionTitular;
		this.nombreTitularCuenta = nombreTitular;
		this.seleccionado = seleccionado;
    }


	// constructor de DTO para mapear consulta de medios a trasladar (efectivo)
	public MedioDePagoModeloDTO(Long idMedioDePago , String tipoMedioDePago, String sitioPago, Boolean seleccionado) {
		this.idMedioDePago = idMedioDePago;
		this.tipoMedioDePago = TipoMedioDePagoEnum.valueOf(tipoMedioDePago);
		this.nombreSitioPago = sitioPago;
		this.seleccionado = seleccionado;
    }

	public MedioDePagoModeloDTO(Long idMedioDePago, String tipoMedioDePago, String numeroCuentaOTarjeta, 
                            String codigoBanco, String nombreBanco, String sitioPago, 
                            String tipoCuenta, String titularCuenta, String identificacionTitular) {
		this.idMedioDePago = idMedioDePago;
		this.tipoMedioDePago = TipoMedioDePagoEnum.valueOf(tipoMedioDePago);
		this.codigoBanco = codigoBanco;
		this.nombreBanco = nombreBanco;
		this.nombreSitioPago = sitioPago;
		if(this.tipoMedioDePago == TipoMedioDePagoEnum.TRANSFERENCIA){
			this.numeroCuenta = numeroCuentaOTarjeta;
		}else if(this.tipoMedioDePago == TipoMedioDePagoEnum.TARJETA){
			this.numeroTarjeta = numeroCuentaOTarjeta;
		}
		try{
			this.tipoCuenta = TipoCuentaEnum.valueOf(tipoCuenta);
			this.tipoIdentificacionTitular = TipoIdentificacionEnum.valueOf(identificacionTitular);
		}catch(Exception e){

		}
		this.nombreTitularCuenta = titularCuenta;
	}

	/**
	 * Asocia los datos del DTO a la Entidad
	 * 
	 * @return MedioEfectivo
	 */
	public MedioDePago convertToMedioDePagoEntity(MedioDePago medioDePago) {
		if (TipoMedioDePagoEnum.EFECTIVO.equals(this.getTipoMedioDePago())) {
			MedioEfectivo medioEfectivo = new MedioEfectivo();
			if (medioDePago != null) {
				medioEfectivo = (MedioEfectivo) medioDePago;
			}
			medioEfectivo.setTipoMediopago(this.getTipoMedioDePago());
			medioEfectivo.setEfectivo(Boolean.TRUE);
			medioEfectivo.setSitioPago(this.getSitioPago());
			return medioEfectivo;
		} else if (TipoMedioDePagoEnum.TRANSFERENCIA.equals(this.getTipoMedioDePago())) {
			MedioTransferencia medioTransferencia = new MedioTransferencia();
			if (medioDePago != null) {
				medioTransferencia = (MedioTransferencia) medioDePago;
			}
			medioTransferencia.setTipoMediopago(this.getTipoMedioDePago());
			medioTransferencia.setIdBanco(this.getIdBanco());
			medioTransferencia.setTipoCuenta(this.getTipoCuenta());
			medioTransferencia.setNumeroCuenta(this.getNumeroCuenta());
			medioTransferencia.setTipoIdentificacionTitular(this.tipoIdentificacionTitular);
			medioTransferencia.setNumeroIdentificacionTitular(this.getNumeroIdentificacionTitular());
			medioTransferencia.setDigitoVerificacionTitular(this.getDigitoVerificacionTitular());
			medioTransferencia.setNombreTitularCuenta(this.getNombreTitularCuenta());
			if(this.getCobroJudicial() != null){
			    medioTransferencia.setCobroJudicial(this.getCobroJudicial());
			}
			if(this.getInfoRelacionadaCobroJudicial() != null){
			    medioTransferencia.setInfoRelacionadaCobroJudicial(this.getInfoRelacionadaCobroJudicial());
			}
                        
                        medioTransferencia.setCdgoficinajuzgado(this.getCdgoficinajuzgado() != null ? this.getCdgoficinajuzgado() : null);
                        medioTransferencia.setNroradicadojuzgado(this.getNroradicadojuzgado() != null ? this.getNroradicadojuzgado() : null);
                        medioTransferencia.setNrodeloficiojudicial(this.getNrodeloficiojudicial() != null ? this.getNrodeloficiojudicial() : null);
                        medioTransferencia.setCtaoficinajuzgado(this.getCtaoficinajuzgado() != null ? this.getCtaoficinajuzgado() : null);
                        
			return medioTransferencia;
		} else if (TipoMedioDePagoEnum.CONSIGNACION.equals(this.getTipoMedioDePago())) {
			MedioConsignacion medioConsignacion = new MedioConsignacion();
			if (medioDePago != null) {
				medioConsignacion = (MedioConsignacion) medioDePago;
			}
			medioConsignacion.setTipoMediopago(this.getTipoMedioDePago());
			medioConsignacion.setIdBanco(this.getIdBanco());
			medioConsignacion.setTipoCuenta(this.getTipoCuenta());
			medioConsignacion.setNumeroCuenta(this.getNumeroCuenta());
			medioConsignacion.setTipoIdentificacionTitular(this.tipoIdentificacionTitular);
			medioConsignacion.setNumeroIdentificacionTitular(this.getNumeroIdentificacionTitular());
			medioConsignacion.setDigitoVerificacionTitular(this.getDigitoVerificacionTitular());
			medioConsignacion.setNombreTitularCuenta(this.getNombreTitularCuenta());
			return medioConsignacion;
		} else if (TipoMedioDePagoEnum.CHEQUE.equals(this.getTipoMedioDePago())) {
			MedioCheque medioCheque = new MedioCheque();
			if (medioDePago != null) {
				medioCheque = (MedioCheque) medioDePago;
			}
			medioCheque.setTipoMediopago(this.getTipoMedioDePago());
			medioCheque.setTipoIdentificacionTitular(this.tipoIdentificacionTitular);
			medioCheque.setNumeroIdentificacionTitular(this.getNumeroIdentificacionTitular());
			medioCheque.setDigitoVerificacionTitular(this.getDigitoVerificacionTitular());
			medioCheque.setNombreTitularCuenta(this.getNombreTitularCuenta());
			return medioCheque;
		} else if (TipoMedioDePagoEnum.TARJETA.equals(this.getTipoMedioDePago())){
		    MedioTarjeta medioTarjeta = new MedioTarjeta();
		    if (medioDePago != null) {
		        medioTarjeta = (MedioTarjeta) medioDePago;
		    }
		    medioTarjeta.setTipoMediopago(this.getTipoMedioDePago());
		    medioTarjeta.setDisponeTarjeta(this.getDisponeTarjeta());
		    medioTarjeta.setEstadoTarjetaMultiservicios(this.getEstadoTarjetaMultiservicios());
		    medioTarjeta.setNumeroTarjeta(this.getNumeroTarjeta());
		    return medioTarjeta;
		}
		return medioDePago;
	}

	/**
	 * @param Asocia
	 *            los datos de la Entidad al DTO
	 */
	public void convertToDTO(MedioDePago medioDePago) {
		this.setTipoMedioDePago(medioDePago.getTipoMediopago());
		if (TipoMedioDePagoEnum.EFECTIVO.equals(medioDePago.getTipoMediopago())) {
			MedioEfectivo medioEfectivo = (MedioEfectivo) medioDePago;
			this.setIdMedioDePago(medioEfectivo.getIdMedioPago());
			this.setEfectivo(medioEfectivo.getEfectivo());
			this.setSitioPago(medioEfectivo.getSitioPago());
			this.setEstadoTarjetaMultiservicios(EstadoTarjetaMultiserviciosEnum.INACTIVA);
		} else if (TipoMedioDePagoEnum.TARJETA.equals(medioDePago.getTipoMediopago())) {
			MedioTarjeta medioTarjeta = (MedioTarjeta) medioDePago;
			this.setIdMedioDePago(medioTarjeta.getIdMedioPago());
			this.setDisponeTarjeta(medioTarjeta.getDisponeTarjeta());
			this.setEstadoTarjetaMultiservicios(medioTarjeta.getEstadoTarjetaMultiservicios());
			this.setSolicitudTarjeta(medioTarjeta.getSolicitudTarjeta());
			this.setNumeroTarjeta(medioTarjeta.getNumeroTarjeta());
		} else if (TipoMedioDePagoEnum.TRANSFERENCIA.equals(medioDePago.getTipoMediopago())) {
			MedioTransferencia medioTransferencia = (MedioTransferencia) medioDePago;
			this.setIdBanco(medioTransferencia.getIdBanco());
			this.setIdMedioDePago(medioTransferencia.getIdMedioPago());
			this.setNumeroCuenta(medioTransferencia.getNumeroCuenta());
			this.setTipoCuenta(medioTransferencia.getTipoCuenta());
			this.setNumeroIdentificacionTitular(medioTransferencia.getNumeroIdentificacionTitular());
			this.setTipoIdentificacionTitular(medioTransferencia.getTipoIdentificacionTitular());
			this.setDigitoVerificacionTitular(medioTransferencia.getDigitoVerificacionTitular());
			this.setNombreTitularCuenta(medioTransferencia.getNombreTitularCuenta());
			this.setCobroJudicial(medioTransferencia.getCobroJudicial());
			this.setInfoRelacionadaCobroJudicial(medioTransferencia.getInfoRelacionadaCobroJudicial());       
			this.setCtaoficinajuzgado(medioTransferencia.getCtaoficinajuzgado());
			this.setNrodeloficiojudicial(medioTransferencia.getNrodeloficiojudicial());
			this.setNroradicadojuzgado(medioTransferencia.getNroradicadojuzgado());
			this.setCdgoficinajuzgado(medioTransferencia.getCdgoficinajuzgado());
                        
                        
		} else if (TipoMedioDePagoEnum.CONSIGNACION.equals(medioDePago.getTipoMediopago())) {
			MedioConsignacion medioConsignacion = (MedioConsignacion) medioDePago;
			this.setIdBanco(medioConsignacion.getIdBanco());
			this.setIdMedioDePago(medioConsignacion.getIdMedioPago());
			this.setNumeroCuenta(medioConsignacion.getNumeroCuenta());
			this.setTipoCuenta(medioConsignacion.getTipoCuenta());
			this.setNumeroIdentificacionTitular(medioConsignacion.getNumeroIdentificacionTitular());
			this.setTipoIdentificacionTitular(medioConsignacion.getTipoIdentificacionTitular());
			this.setDigitoVerificacionTitular(medioConsignacion.getDigitoVerificacionTitular());
			this.setNombreTitularCuenta(medioConsignacion.getNombreTitularCuenta());
		} else if (TipoMedioDePagoEnum.CHEQUE.equals(medioDePago.getTipoMediopago())) {
			MedioCheque medioCheque = (MedioCheque) medioDePago;
			this.setIdMedioDePago(medioCheque.getIdMedioPago());
			this.setNumeroIdentificacionTitular(medioCheque.getNumeroIdentificacionTitular());
			this.setTipoIdentificacionTitular(medioCheque.getTipoIdentificacionTitular());
			this.setDigitoVerificacionTitular(medioCheque.getDigitoVerificacionTitular());
			this.setNombreTitularCuenta(medioCheque.getNombreTitularCuenta());
		}
	}

	/**
	 * @return the idMedioDePago
	 */
	public Long getIdMedioDePago() {
		return idMedioDePago;
	}

	/**
	 * @param idMedioDePago
	 *            the idMedioDePago to set
	 */
	public void setIdMedioDePago(Long idMedioDePago) {
		this.idMedioDePago = idMedioDePago;
	}

	/**
	 * @return the tipoMedioDePago
	 */
	public TipoMedioDePagoEnum getTipoMedioDePago() {
		return tipoMedioDePago;
	}

	/**
	 * @param tipoMedioDePago
	 *            the tipoMedioDePago to set
	 */
	public void setTipoMedioDePago(TipoMedioDePagoEnum tipoMedioDePago) {
		this.tipoMedioDePago = tipoMedioDePago;
	}

	/**
	 * @return the efectivo
	 */
	public Boolean getEfectivo() {
		return efectivo;
	}

	/**
	 * @param efectivo
	 *            the efectivo to set
	 */
	public void setEfectivo(Boolean efectivo) {
		this.efectivo = efectivo;
	}

	/**
	 * @return the numeroTarjeta
	 */
	public String getNumeroTarjeta() {
		return numeroTarjeta;
	}

	/**
	 * @param numeroTarjeta
	 *            the numeroTarjeta to set
	 */
	public void setNumeroTarjeta(String numeroTarjeta) {
		this.numeroTarjeta = numeroTarjeta;
	}

	/**
	 * @return the disponeTarjeta
	 */
	public Boolean getDisponeTarjeta() {
		return disponeTarjeta;
	}

	/**
	 * @param disponeTarjeta
	 *            the disponeTarjeta to set
	 */
	public void setDisponeTarjeta(Boolean disponeTarjeta) {
		this.disponeTarjeta = disponeTarjeta;
	}

	/**
	 * @return the estadoTarjetaMultiservicios
	 */
	public EstadoTarjetaMultiserviciosEnum getEstadoTarjetaMultiservicios() {
		return estadoTarjetaMultiservicios;
	}

	/**
	 * @param estadoTarjetaMultiservicios
	 *            the estadoTarjetaMultiservicios to set
	 */
	public void setEstadoTarjetaMultiservicios(EstadoTarjetaMultiserviciosEnum estadoTarjetaMultiservicios) {
		this.estadoTarjetaMultiservicios = estadoTarjetaMultiservicios;
	}

	/**
	 * @return the solicitudTarjeta
	 */
	public SolicitudTarjetaEnum getSolicitudTarjeta() {
		return solicitudTarjeta;
	}

	/**
	 * @param solicitudTarjeta
	 *            the solicitudTarjeta to set
	 */
	public void setSolicitudTarjeta(SolicitudTarjetaEnum solicitudTarjeta) {
		this.solicitudTarjeta = solicitudTarjeta;
	}

	/**
	 * @return the idBanco
	 */
	public Long getIdBanco() {
		return idBanco;
	}

	/**
	 * @param idBanco
	 *            the idBanco to set
	 */
	public void setIdBanco(Long idBanco) {
		this.idBanco = idBanco;
	}

	/**
	 * @return the tipoCuenta
	 */
	public TipoCuentaEnum getTipoCuenta() {
		return tipoCuenta;
	}

	/**
	 * @param tipoCuenta
	 *            the tipoCuenta to set
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
	 *            the numeroCuenta to set
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
	 *            the tipoIdentificacionTitular to set
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
	 *            the numeroIdentificacionTitular to set
	 */
	public void setNumeroIdentificacionTitular(String numeroIdentificacionTitular) {
		this.numeroIdentificacionTitular = numeroIdentificacionTitular;
	}

	/**
	 * @return the digitoVerificacionTitular
	 */
	public Short getDigitoVerificacionTitular() {
		return digitoVerificacionTitular;
	}

	/**
	 * @param digitoVerificacionTitular
	 *            the digitoVerificacionTitular to set
	 */
	public void setDigitoVerificacionTitular(Short digitoVerificacionTitular) {
		this.digitoVerificacionTitular = digitoVerificacionTitular;
	}

	/**
	 * @return the nombreTitularCuenta
	 */
	public String getNombreTitularCuenta() {
		return nombreTitularCuenta;
	}

	/**
	 * @param nombreTitularCuenta
	 *            the nombreTitularCuenta to set
	 */
	public void setNombreTitularCuenta(String nombreTitularCuenta) {
		this.nombreTitularCuenta = nombreTitularCuenta;
	}

	/**
	 * @return the admonSubsidio
	 */
	public PersonaModeloDTO getAdmonSubsidio() {
		return admonSubsidio;
	}

	/**
	 * @param admonSubsidio
	 *            the admonSubsidio to set
	 */
	public void setAdmonSubsidio(PersonaModeloDTO admonSubsidio) {
		this.admonSubsidio = admonSubsidio;
	}

	/**
	 * @return the idGrupoFamiliar
	 */
	public Long getIdGrupoFamiliar() {
		return idGrupoFamiliar;
	}

	/**
	 * @param idGrupoFamiliar
	 *            the idGrupoFamiliar to set
	 */
	public void setIdGrupoFamiliar(Long idGrupoFamiliar) {
		this.idGrupoFamiliar = idGrupoFamiliar;
	}

	/**
	 * @return the afiliadoEsAdministradorSubsidio
	 */
	public Boolean getAfiliadoEsAdministradorSubsidio() {
		return afiliadoEsAdministradorSubsidio;
	}

	/**
	 * @param afiliadoEsAdministradorSubsidio
	 *            the afiliadoEsAdministradorSubsidio to set
	 */
	public void setAfiliadoEsAdministradorSubsidio(Boolean afiliadoEsAdministradorSubsidio) {
		this.afiliadoEsAdministradorSubsidio = afiliadoEsAdministradorSubsidio;
	}

	/**
	 * @return the persona
	 */
	public PersonaModeloDTO getPersona() {
		return persona;
	}

	/**
	 * @param persona
	 *            the persona to set
	 */
	public void setPersona(PersonaModeloDTO persona) {
		this.persona = persona;
	}

	/**
	 * @return the idRelacionGrupoFamiliar
	 */
	public Short getIdRelacionGrupoFamiliar() {
		return idRelacionGrupoFamiliar;
	}

	/**
	 * @param idRelacionGrupoFamiliar
	 *            the idRelacionGrupoFamiliar to set
	 */
	public void setIdRelacionGrupoFamiliar(Short idRelacionGrupoFamiliar) {
		this.idRelacionGrupoFamiliar = idRelacionGrupoFamiliar;
	}

	/**
	 * @return the sitioPago
	 */
	public Long getSitioPago() {
		return sitioPago;
	}

	/**
	 * @param sitioPago
	 *            the sitioPago to set
	 */
	public void setSitioPago(Long sitioPago) {
		this.sitioPago = sitioPago;
	}

	/**
	 * Obtiene el valor de sede
	 * 
	 * @return El valor de sede
	 */
	public Long getSede() {
		return sede;
	}

	/**
	 * Establece el valor de sede
	 * 
	 * @param sede
	 *            El valor de sede por asignar
	 */
	public void setSede(Long sede) {
		this.sede = sede;
	}

    /**
     * @return the nombreBanco
     */
    public String getNombreBanco() {
        return nombreBanco;
    }

    /**
     * @param nombreBanco the nombreBanco to set
     */
    public void setNombreBanco(String nombreBanco) {
        this.nombreBanco = nombreBanco;
    }

    /**
     * @return the codigoBanco
     */
    public String getCodigoBanco() {
        return codigoBanco;
    }

    /**
     * @param codigoBanco the codigoBanco to set
     */
    public void setCodigoBanco(String codigoBanco) {
        this.codigoBanco = codigoBanco;
    }

    /**
     * @return the cobroJudicial
     */
    public Boolean getCobroJudicial() {
        return cobroJudicial;
    }

    /**
     * @param cobroJudicial the cobroJudicial to set
     */
    public void setCobroJudicial(Boolean cobroJudicial) {
        this.cobroJudicial = cobroJudicial;
    }

    /**
     * @return the infoRelacionadaCobroJudicial
     */
    public String getInfoRelacionadaCobroJudicial() {
        return infoRelacionadaCobroJudicial;
    }

    /**
     * @param infoRelacionadaCobroJudicial the infoRelacionadaCobroJudicial to set
     */
    public void setInfoRelacionadaCobroJudicial(String infoRelacionadaCobroJudicial) {
        this.infoRelacionadaCobroJudicial = infoRelacionadaCobroJudicial;
    }


	public Boolean getTarjetaMultiservicio() {
		return this.tarjetaMultiservicio;
	}

	public void setTarjetaMultiservicio(Boolean tarjetaMultiservicio) {
		this.tarjetaMultiservicio = tarjetaMultiservicio;
	}

	public BancoModeloDTO getBancoModeloDTO() {
		return bancoModeloDTO;
	}

	public void setBancoModeloDTO(BancoModeloDTO bancoModeloDTO) {
		this.bancoModeloDTO = bancoModeloDTO;
	}


	public Boolean isEfectivo() {
		return this.efectivo;
	}

	public Boolean isDisponeTarjeta() {
		return this.disponeTarjeta;
	}

	public Boolean isCobroJudicial() {
		return this.cobroJudicial;
	}

	public Boolean isAfiliadoEsAdministradorSubsidio() {
		return this.afiliadoEsAdministradorSubsidio;
	}

	public Boolean isTarjetaMultiservicio() {
		return this.tarjetaMultiservicio;
	}

	public String getTipoSolicitud() {
		return this.tipoSolicitud;
	}

	public void setTipoSolicitud(String tipoSolicitud) {
		this.tipoSolicitud = tipoSolicitud;
	}

	public Long getIdMEdioDePagoActualizar() {
		return this.idMEdioDePagoActualizar;
	}

	public void setIdMEdioDePagoActualizar(Long idMEdioDePagoActualizar) {
		this.idMEdioDePagoActualizar = idMEdioDePagoActualizar;
	}

	public String getNombreSitioPago() {
		return this.nombreSitioPago;
	}

	public void setNombreSitioPago(String nombreSitioPago) {
		this.nombreSitioPago = nombreSitioPago;
	}

	public String getMarcaAfiliacionMasiva(){
		return marcaAfiliacionMasiva;
	}

	public void setMarcaAfilicacionMasiva(String marcaAfiliacionMasiva){
		this.marcaAfiliacionMasiva = marcaAfiliacionMasiva; 
	}




	@Override
	public String toString() {
		return "{" +
			" idMedioDePago='" + getIdMedioDePago() + "'" +
			", tipoMedioDePago='" + getTipoMedioDePago() + "'" +
			", efectivo='" + isEfectivo() + "'" +
			", sede='" + getSede() + "'" +
			", numeroTarjeta='" + getNumeroTarjeta() + "'" +
			", disponeTarjeta='" + isDisponeTarjeta() + "'" +
			", estadoTarjetaMultiservicios='" + getEstadoTarjetaMultiservicios() + "'" +
			", solicitudTarjeta='" + getSolicitudTarjeta() + "'" +
			", cobroJudicial='" + isCobroJudicial() + "'" +
			", infoRelacionadaCobroJudicial='" + getInfoRelacionadaCobroJudicial() + "'" +
			", idBanco='" + getIdBanco() + "'" +
			", nombreBanco='" + getNombreBanco() + "'" +
			", codigoBanco='" + getCodigoBanco() + "'" +
			", tipoCuenta='" + getTipoCuenta() + "'" +
			", numeroCuenta='" + getNumeroCuenta() + "'" +
			", tipoIdentificacionTitular='" + getTipoIdentificacionTitular() + "'" +
			", numeroIdentificacionTitular='" + getNumeroIdentificacionTitular() + "'" +
			", digitoVerificacionTitular='" + getDigitoVerificacionTitular() + "'" +
			", nombreTitularCuenta='" + getNombreTitularCuenta() + "'" +
			", admonSubsidio='" + getAdmonSubsidio() + "'" +
			", persona='" + getPersona() + "'" +
			", idGrupoFamiliar='" + getIdGrupoFamiliar() + "'" +
			", afiliadoEsAdministradorSubsidio='" + isAfiliadoEsAdministradorSubsidio() + "'" +
			", idRelacionGrupoFamiliar='" + getIdRelacionGrupoFamiliar() + "'" +
			", sitioPago='" + getSitioPago() + "'" +
			", bancoModeloDTO='" + getBancoModeloDTO() + "'" +
			", tarjetaMultiservicio='" + isTarjetaMultiservicio() + "'" +
			", ctaoficinajuzgado='" + getCtaoficinajuzgado() + "'" +
			", cdgoficinajuzgado='" + getCdgoficinajuzgado() + "'" +
			", nmboficinajuzgado='" + getNmboficinajuzgado() + "'" +
			", nroradicadojuzgado='" + getNroradicadojuzgado() + "'" +
			", nrodeloficiojudicial='" + getNrodeloficiojudicial() + "'" +
			", tipoSolicitud='" + getTipoSolicitud() + "'" +
			", idMEdioDePagoActualizar='" + getIdMEdioDePagoActualizar() + "'" +
			"}";
	}
	
}
