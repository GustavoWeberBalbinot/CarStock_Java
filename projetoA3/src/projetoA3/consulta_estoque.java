package projetoA3;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Font;

import javax.sql.ConnectionEventListener;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class consulta_estoque extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField idPeca;
	private JTextField nomePeca;
	private JTable tabela_listagem;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					consulta_estoque frame = new consulta_estoque();
					
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public void Selecionando()
    {
        // Precisamos criar algumas variáveis para conectar, dar comandos e
        // também para armazenar a pesquisa com o SELECT:
        Connection conexao = null;
        Statement  comando = null;
        ResultSet  resultado = null;
        try {
            conexao = ClasseConexao.Conectar();
            comando = conexao.createStatement();
            String meu_sql = "SELECT * FROM cadastro_pecas_estoque";
            resultado = comando.executeQuery(meu_sql); // Executa o SQL

// O nome da sua jTable deve ser “tabela_listagem”
            tabela_listagem.setModel(DbUtils.resultSetToTableModel(resultado));
// O DbUtils deve ser importado (java.sql)

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
	
	
	//
	public consulta_estoque() {
		setTitle("Consulta Estoque");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 487, 460);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Nome Peça:");
		lblNewLabel_1.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 20));
		lblNewLabel_1.setBounds(32, 66, 111, 25);
		contentPane.add(lblNewLabel_1);
		
		idPeca = new JTextField();
		idPeca.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 20));
		idPeca.setBounds(153, 40, 86, 20);
		contentPane.add(idPeca);
		idPeca.setColumns(10);
		
		nomePeca = new JTextField();
		nomePeca.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 20));
		nomePeca.setBounds(153, 68, 86, 20);
		contentPane.add(nomePeca);
		nomePeca.setColumns(10);
		
		JLabel lblNewLabel_1_1 = new JLabel("ID Peça:");
		lblNewLabel_1_1.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 20));
		lblNewLabel_1_1.setBounds(32, 38, 111, 25);
		contentPane.add(lblNewLabel_1_1);
		
		JButton btnPesquisar = new JButton("Pesquisar");
		btnPesquisar.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 20));
		btnPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				crud_consulta_estoque pesquisar = new crud_consulta_estoque();
				pesquisar.Selecionar();
				Connection conexao = ClasseConexao.Conectar();
				ResultSet rs = pesquisar.Selecionar();
			    
			    try {
			        while (rs.next()) {
			        	idPeca.setText(rs.getString(1));
			        	nomePeca.setText(rs.getString(2));
			           
			        }
			    } catch (SQLException erro) {
			        erro.printStackTrace();
			    }
			     finally
			        {
			            ClasseConexao.FecharConexao(conexao);
			            Selecionando();
			        }
			}

		});
		btnPesquisar.setBounds(263, 50, 127, 25);
		contentPane.add(btnPesquisar);
		
		tabela_listagem = new JTable();
		tabela_listagem.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
					int linha = tabela_listagem.getSelectedRow();
					idPeca.setText(tabela_listagem.getModel().getValueAt(linha,0).toString());
					nomePeca.setText(tabela_listagem.getModel().getValueAt(linha,1).toString());
					
			}
		});
		tabela_listagem.setBounds(60, 183, 360, 183);
		contentPane.add(tabela_listagem);
	}

}