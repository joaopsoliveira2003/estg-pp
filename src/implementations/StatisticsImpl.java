/*
 * Nome: Rui Alexandre Borba Vitorino
 * Número: 8190479
 * Turma: LSIRC12T1
 *
 * Nome: João Pedro Silva Oliveira
 * Número: 8210291
 * Turma: LSIRC11T2
 */

package implementations;

import estg.ipp.pt.tp02_conferencesystem.io.interfaces.Statistics;

/** Class responsible for the statistics */
public class StatisticsImpl implements Statistics {
    /** The description */
    private String description;

    /** The value */
    private double value;

    /**
     * Constructor
     * @param description the description
     * @param value the value
     */
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
     * Sets the description of the statistics
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
