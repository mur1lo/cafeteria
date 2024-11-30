package cafeteria.vendas;

import java.time.LocalDateTime;
import java.util.List;

public interface IVendaRepository {
    void registrarVenda(Venda venda);

    List<VendasDiaRelatorio> buscarVendas(LocalDateTime dataHora);
}
