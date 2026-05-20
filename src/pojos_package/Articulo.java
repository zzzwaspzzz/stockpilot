package pojos_package;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * Articulo generado por Hibernate Tools
 */
public class Articulo implements java.io.Serializable {

    private Integer idArticulo;
    private String nombreArt;
    private String descripcion;
    private BigDecimal precioVentaBase;
    private Integer stockMinimo;
    private Boolean activo;

    // 🔗 RELACIÓN N:M CORRECTA
    private Set articuloProveedors = new HashSet(0);

    // 🔗 INVENTARIO (OK mantenerlo)
    private Set inventarios = new HashSet(0);

    public Articulo() {
    }

    public Articulo(String nombreArt) {
        this.nombreArt = nombreArt;
    }

    public Articulo(
            String nombreArt,
            String descripcion,
            BigDecimal precioVentaBase,
            Integer stockMinimo,
            Boolean activo,
            Set articuloProveedors,
            Set inventarios) {

        this.nombreArt = nombreArt;
        this.descripcion = descripcion;
        this.precioVentaBase = precioVentaBase;
        this.stockMinimo = stockMinimo;
        this.activo = activo;
        this.articuloProveedors = articuloProveedors;
        this.inventarios = inventarios;
    }

    public Integer getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(Integer idArticulo) {
        this.idArticulo = idArticulo;
    }

    public String getNombreArt() {
        return nombreArt;
    }

    public void setNombreArt(String nombreArt) {
        this.nombreArt = nombreArt;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPrecioVentaBase() {
        return precioVentaBase;
    }

    public void setPrecioVentaBase(BigDecimal precioVentaBase) {
        this.precioVentaBase = precioVentaBase;
    }

    public Integer getStockMinimo() {
        return stockMinimo;
    }

    public void setStockMinimo(Integer stockMinimo) {
        this.stockMinimo = stockMinimo;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    // 🔥 RELACIÓN N:M (ERP CORRECTO)
    public Set getArticuloProveedors() {
        return articuloProveedors;
    }

    public void setArticuloProveedors(Set articuloProveedors) {
        this.articuloProveedors = articuloProveedors;
    }

    public Set getInventarios() {
        return inventarios;
    }

    public void setInventarios(Set inventarios) {
        this.inventarios = inventarios;
    }
    
    
    @Override
    public String toString() {
        return nombreArt;
    }
}