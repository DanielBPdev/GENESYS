package com.asopagos.novedades.personas.web.load;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import com.asopagos.constants.ArchivoMultipleCampoConstants;
import com.asopagos.dto.cargaMultiple.TrabajadorCandidatoNovedadDTO;
import com.asopagos.dto.modelo.PersonaModeloDTO;
import com.asopagos.dto.modelo.UbicacionModeloDTO;
import com.asopagos.entidades.transversal.core.Departamento;
import com.asopagos.entidades.transversal.core.Municipio;
import com.asopagos.enumeraciones.core.TipoContratoEnum;
import com.asopagos.enumeraciones.core.TipoSalarioEnum;
import com.asopagos.novedades.constants.ArchivoCampoNovedadConstante;
import com.asopagos.util.CalendarUtils;
import com.asopagos.util.GetValueUtil;
import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.loader.IPersistLine;

/**
 * <b>Descripcion:</b> <b>Módulo:</b> Asopagos - HU </br>
 *
 * @author <a href="mailto:jusanchez@heinsohn.com.co"> jusanchez</a>
 */
public class NovedadTrabajadorPersistLine implements IPersistLine {

    /**
     * (non-Javadoc)
     * 
     * @see co.com.heinsohn.lion.fileprocessing.fileloader.loader.IPersistLine#persistLine(java.util.List,
     *      javax.persistence.EntityManager)
     */
    @Override
    public void persistLine(List<LineArgumentDTO> lines, EntityManager em) throws FileProcessingException {
        TrabajadorCandidatoNovedadDTO inDTO = null;
        if (lines.size() <= ArchivoMultipleCampoConstants.LONGITUD_REGISTROS) {
            for (int i = 0; i < lines.size(); i++) {
                if (i > 0 && i <= ArchivoMultipleCampoConstants.LONGITUD_REGISTROS) {
                    LineArgumentDTO lineArgumentDTO = lines.get(i);
                    try {
                        inDTO = generarTrabajadorCandidatoNovedadDTO(lineArgumentDTO);
                        ((List<TrabajadorCandidatoNovedadDTO>) lineArgumentDTO.getContext()
                                .get(ArchivoMultipleCampoConstants.LISTA_CANDIDATOS)).add(inDTO);
                    } catch (Exception e) {
                        ((List<TrabajadorCandidatoNovedadDTO>) lineArgumentDTO.getContext()
                                .get(ArchivoMultipleCampoConstants.LISTA_CANDIDATOS)).add(inDTO);
                    }
                }
            }
        }
        else {
            return;
        }
    }

    /**
     * (non-Javadoc)
     * 
     * @see co.com.heinsohn.lion.fileprocessing.fileloader.loader.IPersistLine#setRollback(javax.persistence.EntityManager)
     */
    @Override
    public void setRollback(EntityManager em) throws FileProcessingException {
        throw new UnsupportedOperationException();
    }

