package projetoA3;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import net.proteanit.sql.DbUtils;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import com.toedter.calendar.JDateChooser;
import javax.swing.JTextArea;

public class Pedido_de_compra extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNome_pedido;
	private JTextField txtQuantidade_pedido;
	private JButton btnRegistrar_pedido;
	protected JTable tabela_pedido;
	private JButton btnApagarQuantidadePecas;
	private JButton btnVoltar;
	public Crud_pedido_compra Crud_pc = new Crud_pedido_compra();
	private DefaultTableModel modeloTabela;
	JComboBox cbCapacidade = new JComboBox();
	int[] medida = {0};
	String[] capacidade = {""};
	private JTextField txtPrecoCompra;
	private JTextField txtIDPecas;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Pedido_de_compra frame = new Pedido_de_compra();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	//Comando para demonstrar a tabela
	public void Selecionando_pedido()
    {
        Connection conexao = null;
        Statement  comando = null;
        ResultSet  resultado = null;
        try {
            conexao = ClasseConexao.Conectar();
            comando = conexao.createStatement();
            String meu_sql = "SELECT idPecas, nome_pecas, quantidade_total, capacidade, descricao_pecas from cadastro_pecas_estoque ORDER BY idPecas  DESC";
            resultado = comando.executeQuery(meu_sql);
            tabela_pedido.setModel(DbUtils.resultSetToTableModel(resultado));
            tabela_pedido.getColumnModel().getColumn(0).setHeaderValue("ID");
            tabela_pedido.getColumnModel().getColumn(0).setPreferredWidth(25);
            tabela_pedido.getColumnModel().getColumn(1).setHeaderValue("Nome");
            tabela_pedido.getColumnModel().getColumn(1).setPreferredWidth(85);
            tabela_pedido.getColumnModel().getColumn(2).setHeaderValue("Quantidade");
            tabela_pedido.getColumnModel().getColumn(2).setPreferredWidth(60);
            tabela_pedido.getColumnModel().getColumn(3).setHeaderValue("Capacidade");
            tabela_pedido.getColumnModel().getColumn(3).setPreferredWidth(85);
            tabela_pedido.getColumnModel().getColumn(4).setHeaderValue("Descrição");
            tabela_pedido.getColumnModel().getColumn(4).setPreferredWidth(200);
            

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
	
	public Pedido_de_compra() {
		
		setTitle("Pedido de Compras");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(150, 50, 1000, 650);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Nome peça:");
		lblNewLabel.setBounds(30, 52, 99, 27);
		lblNewLabel.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 17));
		contentPane.add(lblNewLabel);
		
		txtNome_pedido = new JTextField();
		txtNome_pedido.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 17));
		txtNome_pedido.setBounds(126, 52, 231, 27);
		contentPane.add(txtNome_pedido);
		txtNome_pedido.setColumns(10);{
			FiltroDeCaracteres filtro = new FiltroDeCaracteres(FiltroDeCaracteres.TipoDeFiltro.nome, 50);
			txtNome_pedido.setDocument(filtro);
		}

		
		
		JTextArea txaDescricao = new JTextArea();
		txaDescricao.setFont(new Font("Courier New", Font.PLAIN, 9));
		txaDescricao.setToolTipText("");
		txaDescricao.setLineWrap(true);
		txaDescricao.setBounds(636, 50, 265, 106);
		contentPane.add(txaDescricao);{
			FiltroDeCaracteres filtro = new FiltroDeCaracteres(FiltroDeCaracteres.TipoDeFiltro.email, 200);
			txaDescricao.setDocument(filtro);
		}
		
		JLabel lblQuantidadeDePeas = new JLabel("Quantidade:");
		lblQuantidadeDePeas.setBounds(30, 92, 99, 27);
		lblQuantidadeDePeas.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 17));
		contentPane.add(lblQuantidadeDePeas);
		
		txtQuantidade_pedido = new JTextField();
		txtQuantidade_pedido.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 17));
		txtQuantidade_pedido.setBounds(126, 92, 231, 27);
		txtQuantidade_pedido.setColumns(10);
		contentPane.add(txtQuantidade_pedido);{
			FiltroDeCaracteres filtro = new FiltroDeCaracteres(FiltroDeCaracteres.TipoDeFiltro.numerosDouble, 20);
			txtQuantidade_pedido.setDocument(filtro);
		}
		
		//Tabela
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(162, 330, 660, 229);
		contentPane.add(scrollPane);
		
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setBounds(87, 172, 294, 27);
		contentPane.add(dateChooser);
		
		//Dados da tabela, com capacidade de puxar os itens para os JField
		tabela_pedido = new JTable(modeloTabela);
		tabela_pedido.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 12));
		scrollPane.setViewportView(tabela_pedido);
		tabela_pedido.setCellSelectionEnabled(true);
		tabela_pedido.setColumnSelectionAllowed(true);
		tabela_pedido.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int linha = tabela_pedido.getSelectedRow();
				txtIDPecas.setText(tabela_pedido.getModel().getValueAt(linha,0).toString());
				txtNome_pedido.setText(tabela_pedido.getModel().getValueAt(linha,1).toString());
				 Object capacidadeSelecionada = tabela_pedido.getModel().getValueAt(linha, 3);
			     cbCapacidade.setSelectedItem(capacidadeSelecionada); 
			}
		});
		Selecionando_pedido();
		
		//Botão para Salvar o pedido de compra e manda para listagem.
		btnRegistrar_pedido = new JButton("Salvar");
		btnRegistrar_pedido.setToolTipText("Salvar produto e mandar para listagem");
		btnRegistrar_pedido.setBounds(139, 219, 131, 27);
		btnRegistrar_pedido.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 17));
		btnRegistrar_pedido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if ((txtNome_pedido.getText().isBlank() ||
						 txtQuantidade_pedido.getText().isBlank() ||
					     txtPrecoCompra.getText().isBlank() ||
					     txaDescricao.getText().isBlank())) {
						 JOptionPane.showMessageDialog(null, "Preencha todos os campos!");
						 return;
				}else if(dateChooser.getDate() == null) {
					JOptionPane.showMessageDialog(null, "Preencha a data corretamente! Usando o calendário ao lado do campo!");
					return;
				}
					java.sql.Date data_pedido = new java.sql.Date(dateChooser.getDate().getTime());
					Crud_pc.Inserir(Double.parseDouble(txtQuantidade_pedido.getText()),data_pedido,
							Double.parseDouble(txtPrecoCompra.getText()),txaDescricao.getText(),txtNome_pedido.getText(), Integer.parseInt(txtIDPecas.getText()));
					Selecionando_pedido();}
		});
		contentPane.add(btnRegistrar_pedido);
		
		//Botão para ir para tabela listagem
		JButton btnListar = new JButton("->");
		btnListar.setBounds(651, 290, 131, 27);
		btnListar.setToolTipText("Ir para tela de listagem\r\n");
		btnListar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Listar_pecas link_listar = new Listar_pecas();
				link_listar.setVisible(true);
			}
		});
		btnListar.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 17));
		contentPane.add(btnListar);
		
		JButton btnApagarNomePeca = new JButton("X");
		btnApagarNomePeca.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtNome_pedido.setText("");
			}
		});
		btnApagarNomePeca.setBounds(364, 56, 17, 17);
		contentPane.add(btnApagarNomePeca);
		
		btnApagarQuantidadePecas = new JButton("X");
		btnApagarQuantidadePecas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtQuantidade_pedido.setText("");
			}
		});
		btnApagarQuantidadePecas.setBounds(364, 98, 17, 17);
		contentPane.add(btnApagarQuantidadePecas);
		
		//Botão voltar para o menu principal
		btnVoltar = new JButton("Voltar");
		btnVoltar.setToolTipText("Voltar para o menu");
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Menu_principal menu = new Menu_principal();
				menu.setVisible(true);
				dispose();
			}
		});
		btnVoltar.setBounds(10, 560, 131, 27);
		btnVoltar.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 17));
		contentPane.add(btnVoltar);
		
		
		//Botão para pesquisar uma peça através do nome, usando comandos para demonstrar tal conteúdo na tabela
		JButton btnPesquisar_pedido = new JButton("Pesquisar");
		btnPesquisar_pedido.setToolTipText("Pesquisar através do nome");
		btnPesquisar_pedido.setBounds(392, 46, 131, 30);
		btnPesquisar_pedido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 ResultSet rs =Crud_pc.Selecionar(txtNome_pedido.getText());
	                DefaultTableModel model = (DefaultTableModel) tabela_pedido.getModel();
	                model.setRowCount(0);


	                try {
	                    while (rs != null && rs.next()) {
	                        model.addRow(new Object[]{rs.getString("idPecas"), 
	                        		rs.getString("nome_pecas"), rs.getString("quantidade_total"),
	                        		rs.getString("capacidade"),rs.getString("descricao_pecas")});
	                    }

	                } catch (SQLException e2) {
	                    e2.printStackTrace();
	                }

			}
		});
		btnPesquisar_pedido.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 17));
		contentPane.add(btnPesquisar_pedido);
		
		JLabel lblTabelaListagem = new JLabel("Tabela Listagem:");
		lblTabelaListagem.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 17));
		lblTabelaListagem.setBounds(648, 252, 157, 27);
		contentPane.add(lblTabelaListagem);
	
		JLabel dataTEXT = new JLabel("Data:");
		dataTEXT.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 17));
		dataTEXT.setBounds(30, 172, 47, 27);
		contentPane.add(dataTEXT);
		
		
		
		JLabel labelData = new JLabel("Descrição:");
		labelData.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 17));
		labelData.setBounds(540, 50, 203, 27);
		contentPane.add(labelData);
		
		//Botão de recarregar a tabela
		JButton btnRecaregar = new JButton("Recarregar");
		btnRecaregar.setToolTipText("Recarregar a tabela");
		btnRecaregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Selecionando_pedido();
			}
		});
		btnRecaregar.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 17));
		btnRecaregar.setBounds(173, 290, 131, 27);
		contentPane.add(btnRecaregar);
		
		txtPrecoCompra = new JTextField();
		txtPrecoCompra.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 17));
		txtPrecoCompra.setColumns(10);
		txtPrecoCompra.setBounds(126, 132, 231, 27);
		contentPane.add(txtPrecoCompra);{
			FiltroDeCaracteres filtro = new FiltroDeCaracteres(FiltroDeCaracteres.TipoDeFiltro.numerosDouble, 20);
			txtPrecoCompra.setDocument(filtro);
		}
		
		JLabel lblPrecoCompra = new JLabel("Preço  total:");
		lblPrecoCompra.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 17));
		lblPrecoCompra.setBounds(30, 132, 99, 27);
		contentPane.add(lblPrecoCompra);
		
		JButton btnApagarPrecoCompra = new JButton("X");
		btnApagarPrecoCompra.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtPrecoCompra.setText("");
			}
		});
		btnApagarPrecoCompra.setBounds(364, 136, 17, 17);
		contentPane.add(btnApagarPrecoCompra);
		
		JButton btnApagaDescricao = new JButton("X");
		btnApagaDescricao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txaDescricao.setText("");
			}
		});
		btnApagaDescricao.setBounds(911, 54, 17, 17);
		contentPane.add(btnApagaDescricao);
		
		JLabel lblTabela = new JLabel("Tabela de produtos registrados");
		lblTabela.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 17));
		lblTabela.setBounds(367, 298, 250, 27);
		contentPane.add(lblTabela);
		
		
		JLabel lblIdPea = new JLabel("ID da peça:");
		lblIdPea.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 17));
		lblIdPea.setBounds(30, 12, 99, 27);
		contentPane.add(lblIdPea);
		
		txtIDPecas = new JTextField();
		txtIDPecas.setEditable(false);
		txtIDPecas.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 17));
		txtIDPecas.setColumns(10);
		txtIDPecas.setBounds(126, 12, 231, 27);
		contentPane.add(txtIDPecas);
	
	}
}