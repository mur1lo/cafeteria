package cafeteria.vendas.clientes;

public interface IClienteService {
    Cliente buscar(int id);

    void salvar(Cliente cliente);

    void atualizar(Cliente cliente);

}
