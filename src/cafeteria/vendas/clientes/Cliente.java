package cafeteria.vendas.clientes;

public class Cliente {
    private int id;
    private String nome;
    private String telefone;


    public Cliente() {
    }

    public Cliente(String telefone, String nome) {
        this.telefone = telefone;
        this.nome = nome;
    }

    public Cliente(int id, String telefone, String nome) {
        this.telefone = telefone;
        this.nome = nome;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", telefone='" + telefone + '\'' +
                '}';
    }
}
