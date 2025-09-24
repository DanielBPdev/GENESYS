package com.asopagos.util;
 
  
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.net.ssl.HttpsURLConnection;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.util.HashMap;
import java.util.Iterator;
import javax.net.ssl.SSLContext;
import java.util.Map;
import java.util.Set;

public class HttpWrapper {


    private static final ILogger logger = LogManager.getLogger(HttpWrapper.class);

    public static Map<String, Object> sendHttpPost(String urlRequest, Map<String, Object> parametros, String bearer, SSLContext ssl) throws KeyManagementException {
        Map<String, Object> retorno = new HashMap<String, Object>();
        try {
            if (parametros != null) {
                StringBuilder postData = new StringBuilder();
                for (Map.Entry<String, Object> param : parametros.entrySet()) {
                    if (postData.length() != 0) {
                        postData.append('&');
                    }
                    postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                    postData.append('=');
                    postData.append(URLEncoder.encode(String.valueOf(param.getValue()),
                            "UTF-8"));
                }
                byte[] postDataBytes = postData.toString().getBytes("UTF-8");

                URL url = new URL(urlRequest);
                HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
                conn.setSSLSocketFactory(ssl.getSocketFactory());
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
                conn.setRequestProperty("Authorization",bearer);
                conn.setDoOutput(true);
                conn.getOutputStream().write(postDataBytes);
                if (conn.getResponseCode() != HttpsURLConnection.HTTP_OK && conn.getResponseCode() != HttpsURLConnection.HTTP_ACCEPTED) {
                    System.out.println("Respuesta ERROR");
                    StringBuilder respuesta = new StringBuilder();
                    System.out.println("conn.getInputStream(): " + conn.getErrorStream());
                    if (conn.getErrorStream() != null) {
                        System.out.println("conn.getInputStream()");
                        BufferedReader br = new BufferedReader(new InputStreamReader((conn.getErrorStream())));
                        String output;
                        while ((output = br.readLine()) != null) {
                            System.out.println("output");
                            respuesta.append(output);
                        }
                    }
                    System.out.println(respuesta);
                    retorno.put("status", "KO");
                    retorno.put("mensaje", respuesta);
                    if (respuesta != null && respuesta.toString().contains("error_description")) {
                        retorno.put("status", "KO");
                        retorno.put("mensaje", respuesta);
                    } else {
                        logger.error("Se produjo un error al llamadar el servicio,codigo de error:" + conn.getResponseCode());
                        throw new RuntimeException("Failed : HTTP error code : "
                                + conn.getResponseCode());
                    }
                }else{
                    BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
                    String output;
                    StringBuilder respuesta = new StringBuilder();
                    while ((output = br.readLine()) != null) {
                        respuesta.append(output);
                    }
                    conn.disconnect();
                    retorno.put("status", "OK");
                    retorno.put("mensaje", respuesta);
                }
            }
        } catch (MalformedURLException e) {
            logger.error("Se produjo un error al construir la url", e);
            retorno = null;
        } catch (IOException e) {
            logger.error("Se produjo un error al llamar el servicio", e);
            retorno = null;
        }
        return retorno;
    }

