/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista_componentes_graficas;

/**
 *
 * @author Usuario
 */
public class ModelChart {
    private String label;
    private double values[];

    public ModelChart(String label, double[] values) {
        this.label = label;
        this.values = values;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public double[] getValues() {
        return values;
    }

    public void setValues(double[] values) {
        this.values = values;
    }
    
    public double getMaxValues() {
        double max = 0;
        for (double v : values) {
            if (v > max) {
                max = v;
            }
        }
        return max;
    }
}
