/**
 * 
 */
package com.asopagos.novedades.convertidores.persona;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import com.asopagos.afiliados.clients.ConsultarBeneficiariosAfiliacion;
import com.asopagos.afiliados.clients.InactivarAfiliadosMasivo;
import com.asopagos.dto.PersonaRetiroNovedadAutomaticaDTO;
import com.asopagos.dto.modelo.BeneficiarioModeloDTO;
import com.asopagos.dto.modelo.RolAfiliadoModeloDTO;
import com.asopagos.enumeraciones.afiliaciones.MotivoDesafiliacionAfiliadoEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.novedades.composite.clients.RadicarSolicitudNovedadCascada;
import com.asopagos.novedades.composite.service.NovedadCore;
import com.asopagos.novedades.dto.DatosNovedadCascadaDTO;
import com.asopagos.novedades.dto.SolicitudNovedadDTO;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.services.common.ServiceClient;

/**
 * <b>Descripción:</b> Clase que contiene la lógica para ejecutar la desafiliación
 * de Independientes y Pensionados por Mora en Aportes
 * 
 * <b>Historia de Usuario:</b> HU 496
 * proceso 1.3
 * 
 * @author Fabian Hernando López <flopez@heinsohn.com.co>
 * @author Jose Arley Correa <jocorrea@heinsohn.com.co>
 */
public class ActualizarRetiroMoraAportes implements NovedadCore {

    private final ILogger logger = LogManager.getLogger(ActualizarRetiroMoraAportes.class);

    /**
     * (non-Javadoc)
     * @see com.asopagos.novedades.composite.service.NovedadCore#transformarServicio(com.asopagos.novedades.dto.SolicitudNovedadDTO)
     */
    @Override
    public ServiceClient transformarServicio(SolicitudNovedadDTO solicitudNovedadDTO) {
        logger.debug("Inicio ActualizarRetiroMoraAportes.transformarServicio");
        List<RolAfiliadoModeloDTO> listRoles = new ArrayList<>();
        // Se obtiene la lista de personas a retirar
        List<PersonaRetiroNovedadAutomaticaDTO> listPersonas = solicitudNovedadDTO.getPersonasRetiroAutomatico();
        for (PersonaRetiroNovedadAutomaticaDTO personaRetiroNovedadAutomaticaDTO : listPersonas) {
            listRoles.addAll(personaRetiroNovedadAutomaticaDTO.getListRolAfiliado());
        }

        for (RolAfiliadoModeloDTO rolAfiliadoModeloDTO : listRoles) {
            // Se indican los datos de retiro del rol afiliado
            rolAfiliadoModeloDTO.setEstadoAfiliado(EstadoAfiliadoEnum.INACTIVO);
            rolAfiliadoModeloDTO.setFechaRetiro(new Date().getTime());
            rolAfiliadoModeloDTO.setMotivoDesafiliacion(MotivoDesafiliacionAfiliadoEnum.RETIRO_POR_MORA_APORTES);

            List<BeneficiarioModeloDTO> listaBeneficiarios = consultarBeneficiariosAfiliacion(
                    rolAfiliadoModeloDTO.getAfiliado().getIdAfiliado(), rolAfiliadoModeloDTO.getIdRolAfiliado());
            DatosNovedadCascadaDTO datosNovedadConsecutivaDTO = new DatosNovedadCascadaDTO();
            datosNovedadConsecutivaDTO.setFechaRetiro(rolAfiliadoModeloDTO.getFechaRetiro());
            datosNovedadConsecutivaDTO.setListaBeneficiario(listaBeneficiarios);
            datosNovedadConsecutivaDTO.setMotivoDesafiliacionAfiliado(rolAfiliadoModeloDTO.getMotivoDesafiliacion());
            datosNovedadConsecutivaDTO.setNumeroRadicadoOriginal(solicitudNovedadDTO.getNumeroRadicacion());
            datosNovedadConsecutivaDTO.setRolAfiliadoDTO(rolAfiliadoModeloDTO);
            datosNovedadConsecutivaDTO.setTipoTransaccionOriginal(solicitudNovedadDTO.getNovedadDTO().getNovedad());
            RadicarSolicitudNovedadCascada novedadCascada = new RadicarSolicitudNovedadCascada(datosNovedadConsecutivaDTO);
            novedadCascada.execute();
        }
        // Se inactivan las personas
        InactivarAfiliadosMasivo inactivarAfiliadosMasivo = new InactivarAfiliadosMasivo(listRoles);
        logger.debug("Fin ActualizarRetiroMoraAportes.transformarServicio");
        return inactivarAfiliadosMasivo;
    }

    /**
     * Realiza el llamado al servicio que consulta los beneficiarios asociados a la afiliación objeto del retiro
     * @param idAfiliado
     *        Identificador afiliado
     * @param idRolAfiliado
     *        Identificador del rol afiliado
     * @return
     */
    private List<BeneficiarioModeloDTO> consultarBeneficiariosAfiliacion(Long idAfiliado, Long idRolAfiliado) {
        ConsultarBeneficiariosAfiliacion consultarBeneficiariosAfiliacionService = new ConsultarBeneficiariosAfiliacion(idAfiliado,
                idRolAfiliado);
        consultarBeneficiariosAfiliacionService.execute();
        return consultarBeneficiariosAfiliacionService.getResult();
    }

    @Override
    public void transformarEjecutarRutinaNovedad(SolicitudNovedadDTO datosNovedad, EntityManager entityManager, UserDTO userDTO) {
        // TODO Auto-generated method stub
        
    }
}
