package projetoA3;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Gerenciar_faturamentos extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tabela_faturamento;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JButton btnNewButton;
	private JTextField txtIDFaturamento;
	Crud_faturamento cff = new Crud_faturamento();
	private JButton btnAlterar_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gerenciar_faturamentos frame = new Gerenciar_faturamentos();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void Selecionando_faturamento()
    {
        // Precisamos criar algumas variáveis para conectar, dar comandos e
        // também para armazenar a pesquisa com o SELECT:
        Connection conexao = null;
        Statement  comando = null;
        ResultSet  resultado = null;
        try {
            conexao = ClasseConexao.Conectar();
            comando = conexao.createStatement();
            String meu_sql = "SELECT *  FROM controle_faturamento";
            resultado = comando.executeQuery(meu_sql); // Executa o SQL
            
            tabela_faturamento.setModel(DbUtils.resultSetToTableModel(resultado));
            tabela_faturamento.getColumnModel().getColumn(0).setHeaderValue("ID");
            tabela_faturamento.getColumnModel().getColumn(0).setPreferredWidth(35);
            tabela_faturamento.getColumnModel().getColumn(1).setHeaderValue("Descrição do faturamento");
            tabela_faturamento.getColumnModel().getColumn(1).setPreferredWidth(1000);
           
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            ClasseConexao.FecharConexao(conexao);
            try
            {
                comando.close();
                resultado.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }
	
	public Gerenciar_faturamentos() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1007, 536);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTextArea taFaturamento = new JTextArea();
		taFaturamento.setBounds(162, 39, 664, 96);
		contentPane.add(taFaturamento);{
			FiltroDeCaracteres filtro = new FiltroDeCaracteres(FiltroDeCaracteres.TipoDeFiltro.email, 400);
			taFaturamento.setDocument(filtro);
		}
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(162, 264, 669, 225);
		contentPane.add(scrollPane);
		
		tabela_faturamento = new JTable();
		tabela_faturamento.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int linha = tabela_faturamento.getSelectedRow();
				txtIDFaturamento.setText(tabela_faturamento.getModel().getValueAt(linha,0).toString());
				taFaturamento.setText(tabela_faturamento.getModel().getValueAt(linha,1).toString());
				
			}
		});
		scrollPane.setViewportView(tabela_faturamento);{
			Selecionando_faturamento();
		}
		
		lblNewLabel = new JLabel("ID Faturamento:");
		lblNewLabel.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 20));
		lblNewLabel.setBounds(55, 161, 150, 39);
		contentPane.add(lblNewLabel);
		
		lblNewLabel_1 = new JLabel("Descrição:");
		lblNewLabel_1.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 20));
		lblNewLabel_1.setBounds(55, 39, 150, 39);
		contentPane.add(lblNewLabel_1);
		
		btnNewButton = new JButton("Deletar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(txtIDFaturamento.getText().isBlank()) {
					JOptionPane.showMessageDialog(null, "Preencha o ID de faturamento");
					return;
				}
				cff.Deletar(Integer.parseInt(txtIDFaturamento.getText()));
				Selecionando_faturamento();
			}
		});
		btnNewButton.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 20));
		btnNewButton.setBounds(358, 165, 118, 30);
		contentPane.add(btnNewButton);
		
		txtIDFaturamento = new JTextField();
		txtIDFaturamento.setEditable(false);
		txtIDFaturamento.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 17));
		txtIDFaturamento.setBounds(199, 163, 116, 30);
		contentPane.add(txtIDFaturamento);
		txtIDFaturamento.setColumns(10);
		
		JButton btnAlterar = new JButton("Alterar");
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(txtIDFaturamento.getText().isBlank()|| taFaturamento.getText().isBlank()) {
					JOptionPane.showMessageDialog(null, "Preencha o todos os campos");
					return;
				}
					cff.Alterar(taFaturamento.getText(), Integer.parseInt(txtIDFaturamento.getText()));
					Selecionando_faturamento();
			}
		});
		btnAlterar.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 20));
		btnAlterar.setBounds(515, 165, 118, 30);
		contentPane.add(btnAlterar);
		
		JButton btnAlterar_1 = new JButton("Alterar");
		btnAlterar_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				taFaturamento.setText("");
			}
		});
		btnAlterar_1.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 20));
		btnAlterar_1.setBounds(836, 39, 17, 17);
		contentPane.add(btnAlterar_1);
		
		JLabel lblTabelaDeFaturamento = new JLabel("Tabela de faturamento");
		lblTabelaDeFaturamento.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 20));
		lblTabelaDeFaturamento.setBounds(395, 215, 203, 39);
		contentPane.add(lblTabelaDeFaturamento);
		
		btnAlterar_2 = new JButton("Alterar");
		btnAlterar_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtIDFaturamento.setText("");
			}
		});
		btnAlterar_2.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 20));
		btnAlterar_2.setBounds(320, 170, 17, 17);
		contentPane.add(btnAlterar_2);
}
}