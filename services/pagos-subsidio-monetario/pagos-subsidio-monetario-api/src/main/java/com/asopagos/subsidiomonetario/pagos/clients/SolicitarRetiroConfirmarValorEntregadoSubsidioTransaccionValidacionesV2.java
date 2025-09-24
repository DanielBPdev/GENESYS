package com.asopagos.subsidiomonetario.pagos.clients;

import java.math.BigDecimal;
import java.lang.Long;
import com.asopagos.enumeraciones.personas.TipoMedioDePagoEnum;
import java.util.Map;
import java.lang.Boolean;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/pagosSubsidioMonetario/solicitarRetiro/confirmarValorEntregadoSubsidioTransaccionValidacionesV2
 */
public class SolicitarRetiroConfirmarValorEntregadoSubsidioTransaccionValidacionesV2 extends ServiceClient {
  
	private String tipoIdAdmin;
	private String numeroIdAdmin;
	private BigDecimal valorSolicitado;
    private String usuario; 
	private String idTransaccionTercerPagador; 
	private String departamento;
	private String municipio;
	private String user;
	private String password;
	private String idPuntoCobro;


  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Map<String,String> result;
  
 	public SolicitarRetiroConfirmarValorEntregadoSubsidioTransaccionValidacionesV2 (
		String tipoIdAdmin,String numeroIdAdmin, BigDecimal valorSolicitado, String usuario, String idTransaccionTercerPagador, String departamento,
		String municipio, String user, String password,String idPuntoCobro) {

 		super();
		this.tipoIdAdmin = tipoIdAdmin;
		this.numeroIdAdmin = numeroIdAdmin;
		this.valorSolicitado = valorSolicitado;
        this.usuario = usuario;
		this.idTransaccionTercerPagador = idTransaccionTercerPagador;
		this.departamento = departamento;
		this.municipio = municipio;
		this.user = user;
		this.password = password;
		this.idPuntoCobro = idPuntoCobro;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.queryParam("tipoIdAdmin", tipoIdAdmin)
						.queryParam("numeroIdAdmin", numeroIdAdmin)
						.queryParam("valorSolicitado", valorSolicitado)
						.queryParam("usuario", usuario)
						.queryParam("idTransaccionTercerPagador", idTransaccionTercerPagador)
						.queryParam("departamento", departamento)
						.queryParam("municipio", municipio)
						.queryParam("user", user)
						.queryParam("password", password)
						.queryParam("idPuntoCobro", idPuntoCobro)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (Map<String,String>) response.readEntity(Map.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public Map<String,String> getResult() {
		return result;
	}

 
	public String getTipoIdAdmin() {
        return tipoIdAdmin;
    }

    public void setTipoIdAdmin(String tipoIdAdmin) {
        this.tipoIdAdmin = tipoIdAdmin;
    }

    // Getter y Setter para numeroIdAdmin
    public String getNumeroIdAdmin() {
        return numeroIdAdmin;
    }

    public void setNumeroIdAdmin(String numeroIdAdmin) {
        this.numeroIdAdmin = numeroIdAdmin;
    }

    // Getter y Setter para valorSolicitado
    public BigDecimal getValorSolicitado() {
        return valorSolicitado;
    }

    public void setValorSolicitado(BigDecimal valorSolicitado) {
        this.valorSolicitado = valorSolicitado;
    }
    // Getter y Setter para usuario
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    // Getter y Setter para idTransaccionTercerPagador
    public String getIdTransaccionTercerPagador() {
        return idTransaccionTercerPagador;
    }

    public void setIdTransaccionTercerPagador(String idTransaccionTercerPagador) {
        this.idTransaccionTercerPagador = idTransaccionTercerPagador;
    }

    // Getter y Setter para departamento
    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    // Getter y Setter para municipio
    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    // Getter y Setter para user
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    // Getter y Setter para password
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Getter y Setter para idPuntoCobro
    public String getIdPuntoCobro() {
        return idPuntoCobro;
    }

    public void setIdPuntoCobro(String idPuntoCobro) {
        this.idPuntoCobro = idPuntoCobro;
    }
  
}