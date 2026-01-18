import classes.HO_Calculator;
import classes.TO_Calculator;
import classes.TransportChain;
import classes.TransportChainElement;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


public class TransportChainTest {

    /**
     *
     * Null element added to the Transport Chain
     */
    @Test
    void addTC_TC_TC1_ECP2() {
        TransportChain tc = new TransportChain();

        tc.addTCE(null);

        assertEquals(0, tc.size());
    }

    /**
     *
     * New valid element added to the Transport Chain
     */
    @Test
    void addTC_TC_TC2_ECP1() {
        TransportChain to = new TransportChain();

        TransportChainElement T1 = new TO_Calculator("TO1", 1000, 2000, 500, 0.5, 0.3, 0.2);

        to.addTCE(T1);

        assertEquals(1, to.size());

    }

    /**
     *
     * Other duplicate valid element added to the Transport Chain
     */
    @Test
    void addTC_TC_TC3_ECP1() {
        TransportChain to = new TransportChain();

        TransportChainElement TO1 = new TO_Calculator("TO1", 1000, 2000, 500, 0.5, 0.3, 0.2);

        to.addTCE(TO1);

        TransportChainElement TO2 = new TO_Calculator("TO1", 1500, 2500, 600, 0.6, 0.4, 0.3);

        to.addTCE(TO2);

        assertEquals(1, to.size());

    }

    /**
     *
     * Empty Transport Chain element
     */
    @Test
    void addTC_TC_TC4_ECP1() {
        TransportChain to = new TransportChain();
        TransportChainElement TO1 = new TO_Calculator("", 1000, 2000, 500, 0.5, 0.3, 0.2);

        to.addTCE(TO1);

        assertEquals(1, to.size());

    }

    /**
     *
     * Null ID Transport Chain element
     */
    @Test
    void addTC_TC_TC5_ECP1() {
        TransportChain to = new TransportChain();

        TransportChainElement TO1 = new TO_Calculator(null, 1000, 2000, 500, 0.5, 0.3, 0.2);

        to.addTCE(TO1);

        assertEquals(1, to.size());

    }

    /**
     *
     * A different valid Transport Chain elements added
     */
    @Test
    void addTC_TC_TC6_ECP1() {
        TransportChain to = new TransportChain();

        TransportChainElement HO1 = new HO_Calculator("HO1", 1000, 0.1, 50);

        to.addTCE(HO1);

        assertEquals(1, to.size());

    }

    /**
     *
     * Multiple valid Transport Chain elements added
     */
    @Test
    void addTC_TC_TC7_ECP1() {
        TransportChain to = new TransportChain();

        TransportChainElement TO1 = new TO_Calculator("TO1", 1000, 2000, 500, 0.5, 0.3, 0.2);
        TransportChainElement HO1 = new HO_Calculator("HO1", 1000, 0.1, 50);

        to.addTCE(TO1);
        to.addTCE(HO1);

        assertEquals(2, to.size());

    }

