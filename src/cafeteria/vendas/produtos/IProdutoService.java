package cafeteria.vendas.produtos;

import java.util.List;

public interface IProdutoService {

    Produto buscar(int id);

    void salvar(Produto produto);

    void atualizar(Produto produto);

    List<Produto> buscarTodos();

    int verificarEstoqueDisponivel(int produtoId);
}
