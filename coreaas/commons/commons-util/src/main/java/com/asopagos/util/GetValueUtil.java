package com.asopagos.util;

import java.util.List;
import com.asopagos.entidades.transversal.core.CodigoCIIU;
import com.asopagos.entidades.transversal.core.Departamento;
import com.asopagos.entidades.transversal.core.Municipio;
import com.asopagos.entidades.transversal.personas.AFP;
import com.asopagos.entidades.transversal.personas.GradoAcademico;
import com.asopagos.enumeraciones.afiliaciones.MotivoDesafiliacionAfiliadoEnum;
import com.asopagos.enumeraciones.afiliaciones.MotivoDesafiliacionBeneficiarioEnum;
import com.asopagos.enumeraciones.core.TipoContratoEnum;
import com.asopagos.enumeraciones.core.TipoSalarioEnum;
import com.asopagos.enumeraciones.novedades.TipoInconsistenciaANIEnum;
import com.asopagos.enumeraciones.personas.ClaseTrabajadorEnum;
import com.asopagos.enumeraciones.personas.EstadoCivilEnum;
import com.asopagos.enumeraciones.personas.GeneroEnum;
import com.asopagos.enumeraciones.personas.NivelEducativoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.rest.exception.ParametroInvalidoExcepcion;

/**
 * <b>Descripcion:</b> Clase que crea una anumeración a partir de una cadana
 * dada<br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author <a href="mailto:jocampo@heinsohn.com.co"> jocampo</a>
 */

public class GetValueUtil {

	/**
	 * Método que recibe una string e intenga crear una instancia de
	 * TipoIdentificacionEnum
	 * 
	 * @param value,
	 *            String a convertir a enumeración
	 * @return retorna el tipo de identificacion enum
	 */
	public static TipoIdentificacionEnum getTipoIdentificacion(String value) {
		try {
			TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.valueOf(value);
			return tipoIdentificacion;
		} catch (Exception e) {
			StringBuilder mensajeError = new StringBuilder();
			mensajeError.append("Posibles valores: ");
			for (TipoIdentificacionEnum tie : TipoIdentificacionEnum.values()) {
				if (tie.getIdentificionTrabajador() == true) {
					mensajeError.append(tie.name());
					mensajeError.append(",");
				}
			}
			throw new ParametroInvalidoExcepcion(mensajeError.toString());
		}
	}
        
        /**
	 * Método que recibe una string e intenga crear una instancia de
	 * TipoIdentificacionEnum por codigoPila
	 * 
	 * @param value,
	 *            String a convertir a enumeración
	 * @return retorna el tipo de identificacion enum
	 */
	public static TipoIdentificacionEnum getTipoIdentificacionByPila(String value) {
		try {
			TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.obtenerTiposIdentificacionPILAEnum(value);
			return tipoIdentificacion;
		} catch (Exception e) {
			StringBuilder mensajeError = new StringBuilder();
			mensajeError.append("Posibles valores: ");
			for (TipoIdentificacionEnum tie : TipoIdentificacionEnum.values()) {
				if (tie.getIdentificionTrabajador() == true) {
					mensajeError.append(tie.name());
					mensajeError.append(",");
				}
			}
			throw new ParametroInvalidoExcepcion(mensajeError.toString());
		}
	}

	/**
	 * Método que recibe una string e intenga crear una instancia de GeneroEnum
	 * 
	 * @param value
	 * @return
	 */
	public static GeneroEnum getGenero(String value) {
		try {
			GeneroEnum genero = GeneroEnum.valueOf(value);
			return genero;
		} catch (Exception e) {
			StringBuilder mensajeError = new StringBuilder();
			mensajeError.append("Posibles valores: ");
			for (GeneroEnum g : GeneroEnum.values()) {
				mensajeError.append(g.name());
				mensajeError.append(",");
			}
			throw new ParametroInvalidoExcepcion(mensajeError.toString());
		}
	}

