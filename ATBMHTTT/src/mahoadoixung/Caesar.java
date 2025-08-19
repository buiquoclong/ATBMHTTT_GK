package mahoadoixung;

import java.io.*;
import java.util.Random;

public class Caesar {
    private static final String VIETNAMESE_ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyzÀÁẢÃẠĂÂẰẮẶẶẲẲẴẴÉÈẺẼẸÊẾỀỆỆỂỂỄỄÍÌỈĨỊÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢÚÙỦŨỤ";
    private static final String ALPHABET = "AĂÂBCDĐEÊFGHIJKLMNOÔƠPQRSTUƯVWXYZaăâbcdđeêfghijklmnoôơpqrstuưvwxyz";

    public static int createKey(){
    Random rd = new Random();
    int key = rd.nextInt(26);
    return key;
}



    public static String encryptVietnamese(String text, int key) {
        StringBuilder encryptText = new StringBuilder();
        for (char ch : text.toCharArray()) {
            int index = ALPHABET.indexOf(ch);
            if (index != -1) {
                int encryptedIndex = (index + key) % ALPHABET.length();
                char encryptedChar = ALPHABET.charAt(encryptedIndex);
                encryptText.append(encryptedChar);
            } else {
                encryptText.append(ch);
            }
        }
        return encryptText.toString();
    }

    public static String decryptVietnamese(String text, int key) {
        StringBuilder decryptText = new StringBuilder();
        for (char ch : text.toCharArray()) {
            int index = ALPHABET.indexOf(ch);
            if (index != -1) {
                int decryptedIndex = (index - key + ALPHABET.length()) % ALPHABET.length();
                char decryptedChar = ALPHABET.charAt(decryptedIndex);
                decryptText.append(decryptedChar);
            } else {
                decryptText.append(ch);
            }
        }
        return decryptText.toString();
    }
    public static boolean encryptFile(String inputFile, String outputFile, int key) {
        try (FileReader reader = new FileReader(inputFile);
             BufferedReader bufferedReader = new BufferedReader(reader);
             FileWriter writer = new FileWriter(outputFile)) {

            String mess;
            while ((mess = bufferedReader.readLine()) != null) {
                String encryptedMess = encryptVietnamese(mess,key);
                writer.write(encryptedMess + "\n");
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean decryptFile(String inputFile, String outputFile, int key) {
        try (FileReader reader = new FileReader(inputFile);
             BufferedReader bufferedReader = new BufferedReader(reader);
             FileWriter writer = new FileWriter(outputFile)) {

            String mess;
            while ((mess = bufferedReader.readLine()) != null) {
                String decryptedMess = decryptVietnamese(mess, key);
                writer.write(decryptedMess + "\n");
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static void main(String[] args) {
    int key = createKey();
        System.out.println("tao key:" + key);
        String text = "Mỗi đoạn văn thường bắt đầu bằng một câu chủ đề hoặc một ý chính, từ đó phát triển và mở rộng ý kiến, thông tin hoặc quan điểm của tác giả. Các câu trong đoạn văn liên kết với nhau thông qua những từ nối, ví dụ như thêm vào đó, tuy nhiên, do đó, để tạo sự mạch lạc và logic cho nội dung.";
        String encrypt = encryptVietnamese(text,key);
    System.out.println("ma hoa:" + encrypt);
        String decrypt = decryptVietnamese(encrypt,key);
        System.out.println("giai ma:" + decrypt);

//        System.out.println(encryptFile("D://Laptrinh/ATBMHTTT/Project_ATBMHTTT/test.txt", "D://Laptrinh/ATBMHTTT/Project_ATBMHTTT/test1.txt", 24));
//        System.out.println(decryptFile("D://Laptrinh/ATBMHTTT/Project_ATBMHTTT/test1.txt", "D://Laptrinh/ATBMHTTT/Project_ATBMHTTT/test2.txt", 24));
    }
}
