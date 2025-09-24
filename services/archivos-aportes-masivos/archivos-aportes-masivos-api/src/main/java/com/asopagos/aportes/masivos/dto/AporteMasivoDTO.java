package com.asopagos.aportes.masivos.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.dto.aportes.CotizanteDTO;
import com.asopagos.dto.modelo.RegistroGeneralModeloDTO;
import com.asopagos.enumeraciones.aportes.EstadoAporteEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

@XmlRootElement
public class AporteMasivoDTO implements Serializable{


    private DatosRadicacionMasivaDTO radicacionDTO;
    /**
     * Cotizantes (reporte detallado)
     */
    private List<CotizanteDTO> cotizantes;

    /**
     * Id del registro general almacenado en la primera simulación.
     */
    private Long idRegistroGeneral;
    
    /**
     * Id de la última transacción del registro general
     * */
    private Long idTransaccion;

    private String periodo;

    private TipoIdentificacionEnum tipoIdentificacionAportante;

    private String numeroIdentificacionAportante;

    /*
    public RegistroGeneralModeloDTO convertToDTO() {
        RegistroGeneralModeloDTO registroGeneralDTO = new RegistroGeneralModeloDTO();
        
        registroGeneralDTO.setCuentaBancariaRecaudo(this.getRadicacionDTO().getCuentaBancariaRecaudo());

        if (this.getInformacionPagoDTO().getClaseAportante() != null) {
            registroGeneralDTO.setClaseAportante(this.getInformacionPagoDTO().getClaseAportante().getCodigo());
        }
        // registroGeneralDTO.setCodigoEntidadFinanciera(this.getRadicacionDTO().getCodigoFinanciero());
        registroGeneralDTO.setCodSucursal(this.getInformacionPagoDTO().getCodigoSucursal());
        registroGeneralDTO.setDigVerAportante(this.getRadicacionDTO().getDv());
        //registroGeneralDTO.setFechaRecaudo(this.getRadicacionDTO().getFechaRecepcionAporte());

        registroGeneralDTO.setModalidadPlanilla(this.getInformacionPagoDTO().getModalidadPlantilla());
        StringBuilder nombreAportante = new StringBuilder();
        if (this.getRadicacionDTO().getRazonSocialAportante() == null) {
            nombreAportante.append(this.getRadicacionDTO().getPrimerNombre() + " ");
            nombreAportante
                    .append(this.getRadicacionDTO().getSegundoNombre() != null ? this.getRadicacionDTO().getSegundoNombre() + " " : "");
            //nombreAportante.append(this.getRadicacionDTO().getPrimerApellido() + " ");
            
            nombreAportante
                    .append(this.getRadicacionDTO().getSegundoApellido() != null ? this.getRadicacionDTO().getSegundoApellido() : "");
            
        }
        else {
            nombreAportante.append(this.getRadicacionDTO().getRazonSocialAportante());
        }
        
        if (TipoAfiliadoEnum.PENSIONADO.name().equals(this.getRadicacionDTO().getTipo().name()) ||
                TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE.name().equals(this.getRadicacionDTO().getTipo().name())){
            registroGeneralDTO.setOutPrimerNombreAportante(this.getRadicacionDTO().getPrimerNombre());
            registroGeneralDTO.setOutPrimerApellidoAportante(this.getRadicacionDTO().getPrimerApellido());
            registroGeneralDTO.setOutSegundoNombreAportante(this.getRadicacionDTO().getSegundoNombre());
            registroGeneralDTO.setOutSegundoApellidoAportante(this.getRadicacionDTO().getSegundoApellido());
        }
        
        
        registroGeneralDTO.setNombreAportante(nombreAportante.toString());
        registroGeneralDTO.setNomSucursal(this.getInformacionPagoDTO().getNombreSucursal());
        registroGeneralDTO.setNumeroIdentificacionAportante(this.getRadicacionDTO().getNumeroIdentificacion());
        registroGeneralDTO.setOperadorInformacion(this.getInformacionPagoDTO().getCodigoOperador());
        if (this.getRadicacionDTO().getPeriodoPago() != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
            registroGeneralDTO.setPeriodoAporte(dateFormat.format(this.getRadicacionDTO().getPeriodoPago()));
        }

        //registroGeneralDTO.setTipoIdentificacionAportante(this.getRadicacionDTO().getTipoIdentificacion());
        if (this.getInformacionPagoDTO().getTipoPlanilla() != null) {
            registroGeneralDTO.setTipoPlanilla(this.getInformacionPagoDTO().getTipoPlanilla().getCodigo());
        }
        BigDecimal mora = this.getRadicacionDTO().getMoraAporte();
        if (mora == null) {
            mora = BigDecimal.ZERO;
        }
        registroGeneralDTO.setValorIntMora(mora);
        registroGeneralDTO.setValTotalApoObligatorio(this.getRadicacionDTO().getMontoAporte());
        registroGeneralDTO.setOutFinalizadoProcesoManual(Boolean.FALSE);
        registroGeneralDTO.setEsSimulado(Boolean.FALSE);
        registroGeneralDTO.setEstadoEvaluacion(EstadoAporteEnum.VIGENTE);
        registroGeneralDTO.setNumPlanilla(this.getInformacionPagoDTO().getNumeroRadicacion());
        registroGeneralDTO.setDireccion(this.getInformacionPagoDTO().getDireccion());
        if (this.getInformacionPagoDTO().getCodigoMunicipio() != null) {
            registroGeneralDTO.setCodCiudad(this.getInformacionPagoDTO().getCodigoMunicipio().substring(2, 5));
        }
        registroGeneralDTO.setCodDepartamento(this.getInformacionPagoDTO().getCodigoDepartamento());
        registroGeneralDTO.setEmail(this.getInformacionPagoDTO().getCorreoElectronico());
        if (this.getInformacionPagoDTO().getTelefono() != null) {
            registroGeneralDTO.setTelefono(new Long(this.getInformacionPagoDTO().getTelefono()));
        }
        if (this.getInformacionPagoDTO().getFax() != null) {
            registroGeneralDTO.setFax(new Long(this.getInformacionPagoDTO().getFax()));
        }
        if (this.getInformacionPagoDTO().getNaturalezaJuridica() != null) {
            registroGeneralDTO.setNaturalezaJuridica(this.getInformacionPagoDTO().getNaturalezaJuridica().getCodigo().shortValue());
        }
        registroGeneralDTO.setFechaMatricula(this.getInformacionPagoDTO().getFechaMatriculaMercantil());
        registroGeneralDTO.setDiasMora(this.getInformacionPagoDTO().getDiasMora()!=null?this.getInformacionPagoDTO().getDiasMora().shortValue():null);
        registroGeneralDTO.setFechaPlanilla(this.getInformacionPagoDTO().getFechaPagoPlantilla()!=null? new Date(this.getInformacionPagoDTO().getFechaPagoPlantilla()):null);
        registroGeneralDTO.setFormaPresentacion(this.getInformacionPagoDTO().getFormaPresentacion()!=null?this.getInformacionPagoDTO().getFormaPresentacion().getCodigo():null);
        registroGeneralDTO.setCantEmpleados(this.getInformacionPagoDTO().getNumeroEmpleados()!=null?this.getInformacionPagoDTO().getNumeroEmpleados().intValue():null);
        registroGeneralDTO.setCantAfiliados(this.getInformacionPagoDTO().getNumeroAfiliadosAdministradora()!=null?this.getInformacionPagoDTO().getNumeroAfiliadosAdministradora().intValue():null);
        registroGeneralDTO.setTipoPersona(this.getInformacionPagoDTO().getTipoPersona()!=null?this.getInformacionPagoDTO().getTipoPersona().getCodigo():null);
        
        registroGeneralDTO.setTransaccion(this.getIdTransaccion());
        
        return registroGeneralDTO; 
    }
    */


