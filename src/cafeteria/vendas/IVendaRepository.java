package cafeteria.vendas;

import java.time.LocalDateTime;
import java.util.List;

public interface IVendaRepository {
    void registrarVenda(Venda venda);

    List<VendasDiaRelatorioDTO> buscarVendas(LocalDateTime dataHora);
}
