/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import vista.VentanaLogin;
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
                    EstiloStockpilot.aplicarTemaEspacial();
                    
                    VentanaLogin login = new VentanaLogin((java.awt.Frame) null, true);
                    login.setVisible(true); 

                    if (login.esta_verificado()) {
                        VentanaPrincipal ventana_principal = new VentanaPrincipal();
                        ventana_principal.setVisible(true);
                    } else {                        
                        System.exit(0); 
                    }
                } catch (Exception ex) {
                    System.err.println("Error cargando FlatLaf: " + ex.getMessage());
                }                
                
            }
        });
    }
}
