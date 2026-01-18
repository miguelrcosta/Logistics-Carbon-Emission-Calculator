package classes;

import interfaces.IImporter;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class Data_Importer implements IImporter {

    static class SecondaryData {
        Double ec;
        Double ttwEf;
        Double wttEf;
    }

    @Override
    public TransportChain importPrimaryCSV(Path path) throws IOException {
        TransportChain chain = new TransportChain();

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line = reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] cols = line.split("[,;]");
                if (cols.length < 2) continue;

                String id = cols[0].replace("\uFEFF", "").trim();
                String tipo = cols[1].trim();

                if (tipo.equalsIgnoreCase("TO")) {

                    double totalCapacity = getVal(cols, 4);
                    double payload = getVal(cols, 5);
                    double distance = getVal(cols, 6);

                    double primaryTTW = getVal(cols, 7);

                    TO_Calculator to = new TO_Calculator(
                            id,
                            payload,
                            totalCapacity,
                            distance,
                            0.0,
                            primaryTTW,
                            0.0
                    );
                    chain.addTCE(to);

                } else if (tipo.equalsIgnoreCase("HO")) {

                    double directConsumption = getVal(cols, 10); // Ei_HO

                    HO_Calculator ho = new HO_Calculator(
                            id,
                            directConsumption,
                            0.0,
                            0.0
                    );
                    chain.addTCE(ho);
                }
            }
        }
        return chain;
    }

    @Override
    public void importSecondaryCSV(TransportChain chain, Path path) throws IOException {

        Map<String, SecondaryData> secondaryDataMap = loadSecondaryData(path);

        for (TransportChainElement element : chain.getElements().values()) {
            String id = element.getId();
            SecondaryData sd = secondaryDataMap.get(id);

            if (sd == null) {
                continue;
            }

            if (element instanceof TO_Calculator) {
                TO_Calculator to = (TO_Calculator) element;


                if (sd.ec != null)  {
                    to.setEnergyConsumption_ECi(sd.ec);
                }
                if (sd.wttEf != null) {
                    to.setWttEmissionFactor_EF(sd.wttEf);
                }

            } else if (element instanceof HO_Calculator) {
                HO_Calculator ho = (HO_Calculator) element;

                if (sd.ttwEf != null){
                    ho.setTtwEmissionFactor_EF(sd.ttwEf);
                }
                if (sd.wttEf != null) {
                    ho.setWttEmissionFactor_EF(sd.wttEf);
                }
            }
        }
    }

    private Map<String, SecondaryData> loadSecondaryData(Path path) throws IOException {
        Map<String, SecondaryData> map = new HashMap<>();

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line = reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] cols = line.split(";");
                if (cols.length < 1) continue;

                String id = cols[0].replace("\uFEFF", "").trim();
                SecondaryData sd = new SecondaryData();

                sd.ttwEf = getValNullable(cols, 7);
                sd.wttEf = getValNullable(cols, 8);
                sd.ec    = getValNullable(cols, 9);

                map.put(id, sd);
            }
        }

        return map;
    }

    private double getVal(String[] cols, int index) {
        if (index >= cols.length) return 0.0;
        return parseDoubleSafe(cols[index]);
    }

    private Double getValNullable(String[] cols, int index) {
        if (index >= cols.length) return null;
        return parseDoubleNullable(cols[index]);
    }

    private double parseDoubleSafe(String s) {
        if (s == null || s.isBlank()) return 0.0;
        try {
            return Double.parseDouble(s.replace(",", "."));
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    private Double parseDoubleNullable(String s) {
        if (s == null || s.isBlank()) return null;
        try {
            return Double.valueOf(s.replace(",", "."));
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
