package com.asopagos.notificaciones.client;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import com.asopagos.cache.CacheManager;
import com.asopagos.rest.exception.TechnicalException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonElement;
import com.asopagos.util.HttpWrapper;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;

public class NiyarakyClient {

    private final String urlBase;
    private final String email;
    private final String password;

    private static final ILogger logger = LogManager.getLogger(HttpWrapper.class);

    public NiyarakyClient() {
        this.urlBase = (String) CacheManager.getParametro("NIYARAKY_URL_BASE");
        this.email = (String) CacheManager.getParametro("NIYARAKY_EMAIL_REST");
        this.password = (String) CacheManager.getParametro("NIYARAKY_PASSWORD_REST");

        if (urlBase == null || urlBase.isEmpty()) {
            throw new TechnicalException("No se encontró la URL base para Niyaraky en la cache.");
        }
        if (email == null || email.isEmpty()) {
            throw new TechnicalException("No se encontró el correo de Niyaraky en la cache.");
        }
        if (password == null || password.isEmpty()) {
            throw new TechnicalException("No se encontró la contraseña de acceso para Niyaraky en la cache.");
        }
    }

    public String obtenerToken() throws Exception {
        JsonObject jsonRequest = new JsonObject();
        jsonRequest.addProperty("username", email);
        jsonRequest.addProperty("password", password);

        String response = HttpWrapper.sendHttpPostJSONSinToken(urlBase + "/login", jsonRequest.toString());
        if (response == null) {
            throw new TechnicalException("No se pudo obtener el token de autenticación.");
        }
        JsonObject jsonResponse = new com.google.gson.JsonParser().parse(response).getAsJsonObject();
        return jsonResponse.get("token").getAsString();
    }

    public String enviarMensaje(String asunto, String texto,
                                String nombreDestinatario, String correoDestinatario, String adjuntos) throws Exception {

        String token = obtenerToken();
        JsonObject jsonRequest = new JsonObject();
        jsonRequest.addProperty("idUsuario", email);
        jsonRequest.addProperty("Asunto", asunto + "|[ENVIO_DIRECTO]");
        jsonRequest.addProperty("Texto", texto);
        jsonRequest.addProperty("NombreDestinatario", nombreDestinatario);
        jsonRequest.addProperty("CorreoDestinatario", correoDestinatario);

        try {
            JsonElement adjuntosElement = new com.google.gson.JsonParser().parse(adjuntos);
            if (!adjuntosElement.isJsonArray()) {
                throw new IllegalArgumentException("Adjuntos debe ser un array JSON válido");
            }
            jsonRequest.add("Adjuntos", adjuntosElement);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error en formato JSON de adjuntos", e);
        }


        String response = HttpWrapper.sendHttpPostJSON(urlBase + "/messages/registrarMensaje", 
                jsonRequest.toString(), token);

        if (response == null) {
            throw new TechnicalException("Error al enviar el mensaje.");
        }

        return response;
    }
}
