package cafeteria.vendas.clientes;

public class ClientService implements IClienteService {

    private ClientRepository repository;

    public ClientService(ClientRepository repository) {
        this.repository = repository;
    }

    @Override
    public Cliente buscar(int id) {
        return repository.buscarCliente(id);
    }

    @Override
    public void salvar(Cliente cliente) {
        this.repository.salvar(cliente);
    }

    @Override
    public void atualizar(Cliente cliente) {
        this.repository.atualizar(cliente);
    }
}
