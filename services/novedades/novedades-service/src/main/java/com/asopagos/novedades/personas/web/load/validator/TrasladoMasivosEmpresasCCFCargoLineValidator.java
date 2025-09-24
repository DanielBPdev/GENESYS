package com.asopagos.novedades.personas.web.load.validator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import com.asopagos.novedades.constants.ArchivoTrasladoEmpresasCCFConstants;
import com.asopagos.dto.ResultadoHallazgosValidacionArchivoDTO;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.trasladoEmpresasCCF.GeneroEnumA;
import com.asopagos.enumeraciones.trasladoEmpresasCCF.AreaGeograficaEnumA;
import com.asopagos.enumeraciones.trasladoEmpresasCCF.BenefeciarioCuotaMonetariaEnumA;
import com.asopagos.enumeraciones.trasladoEmpresasCCF.CategoriaEnumA;
import com.asopagos.enumeraciones.trasladoEmpresasCCF.CondicionDiscapacidadEnumA;
import com.asopagos.enumeraciones.trasladoEmpresasCCF.EstadoCivilEnumA;
import com.asopagos.enumeraciones.trasladoEmpresasCCF.FactorVulnerabilidadEnumA;
import com.asopagos.enumeraciones.trasladoEmpresasCCF.NivelEscolaridadEnumA;
import com.asopagos.enumeraciones.trasladoEmpresasCCF.OrientacionSexualEnumA;
import com.asopagos.enumeraciones.trasladoEmpresasCCF.ParentezcoEnumA;
import com.asopagos.enumeraciones.trasladoEmpresasCCF.PerteneciaEtnicaEnumA;
import com.asopagos.enumeraciones.trasladoEmpresasCCF.TipoAfiliadoEnumA;
import com.asopagos.enumeraciones.trasladoEmpresasCCF.TipoCuotaModeradoraEnumA;
import com.asopagos.enumeraciones.trasladoEmpresasCCF.TipoIdentificacionAfiliadoEnumA;
import com.asopagos.enumeraciones.trasladoEmpresasCCF.TipoIdentificacionEmpleadorEnumA;
import com.asopagos.empleadores.clients.ConsultarEmpleadorTipoNumero;
import com.asopagos.util.CalendarUtils;
import com.asopagos.util.GetValueUtil;
import com.asopagos.util.Interpolator;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.validator.LineValidator;
import com.asopagos.dto.modelo.PersonaModeloDTO;
import com.asopagos.entidades.transversal.personas.ISujetoTramite;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.constants.ExpresionesRegularesConstants;
import java.text.SimpleDateFormat;
import com.asopagos.afiliaciones.clients.BuscarMunicipio;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.validaciones.clients.ValidarReglasNegocio;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import java.util.HashMap;




public class TrasladoMasivosEmpresasCCFCargoLineValidator extends LineValidator{
    /**
	 * Listado de hallazgos
	 */
	private List<ResultadoHallazgosValidacionArchivoDTO> lstHallazgos;
	private static final ILogger logger = LogManager.getLogger(TrasladoMasivosEmpresasCCFLineValidator.class);


