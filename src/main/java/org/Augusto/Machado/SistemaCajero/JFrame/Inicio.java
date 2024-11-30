package org.Augusto.Machado.SistemaCajero.JFrame;

import org.Augusto.Machado.SistemaCajero.Models.Usuario;
import org.Augusto.Machado.SistemaCajero.Repositorio.UsuarioRepositorio;
import org.Augusto.Machado.SistemaCajero.Repositorio.UsuarioRepositorioImpl;

import javax.swing.*;
import java.awt.*;

public class Inicio extends JFrame {

    Container c;
    String TEXT_USUARIO = "Usuario";
    String TEXT_PIN = "PIN";
    JTextField INPUT_USUARIO;
    JPasswordField INPUT_PIN;
    JButton btnLogin, btnCancel,btnCrearNuevoUsuario;

    public Inicio() {
        super("Menu Sistema Cajero Automatico");
        c = getContentPane();
        c.setLayout(new BorderLayout(5, 5));

        // Paneles
        JPanel pTop = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel pCenter = new JPanel(new GridBagLayout());
        JPanel pBottom = new JPanel(new FlowLayout(FlowLayout.CENTER));

        // Agregar paneles al contenedor
        c.add(pTop, BorderLayout.NORTH);
        c.add(pCenter, BorderLayout.CENTER);
        c.add(pBottom, BorderLayout.SOUTH);

        // Título
        pTop.add(new JLabel("Menu Sistema Cajero Automatico"));

        // GridBagConstraints para configurar la disposición de los componentes
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Campos de texto
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        pCenter.add(new JLabel(TEXT_USUARIO), gbc);

        gbc.gridx = 1;
        INPUT_USUARIO = new JTextField(15);
        pCenter.add(INPUT_USUARIO, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        pCenter.add(new JLabel(TEXT_PIN), gbc);

        gbc.gridx = 1;
        INPUT_PIN = new JPasswordField(15);
        pCenter.add(INPUT_PIN, gbc);

        // Iniciar Sesion
        btnLogin = new JButton("Login");
        btnLogin.addActionListener(event -> {

                // Campos A completar
                String usuario = INPUT_USUARIO.getText();
                char[] pin = INPUT_PIN.getPassword();


                UsuarioRepositorio<Usuario> usuarioRepositorio = new UsuarioRepositorioImpl();

                if (usuarioRepositorio.porUsuario(usuario,pin)) {
                    // Acciones a realizar en caso de éxito
                    JOptionPane.showMessageDialog(null, "Inicio de sesión exitoso");
                } else {
                    // Acciones a realizar en caso de error
                    JOptionPane.showMessageDialog(null, "Usuario o PIN incorrecto / Tener en cuenta que si " +
                            "usted es usuario nuevo debe crear una cuenta nueva");
                }
        });
        pBottom.add(btnLogin);

        //Cancelar Proceso
        btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(event -> System.exit(0));
        pBottom.add(btnCancel);

        //Crear Nuevo Usuario
        btnCrearNuevoUsuario = new JButton("Crear Nuevo Usuario");
        btnCrearNuevoUsuario.addActionListener(event -> new CrearNuevoUsuario());
        pBottom.add(btnCrearNuevoUsuario);


        // Configuración de la ventana
        setSize(400,250);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

}