    /**
     * Metodo encargado de generar TrabajadorCandidatoNovedadDTO mediante su
     * construccion
     * 
     * @param lineArgumentDTO
     * @return retorna la construccion de un TrabajadorCandidatoNovedadDTO
     */
    private TrabajadorCandidatoNovedadDTO generarTrabajadorCandidatoNovedadDTO(LineArgumentDTO lineArgumentDTO) {
        Map<String, Object> line = lineArgumentDTO.getLineValues();
        TrabajadorCandidatoNovedadDTO trabajadorCandidato = new TrabajadorCandidatoNovedadDTO();
        PersonaModeloDTO personaDTO = generarPersonaDTO(line);
        UbicacionModeloDTO ubicacionDTO = generarUbicacionDTO(lineArgumentDTO, line);
        personaDTO.setUbicacionModeloDTO(ubicacionDTO);
        trabajadorCandidato.setPersonaDTO(personaDTO);

        if (((String) line.get(ArchivoCampoNovedadConstante.CLASE_TRABAJADOR)) != null) {
            trabajadorCandidato.setClaseTrabajador(
                    GetValueUtil.getClaseTrabajadorDescripcion(((String) line.get(ArchivoCampoNovedadConstante.CLASE_TRABAJADOR))));
        }

        Long fInicioLaboresEmpleador = null;
        try {
            if (line.get(ArchivoCampoNovedadConstante.FECHA_INICIO_LABORES_CON_EMPLEADOR) != null) {
                String strFechaInicioLaboresEmpleador = line.get(ArchivoCampoNovedadConstante.FECHA_INICIO_LABORES_CON_EMPLEADOR)
                        .toString();
                fInicioLaboresEmpleador = CalendarUtils.convertirFechaDate(strFechaInicioLaboresEmpleador);
                trabajadorCandidato.setFechaInicioContrato(new Date(fInicioLaboresEmpleador));
            }
        } catch (ParseException e) {
            trabajadorCandidato.setFechaInicioContrato(null);
        }
        if (line.get(ArchivoCampoNovedadConstante.VALOR_SALARIO_MENSUAL) != null) {
            String strSalarioMensual = line.get(ArchivoCampoNovedadConstante.VALOR_SALARIO_MENSUAL).toString();
            BigDecimal salarioMensual = new BigDecimal(strSalarioMensual);
            trabajadorCandidato.setValorSalarioMensual(salarioMensual);
        }
        if ((String) line.get(ArchivoCampoNovedadConstante.CAMBIO_TIPO_SALARIO) != null) {
            String strCambioTipoSalario = ((String) line.get(ArchivoCampoNovedadConstante.CAMBIO_TIPO_SALARIO));
            TipoSalarioEnum tipoSalario = GetValueUtil.getTipoSalarioDescripcion(strCambioTipoSalario);
            trabajadorCandidato.setTipoSalario(tipoSalario);
        }

        if (line.get(ArchivoCampoNovedadConstante.CARGO_OFICIO) != null) {
            trabajadorCandidato.setCargoOficina(((String) line.get(ArchivoCampoNovedadConstante.CARGO_OFICIO)));
        }

        if ((String) line.get(ArchivoCampoNovedadConstante.TIPO_CONTRATO) != null) {
            String strTipoContrato = ((String) line.get(ArchivoCampoNovedadConstante.TIPO_CONTRATO));
            TipoContratoEnum tipoContrato = GetValueUtil.getTipoContratoDescripcion(strTipoContrato);
            trabajadorCandidato.setTipoContratoEnum(tipoContrato);
        }
        return trabajadorCandidato;
    }

    /**
     * Metodo encargado de generar una personaDTO mediante la construccion de el
     * 
     * @param lineArgumentDTO
     * @param line
     * @return retorna la persona dto que se construyo
     */
    private PersonaModeloDTO generarPersonaDTO(Map<String, Object> line) {
        PersonaModeloDTO personaDTO = new PersonaModeloDTO();
        if (line.get(ArchivoCampoNovedadConstante.TIPO_IDENTIFICACION) != null) {
            personaDTO.setTipoIdentificacion(GetValueUtil
                    .getTipoIdentificacionDescripcion(((String) line.get(ArchivoCampoNovedadConstante.TIPO_IDENTIFICACION))));
        }

        if (line.get(ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION) != null) {
            personaDTO.setNumeroIdentificacion((String) line.get(ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION));
        }

        if (line.get(ArchivoCampoNovedadConstante.PRIMER_NOMBRE) != null) {
            personaDTO.setPrimerNombre((String) line.get(ArchivoCampoNovedadConstante.PRIMER_NOMBRE));
        }

        String segundoNombre = null;
        if (line.get(ArchivoCampoNovedadConstante.SEGUNDO_NOMBRE) != null) {
            segundoNombre =  line.get(ArchivoCampoNovedadConstante.SEGUNDO_NOMBRE).toString().toUpperCase();
        }
        personaDTO.setSegundoNombre(segundoNombre);
        personaDTO.setPrimerApellido((String) line.get(ArchivoCampoNovedadConstante.PRIMER_APELLIDO));
        String segundoApellido = null;
        if (line.get(ArchivoCampoNovedadConstante.SEGUNDO_APELLIDO) != null) {
            segundoApellido = line.get(ArchivoCampoNovedadConstante.SEGUNDO_APELLIDO).toString().toUpperCase();
        }
        personaDTO.setSegundoApellido(segundoApellido);
        if (line.get(ArchivoCampoNovedadConstante.RESIDE_SECTOR_RURAL) != null) {
            personaDTO.setResideSectorRural(ArchivoMultipleCampoConstants.SI
                    .equalsIgnoreCase(((String) line.get(ArchivoCampoNovedadConstante.RESIDE_SECTOR_RURAL))));
        }
        if (line.get(ArchivoCampoNovedadConstante.CAMBIO_NIVEL_EDUCATIVO) != null) {
            personaDTO.setNivelEducativo(GetValueUtil
                    .getNivelEducativoEnumDescripcion(((String) line.get(ArchivoCampoNovedadConstante.CAMBIO_NIVEL_EDUCATIVO))));
        }
        if (line.get(ArchivoCampoNovedadConstante.CONDICION_CASA_PROPIA) != null) {
            personaDTO.setHabitaCasaPropia(ArchivoMultipleCampoConstants.SI
                    .equalsIgnoreCase(((String) line.get(ArchivoCampoNovedadConstante.CONDICION_CASA_PROPIA))));
        }
        return personaDTO;
    }

