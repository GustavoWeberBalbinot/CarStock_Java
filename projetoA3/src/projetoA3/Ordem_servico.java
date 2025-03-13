package projetoA3;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JTable;
import java.awt.Color;
import java.awt.Component;

import com.toedter.calendar.JDateChooser;

import net.proteanit.sql.DbUtils;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JScrollPane;

public class Ordem_servico extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtCliente_ordem;
	private JTextField txtNome_peca_ordem;
	private JTextField txt_Comprada;
	private JTextField txtValor_servico;
	private JTable tabela_cliente_ordem;
	private JTable tabela_pecas_ordem;
	private JTextArea taDescricao; // Declare the JTextArea
	Crud_ordem_serviço crudServico = new Crud_ordem_serviço();
	Baixa_estoque be = new Baixa_estoque();
	private JTextField txtCPF_ordem;
	private JTextField txtID_pcs_ordem;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ordem_servico frame = new Ordem_servico();
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
	
	public void Selecionando_cliente_ordem()
	{
		// Precisamos criar algumas variáveis para conectar, dar comandos e
		// também para armazenar a pesquisa com o SELECT:
		Connection conexao = null;
		Statement  comando = null;
		ResultSet  resultado = null;
		try {
			conexao = ClasseConexao.Conectar();
			comando = conexao.createStatement();
			String meu_sql = "SELECT idCliente, nome_cliente, cpf FROM cadastro_cliente";
			resultado = comando.executeQuery(meu_sql); // Executa o SQL
			tabela_cliente_ordem.setModel(DbUtils.resultSetToTableModel(resultado));

			
			//Formatar Colunas
			tabela_cliente_ordem.getColumnModel().getColumn(0).setHeaderValue("ID");
			tabela_cliente_ordem.getColumnModel().getColumn(1).setHeaderValue("Nome do cliente");
			tabela_cliente_ordem.getColumnModel().getColumn(2).setHeaderValue("CPF");
			tabela_cliente_ordem.getColumnModel().getColumn(0).setPreferredWidth(35);
			tabela_cliente_ordem.getColumnModel().getColumn(1).setPreferredWidth(150);
			tabela_cliente_ordem.getColumnModel().getColumn(2).setPreferredWidth(70);		
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
	
	public void Selecionando_pecas_ordem()
	{
		// Precisamos criar algumas variáveis para conectar, dar comandos e
		// também para armazenar a pesquisa com o SELECT:
		Connection conexao = null;
		Statement  comando = null;
		ResultSet  resultado = null;
		try {
			conexao = ClasseConexao.Conectar();
			comando = conexao.createStatement();
			String meu_sql = "SELECT idPecas, nome_pecas, preco_venda FROM cadastro_pecas_estoque";
			resultado = comando.executeQuery(meu_sql); // Executa o SQL
			tabela_pecas_ordem.setModel(DbUtils.resultSetToTableModel(resultado));

			
			//Formatar Colunas
			tabela_pecas_ordem.getColumnModel().getColumn(0).setHeaderValue("ID");
			tabela_pecas_ordem.getColumnModel().getColumn(1).setHeaderValue("Nome da peça");
			tabela_pecas_ordem.getColumnModel().getColumn(2).setHeaderValue("Preço de venda");
			tabela_pecas_ordem.getColumnModel().getColumn(0).setPreferredWidth(35);
			tabela_pecas_ordem.getColumnModel().getColumn(1).setPreferredWidth(150);
			tabela_pecas_ordem.getColumnModel().getColumn(2).setPreferredWidth(70);

				
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
	
	
	
	public Ordem_servico() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1083, 688);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(214, 215, 237));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Nome Cliente:");
		lblNewLabel.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 20));
		lblNewLabel.setBounds(10, 26, 135, 27);
		contentPane.add(lblNewLabel);

		JLabel lblIdCliente = new JLabel("Nome Peça:");
		lblIdCliente.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 20));
		lblIdCliente.setBounds(10, 87, 118, 27);
		contentPane.add(lblIdCliente);

		JLabel lblNewLabel_1_1 = new JLabel("Descrição:");
		lblNewLabel_1_1.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 20));
		lblNewLabel_1_1.setBounds(516, 44, 100, 18);
		contentPane.add(lblNewLabel_1_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 281, 521, 283);
		contentPane.add(scrollPane);
		
				tabela_cliente_ordem = new JTable();
				scrollPane.setViewportView(tabela_cliente_ordem);
				tabela_cliente_ordem.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						int linha = tabela_cliente_ordem.getSelectedRow();
						txtCliente_ordem.setText(tabela_cliente_ordem.getModel().getValueAt(linha,1).toString());
						txtCPF_ordem.setText(tabela_cliente_ordem.getModel().getValueAt(linha,2).toString());
					}
				});{
			Selecionando_cliente_ordem();
		}
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(536, 281, 521, 283);
		contentPane.add(scrollPane_1);
		
				tabela_pecas_ordem = new JTable();
				scrollPane_1.setViewportView(tabela_pecas_ordem);
				tabela_pecas_ordem.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						int linha = tabela_pecas_ordem.getSelectedRow();
						txtID_pcs_ordem.setText(tabela_pecas_ordem.getModel().getValueAt(linha,0).toString());
						txtNome_peca_ordem.setText(tabela_pecas_ordem.getModel().getValueAt(linha,1).toString());
					}
				});
		Selecionando_pecas_ordem();

		txtCliente_ordem = new JTextField();
		txtCliente_ordem.setBounds(137, 25, 164, 27);
		contentPane.add(txtCliente_ordem);
		txtCliente_ordem.setColumns(10);{
			FiltroDeCaracteres filtro = new FiltroDeCaracteres(FiltroDeCaracteres.TipoDeFiltro.nome, 50);
			txtCliente_ordem.setDocument(filtro);
		}

		txtNome_peca_ordem = new JTextField();
		txtNome_peca_ordem.setColumns(10);
		txtNome_peca_ordem.setBounds(125, 86, 176, 27);
		contentPane.add(txtNome_peca_ordem);{
			FiltroDeCaracteres filtro = new FiltroDeCaracteres(FiltroDeCaracteres.TipoDeFiltro.nome, 50);
			txtNome_peca_ordem.setDocument(filtro);
		}
		
		
		JDateChooser data_ordem = new JDateChooser();
		data_ordem.setBounds(61, 210, 239, 27);
		data_ordem.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 15));
		contentPane.add(data_ordem);

		JButton btnNewButton = new JButton("Baixa Estoque");
		btnNewButton.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 20));
		btnNewButton.setToolTipText("Baixa dos produtos em estoque");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				be.setVisible(true);
			}
		});
		btnNewButton.setBounds(881, 248, 176, 27);
		contentPane.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Confirmar");
		btnNewButton_1.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 20));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//Verificação para não aceitar campo em branco
				if ((txtCliente_ordem.getText().isBlank() ||
						 txtCPF_ordem.getText().isBlank() ||
					     txtNome_peca_ordem.getText().isBlank() ||
					     txtID_pcs_ordem.getText().isBlank() ||
					     txtValor_servico.getText().isBlank() ||
						 txt_Comprada.getText().isBlank()||
					     taDescricao.getText().isBlank())) {
						 JOptionPane.showMessageDialog(null, "Preencha todos os campos!");
						 return;
				}else if(data_ordem.getDate() == null) {
					JOptionPane.showMessageDialog(null, "Preencha a data corretamente! Usando o calendário ao lado do campo!");
					return;
				}
				Random random = new Random();
		        
		        // Define o intervalo (10^11 até 10^12 - 1) para garantir que o número tenha 12 dígitos
		        BigInteger min = new BigInteger("100000000000");
		        BigInteger max = new BigInteger("999999999999");
		        
		        // Calcula o intervalo (max - min + 1)
		        BigInteger intervalo = max.subtract(min).add(BigInteger.ONE);
		        
		        // Gera um número aleatório dentro do intervalo
		        BigInteger numeroAleatorio;
		        do {
		            numeroAleatorio = new BigInteger(max.bitLength(), random);
		        } while (numeroAleatorio.compareTo(intervalo) >= 0);
		        
		        // Adiciona o número mínimo para garantir 12 dígitos
		        numeroAleatorio = numeroAleatorio.add(min);
		        
		        
		        double valorServico = Double.parseDouble(txtValor_servico.getText());
		        double comprada = Double.parseDouble(txt_Comprada.getText());
		        long numero = numeroAleatorio.longValue(); // Use long para números grandes
		        java.sql.Date dia_ordem = new java.sql.Date(data_ordem.getDate().getTime());
		        String nomePecaOrdem = txtNome_peca_ordem.getText();
		        int idPcsOrdem= Integer.parseInt(txtID_pcs_ordem.getText());
		        int cpf_ordem= Integer.parseInt(txtCPF_ordem.getText());
		        int conclusao_servico = 0;
		        String descricao = taDescricao.getText(); 
		        crudServico.Inserir(valorServico, comprada, numero, dia_ordem, conclusao_servico, descricao, nomePecaOrdem, cpf_ordem, idPcsOrdem);
		        be.setVisible(true);
			}
		});
		btnNewButton_1.setBounds(789, 104, 135, 27);
		contentPane.add(btnNewButton_1);

		taDescricao = new JTextArea();
		taDescricao.setFont(new Font("Tahoma", Font.PLAIN, 11));
		taDescricao.setBounds(619, 11, 332, 83);
		contentPane.add(taDescricao);{
			FiltroDeCaracteres filtro = new FiltroDeCaracteres(FiltroDeCaracteres.TipoDeFiltro.email, 100);
			taDescricao.setDocument(filtro);
		}

		JButton btnNewButton_2 = new JButton("New button");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtCliente_ordem.setText("");
			}
		});
		btnNewButton_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnNewButton_2.setBounds(304, 30, 17, 17);
		contentPane.add(btnNewButton_2);
		JButton btnNewButton_2_1 = new JButton("New button");
		btnNewButton_2_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnNewButton_2_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtNome_peca_ordem.setText("");
			}
		});
		btnNewButton_2_1.setBounds(304, 91, 17, 17);
		contentPane.add(btnNewButton_2_1);

		JLabel lblValorServio = new JLabel("Valor serviço:");
		lblValorServio.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 20));
		lblValorServio.setBounds(10, 146, 135, 27);
		contentPane.add(lblValorServio);

		JLabel lblQuantidadeComprada = new JLabel("Quantidade comprada:");
		lblQuantidadeComprada.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 20));
		lblQuantidadeComprada.setBounds(10, 179, 212, 27);
		contentPane.add(lblQuantidadeComprada);

		txt_Comprada = new JTextField();
		txt_Comprada.setColumns(10);
		txt_Comprada.setBounds(215, 178, 86, 27);
		contentPane.add(txt_Comprada);{
			FiltroDeCaracteres filtro = new FiltroDeCaracteres(FiltroDeCaracteres.TipoDeFiltro.numerosDouble, 20);
			txt_Comprada.setDocument(filtro);
		}

		txtValor_servico = new JTextField();
		txtValor_servico.setColumns(10);
		txtValor_servico.setBounds(137, 148, 164, 27);
		contentPane.add(txtValor_servico);{
			FiltroDeCaracteres filtro = new FiltroDeCaracteres(FiltroDeCaracteres.TipoDeFiltro.numerosDouble, 20);
			txtValor_servico.setDocument(filtro);
		}

		JLabel lblValorServio_1_1 = new JLabel("Data:");
		lblValorServio_1_1.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 20));
		lblValorServio_1_1.setBounds(10, 209, 53, 27);
		contentPane.add(lblValorServio_1_1);
		


		JButton btnNewButton_1_1 = new JButton("Pesquisar");
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				ResultSet rs = crudServico.Selecionar(txtCliente_ordem.getText());
		        DefaultTableModel model = (DefaultTableModel) tabela_cliente_ordem.getModel();
		        model.setRowCount(0);
		    

		        try {
		            while (rs != null && rs.next()) {
		            	model.addRow(new Object[]{rs.getString("idCliente"), rs.getString("nome_cliente"), rs.getString("cpf")});
		            }
		            
		        } catch (SQLException e2) {
		            e2.printStackTrace();
		        }
			}
		});
		btnNewButton_1_1.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 20));
		btnNewButton_1_1.setBounds(335, 30, 135, 27);
		contentPane.add(btnNewButton_1_1);
		
		JButton btnNewButton_1_1_1 = new JButton("Pesquisar");
		btnNewButton_1_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				ResultSet rs = crudServico.Selecionar2(txtNome_peca_ordem.getText());
		        DefaultTableModel model = (DefaultTableModel) tabela_pecas_ordem.getModel();
		        model.setRowCount(0);
		    

		        try {
		            while (rs != null && rs.next()) {
		            	model.addRow(new Object[]{rs.getString("idPecas"), rs.getString("nome_pecas"), rs.getString("preco_venda")});
		            }
		            
		        } catch (SQLException e2) {
		            e2.printStackTrace();
		        }
			}
		});
		btnNewButton_1_1_1.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 20));
		btnNewButton_1_1_1.setBounds(335, 91, 135, 27);
		contentPane.add(btnNewButton_1_1_1);
		
		JButton btnNewButton_1_1_2 = new JButton("Redefinir");
		btnNewButton_1_1_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Component[] components = contentPane.getComponents();
		        for (Component component : components) {
		            if (component instanceof JTextField) {
		                // Limpar o texto dos JTextField
		                ((JTextField) component).setText("");
		            } else if (component instanceof JTextArea) {
		                // Limpar o texto dos JTextArea
		                ((JTextArea) component).setText("");
		            } else if (component instanceof JDateChooser) {
		            	((JDateChooser) component).setDate(null);
		            }
		        }
			}
		});
		btnNewButton_1_1_2.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 20));
		btnNewButton_1_1_2.setBounds(661, 104, 118, 27);
		contentPane.add(btnNewButton_1_1_2);
		
		JButton btnNewButton_2_1_1 = new JButton("New button");
		btnNewButton_2_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtValor_servico.setText("");
			}
		});
		btnNewButton_2_1_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnNewButton_2_1_1.setBounds(304, 153, 17, 17);
		contentPane.add(btnNewButton_2_1_1);
		
		JButton btnNewButton_2_1_1_1 = new JButton("New button");
		btnNewButton_2_1_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txt_Comprada.setText("");
			}
		});
		btnNewButton_2_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnNewButton_2_1_1_1.setBounds(304, 183, 17, 17);
		contentPane.add(btnNewButton_2_1_1_1);
		
		JButton btnNewButton_2_1_1_2 = new JButton("New button");
		btnNewButton_2_1_1_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				taDescricao.setText("");
			}
			
		});
		btnNewButton_2_1_1_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnNewButton_2_1_1_2.setBounds(961, 32, 17, 17);
		contentPane.add(btnNewButton_2_1_1_2);
		
		txtCPF_ordem = new JTextField();
		txtCPF_ordem.setEditable(false);
		txtCPF_ordem.setColumns(10);
		txtCPF_ordem.setBounds(61, 56, 240, 27);
		contentPane.add(txtCPF_ordem);{
			FiltroDeCaracteres filtro = new FiltroDeCaracteres(FiltroDeCaracteres.TipoDeFiltro.numerosInt, 11);
			txtCPF_ordem.setDocument(filtro);
		}
		
		JLabel lblCpf = new JLabel("CPF:");
		lblCpf.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 20));
		lblCpf.setBounds(10, 57, 118, 27);
		contentPane.add(lblCpf);
		
		JButton btnNewButton_2_1_2 = new JButton("New button");
		btnNewButton_2_1_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtCPF_ordem.setText("");
			}
		});
		btnNewButton_2_1_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnNewButton_2_1_2.setBounds(304, 61, 17, 17);
		contentPane.add(btnNewButton_2_1_2);
		
		txtID_pcs_ordem = new JTextField();
		txtID_pcs_ordem.setEditable(false);
		txtID_pcs_ordem.setColumns(10);
		txtID_pcs_ordem.setBounds(125, 117, 176, 27);
		contentPane.add(txtID_pcs_ordem);
		
		JLabel lblIdDaPeas = new JLabel("ID da peças:");
		lblIdDaPeas.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 20));
		lblIdDaPeas.setBounds(10, 118, 135, 27);
		contentPane.add(lblIdDaPeas);
		
		JButton btnNewButton_2_1_1_3 = new JButton("New button");
		btnNewButton_2_1_1_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtID_pcs_ordem.setText("");
			}
		});
		btnNewButton_2_1_1_3.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnNewButton_2_1_1_3.setBounds(304, 122, 17, 17);
		contentPane.add(btnNewButton_2_1_1_3);
		
		JButton btnNewButton_1_1_2_1 = new JButton("<<");
		btnNewButton_1_1_2_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Menu_principal menu = new Menu_principal();
				menu.setVisible(true);
				dispose();
			}
		});
		btnNewButton_1_1_2_1.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 20));
		btnNewButton_1_1_2_1.setBounds(10, 598, 118, 27);
		contentPane.add(btnNewButton_1_1_2_1);

		
		
	}
}
