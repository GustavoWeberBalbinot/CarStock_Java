package projetoA3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class Crud_ordem_serviço {

    public void Deletar(String cpf) {
        try (Connection conexao = ClasseConexao.Conectar();
             PreparedStatement comando = conexao.prepareStatement("DELETE FROM contatos WHERE cpf=?", Statement.RETURN_GENERATED_KEYS)) {

            comando.setString(1, cpf);
            
            if (comando.executeUpdate() > 0) {
                System.out.println("Dados excluídos");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    

    public ResultSet Selecionar(String nome_pesquisa) {
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
    
    public ResultSet Selecionar2(String nome_pesquisa) {
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
    
    public void Inserir(double valorServ, double qntdComprada, long protocolo, java.sql.Date dataServ, int conclusao_servico, String descricao, String nome_pecas_ordem, int cpf, int idPcsOrdem) {{
    
    }
        String sql = "INSERT INTO ordem_servico (nomeCliente, nomePcsOrdem, valorServ, valorPcs, qntdComprada, protocolo, dataServ, conclusao_servico, descricao_ordem, idPcsFK, idClienteFK) " +
                     " SELECT cli.nome_cliente, pec.nome_pecas, ?, pec.preco_venda, ?, ?, ?, ?, ?, pec.idPecas, cli.idCliente " +
                     " FROM cadastro_cliente cli " +
                     " JOIN cadastro_pecas_estoque pec ON pec.nome_pecas = ?" +
                     " WHERE cli.cpf = ? AND pec.idPecas = ? ";

        try (Connection conexao = ClasseConexao.Conectar();
             PreparedStatement comando = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

           

            comando.setDouble(1, valorServ);
            comando.setDouble(2, qntdComprada);
            comando.setLong(3, protocolo);
            comando.setDate(4, dataServ);
            comando.setInt(5, conclusao_servico);
            comando.setString(6, descricao);
            comando.setString(7, nome_pecas_ordem); 
            comando.setInt(8, cpf);
            comando.setInt(9, idPcsOrdem);
            
            

            if (comando.executeUpdate() > 0) {
                try (ResultSet resultado = comando.getGeneratedKeys()) {
                    if (resultado.next()) {
                    	Baixa_estoque baixaEstoque = new Baixa_estoque();
                    	baixaEstoque.Selecionando_ordem();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

