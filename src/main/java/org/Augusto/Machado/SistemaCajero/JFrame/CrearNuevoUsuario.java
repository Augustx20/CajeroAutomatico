package org.Augusto.Machado.SistemaCajero.JFrame;

import org.Augusto.Machado.SistemaCajero.Models.Usuario;
import org.Augusto.Machado.SistemaCajero.Repositorio.UsuarioRepositorio;
import org.Augusto.Machado.SistemaCajero.Repositorio.UsuarioRepositorioImpl;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class CrearNuevoUsuario extends JFrame {
    Container c;
    String TEXT_USUARIO = "Usuario: ";
    String TEXT_PIN = "PIN: ";
    JTextField INPUT_USUARIO;
    JPasswordField INPUT_PIN;

    public CrearNuevoUsuario() throws HeadlessException {
        super();
        c = getContentPane();
        c.setLayout(new FlowLayout());

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 4, 4));

        JLabel usuario = new JLabel(TEXT_USUARIO, JLabel.CENTER);
        INPUT_USUARIO = new JTextField();
        formPanel.add(usuario);
        formPanel.add(INPUT_USUARIO);
        JLabel pin = new JLabel(TEXT_PIN, JLabel.CENTER);
        INPUT_PIN = new JPasswordField();
        formPanel.add(pin);
        formPanel.add(INPUT_PIN);

        UsuarioRepositorio<Usuario> usuarioRepositorio = new UsuarioRepositorioImpl();

        // Bucle para pedir los datos hasta que el usuario sea único
        boolean usuarioValido = false;
        while (!usuarioValido) {
            int option = JOptionPane.showConfirmDialog(
                    this,
                    formPanel,
                    "Registro de Usuario nuevo",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
            );

            if (option == JOptionPane.OK_OPTION) {
                // Verificar si el usuario ya existe
                if (usuarioRepositorio.ValidarUsuario(INPUT_USUARIO.getText())) {
                    JOptionPane.showMessageDialog(null, "¡El usuario ya existe!", "Error", JOptionPane.ERROR_MESSAGE);
                    // Limpiar los campos para que el usuario intente de nuevo
                    INPUT_USUARIO.setText("");
                    INPUT_PIN.setText("");
                } else {
                    // Si el usuario es válido, crear el usuario
                    Usuario uNuevo = new Usuario();
                    uNuevo.setUsuario(INPUT_USUARIO.getText());

                    // Convertir el PIN de JPasswordField a int
                    char[] pinArray = INPUT_PIN.getPassword();
                    String pinString = new String(pinArray);
                    int pinInt;
                    try {
                        pinInt = Integer.parseInt(pinString);
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(this, "El PIN debe ser un número entero.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    uNuevo.setPIN(pinInt);
                    uNuevo.setFecha_registro(new Date());
                    uNuevo.setEstado(1);
                    usuarioRepositorio.crearUsuario(uNuevo);
                    JOptionPane.showMessageDialog(null,"Usuario nuevo creado con exito!","Confirmacion", JOptionPane.INFORMATION_MESSAGE);
                    usuarioValido = true; // Salir del ciclo porque el usuario fue creado con éxito
                }
            } else if (option == JOptionPane.CANCEL_OPTION) {
                this.dispose(); // Cerrar la ventana si el usuario cancela
                break;
            }
        }
    }
}
