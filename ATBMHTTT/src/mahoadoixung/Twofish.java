package mahoadoixung;



import org.bouncycastle.jce.provider.BouncyCastleProvider;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Base64;


public class Twofish {
//    private static SecretKey key;
//    private final int KEY_SIZE = 128;
//    private Cipher encryptionCipher;
    private static byte[] iv = new byte[16];
    public static SecretKey createKey() throws Exception {
        SecretKey key;
        int KEY_SIZE = 128;
        Security.addProvider(new BouncyCastleProvider());
        KeyGenerator keyGenerator = KeyGenerator.getInstance("Twofish");
        keyGenerator.init(KEY_SIZE);
        key = keyGenerator.generateKey();
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);
        return key;
    }

    public static SecretKey createKeyFromString(String keyString) {
        Security.addProvider(new BouncyCastleProvider());
        String algorithm = "Twofish";
        byte[] keyData = keyString.getBytes(StandardCharsets.UTF_8);
        return new SecretKeySpec(keyData, algorithm);
    }


    public static String encrypt(String text, SecretKey key) throws Exception {
        byte[] textInByte = text.getBytes("UTF-8");
        Cipher encryptionCipher;
        encryptionCipher = Cipher.getInstance("Twofish/CBC/PKCS5Padding");
        encryptionCipher.init(Cipher.ENCRYPT_MODE,key,new IvParameterSpec(iv));
        byte[] encyptByte = encryptionCipher.doFinal(textInByte);
        String result = encode(encyptByte)+";"+ encode(iv);
        return result;
    }
    public static String decrypt(String encryptedData, SecretKey key) throws Exception {
        String minusData = encryptedData.split(";")[0];
        byte[] dataInBytes = decode(minusData);
        Cipher decryptionCipher = Cipher.getInstance("Twofish/CBC/PKCS5Padding");
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        decryptionCipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
        byte[] decryptedBytes = decryptionCipher.doFinal(dataInBytes);
        return new String(decryptedBytes, "UTF-8");
    }


    private static String encode(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    public static byte[] decode(String data) {
        return Base64.getDecoder().decode(data);
    }

    public static String exportKey( SecretKey key){
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }

    public static boolean encryptFile(String sourceFile, String desFile, SecretKey key) throws Exception {
        if (key == null) throw new FileNotFoundException("key not found");
        File file  = new File(sourceFile);
        if (file.isFile()){
            Cipher cipher = Cipher.getInstance("Twofish/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key,new IvParameterSpec(iv));

            FileInputStream fis = new FileInputStream(file);
            FileOutputStream fos = new FileOutputStream(desFile);
            byte[]  input = new byte[64];
            int byteRead;
            while ((byteRead = fis.read(input))!= -1){
                byte[] output = cipher.update(input, 0,byteRead);
                if (output != null){
                    fos.write(output);
                }
            }
            byte[] output = cipher.doFinal();
            if(output != null){
                fos.write(output);
                fis.close();
                fos.flush();
                fos.close();
                return true;
            }
        }
        return false;
    }

    public static boolean decryptFile(String sourceFile, String desFile, SecretKey key) throws Exception {
        if (key == null) throw new FileNotFoundException("key not found");
        File file  = new File(sourceFile);
        if (file.isFile()){
            Cipher cipher = Cipher.getInstance("Twofish/CBC/PKCS5Padding");
            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);

            FileInputStream fis = new FileInputStream(file);
            FileOutputStream fos = new FileOutputStream(desFile);
            byte[]  input = new byte[64];
            int byteRead;
            while ((byteRead = fis.read(input))!= -1){
                byte[] output = cipher.update(input, 0,byteRead);
                if (output != null){
                    fos.write(output);
                }
            }
            byte[] output = cipher.doFinal();
            if(output != null){
                fos.write(output);
                fis.close();
                fos.flush();
                fos.close();
                return true;
            }
        }

        return false;
    }
    public static void main(String[] args) throws Exception {
        Twofish tw = new Twofish();

        SecretKey key = createKey();
//        String keyStr = "zYcV+/yMWNvtJwZTc59ppg==";
//        SecretKey key = createKeyFromString(keyStr);
        System.out.println("key: "+ exportKey(key));
        String out = tw.encrypt("Đây là mã hóa ký tự của giải thuật Twofish", key);
        System.out.println("encrypt:" + out);
        System.out.println("decrypt:" + tw.decrypt(out, key));


//        System.out.println(encryptFile("D://Laptrinh/ATBMHTTT/Project_ATBMHTTT/test.txt", "D://Laptrinh/ATBMHTTT/Project_ATBMHTTT/test5.txt"));
//        System.out.println(decryptFile("D://Laptrinh/ATBMHTTT/Project_ATBMHTTT/test5.txt", "D://Laptrinh/ATBMHTTT/Project_ATBMHTTT/test6.txt"));

    }
}
