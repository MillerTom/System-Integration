/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.util;

/**
 *
 * @author tmiller
 */
public enum UploadFiles {
    
    SUCCESS(8),
    FAILURE(9),
    INPROCESS(10);
    
    
    private int value;

    private UploadFiles(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }

    /**
     * This method takes integer Value and returned
     * its appropriate Upload File Result...
     * 
     * @param value
     * 
     * @return UploadFiles 
     */
    public static UploadFiles valueOf(int value) {
        for (UploadFiles status : UploadFiles.values()) {
            if (status.value == value) {
                return status;
            }
        }
        throw new IllegalArgumentException();
    }
}
