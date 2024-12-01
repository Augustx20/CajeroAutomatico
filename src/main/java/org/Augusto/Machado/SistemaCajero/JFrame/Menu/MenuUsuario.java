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
    String TEXT_REALIZAR = "Realizar";
    String TEXT_DEPOSITAR = "Depositar";
    String TEXT_RETIRAR = "Retirar";
    String TEXT_TRANSFERENCIA = "Transferir";
    String TEXT_SALIR = "Salir";
    String TEXT_PIN = "Resetear PIN";

    public MenuUsuario(String u) throws HeadlessException {
        super("Menu Usuario");
        c = getContentPane();
        c.setLayout(new BorderLayout(5, 5));

        // Panel para el título
        JPanel panelTop = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel titulo = new JLabel(TEXT_CONSULTAR);
        panelTop.add(titulo);

        // Panel para los botones
        JPanel panelCenter = new JPanel(new GridLayout(4, 3, 10, 10)); // 4 filas, 3 columnas, espacio horizontal y vertical de 10 píxeles
        JPanel panelBottom = new JPanel(new FlowLayout(FlowLayout.CENTER));

        // Añadir los botones y etiquetas
        JButton btnSaldo = new JButton(TEXT_SALDO);
        JButton btnMovimiento = new JButton(TEXT_MOVIMIENTO);
        JButton btnDepositar = new JButton(TEXT_DEPOSITAR);
        JButton btnRetirar = new JButton(TEXT_RETIRAR);
        JButton btnTransferencia = new JButton(TEXT_TRANSFERENCIA);
        JButton btnSalir = new JButton(TEXT_SALIR);
        JButton btnPIN = new JButton(TEXT_PIN);


        btnSalir.setPreferredSize(new Dimension(100, 50));
        btnSaldo.addActionListener(e -> {
            UsuarioRepositorio<Usuario> usuarioRepositorio = new UsuarioRepositorioImpl();
            int resultado = usuarioRepositorio.MostrarSaldoUsuario(u);
            System.out.println(u);
            JOptionPane.showMessageDialog(null, "Tu saldo es de: $ " + resultado, "Saldo Disponible", JOptionPane.PLAIN_MESSAGE);
        });

        // Agregar botones y títulos en el orden correcto

        panelCenter.add(btnSaldo);
        panelCenter.add(new JLabel());
        panelCenter.add(btnMovimiento);

        panelCenter.add(new JLabel());
        panelCenter.add(new JLabel(TEXT_REALIZAR, SwingConstants.CENTER));
        panelCenter.add(new JLabel());

        panelCenter.add(btnDepositar);
        btnDepositar.addActionListener(e -> {
            UsuarioRepositorio<Usuario> usuarioRepositorio = new UsuarioRepositorioImpl();
            String textsaldoNuevo = "";
            textsaldoNuevo = JOptionPane.showInputDialog("Por favor, ingrese el saldo a depositar: ");

            int saldo = Integer.parseInt(textsaldoNuevo);


            int resultado =  usuarioRepositorio.Depositar(u, saldo);
            JOptionPane.showMessageDialog(null, "Tu saldo fue actualizado a: $ " + resultado, "Saldo Disponible", JOptionPane.PLAIN_MESSAGE);
        });

        panelCenter.add(btnRetirar);
        panelCenter.add(btnTransferencia);
        panelCenter.add(new JLabel());
        panelCenter.add(btnPIN);
        panelCenter.add(btnSalir);

        // Agregar los paneles al contenedor principal
        c.add(panelTop, BorderLayout.NORTH);
        c.add(panelCenter, BorderLayout.CENTER);
        c.add(panelBottom, BorderLayout.SOUTH);

        // Configuración de la ventana
        setSize(500, 300);
        //pack();
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
