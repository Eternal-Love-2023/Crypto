import java.util.HashMap;
import java.util.Map;

public class Single_table {
    // 加密代替表
    private static final String KEY_BOX = "qwertyuiopasdfghjklzxcvbnm";
    // 解密代替表（加密表的逆）
    private static final String INV_KEY_BOX = generateInvKeyBox(KEY_BOX);

    // 加密函数
    public static String encrypt(String plaintext, String keyBox) {
        StringBuilder ciphertext = new StringBuilder();
        for (int i = 0; i < plaintext.length(); i++) {
            char c = plaintext.charAt(i);
            if (c >= 'a' && c <= 'z') {
                int pos = c - 'a';
                ciphertext.append(keyBox.charAt(pos));
            } else if (c >= 'A' && c <= 'Z') {
                int pos = c - 'A';
                ciphertext.append(Character.toUpperCase(keyBox.charAt(pos)));
            } else {
                ciphertext.append(c);
            }
        }
        return ciphertext.toString();
    }

    // 解密函数
    public static String decrypt(String ciphertext, String invKeyBox) {
        StringBuilder plaintext = new StringBuilder();
        for (int i = 0; i < ciphertext.length(); i++) {
            char c = ciphertext.charAt(i);
            if (c >= 'a' && c <= 'z') {
                int pos = c - 'a';
                plaintext.append(invKeyBox.charAt(pos));
            } else if (c >= 'A' && c <= 'Z') {
                int pos = Character.toLowerCase(c) - 'a';
                plaintext.append(Character.toUpperCase(invKeyBox.charAt(pos)));
            } else {
                plaintext.append(c);
            }
        }
        return plaintext.toString();
    }

    // 生成解密代替表
    private static String generateInvKeyBox(String keyBox) {
        char[] invBox = new char[26];
        for (int i = 0; i < 26; i++) {
            char encChar = keyBox.charAt(i);
            int invIndex = encChar - 'a';
            invBox[invIndex] = (char) ('a' + i);
        }
        return new String(invBox);
    }

    // 测试
    public static void main(String[] args) {
        String plaintext = "youneverknowyourluck";
        String ciphertext = encrypt(plaintext, KEY_BOX);
        String decrypted = decrypt(ciphertext, INV_KEY_BOX);

        System.out.println("明文: " + plaintext);
        System.out.println("密文: " + ciphertext);
        System.out.println("解密: " + decrypted);
    }
}

