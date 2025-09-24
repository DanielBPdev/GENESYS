package com.asopagos.usuarios.mapper;

import java.util.Calendar;
import java.util.List;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.UserRepresentation;
import com.asopagos.usuarios.constants.UserAttributesEnum;
import com.asopagos.usuarios.dto.TokenDTO;
import com.asopagos.usuarios.dto.UsuarioCCF;
import com.asopagos.usuarios.dto.UsuarioDTO;
import com.asopagos.usuarios.dto.UsuarioEmpleadorDTO;
import com.asopagos.usuarios.dto.UsuarioTemporalDTO;
import com.asopagos.usuarios.dto.UsuarioGestionDTO;

public class KeyCloakMapper {
	
	public static UsuarioDTO toUserDTO(UserRepresentation representation){
		UsuarioDTO usuarioDTO = new UsuarioDTO();

        usuarioDTO.setCodigoSede(obtenerPropiedadUsuario(representation,
	                UserAttributesEnum.SEDE.getNombre()));	
        
        usuarioDTO.setCiudadSede(obtenerPropiedadUsuario(representation,
                    UserAttributesEnum.CIUDAD_SEDE.getNombre()));  
		
        usuarioDTO.setEmail(obtenerPropiedadUsuario(representation,
		                UserAttributesEnum.EMAIL.getNombre()));
        
		String fechaFinContrato = obtenerPropiedadUsuario(representation,
                UserAttributesEnum.FECHA_FIN_CONTRATO.getNombre());
        if (fechaFinContrato != null && !fechaFinContrato.isEmpty()) {
            usuarioDTO.setFechaFinContrato(new Long(fechaFinContrato));
        }

        usuarioDTO.setNombreUsuario(representation.getUsername());
		
		
		if(representation.getFirstName() != null){
		
	        usuarioDTO.setPrimerNombre(representation.getFirstName());
		}
		usuarioDTO.setSegundoNombre(obtenerPropiedadUsuario(representation,
		        UserAttributesEnum.SEGUNDO_NOMBRE.getNombre()));
		
		if(representation.getLastName() != null){
	        usuarioDTO.setPrimerApellido(representation.getLastName());
		}
		usuarioDTO.setSegundoApellido(obtenerPropiedadUsuario(representation,
		        UserAttributesEnum.SEGUNDO_APELLIDO.getNombre()));
		

	    usuarioDTO.setNumIdentificacion(obtenerPropiedadUsuario(representation,
		                UserAttributesEnum.NUM_IDENTIFICACION.getNombre()));
		

	    usuarioDTO.setTelefono(obtenerPropiedadUsuario(representation,
		                UserAttributesEnum.TELEFONO.getNombre()));
		
        usuarioDTO.setTipoIdentificacion(obtenerPropiedadUsuario(representation,
	                UserAttributesEnum.TIPO_IDENTIFICACION.getNombre()));		    
		
        List<String> grupos = representation.getGroups();
        
        
		if(representation.getGroups() != null){
	        usuarioDTO.setGrupos(grupos);
		}
		
		if(representation.getRealmRoles() != null){
		    usuarioDTO.setRoles(representation.getRealmRoles());
		}
		
		if(representation.getId() != null){
	        usuarioDTO.setIdUsuario(representation.getId());		    
		}

		if(representation.isEnabled() != null){
	        usuarioDTO.setUsuarioActivo(representation.isEnabled());		    
		}
		
		if(representation.isEnabled() != null){
	        usuarioDTO.setUsuarioActivo(representation.isEnabled());		    
		}
		
		if(representation.isEmailVerified() != null){
	        usuarioDTO.setEmailVerified(representation.isEmailVerified());
		}

		if(representation.getCreatedTimestamp() != null){
          usuarioDTO.setFechaCreacion(representation.getCreatedTimestamp() / 1000);       
    	}

		usuarioDTO.setCreadoPor(obtenerPropiedadUsuario(representation,
	                UserAttributesEnum.USUARIO_CREADO_POR.getNombre()));	

		String fechaModificacion = obtenerPropiedadUsuario(representation,
                UserAttributesEnum.FECHA_MODIFICACION.getNombre());
        if (fechaModificacion != null && !fechaModificacion.isEmpty()) {
            usuarioDTO.setFechaModificacion(new Long(fechaModificacion));
        }	

		usuarioDTO.setModificadoPor(obtenerPropiedadUsuario(representation,
	                UserAttributesEnum.USUARIO_MODIFICADO_POR.getNombre()));	

		
		return usuarioDTO;
	}
	
	public static TokenDTO toTokenDTO(AccessTokenResponse response){
		TokenDTO token=new TokenDTO();
		token.setId(response.getIdToken());
		token.setSessionId(response.getSessionState());
		token.setToken(response.getToken());
		return token;
	}

	public static UserRepresentation toUserRepresentation(UsuarioEmpleadorDTO user) {
		UserRepresentation userRepresentation=new UserRepresentation();
		userRepresentation.setCreatedTimestamp(Calendar.getInstance().getTimeInMillis());
		userRepresentation.setEnabled(true);
		userRepresentation.setFirstName(user.getPrimerNombre());
		userRepresentation.setLastName(user.getPrimerApellido());
		userRepresentation.setUsername(user.getNombreUsuario()==null ? user.getEmail():user.getNombreUsuario());
		return userRepresentation;
	}
	
