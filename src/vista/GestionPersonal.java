// vista/GestionPersonal.java
package vista;

import modelo.dao.PersonalDAO;
import modelo.entidades.Personal;
import util.Validaciones;
import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;

public class GestionPersonal extends JFrame {
    private PersonalDAO personalDAO;
    
    // Componentes
    private JPanel panelPrincipal;
    private JPanel panelSuperior;
    private JPanel panelFormulario;
    private JPanel panelTabla;
    private JPanel panelBotones;
    
    private JLabel lblTitulo;
    private JLabel lblDni;
    private JLabel lblNombres;
    private JLabel lblApellidos;
    private JLabel lblTipo;
    private JLabel lblCargo;
    private JLabel lblFechaContratacion;
    private JLabel lblSalario;
    private JLabel lblHorarioEntrada;
    private JLabel lblHorarioSalida;
    
    private JTextField txtDni;
    private JTextField txtNombres;
    private JTextField txtApellidos;
    private JComboBox<String> cmbTipo;
    private JTextField txtCargo;
    private JDateChooser dateFechaContratacion;
    private JTextField txtSalario;
    private JTextField txtHorarioEntrada;
    private JTextField txtHorarioSalida;
    
    private JButton btnNuevo;
    private JButton btnGuardar;
    private JButton btnModificar;
    private JButton btnEliminar;
    private JButton btnCancelar;
    private JButton btnCerrar;
    
    private JTable tablaPersonal;
    private JScrollPane scrollPane;
    private DefaultTableModel modeloTabla;
    
    private boolean modoEdicion = false;
    private int idPersonalSeleccionado = -1;
    
    public GestionPersonal() {
        personalDAO = new PersonalDAO();
        inicializarComponentes();
        configurarVentana();
        cargarPersonal();
    }
    
    private void inicializarComponentes() {
        // Panel principal
        panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBackground(Color.WHITE);
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Panel superior
        panelSuperior = new JPanel();
        panelSuperior.setBackground(new Color(26, 188, 156));
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        lblTitulo = new JLabel("GESTIÓN DE PERSONAL");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(Color.BLACK);
        panelSuperior.add(lblTitulo);
        
        // Panel de formulario
        panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBackground(Color.WHITE);
        panelFormulario.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                "Datos del Personal",
                0,
                0,
                new Font("Segoe UI", Font.BOLD, 14)
            ),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Primera fila
        lblDni = new JLabel("DNI:");
        lblDni.setFont(new Font("Segoe UI", Font.BOLD, 13));
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelFormulario.add(lblDni, gbc);
        
        txtDni = new JTextField(15);
        txtDni.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        gbc.gridx = 1;
        panelFormulario.add(txtDni, gbc);
        
        lblNombres = new JLabel("Nombres:");
        lblNombres.setFont(new Font("Segoe UI", Font.BOLD, 13));
        gbc.gridx = 2;
        panelFormulario.add(lblNombres, gbc);
        
        txtNombres = new JTextField(20);
        txtNombres.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        gbc.gridx = 3;
        panelFormulario.add(txtNombres, gbc);
        
        // Segunda fila
        lblApellidos = new JLabel("Apellidos:");
        lblApellidos.setFont(new Font("Segoe UI", Font.BOLD, 13));
        gbc.gridx = 0;
        gbc.gridy = 1;
        panelFormulario.add(lblApellidos, gbc);
        
        txtApellidos = new JTextField(20);
        txtApellidos.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        gbc.gridx = 1;
        panelFormulario.add(txtApellidos, gbc);
        
        lblTipo = new JLabel("Tipo:");
        lblTipo.setFont(new Font("Segoe UI", Font.BOLD, 13));
        gbc.gridx = 2;
        panelFormulario.add(lblTipo, gbc);
        
        cmbTipo = new JComboBox<>(new String[]{"ADMINISTRATIVO", "DOCENTE"});
        cmbTipo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        gbc.gridx = 3;
        panelFormulario.add(cmbTipo, gbc);
        