	/**
	 * Método que recibe una string e intenta crear una instancia de EstadoCivil
	 * 
	 * @param value
	 * @return
	 */
	public static EstadoCivilEnum getEstadoCivil(String value) {
		try {
			EstadoCivilEnum estadoCivil = EstadoCivilEnum.valueOf(value);
			return estadoCivil;
		} catch (Exception e) {
			StringBuilder mensajeError = new StringBuilder();
			mensajeError.append("Posibles valores: ");
			for (EstadoCivilEnum g : EstadoCivilEnum.values()) {
				mensajeError.append(g.name());
				mensajeError.append(",");
			}
			throw new ParametroInvalidoExcepcion(mensajeError.toString());
		}
	}

	/**
	 * Método que recibe una string e intenta crear una instancia de
	 * NivelEducativoEnum
	 * 
	 * @param value
	 * @return
	 */
	public static NivelEducativoEnum getNivelEducativoEnum(String value) {
		try {
			NivelEducativoEnum nivelEducativo = NivelEducativoEnum.valueOf(value);
			return nivelEducativo;
		} catch (Exception e) {
			StringBuilder mensajeError = new StringBuilder();
			mensajeError.append("Posibles valores: ");
			for (NivelEducativoEnum g : NivelEducativoEnum.values()) {
				mensajeError.append(g.name());
				mensajeError.append(",");
			}
			throw new ParametroInvalidoExcepcion(mensajeError.toString());
		}
	}

	/**
	 * Método que recibe una string e intenga crear una instancia de
	 * ClaseTrabajadorEnum
	 * 
	 * @param value
	 * @return retorna la clase trabajador Enum
	 */
	public static ClaseTrabajadorEnum getClaseTrabajador(String value) {
		try {
			ClaseTrabajadorEnum claseTrabajador = ClaseTrabajadorEnum.valueOf(value);
			return claseTrabajador;
		} catch (Exception e) {
			StringBuilder mensajeError = new StringBuilder();
			mensajeError.append("Posibles valores: ");
			for (ClaseTrabajadorEnum g : ClaseTrabajadorEnum.values()) {
				mensajeError.append(g.name());
				mensajeError.append(",");
			}
			throw new ParametroInvalidoExcepcion(mensajeError.toString());
		}
	}

	/**
	 * Método que recibe el código del departamente y retorna el objeto
	 * correspondiente.
	 * 
	 * @param listaMunicipios
	 * @param codigoMunicipio
	 * @return el objeto municipío que coincide, de no encontrarse retorna null.
	 */
	public static Municipio getMunicipio(List<Municipio> listaMunicipios, Long codigoMunicipio) {
		String codigo;
		if (codigoMunicipio > 0 && codigoMunicipio < 100000) {
			codigo = String.format("%05d", codigoMunicipio);
			for (Municipio municipio : listaMunicipios) {
				if (municipio.getCodigo().equals(codigo)) {
					return municipio;
				}
			}
			return null;
		} else {
			StringBuilder mensajeError = new StringBuilder();
			mensajeError.append("Posibles valores 5 dígitos según DANE");
			throw new ParametroInvalidoExcepcion(mensajeError.toString());
		}
	}

	/**
	 * Método que recibe el código del departamente y retorna el objeto
	 * correspondiente.
	 * 
	 * @param listaDepartamentos
	 * @param codigoDepto
	 * @return el objeto departamento que coincide, de no encontrarse retorna
	 *         null.
	 */
	public static Departamento getDepartamento(List<Departamento> listaDepartamentos, Long codigoDepto) {
		String codigo;
		if ((codigoDepto > 0 && codigoDepto < 100)) {
			codigo = String.format("%02d", codigoDepto);
			for (Departamento depto : listaDepartamentos) {
				if (depto.getCodigo().equals(codigo)) {
					return depto;
				}
			}
			return null;
		} else {
			StringBuilder mensajeError = new StringBuilder();
			mensajeError.append("Posibles valores 5 dígitos según DANE");
			throw new ParametroInvalidoExcepcion(mensajeError.toString());
		}
	}

