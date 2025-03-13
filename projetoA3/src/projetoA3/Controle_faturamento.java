package projetoA3;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableModel;

import com.toedter.calendar.JDateChooser;

import net.proteanit.sql.DbUtils;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;

public class Controle_faturamento extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tabela_ordem_faturamento;
	private JScrollPane scrollPane;
	Crud_faturamento cf = new Crud_faturamento();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Controle_faturamento frame = new Controle_faturamento();
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
	
	public void Selecionando_ordem_faturamento()
    {
        // Precisamos criar algumas variáveis para conectar, dar comandos e
        // também para armazenar a pesquisa com o SELECT:
        Connection conexao = null;
        Statement  comando = null;
        ResultSet  resultado = null;
        try {
            conexao = ClasseConexao.Conectar();
            comando = conexao.createStatement();
            String meu_sql = "SELECT `idOrdem`, `nomeCliente`, `nomePcsOrdem`, `valorServ`, `valorPcs`, `qntdComprada`, `protocolo`, `dataServ`  FROM `ordem_servico`";
            resultado = comando.executeQuery(meu_sql); // Executa o SQL
            
            tabela_ordem_faturamento.setModel(DbUtils.resultSetToTableModel(resultado));
            tabela_ordem_faturamento.getColumnModel().getColumn(0).setHeaderValue("ID Ordem");
            tabela_ordem_faturamento.getColumnModel().getColumn(1).setHeaderValue("Nome do Cliente");
            tabela_ordem_faturamento.getColumnModel().getColumn(2).setHeaderValue("Nome da peça");
            tabela_ordem_faturamento.getColumnModel().getColumn(3).setHeaderValue("Valor do serviço");
            tabela_ordem_faturamento.getColumnModel().getColumn(4).setHeaderValue("Valor da Peça");
            tabela_ordem_faturamento.getColumnModel().getColumn(5).setHeaderValue("Quantidade Comprada");
            tabela_ordem_faturamento.getColumnModel().getColumn(6).setHeaderValue("Protocolo");
            tabela_ordem_faturamento.getColumnModel().getColumn(7).setHeaderValue("Data da ordem");
            tabela_ordem_faturamento.getColumnModel().getColumn(0).setPreferredWidth(35);
            tabela_ordem_faturamento.getColumnModel().getColumn(1).setPreferredWidth(135);
            tabela_ordem_faturamento.getColumnModel().getColumn(2).setPreferredWidth(80);
            tabela_ordem_faturamento.getColumnModel().getColumn(3).setPreferredWidth(70);
            tabela_ordem_faturamento.getColumnModel().getColumn(4).setPreferredWidth(70);
            tabela_ordem_faturamento.getColumnModel().getColumn(5).setPreferredWidth(110);
            tabela_ordem_faturamento.getColumnModel().getColumn(6).setPreferredWidth(70);
            tabela_ordem_faturamento.getColumnModel().getColumn(7).setPreferredWidth(80);
// O nome da sua jTable deve ser “tabela_listagem”

            


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
	
	public Controle_faturamento() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1033, 594);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JDateChooser data_inicio = new JDateChooser();
		data_inicio.setBounds(149, 24, 162, 27);
		contentPane.add(data_inicio);
		
		JDateChooser data_final = new JDateChooser();
		data_final.setBounds(383, 24, 175, 27);
		contentPane.add(data_final);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(98, 286, 876, 195);
		contentPane.add(scrollPane);
		
		tabela_ordem_faturamento = new JTable();
		scrollPane.setViewportView(tabela_ordem_faturamento);
		Selecionando_ordem_faturamento();
		
		JButton btnPesquisar = new JButton("Pesquisar");
		btnPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
		       if (data_inicio.getDate() == null || data_final.getDate() == null) {
		    	   JOptionPane.showMessageDialog(null, "Preencha as datas");
		    	   return;
		       }
		       java.sql.Date dataInicio = new java.sql.Date(data_inicio.getDate().getTime());
		       java.sql.Date dataFinal = new java.sql.Date(data_final.getDate().getTime());
		       
		        ResultSet rs = cf.Selecionar(dataInicio, dataFinal);
		        tabela_ordem_faturamento.setModel(DbUtils.resultSetToTableModel(rs));
			}
		});
		btnPesquisar.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 20));
		btnPesquisar.setBounds(581, 24, 128, 27);
		contentPane.add(btnPesquisar);
		
		JButton btnSomar = new JButton("Total");
		btnSomar.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        double soma = 0;
		        double valor1 = 0;
		        double valor2 = 0;
		        double totalvalor1 = 0;
		        double totalvalor2 = 0;
		        for (int i = 0; i < tabela_ordem_faturamento.getRowCount(); i++) {
		            valor1 = Double.parseDouble(tabela_ordem_faturamento.getValueAt(i, 3).toString());
		            valor2 = Double.parseDouble(tabela_ordem_faturamento.getValueAt(i, 4).toString());
		            soma += valor1 + valor2;
		            totalvalor1 = totalvalor1 + valor1; 
		            totalvalor2 = totalvalor2 + valor2;
		        }
		        JOptionPane.showMessageDialog(null, "Valor total do serviço: " + totalvalor1+ "\nValor total das peças: " + totalvalor2 + "\nValor total: " + soma + "");
		    }
		});

		btnSomar.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 20));
		btnSomar.setBounds(737, 24, 120, 27);
		contentPane.add(btnSomar);
		
		JLabel lblNewLabel = new JLabel("De:");
		lblNewLabel.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 20));
		lblNewLabel.setBounds(111, 24, 39, 27);
		contentPane.add(lblNewLabel);
		
		JLabel lblAt = new JLabel("Até:");
		lblAt.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 20));
		lblAt.setBounds(334, 24, 39, 27);
		contentPane.add(lblAt);
		
		JLabel lblTabelaDeOrdem = new JLabel("Tabela de ordem de serviço");
		lblTabelaDeOrdem.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 20));
		lblTabelaDeOrdem.setBounds(380, 241, 259, 27);
		contentPane.add(lblTabelaDeOrdem);
		
		JTextArea taDescricao_faturamento = new JTextArea();
		taDescricao_faturamento.setWrapStyleWord(true);
		taDescricao_faturamento.setLineWrap(true);
		taDescricao_faturamento.setBounds(218, 112, 573, 100);
		contentPane.add(taDescricao_faturamento);{
			FiltroDeCaracteres filtro = new FiltroDeCaracteres(FiltroDeCaracteres.TipoDeFiltro.email, 400);
			taDescricao_faturamento.setDocument(filtro);
		}
		
		JLabel lblDescrio = new JLabel("Descrição:");
		lblDescrio.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 20));
		lblDescrio.setBounds(111, 99, 97, 35);
		contentPane.add(lblDescrio);
		
		JButton btnPesquisar_1 = new JButton("Pesquisar");
		btnPesquisar_1.setFont(new Font("Tahoma", Font.PLAIN, 17));
		btnPesquisar_1.setBounds(801, 117, 17, 17);
		contentPane.add(btnPesquisar_1);
		
		JButton btnConcluir = new JButton("Concluir");
		btnConcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(taDescricao_faturamento.getText().isBlank()){
					JOptionPane.showMessageDialog(null, "Preencha a descrição");
					return;
				}
				cf.Inserir(taDescricao_faturamento.getText());
				
			}
		});
		btnConcluir.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 20));
		btnConcluir.setBounds(98, 241, 128, 27);
		contentPane.add(btnConcluir);
		
		JButton btn_faturamento = new JButton("Gerenciar faturamentos");
		btn_faturamento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Gerenciar_faturamentos gf = new Gerenciar_faturamentos();
				gf.setVisible(true);
				Controle_faturamento controle_faturamento = new Controle_faturamento();
				controle_faturamento.setVisible(false);
				

			}
		});
		btn_faturamento.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 20));
		btn_faturamento.setBounds(685, 241, 251, 27);
		contentPane.add(btn_faturamento);
		
		JButton btnConcluir_1 = new JButton("<<");
		btnConcluir_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Menu_principal menu = new Menu_principal();
				menu.setVisible(true);
				dispose();
			}
		});
		btnConcluir_1.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 20));
		btnConcluir_1.setBounds(10, 498, 128, 27);
		contentPane.add(btnConcluir_1);{
		}
	}
}
