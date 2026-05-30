/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.componentes.graficas;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Usuario
 */
public class BlankPlotChart extends JPanel{
    private final int leftSpacing = 30;
    private final int bottomSpacing = 30;

    public BlankPlotChart() {
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(5, 5, 5, 5));
    }

    public void renderGraphics(Graphics2D g2, Rectangle2D rectangle) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        // Calculamos el espacio real para dibujar restando los bordes
        Insets insets = getInsets();
        double width = getWidth() - (insets.left + insets.right + leftSpacing);
        double height = getHeight() - (insets.top + insets.bottom + bottomSpacing);
        
        double x = insets.left + leftSpacing;
        double y = insets.top;

        // Dibujamos una cuadrícula base sutil (Gris claro)
        g2.setColor(new Color(230, 230, 230));
        int totalLineasHorizontal = 5;
        double espacioFila = height / totalLineasHorizontal;
        
        for (int i = 0; i <= totalLineasHorizontal; i++) {
            double ly = y + height - (espacioFila * i);
            g2.draw(new Line2D.Double(x, ly, x + width, ly));
            
            // Renderizamos los números de escala del eje Y de forma genérica de momento
            g2.setColor(new Color(150, 150, 150));
            FontMetrics fm = g2.getFontMetrics();
            String scaleText = String.valueOf(i * 20); // Escala básica temporal
            Rectangle2D r2 = fm.getStringBounds(scaleText, g2);
            double tx = x - r2.getWidth() - 5;
            double ty = ly + r2.getHeight() / 2 - fm.getDescent();
            g2.drawString(scaleText, (float) tx, (float) ty);
            g2.setColor(new Color(230, 230, 230));
        }
    }
}
