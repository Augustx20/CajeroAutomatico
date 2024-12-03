package org.Augusto.Machado.SistemaCajero.JFrame.Menu;

import org.Augusto.Machado.SistemaCajero.Models.Usuario;
import org.Augusto.Machado.SistemaCajero.Repositorio.Usuario.UsuarioRepositorio;
import org.Augusto.Machado.SistemaCajero.Repositorio.Usuario.UsuarioRepositorioImpl;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.List;

public class MostrarTransacciones extends JFrame {

    public MostrarTransacciones(String u) throws HeadlessException {
        super("Transacciones");
        UsuarioRepositorio<Usuario> usuarioRepositorio = new UsuarioRepositorioImpl();
        List<Usuario> dataList = usuarioRepositorio.MostrarTransferencia(u);
        JTable table = new JTable(new UserTable(dataList));

        JScrollPane scrollPane = new JScrollPane(table);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);

        // Crear el botón de salir
        JButton btnSalir = new JButton("Salir");
        btnSalir.addActionListener(e -> dispose());
        JPanel panelBottom = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBottom.add(btnSalir);

        panel.add(panelBottom, BorderLayout.SOUTH);

        getContentPane().add(panel);
        pack();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true); // Asegúrate de que esta línea esté presente para mostrar la ventana
    }

    public static class UserTable extends AbstractTableModel {
        private List<Usuario> usuarios;
        private final String[] columnNames = {"Usuario", "Destinatario", "Categoria", "Saldo"};

        public UserTable(List<Usuario> usuarios) {
            this.usuarios = usuarios;
        }

        @Override
        public int getRowCount() {
            return usuarios.size();
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public String getColumnName(int columnIndex) {
            return columnNames[columnIndex];
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Usuario usuario = usuarios.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return usuario.getUsuario();
                case 1:
                    return usuario.getDestinatario();
                case 2:
                    return usuario.getCategoria();
                case 3:
                    return usuario.getSaldo();
                default:
                    return null;
            }
        }
    }
}
