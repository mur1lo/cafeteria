package cafeteria;

import cafeteria.config.CarregarConfig;
import cafeteria.vendas.IVendaRepository;
import cafeteria.vendas.IVendaService;
import cafeteria.vendas.VendaRepository;
import cafeteria.vendas.VendaService;
import cafeteria.vendas.VendaView;
import cafeteria.vendas.clientes.ClientRepository;
import cafeteria.vendas.clientes.ClientService;
import cafeteria.vendas.clientes.ClienteView;
import cafeteria.vendas.clientes.IClienteService;
import cafeteria.vendas.produtos.IProdutoDAO;
import cafeteria.vendas.produtos.IProdutoService;
import cafeteria.vendas.produtos.ProdutoRepository;
import cafeteria.vendas.produtos.ProdutoService;
import cafeteria.vendas.produtos.ProdutoView;
import cafeteria.vendas.relatorios.ProdutoRelatorioCSV;
import cafeteria.vendas.relatorios.RelatorioClienteCSV;
import cafeteria.vendas.relatorios.RelatorioExportavelEmArquivoTexto;
import cafeteria.vendas.relatorios.RelatorioMelhorClienteCSV;
import cafeteria.vendas.relatorios.RelatorioVendaDiariaCSV;
import cafeteria.vendas.relatorios.RelatorioView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * Tela Principa
 */
public class TelaPrincipal extends JFrame implements ActionListener {

    private static final long serialVersionUID = -6654623057199020421L;

    private static final String TITULO = "Café-IN-a";
    private static final String VERSAO = "1.0.0";

    private static final int SCREEN_SIZE_WIDTH = 1200;
    private static final int SCREEN_SIZE_HEIGHT = 900;

    private JDesktopPane desktop;

    //config
    private CarregarConfig configLoader = null;

    //repository
    private ClientRepository clientRepository = null;
    private IProdutoDAO produtoRepository = null;
    private IVendaRepository vendaRepository = null;

    // Services
    private IClienteService clienteService = null;
    private IProdutoService produtoService = null;
    private IVendaService vendaService = null;

    public TelaPrincipal() {
        super("::: " + TITULO + "  | v" + VERSAO + " :::");

        // Define o tamanho e a posição da tela
        int[] bounds = calcBounds();
        setBounds(bounds[0], bounds[1], bounds[2], bounds[3]);

        // Set up the GUI.
        desktop = new JDesktopPane(); // a specialized layered pane
        setContentPane(desktop);
        setJMenuBar(createMenuBar());

        // Make dragging a little faster but perhaps uglier.
        desktop.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);

