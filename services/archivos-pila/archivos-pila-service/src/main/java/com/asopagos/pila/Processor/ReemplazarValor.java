package com.asopagos.pila.Processor;

import java.util.Map;

import co.com.heinsohn.lion.fileCommon.dto.FieldArgumentDTO;
import co.com.heinsohn.lion.fileCommon.exception.FileCommonException;
import co.com.heinsohn.lion.fileCommon.processors.FieldProcessor;

public class ReemplazarValor extends FieldProcessor {

	private static final String ACTUAL_VALOR = "ACTUAL_VALOR";
	private static final String NUEVO_VALOR = "NUEVO_VALOR";

	@Override
	public void process(FieldArgumentDTO arguments) throws FileCommonException {

		Object valorCampo = arguments.getFieldValue();

		if (valorCampo != null) {

			String actualValor = null;
			String nuevoValor = null;

			Map<String, String> params = this.getParams();
			for (Map.Entry<String, String> entry : params.entrySet()) {
				String nombreParametro = entry.getKey();

				if (nombreParametro.equalsIgnoreCase(ACTUAL_VALOR)) {
					actualValor = entry.getValue();
				}

				if (nombreParametro.equalsIgnoreCase(NUEVO_VALOR)) {
					nuevoValor = entry.getValue();
				}
			}

			if (actualValor != null && nuevoValor != null) {
				if (actualValor.equals(arguments.getFieldValue())) {
					arguments.setFieldValue(nuevoValor);
				}
			}
		}
	}
}
