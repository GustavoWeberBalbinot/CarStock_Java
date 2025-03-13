package projetoA3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class Crud_baixa_estoque {
	
	
	//Para guardar a consulta feita na tabela baixa_no_estoque
	public void Inserir(double quantidade_item_baixa, java.sql.Date data_baixa, String motivo_baixa, int idPcsFK, int idOrdemBaixa)
	{
		//Para poder setar os valores
		Connection conexao = null;
		PreparedStatement comando = null;
		
		//Consulta
		try {
			conexao = ClasseConexao.Conectar();
			String sql = "INSERT INTO baixa_no_estoque (nome_baixa, quantidade_item_baixa, data_baixa, motivo_baixa, idPecasFKBaixa, idOrdemBaixa)\n"
					+ "SELECT os.nomePcsOrdem, ?,?,?, os.idPcsFK, idOrdem \n"
					+ "FROM cadastro_pecas_estoque AS cpe\n"
					+ "JOIN ordem_servico AS os ON cpe.idPecas = os.idPcsFK\n"
					+ "WHERE cpe.idPecas = ? AND  os.idOrdem = ? AND os.conclusao_servico = 2;\n";

			
			//Setando os valores
			comando = conexao.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			comando.setDouble(1,quantidade_item_baixa);
			comando.setDate(2,data_baixa);
			comando.setString(3, motivo_baixa);
			comando.setInt(4,idPcsFK);
			comando.setInt(5,idOrdemBaixa);
			
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

	//Para poder alterar a quantidade de peças
	public void Alterar(String quantidade_comprada, int txtidPecas_baixa, int idOrdemBaixa)
	{
		
		//Para poder setar os valores
		Connection conexao = null;
		PreparedStatement comando = null;
		
		//Consulta
		try {
			conexao = ClasseConexao.Conectar();
			String sql = "UPDATE cadastro_pecas_estoque\r\n"
					+ "JOIN baixa_no_estoque b ON b.idPecasFKBaixa = idPecas\r\n"
					+ "JOIN ordem_servico AS os ON idPecas = os.idPcsFK\r\n"
					+ "SET quantidade_total = quantidade_total - ?\r\n"
					+ "WHERE idPecas = ? AND os.idOrdem = ? AND os.conclusao_servico = 2;\r\n";
			
			//Setando os valores
			comando = conexao.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			comando.setString(1,quantidade_comprada);
			comando.setInt(2,txtidPecas_baixa);
			comando.setInt(3,idOrdemBaixa);
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
	
	//Altera a conclusao do serviço para não ser possivel fazer mais de uma vez a mesma baixa
	public void Alterar_conclusao_servico(int conclusao_servico, int idOrdem)
	{
		
		//Para poder setar os valores
		Connection conexao = null;
		PreparedStatement comando = null;
		
		
		//Consulta
		try {
			conexao = ClasseConexao.Conectar();
			String sql = "UPDATE ordem_servico c\r\n "
					+ "SET conclusao_servico = ? "
					+ "WHERE idOrdem =? ";
			
			//Setando valores
			comando = conexao.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			comando.setInt(1, conclusao_servico);
			comando.setInt(2, idOrdem);
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
	
	//Para poder fazer pesquisas específicas por nome
	public ResultSet Selecionar_pesquisa(String nome_pesquisa) {
		//Para poder fazer a pesquisa
        Connection conexao = null;
        PreparedStatement comando = null;
        ResultSet resultado = null;
        
        //Consulta
        try {
            conexao = ClasseConexao.Conectar();
            String sql = "SELECT * FROM ordem_servico WHERE nomePcsOrdem LIKE ?";
            
            //Salva os valores
            comando = conexao.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            comando.setString(1, "%" + nome_pesquisa + "%");
            resultado = comando.executeQuery();
            return resultado;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;

        } 
        
	}
	
	//Para verificar a conclusão e não deixar a baixa ser feita mais de uma vez
	public ResultSet verificaConclusao(int idOrdemPcs) {
        Connection conexao = null;
        PreparedStatement comando = null;
        ResultSet resultado = null;

        try {
            conexao = ClasseConexao.Conectar();
            String sql = "SELECT * FROM ordem_servico WHERE idOrdem=?";
            comando = conexao.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        	comando.setInt(1, idOrdemPcs);
            
            resultado = comando.executeQuery();
			if (resultado.next()) {
			int conclusao_servico = resultado.getInt("conclusao_servico");
    	    if (conclusao_servico == 1) {
    	    	JOptionPane.showMessageDialog(null, "Essa baixa ja foi feita");
    	    }else {
    	    	JOptionPane.showMessageDialog(null, "Baixa feita com sucesso");
    	    }
			}
            return resultado;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;

        } 
        
	}
		
}