        // Tercera fila
        lblCargo = new JLabel("Cargo:");
        lblCargo.setFont(new Font("Segoe UI", Font.BOLD, 13));
        gbc.gridx = 0;
        gbc.gridy = 2;
        panelFormulario.add(lblCargo, gbc);
        
        txtCargo = new JTextField(20);
        txtCargo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        gbc.gridx = 1;
        panelFormulario.add(txtCargo, gbc);
        
        lblFechaContratacion = new JLabel("F. Contratación:");
        lblFechaContratacion.setFont(new Font("Segoe UI", Font.BOLD, 13));
        gbc.gridx = 2;
        panelFormulario.add(lblFechaContratacion, gbc);
        
        dateFechaContratacion = new JDateChooser();
        dateFechaContratacion.setDateFormatString("dd/MM/yyyy");
        dateFechaContratacion.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        gbc.gridx = 3;
        panelFormulario.add(dateFechaContratacion, gbc);
        
        // Cuarta fila
        lblSalario = new JLabel("Salario Base:");
        lblSalario.setFont(new Font("Segoe UI", Font.BOLD, 13));
        gbc.gridx = 0;
        gbc.gridy = 3;
        panelFormulario.add(lblSalario, gbc);
        
        txtSalario = new JTextField(10);
        txtSalario.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        gbc.gridx = 1;
        panelFormulario.add(txtSalario, gbc);
        
        lblHorarioEntrada = new JLabel("Hora Entrada:");
        lblHorarioEntrada.setFont(new Font("Segoe UI", Font.BOLD, 13));
        gbc.gridx = 2;
        panelFormulario.add(lblHorarioEntrada, gbc);
        
        txtHorarioEntrada = new JTextField(10);
        txtHorarioEntrada.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtHorarioEntrada.setToolTipText("Formato: HH:MM (ejemplo: 08:00)");
        gbc.gridx = 3;
        panelFormulario.add(txtHorarioEntrada, gbc);
        
        // Quinta fila
        lblHorarioSalida = new JLabel("Hora Salida:");
        lblHorarioSalida.setFont(new Font("Segoe UI", Font.BOLD, 13));
        gbc.gridx = 0;
        gbc.gridy = 4;
        panelFormulario.add(lblHorarioSalida, gbc);
        
        txtHorarioSalida = new JTextField(10);
        txtHorarioSalida.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtHorarioSalida.setToolTipText("Formato: HH:MM (ejemplo: 17:00)");
        gbc.gridx = 1;
        panelFormulario.add(txtHorarioSalida, gbc);
        
