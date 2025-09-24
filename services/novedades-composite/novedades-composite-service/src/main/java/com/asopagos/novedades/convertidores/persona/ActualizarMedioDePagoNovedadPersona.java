package com.asopagos.novedades.convertidores.persona;
/**
 * 
 */

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import com.asopagos.afiliados.clients.ActualizarMedioDePagoGrupoFamiliar;
import com.asopagos.afiliados.clients.ActualizarMedioDePagoPersona;
import com.asopagos.dto.modelo.MedioDePagoModeloDTO;
import com.asopagos.dto.modelo.PersonaModeloDTO;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.novedades.composite.service.NovedadCore;
import com.asopagos.novedades.dto.DatosPersonaNovedadDTO;
import com.asopagos.novedades.dto.SolicitudNovedadDTO;
import com.asopagos.personas.clients.ConsultarDatosPersona;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.services.common.ServiceClient;
import com.asopagos.afiliados.clients.ConsultarIdsGruposFamiliaresCargueMasivoTransferencia;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.LinkedList;
import java.util.concurrent.Callable;

/**
 * Clase que contiene la lógica para actualizar los datos de los medios de Pago.
 * @author Fabian López <flopez@heinsohn.com.co>
 *
 */
public class ActualizarMedioDePagoNovedadPersona implements NovedadCore{

    private List<TipoTransaccionEnum> cambioMedioDePagoAdmonSubsidio;
    private List<TipoTransaccionEnum> cambioMedioDePagoTrDependiente;
    private List<TipoTransaccionEnum> cambioAdministradorSubsidio;
    private List<TipoTransaccionEnum> cambioAdministradorSubsidioDepIndPens; 
    private List<TipoTransaccionEnum> cambioTitularCuentaNumCuentaDependiente; 

    @Override
    public ServiceClient transformarServicio(SolicitudNovedadDTO solicitudNovedadDTO) {
        // Se obtiene las listas de novedades a procesar
        System.out.println ("**__**ActualizarMedioDePagoNovedadPersona solicitudNovedadDTO "+solicitudNovedadDTO.toString());
        this.llenarListaNovedades();

        ServiceClient service = null;
        // Se obtiene la información a registrar de la persona
        DatosPersonaNovedadDTO datosPersona = solicitudNovedadDTO.getDatosPersona();
        System.out.println("ActualizarMedioDePagoNovedadPersona datosPersona --> "+ datosPersona);
        // Se obtiene la novedad registrada para procesarla
        TipoTransaccionEnum novedad = solicitudNovedadDTO.getTipoTransaccion();

        // Se consulta la persona afiliado
        ConsultarDatosPersona consultarDatosPersona = new ConsultarDatosPersona(datosPersona.getNumeroIdentificacion(), datosPersona.getTipoIdentificacion());
        consultarDatosPersona.execute();
        PersonaModeloDTO personaAfiliado = consultarDatosPersona.getResult();
        System.out.println ("**__**ActualizarMedioDePagoNovedadPersona personaAfiliado.identificacion "+personaAfiliado.getNumeroIdentificacion());

        // Se obtienen los datos del medio de pago
        MedioDePagoModeloDTO medioDePagoModeloDTO = datosPersona.getMedioDePagoModeloDTO();
        System.out.println ("**__**ActualizarMedioDePagoNovedadPersona medioDePagoModeloDTO "+medioDePagoModeloDTO.toString() );
        // Se verifica la novedad que se procesa
        System.out.println ("**__**ActualizarMedioDePagoNovedadPersona novedad "+novedad );
        if (cambioMedioDePagoTrDependiente.contains(novedad)
                || cambioTitularCuentaNumCuentaDependiente.contains(novedad)) {
            medioDePagoModeloDTO.setPersona(personaAfiliado);
            service = new ActualizarMedioDePagoPersona(medioDePagoModeloDTO);
            System.out.println ("**__**if ActualizarMedioDePagoNovedadPersona novedad "+novedad );
        }
        // =========== MASIVO TRANSFERENCIA
        else if(novedad == TipoTransaccionEnum.CAMBIO_MEDIO_DE_PAGO_MASIVO_TRANSFERENCIA){
            ConsultarIdsGruposFamiliaresCargueMasivoTransferencia idsSrv = new ConsultarIdsGruposFamiliaresCargueMasivoTransferencia(datosPersona.getTipoIdentificacion(),datosPersona.getNumeroIdentificacion());
            idsSrv.execute();
                for(Long idGrupo : idsSrv.getResult()){
                    medioDePagoModeloDTO.setIdGrupoFamiliar(idGrupo);
                    System.out.println ("**__**else if ActualizarMedioDePagoNovedadPersona medioDePagoModeloDTO "+medioDePagoModeloDTO.getNumeroTarjeta());
                    ActualizarMedioDePagoGrupoFamiliar servicioNovedad = new ActualizarMedioDePagoGrupoFamiliar(medioDePagoModeloDTO);
                    servicioNovedad.execute();
                }
        }else {
            // Actualizar los medio de pago de los grupos familiares
            medioDePagoModeloDTO.setIdGrupoFamiliar(datosPersona.getIdGrupoFamiliar());
            System.out.println ("**__**else ActualizarMedioDePagoNovedadPersona medioDePagoModeloDTO "+medioDePagoModeloDTO.getNumeroTarjeta());
            service = new ActualizarMedioDePagoGrupoFamiliar(medioDePagoModeloDTO);
        }
        return service;
    }

