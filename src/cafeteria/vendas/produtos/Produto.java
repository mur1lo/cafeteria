package cafeteria.vendas.produtos;

public class Produto {
    private int id;
    private String nome;
    private double preco;
    private UnidadeMedida unidadeMedida;
    private int estoque;


    public Produto() {
    }

    public Produto(String nome, double preco, UnidadeMedida unidadeMedida, int estoque) {
        this.nome = nome;
        this.preco = preco;
        this.unidadeMedida = unidadeMedida;
        this.estoque = estoque;
    }

    public Produto(int id, String nome, double preco, UnidadeMedida unidadeMedida, int estoque) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.unidadeMedida = unidadeMedida;
        this.estoque = estoque;
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

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public UnidadeMedida getUnidadeMedida() {
        return unidadeMedida;
    }

    public void setUnidadeMedida(UnidadeMedida unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }

    public int getEstoque() {
        return estoque;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }

    @Override
    public String toString() {
        return this.nome;
    }
}
