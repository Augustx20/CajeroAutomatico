package org.Augusto.Machado.SistemaCajero.JFrame.Menu;

import org.Augusto.Machado.SistemaCajero.Models.Usuario;
import org.Augusto.Machado.SistemaCajero.Repositorio.Usuario.UsuarioRepositorio;
import org.Augusto.Machado.SistemaCajero.Repositorio.Usuario.UsuarioRepositorioImpl;

import javax.swing.*;
import java.awt.*;

public class Transferencia extends JFrame {

    Container c;
    JTextField usuarioField, montoField, categoriaField;
    JPasswordField pinField;

    public Transferencia(String u) throws HeadlessException {
        super();
        c = getContentPane();
        c.setLayout(new FlowLayout());

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 4, 4));

        JLabel usuario = new JLabel("Usuario a transferir: ", JLabel.RIGHT);
        usuarioField = new JTextField();
        formPanel.add(usuario);
        formPanel.add(usuarioField);

        JLabel monto = new JLabel("Monto: ", JLabel.RIGHT);
        montoField = new JTextField();
        formPanel.add(monto);
        formPanel.add(montoField);

        JLabel categoria = new JLabel("Categoria: ", JLabel.RIGHT);
        categoriaField = new JTextField();
        formPanel.add(categoria);
        formPanel.add(categoriaField);

        JLabel pin = new JLabel("PIN: ", JLabel.RIGHT);
        pinField = new JPasswordField();
        formPanel.add(pin);
        formPanel.add(pinField);

        while (true) {
            UsuarioRepositorio<Usuario> usuarioRepositorio = new UsuarioRepositorioImpl();
            int option = JOptionPane.showConfirmDialog(
                    this,
                    formPanel,
                    "Transferencia",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
            );

            if (option == JOptionPane.OK_OPTION) {
                boolean isValid = true;
                String errorMessage = "";
                Double value = 0.0;
                Double saldoUsuario = 0.0;

                // Validate montoField
                try {
                    saldoUsuario = usuarioRepositorio.MostrarSaldoUsuario(u);
                    value = Double.parseDouble(montoField.getText());

                    if (value < 0) {
                        isValid = false;
                        errorMessage += "El monto debe ser un número positivo.\n";
                    } else if (value > saldoUsuario) {
                        isValid = false;
                        errorMessage += "El monto supera a tu saldo en el cajero.\n";
                    }
                } catch (NumberFormatException e) {
                    isValid = false;
                    errorMessage += "El monto debe ser un número válido.\n";
                }

                // Validar PIN
                int usuario_pin = usuarioRepositorio.ValidarPIN(u);
                try {
                    int enteredPin = Integer.parseInt(new String(pinField.getPassword()).trim());
                    if (enteredPin != usuario_pin) {
                        isValid = false;
                        errorMessage += "El PIN es incorrecto.\n";
                    }
                } catch (NumberFormatException e) {
                    isValid = false;
                    errorMessage += "El PIN debe ser un número válido.\n";
                }

                if (isValid) {
                    saldoUsuario -= value;
                    usuarioRepositorio.Retirar(u, value);
                    usuarioRepositorio.AgregarMovimiento(u,"Transferencia",value,0.0,saldoUsuario);
                    usuarioRepositorio.AgregarTransacciones(u,usuarioField.getText(),categoriaField.getText(),value,usuarioRepositorio.porId(u));
                    JOptionPane.showMessageDialog(null,"Transferencia con Exito","Transferencia",JOptionPane.INFORMATION_MESSAGE);
                    break;
                } else {
                    JOptionPane.showMessageDialog(this, errorMessage, "Error de entrada", JOptionPane.ERROR_MESSAGE);
                }

            } else if (option == JOptionPane.CANCEL_OPTION) {
                break;
            }
        }
    }
}
