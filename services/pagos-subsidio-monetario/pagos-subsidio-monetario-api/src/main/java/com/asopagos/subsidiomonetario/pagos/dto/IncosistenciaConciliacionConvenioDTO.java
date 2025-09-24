package com.asopagos.subsidiomonetario.pagos.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.entidades.subsidiomonetario.pagos.CampoArchivoRetiroTerceroPagador;
import com.asopagos.entidades.subsidiomonetario.pagos.RegistroArchivoRetiroTerceroPagador;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.TipoCampoInconsistenciaArchivoRetiroTercerPagadorEnum;

/**
 * <b>Descripción:</b> DTO que contiene la inconsistencia que se genero al momento
 * de realizar la validación del archivo de retiro del tercero pagador
 * <b>Historia de Usuario:</b> HU-31-205
 * 
 * author <a href="mailto:mosorio@heinsohn.com.co"> Miguel Angel Osorio</a>
 */
@XmlRootElement
public class IncosistenciaConciliacionConvenioDTO implements Serializable {

    private static final long serialVersionUID = 3492006867399357533L;

    /**
     * Titulo del campo por el cual hubo inconsistencia
     */
    private TipoCampoInconsistenciaArchivoRetiroTercerPagadorEnum tituloCampo;

    /**
     * Numero de identificación por el cual se asocia el registro
     * del archivo de retiro
     */
    private String campoArchivo;

    /**
     * Numero de identificación de la incosistencia generada por el
     * registro del archivo
     */
    private String campoCuenta;

    /**
     * Enumeración que contiene el campo y la descripción por el cual
     * ocurrío la incosistencia
     */
    private String inconsistenciaCampo;
    
    /**
     * 
     * 
     */
    private Long registroArchivoRetiroTerceroPagador;
    
    /**
     * 
     * 
     */
    private String identificadorTransaccionTerceroPagador;

    public String getIdentificadorTransaccionTerceroPagador() {
		return identificadorTransaccionTerceroPagador;
	}

	public void setIdentificadorTransaccionTerceroPagador(String identificadorTransaccionTerceroPagador) {
		this.identificadorTransaccionTerceroPagador = identificadorTransaccionTerceroPagador;
	}

	public Long getRegistroArchivoRetiroTerceroPagador() {
		return registroArchivoRetiroTerceroPagador;
	}

	public void setRegistroArchivoRetiroTerceroPagador(Long registroArchivoRetiroTerceroPagador) {
		this.registroArchivoRetiroTerceroPagador = registroArchivoRetiroTerceroPagador;
	}

	/**
     * Constructor vacio de la clase
     */
    public IncosistenciaConciliacionConvenioDTO() {  }

    /**
     * Constructor que inicializa la clase por medio de las incosistencias encontradas
     * en la entidad CampoArchivoRetiroTerceroPagador
     * @param car
     *        Entidad de la clase CampoArchivoRetiroTerceroPagador
     */
    public IncosistenciaConciliacionConvenioDTO(CampoArchivoRetiroTerceroPagador car,RegistroArchivoRetiroTerceroPagador rar){
        
        this.setTituloCampo(car.getDescripcionCampo()); 
        this.setCampoArchivo(car.getValorCampoArchivo());
        this.setCampoCuenta(car.getValorCampoCuentaAdmonSubsidio());
        this.setInconsistenciaCampo(car.getInconsistenciaDetectada()); 
        this.setRegistroArchivoRetiroTerceroPagador(car.getIdRegistroArchivoRetiroTerceroPagador()); 
        this.setIdentificadorTransaccionTerceroPagador(rar.getIdentificadorTransaccionTerceroPagador());
        
    }

    /**
     * @return the tituloCampo
     */
    public TipoCampoInconsistenciaArchivoRetiroTercerPagadorEnum getTituloCampo() {
        return tituloCampo;
    }

    /**
     * @param tituloCampo
     *        the tituloCampo to set
     */
    public void setTituloCampo(TipoCampoInconsistenciaArchivoRetiroTercerPagadorEnum tituloCampo) {
        this.tituloCampo = tituloCampo;
    }

    /**
     * @return the campoArchivo
     */
    public String getCampoArchivo() {
        return campoArchivo;
    }

    /**
     * @param campoArchivo
     *        the campoArchivo to set
     */
    public void setCampoArchivo(String campoArchivo) {
        this.campoArchivo = campoArchivo;
    }

    /**
     * @return the campoCuenta
     */
    public String getCampoCuenta() {
        return campoCuenta;
    }

    /**
     * @param campoCuenta
     *        the campoCuenta to set
     */
    public void setCampoCuenta(String campoCuenta) {
        this.campoCuenta = campoCuenta;
    }

    /**
     * @return the inconsistenciaCampo
     */
    public String getInconsistenciaCampo() {
        return inconsistenciaCampo;
    }

    /**
     * @param inconsistenciaCampo
     *        the inconsistenciaCampo to set
     */
    public void setInconsistenciaCampo(String inconsistenciaCampo) {
        this.inconsistenciaCampo = inconsistenciaCampo;
    }

}
