package org.example;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Menu {

    private Connection conexion;

    public Menu() {
        // Inicializar la conexión a la base de datos aquí
        inicializarConexion();
    }

    public void mostrarMenu() {
        System.out.println("Seleccione una opción:");
        System.out.println("1. Lanzar informe");
        System.out.println("2. Salir");

        int opcion = obtenerOpcionUsuario();

        switch (opcion) {
            case 1:
                generarInforme();
                break;
            case 2:
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

    private void generarInforme() {
        try {
            // Compilar el informe
            JasperReport jasperReport = JasperCompileManager.compileReport("src/main/resources/1.jrxml");

            // Establecer los parámetros para el informe
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("Conexion", conexion);

            // Llenar el informe
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, conexion);

            // Mostrar el informe en una ventana de visualización
            JasperViewer.viewReport(jasperPrint);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }
}
