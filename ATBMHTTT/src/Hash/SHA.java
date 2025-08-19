package Hash;

import java.io.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA {
    public static String SHA_1 = "SHA-1";
    public static String SHA_224 = "SHA-224";
    public static String SHA_256 = "SHA-256";
    public static String SHA_384 = "SHA-384";
    public static String SHA_512_224 = "SHA-512/224";
    public static String SHA_512_256 = "SHA-1512/256";

    // SHA-256 hash function
//    public String hash(String input){return hash(input,SHA_256);}
//    public static String hash(String input, String algorithms){
//        try {
//            MessageDigest md = MessageDigest.getInstance(algorithms);
//            byte[] messageDigest = md.digest(input.getBytes());
//            BigInteger number = new BigInteger(1, messageDigest);
//            String hashtext = number.toString(16);// hexadecimal
//            return hashtext;
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//        return "";
//    }

    public static String hash(String input, String algorithm) {
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            byte[] messageDigest = md.digest(input.getBytes(StandardCharsets.UTF_8));
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16); // hexadecimal
            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
    public static  String hashFile(String file, String algorithms) throws Exception {
        MessageDigest digest = MessageDigest.getInstance(algorithms);
        InputStream is = new BufferedInputStream(new FileInputStream(file));
        DigestInputStream dis = new DigestInputStream(is,digest);

        byte[] buffer = new byte[1024];
        int read;
        do {
            read = dis.read(buffer);
        }while (read != -1);
        BigInteger number = new BigInteger(1, dis.getMessageDigest().digest()); // binary

        return  number.toString(16);
    }
    public static boolean hashToFile(String inputFile, String outputFile, String algorithms) {
        try (FileReader reader = new FileReader(inputFile);
             BufferedReader bufferedReader = new BufferedReader(reader);
             FileWriter writer = new FileWriter(outputFile)) {

            String mess;
            while ((mess = bufferedReader.readLine()) != null) {
                String encryptedMess = hash(mess, algorithms);
                writer.write(encryptedMess + "\n");
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static void main(String[] args) throws Exception {

//        String kind = getKindHash(SHA_1);
//        System.out.println(kind);
        String hash = hash("á", "SHA-1");
        System.out.println("1: "+hash("á", "SHA-1"));
//        System.out.println("2: "+hash1("á", "SHA-1"));
//        String hashF = hashFile("D://Laptrinh/ATBMHTTT/Project_ATBMHTTT/test.rar", SHA_1);
//        System.out.println("hashF: "+hashF);
//        System.out.println(hashToFile("D://test.txt", "D://test3.txt", SHA_1));
    }
}
