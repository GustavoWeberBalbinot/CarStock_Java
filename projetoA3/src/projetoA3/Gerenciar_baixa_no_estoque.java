package projetoA3;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JTextField;
import com.toedter.calendar.JDateChooser;

import net.proteanit.sql.DbUtils;

import javax.swing.JTextArea;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Gerenciar_baixa_no_estoque extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tabela_baixa;
	private JTextField txtIDBaixa;
	Crud_baixa_para_baixa Crud_baixa = new Crud_baixa_para_baixa();
	
	public void Selecionando_cadastro_estoque()
    {
        // Precisamos criar algumas variáveis para conectar, dar comandos e
        // também para armazenar a pesquisa com o SELECT:
        Connection conexao = null;
        Statement  comando = null;
        ResultSet  resultado = null;
        try {
            conexao = ClasseConexao.Conectar();
            comando = conexao.createStatement();
            String meu_sql = "SELECT * FROM baixa_no_estoque";
            resultado = comando.executeQuery(meu_sql); // Executa o SQL

            tabela_baixa.setModel(DbUtils.resultSetToTableModel(resultado));
            tabela_baixa.getColumnModel().getColumn(0).setResizable(false);

            tabela_baixa.getColumnModel().getColumn(0).setHeaderValue("ID");
            tabela_baixa.getColumnModel().getColumn(1).setHeaderValue("Nome da peça");
            tabela_baixa.getColumnModel().getColumn(2).setHeaderValue("Quantidade da baixa");
            tabela_baixa.getColumnModel().getColumn(3).setHeaderValue("Data da baixa");
            tabela_baixa.getColumnModel().getColumn(4).setHeaderValue("Motivo da baixa");
            tabela_baixa.getColumnModel().getColumn(5).setHeaderValue("ID Peça");
            tabela_baixa.getColumnModel().getColumn(6).setHeaderValue("ID Ordem");
            tabela_baixa.getColumnModel().getColumn(0).setPreferredWidth(25);
            tabela_baixa.getColumnModel().getColumn(1).setPreferredWidth(110);
            tabela_baixa.getColumnModel().getColumn(2).setPreferredWidth(80);
            tabela_baixa.getColumnModel().getColumn(3).setPreferredWidth(25);
            tabela_baixa.getColumnModel().getColumn(4).setPreferredWidth(160);
            tabela_baixa.getColumnModel().getColumn(5).setPreferredWidth(30);
            tabela_baixa.getColumnModel().getColumn(6).setPreferredWidth(30);
            
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
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gerenciar_baixa_no_estoque frame = new Gerenciar_baixa_no_estoque();
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
	public Gerenciar_baixa_no_estoque() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 957, 621);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(214, 215, 237));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(44, 233, 854, 314);
		contentPane.add(scrollPane);
		
		txtIDBaixa = new JTextField();
		txtIDBaixa.setEnabled(false);
		txtIDBaixa.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 17));
		txtIDBaixa.setBounds(489, 200, 124, 27);
		contentPane.add(txtIDBaixa);
		txtIDBaixa.setColumns(10);
		
		JDateChooser data_da_baixa = new JDateChooser();
		data_da_baixa.setDateFormatString("dd'/'MM'/'yyyy");
		data_da_baixa.setBounds(173, 27, 169, 27);
		contentPane.add(data_da_baixa);
		
		JTextArea ta_motivo_baixa = new JTextArea();
		ta_motivo_baixa.setBounds(192, 86, 385, 61);
		contentPane.add(ta_motivo_baixa);{
			FiltroDeCaracteres filtro = new FiltroDeCaracteres(FiltroDeCaracteres.TipoDeFiltro.email, 50);
			ta_motivo_baixa.setDocument(filtro);
		}
		
		tabela_baixa = new JTable();
		tabela_baixa.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//Variavel que seleciona a linha que clica
				int linha = tabela_baixa.getSelectedRow();
				
				//Pega o campo e coloca no campo de texto selecionado
				txtIDBaixa.setText(tabela_baixa.getModel().getValueAt(linha,0).toString());
				ta_motivo_baixa.setText(tabela_baixa.getModel().getValueAt(linha,4).toString());
				java.sql.Date diaBaixa = (java.sql.Date) tabela_baixa.getModel().getValueAt(linha, 3);
                if (diaBaixa != null) {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    String dataFormatada = sdf.format(diaBaixa);
                    data_da_baixa.setDate(diaBaixa);
                    Selecionando_cadastro_estoque();	
				 }
               		}
		});
		scrollPane.setViewportView(tabela_baixa);{
			Selecionando_cadastro_estoque();
		}
		
		JLabel lblNewLabel = new JLabel("Tabela de baixa no estoque");
		lblNewLabel.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 20));
		lblNewLabel.setBounds(43, 200, 251, 27);
		contentPane.add(lblNewLabel);
		
		JLabel lblDataDaBaixa = new JLabel("Data da baixa:");
		lblDataDaBaixa.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 20));
		lblDataDaBaixa.setBounds(43, 27, 147, 27);
		contentPane.add(lblDataDaBaixa);
		
		JLabel lblNewLabel_1_1 = new JLabel("ID Baixa:");
		lblNewLabel_1_1.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 20));
		lblNewLabel_1_1.setBounds(407, 200, 91, 27);
		contentPane.add(lblNewLabel_1_1);
		
		JButton btnNewButton = new JButton("Deletar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtIDBaixa.getText().isBlank()) {
						//Mensagem caso alguma das verificações seja verdadeira
							JOptionPane.showMessageDialog(null, "Preencha todos os campos necessários!");
							
							//Retorna para não continuar o código abaixo
							return;
					}
				Crud_baixa.Deletar(Integer.parseInt(txtIDBaixa.getText()));
				Selecionando_cadastro_estoque();
			}
			
		});
		btnNewButton.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 20));
		btnNewButton.setBounds(623, 200, 110, 27);
		contentPane.add(btnNewButton);
		
		JLabel lblMotivoDaBaixa = new JLabel("Motivo da baixa:");
		lblMotivoDaBaixa.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 20));
		lblMotivoDaBaixa.setBounds(43, 86, 147, 27);
		contentPane.add(lblMotivoDaBaixa);
		
		JButton btnAlterar = new JButton("Alterar");
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtIDBaixa.getText().isBlank()||
					ta_motivo_baixa.getText().isBlank()) {
					//Mensagem caso alguma das verificações seja verdadeira
						JOptionPane.showMessageDialog(null, "Preencha todos os campos necessários!");
						
						//Retorna para não continuar o código abaixo
						return;
				}else if(data_da_baixa.getDate() == null) {
					JOptionPane.showMessageDialog(null, "Preencha a data corretamente! Usando o calendário ao lado do campo!");
					return;
				}
			java.sql.Date diaBaixa = new java.sql.Date(data_da_baixa.getDate().getTime());
			Crud_baixa.Alterar(diaBaixa, ta_motivo_baixa.getText(),Integer.parseInt(txtIDBaixa.getText()));
			Selecionando_cadastro_estoque();
			}
		});
		btnAlterar.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 20));
		btnAlterar.setBounds(743, 199, 110, 27);
		contentPane.add(btnAlterar);
		
		JButton btnAlterar_1 = new JButton("Alterar");
		btnAlterar_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ta_motivo_baixa.setText("");
			}
		});
		btnAlterar_1.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 20));
		btnAlterar_1.setBounds(587, 91, 17, 17);
		contentPane.add(btnAlterar_1);
		
		JButton btnAlterar_1_1 = new JButton("Alterar");
		btnAlterar_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				data_da_baixa.setDate(null);
			}
		});
		btnAlterar_1_1.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 20));
		btnAlterar_1_1.setBounds(352, 31, 17, 17);
		contentPane.add(btnAlterar_1_1);
	}
}
