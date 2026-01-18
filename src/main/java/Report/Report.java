package Report;

import classes.HO_Calculator;
import classes.TO_Calculator;
import classes.TransportChain;
import classes.TransportChainElement;
import interfaces.IReport;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Report implements IReport {

    private TransportChain chain;

    // Definimos o separador aqui para ser fácil mudar (ex: "," para sistema US)
    private static final String SEP = ";";
    private static final String NEW_LINE = "\n";

    public Report(TransportChain chain) {
        this.chain = chain;
    }

    @Override
    public void printReportToConsole() {
        System.out.println(generateCsvContent());
    }

    @Override
    public void generateReportFile(String filePath) throws IOException {
        if(filePath == null) {
            throw new NullPointerException("O caminho do ficheiro nao pode ser nulo.");
        }

        if(filePath.isEmpty()) {
            throw new IllegalArgumentException("O caminho do ficheiro nao pode ser vazio.");
        }

        String content = generateCsvContent();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(content);
        }
    }

    private String generateCsvContent() {
        StringBuilder sb = new StringBuilder();

        // 1. CABEÇALHO
        sb.append("ID").append(SEP)
                .append("TIPO").append(SEP)
                .append("CARGA (t)").append(SEP)
                .append("DISTANCIA (km)").append(SEP)
                .append("CONSUMO ENERGIA").append(SEP) // EC para TOs, Consumo Direto para HOs
                .append("TTW (kgCO2e)").append(SEP)
                .append("WTT (kgCO2e)").append(SEP)
                .append("WTW TOTAL (kgCO2e)").append(SEP)
                .append("INTENSIDADE (kgCO2e/t.km)").append(NEW_LINE);

        // 2. LINHAS DE DADOS
        for (TransportChainElement tce : chain.getElements().values()) {

            // Dados comuns
            String id = tce.getId();
            String type = "";
            double wtw = tce.getWTW();

            // Variáveis específicas (inicializadas a 0 ou vazio)
            double payload = 0.0;
            double distance = 0.0;
            double energy = 0.0;
            double ttw = 0.0;
            double wtt = 0.0;
            double intensity = 0.0;

            // Lógica de Extração (Casting seguro)
            if (tce instanceof TO_Calculator) {
                TO_Calculator to = (TO_Calculator) tce;
                type = "TO";
                payload = to.getPayload();
                distance = to.getDistance();
                energy = to.getEnergyConsumption_ECi();
                ttw = to.getTtw();
                wtt = to.getWtt();
                intensity = to.getIntensity();

            } else if (tce instanceof HO_Calculator) {
                HO_Calculator ho = (HO_Calculator) tce;
                type = "HO";
                // HO não tem carga/distância no contexto de transporte
                energy = ho.getDirectEnergyConsumption_Ei();
                ttw = ho.getTtw();
                wtt = ho.getWtt();
                intensity = 0.0; // Não aplicável
            }

            // Construção da linha
            // Usamos String.format padrao (usa vírgula se o teu PC estiver em PT)
            sb.append(id).append(SEP)
                    .append(type).append(SEP)
                    .append(format(payload)).append(SEP)
                    .append(format(distance)).append(SEP)
                    .append(format(energy)).append(SEP)
                    .append(format(ttw)).append(SEP)
                    .append(format(wtt)).append(SEP)
                    .append(format(wtw)).append(SEP)
                    .append(format(intensity)).append(NEW_LINE);
        }

        // 3. TOTAL FINAL (Rodapé)
        sb.append("TOTAL").append(SEP)
                .append("ALL").append(SEP)
                .append(SEP) // Carga vazia
                .append(SEP) // Distancia vazia
                .append(SEP) // Energia vazia
                .append(SEP) // TTW vazio
                .append(SEP) // WTT vazio
                .append(format(chain.calculateTotalEmissions())).append(SEP)
                .append(NEW_LINE);

        // ---------------------------------------------------------
        // 4. SECÇÃO DE ESTATÍSTICAS (NOVO CÓDIGO)
        // ---------------------------------------------------------
        sb.append(NEW_LINE).append(NEW_LINE); // Duas quebras para separar
        sb.append("FILTRO").append(SEP).append("ID").append(SEP).append("VALOR").append(NEW_LINE);

        // A) TOs com Maior Carga
        List<TO_Calculator> maxPayloadTOs = chain.getTOsWithMaxPayload();
        if (!maxPayloadTOs.isEmpty()) {
            for (TO_Calculator to : maxPayloadTOs) {
                sb.append("TO MAIOR CARGA").append(SEP)
                        .append(to.getId()).append(SEP)
                        .append(format(to.getPayload()) + " t") // Adicionamos unidade para clareza
                        .append(NEW_LINE);
            }
        } else {
            sb.append("TO MAIOR CARGA").append(SEP).append("N/A").append(NEW_LINE);
        }

        // B) HOs com Menor Emissão
        List<HO_Calculator> minWtwHOs = chain.getHOsWithMinWTW();
        if (!minWtwHOs.isEmpty()) {
            for (HO_Calculator ho : minWtwHOs) {
                sb.append("HO MENOR WTW").append(SEP)
                        .append(ho.getId()).append(SEP)
                        .append(format(ho.getWTW()) + " kgCO2e")
                        .append(NEW_LINE);
            }
        } else {
            sb.append("HO MENOR WTW").append(SEP).append("N/A").append(NEW_LINE);
        }

        return sb.toString();
    }

    // Metodo auxiliar para formatar números (evita notação científica e controla casas decimais)
    private String format(double value) {
        // Mostra 4 casas decimais e usa Ponto (.) como separador decimal
        return String.format(Locale.US, "%.2f", value);
    }


}