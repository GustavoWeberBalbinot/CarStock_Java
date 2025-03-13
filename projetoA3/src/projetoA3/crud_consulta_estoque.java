package projetoA3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class crud_consulta_estoque {
	
	public void Deletar(String cpf)
	{
		Connection conexao = null;
		PreparedStatement comando = null;
		
		
		try {
			conexao = ClasseConexao.Conectar();
			String sql = "DELETE FROM contatos where cpf=?";
			
			comando = conexao.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			comando.setString(1,cpf);
			
			
			if(comando.executeUpdate()>0) {
				
				
				System.out.println("Dados excluídos");
				
				
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
	
	public void Alterar(String cpf, String nome, int idade)
	{
		Connection conexao = null;
		PreparedStatement comando = null;
		
		
		try {
			conexao = ClasseConexao.Conectar();
			String sql = "UPDATE contatos SET nome=?,idade=? WHERE cpf=?";
			
			comando = conexao.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			comando.setString(1,nome);
			comando.setInt(2,idade);
			comando.setString(3,cpf);
			
			
			if(comando.executeUpdate()>0) {
				
				
				System.out.println("Dados alterados com sucesso");
				
				
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
	
	
	public ResultSet Selecionar()
	{
		Connection conexao = null;
		Statement comando = null;
		ResultSet resultado = null;
		
		try {
			conexao = ClasseConexao.Conectar();
			String sql = "SELECT * FROM cadastro_pecas_estoque JOIN consulta_estoque +"
					+ "ON consulta_estoque.idPecasFK = cadastro_pecas_estoque.idPecas+"
					+ " WHERE cadastro_pecas_estoque.nome =? OR  +"
					+ "cadastro_pecas_estoque.idPecas =?";
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


	
	
	
	
	public void Inserir(String cidade, String estado, String idEstadoCadFK, String idCidadeFK ) {
	    Connection conexao = null;
	    PreparedStatement comando = null;
	    ResultSet resultado = null;

	    try {
	        conexao = ClasseConexao.Conectar();

	        // Código para inserir dados na tabela `cidade` usando uma subconsulta

	        String sql = "INSERT INTO cadastro_cliente(estado, cidade) VALUES(?,?,?,?)";
	        
	                                        

	        comando = conexao.prepareStatement(sql);
	        comando.setString(1, estado);
	        comando.setString(2, cidade);

	        // Executa o comando SQL
	        comando.executeUpdate();

	        // Pega o código gerado
	        resultado = comando.getGeneratedKeys();
	        if (resultado.next()) {
	            System.out.println("Dados gravados na tabela com o código: " + resultado.getInt(1));
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
