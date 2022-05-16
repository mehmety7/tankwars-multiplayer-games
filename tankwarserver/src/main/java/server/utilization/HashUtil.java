package server.utilization;

public class HashUtil {

    public static String hashValue(String value) {
        
        String hashedValue = null;

        try {

        MessageDigest md = MessageDigest.getInstance("MD5");

        md.update(passwordToHash.getBytes());

        byte[] bytes = md.digest();

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }

        hashedValue = sb.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } finally {
            return hashedValue;
        }
        
    }

}