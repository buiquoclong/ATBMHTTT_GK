package mahoadoixung;

import java.io.*;
import java.util.*;

public class Vigenere {
    private static final String ALPHABET = "AĂÂBCDĐEÊFGHIJKLMNOÔƠPQRSTUƯVWXYZaăâbcdđeêfghijklmnoôơpqrstuưvwxyz";

    public static String createKey(int length) {
        String key = "";
        Random rd = new Random();
        for (int i = 0; i < length; i++) {
            int randomIndex = rd.nextInt(ALPHABET.length());
            char randomChar = ALPHABET.charAt(randomIndex);
            key += randomChar;
        }

        return key.toString();
    }

    public static String encrypt(String text, final String key) {
        StringBuilder res = new StringBuilder();
        int keyLength = key.length();

        for (int i = 0, j = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            char keyChar = key.charAt(j);

            if (ALPHABET.contains(String.valueOf(c))) {
                int index = ALPHABET.indexOf(c);
                int keyIndex = ALPHABET.indexOf(keyChar);
                int encryptedIndex = (index + keyIndex) % ALPHABET.length();

                char encryptedChar = ALPHABET.charAt(encryptedIndex);

                // Kiểm tra nếu ký tự gốc là chữ hoa hoặc chữ thường
                if (Character.isUpperCase(c)) {
                    encryptedChar = Character.toUpperCase(encryptedChar);
                } else {
                    encryptedChar = Character.toLowerCase(encryptedChar);
                }

                res.append(encryptedChar);
                j = ++j % keyLength;
            } else {
                res.append(c);
            }
        }

        return res.toString();
    }

    public static String decrypt(String text, final String key) {
        StringBuilder res = new StringBuilder();
        int keyLength = key.length();

        for (int i = 0, j = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            char keyChar = key.charAt(j);

            if (ALPHABET.contains(String.valueOf(c))) {
                int index = ALPHABET.indexOf(c);
                int keyIndex = ALPHABET.indexOf(keyChar);
                int decryptedIndex = (index - keyIndex + ALPHABET.length()) % ALPHABET.length();

                char decryptedChar = ALPHABET.charAt(decryptedIndex);

                // Kiểm tra nếu ký tự gốc là chữ hoa hoặc chữ thường
                if (Character.isUpperCase(c)) {
                    decryptedChar = Character.toUpperCase(decryptedChar);
                } else {
                    decryptedChar = Character.toLowerCase(decryptedChar);
                }

                res.append(decryptedChar);
                j = ++j % keyLength;
            } else {
                res.append(c);
            }
        }

        return res.toString();
    }







    public static boolean encryptFile(String inputFile, String outputFile, String key) {
        try (FileReader reader = new FileReader(inputFile);
             BufferedReader bufferedReader = new BufferedReader(reader);
             FileWriter writer = new FileWriter(outputFile)) {

            String mess;
            while ((mess = bufferedReader.readLine()) != null) {
                String encryptedMess = encrypt(mess, key);
                writer.write(encryptedMess + "\n");
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean decryptFile(String inputFile, String outputFile, String key) {
        try (FileReader reader = new FileReader(inputFile);
             BufferedReader bufferedReader = new BufferedReader(reader);
             FileWriter writer = new FileWriter(outputFile)) {

            String mess;
            while ((mess = bufferedReader.readLine()) != null) {
                String decryptedMess = decrypt(mess, key);
                writer.write(decryptedMess + "\n");
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        int lengthKey = 10;
        String key = createKey(lengthKey);
//        String key = "yLYÃEỠJKFS";
        System.out.println(key);
//        String text = "The goal of MD5 development was to create a function/algorithm that quickly and without much computational power creates a unique digest for each unique string (message).";
        String text = "Không chỉ vậy, tiếng Việt có một hệ thống nguyên âm, phụ âm phong phú và giàu về thanh điệu. Tiếng Việt có những khả năng dồi dào về phần cấu tạo từ ngữ, cũng như hình thức diễn đạt. Từ vựng qua thời gian cũng tăng lên và ngữ pháp trở nên uyển chuyển, chính xác hơn. Ngày nay, chúng ta cần phải giữ gìn sự trong sáng, giàu đẹp của tiếng Việt.";
        String encrypt = encrypt(text, key);
        System.out.println("encrypt: "+encrypt);
        String decrypt = decrypt(encrypt, key);
        System.out.println("decrypt: "+decrypt);

//        System.out.println(encryptFile("D://Laptrinh/ATBMHTTT/Project_ATBMHTTT/test.txt", "D://Laptrinh/ATBMHTTT/Project_ATBMHTTT/test7.txt", key));
//        System.out.println(decryptFile("D://Laptrinh/ATBMHTTT/Project_ATBMHTTT/test7.txt", "D://Laptrinh/ATBMHTTT/Project_ATBMHTTT/test8.txt", key));

    }
}
