package cafeteria.vendas.relatorios;

import cafeteria.vendas.IVendaRepository;
import cafeteria.vendas.VendasDiaRelatorio;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.List;

public class RelatorioVendaDiariaCSV implements RelatorioExportavelEmArquivoTexto {

    private IVendaRepository repository;

    public RelatorioVendaDiariaCSV(IVendaRepository repository) {
        this.repository = repository;
    }

    @Override
    public String getNomeRelatorio() {
        return "vendas-diaria.csv";
    }

    @Override
    public void exportar(File destino) {
        try (FileWriter fileWriter = new FileWriter(destino);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {


            printWriter.println("nome,medida,quantidade,preco");

            List<VendasDiaRelatorio> vendas = repository.buscarVendas(LocalDateTime.now());

            for (VendasDiaRelatorio venda : vendas) {
                printWriter.println(venda.getNome() + "," + venda.getMedida() + "," + venda.getQuantidade() + "," + venda.getPreco());
            }

            printWriter.println("total quantidades: " + vendas.stream().mapToInt(VendasDiaRelatorio::getQuantidade).sum());
            printWriter.println("total preços: " + vendas.stream().mapToDouble(VendasDiaRelatorio::getPreco).sum());

            JOptionPane.showMessageDialog(null, "Relatório exportado com sucesso!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
