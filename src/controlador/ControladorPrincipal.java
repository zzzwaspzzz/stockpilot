/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import vista.VentanaPrincipal;


/**
 *
 * @author Usuario
 */
public class ControladorPrincipal {
    
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    // 1. Forzamos los colores de la simulación del "Tercer Tono" (Azul Espacial Profundo)
                    javax.swing.UIManager.put("defaultFont", new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13));
                    
                    java.awt.Color fondoEspacial = new java.awt.Color(10, 17, 31); // #0A111F
                    java.awt.Color fondoContraste = new java.awt.Color(7, 11, 22); // #070B16
                    
                    javax.swing.UIManager.put("Component.background", fondoEspacial);
                    javax.swing.UIManager.put("Panel.background", fondoEspacial);
                    javax.swing.UIManager.put("TitlePane.background", fondoContraste);
                    javax.swing.UIManager.put("TabbedPane.background", fondoContraste);
                    
                    // Halo neón cian en los bordes y enfoques al hacer clic
                    javax.swing.UIManager.put("Component.focusColor", new java.awt.Color(12, 194, 240, 100));
                    javax.swing.UIManager.put("Component.borderColor", new java.awt.Color(30, 41, 59));
                    
                    // Color de los textos para que contrasten bien en blanco/gris suave
                    javax.swing.UIManager.put("Component.foreground", new java.awt.Color(226, 232, 240));
                    javax.swing.UIManager.put("Label.foreground", new java.awt.Color(226, 232, 240));

                    // 2. Encendemos el motor oscuro básico de FlatLaf
                    com.formdev.flatlaf.FlatDarkLaf.setup();
                    
                } catch (Exception ex) {
                    System.err.println("No se pudo iniciar FlatLaf: " + ex.getMessage());
                }

                // 3. Lanzamos la interfaz una vez cargado el LookAndFeel
                VentanaPrincipal.main(args);
            }
        });
    }
}
