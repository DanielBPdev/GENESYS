package com.asopagos.pila.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import com.asopagos.entidades.pila.procesamiento.IndicePlanilla;
import com.asopagos.entidades.pila.procesamiento.IndicePlanillaOF;
import com.asopagos.enumeraciones.pila.BloqueValidacionEnum;

/**
 * <b>Descripcion:</b> DTO empleado por cada bloque de validación para informar al orquestador su resultado<br/>
 * <b>Módulo:</b> ArchivosPILAService - HU 391, 407, 393<br/>
 *
 * @author <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */

public class RespuestaValidacionDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** Entrada de índice de planilla OI */
    private IndicePlanilla indicePlanilla;

    /** Entrada de índice de planilla OF */
    private IndicePlanillaOF indicePlanillaOF;

    /** Listado de DTOs de errores funcional */
    private List<ErrorDetalladoValidadorDTO> erroresDetalladosValidadorDTO;
    
    /** Indicador de archivo de reproceso */
    private Boolean esReproceso = Boolean.FALSE;
    
    /** Listado de índices OI asociados a un indice OF */
    private List<IndicePlanilla> indicesOIenOF;
    
    /** Siguiente bloque de validación */
    private BloqueValidacionEnum bloqueSiguente;

    /** Constructor del DTO que inicializa el listado de errores */
    public RespuestaValidacionDTO() {
        erroresDetalladosValidadorDTO = new ArrayList<ErrorDetalladoValidadorDTO>();
    }

    /**
     * @return the indicePlanilla
     */
    public IndicePlanilla getIndicePlanilla() {
        return indicePlanilla;
    }

    /**
     * @param indicePlanilla
     *        the indicePlanilla to set
     */
    public void setIndicePlanilla(IndicePlanilla indicePlanilla) {
        this.indicePlanilla = indicePlanilla;
    }

    /**
     * @return the indicePlanillaOF
     */
    public IndicePlanillaOF getIndicePlanillaOF() {
        return indicePlanillaOF;
    }

    /**
     * @param indicePlanillaOF
     *        the indicePlanillaOF to set
     */
    public void setIndicePlanillaOF(IndicePlanillaOF indicePlanillaOF) {
        this.indicePlanillaOF = indicePlanillaOF;
    }

    /**
     * @return the errorDetalladoValidadorDTO
     */
    public List<ErrorDetalladoValidadorDTO> getErrorDetalladoValidadorDTO() {
        return erroresDetalladosValidadorDTO;
    }

    /**
     * @param errorDetalladoValidadorDTO
     *        the errorDetalladoValidadorDTO to add
     */
    public void addErrorDetalladoValidadorDTO(ErrorDetalladoValidadorDTO errorDetalladoValidadorDTO) {
        this.erroresDetalladosValidadorDTO.add(errorDetalladoValidadorDTO);
    }

    /**
     * Adiciona la lista de errores
     * @param listaErroresDetalladoValidadorDTO
     */
    public void addListaErroresDetalladoValidadorDTO(List<ErrorDetalladoValidadorDTO> listaErroresDetalladoValidadorDTO) {
        this.erroresDetalladosValidadorDTO.addAll(listaErroresDetalladoValidadorDTO);
    }
    
    /**
     * Método encargado de organizar los errores presentados de acuerdo a su número de línea
     */
    public void ordenarListaErrores() {
        Comparator<ErrorDetalladoValidadorDTO> comparador = (e1, e2) -> Long.compare(e1.getLineNumber() == null ? 0L : e1.getLineNumber(),
                e2.getLineNumber() == null ? -1L : e2.getLineNumber());
        this.erroresDetalladosValidadorDTO = this.erroresDetalladosValidadorDTO.stream().sorted(comparador).collect(Collectors.toList());
    }

    /**
     * @return the esReproceso
     */
    public Boolean getEsReproceso() {
        return esReproceso;
    }

    /**
     * @param esReproceso the esReproceso to set
     */
    public void setEsReproceso(Boolean esReproceso) {
        this.esReproceso = esReproceso;
    }

	/**
	 * @return the indicesOIenOF
	 */
	public List<IndicePlanilla> getIndicesOIenOF() {
		return indicesOIenOF;
	}

	/**
	 * @param indicesOIenOF the indicesOIenOF to set
	 */
	public void setIndicesOIenOF(List<IndicePlanilla> indicesOIenOF) {
		this.indicesOIenOF = indicesOIenOF;
	}

	/**
	 * @return the bloqueSiguente
	 */
	public BloqueValidacionEnum getBloqueSiguente() {
		return bloqueSiguente;
	}

	/**
	 * @param bloqueSiguente the bloqueSiguente to set
	 */
	public void setBloqueSiguente(BloqueValidacionEnum bloqueSiguente) {
		this.bloqueSiguente = bloqueSiguente;
	}
}
