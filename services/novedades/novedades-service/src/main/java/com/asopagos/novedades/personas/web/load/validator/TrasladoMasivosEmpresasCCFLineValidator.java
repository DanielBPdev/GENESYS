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
import com.asopagos.util.CalendarUtils;
import com.asopagos.util.GetValueUtil;
import com.asopagos.util.Interpolator;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.validator.LineValidator;
import com.asopagos.dto.modelo.PersonaModeloDTO;
import com.asopagos.empleadores.clients.ConsultarEmpleadorTipoNumero;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.constants.ExpresionesRegularesConstants;
import java.text.SimpleDateFormat;
import com.asopagos.afiliaciones.clients.BuscarMunicipio;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.validaciones.clients.ValidarReglasNegocio;
import com.asopagos.enumeraciones.core.TipoExcepcionFuncionalEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import java.util.HashMap;
import com.asopagos.empleadores.clients.BuscarEmpleador;
import com.asopagos.entidades.ccf.personas.Empleador;
import com.asopagos.enumeraciones.personas.EstadoEmpleadorEnum;


public class TrasladoMasivosEmpresasCCFLineValidator extends LineValidator{
    private List<ResultadoHallazgosValidacionArchivoDTO> lstHallazgos;
	private static final ILogger logger = LogManager.getLogger(TrasladoMasivosEmpresasCCFLineValidator.class);

