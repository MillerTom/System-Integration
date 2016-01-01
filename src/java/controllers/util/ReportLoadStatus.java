/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.util;

/**
 *
 * @author tmiller
 */
public enum ReportLoadStatus {

    LOADED(16),
    NOT_YET(17),
    LOADING(18);
    private int value;

    private ReportLoadStatus(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }

    /**
     * This method takes integer Value and returned its appropriate Download
     * File Result...
     *
     * @param value
     *
     * @return DownloadFiles
     */
    public static ReportLoadStatus valueOf(int value) {
        for (ReportLoadStatus status : ReportLoadStatus.values()) {
            if (status.value == value) {
                return status;
            }
        }
        throw new IllegalArgumentException();
    }
}
