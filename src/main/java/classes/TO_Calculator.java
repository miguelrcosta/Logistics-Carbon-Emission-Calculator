package classes;

public class TO_Calculator extends TransportChainElement{

    // carga total transportada
    private double payload;
    // capacidade de carga total do veiculo por tonelada
    private double totalCapacity;
    // distancia total do trajeto em km (ida e volta)
    private double distance;

    // Consumo direto de energia/combustível (Ei)
    private double energyConsumption_ECi;

    private double ttwEmissionFactor_EF;
    private double wttEmissionFactor_EF;

    private double wtt;

    private double ttw;

    public TO_Calculator(String id,
                         double payload,
                         double totalCapacity,
                         double distance,
                         double energyConsumption_ECi,
                         double ttwEmissionFactor_EF,
                         double wttEmissionFactor_EF) {
        super(id);
        this.payload = payload;
        this.totalCapacity = totalCapacity;
        this.distance = distance;
        this.energyConsumption_ECi = energyConsumption_ECi;
        this.ttwEmissionFactor_EF = ttwEmissionFactor_EF;
        this.wttEmissionFactor_EF = wttEmissionFactor_EF;
    }


    // Cálculo do WTW (Well-To-Wheel) do TO
    @Override
    public Double getWTW() {
        double Ei_energy_fuel = getEi_energy_fuel();
        ttw = Ei_energy_fuel * ttwEmissionFactor_EF;
        wtt = Ei_energy_fuel * wttEmissionFactor_EF;

        return ttw + wtt;
    }

    // Cálculo da Intensidade de Emissões do TO
    @Override
    public Double getIntensity() {
        double WTW = getWTW();

        if (getActivityData() == 0.0) {
            return 0.0;
        }

        return WTW/getActivityData();
    }

    // Cálculo dos Dados de Atividade do TO
    @Override
    public Double getActivityData() {
        return getAdjustedLoad() * getAdjustedDistance();
    }

    /**
     * - Calcular o Fator ER (Empty Return)
     * - Calcular a Distância Ajustada
     */
    private Double getAdjustedDistance() {
        if (distance == 0.0) {
            return 0.0;
        }

        double emptyReturnFactor = (distance / 2) / distance;
        return distance * (1 + emptyReturnFactor);
    }

    /**
     * - Calcular o Fator de Carga
     * - Calcular a Massa Ajustada
     */
    private Double getAdjustedLoad() {
        if (totalCapacity == 0.0) {
            return 0.0;
        }
        double loadFactor = payload / totalCapacity;
        return totalCapacity * loadFactor;

    }

    private Double getEi_energy_fuel() {
        double activityData = getActivityData();
        if (activityData == 0.0) {
            return 0.0;
        }

        return this.energyConsumption_ECi * getActivityData();
    }

    public double getPayload() {
        return payload;
    }

    public void setPayload(double payload) {
        this.payload = payload;
    }

    public double getTotalCapacity() {
        return totalCapacity;
    }

    public void setTotalCapacity(double totalCapacity) {
        this.totalCapacity = totalCapacity;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getEnergyConsumption_ECi() {
        return energyConsumption_ECi;
    }

    public void setEnergyConsumption_ECi(double energyConsumption_ECi) {
        this.energyConsumption_ECi = energyConsumption_ECi;
    }

    public double getTtwEmissionFactor_EF() {
        return ttwEmissionFactor_EF;
    }

    public void setTtwEmissionFactor_EF(double ttwEmissionFactor_EF) {
        this.ttwEmissionFactor_EF = ttwEmissionFactor_EF;
    }

    public double getWttEmissionFactor_EF() {
        return wttEmissionFactor_EF;
    }

    public void setWttEmissionFactor_EF(double wttEmissionFactor_EF) {
        this.wttEmissionFactor_EF = wttEmissionFactor_EF;
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