	public static UserRepresentation toUserRepresentation(UsuarioTemporalDTO user) {
        UserRepresentation userRepresentation=new UserRepresentation();
        userRepresentation.setCreatedTimestamp(Calendar.getInstance().getTimeInMillis());
        userRepresentation.setEnabled(true);
        userRepresentation.setFirstName(user.getPrimerNombre());
        userRepresentation.setLastName(user.getPrimerApellido());
        userRepresentation.setUsername(user.getEmail());
        userRepresentation.setEmail(user.getEmail());
        return userRepresentation;
    }
	
	public static UserRepresentation toUserRepresentation(UsuarioCCF user) {
		UserRepresentation userRepresentation=new UserRepresentation();		
		userRepresentation.setCreatedTimestamp(Calendar.getInstance().getTimeInMillis());
		userRepresentation.setEnabled(true);
		userRepresentation.setFirstName(user.getPrimerNombre());
		userRepresentation.setLastName(user.getPrimerApellido());
		userRepresentation.setUsername(user.getNombreUsuario()==null ? user.getEmail():user.getNombreUsuario());
		return userRepresentation;
	}

	public static UsuarioCCF toUsuarioCCF(UserRepresentation user) {
		
		//se mapean los datos generales para todos los usuarios
		UsuarioCCF usuarioCCF=new UsuarioCCF(toUserDTO(user));
		
		//se mapean los datos particulares para los usuarios de
		//se obtiene la dependencia
		usuarioCCF.setDependencia(obtenerPropiedadUsuario(user,
				UserAttributesEnum.DEPENDENCIA.getNombre()));
		usuarioCCF.setSegundoApellido(obtenerPropiedadUsuario(user,
                UserAttributesEnum.SEGUNDO_APELLIDO.getNombre()));
		usuarioCCF.setSegundoNombre(obtenerPropiedadUsuario(user,
                UserAttributesEnum.SEGUNDO_NOMBRE.getNombre()));
		return usuarioCCF;
	}

	public static UsuarioGestionDTO toUsuarioGestion(UserRepresentation user) {
		
		//se mapean los datos generales para todos los usuarios
		UsuarioGestionDTO usuarioGestionDTO = new UsuarioGestionDTO(toUserDTO(user));
		
		//se mapean los datos particulares para los usuarios 
		if(user.getCreatedTimestamp() != null){
          usuarioGestionDTO.setFechaCreacion(user.getCreatedTimestamp() / 1000);        
    	}
		usuarioGestionDTO.setCreadoPor(obtenerPropiedadUsuario(user, UserAttributesEnum.USUARIO_CREADO_POR.getNombre()));
		String fechaModificacion = obtenerPropiedadUsuario(user, UserAttributesEnum.FECHA_MODIFICACION.getNombre());
        if (fechaModificacion != null && !fechaModificacion.isEmpty()) {
            usuarioGestionDTO.setFechaModificacion(new Long(fechaModificacion));
        }	
		usuarioGestionDTO.setModificadoPor(obtenerPropiedadUsuario(user, UserAttributesEnum.USUARIO_MODIFICADO_POR.getNombre()));
		usuarioGestionDTO.setRazonSocial(obtenerPropiedadUsuario(user, UserAttributesEnum.RAZON_SOCIAL.getNombre()));
		usuarioGestionDTO.setNombreConvenio(obtenerPropiedadUsuario(user, UserAttributesEnum.NOMBRE_CONVENIO.getNombre()));
		usuarioGestionDTO.setEstadoConvenio(obtenerPropiedadUsuario(user, UserAttributesEnum.ESTADO_CONVENIO.getNombre()));
		String valorConvenio = obtenerPropiedadUsuario(user, UserAttributesEnum.ID_CONVENIO.getNombre());
		usuarioGestionDTO.setIdConvenio(valorConvenio != null && !valorConvenio.isEmpty() ? Long.valueOf(valorConvenio) : null);
		usuarioGestionDTO.setMedioPago(obtenerPropiedadUsuario(user, UserAttributesEnum.MEDIO_PAGO.getNombre()));
		usuarioGestionDTO.setTipoUsuario(obtenerPropiedadUsuario(user, UserAttributesEnum.TIPO_USUARIO.getNombre()));
		return usuarioGestionDTO;
	}
	
	public static UsuarioTemporalDTO toUsuarioTemporalDTO(UserRepresentation user, Long fechaInicio, Long fechaFin) {
        //se mapean los datos generales para todos los usuarios
	    UsuarioTemporalDTO usuarioTem=new UsuarioTemporalDTO(toUserDTO(user));
	    usuarioTem.setFechaInicio(fechaInicio);
	    usuarioTem.setFechaFin(fechaFin);
        return usuarioTem;
    }
	

	private static String obtenerPropiedadUsuario(UserRepresentation user, String propiedad) {

        List<?> atributos = null;

        String atributo = null;
       
        if(propiedad != null){

            if(user.getAttributes() != null 
                    && user.getAttributes().get(propiedad) != null){

                atributos =(List<?>)user.getAttributes().get(propiedad);

                if(atributos != null){
                    atributo = (String)atributos.get(0);
                }else{
                    atributo = null;
                }
            }
        }
        
        return atributo;
	}

}
