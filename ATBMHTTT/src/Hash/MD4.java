package Hash;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

public class MD4 {
    public static String hash(String input){
//        Security.addProvider(new Bounc)
        try {
            Security.addProvider(new BouncyCastleProvider());
            MessageDigest md = MessageDigest.getInstance("MD4");
            byte[] messageDigest = md.digest(input.getBytes(StandardCharsets.UTF_8));
            BigInteger number = new BigInteger(1, messageDigest);

            return number.toString(16);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    public static String hashFile(String file) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("MD4");
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

    public static boolean hashToFile(String inputFile, String outputFile) {
        try (FileReader reader = new FileReader(inputFile);
             BufferedReader bufferedReader = new BufferedReader(reader);
             FileWriter writer = new FileWriter(outputFile)) {

            String mess;
            while ((mess = bufferedReader.readLine()) != null) {
                String encryptedMess = hash(mess);
                writer.write(encryptedMess + "\n");
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static void main(String[] args) throws Exception {
        String hash = hash("Đây là một text");
        System.out.println(hash);
        String hashF = hashFile("D://Laptrinh/ATBMHTTT/Project_ATBMHTTT/test.rar");
        System.out.println("hashF: "+hashF);
        System.out.println(hashToFile("D://test.txt", "D://test2.txt"));
    }
}
