package interfaces;

public interface ITCECalculator {
    /**
     * Calcula o WTW (Well-To-Wheel) total (TTW + WTT).
     * @return
     */
    Double getWTW();

    /**
     * Calcula a Intensidade das Emissões (WTW / Atividade).
     * Deve retornar 0.0 para HOs
     * @return
     */
    Double getIntensity();

    /**
     * Calcula os Dados de Atividade.
     * Deve retornar 0.0 para HOs
     * @return
     */
    Double getActivityData();

}