	/**
	 * (non-Javadoc)
	 * 
	 * @see co.com.heinsohn.lion.fileprocessing.fileloader.validator.LineValidator#validate(co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO)
	 */
	// TODO: agregar validacion para beneficiarios usando el servicio de ValidarReglasNegocio
	@Override
	public void validate(LineArgumentDTO arguments) throws FileProcessingException {
        System.out.println("Nico estuvo aqui >:) LINE Cargo");
		String docEmpleador= (String) arguments.getContext().get("docEmpleador");
		String tipoDocumneto= (String) arguments.getContext().get("tipoDocumneto");
		TipoIdentificacionEnum tipoIdentificacionBeneficiario = null;
		TipoIdentificacionEnum tipoIdentificacionAfiliado = null;
		String numeroIdentificacionAfiliado = null;
		String numeroIdentificacionBeneficiario = null;
		String clasificacionBeneficiario = null;

		lstHallazgos = new ArrayList<>();
		Map<String, Object> line = arguments.getLineValues();
		try {

			if (line.get(ArchivoTrasladoEmpresasCCFConstants.TIPO_DOCUMENTO_AFILIADO_A_CARGO) != null
				&& line.get(ArchivoTrasladoEmpresasCCFConstants.NUMERO_DOCUMENTO_AFILIADO_A_CARGO) != null) {
				String tipoDocumentoAfiliadoCargo = ((String) line.get(ArchivoTrasladoEmpresasCCFConstants.TIPO_DOCUMENTO_AFILIADO_A_CARGO))
						.toUpperCase();

				String numeroDocumentoAfiliadoCargo = ((String) line.get(ArchivoTrasladoEmpresasCCFConstants.NUMERO_DOCUMENTO_AFILIADO_A_CARGO))
						.toUpperCase();
				numeroIdentificacionBeneficiario = ((String) line.get(ArchivoTrasladoEmpresasCCFConstants.NUMERO_DOCUMENTO_AFILIADO_A_CARGO)).toUpperCase();	
				

				try{
					TipoIdentificacionAfiliadoEnumA tipoAfiliadoCargo = TipoIdentificacionAfiliadoEnumA.obtenerTipoIdentificacionEnum(Integer.parseInt(tipoDocumentoAfiliadoCargo));

					// TODO 10: guardar valor en la variable tipoIdentificacionBeneficiario TipoIdentificacionEnum.valueOf(tipoAfiliadoCargo.name());
					tipoIdentificacionAfiliado = TipoIdentificacionEnum.valueOf(tipoAfiliadoCargo.name());
					tipoIdentificacionBeneficiario = TipoIdentificacionEnum.valueOf(tipoAfiliadoCargo.name());


				}catch(Exception e){
					System.out.println("error "+e);
					lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
										"Tipo de documento afiliado a cargo",
										"Tipo de identificación del afiliado no valida"));
				}
			}else{
					lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
										"Tipo de documento afiliado a cargo",
										"Es un campo obligatorio"));

			}

			if (line.get(ArchivoTrasladoEmpresasCCFConstants.TIPO_DOCUMENTO_AFILIADO_CARGO) != null) {

				String tipoDocumentoAfiliado = ((String) line.get(ArchivoTrasladoEmpresasCCFConstants.TIPO_DOCUMENTO_AFILIADO_CARGO))
						.toUpperCase();

				try{
					TipoIdentificacionAfiliadoEnumA tipoB = TipoIdentificacionAfiliadoEnumA.obtenerTipoIdentificacionEnum(Integer.parseInt(tipoDocumentoAfiliado));
                    tipoIdentificacionAfiliado = TipoIdentificacionEnum.valueOf(tipoB.name());
				}catch(Exception e){
					System.out.println("error "+e);
					lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
										"Tipo de documento afiliado",
										"Tipo de identificación del afiliado no valida"));
				}
			}else{
				lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
										"Tipo de documento afiliado",
										"El campo es obligatorio"));

			}
			if (line.get(ArchivoTrasladoEmpresasCCFConstants.NUMERO_DOCUMENTO_AFILIADO_CARGO) != null) {

				// TODO 12: guardar valor en la variable numeroIdentificacionAfiliado  ((String) line.get(ArchivoTrasladoEmpresasCCFConstants.NUMERO_DOCUMENTO_AFILIADO_CARGO))
				String numeroDocumentoAfiliado = ((String) line.get(ArchivoTrasladoEmpresasCCFConstants.NUMERO_DOCUMENTO_AFILIADO_CARGO))
						.toUpperCase();
				numeroIdentificacionAfiliado = ((String) line.get(ArchivoTrasladoEmpresasCCFConstants.NUMERO_DOCUMENTO_AFILIADO_CARGO));
					
					verificarNumeroDocumento(tipoIdentificacionAfiliado,arguments,"NUMERO_DOCUMENTO_AFILIADO_CARGO");


				try{
					String tipoDocumentoAfiliadoCargo = ((String) line.get(ArchivoTrasladoEmpresasCCFConstants.TIPO_DOCUMENTO_AFILIADO_A_CARGO))
							.toUpperCase();
					TipoIdentificacionAfiliadoEnumA tipoB = TipoIdentificacionAfiliadoEnumA.obtenerTipoIdentificacionEnum(Integer.parseInt(tipoDocumentoAfiliadoCargo));
					TipoIdentificacionEnum tipoIdentificacionBeneficiario1 = TipoIdentificacionEnum.valueOf(tipoB.name());
					/*if(ejecutarMallaValidacion(tipoIdentificacionBeneficiario1,numeroDocumentoAfiliado) == false){
							lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
							"Documento de identificación del afiliado",
							"no cumple con alguna de las siguiente validaciones; persona fallecida, expulsión por mora, expulsión por uso indebido de servicios o expulsión por suministro de información incorrecta o no entrega de información"));
					}*/
				}catch(Exception e){
					e.printStackTrace();
				}

			}else{
				lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
										"NumeroIdentificacionAfiliadoPrincipal",
										"El campo es obligatorio"));
			}
			
			if (line.get(ArchivoTrasladoEmpresasCCFConstants.TIPO_DOCUMENTO_AFILIADO_A_CARGO) != null) {
				String tipoDocumentoAfiliadoCargo = ((String) line.get(ArchivoTrasladoEmpresasCCFConstants.TIPO_DOCUMENTO_AFILIADO_A_CARGO))
						.toUpperCase();

				try{
					TipoIdentificacionAfiliadoEnumA tipoB = TipoIdentificacionAfiliadoEnumA.obtenerTipoIdentificacionEnum(Integer.parseInt(tipoDocumentoAfiliadoCargo));
					tipoIdentificacionBeneficiario = TipoIdentificacionEnum.valueOf(tipoB.name());
				}catch(Exception e){
					System.out.println("error "+e);
					lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
										"Tipo de documento empleador",
										"Tipo de identificación del afiliado no valida"));
				}
			}
			else{
				lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
										"Tipo de documento empleador",
										"El campo es obligatorio"));
			}
			if (line.get(ArchivoTrasladoEmpresasCCFConstants.NUMERO_DOCUMENTO_AFILIADO_A_CARGO) != null) {
				String numeroDocumentoAfiliadoCargo = ((String) line.get(ArchivoTrasladoEmpresasCCFConstants.NUMERO_DOCUMENTO_AFILIADO_A_CARGO))
						.toUpperCase();
					
					verificarNumeroDocumento(tipoIdentificacionBeneficiario,arguments,"NUMERO_DOCUMENTO_AFILIADO_A_CARGO");

			}else{
				lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
										"NumeroIdentificacionAfiliadoaCargo",
										"El campo es obligatorio"));
			}


			if (line.get(ArchivoTrasladoEmpresasCCFConstants.PRIMER_NOMBRE_AFILIADO_A_CARGO) != null) {
				String primerNombreAfiliadoCargo = ((String) line.get(ArchivoTrasladoEmpresasCCFConstants.PRIMER_NOMBRE_AFILIADO_A_CARGO))
						.toUpperCase();

				if(primerNombreAfiliadoCargo.length()>30){
						lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
								"primerNombreAfiliadoCargo",
								"Longitud del primerNombreAfiliadoCargo superior"));
				}

			}else{
				lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
								"primerNombreAfiliadoCargo",
								"Longitud del primerNombreAfiliadoCargo superior"));

			}
			if (line.get(ArchivoTrasladoEmpresasCCFConstants.SEGUNDO_NOMBRE_AFILIADO_A_CARGO) != null) {
				String segundoNombreAfiliadoCargo = ((String) line.get(ArchivoTrasladoEmpresasCCFConstants.SEGUNDO_NOMBRE_AFILIADO_A_CARGO))
						.toUpperCase();

						if(segundoNombreAfiliadoCargo != null){
							if(segundoNombreAfiliadoCargo.length()>30){
								lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
										"segundoNombreAfiliadoCargo",
										"Longitud del segundoNombreAfiliadoCargo superior"));
							}
						} 

			}
			if (line.get(ArchivoTrasladoEmpresasCCFConstants.PRIMER_APELLIDO_AFILIADO_A_CARGO) != null) {
				String primerApellidoAfiliadoCargo = ((String) line.get(ArchivoTrasladoEmpresasCCFConstants.PRIMER_APELLIDO_AFILIADO_A_CARGO))
						.toUpperCase();
				
				if(primerApellidoAfiliadoCargo.length()>30){
						lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
								"primerApellidoAfiliadoCargo",
								"Longitud del primerApellidoAfiliadoCargo superior"));
				}

			}else{
				lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
								"primerApellidoAfiliadoCargo",
								"El campo es obligatorio"));
			}
			if (line.get(ArchivoTrasladoEmpresasCCFConstants.SEGUNDO_APELLIDO_AFILIADO_A_CARGO) != null) {
				String segundoApellidoAfiliadoCargo = ((String) line.get(ArchivoTrasladoEmpresasCCFConstants.SEGUNDO_APELLIDO_AFILIADO_A_CARGO))
						.toUpperCase();
						if(segundoApellidoAfiliadoCargo != null){
							if(segundoApellidoAfiliadoCargo.length()>30){
								lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
										"segundoApellidoAfiliadoCargo",
										"Longitud del segundoApellidoAfiliadoCargo superior"));
							}
						} 

			}
			if (line.get(ArchivoTrasladoEmpresasCCFConstants.FECHA_NACIMIENTO_AFILIADO_A_CARGO) != null) {
				String fechaNacimiento = ((String) line.get(ArchivoTrasladoEmpresasCCFConstants.FECHA_NACIMIENTO_AFILIADO_A_CARGO))
						.toUpperCase();

				try{
					Date fechaNacimientoD =new SimpleDateFormat("yyyyMMdd").parse(fechaNacimiento);  
					System.out.println(fechaNacimientoD);
					if(Integer.parseInt(fechaNacimiento) < 19200101){
							lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
										"fechaNacimiento",
										"Fecha de nacimiento del afiliado principal no valida"));
					}
				}catch(Exception e){
					System.out.println("error fechaNacimiento "+e);
					lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
									"fechaNacimiento",
									"Fecha de nacimiento del afiliado principal no valida"));
				}
			}else{
				lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
									"fechaNacimiento",
									"El campo es obligatorio"));

			}
			if (line.get(ArchivoTrasladoEmpresasCCFConstants.SEXO_AFILIADO_A_CARGO) != null) {
				String sexoAfiliadoCargo = ((String) line.get(ArchivoTrasladoEmpresasCCFConstants.SEXO_AFILIADO_A_CARGO))
						.toUpperCase();

					GeneroEnumA genero = GeneroEnumA.obtenerGeneroEnum(Integer.parseInt(sexoAfiliadoCargo));
					if (genero == null){//obtener tipo
						lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
								"sexoAfiliadoCargo",
								"Genero del afiliado principal no valida"));
					}
			}else{
				lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
								"sexoAfiliadoCargo",
								"El campo es obligatorio"));
			}
			if (line.get(ArchivoTrasladoEmpresasCCFConstants.PARENTESCO_PERSONA_A_CARGO) != null) {
				
				String parentescoPersonaCargo = ((String) line.get(ArchivoTrasladoEmpresasCCFConstants.PARENTESCO_PERSONA_A_CARGO))
						.toUpperCase();
						ParentezcoEnumA parentezco = ParentezcoEnumA.obtenerParentezcoEnum(Integer.parseInt(parentescoPersonaCargo));
						if(parentezco != null){
							clasificacionBeneficiario = parentezco.name();
						}
				
						if (parentezco == null){//obtener tipo

							lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
									"parentescoPersonaCargo",
									"Parentesco no valida"));
					
						}
			}else{
				lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
									"parentescoPersonaCargo",
									"El campo es obligatorio"));
			}

            if (line.get(ArchivoTrasladoEmpresasCCFConstants.CODIGO_MUNICIPIO_AFILIADO_A_CARGO) != null) {
				String codigoMunicipioAfiliadoCargo = ((String) line.get(ArchivoTrasladoEmpresasCCFConstants.CODIGO_MUNICIPIO_AFILIADO_A_CARGO));

				BuscarMunicipio buscarMunicipio = new BuscarMunicipio(codigoMunicipioAfiliadoCargo);
				buscarMunicipio.execute();

				Short idCodigo = buscarMunicipio.getResult();
				if(idCodigo == null){
					lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
									"codigoMunicipioAfiliadoCargo",
									"el codigo del municipio no es valido"));
				}
						
			}else{
				lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
									"codigoMunicipioAfiliadoCargo",
									"El campo es obligatorio"));
			}
            if (line.get(ArchivoTrasladoEmpresasCCFConstants.DIRECCION_AFILIADO_A_CARGO) != null) {
				String direccionAfiliado = ((String) line.get(ArchivoTrasladoEmpresasCCFConstants.DIRECCION_AFILIADO_A_CARGO))
						.toUpperCase();
				if(direccionAfiliado.length()<10){
					lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
							"direccionAfiliado",
							"Longitud del direccionAfiliado inferior"));
				}else if(direccionAfiliado.length()>100){
					lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
							"direccionAfiliado",
							"Longitud del direccionAfiliado superior"));
				}
			}
			else{
				lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
							"direccionAfiliado",
							"El campo es obligatorio"));
			}
            if (line.get(ArchivoTrasladoEmpresasCCFConstants.TELEFONO_CELULAR) != null) {
				String telefonoCelular = ((String) line.get(ArchivoTrasladoEmpresasCCFConstants.TELEFONO_CELULAR))
						.toUpperCase();
				if(!telefonoCelular.matches(ExpresionesRegularesConstants.TELEFONO_CELULAR)){
					lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
							"telefonoCelular",
							"Telefono celular no valido"));
				}
			}else{
				lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
							"telefonoCelular",
							"El campo es obligatorio"));
				
			}
			 if (line.get(ArchivoTrasladoEmpresasCCFConstants.TELEFONO_FIJO) != null) {
				String telefonoFijo = ((String) line.get(ArchivoTrasladoEmpresasCCFConstants.TELEFONO_FIJO))
						.toUpperCase();
				if(!telefonoFijo.matches(ExpresionesRegularesConstants.TELEFONO_FIJO)){
					lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
							"telefonoFijo",
							"Telefono fijo no valido"));
				}
			}else{
				lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
							"telefonoFijo",
							"El campo es obligatorio"));

			}
            if (line.get(ArchivoTrasladoEmpresasCCFConstants.CONYUGE_LABORA) != null || (line.get(ArchivoTrasladoEmpresasCCFConstants.PARENTESCO_PERSONA_A_CARGO) != null && line.get(ArchivoTrasladoEmpresasCCFConstants.PARENTESCO_PERSONA_A_CARGO).toString().equals("5"))) {
				String conyugeLabora = ((String) line.get(ArchivoTrasladoEmpresasCCFConstants.CONYUGE_LABORA));
					if(conyugeLabora == null ||conyugeLabora.equals("")){
						lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
											"conyugeLabora",
											"es obligatorio el campo de conyuge labora"));
					}
					else if(!conyugeLabora.equals("1") && !conyugeLabora.equals("0")){
						lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
											"conyugeLabora",
											"el campo de conyuge labora debe ser 1 o 0"));
					}
		 
			}
			if (line.get(ArchivoTrasladoEmpresasCCFConstants.INGRESO_MENSUAL) != null || (line.get(ArchivoTrasladoEmpresasCCFConstants.CONYUGE_LABORA) != null && line.get(ArchivoTrasladoEmpresasCCFConstants.CONYUGE_LABORA).toString().equals("1"))) {
				String ingresoMensual = ((String) line.get(ArchivoTrasladoEmpresasCCFConstants.INGRESO_MENSUAL));
					if(ingresoMensual == null ||ingresoMensual.equals("")){
						lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
											"ingresoMensual",
											"es obligatorio en campo de ingreso mensual"));
					}
				
			}

            if (line.get(ArchivoTrasladoEmpresasCCFConstants.FECHA_INICIO_UNION_AFILIADO) != null || (line.get(ArchivoTrasladoEmpresasCCFConstants.PARENTESCO_PERSONA_A_CARGO) != null && line.get(ArchivoTrasladoEmpresasCCFConstants.PARENTESCO_PERSONA_A_CARGO).toString().equals("5"))) {
				String fechaInicioUnionAfiliado = ((String) line.get(ArchivoTrasladoEmpresasCCFConstants.FECHA_INICIO_UNION_AFILIADO));
				if(fechaInicioUnionAfiliado !=null){
					try{
						Date fechaInicioUnionAfiliadoD =new SimpleDateFormat("yyyyMMdd").parse(fechaInicioUnionAfiliado);  
						System.out.println(fechaInicioUnionAfiliadoD);
						if(Integer.parseInt(fechaInicioUnionAfiliado) < 19200101){
								lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
											"fechaInicioUnionAfiliado",
											"Fecha inicio de unión con el afiliado principal no valida"));
						}
					}catch(Exception e){
						System.out.println("error fechaInicioUnionAfiliado "+e);
						lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
										"fechaInicioUnionAfiliado",
										"Fecha inicio de unión con el afiliado principal no valida"));
					}
				}else{
					lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
										"fechaInicioUnionAfiliado",
										"Fecha inicio de unión con el afiliado principal es obligatorio"));
				}
					
					
				
			}

			
			/*if( tipoIdentificacionBeneficiario != null && tipoIdentificacionAfiliado != null && numeroIdentificacionAfiliado != null && numeroIdentificacionBeneficiario != null && clasificacionBeneficiario != null){
                if(ejecutarMallaValidacionBeneficiario(tipoIdentificacionAfiliado,numeroIdentificacionAfiliado,tipoIdentificacionBeneficiario,numeroIdentificacionBeneficiario,clasificacionBeneficiario) == false){
						lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
						"Documento de identificación del afiliado a cargo",
						"no cumple con alguna de las siguiente validaciones; persona fallecida"));
					}
			}*/

			

		} finally {
			((List<Long>) arguments.getContext().get(ArchivoTrasladoEmpresasCCFConstants.TOTAL_REGISTRO)).add(1L);
			((List<ResultadoHallazgosValidacionArchivoDTO>) arguments.getContext()
					.get(ArchivoTrasladoEmpresasCCFConstants.LISTA_HALLAZGOS)).addAll(lstHallazgos);
			if (!lstHallazgos.isEmpty()) {
				System.out.println("lista no limpia");
				((List<Long>) arguments.getContext().get(ArchivoTrasladoEmpresasCCFConstants.TOTAL_REGISTRO_ERRORES)).add(1L);
			}
		}
    }


	private Boolean verificarEntero(String cadena) {
		try {
			int valorEntero = Integer.parseInt(cadena);
			return false;
		} catch (NumberFormatException e) {
			return true;
		}
	}

	private Boolean verificarLong(String cadena) {
		try {
			Long valorEntero = Long.parseLong(cadena);
			return false;
		} catch (NumberFormatException e) {
			return true;
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
			} else if (tipoIdentificacion.equals(TipoIdentificacionEnum.CEDULA_EXTRANJERIA)) {
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
			}else if (tipoIdentificacion.equals(TipoIdentificacionEnum.PERM_ESP_PERMANENCIA)) {
				// se valida el número de identificación
				validarRegex(arguments, numeroIdentificacionKey, ExpresionesRegularesConstants.PERM_ESP_PERMANENCIA,
						"La longitud debe ser de 15  caracteres alfanuméricos.", numeroIdentificacionKey);
				return;
			}
			else if (tipoIdentificacion.equals(TipoIdentificacionEnum.PERM_PROT_TEMPORAL)) {
				// se valida el número de identificación
				validarRegex(arguments, numeroIdentificacionKey, ExpresionesRegularesConstants.PERM_PROT_TEMPORAL,
						"La longitud no puede ser mayor de 16 caracteres alfanuméricos.", numeroIdentificacionKey);
				return;
			}else {
				lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), numeroIdentificacionKey,
						"Tipo de identificación invalido para verificar respecto al número de identificación"));
			}
		} else {
			lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), numeroIdentificacionKey,
					"Tipo de identificación invalido para verificar respecto al número de identificación"));

		}
	}
	private void validarRegex(LineArgumentDTO arguments, String campoKey, String regex, String mensaje,
			String campoMSG) {
		try {
			String campoValue = null;
			if(campoKey.equals(ArchivoTrasladoEmpresasCCFConstants.NUMERO_DOCUMENTO_AFILIADO_A_CARGO)
			   || campoKey.equals(ArchivoTrasladoEmpresasCCFConstants.NUMERO_DOCUMENTO_AFILIADO_CARGO)){
				
				campoValue = arguments.getLineValues().get(campoKey).toString();
			
			}
			
		} catch (Exception e) {
			lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), campoMSG,
					" Valor perteneciente " + campoMSG + " invalido"));
		}
	}

    private Boolean validarFechaNacimiento(String fechaNacimiento){
		System.out.println("fecha nacimiento: " + fechaNacimiento);
        Date fechaN = new Date(fechaNacimiento);
        Date fechaActual = new Date();
        if(fechaN.compareTo(fechaActual)<0){
            return false;
        }else{
            return true;
        }

    }

    private ResultadoHallazgosValidacionArchivoDTO crearHallazgo(Long lineNumber, String campo, String errorMessage) {
            String error = Interpolator.interpolate(ArchivoTrasladoEmpresasCCFConstants.MENSAJE_ERROR_CAMPO, campo, errorMessage);
            ResultadoHallazgosValidacionArchivoDTO hallazgo = new ResultadoHallazgosValidacionArchivoDTO();
            hallazgo.setNumeroLinea(lineNumber);
            hallazgo.setNombreCampo(campo);
            hallazgo.setError(error);
             return hallazgo;
    }
	
	private Boolean ejecutarMallaValidacion(
            TipoIdentificacionEnum tipoIdAfiliado, String numIdAfiliado) {
        String firmaMetodo = "NovedadesBussines.ejecutarMallaValidacionBeneficiario(BeneficiarioDTO, TipoIdentificacionEnum, String)";
        TipoTransaccionEnum bloque = TipoTransaccionEnum.AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION;

		logger.info(firmaMetodo + ": " + tipoIdAfiliado+ " : "+ numIdAfiliado);

        if (bloque == null) {
            return Boolean.FALSE;
        };

        //

        // se diligencia el mapa de datos
        Map<String, String> datosValidacion = new HashMap<>();
        datosValidacion.put("tipoTransaccion", bloque.name());
        datosValidacion.put("tipoIdentificacion", tipoIdAfiliado.name());
        datosValidacion.put("numeroIdentificacion", numIdAfiliado);
        datosValidacion.put("tipoIdentificacionAfiliado", tipoIdAfiliado.name());
        datosValidacion.put("numeroIdentificacionAfiliado", numIdAfiliado);
		datosValidacion.put("tipoAfiliado", "TRABAJADOR_DEPENDIENTE");

        ValidarReglasNegocio validador = new ValidarReglasNegocio("121-104-5", bloque.getProceso(),
                "TRABAJADOR_DEPENDIENTE", datosValidacion);
        validador.execute();
        List<ValidacionDTO> resultadoValidacion = validador.getResult();

        for (ValidacionDTO validacion : resultadoValidacion) {
            if (validacion.getTipoExcepcion() != null
                    && TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2.equals(validacion.getTipoExcepcion())) {
				logger.info("Validaciones no existosas");
                return Boolean.FALSE;
            }
        }
        return Boolean.TRUE;
    }

	private boolean ejecutarMallaValidacionBeneficiario(
		TipoIdentificacionEnum tipoIdAfiliado,String numIdAfiliado,TipoIdentificacionEnum tipoIdBeneficiario,String numIdBeneficiario,String clasificacionBeneficiario){
		String firmaMetodo = "NovedadesBussines.ejecutarMallaValidacionBeneficiario(BeneficiarioDTO, TipoIdentificacionEnum, String)";
        TipoTransaccionEnum bloque = TipoTransaccionEnum.AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION;

		logger.info(firmaMetodo + ": " + tipoIdAfiliado+ " : "+ numIdAfiliado);

        if (bloque == null) {
            return Boolean.FALSE;
        };

        //

        // se diligencia el mapa de datos
        Map<String, String> datosValidacion = new HashMap<>();
        datosValidacion.put("tipoTransaccion", bloque.name());
        datosValidacion.put("tipoIdentificacion", tipoIdAfiliado.name());
        datosValidacion.put("numeroIdentificacion", numIdAfiliado);
        datosValidacion.put("tipoIdentificacionAfiliado", tipoIdAfiliado.name());
        datosValidacion.put("numeroIdentificacionAfiliado", numIdAfiliado);
		datosValidacion.put("tipoAfiliado", clasificacionBeneficiario);
		datosValidacion.put("tipoIdentificacionBeneficiario",tipoIdBeneficiario.name());
		datosValidacion.put("numeroIdentificacionBeneficiario",numIdBeneficiario);

        ValidarReglasNegocio validador = new ValidarReglasNegocio("TRASLADOS", bloque.getProceso(),
                clasificacionBeneficiario, datosValidacion);
        validador.execute();
        List<ValidacionDTO> resultadoValidacion = validador.getResult();

        for (ValidacionDTO validacion : resultadoValidacion) {
            if (validacion.getTipoExcepcion() != null
                    && TipoExcepcionFuncionalEnum.EXCEPCION_FUNCIONAL_TIPO_2.equals(validacion.getTipoExcepcion())) {
				logger.info("Validaciones no existosas");
                return Boolean.FALSE;
            }
        }
        return Boolean.TRUE;
		}
    
}