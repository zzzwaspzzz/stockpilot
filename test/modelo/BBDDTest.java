/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import data_transfer_object.InventarioDTO;
import data_transfer_object.VentaAlbaranDTO;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import pojos_package.Articulo;
import pojos_package.Cliente;
import pojos_package.Inventario;
import pojos_package.Proveedor;
import pojos_package.Provincia;
import pojos_package.Ubicacion;
import pojos_package.Venta;

/**
 *
 * @author Usuario
 */
public class BBDDTest {
    
    public BBDDTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of listar_articulos method, of class BBDD.
     */
    @Test
    public void testListar_articulos() {
        System.out.println("listar_articulos");
        BBDD instance = new BBDD();
        List<Articulo> expResult = null;
        List<Articulo> result = instance.listar_articulos();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of obtener_proveedores method, of class BBDD.
     */
    @Test
    public void testObtener_proveedores() {
        System.out.println("obtener_proveedores");
        BBDD instance = new BBDD();
        List<Proveedor> expResult = null;
        List<Proveedor> result = instance.obtener_proveedores();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of insertar_articulo method, of class BBDD.
     */
    @Test
    public void testInsertar_articulo() {
        System.out.println("insertar_articulo");
        Articulo art = null;
        BBDD instance = new BBDD();
        boolean expResult = false;
        boolean result = instance.insertar_articulo(art);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of insertar_ubicacion method, of class BBDD.
     */
    @Test
    public void testInsertar_ubicacion() {
        System.out.println("insertar_ubicacion");
        Ubicacion ubi = null;
        BBDD instance = new BBDD();
        Ubicacion expResult = null;
        Ubicacion result = instance.insertar_ubicacion(ubi);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of obtener_articulo_por_nombre method, of class BBDD.
     */
    @Test
    public void testObtener_articulo_por_nombre() {
        System.out.println("obtener_articulo_por_nombre");
        String nombre_articulo = "";
        BBDD instance = new BBDD();
        Articulo expResult = null;
        Articulo result = instance.obtener_articulo_por_nombre(nombre_articulo);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of registrar_en_inventario method, of class BBDD.
     */
    @Test
    public void testRegistrar_en_inventario() {
        System.out.println("registrar_en_inventario");
        Inventario invent = null;
        BBDD instance = new BBDD();
        boolean expResult = false;
        boolean result = instance.registrar_en_inventario(invent);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of llenar_tabla_consulta method, of class BBDD.
     */
    @Test
    public void testLlenar_tabla_consulta() {
        System.out.println("llenar_tabla_consulta");
        BBDD instance = new BBDD();
        List<Inventario> expResult = null;
        List<Inventario> result = instance.llenar_tabla_consulta();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of obtener_item_inventario method, of class BBDD.
     */
    @Test
    public void testObtener_item_inventario() {
        System.out.println("obtener_item_inventario");
        String num_serie = "";
        BBDD instance = new BBDD();
        Inventario expResult = null;
        Inventario result = instance.obtener_item_inventario(num_serie);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of registrar_cliente method, of class BBDD.
     */
    @Test
    public void testRegistrar_cliente() {
        System.out.println("registrar_cliente");
        Cliente cl = null;
        BBDD instance = new BBDD();
        boolean expResult = false;
        boolean result = instance.registrar_cliente(cl);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of listar_clientes method, of class BBDD.
     */
    @Test
    public void testListar_clientes() {
        System.out.println("listar_clientes");
        BBDD instance = new BBDD();
        List<Cliente> expResult = null;
        List<Cliente> result = instance.listar_clientes();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of obtener_item_inventario_dto method, of class BBDD.
     */
    @Test
    public void testObtener_item_inventario_dto() {
        System.out.println("obtener_item_inventario_dto");
        String numSerie = "";
        BBDD instance = new BBDD();
        InventarioDTO expResult = null;
        InventarioDTO result = instance.obtener_item_inventario_dto(numSerie);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of proveedor_num_serie method, of class BBDD.
     */
    @Test
    public void testProveedor_num_serie() {
        System.out.println("proveedor_num_serie");
        String num_serie = "";
        BBDD instance = new BBDD();
        String expResult = "";
        String result = instance.proveedor_num_serie(num_serie);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of listar_proveedores method, of class BBDD.
     */
    @Test
    public void testListar_proveedores() {
        System.out.println("listar_proveedores");
        BBDD instance = new BBDD();
        List<Proveedor> expResult = null;
        List<Proveedor> result = instance.listar_proveedores();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of obtener_numeros_serie_disponibles method, of class BBDD.
     */
    @Test
    public void testObtener_numeros_serie_disponibles() {
        System.out.println("obtener_numeros_serie_disponibles");
        BBDD instance = new BBDD();
        List<String> expResult = null;
        List<String> result = instance.obtener_numeros_serie_disponibles();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of buscar_articulo_por_numserie method, of class BBDD.
     */
    @Test
    public void testBuscar_articulo_por_numserie() {
        System.out.println("buscar_articulo_por_numserie");
        String numserie = "";
        BBDD instance = new BBDD();
        VentaAlbaranDTO expResult = null;
        VentaAlbaranDTO result = instance.buscar_articulo_por_numserie(numserie);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of obtener_clientes_activos method, of class BBDD.
     */
    @Test
    public void testObtener_clientes_activos() {
        System.out.println("obtener_clientes_activos");
        BBDD instance = new BBDD();
        List<Cliente> expResult = null;
        List<Cliente> result = instance.obtener_clientes_activos();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of registrar_transaccion method, of class BBDD.
     */
    @Test
    public void testRegistrar_transaccion() {
        System.out.println("registrar_transaccion");
        int idCliente = 0;
        double totalVenta = 0.0;
        ArrayList<String> lista_series = null;
        BBDD instance = new BBDD();
        boolean expResult = false;
        boolean result = instance.registrar_transaccion(idCliente, totalVenta, lista_series);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of listar_provincias method, of class BBDD.
     */
    @Test
    public void testListar_provincias() {
        System.out.println("listar_provincias");
        BBDD instance = new BBDD();
        List<Provincia> expResult = null;
        List<Provincia> result = instance.listar_provincias();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of obtener_cliente_por_dni method, of class BBDD.
     */
    @Test
    public void testObtener_cliente_por_dni() {
        System.out.println("obtener_cliente_por_dni");
        String dni = "";
        BBDD instance = new BBDD();
        Cliente expResult = null;
        Cliente result = instance.obtener_cliente_por_dni(dni);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of actualizar_cliente method, of class BBDD.
     */
    @Test
    public void testActualizar_cliente() {
        System.out.println("actualizar_cliente");
        Cliente c = null;
        BBDD instance = new BBDD();
        boolean expResult = false;
        boolean result = instance.actualizar_cliente(c);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of obtener_compras_por_cliente method, of class BBDD.
     */
    @Test
    public void testObtener_compras_por_cliente() {
        System.out.println("obtener_compras_por_cliente");
        String dniCliente = "";
        BBDD instance = new BBDD();
        List<Venta> expResult = null;
        List<Venta> result = instance.obtener_compras_por_cliente(dniCliente);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of eliminar_cliente method, of class BBDD.
     */
    @Test
    public void testEliminar_cliente() {
        System.out.println("eliminar_cliente");
        String dni_eliminar = "";
        BBDD instance = new BBDD();
        boolean expResult = false;
        boolean result = instance.eliminar_cliente(dni_eliminar);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of listar_clientes_inactivos method, of class BBDD.
     */
    @Test
    public void testListar_clientes_inactivos() {
        System.out.println("listar_clientes_inactivos");
        BBDD instance = new BBDD();
        List<Cliente> expResult = null;
        List<Cliente> result = instance.listar_clientes_inactivos();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of activar_cliente method, of class BBDD.
     */
    @Test
    public void testActivar_cliente() {
        System.out.println("activar_cliente");
        String dni_activar = "";
        BBDD instance = new BBDD();
        boolean expResult = false;
        boolean result = instance.activar_cliente(dni_activar);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of obtener_stock_por_pasillo method, of class BBDD.
     */
    @Test
    public void testObtenerStockPorPasillo() {
        BBDD instance = new BBDD();
        System.out.println("====== [TEST JUNIT] Iniciando prueba de Stock por Pasillo ======");
        
        // 1. EJECUCIÓN: Llamamos directamente a tu método de Hibernate
        List<Object[]> registros = instance.obtener_stock_por_pasillo();
        
        // 2. VERIFICACIONES DE SEGURIDAD (Las aserciones de JUnit)
        
        // Verificación A: Comprobamos que la lista no venga nula (fallo de mapeo o conexión)
        assertNotNull("La consulta devolvió un objeto nulo. Revisa la conexión o Hibernate.", registros);
        
        // Verificación B: Comprobamos si la base de datos tiene datos dentro
        System.out.println("-> Número de pasillos recuperados: " + registros.size());
        assertTrue("La lista está vacía. Asegúrate de tener datos en la tabla Inventario.", registros.size() > 0);
        
        // 3. INSPECCIÓN DE COMPOSICIÓN (Para entender qué devuelve tu HQL)
        for (Object[] fila : registros) {
            Object pasillo = fila[0];
            Object cantidad = fila[1];
            
            System.out.println("   [Pasillo Detectado] ID: " + pasillo + " | Unidades: " + cantidad);
            
            // Verificamos que las posiciones del array contengan los tipos de datos correctos
            assertNotNull("El ID del pasillo es nulo", pasillo);
            assertNotNull("La cantidad de stock es nula", cantidad);
        }
        
        System.out.println("====== [TEST JUNIT] ¡Prueba superada con éxito (Barra Verde)! ======");
    }

    /**
     * Test of obtener_ingresos_mensuales_2026 method, of class BBDD.
     */
    @Test
    public void testObtener_ingresos_mensuales_2026() {
        System.out.println("obtener_ingresos_mensuales_2026");
        BBDD instance = new BBDD();
        List expResult = null;
        List result = instance.obtener_ingresos_mensuales_2026();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of obtener_estado_global_inventario method, of class BBDD.
     */
    @Test
    public void testObtener_estado_global_inventario() {
        System.out.println("obtener_estado_global_inventario");
        BBDD instance = new BBDD();
        List expResult = null;
        List result = instance.obtener_estado_global_inventario();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of obtener_top_productos_mas_vendidos method, of class BBDD.
     */
    @Test
    public void testObtener_top_productos_mas_vendidos() {
        System.out.println("obtener_top_productos_mas_vendidos");
        BBDD instance = new BBDD();
        List expResult = null;
        List result = instance.obtener_top_productos_mas_vendidos();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
