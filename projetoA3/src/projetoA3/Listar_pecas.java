package projetoA3;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import net.proteanit.sql.DbUtils;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JCheckBox;
import com.toedter.calendar.JDateChooser;

public class Listar_pecas extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNome_lista;
	private JTable tabela_lista;
	private JTextField txtIDPeca_lista;
	private JTextField txtQuantidade_lista;
	Crud_listar_pecas Crud_lc = new Crud_listar_pecas();
	private JTextField txtConclusao;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Listar_pecas frame = new Listar_pecas();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	

	public void Selecionando_lista()
    {
        // Precisamos criar algumas variáveis para conectar, dar comandos e
        // também para armazenar a pesquisa com o SELECT:
        Connection conexao = null;
        Statement  comando = null;
        ResultSet  resultado = null;
        try {
            conexao = ClasseConexao.Conectar();
            comando = conexao.createStatement();
            String meu_sql = "SELECT idPedido,nome_peca_pedido,quantidade_pedido, descricao_pedido, data_compra_pedido, preco_compra_pedido,"
            		+ "campo_conclusao from pedido_de_compra ORDER BY idPedido  DESC";
            resultado = comando.executeQuery(meu_sql); // Executa o SQL
            tabela_lista.setModel(DbUtils.resultSetToTableModel(resultado));
            tabela_lista.getColumnModel().getColumn(0).setHeaderValue("ID");
    		tabela_lista.getColumnModel().getColumn(0).setPreferredWidth(35);
    		tabela_lista.getColumnModel().getColumn(1).setHeaderValue("Nome");
    		tabela_lista.getColumnModel().getColumn(1).setPreferredWidth(150);
    		tabela_lista.getColumnModel().getColumn(2).setHeaderValue("Quantidade");
    		tabela_lista.getColumnModel().getColumn(2).setPreferredWidth(85);
    		tabela_lista.getColumnModel().getColumn(3).setHeaderValue("Descrição");
    		tabela_lista.getColumnModel().getColumn(3).setPreferredWidth(200);
    		tabela_lista.getColumnModel().getColumn(4).setHeaderValue("Data de Compra");
    		tabela_lista.getColumnModel().getColumn(4).setPreferredWidth(150);
    		tabela_lista.getColumnModel().getColumn(5).setHeaderValue("Preço da compra");
    		tabela_lista.getColumnModel().getColumn(5).setPreferredWidth(150);
    		tabela_lista.getColumnModel().getColumn(6).setHeaderValue("Conclusão");
    		tabela_lista.getColumnModel().getColumn(6).setPreferredWidth(85);
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
	
	public Listar_pecas() {
		
		
		setTitle("Listagem de peças");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 669, 669);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel contentPane_1 = new JPanel();
		contentPane_1.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane_1.setBounds(0, 0, 669, 669);
		contentPane.add(contentPane_1);
		contentPane_1.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Nome peça:");
		lblNewLabel.setBounds(25, 68, 116, 27);
		lblNewLabel.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 17));
		contentPane_1.add(lblNewLabel);
		
		//Botão da data
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setToolTipText("Data de conclusão");
		dateChooser.setBounds(441, 225, 165, 27);
		contentPane_1.add(dateChooser);
		
		
		txtNome_lista = new JTextField();
		txtNome_lista.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 17));
		txtNome_lista.setBounds(136, 72, 144, 27);
		txtNome_lista.setColumns(10);
		contentPane_1.add(txtNome_lista);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(40, 300, 589, 290);
		contentPane_1.add(scrollPane);
		
		txtConclusao = new JTextField();
		txtConclusao.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 17));
		txtConclusao.setEditable(false);
		txtConclusao.setColumns(10);
		txtConclusao.setBounds(136, 140, 144, 27);
		contentPane_1.add(txtConclusao);
		
		tabela_lista = new JTable();
		tabela_lista.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 12));
		tabela_lista.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int linha = tabela_lista.getSelectedRow();
				txtIDPeca_lista.setText(tabela_lista.getModel().getValueAt(linha,0).toString());
				txtNome_lista.setText(tabela_lista.getModel().getValueAt(linha,1).toString());
				txtQuantidade_lista.setText(tabela_lista.getModel().getValueAt(linha,2).toString());
				txtConclusao.setText(tabela_lista.getModel().getValueAt(linha,6).toString());
			}
		});
		scrollPane.setViewportView(tabela_lista);
		Selecionando_lista();
		
		JButton btnNewButton_1 = new JButton("X");
		btnNewButton_1.setBounds(294, 37, 17, 17);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		contentPane_1.add(btnNewButton_1);
		
		
		////botão para pesquisar uma peça na tabela através do nome
		JButton btnPesquisar_lista = new JButton("Pesquisar");
		btnPesquisar_lista.setBounds(321, 70, 165, 27);
		btnPesquisar_lista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                ResultSet rs =Crud_lc.Selecionar(txtNome_lista.getText());
                DefaultTableModel model = (DefaultTableModel) tabela_lista.getModel();
                model.setRowCount(0);


                try {
                    while (rs != null && rs.next()) {
                        model.addRow(new Object[]{rs.getString("idPedido"), 
                        		rs.getString("nome_peca_pedido"), rs.getString("quantidade_pedido"),
                        		rs.getString("campo_conclusao"),  rs.getString("data_compra_pedido"),
                        		rs.getString("preco_compra_pedido"),rs.getString("descricao_pedido")});
                    }

                } catch (SQLException e2) {
                    e2.printStackTrace();
                }
            }
		});
		btnPesquisar_lista.setToolTipText("Pesquisar pelo nome");
		btnPesquisar_lista.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 17));
		contentPane_1.add(btnPesquisar_lista);
		
		
		
		
		JLabel aaa = new JLabel("ID Pedido:");
		aaa.setBounds(25, 30, 116, 27);
		aaa.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 17));
		contentPane_1.add(aaa);
		
		txtIDPeca_lista = new JTextField();
		txtIDPeca_lista.setEditable(false);
		txtIDPeca_lista.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 17));
		txtIDPeca_lista.setBounds(135, 30, 144, 27);
		txtIDPeca_lista.setColumns(10);
		contentPane_1.add(txtIDPeca_lista);
		
		JLabel lblQuantidade = new JLabel("Quantidade:");
		lblQuantidade.setBounds(25, 102, 116, 27);
		lblQuantidade.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 17));
		contentPane_1.add(lblQuantidade);
		
		txtQuantidade_lista = new JTextField();
		txtQuantidade_lista.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 17));
		txtQuantidade_lista.setBounds(136, 106, 144, 27);
		txtQuantidade_lista.setColumns(10);
		contentPane_1.add(txtQuantidade_lista);
		
		
		//Botão para alterar a quantidade de um pedido, apenas se ele não foi concluído
		JButton btnAlterar_lista = new JButton("Alterar");
		btnAlterar_lista.setBounds(25, 179, 165, 27);
		btnAlterar_lista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if ((txtIDPeca_lista.getText().isBlank() ||
                        txtNome_lista.getText().isBlank() ||
                        txtQuantidade_lista.getText().isBlank()||
                        txtConclusao.getText().isBlank())) {
                        JOptionPane.showMessageDialog(null, "Preencha todos os campos!");
                        return;
               }else if(dateChooser.getDate() == null) {
                   JOptionPane.showMessageDialog(null, "Preencha a data corretamente! Usando o calendário ao lado do campo!");
                   return;
               }
				int verificacao_conclusao = Integer.parseInt(txtConclusao.getText());
				if (verificacao_conclusao == 0){
					Crud_lc.Alterar(Double.parseDouble(txtQuantidade_lista.getText()), Integer.parseInt(txtIDPeca_lista.getText()),txtNome_lista.getText());
					Selecionando_lista();
				}
				else {
					JOptionPane.showMessageDialog(null, "Produto já foi concluído");
				}
			}
		});
		btnAlterar_lista.setToolTipText("Alterar a quantidade, apenas não concluido");
		btnAlterar_lista.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 17));
		contentPane_1.add(btnAlterar_lista);
		
		
		//Botão para deletar, deleta apenas se a peça não foi para o estoque
		JButton btnDeletar_lista = new JButton("Deletar");
		btnDeletar_lista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if ((txtIDPeca_lista.getText().isBlank())) {
                        JOptionPane.showMessageDialog(null, "Preencha o ID!");
                        return;
               }{
				Crud_lc.Deletar(Integer.parseInt(txtIDPeca_lista.getText()));
				}
				Selecionando_lista();
			}
		});
		btnDeletar_lista.setBounds(25, 218, 165, 27);
		btnDeletar_lista.setToolTipText("Botão para deletar, apenas não concluidos");
		btnDeletar_lista.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 17));
		contentPane_1.add(btnDeletar_lista);
		
		JButton btnNewButton_1_1 = new JButton("X");
		btnNewButton_1_1.setBounds(294, 109, 17, 17);
		contentPane_1.add(btnNewButton_1_1);
		
		JButton btnNewButton_1_2 = new JButton("X");
		btnNewButton_1_2.setBounds(294, 75, 17, 17);
		contentPane_1.add(btnNewButton_1_2);
		
		
		//Botão para concluir, e mandar o pedido para o estoque
		JButton btnConcluir = new JButton("Concluir");
		btnConcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if ((txtNome_lista.getText().isBlank() ||
                        txtQuantidade_lista.getText().isBlank()||
                        txtConclusao.getText().isBlank())) {
                        JOptionPane.showMessageDialog(null, "Preencha todos os campos!");
                        return;
               }else if(dateChooser.getDate() == null) {
                   JOptionPane.showMessageDialog(null, "Preencha a data corretamente! Usando o calendário ao lado do campo!");
                   return;
               }
				
				java.sql.Date data_lista = new java.sql.Date(dateChooser.getDate().getTime());
				int verificacao_conclusao = Integer.parseInt(txtConclusao.getText());
				if (verificacao_conclusao == 0){
				Crud_lc.Alterar_conclusao(2, data_lista,Integer.parseInt(txtIDPeca_lista.getText()),txtNome_lista.getText());
				Crud_lc.Inserir_peca_nova(txtNome_lista.getText());
				Crud_lc.Alterar_conclusao(1, data_lista,Integer.parseInt(txtIDPeca_lista.getText()),txtNome_lista.getText());
				}
				else {
					JOptionPane.showMessageDialog(null, "Produto já foi concluído");
				}
				Selecionando_lista();
			}
		});
		btnConcluir.setToolTipText("Botão para concluir");
		btnConcluir.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 17));
		btnConcluir.setBounds(441, 190, 165, 27);
		contentPane_1.add(btnConcluir);
		
		
		//Combo box para pesquisar por tipo de conclusão
		JComboBox cbLista = new JComboBox();
		cbLista.setToolTipText("Pesquise por conclusão");
		cbLista.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 17));
		cbLista.setModel(new DefaultComboBoxModel(new String[] {"Todos", "Concluído", "Não-Concluído"}));
		cbLista.addActionListener(e -> {
            String selectedItem = (String) cbLista.getSelectedItem();
            int conclusao = 3;
            switch (selectedItem) {
            case "Nenhum":
            	JOptionPane.showConfirmDialog(null, "BUGO");
            case "Concluído":
            	conclusao = 1;
                break;
            case "Não-Concluído":
            	conclusao = 0;
                break;
            case "Todos":
            	conclusao = 3;
            	Selecionando_lista();
            	break;
            }
            //Se conclusao for diferente de 3, mostre qual campo é
            if(conclusao != 3) {
            	ResultSet rs = Crud_lc.Selecionar_Campo_Conclusao(conclusao);
            
            DefaultTableModel model = (DefaultTableModel) tabela_lista.getModel();
            model.setRowCount(0);
            try {
                while (rs != null && rs.next()) {
                    model.addRow(new Object[]{rs.getString("idPedido"),
                    		rs.getString("nome_peca_pedido"), rs.getString("quantidade_pedido"),
                    		rs.getString("descricao_pedido"),rs.getString("data_compra_pedido"),
                    		rs.getString("preco_compra_pedido"),rs.getString("campo_conclusao")});
                }

            } catch (SQLException e2) {
                e2.printStackTrace();
            	}
            }
		});
		cbLista.setBounds(441, 260, 165, 27);
		contentPane_1.add(cbLista);
		
		JLabel lblConclusao = new JLabel("Conclusao:");
		lblConclusao.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 17));
		lblConclusao.setBounds(25, 140, 116, 27);
		contentPane_1.add(lblConclusao);
		
		//Botão para recarregar a tabela
		JButton btnRecarregar = new JButton("Recarregar");
		btnRecarregar.setToolTipText("Recarregar a tabela");
		btnRecarregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Selecionando_lista();
				cbLista.setSelectedItem("Todos");
				
			}
		});
		btnRecarregar.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 17));
		btnRecarregar.setBounds(615, 265, 18, 18);
		contentPane_1.add(btnRecarregar);
		
		JLabel lblTabela = new JLabel("Tabela de listagem");
		lblTabela.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 17));
		lblTabela.setBounds(254, 262, 160, 27);
		contentPane_1.add(lblTabela);
		
		
		
		
	}
}