/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.componentes.graficas;

import javax.swing.JPanel;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;
import java.util.List;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTargetAdapter;

/**
 *
 * @author Usuario
 */
public class CurveLineChart extends JPanel{
    private final List<ModelChart> data = new ArrayList<>();
    private final List<String> legends = new ArrayList<>();
    private final List<Color> colors = new ArrayList<>();
    
    private final BlankPlotChart blankPlotChart;
    private final Animator animator;
    private float animacionProgreso = 0f;
    private String title = "Gráfica de Rendimiento";

    public CurveLineChart() {
        setLayout(new java.awt.BorderLayout());
        blankPlotChart = new BlankPlotChart();
        add(blankPlotChart, java.awt.BorderLayout.CENTER);
        
        
        animator = new Animator(800, new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                animacionProgreso = fraction;
                repaint();
            }
        });
        animator.setResolution(0);
        animator.setAcceleration(0.5f);
        animator.setDeceleration(0.5f);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void addLegend(String name, Color color) {
        legends.add(name);
        colors.add(color);
    }

    public void addData(ModelChart model) {
        data.add(model);
    }

    public void clear() {
        data.clear();
        animacionProgreso = 0f;
        repaint();
    }

    public void start() {
        if (animator.isRunning()) {
            animator.stop();
        }
        animator.start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (!data.isEmpty()) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);           
            
            renderLineasYDegradados(g2);
        }
    }

    private void renderLineasYDegradados(Graphics2D g2) {
        int width = blankPlotChart.getWidth() - 30;
        int height = blankPlotChart.getHeight() - 30;
        int xStart = blankPlotChart.getX() + 30;
        int yStart = blankPlotChart.getY();

        double espacioX = (double) width / (data.size() - 1);
        double maxValor = getValorMaximoDatos();

        
        int totalSeries = colors.size();
        for (int serie = 0; serie < totalSeries; serie++) {
            GeneralPath path = new GeneralPath();
            boolean primerPunto = true;
            
            double prevX = 0, prevY = 0;

            for (int i = 0; i < data.size(); i++) {
                double vx = xStart + (i * espacioX);
                
                double valorAnimado = data.get(i).getValues()[serie] * animacionProgreso;
                double vy = yStart + height - (valorAnimado / maxValor * height);

                if (primerPunto) {
                    path.moveTo(vx, vy);
                    primerPunto = false;
                } else {                    
                    double ctrlX1 = prevX + (espacioX / 2);
                    double ctrlY1 = prevY;
                    double ctrlX2 = vx - (espacioX / 2);
                    double ctrlY2 = vy;
                    path.curveTo(ctrlX1, ctrlY1, ctrlX2, ctrlY2, vx, vy);
                }
                prevX = vx;
                prevY = vy;
            }

            
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.15f)); // Opacidad sutil
            GeneralPath degeterPath = (GeneralPath) path.clone();
            degeterPath.lineTo(prevX, yStart + height);
            degeterPath.lineTo(xStart, yStart + height);
            degeterPath.closePath();
            
            g2.setPaint(new GradientPaint(xStart, yStart, colors.get(serie), xStart, yStart + height, new Color(0,0,0,0)));
            g2.fill(degeterPath);

            
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f)); // Opacidad completa
            g2.setStroke(new java.awt.BasicStroke(3f, java.awt.BasicStroke.CAP_ROUND, java.awt.BasicStroke.JOIN_ROUND));
            g2.setColor(colors.get(serie));
            g2.draw(path);
        }
    }

    private double getValorMaximoDatos() {
        double max = 0;
        for (ModelChart m : data) {
            if (m.getMaxValues() > max) {
                max = m.getMaxValues();
            }
        }
        return max == 0 ? 100 : max;
    }
}
