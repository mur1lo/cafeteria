package cafeteria.vendas;

import cafeteria.vendas.clientes.Cliente;

import java.time.LocalDateTime;
import java.util.List;

public class Venda {

    private int id;
    private LocalDateTime dataHora;
    private Cliente cliente;
    private List<ItemVenda> itens;
    private double desconto;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<ItemVenda> getItens() {
        return itens;
    }

    public void setItens(List<ItemVenda> itens) {
        this.itens = itens;
    }

    public double getDesconto() {
        return desconto;
    }

    public void setDesconto(double desconto) {
        this.desconto = desconto;
    }


    public int getClienteId() {
        return cliente.getId();
    }

    public double getValorTotal() {
        double valorTotal = 0;
        for (ItemVenda item : itens) {
            valorTotal += item.getPreco();
        }
        return valorTotal - desconto;
    }


    @Override
    public String toString() {
        return "Venda{" +
                "id=" + id +
                ", dataHora=" + dataHora +
                ", cliente=" + cliente +
                ", itens=" + itens +
                ", desconto=" + desconto +
                '}';
    }
}
