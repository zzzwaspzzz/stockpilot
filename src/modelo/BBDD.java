/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import controlador.HibernateUtil;
import data_transfer_object.InventarioDTO;
import data_transfer_object.VentaAlbaranDTO;
import java.util.List;
import java.util.Set;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import pojos_package.Articulo;
import pojos_package.ArticuloProveedor;
import pojos_package.Cliente;
import pojos_package.Inventario;
import pojos_package.Proveedor;
import pojos_package.Ubicacion;

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
        "JOIN FETCH inv.ubicacion";        
        Query query = sesion.createQuery(hql);
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
            String hql = "SELECT new data_transfer_object.VentaAlbaranDTO(i.numeroSerie, a.nombre, u.pasillo, u.estante) " +
                     "FROM Inventario i " +
                     "JOIN i.articulo a " +
                     "JOIN i.ubicacion u " +
                     "WHERE i.numeroSerie = :serie AND i.estado = 'disponible'";
        }catch(HibernateException he){
            manejaExcepcion(he);
        }finally{
            sesion.close();
        }
        return ventaDTO;
    }
    
}