        // Panel de tabla
        panelTabla = new JPanel(new BorderLayout());
        panelTabla.setBackground(Color.WHITE);
        panelTabla.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.GRAY),
            "Lista de Personal",
            0,
            0,
            new Font("Segoe UI", Font.BOLD, 14)
        ));
        
        String[] columnas = {"ID", "DNI", "Nombres", "Apellidos", "Tipo", "Cargo", "Salario", "Horario"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };   tablaPersonal = new JTable(modeloTabla);
        tablaPersonal.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tablaPersonal.setRowHeight(25);
        tablaPersonal.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tablaPersonal.getTableHeader().setBackground(new Color(26, 188, 156));
        tablaPersonal.getTableHeader().setForeground(Color.BLACK);
        tablaPersonal.setSelectionBackground(new Color(26, 188, 156));
        tablaPersonal.setSelectionForeground(Color.BLACK);
        tablaPersonal.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        scrollPane = new JScrollPane(tablaPersonal);
        panelTabla.add(scrollPane, BorderLayout.CENTER);
        
        // Panel de botones
        panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelBotones.setBackground(Color.WHITE);
        
        btnNuevo = new JButton("Nuevo");
        btnNuevo.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnNuevo.setBackground(new Color(52, 152, 219));
        btnNuevo.setForeground(Color.BLACK);
        btnNuevo.setFocusPainted(false);
        btnNuevo.setPreferredSize(new Dimension(110, 35));
        btnNuevo.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnGuardar = new JButton("Guardar");
        btnGuardar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnGuardar.setBackground(new Color(46, 204, 113));
        btnGuardar.setForeground(Color.BLACK);
        btnGuardar.setFocusPainted(false);
        btnGuardar.setPreferredSize(new Dimension(110, 35));
        btnGuardar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnGuardar.setEnabled(false);
        
        btnModificar = new JButton("Modificar");
        btnModificar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnModificar.setBackground(new Color(230, 126, 34));
        btnModificar.setForeground(Color.BLACK);
        btnModificar.setFocusPainted(false);
        btnModificar.setPreferredSize(new Dimension(110, 35));
        btnModificar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnModificar.setEnabled(false);
        
        btnEliminar = new JButton("Eliminar");
        btnEliminar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnEliminar.setBackground(new Color(231, 76, 60));
        btnEliminar.setForeground(Color.BLACK);
        btnEliminar.setFocusPainted(false);
        btnEliminar.setPreferredSize(new Dimension(110, 35));
        btnEliminar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnEliminar.setEnabled(false);
        
        btnCancelar = new JButton("Cancelar");
        btnCancelar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnCancelar.setBackground(new Color(149, 165, 166));
        btnCancelar.setForeground(Color.BLACK);
        btnCancelar.setFocusPainted(false);
        btnCancelar.setPreferredSize(new Dimension(110, 35));
        btnCancelar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCancelar.setEnabled(false);
        
        btnCerrar = new JButton("Cerrar");
        btnCerrar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnCerrar.setBackground(new Color(52, 73, 94));
        btnCerrar.setForeground(Color.BLACK);
        btnCerrar.setFocusPainted(false);
        btnCerrar.setPreferredSize(new Dimension(110, 35));
        btnCerrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        panelBotones.add(btnNuevo);
        panelBotones.add(btnGuardar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnCancelar);
        panelBotones.add(btnCerrar);
        
        // Agregar paneles
        JPanel panelCentro = new JPanel(new BorderLayout(10, 10));
        panelCentro.setBackground(Color.WHITE);
        panelCentro.add(panelFormulario, BorderLayout.NORTH);
        panelCentro.add(panelTabla, BorderLayout.CENTER);
        
        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
        panelPrincipal.add(panelCentro, BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
        
        // Configurar eventos
        configurarEventos();
        
        // Deshabilitar campos inicialmente
        deshabilitarCampos();
    }
    
    private void configurarEventos() {
        // Evento de selección en tabla
        tablaPersonal.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = tablaPersonal.getSelectedRow();
                if (selectedRow >= 0 && !modoEdicion) {
                    btnModificar.setEnabled(true);
                    btnEliminar.setEnabled(true);
                    cargarDatosPersonalSeleccionado(selectedRow);
                }
            }
        });
        
        btnNuevo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nuevo();
            }
        });
        
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardar();
            }
        });
        
        btnModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modificar();
            }
        });
        
        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminar();
            }
        });
        
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelar();
            }
        });
        
        btnCerrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
    
    private void nuevo() {
        limpiarCampos();
        habilitarCampos();
        modoEdicion = true;
        idPersonalSeleccionado = -1;
        
        btnNuevo.setEnabled(false);
        btnGuardar.setEnabled(true);
        btnModificar.setEnabled(false);
        btnEliminar.setEnabled(false);
        btnCancelar.setEnabled(true);
        
        txtDni.requestFocus();
    }
    
    private void guardar() {
        // Validar campos
        if (!validarCampos()) {
            return;
        }
        
        try {
            Personal personal = new Personal();
            personal.setDni(txtDni.getText().trim());
            personal.setNombres(txtNombres.getText().trim());
            personal.setApellidos(txtApellidos.getText().trim());
            personal.setTipoPersonal(cmbTipo.getSelectedItem().toString());
            personal.setCargo(txtCargo.getText().trim());
            personal.setFechaContratacion(dateFechaContratacion.getDate());
            personal.setSalarioBase(Double.parseDouble(txtSalario.getText().trim()));
            personal.setHorarioEntrada(txtHorarioEntrada.getText().trim());
            personal.setHorarioSalida(txtHorarioSalida.getText().trim());
            personal.setActivo(true);
            
            boolean resultado;
            
            if (idPersonalSeleccionado == -1) {
                // Nuevo registro
                resultado = personalDAO.crearPersonal(personal);
                if (resultado) {
                    JOptionPane.showMessageDialog(this,
                        "Personal registrado exitosamente",
                        "Registro exitoso",
                        JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                // Actualizar registro
                personal.setIdPersonal(idPersonalSeleccionado);
                resultado = personalDAO.actualizarPersonal(personal);
                if (resultado) {
                    JOptionPane.showMessageDialog(this,
                        "Personal actualizado exitosamente",
                        "Actualización exitosa",
                        JOptionPane.INFORMATION_MESSAGE);
                }
            }
            
            if (resultado) {
                cancelar();
                cargarPersonal();
            } else {
                JOptionPane.showMessageDialog(this,
                    "Error al guardar el personal",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Error: " + ex.getMessage(),
                "Error del sistema",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void modificar() {
        habilitarCampos();
        txtDni.setEditable(false); // DNI no se puede modificar
        modoEdicion = true;
        
        btnNuevo.setEnabled(false);
        btnGuardar.setEnabled(true);
        btnModificar.setEnabled(false);
        btnEliminar.setEnabled(false);
        btnCancelar.setEnabled(true);
    }
    
    private void eliminar() {
        int confirmacion = JOptionPane.showConfirmDialog(this,
            "¿Está seguro de eliminar este personal?\n" +
            "Esta acción no se puede deshacer.",
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            boolean resultado = personalDAO.eliminarPersonal(idPersonalSeleccionado);
            
            if (resultado) {
                JOptionPane.showMessageDialog(this,
                    "Personal eliminado exitosamente",
                    "Eliminación exitosa",
                    JOptionPane.INFORMATION_MESSAGE);
                cancelar();
                cargarPersonal();
            } else {
                JOptionPane.showMessageDialog(this,
                    "Error al eliminar el personal",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void cancelar() {
        limpiarCampos();
        deshabilitarCampos();
        modoEdicion = false;
        idPersonalSeleccionado = -1;
        
        btnNuevo.setEnabled(true);
        btnGuardar.setEnabled(false);
        btnModificar.setEnabled(false);
        btnEliminar.setEnabled(false);
        btnCancelar.setEnabled(false);
        
        tablaPersonal.clearSelection();
    }
    
    private void cargarPersonal() {
        modeloTabla.setRowCount(0);
        List<Personal> listaPersonal = personalDAO.listarPersonal();
        
        for (Personal personal : listaPersonal) {
            Object[] fila = {
                personal.getIdPersonal(),
                personal.getDni(),
                personal.getNombres(),
                personal.getApellidos(),
                personal.getTipoPersonal(),
                personal.getCargo(),
                String.format("S/ %.2f", personal.getSalarioBase()),
                personal.getHorarioEntrada() + " - " + personal.getHorarioSalida()
            };
            modeloTabla.addRow(fila);
        }
    }
    
    private void cargarDatosPersonalSeleccionado(int fila) {
        idPersonalSeleccionado = (int) modeloTabla.getValueAt(fila, 0);
        txtDni.setText(modeloTabla.getValueAt(fila, 1).toString());
        txtNombres.setText(modeloTabla.getValueAt(fila, 2).toString());
        txtApellidos.setText(modeloTabla.getValueAt(fila, 3).toString());
        cmbTipo.setSelectedItem(modeloTabla.getValueAt(fila, 4).toString());
        txtCargo.setText(modeloTabla.getValueAt(fila, 5).toString());
        
        // Cargar datos completos del personal
        Personal personal = personalDAO.buscarPorDni(txtDni.getText());
        if (personal != null) {
            dateFechaContratacion.setDate(personal.getFechaContratacion());
            txtSalario.setText(String.valueOf(personal.getSalarioBase()));
            txtHorarioEntrada.setText(personal.getHorarioEntrada());
            txtHorarioSalida.setText(personal.getHorarioSalida());
        }
    }
    
    private boolean validarCampos() {
        // Validar DNI
        if (!Validaciones.validarCampoVacio(txtDni, "DNI")) {
            return false;
        }
        if (!Validaciones.validarDNI(txtDni.getText().trim())) {
            return false;
        }
        
        // Validar nombres
        if (!Validaciones.validarCampoVacio(txtNombres, "Nombres")) {
            return false;
        }
        if (!Validaciones.validarSoloLetras(txtNombres.getText().trim(), "Nombres")) {
            return false;
        }
        
        // Validar apellidos
        if (!Validaciones.validarCampoVacio(txtApellidos, "Apellidos")) {
            return false;
        }
        if (!Validaciones.validarSoloLetras(txtApellidos.getText().trim(), "Apellidos")) {
            return false;
        }
        
        // Validar cargo
        if (!Validaciones.validarCampoVacio(txtCargo, "Cargo")) {
            return false;
        }
        
        // Validar fecha de contratación
        if (dateFechaContratacion.getDate() == null) {
            JOptionPane.showMessageDialog(this,
                "Por favor seleccione la fecha de contratación",
                "Campo vacío",
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        // Validar salario
        if (!Validaciones.validarCampoVacio(txtSalario, "Salario")) {
            return false;
        }
        if (!Validaciones.validarDecimalPositivo(txtSalario.getText().trim(), "Salario")) {
            return false;
        }
        
        // Validar horario de entrada
        if (!Validaciones.validarCampoVacio(txtHorarioEntrada, "Horario de Entrada")) {
            return false;
        }
        if (!Validaciones.validarFormatoHora(txtHorarioEntrada.getText().trim(), "Horario de Entrada")) {
            return false;
        }
        
        // Validar horario de salida
        if (!Validaciones.validarCampoVacio(txtHorarioSalida, "Horario de Salida")) {
            return false;
        }
        if (!Validaciones.validarFormatoHora(txtHorarioSalida.getText().trim(), "Horario de Salida")) {
            return false;
        }
        
        return true;
    }
    
    private void limpiarCampos() {
        txtDni.setText("");
        txtNombres.setText("");
        txtApellidos.setText("");
        cmbTipo.setSelectedIndex(0);
        txtCargo.setText("");
        dateFechaContratacion.setDate(null);
        txtSalario.setText("");
        txtHorarioEntrada.setText("");
        txtHorarioSalida.setText("");
    }
    
    private void habilitarCampos() {
        txtDni.setEditable(true);
        txtNombres.setEditable(true);
        txtApellidos.setEditable(true);
        cmbTipo.setEnabled(true);
        txtCargo.setEditable(true);
        dateFechaContratacion.setEnabled(true);
        txtSalario.setEditable(true);
        txtHorarioEntrada.setEditable(true);
        txtHorarioSalida.setEditable(true);
    }
    
    private void deshabilitarCampos() {
        txtDni.setEditable(false);
        txtNombres.setEditable(false);
        txtApellidos.setEditable(false);
        cmbTipo.setEnabled(false);
        txtCargo.setEditable(false);
        dateFechaContratacion.setEnabled(false);
        txtSalario.setEditable(false);
        txtHorarioEntrada.setEditable(false);
        txtHorarioSalida.setEditable(false);
    }
    
    private void configurarVentana() {
        this.setTitle("Gestión de Personal");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setContentPane(panelPrincipal);
        this.setSize(1200, 750);
        this.setLocationRelativeTo(null);
    }
}
