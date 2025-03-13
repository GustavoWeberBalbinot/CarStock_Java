package projetoA3;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JTextField;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class Cadastro_usuario extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNome_completo_cadastro;
	private JTextField txtUsuario_cadastro;
	private JPasswordField pswSenha_cadastro;
	Crud_login clc = new Crud_login();
	Login login = new Login();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Cadastro_usuario frame = new Cadastro_usuario();
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
	public Cadastro_usuario() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 992, 518);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(214, 215, 237));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Nome completo:");
		lblNewLabel.setForeground(new Color(0, 0, 0));
		lblNewLabel.setBackground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 20));
		lblNewLabel.setBounds(280, 117, 157, 41);
		contentPane.add(lblNewLabel);
		
		txtNome_completo_cadastro = new JTextField();
		txtNome_completo_cadastro.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtNome_completo_cadastro.setBounds(430, 117, 255, 33);
		contentPane.add(txtNome_completo_cadastro);
		txtNome_completo_cadastro.setColumns(10);
		
		JLabel lblUsuario = new JLabel("Usuario:");
		lblUsuario.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 20));
		lblUsuario.setBounds(280, 189, 107, 41);
		contentPane.add(lblUsuario);
		
		JLabel lblNewLabel_1_1 = new JLabel("Senha:");
		lblNewLabel_1_1.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 20));
		lblNewLabel_1_1.setBounds(280, 256, 107, 41);
		contentPane.add(lblNewLabel_1_1);
		
		JButton btnCadastro = new JButton("Cadastrar");
		btnCadastro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				char[] senha = pswSenha_cadastro.getPassword();
				String senhaString = new String(senha);
				if(txtNome_completo_cadastro.getText().isBlank()||
					txtUsuario_cadastro.getText().isBlank()||
					senhaString.isBlank()) {
					JOptionPane.showMessageDialog(null, "Preencha todos os campos");
					return;
				}else if((senhaString.length() < 8)){
					JOptionPane.showMessageDialog(null, "A senha deve conter no mínimo 8 caracteres");
					return;
				}else if (!senhaString.matches("^[a-zA-Z0-9*@#$/+-.]+$")) {
				    JOptionPane.showMessageDialog(null, "A senha deve conter os seguintes caractres:a-zA-Z0-9*@#$/+-.");
				    return;
				}
				else {
				
				clc.Inserir(txtNome_completo_cadastro.getText(), txtUsuario_cadastro.getText(), senhaString);
				}
			}
		});
		btnCadastro.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 17));
		btnCadastro.setBounds(360, 334, 117, 29);
		contentPane.add(btnCadastro);
		
		JButton btnRedefinir = new JButton("Redefinir");
		btnRedefinir.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 17));
		btnRedefinir.setBounds(530, 334, 117, 29);
		contentPane.add(btnRedefinir);
		
		JPanel roundedPanel = new RoundedPanel();
        roundedPanel.setBackground(new Color(128, 255, 255));
        roundedPanel.setBounds(236, 10, 505, 461);
		contentPane.add(roundedPanel);
		roundedPanel.setLayout(null);
		
		pswSenha_cadastro = new JPasswordField();
		pswSenha_cadastro.setBounds(113, 252, 337, 31);
		roundedPanel.add(pswSenha_cadastro);
		
		txtUsuario_cadastro = new JTextField();
		txtUsuario_cadastro.setBounds(125, 179, 325, 33);
		roundedPanel.add(txtUsuario_cadastro);
		txtUsuario_cadastro.setColumns(10);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				login.setVisible(true);
				dispose();
			}
		});
		btnLogin.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 17));
		btnLogin.setBounds(205, 386, 117, 29);
		roundedPanel.add(btnLogin);
		
		JLabel lblCadastro = new JLabel("Cadastro");
		lblCadastro.setForeground(Color.BLACK);
		lblCadastro.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 25));
		lblCadastro.setBackground(Color.WHITE);
		lblCadastro.setBounds(194, 31, 116, 36);
		roundedPanel.add(lblCadastro);
	}
}
