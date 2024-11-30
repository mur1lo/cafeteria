package cafeteria.vendas.produtos;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutoRepository implements IProdutoRepository {

    private static final Logger logger = LogManager.getLogger(ProdutoRepository.class);
    private Connection conn;

    public ProdutoRepository(Connection conn) {
        this.conn = conn;
    }

    public void atualizar(Produto produto) {
        String sql = "UPDATE produto SET nome = ?, preco = ?, medida = ?, estoque = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, produto.getNome());
            stmt.setDouble(2, produto.getPreco());
            stmt.setInt(3, produto.getUnidadeMedida().getValor());
            stmt.setInt(4, produto.getEstoque());
            stmt.setInt(5, produto.getId());
            stmt.executeUpdate();
            logger.info("Produto atualizado com sucesso: {}", produto);
        } catch (SQLException e) {
            logger.error("Erro ao atualizar produto: {}", e.getMessage(), e);
            throw new RuntimeException("Erro ao atualizar produto: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Produto> buscarTodos() {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT * FROM produto ORDER BY nome";
        try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Produto produto = new Produto();
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setUnidadeMedida(UnidadeMedida.from(rs.getInt("medida")));
                produto.setEstoque(rs.getInt("estoque"));
                produto.setPreco(rs.getDouble("preco"));
                produtos.add(produto);
            }
            logger.info("Todos os produtos buscados com sucesso");
        } catch (SQLException e) {
            logger.error("Erro ao buscar produtos: {}", e.getMessage(), e);
            throw new RuntimeException("Erro ao buscar produtos: " + e.getMessage(), e);
        }
        return produtos;
    }

    public void salvar(Produto produto) {
        String sql = "INSERT INTO produto (nome, preco, medida, estoque) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, produto.getNome());
            stmt.setDouble(2, produto.getPreco());
            stmt.setInt(3, produto.getUnidadeMedida().getValor());
            stmt.setInt(4, produto.getEstoque());
            stmt.executeUpdate();
            logger.info("Produto salvo com sucesso: {}", produto);
        } catch (SQLException e) {
            logger.error("Erro ao salvar produto: {}", e.getMessage(), e);
            throw new RuntimeException("Erro ao salvar produto: " + e.getMessage(), e);
        }
    }

    @Override
    public Produto buscar(int id) {
        Produto produto = null;
        String sql = "SELECT * FROM produto WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    produto = new Produto();
                    produto.setId(rs.getInt("id"));
                    produto.setNome(rs.getString("nome"));
                    produto.setUnidadeMedida(UnidadeMedida.from(rs.getInt("medida")));
                    produto.setEstoque(rs.getInt("estoque"));
                    produto.setPreco(rs.getDouble("preco"));
                    logger.info("Produto encontrado: {}", produto);
                }
            }
        } catch (SQLException e) {
            logger.error("Erro ao buscar produto: {}", e.getMessage(), e);
            throw new RuntimeException("Erro ao buscar produto: " + e.getMessage(), e);
        }
        return produto;
    }

    public int verificarEstoqueDisponivel(int produtoId) {
        String sql = "SELECT estoque FROM produto WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, produtoId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("estoque");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar estoque: " + e.getMessage(), e);
        }
        return 0;
    }

}