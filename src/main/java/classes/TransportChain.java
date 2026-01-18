package classes;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TransportChain {

    /**
     * O Map armazena os elementos.
     * A Chave é o ID, o Valor é o próprio objeto (TO ou HO).
     * Utilizamos 'TransportChainElement' como o tipo do Valor,
     * pois é o "pai" de TO e HO.
     */
    private Map<String, TransportChainElement> elements;

    public TransportChain() {
        this.elements = new LinkedHashMap<>();
    }

    public void addTCE(TransportChainElement tce) {
        if (tce != null) {
            this.elements.put(tce.getId(), tce);
        }
    }

    public Map<String, Double> getToIntensityResults() {
        Map<String, Double> results = new LinkedHashMap<>();
        for (TransportChainElement element : this.elements.values()) {
            double intensity = element.getIntensity();
            if (intensity > 0) {
                results.put(element.getId(), intensity);
            }
        }
        return results;
    }

    public double calculateTotalEmissions() {
        Double totalEmissions = 0.0;
        for (TransportChainElement element : this.elements.values()) {
            totalEmissions += element.getWTW(); // O polimorfismo chama o método certo
        }
        return totalEmissions;
    }

    /**
     * O metodo getTotalWTWResults nao foi testado pois os inputs a serem testados sao os mesmos que o metodo
     * calculateTotalEmissions, o que significaria uma repetiçao de testes. A nao realizaçao dos testes deste metodo ira
     * implicar a queda da coverage.
     *
     * @return
     */
    public Map<String, Double> getTotalWTWResults() {
        Map<String, Double> results = new LinkedHashMap<>();
        for (TransportChainElement element : this.elements.values()) {
            double totalEmissions = element.getWTW();
            if (totalEmissions > 0) {
                results.put(element.getId(), element.getWTW());
            }

        }
        return results;
    }

    /**
     * Retorna uma lista com todos os TOs com a maior carga transportada.
     *
     * @return
     */
    public List<TO_Calculator> getTOsWithMaxPayload() {
        List<TO_Calculator> maxList = new ArrayList<>();
        double maxPayload = -1.0;

        for (TransportChainElement element : this.elements.values()) {
            if (element instanceof TO_Calculator) {
                TO_Calculator to = (TO_Calculator) element;

                if (to.getPayload() > maxPayload) {

                    maxPayload = to.getPayload();
                    maxList.clear();
                    maxList.add(to);
                }
                else if (to.getPayload() == maxPayload) {
                    maxList.add(to);
                }
            }
        }
        return maxList;
    }

    /**
     * Retorna o HO (Hub Operation) com o menor valor de emissões WTW.
     * Retorna null se não existirem HOs na cadeia.
     */
    public List<HO_Calculator> getHOsWithMinWTW() {
        List<HO_Calculator> minList = new ArrayList<>();
        double minWtw = Double.MAX_VALUE;

        for (TransportChainElement element : this.elements.values()) {
            if (element instanceof HO_Calculator) {
                HO_Calculator ho = (HO_Calculator) element;
                double currentWtw = ho.getWTW();

                if (currentWtw < minWtw) {
                    minWtw = currentWtw;
                    minList.clear();
                    minList.add(ho);
                } else if (Double.compare(currentWtw, minWtw) == 0) {
                    // empate exacto
                    minList.add(ho);
                }
            }
        }
        return minList;
    }


    public int size() {
        return elements.size();
    }

    public Map<String, TransportChainElement> getElements() {
        return elements;
    }
}
