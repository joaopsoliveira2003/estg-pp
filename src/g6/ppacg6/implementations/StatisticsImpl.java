package g6.ppacg6.implementations;

import estg.ipp.pt.tp02_conferencesystem.io.interfaces.Statistics;

public class StatisticsImpl implements Statistics {
    // TODO - check the class
    private String description;
    private double value;

    public StatisticsImpl(String description, double value) {
        this.description = description;
        this.value = value;
    }

    /**
     * Gets the description of the statistics
     * @return description
     */
    @Override
    public String getDescription() {
        return this.description;
    }

    /**
     * Sets the value of the statistics
     * @param description - String
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the value
     * @return value
     */
    @Override
    public double getValue() {
        return this.value;
    }

    /**
     * Sets the value
     * @param value - Double
     */
    public void setValue(double value) {
        this.value = value;
    }
}