    public static String sendHttpPostJSONRHeader(String urlRequest, String json, String bearer) {
        //Map<String,Object> retorno = new HashMap<String,Object>();
        String retorno = null;
        try {
            byte[] postDataBytes = json.toString().getBytes("UTF-8");

            URL url = new URL(urlRequest);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", "bearer " + bearer);
            conn.setDoOutput(true);
            conn.getOutputStream().write(postDataBytes);

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK && conn.getResponseCode() != HttpURLConnection.HTTP_NO_CONTENT && conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
                logger.error("Se produjo un error al llamadar el servicio,codigo de error:" + conn.getResponseCode());
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }
            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            String output;
            StringBuilder respuesta = new StringBuilder();
            while ((output = br.readLine()) != null) {
                respuesta.append(output);
            }
            String location = conn.getHeaderField("Location");
            conn.disconnect();
            retorno = location;
        } catch (MalformedURLException e) {
            logger.error("Se produjo un error al construir la url", e);
            retorno = null;
        } catch (IOException e) {
            logger.error("Se produjo un error al llamar el servicio", e);
            retorno = null;
        }
        return retorno;
    }

    public static String httpGETBearer(String urlRequest, String token) {
        String retorno = null;
        try {
            URL url = new URL(urlRequest);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            StringBuilder postData = new StringBuilder();
            postData.append(URLEncoder.encode("grant_type", "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf("client_credentials"),
                    "UTF-8"));
            byte[] postDataBytes = postData.toString().getBytes("UTF-8");
//            conn.setRequestProperty("Accept","application/json");
            conn.setRequestProperty("Authorization", "bearer " + token);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setDoOutput(true);
            conn.getOutputStream().write(postDataBytes);

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                logger.error("Se produjo un error al llamadar el servicio,codigo de error:" + conn.getResponseCode());
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }
            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            String output;
            StringBuilder respuesta = new StringBuilder();
            while ((output = br.readLine()) != null) {
                respuesta.append(output);
            }
            conn.disconnect();
            retorno = respuesta.toString();
        } catch (MalformedURLException e) {
            logger.error("Se produjo un error al construir la url", e);
            retorno = null;
        } catch (IOException e) {
            logger.error("Se produjo un error al llamar el servicio", e);
            retorno = null;
        }
        return retorno;
    }
    
    public static String httpGETBearerNoContentType(String urlRequest, String token) {
        String retorno = null;
        try {
            URL url = new URL(urlRequest);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

//            StringBuilder postData = new StringBuilder();
//            postData.append(URLEncoder.encode("grant_type", "UTF-8"));
//            postData.append('=');
//            postData.append(URLEncoder.encode(String.valueOf("client_credentials"),"UTF-8"));
//            byte[] postDataBytes = postData.toString().getBytes("UTF-8");
//            conn.setRequestProperty("Accept","application/json");
            conn.setRequestProperty("Authorization", "bearer " + token);
//            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setDoOutput(true);
//            conn.getOutputStream().write(postDataBytes);

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                logger.error("Se produjo un error al llamadar el servicio,codigo de error:" + conn.getResponseCode());
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }
            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            String output;
            StringBuilder respuesta = new StringBuilder();
            while ((output = br.readLine()) != null) {
                respuesta.append(output);
            }
            conn.disconnect();
            retorno = respuesta.toString();
        } catch (MalformedURLException e) {
            logger.error("Se produjo un error al construir la url", e);
            retorno = null;
        } catch (IOException e) {
            logger.error("Se produjo un error al llamar el servicio", e);
            retorno = null;
        }
        return retorno;
    }

    public static String httpGETBasicSinToken(String urlRequest) {
        String retorno = null;
        try {
            URL url = new URL(urlRequest);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);

            /*if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                logger.error("Se produjo un error al llamadar el servicio,codigo de error:" + conn.getResponseCode());
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }*/
            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            String output;
            StringBuilder respuesta = new StringBuilder();
            while ((output = br.readLine()) != null) {
                respuesta.append(output);
            }
            conn.disconnect();
            retorno = respuesta.toString();
        } catch (MalformedURLException e) {
            logger.error("Se produjo un error al construir la url", e);
            retorno = null;
        } catch (IOException e) {
            logger.error("Se produjo un error al llamar el servicio", e);
            retorno = null;
        }
        return retorno;
    }
    
    
    public static String sendHttpPutJSON(String urlRequest, String json, String bearer) {
        //Map<String,Object> retorno = new HashMap<String,Object>();
        String retorno = null;
        try {
            byte[] postDataBytes = json.toString().getBytes("UTF-8");

            URL url = new URL(urlRequest);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", "bearer " + bearer);
            conn.setDoOutput(true);
            conn.getOutputStream().write(postDataBytes);

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK && conn.getResponseCode() != HttpURLConnection.HTTP_NO_CONTENT) {
                logger.error("Se produjo un error al llamadar el servicio,codigo de error:" + conn.getResponseCode());
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }
            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            String output;
            StringBuilder respuesta = new StringBuilder();
            while ((output = br.readLine()) != null) {
                respuesta.append(output);
            }
            conn.disconnect();
            retorno = respuesta.toString();
        } catch (MalformedURLException e) {
            logger.error("Se produjo un error al construir la url", e);
            retorno = null;
        } catch (IOException e) {
            logger.error("Se produjo un error al llamar el servicio", e);
            retorno = null;
        }
        return retorno;
    }
    
    public static Map<String, Object> sendHttp(String urlRequest,String metodo,String payload, Map<String, String> header) {
        Map<String, Object> retorno = new HashMap<String, Object>();
        try {
            // PASAR EL PAYLOAD  A BYTES
            byte[] postDataBytes = payload.toString().getBytes("UTF-8");

            // CREAR OBJETO URL
            URL url = new URL(urlRequest);
            // CREAR CONEXION
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // SETEAR METODO (POST,GET,ETC)
            conn.setRequestMethod(metodo);
            // SETEAR HEADER
            Set<String> keys = header.keySet();
            Iterator<String> ite = keys.iterator();
            while(ite.hasNext()){
                String key = ite.next();
                conn.setRequestProperty(key, header.get(key));
            }
            if(metodo.equals("POST") || metodo.equals("PUT")){
                conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
            }
            // ENVIAR PETICION
            conn.setDoOutput(true);
            if(metodo.equals("POST") || metodo.equals("PUT")){
                conn.getOutputStream().write(postDataBytes);
            }

            // SE LA RESPUESTA ES OK
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                StringBuilder respuesta = new StringBuilder();
                // EL REPSONSE NO ES NULA
                if (conn.getInputStream() != null) {
                    BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
                    String output;
                    while ((output = br.readLine()) != null) {
                        respuesta.append(output);
                    }
                    retorno.put("status", "OK");
                    retorno.put("mensaje", respuesta);
                }else{
                    retorno.put("status", "KO");
                    retorno.put("mensaje", null);
                }
            }else{
                StringBuilder respuesta = new StringBuilder();
                 if (conn.getInputStream() != null) {
                      BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
                    String output;
                    while ((output = br.readLine()) != null) {
                        respuesta.append(output);
                    }
                    retorno.put("status", "KO");
                    retorno.put("mensajeError", respuesta);
                 }
            }
            conn.disconnect();
        } catch (MalformedURLException e) {
            logger.error("Se produjo un error al construir la url", e);
            retorno = null;
        } catch (IOException e) {
            logger.error("Se produjo un error al llamar el servicio", e);
            retorno = null;
        }
        return retorno;
    }
    
    public static String sendHttpPostJSON(String urlRequest, String json,String bearer ) {
        //Map<String,Object> retorno = new HashMap<String,Object>();
        String retorno = null;
        try {
            byte[] postDataBytes = json.toString().getBytes("UTF-8");

            URL url = new URL(urlRequest);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", "Bearer " + bearer);
            conn.setDoOutput(true);
            conn.getOutputStream().write(postDataBytes);

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK && conn.getResponseCode() != HttpURLConnection.HTTP_NO_CONTENT && conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
                StringBuilder respuesta = new StringBuilder();
                System.out.println("conn.getInputStream(): " + conn.getErrorStream());
                if (conn.getErrorStream() != null) {
                    System.out.println("conn.getInputStream()");
                    BufferedReader br = new BufferedReader(new InputStreamReader((conn.getErrorStream())));
                    String output;
                    while ((output = br.readLine()) != null) {
                        System.out.println("output");
                        respuesta.append(output);
                    }
                }
                System.out.println(respuesta);
                retorno = respuesta.toString();
            }else{
                BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
                String output;
                StringBuilder respuesta = new StringBuilder();
                while ((output = br.readLine()) != null) {
                    respuesta.append(output);
                }
                conn.disconnect();
                retorno = respuesta.toString();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            logger.error("Se produjo un error al construir la url", e);
            retorno = null;
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("Se produjo un error al llamar el servicio", e);
            retorno = null;
        }
        return retorno;
    }

    public static String sendHttpPostJSONSinToken(String urlRequest, String json ) {
        //Map<String,Object> retorno = new HashMap<String,Object>();
        String retorno = null;
        try {
            byte[] postDataBytes = json.toString().getBytes("UTF-8");

            URL url = new URL(urlRequest);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            conn.getOutputStream().write(postDataBytes);

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK && conn.getResponseCode() != HttpURLConnection.HTTP_NO_CONTENT && conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
                StringBuilder respuesta = new StringBuilder();
                System.out.println("conn.getInputStream(): " + conn.getErrorStream());
                if (conn.getErrorStream() != null) {
                    System.out.println("conn.getInputStream()");
                    BufferedReader br = new BufferedReader(new InputStreamReader((conn.getErrorStream())));
                    String output;
                    while ((output = br.readLine()) != null) {
                        System.out.println("output");
                        respuesta.append(output);
                    }
                }
                System.out.println(respuesta);
                retorno = respuesta.toString();
            }else{
                BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
                String output;
                StringBuilder respuesta = new StringBuilder();
                while ((output = br.readLine()) != null) {
                    respuesta.append(output);
                }
                conn.disconnect();
                retorno = respuesta.toString();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            logger.error("Se produjo un error al construir la url", e);
            retorno = null;
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("Se produjo un error al llamar el servicio", e);
            retorno = null;
        }
        return retorno;
    }
    
}