	/**
	 * Método encargado de consultar un departamento por el nombre
	 * 
	 * @param listaDepartamentos,
	 *            lista de departamentos
	 * @param nombreDepto,
	 *            Nombre del departamento a verificar
	 * @return Departamento encontrado
	 */
	public static Departamento getDepartamentoNombre(List<Departamento> listaDepartamentos, String nombreDepto) {
		if (nombreDepto != null && !nombreDepto.equals("")) {
			for (Departamento depto : listaDepartamentos) {
				if (depto.getNombre().equals(nombreDepto)) {
					return depto;
				}
			}
			return null;
		} else {
			StringBuilder mensajeError = new StringBuilder();
			mensajeError.append("Posibles nombres de departamentos según DANE");
			throw new ParametroInvalidoExcepcion(mensajeError.toString());
		}
	}

	/**
	 * Método que recibe una string e intenta crear una instancia de
	 * TipoSalarioEnum
	 * 
	 * @param value
	 * @return
	 */
	public static TipoSalarioEnum getTipoSalario(String value) {
		try {
			TipoSalarioEnum tipoSalario = TipoSalarioEnum.valueOf(value);
			return tipoSalario;
		} catch (Exception e) {
			StringBuilder mensajeError = new StringBuilder();
			mensajeError.append("Posibles valores: ");
			for (TipoSalarioEnum g : TipoSalarioEnum.values()) {
				mensajeError.append(g.name());
				mensajeError.append(",");
			}
			throw new ParametroInvalidoExcepcion(mensajeError.toString());
		}
	}

	/**
	 * Método encargado de buscar un municipio por el nombre de este y por el
	 * departamento
	 * 
	 * @param listaMunicipios,lista
	 *            de municipios
	 * @param nombreMunicipio,
	 *            nombre del municipio
	 * @param idDepartamento,id
	 *            del departamento
	 * @return retorna el municipio
	 */
	public static Municipio getMunicipioNombre(List<Municipio> listaMunicipios, String nombreMunicipio,
			Short idDepartamento) {
		if (nombreMunicipio != null && !nombreMunicipio.equals("") && idDepartamento!=null) {
			for (Municipio municipio : listaMunicipios) {
				if (municipio.getNombre().equals(nombreMunicipio)
						&& municipio.getIdDepartamento().equals(idDepartamento)) {
					return municipio;
				}
			}
			return null;
		} else {
			StringBuilder mensajeError = new StringBuilder();
			mensajeError.append("Posibles nombres de municipios según DANE");
			throw new ParametroInvalidoExcepcion(mensajeError.toString());
		}
	}

	/**
	 * Método encargado de verificar el tipo de contrato solicitado se encuentra
	 * entre los permitidos
	 * 
	 * @param value,
	 *            valor a verificar
	 * @return retorna el tipo de contrato
	 */
	public static TipoContratoEnum getTipoContrato(String value) {
		try {
			TipoContratoEnum tipoContrato = TipoContratoEnum.valueOf(value);
			return tipoContrato;
		} catch (Exception e) {
			StringBuilder mensajeError = new StringBuilder();
			mensajeError.append("Posibles valores: ");
			for (TipoContratoEnum g : TipoContratoEnum.values()) {
				mensajeError.append(g.name());
				mensajeError.append(",");
			}
			throw new ParametroInvalidoExcepcion(mensajeError.toString());
		}
	}

