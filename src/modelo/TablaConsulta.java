package modelo;

import java.util.List;
import javax.swing.table.DefaultTableModel;
import pojos_package.Inventario;
import pojos_package.ArticuloProveedor;

/**
 *
 * @author Usuario
 */
public class TablaConsulta {

    public static DefaultTableModel crear_modelo_de_tabla(List<Inventario> listaInventario) {

        String[] columnas = {"Nº Serie", "Artículo", "Proveedores", "Pasillo", "Estante"};

        DefaultTableModel modelo = new DefaultTableModel(null, columnas) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        if (listaInventario != null) {

            System.out.println("DEBUG TablaConsulta: Iterando sobre " + listaInventario.size() + " elementos.");

            for (Inventario inv : listaInventario) {

                Object[] fila = new Object[5];

                // Nº SERIE
                fila[0] = inv.getNumeroSerie();

                // ARTÍCULO + PROVEEDORES (ERP N:M)
                if (inv.getArticulo() != null) {

                    fila[1] = inv.getArticulo().getNombreArt();

                    String proveedoresTexto = "Sin Proveedor";

                    if (inv.getArticulo().getArticuloProveedors() != null &&
                        !inv.getArticulo().getArticuloProveedors().isEmpty()) {

                        StringBuilder sb = new StringBuilder();

                        for (Object obj : inv.getArticulo().getArticuloProveedors()) {

                            ArticuloProveedor ap = (ArticuloProveedor) obj;

                            if (ap.getProveedor() != null) {

                                if (sb.length() > 0) {
                                    sb.append(", ");
                                }

                                sb.append(ap.getProveedor().getNombreProov());
                            }
                        }

                        proveedoresTexto = sb.toString();
                    }

                    fila[2] = proveedoresTexto;

                } else {
                    fila[1] = "Articulo Nulo";
                    fila[2] = "-";
                }

                // UBICACIÓN
                if (inv.getUbicacion() != null) {
                    fila[3] = inv.getUbicacion().getPasillo();
                    fila[4] = inv.getUbicacion().getEstante();
                } else {
                    fila[3] = "Ubicación Nula";
                    fila[4] = "-";
                }

                modelo.addRow(fila);
            }
        }

        return modelo;
    }
}