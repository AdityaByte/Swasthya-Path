package in.ayush.swasthyapath.utils;

import java.util.Date;

/**
 * GeneralUtility: This class is mainly used to store common utility methods and prevent code duplication.
 */
public class GeneralUtility {

    public static byte calculateAge(Date dob) {
        Date now = new Date();
        return (byte) (now.getYear() - dob.getYear());
    }
}
