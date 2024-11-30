package cafeteria.vendas.produtos;

import java.util.List;

public class ProdutoService implements IProdutoService {

    private IProdutoDAO repository;

    public ProdutoService(IProdutoDAO repository) {
        this.repository = repository;
    }

    @Override
    public Produto buscar(int id) {
        return repository.buscar(id);
    }

    @Override
    public void salvar(Produto produto) {
        repository.salvar(produto);
    }

    @Override
    public void atualizar(Produto produto) {
        repository.atualizar(produto);
    }

    @Override
    public List<Produto> buscarTodos() {
        return repository.buscarTodos();
    }

    @Override
    public int verificarEstoqueDisponivel(int produtoId) {
        return repository.verificarEstoqueDisponivel(produtoId);
    }
}