    @Test
    void getTI_TC_TC8_ECP4() {
        TransportChain tc = new TransportChain();

        TransportChainElement TO1 = new TO_Calculator("TO1", 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        tc.addTCE(TO1);

        Map<String, Double> actual = tc.getToIntensityResults();

        System.out.println("TO1 getIntensity(): " + TO1.getIntensity());

        assertEquals(0.0, actual.size(), 0.001);
    }

    @Test
    void getTI_TC_TC9_ECP3() {
        TransportChain tc = new TransportChain();

        TransportChainElement TO1 = new TO_Calculator("TO1", 100.0, 100.0, 100.0, 1.0, 5000.0, 5000.0);
        tc.addTCE(TO1);

        Map<String, Double> actual = tc.getToIntensityResults();

        System.out.println("TO1 getIntensity(): " + TO1.getIntensity());
        System.out.println("TI Results: " + actual);

        assertNotNull(actual);
        assertEquals(1, actual.size());
        assertTrue(actual.containsKey("TO1"));
        assertEquals(10000.0, actual.get("TO1"), 0.001);
    }

    @Test
    void getTI_TC_TC10_ECP4() {
        TransportChain tc = new TransportChain();

        TransportChainElement TO3 = new TO_Calculator("TO3", -1, 0, 0, 0.0, 0.0, 0.0);
        tc.addTCE(TO3);

        Map<String, Double> actual = tc.getToIntensityResults();

        System.out.println("TO3 getIntensity(): " + TO3.getIntensity());
        System.out.println("TI Results: " + actual);

        assertNotNull(actual);
        assertEquals(0, actual.size());
        // Como temos uma condição para não adicionar intensidades negativas ou NaN,
        // garantimos que o mapa não contém a chave "TO3"
        assertFalse(actual.containsKey("TO3"));
    }

    @Test
    void getTI_TC_TC11_ECP3() {
        TransportChain tc = new TransportChain();

        TransportChainElement TO4 = new TO_Calculator("TO4", 100.0, 100.0, 100.0, 1.0, 5000.5, 5000.5);
        tc.addTCE(TO4);

        Map<String, Double> actual = tc.getToIntensityResults();

        System.out.println("TO4 getIntensity(): " + TO4.getIntensity());
        System.out.println("TI Results: " + actual);

        assertNotNull(actual);
        assertEquals(1, actual.size());
        assertTrue(actual.containsKey("TO4"));
        assertEquals(10001.0, actual.get("TO4"), 0.001);
    }

    @Test
    void getTI_TC_TC12_ECP3() {
        TransportChain tc = new TransportChain();

        TransportChainElement TO5 = new TO_Calculator("TO5", 100.0, 100.0, 100.0, 1.0, 250.0, 250.0);
        tc.addTCE(TO5);

        Map<String, Double> actual = tc.getToIntensityResults();

        System.out.println("TO1 getIntensity(): " + TO5.getIntensity());
        System.out.println("TI Results: " + actual);

        assertNotNull(actual);
        assertEquals(1, actual.size());
        assertTrue(actual.containsKey("TO5"));
        assertEquals(500.0, actual.get("TO5"), 0.001);
    }
    @Test
    void calculateTotalEmissions_TC_TC13_ECP6() {
        TransportChain tc = new TransportChain();

        TransportChainElement TO1 = new TO_Calculator("TO1", 1.0, 1.0, 1.0, 1.0, -0.5, -0.5);
        tc.addTCE(TO1);
        double total = tc.calculateTotalEmissions();


        System.out.println("Total Emissions: " + TO1.getWTW());
        System.out.println("Total Emissions: " + total);
        assertEquals(-1.5, total, 0.0001);
    }

    @Test
    void calculateTotalEmissions_TC_TC14_ECP6() {
        TransportChain tc = new TransportChain();

        TransportChainElement TO1 = new TO_Calculator("TO1", 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        tc.addTCE(TO1);

        double total = tc.calculateTotalEmissions();
        System.out.println("Total Emissions: " + total);
        System.out.println("Total Emissions: " + TO1.getWTW());

        assertEquals(0.0, total, 0.0001);
    }

    @Test
    void calculateTotalEmissions_TC_TC15_ECP5() {
        TransportChain tc = new TransportChain();

        TransportChainElement HO1 = new HO_Calculator("HO1", 100.0, 0.5, 0.5);

        tc.addTCE(HO1);

        double total = tc.calculateTotalEmissions();
        System.out.println("Total Emissions: " + HO1.getWTW());
        System.out.println("Total Emissions: " + total);

        assertEquals(100.0, total, 0.0001);
    }

    @Test
    void calculateTotalEmissions_TC_TC16_ECP5() {
        TransportChain tc = new TransportChain();

        TransportChainElement HO1 = new HO_Calculator("HO1", 10000.0, 0.5, 0.5);

        tc.addTCE(HO1);

        double total = tc.calculateTotalEmissions();
        System.out.println("Total Emissions: " + HO1.getWTW());
        System.out.println("Total Emissions: " + total);

        assertEquals(10000.0, total, 0.0001);
    }

    @Test
    void calculateTotalEmissions_TC_TC17_ECP5() {
        TransportChain tc = new TransportChain();

        TransportChainElement HO1 = new HO_Calculator("HO1", 10001.0, 0.5, 0.5);

        tc.addTCE(HO1);

        double total = tc.calculateTotalEmissions();
        System.out.println("Total Emissions: " + HO1.getWTW());
        System.out.println("Total Emissions: " + total);

        assertEquals(10001.0, total, 0.0001);
    }

    @Test
    void getTotalWTWResults_TC_TC18_ECP7() {
        TransportChain tc = new TransportChain();

        Map<String, Double> expected = new LinkedHashMap<>();

        Map<String, Double> results = tc.getTotalWTWResults();

        assertEquals(expected, results);
    }

    @Test
    void getTotalWTWResults_TC_TC19_ECP8() {
        TransportChain tc = new TransportChain();

        TransportChainElement HO1 = new HO_Calculator("HO1", -1.0, 0.5, 0.5);
        tc.addTCE(HO1);

        Map<String, Double> expected = new LinkedHashMap<>();

        Map<String, Double> results = tc.getTotalWTWResults();

        assertEquals(expected, results);
    }

    @Test
    void getTotalWTWResults_TC_TC20_ECP8() {
        TransportChain tc = new TransportChain();

        TransportChainElement HO2 = new HO_Calculator("HO2", 0.0, 0.5, 0.5);
        tc.addTCE(HO2);

        Map<String, Double> expected = new LinkedHashMap<>();

        Map<String, Double> results = tc.getTotalWTWResults();

        assertEquals(expected, results);
    }

    @Test
    void getTotalWTWResults_TC_TC21_ECP7_singlePositiveWTWIncluded() {
        TransportChain tc = new TransportChain();

        TransportChainElement HO3 = new HO_Calculator("HO3", 100.0, 0.5, 0.5);
        tc.addTCE(HO3);

        Map<String, Double> expected = new LinkedHashMap<>();
        expected.put("HO3", HO3.getWTW());

        Map<String, Double> results = tc.getTotalWTWResults();

        assertEquals(expected, results);
    }

    @Test
    void getTotalWTWResults_TC_TC22_ECP7() {
        TransportChain tc = new TransportChain();

        TransportChainElement HO4 = new HO_Calculator("HO4", 10000.0, 0.5, 0.5);
        tc.addTCE(HO4);

        Map<String, Double> expected = new LinkedHashMap<>();
        expected.put("HO4", HO4.getWTW());

        Map<String, Double> results = tc.getTotalWTWResults();

        assertEquals(expected, results);
    }

    @Test
    void getTotalWTWResults_TC_TC23_ECP7() {
        TransportChain tc = new TransportChain();

        TransportChainElement HO5 = new HO_Calculator("HO5", 100.0, 0.5, 0.5);
        TransportChainElement HO6 = new HO_Calculator("HO6", 0.0, 0.5, 0.5);
        TransportChainElement H07  = new HO_Calculator("H07", -1.0, 0.5, 0.5);

        tc.addTCE(HO5);
        tc.addTCE(HO6);
        tc.addTCE(H07);

        Map<String, Double> expected = new LinkedHashMap<>();
        expected.put("HO5", HO5.getWTW());

        Map<String, Double> results = tc.getTotalWTWResults();

        assertEquals(expected, results);
    }

    @Test
    void getTOsWithMaxPayload_TC_TC24_ECP9() {
        TransportChain tc = new TransportChain();

        List<TO_Calculator> result = tc.getTOsWithMaxPayload();

        assertNotNull(result);
        assertTrue(result.isEmpty(), "Quando não há TOs a lista deve ser vazia");
    }

    @Test
    void getTOsWithMaxPayload_TC_TC25_ECP9() {
        TransportChain tc = new TransportChain();

        TO_Calculator TO1 = new TO_Calculator("TO1", 1000.0, 2000.0, 500.0,
                0.5, 0.3, 0.2);
        tc.addTCE(TO1);

        List<TO_Calculator> result = tc.getTOsWithMaxPayload();

        assertNotNull(result);
        assertEquals(1, result.size(), "Com 1 TO deve devolver exatamente 1 elemento");
        assertSame(TO1, result.get(0), "O elemento devolvido deve ser o próprio TO1");
    }

    @Test
    void getTOsWithMaxPayload_TC_TC26_ECP9() {
        TransportChain tc = new TransportChain();

        TO_Calculator TO_LOW  = new TO_Calculator("TO_LOW",  500.0, 2000.0, 500.0,
                0.5, 0.3, 0.2);
        TO_Calculator TO_HIGH = new TO_Calculator("TO_HIGH", 1000.0, 2000.0, 500.0,
                0.5, 0.3, 0.2);

        tc.addTCE(TO_LOW);
        tc.addTCE(TO_HIGH);

        List<TO_Calculator> result = tc.getTOsWithMaxPayload();

        assertNotNull(result);
        assertEquals(1, result.size(), "Com máximo único só deve devolver um TO");
        assertSame(TO_HIGH, result.get(0), "O TO devolvido deve ser o que tem maior payload");
    }

    @Test
    void getTOsWithMaxPayload_TC_TC27_ECP9() {
        TransportChain tc = new TransportChain();

        TO_Calculator TO_MAX1 = new TO_Calculator("TO_MAX1", 1000.0, 2000.0, 500.0,
                0.5, 0.3, 0.2);
        TO_Calculator TO_MAX2 = new TO_Calculator("TO_MAX2", 1000.0, 2000.0, 500.0,
                0.5, 0.3, 0.2);
        TO_Calculator TO_LOW  = new TO_Calculator("TO_LOW",  500.0, 2000.0, 500.0,
                0.5, 0.3, 0.2);

        tc.addTCE(TO_MAX1);
        tc.addTCE(TO_LOW);
        tc.addTCE(TO_MAX2);

        List<TO_Calculator> result = tc.getTOsWithMaxPayload();

        assertNotNull(result);
        assertEquals(2, result.size(), "Devem ser devolvidos todos os TOs com payload máximo");
        assertTrue(result.contains(TO_MAX1), "A lista deve conter o primeiro TO máximo");
        assertTrue(result.contains(TO_MAX2), "A lista deve conter o segundo TO máximo");
    }

    @Test
    void getTOsWithMaxPayload_TC_TC28_ECP10() throws Exception {
        TransportChain tc = new TransportChain();

        Field elementsField = TransportChain.class.getDeclaredField("elements");
        elementsField.setAccessible(true);
        elementsField.set(tc, null);

        assertThrows(NullPointerException.class, () -> {
            tc.getTOsWithMaxPayload();
        });
    }

    /**
     * TC_TC#29 - ECP11
     * TransportChain inicializada mas sem nenhum HO:
     * deve devolver lista vazia.
     */
    @Test
    void getHOsWithMinWTW_TC_TC29_ECP11() {
        TransportChain tc = new TransportChain();

        List<HO_Calculator> result = tc.getHOsWithMinWTW();

        assertNotNull(result, "A lista devolvida não deve ser null");
        assertTrue(result.isEmpty(), "Sem HOs, a lista deve ser vazia");
    }

    /**
     * TC_TC#30 - ECP11
     * TC com 1 HO → esse HO é o mínimo e deve ser o único na lista.
     */
    @Test
    void getHOsWithMinWTW_TC_TC30_ECP11() {
        TransportChain tc = new TransportChain();

        HO_Calculator HO1 = new HO_Calculator("HO1", 100.0, 0.5, 0.5);
        tc.addTCE(HO1);

        List<HO_Calculator> result = tc.getHOsWithMinWTW();

        assertNotNull(result);
        assertEquals(1, result.size(), "Com 1 HO deve devolver exatamente 1 elemento");
        assertSame(HO1, result.get(0), "O elemento devolvido deve ser o próprio HO1");
    }

    /**
     * TC_TC#31 - ECP11
     * TC com ≥2 HOs e apenas um com WTW mínimo → devolve só esse HO.
     */
    @Test
    void getHOsWithMinWTW_TC_TC31_ECP11() {
        TransportChain tc = new TransportChain();

        HO_Calculator HO_LOW  = new HO_Calculator("HO_LOW",  50.0, 0.5, 0.5);
        HO_Calculator HO_HIGH = new HO_Calculator("HO_HIGH", 100.0, 0.5, 0.5);

        tc.addTCE(HO_LOW);
        tc.addTCE(HO_HIGH);

        List<HO_Calculator> result = tc.getHOsWithMinWTW();

        assertNotNull(result);
        assertEquals(1, result.size(), "Com mínimo único só deve devolver um HO");
        assertSame(HO_LOW, result.get(0), "O HO devolvido deve ser o que tem menor WTW");
    }

    /**
     * TC_TC#32 - ECP11
     * TC com ≥3 HOs, sendo pelo menos 2 com o mesmo WTW mínimo.
     * Deve devolver todos os HOs com WTW mínimo (empate).
     */
    @Test
    void getHOsWithMinWTW_TC_TC32_ECP11() {
        TransportChain tc = new TransportChain();

        HO_Calculator HO_MIN1 = new HO_Calculator("HO_MIN1", 50.0, 0.5, 0.5);
        HO_Calculator HO_MIN2 = new HO_Calculator("HO_MIN2", 50.0, 0.5, 0.5);
        HO_Calculator HO_HIGH = new HO_Calculator("HO_HIGH", 100.0, 0.5, 0.5);

        tc.addTCE(HO_MIN1);
        tc.addTCE(HO_HIGH);
        tc.addTCE(HO_MIN2);

        List<HO_Calculator> result = tc.getHOsWithMinWTW();

        assertNotNull(result);
        assertEquals(2, result.size(), "Devem ser devolvidos todos os HOs com WTW mínimo");
        assertTrue(result.contains(HO_MIN1), "A lista deve conter o primeiro HO mínimo");
        assertTrue(result.contains(HO_MIN2), "A lista deve conter o segundo HO mínimo");
    }

    /**
     * TC_TC#33 - ECP12
     * Estado inválido: elements == null → deve lançar NullPointerException.
     */
    @Test
    void getHOsWithMinWTW_TC_TC33_ECP12() throws Exception {
        TransportChain tc = new TransportChain();

        Field elementsField = TransportChain.class.getDeclaredField("elements");
        elementsField.setAccessible(true);
        elementsField.set(tc, null);

        assertThrows(NullPointerException.class, tc::getHOsWithMinWTW);
    }

}

