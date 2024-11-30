package cafeteria.vendas;

import cafeteria.vendas.produtos.IProdutoService;

public class VendaService implements IVendaService {

    private final IVendaRepository vendaRepository;

    private final IProdutoService produtoService;


    public VendaService(IVendaRepository vendaRepository, IProdutoService produtoService) {
        this.vendaRepository = vendaRepository;
        this.produtoService = produtoService;
    }

    @Override
    public void registrarVenda(Venda venda) {
        for (ItemVenda item : venda.getItens()) {
            int estoqueDisponivel = produtoService.verificarEstoqueDisponivel(item.getProdutoId());
            if (estoqueDisponivel < item.getQuantidade()) {
                throw new RuntimeException("Estoque insuficiente para o produto: " + item.getNome());
            }
        }
        this.vendaRepository.registrarVenda(venda);
    }
}
