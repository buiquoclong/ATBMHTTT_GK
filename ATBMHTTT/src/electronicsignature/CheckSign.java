package electronicsignature;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.DigestInputStream;
import java.security.MessageDigest;

public class CheckSign {
    private  String SignCheck = "afb613f878e5f17af689cb387794f63abc55fd4d2317834e6ca79af464db15ae"; //  Bùi Quốc Long

    public String getSignCheck() {
        return SignCheck;
    }

    public static  String hashFile(String file) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
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

    public static boolean checkSumSign(String signString){
        CheckSign cs = new CheckSign();
        if(signString.equals(cs.getSignCheck())){
            return true;
        }else {
            return false;
        }
    }
    public static void main(String[] args) throws Exception {
        String Sign = hashFile("D://Laptrinh/ATBMHTTT/Project_ATBMHTTT/test.txt");
        System.out.println(Sign);
        System.out.println(checkSumSign(Sign));
    }
}
