package projetoA3;


import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import net.proteanit.sql.DbUtils;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.Color;
import com.toedter.calendar.JDateChooser;

public class Baixa_estoque extends JFrame {

	//(J)Componentes e Objetos
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtQuantidade_baixa;
	private JButton btnNewButton_1;
	private JTextField txtNome_baixa;
	private JTextField txtidPecas_baixa;
	private JTextArea txt_motivo;
	private JTable tabela_ordem;
	private JTextField txtID_ordem;
	//Crud´s usados nesse Jframe
	Crud_baixa_estoque crudBaixa = new Crud_baixa_estoque();
	Crud_ordem_serviço crudOrdemBaixa = new Crud_ordem_serviço();

	//Para poder limpar todos os campos de texto de uma só vez
	private void limparTodosJTextFields() {
	    List<JTextField> textFields = Arrays.asList(txtID_ordem, txtidPecas_baixa, txtNome_baixa, txtQuantidade_baixa);
	    for (JTextField txt : textFields) {
	        txt.setText("");
	    }
	}
	
	//Inicia
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Baixa_estoque frame = new Baixa_estoque();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	//Tabela com resultados do banco de dados
	public void Selecionando_ordem()
	{
		// Precisamos criar algumas variáveis para conectar, dar comandos e
		// também para armazenar a pesquisa com o SELECT:
		Connection conexao = null;
		Statement  comando = null;
		ResultSet  resultado = null;
		try 
		{
			conexao = ClasseConexao.Conectar();
			comando = conexao.createStatement();
			String meu_sql = "SELECT idOrdem, nomePcsOrdem, qntdComprada, conclusao_servico, idPcsFK FROM ordem_servico";
			resultado = comando.executeQuery(meu_sql); // Executa o SQL
			//Pega o resultado e coloca na tabela
			tabela_ordem.setModel(DbUtils.resultSetToTableModel(resultado));	
			//Arrumando as colunas(nome e tamanho) 
			tabela_ordem.getColumnModel().getColumn(0).setHeaderValue("ID Ordem");
			tabela_ordem.getColumnModel().getColumn(1).setHeaderValue("Nome da peça");
			tabela_ordem.getColumnModel().getColumn(2).setHeaderValue("Quantidade");
			tabela_ordem.getColumnModel().getColumn(3).setHeaderValue("Conclusão");
			tabela_ordem.getColumnModel().getColumn(4).setHeaderValue("ID Peça");
			tabela_ordem.getColumnModel().getColumn(0).setPreferredWidth(35);
			tabela_ordem.getColumnModel().getColumn(1).setPreferredWidth(110);
			tabela_ordem.getColumnModel().getColumn(2).setPreferredWidth(70);
			tabela_ordem.getColumnModel().getColumn(3).setPreferredWidth(35);
			tabela_ordem.getColumnModel().getColumn(4).setPreferredWidth(35);
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
	public Baixa_estoque() {
		//Propriedades dessa janela
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(167, 92, 997, 700);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(214, 215, 237));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//Propriedades dessa label
		JLabel lblNewLabel = new JLabel("Quantidade baixa:");
		lblNewLabel.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 20));
		lblNewLabel.setBounds(39, 26, 163, 27);
		contentPane.add(lblNewLabel);
		
