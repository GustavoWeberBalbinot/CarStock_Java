package projetoA3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;


public class Crud_cad_cliente {
	
	public void Deletar(int idPecas)
	{
		Connection conexao = null;
		PreparedStatement comando = null;
		
		try {
			conexao = ClasseConexao.Conectar();
			String sql = "DELETE FROM cadastro_cliente WHERE idCliente = ?";
			
			comando = conexao.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			comando.setInt(1,idPecas);

			
			
			
	        int respostaDeletar = JOptionPane.showConfirmDialog(null, "Você tem certeza que deseja deletar este cliente?", "Alerta", JOptionPane.YES_NO_OPTION);
			if (respostaDeletar == JOptionPane.NO_OPTION) {
				return;
			}
			else {
				JOptionPane.showMessageDialog(null, "O cliente foi deletada com sucesso");
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
	
	public void Alterar(String nome_cliente, int cpf, java.sql.Date data_nascimento, String estado, String cidade, int cep, int telefone, String endereco, String descricao_endereco, int idCliente){
		Connection conexao = null;
		PreparedStatement comando = null;
		
		
		try {
			conexao = ClasseConexao.Conectar();
			String sql = "UPDATE cadastro_cliente SET "
					+ "nome_cliente=?, cpf=?, "
					+ "data_nascimento=?, estado=?, cidade=?, "
					+ "cep=?, telefone=?, endereco=?, descricao_endereco=? "
					+ "WHERE idCliente =?";
			
				comando = conexao.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
				comando.setString(1, nome_cliente);
		        comando.setInt(2, cpf);
		        comando.setDate(3, data_nascimento);
		        comando.setString(4, estado);
		        comando.setString(5, cidade);
		        comando.setInt(6, cep);
		        comando.setInt(7, telefone);
		        comando.setString(8, endereco);
		        comando.setString(9, descricao_endereco);
		        comando.setInt(10, idCliente);
			
					JOptionPane.showMessageDialog(null, "O cliente foi alterado com sucesso");
					comando.executeUpdate();
				
			
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
            String sql = "SELECT * FROM cadastro_cliente WHERE nome_cliente LIKE ?";
            comando = conexao.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            comando.setString(1, "%" + nome_pesquisa + "%");
            resultado = comando.executeQuery();
            return resultado;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;

        }
    }
	
	public ResultSet SelecionarEstados()
	{
		Connection conexao = null;
		Statement comando = null;
		ResultSet resultado = null;
		
		try {
			conexao = ClasseConexao.Conectar();
			String sql = "SELECT * from estado";
			comando = conexao.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			resultado = comando.executeQuery(sql);
			return resultado;
			
		} catch (SQLException e) {
			return null;
			
		}
	}
	
	public ResultSet Selecionar_Cidades(String estadoSelecionado) {
	    Connection conexao = null;
	    Statement comando = null;
	    ResultSet resultado = null;

	    try {
	        conexao = ClasseConexao.Conectar();
	        String sql = "SELECT * " +
	                "FROM municipio m " +
	                "JOIN estado e ON m.idEstadoFK = e.idEstado " +
	                "WHERE e.nome_estado = '" + estadoSelecionado + "' " +
	                "ORDER BY m.nome_cidade";

	        comando = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	        resultado = comando.executeQuery(sql);
	        return resultado;

	    } catch (SQLException e) {
	        return null;

	    }
	}



	
	
	
	
	public void Inserir(String nome_cliente, int cpf, java.sql.Date data_nascimento, String estado, String cidade, int cep, int telefone, String endereco, String descricao_endereco){
	    Connection conexao = null;
	    PreparedStatement comando = null;

	    try {
	        conexao = ClasseConexao.Conectar();

	        // Código para inserir dados na tabela `cidade` usando uma subconsulta

	        String sql = "INSERT INTO cadastro_cliente(nome_cliente, cpf, data_nascimento, estado, cidade, cep, telefone, endereco, descricao_endereco) VALUES(?,?,?,?,?,?,?,?,?)";
	        			 
	                                        

	        comando = conexao.prepareStatement(sql);
	        comando = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	        comando.setString(1, nome_cliente);
	        comando.setInt(2, cpf);
	        comando.setDate(3, data_nascimento);
	        comando.setString(4, estado);
	        comando.setString(5, cidade);
	        comando.setInt(6, cep);
	        comando.setInt(7, telefone);
	        comando.setString(8, endereco);
	        comando.setString(9, descricao_endereco);
	        
	      
	        comando.executeLargeUpdate();
	        
			JOptionPane.showMessageDialog(null, "A peça foi cadastrada com sucesso");

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