	/**
	 * Método encargado de verificar la enumeración de tipo de inconsistencia
	 * ANI
	 * 
	 * @param value,
	 *            valor a verificar de la enumeración
	 * @return retorna la enumeación TipoInconsistenciaANIEnum
	 */
	public static TipoInconsistenciaANIEnum getTipoInconsistenciaANI(String value) {
		try {
			TipoInconsistenciaANIEnum tipoInconsistenciaANI = TipoInconsistenciaANIEnum.valueOf(value);
			return tipoInconsistenciaANI;
		} catch (Exception e) {
			StringBuilder mensajeError = new StringBuilder();
			mensajeError.append("Posibles valores: ");
			for (TipoInconsistenciaANIEnum g : TipoInconsistenciaANIEnum.values()) {
				mensajeError.append(g.name());
				mensajeError.append(",");
			}
			throw new ParametroInvalidoExcepcion(mensajeError.toString());
		}
	}

	/**
	 * Método encargado de verificar el tipo de identificacion de la enumeracion
	 * por la descripción
	 * 
	 * @param strTipoIdentificacion,
	 *            tipo de identificacion string
	 * @return retorna la enumeracion del tipo de identificacion
	 */
	public static TipoIdentificacionEnum getTipoIdentificacionDescripcion(String strTipoIdentificacion) {
		try {
			for (TipoIdentificacionEnum tipoIdentificacion : TipoIdentificacionEnum.values()) {
				if (tipoIdentificacion.getDescripcion().equals(strTipoIdentificacion)) {
					return tipoIdentificacion;
				}
			}
			return null;
		} catch (Exception e) {
			StringBuilder mensajeError = new StringBuilder();
			mensajeError.append("Posibles valores: ");
			for (TipoIdentificacionEnum tie : TipoIdentificacionEnum.values()) {
				if (tie.getIdentificionTrabajador() == true) {
					mensajeError.append(tie.name());
					mensajeError.append(",");
				}
			}
			throw new ParametroInvalidoExcepcion(mensajeError.toString());
		}
	}

	/**
	 * Método encargado de verificar el genero de la enumeracion por la
	 * descripción
	 * 
	 * @param strGenero,
	 *            genero descripción string
	 * @return retorna la enumeracion del genero
	 */
	public static GeneroEnum getGeneroDescripcion(String strGenero) {
		try {
			for (GeneroEnum generoEnum : GeneroEnum.values()) {
				if (generoEnum.getDescripcion().equals(strGenero)) {
					return generoEnum;
				}
			}
			return null;
		} catch (Exception e) {
			StringBuilder mensajeError = new StringBuilder();
			mensajeError.append("Posibles valores: ");
			for (GeneroEnum g : GeneroEnum.values()) {
				mensajeError.append(g.name());
				mensajeError.append(",");
			}
			throw new ParametroInvalidoExcepcion(mensajeError.toString());
		}
	}

	/**
	 * Método que recibe una string de la descripcion intenta crear una
	 * instancia de EstadoCivil
	 * 
	 * @param strDescripcion,
	 *            descripcion estado civil
	 * @return retorna el EstadoCivilEnum
	 */
	public static EstadoCivilEnum getEstadoCivilDescripcion(String strDescripcion) {
		try {
			for (EstadoCivilEnum estadoCivilEnum : EstadoCivilEnum.values()) {
				if (estadoCivilEnum.getDescripcion().equals(strDescripcion)) {
					return estadoCivilEnum;
				}
			}
			return null;
		} catch (Exception e) {
			StringBuilder mensajeError = new StringBuilder();
			mensajeError.append("Posibles valores: ");
			for (EstadoCivilEnum g : EstadoCivilEnum.values()) {
				mensajeError.append(g.name());
				mensajeError.append(",");
			}
			throw new ParametroInvalidoExcepcion(mensajeError.toString());
		}
	}

