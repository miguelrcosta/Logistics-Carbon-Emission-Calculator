import classes.HO_Calculator;
import classes.TO_Calculator;
import classes.TransportChain;
import Report.Report;
import classes.HO_Calculator;
import classes.TO_Calculator;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReportTest {

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


    /**
     * TC_REP_01 - filePath is null
     */
    @Test
    void TC_REP_01() {
        TransportChain chain = createChainWithElements();
        Report report = new Report(chain);

        assertThrows(NullPointerException.class, () -> {
            report.generateReportFile(null);
        }, "Deve lançar NullPointerException se o caminho for nulo");
    }


    /**
     * TC_REP_02 - String filePath is empty
     */
    @Test
    void TC_REP_02() {
        TransportChain chain = createChainWithElements();
        Report report = new Report(chain);

        assertThrows(IllegalArgumentException.class, () -> {
            report.generateReportFile("");
        }, "Deve lançar IllegalArgumentException se o caminho for vazio");
    }

    /**
     * TC_REP_03 - filePath is valid
     * @throws Exception
     */
    @Test
    void TC_REP_03() throws Exception {
        TransportChain chain = createChainWithElements();
        Report report = new Report(chain);


        report.generateReportFile("Relatorio_Final.csv");

        File file = new File("Relatorio_Final.csv");
        assertTrue(file.exists(), "O ficheiro deve ser criado com sucesso");
        assertTrue(file.length() > 0, "O ficheiro não deve estar vazio");
    }


    /**
    TC_REP_04 - dir does not exist
     */
    @Test
    void TC_REP_04() {
        TransportChain chain = createChainWithElements();
        Report report = new Report(chain);

        assertThrows(IOException.class, () -> {
            report.generateReportFile("non_existent_dir/Relatorio_Final.csv");
        }, "Deve lançar IOException se o diretório não existir");
    }

}