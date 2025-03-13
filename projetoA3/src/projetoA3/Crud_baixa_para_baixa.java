package projetoA3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class Crud_baixa_para_baixa {


	public void Deletar(int idBaixa)
	{
		Connection conexao = null;
		PreparedStatement comando = null;
		
		try {
			conexao = ClasseConexao.Conectar();
			String sql = "DELETE FROM baixa_no_estoque WHERE idBaixa = ?";
			
			comando = conexao.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			comando.setInt(1,idBaixa);

	        int respostaDeletar = JOptionPane.showConfirmDialog(null, "Você tem certeza que deseja deletar esta baixa?", "Alerta", JOptionPane.YES_NO_OPTION);
			if (respostaDeletar == JOptionPane.NO_OPTION) {
				return;
			}
			else {
				JOptionPane.showMessageDialog(null, "A baixa foi deletada com sucesso");
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

  //Para poder alterar a quantidade de peças
  	public void Alterar(java.sql.Date data_baixa, String motivo, int idBaixa)
  	{
  		
  		//Para poder setar os valores
  		Connection conexao = null;
  		PreparedStatement comando = null;
  		
  		//Consulta
  		try {
  			conexao = ClasseConexao.Conectar();
  			String sql = "UPDATE baixa_no_estoque SET data_baixa=?,motivo_baixa=? WHERE idBaixa = ?";

  			
  			//Setando os valores
  			comando = conexao.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
  			comando.setDate(1,data_baixa);
  			comando.setString(2,motivo);
  			comando.setInt(3,idBaixa);
  			comando.executeUpdate();
  			JOptionPane.showMessageDialog(null, "A baixa foi deletada com sucesso");
  			
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
    

    
}

