import java.util.*;
import java.lang.ClassNotFoundException;
public class Main{
    public static void main(String[] args){
        System.out.println("=== 矩阵密码测试 ===");
        String key1 = "342165";
        String plaintext1 = "betterlatethannever";
        String ciphertext1 = encrypt(plaintext1, key1);
        System.out.println("加密: " + ciphertext1);
        System.out.println("解密: " + decrypt(ciphertext1, key1));
        String key2 = "53412";
        String plaintext2 = "aboldattemptishalfsuccess";
        String ciphertext2 = encrypt(plaintext2, key2);
        System.out.println("加密: " + ciphertext2);
        System.out.println("解密: " + decrypt(ciphertext2, key2));
        String key3 = "34512";
        String plaintext3 = "TeacherIwanttoparticipateinCTF.";
        String ciphertext3 = encrypt(plaintext3, key3);
        System.out.println("加密: " + ciphertext3);
        System.out.println("解密: " + decrypt(ciphertext3, key3));
    }

    public static String encrypt(String plaintext,String key){
        int m = key.length(); // 分组宽度m
        int n = (int) Math.ceil((double)plaintext.length() / m); // 行数n

        // 创建n*m矩阵并按行填充明文
        char[][] mtext = new char[n][m];
        int index = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (index < plaintext.length()) {
                    mtext[i][j] = plaintext.charAt(index++);
                } else {
                    mtext[i][j] = ' '; // 用空格填充
                }
            }
        }

        // 按照置换σ（密钥顺序）交换列的位置
        char[][] permutedMatrix = new char[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                // 密钥中的数字表示目标列位置（从1开始，需要减1）
                int targetCol = Character.getNumericValue(key.charAt(j)) - 1;//-1后得到的是实际插入的列数 而非key中的顺序
                permutedMatrix[i][targetCol] = mtext[i][j];
            }
        }

        // 按列顺序依次读出密文
        StringBuilder ctext = new StringBuilder();
        for (int j = 0; j < m; j++) {
            for (int i = 0; i < n; i++) {
                ctext.append(permutedMatrix[i][j]);
            }
        }

        return ctext.toString();
    }

    public static String decrypt(String ciphertext,String key){
        int m = key.length(); // 分组宽度m
        int n = (int) Math.ceil((double)ciphertext.length() / m); // 行数n

        // 密文按列写入n*m矩阵
        char[][] permutedMatrix = new char[n][m];
        int index = 0;
        for (int j = 0; j < m; j++) {
            for (int i = 0; i < n; i++) {
                if (index < ciphertext.length()) {
                    permutedMatrix[i][j] = ciphertext.charAt(index++);
                } else {
                    permutedMatrix[i][j] = ' ';
                }
            }
        }

        // 按照逆置换σ^{-1}交换列的位置
        char[][] matrix = new char[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                // 找到密钥中数字j+1对应的位置（逆置换）
                int originalCol = key.indexOf(String.valueOf(j + 1));
                matrix[i][originalCol] = permutedMatrix[i][j];//将所对应的列放置正确的列位置
            }
        }

        // 按行顺序依次读出明文
        StringBuilder plaintext = new StringBuilder();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                plaintext.append(matrix[i][j]);
            }
        }

        return plaintext.toString().trim(); // 去除末尾空格
    }
}