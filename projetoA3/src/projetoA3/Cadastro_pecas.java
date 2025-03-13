package projetoA3;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;

import java.awt.Font;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import com.toedter.calendar.JDateChooser;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;


public class Cadastro_pecas extends javax.swing.JFrame  {

	//Componentes
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNome_pecas;
	private JTextField txtQuantidade_comprada;
	private JTextField txtPreco_compra;
	private JTable tabela_cadastro_pecas;
	private JTextField txtIDpecas;
	private JTextField txtFornecedor;
	private JTextField txtContato_fornecedor;
	//Deixa em branco o campo de texto
	private void limpatxt(JTextField txt){
		txt.setText("");
	}
	
	//Deixa em branco a área de texto
	private void limpata(JTextArea ta){
		ta.setText("");
	}
	
	//Deixa em branco todos os campos de texto
	private void limparTodosJTextFields() {
	    List<JTextField> textFields = Arrays.asList(txtNome_pecas, txtQuantidade_comprada, txtPreco_compra, txtContato_fornecedor, txtFornecedor);
	    for (JTextField txt : textFields) {
	        txt.setText("");
	    }
	}
	//Crud utilizado
	Crud_cadastro_estoque cde = new Crud_cadastro_estoque();


	//Inicia
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Cadastro_pecas frame = new Cadastro_pecas();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	//Tabela com consulta do banco de dados
	public void Selecionando_cadastro_estoque()
    {
        // Precisamos criar algumas variáveis para conectar, dar comandos e
        // também para armazenar a pesquisa com o SELECT:
        Connection conexao = null;
        Statement  comando = null;
        ResultSet  resultado = null;
        try {
        	//Conecta com o banco de dados e faz a consulta
            conexao = ClasseConexao.Conectar();
            comando = conexao.createStatement();
            String meu_sql = "SELECT * FROM cadastro_pecas_estoque";
            resultado = comando.executeQuery(meu_sql); // Executa o SQL
            
            //Pega o resultado e coloca na tabela
            tabela_cadastro_pecas.setModel(DbUtils.resultSetToTableModel(resultado));
            //Formatação da coluna(nome e tamanho)
            tabela_cadastro_pecas.getColumnModel().getColumn(0).setHeaderValue("ID");
            tabela_cadastro_pecas.getColumnModel().getColumn(1).setHeaderValue("Nome");
            tabela_cadastro_pecas.getColumnModel().getColumn(2).setHeaderValue("Quantidade");
            tabela_cadastro_pecas.getColumnModel().getColumn(3).setHeaderValue("Capacidade");
            tabela_cadastro_pecas.getColumnModel().getColumn(4).setHeaderValue("Fornecedor");
            tabela_cadastro_pecas.getColumnModel().getColumn(5).setHeaderValue("Contato");
            tabela_cadastro_pecas.getColumnModel().getColumn(6).setHeaderValue("Preço de compra");
            tabela_cadastro_pecas.getColumnModel().getColumn(7).setHeaderValue("Preço total");
            tabela_cadastro_pecas.getColumnModel().getColumn(8).setHeaderValue("Preço de venda");
            tabela_cadastro_pecas.getColumnModel().getColumn(9).setHeaderValue("Data de entrada");
            tabela_cadastro_pecas.getColumnModel().getColumn(10).setHeaderValue("Descrição");
            tabela_cadastro_pecas.getColumnModel().getColumn(0).setPreferredWidth(35);
            tabela_cadastro_pecas.getColumnModel().getColumn(1).setPreferredWidth(100);
            tabela_cadastro_pecas.getColumnModel().getColumn(2).setPreferredWidth(110);
            tabela_cadastro_pecas.getColumnModel().getColumn(3).setPreferredWidth(110);
            tabela_cadastro_pecas.getColumnModel().getColumn(4).setPreferredWidth(180);
            tabela_cadastro_pecas.getColumnModel().getColumn(5).setPreferredWidth(50);
            tabela_cadastro_pecas.getColumnModel().getColumn(6).setPreferredWidth(160);
            tabela_cadastro_pecas.getColumnModel().getColumn(7).setPreferredWidth(140);
            tabela_cadastro_pecas.getColumnModel().getColumn(8).setPreferredWidth(140);
            tabela_cadastro_pecas.getColumnModel().getColumn(9).setPreferredWidth(150);
            tabela_cadastro_pecas.getColumnModel().getColumn(10).setPreferredWidth(50);
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
	
	//Jframe com todos os componentes
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Cadastro_pecas() {
		//Variáveis para poder salvar a combobox cbCapacidade
		int[] medida = {0};
	    String[] capacidade = {""}; //Usando Array para o Switch
	    
	    //Propriedades da janela
		setBackground(new Color(240, 240, 240));
		setResizable(false);
		setTitle("Cadastro de peças");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1400, 700);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(214, 215, 237));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//Propriedades dessa label
		JLabel lblNome_peca = new JLabel("Nome da peças:");
		lblNome_peca.setBounds(38, 28, 164, 27);
		lblNome_peca.setBackground(new Color(255, 255, 255));
		lblNome_peca.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 20));
		contentPane.add(lblNome_peca);
		
		//Propriedades dessa label
		JLabel lblQuantiedadeComprada = new JLabel("Quantidade comprada:");
		lblQuantiedadeComprada.setBounds(38, 78, 209, 27);
		lblQuantiedadeComprada.setBackground(new Color(255, 255, 255));
		lblQuantiedadeComprada.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 20));
		contentPane.add(lblQuantiedadeComprada);
		
		//Propriedades dessa label
		JLabel lblPreoDeCompra = new JLabel("Preço de compra:");
		lblPreoDeCompra.setBounds(38, 278, 164, 27);
		lblPreoDeCompra.setBackground(new Color(255, 255, 255));
		lblPreoDeCompra.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 20));
		contentPane.add(lblPreoDeCompra);
		
		//Propriedades desse campo de texto
		txtNome_pecas = new JTextField();
		txtNome_pecas.setBounds(191, 28, 171, 27);
		txtNome_pecas.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 15));
		contentPane.add(txtNome_pecas);
		txtNome_pecas.setColumns(10);{
			//Para filtrar o txtNome_pecas
			FiltroDeCaracteres filtro = new FiltroDeCaracteres(FiltroDeCaracteres.TipoDeFiltro.nome, 50);
			txtNome_pecas.setDocument(filtro);
		}
		
		//Propriedades dessa JComboBox
		JComboBox<?> cbCapacidade = new JComboBox();
		cbCapacidade.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 15));
		cbCapacidade.setModel(new DefaultComboBoxModel(new String[] {"Selecione uma medida", "Unidade", "Kg", "g", "L", "mL"}));
		cbCapacidade.setBounds(147, 128, 215, 27);
		contentPane.add(cbCapacidade);{
			//Quando clicar na JComboBox
			cbCapacidade.addActionListener(e -> {
				//Seleciona o campo clicado e coverte em String
	            String selectedItem = (String) cbCapacidade.getSelectedItem();
	            //Switch para quando escolher um campo atribuir a medida[]
	            switch (selectedItem) {
	            case "Selecione uma medida":
	            	medida[0] = 0;
	                break;
	            case "Unidade":
	                medida[0] = 1;
	                capacidade[0] = "Unidade";
	                break;
	            case "Kg":
	                medida[0] = 2;
	                capacidade[0] = "Kg";
	                break;
	            case "g":
	                medida[0] = 3;
	                capacidade[0] = "g";
	                break;
	            case "L":
	                medida[0] = 4;
	                capacidade[0] = "L";
	                break;
	            case "mL":
	                medida[0] = 5;
	                capacidade[0] = "mL";
	                break;
	            }

	        });
		}
		
		//Propriedades desse campo de texto
		txtQuantidade_comprada = new JTextField();
		txtQuantidade_comprada.setBounds(242, 78, 120, 27);
		txtQuantidade_comprada.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 15));
		txtQuantidade_comprada.setColumns(10);
		contentPane.add(txtQuantidade_comprada);{
			//Filtragem para aceitar apenas numeros e (,.)
			FiltroDeCaracteres filtro = new FiltroDeCaracteres(FiltroDeCaracteres.TipoDeFiltro.numerosDouble, 20);
			txtQuantidade_comprada.setDocument(filtro);
		}
		
		//Propriedades desse campo de texto
		txtPreco_compra = new JTextField();
		txtPreco_compra.setBounds(198, 278, 164, 27);
		txtPreco_compra.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 15));
		txtPreco_compra.setColumns(10);
		contentPane.add(txtPreco_compra);{
			//Filtragem para aceitar apenas numeros e (,.)
			FiltroDeCaracteres filtro = new FiltroDeCaracteres(FiltroDeCaracteres.TipoDeFiltro.numerosDouble, 20);
			txtPreco_compra.setDocument(filtro);
		}
		
		//Propriedades dessa data
		JDateChooser data_entrada = new JDateChooser();
		data_entrada.setBounds(191, 328, 171, 27);
		data_entrada.setDateFormatString("dd'-'MM'-'yyyy");
		contentPane.add(data_entrada);
		JLabel lblFornecedor = new JLabel("Fornecedor:");
		lblFornecedor.setBounds(38, 178, 108, 27);
		lblFornecedor.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 20));
		lblFornecedor.setBackground(Color.WHITE);
		contentPane.add(lblFornecedor);
		
		//Quando clicar nesse botão
		JButton btnApaga_preco_compra_1 = new JButton("Voltar");
		btnApaga_preco_compra_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Seta o limpatxt para essa variável(txtFornecedor)
				limpatxt(txtFornecedor);
			}
		});
		
		//Propriedades desse campo de texto
		txtFornecedor = new JTextField();
		txtFornecedor.setBounds(147, 178, 215, 27);
		txtFornecedor.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 15));
		txtFornecedor.setColumns(10);
		contentPane.add(txtFornecedor);{
			//Para filtrar o fornecedor
			FiltroDeCaracteres filtro = new FiltroDeCaracteres(FiltroDeCaracteres.TipoDeFiltro.nome, 50);
			txtFornecedor.setDocument(filtro);
		}
		
		//Propriedades desse botão
		btnApaga_preco_compra_1.setBounds(372, 183, 17, 17);
		btnApaga_preco_compra_1.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 17));
		contentPane.add(btnApaga_preco_compra_1);
		
		//Propriedades desse campo de texto
		txtContato_fornecedor = new JTextField();
		txtContato_fornecedor.setBounds(116, 228, 246, 27);
		txtContato_fornecedor.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 15));
		txtContato_fornecedor.setColumns(10);
		contentPane.add(txtContato_fornecedor);{
			//Para filtrar o txtContato_fornecedor
			FiltroDeCaracteres filtro = new FiltroDeCaracteres(FiltroDeCaracteres.TipoDeFiltro.email, 100);
			txtContato_fornecedor.setDocument(filtro);
		}
		
		//Propriedades dessa label
		JLabel lblPreoDeCompra_1_1 = new JLabel("Contato:");
		lblPreoDeCompra_1_1.setBounds(38, 228, 83, 27);
		lblPreoDeCompra_1_1.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 20));
		lblPreoDeCompra_1_1.setBackground(Color.WHITE);
		contentPane.add(lblPreoDeCompra_1_1);
		
		//Quando clicar nesse botão
		JButton btnApaga_preco_compra_1_1 = new JButton("Voltar");
		btnApaga_preco_compra_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Seta o limpatxt para essa variável(txtContato_fornecedor)
				limpatxt(txtContato_fornecedor);
			}
		});
		//Propriedades desse botão
		btnApaga_preco_compra_1_1.setBounds(372, 233, 17, 17);
		btnApaga_preco_compra_1_1.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 17));
		contentPane.add(btnApaga_preco_compra_1_1);
		
		//Propriedades dessa área de texto
		JTextArea taDescricao = new JTextArea();
		taDescricao.setLineWrap(true);
		taDescricao.setBounds(722, 28, 463, 72);
		contentPane.add(taDescricao);{
			//Para filtrar o taDescricao
			FiltroDeCaracteres filtro = new FiltroDeCaracteres(FiltroDeCaracteres.TipoDeFiltro.email, 300);
			taDescricao.setDocument(filtro);
		}
		
		//Quando clicar nesse botão
		JButton btnCadastro_pecas = new JButton("Cadastrar");
		btnCadastro_pecas.setBounds(67, 400, 127, 27);
		btnCadastro_pecas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//Verificação para não aceitar campo em branco
				if ((txtNome_pecas.getText().isBlank() ||
						 txtQuantidade_comprada.getText().isBlank() ||
					     txtFornecedor.getText().isBlank() ||
					     txtContato_fornecedor.getText().isBlank() ||
					     txtPreco_compra.getText().isBlank() ||
						 taDescricao.getText().isBlank())) {
						 JOptionPane.showMessageDialog(null, "Preencha todos os campos!");
						 return;
				}else if(data_entrada.getDate() == null) {
					JOptionPane.showMessageDialog(null, "Preencha a data corretamente! Usando o calendário ao lado do campo!");
					return;
				}else if (medida[0] == 0){
                    JOptionPane.showMessageDialog(null, "Selecione uma medida");
                    return;
                }
				else {
					//Convete a data para data so sql
					java.sql.Date diaEntrada = new java.sql.Date(data_entrada.getDate().getTime());
					
					//Usando o Crud
					cde.Inserir(txtNome_pecas.getText(), Double.parseDouble(txtQuantidade_comprada.getText()), capacidade[0],txtFornecedor.getText() , txtContato_fornecedor.getText(), Double.parseDouble(txtPreco_compra.getText()), diaEntrada, taDescricao.getText());

				//Converter virgula para ponto
				/*
				String converteV = txtPreco_venda.getText() ;
				double d = Double.parseDouble(converteV.replace(",", "."));
				*/
					
					//Para atualizar a tabela depois de clicar n0 botão
					Selecionando_cadastro_estoque();
				
				}
				
			}
			
		});

		//Propriedades desse botão
		btnCadastro_pecas.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 17));
		contentPane.add(btnCadastro_pecas);
		
		//Propriedades dessa label
		JLabel lblDataDaEntrada = new JLabel("Data de entrada:");
		lblDataDaEntrada.setBounds(38, 328, 156, 27);
		lblDataDaEntrada.setBackground(new Color(255, 255, 255));
		lblDataDaEntrada.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 20));
		contentPane.add(lblDataDaEntrada);
		
		//Propriedades dessa rolagem de mouse
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(460, 178, 887, 419);
		contentPane.add(scrollPane);
		
		//Propriedades dessa tabela
		tabela_cadastro_pecas = new JTable();
		tabela_cadastro_pecas.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 12));
		tabela_cadastro_pecas.addMouseListener(new MouseAdapter() {
			@Override
			//Quando clicar na tabela
			public void mouseClicked(MouseEvent e) {
				//Variavel que seleciona a linha que clica
				int linha = tabela_cadastro_pecas.getSelectedRow();
				
				//Pega o campo e coloca no campo de texto selecionado
				txtIDpecas.setText(tabela_cadastro_pecas.getModel().getValueAt(linha,0).toString());
				txtNome_pecas.setText(tabela_cadastro_pecas.getModel().getValueAt(linha,1).toString());
				txtQuantidade_comprada.setText(tabela_cadastro_pecas.getModel().getValueAt(linha,2).toString());
				Object capacidadeSelecionada = tabela_cadastro_pecas.getModel().getValueAt(linha, 3);
                cbCapacidade.setSelectedItem(capacidadeSelecionada);
				txtFornecedor.setText(tabela_cadastro_pecas.getModel().getValueAt(linha,4).toString());
				txtContato_fornecedor.setText(tabela_cadastro_pecas.getModel().getValueAt(linha,5).toString());
				txtPreco_compra.setText(tabela_cadastro_pecas.getModel().getValueAt(linha,6).toString());
				//Para poder pegar a data
				java.sql.Date diaEntrada = (java.sql.Date) tabela_cadastro_pecas.getModel().getValueAt(linha, 9);
                if (diaEntrada != null) {
                    data_entrada.setDate(diaEntrada);
				 }
				taDescricao.setText(tabela_cadastro_pecas.getModel().getValueAt(linha,10).toString());
			}
		});
		
		
		//Propriedades da tabela
		tabela_cadastro_pecas.setBackground(new Color(255, 255, 255));
		scrollPane.setViewportView(tabela_cadastro_pecas);
		Selecionando_cadastro_estoque();
		tabela_cadastro_pecas.setVisible(true);
		
		//Propriedades desse botão
		JButton btnVoltaMenu = new JButton("<<");
		btnVoltaMenu.setBounds(10, 600, 127, 27);
		btnVoltaMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	//Objeto do Menu_principal
            	Menu_principal mp = new Menu_principal();
            	//Mostra a janela
            	mp.setVisible(true);
            	
            	//Fecha a janela do botão
            	dispose();
            }
        });
		//Propriedades desse botão
		btnVoltaMenu.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 17));
		contentPane.add(btnVoltaMenu);
		
		//Propriedades desse botão
		JButton btnApaga_nome_pecas = new JButton("Voltar");
		btnApaga_nome_pecas.setBounds(371, 33, 17, 17);
		btnApaga_nome_pecas.addActionListener(new ActionListener() {
			//Quando clicar no botão
			public void actionPerformed(ActionEvent e) {
				//Deixa em branco o campo(txtNome_pecas)
				limpatxt(txtNome_pecas);
			}
		});
		//Propriedades desse botão
		btnApaga_nome_pecas.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 17));
		contentPane.add(btnApaga_nome_pecas);
		
		//Propriedades desse botão
		JButton btnApaga_quantidade_comprada = new JButton("X");
		btnApaga_quantidade_comprada.setBounds(372, 83, 17, 17);
		btnApaga_quantidade_comprada.addActionListener(new ActionListener() {
			//Quando clicar no botão
			public void actionPerformed(ActionEvent e) {
				//Deixa em branco o campo(txtQuantidade_comprada)
				limpatxt(txtQuantidade_comprada);
			}
		});
		//Propriedades desse botão
		btnApaga_quantidade_comprada.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 17));
		contentPane.add(btnApaga_quantidade_comprada);
		
		//Propriedades desse botão
		JButton btnApaga_preco_compra = new JButton("Voltar");
		btnApaga_preco_compra.setBounds(372, 283, 17, 17);
		btnApaga_preco_compra.addActionListener(new ActionListener() {
			//Quando clicar no botão
			public void actionPerformed(ActionEvent e) {
				//Deixa em branco o campo(txtPreco_compra)
				limpatxt(txtPreco_compra);
			}
		});
		//Propriedades desse botão
		btnApaga_preco_compra.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 17));
		contentPane.add(btnApaga_preco_compra);

		//Propriedades dessa label
		JLabel lblCapacidade = new JLabel("Capacidade:");
		lblCapacidade.setBounds(38, 128, 120, 27);
		lblCapacidade.setBackground(new Color(255, 255, 255));
		lblCapacidade.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 20));
		contentPane.add(lblCapacidade);
		
		//Propriedades desse campo de texto
		txtIDpecas = new JTextField();
		txtIDpecas.setBounds(633, 133, 108, 27);
		txtIDpecas.setEditable(false);
		txtIDpecas.setColumns(10);
		contentPane.add(txtIDpecas);
		
		//Propriedades dessa label
		JLabel lblCodigo = new JLabel("Código da peça:");
		lblCodigo.setBounds(486, 133, 158, 27);
		lblCodigo.setBackground(new Color(255, 255, 255));
		lblCodigo.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 20));
		contentPane.add(lblCodigo);
		
		//Propriedades dessa label
		JLabel lblDescricao = new JLabel("Descrição:");
		lblDescricao.setBounds(625, 28, 96, 27);
		lblDescricao.setBackground(new Color(255, 255, 255));
		lblDescricao.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 20));
		contentPane.add(lblDescricao);
		
		//Propriedades desse botão
		JButton btnApaga_taDescricao = new JButton("Voltar");
		btnApaga_taDescricao.setBounds(1195, 34, 17, 17);
		//Quando clicar no botão
		btnApaga_taDescricao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Deixa em branco o campo(txtPreco_compra)
				limpata(taDescricao);
			}
		});
		//Propriedades desse botão
		btnApaga_taDescricao.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 17));
		contentPane.add(btnApaga_taDescricao);
		
		//Propriedades desse botão
		JButton btnRedefinir = new JButton("Redefinir");
		btnRedefinir.setBounds(405, 78, 127, 27);
		//Quando clicar no botão
		btnRedefinir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Deixa em branco todos os campo(menos a data e a combobox)
				limparTodosJTextFields();
				limpata(taDescricao);
			}
		});
		//Propriedades desse botão
		btnRedefinir.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 17));
		contentPane.add(btnRedefinir);
		
		//Propriedades desse botão
		JButton btnDeletar_pecas = new JButton("Deletar");
		btnDeletar_pecas.setBounds(751, 133, 96, 27);
		//Quando clicar no botão
		btnDeletar_pecas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Verificação para o campo de ID não estar nulo
				if (txtIDpecas.getText().isBlank()) {
						 JOptionPane.showMessageDialog(null, "Preencha apenas o campo código!");
						 return;
				}
				else {
					//Usando o Crud
					cde.Deletar(Integer.parseInt(txtIDpecas.getText()));
					//Atualiza a tabela
					Selecionando_cadastro_estoque();
					//Deixa em branco depois de usar o botão ja que não se pode apagar manualmente
					limpatxt(txtIDpecas);
				}
			}
		});
		//Propriedades desse botão
		btnDeletar_pecas.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 17));
		contentPane.add(btnDeletar_pecas);
		
		//Propriedades desse botão
		JButton btnAlterar_pecas = new JButton("Alterar");
		btnAlterar_pecas.setBounds(204, 400, 127, 27);
		//Quando clicar no botão
		btnAlterar_pecas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Verifica todos os campos pedidos para não serem nulos
				if ((txtNome_pecas.getText().isBlank() ||
						 txtQuantidade_comprada.getText().isBlank() ||
					     txtFornecedor.getText().isBlank() ||
					     txtContato_fornecedor.getText().isBlank() ||
					     txtPreco_compra.getText().isBlank() ||
						 taDescricao.getText().isBlank() ||
						 txtIDpecas.getText().isBlank())) {
						 JOptionPane.showMessageDialog(null, "Preencha todos os campos!");
						 return;
				}else if(data_entrada.getDate() == null) {
					JOptionPane.showMessageDialog(null, "Preencha a data corretamente! Usando o calendário ao lado do campo!");
					return;
				}else if (medida[0] == 0){
                    JOptionPane.showMessageDialog(null, "Selecione uma medida");
                    return;
                }
				else {
					//COnverte a data em data sql
					java.sql.Date diaEntrada = new java.sql.Date(data_entrada.getDate().getTime());
					
					//Usando o Crud
					cde.Alterar(txtNome_pecas.getText(), Double.parseDouble(txtQuantidade_comprada.getText()), 
					capacidade[0], txtFornecedor.getText(), txtContato_fornecedor.getText(), 
					Double.parseDouble(txtPreco_compra.getText()), 
					diaEntrada, taDescricao.getText(), 
					Integer.parseInt(txtIDpecas.getText()));
					
					//Atualiza a tabela
					Selecionando_cadastro_estoque();
			
				}
			}
		});
		//Propriedades desse botão
		btnAlterar_pecas.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 17));
		contentPane.add(btnAlterar_pecas);
		
		//Propriedades desse botão
		JButton btnDeletar_pecas_1 = new JButton("Pesquisar");
		btnDeletar_pecas_1.setBounds(405, 29, 127, 27);
		//Quando clicar nesse botão
		btnDeletar_pecas_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				{
					//Pega o resultado do Crud
	                ResultSet rs = cde.Selecionar(txtNome_pecas.getText());
	                //Define um novo padrão da tabela(para poder fazer a pesquisa
	                DefaultTableModel model = (DefaultTableModel) tabela_cadastro_pecas.getModel();
	                model.setRowCount(0);

	                
	                try {
	                	//Verifica se o resultado está retornando algum campo
	                    while (rs != null && rs.next()) {
	                    	//Mostra os campos
	                        model.addRow(new Object[]{rs.getString("idPecas"), 
	                                rs.getString("nome_pecas"), rs.getString("quantidade_comprada"), 
	                                rs.getString("capacidade"), rs.getString("preco_compra"),
	                                rs.getString("preco_venda"), rs.getString("data_entrada"), 
	                                rs.getString("descricao_pecas")});
	                    }

	                } catch (SQLException e2) {
	                    e2.printStackTrace();
	                }
	            }
			}
		});
		//Propriedades desse botão
		btnDeletar_pecas_1.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 17));
		contentPane.add(btnDeletar_pecas_1);
		
		//Propriedades desse botão
		JButton btnLista = new JButton("Lista");
		btnLista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Retorna a tabela ao padrão caso tenha alguma modificação
				Selecionando_cadastro_estoque();
			}
		});
		//Propriedades desse botão
		btnLista.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 17));
		btnLista.setBounds(1218, 133, 96, 27);
		contentPane.add(btnLista);
		
		//Propriedades desse botão
		JButton btnDescro = new JButton("Descrição");
		btnDescro.addActionListener(new ActionListener() {
			//Quando clicar no botão
			//Esse botão faz a mesma coisa que o botão"Pesquisar", porém ele aumenta o campo"Descrição"
			public void actionPerformed(ActionEvent e) {
	                ResultSet rs = cde.Selecionar(txtNome_pecas.getText());
	                DefaultTableModel model = (DefaultTableModel) tabela_cadastro_pecas.getModel();
	                model.setRowCount(0);

	                try {
	                    while (rs != null && rs.next()) {
	                        model.addRow(new Object[]{rs.getString("idPecas"), 
	                        		 	rs.getString("nome_pecas"), rs.getString("quantidade_total"), 
		                                rs.getString("capacidade"), rs.getString("fornecedor"), rs.getString("email_fornecedor"), rs.getString("preco_compra"), rs.getString("preco_total"),
		                                rs.getString("preco_venda"), rs.getString("data_entrada"), 
		                                rs.getString("descricao_pecas")});
	                        tabela_cadastro_pecas.getColumnModel().getColumn(10).setPreferredWidth(1280);
	                        tabela_cadastro_pecas.getColumnModel().getColumn(10).setHeaderValue("Descrição");
	                    }

	                } catch (SQLException e2) {
	                    e2.printStackTrace();
	                }
	            
			}
		});
		//Propriedades desse botão
		btnDescro.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 17));
		btnDescro.setBounds(1081, 134, 120, 27);
		contentPane.add(btnDescro);
		
		//Propriedades desse botão
		JButton btnCadastro_pecas_1 = new JButton("Baixa no estoque");
		btnCadastro_pecas_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Objeto da Baixa_estoque
				Baixa_estoque beC = new Baixa_estoque();
				//Mostra a janela Baixa_estoque
				beC.setVisible(true);
			}
		});
		//Propriedades desse botão
		btnCadastro_pecas_1.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 17));
		btnCadastro_pecas_1.setBounds(109, 452, 180, 27);
		contentPane.add(btnCadastro_pecas_1);
		
		//Propriedades desse botão
		JButton btnCadastro_pecas_1_1 = new JButton("Pedido de compra");
		btnCadastro_pecas_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Pedido_de_compra pdC = new Pedido_de_compra();
			}
		});
		//Propriedades desse botão
		btnCadastro_pecas_1_1.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 17));
		btnCadastro_pecas_1_1.setBounds(109, 495, 180, 27);
		contentPane.add(btnCadastro_pecas_1_1);
		
	}
}
