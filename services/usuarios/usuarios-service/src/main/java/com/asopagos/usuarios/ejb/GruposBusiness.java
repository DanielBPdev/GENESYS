package com.asopagos.usuarios.ejb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import com.asopagos.cache.CacheManager;
import com.asopagos.constants.ConstantesSistemaConstants;
import com.asopagos.enumeraciones.usuarios.EstadoUsuarioEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.usuarios.clients.KeyCloakRestClient;
import com.asopagos.usuarios.clients.KeyCloakRestClientFactory;
import com.asopagos.usuarios.constants.TipoGrupoEnum;
import com.asopagos.usuarios.constants.UserAttributesEnum;
import com.asopagos.usuarios.dto.GrupoDTO;
import com.asopagos.usuarios.dto.RolDTO;
import com.asopagos.usuarios.dto.UsuarioDTO;
import com.asopagos.usuarios.mapper.KeyCloakMapper;
import com.asopagos.usuarios.mapper.KeycloakAdapter;
import com.asopagos.usuarios.mapper.KeycloakClientFactory;
import com.asopagos.usuarios.service.GruposService;
import com.asopagos.util.Interpolator;

/**
 * <b>Descripcion:</b> EJB que implementa los métodos de negocio relacionados
 * con la gestión grupos del modulo de seguridad <br/>
 * <b>Módulo:</b> Asopagos - HU-SEG <br/>
 *
 * @author Luis Arturo Zarate Ayala<a href="mailto:lzarate@heinsohn.com.co"></a>
 */
@Stateless
public class GruposBusiness implements GruposService {

    private static final String DESCRIPCION = "descripcion";
    private static final String TIPO_GRUPO_ATTR = "tipo";

