package cafeteria.vendas.relatorios;

import cafeteria.vendas.clientes.ClientRepository;
import cafeteria.vendas.clientes.MelhorClienteRelatorio;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class RelatorioMelhorClienteCSV implements RelatorioExportavelEmArquivoTexto {

    private ClientRepository repository;

    public RelatorioMelhorClienteCSV(ClientRepository repository) {
        this.repository = repository;
    }

    @Override
    public String getNomeRelatorio() {
        return "melhor-cliente.csv";
    }

    @Override
    public void exportar(File destino) {
        try (FileWriter fileWriter = new FileWriter(destino); PrintWriter printWriter = new PrintWriter(fileWriter)) {

            printWriter.println("nome cliente,total gasto,quantidade de vendas");

            List<MelhorClienteRelatorio> clientes = repository.relatorioMelhorCliente();

            for (MelhorClienteRelatorio c : clientes) {
                printWriter.println(c.getNome() + "," + c.getTotalGasto() + "," + c.getTotalCompras());
            }

            JOptionPane.showMessageDialog(null, "Relatório exportado com sucesso!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
