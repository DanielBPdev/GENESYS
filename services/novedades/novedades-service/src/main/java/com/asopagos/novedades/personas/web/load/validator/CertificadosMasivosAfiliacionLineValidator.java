package com.asopagos.novedades.personas.web.load.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.asopagos.constants.ArchivoMultipleCampoConstants;
import com.asopagos.constants.ExpresionesRegularesConstants;
import com.asopagos.dto.ResultadoHallazgosValidacionArchivoDTO;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.util.Interpolator;
import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.validator.LineValidator;
import com.asopagos.enumeraciones.aportes.TipoAportanteEnum;
import com.asopagos.novedades.business.interfaces.EntityManagerProceduresPersistenceLocal;
import com.asopagos.novedades.constants.NamedQueriesConstants;
import javax.persistence.EntityManager;
import com.asopagos.locator.ResourceLocator;
public class CertificadosMasivosAfiliacionLineValidator extends LineValidator{

    private ILogger logger = LogManager.getLogger(CertificadosMasivosAfiliacionLineValidator.class);

    private List<ResultadoHallazgosValidacionArchivoDTO> lstHallazgos;

	private final String MENSAJE_GENERICO_HALLAZGO = "El registro no cumple con la estructura de contenido y/o la obligatoriedad de los datos";

    @Override
    public void validate(LineArgumentDTO arguments) throws FileProcessingException {
		logger.info("Inicia validador para archivo de cargue masivo novedad cambio medio de pago transferencia");
		logger.warn("incia public void validate(LineArgumentDTO arguments) throws FileProcessingException ");

		EntityManager em = obtenerEntityCore();

		lstHallazgos = new ArrayList<ResultadoHallazgosValidacionArchivoDTO>();
		Map<String, Object> line = arguments.getLineValues();

		try{
			TipoIdentificacionEnum tipoIdentificacionAfiliadoPrincipal = null;
			try{
                    
				tipoIdentificacionAfiliadoPrincipal	= verificarTipoDocumento(line.get(ArchivoMultipleCampoConstants.TIPO_IDENTIFICACION_AFILIADO).toString());			

				if(tipoIdentificacionAfiliadoPrincipal == null){
					lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),ArchivoMultipleCampoConstants.TIPO_IDENTIFICACION_AFILIADO,
						this.MENSAJE_GENERICO_HALLAZGO,line.get(ArchivoMultipleCampoConstants.NUMERO_IDENTIFICACION_AFILIADO).toString(),line.get(ArchivoMultipleCampoConstants.TIPO_IDENTIFICACION_AFILIADO).toString()));
				}
			}catch(Exception e){
				e.printStackTrace();
				lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),ArchivoMultipleCampoConstants.TIPO_IDENTIFICACION_AFILIADO,
					this.MENSAJE_GENERICO_HALLAZGO,line.get(ArchivoMultipleCampoConstants.NUMERO_IDENTIFICACION_AFILIADO).toString(),line.get(ArchivoMultipleCampoConstants.TIPO_IDENTIFICACION_AFILIADO).toString()));
			}
			verificarNumeroDocumento(tipoIdentificacionAfiliadoPrincipal, arguments, ArchivoMultipleCampoConstants.NUMERO_IDENTIFICACION_AFILIADO);

			if(tipoIdentificacionAfiliadoPrincipal != null){
				String verificacionAfiliado =  validacionesAfiliado(tipoIdentificacionAfiliadoPrincipal,line.get(ArchivoMultipleCampoConstants.NUMERO_IDENTIFICACION_AFILIADO).toString(),Long.valueOf(arguments.getContext().get(ArchivoMultipleCampoConstants.ID_EMPLEADOR_CERTIFICADO_MASIVO).toString()),em);
				if(verificacionAfiliado == null || !verificacionAfiliado.equals("VALIDACION_EXITOSA") || (verificacionAfiliado.equals("")) ){
					lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),ArchivoMultipleCampoConstants.TIPO_IDENTIFICACION_AFILIADO +" " + ArchivoMultipleCampoConstants.NUMERO_IDENTIFICACION_AFILIADO ,
					(verificacionAfiliado == null || verificacionAfiliado.equals("")) ? "No existe registro asociado a ese tipo y numero de documento" : verificacionAfiliado,line.get(ArchivoMultipleCampoConstants.NUMERO_IDENTIFICACION_AFILIADO).toString(),line.get(ArchivoMultipleCampoConstants.TIPO_IDENTIFICACION_AFILIADO).toString()));
				}
			}else{
				lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),ArchivoMultipleCampoConstants.TIPO_IDENTIFICACION_AFILIADO,
					this.MENSAJE_GENERICO_HALLAZGO,line.get(ArchivoMultipleCampoConstants.NUMERO_IDENTIFICACION_AFILIADO).toString(),line.get(ArchivoMultipleCampoConstants.TIPO_IDENTIFICACION_AFILIADO).toString()));
			}
		}catch(Exception e){
			logger.error("fallo la validacion del archivo para cargue certificados masivos");
			e.printStackTrace();
		}finally {
			((List<Long>) arguments.getContext().get(ArchivoMultipleCampoConstants.TOTAL_REGISTRO)).add(1L);
			((List<ResultadoHallazgosValidacionArchivoDTO>) arguments.getContext()
					.get(ArchivoMultipleCampoConstants.LISTA_HALLAZGOS)).addAll(lstHallazgos);
			if (!lstHallazgos.isEmpty()) {
				((List<Long>) arguments.getContext().get(ArchivoMultipleCampoConstants.TOTAL_REGISTRO_ERRORES)).add(1L);
			}
		}
		logger.info("Finalizaa validador para archivo de cargue masivo novedad cambio medio de pago transferencia");
    }

	private String verificarAfiliadoAdministrador(TipoIdentificacionEnum tipoDocumento,String numeroDocumento,EntityManager entityManager){
		logger.warn("Inicia private String verificarAfiliadoAdministrador(TipoIdentificacionEnum "+ tipoDocumento +",numeroDocumento "+ numeroDocumento+ " ,EntityManager entityManager)");
		return (String) entityManager.createNamedQuery(NamedQueriesConstants.VALIDAR_AFILIADO_ADMINISTRADOR)
			.setParameter("tipoDocumento",tipoDocumento.name())
			.setParameter("numeroDocumento",numeroDocumento)
			.getSingleResult();
	}

    private String validacionesAfiliado(TipoIdentificacionEnum tipoDocumento,String numeroDocumento,Long idEmpleador,EntityManager entityManager){
        logger.warn("Incia string validacionesAfiliado(TipoIdentificacionEnum tipoDocumento,String numeroDocumento,EntityManager entityManager)");
        try{
            return (String) entityManager.createNamedQuery(NamedQueriesConstants.VALIDAR_RELACION_AFILIADO_CERTIFICADOS_MASIVOS)
                .setParameter("tipoDocumento",tipoDocumento.name())
                .setParameter("numeroDocumento",numeroDocumento)
                .setParameter("idEmpleador",idEmpleador)
                .getSingleResult();
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
	
	private TipoIdentificacionEnum verificarTipoDocumento(String diminutivo) {
		switch (diminutivo) {
			case "CC":
				return TipoIdentificacionEnum.CEDULA_CIUDADANIA;
			case "CE":
				return TipoIdentificacionEnum.CEDULA_EXTRANJERIA;
			case "TI":
				return TipoIdentificacionEnum.TARJETA_IDENTIDAD;
			case "PA":
				return TipoIdentificacionEnum.PASAPORTE;
			case "CD":
				return TipoIdentificacionEnum.CARNE_DIPLOMATICO;
			case "SC":
				return TipoIdentificacionEnum.SALVOCONDUCTO;
			case "PE":
				return TipoIdentificacionEnum.PERM_ESP_PERMANENCIA;
			case "PT":
				return TipoIdentificacionEnum.PERM_PROT_TEMPORAL;
			default:
				return null;
		}
	}	

    private void verificarNumeroDocumento(TipoIdentificacionEnum tipoIdentificacion, LineArgumentDTO arguments,
			String numeroIdentificacionKey) {
		if (tipoIdentificacion != null) {
			if (tipoIdentificacion.equals(TipoIdentificacionEnum.CEDULA_CIUDADANIA)) {
				// se valida el número de identificación
				validarRegex(arguments, numeroIdentificacionKey, ExpresionesRegularesConstants.CEDULA_CIUDADANIA,
						"La cédula de ciudadanía deber tener 3, 4, 5, 8 o 10 dígitos.", numeroIdentificacionKey);
				return;
			} else if (tipoIdentificacion.equals(TipoIdentificacionEnum.NIT)) {
                validarRegex(arguments, numeroIdentificacionKey, ExpresionesRegularesConstants.NIT,
                        "El nit solo puede hasta 10.",
                        numeroIdentificacionKey);
                return;
            }else if (tipoIdentificacion.equals(TipoIdentificacionEnum.CEDULA_EXTRANJERIA)) {
				// se valida el número de identificación
				validarRegex(arguments, numeroIdentificacionKey, ExpresionesRegularesConstants.CEDULA_EXTRANJERIA,
						"La cédula de extranjería debe tener máximo 16 caracteres.", numeroIdentificacionKey);
				return;
			} else if (tipoIdentificacion.equals(TipoIdentificacionEnum.TARJETA_IDENTIDAD)) {
				// se valida el número de identificación
				validarRegex(arguments, numeroIdentificacionKey, ExpresionesRegularesConstants.TARJETA_IDENTIDAD,
						"La tarjeta de identidad solo puede ser de 10 u 11 dígitos.", numeroIdentificacionKey);
				return;
			} else if (tipoIdentificacion.equals(TipoIdentificacionEnum.PASAPORTE)) {
				// se valida el número de identificación
				validarRegex(arguments, numeroIdentificacionKey, ExpresionesRegularesConstants.PASAPORTE,
						"El pasaporte solo puede ser de 1 o 16 caracteres.", numeroIdentificacionKey);
				return;
			} else if (tipoIdentificacion.equals(TipoIdentificacionEnum.REGISTRO_CIVIL)) {
				// se valida el número de identificación
				validarRegex(arguments, numeroIdentificacionKey, ExpresionesRegularesConstants.REGISTRO_CIVIL,
						"El registro civil solo puede ser de 8, 10 u 11 caracteres.", numeroIdentificacionKey);
				return;
			} else if (tipoIdentificacion.equals(TipoIdentificacionEnum.CARNE_DIPLOMATICO)) {
				// se valida el número de identificación
				validarRegex(arguments, numeroIdentificacionKey, ExpresionesRegularesConstants.CARNE_DIPLOMATICO,
						"La estructura del Carné diplomático no coincide.", numeroIdentificacionKey);
				return;
			} else if (tipoIdentificacion.equals(TipoIdentificacionEnum.SALVOCONDUCTO)) {
				// se valida el número de identificación
				validarRegex(arguments, numeroIdentificacionKey, ExpresionesRegularesConstants.SALVO_CONDUCTO,
						"El salvoconducto de permanencia solo puede ser de 9 digitos.", numeroIdentificacionKey);
				return;
			} else if (tipoIdentificacion.equals(TipoIdentificacionEnum.PERM_ESP_PERMANENCIA)) {
				// se valida el número de identificación
				validarRegex(arguments, numeroIdentificacionKey, ExpresionesRegularesConstants.PERM_ESP_PERMANENCIA,
						"El permiso especial debe tener 15 dígitos.", numeroIdentificacionKey);
				return;
			}  else if (tipoIdentificacion.equals(TipoIdentificacionEnum.PERM_PROT_TEMPORAL)) {
				// se valida el número de identificación
				validarRegex(arguments, numeroIdentificacionKey, ExpresionesRegularesConstants.PERM_PROT_TEMPORAL,
						"El Permiso por Protección Temporal debe tener máximo 8 dígitos.", numeroIdentificacionKey);
				return;
			}  else {
				lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), numeroIdentificacionKey,
						"Tipo de identificación invalido para verificar respecto al número de identificación","",""));
			}
		} else {
			lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), numeroIdentificacionKey,
					"Tipo de identificación invalido para verificar respecto al número de identificación","",""));

		}
	}
    /**
     * Método encargado de evaluar la validez de un campo frente a una expresión regular
     * @param arguments
     *        argumentos de la linea
     * @param campoVal
     *        valor del campo
     * @param regex
     * @param mensaje
     * @param campoMSG
     */
    private void validarRegex(LineArgumentDTO arguments, String campoKey, String regex, String mensaje, String campoMSG) {
        try {
            Object campoValue = ((Object) ((Map<String, Object>) arguments.getLineValues()).get(campoKey));
            if (campoValue == null || campoValue.equals("")) {
                lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), campoMSG, mensaje,"",""));
                return;
            }

            if (campoKey.equals(ArchivoMultipleCampoConstants.NUMERO_IDENTIFICACION_AFILIADO)) {
                if (!campoValue.toString().matches(regex)) {
                    lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), campoMSG, mensaje,"",""));
                }
            }
        } catch (Exception e) {
            lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), campoMSG, " Valor perteneciente " + campoMSG + " invalido","",""));
        }
    }

	private ResultadoHallazgosValidacionArchivoDTO crearHallazgo(Long lineNumber, String campo, String errorMessage,String numerDocumento,String tipoDocumento) {
        String error = Interpolator.interpolate(ArchivoMultipleCampoConstants.MENSAJE_ERROR_CAMPO, campo, errorMessage);
        ResultadoHallazgosValidacionArchivoDTO hallazgo = new ResultadoHallazgosValidacionArchivoDTO();
        hallazgo.setNumeroLinea(lineNumber);
        hallazgo.setNombreCampo(campo);
        hallazgo.setError(error);
		hallazgo.setNumeroDocumento(numerDocumento);
		hallazgo.setTipoDocumento(tipoDocumento);
        return hallazgo;
    }

	private EntityManager obtenerEntityCore() {
        EntityManagerProceduresPersistenceLocal em = ResourceLocator.lookupEJBReference(EntityManagerProceduresPersistenceLocal.class);
        return em.getEntityManager();
    }
    
}
