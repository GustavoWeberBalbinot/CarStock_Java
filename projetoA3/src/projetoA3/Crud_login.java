package projetoA3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import javax.swing.JOptionPane;

public class Crud_login {
	
	public void Deletar(int idPecas)
	{
		Connection conexao = null;
		PreparedStatement comando = null;
		
		try {
			conexao = ClasseConexao.Conectar();
			String sql = "DELETE FROM cadastro_pecas_estoque WHERE idPecas = ?";
			
			comando = conexao.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			comando.setInt(1,idPecas);

			
			
			
	        int respostaDeletar = JOptionPane.showConfirmDialog(null, "Você tem certeza que deseja deletar esta peça?", "Alerta", JOptionPane.YES_NO_OPTION);
			if (respostaDeletar == JOptionPane.NO_OPTION) {
				return;
			}
			else {
				JOptionPane.showMessageDialog(null, "A peça foi deletada com sucesso");
				comando.executeUpdate();
			}
			
		    
	        
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			ClasseConexao.FecharConexao(conexao);
			try {
				comando.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void Alterar(String nome_pecas, double quantidade_comprada, String capacidade, String fornecedor, String email_fornecedor, double preco_compra, double preco_venda, java.sql.Date data_entrada, String descricao_pecas, int idPecas)
	{
		Connection conexao = null;
		PreparedStatement comando = null;
		
		
		try {
			conexao = ClasseConexao.Conectar();
			String sql = "UPDATE cadastro_pecas_estoque SET "
					+ "nome_pecas=?, quantidade_comprada=?, "
					+ "capacidade=?, fornecedor=?, email_fornecedor=?, preco_compra=?, preco_venda=?, "
					+ "data_entrada=?, descricao_pecas=? WHERE idPecas = ?";
			
				comando = conexao.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
				comando.setString(1, nome_pecas);
		        comando.setDouble(2, quantidade_comprada);
		        comando.setString(3, capacidade);
		        comando.setString(4, fornecedor);
		        comando.setString(5, email_fornecedor);
		        comando.setDouble(6, preco_compra);
		        comando.setDouble(7, preco_venda);
		        comando.setDate(8, data_entrada);
		        comando.setString(9, descricao_pecas);
		        comando.setInt(10, idPecas);
			
			
				if (comando.executeUpdate() == 1) {
					JOptionPane.showMessageDialog(null, "A peça foi alterada com sucesso");
				}
				
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			ClasseConexao.FecharConexao(conexao);
			try {
				comando.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public ResultSet Selecionar(String nome_pesquisa)
    {
        Connection conexao = null;
        PreparedStatement comando = null;
        ResultSet resultado = null;

        try {
            conexao = ClasseConexao.Conectar();
            String sql = "SELECT * FROM cadastro_pecas_estoque WHERE nome_pecas LIKE ?";
            comando = conexao.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            comando.setString(1, "%" + nome_pesquisa + "%");
            resultado = comando.executeQuery();
            return resultado;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;

        }
    }
	
	public boolean verificarLogin(String usuario, String senha) {
	    // Obter o nome de usuário e a senha dos dados passados


	    // Cria a conexão com o banco de dados
	    Connection conexao = ClasseConexao.Conectar();
	    PreparedStatement comando = null;
	    ResultSet resultado = null;

	    // Prepara a consulta SQL para verificar se o usuário existe
	    String sql = "SELECT * FROM login_usuario WHERE nome_usuario = ? AND senha_usuario = ?";

	    try {
	        // Cria o objeto PreparedStatement para executar a consulta
	        comando = conexao.prepareStatement(sql);

	        // Define os parâmetros da consulta
	        comando.setString(1, usuario);
	        comando.setString(2, senha);

	        // Executa a consulta
	        resultado = comando.executeQuery();

	        // Verifica se o usuário existe
	        if (resultado.next()) {
	        	String Usuario = resultado.getString("nome_completo");
	        	 JOptionPane.showMessageDialog(null, "Seja bem-vindo " + Usuario);
	        	 Menu_principal menu = new Menu_principal();
	        	 Login login = new Login();
	        	 login.dispose();
	        	 menu.setVisible(true);
	        	 
	            return true;
	        } else {
	            JOptionPane.showMessageDialog(null, "Usuário ou senha inválidos!");
	            return false;
	        
	        } 
	    }catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    } finally {
	        ClasseConexao.FecharConexao(conexao);
	        try {
	            if (comando != null) {
	                comando.close();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}

	
	
	
	public void Inserir(String nome_completo, String usuario, String senha){
	    Connection conexao = null;
	    PreparedStatement comando = null;
	    ResultSet resultado = null;

	    try {
	        conexao = ClasseConexao.Conectar();

	        String sql = "INSERT INTO login_usuario(nome_completo, nome_usuario, senha_usuario, nivel) VALUES(?,?,?,1)";
	        			 
	                                        

	        comando = conexao.prepareStatement(sql);
	        comando = conexao.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
	        comando.setString(1, nome_completo);
	        comando.setString(2, usuario);
	        comando.setString(3, senha);
	        
	        try {
	            comando.executeUpdate();
	            JOptionPane.showMessageDialog(null, "Cadastro feito com sucesso");
	        } catch (SQLException e) {
	            if (e.getErrorCode() == 1062) {
	                JOptionPane.showMessageDialog(null, "Este usuário já existe");
	            } else {
	                JOptionPane.showMessageDialog(null, "Ocorreu um erro ao cadastrar o usuário");
	            }
	        }

	    } catch (SQLException e) {

	        e.printStackTrace();
	    } finally {
	        ClasseConexao.FecharConexao(conexao);
	        try {
	            if (comando != null) {
	                comando.close();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}

	
}
