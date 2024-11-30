package cafeteria.vendas;

import cafeteria.vendas.produtos.UnidadeMedida;

public class ItemVenda {

    private int id;
    private String nome;
    private UnidadeMedida unidadeMedida;
    private int quantidade;
    private double preco;
    private int produtoId;

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

    public UnidadeMedida getUnidadeMedida() {
        return unidadeMedida;
    }

    public void setUnidadeMedida(UnidadeMedida unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(int produtoId) {
        this.produtoId = produtoId;
    }

    @Override
    public String toString() {
        return "ItemVenda{" + "id=" + id + ", nome='" + nome + '\'' + ", unidadeMedida=" + unidadeMedida + ", quantidade=" + quantidade + ", preco=" + preco + ", produtoId=" + produtoId + '}';
    }
}