        createServices();
    }


    public Connection getConnection() {
        Connection connection = null;
        try {
            String url = this.configLoader.getProperty("db.url");
            String user = this.configLoader.getProperty("db.user");
            String password = this.configLoader.getProperty("db.password");
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    protected int[] calcBounds() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = SCREEN_SIZE_WIDTH > screenSize.width ? screenSize.width : SCREEN_SIZE_WIDTH;
        int height = SCREEN_SIZE_HEIGHT > screenSize.height ? screenSize.height : SCREEN_SIZE_HEIGHT;
        int insetWidth = (screenSize.width - width) / 2;
        int insetHeight = (screenSize.height - height) / 2;
        return new int[]{insetWidth, insetHeight, width, height};
    }

    public void actionPerformed(ActionEvent e) {
        System.exit(0);
    }

    protected void createAndShowFrameCliente() {
        var frame = new ClienteView(this.clienteService);
        desktop.add(frame);
        frame.setupConsultar();
        frame.setVisible(true);
        try {
            frame.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {
            e.printStackTrace();
        }
    }

    protected void createAndShowFrameProduto() {
        var frame = new ProdutoView(this.produtoService);
        desktop.add(frame);
        frame.setupConsultar();
        frame.setVisible(true);
        try {
            frame.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {
            e.printStackTrace();
        }
    }

    protected void createAndShowFrameVendas() {
        var frame = new VendaView(this.vendaService, this.clienteService, this.produtoService);
        desktop.add(frame);
        frame.setupRegistrarNovaVenda();
        frame.setVisible(true);
        try {
            frame.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {
            e.printStackTrace();
        }
    }

    protected void createAndShowFrameExportarRelatorio(RelatorioExportavelEmArquivoTexto exportador) {
        var frame = new RelatorioView(exportador);
        desktop.add(frame);
        frame.setVisible(true);
        try {
            frame.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método para criação dos services que serão usados
     */
    protected void createServices() {
        this.configLoader = new CarregarConfig("config/config.properties");
        Connection connection = getConnection();

        this.produtoRepository = new ProdutoRepository(connection);
        this.produtoService = new ProdutoService(produtoRepository);

        this.clientRepository = new ClientRepository(connection);
        this.clienteService = new ClientService(clientRepository);

        this.vendaRepository = new VendaRepository(connection, produtoRepository);
        this.vendaService = new VendaService(vendaRepository, produtoService);
    }

    protected JMenuBar createMenuBar() {
        JMenuBar menuBar;
        JMenu menu, submenu;
        JMenuItem menuItem;

        // cria a barra de menu.
        menuBar = new JMenuBar();

        // --> CLIENTES
        menu = new JMenu("Clientes");
        menu.setMnemonic(KeyEvent.VK_C);
        menu.getAccessibleContext().setAccessibleDescription("Operações no cadastro de clientes");
        menuBar.add(menu);

        menuItem = new JMenuItem("Cadastro", KeyEvent.VK_C);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.ALT_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription("Cadastro de clientes");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createAndShowFrameCliente();
            }
        });
        menu.add(menuItem);

        // define um submenu de Relatórios
        submenu = new JMenu("Relatórios");
        submenu.setMnemonic(KeyEvent.VK_R);
        menu.add(submenu);

        menuItem = new JMenuItem("Lista de Clientes", KeyEvent.VK_C);
        menuItem.getAccessibleContext().setAccessibleDescription("Relatório de todos os clientes em ordem alfabética");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RelatorioExportavelEmArquivoTexto exportador = new RelatorioClienteCSV(clientRepository);
                createAndShowFrameExportarRelatorio(exportador);
            }
        });
        submenu.add(menuItem);
        // <-- CLIENTES

        // --> PRODUTOS
        menu = new JMenu("Produtos");
        menu.setMnemonic(KeyEvent.VK_P);
        menu.getAccessibleContext().setAccessibleDescription("Operações no cadastro de produtos");
        menuBar.add(menu);

        menuItem = new JMenuItem("Cadastro", KeyEvent.VK_C);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.ALT_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription("Cadastro de produtos");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createAndShowFrameProduto();
            }
        });
        menu.add(menuItem);

        // define um submenu de Relatórios
        submenu = new JMenu("Relatórios");
        submenu.setMnemonic(KeyEvent.VK_R);
        menu.add(submenu);

        menuItem = new JMenuItem("Lista de Produtos", KeyEvent.VK_C);
        menuItem.getAccessibleContext().setAccessibleDescription(
                "Relatório de todos os produtos em ordem alfabética com seu respectivo estoque");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RelatorioExportavelEmArquivoTexto exportador = new ProdutoRelatorioCSV(produtoService);
                createAndShowFrameExportarRelatorio(exportador);
            }
        });
        submenu.add(menuItem);
        // <-- PRODUTOS

        // --> VENDAS
        menu = new JMenu("Vendas");
        menu.setMnemonic(KeyEvent.VK_V);
        menu.getAccessibleContext()
                .setAccessibleDescription("Ações pertinentes as vendas (exemplos: inclusão, cancelamento e consulta)");
        menuBar.add(menu);

        // adiciona os itens no menu
        menuItem = new JMenuItem("Registrar Venda", KeyEvent.VK_R);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.ALT_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription("Inclusão de uma venda");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createAndShowFrameVendas();
            }
        });
        menu.add(menuItem);

        // define um submenu de Relatórios
        submenu = new JMenu("Relatórios");
        submenu.setMnemonic(KeyEvent.VK_R);
        menu.add(submenu);

        menuItem = new JMenuItem("Vendas do Dia", KeyEvent.VK_V);
        menuItem.getAccessibleContext().setAccessibleDescription("Relatório de vendas do dia");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RelatorioExportavelEmArquivoTexto exportador = new RelatorioVendaDiariaCSV(vendaRepository);
                createAndShowFrameExportarRelatorio(exportador);
            }
        });
        submenu.add(menuItem);

        menuItem = new JMenuItem("Melhores Clientes", KeyEvent.VK_M);
        menuItem.getAccessibleContext().setAccessibleDescription("Relação dos melhores clientes do estabelecimento");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RelatorioExportavelEmArquivoTexto exportador = new RelatorioMelhorClienteCSV(clientRepository);
                createAndShowFrameExportarRelatorio(exportador);
            }
        });
        submenu.add(menuItem);
        // <-- VENDAS

        return menuBar;
    }
}