package projetoA3;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import java.awt.Color;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.UIManager;
import java.awt.Window.Type;
import com.toedter.calendar.JDateChooser;

import net.proteanit.sql.DbUtils;

import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class cad_cliente extends JFrame {
	
	//Componentes criados
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNome_cliente;
	private JTextField txtCPF;
	private JTextField txtCEP;
	private JTable tabela_cadastro_cliente;
	private JTextField txtIDCliente;
	private JTextField txtTelefone;
	private void limpatxt(JTextField txt){
		txt.setText("");
	}
	
	//Deixa em branco a área de texto
	private void limpata(JTextArea ta){
		ta.setText("");
	}
	
	//Deixa em branco todos os campos de texto
	private void limparTodosJTextFields() {
	    List<JTextField> textFields = Arrays.asList(txtCEP, txtCPF, txtIDCliente, txtNome_cliente, txtTelefone);
	    for (JTextField txt : textFields) {
	        txt.setText("");
	    }
	}
	
	//Crud usado
	Crud_cad_cliente ccc = new Crud_cad_cliente();

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {			
					cad_cliente frame = new cad_cliente();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	//Para a tabela aparecer as colunas do banco de dados
	public void Selecionando_cadastro_cliente()
    {
        // Precisamos criar algumas variáveis para conectar, dar comandos e
        // também para armazenar a pesquisa com o SELECT:
        Connection conexao = null;
        Statement  comando = null;
        ResultSet  resultado = null;
        try {
        	//Faz a consulta
            conexao = ClasseConexao.Conectar();
            comando = conexao.createStatement();
            String meu_sql = "SELECT * FROM cadastro_cliente";
            resultado = comando.executeQuery(meu_sql); // Executa o SQL

            //Retorna os valores para a tabela
            tabela_cadastro_cliente.setModel(DbUtils.resultSetToTableModel(resultado));
            //Para formatar as colunas(nome e tamanho)
            tabela_cadastro_cliente.getColumnModel().getColumn(0).setHeaderValue("ID");
            tabela_cadastro_cliente.getColumnModel().getColumn(1).setHeaderValue("Nome");
            tabela_cadastro_cliente.getColumnModel().getColumn(2).setHeaderValue("CPF");
            tabela_cadastro_cliente.getColumnModel().getColumn(3).setHeaderValue("Nascimento");
            tabela_cadastro_cliente.getColumnModel().getColumn(4).setHeaderValue("Estado");
            tabela_cadastro_cliente.getColumnModel().getColumn(5).setHeaderValue("Cidade");
            tabela_cadastro_cliente.getColumnModel().getColumn(6).setHeaderValue("CEP");
            tabela_cadastro_cliente.getColumnModel().getColumn(7).setHeaderValue("Telefone");
            tabela_cadastro_cliente.getColumnModel().getColumn(8).setHeaderValue("Endereço");
            tabela_cadastro_cliente.getColumnModel().getColumn(9).setHeaderValue("Descirção");
            tabela_cadastro_cliente.getColumnModel().getColumn(0).setPreferredWidth(35);
            tabela_cadastro_cliente.getColumnModel().getColumn(1).setPreferredWidth(130);
            tabela_cadastro_cliente.getColumnModel().getColumn(2).setPreferredWidth(80);
            tabela_cadastro_cliente.getColumnModel().getColumn(3).setPreferredWidth(80);
            tabela_cadastro_cliente.getColumnModel().getColumn(4).setPreferredWidth(120);
            tabela_cadastro_cliente.getColumnModel().getColumn(5).setPreferredWidth(120);
            tabela_cadastro_cliente.getColumnModel().getColumn(6).setPreferredWidth(65);
            tabela_cadastro_cliente.getColumnModel().getColumn(7).setPreferredWidth(85);
            tabela_cadastro_cliente.getColumnModel().getColumn(8).setPreferredWidth(20);
            tabela_cadastro_cliente.getColumnModel().getColumn(9).setPreferredWidth(20);
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
	
	public cad_cliente() {
		//Janela
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBackground(new Color(240, 240, 240));
		setTitle("Teste");
		setBounds(60, 50, 1400, 797);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(214, 215, 237));
		contentPane.setForeground(new Color(0, 0, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JDateChooser data_nasc = new JDateChooser();
		data_nasc.setBounds(148, 128, 168, 27);
		contentPane.add(data_nasc);
		
		//Botão par voltar
		JButton btnVoltaMenu = new JButton("<<");
		btnVoltaMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Menu_principal janela_principal = new Menu_principal();
				janela_principal.setVisible(true);
				dispose();
			}
		});
		//Botão
		btnVoltaMenu.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnVoltaMenu.setBounds(25, 600, 105, 27);
		contentPane.add(btnVoltaMenu);
		
		JLabel lblNewLabel = new JLabel("Nome:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(34, 28, 68, 27);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1_1 = new JLabel("CPF:");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_1_1.setBounds(34, 78, 105, 27);
		contentPane.add(lblNewLabel_1_1);
		
		txtNome_cliente = new JTextField();
		txtNome_cliente.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtNome_cliente.setBounds(95, 28, 221, 27);
		contentPane.add(txtNome_cliente);
		txtNome_cliente.setColumns(10);{
			FiltroDeCaracteres filtro = new FiltroDeCaracteres(FiltroDeCaracteres.TipoDeFiltro.nome, 50);
			txtNome_cliente.setDocument(filtro);
		}
		
		txtCPF = new JTextField();
		txtCPF.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtCPF.setColumns(10);
		txtCPF.setBounds(78, 78, 238, 27);
		contentPane.add(txtCPF);{
			FiltroDeCaracteres filtro = new FiltroDeCaracteres(FiltroDeCaracteres.TipoDeFiltro.numerosInt, 11);
			txtCPF.setDocument(filtro);
		}
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Nascimento:");
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_1_1_1.setBounds(34, 128, 122, 27);
		contentPane.add(lblNewLabel_1_1_1);
		
		
		JComboBox<String> cbEstado = new JComboBox<>();
		cbEstado.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 15));
		cbEstado.setBackground(new Color(255, 255, 255));
		cbEstado.setBounds(101, 178, 215, 27);
		contentPane.add(cbEstado);
		{
		    
		    ResultSet rs = ccc.SelecionarEstados();
		    
		    try {
		        // Tente obter o ResultSet do método SelecionarEstados()
		        rs = ccc.SelecionarEstados();

		     
		            // Adicione o item "Selecione um estado" ao ComboBox
		            cbEstado.addItem("Selecione um estado");

		            // Percorra o ResultSet e adicione os estados ao ComboBox
		            while (rs.next()) {
		                cbEstado.addItem(rs.getString(3));
		            }
		        
		    } catch (SQLException erro) {
		        erro.printStackTrace();
		    }
		}

		

		
		
		JLabel lblNewLabel_1_1_2_1 = new JLabel("Cidade:");
		lblNewLabel_1_1_2_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_1_1_2_1.setBounds(34, 228, 68, 27);
		contentPane.add(lblNewLabel_1_1_2_1);
		
		JComboBox<String> cbCidade = new JComboBox<>();
		cbCidade.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 15));
		cbCidade.setBackground(new Color(255, 255, 255));
		cbCidade.setBounds(101, 228, 215, 27);
		contentPane.add(cbCidade);
		cbEstado.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        String estadoSelecionado = (String) cbEstado.getSelectedItem();
		        
		        // Atualiza o cbCidade com as cidades do estado selecionado
		        
		        ResultSet rs = ccc.Selecionar_Cidades(estadoSelecionado);
		        
		        cbCidade.removeAllItems();
		        
		        try {
		        	cbCidade.addItem("Selecione uma cidade");
		            while (rs.next()) {
		                cbCidade.addItem(rs.getString(3));
		            }
		        } catch (SQLException erro) {
		            erro.printStackTrace();
		        }
		    }
		});
		
		JTextArea taEndereco_cliente = new JTextArea();
		taEndereco_cliente.setFont(new Font("Tahoma", Font.PLAIN, 20));
		taEndereco_cliente.setBounds(673, 23, 670, 69);
		contentPane.add(taEndereco_cliente);{
			FiltroDeCaracteres filtro = new FiltroDeCaracteres(FiltroDeCaracteres.TipoDeFiltro.nome, 50);
			taEndereco_cliente.setDocument(filtro);
		}
		
		JTextArea taDescricao_cliente = new JTextArea();
		taDescricao_cliente.setFont(new Font("Tahoma", Font.PLAIN, 20));
		taDescricao_cliente.setBounds(673, 119, 670, 69);
		contentPane.add(taDescricao_cliente);{
			FiltroDeCaracteres filtro = new FiltroDeCaracteres(FiltroDeCaracteres.TipoDeFiltro.nome, 50);
			taDescricao_cliente.setDocument(filtro);
		}
		
		JLabel lblNewLabel_1_1_3 = new JLabel("Endereço:");
		lblNewLabel_1_1_3.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_1_1_3.setBounds(569, 23, 105, 27);
		contentPane.add(lblNewLabel_1_1_3);
		
		JLabel lblNewLabel_1_1_3_1 = new JLabel("Descrição:");
		lblNewLabel_1_1_3_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_1_1_3_1.setBounds(569, 119, 105, 27);
		contentPane.add(lblNewLabel_1_1_3_1);
		
		JButton btnRedefinirCadCLiente = new JButton("Redefinir");
		btnRedefinirCadCLiente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparTodosJTextFields();
				limpata(taEndereco_cliente);
				limpata(taDescricao_cliente);
				cbEstado.setSelectedIndex(0);
				cbCidade.setSelectedIndex(0);
				data_nasc.setDate(null);
				
			}
		});
		btnRedefinirCadCLiente.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnRedefinirCadCLiente.setBounds(1120, 222, 157, 27);
		contentPane.add(btnRedefinirCadCLiente);
		
		
		JLabel lblNewLabel_1_1_3_2 = new JLabel("CEP:");
		lblNewLabel_1_1_3_2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_1_1_3_2.setBounds(34, 278, 53, 27);
		contentPane.add(lblNewLabel_1_1_3_2);
		
		txtCEP = new JTextField();
		txtCEP.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 15));
		txtCEP.setColumns(10);
		txtCEP.setBounds(78, 278, 238, 27);
		contentPane.add(txtCEP);{
			FiltroDeCaracteres filtro = new FiltroDeCaracteres(FiltroDeCaracteres.TipoDeFiltro.numerosInt, 8);
			txtCEP.setDocument(filtro);
		}
		
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(469, 370, 900, 338);
		contentPane.add(scrollPane);
		
		tabela_cadastro_cliente = new JTable();
		tabela_cadastro_cliente.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int linha = tabela_cadastro_cliente.getSelectedRow();
				txtIDCliente.setText(tabela_cadastro_cliente.getModel().getValueAt(linha,0).toString());
				txtNome_cliente.setText(tabela_cadastro_cliente.getModel().getValueAt(linha,1).toString());
				txtCPF.setText(tabela_cadastro_cliente.getModel().getValueAt(linha,2).toString());
				java.sql.Date diaNasc = (java.sql.Date) tabela_cadastro_cliente.getModel().getValueAt(linha, 3);
                if (diaNasc != null) {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    String dataFormatada = sdf.format(diaNasc);
                    data_nasc.setDate(diaNasc);
				 }
                Object estado = tabela_cadastro_cliente.getModel().getValueAt(linha, 4);
                cbEstado.setSelectedItem(estado);
                Object cidade = tabela_cadastro_cliente.getModel().getValueAt(linha, 5);
                cbCidade.setSelectedItem(cidade);
				txtCEP.setText(tabela_cadastro_cliente.getModel().getValueAt(linha,6).toString());
				txtTelefone.setText(tabela_cadastro_cliente.getModel().getValueAt(linha,7).toString());
				taEndereco_cliente.setText(tabela_cadastro_cliente.getModel().getValueAt(linha,8).toString());
				taDescricao_cliente.setText(tabela_cadastro_cliente.getModel().getValueAt(linha,9).toString());
			}
		});
		scrollPane.setViewportView(tabela_cadastro_cliente);
		
		JButton btnPesquisar = new JButton("Pesquisar");
		btnPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

	                ResultSet rs = ccc.Selecionar(txtNome_cliente.getText());
	                DefaultTableModel model = (DefaultTableModel) tabela_cadastro_cliente.getModel();
	                model.setRowCount(0);


	                try {
	                    while (rs != null && rs.next()) {
	                        model.addRow(new Object[]{rs.getString("idCliente"), rs.getString("nome_cliente"), 
	                                rs.getString("cpf"), rs.getString("data_nascimento"), 
	                                rs.getString("estado"), rs.getString("cidade"),
	                                rs.getString("cep"), rs.getString("telefone"), rs.getString("endereco"), 
	                                rs.getString("descricao_endereco")});
	                    }

	                } catch (SQLException e2) {
	                    e2.printStackTrace();
	                }
			}
		});
		btnPesquisar.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnPesquisar.setBounds(363, 28, 136, 27);
		contentPane.add(btnPesquisar);
		
		JButton btnRedefinirCadCLiente_1 = new JButton("Redefinir");
		btnRedefinirCadCLiente_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpatxt(txtNome_cliente);
			}
		});
		btnRedefinirCadCLiente_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnRedefinirCadCLiente_1.setBounds(326, 32, 17, 17);
		contentPane.add(btnRedefinirCadCLiente_1);
		
		JButton btnRedefinirCadCLiente_1_1 = new JButton("Redefinir");
		btnRedefinirCadCLiente_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpatxt(txtCPF);
			}
		});
		btnRedefinirCadCLiente_1_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnRedefinirCadCLiente_1_1.setBounds(326, 82, 17, 17);
		contentPane.add(btnRedefinirCadCLiente_1_1);
		
		JButton btnRedefinirCadCLiente_1_5 = new JButton("Redefinir");
		btnRedefinirCadCLiente_1_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpatxt(txtCEP);
			}
		});
		btnRedefinirCadCLiente_1_5.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnRedefinirCadCLiente_1_5.setBounds(326, 282, 17, 17);
		contentPane.add(btnRedefinirCadCLiente_1_5);
		
		JLabel lblNewLabel_1_1_2_1_1 = new JLabel("Estado:");
		lblNewLabel_1_1_2_1_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_1_1_2_1_1.setBounds(34, 178, 68, 27);
		contentPane.add(lblNewLabel_1_1_2_1_1);{
			Selecionando_cadastro_cliente();		
			}
		
		JButton btnCadCLiente = new JButton("Cadastrar");
		btnCadCLiente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String estado = (String) cbEstado.getSelectedItem();
				String cidade = (String) cbCidade.getSelectedItem();
				if ((txtNome_cliente.getText().isBlank() ||
						 txtCPF.getText().isBlank() ||
					     txtCEP.getText().isBlank() ||
					     txtTelefone.getText().isBlank() ||
					     taDescricao_cliente.getText().isBlank() ||
						 taEndereco_cliente.getText().isBlank())){
						 JOptionPane.showMessageDialog(null, "Preencha todos os campos!");
						 return;
				}
			
				else if(data_nasc.getDate() == null) {
					JOptionPane.showMessageDialog(null, "Preencha a data corretamente! Usando o calendário ao lado do campo!");
				return;
				}
				
				else if(estado.isBlank() || estado.equals("Selecione um estado")) {
					JOptionPane.showMessageDialog(null, "Preencha o estado corretamente! Usando o calendário ao lado do campo!");
				return;
				}
				
				else if(cidade.isBlank() || cidade.equals("Selecione uma cidade")) {
					JOptionPane.showMessageDialog(null, "Preencha a cidade corretamente! Você precisa escolher um estado primeiramente");
				return;
				}
				
				java.sql.Date diaNascimento = new java.sql.Date(data_nasc.getDate().getTime());
				Calendar dataAtual = Calendar.getInstance();
				dataAtual.add(Calendar.YEAR, -18);

				if (diaNascimento.after(dataAtual.getTime())) { 
					JOptionPane.showMessageDialog(null, "O cliente precisa ser maior de idade");
				}
				else {
				  // A data de nascimento é maior que 18 anos
				
				
				ccc.Inserir(txtNome_cliente.getText(), Integer.parseInt(txtCPF.getText()), diaNascimento, estado, cidade, Integer.parseInt(txtCEP.getText()), Integer.parseInt(txtTelefone.getText()), taEndereco_cliente.getText(), taDescricao_cliente.getText());
				}
				
				Selecionando_cadastro_cliente();
			}
		});
		btnCadCLiente.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnCadCLiente.setBounds(735, 222, 157, 27);
		contentPane.add(btnCadCLiente);
		
		JButton btnRedefinirCadCLiente_1_7_1 = new JButton("Redefinir");
		btnRedefinirCadCLiente_1_7_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpata(taDescricao_cliente);
			}
		});
		btnRedefinirCadCLiente_1_7_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnRedefinirCadCLiente_1_7_1.setBounds(1352, 125, 17, 17);
		contentPane.add(btnRedefinirCadCLiente_1_7_1);
		
		JButton btnRedefinirCadCLiente_1_6_1 = new JButton("Redefinir");
		btnRedefinirCadCLiente_1_6_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpata(taEndereco_cliente);
			}
		});
		btnRedefinirCadCLiente_1_6_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnRedefinirCadCLiente_1_6_1.setBounds(1352, 29, 17, 17);
		contentPane.add(btnRedefinirCadCLiente_1_6_1);
		
		txtIDCliente = new JTextField();
		txtIDCliente.setEnabled(false);
		txtIDCliente.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtIDCliente.setColumns(10);
		txtIDCliente.setBounds(615, 331, 122, 27);
		contentPane.add(txtIDCliente);
		
		JLabel lblIdCliente = new JLabel("ID CLiente:");
		lblIdCliente.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblIdCliente.setBounds(513, 332, 113, 27);
		contentPane.add(lblIdCliente);
		
		JButton btnDeletar = new JButton("Deletar");
		btnDeletar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ccc.Deletar(Integer.parseInt(txtIDCliente.getText()));
				Selecionando_cadastro_cliente();
			}
		});
		btnDeletar.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnDeletar.setBounds(757, 331, 109, 27);
		contentPane.add(btnDeletar);
		
		txtTelefone = new JTextField();
		txtTelefone.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 15));
		txtTelefone.setColumns(10);
		txtTelefone.setBounds(124, 328, 192, 27);
		contentPane.add(txtTelefone);{
			FiltroDeCaracteres filtro = new FiltroDeCaracteres(FiltroDeCaracteres.TipoDeFiltro.numerosInt, 11);
			txtTelefone.setDocument(filtro);
		}
		
		JButton btnRedefinirCadCLiente_1_5_1 = new JButton("Redefinir");
		btnRedefinirCadCLiente_1_5_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpatxt(txtTelefone);
			}
		});
		btnRedefinirCadCLiente_1_5_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnRedefinirCadCLiente_1_5_1.setBounds(326, 332, 17, 17);
		contentPane.add(btnRedefinirCadCLiente_1_5_1);
		
		JLabel lblNewLabel_1_1_3_2_1 = new JLabel("Tefefone:");
		lblNewLabel_1_1_3_2_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_1_1_3_2_1.setBounds(34, 328, 96, 27);
		contentPane.add(lblNewLabel_1_1_3_2_1);
		
		JButton btnAlterar = new JButton("Alterar");
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String estado = (String) cbEstado.getSelectedItem();
				String cidade = (String) cbCidade.getSelectedItem();
				if ((txtNome_cliente.getText().isBlank() ||
						 txtCPF.getText().isBlank() ||
					     txtCEP.getText().isBlank() ||
					     txtTelefone.getText().isBlank() ||
					     taDescricao_cliente.getText().isBlank() ||
						 taEndereco_cliente.getText().isBlank())){
						 JOptionPane.showMessageDialog(null, "Preencha todos os campos!");
						 return;
				}
			
				else if(data_nasc.getDate() == null) {
					JOptionPane.showMessageDialog(null, "Preencha a data corretamente! Usando o calendário ao lado do campo!");
				return;
				}
				
				else if(estado.isBlank() || estado.equals("Selecione um estado")) {
					JOptionPane.showMessageDialog(null, "Preencha o estado corretamente! Usando o calendário ao lado do campo!");
				return;
				}
				
				else if(cidade.isBlank() || cidade.equals("Selecione uma cidade")) {
					JOptionPane.showMessageDialog(null, "Preencha a cidade corretamente! Você precisa escolher um estado primeiramente");
				return;
				}
				
			java.sql.Date diaNascimento = new java.sql.Date(data_nasc.getDate().getTime());
				
				Calendar dataAtual = Calendar.getInstance();
				dataAtual.add(Calendar.YEAR, -18);

				if (diaNascimento.after(dataAtual.getTime())) { 
					JOptionPane.showMessageDialog(null, "O cliente precisa ser maior de idade");
				}
				else {
				  // A data de nascimento é maior que 18 anos

				ccc.Alterar(txtNome_cliente.getText(), Integer.parseInt(txtCPF.getText()), diaNascimento, estado, cidade, Integer.parseInt(txtCEP.getText()), Integer.parseInt(txtTelefone.getText()), taEndereco_cliente.getText(), taDescricao_cliente.getText(), Integer.parseInt(txtIDCliente.getText()));
				}
				Selecionando_cadastro_cliente();
			}
		});
		btnAlterar.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnAlterar.setBounds(929, 222, 157, 27);
		contentPane.add(btnAlterar);
	}

	protected static void EXIT_ON_CLOSE() {
		// TODO Auto-generated method stub
		
	}

	protected static void DISPOSE_ON_CLOSE() {
		// TODO Auto-generated method stub
		
	}
}
