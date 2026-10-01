// vista/MenuPrincipal.java
package vista;

import modelo.entidades.Usuario;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPrincipal extends JFrame {
    private Usuario usuarioActual;
    
    // Componentes
    private JMenuBar menuBar;
    private JMenu menuAsistencia;
    private JMenu menuPlanilla;
    private JMenu menuReportes;
    private JMenu menuMantenimiento;
    private JMenu menuSistema;
    
    private JMenuItem itemRegistrarAsistencia;
    private JMenuItem itemConsultarAsistencia;
    private JMenuItem itemGenerarPlanilla;
    private JMenuItem itemConsultarPlanilla;
    private JMenuItem itemReporteAsistencia;
    private JMenuItem itemReporteTardanzas;
    private JMenuItem itemReporteHorasExtras;
    private JMenuItem itemGestionPersonal;
    private JMenuItem itemGestionUsuarios;
    private JMenuItem itemCerrarSesion;
    private JMenuItem itemSalir;
    
    private JPanel panelPrincipal;
    private JPanel panelBienvenida;
    private JPanel panelAccesos;
    private JLabel lblBienvenida;
    private JLabel lblUsuario;
    private JLabel lblFecha;
    
    public MenuPrincipal(Usuario usuario) {
        this.usuarioActual = usuario;
        inicializarComponentes();
        configurarVentana();
        configurarEventos();
    }
    
    private void inicializarComponentes() {
        // Panel principal
        panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(new Color(236, 240, 241));
        
        // Panel superior de bienvenida
        panelBienvenida = new JPanel();
        panelBienvenida.setLayout(new BoxLayout(panelBienvenida, BoxLayout.Y_AXIS));
        panelBienvenida.setBackground(new Color(41, 128, 185));
        panelBienvenida.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        lblBienvenida = new JLabel("INSTITUTO AMERICANO");
        lblBienvenida.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblBienvenida.setForeground(Color.BLACK);
        lblBienvenida.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        lblUsuario = new JLabel("Usuario: " + usuarioActual.getNombreUsuario());
        lblUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblUsuario.setForeground(Color.BLACK);
        lblUsuario.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        lblFecha = new JLabel("Fecha: " + new java.text.SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date()));
        lblFecha.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblFecha.setForeground(Color.BLACK);
        lblFecha.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        panelBienvenida.add(lblBienvenida);
        panelBienvenida.add(Box.createRigidArea(new Dimension(0, 10)));
        panelBienvenida.add(lblUsuario);
        panelBienvenida.add(Box.createRigidArea(new Dimension(0, 5)));
        panelBienvenida.add(lblFecha);
        
        // Panel de accesos rápidos
        panelAccesos = new JPanel(new GridLayout(2, 3, 20, 20));
        panelAccesos.setBackground(new Color(236, 240, 241));
        panelAccesos.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        // Botones de acceso rápido
        agregarBotonAcceso(panelAccesos, "Registrar Asistencia", "👤", new Color(46, 204, 113));
        agregarBotonAcceso(panelAccesos, "Consultar Asistencia", "📋", new Color(52, 152, 219));
        agregarBotonAcceso(panelAccesos, "Generar Planilla", "💰", new Color(155, 89, 182));
        agregarBotonAcceso(panelAccesos, "Reportes", "📊", new Color(230, 126, 34));
        agregarBotonAcceso(panelAccesos, "Gestión Personal", "👥", new Color(26, 188, 156));
        agregarBotonAcceso(panelAccesos, "Configuración", "🔧️", new Color(149, 165, 166));
        
        panelPrincipal.add(panelBienvenida, BorderLayout.NORTH);
        panelPrincipal.add(panelAccesos, BorderLayout.CENTER);
        
        // Crear barra de menú
        crearMenuBar();
    }
    
    private void agregarBotonAcceso(JPanel panel, String texto, String icono, Color color) {
        JButton boton = new JButton("<html><center>" + icono + "<br>" + texto + "</center></html>");
        boton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        boton.setBackground(color);
        boton.setForeground(Color.BLACK);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setPreferredSize(new Dimension(200, 120));
        
        boton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirVentanaSegunBoton(texto);
            }
        });
        
        panel.add(boton);
    }
    
    private void abrirVentanaSegunBoton(String texto) {
        int rol = usuarioActual.getIdRol();
        // Validar acceso según rol antes de abrir
        boolean tieneAcceso = false;

        switch (texto) {
            case "Registrar Asistencia":
            case "Consultar Asistencia":
                // Todos los roles pueden ver asistencia
                tieneAcceso = true;
                break;
            case "Generar Planilla":
            case "Reportes":
                // Solo RRHH (2) y Administrador (1)
                tieneAcceso = (rol == 1 || rol == 2);
                break;
            case "Gestión Personal":
            case "Configuración":
                // Solo Administrador (1)
                tieneAcceso = (rol == 1);
                break;
        }
        if (!tieneAcceso) {
            JOptionPane.showMessageDialog(this,
                    "No tiene permisos para acceder a este módulo.",
                    "Acceso denegado",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Si tiene acceso, abrir la ventana correspondiente
        switch (texto) {
            case "Registrar Asistencia":
                abrirRegistroAsistencia();
                break;
            case "Consultar Asistencia":
                abrirConsultaAsistencia();
                break;
            case "Generar Planilla":
                abrirGenerarPlanilla();
                break;
            case "Reportes":
                abrirReportes();
                break;
            case "Gestión Personal":
                abrirGestionPersonal();
                break;
            case "Configuración":
                JOptionPane.showMessageDialog(this, "Módulo en desarrollo", "Información", JOptionPane.INFORMATION_MESSAGE);
                break;
        }
    }
    
    private void crearMenuBar() {
        menuBar = new JMenuBar();
        
        // Menú Asistencia
        menuAsistencia = new JMenu("Asistencia");
        itemRegistrarAsistencia = new JMenuItem("Registrar Asistencia");
        itemConsultarAsistencia = new JMenuItem("Consultar Asistencia");
        menuAsistencia.add(itemRegistrarAsistencia);
        menuAsistencia.add(itemConsultarAsistencia);
        
        // Menú Planilla
        menuPlanilla = new JMenu("Planilla");
        itemGenerarPlanilla = new JMenuItem("Generar Planilla");
        itemConsultarPlanilla = new JMenuItem("Consultar Planilla");
        menuPlanilla.add(itemGenerarPlanilla);
        menuPlanilla.add(itemConsultarPlanilla);
        
        // Menú Reportes
        menuReportes = new JMenu("Reportes");
        itemReporteAsistencia = new JMenuItem("Reporte de Asistencia");
        itemReporteTardanzas = new JMenuItem("Reporte de Tardanzas");
        itemReporteHorasExtras = new JMenuItem("Reporte de Horas Extras");
        menuReportes.add(itemReporteAsistencia);
        menuReportes.add(itemReporteTardanzas);
        menuReportes.add(itemReporteHorasExtras);
        
        // Menú Mantenimiento
        menuMantenimiento = new JMenu("Mantenimiento");
        itemGestionPersonal = new JMenuItem("Gestión de Personal");
        itemGestionUsuarios = new JMenuItem("Gestión de Usuarios");
        menuMantenimiento.add(itemGestionPersonal);
        menuMantenimiento.add(itemGestionUsuarios);
        
        // Menú Sistema
        menuSistema = new JMenu("Sistema");
        itemCerrarSesion = new JMenuItem("Cerrar Sesión");
        itemSalir = new JMenuItem("Salir");
        menuSistema.add(itemCerrarSesion);
        menuSistema.addSeparator();
        menuSistema.add(itemSalir);
        
        menuBar.add(menuAsistencia);
        menuBar.add(menuPlanilla);
        menuBar.add(menuReportes);
        menuBar.add(menuMantenimiento);
        menuBar.add(menuSistema);
        
        this.setJMenuBar(menuBar);
    }
    
    private void configurarEventos() {
        // Eventos del menú Asistencia
        itemRegistrarAsistencia.addActionListener(e -> abrirRegistroAsistencia());
        itemConsultarAsistencia.addActionListener(e -> abrirConsultaAsistencia());
        
        // Eventos del menú Planilla
        itemGenerarPlanilla.addActionListener(e -> abrirGenerarPlanilla());
        itemConsultarPlanilla.addActionListener(e -> abrirConsultaPlanilla());
        
        // Eventos del menú Reportes
        itemReporteAsistencia.addActionListener(e -> abrirReportes());
        itemReporteTardanzas.addActionListener(e -> abrirReporteTardanzas());
        itemReporteHorasExtras.addActionListener(e -> abrirReporteHorasExtras());
        
        // Eventos del menú Mantenimiento
        itemGestionPersonal.addActionListener(e -> abrirGestionPersonal());
        itemGestionUsuarios.addActionListener(e -> abrirGestionUsuarios());
        
        // Eventos del menú Sistema
        itemCerrarSesion.addActionListener(e -> cerrarSesion());
        itemSalir.addActionListener(e -> salirAplicacion());
    }
    
    private void abrirRegistroAsistencia() {
        RegistroAsistencia ventana = new RegistroAsistencia();
        ventana.setVisible(true);
    }
    
    private void abrirConsultaAsistencia() {
        ConsultaAsistencia ventana = new ConsultaAsistencia();
        ventana.setVisible(true);
    }
    
    private void abrirGenerarPlanilla() {
        GenerarPlanilla ventana = new GenerarPlanilla(usuarioActual);
        ventana.setVisible(true);
    }
    
    private void abrirConsultaPlanilla() {
        ConsultaPlanilla ventana = new ConsultaPlanilla();
        ventana.setVisible(true);
    }
    
    private void abrirReportes() {
        ReporteAsistencia ventana = new ReporteAsistencia();
        ventana.setVisible(true);
    }
    
    private void abrirReporteTardanzas() {
        ReporteTardanzas ventana = new ReporteTardanzas();
        ventana.setVisible(true);
    }
    
    private void abrirReporteHorasExtras() {
        ReporteHorasExtras ventana = new ReporteHorasExtras();
        ventana.setVisible(true);
    }
    
    private void abrirGestionPersonal() {
        GestionPersonal ventana = new GestionPersonal();
        ventana.setVisible(true);
    }
    
    private void abrirGestionUsuarios() {
        JOptionPane.showMessageDialog(this, 
            "Módulo de Gestión de Usuarios en desarrollo", 
            "Información", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void cerrarSesion() {
        int confirmacion = JOptionPane.showConfirmDialog(this,
            "¿Está seguro de cerrar sesión?",
            "Confirmar cierre de sesión",
            JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            Login login = new Login();
            login.setVisible(true);
            this.dispose();
        }
    }
    
    private void salirAplicacion() {
        int confirmacion = JOptionPane.showConfirmDialog(this,
            "¿Está seguro de salir de la aplicación?",
            "Confirmar salida",
            JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
    
    private void configurarVentana() {
        this.setTitle("Sistema de Asistencias y Planillas - Instituto Americano");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(panelPrincipal);
        this.setSize(1000, 700);
        this.setLocationRelativeTo(null);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
}
