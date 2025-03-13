package projetoA3;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JPasswordField;

public class Login extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtUsuario;
	Crud_login cl = new Crud_login();
	private JPasswordField pswSenhaL;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
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
	public Login() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1108, 544);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(214, 215, 237));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new RoundedPanel();
		panel.setBackground(new Color(128, 255, 255));
		panel.setBounds(304, 10, 485, 487);
		contentPane.add(panel);
		panel.setLayout(null);
		
		pswSenhaL = new JPasswordField();
		pswSenhaL.setBounds(142, 207, 247, 27);
		panel.add(pswSenhaL);
		
		txtUsuario = new JTextField();
		txtUsuario.setBounds(152, 153, 237, 27);
		panel.add(txtUsuario);
		txtUsuario.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Usuário:");
		lblNewLabel.setBounds(73, 153, 84, 25);
		panel.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 20));
		
		JLabel lblSenha = new JLabel("Senha:");
		lblSenha.setBounds(73, 204, 72, 27);
		panel.add(lblSenha);
		lblSenha.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 20));
		
		JButton btnNewButton = new JButton("Entrar");
		btnNewButton.setBounds(142, 263, 109, 36);
		panel.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
	           // Verifica o login
		    	char[] senha = pswSenhaL.getPassword();
				String senhaString = new String(senha);
		    	if(senhaString.isBlank() || txtUsuario.getText().isBlank()) {
		    		JOptionPane.showMessageDialog(null, "Preencha todos os campos");
		    		return;
		    	}
		    	boolean loginValido = cl.verificarLogin(txtUsuario.getText(), senhaString);

		    	if(loginValido == true) {
		    		dispose();
		    	}
		    	
		    }
		});
		
				btnNewButton.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 20));
				
				JButton btnCadastro = new JButton("Cadastro");
				btnCadastro.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Cadastro_usuario caduser = new Cadastro_usuario();
						caduser.setVisible(true);
						dispose();
					}
				});
				btnCadastro.setBounds(261, 263, 128, 36);
				panel.add(btnCadastro);
				btnCadastro.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 20));
				
				JLabel lblLogin = new JLabel("Login");
				lblLogin.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 25));
				lblLogin.setBounds(206, 45, 72, 36);
				panel.add(lblLogin);
				
			
	}
}