    public DatosRadicacionMasivaDTO getRadicacionDTO() {
        return this.radicacionDTO;
    }

    public void setRadicacionDTO(DatosRadicacionMasivaDTO radicacionDTO) {
        this.radicacionDTO = radicacionDTO;
    }
    
    /*
    public InfromacionPagoMasivoDTO getInformacionPagoDTO() {
        return this.informacionPagoDTO;
    }

    public void setInformacionPagoDTO(InfromacionPagoMasivoDTO informacionPagoDTO) {
        this.informacionPagoDTO = informacionPagoDTO;
    }
    */

    public List<CotizanteDTO> getCotizantes() {
        return this.cotizantes;
    }

    public void setCotizantes(List<CotizanteDTO> cotizantes) {
        this.cotizantes = cotizantes;
    }

    public Long getIdRegistroGeneral() {
        return this.idRegistroGeneral;
    }

    public void setIdRegistroGeneral(Long idRegistroGeneral) {
        this.idRegistroGeneral = idRegistroGeneral;
    }

    public Long getIdTransaccion() {
        return this.idTransaccion;
    }

    public void setIdTransaccion(Long idTransaccion) {
        this.idTransaccion = idTransaccion;
    }


    public String getPeriodo() {
        return this.periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public TipoIdentificacionEnum getTipoIdentificacionAportante() {
        return this.tipoIdentificacionAportante;
    }

    public void setTipoIdentificacionAportante(TipoIdentificacionEnum tipoIdentificacionAportante) {
        this.tipoIdentificacionAportante = tipoIdentificacionAportante;
    }

    public String getNumeroIdentificacionAportante() {
        return this.numeroIdentificacionAportante;
    }

    public void setNumeroIdentificacionAportante(String numeroIdentificacionAportante) {
        this.numeroIdentificacionAportante = numeroIdentificacionAportante;
    }


}
