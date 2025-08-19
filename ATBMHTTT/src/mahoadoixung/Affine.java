package mahoadoixung;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Affine {
//    private static final String ALPHABET = "AÀÁẢÃẠĂẰẮẶẲẴẶÂẦẤẨẪẬBCDĐEÈÉẺẼẸÊỀẾỂỄỆFGHIÌÍỈĨỊJKLMNOÒÓỎÕỌÔỒỐỔỖỘƠỜỚỞỠỢPQRSTUÙÚỦŨỤVWXYZaàáảãạăằắặẳẵặâầấẩẫậbcdđeèéẻẽẹêềếểễệfghiìíỉĩịjklmnoòóỏõọôồốổỗộơờớởỡợpqrstuùúủũụvwxyz";
//private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyzÀÁẢÃẠĂÂẰẮẶẶẲẲẴẴÉÈẺẼẸÊẾỀỆỆỂỂỄỄÍÌỈĨỊÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢÚÙỦŨỤ";
    private static final String ALPHABET = "AĂÂBCDĐEÊFGHIJKLMNOÔƠPQRSTUƯVWXYZaăâbcdđeêfghijklmnoôơpqrstuưvwxyz";

    public static int[] createKey() {
        Random rd = new Random();
        int a, b;

        do {
            a = rd.nextInt(ALPHABET.length() - 1) + 1;
            b = rd.nextInt(ALPHABET.length());
        } while (!isPrimeTogether(a, ALPHABET.length()));

        int[] key = {a, b};
        return key;
    }

    private static boolean isPrimeTogether(int a, int b) {
        return gcd(a, b) == 1;
    }

    private static int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    // Kiểm tra nghịch đảo
    public static int checkInverse(int a, int m){
        for (int i = 1; i < m; i++) {
            if ((a * i)% m == 1){
                return i;
            }
        }
        return -1;
    }
    public static String encrypt(String text, int a, int b){
        String encryptText = "";
        for (char ch : text.toCharArray()){
            if (Character.isLetter(ch)){
                char checkchar = Character.isLowerCase(ch)?'a':'A';
                int x = ch - checkchar;
                int encryptChar = (a*x+b)%26+ checkchar;
                encryptText += (char) ( encryptChar);
            }else {
                encryptText += ch;
            }
        }
    return encryptText;
    }

    public static String decrypt(String text, int a, int b){
        String decryptText = "";
        int inverseA = checkInverse(a,26);
        for (char ch : text.toCharArray()){
            if (Character.isLetter(ch)){
                char checkchar = Character.isLowerCase(ch)?'a':'A';
                int x = ch - checkchar;
                int decryptChar = (inverseA * (x - b + 26))%26 + checkchar;
                decryptText += (char) ( decryptChar);
            }else {
                decryptText += ch;
            }
        }
        return decryptText;
    }


    public static String encryptVietnamese(String text, int a, int b) {
        StringBuilder encryptText = new StringBuilder();
        for (char ch : text.toCharArray()) {
            int index = ALPHABET.indexOf(ch);
            if (index != -1) {
                int encryptedIndex = (a * index + b) % ALPHABET.length();
                char encryptedChar = ALPHABET.charAt(encryptedIndex);
                encryptText.append(encryptedChar);
            } else {
                encryptText.append(ch);
            }
        }
        return encryptText.toString();
    }

    public static String decryptVietnamese(String text, int a, int b) {
        StringBuilder decryptText = new StringBuilder();
        int inverseA = checkInverse(a, ALPHABET.length());
        for (char ch : text.toCharArray()) {
            int index = ALPHABET.indexOf(ch);
            if (index != -1) {
                int decryptedIndex = (inverseA * (index - b + ALPHABET.length())) % ALPHABET.length();
                char decryptedChar = ALPHABET.charAt(decryptedIndex);
                decryptText.append(decryptedChar);
            } else {
                decryptText.append(ch);
            }
        }
        return decryptText.toString();
    }


    public static boolean encryptFile(String inputFile, String outputFile, int a, int b) {
        try (FileReader reader = new FileReader(inputFile);
             BufferedReader bufferedReader = new BufferedReader(reader);
             FileWriter writer = new FileWriter(outputFile)) {

            String mess;
            while ((mess = bufferedReader.readLine()) != null) {
                String encryptedMess = encryptVietnamese(mess,a,b);
                writer.write(encryptedMess + "\n");
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean decryptFile(String inputFile, String outputFile, int a, int b) {
        try (FileReader reader = new FileReader(inputFile);
             BufferedReader bufferedReader = new BufferedReader(reader);
             FileWriter writer = new FileWriter(outputFile)) {

            String mess;
            while ((mess = bufferedReader.readLine()) != null) {
                String decryptedMess = decryptVietnamese(mess, a,b);
                writer.write(decryptedMess + "\n");
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static void main(String[] args) {
        int[] key  = createKey();
        int a  = key[0];
        int b = key[1];
//        int a  = 15;
//        int b = 7;
        System.out.println("key: "+ a + " "+ b);
//        String text = "Mỗi đoạn văn thường bắt đầu bằng một câu chủ đề hoặc một ý chính, từ đó phát triển và mở rộng ý kiến, thông tin hoặc quan điểm của tác giả. Các câu trong đoạn văn liên kết với nhau thông qua những từ nối, ví dụ như thêm vào đó, tuy nhiên, do đó, để tạo sự mạch lạc và logic cho nội dung.";
        String text = "Cách học ngôn ngữ miễn phí, vui nhộn và hiệu quả!";
        String encrypt =  encryptVietnamese(text, a,b);
        System.out.println("encrypt: "+encrypt);
        String decrypt =  decryptVietnamese(encrypt, a,b);
        System.out.println("decrypt: "+decrypt);

//        System.out.println(encryptFile("D://Laptrinh/ATBMHTTT/Project_ATBMHTTT/test.txt", "D://Laptrinh/ATBMHTTT/Project_ATBMHTTT/test3.txt", a,b));
//        System.out.println(decryptFile("D://Laptrinh/ATBMHTTT/Project_ATBMHTTT/test3.txt", "D://Laptrinh/ATBMHTTT/Project_ATBMHTTT/test4.txt", a,b));

    }
}
