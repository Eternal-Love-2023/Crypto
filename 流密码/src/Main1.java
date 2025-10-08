import java.util.Arrays;

public class Main1 {
    public static void main(String[] args){
        System.out.println("=== 测试用例1 ===");
        String key = "cipherByRC4";
        String plaintext = "7896";

        System.out.println("密钥: " + key);
        System.out.println("明文: " + plaintext);

        // 加密
        RC4 rc4 = new RC4(key.getBytes());
        byte[] plaintextBytes = plaintext.getBytes();
        byte[] ciphertext = rc4.jm(plaintextBytes);

        System.out.println("密文(16进制): " + hex(ciphertext));
        System.out.println("密文(字符): " + new String(ciphertext));
    }

    public static String hex(byte[] bytes){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            sb.append(String.format("%02x", bytes[i] & 0xFF));  // 修复：输出字节值而不是索引
        }
        return sb.toString();
    }
}

class RC4{
    private byte[] S;
    private byte[] T;
    private int keylen;

    public RC4(byte[] key){
        this.S = new byte[256];
        this.T = new byte[256];
        this.keylen = key.length;  // 添加：初始化keylen
        csh(key);
    }

    private void csh(byte[] key){
        for (int i = 0; i < 256; i++){  // 修复：应该是256不是255
            S[i] = (byte) i;
            T[i] = key[i % keylen];
        }

        int j = 0;
        for(int i = 0; i < 256; i++){
            j = (j + (S[i] & 0xFF) + (T[i] & 0xFF)) % 256;  // 修复：处理字节无符号值
            swap(S, i, j);
        }
    }

    //交换
    private void swap(byte[] a, int i, int j){
        byte t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    //密钥流生成
    public byte[] keystream(int len){
        byte[] keyStream = new byte[len];
        int i = 0, j = 0;
        byte[] s_copy = Arrays.copyOf(S, S.length);
        for (int k = 0; k < len; k++){
            i = (i + 1) % 256;
            j = (j + (s_copy[i] & 0xFF)) % 256;  // 修复：使用s_copy而不是S
            swap(s_copy, i, j);
            int t = ((s_copy[i] & 0xFF) + (s_copy[j] & 0xFF)) % 256;  // 修复：处理字节无符号值
            keyStream[k] = s_copy[t];
        }
        return keyStream;
    }

    //加密
    public byte[] jm(byte[] plaintext){
        byte[] keystream = keystream(plaintext.length);
        byte[] c = new byte[plaintext.length];

        for (int i = 0; i < plaintext.length; i++){
            c[i] = (byte) (plaintext[i] ^ keystream[i]);
        }
        return c;
    }
}