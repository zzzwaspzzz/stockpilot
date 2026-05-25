/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pojos_package;

/**
 *
 * @author Usuario
 */
public class Provincia implements java.io.Serializable {

    private Integer idProvincia;
    private String nombreProvincia;

    // Constructor vacío obligatorio para Hibernate
    public Provincia() {
    }

    public Provincia(String nombreProvincia) {
        this.nombreProvincia = nombreProvincia;
    }
   
    // Getters y Setters
    public Integer getIdProvincia() {
        return this.idProvincia;
    }
    
    public void setIdProvincia(Integer idProvincia) {
        this.idProvincia = idProvincia;
    }

    public String getNombreProvincia() {
        return this.nombreProvincia;
    }
    
    public void setNombreProvincia(String nombreProvincia) {
        this.nombreProvincia = nombreProvincia;
    }
}