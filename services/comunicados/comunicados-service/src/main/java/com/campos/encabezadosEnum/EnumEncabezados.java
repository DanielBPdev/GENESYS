package com.campos.encabezadosEnum;

import com.asopagos.dto.fovis.SolicitudLegalizacionDesembolsoDTO;
import com.asopagos.dto.fovis.SolicitudPostulacionFOVISDTO;

public class EnumEncabezados {
    
	public enum fovis{
    	HU_PROCESO_321_023(new SolicitudPostulacionFOVISDTO()),
    	HU_PROCESO_321_024(new SolicitudPostulacionFOVISDTO()),
    	HU_PROCESO_321_025(new SolicitudPostulacionFOVISDTO()),
    	HU_PROCESO_321_026(new SolicitudPostulacionFOVISDTO()),
		HU_PROCESO_321_028(new SolicitudPostulacionFOVISDTO()),
		HU_PROCESO_321_029(new SolicitudPostulacionFOVISDTO()),
		HU_PROCESO_321_030(new SolicitudPostulacionFOVISDTO()),
		HU_PROCESO_321_031(new SolicitudPostulacionFOVISDTO()),
		HU_PROCESO_321_034(new SolicitudPostulacionFOVISDTO()),
		HU_PROCESO_321_036(new SolicitudPostulacionFOVISDTO()),
		HU_PROCESO_321_037(new SolicitudPostulacionFOVISDTO()),
		HU_PROCESO_321_038(new SolicitudPostulacionFOVISDTO()),
		HU_PROCESO_323_046(new SolicitudPostulacionFOVISDTO()),
		HU_PROCESO_323_049(new SolicitudPostulacionFOVISDTO()),
		HU_PROCESO_323_050(new SolicitudPostulacionFOVISDTO()),
    	HU_PROCESO_324_053(new SolicitudLegalizacionDesembolsoDTO()),
    	HU_PROCESO_324_057(new SolicitudLegalizacionDesembolsoDTO()),
    	HU_PROCESO_324_058(new SolicitudLegalizacionDesembolsoDTO()),
    	HU_PROCESO_324_062(new SolicitudLegalizacionDesembolsoDTO()),
    	HU_PROCESO_324_102(new SolicitudLegalizacionDesembolsoDTO()),
    	HU_PROCESO_324_063(new SolicitudLegalizacionDesembolsoDTO()),
    	HU_PROCESO_324_065(new SolicitudLegalizacionDesembolsoDTO()),
    	HU_PROCESO_324_068(new SolicitudLegalizacionDesembolsoDTO()),
		HU_PROCESO_324_072(new SolicitudLegalizacionDesembolsoDTO()),
		HU_PROCESO_324_074(new SolicitudLegalizacionDesembolsoDTO());
		
		private Object dto;

		public Object getDto() {
			return dto;
		}

		public void setDto(Object dto) {
			this.dto = dto;
		}
		private fovis(Object dto){
			this.dto = dto;
		}
    }
	
	 public enum CamposEnum {	
		JEFEHOGAR("${jefeDeHogar}"),
		TIPOIDENTIFICACION("${tipoIdentificacion}"),
		NUMEROIDENTIFICACION("${numeroIdentificacion}"),
		MODALIDAD("${modalidad}"),
		TIPOTRANSACCION("${tipoDeTransaccion}"),
		NUMEROSOLICITUD("${numeroDeSolicitud}"),
		CICLOASIGNACION("${cicloDeAsignacion}"),
		METODOENVIO("${metodoEnvioDocumentos}"),
		ESTADOSOLICITUD("${estadoDeLaSolicitud}");
		
		private String key;
		
		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		private CamposEnum(String key){
			this.key = key;
		}
	 }
}
