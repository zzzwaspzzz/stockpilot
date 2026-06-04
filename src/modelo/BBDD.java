/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import controlador.HibernateUtil;
import data_transfer_object.InventarioDTO;
import data_transfer_object.VentaAlbaranDTO;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import pojos_package.Articulo;
import pojos_package.Cliente;
import pojos_package.Estado_inventario;
import pojos_package.Inventario;
import pojos_package.Lineaventa;
import pojos_package.Proveedor;
import pojos_package.Provincia;
import pojos_package.Ubicacion;
import pojos_package.Usuario;
import pojos_package.Venta;

/**
 *
 * @author Usuario
 */
public class BBDD {

    
    
    private Session sesion;
    private Transaction tx;
    
    
    private void iniciaOperacion() throws HibernateException {
        sesion = HibernateUtil.getSessionFactory().openSession();
        tx = sesion.beginTransaction();
    }
    
    private void manejaExcepcion(HibernateException he) {
    try {
        if (tx != null) {
            tx.rollback();
        }
    } catch (Exception ignored) {        
    }

    System.err.println("=== ERROR BBDD ===");
    he.printStackTrace();

    if (he.getCause() != null) {
        System.err.println("CAUSA: " + he.getCause());
    }

    if (he.getMessage() != null) {
        System.err.println("MENSAJE: " + he.getMessage());
    }
}
    
    
    public List<Articulo> listar_articulos() {
    List<Articulo> articulos = null;
    Query query;

    try {
        iniciaOperacion();
        String hql = "FROM Articulo a ORDER BY a.nombreArt ASC";
        query = sesion.createQuery(hql);
        articulos = query.list();
    } catch (HibernateException he) {
        he.printStackTrace();
    } finally {
        sesion.close();
    }
    return articulos;
    }
    
    
    public List<Proveedor> obtener_proveedores(){
        List<Proveedor> proveedores = null;
        Query query;
        
        try{
            iniciaOperacion();
            String hql = "FROM Proveedor p ORDER BY p.nombreProov ASC";
            query = sesion.createQuery(hql);
            proveedores = query.list();
        }catch(HibernateException he){
            he.printStackTrace();
        }finally{
            sesion.close();
        }
        return proveedores;
    }
    

    public boolean insertar_articulo(Articulo art) {
        boolean exito = false;
        try{
            iniciaOperacion();
            sesion.save(art);
            tx.commit();
            exito = true;
        }catch(HibernateException he){
            manejaExcepcion(he);
            exito = false;
        }finally{
            sesion.close();
        }
        return exito;
    }   

    public Ubicacion insertar_ubicacion(Ubicacion ubi) {        
        try{
            iniciaOperacion();
            sesion.save(ubi);
            tx.commit();
        }catch(HibernateException he){
            manejaExcepcion(he);
        }finally{
            sesion.close();
        }        
        return ubi;
    }

    public Articulo obtener_articulo_por_nombre(String nombre_articulo) {
        Articulo articulo = null;
        Query query;
        String hql;
        try{
            iniciaOperacion();
            hql = "FROM Articulo a WHERE a.nombreArt Like :nombre ";
            query = sesion.createQuery(hql).setParameter("nombre", nombre_articulo);
            articulo = (Articulo) query.uniqueResult();
        }catch(HibernateException he){
            manejaExcepcion(he);
        }finally{
            sesion.close();
        }
        return articulo;
    }

