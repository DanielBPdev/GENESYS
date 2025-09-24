package com.asopagos.novedades.personas.web.load.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
import javax.persistence.NoResultException;

import com.asopagos.locator.ResourceLocator;

public class NovedadMasivaCambioMedioDePagoTransferenciaLineValidator extends LineValidator{

    private ILogger logger = LogManager.getLogger(NovedadMasivaCambioMedioDePagoTransferenciaLineValidator.class);

    private List<ResultadoHallazgosValidacionArchivoDTO> lstHallazgos;

	private final String MENSAJE_GENERICO_HALLAZGO = "El registro no cumple con la estructura de contenido y/o la obligatoriedad de los datos";

    @Override
    public void validate(LineArgumentDTO arguments) throws FileProcessingException {
		logger.info("Inicia validador para archivo de cargue masivo novedad cambio medio de pago transferencia");
		logger.warn("incia public void validate(LineArgumentDTO arguments) throws FileProcessingException ");

		EntityManager em = obtenerEntityCore();

		lstHallazgos = new ArrayList<ResultadoHallazgosValidacionArchivoDTO>();
		Map<String, Object> line = arguments.getLineValues();
        
        // ((List<Long>) arguments.getContext().get(CamposArchivoConstants.TOTAL_REGISTRO)).add(1L);
        //      System.out.println("NovedadMasivaCambioMedioDePagoTransferenciaLineValidator - CamposArchivoConstants.TOTAL_REGISTRO: "+  arguments.getContext().get(CamposArchivoConstants.TOTAL_REGISTRO));
		try{
			TipoIdentificacionEnum tipoIdentificacionAfiliadoPrincipal = null;
			try{

				tipoIdentificacionAfiliadoPrincipal	= verificarTipoDocumento(line.get(ArchivoMultipleCampoConstants.TIPO_IDENTIFICACION_AFILIADO_PRINCIPAL).toString());			

				if(tipoIdentificacionAfiliadoPrincipal == null){
					lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),ArchivoMultipleCampoConstants.TIPO_IDENTIFICACION_AFILIADO_PRINCIPAL,
						this.MENSAJE_GENERICO_HALLAZGO));
				}
			}catch(Exception e){
				e.printStackTrace();
				lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),ArchivoMultipleCampoConstants.TIPO_IDENTIFICACION_AFILIADO_PRINCIPAL,
					this.MENSAJE_GENERICO_HALLAZGO));
			}
			verificarNumeroDocumento(tipoIdentificacionAfiliadoPrincipal, arguments, ArchivoMultipleCampoConstants.NUMERO_IDENTIFICACION_AFILIADO_PRINCIPAL);

			if(tipoIdentificacionAfiliadoPrincipal != null){
				String verificacionAfiliado =  verificarAfiliadoAdministrador(tipoIdentificacionAfiliadoPrincipal,line.get(ArchivoMultipleCampoConstants.NUMERO_IDENTIFICACION_AFILIADO_PRINCIPAL).toString(),em);
				if(verificacionAfiliado == null || !verificacionAfiliado.equals("VALIDACION_EXITOSA") || (verificacionAfiliado.equals("")) ){
					logger.warn("ENTREA IF "+verificacionAfiliado);
					lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),ArchivoMultipleCampoConstants.TIPO_IDENTIFICACION_AFILIADO_PRINCIPAL +" " + ArchivoMultipleCampoConstants.NUMERO_IDENTIFICACION_AFILIADO_PRINCIPAL ,
					(verificacionAfiliado == null || verificacionAfiliado.equals("")) ? "No existe registro asociado a ese tipo y numero de documento" : verificacionAfiliado));
				}
			}else{
				lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),ArchivoMultipleCampoConstants.TIPO_IDENTIFICACION_AFILIADO_PRINCIPAL,
					this.MENSAJE_GENERICO_HALLAZGO));
			}

			if(!verificarCodigoDeBanco(line.get(ArchivoMultipleCampoConstants.BANCO).toString(),em)){
				lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),ArchivoMultipleCampoConstants.BANCO,
						"El codigo de banco proporcionado no es valido"+line.get(ArchivoMultipleCampoConstants.BANCO).toString()));
			}
			logger.warn("========================================"+ verificarTipoCuenta(line.get(ArchivoMultipleCampoConstants.TIPO_CUENTA).toString(),line.get(ArchivoMultipleCampoConstants.BANCO).toString()));
			if(!verificarTipoCuenta(line.get(ArchivoMultipleCampoConstants.TIPO_CUENTA).toString(),line.get(ArchivoMultipleCampoConstants.BANCO).toString())){
				logger.warn("entra validacion tipo cuenta");
				lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),ArchivoMultipleCampoConstants.TIPO_CUENTA,
					"El tipo de cuenta proporcionado no es valido "+ line.get(ArchivoMultipleCampoConstants.TIPO_CUENTA).toString()));
			}
			if(!verificarNumeroDeCuenta(line.get(ArchivoMultipleCampoConstants.NUMERO_CUENTA).toString())){
				lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),ArchivoMultipleCampoConstants.NUMERO_CUENTA,
					"El numero de cuenta proporcionado no es valido"));
			}
		}catch(Exception e){
			logger.error("fallo la validacion del archivo para cargue masico transferencia");
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

	private Boolean verificarNumeroDeCuenta(String numeroCuenta){
		logger.info("private Boolean verificarNumeroDeCuenta(String numeroCuenta)"+numeroCuenta);
		return numeroCuenta != null && !numeroCuenta.isEmpty() && numeroCuenta.length() <= 15;
	}

	private Boolean verificarTipoCuenta(String tipoCuenta, String codigoBanco){
		logger.info("inicia private Boolean verificarTipoCuenta(tipoCuenta: "+tipoCuenta + "String codigoBanco" + codigoBanco +")");
		if ((tipoCuenta.equals("01") || tipoCuenta.equals("02") || tipoCuenta.equals("03")) && codigoBanco.equals("1051")){
			logger.warn("entra primer if tuenta");
			return Boolean.TRUE;
		}else if (!codigoBanco.equals("1051") && (tipoCuenta.equals("01") || tipoCuenta.equals("02"))){
			logger.warn("entra segundo if tuenta");
			return Boolean.TRUE;
		}else{
			logger.warn("entra tercer if tuenta");
			return Boolean.FALSE;
		}
	}

	private Boolean verificarCodigoDeBanco(String codigoBanco,EntityManager entityManager){
		logger.info("private Boolean verificarCodigoDeBanco(String codigoBanco "+codigoBanco + ",EntityManager entityManager)");
		return (Boolean) entityManager.createNamedQuery(NamedQueriesConstants.VERIFICAR_CODIGO_BANCO)
			.setParameter("codigoBanco",codigoBanco)
			.getSingleResult();
	}

	private String verificarAfiliadoAdministrador(TipoIdentificacionEnum tipoDocumento,String numeroDocumento,EntityManager entityManager){
		try{
			logger.warn("Inicia private String verificarAfiliadoAdministrador(TipoIdentificacionEnum "+ tipoDocumento +",numeroDocumento "+ numeroDocumento+ " ,EntityManager entityManager)");
			return (String) entityManager.createNamedQuery(NamedQueriesConstants.VALIDAR_AFILIADO_ADMINISTRADOR)
				.setParameter("tipoDocumento",tipoDocumento.name())
				.setParameter("numeroDocumento",numeroDocumento)
				.getSingleResult();
		}catch(NoResultException e){
			logger.error("Los datos de tipo: "+tipoDocumento+" y numero: "+numeroDocumento+" no coinciden para la busqueda de afiliado admin");
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
						"Tipo de identificación invalido para verificar respecto al número de identificación"));
			}
		} else {
			lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), numeroIdentificacionKey,
					"Tipo de identificación invalido para verificar respecto al número de identificación"));

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
                lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), campoMSG, mensaje));
                return;
            }

            if (campoKey.equals(ArchivoMultipleCampoConstants.NUMERO_IDENTIFICACION_AFILIADO_PRINCIPAL)) {
                if (!campoValue.toString().matches(regex)) {
                    lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), campoMSG, mensaje));
                }
            }
        } catch (Exception e) {
            lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), campoMSG, " Valor perteneciente " + campoMSG + " invalido"));
        }
    }

	private ResultadoHallazgosValidacionArchivoDTO crearHallazgo(Long lineNumber, String campo, String errorMessage) {
        String error = Interpolator.interpolate(ArchivoMultipleCampoConstants.MENSAJE_ERROR_CAMPO, campo, errorMessage);
        ResultadoHallazgosValidacionArchivoDTO hallazgo = new ResultadoHallazgosValidacionArchivoDTO();
        hallazgo.setNumeroLinea(lineNumber);
        hallazgo.setNombreCampo(campo);
        hallazgo.setError(error);
        return hallazgo;
    }

	private EntityManager obtenerEntityCore() {
        EntityManagerProceduresPersistenceLocal em = ResourceLocator.lookupEJBReference(EntityManagerProceduresPersistenceLocal.class);
        return em.getEntityManager();
    }
    
}
