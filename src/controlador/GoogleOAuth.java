
package controlador;

import com.google.api.client.googleapis.auth.oauth2.GoogleBrowserClientRequestUrl;
import java.awt.Desktop;
import java.net.URI;
import java.util.Arrays;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Usuario
 */
public class GoogleOAuth {
   
    private static final String CLIENT_ID = "601423171395-3gbcbdm19pmso63vbkgrp5u1k8t6d18g.apps.googleusercontent.com";
    private static final String REDIRECT_URI = "http://localhost:8080/Callback";
    private static final int PUERTO_LOCAL = 8080;
    
    public String obtener_token_de_acceso() {
        String url_autorizacion = new GoogleBrowserClientRequestUrl(CLIENT_ID, REDIRECT_URI, Arrays.asList("email", "profile"))
                .setResponseTypes(Arrays.asList("code")) 
                .build();
        try
        {
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(new URI(url_autorizacion));
            } else {
                System.err.println("Error: El sistema operativo no soporta la apertura del navegador.");
                return null;
            }
        } catch (Exception ex)
        {
            Logger.getLogger(GoogleOAuth.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
        return escuchar_puerto_local();
    }
    
    private String escuchar_puerto_local() {
    try (ServerSocket server_socket = new ServerSocket(PUERTO_LOCAL)) {
        
        // 1. Capturamos la primera y única petición directa que nos envía Google
        Socket socket_unico = server_socket.accept();
        BufferedReader in = new BufferedReader(new InputStreamReader(socket_unico.getInputStream()));
        String linea_peticion = in.readLine();
        
        String codigo_autorizacion = null;
        
        // 2. Analizamos si la petición directa contiene el parámetro code=
        if (linea_peticion != null && linea_peticion.contains("code=")) {
            // Extraemos la sección intermedia de la URL (ej: /Callback?code=xxxx HTTP/1.1)
            String[] partes = linea_peticion.split(" ");
            if (partes.length > 1) {
                codigo_autorizacion = extraer_parametro(partes[1], "code");
            }
        }
        
        // 3. Le respondemos al navegador directamente con el mensaje de éxito final
        OutputStream out = socket_unico.getOutputStream();
        String html = "<html><body style='font-family:sans-serif; text-align:center; padding-top:50px;'>"
                + "<h2 style='color:#2c3e50;'>¡Autenticación procesada con éxito!</h2>"
                + "<p>Ya puede cerrar esta pestaña de forma segura y regresar a la aplicación de escritorio.</p>"
                + "</body></html>";
        
        out.write("HTTP/1.1 200 OK\r\n".getBytes());
        out.write("Content-Type: text/html; charset=UTF-8\r\n".getBytes());
        out.write(("Content-Length: " + html.getBytes("UTF-8").length + "\r\n\r\n").getBytes());
        out.write(html.getBytes("UTF-8"));
        out.flush();
        
        socket_unico.close();
        
        // 4. Retornamos el código interceptado para que la ventana continúe su flujo
        return codigo_autorizacion;
        
    } catch (Exception e) {
        System.err.println("Error en el servidor local de OAuth de un solo paso: " + e.getMessage());
        return null;
    }
}
    
    private String extraer_parametro(String url_completa, String parametro) throws Exception {
        Map<String, String> mapa_parametros = new HashMap<>();
        int index = url_completa.indexOf("?");
        if (index != -1) {
            String query = url_completa.substring(index + 1);
            String[] pairs = query.split("&");
            for (String pair : pairs) {
                int idx = pair.indexOf("=");
                mapa_parametros.put(
                    URLDecoder.decode(pair.substring(0, idx), "UTF-8"), 
                    URLDecoder.decode(pair.substring(idx + 1), "UTF-8")
                );
            }
        }
        return mapa_parametros.get(parametro);
    }
    
    private void enviar_respuesta_final_ok(Socket socket_dos) {
        try
        {
            OutputStream out = socket_dos.getOutputStream();
            String html = "<html><body style='font-family:sans-serif; text-align:center; padding-top:50px;'>"
                    + "<h2 style='color:#2c3e50;'>¡Autenticación procesada!</h2>"
                    + "<p>Ya puede cerrar esta pestaña de forma segura y regresar a la aplicación.</p>"
                    + "</body></html>";
            out.write("HTTP/1.1 200 OK\r\n".getBytes());
            out.write("Content-Type: text/html\r\n".getBytes());
            out.write(("Content-Length: " + html.length() + "\r\n\r\n").getBytes());
            out.write(html.getBytes());
            out.flush();
            socket_dos.close();
        } catch (IOException ex)
        {
            Logger.getLogger(GoogleOAuth.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void enviar_pagina_intermedia(Socket socket) {
        try
        {
            OutputStream out = socket.getOutputStream();
            String html = "<html><head><script>"
                    + "if(window.location.hash) { window.location.href = window.location.href.replace('#', '?'); }"
                    + "</script></head><body style='font-family:sans-serif; text-align:center; padding-top:50px;'> "
                    + "<h2>Procesando credenciales de StockPilot...</h2>"
                    + "</body></html>";            
            out.write("HTTP/1.1 200 OK\r\n".getBytes());
            out.write("Content-Type: text/html\r\n".getBytes());
            out.write(("Content-Length: " + html.length() + "\r\n\r\n").getBytes());
            out.write(html.getBytes());
            out.flush();
            socket.close();
        } catch (IOException ex)
        {
            Logger.getLogger(GoogleOAuth.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}