    public boolean registrar_en_inventario(Inventario invent) {
        boolean exito = false;
        try{
            iniciaOperacion();
            sesion.save(invent);
            tx.commit();
            exito = true;            
        }catch(HibernateException he){
            manejaExcepcion(he);
            exito = false;            
        }finally{
            sesion.close();
        }
        return exito;
    }
    
    
    public List<Inventario> llenar_tabla_consulta() {
    List<Inventario> lista_inventario = null;
    Session sesion = null;
    try {       
        sesion = HibernateUtil.getSessionFactory().openSession();
        String hql =
        "FROM Inventario inv " +
        "JOIN FETCH inv.articulo art " +
        "JOIN FETCH art.articuloProveedors ap " +
        "JOIN FETCH ap.proveedor " +
        "JOIN FETCH inv.ubicacion" +
        " WHERE inv.estado = :disponible";        
        Query query = sesion.createQuery(hql).setParameter("disponible", Estado_inventario.disponible);
        lista_inventario = query.list();        
    } catch (HibernateException he) {
        manejaExcepcion(he);
        he.printStackTrace();
        lista_inventario = null;
    } finally {        
        if (sesion != null) {
            sesion.close();
        }
    }
    return lista_inventario;
    }
    
    public Inventario obtener_item_inventario(String num_serie) {
        Inventario inv = null;
        Query query;
        try{
            iniciaOperacion();
            String hql = "FROM Inventario i\n" +
                "JOIN FETCH i.articulo art\n" +
                "LEFT JOIN FETCH art.articuloProveedors\n" +
                "JOIN FETCH i.ubicacion\n" +
                "WHERE i.numeroSerie = :num_serie";
            query = sesion.createQuery(hql).setParameter("num_serie", num_serie);
            inv = (Inventario) query.uniqueResult();
            System.out.println("Elemento cargado correctamente");
        }catch(HibernateException he){
            manejaExcepcion(he);
            System.err.println("Problema de carga");
        }finally{
            sesion.close();
        }
        return inv;
    }

    public boolean registrar_cliente(Cliente cl) {
        boolean exito = false;
        
        try{
            iniciaOperacion();
            sesion.save(cl);
            tx.commit();
            exito = true;
        }catch(HibernateException he){
            manejaExcepcion(he);
            exito = false;
        }finally{
            sesion.close();
        }        
        return exito;
    }

    public List<Cliente> listar_clientes() {
        List<Cliente> lista_clientes = null;
        Query query;
        
        try{
            iniciaOperacion();
            String hql = "FROM Cliente c WHERE c.estado = 'activo'";
            query = sesion.createQuery(hql);
            lista_clientes = query.list();
        }catch(HibernateException he){
            manejaExcepcion(he);
            System.err.println("Problema de carga");
        }finally{
            sesion.close();
        }
        return lista_clientes;
    }

    public InventarioDTO obtener_item_inventario_dto(String numSerie) {

    try {
        iniciaOperacion();

        String hql =
            "SELECT new data_transfer_object.InventarioDTO(" +
            "i.numeroSerie, " +
            "a.nombreArt, " +
            "CONCAT(u.pasillo, '-', u.estante), " +
            "cast(i.estado as string), " +
            "cast(i.fechaEntrada as string)) " +
            "FROM Inventario i " +
            "JOIN i.articulo a " +
            "JOIN i.ubicacion u " +
            "WHERE i.numeroSerie = :num";

        Query query = sesion.createQuery(hql);
        query.setParameter("num", numSerie);

        return (InventarioDTO) query.uniqueResult();

    } catch (HibernateException he) {
        he.printStackTrace();
        return null;

    } finally {
        sesion.close();
    }
}

    public String proveedor_num_serie(String num_serie) {
        String nombre_proveedor = null;
        Query query;
        try{
            iniciaOperacion();
            String hql = "SELECT i.proveedor.nombreProov FROM Inventario i WHERE i.numeroSerie = :num";
            query = sesion.createQuery(hql).setParameter("num", num_serie);
            nombre_proveedor = (String) query.uniqueResult();
        }catch(HibernateException he){
            manejaExcepcion(he);
        }finally{
            sesion.close();
        }
        return nombre_proveedor;
    }

    public List<Proveedor> listar_proveedores() {
        List<Proveedor> lista = null;
        Query query;
        try{
            iniciaOperacion();
            String hql = "FROM Proveedor";
            query = sesion.createQuery(hql);
            lista = query.list();
        }catch(HibernateException he){
            manejaExcepcion(he);
        }finally{
            sesion.close();
        }
        return lista;
    }

