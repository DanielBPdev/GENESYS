package com.asopagos.novedades.dto;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import com.asopagos.dto.modelo.AfiliadoModeloDTO;
import com.asopagos.entidades.ccf.personas.Afiliado;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.novedades.constants.NamedQueriesConstants;
import co.com.heinsohn.lion.filegenerator.dto.QueryFilterInDTO;
import co.com.heinsohn.lion.filegenerator.ejb.ILineDataSource;
import co.com.heinsohn.lion.filegenerator.exception.FileGeneratorException;

/**
 * La siguiente estructura de los registros está dada para el convenio de la
 * caja de compensación “Confa” y “Colpensiones”. Estructura Solicitud de
 * Alta/Retiro
 * 
 * 
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author <a href="mailto:jzambrano@heinsohn.com.co"> Jerson Zambrano</a>
 */

public class EstructuraReporteSupervivenciaDataLine implements ILineDataSource {

    private List<EstructuraReporteSupervivenciaTipo2DTO> datosTipo2;

    private static final String TIPO_REGISTRO_2 = "2";

    /**
     * (non-Javadoc)
     * 
     * @see co.com.heinsohn.lion.filegenerator.ejb.ILineDataSource#getData(co.com.heinsohn.lion.filegenerator.dto.QueryFilterInDTO,
     *      int, int, javax.persistence.EntityManager)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Object> getData(QueryFilterInDTO queryFilter, int firstResult, int maxResults, EntityManager em)
            throws FileGeneratorException {

        List<Object> dato = new ArrayList<Object>();
        List<Object[]> listPersonas = new ArrayList<Object[]>();
        try {
            // Se crea el filtro de busqueda
            List<String> listFilter = new ArrayList<>();
            ((DatosFiltroConsultaDTO) queryFilter).getCriteriosBusquedad();
            for (EstadoAfiliadoEnum estado : ((DatosFiltroConsultaDTO) queryFilter).getCriteriosBusquedad()) {
                listFilter.add(estado.name());
            }
            //Se ejecuta el query
            listPersonas = em.createNamedQuery(NamedQueriesConstants.CONSULTAR_AFILIADO_POR_ESTADO)
                    .setParameter("estadoAfiliadoCaja", listFilter).getResultList();
        } catch (NoResultException e) {
            listPersonas = null;
        }
        // Lista de personas asocidas en pantalla
        List<AfiliadoModeloDTO> listPantalla = ((DatosFiltroConsultaDTO) queryFilter).getAfiliadosDto();
        if (listPersonas != null && !listPersonas.isEmpty()) {
            for (Object[] objects : listPersonas) {
                Persona persona = (Persona) objects[0];
                EstructuraReporteSupervivenciaTipo2DTO d2 = new EstructuraReporteSupervivenciaTipo2DTO();
                d2.setNumeroIdentificacion(persona.getNumeroIdentificacion());
                d2.setTipoIdentificacion(persona.getTipoIdentificacion().getValorEnPILA());
                d2.setTipoRegistro(TIPO_REGISTRO_2);
                dato.add(d2);
            }
            if (listPantalla != null && !listPantalla.isEmpty()) {
                addRegistro(listPantalla, dato);
            }
        }
        else if (listPantalla != null && !listPantalla.isEmpty()) {
            for (AfiliadoModeloDTO afiliadoModeloDTO : listPantalla) {
                EstructuraReporteSupervivenciaTipo2DTO d2 = new EstructuraReporteSupervivenciaTipo2DTO();
                d2.setNumeroIdentificacion(afiliadoModeloDTO.getNumeroIdentificacion());
                d2.setTipoIdentificacion(afiliadoModeloDTO.getTipoIdentificacion().getValorEnPILA());
                d2.setTipoRegistro(TIPO_REGISTRO_2);
                dato.add(d2);
            }
        }
        return dato;
    }

    /**
     * Se realiza verificacion si la persona enviada en pantalla tambien sale en la consulta por estado
     * @param afiliadoModeloDTO
     *        Informacion persona pantalla
     * @param listPersonas
     *        Lista personas
     * @return
     */
    private boolean existePersona(AfiliadoModeloDTO afiliadoModeloDTO, List<Object> dato) {
        boolean result = false;
        for (Object object : dato) {
            EstructuraReporteSupervivenciaTipo2DTO dt2 = (EstructuraReporteSupervivenciaTipo2DTO) object;
            if (dt2.getNumeroIdentificacion().equals(afiliadoModeloDTO.getNumeroIdentificacion())
                    && dt2.getTipoIdentificacion().equals(afiliadoModeloDTO.getTipoIdentificacion().getValorEnPILA())) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * Verifica si la persona de pantalla no hace parte del total
     * @param listPantalla
     *        Lista de personas de pantalla
     * @param dato
     *        Lista de persona consultadas por estado
     */
    private void addRegistro(List<AfiliadoModeloDTO> listPantalla, List<Object> dato) {
        if (listPantalla != null && !listPantalla.isEmpty()) {
            for (AfiliadoModeloDTO afiliadoModeloDTO : listPantalla) {
                if (!existePersona(afiliadoModeloDTO, dato)) {
                    EstructuraReporteSupervivenciaTipo2DTO d2 = new EstructuraReporteSupervivenciaTipo2DTO();
                    d2.setNumeroIdentificacion(afiliadoModeloDTO.getNumeroIdentificacion());
                    d2.setTipoIdentificacion(afiliadoModeloDTO.getTipoIdentificacion().getValorEnPILA());
                    d2.setTipoRegistro(TIPO_REGISTRO_2);
                    dato.add(d2);
                }
            }
        }
    }

    /**
     * @return the datosTipo2
     */
    public List<EstructuraReporteSupervivenciaTipo2DTO> getDatosTipo2() {
        return datosTipo2;
    }

    /**
     * @param datosTipo2
     *        the datosTipo2 to set
     */
    public void setDatosTipo2(List<EstructuraReporteSupervivenciaTipo2DTO> datosTipo2) {
        this.datosTipo2 = datosTipo2;
    }
}
