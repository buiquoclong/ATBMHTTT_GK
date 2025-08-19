package mahoadoixung;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.spec.KeySpec;
import java.util.Base64;

public class DES {
//    private static SecretKey key;
    public static SecretKey createKey() throws NoSuchAlgorithmException {
        SecretKey key;
        KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
        keyGenerator.init(56);
        key = keyGenerator.generateKey();
        return key;
    }
    public String encrypt(String text, SecretKey key) throws Exception {
        String result;
        if (key == null) return  result = encode(new byte[]{});
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] planinText = text.getBytes("UTF-8");
        byte[] cipherText = cipher.doFinal(planinText);
         result = encode(cipherText);
        return result;
    }


    public static String encryptToBase64(String text, SecretKey key) throws Exception {
        if (key == null) return  null;
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] planinText = text.getBytes("UTF-8");
        byte[] cipherText = cipher.doFinal(planinText);
        return Base64.getEncoder().encodeToString(cipherText);
    }



    public static String decrypt(String text, SecretKey key) throws Exception {
        if (key == null) return  null;
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] planinText = cipher.doFinal(decode(text));
        String output = new String(planinText,"UtF-8");
        return output;
    }



    public static String decryptToBase64(String text, SecretKey key) throws Exception {
        if (key == null) return  null;
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.DECRYPT_MODE, key);

        byte[] planinText = cipher.doFinal(Base64.getDecoder().decode(text));
        String output = new String(planinText, "UTF-8");
        return output;
    }


    public static boolean encryptFile(String sourceFile, String desFile, SecretKey key) throws Exception {
        if (key == null) throw new FileNotFoundException("key not found");
        File file  = new File(sourceFile);
        if (file.isFile()){
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, key);

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
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, key);

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

    public static String exportKey(SecretKey key){
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }

    private static String encode(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    public static byte[] decode(String data) {
        return Base64.getDecoder().decode(data);
    }

    public static SecretKey createKeyFromString(String keyString) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(keyString);

        // Sử dụng DESKeySpec để tạo ra một đối tượng DES KeySpec từ byte array
        KeySpec keySpec = new DESKeySpec(keyBytes);

        // Sử dụng SecretKeyFactory để tạo ra SecretKey từ DES KeySpec
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(keySpec);
        return secretKey;
    }
    public static void main(String[] args) throws Exception {
//        DES des = new DES();
//        des.createKey();
//        String out = des.encryptToBase64("Khoa công nghệ thông tin");
//        System.out.println("key: "+des.exportKey());
//        System.out.println(out);
//        System.out.println("decryptToBase64: "+des.decryptToBase64(out));

        String text = "FjgvKh+urTI=";
//        SecretKey secretKey = createKey();
        SecretKey key = createKeyFromString(text);
        System.out.println(exportKey(key));
        String enc = encryptToBase64("Khoa công nghệ thông tin", key);
        System.out.println(enc);
        String dec = decryptToBase64(enc, key);
        System.out.println(dec);
//        des.encryptFile("out.rar", "out1.rar");
//        des.decryptFile("out1.rar", "out2.rar");
    }
}