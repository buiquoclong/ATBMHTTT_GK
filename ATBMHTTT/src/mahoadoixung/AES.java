package mahoadoixung;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;


public class AES {
    private SecretKey key;
    private final int KEY_SIZE = 256;
    private final int DATA_LENGTH = 128;
    private Cipher encryptionCipher;

    public void createKey() throws Exception {
//        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
//        keyGenerator.init(KEY_SIZE);
//        key = keyGenerator.generateKey();
        byte[] keyBytes = new byte[32];
        SecureRandom random = SecureRandom.getInstanceStrong();
        random.nextBytes(keyBytes);

        // Create secret key
        key = new SecretKeySpec(keyBytes, "AES");
    }

    public String encrypt(String text) throws Exception {
        byte[] textInByte = text.getBytes();
        encryptionCipher = Cipher.getInstance("AES");
        encryptionCipher.init(Cipher.ENCRYPT_MODE,key);
        byte[] encyptByte = encryptionCipher.doFinal(textInByte);
        return encode(encyptByte);
    }

    public String decrypt(String encryptedData) throws Exception {
        byte[] dataInBytes = decode(encryptedData);
        Cipher decryptionCipher = Cipher.getInstance("AES");
        decryptionCipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedBytes = decryptionCipher.doFinal(dataInBytes);
        return new String(decryptedBytes);
    }

    private String encode(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    private byte[] decode(String data) {
        return Base64.getDecoder().decode(data);
    }

    public String exportKey(){
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }



    public static String encrypt1(String strToEncrypt, String myKey) {
        String encrypt = "";
        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            byte[] key = myKey.getBytes("UTF-8");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            encrypt += Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return encrypt;
    }
    public static String decrypt1(String strToDecrypt, String myKey) {
        String decrypt = "";
        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            byte[] key = myKey.getBytes("UTF-8");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            decrypt+= new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return decrypt;
    }
    public static void main(String[] args) throws Exception {
//        AES aes = new AES();
//        aes.createKey();
//
//        System.out.println("key: "+ aes.exportKey());
////        String out = aes.encrypt("Có thể khẳng định rằng Tiếng Việt là một thứ tiếng đẹp, một thứ tiếng hay. Nó là một thứ tiếng “đẹp” bởi “rất rành mạch trong lối nói, rất uyển chuyển trong câu kép, rất ngon lành trong những câu tục ngữ”. Không chỉ vậy, tiếng Việt có một hệ thống nguyên âm, phụ âm phong phú và giàu về thanh điệu. Tiếng Việt có những khả năng dồi dào về phần cấu tạo từ ngữ, cũng như hình thức diễn đạt. Từ vựng qua thời gian cũng tăng lên và ngữ pháp trở nên uyển chuyển, chính xác hơn. Ngày nay, chúng ta cần phải giữ gìn sự trong sáng, giàu đẹp của tiếng Việt.");
//        String out = aes.encrypt("Đây là một ví dụ");
//        System.out.println("encrypt:" + out);
//        System.out.println("decrypt:" + aes.decrypt(out));
        String secretKey = "TVD";
        String originalString = "Đây là một ví dụ";


        String encryptedString = encrypt1(originalString, secretKey);
        System.out.println("Encrypt: " + encryptedString);
        String decryptedString = decrypt1(encryptedString, secretKey);
        System.out.println("Decrypt: " + decryptedString);
    }
}