    public List<String> obtener_numeros_serie_disponibles() {
        List<String> lista_numeros = null;
        Query query;
        try{
            iniciaOperacion();
            String hql = "SELECT i.numeroSerie FROM Inventario i WHERE i.estado = 'disponible'";
            query = sesion.createQuery(hql);
            lista_numeros = query.list();
        }catch(HibernateException he){
            manejaExcepcion(he);
        }finally{
            sesion.close();
        }
        return lista_numeros;        
    }
    
    public VentaAlbaranDTO buscar_articulo_por_numserie(String numserie){
        VentaAlbaranDTO ventaDTO = null;
        Query query;
        try{
            iniciaOperacion();
            String hql = "SELECT new data_transfer_object.VentaAlbaranDTO(i.numeroSerie, a.nombreArt, u.pasillo, u.estante) " +
                     "FROM Inventario i " +
                     "JOIN i.articulo a " +
                     "JOIN i.ubicacion u " +
                     "WHERE i.numeroSerie = :serie AND i.estado = 'disponible'";
            query = sesion.createQuery(hql);
            query.setParameter("serie", numserie);
            ventaDTO = (VentaAlbaranDTO) query.uniqueResult();
        }catch(HibernateException he){
            manejaExcepcion(he);
        }finally{
            sesion.close();
        }
        return ventaDTO;
    }
    
    public List<Cliente> obtener_clientes_activos(){
        List<Cliente> lista_clientes_activos = null;
        Query query;
        
        try{
            iniciaOperacion();
            String hql = "FROM Cliente c WHERE c.estado = 'activo'";
            query = sesion.createQuery(hql);
            lista_clientes_activos = query.list();
        }catch(HibernateException he){
            manejaExcepcion(he);
        }finally{
            sesion.close();
        }
        return lista_clientes_activos;
    }

