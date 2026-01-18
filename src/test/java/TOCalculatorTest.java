import classes.TO_Calculator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TOCalculatorTest {

    private static final double DELTA = 0.0001;

    private TO_Calculator makeWith(double wtw, double activityData) {
        return new TO_Calculator( "T_Intensity", 0, 0, 0, 0, 0, 0) {
            @Override
            public Double getWTW() {
                return wtw;
            }

            @Override
            public Double getActivityData() {
                return activityData;
            }
        };
    }

    @Test
    void getActivityData_TC1_ECP1() {
        double payload = 0.0;
        double totalCapacity = 10000.0;
        double distance = 1000.0;

        String id = "TO1";
        double Ei = 0.0;
        double ttwEF = 0.0;
        double wttEF = 0.0;

        TO_Calculator to = new TO_Calculator(id,payload, totalCapacity, distance, Ei, ttwEF, wttEF);

        double emptyReturnFactor = 0.5;
        double expected = payload * distance * (1 + emptyReturnFactor);

        assertEquals(expected, to.getActivityData(), DELTA);
    }

    @Test
    void getActivityData_TC2_ECP1() {
        double payload = 10000.0;
        double totalCapacity = 10000.0;
        double distance = 1.0;

        String id = "TO2";
        double Ei = 0.0;
        double ttwEF = 0.0;
        double wttEF = 0.0;

        TO_Calculator to = new TO_Calculator(id,payload, totalCapacity, distance, Ei, ttwEF, wttEF);

        double emptyReturnFactor = 0.5;
        double expected = payload * distance * (1 + emptyReturnFactor);

        assertEquals(expected, to.getActivityData(), DELTA);
    }

    @Test
    void getActivityData_TC3_ECP2() {
        double payload = 10000.0;
        double totalCapacity = 10000.0;
        double distance = 0.0;

        String id = "TO3";
        double Ei = 0.0;
        double ttwEF = 0.0;
        double wttEF = 0.0;

        TO_Calculator to = new TO_Calculator(id,
                payload, totalCapacity, distance,
                Ei, ttwEF, wttEF
        ) {
            public Double getAdjustedLoad() {
                return 1.0;   // evitar divisão por zero
            }
            public Double getAdjustedDistance() {
                return 1.0;   // evitar divisão por zero
            }
        };
        
        assertEquals(0.0, to.getActivityData(), DELTA);
    }

    @Test
    void getActivityData_TC4_ECP2() {
        double payload = 1000.0;
        double totalCapacity = 0.0;
        double distance = 1000.0;

        String id = "TO4";
        double Ei = 0.0;
        double ttwEF = 0.0;
        double wttEF = 0.0;

        TO_Calculator to = new TO_Calculator(id,payload, totalCapacity, distance, Ei, ttwEF, wttEF);

        assertEquals(0.0, to.getActivityData(), DELTA);
    }

    @Test
    void getActivityData_TC5_ECP2() {
        double payload = -1.0;
        double totalCapacity = 10000.0;
        double distance = 1000.0;

        String id = "TO5";
        double Ei = 0.0;
        double ttwEF = 0.0;
        double wttEF = 0.0;

        TO_Calculator to = new TO_Calculator(id, payload, totalCapacity, distance, Ei, ttwEF, wttEF);

        double emptyReturnFactor = 0.5;
        double expected = payload * distance * (1 + emptyReturnFactor);

        assertEquals(expected, to.getActivityData(), DELTA);
    }

    @Test
    void getActivityData_TC6_ECP2() {
        double payload = 10001.0;
        double totalCapacity = 10000.0;
        double distance = 1000.0;

        String id = "TO6";
        double Ei = 0.0;
        double ttwEF = 0.0;
        double wttEF = 0.0;

        TO_Calculator to = new TO_Calculator(id, payload, totalCapacity, distance, Ei, ttwEF, wttEF);

        double emptyReturnFactor = 0.5;
        double expected = payload * distance * (1 + emptyReturnFactor);

        assertEquals(expected, to.getActivityData(), DELTA);
    }

    @Test
    void getWTW_TC7_ECP4() {
        double energyConsumption_ECi = -1.0;
        double ttwEmissionFactor_EF = 0.5;
        double wttEmissionFactor_EF = 1.5;

        TO_Calculator to = new TO_Calculator(
                "T07", 0.0, 0.0,
                0.0,
                energyConsumption_ECi, ttwEmissionFactor_EF, wttEmissionFactor_EF
        ) {
            @Override
            public Double getActivityData() {
                return 1.0;
            }
        };

        double expected = (energyConsumption_ECi * ttwEmissionFactor_EF)
                + (energyConsumption_ECi * wttEmissionFactor_EF);

        assertEquals(expected, to.getWTW(), DELTA);
    }

    @Test
    void getWTW_TC8_ECP3() {
        double energyConsumption_ECi = 0.0;
        double wttEmissionFactor_EF = 1.5;
        double ttwEmissionFactor_EF = 0.5;

        TO_Calculator to = new TO_Calculator(
                "T08", 0.0, 0.0,
                0.0,
                energyConsumption_ECi, ttwEmissionFactor_EF, wttEmissionFactor_EF
        );

        double expected = (energyConsumption_ECi * ttwEmissionFactor_EF)
                + (energyConsumption_ECi * wttEmissionFactor_EF);
        //System.out.println("WTW TC8: " + expected);

        assertEquals(expected, to.getWTW(), DELTA);
    }

    @Test
    void getWTW_TC9_ECP3() {
        double energyConsumption_ECi = 10_000.0;
        double ttwEmissionFactor_EF = 0.0;
        double wttEmissionFactor_EF = 1.0;

        TO_Calculator to = new TO_Calculator(
                "TC9", 0.0, 0.0,
                0.0,
                energyConsumption_ECi, ttwEmissionFactor_EF, wttEmissionFactor_EF
        ) {
            @Override
            public Double getActivityData() {
                return 1.0;
            }
        };

        double expected = (energyConsumption_ECi * ttwEmissionFactor_EF)
                + (energyConsumption_ECi * wttEmissionFactor_EF);

        assertEquals(expected, to.getWTW(), DELTA);
    }

    @Test
    void getWTW_TC10_ECP3() {
        double energyConsumption_ECi = 10000.0;
        double ttwEmissionFactor_EF = 10000.0;
        double wttEmissionFactor_EF = 10000.0;

        TO_Calculator to = new TO_Calculator(
                "T10", 0.0, 0.0,
                0.0,
                energyConsumption_ECi, ttwEmissionFactor_EF, wttEmissionFactor_EF
        ) {
            @Override
            public Double getActivityData() {
                return 1.0;
            }
        };

        double expected = (energyConsumption_ECi * ttwEmissionFactor_EF)
                + (energyConsumption_ECi * wttEmissionFactor_EF);

        assertEquals(expected, to.getWTW(), DELTA);
    }

    @Test
    void getWTW_TC11_ECP4() {
        double energyConsumption_ECi = 10000.0;
        double wttEmissionFactor_EF = 1.5;
        double ttwEmissionFactor_EF = -0.1;

        TO_Calculator to = new TO_Calculator(
                "T11", 0.0, 0.0,
                0.0,
                energyConsumption_ECi, ttwEmissionFactor_EF, wttEmissionFactor_EF
        ) {
            @Override
            public Double getActivityData() {
                return 1.0;
            }
        };

        double expected = (energyConsumption_ECi * ttwEmissionFactor_EF)
                + (energyConsumption_ECi * wttEmissionFactor_EF);

        assertEquals(expected, to.getWTW(), DELTA);
    }

    @Test
    void getIntensity_TC12_ECP6() {
        TO_Calculator calc = makeWith(1000.0, -1.0);
        assertEquals(-1000.0, calc.getIntensity());
    }

    @Test
    void getIntensity_TC13_ECP5() {
        TO_Calculator calc = makeWith(0.0, 0.0);
        assertEquals(0.0, calc.getIntensity());
    }

    @Test
    void getIntensity_TC14_ECP5() {
        TO_Calculator calc = makeWith(1000.0, 0.0);
        assertEquals(0.0, calc.getIntensity());
    }

    @Test
    void getIntensity_TC15_ECP5() {
        TO_Calculator calc = makeWith(0.0, 1000.0);
        assertEquals(0.0, calc.getIntensity());
    }

    @Test
    void getIntensity_TC16_ECP5() {
        TO_Calculator calc = makeWith(1000.0, 1000.0);
        assertEquals(1.0, calc.getIntensity());
    }

    @Test
    void getIntensity_TC17_ECP6() {
        TO_Calculator calc = makeWith(-10.0, 1000.0);
        assertEquals(-0.01, calc.getIntensity());
    }
}
