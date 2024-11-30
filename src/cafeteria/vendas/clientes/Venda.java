package cafeteria.vendas.clientes;

import java.util.List;

public class Venda {

    private int id;
    private Cliente cliente;
    private List<ItemVenda> itemVenda;
    private double valorTotal;
    private double desconto;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<ItemVenda> getItemVenda() {
        return itemVenda;
    }

    public void setItemVenda(List<ItemVenda> itemVenda) {
        this.itemVenda = itemVenda;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public double getDesconto() {
        return desconto;
    }

    public void setDesconto(double desconto) {
        this.desconto = desconto;
    }

    @Override
    public String toString() {
        return "Venda{" +
                "id=" + id +
                ", cliente=" + cliente +
                ", itemVenda=" + itemVenda +
                ", valorTotal=" + valorTotal +
                ", desconto=" + desconto +
                '}';
    }
}
