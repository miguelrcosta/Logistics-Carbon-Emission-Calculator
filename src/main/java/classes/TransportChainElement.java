package classes;

import interfaces.ITCECalculator;

public abstract class TransportChainElement  implements ITCECalculator {

    protected String id;

    public TransportChainElement(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public abstract Double getWTW();

    @Override
    public abstract Double getIntensity();

    @Override
    public abstract Double getActivityData();
}
