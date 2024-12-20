package cafeteria.vendas.produtos;

import java.util.List;

public interface IProdutoRepository {

    Produto buscar(int id);

    void salvar(Produto produto);

    void atualizar(Produto produto);

    List<Produto> buscarTodos();

    int verificarEstoqueDisponivel(int produtoId);
}
