/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import com.formdev.flatlaf.FlatDarkLaf;
import java.awt.Color;
import java.awt.Font;
import javax.swing.UIManager;

/**
 *
 * @author Usuario
 */
public class EstiloStockpilot {
    public static void aplicarTemaEspacial() {
        try {
            // 1. Definición estricta de la paleta de colores STOCKPILOT
            Font fuenteGeneral = new Font("Segoe UI", Font.PLAIN, 13);
            Color fondoEspacial = new Color(10, 17, 31);     // #0a111f
            Color fondoContraste = new Color(7, 11, 22);    // #070b16
            Color textoClaro = new Color(226, 232, 240);     // #e2e8f0
            Color azulBoton = new Color(30, 41, 59);         // #1e293b
            Color azulBotonHover = new Color(51, 65, 85);    // #334155
            Color bordeCajas = new Color(45, 55, 72);        // #2d3748

            // 2. Configuración de Fuentes y Contenedores Base
            UIManager.put("defaultFont", fuenteGeneral);
            UIManager.put("Panel.background", fondoEspacial);
            UIManager.put("Component.background", fondoEspacial);
            UIManager.put("Component.foreground", textoClaro);
            UIManager.put("Label.foreground", textoClaro);

            // 3. Pestañas y Barras de Título superiores
            UIManager.put("TitlePane.background", fondoContraste);
            UIManager.put("TabbedPane.background", fondoContraste);
            
            // 4. Cajas de Texto (JTextField, JPasswordField...)
            UIManager.put("TextField.background", fondoContraste);
            UIManager.put("TextField.foreground", textoClaro);
            UIManager.put("PasswordField.background", fondoContraste);
            UIManager.put("PasswordField.foreground", textoClaro);
            UIManager.put("Component.borderColor", bordeCajas);
            UIManager.put("Component.focusColor", new Color(12, 194, 240, 120)); // Azul cian para el foco

            // 5. 🎯 LA SOLUCIÓN EXPLICITA PARA BOTONES EN FLATLAF 🎯
            // Forzamos las propiedades específicas que FlatDarkLaf requiere para anular su gris nativo
            UIManager.put("Button.background", azulBoton);
            UIManager.put("Button.foreground", textoClaro);
            UIManager.put("Button.hoverBackground", azulBotonHover);
            UIManager.put("Button.focusedBackground", azulBoton);
            UIManager.put("Button.borderColor", bordeCajas);
            
            UIManager.put("ToggleButton.background", azulBoton);
            UIManager.put("ToggleButton.foreground", textoClaro);
            UIManager.put("ToggleButton.hoverBackground", azulBotonHover);
            
            UIManager.put("Button.arc", 15);
            UIManager.put("Component.arc", 12);
            UIManager.put("CheckBox.arc", 5);
            UIManager.put("ProgressBar.arc", 10);
            UIManager.put("Button.innerFocusWidth", 0);
            UIManager.put("Button.outlineWidth", 1);

            // 6. Arrancamos el motor gráfico recogiendo todo el mapa configurado arriba
            System.setProperty("flatlaf.useNativeWindowDecorations", "true");
            FlatDarkLaf.setup();

        } catch (Exception ex) {
            System.err.println("Error aplicando el motor de estilos EstiloAlmacen: " + ex.getMessage());
        }
    }
    
}
