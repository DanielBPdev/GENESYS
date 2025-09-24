/**
 * 
 */
package com.asopagos.aportes.masivos.dto;

import com.asopagos.dto.aportes.CotizanteDTO;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.aportes.masivos.dto.DatosRadicacionMasivaDTO;
import com.asopagos.aportes.masivos.dto.ResultadoCotizanteAporteMasivoDTO;
import com.asopagos.dto.ResultadoHallazgosValidacionArchivoDTO;
import com.asopagos.entidades.pila.masivos.MasivoArchivo;
import java.util.Date;


@XmlRootElement
public class ResultadoValidacionAporteDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;

    // Es necesario asociar el ECM al dato
    private String ecmIdentificador;

    private DatosRadicacionMasivaDTO resultadoDatosRadicacion;

    private List<ResultadoCotizanteAporteMasivoDTO> resultadoCotizantesMasivos;
    
    private Long solicitud;

    private Long idCargue;

    public Long getSolicitud() {
        return this.solicitud;
    }

    public void setSolicitud(Long solicitud) {
        this.solicitud = solicitud;
    }

    public String getEcmIdentificador() {
        return this.ecmIdentificador;


    }

    public void setEcmIdentificador(String ecmIdentificador) {
        this.ecmIdentificador = ecmIdentificador;
    }

    public DatosRadicacionMasivaDTO getResultadoDatosRadicacion() {
        return this.resultadoDatosRadicacion;
    }

    public void setResultadoDatosRadicacion(DatosRadicacionMasivaDTO resultadoDatosRadicacion) {
        this.resultadoDatosRadicacion = resultadoDatosRadicacion;
    }

    public List<ResultadoCotizanteAporteMasivoDTO> getResultadoCotizantesMasivos() {
        return this.resultadoCotizantesMasivos;
    }

    public void setResultadoCotizantesMasivos(List<ResultadoCotizanteAporteMasivoDTO> resultadoCotizantesMasivos) {
        this.resultadoCotizantesMasivos = resultadoCotizantesMasivos;
    }


    public Long getIdCargue() {
        return this.idCargue;
    }

    public void setIdCargue(Long idCargue) {
        this.idCargue = idCargue;
    }



    public MasivoArchivo toMasivoArchivo(String usuario) {

        MasivoArchivo archivo = new MasivoArchivo();
        archivo.setNombreArchivo(this.ecmIdentificador);
        archivo.setSolicitud(this.solicitud);
        archivo.setEstado("CARGADO");
        archivo.setFechaProcesamiento(new Date());
        archivo.setFechaActualizacion(new Date());
        archivo.setUsuario(usuario);
        archivo.setIdCargue(this.idCargue);
        archivo.setValSubsidio(Boolean.FALSE);
        return archivo;

    }
}
