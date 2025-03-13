package projetoA3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Set;

import javax.swing.JOptionPane;

import com.mysql.cj.protocol.Resultset;

public class Crud_listar_pecas{
	//Deletar um pedido através do id
	public void Deletar(int idPedido)
	{
		Connection conexao = null;
		PreparedStatement comando = null;
		
		
		try {
			conexao = ClasseConexao.Conectar();
			String sql = "DELETE FROM `pedido_de_compra` WHERE idPedido = ?";
			
			comando = conexao.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			comando.setInt(1,idPedido);
			
			
			int resposta = JOptionPane.showConfirmDialog(null, "Você tem certeza que deseja deletar o pedido", "Confirmação", JOptionPane.YES_NO_OPTION);
            if (resposta == JOptionPane.YES_OPTION) {
				JOptionPane.showMessageDialog(null, "Pedido deletado com sucesso");
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

	//Alaterar a quantidade de um pedido através do id e nome
	public void Alterar(double quantidade_pedido , int idPedido, String nome_peca_pedido)
	{
		Connection conexao = null;
		PreparedStatement comando = null;
		
		
		try {
			conexao = ClasseConexao.Conectar();
			String sql ="UPDATE pedido_de_compra SET quantidade_pedido = ?"
					+ " WHERE idPedido = ? AND nome_peca_pedido = ? ";
			
			comando = conexao.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			comando.setDouble(1, quantidade_pedido);
			comando.setInt(2, idPedido);
			comando.setString(3, nome_peca_pedido);
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
	//Específico para alterar a conclusão, e a data que foi concluído
	public void Alterar_conclusao(int conclusao , java.sql.Date data_conclusao, int idPedido, String nome_peca_pedido)
	{
		Connection conexao = null;
		PreparedStatement comando = null;
		
		
		try {
			conexao = ClasseConexao.Conectar();
			String sql ="UPDATE pedido_de_compra SET campo_conclusao = ?, data_conclusao_pedido = ?"
					+ " WHERE idPedido = ? AND nome_peca_pedido = ? ";
			
			comando = conexao.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			comando.setInt(1, conclusao);
			comando.setDate(2, data_conclusao);
			comando.setInt(3, idPedido);
			comando.setString(4, nome_peca_pedido);
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
	
	
	
	
	//Mecanismo de pesquisa através do nome
	public ResultSet Selecionar(String nome_pesquisa)
	{
        Connection conexao = null;
        PreparedStatement comando = null;
        ResultSet resultado = null;

        try {
            conexao = ClasseConexao.Conectar();
            String sql = "SELECT idPedido, nome_peca_pedido, quantidade_pedido, descricao_pedido, data_compra_pedido,preco_compra_pedido,campo_conclusao FROM pedido_de_compra WHERE nome_peca_pedido LIKE ?";
            comando = conexao.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            comando.setString(1, "%" + nome_pesquisa + "%");
            resultado = comando.executeQuery();
            return resultado;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;

        } 

    }
	//Pesquisa utilizada no combobox, para pesquisar através do campo de conclusão.
	public ResultSet Selecionar_Campo_Conclusao(int conclusao)
	{
        Connection conexao = null;
        PreparedStatement comando = null;
        ResultSet resultado = null;

        try {
            conexao = ClasseConexao.Conectar();
            String sql = "SELECT idPedido, nome_peca_pedido, quantidade_pedido, descricao_pedido, data_compra_pedido,preco_compra_pedido,campo_conclusao FROM pedido_de_compra WHERE campo_conclusao LIKE ? ORDER BY idPedido  DESC";
            comando = conexao.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            comando.setInt(1, conclusao);
            resultado = comando.executeQuery();
            return resultado;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;

        } 
	}
    
	
	
	
	//Após concluir um produto, faz um update na quantidade e preço do estoque
	public void Inserir_peca_nova(String nome_peca_pedido)
	{
		Connection conexao = null;
		PreparedStatement comando = null;
		ResultSet resultado = null;
		
		try {
			conexao = ClasseConexao.Conectar();
			String sql = "UPDATE cadastro_pecas_estoque AS c\r\n"
					+ "JOIN pedido_de_compra AS p ON p.idPecasFKPedido = c.idPecas \r\n"
					+ "SET c.quantidade_total = p.quantidade_pedido + c.quantidade_total,\r\n"
					+ "    c.preco_total = p.preco_compra_pedido + c.preco_total,\r\n"
					+ "    c.preco_venda = (c.preco_total / c.quantidade_total) * 1.30\r\n"
					+ "WHERE c.nome_pecas = ? AND p.campo_conclusao = 2";
			comando = conexao.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			comando.setString(1,nome_peca_pedido);
			
			if(comando.executeUpdate()>0) {
				JOptionPane.showMessageDialog(null, "A adição da peça foi inserida no banco de dados");				
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

}