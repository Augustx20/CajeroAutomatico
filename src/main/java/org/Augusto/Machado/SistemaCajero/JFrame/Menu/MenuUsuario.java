package org.Augusto.Machado.SistemaCajero.JFrame.Menu;

import org.Augusto.Machado.SistemaCajero.Models.Usuario;
import org.Augusto.Machado.SistemaCajero.Repositorio.Usuario.UsuarioRepositorio;
import org.Augusto.Machado.SistemaCajero.Repositorio.Usuario.UsuarioRepositorioImpl;

import javax.swing.*;
import java.awt.*;

public class MenuUsuario extends JFrame {
    Container c;
    String TEXT_SALDO = "Saldo";
    String TEXT_MOVIMIENTO = "Movimiento";
    String TEXT_CONSULTAR = "Consultar";

    public MenuUsuario(String u) throws HeadlessException {
        super("Menu Usuario");
        c = getContentPane();
        c.setLayout(new BorderLayout(5, 5));

        // Panel para el título
        JPanel panelTop = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel titleLabel = new JLabel(TEXT_CONSULTAR);
        panelTop.add(titleLabel);

        // Panel para los botones
        JPanel panelCenter = new JPanel(new FlowLayout(FlowLayout.CENTER, 100, 0)); // Espacio horizontal de 20 píxeles entre botones
        JButton btnSaldo = new JButton(TEXT_SALDO);
        btnSaldo.addActionListener(e -> {
            UsuarioRepositorio<Usuario> usuarioRepositorio = new UsuarioRepositorioImpl();
            int resultado = usuarioRepositorio.MostrarSaldoUsuario(u);
            System.out.println(u);
            JOptionPane.showMessageDialog(null, "Tu saldo es de: $ "+resultado,"Saldo Disponible", JOptionPane.PLAIN_MESSAGE);

        });
        JButton btnMovimiento = new JButton(TEXT_MOVIMIENTO);
        panelCenter.add(btnSaldo);
        panelCenter.add(btnMovimiento);

        // Agregar los paneles al contenedor principal
        c.add(panelTop, BorderLayout.NORTH);
        c.add(panelCenter, BorderLayout.CENTER);

        // Configuración de la ventana
        setSize(400, 250);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }


}
