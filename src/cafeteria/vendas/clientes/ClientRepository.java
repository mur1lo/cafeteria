package cafeteria.vendas.clientes;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientRepository {

    private static final Logger logger = LogManager.getLogger(ClientRepository.class);
    private Connection conn;

    public ClientRepository(Connection conn) {
        this.conn = conn;
    }

    public void atualizar(Cliente cliente) {
        String sql = "UPDATE cliente SET nome = ?, telefone = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getTelefone());
            stmt.setInt(3, cliente.getId());
            stmt.executeUpdate();
            logger.info("Cliente atualizado com sucesso: {}", cliente);
        } catch (SQLException e) {
            logger.error("Erro ao atualizar cliente: {}", e.getMessage(), e);
            throw new RuntimeException("Erro ao atualizar cliente: " + e.getMessage(), e);
        }
    }

    public void salvar(Cliente cliente) {
        String sql = "INSERT INTO cliente (nome, telefone) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getTelefone());
            stmt.executeUpdate();
            logger.info("Cliente salvo com sucesso: {}", cliente);
        } catch (SQLException e) {
            logger.error("Erro ao salvar cliente: {}", e.getMessage(), e);
            throw new RuntimeException("Erro ao salvar cliente: " + e.getMessage(), e);
        }
    }

    public Cliente buscarCliente(int id) {
        Cliente cliente = null;
        String sql = "SELECT * FROM cliente WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    cliente = new Cliente();
                    cliente.setId(rs.getInt("id"));
                    cliente.setNome(rs.getString("nome"));
                    cliente.setTelefone(rs.getString("telefone"));
                    logger.info("Cliente encontrado: {}", cliente);
                }
            }
        } catch (SQLException e) {
            logger.error("Erro ao buscar cliente: {}", e.getMessage(), e);
            throw new RuntimeException("Erro ao buscar cliente: " + e.getMessage(), e);
        }
        return cliente;
    }

    public List<Cliente> buscarTodos() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM cliente ORDER BY nome";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("id"));
                cliente.setNome(rs.getString("nome"));
                cliente.setTelefone(rs.getString("telefone"));
                clientes.add(cliente);
            }
            logger.info("Todos os clientes buscados com sucesso");
        } catch (SQLException e) {
            logger.error("Erro ao buscar todos clientes: {}", e.getMessage(), e);
            throw new RuntimeException("Erro ao buscar todos clientes: " + e.getMessage(), e);
        }
        return clientes;
    }

    public List<MelhorClienteRelatorio> relatorioMelhorCliente() {
        String sql = "SELECT\n" +
                "\tc.nome as nome_cliente, sum(iv.preco) as total_gasto, count(v.id) as quantidade_compra\n" +
                "FROM\n" +
                "\tvenda v\n" +
                "INNER JOIN item_venda iv ON (v.id = iv.venda_id)\n" +
                "INNER JOIN cliente c ON (v.cliente_id = c.id)\n" +
                "where 1=1\n" +
                "group by c.nome\n" +
                "order by total_gasto desc\n";
        List<MelhorClienteRelatorio> clientes = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                MelhorClienteRelatorio cliente = new MelhorClienteRelatorio();
                cliente.setNome(rs.getString("nome_cliente"));
                cliente.setTotalCompras(rs.getInt("quantidade_compra"));
                cliente.setTotalGasto(rs.getDouble("total_gasto"));
                clientes.add(cliente);
            }
            logger.info("Todos os clientes buscados com sucesso");
        } catch (SQLException e) {
            logger.error("Erro ao buscar todos clientes: {}", e.getMessage(), e);
            throw new RuntimeException("Erro ao buscar todos clientes: " + e.getMessage(), e);
        }
        return clientes;

    }
}