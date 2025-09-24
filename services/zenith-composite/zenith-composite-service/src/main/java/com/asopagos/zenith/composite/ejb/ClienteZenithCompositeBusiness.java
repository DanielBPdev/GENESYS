package com.asopagos.zenith.composite.ejb;

import java.util.List;

import javax.ejb.Stateless;

import com.asopagos.cache.CacheManager;
import com.asopagos.constants.ConstantesSistemaConstants;
import com.asopagos.dto.BeneficiariosSubsidioFosfecDTO;
import com.asopagos.dto.ConsultaSubsidioFosfecDTO;
import com.asopagos.dto.SolicitudSubsidioFosfecDTO;
import com.asopagos.zenith.clients.ObtenerBeneficiariosSubsidioFosfec;
import com.asopagos.zenith.clients.ObtenerSolicitudesSubsidioFosfec;
import com.asopagos.zenith.composite.service.ClienteZenithCompositeService;

@Stateless
public class ClienteZenithCompositeBusiness implements ClienteZenithCompositeService{

	@Override
	public List<SolicitudSubsidioFosfecDTO> obtenerSolicitudesFosfec(ConsultaSubsidioFosfecDTO consultaSubsidioFosfecDTO) {
		
		consultaSubsidioFosfecDTO.setCodigoCCF(CacheManager.getConstante(ConstantesSistemaConstants.CAJA_COMPENSACION_CODIGO).toString());
		ObtenerSolicitudesSubsidioFosfec obtenerSolicitudes = new ObtenerSolicitudesSubsidioFosfec(consultaSubsidioFosfecDTO);
		obtenerSolicitudes.execute();
		return obtenerSolicitudes.getResult();
	}

	@Override
	public List<BeneficiariosSubsidioFosfecDTO> obtenerBeneficiariosFosfec(ConsultaSubsidioFosfecDTO consultaSubsidioFosfecDTO) {
		
		consultaSubsidioFosfecDTO.setCodigoCCF(CacheManager.getConstante(ConstantesSistemaConstants.CAJA_COMPENSACION_CODIGO).toString());
		ObtenerBeneficiariosSubsidioFosfec obtenerBeneficiarios = new ObtenerBeneficiariosSubsidioFosfec(consultaSubsidioFosfecDTO);
		obtenerBeneficiarios.execute();
		return obtenerBeneficiarios.getResult();
	}

	
}
