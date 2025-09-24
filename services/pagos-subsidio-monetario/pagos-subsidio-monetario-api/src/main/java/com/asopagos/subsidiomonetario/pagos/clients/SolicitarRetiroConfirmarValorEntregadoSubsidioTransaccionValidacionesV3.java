package com.asopagos.subsidiomonetario.pagos.clients;

import java.math.BigDecimal;
import java.lang.Long;
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
 * /rest/pagosSubsidioMonetario/solicitarRetiro/SolicitarRetiroConfirmarValorEntregadoSubsidioTransaccionValidacionesV3
 */
public class SolicitarRetiroConfirmarValorEntregadoSubsidioTransaccionValidacionesV3 extends ServiceClient {


    private String departamento;
    private BigDecimal valorSolicitado;
    private String idTransaccionTercerPagador;
    private String municipio;
    private String numeroIdAdmin;
    private String user;
    private String usuario;
    private String idPuntoCobro;
    private String tipoIdAdmin;
    private String password;


    /** Atributo que almacena los datos resultado del llamado al servicio */
    private Map<String, String> result;

    public SolicitarRetiroConfirmarValorEntregadoSubsidioTransaccionValidacionesV3 (String tipoIdAdmin,
                                                                                    String numeroIdAdmin, BigDecimal valorSolicitado,
                                                                                    String usuario, String idTransaccionTercerPagador, String departamento,
                                                                                    String municipio, String user, String password, String idPuntoCobro){
        super();
        this.departamento=departamento;
        this.valorSolicitado=valorSolicitado;
        this.idTransaccionTercerPagador=idTransaccionTercerPagador;
        this.municipio=municipio;
        this.numeroIdAdmin=numeroIdAdmin;
        this.user=user;
        this.usuario=usuario;
        this.idPuntoCobro=idPuntoCobro;
        this.tipoIdAdmin=tipoIdAdmin;
        this.password = password;
    }

    @Override
    protected Response invoke(WebTarget webTarget, String path) {
        Response response = webTarget.path(path)
                .queryParam("departamento", departamento)
                .queryParam("valorSolicitado", valorSolicitado)
                .queryParam("idTransaccionTercerPagador", idTransaccionTercerPagador)
                .queryParam("municipio", municipio)
                .queryParam("numeroIdAdmin", numeroIdAdmin)
                .queryParam("user", user)
                .queryParam("usuario", usuario)
                .queryParam("idPuntoCobro", idPuntoCobro)
                .queryParam("tipoIdAdmin", tipoIdAdmin)
                .queryParam("password", password)
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


    public void setDepartamento (String departamento){
        this.departamento=departamento;
    }

    public String getDepartamento (){
        return departamento;
    }
    public void setValorSolicitado (BigDecimal valorSolicitado){
        this.valorSolicitado=valorSolicitado;
    }

    public BigDecimal getValorSolicitado (){
        return valorSolicitado;
    }
    public void setIdTransaccionTercerPagador (String idTransaccionTercerPagador){
        this.idTransaccionTercerPagador=idTransaccionTercerPagador;
    }

    public String getIdTransaccionTercerPagador (){
        return idTransaccionTercerPagador;
    }
    public void setMunicipio (String municipio){
        this.municipio=municipio;
    }

    public String getMunicipio (){
        return municipio;
    }
    public void setNumeroIdAdmin (String numeroIdAdmin){
        this.numeroIdAdmin=numeroIdAdmin;
    }

    public String getNumeroIdAdmin (){
        return numeroIdAdmin;
    }
    public void setUser (String user){
        this.user=user;
    }

    public String getUser (){
        return user;
    }
    public void setUsuario (String usuario){
        this.usuario=usuario;
    }

    public String getUsuario (){
        return usuario;
    }
    public void setIdPuntoCobro (String idPuntoCobro){
        this.idPuntoCobro=idPuntoCobro;
    }

    public String getIdPuntoCobro (){
        return idPuntoCobro;
    }
    public void setTipoIdAdmin (String tipoIdAdmin){
        this.tipoIdAdmin=tipoIdAdmin;
    }

    public String getTipoIdAdmin (){
        return tipoIdAdmin;
    }

}