    /**
     * Asocia las novedades por cada tipo
     * 
     */
    private void llenarListaNovedades() {
        cambioMedioDePagoAdmonSubsidio = new ArrayList<>();
        cambioMedioDePagoAdmonSubsidio.add(TipoTransaccionEnum.CAMBIAR_MEDIO_DE_PAGO_ADMINISTRADOR_DE_SUBSIDIO_DEPWEB);
        cambioMedioDePagoAdmonSubsidio.add(TipoTransaccionEnum.CAMBIAR_MEDIO_DE_PAGO_ADMINISTRADOR_DE_SUBSIDIO_PRESENCIAL);
        cambioMedioDePagoAdmonSubsidio.add(TipoTransaccionEnum.CAMBIAR_MEDIO_DE_PAGO_ADMINISTRADOR_DE_SUBSIDIO_WEB);

        cambioMedioDePagoTrDependiente = new ArrayList<>();
        cambioMedioDePagoTrDependiente.add(TipoTransaccionEnum.CAMBIAR_MEDIO_DE_PAGO_DEPENDIENTE_DEPWEB);
        cambioMedioDePagoTrDependiente.add(TipoTransaccionEnum.CAMBIAR_MEDIO_DE_PAGO_DEPENDIENTE_PRESENCIAL);
        cambioMedioDePagoTrDependiente.add(TipoTransaccionEnum.CAMBIAR_MEDIO_DE_PAGO_DEPENDIENTE_WEB);

        cambioAdministradorSubsidio = new ArrayList<>();
        cambioAdministradorSubsidio.add(TipoTransaccionEnum.CAMBIO_ADMINISTRADOR_DEL_SUBSIDIO_MONETARIO_ADMINISTRADOR_SUBSIDIO);
        cambioAdministradorSubsidio.add(TipoTransaccionEnum.CAMBIO_ADMINISTRADOR_DEL_SUBSIDIO_MONETARIO_ADMINISTRADOR_SUBSIDIO_WEB);

        cambioAdministradorSubsidioDepIndPens = new ArrayList<>();
        cambioAdministradorSubsidioDepIndPens.add(TipoTransaccionEnum.CAMBIO_ADMINISTRADOR_DEL_SUBSIDIO_MONETARIO_TRABAJADOR_DEPENDIENTE);
        cambioAdministradorSubsidioDepIndPens.add(TipoTransaccionEnum.CAMBIO_ADMINISTRADOR_DEL_SUBSIDIO_MONETARIO_TRABAJADOR_DEPENDIENTE_WEB);
        
        cambioTitularCuentaNumCuentaDependiente = new ArrayList<>();
        cambioTitularCuentaNumCuentaDependiente.add(TipoTransaccionEnum.CAMBIO_DATOS_DE_CUENTA_TRABAJADOR_DEPENDIENTE_DEPWEB);
        cambioTitularCuentaNumCuentaDependiente.add(TipoTransaccionEnum.CAMBIO_DATOS_DE_CUENTA_TRABAJADOR_DEPENDIENTE_PRESENCIAL);
        cambioTitularCuentaNumCuentaDependiente.add(TipoTransaccionEnum.CAMBIO_DATOS_DE_CUENTA_TRABAJADOR_DEPENDIENTE_WEB);
    }

    @Override
    public void transformarEjecutarRutinaNovedad(SolicitudNovedadDTO datosNovedad, EntityManager entityManager, UserDTO userDTO) {
        // TODO Auto-generated method stub
        
    }

}