	/**
	 * Método que recibe una string e intenga crear una instancia de
	 * ClaseTrabajadorEnum
	 * 
	 * @param strClaseTrabajador
	 * @return retorna la clase trabajador Enum
	 */
	public static ClaseTrabajadorEnum getClaseTrabajadorDescripcion(String strClaseTrabajador) {
		try {
			for (ClaseTrabajadorEnum claseTrabjadorEnum : ClaseTrabajadorEnum.values()) {
				if (claseTrabjadorEnum.getDescripcion().equals(strClaseTrabajador)) {
					return claseTrabjadorEnum;
				}
			}
			return null;
		} catch (Exception e) {
			StringBuilder mensajeError = new StringBuilder();
			mensajeError.append("Posibles valores: ");
			for (ClaseTrabajadorEnum g : ClaseTrabajadorEnum.values()) {
				mensajeError.append(g.name());
				mensajeError.append(",");
			}
			throw new ParametroInvalidoExcepcion(mensajeError.toString());
		}
	}

	/**
	 * Método que recibe el código del departamente y retorna el objeto
	 * correspondiente.
	 * 
	 * @param listaMunicipios
	 * @param codigoMunicipio
	 * @return el objeto municipío que coincide, de no encontrarse retorna null.
	 */
	public static Municipio getMunicipioCodigo(List<Municipio> listaMunicipios, String codigoMunicipio) {
		for (Municipio municipio : listaMunicipios) {
			if (municipio.getCodigo().equals(codigoMunicipio)) {
				return municipio;
			}
		}
		return null;
	}

    /**
     * Método encargado de verificar el genero de la enumeracion por el
     * codigo
     * 
     * @param strGenero,
     *        genero codigo string
     * @return retorna la enumeracion del genero
     */
    public static GeneroEnum getGeneroCodigo(String strGenero) {
        try {
            for (GeneroEnum generoEnum : GeneroEnum.values()) {
                if (generoEnum.getCodigo().equals(strGenero)) {
                    return generoEnum;
                }
            }
            return null;
        } catch (Exception e) {
            StringBuilder mensajeError = new StringBuilder();
            mensajeError.append("Posibles valores: ");
            for (GeneroEnum g : GeneroEnum.values()) {
                mensajeError.append(g.name());
                mensajeError.append(",");
            }
            throw new ParametroInvalidoExcepcion(mensajeError.toString());
        }
    }

    /**
     * Método que recibe una integer e intenta crear una instancia de
     * NivelEducativoEnum
     * 
     * @param value
     *        Codigo homolagacion del enum
     * @return
     */
    public static NivelEducativoEnum getNivelEducativoEnumByCodigoHomologa(Integer value) {
        try {
            for (NivelEducativoEnum nivelEducativo : NivelEducativoEnum.values()) {
                if (nivelEducativo.getCodigosHomologacion() != null) {
                    for (Integer codigo : nivelEducativo.getCodigosHomologacion()) {
                        if (codigo.equals(value)) {
                            return nivelEducativo;
                        }
                    }
                }
            }
            return null;
        } catch (Exception e) {
            StringBuilder mensajeError = new StringBuilder();
            mensajeError.append("Posibles valores: ");
            for (NivelEducativoEnum g : NivelEducativoEnum.values()) {
                mensajeError.append(g.getCodigosHomologacion());
                mensajeError.append(",");
            }
            throw new ParametroInvalidoExcepcion(mensajeError.toString());
        }
    }

    /**
     * Obtiene el codigo CIIU de acuerdo al enviado por parametro de la lista enviada
     * @param listaCodigosCIIU
     *        Lista de codigos CIIU existentes
     * @param codigo
     *        CodigoCIIU a buscar
     * @return CodigoCIIU existente
     */
    public static CodigoCIIU getCodigoCIIU(List<CodigoCIIU> listaCodigosCIIU, String codigo) {
        if (codigo != null) {
            for (CodigoCIIU codigoCIIU : listaCodigosCIIU) {
                if (codigoCIIU.getCodigo().equals(codigo)) {
                    return codigoCIIU;
                }
            }
            return null;
        }
        else {
            StringBuilder mensajeError = new StringBuilder();
            mensajeError.append("Posibles valores 4 dígitos");
            throw new ParametroInvalidoExcepcion(mensajeError.toString());
        }
    }

