/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.util;

/**
 * @author tmiller
 */
public enum ReportTypes {

    INVENTORY(14),
    SOLID(15);
    
    private int value;

    private ReportTypes(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }

    /**
     * This method takes integer Value and returned its appropriate Report Type...
     *
     * @param value
     *
     * @return ReportTypes
     */
    public static ReportTypes valueOf(int value) {
        for (ReportTypes status : ReportTypes.values()) {
            if (status.value == value) {
                return status;
            }
        }
        throw new IllegalArgumentException();
    }
}
