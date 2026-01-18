package classes;

public class HO_Calculator extends TransportChainElement {

    private double directEnergyConsumption_Ei;

    private double ttwEmissionFactor_EF;

    private double wttEmissionFactor_EF;

    private double wtt;

    private double ttw;

    public HO_Calculator(String id, double directEnergyConsumption_Ei, double ttwEmissionFactor_EF, double wttEmissionFactor_EF) {
        super(id);
        this.directEnergyConsumption_Ei = directEnergyConsumption_Ei;
        this.ttwEmissionFactor_EF = ttwEmissionFactor_EF;
        this.wttEmissionFactor_EF = wttEmissionFactor_EF;
    }

    @Override
    public Double getWTW() {
        ttw = directEnergyConsumption_Ei * ttwEmissionFactor_EF;
        wtt = directEnergyConsumption_Ei * wttEmissionFactor_EF;

        return ttw + wtt;
    }

    @Override
    public Double getIntensity() {
        return 0.0;
    }

    @Override
    public Double getActivityData() {
        return 0.0;
    }

    public double getDirectEnergyConsumption_Ei() {
        return directEnergyConsumption_Ei;
    }

    public void setDirectEnergyConsumption_Ei(double directEnergyConsumption_Ei) {
        this.directEnergyConsumption_Ei = directEnergyConsumption_Ei;
    }

    public double getWttEmissionFactor_EF() {
        return wttEmissionFactor_EF;
    }

    public void setWttEmissionFactor_EF(double wttEmissionFactor_EF) {
        this.wttEmissionFactor_EF = wttEmissionFactor_EF;
    }

    public double getTtwEmissionFactor_EF() {
        return ttwEmissionFactor_EF;
    }

    public void setTtwEmissionFactor_EF(double ttwEmissionFactor_EF) {
        this.ttwEmissionFactor_EF = ttwEmissionFactor_EF;
    }

    public double getWtt() {
        return wtt;
    }

    public void setWtt(double wtt) {
        this.wtt = wtt;
    }

    public double getTtw() {
        return ttw;
    }

    public void setTtw(double ttw) {
        this.ttw = ttw;
    }
}
