import java.util.Scanner;

public class Virginia {
    public static void main(String[] args) {
        System.out.println("=== 维吉尼亚密码测试 ===");
        Scanner sc=new Scanner(System.in);
        while (true){
            System.out.print("key:");
            String key = sc.next();
            System.out.print("明文:");
            String plaintext = sc.next();
            String ciphertext = encrypt(plaintext, key);
            System.out.println("加密: " + ciphertext);
            String decrypted = decrypt(ciphertext, key);
            System.out.println("解密: " + decrypted);
        }

    }

    public static String encrypt(String plaintext, String key) {
        key = key.toLowerCase();
        StringBuilder ciphertext = new StringBuilder();
        int keyIndex = 0;

        for (int i = 0; i < plaintext.length(); i++) {
            char c = plaintext.charAt(i);
            if (c >= 'a' && c <= 'z') {
                int p = c - 'a';
                int k = key.charAt(keyIndex % key.length()) - 'a';//keyIndex % key.length()计算第几个字符如 余1 就是 o
                int encrypted = (p + k) % 26;
                ciphertext.append((char) (encrypted + 'a'));
                keyIndex++;
            } else if (c >= 'A' && c <= 'Z') {
                int p = c - 'A';
                int k = key.charAt(keyIndex % key.length()) - 'a';
                int encrypted = (p + k) % 26;
                ciphertext.append((char) (encrypted + 'A'));
                keyIndex++;
            } else {
                ciphertext.append(c);
            }
        }
        return ciphertext.toString();
    }

    public static String decrypt(String ciphertext, String key) {
        key = key.toLowerCase();//转小写
        StringBuilder plaintext = new StringBuilder();
        int keyIndex = 0;

        for (int i = 0; i < ciphertext.length(); i++) {
            char c = ciphertext.charAt(i);
            if (c >= 'a' && c <= 'z') {
                int encrypted = c - 'a';
                int k = key.charAt(keyIndex % key.length()) - 'a';//keyIndex % key.length()计算第几个字符如 余1 就是 o
                int decrypted = (encrypted - k) % 26;
                if (decrypted < 0) decrypted += 26;
                plaintext.append((char) (decrypted + 'a'));
                keyIndex++;
            } else if (c >= 'A' && c <= 'Z') {
                int encrypted = c - 'A';
                int k = key.charAt(keyIndex % key.length()) - 'a';
                int decrypted = (encrypted - k) % 26;
                if (decrypted < 0) decrypted += 26;
                plaintext.append((char) (decrypted + 'A'));
                keyIndex++;
            } else {
                plaintext.append(c);
            }
        }
        return plaintext.toString();
    }
}
