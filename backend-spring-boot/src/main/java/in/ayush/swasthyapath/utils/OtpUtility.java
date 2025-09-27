package in.ayush.swasthyapath.utils;

import in.ayush.swasthyapath.dto.Otp;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

public class OtpUtility {

    private static final SecureRandom secureRandom = new SecureRandom();

    private static final Map<String, String> tempStorage = new HashMap<>();

    public static String generateOTP(String email) {
        int otp = 100000 + secureRandom.nextInt(900000);
        tempStorage.put(email, String.valueOf(otp));
        return String.valueOf(otp);
    }

    public static boolean isValid(Otp otp) {
        String storedOTP = tempStorage.get(otp.getEmail());
        return storedOTP.equals(otp.getOtp());
    }

}
