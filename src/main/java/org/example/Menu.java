package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

public class Menu {

    private Connection conexion;

    public Menu() {
        // Inicializar la conexión a la base de datos aquí
        inicializarConexion();
    }

    public void mostrarMenu() {
        System.out.println("Seleccione una opción:");
        System.out.println("1. Subinforme con los datos del centro");
        System.out.println("2. Subinforme con médicos especialistas");
        System.out.println("3. Subinforme gráfico circular de causas de urgencias");
        System.out.println("4. Salir");

        int opcion = obtenerOpcionUsuario();

        switch (opcion) {
            case 1:
                generarInformeCentro();
                break;
            case 2:
                generarInformeMedicosEspecialistas();
                break;
            case 3:
                generarInformeCausasUrgencias();
                break;
            case 4:
                cerrarConexion();
                System.out.println("Saliendo...");
                System.exit(0);
            default:
                System.out.println("Opción no válida. Intente de nuevo.");
                mostrarMenu();
                break;
        }
    }

    private int obtenerOpcionUsuario() {
        Scanner scanner = new Scanner(System.in);
        int opcion = 0;

        try {
            opcion = scanner.nextInt();
        } catch (java.util.InputMismatchException e) {
            System.out.println("Opción no válida. Intente de nuevo.");
            opcion = obtenerOpcionUsuario();
        }

        return opcion;
    }

    private void inicializarConexion() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = Conexion.getUrl();
            String usuario = Conexion.getUsuario();
            String contraseña = Conexion.getContraseña();
            conexion = DriverManager.getConnection(url, usuario, contraseña);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void cerrarConexion() {
        if (conexion != null) {
            try {
                conexion.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void generarInformeCentro() {
        try {
            // Pedir la ID al usuario
            Scanner scanner = new Scanner(System.in);
            System.out.print("Ingrese la ID del centro de salud: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            // Compilar el informe principal
            JasperReport jasperReport = JasperCompileManager.compileReport("src/main/resources/sub1.jrxml");

            // Establecer los parámetros para el informe principal
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("IDParametro", id);
            parametros.put("Conexion", conexion);  // Pasar la conexión como parámetro

            // Llenar el informe principal
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, conexion);


            // Ver el informe principal
            JasperViewer.viewReport(jasperPrint);
        } catch (java.util.InputMismatchException e) {
            System.out.println("Entrada no válida. Debe ingresar un número.");
        } catch (JRException e) {
            e.printStackTrace();
        }
    }


    private void generarInformeMedicosEspecialistas() {
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport("src/main/resources/(posible nombre)reporteMedicosEspecialistas.jrxml");
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("Conexion", conexion);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, conexion);
            JasperViewer.viewReport(jasperPrint);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    private void generarInformeCausasUrgencias() {
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport("src/main/resources/(posible nombre)reporteCausasUrgencias.jrxml");
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("Conexion", conexion);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, conexion);
            JasperViewer.viewReport(jasperPrint);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }
}