	/**
	 * (non-Javadoc)
	 * 
	 * @see co.com.heinsohn.lion.fileprocessing.fileloader.validator.LineValidator#validate(co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO)
	 */
	@Override
	public void validate(LineArgumentDTO arguments) throws FileProcessingException {
			

        System.out.println("Nico estuvo aqui >:) LINE");
		String docEmpleador= (String) arguments.getContext().get("docEmpleador");
		String tipoDocumneto= (String) arguments.getContext().get("tipoDocumneto");
		TipoIdentificacionEnum tipoIdentificacionEmpleador = null;
		TipoIdentificacionEnum tipoIdentificacionAfiliado = null;


		lstHallazgos = new ArrayList<>();
                TipoIdentificacionEnum tipoIdentEnum = null;
		Map<String, Object> line = arguments.getLineValues();
		List<ResultadoHallazgosValidacionArchivoDTO> listaHallazgosReal = (List<ResultadoHallazgosValidacionArchivoDTO>) arguments.getContext()
					.get(ArchivoTrasladoEmpresasCCFConstants.LISTA_HALLAZGOS);
		  try {
			if (line.get(ArchivoTrasladoEmpresasCCFConstants.TIPO_DOCUMENTO_EMPRESA) != null) {

				String tipoDocumentoEmpleador = ((String) line.get(ArchivoTrasladoEmpresasCCFConstants.TIPO_DOCUMENTO_EMPRESA));
						// TipoIdentificacionEmpleadorEnum tipoIdentEnum = null;

				try{
					TipoIdentificacionEmpleadorEnumA tipo = TipoIdentificacionEmpleadorEnumA.obtenerTipoIdentificacionEmpleadorEnum(Integer.parseInt(tipoDocumentoEmpleador));
					tipoIdentificacionEmpleador = TipoIdentificacionEnum.valueOf(tipo.name());
				}catch(Exception e){
					System.out.println("error "+e);
					lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
										"Tipo de documento empleador",
										"Tipo de identificación del afiliado no valida"));
				}
			} else {
				lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
										"Tipo de documento empleador",
										"Tipo de identificación es un campo obligatorio"));
			}

		

				
		// Validación para el número de documento
			if (line.get(ArchivoTrasladoEmpresasCCFConstants.NUMERO_DOCUMENTO_EMPRESA) != null ) {

				String numeroDocumentoEmpleador = ((String) line.get(ArchivoTrasladoEmpresasCCFConstants.NUMERO_DOCUMENTO_EMPRESA));
				verificarNumeroDocumento(tipoIdentificacionEmpleador, arguments, "NUMERO_DOCUMENTO_EMPRESA");
				logger.info("Tipo Identificacion Enum: " + tipoIdentificacionEmpleador.name());
				logger.info("Tipo Documento: " + tipoDocumneto);
				logger.info("Numero Documento Empleador: " + numeroDocumentoEmpleador);
				logger.info("Doc Empleador: " + docEmpleador);

				if(!tipoIdentificacionEmpleador.name().equals(tipoDocumneto) || !numeroDocumentoEmpleador.equals(docEmpleador)) {
					lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
										"Tipo y número de documento de la empresa",
										""));
				}

				BuscarEmpleador buscarEmpleador = new BuscarEmpleador(Boolean.FALSE,numeroDocumentoEmpleador,tipoIdentificacionEmpleador,null);
				buscarEmpleador.execute();
				List<Empleador> empleadoresTraslados = buscarEmpleador.getResult();
				
				if(empleadoresTraslados == null || empleadoresTraslados.isEmpty()){
					lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
										"Número de documento de la empresa",
										"Empresa no existe en BD"));
				}
				if(empleadoresTraslados != null && !empleadoresTraslados.isEmpty()){
					Empleador empresa = empleadoresTraslados.get(0);
					if(empresa.getEstadoEmpleador() != EstadoEmpleadorEnum.ACTIVO){
						lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
										"Número de documento de la empresa",
										"Empresa no existe en BD"));
					}
					if(empresa.getTrasladoCajasCompensacion() == null || empresa.getTrasladoCajasCompensacion() == false){
							lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
							"Número de documento de la empresa",
							"La empresa no presenta marca de traslado entre CCF"));
					}
				}

			}else{
				lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
							"Número de documento de la empresa",
							"El campo es obligatorio"));
			}
			
			if (line.get(ArchivoTrasladoEmpresasCCFConstants.TIPO_DOCUMENTO_AFILIADO) != null) {
				String tipoDocumentoAfiliado = ((String) line.get(ArchivoTrasladoEmpresasCCFConstants.TIPO_DOCUMENTO_AFILIADO))
						.toUpperCase();

				try{
					TipoIdentificacionAfiliadoEnumA tipoA = TipoIdentificacionAfiliadoEnumA.obtenerTipoIdentificacionEnum(Integer.parseInt(tipoDocumentoAfiliado));
					tipoIdentificacionAfiliado = TipoIdentificacionEnum.valueOf(tipoA.name());
					String tipoDocumentoAfiliadoCargo = ((String) line.get(ArchivoTrasladoEmpresasCCFConstants.TIPO_DOCUMENTO_AFILIADO))
							.toUpperCase();
					
					String numDocumentoAfiliado = ((String) line.get(ArchivoTrasladoEmpresasCCFConstants.NUMERO_DOCUMENTO_AFILIADO))
							.toUpperCase();
					String numDocumentoEmpleador = ((String) line.get(ArchivoTrasladoEmpresasCCFConstants.NUMERO_DOCUMENTO_EMPRESA)).toUpperCase();
					
					/*if(ejecutarMallaValidacionAfiliado(tipoIdentificacionAfiliado,numDocumentoAfiliado,tipoIdentificacionEmpleador,numDocumentoEmpleador) == false){
							lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
							"tipo de documento persona",
							"no cumple con alguna de las siguiente validaciones; persona fallecida, expulsión por mora o esta persona ya tiene una solicitud con este empleador"));
						}*/
				
				}catch(Exception e){
					System.out.println("error "+e);
					e.printStackTrace();
					lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
										"Tipo de documento afiliado",
										"Tipo de identificación del afiliado no valida"));
				}
                


			} else{
				lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
										"Tipo de documento afiliado",
										"El campo es obligatorio"));
				}

			
			if (line.get(ArchivoTrasladoEmpresasCCFConstants.NUMERO_DOCUMENTO_AFILIADO) != null) {
					
				verificarNumeroDocumento(tipoIdentificacionAfiliado,arguments,"NUMERO_DOCUMENTO_AFILIADO");
			}else{
				lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
										"numeroAfiliado",
										"El campo es obligatorio"));
			}

			if (line.get(ArchivoTrasladoEmpresasCCFConstants.PRIMER_NOMBRE_AFILIADO) != null) {
				String primerNombreAfiliado = ((String) line.get(ArchivoTrasladoEmpresasCCFConstants.PRIMER_NOMBRE_AFILIADO))
						.toUpperCase();

						if(primerNombreAfiliado.length()>30){
								lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
										"primerNombreAfiliado",
										"Longitud del primerNombreAfiliado superior"));
						}

			} else {
				lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
										"primerNombreAfiliado",
										"El campo es obligatorio"));
			}
			if (line.get(ArchivoTrasladoEmpresasCCFConstants.SEGUNDO_NOMBRE_AFILIADO) != null) {
				String segundoNombreAfiliado = ((String) line.get(ArchivoTrasladoEmpresasCCFConstants.SEGUNDO_NOMBRE_AFILIADO))
						.toUpperCase();
						if(segundoNombreAfiliado != null){
							if(segundoNombreAfiliado.length()>30){
								lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
										"segundoNombreAfiliado",
										"Longitud del segundoNombreAfiliado superior"));
							}
						} 

			}
			if (line.get(ArchivoTrasladoEmpresasCCFConstants.PRIMER_APELLIDO_AFILIADO) != null) {
				String primerApellidoAfiliado = ((String) line.get(ArchivoTrasladoEmpresasCCFConstants.PRIMER_APELLIDO_AFILIADO))
						.toUpperCase();
				
						if(primerApellidoAfiliado.length()>30){
								lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
										"primerApellidoAfiliado",
										"Longitud del primerNombreAfiliado superior"));
						}

			}else{
				lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
										"primerApellidoAfiliado",
										"El campo es obligario"));
			}
			if (line.get(ArchivoTrasladoEmpresasCCFConstants.SEGUNDO_APELLIDO_AFILIADO) != null) {
				String segundoApellidofiliado = ((String) line.get(ArchivoTrasladoEmpresasCCFConstants.SEGUNDO_APELLIDO_AFILIADO))
						.toUpperCase();
						if(segundoApellidofiliado != null){
							if(segundoApellidofiliado.length()>30){
								lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
										"segundoApellidofiliado",
										"Longitud del segundoApellidofiliado superior"));
							}
						} 

			}
			if (line.get(ArchivoTrasladoEmpresasCCFConstants.FECHA_NACIMIENTO_AFILIADO) != null) {
				String fechaNacimiento = ((String) line.get(ArchivoTrasladoEmpresasCCFConstants.FECHA_NACIMIENTO_AFILIADO))
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
			if (line.get(ArchivoTrasladoEmpresasCCFConstants.SEXO_AFILIADO) != null) {
				String sexoAfiliado = ((String) line.get(ArchivoTrasladoEmpresasCCFConstants.SEXO_AFILIADO))
						.toUpperCase();
					try{
						GeneroEnumA genero = GeneroEnumA.obtenerGeneroEnum(Integer.parseInt(sexoAfiliado));
							if (genero==null){//obtener tipo GeneroEnum
								lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
										"generoAfiliado",
										"Género del afiliado principal no valida")); 
				             }}
							 catch (Exception e){
								lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
										"generoAfiliado",
										"Género no valido")); 
							 }
			}else{
				lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
										"generoAfiliado",
										"El campo es obligatorio")); 
			}

			if (line.get(ArchivoTrasladoEmpresasCCFConstants.ORIENTACION_SEXUAL_AFILIADO) != null) {
				String orientacionSexualAfiliado = ((String) line.get(ArchivoTrasladoEmpresasCCFConstants.ORIENTACION_SEXUAL_AFILIADO))
						.toUpperCase();
						try{
						OrientacionSexualEnumA orientacion = OrientacionSexualEnumA.obtenerOrientacionSexualEnum(Integer.parseInt(orientacionSexualAfiliado));
								if (orientacion == null){//obtener tipo
									lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
											"orientacionSexualAfiliado",
											"Orientación sexual del afiliado principal no valida"));
								}}
						catch (Exception e){
							lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
										"orientacionSexualAfiliado",
										"orientacionSexualAfiliado no valido")); 
						}		
			}

            if (line.get(ArchivoTrasladoEmpresasCCFConstants.NIVEL_ESCOLARIDAD_AFIIADO) != null) {
				String nivelEscolaridadAfiliado = ((String) line.get(ArchivoTrasladoEmpresasCCFConstants.NIVEL_ESCOLARIDAD_AFIIADO))
						.toUpperCase();
						try{
						NivelEscolaridadEnumA nivel = NivelEscolaridadEnumA.obtenerNivelEscolaridadEnum(Integer.parseInt(nivelEscolaridadAfiliado));
								if (nivel == null){//obtener tipo
									lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
											"nivelEscolaridadAfiliado",
											"Nivel de escolaridad del afiliado principal no valida"));
										
						}}catch (Exception e){
							lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
										"nivelEscolaridadAfiliado",
										"nivelEscolaridadAfiliado no valido")); 
						}
			}
            if (line.get(ArchivoTrasladoEmpresasCCFConstants.CODIGO_OCUPACION_AFILIADO) != null) {
				String codigOcupacionAfiliado = ((String) line.get(ArchivoTrasladoEmpresasCCFConstants.CODIGO_OCUPACION_AFILIADO))
						.toUpperCase();
						if(codigOcupacionAfiliado.length()>4){
							lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
									"codigOcupacionAfiliado",
									"Longitud del codigOcupacionAfiliado superior"));
						}
			}
            if (line.get(ArchivoTrasladoEmpresasCCFConstants.FACTOR_VULNERABILIDAD_AFILIADO) != null) {
				String factorVulnerabilidadAfiliado = ((String) line.get(ArchivoTrasladoEmpresasCCFConstants.FACTOR_VULNERABILIDAD_AFILIADO))
						.toUpperCase();
						FactorVulnerabilidadEnumA factor = FactorVulnerabilidadEnumA.obtenerFactorVulnerabilidadEnum(Integer.parseInt(factorVulnerabilidadAfiliado));
						if (factorVulnerabilidadAfiliado.length()>2){
							lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
									"factorVulnerabilidadAfiliado",
									"Longitud del codigOcupacionAfiliado superior"));
	
						}
			}
            if (line.get(ArchivoTrasladoEmpresasCCFConstants.ESTADO_CIVIL_AFILIADO) != null) {
				String estadoCivilAfiliado = ((String) line.get(ArchivoTrasladoEmpresasCCFConstants.ESTADO_CIVIL_AFILIADO))
						.toUpperCase();
						EstadoCivilEnumA estadoCivil = EstadoCivilEnumA.obtenerEstadoCivilEnum(Integer.parseInt(estadoCivilAfiliado));
						if (estadoCivil == null){//obtener tipo
							lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
									"estadoCivilAfiliado",
									"Estado Civil del afiliado principal no valida"));
						}
			}else{
				lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
									"estadoCivilAfiliado",
									"El campo es obligatorio"));
			}
			if (line.get(ArchivoTrasladoEmpresasCCFConstants.PERTENENCIA_ETNICA_AFILIADO) != null) {
				String pertenenciaEtnicaAfiliado = ((String) line.get(ArchivoTrasladoEmpresasCCFConstants.PERTENENCIA_ETNICA_AFILIADO))
						.toUpperCase();
						PerteneciaEtnicaEnumA pertenencia = PerteneciaEtnicaEnumA.obtenerPerteneciaEtnicaEnum(Integer.parseInt(pertenenciaEtnicaAfiliado));
						if (pertenencia == null){//obtener tipo
							lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
									"pertenenciaEtnicaAfiliado",
									"Pertenencia Étnica del afiliado principal no valida"));
						}

					
			}

            if (line.get(ArchivoTrasladoEmpresasCCFConstants.PAIS_RESIDENCIA_AFILIADO) != null) {
				String paisResidenciaAfiliado = ((String) line.get(ArchivoTrasladoEmpresasCCFConstants.PAIS_RESIDENCIA_AFILIADO))
						.toUpperCase();

			}

            if (line.get(ArchivoTrasladoEmpresasCCFConstants.CODIGO_MUNICIPIO_AFILIADO) != null) {
				String codigoMunicipioAfiliado = ((String) line.get(ArchivoTrasladoEmpresasCCFConstants.CODIGO_MUNICIPIO_AFILIADO));
				logger.info("Error de consola"+ codigoMunicipioAfiliado);
				BuscarMunicipio buscarMunicipio = new BuscarMunicipio(codigoMunicipioAfiliado);
				buscarMunicipio.execute();

				Short idCodigo = buscarMunicipio.getResult();
				if(idCodigo == null){
					lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
									"codigoMunicipioAfiliado",
									"el código del municipio no es valido"));
				}
	
			}else{
				lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
									"codigoMunicipioAfiliado",
									"El campo es obligatorio"));

			}

            if (line.get(ArchivoTrasladoEmpresasCCFConstants.RESIDE_RURAL) != null) {
				String resideRural = ((String) line.get(ArchivoTrasladoEmpresasCCFConstants.RESIDE_RURAL));
				if(!resideRural.equals("1") && !resideRural.equals("2")){
					lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
									"resideRural",
									"Residete en sector rural no valida"));
				}
			}else{
				lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
									"resideRural",
									"El campo es obligatorio"));
			}

            if (line.get(ArchivoTrasladoEmpresasCCFConstants.CODIGO_MUNICIPIO_LABOR_AFILIADO) != null) {
				String codigoMunicipioLaborAfiliado = ((String) line.get(ArchivoTrasladoEmpresasCCFConstants.CODIGO_MUNICIPIO_LABOR_AFILIADO));
				logger.info("error1"+codigoMunicipioLaborAfiliado);
				BuscarMunicipio buscarMunicipio = new BuscarMunicipio(codigoMunicipioLaborAfiliado);
				buscarMunicipio.execute();

				Short idCodigo = buscarMunicipio.getResult();
				if(idCodigo == null){
					lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
									"codigoMunicipioLaborAfiliado",
									"el codigo del municipio no es valido"));
				}

			}

            if (line.get(ArchivoTrasladoEmpresasCCFConstants.SALARIO_MENSUAL) != null) {
				String salarioMensual = ((String) line.get(ArchivoTrasladoEmpresasCCFConstants.SALARIO_MENSUAL))
						.toUpperCase();
				if(!salarioMensual.matches(ExpresionesRegularesConstants.REGEX_NUMERO_DOC_BENEFICIARIO)){
					lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
									"salarioMensual",
									"Valor salario mensual no valida"));
				}

			}else{
				lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
									"salarioMensual",
									"El campo es obligatorio"));

			}
            if (line.get(ArchivoTrasladoEmpresasCCFConstants.TIPO_SOLICITANTE) != null) {
				String tipoSolicitante = ((String) line.get(ArchivoTrasladoEmpresasCCFConstants.TIPO_SOLICITANTE)).toUpperCase();
				TipoAfiliadoEnumA tiposolicitanteA = TipoAfiliadoEnumA.obtenerTipoAfiliadoEnum(Integer.parseInt(tipoSolicitante));
				if (tiposolicitanteA == null){//obtener tipo
							lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
									"tipoSolicitante",
									"Tipo de solicitante no valida"));
						}
			}else{
				lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
									"tipoSolicitante",
									"El campo de obligatorio"));
			}
            if (line.get(ArchivoTrasladoEmpresasCCFConstants.DIRECCION_AFILIADO) != null) {
				String direccion = ((String) line.get(ArchivoTrasladoEmpresasCCFConstants.DIRECCION_AFILIADO))
						.toUpperCase();
				if(direccion.length()<10){
					lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
							"direccionAfiliado",
							"Longitud del direccion inferior"));
				}else if(direccion.length()>100){
					lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
							"direccionAfiliado",
							"Longitud del direccion superior"));
				}
			}else{
				lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
							"direccionAfiliado",
							"El campo es obligatorio"));
			}
            if (line.get(ArchivoTrasladoEmpresasCCFConstants.TELEFONO_CELULAR_AFILIADO) != null) {
				String telefonoCelular = ((String) line.get(ArchivoTrasladoEmpresasCCFConstants.TELEFONO_CELULAR_AFILIADO))
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
            if (line.get(ArchivoTrasladoEmpresasCCFConstants.TELEFONO_FIJO_AFILIADO) != null) {
				String telefonoFijo = ((String) line.get(ArchivoTrasladoEmpresasCCFConstants.TELEFONO_FIJO_AFILIADO))
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
            if (line.get(ArchivoTrasladoEmpresasCCFConstants.CORREO_ELECTRONICO) != null) {
				String correoElectronico = ((String) line.get(ArchivoTrasladoEmpresasCCFConstants.CORREO_ELECTRONICO)).toLowerCase();
				if(!correoElectronico.matches(ExpresionesRegularesConstants.EXPRESION_REGULAR_VALIDA_EMAIL)){
					lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
							"correoElectronico",
							"Correo electronico no valido"));

				}
			}	
            if (line.get(ArchivoTrasladoEmpresasCCFConstants.CATEGORIA) != null) {
				String telefonoAfiliado = ((String) line.get(ArchivoTrasladoEmpresasCCFConstants.CATEGORIA))
						.toUpperCase();

			}
			if (line.get(ArchivoTrasladoEmpresasCCFConstants.BENEFICIARIO_CUOTA) != null) {
				String BeneficiarioCuota = ((String) line.get(ArchivoTrasladoEmpresasCCFConstants.BENEFICIARIO_CUOTA))
						.toUpperCase();
				if(!BeneficiarioCuota.matches(ExpresionesRegularesConstants.REGEX_UNO_CERO)){
					lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
									"BeneficiarioCuota",
									"Beneficiario de cuota monetaria no valida"));
				}
			}
			

			
		}finally {


			((List<Long>) arguments.getContext().get(ArchivoTrasladoEmpresasCCFConstants.TOTAL_REGISTRO)).add(1L);
			((List<ResultadoHallazgosValidacionArchivoDTO>) arguments.getContext()
					.get(ArchivoTrasladoEmpresasCCFConstants.LISTA_HALLAZGOS)).addAll(lstHallazgos);
			if (!lstHallazgos.isEmpty()) {
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

    private Boolean verificarTipoDocumento(String tipoDocumento, String tipo) {

		if(ArchivoTrasladoEmpresasCCFConstants.TIPO_DOCUMENTO_EMPRESA.equals(tipo)){
			try {
				TipoIdentificacionEmpleadorEnumA tipoIdentificacionEmp = TipoIdentificacionEmpleadorEnumA
						.obtenerTipoIdentificacionEmpleadorEnum(Integer.parseInt(tipoDocumento));
				if(tipoIdentificacionEmp != null){
					return false;
				}else{
					return true;
				}
			} catch (Exception e) {
				System.out.println("Return true tipoAportante");
				return Boolean.TRUE;
			}
		}else if(ArchivoTrasladoEmpresasCCFConstants.TIPO_DOCUMENTO_AFILIADO.equals(tipo)){
			try {
				TipoIdentificacionAfiliadoEnumA tipoIdentificacion = TipoIdentificacionAfiliadoEnumA
						.obtenerTipoIdentificacionEnum(Integer.parseInt(tipoDocumento));
				if(tipoIdentificacion != null){
					return false;
				}else{
					return true;
				}
			} catch (Exception e) {
				System.out.println("Return true tipoAportante");
				return Boolean.TRUE;
			}

		}

		return false;
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
			}
			else if (tipoIdentificacion.equals(TipoIdentificacionEnum.NIT)) {
				// se valida el número de identificación
				validarRegex(arguments, numeroIdentificacionKey, ExpresionesRegularesConstants.NIT,
						"La longitud no puede ser mayor de 10 dígitos.", numeroIdentificacionKey);
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

    private ResultadoHallazgosValidacionArchivoDTO crearHallazgo(Long lineNumber, String campo, String errorMessage) {
            String error = Interpolator.interpolate(ArchivoTrasladoEmpresasCCFConstants.MENSAJE_ERROR_CAMPO, campo, errorMessage);
            ResultadoHallazgosValidacionArchivoDTO hallazgo = new ResultadoHallazgosValidacionArchivoDTO();
            hallazgo.setNumeroLinea(lineNumber);
            hallazgo.setNombreCampo(campo);
            hallazgo.setError(error);
             return hallazgo;
    }
	private void validarRegex(LineArgumentDTO arguments, String campoKey, String regex, String mensaje,
			String campoMSG) {
		try {
			String campoValue = null;
			if(campoKey.equals(ArchivoTrasladoEmpresasCCFConstants.NUMERO_DOCUMENTO_EMPRESA)
			   || campoKey.equals(ArchivoTrasladoEmpresasCCFConstants.NUMERO_DOCUMENTO_AFILIADO)){
				
				campoValue = arguments.getLineValues().get(campoKey).toString();
			
			}
			
		} catch (Exception e) {
			lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), campoMSG,
					" Valor perteneciente " + campoMSG + " invalido"));
		}
	}

	
	private Boolean ejecutarMallaValidacionAfiliado(
		TipoIdentificacionEnum tipoIdAfiliado, String numIdAfiliado, TipoIdentificacionEnum tipoIdEmpleador,String numIdEmpleador) {
        String firmaMetodo = "NovedadesBussines.ejecutarMallaValidacionBeneficiario(BeneficiarioDTO, TipoIdentificacionEnum, String)";
        TipoTransaccionEnum bloque = TipoTransaccionEnum.AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION;

		logger.info(firmaMetodo + ": " + tipoIdAfiliado+ " : "+ numIdAfiliado);

        if (bloque == null) {
            return Boolean.FALSE;
        };


        // se diligencia el mapa de datos
        Map<String, String> datosValidacion = new HashMap<>();
        datosValidacion.put("tipoTransaccion", bloque.name());
        datosValidacion.put("tipoIdentificacion", tipoIdAfiliado.name());
        datosValidacion.put("numeroIdentificacion", numIdAfiliado);
        datosValidacion.put("tipoIdentificacionAfiliado", tipoIdAfiliado.name());
        datosValidacion.put("numeroIdentificacionAfiliado", numIdAfiliado);
		datosValidacion.put("tipoIdentificacionEmpleador",tipoIdEmpleador.name());
		datosValidacion.put("numeroIdentificacionEmpleador",numIdEmpleador);

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

	
}