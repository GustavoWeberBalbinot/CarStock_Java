package projetoA3;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import java.awt.Color;

public class Menu_principal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Menu_principal frame = new Menu_principal();
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
	public Menu_principal() {
		setTitle("Menu Principal");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1086, 530);
		setMinimumSize(new Dimension(850, 650));
		contentPane = new JPanel();
		contentPane.setBackground(new Color(214, 215, 237));
		contentPane.setBorder(new EmptyBorder(5, 60, 5, 60));

		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 2, 20, 30));

		JLabel lblNewLabel = new JLabel("Escolha uma janela");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 30));
		contentPane.add(lblNewLabel);
		
		JLabel label = new JLabel("");
		contentPane.add(label);
		
		JButton btnCadPecas = new JButton("Cadastro de peças");
		btnCadPecas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Cadastro_pecas cdp = new Cadastro_pecas();
				cdp.setBounds(150, 50, 1200, 700);
				cdp.setVisible(true);
				dispose();
			}
		});
		btnCadPecas.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 25));
		contentPane.add(btnCadPecas);
		
		JButton btnCadCliente = new JButton("Cadastro cliente");
		btnCadCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cad_cliente janela_Cad_cliente = new cad_cliente();
				janela_Cad_cliente.setVisible(true);
				dispose();
			}
		});
		btnCadCliente.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 25));
		contentPane.add(btnCadCliente);
		
		JButton btnPedidoCompra = new JButton("Pedido de compra");
		btnPedidoCompra.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Pedido_de_compra pdcm = new Pedido_de_compra();
				pdcm.setVisible(true);
				dispose();
			}
		});
		btnPedidoCompra.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 25));
		contentPane.add(btnPedidoCompra);
		
		JButton btnBaixaEstoque = new JButton("Baixa estoque");
		btnBaixaEstoque.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Baixa_estoque bem = new Baixa_estoque();
				bem.setVisible(true);
				dispose();
			}
		});
		btnBaixaEstoque.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 25));
		contentPane.add(btnBaixaEstoque);
		
		JButton btnOrdemServico = new JButton("Ordem de serviço");
		btnOrdemServico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Ordem_servico osm = new Ordem_servico();
				osm.setVisible(true);
				dispose();
			}
		});
		btnOrdemServico.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 25));
		contentPane.add(btnOrdemServico);
		
		JButton btnFaturamento = new JButton("Faturamento");
		btnFaturamento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Controle_faturamento cfm = new Controle_faturamento();
				cfm.setVisible(true);
				dispose();
			}
		});
		btnFaturamento.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 25));
		contentPane.add(btnFaturamento);
		
	}


}
