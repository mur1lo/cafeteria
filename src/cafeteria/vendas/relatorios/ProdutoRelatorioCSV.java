package cafeteria.vendas.relatorios;

import cafeteria.vendas.produtos.IProdutoService;
import cafeteria.vendas.produtos.Produto;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ProdutoRelatorioCSV implements RelatorioExportavelEmArquivoTexto {

    private IProdutoService produtoService;

    public ProdutoRelatorioCSV(IProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @Override
    public String getNomeRelatorio() {
        return "produto.csv";
    }

    @Override
    public void exportar(File destino) {
        try (FileWriter fileWriter = new FileWriter(destino); PrintWriter printWriter = new PrintWriter(fileWriter)) {

            // Write CSV header
            printWriter.println("id,nome,medida,estoque,preco");

            List<Produto> clientes = produtoService.buscarTodos();

            for (Produto produto : clientes) {
                printWriter.println(produto.getId() + "," + produto.getNome() + "," + produto.getUnidadeMedida().name() + "," + produto.getEstoque() + "," + produto.getPreco());
            }

            JOptionPane.showMessageDialog(null, "Relatório exportado com sucesso!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