    /**
     * Obtiene el grado academico de la lista enviada y de acuerdo al valor enviado
     * @param listaGradoAcademico
     *        Lista de grados academicos
     * @param strGradoAcademico
     *        Nombre de grado acedemico
     * @return Grado academico encontrado
     */
    public static GradoAcademico getGradoAcademico(List<GradoAcademico> listaGradoAcademico, String strGradoAcademico) {
        if (strGradoAcademico != null && !strGradoAcademico.isEmpty()) {
            for (GradoAcademico gradoAcademico : listaGradoAcademico) {
                if (gradoAcademico.getNombre().trim().equals(strGradoAcademico.trim())) {
                    return gradoAcademico;
                }
            }
            return null;
        }
        else {
            StringBuilder mensajeError = new StringBuilder();
            for (GradoAcademico gradoAcademico : listaGradoAcademico) {
                mensajeError.append(gradoAcademico.getNombre());
                mensajeError.append(",");
            }
            throw new ParametroInvalidoExcepcion(mensajeError.toString());
        }
    }
    
    /**
     * Obtiene la AFP de la lista y el valor enviados
     * @param listaAFP
     *        Lista de AFP existentes
     * @param strCodigoAFP
     *        Codigo pila del AFP
     * @return AFP encontrado
     */
    public static AFP getAFPByCodigoPila(List<AFP> listaAFP, String strCodigoAFP) {
        if (strCodigoAFP != null && !strCodigoAFP.isEmpty()) {
            for (AFP afp : listaAFP) {
                if (afp.getCodigoPila().equals(strCodigoAFP)) {
                    return afp;
                }
            }
            return null;
        }
        else {
            StringBuilder mensajeError = new StringBuilder();
            mensajeError.append("Posibles valores alfanumericos codigo PILA");
            throw new ParametroInvalidoExcepcion(mensajeError.toString());
        }
    }
    
    /**
     * Método encargado de verificar la enumeración de tipo de inconsistencia
     * ANI
     * 
     * @param value,
     *            descripción a verificar de la enumeración
     * @return retorna la enumeación TipoInconsistenciaANIEnum
     */
    public static TipoInconsistenciaANIEnum getTipoInconsistenciaANIPorDescripcion(String value) {
        try {
            TipoInconsistenciaANIEnum tipoInconsistenciaEnum=null;
            if(value.equals("")){
                return tipoInconsistenciaEnum;
            }
            for (TipoInconsistenciaANIEnum tipoInconsistencia : TipoInconsistenciaANIEnum.values()) {
                if(tipoInconsistencia.getDescripcion().equalsIgnoreCase(value)){
                    tipoInconsistenciaEnum=tipoInconsistencia;
                    break; 
                } 
            }
			System.out.println("Inconsistencia ani: " + tipoInconsistenciaEnum.name());
            return tipoInconsistenciaEnum;
        } catch (Exception e) {
            StringBuilder mensajeError = new StringBuilder();
            mensajeError.append("Posibles valores: ");
            for (TipoInconsistenciaANIEnum g : TipoInconsistenciaANIEnum.values()) {
                mensajeError.append(g.getDescripcion());
                mensajeError.append(",");
            }
            throw new ParametroInvalidoExcepcion(mensajeError.toString());
        }
    }

    /**
     * Método que recibe una cadena con la descripcion de NivelEducativoEnum
     * 
     * @param strDescripcion
     * @return
     */
    public static NivelEducativoEnum getNivelEducativoEnumDescripcion(String strDescripcion) {
        try {
            
            for (NivelEducativoEnum nivelEducativo : NivelEducativoEnum.values()) {
                if (nivelEducativo.getDescripcion().equals(strDescripcion)) {
                    return nivelEducativo;
                }
            }
            return null;
            
        } catch (Exception e) {
            StringBuilder mensajeError = new StringBuilder();
            mensajeError.append("Posibles valores: ");
            for (NivelEducativoEnum g : NivelEducativoEnum.values()) {
                mensajeError.append(g.name());
                mensajeError.append(",");
            }
            throw new ParametroInvalidoExcepcion(mensajeError.toString());
        }
    }
    
