import classes.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

class DataImporterTest {

    private static final double DELTA = 0.01;

    private TransportChain createChainWithElements() {
        TransportChain chain = new TransportChain();

        TO_Calculator to = new TO_Calculator(
                "T1",
                1000.0,
                2000.0,
                50.0,
                10.0,
                1.0,
                2.0
        );

        HO_Calculator ho = new HO_Calculator(
                "H1",
                500.0,
                1.0,
                2.0
        );

        chain.addTCE(to);
        chain.addTCE(ho);
        return chain;
    }

    @Test
    void TC_IMP1_ECP2() throws IOException {
        Data_Importer dataImporter = new Data_Importer();

        Path csv = Path.of("src/main/resources/files/secundarios.csv");

        assertThrows(Exception.class, () -> dataImporter.importSecondaryCSV(null, csv));
    }

    @Test
    void TC_IMP2_ECP2() {
        Data_Importer dataImporter = new Data_Importer();
        TransportChain chain = createChainWithElements();

        assertThrows(Exception.class, () -> dataImporter.importSecondaryCSV(chain, null));
    }

    @Test
    void TC_IMP3_ECP2() {
        Data_Importer dataImporter = new Data_Importer();
        TransportChain chain = createChainWithElements();

        Path missing = Path.of("src/main/resources/files/missed.csv");

        assertThrows(IOException.class, () -> dataImporter.importSecondaryCSV(chain, missing));
    }

    @Test
    void TC_IMP4_ECP1() throws IOException {
        Data_Importer dataImporter = new Data_Importer();
        TransportChain chain = new TransportChain();

        HO_Calculator ho = new HO_Calculator("TCE-01", 500.0, 1.0, 2.0);
        chain.addTCE(ho);

        TO_Calculator to = new TO_Calculator("TCE-02",
                1000.0, 2000.0, 50.0,
                10.0,
                1.0,
                2.0);
        chain.addTCE(to);

        Path csv = Path.of("src/main/resources/files/secundarios.csv");

        dataImporter.importSecondaryCSV(chain, csv);

        System.out.println("HO (TCE-01):");
        System.out.println(" TTW_EF : " + ho.getTtwEmissionFactor_EF());
        System.out.println(" WTT_EF : " + ho.getWttEmissionFactor_EF());

        System.out.println("TO (TCE-02):");
        System.out.println(" ECi : " + to.getEnergyConsumption_ECi());
        System.out.println(" WTT_EF : " + to.getWttEmissionFactor_EF());
        System.out.println(" TTW_EF : " + to.getTtwEmissionFactor_EF());

        assertEquals(0.2, ho.getTtwEmissionFactor_EF(), DELTA);
        assertEquals(0.1, ho.getWttEmissionFactor_EF(), DELTA);

        assertEquals(0.298, to.getEnergyConsumption_ECi(), DELTA);
        assertEquals(0.25, to.getWttEmissionFactor_EF(), DELTA);

        assertEquals(1.0, to.getTtwEmissionFactor_EF(), DELTA);
    }

    @Test
    void TC_IMP5_ECP4() {
        Data_Importer dataImporter = new Data_Importer();

        assertThrows(Exception.class,
                () -> dataImporter.importPrimaryCSV(null));
    }

    @Test
    void TC_IMP6_ECP4() {
        Data_Importer dataImporter = new Data_Importer();

        Path missing = Path.of("src/main/resources/files/primarios_missing.csv");

        assertThrows(IOException.class,
                () -> dataImporter.importPrimaryCSV(missing));
    }

    @Test
    void TC_IMP7_ECP3() throws IOException {
        Data_Importer dataImporter = new Data_Importer();

        Path file = Files.createTempFile("primary_header_only", ".csv");
        String header =
                "id,tipo,c2,c3,vehicle_payload,payload,distance,TTW_EF,c8,c9,Ei_HO\n";
        Files.writeString(file, header);

        TransportChain chain = dataImporter.importPrimaryCSV(file);

        assertEquals(0, chain.size());
    }

    @Test
    void TC_IMP8_ECP3() throws IOException {
        Data_Importer dataImporter = new Data_Importer();

        Path file = Files.createTempFile("primary_to_ho", ".csv");

        String header =
                "id,tipo,c2,c3,vehicle_payload,payload,distance,TTW_EF,c8,c9,Ei_HO\n";
        String toLine = "T1,TO,,,10000,5000,100,3.5,,,\n";
        String hoLine = "H1,HO,,,,,,,,,150\n";

        Files.writeString(file, header + toLine + hoLine);

        TransportChain chain = dataImporter.importPrimaryCSV(file);

        assertEquals(2, chain.size());
        assertTrue(chain.getElements().get("T1") instanceof TO_Calculator);
        assertTrue(chain.getElements().get("H1") instanceof HO_Calculator);
    }
}
