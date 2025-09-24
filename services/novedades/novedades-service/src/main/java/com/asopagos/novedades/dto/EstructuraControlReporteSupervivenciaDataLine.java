package com.asopagos.novedades.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import com.asopagos.dto.modelo.AfiliadoModeloDTO;
import com.asopagos.entidades.ccf.personas.Afiliado;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.novedades.constants.NamedQueriesConstants;
import com.asopagos.util.CalendarUtils;
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

public class EstructuraControlReporteSupervivenciaDataLine implements ILineDataSource {

    private List<EstructuraReporteSupervivenciaTipo1DTO> datosTipo1;

    private static final String TIPO_REGISTRO_1 = "1";

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

        String nombre = "SMC";
        ArrayList<Object> dato = new ArrayList<Object>();
        EstructuraReporteSupervivenciaTipo1DTO d1;
        d1 = new EstructuraReporteSupervivenciaTipo1DTO();
        d1.setTipoRegistro(TIPO_REGISTRO_1);
        d1.setFechaInicial(CalendarUtils.darFormatoYYYYMMDD(new Date()));
        d1.setFechaFinal(CalendarUtils.darFormatoYYYYMMDD(new Date()));
        d1.setCodigoEntidad(((DatosFiltroConsultaDTO) queryFilter).getCodigoEntidad());
        nombre = nombre.concat(((DatosFiltroConsultaDTO) queryFilter).getCodigoEntidad());
        nombre = nombre.concat(CalendarUtils.darFormatoYYYYMMDDSinGuion(new Date()));
        d1.setNombreArchivo(nombre);

        Integer cont = 0;
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
        // Se revisa la lista para el conteo de registros
        if (listPersonas != null && !listPersonas.isEmpty()) {
            cont = listPersonas.size();
            cont = verificarListaPantalla(listPantalla, listPersonas, cont);
        }
        else if (listPantalla != null && !listPantalla.isEmpty()) {
            cont = listPantalla.size();
        }
        d1.setTotalRegistros(cont.toString());
        dato.add(d1);
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
    private boolean existePersona(AfiliadoModeloDTO afiliadoModeloDTO, List<Object[]> listPersonas) {
        boolean result = false;
        for (Object[] objects : listPersonas) {
            Persona persona = (Persona) objects[0];
            if (afiliadoModeloDTO.getNumeroIdentificacion().equals(persona.getNumeroIdentificacion())
                    && afiliadoModeloDTO.getTipoIdentificacion().equals(persona.getTipoIdentificacion())) {
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
     * @param listPersonas
     *        Lista de persona consultadas por estado
     * @param cont
     *        Contador de registros
     */
    private Integer verificarListaPantalla(List<AfiliadoModeloDTO> listPantalla, List<Object[]> listPersonas, Integer cont) {
        if (listPantalla != null && !listPantalla.isEmpty()) {
            cont += listPantalla.size();
            for (AfiliadoModeloDTO afiliadoModeloDTO : listPantalla) {
                if (existePersona(afiliadoModeloDTO, listPersonas)) {
                    cont--;
                }
            }
        }
        return cont;
    }

    /**
     * @return the datosTipo2
     */
    public List<EstructuraReporteSupervivenciaTipo1DTO> getDatosTipo2() {
        return datosTipo1;
    }

    /**
     * @param datosTipo2
     *        the datosTipo2 to set
     */
    public void setDatosTipo1(List<EstructuraReporteSupervivenciaTipo1DTO> datosTipo1) {
        this.datosTipo1 = datosTipo1;
    }

}
