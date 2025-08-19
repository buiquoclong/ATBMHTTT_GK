package mahoabatdoixung;

import javax.crypto.Cipher;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RSA {
    private KeyPair keypair;
    private PublicKey publicKey;
    private PrivateKey privateKey;

    public static String encrypt(String text, PublicKey publicKey) throws Exception {
        String result;
//        if (publicKey == null) genKey();
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] plaintext = text.getBytes(StandardCharsets.UTF_8);
            byte[] cipherText = cipher.doFinal(plaintext);
            result = encode(cipherText);
            return result;

    }
    public static String decrypt(String text, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        byte[] planinText = cipher.doFinal(decode(text));
        String output = new String(planinText, "UTF-8");
        return output;
    }

    public static boolean encryptFile(String source, String dest, PublicKey publicKey) {
        try {
            File file = new File(source);
            if (!file.exists()) {
                return false;
            }
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

            try (DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
                 DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(dest)))) {

                long length = file.length();
                byte[] input = new byte[256];
                int bytesRead;

                while ((bytesRead = dis.read(input)) != -1) {
                    byte[] encryptedBytes = cipher.doFinal(input, 0, bytesRead);
                    dos.write(encryptedBytes);
                }

                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public static boolean decryptFile(String source, String dest, PrivateKey privateKey) {
        try {
            File file = new File(source);
            if (!file.exists()) {
                return false;
            }

            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            try (DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
                 DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(dest)))) {

                byte[] input = new byte[256];
                int bytesRead;

                while ((bytesRead = dis.read(input)) != -1) {
                    byte[] decryptedBytes = cipher.doFinal(input, 0, bytesRead);
                    dos.write(decryptedBytes);
                }

                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
//    public static boolean encryptFile(String source, String dest, PublicKey publicKey) throws Exception {
//        File file = new File(source);
//        if (file.exists()){
////            if (publicKey == null) genKey();
//
//            Cipher cipher = Cipher.getInstance("RSA");
//            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
//            DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
//            DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(dest)));
//
//            byte[] input = new byte[256];
//             long length = file.length();
//             int byteRead = 0;
//             while ((length > 0)){}
//             dos.close();
//             dis.close();
//             return true;
//        }else {
//            return false;
//        }
//    }

    public void genKey(){
        KeyPairGenerator keyGenerator = null;
        try {
            keyGenerator = KeyPairGenerator.getInstance("RSA");
            keyGenerator.initialize(2048);
            keypair = keyGenerator.generateKeyPair();
            publicKey = keypair.getPublic();
            privateKey = keypair.getPrivate();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
    public static PublicKey createPublicKeyFromString(String publicKeyString) throws Exception {
        // Decode chuỗi Base64 để lấy byte array từ chuỗi
        byte[] keyBytes = Base64.getDecoder().decode(publicKeyString);

        // Sử dụng X509EncodedKeySpec để tạo đối tượng PublicKey từ byte array
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);

        return publicKey;
    }
    public static String exportPublicKey(PublicKey publicKey) {
        byte[] publicKeyBytes = publicKey.getEncoded();
        return Base64.getEncoder().encodeToString(publicKeyBytes);
    }

    public static String exportPrivateKey(PrivateKey privateKey) {
        byte[] privateKeyBytes = privateKey.getEncoded();
        return Base64.getEncoder().encodeToString(privateKeyBytes);
    }
    public static PrivateKey createPrivateKeyFromString(String privateKeyString) throws Exception {
        // Decode chuỗi Base64 để lấy byte array từ chuỗi
        byte[] keyBytes = Base64.getDecoder().decode(privateKeyString);

        // Sử dụng PKCS8EncodedKeySpec để tạo đối tượng PrivateKey từ byte array
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

        return privateKey;
    }
    private static String encode(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    public static byte[] decode(String data) {
        return Base64.getDecoder().decode(data);
    }

    public KeyPair getKeypair() {
        return keypair;
    }

    public void setKeypair(KeyPair keypair) {
        this.keypair = keypair;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    public static void main(String[] args) throws Exception {
        RSA rsa = new RSA();
        rsa.genKey();
        String text = " Đây là một ví dụ";
//        String pubKey = exportPublicKey(rsa.getPublicKey());
//        String PriKey = exportPrivateKey(rsa.getPrivateKey());
//        System.out.println("pubKey: "+pubKey);
//        System.out.println("PriKey: "+PriKey);
        String puk = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA73JhbFhyPSm9/y/uBrJozwLmiVEkH39xJF9stWcRtav6wgWK2emjSa+IsxOh1otr+OYAVQNLHdgvZmhMMIcH1Cn/H5SLoMIlQJFYV3snQaqHl532slmSHGfdTRQG6IwmdYdMEdHDA/Rq9x7bZk0eUPX9Z8F2jy5vS9PxNJsxi5yQKXPhxlTG3RkUo8Wm6harlOjYyIE0MZeS0UIvesQl6LUlt/OeFM5yUs0L4E5053ZOZ9Xw8lp9UKYk1nkrR4LogeSLugtW1+Jal00u39dFBPNEPl8tCx0K/QJBYIYE1liGEvThOKegqKHj2rgTSvCi/nxk0ALoEBx7x6lzeWPXrQIDAQAB";
       String prik = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQDvcmFsWHI9Kb3/L+4GsmjPAuaJUSQff3EkX2y1ZxG1q/rCBYrZ6aNJr4izE6HWi2v45gBVA0sd2C9maEwwhwfUKf8flIugwiVAkVhXeydBqoeXnfayWZIcZ91NFAbojCZ1h0wR0cMD9Gr3HttmTR5Q9f1nwXaPLm9L0/E0mzGLnJApc+HGVMbdGRSjxabqFquU6NjIgTQxl5LRQi96xCXotSW3854UznJSzQvgTnTndk5n1fDyWn1QpiTWeStHguiB5Iu6C1bX4lqXTS7f10UE80Q+Xy0LHQr9AkFghgTWWIYS9OE4p6CooePauBNK8KL+fGTQAugQHHvHqXN5Y9etAgMBAAECggEAPjzKQtmoc+pmJq7EHTyBTpFv77BGBePfujcNcAdGzKRTjNvY7/QxC6WTEAzhy3y4x1ytKGuvOiYnh+RDLXfjmouB8yjutA3SlfpRboeluAVFsiyOY2QxvK1okbDLbOWE15afuJ//owcESJBeMbSI1T1jLPVVxjipGBoSP3y5P5KlFao3oj0h3acP2+zodqEQQ+KS6rejPsU1JLIyh/YlbY4QemwaT6CtCq61EhH8jrncF+X9Vt0ytWEZrD/vQRhjaPZnuqGMbDFqxBcje4DUL60CxU7G/S8hiVASEb+KnMyVg2Qe3MKLRhwckwAcnAV8/Z9besctPP9no+MGP4pbFwKBgQD1cYnHukWpGvA4/c67zL/zSIAZErj6AFMRLSCzM8NQOg0MO7FOPbdO5Z7JUn4ejlLPKUQqpvxoMGbN/EB43Ttb1YkjhgAroboXB1yu2j2ecXgQvoTjC1Dj6ZAocG+tAlOk6JrkDhw8+SflsJRNWVwtBrZ6evtNKUGPxE90BjPDZwKBgQD5vtDAgk4wROCGyJX1E+xQPj1o//IztYhYdwh/WSehyVMTiMyICP/o2SP0Si98ujNJegkHPdOQu5cvwiyuhboBUV7vkgTi2qMe/4x6XJB+3waTHMNA8fokvWoYuuxED5LHofereVjPobCUmLFcZdKSnDUy1ZJ3cJhaFWuvJSbTywKBgHIUdKLFnG0uHSuQJI+7ahhswUVmI13n2rQoEoPYclaOtNPTxuSD4Pil13jhg8c2BZ1OI+XjOXw/yvjA30KOKXHUvWoqdVfSUskzXcvTmkJToSe/NcrFzrIwNBXntLBoXXJPvvZy7IMWl4m12ihdeW9gKF6jXLyPiW6GIxvdah/PAoGAfgKcO3XD07D9BKSynPyXcIlm3NK/Nv3yNIiivfaS+5ukEigk4sJ/tNHSxVClEy6z7+/mHERCujUcfFlhyBDrH/0ZhJFNa9TbFynjjF2l/o2zOIj7r4ooJ19259FwooqlCZscZuWMPP8dJGzCmJc7nwO4B9Ug2Kj0Bgfm1HFPXO8CgYBeIqKSc+gUHZEzl4VRjitbc1kqDirrjxybxaucwyjJxsE3+gZJ/Mz24t5OfVhw3qheqlqSGG0JJ9xsEYKUmP+MWHm1DNJbNE8XdtKaV/dnu3TaNovLLuVgPuRvKI9tRqViVFDS1hjnR42j2kV+P3TGjUojd8gwCcttykKLvQmVVA==";
        System.out.println(encrypt("en: "+text, createPublicKeyFromString(puk)));
        System.out.println(decrypt(encrypt("de: "+text, createPublicKeyFromString(puk)),createPrivateKeyFromString(prik)));
//        System.out.println("publickey: "+ rsa.getPublicKey());
//        System.out.println("privateckey: "+ rsa.getPrivateKey());
//        byte[] encrypt  = rsa.encrypt(text);
//        System.out.println("encrypt: "+Base64.getEncoder().encodeToString(encrypt));
//        String decrypt = rsa.decrypt(encrypt, rsa.getPrivateKey());
//        System.out.println("decrypt: "+decrypt);
    }
}