    /**
     * Log reference
     */
    private final ILogger logger = LogManager.getLogger(GruposBusiness.class);

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.usuarios.service.GruposService#obtenerMiembrosGrupo(java.lang.String, java.lang.String,
     * com.asopagos.enumeraciones.usuarios.EstadoUsuarioEnum)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<UsuarioDTO> obtenerMiembrosGrupo(String id, String sede, EstadoUsuarioEnum estado) {
        logger.debug(Interpolator.interpolate(
                "Inicio GruposBusiness.obtenerMiembrosGrupo(String,String,EstadoUsuarioEnum)\nid: {1}\tsede: {2}\t estado: {3}", id, sede,
                estado));
        final int max_limit = 1000;
        final int min_limit = 0;
        Set<UsuarioDTO> usuariosDTO = new HashSet<UsuarioDTO>();
        KeycloakAdapter kc = KeycloakClientFactory.getInstance().getKeycloakClient(KeycloakClientFactory.INTEGRACION);
        // se consulta que exista el grupo
        GroupRepresentation rep = kc.getKc().realm(kc.getRealm()).getGroupByPath("/" + id);
        logger.debug("Nombre grupo obtenido en KC: " + rep.getName());
        if (rep != null) {
            List<UserRepresentation> users = kc.getKc().realm(kc.getRealm()).groups().group(rep.getId()).members(min_limit, max_limit);
            for (UserRepresentation userRepresentation : users) {
                // se filtran los parametros de busqueda
                if (sede != null || estado != null) {
                	if(userRepresentation.getAttributes()!=null && !userRepresentation.getAttributes().isEmpty()){
                    List<?> atributos = (List<?>) userRepresentation.getAttributes().get(UserAttributesEnum.SEDE.getNombre());
                    String sedeUsuario = atributos != null ? (String) atributos.get(min_limit) : null;
                    if (sede == null) {
                        if (estado != null) {
                            if (estado.estaActivo().equals(userRepresentation.isEnabled())) {
                                usuariosDTO.add(KeyCloakMapper.toUserDTO(userRepresentation));
                            }
                        }
                        else {
                            usuariosDTO.add(KeyCloakMapper.toUserDTO(userRepresentation));
                        }
                    }
                    else {
                        if (sedeUsuario != null && sedeUsuario.equals(sede)) {
                            if (estado != null) {
                                if (estado.estaActivo().equals(userRepresentation.isEnabled())) {
                                    usuariosDTO.add(KeyCloakMapper.toUserDTO(userRepresentation));
                                }
                            }
                            else {
                                usuariosDTO.add(KeyCloakMapper.toUserDTO(userRepresentation));
                            }
                        }
                    }
                	}else{
                    	logger.debug("el usuario "+userRepresentation.getUsername()+" no tiene atributos en keycloak en el grupo "+ rep.getName());
                    }
                }
                else {
                    usuariosDTO.add(KeyCloakMapper.toUserDTO(userRepresentation));
                }
            }
        }
        else {
            return null;

        }
        logger.debug(Interpolator.interpolate("Grupo id: {1}, nombre grupo {2}, Usuarios obtenidos {3}", id, rep.getName(),
                usuariosDTO.toString()));

        return new ArrayList<UsuarioDTO>(usuariosDTO);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.usuarios.service.GruposService#consultarGrupos(com.asopagos.usuarios.constants.TipoGrupoEnum)
     */
    @Override
    public List<GrupoDTO> consultarGrupos(TipoGrupoEnum tipo) {
        String domain = (String) CacheManager.getConstante(ConstantesSistemaConstants.IDM_INTEGRATION_WEB_DOMAIN_NAME);
        logger.info("Dominio: " + domain);
        KeyCloakRestClient client = KeyCloakRestClientFactory
                .getKeyCloakRestClient(domain);
				logger.info("Cliente: " + client);
        return client.consultarGrupos(tipo.name());
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.usuarios.service.GruposService#esMiembroGrupo(java.lang.String, java.lang.String)
     */
    @Override
    public Boolean esMiembroGrupo(String grupo, String nombreUsuario) {
    	logger.info("esMiembroGrupo(String "+grupo+", String "+nombreUsuario+")");
        final int max_limit = 1;
        final int min_limit = 0;
        KeycloakAdapter adapter = KeycloakClientFactory.getInstance().getKeycloakClient(KeycloakClientFactory.INTEGRACION);
        Keycloak kc = adapter.getKc();

        List<UserRepresentation> users = kc.realm(adapter.getRealm()).users().search(nombreUsuario, null, null, null, min_limit, max_limit);
        if (users != null && !users.isEmpty()) {
            for (UserRepresentation userRepresentation : users) {
                List<GroupRepresentation> membership = kc.realm(adapter.getRealm()).users().get(userRepresentation.getId()).groups();
                if (membership != null && !membership.isEmpty()) {
                    for (GroupRepresentation groupRepresentation : membership) {
                        if (groupRepresentation.getName().equals(grupo)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.usuarios.service.GruposService#consultarRolesGrupo(java.lang.String)
     */
    @Override
    public List<RolDTO> consultarRolesGrupo(String id) {
        KeycloakAdapter adapter = KeycloakClientFactory.getInstance().getKeycloakClient(KeycloakClientFactory.INTEGRACION);
        Keycloak kc = adapter.getKc();
        // Lista de roles
        List<RolDTO> lstRoles = consultarRoles(adapter);
        // Lista de roles por id
        List<String> rep = kc.realm(adapter.getRealm()).groups().group(id).toRepresentation().getRealmRoles();
        for (int i = 0; i < rep.size(); i++) {
            for (int j = 0; j < lstRoles.size(); j++) {
                if (rep.get(i).equals(lstRoles.get(j).getName())) {
                    lstRoles.get(j).setHabilitado(true);
                }
            }
        }
        return lstRoles;
    }

    /**
     * Método encargado de consultar todos los roles
     * 
     * @return retorna la lista de todos roles
     */
    private List<RolDTO> consultarRoles(KeycloakAdapter kc) {
        List<RolDTO> lstRoles = new ArrayList<RolDTO>();
        // se consulta que exista el grupo
        RolesResource roleRep = kc.getKc().realm(kc.getRealm()).roles();
        if (roleRep != null) {
            for (RoleRepresentation roleRepresentation : roleRep.list()) {
                RolDTO rolDTO = new RolDTO();
                // Datos necesarios del rol para la pantalla
                rolDTO.setId(roleRepresentation.getId());
                rolDTO.setName(roleRepresentation.getName());
                lstRoles.add(rolDTO);
            }
        }
        else {
            return null;
        }
        return lstRoles;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.usuarios.service.GruposService#actualizarGrupoRoles(java.lang.String, java.util.List)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void actualizarGrupoRoles(String idGrupo, List<RolDTO> lstRoles) {
        KeyCloakRestClient client = KeyCloakRestClientFactory
                .getKeyCloakRestClient((String) CacheManager.getConstante(ConstantesSistemaConstants.IDM_INTEGRATION_WEB_DOMAIN_NAME));
        client.actualizarRolesGrupos(idGrupo, lstRoles);
    }

    @Override
    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.usuarios.service.GruposService#inactivarMiembrosGrupo(java.lang.String)
     */
    public void inactivarMiembrosGrupo(String id) {
        final int firstResult = 0;
        final int maxResults = 1000;
        KeycloakAdapter adapter = KeycloakClientFactory.getInstance().getKeycloakClient(KeycloakClientFactory.INTEGRACION);
        Keycloak kc = adapter.getKc();
        List<UserRepresentation> users = kc.realm(adapter.getRealm()).groups().group(id).members(firstResult, maxResults);
        if (users != null && !users.isEmpty()) {
            for (UserRepresentation userRepresentation : users) {
                if (userRepresentation.isEnabled()) {
                    userRepresentation.setEnabled(false);
                    kc.realm(adapter.getRealm()).users().get(userRepresentation.getId()).update(userRepresentation);
                }
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.usuarios.service.GruposService#consultarIdsPorNombresGrupos(java.util.List)
     */
    @Override
    public Map<String, String> consultarIdsPorNombresGrupos(List<String> nombreGrupos) {
        KeycloakAdapter adapter = KeycloakClientFactory.getInstance().getKeycloakClient(KeycloakClientFactory.INTEGRACION);
        Keycloak kc = adapter.getKc();
        List<GroupRepresentation> listadoGrupos = kc.realm(adapter.getRealm()).groups().groups();
        Map<String, String> gruposRep = new HashMap<>();
        for (GroupRepresentation gr : listadoGrupos) {
            gruposRep.put(gr.getName(), gr.getId());
        }
        Set<String> nombres = gruposRep.keySet();
        Map<String, String> retorno = new HashMap<>();
        for (String nombre : nombres) {
            if (nombreGrupos.contains(nombre)) {
                retorno.put(nombre, gruposRep.get(nombre));
            }
        }
        return retorno;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.usuarios.service.GruposService#consultarNombresPorIdsGrupos(java.util.List)
     */
    @Override
    public Map<String, String> consultarNombresPorIdsGrupos(List<String> ids) {
        KeycloakAdapter adapter = KeycloakClientFactory.getInstance().getKeycloakClient(KeycloakClientFactory.INTEGRACION);
        Keycloak kc = adapter.getKc();
        Map<String, String> retorno = new HashMap<>();
        for (String id : ids) {
            try {
                GroupRepresentation grupo = kc.realm(adapter.getRealm()).groups().group(id).toRepresentation();
                if (grupo != null) {
                    retorno.put(grupo.getId(), grupo.getName());
                }
            } catch (Exception e) {
            }
        }
        return retorno;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.usuarios.service.GruposService#consultarRoles()
     */
    @Override
    public List<RolDTO> consultarRoles() {
        List<RolDTO> roles = consultarRoles(KeycloakClientFactory.getInstance().getKeycloakClient(KeycloakClientFactory.INTEGRACION));
        return roles;
    }

}
