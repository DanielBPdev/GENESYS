package com.asopagos.aportes.masivos.service.load.source;

import co.com.heinsohn.lion.filegenerator.dto.QueryFilterInDTO;
import com.asopagos.aportes.masivos.dto.AportanteCruceCierreDTO;
import java.util.List;
/**
 * <b>Descripcion:</b> Clase que se encarga de definir los atributos utilizados como filtros en la generación de archivos de descuento<br/>
 * <b>Módulo:</b> Asopagos - HU 432<br/>
 *
 * @author <a href="mailto:rlopez@heinsohn.com.co"> Roy López Cardona</a>
 */
public class ArchivoCruceAportesFilterDTO extends QueryFilterInDTO {

    private static final long serialVersionUID = 1L;

    /**
     * Atributo que representa el identificador del número Radicación.
     */
    private List<AportanteCruceCierreDTO> aportantes;
    


	public List<AportanteCruceCierreDTO> getAportantes() {
		return this.aportantes;
	}

	public void setAportantes(List<AportanteCruceCierreDTO> aportantes) {
		this.aportantes = aportantes;
	}

}
