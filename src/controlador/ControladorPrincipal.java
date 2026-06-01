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
                    javax.swing.UIManager.put("defaultFont", new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13));
                    
                    java.awt.Color fondoEspacial = new java.awt.Color(10, 17, 31);
                    java.awt.Color fondoContraste = new java.awt.Color(7, 11, 22);
                    
                    javax.swing.UIManager.put("Component.background", fondoEspacial);
                    javax.swing.UIManager.put("Panel.background", fondoEspacial);
                    javax.swing.UIManager.put("TitlePane.background", fondoContraste);
                    javax.swing.UIManager.put("TabbedPane.background", fondoContraste);                    
                   
                    javax.swing.UIManager.put("Component.foreground", new java.awt.Color(226, 232, 240));
                    javax.swing.UIManager.put("Label.foreground", new java.awt.Color(226, 232, 240));                    
                    
                    javax.swing.UIManager.put("Component.focusColor", new java.awt.Color(12, 194, 240, 100));
                    javax.swing.UIManager.put("Component.borderColor", new java.awt.Color(30, 41, 59));
                    
                    com.formdev.flatlaf.FlatDarkLaf.setup();
                    
                } catch (Exception ex) {
                    System.err.println("Error cargando FlatLaf: " + ex.getMessage());
                }                
                VentanaPrincipal.main(args);
            }
        });
    }
}
