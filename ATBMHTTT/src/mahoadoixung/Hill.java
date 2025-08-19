package mahoadoixung;


import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Random;

public class Hill {
//    private static final String ALPHABET_ENGLISH = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
private static final String ALPHABET = " AÁÀẢẠÃĂẮẰẲẶẴÂẤẦẨẬẪBCDĐEÉÈẺẼẸÊẾỀỂỆỄFGHIÍÌỈỊĨJKLMNOÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỢỠPQRSTUÚÙỦŨỤƯỨỪỬỰỮVWXYÝỲỶỴỸZaáàảạãăắằẳặẵâấầẩậẫbcdđeéèẻẽẹêếềểệễfghiíìỉịĩjklmnoóòỏõọôốồổỗộơớờởợỡpqrstuúùủũụưứừửựữvwxyýỳỷỵỹz0123456789!%&()-=_+;:'\",?/ ";

    public static int[][] createKey(int n) {
        int[][] keyMatrix;
        Random random = new Random();

        do {
            keyMatrix = new int[n][n];

            // Điền ma trận khóa với các giá trị ngẫu nhiên
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    keyMatrix[i][j] = random.nextInt(ALPHABET.length());
                }
            }

        } while (!hasInverse(keyMatrix, ALPHABET.length()));

        return keyMatrix;
    }

    // Các phương thức kiểm tra ma trận nghịch đảo và định thức
    public static boolean hasInverse(int[][] matrix, int m) {
        int det = matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
        det = (det % m + m) % m;
        int detInverse = modInverse(det, m);
        return det != 0 && detInverse != -1;
    }

    public static int modInverse(int a, int m) {
        for (int i = 1; i < m; i++) {
            if ((a * i) % m == 1) {
                return i;
            }
        }
        return -1;
    }

    public static String printKey(int[][] key) {
        String k = "";
        for (int i = 0; i < key.length; i++) {
            for (int j = 0; j < key[i].length; j++) {
                k += key[i][j];
                k += " ";
            }
            k += "\n";
        }
        return k;
    }



    public static String encrypt(String plaintext, int[][] keyMatrix) {
        int n = keyMatrix.length;
        StringBuilder ciphertext = new StringBuilder();

        // Chia văn bản rõ thành các khối có kích thước n
        String[] plaintextBlocks = plaintext.split("(?<=\\G.{" + n + "})");

        for (String block : plaintextBlocks) {
            // Chuyển đổi ký tự thành số
            int[] vector = textToNumbers(block);

            // Mã hóa từng vectơ số
            int[] resultVector = matrixMultiply(keyMatrix, vector);

            // Chuyển đổi số thành ký tự và ghép vào văn bản mã hóa
            ciphertext.append(numbersToText(resultVector));
        }

        return ciphertext.toString();
    }

    private static int[] textToNumbers(String text) {
        return text.chars().map(c -> ALPHABET.indexOf((char) c)).toArray();
    }

    private static String numbersToText(int[] numbers) {
        return Arrays.stream(numbers).mapToObj(ALPHABET::charAt).map(String::valueOf).collect(StringBuilder::new, StringBuilder::append, StringBuilder::append).toString();
    }
private static int[] matrixMultiply(int[][] matrix, int[] vector) {
    int[] result = new int[matrix.length];
    for (int i = 0; i < matrix.length; i++) {
        for (int j = 0; j < vector.length; j++) {
            result[i] += matrix[i][j] * vector[j];
        }
        result[i] %= ALPHABET.length();
    }

    return result;
}


    public static String decrypt(String ciphertext, int[][] keyMatrix) {
        int n = keyMatrix.length;
        StringBuilder decryptedText = new StringBuilder();

        // Chia văn bản mã hóa thành các khối có kích thước n
        String[] ciphertextBlocks = ciphertext.split("(?<=\\G.{" + n + "})");

        for (String block : ciphertextBlocks) {
            // Chuyển đổi ký tự thành số
            int[] vector = textToNumbers(block);

            // Tính ma trận nghịch đảo modulo kích thước bảng chữ cái
            int[][] inverseMatrix = matrixInverse(keyMatrix);

            // Giải mã từng vectơ số
            int[] resultVector = matrixMultiply(inverseMatrix, vector);

            // Chuyển đổi số thành ký tự và ghép vào văn bản giải mã
            decryptedText.append(numbersToText(resultVector));
        }

        return decryptedText.toString();
    }
    private static int[][] matrixInverse(int[][] matrix) {
        int det = matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];

        // Tính modulo nếu det âm
        det = (det % ALPHABET.length() + ALPHABET.length()) % ALPHABET.length();

        int detInv = findMultiplicativeInverse(det);

        int[][] inverseMatrix = {
                {matrix[1][1], -matrix[0][1]},
                {-matrix[1][0], matrix[0][0]}
        };

        // Nhân ma trận nghịch đảo với detInv modulo kích thước bảng chữ cái
        for (int i = 0; i < inverseMatrix.length; i++) {
            for (int j = 0; j < inverseMatrix[0].length; j++) {
                inverseMatrix[i][j] = (inverseMatrix[i][j] * detInv) % ALPHABET.length();
                // Tính modulo nếu giá trị âm
                inverseMatrix[i][j] = (inverseMatrix[i][j] + ALPHABET.length()) % ALPHABET.length();
            }
        }

        return inverseMatrix;
    }

    private static int findMultiplicativeInverse(int det) {
        for (int i = 1; i < ALPHABET.length(); i++) {
            if ((det * i) % ALPHABET.length() == 1) {
                return i;
            }
        }
        return -1; // Không có nghịch đảo
    }

public static boolean encryptFile(String inputFile, String outputFile, int[][] key) {
    try (FileReader reader = new FileReader(inputFile, StandardCharsets.UTF_8);
         BufferedReader bufferedReader = new BufferedReader(reader);
         FileWriter writer = new FileWriter(outputFile)) {

        String mess;
        while ((mess = bufferedReader.readLine()) != null) {
            String encryptedMess = encrypt(mess,key);
            writer.write(encryptedMess + "\n");
        }
        return true;
    } catch (IOException e) {
        e.printStackTrace();
    }
    return false;
}


    public static boolean decryptFile(String inputFile, String outputFile, int[][] key) {
        try (FileReader reader = new FileReader(inputFile, StandardCharsets.UTF_8);
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
//        int[][] keyMatrix = {{2, 3}, {1, 5}};
        String text = "HELLOWORLD THISISMY";

//        int[][] keyMatrix = createKey(2);
        int[][] keyMatrix = {{97, 127}, {70, 185}};
        System.out.println(printKey(keyMatrix));
        String plaintext = "Đây là mã hóa ký tự của giải thuật Hill";
        String ciphertext = encrypt(plaintext, keyMatrix);

        System.out.println("Plaintext: " + plaintext);
        System.out.println("Ciphertext: " + ciphertext);

        String decryptedText = decrypt(ciphertext, keyMatrix);
        System.out.println("Decrypted Text: " + decryptedText);

//        System.out.println(encryptFile("D://test.txt", "D://test1.txt", keyMatrix));
//        System.out.println(decryptFile("D://test1.txt", "D://test2.txt", keyMatrix));
   }
}
