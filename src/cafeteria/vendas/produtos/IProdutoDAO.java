package cafeteria.vendas.produtos;

import java.util.List;

public interface IProdutoDAO {

    Produto buscar(int id);

    void salvar(Produto produto);

    void atualizar(Produto produto);

    List<Produto> buscarTodos();

    int verificarEstoqueDisponivel(int produtoId);
}
