package com.asopagos.clienteanibol.ejb;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Response;
import com.asopagos.clienteanibol.dto.BeneficiariosSubsidioFosfecDTO;
import com.asopagos.clienteanibol.dto.ConsultaSubsidioFosfecDTO;
import com.asopagos.clienteanibol.dto.SolicitudesSubsidioFosfecDTO;
import com.asopagos.clienteanibol.enums.BeneficiarioMPCEnum;
import com.asopagos.clienteanibol.enums.VerificacionRequisitosEnum;
import com.asopagos.clienteanibol.service.IntegracionZenithService;
import com.asopagos.enumeraciones.personas.GeneroEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.TipoCumplimientoEnum;

public class IntegracionZenithBusiness implements IntegracionZenithService {

	@Override
	public Response obtenerSolicitudesSubsidioFosfec(ConsultaSubsidioFosfecDTO consultaSubsidioFosfecDTO) {

		List<SolicitudesSubsidioFosfecDTO> listaSolicitudesSubsidio = new ArrayList<SolicitudesSubsidioFosfecDTO>();
		
		SolicitudesSubsidioFosfecDTO solicitudesSubsidio1 = new SolicitudesSubsidioFosfecDTO(
				TipoIdentificacionEnum.CEDULA_CIUDADANIA, "105788551045", TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE,
				"CCF05", 1340314159000l, "ADMS10", false, "AMDP015", false, false, "ADMFC02", 15, false,
				VerificacionRequisitosEnum.APROBADO, 1340314159000l, 15);
		SolicitudesSubsidioFosfecDTO solicitudesSubsidio2 = new SolicitudesSubsidioFosfecDTO(
				TipoIdentificacionEnum.CEDULA_CIUDADANIA, "105788551045", TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE,
				"CCF05", 1340314159000l, "ADMS10", false, "AMDP015", false, false, "ADMFC02", 15, false,
				VerificacionRequisitosEnum.APROBADO, 1340314159000l, 15);
		SolicitudesSubsidioFosfecDTO solicitudesSubsidio3 = new SolicitudesSubsidioFosfecDTO(
				TipoIdentificacionEnum.CEDULA_CIUDADANIA, "105788551045", TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE,
				"CCF05", 1340314159000l, "ADMS10", false, "AMDP015", false, false, "ADMFC02", 15, false,
				VerificacionRequisitosEnum.APROBADO, 1340314159000l, 15);

		listaSolicitudesSubsidio.add(solicitudesSubsidio1);
		listaSolicitudesSubsidio.add(solicitudesSubsidio2);
		listaSolicitudesSubsidio.add(solicitudesSubsidio3);

		return Response.ok().entity(listaSolicitudesSubsidio).build();

	}

	@Override
	public Response obtenerBeneficiariosSubsidioFosfec(ConsultaSubsidioFosfecDTO consultaSubsidioFosfecDTO) {
		List<BeneficiariosSubsidioFosfecDTO> listaBeneficiariosSubsidio = new ArrayList<BeneficiariosSubsidioFosfecDTO>();

		BeneficiariosSubsidioFosfecDTO beneficiariosSubsidioFosfecDTO1 = new BeneficiariosSubsidioFosfecDTO(
				TipoIdentificacionEnum.CEDULA_CIUDADANIA, "105788551045", "PN", "SN", "PA", "SA", 824851759000l,
				GeneroEnum.MASCULINO, "CFF09", BeneficiarioMPCEnum.SI, TipoCumplimientoEnum.CUMPLE, 824851759000l,
				824851759000l, 0, 0, 0, 0, 0, 0, 0, 0, 1000, 0, false, 824851759000l, false, 824851759000l, false,
				824851759000l, 15);

		BeneficiariosSubsidioFosfecDTO beneficiariosSubsidioFosfecDTO2 = new BeneficiariosSubsidioFosfecDTO(
				TipoIdentificacionEnum.CEDULA_CIUDADANIA, "105788551045", "PN", "SN", "PA", "SA", 824851759000l,
				GeneroEnum.MASCULINO, "CFF09", BeneficiarioMPCEnum.SI, TipoCumplimientoEnum.CUMPLE, 824851759000l,
				824851759000l, 0, 0, 0, 0, 0, 0, 0, 0, 1000, 0, false, 824851759000l, false, 824851759000l, false,
				824851759000l, 15);

		BeneficiariosSubsidioFosfecDTO beneficiariosSubsidioFosfecDTO3 = new BeneficiariosSubsidioFosfecDTO(
				TipoIdentificacionEnum.CEDULA_CIUDADANIA, "105788551045", "PN", "SN", "PA", "SA", 824851759000l,
				GeneroEnum.MASCULINO, "CFF09", BeneficiarioMPCEnum.NO, TipoCumplimientoEnum.CUMPLE, 824851759000l,
				824851759000l, 0, 0, 0, 0, 0, 0, 0, 0, 1000, 0, false, 824851759000l, false, 824851759000l, false,
				824851759000l, 15);

		BeneficiariosSubsidioFosfecDTO beneficiariosSubsidioFosfecDTO4 = new BeneficiariosSubsidioFosfecDTO(
				TipoIdentificacionEnum.CEDULA_CIUDADANIA, "105788551045", "PN", "SN", "PA", "SA", 824851759000l,
				GeneroEnum.MASCULINO, "CFF09", BeneficiarioMPCEnum.NO, TipoCumplimientoEnum.CUMPLE, 824851759000l,
				824851759000l, 0, 0, 0, 0, 0, 0, 0, 0, 1000, 0, false, 824851759000l, false, 824851759000l, false,
				824851759000l, 15);

		BeneficiariosSubsidioFosfecDTO beneficiariosSubsidioFosfecDTO5 = new BeneficiariosSubsidioFosfecDTO(
				TipoIdentificacionEnum.CEDULA_CIUDADANIA, "105788551045", "PN", "SN", "PA", "SA", 824851759000l,
				GeneroEnum.MASCULINO, "CFF09", BeneficiarioMPCEnum.PROCESO_VERIFICACIÓN, TipoCumplimientoEnum.CUMPLE,
				824851759000l, 824851759000l, 0, 0, 0, 0, 0, 0, 0, 0, 1000, 0, false, 824851759000l, false,
				824851759000l, false, 824851759000l, 15);

		listaBeneficiariosSubsidio.add(beneficiariosSubsidioFosfecDTO1);
		listaBeneficiariosSubsidio.add(beneficiariosSubsidioFosfecDTO2);
		listaBeneficiariosSubsidio.add(beneficiariosSubsidioFosfecDTO3);
		listaBeneficiariosSubsidio.add(beneficiariosSubsidioFosfecDTO4);
		listaBeneficiariosSubsidio.add(beneficiariosSubsidioFosfecDTO5);

		return Response.ok().entity(listaBeneficiariosSubsidio).build();
	}

	@Override
	public Response consultarArchivoMaestroTrabajadoresActivos() {
		
		return null;
	}

}