		//Propriedades desse campo de texto
		txtQuantidade_baixa = new JTextField();
		txtQuantidade_baixa.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 17));
		txtQuantidade_baixa.setBounds(204, 27, 227, 27);
		contentPane.add(txtQuantidade_baixa);
		txtQuantidade_baixa.setColumns(10);{
			//Filtragem de caracteres com limite
			FiltroDeCaracteres filtro = new FiltroDeCaracteres(FiltroDeCaracteres.TipoDeFiltro.numerosDouble, 20);
			txtQuantidade_baixa.setDocument(filtro);
		}
		
		//Propriedades desse campo de texto
		txtidPecas_baixa = new JTextField();
		txtidPecas_baixa.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 17));
		txtidPecas_baixa.setEditable(false);				
		txtidPecas_baixa.setColumns(10);
		txtidPecas_baixa.setBounds(142, 106, 289, 27);
		contentPane.add(txtidPecas_baixa);
		
		//Propriedades dessa área de texto
		txt_motivo = new JTextArea();
		txt_motivo.setLineWrap(true);
		txt_motivo.setBounds(663, 26, 250, 118);
		contentPane.add(txt_motivo);{
			//Filtragem de caracteres com limite
			FiltroDeCaracteres filtro = new FiltroDeCaracteres(FiltroDeCaracteres.TipoDeFiltro.email, 50);
			txt_motivo.setDocument(filtro);
			
		}
		
		//Propriedades dessa data
		JDateChooser data_baixa = new JDateChooser();
		data_baixa.setBounds(177, 146, 254, 27);
		contentPane.add(data_baixa);
	
		//Quando clicar nesse botão
		JButton btnNewButton = new JButton("Confirmar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//Verificação para não deixar os campos em branco
				if (txtQuantidade_baixa.getText().isBlank() ||
					txtNome_baixa.getText().isBlank() ||
					txt_motivo.getText().isBlank()) {
					//Mensagem caso alguma das verificações seja verdadeira
						JOptionPane.showMessageDialog(null, "Preencha todos os campos necessários!");
						
						//Retorna para não continuar o código abaixo
						return;
				}else if(data_baixa.getDate() == null) {
					JOptionPane.showMessageDialog(null, "Preencha a data corretamente! Usando o calendário ao lado do campo!");
					return;
				}else {
				java.sql.Date diaBaixa = new java.sql.Date(data_baixa.getDate().getTime());
				
				//Verificaçã para confirmar a baixa
				int resposta = JOptionPane.showConfirmDialog(null, "Você tem certeza que deseja confirmar a baixa?", "Confirmação", JOptionPane.YES_NO_OPTION);
                if (resposta == JOptionPane.YES_OPTION) {
				
                	
                	//Pega o resultado do crud para poder se a baixa foi ou não feita
    				ResultSet verifica = crudBaixa.verificaConclusao(Integer.parseInt(txtID_ordem.getText()));
                	int conclusao_servico = 0;
    				try {
    					conclusao_servico = verifica.getInt("conclusao_servico");
    					
    				} catch (SQLException e1) {
    					return;
    				}
                	if (conclusao_servico == 1) {
                	    return;
                	}
	                //Crud fazendo a consulta com o banco de dados
	                crudBaixa.Alterar_conclusao_servico(2,Integer.parseInt(txtID_ordem.getText()));
					crudBaixa.Inserir(Double.parseDouble(txtQuantidade_baixa.getText()), diaBaixa, txt_motivo.getText(),Integer.parseInt(txtidPecas_baixa.getText()), Integer.parseInt(txtID_ordem.getText()));
					crudBaixa.Alterar(txtQuantidade_baixa.getText(), Integer.parseInt(txtidPecas_baixa.getText()), Integer.parseInt(txtID_ordem.getText()));
	                
	                crudBaixa.Alterar_conclusao_servico(1,Integer.parseInt(txtID_ordem.getText()));
					
					//Atualizar a tabela pois de apertar o botão caso mude alguma coisa
					Selecionando_ordem();
                }
				}
			}
		});
		
		//Propriedades desse botão
		btnNewButton.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 17));
		btnNewButton.setBounds(221, 193, 112, 27);
		contentPane.add(btnNewButton);
		
		
		//Botão para apagar o campo ao lado dele
		btnNewButton_1 = new JButton("New button");
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtQuantidade_baixa.setText("");
				    }
				});
				
		//Propriedades desse botão
		btnNewButton_1.setBounds(437, 33, 17, 17);
		contentPane.add(btnNewButton_1);
		
		//Propriedades desse campo de texto
		txtNome_baixa = new JTextField();
		txtNome_baixa.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 17));
		txtNome_baixa.setBounds(177, 66, 254, 27);
		contentPane.add(txtNome_baixa);
		txtNome_baixa.setColumns(10);{
			//Filtragem de caracteres com limite
			FiltroDeCaracteres filtro = new FiltroDeCaracteres(FiltroDeCaracteres.TipoDeFiltro.nome, 50);
			txtNome_baixa.setDocument(filtro);
		}
		
		//Propriedades dessa label
		JLabel lblNewLabel_1 = new JLabel("Nome da peça:");
		lblNewLabel_1.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 20));
		lblNewLabel_1.setBounds(39, 66, 144, 27);
		contentPane.add(lblNewLabel_1);
		
		//Propriedades dessa label
		JLabel lblNewLabel_3 = new JLabel("Motivo:");
		lblNewLabel_3.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 20));
		lblNewLabel_3.setBounds(586, 29, 67, 20);
		contentPane.add(lblNewLabel_3);
		
		//Botão para apagar o campo ao lado dele
		JButton btnNewButton_1_1 = new JButton("New button");
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtNome_baixa.setText("");
				    
			}
				});
		
		//Propriedades desse botão
		btnNewButton_1_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnNewButton_1_1.setBounds(437, 73, 17, 17);
		contentPane.add(btnNewButton_1_1);
		
		//Quando clicar nesse botão
		JButton btnNewButton_1_1_2 = new JButton("Pesquisar");
		btnNewButton_1_1_2.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 17));
		btnNewButton_1_1_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				//Pega os resultados desse Crud(Feito um Select)
			    ResultSet rs = crudBaixa.Selecionar_pesquisa(txtNome_baixa.getText());
			    
			    //Muda as propriedades da tabela
			    DefaultTableModel model = (DefaultTableModel) tabela_ordem.getModel();
			    model.setRowCount(0);
			    
			    //Tenta
			    try {
			    	//Procura se existe essa linha com base na pesquisa feita
		            while (rs != null && rs.next()) {
		            	//Adiciona os campo que estão ("") caso a linha exista
		            	model.addRow(new Object[]{rs.getString("idOrdem"), rs.getString("nomePcsOrdem"), rs.getString("qntdcomprada"), rs.getString("conclusao_servico"), rs.getString("idPcsFK")});
		            }
		        //Caso de algo de errado
			    } catch (SQLException e2) {
			    	e2.printStackTrace();
			      }
			}
		});
		btnNewButton_1_1_2.setBounds(464, 66, 112, 27);
		contentPane.add(btnNewButton_1_1_2);
		
		//Propriedades dessa label
		JLabel lblNewLabel_2_1 = new JLabel("ID da peça:");
		lblNewLabel_2_1.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 20));
		lblNewLabel_2_1.setBounds(39, 106, 123, 27);
		contentPane.add(lblNewLabel_2_1);
		
		//Botão para apagar o campo ao lado dele
		JButton btnNewButton_1_2 = new JButton("New button");
		btnNewButton_1_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txt_motivo.setText("");
			}
		});
		
		//Propriedades desse botão
		btnNewButton_1_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnNewButton_1_2.setBounds(919, 37, 17, 17);
		contentPane.add(btnNewButton_1_2);
		
		
		//Quando clicar nesse botão
		JButton btnNewButton_2 = new JButton("Redefinir");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//Limpa todos os campos de texto
				limparTodosJTextFields();
				
				//Limpa a área de texto
				txt_motivo.setText("");
			}
		});
		btnNewButton_2.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 17));
		btnNewButton_2.setBounds(69, 193, 112, 27);
		contentPane.add(btnNewButton_2);
		
		//Propriedade da Rolagem de mouse(essa rolagem é para a tabela quando tiver mais linhas do que o tamanho máximo)
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(42, 275, 894, 340);
		contentPane.add(scrollPane);
		
		//Quando clicar a tabela
		tabela_ordem = new JTable();
		tabela_ordem.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//Variavel que seleciona a linha que clica
				int linha = tabela_ordem.getSelectedRow();
				
				//Pega o campo e coloca no campo de texto selecionado
				txtID_ordem.setText(tabela_ordem.getModel().getValueAt(linha,0).toString());
				txtNome_baixa.setText(tabela_ordem.getModel().getValueAt(linha,1).toString());
				txtQuantidade_baixa.setText(tabela_ordem.getModel().getValueAt(linha,2).toString());
				txtidPecas_baixa.setText(tabela_ordem.getModel().getValueAt(linha,4).toString());
			}
		});
		scrollPane.setViewportView(tabela_ordem);{
			//Atualiza a tabela
			Selecionando_ordem();
		}
		
		//Propriedades dessa label
		JLabel lblNewLabel_2_1_1 = new JLabel("ID Ordem:");
		lblNewLabel_2_1_1.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 20));
		lblNewLabel_2_1_1.setBounds(495, 238, 100, 27);
		contentPane.add(lblNewLabel_2_1_1);
		
		//Propriedades desse campo de texto
		txtID_ordem = new JTextField();
		txtID_ordem.setEditable(false);
		txtID_ordem.setColumns(10);
		txtID_ordem.setBounds(590, 238, 96, 27);
		contentPane.add(txtID_ordem);
		
		//Propriedades dessa label
		JLabel lblNewLabel_2_1_2 = new JLabel("Tabela de ordem de serviço");
		lblNewLabel_2_1_2.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 20));
		lblNewLabel_2_1_2.setBounds(39, 238, 294, 27);
		contentPane.add(lblNewLabel_2_1_2);
		
		JLabel lblNewLabel_1_1 = new JLabel("Nome da peça:");
		lblNewLabel_1_1.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 20));
		lblNewLabel_1_1.setBounds(39, 146, 144, 27);
		contentPane.add(lblNewLabel_1_1);
		
		JButton btnNewButton_2_1 = new JButton("<<");
		btnNewButton_2_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Menu_principal menu = new Menu_principal();
				menu.setVisible(true);
				dispose();
			}
		});
		btnNewButton_2_1.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 17));
		btnNewButton_2_1.setBounds(10, 626, 112, 27);
		contentPane.add(btnNewButton_2_1);
		
		JButton btnNewButton_1_1_2_1 = new JButton("Baixa no estoque");
		btnNewButton_1_1_2_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Gerenciar_baixa_no_estoque gbn = new Gerenciar_baixa_no_estoque();
				gbn.setVisible(true);
			}
		});
		btnNewButton_1_1_2_1.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 17));
		btnNewButton_1_1_2_1.setBounds(766, 238, 170, 27);
		contentPane.add(btnNewButton_1_1_2_1);{
			Selecionando_ordem();
		}
		
	
	}
}