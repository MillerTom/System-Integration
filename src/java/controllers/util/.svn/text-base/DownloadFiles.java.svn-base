/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.util;

/**
 *
 * @author tmiller
 */
public enum DownloadFiles {

    SUCCESS(11),
    FAILURE(12),
    INPROCESS(13);
    private int value;

    private DownloadFiles(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }

    /**
     * This method takes integer Value and returned its appropriate Download File
     * Result...
     *
     * @param value
     *
     * @return DownloadFiles
     */
    public static DownloadFiles valueOf(int value) {
        for (DownloadFiles status : DownloadFiles.values()) {
            if (status.value == value) {
                return status;
            }
        }
        throw new IllegalArgumentException();
    }
}
