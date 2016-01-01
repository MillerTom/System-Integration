/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author tmiller
 */
public class EncryptPassword {

    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws NoSuchAlgorithmException, Exception {
        this.password = password;
        byte[] defaultBytes = this.password.getBytes();
        MessageDigest algorithm = MessageDigest.getInstance("MD5");
        algorithm.reset();
        algorithm.update(defaultBytes);
        byte messageDigest[] = algorithm.digest();
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < messageDigest.length; i++) {
            hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
        }
        String foo = messageDigest.toString();
        this.password = hexString + "";
    }
}
