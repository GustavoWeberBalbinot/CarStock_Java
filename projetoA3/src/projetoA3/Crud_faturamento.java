package projetoA3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import javax.swing.JOptionPane;

public class Crud_faturamento {
	
	public void Deletar(int idFaturamento)
	{
		Connection conexao = null;
		PreparedStatement comando = null;
		
		try {
			conexao = ClasseConexao.Conectar();
			String sql = "DELETE FROM controle_faturamento WHERE idFaturamento = ?";
			
			comando = conexao.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			comando.setInt(1,idFaturamento);

			
			
			
	        int respostaDeletar = JOptionPane.showConfirmDialog(null, "Você tem certeza que deseja deletar esta peça?", "Alerta", JOptionPane.YES_NO_OPTION);
			if (respostaDeletar == JOptionPane.NO_OPTION) {
				return;
			}
			else {
				JOptionPane.showMessageDialog(null, "O faturamento foi deletada com sucesso");
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
	
	public void Alterar(String taFaturamento, int idFaturamento)
	{
		Connection conexao = null;
		PreparedStatement comando = null;
		
		
		try {
			conexao = ClasseConexao.Conectar();
			String sql = "UPDATE controle_faturamento SET "
					+ "descricao_faturamento = ? WHERE idFaturamento = ?";
			
				comando = conexao.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
				comando.setString(1, taFaturamento);
		        comando.setInt(2, idFaturamento);
			
			
				if (comando.executeUpdate() == 1) {
					JOptionPane.showMessageDialog(null, "A descrição do faturamento foi alterada com sucesso");
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
	
	
	public ResultSet Selecionar(Date dataInicio, Date dataFinal)
    {
        Connection conexao = null;
        PreparedStatement comando = null;
        ResultSet resultado = null;

        try {
            conexao = ClasseConexao.Conectar();
            String sql = "SELECT * FROM ordem_servico WHERE dataServ BETWEEN '" + dataInicio + "' AND '" + dataFinal + "' AND conclusao_servico = 1" ;
            comando = conexao.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            resultado = comando.executeQuery();
            return resultado;

        } catch (SQLException e) {
            e.printStackTrace();
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


	
	
	
	
	public void Inserir(String descricao_faturamento){
	    Connection conexao = null;
	    PreparedStatement comando = null;

	    try {
	        conexao = ClasseConexao.Conectar();

	        String sql = "INSERT INTO controle_faturamento(descricao_faturamento) VALUES(?)";
	        			 
	                                        

	        comando = conexao.prepareStatement(sql);
	        comando = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	        comando.setString(1, descricao_faturamento);

	        
	    	if (comando.executeUpdate() == 1) {
				JOptionPane.showMessageDialog(null, "Faturamento feito com sucesso");
			}else {
				JOptionPane.showMessageDialog(null, "Algo deu errado");
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

