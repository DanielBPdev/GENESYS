/**
 * 
 */
package com.asopagos.novedades.dto;

import java.util.List;
import com.asopagos.dto.modelo.BeneficiarioModeloDTO;
import com.asopagos.dto.modelo.RolAfiliadoModeloDTO;
import com.asopagos.enumeraciones.afiliaciones.MotivoDesafiliacionAfiliadoEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;

/**
 * Contiene la información de las novedades consecutivas
 * @author Jose Arley Correa Salamanca <jocorrea@heinsohn.com>
 *
 */
public class DatosNovedadCascadaDTO {

    /**
     * Fecha retiro
     */
    private Long fechaRetiro;

    /**
     * Lista de benenfiarios para aplicarles la novedad - Desde RetiroTrabajador
     */
    private List<BeneficiarioModeloDTO> listaBeneficiario;

    /**
     * Lista de roles afiliados para aplicarles la novedad - Desde RetiroEmpleador
     */
    private List<RolAfiliadoModeloDTO> listaRoles;

    /**
     * Motivo desafiliación afiliado
     */
    private MotivoDesafiliacionAfiliadoEnum motivoDesafiliacionAfiliado;

    /**
     * Numero de radicado de la solicitud original que registra la cascada
     */
    private String numeroRadicadoOriginal;

    /**
     * Información del afiliado - Retiro
     */
    private RolAfiliadoModeloDTO rolAfiliadoDTO;

    /**
     * Tipo de transaccion de la solicitud original que registra la cascada
     */
    private TipoTransaccionEnum tipoTransaccionOriginal;

    /**
     * Cosntructor por defecto
     */
    public DatosNovedadCascadaDTO() {
        super();
    }

    /**
     * @return the fechaRetiro
     */
    public Long getFechaRetiro() {
        return fechaRetiro;
    }

    /**
     * @return the listaBeneficiario
     */
    public List<BeneficiarioModeloDTO> getListaBeneficiario() {
        return listaBeneficiario;
    }

    /**
     * @return the listaRoles
     */
    public List<RolAfiliadoModeloDTO> getListaRoles() {
        return listaRoles;
    }

    /**
     * @return the motivoDesafiliacionAfiliado
     */
    public MotivoDesafiliacionAfiliadoEnum getMotivoDesafiliacionAfiliado() {
        return motivoDesafiliacionAfiliado;
    }

    /**
     * @return the numeroRadicadoOriginal
     */
    public String getNumeroRadicadoOriginal() {
        return numeroRadicadoOriginal;
    }

    /**
     * @return the rolAfiliadoDTO
     */
    public RolAfiliadoModeloDTO getRolAfiliadoDTO() {
        return rolAfiliadoDTO;
    }

    /**
     * @return the tipoTransaccionEnumOriginal
     */
    public TipoTransaccionEnum getTipoTransaccionOriginal() {
        return tipoTransaccionOriginal;
    }

    /**
     * @param fechaRetiro
     *        the fechaRetiro to set
     */
    public void setFechaRetiro(Long fechaRetiro) {
        this.fechaRetiro = fechaRetiro;
    }

    /**
     * @param listaBeneficiario
     *        the listaBeneficiario to set
     */
    public void setListaBeneficiario(List<BeneficiarioModeloDTO> listaBeneficiario) {
        this.listaBeneficiario = listaBeneficiario;
    }

    /**
     * @param listaRoles
     *        the listaRoles to set
     */
    public void setListaRoles(List<RolAfiliadoModeloDTO> listaRoles) {
        this.listaRoles = listaRoles;
    }

    /**
     * @param motivoDesafiliacionAfiliado
     *        the motivoDesafiliacionAfiliado to set
     */
    public void setMotivoDesafiliacionAfiliado(MotivoDesafiliacionAfiliadoEnum motivoDesafiliacionAfiliado) {
        this.motivoDesafiliacionAfiliado = motivoDesafiliacionAfiliado;
    }

    /**
     * @param numeroRadicadoOriginal
     *        the numeroRadicadoOriginal to set
     */
    public void setNumeroRadicadoOriginal(String numeroRadicadoOriginal) {
        this.numeroRadicadoOriginal = numeroRadicadoOriginal;
    }

    /**
     * @param rolAfiliadoDTO
     *        the rolAfiliadoDTO to set
     */
    public void setRolAfiliadoDTO(RolAfiliadoModeloDTO rolAfiliadoDTO) {
        this.rolAfiliadoDTO = rolAfiliadoDTO;
    }

    /**
     * @param tipoTransaccionEnumOriginal
     *        the tipoTransaccionEnumOriginal to set
     */
    public void setTipoTransaccionOriginal(TipoTransaccionEnum tipoTransaccionOriginal) {
        this.tipoTransaccionOriginal = tipoTransaccionOriginal;
    }

}
