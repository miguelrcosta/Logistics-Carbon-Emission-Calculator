import classes.HO_Calculator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class HOCalculatorTest {

    private static final double DELTA = 0.0001;

    @Test
    void testGetWTW_TC_HO1_ECP1() {
        double Ei = 0.0;
        double wttEF = 1.0;
        double ttwEF = 0.0;

        String id = "HO1";
        HO_Calculator ho = new HO_Calculator(id, Ei, ttwEF, wttEF);
        double expected = (Ei * ttwEF) + (Ei * wttEF);

        assertEquals(expected, ho.getWTW());
    }

    @Test
    void testGetWTW_TC_HO2_ECP1() {
        double Ei = 1000.0;
        double wttEF = 500.0;
        double ttwEF = 500.0;

        String id = "HO2";
        HO_Calculator ho = new HO_Calculator(id, Ei, ttwEF, wttEF);

        double expected = (Ei * ttwEF) + (Ei * wttEF);
        assertEquals(expected, ho.getWTW(), DELTA);
    }

    @Test
    void testGetWTW_TC_HO3_ECP1() {
        double Ei = 10000.0;
        double wttEF = 10000.0;
        double ttwEF = 10000.0;

        String id = "HO3";
        HO_Calculator ho = new HO_Calculator(id, Ei, ttwEF, wttEF);

        double expected = (Ei * ttwEF) + (Ei * wttEF);
        assertEquals(expected, ho.getWTW(), DELTA);
    }

    @Test
    void testGetWTW_TC_HO4_ECP2() {
        double Ei = -1.0;
        double wttEF = 500.0;
        double ttwEF = 500.0;

        String id = "HO4";
        HO_Calculator ho = new HO_Calculator(id, Ei, ttwEF, wttEF);

        double expected = (Ei * ttwEF) + (Ei * wttEF);
        assertEquals(expected, ho.getWTW(), DELTA);
    }

    @Test
    void testGetWTW_TC_HO5_ECP2() {
        double Ei = 1000.0;
        double wttEF = 500.0;
        double ttwEF = -1.0;

        String id = "HO5";
        HO_Calculator ho = new HO_Calculator(id, Ei, ttwEF, wttEF);

        double expected = (Ei * ttwEF) + (Ei * wttEF);
        assertEquals(expected, ho.getWTW(), DELTA);
    }

    @Test
    void testGetWTW_TC_HO6_ECP2() {
        double Ei = 1000.0;
        double wttEF = 0.0;
        double ttwEF = 500.0;

        String id = "HO6";
        HO_Calculator ho = new HO_Calculator(id, Ei, ttwEF, wttEF);

        double expected = (Ei * ttwEF) + (Ei * wttEF);
        assertEquals(expected, ho.getWTW(), DELTA);
    }

    @Test
    void testGetWTW_TC_HO7_ECP2() {
        double Ei = 1000.0;
        double wttEF = 10001.0;
        double ttwEF = 500.0;

        String id = "HO7";
        HO_Calculator ho = new HO_Calculator(id, Ei, ttwEF, wttEF);

        double expected = (Ei * ttwEF) + (Ei * wttEF);
        assertEquals(expected, ho.getWTW(), DELTA);
    }

    @Test
    void testGetWTW_TC_HO8_ECP2() {
        double Ei = 1000.0;
        double wttEF = 500.0;
        double ttwEF = 10001.0;

        String id = "HO8";
        HO_Calculator ho = new HO_Calculator(id, Ei, ttwEF, wttEF);

        double expected = (Ei * ttwEF) + (Ei * wttEF);
        assertEquals(expected, ho.getWTW(), DELTA);
    }

    @Test
    void testGetIntensity() {
        double Ei = 100.0;
        double wttEF = 50.0;
        double ttwEF = 30.0;

        String id = "HO_Intensity";
        HO_Calculator ho = new HO_Calculator(id, Ei, ttwEF, wttEF);

        assertEquals(0.0, ho.getIntensity());
    }

    @Test
    void testGetActivityData() {
        double Ei = 100.0;
        double wttEF = 50.0;
        double ttwEF = 30.0;

        String id = "HO_ActivityData";
        HO_Calculator ho = new HO_Calculator(id, Ei, ttwEF, wttEF);

        assertEquals(0.0, ho.getActivityData());
    }
}
