package data_transfer_object;

public class InventarioDTO {

    private String numeroSerie;
    private String nombreArticulo;
    private String ubicacion;
    private String estado;
    private String fechaEntrada;

    public InventarioDTO(String numeroSerie, String nombreArticulo, String ubicacion, String estado, String fechaEntrada) {
        this.numeroSerie = numeroSerie;
        this.nombreArticulo = nombreArticulo;
        this.ubicacion = ubicacion;
        this.estado = estado;
        this.fechaEntrada = fechaEntrada;
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

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFechaEntrada() {
        return fechaEntrada;
    }

    public void setFechaEntrada(String fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    
}