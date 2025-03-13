package projetoA3;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Set;

import javax.swing.JOptionPane;

import com.mysql.cj.protocol.Resultset;

public class Crud_pedido_compra {
	
	//Pesquisa uma peca  que existe no estoque  através do nome
	public ResultSet Selecionar(String nome_pesquisa)
	{
        Connection conexao = null;
        PreparedStatement comando = null;
        ResultSet resultado = null;

        try {
            conexao = ClasseConexao.Conectar();
            String sql = "SELECT idPecas, nome_pecas, quantidade_comprada, capacidade, descricao_pecas from cadastro_pecas_estoque WHERE nome_pecas LIKE ? ORDER BY idPecas  DESC";
            comando = conexao.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            comando.setString(1, "%" + nome_pesquisa + "%");
            resultado = comando.executeQuery();
            return resultado;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;

        } 

    }


   
	
	
	
	//Insere um pedido de compra, e o manda para a tabela listagem
	public void Inserir(double quantidade_pedido, java.sql.Date data_compra , double preco_compra_pedido,String descricao_pedido, String nome_peca_pedido, int idPecas)
	{
		Connection conexao = null;
		PreparedStatement comando = null;
		ResultSet resultado = null;
		
		try {
			conexao = ClasseConexao.Conectar();
			String sql = "INSERT INTO pedido_de_compra(nome_peca_pedido, quantidade_pedido,campo_conclusao,"
					+ "data_compra_pedido, preco_compra_pedido ,idPecasFKPedido, descricao_pedido)\r\n"
                    + "SELECT nome_pecas, ?, 0, ?, ?, idPecas, ? \r\n"
                    + "FROM cadastro_pecas_estoque\r\n"
                    + "WHERE nome_pecas = ? AND idPecas = ?";
			comando = conexao.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			comando.setDouble(1,quantidade_pedido);
			comando.setDate(2, data_compra);
			comando.setDouble(3, preco_compra_pedido);
			comando.setString(4, descricao_pedido);
			comando.setString(5,nome_peca_pedido);
			comando.setInt(6,idPecas);
			
			if(comando.executeUpdate()>0) {
				resultado = comando.getGeneratedKeys(); // Pega o código gerado
				if(resultado.next()) {
					JOptionPane.showMessageDialog(null, "Pedido salvo com sucesso");
				}
				
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