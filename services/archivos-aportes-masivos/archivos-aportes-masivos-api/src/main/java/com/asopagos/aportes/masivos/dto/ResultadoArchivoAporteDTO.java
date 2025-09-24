/**
 * 
 */
package com.asopagos.aportes.masivos.dto;

import com.asopagos.dto.aportes.CotizanteDTO;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.aportes.masivos.dto.DatosRadicacionMasivaDTO;
import com.asopagos.aportes.masivos.dto.ResultadoCotizanteAporteMasivoDTO;
import com.asopagos.dto.ResultadoHallazgosValidacionArchivoDTO;


@XmlRootElement
public class ResultadoArchivoAporteDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;

    // Es necesario asociar el ECM al dato

    private List<ResultadoValidacionAporteDTO> resultadoAportes;

    private String ecmIdentificador;

    private Long idArchivoMasivo;
    //private String ecmIdentificadorCargueArchivo;
    
    private List<ResultadoHallazgosValidacionArchivoDTO> resultadoHallazgosAportesMasivos;

    private String numeroRadicado;


    public List<ResultadoValidacionAporteDTO> getResultadoAportes() {
        return this.resultadoAportes;
    }

    public void setResultadoAportes(List<ResultadoValidacionAporteDTO> resultadoAportes) {
        this.resultadoAportes = resultadoAportes;
    }

    public String getEcmIdentificador() {
        return this.ecmIdentificador;
    }

    public void setEcmIdentificador(String ecmIdentificador) {
        this.ecmIdentificador = ecmIdentificador;
    }

    public List<ResultadoHallazgosValidacionArchivoDTO> getResultadoHallazgosAportesMasivos() {
        return this.resultadoHallazgosAportesMasivos;
    }

    public void setResultadoHallazgosAportesMasivos(List<ResultadoHallazgosValidacionArchivoDTO> resultadoHallazgosAportesMasivos) {
        this.resultadoHallazgosAportesMasivos = resultadoHallazgosAportesMasivos;
    }


    public Long getIdArchivoMasivo() {
        return this.idArchivoMasivo;
    }

    public void setIdArchivoMasivo(Long idArchivoMasivo) {
        this.idArchivoMasivo = idArchivoMasivo;
    }


    public String getNumeroRadicado() {
        return this.numeroRadicado;
    }

    public void setNumeroRadicado(String numeroRadicado) {
        this.numeroRadicado = numeroRadicado;
    }
    

}
