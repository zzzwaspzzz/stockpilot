/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data_transfer_object;

/**
 *
 * @author Usuario
 */
public class VentaAlbaranDTO {
    private String numeroSerie;
    private String nombreArticulo;
    private int pasillo;
    private int estante;

    public VentaAlbaranDTO(String numeroSerie, String nombreArticulo, int pasillo, int estante) {
        this.numeroSerie = numeroSerie;
        this.nombreArticulo = nombreArticulo;
        this.pasillo = pasillo;
        this.estante = estante;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public String getNombreArticulo() {
        return nombreArticulo;
    }

    public void setNombreArticulo(String nombreArticulo) {
        this.nombreArticulo = nombreArticulo;
    }

    public int getPasillo() {
        return pasillo;
    }

    public void setPasillo(int pasillo) {
        this.pasillo = pasillo;
    }

    public int getEstante() {
        return estante;
    }

    public void setEstante(int estante) {
        this.estante = estante;
    }    
}