    /**
     * Metodo encargado de generar la ubicacion dto mediante la construccion de
     * el
     * 
     * @param lineArgumentDTO
     * @param line
     * @return retorna la ubicacionDTO construida
     */
    private UbicacionModeloDTO generarUbicacionDTO(LineArgumentDTO lineArgumentDTO, Map<String, Object> line) {
        UbicacionModeloDTO ubicacionDTO = new UbicacionModeloDTO();
        if (line.get(ArchivoCampoNovedadConstante.DEPARTAMENTO) != null) {
            List<Departamento> lstDepartamento = ((List<Departamento>) lineArgumentDTO.getContext()
                    .get(ArchivoMultipleCampoConstants.LISTA_DEPARTAMENTO));
            String nombreDepartamento = ((String) line.get(ArchivoCampoNovedadConstante.DEPARTAMENTO)).toUpperCase();
            Departamento departamento = GetValueUtil.getDepartamentoNombre(lstDepartamento, nombreDepartamento);
            if (line.get(ArchivoCampoNovedadConstante.MUNICIPIO) != null) {
                List<Municipio> listaMunicipios = (List<Municipio>) lineArgumentDTO.getContext()
                        .get(ArchivoMultipleCampoConstants.LISTA_MUNICIPIO);
                Municipio municipio = GetValueUtil.getMunicipioNombre(listaMunicipios,
                        (String) line.get(ArchivoCampoNovedadConstante.MUNICIPIO), departamento.getIdDepartamento());
                ubicacionDTO.setIdMunicipio(municipio.getIdMunicipio());
            }
        }
        if (line.get(ArchivoCampoNovedadConstante.DIRECCION_RESIDENCIA) != null) {
            ubicacionDTO.setDireccionFisica(((String) line.get(ArchivoCampoNovedadConstante.DIRECCION_RESIDENCIA)).toUpperCase());
        }
        if (line.get(ArchivoCampoNovedadConstante.TELEFONO_FIJO) != null) {
            ubicacionDTO.setTelefonoFijo((String) line.get(ArchivoCampoNovedadConstante.TELEFONO_FIJO));
        }
        if (line.get(ArchivoCampoNovedadConstante.TELEFONO_CELULAR) != null) {
            ubicacionDTO.setTelefonoCelular((String) line.get(ArchivoCampoNovedadConstante.TELEFONO_CELULAR));
        }
        if (line.get(ArchivoCampoNovedadConstante.CORREO_ELECTRONICO) != null) {
            ubicacionDTO.setEmail((String) line.get(ArchivoCampoNovedadConstante.CORREO_ELECTRONICO));
        }
        if (line.get(ArchivoCampoNovedadConstante.CODIGO_POSTAL) != null) {
            ubicacionDTO.setCodigoPostal((String) line.get(ArchivoCampoNovedadConstante.CODIGO_POSTAL));
        }
        return ubicacionDTO;
    }
}