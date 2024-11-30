package cafeteria.vendas.relatorios;

import cafeteria.vendas.clientes.ClientRepository;
import cafeteria.vendas.clientes.Cliente;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class RelatorioClienteCSV implements RelatorioExportavelEmArquivoTexto {

    private ClientRepository repository;

    public RelatorioClienteCSV(ClientRepository repository) {
        this.repository = repository;
    }

    @Override
    public String getNomeRelatorio() {
        return "cliente.csv";
    }

    @Override
    public void exportar(File destino) {
        try (FileWriter fileWriter = new FileWriter(destino);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {

            printWriter.println("id,nome,telefone");

            List<Cliente> clientes = repository.buscarTodos();

            for (Cliente cliente : clientes) {
                printWriter.println(cliente.getId() + "," + cliente.getNome() + "," + cliente.getTelefone());
            }

            JOptionPane.showMessageDialog(null, "Relatório exportado com sucesso!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
