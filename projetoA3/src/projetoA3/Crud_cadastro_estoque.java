package projetoA3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import javax.swing.JOptionPane;

public class Crud_cadastro_estoque {
	
	//Para poder deletar uma peça
	public void Deletar(int idPecas)
	{
		//para poder ter uma conexão com o banco de dados
		Connection conexao = null;
		PreparedStatement comando = null;
		
		try {
			
			//Faz a consulta
			conexao = ClasseConexao.Conectar();
			String sql = "DELETE FROM cadastro_pecas_estoque WHERE idPecas = ?";
			
			//Seta o valor
			comando = conexao.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			comando.setInt(1,idPecas);

			
			
			//Verificação para confirmar o DELETE
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
	
	//Para poder alterar a peça
	public void Alterar(String nome_pecas, double quantidade_comprada, String capacidade, String fornecedor, String email_fornecedor, double preco_compra, java.sql.Date data_entrada, String descricao_pecas, int idPecas)
	{
		//Para poder conectar co o banco de dados
		Connection conexao = null;
		PreparedStatement comando = null;
		
		
		try {
			//Faz a consulta
			conexao = ClasseConexao.Conectar();
			String sql = "UPDATE cadastro_pecas_estoque SET "
					+ "nome_pecas=?, quantidade_total=?, "
					+ "capacidade=?, fornecedor=?, email_fornecedor=?, preco_compra=?, preco_total= preco_compra * quantidade_total, preco_venda=preco_compra*1.30, "
					+ "data_entrada=?, descricao_pecas=? WHERE idPecas = ?";
			
			//Seta os valores
			comando = conexao.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			comando.setString(1, nome_pecas);
	        comando.setDouble(2, quantidade_comprada);
		    comando.setString(3, capacidade);
		    comando.setString(4, fornecedor);
		    comando.setString(5, email_fornecedor);
		    comando.setDouble(6, preco_compra);
		    comando.setDate(7, data_entrada);
		    comando.setString(8, descricao_pecas);
		    comando.setInt(9, idPecas);
			
			
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
	
	//Para fazer a pesquisa específica
	public ResultSet Selecionar(String nome_pesquisa)
    {
		//Para conectar com o banco de dados
        Connection conexao = null;
        PreparedStatement comando = null;
        ResultSet resultado = null;

        try {
        	//Faz a consulta
            conexao = ClasseConexao.Conectar();
            String sql = "SELECT * FROM cadastro_pecas_estoque WHERE nome_pecas LIKE ?";
            comando = conexao.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            //Seta o resultado e retorna o valor
            comando.setString(1, "%" + nome_pesquisa + "%");
            resultado = comando.executeQuery();
            return resultado;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;

        }
    }
	
	//Para funcionar o comboboc
	public ResultSet Selecionar_Cidades(String estadoSelecionado) {
		//Para conectar com o banco de dados
	    Connection conexao = null;
	    Statement comando = null;
	    ResultSet resultado = null;

	    try {
	    	//Faz a consulta
	        conexao = ClasseConexao.Conectar();
	        String sql = "SELECT * " +
	                "FROM municipio m " +
	                "JOIN estado e ON m.idEstadoFK = e.idEstado " +
	                "WHERE e.nome_estado = '" + estadoSelecionado + "' " +
	                "ORDER BY m.nome_cidade";
	        //Pega os valores e retorna o resultado
	        comando = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	        resultado = comando.executeQuery(sql);
	        return resultado;

	    } catch (SQLException e) {
	        return null;

	    }
	}

	//Para poder inserir a peça
	public void Inserir(String nome_pecas, double quantidade_comprada, String capacidade, String fornecedor, String email_fornecedor, double preco_compra, java.sql.Date data_entrada, String descricao_pecas){
		//Para conectar com o banco de dados
		Connection conexao = null;
	    PreparedStatement comando = null;
	    ResultSet resultado = null;

	    try {
	    	//Faz a consulta
	        conexao = ClasseConexao.Conectar();
	        String sql = "INSERT INTO cadastro_pecas_estoque(nome_pecas, quantidade_total, capacidade, "
	        		+ "fornecedor, email_fornecedor, preco_compra, preco_total, preco_venda, data_entrada, "
	        		+ "descricao_pecas) VALUES(?,?,?,?,?,?,preco_compra * quantidade_total, (preco_compra * 1.30),?,?)";
	                                        
	        //Seta os valores
	        comando = conexao.prepareStatement(sql);
	        comando = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	        comando.setString(1, nome_pecas);
	        comando.setDouble(2, quantidade_comprada);
	        comando.setString(3, capacidade);
	        comando.setString(4, fornecedor);
	        comando.setString(5, email_fornecedor);
	        comando.setDouble(6, preco_compra);
	        comando.setDate(7, data_entrada);
	        comando.setString(8, descricao_pecas);
	        
	        //Verifica se algum campo foi cadastrado
	    	if (comando.executeUpdate() == 1) {
				JOptionPane.showMessageDialog(null, "A peça foi cadastrada com sucesso");
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
