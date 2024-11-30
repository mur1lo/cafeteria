package cafeteria.vendas.produtos;

public enum UnidadeMedida {

    UNIDADE(1), LATA(2), LITRO(3), PACOTE(4), FATIA(5), GARRAFA(6);

    private final int valor;

    UnidadeMedida(int valor) {
        this.valor = valor;
    }

    public int getValor() {
        return valor;
    }

    public static UnidadeMedida from(int valor) {
        for (UnidadeMedida unidade : UnidadeMedida.values()) {
            if (unidade.valor == valor) {
                return unidade;
            }
        }
        return null;
    }
}
