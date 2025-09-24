package com.asopagos.reportes.giass;

import java.util.ArrayList;
import java.util.List;

import com.asopagos.reportes.normativos.GeneradorContenidoArchivoUtil;

public abstract class ReporteGiassAbstract {

	protected List<String[]> listHeader;
	protected String nameQuery;
	public String fileName;

	protected abstract List<String[]> obtenerDatos();

	public List<String[]> convertResponseNativeToArrayString(List<Object[]> responseNative) {
		List<String[]> arrayStringResponse = new ArrayList<>();
		for (Object[] response : responseNative) {
			String[] responseString = new String[response.length];
			for (int i = 0; i < response.length; i++) {
				responseString[i] = response[i] != null ? response[i].toString() : null;
			}
			arrayStringResponse.add(responseString);
		}
		return arrayStringResponse;
	}
	
	
	public byte[] generarReporte(){
		return GeneradorContenidoArchivoUtil
				.generarArchivoPlano(null, this.obtenerDatos() , ",");
	}

}
