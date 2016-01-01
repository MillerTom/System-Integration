/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.util;

/**
 *
 * @author tmiller
 */
public enum FileTypes {
    
    Add(5),
    Revise(6),
    End(7);
    
    private int value;

    private FileTypes(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }

    /**
     * This method takes integer Value and returned
     * its appropriate Login Status...
     * 
     * @param value
     * 
     * @return LoginStatus 
     */
    public static FileTypes valueOf(int value) {
        for (FileTypes status : FileTypes.values()) {
            if (status.value == value) {
                return status;
            }
        }
        throw new IllegalArgumentException();
    }
}