    /**
     * Método que recibe una string e intenta crear una instancia de
     * TipoSalarioEnum
     * 
     * @param strDescripcion
     * @return
     */
    public static TipoSalarioEnum getTipoSalarioDescripcion(String strDescripcion) {
        try {
            for (TipoSalarioEnum tipoSalarioEnum : TipoSalarioEnum.values()) {
                if (tipoSalarioEnum.getDescripcion().equals(strDescripcion)) {
                    return tipoSalarioEnum;
                }
            }
            return null;
        } catch (Exception e) {
            StringBuilder mensajeError = new StringBuilder();
            mensajeError.append("Posibles valores: ");
            for (TipoSalarioEnum g : TipoSalarioEnum.values()) {
                mensajeError.append(g.name());
                mensajeError.append(",");
            }
            throw new ParametroInvalidoExcepcion(mensajeError.toString());
        }
    }
    
    /**
     * Método encargado de verificar el tipo de contrato solicitado se encuentra
     * entre los permitidos
     * 
     * @param strDescripcion,
     *            valor a verificar
     * @return retorna el tipo de contrato
     */
    public static TipoContratoEnum getTipoContratoDescripcion(String strDescripcion) {
        try {
            for (TipoContratoEnum tipoContrato : TipoContratoEnum.values()) {
                if (tipoContrato.getDescripcion().equals(strDescripcion)) {
                    return tipoContrato;
                }
            }
            return null;
        } catch (Exception e) {
            StringBuilder mensajeError = new StringBuilder();
            mensajeError.append("Posibles valores: ");
            for (TipoContratoEnum g : TipoContratoEnum.values()) {
                mensajeError.append(g.name());
                mensajeError.append(",");
            }
            throw new ParametroInvalidoExcepcion(mensajeError.toString());
        }
    }
    
    /**
	 * Método que recibe una string e intenga crear una instancia de
	 * MotivoDesafiliacionAfiliadoEnum
	 * 
	 * @param value,
	 *            String a convertir a enumeración
	 * @return retorna el tipo de identificacion enum
	 */
	public static MotivoDesafiliacionAfiliadoEnum getTipoMotivoDesafiliacionAfiliado(String value) {
		try {
			MotivoDesafiliacionAfiliadoEnum motivo = MotivoDesafiliacionAfiliadoEnum.valueOf(value);
			return motivo;
		} catch (Exception e) {
			StringBuilder mensajeError = new StringBuilder();
			mensajeError.append("Posibles valores: ");
			for (MotivoDesafiliacionAfiliadoEnum motivo : MotivoDesafiliacionAfiliadoEnum.values()) {
                            mensajeError.append(motivo.getCodigo());
                            mensajeError.append(",");
			}
			throw new ParametroInvalidoExcepcion(mensajeError.toString());
		}
	}
        
        /**
	 * Método que recibe una string e intenga crear una instancia de
	 * MotivoDesafiliacionBeneficiarioEnum
	 * 
	 * @param value,
	 *            String a convertir a enumeración
	 * @return retorna el tipo de identificacion enum
	 */
	public static MotivoDesafiliacionBeneficiarioEnum getMotivoDesafiliacionBeneficiario(String value) {
		try {
			MotivoDesafiliacionBeneficiarioEnum motivo = MotivoDesafiliacionBeneficiarioEnum.valueOf(value);
			return motivo;
		} catch (Exception e) {
			StringBuilder mensajeError = new StringBuilder();
			mensajeError.append("Posibles valores: ");
			for (MotivoDesafiliacionBeneficiarioEnum motivo : MotivoDesafiliacionBeneficiarioEnum.values()) {
                            mensajeError.append(motivo.getCodigo());
                            mensajeError.append(",");
			}
			throw new ParametroInvalidoExcepcion(mensajeError.toString());
		}
	}
}
