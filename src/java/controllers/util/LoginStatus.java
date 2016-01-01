package controllers.util;

/**
 *
 * @author tmiller
 */
public enum LoginStatus {
    
    SUCCESS(4),
    FAILURE(3);
    
    
    private int value;

    private LoginStatus(int value) {
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
    public static LoginStatus valueOf(int value) {
        for (LoginStatus status : LoginStatus.values()) {
            if (status.value == value) {
                return status;
            }
        }
        throw new IllegalArgumentException();
    }
}
