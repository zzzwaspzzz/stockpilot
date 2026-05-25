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
        // evita segundo error
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
            String hql = "FROM Cliente";
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
    
    private List<Provincia> obtener_provincias(){
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
    
}