    public boolean registrar_transaccion(int idCliente, double totalVenta, ArrayList<String> lista_series) {
        boolean exito = false;
        try{
            iniciaOperacion();
            Cliente cl = obtenerYValidarCliente(idCliente);            
            
            Venta nuevaVenta = crearYGuardarVenta(cl, totalVenta);
            
            procesarArticulosVenta(nuevaVenta, lista_series);
            tx.commit();
            exito = true;
            
        }catch(HibernateException he){
            manejaExcepcion(he);
        } catch (Exception ex)
        {
            Logger.getLogger(BBDD.class.getName()).log(Level.SEVERE, null, ex);
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            System.err.println("Error crítico en la transacción: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }finally{
            sesion.close();
        }
        return exito;       
    }
    
    private Cliente obtenerYValidarCliente(int idCliente) throws Exception {
        Cliente cl = (Cliente) sesion.get(Cliente.class, idCliente);
        if (cl == null) {
            throw new Exception("El cliente no existe en la base de datos.");
        }
        return cl;
    }

    private Venta crearYGuardarVenta(Cliente cl, double totalVenta) {
        Venta nuevaVenta = new Venta();
        nuevaVenta.setCliente(cl);
        nuevaVenta.setFechaVenta(new Date());
        nuevaVenta.setTotal(BigDecimal.valueOf(totalVenta));
        nuevaVenta.setEstado("pagada");
        nuevaVenta.setMetodoPago("Efectivo");
        
        sesion.save(nuevaVenta);
        return nuevaVenta;
    }

    private void procesarArticulosVenta(Venta nuevaVenta, ArrayList<String> lista_series) {
        for (String numero_serie : lista_series) {
           
            String hql = "FROM Inventario i WHERE i.numeroSerie = :serie";
            Inventario inv = (Inventario) sesion.createQuery(hql).setParameter("serie", numero_serie).uniqueResult();
            
            if (inv == null) {
                try
                {
                    throw new Exception("El número de serie '" + numero_serie + "' no existe en el inventario.");
                } catch (Exception ex)
                {
                    Logger.getLogger(BBDD.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            // Actualizamos el stock (Dirty Checking)
            inv.setEstado(Estado_inventario.vendido);
            sesion.update(inv);
            
            // Creamos y guardamos la línea de venta asociada
            Lineaventa linea = new Lineaventa(inv, nuevaVenta);
            sesion.save(linea);
        }
    }
    
    public List<Provincia> listar_provincias(){
        List<Provincia> provincias = null;
        Query query;
        try{
            iniciaOperacion();
            String hql = "FROM Provincia p ORDER BY p.nombreProvincia ASC";
            query = sesion.createQuery(hql);
            provincias = query.list();
        }catch(HibernateException he){
            manejaExcepcion(he);
        }finally{
            sesion.close();
        }
        return provincias;
    }

    public Cliente obtener_cliente_por_dni(String dni) {
        Cliente c = null;
        Query query;
        try{
            iniciaOperacion();
            String hql = "FROM Cliente c WHERE c.dniCliente = :dni";
            query = sesion.createQuery(hql).setParameter("dni", dni);
            c = (Cliente) query.uniqueResult();
        }catch(HibernateException he){
            manejaExcepcion(he);
        }finally{
            sesion.close();
        }
        return c;
    }    

    public boolean actualizar_cliente(Cliente c) {
        boolean exito = false;
        Query query;
        try{
            iniciaOperacion();
            sesion.update(c);
            tx.commit();
            exito = true;
        }catch(HibernateException he){
            manejaExcepcion(he);
            tx.rollback();
            exito = false;
        }finally{
            sesion.close();
        }
        return exito;
    }

    public List<Venta> obtener_compras_por_cliente(String dniCliente) {
        List<Venta> lista_ventas = null;
        Query query;
        try{
            iniciaOperacion();
            String hql = "FROM Venta v WHERE v.cliente.dniCliente = :dni";
            query = sesion.createQuery(hql).setParameter("dni", dniCliente);
            lista_ventas = query.list();
        }catch(HibernateException he){
            manejaExcepcion(he);
        }finally{
            sesion.close();
        }
        return lista_ventas;
    }

    public boolean eliminar_cliente(String dni_eliminar) {
        boolean exito = false;
        Query query;
        try{
            iniciaOperacion();
            String hql = "UPDATE Cliente c SET c.estado = :nuevo_estado WHERE c.dniCliente = :dni_eliminar";
            query = sesion.createQuery(hql).setParameter("nuevo_estado", "inactivo").setParameter("dni_eliminar", dni_eliminar);
            int filas_afectadas = query.executeUpdate();
            if(filas_afectadas == 1){
                exito = true;
            }else{
                exito= false;
            }
            tx.commit();
        }catch(HibernateException he){
            manejaExcepcion(he);
            tx.rollback();
            exito= false;
        }finally{
            sesion.close();
        }
        return exito;
    }
    
    public List<Cliente> listar_clientes_inactivos() {
        List<Cliente> lista_clientes = null;
        Query query;
        
        try{
            iniciaOperacion();
            String hql = "FROM Cliente c WHERE c.estado = 'inactivo'";
            query = sesion.createQuery(hql);
            lista_clientes = query.list();
        }catch(HibernateException he){
            manejaExcepcion(he);
            System.err.println("Problema de carga");
        }finally{
            sesion.close();
        }
        return lista_clientes;
    }
    
    public boolean activar_cliente(String dni_activar){
        boolean exito = false;
        Query query;
        try{
            iniciaOperacion();
            String hql = "UPDATE Cliente c SET c.estado = :nuevo_estado WHERE c.dniCliente = :dni_activar";
            query = sesion.createQuery(hql).setParameter("nuevo_estado", "activo").setParameter("dni_activar", dni_activar);
            int filas_afectadas = query.executeUpdate();
            if(filas_afectadas ==1){
                exito = true;
            }
            tx.commit();
        }catch(HibernateException he){
            manejaExcepcion(he);
            tx.rollback();
        }finally{
            sesion.close();
        }
        return exito;
    }
    
    public List<Object[]> obtener_stock_por_pasillo() {
        List<Object[]> resultado = null;
        try {
            iniciaOperacion();            
            String hql = "SELECT u.pasillo, COUNT(i.numeroSerie) " +
                         "FROM Inventario i " +
                         "JOIN i.ubicacion u " +
                         "WHERE i.estado = :estadoFiltro " +
                         "GROUP BY u.pasillo " +
                         "ORDER BY u.pasillo ASC";           
            resultado = sesion.createQuery(hql).setParameter("estadoFiltro", pojos_package.Estado_inventario.disponible).list();
        } catch (HibernateException he) {
            manejaExcepcion(he);
        } finally {
            sesion.close();
        }
        return resultado;
    }
    
    public List<Object[]> obtener_ingresos_mensuales_2026() {
    List<Object[]> lista_ingresos = null;
        try {
            iniciaOperacion();            
            String sql = "SELECT MONTHNAME(fecha_venta), SUM(total) FROM venta " +
                         "WHERE estado = 'pagada' AND YEAR(fecha_venta) = 2026 " +
                         "GROUP BY MONTH(fecha_venta) " +
                         "ORDER BY MONTH(fecha_venta) ASC";            
            lista_ingresos = sesion.createSQLQuery(sql).list();

        } catch (HibernateException he) {
            manejaExcepcion(he);
        } finally {
            sesion.close();
        }
    return lista_ingresos;
}
    
    public List<Object[]> obtener_estado_global_inventario() {
        List<Object[]> estado = null;
            try {
                iniciaOperacion();               
                String hql = "SELECT cast(i.estado as string), COUNT(i.numeroSerie) " +
                             "FROM Inventario i " +
                             "GROUP BY i.estado";
                estado = sesion.createQuery(hql).list();
            } catch (HibernateException he) {
                manejaExcepcion(he);
            } finally {
                sesion.close();
            }
            return estado;
    }
    
    public List<Object[]> obtener_top_productos_mas_vendidos() {
        List<Object[]> resultado = null;
        try {
            iniciaOperacion();            
            String hql = "SELECT lv.inventario.articulo.nombreArt, COUNT(lv.idLineaVenta), SUM(lv.venta.total) " +
                         "FROM Lineaventa lv " +
                         "GROUP BY lv.inventario.articulo.idArticulo, lv.inventario.articulo.nombreArt " +
                         "ORDER BY COUNT(lv.idLineaVenta) DESC";
            resultado = sesion.createQuery(hql).setMaxResults(5).list();
        } catch (HibernateException he) {
            manejaExcepcion(he);
        } finally {
            sesion.close();
        }
        return resultado;
    }

    public List<Object[]> obtener_articulos_bajo_minimos() {
        List<Object[]> alertas = null;
        try {
            iniciaOperacion();
            String hql = "SELECT a.nombreArt, a.stockMinimo, COUNT(i.numeroSerie) " +
                         "FROM Inventario i " +
                         "JOIN i.articulo a " +
                         "WHERE cast(i.estado as string) = 'disponible' " +
                         "GROUP BY a.idArticulo, a.nombreArt, a.stockMinimo " +
                         "HAVING COUNT(i.numeroSerie) <= a.stockMinimo";

            alertas = sesion.createQuery(hql).list();
        } catch (HibernateException he) {
            manejaExcepcion(he);
        } finally {
            sesion.close();
        }
        return alertas;
    }
    
    public boolean comprobar_credenciales_local(String userOrEmail, String pass) {
    boolean valido = false;
    Query query;
        try {
            iniciaOperacion();
            String hql = "FROM Usuario u WHERE u.username = :login OR u.email = :login";
            query = sesion.createQuery(hql);
            query.setParameter("login", userOrEmail);        
            Usuario u = (Usuario) query.uniqueResult();
            if (u != null) {
                if (u.getPasswordHash() != null && u.getPasswordHash().equals(pass)) {
                    valido = true;
                }
            }
        } catch (HibernateException he) {
            manejaExcepcion(he);
        } finally {
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }
    return valido;
    }
    
    public boolean verificar_o_registrar_google(String googleId, String email) {
        boolean verificacion = false;
        Query query;
            try {
                iniciaOperacion();
                String hql = "FROM Usuario u WHERE u.googleId = :googleId";
               query = sesion.createQuery(hql);
                query.setParameter("googleId", googleId);
                Usuario u = (Usuario) query.uniqueResult();

                if (u != null) {
                    verificacion = true; 
                } else {
                    Usuario nuevoUsuario = new Usuario();
                    nuevoUsuario.setEmail(email);
                    nuevoUsuario.setGoogleId(googleId);
                    nuevoUsuario.setUsername(email.split("@")[0]);
                    sesion.save(nuevoUsuario);
                    tx.commit(); 
                    verificacion = true;
                }
            } catch (HibernateException he) {
                if (tx != null){
                    tx.rollback();
                }
                manejaExcepcion(he);
                verificacion = false;
            } finally {            
                sesion.close();            
            }
        return verificacion;
    }
    
    public void insertar_venta(Venta v, List<String> numerosSerie) {
        try {
            iniciaOperacion();
            sesion.save(v);
            for (String num : numerosSerie) {
                String hql = "UPDATE Inventario i SET i.estado = :estadoParam WHERE i.numeroSerie = :num";
                sesion.createQuery(hql)
                      .setParameter("estadoParam", pojos_package.Estado_inventario.vendido) 
                      .setParameter("num", num)
                      .executeUpdate();
            }
            tx.commit();
        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        } finally {        
            sesion.close();            
        }
    }
    
    public Inventario obtener_articulo_por_serie(String numeroSerie) {
        Inventario articulo = null;
        try {
            iniciaOperacion();
            articulo = (Inventario) sesion.get(Inventario.class, numeroSerie);
        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        } finally {
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }
        return articulo;
    }
    
    public void registrar_linea_y_actualizar_inventario(Inventario articulo, Lineaventa linea) {
        try {
            iniciaOperacion();
            sesion.update(articulo);
            sesion.save(linea);        
            tx.commit();
        } catch (HibernateException he) {
            if (tx != null) tx.rollback();
            manejaExcepcion(he);
            throw he;
        } finally {
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }
    }
    
    public List<Venta> obtener_todos_los_albaranes() {
        List<Venta> lista = null;
        Query query;
        try {
            iniciaOperacion();
            String hql = "FROM Venta v JOIN FETCH v.cliente ORDER BY v.fechaVenta DESC";
            query = sesion.createQuery(hql);
            lista = query.list();
        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        } finally {          
            sesion.close();           
        }
        return lista;
    }
    
    public List<Lineaventa> obtener_detalles_albaran(int idVenta) {
        List<Lineaventa> lista = null;
        Query query;
        try {
            iniciaOperacion(); 
            sesion.clear();
            String hql = "FROM Lineaventa lv "
                   + "JOIN FETCH lv.venta v "
                   + "LEFT JOIN FETCH v.cliente "
                   + "JOIN FETCH lv.inventario i "
                   + "JOIN FETCH i.articulo "
                   + "WHERE v.idVenta = :idVenta";
           query = sesion.createQuery(hql).setParameter("idVenta", idVenta);
           lista = query.list();
        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        } finally {           
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }    
        return lista;
    }
    
    public void actualizarEstadoInventario(String numeroSerie, pojos_package.Estado_inventario nuevoEstado) {
    Query query;
        try {
            iniciaOperacion();
            String hql = "UPDATE Inventario i SET i.estado = :estadoParam WHERE i.numeroSerie = :numSerie";        
            query = sesion.createQuery(hql);
            query.setParameter("estadoParam", nuevoEstado);
            query.setParameter("numSerie", numeroSerie);        
            query.executeUpdate();
            tx.commit();
        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        } finally {       
            sesion.close();      
        }
}
    
}
