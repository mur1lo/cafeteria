package cafeteria.vendas;

import cafeteria.vendas.produtos.IProdutoDAO;
import cafeteria.vendas.produtos.UnidadeMedida;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class VendaRepository implements IVendaRepository {

    private static final Logger logger = LogManager.getLogger(VendaRepository.class);    private Connection connection;
    private final IProdutoDAO produtoRepository;

    public VendaRepository(Connection connection, IProdutoDAO produtoRepository) {
        this.connection = connection;
        this.produtoRepository = produtoRepository;
    }

    @Override
    public void registrarVenda(Venda venda) {
        String sqlVenda = "INSERT INTO venda (cliente_id, data_hora, desconto) VALUES (?, ?, ?)";
        String sqlItemVenda = "INSERT INTO item_venda (nome, medida, quantidade, preco, venda_id) VALUES (?, ?, ?, ?, ?)";
        String sqlAtualizarEstoque = "UPDATE produto SET estoque = ? WHERE id = ?";

        try {
            connection.setAutoCommit(false);

            try (PreparedStatement stmtVenda = connection.prepareStatement(sqlVenda, Statement.RETURN_GENERATED_KEYS)) {
                stmtVenda.setInt(1, venda.getClienteId());
                stmtVenda.setTimestamp(2, Timestamp.valueOf(venda.getDataHora()));
                stmtVenda.setDouble(3, venda.getDesconto());
                stmtVenda.executeUpdate();

                try (ResultSet generatedKeys = stmtVenda.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int vendaId = generatedKeys.getInt(1);

                        try (PreparedStatement stmtItemVenda = connection.prepareStatement(sqlItemVenda)) {
                            for (ItemVenda item : venda.getItens()) {
                                stmtItemVenda.setString(1, item.getNome());
                                stmtItemVenda.setInt(2, item.getUnidadeMedida().getValor());
                                stmtItemVenda.setInt(3, item.getQuantidade());
                                stmtItemVenda.setDouble(4, item.getPreco());
                                stmtItemVenda.setInt(5, vendaId);
                                stmtItemVenda.addBatch();

                                try (PreparedStatement stmtAtualizarEstoque = connection.prepareStatement(sqlAtualizarEstoque)) {
                                    stmtAtualizarEstoque.setInt(1, produtoRepository.verificarEstoqueDisponivel(item.getProdutoId()) - item.getQuantidade());
                                    stmtAtualizarEstoque.setInt(2, item.getProdutoId());
                                    stmtAtualizarEstoque.executeUpdate();
                                }

                            }
                            stmtItemVenda.executeBatch();
                        }
                    }
                }
            }

            connection.commit();
            logger.info("Venda registrada com sucesso: {}", venda);
        } catch (SQLException e) {
            try {
                connection.rollback();
                logger.error("Erro ao registrar venda, rollback executado: {}", e.getMessage(), e);
            } catch (SQLException rollbackEx) {
                logger.error("Erro ao executar rollback: {}", rollbackEx.getMessage(), rollbackEx);
            }
            throw new RuntimeException("Erro ao registrar venda: " + e.getMessage(), e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                logger.error("Erro ao redefinir auto-commit: {}", ex.getMessage(), ex);
            }
        }
    }

    @Override
    public List<VendasDiaRelatorio> buscarVendas(LocalDateTime dataHora) {
        List<VendasDiaRelatorio> vendasDiaRelatorio = new ArrayList<>();
        String sql = "SELECT " +
                "iv.nome as nome_produto, iv.medida , sum(iv.quantidade) as quantidade , sum(iv.preco) as preco " +
                "FROM " +
                "venda v " +
                "INNER JOIN item_venda iv ON (v.id = iv.venda_id) " +
                "INNER JOIN cliente c ON (v.cliente_id = c.id) " +
                "where 1=1 " +
                "and DATE(v.data_hora) = ? " +
                "group by iv.nome, iv.medida " +
                "ORDER BY iv.nome";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(dataHora.toLocalDate()));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                VendasDiaRelatorio venda = new VendasDiaRelatorio();
                venda.setNome(rs.getString("nome_produto"));
                venda.setMedida(UnidadeMedida.from(rs.getInt("medida")));
                venda.setQuantidade(rs.getInt("quantidade"));
                venda.setPreco(rs.getDouble("preco"));
                vendasDiaRelatorio.add(venda);
            }
            logger.info("relatorio de vendas do dia buscadas com sucesso");
        } catch (SQLException e) {
            logger.error("Erro ao buscar vendas: {}", e.getMessage(), e);
            throw new RuntimeException("Erro ao buscar vendas: " + e.getMessage(), e);
        }

        return vendasDiaRelatorio;
    }

    private Venda getVendaById(List<Venda> vendas, int id) {
        for (Venda venda : vendas) {
            if (venda.getId() == id) {
                return venda;
            }
        }
        return null;
    }
}