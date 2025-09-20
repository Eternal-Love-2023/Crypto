import java.util.*;

public class Affine {
    public static void main(String[] args){
        Scanner sc=new Scanner(System.in);
        System.out.println("====单表加解密测试====");

       while (true) {
           String m = sc.next();
           int a = sc.nextInt();
           int b = sc.nextInt();
           if (judge(a) != 1) {
               System.out.println("a与26不互素");
           } else {
               System.out.println("源码:" + m);
               System.out.println("密文" + encrypt(m, a, b));
               System.out.println("明文" + decrypt(encrypt(m, a, b), a, b));
           }
       }
    }
    public static String encrypt(String plaintext,int a,int b){
        int n=plaintext.length();//将明文长度作为循环判断
        StringBuilder ctext=new StringBuilder();//密文空间
        for (int i=0;i<n;i++){
            if(plaintext.charAt(i)!=' '){
                char m=plaintext.charAt(i);
                if (m>='A'&&m<='Z'){
                    int p=m-'A';
                    int c=(a*p+b)%26;
                    if (c<0){
                        c=c+26;
                    }
                    ctext.append((char) (c+'A'));
                } else if (m>='a'&&m<='z') {
                    int p=m-'a';
                    int c=(a*p+b)%26;
                    if (c<0){
                        c=c+26;
                    }
                    ctext.append((char) (c+'a'));
                }
            }
        }
        return ctext.toString();
    }
    public static String decrypt(String ciphertext,int a,int b){
        int n=ciphertext.length();//将明文长度作为循环判断
        StringBuilder mtext=new StringBuilder();//密文空间
        int a_=converse(a);//算逆元
        for (int i=0;i<n;i++){
            if(ciphertext.charAt(i)!=' '){
                char m=ciphertext.charAt(i);
                if (m>='A'&&m<='Z'){
                    int c=m-'A';
                    int p=(a_*(c-b))%26;
                    if (p<0){
                        p=p+26;
                    }
                    mtext.append((char) (p+'A'));
                } else if (m>='a'&&m<='z') {
                    int c=m-'a';
                    int p=(a_*(c-b))%26;
                    if (p<0){
                        p=p+26;
                    }
                    mtext.append((char) (p+'a'));
                }
            }
        }
        return mtext.toString();
    }
    private static int judge(int a){
        int n1=Math.abs(a);
        int n2=26;

        while (n2!=0){
            int t=n2;
            n2=n1%n2;
            n1=t;
        }
        return n1;
    }
    private static int converse(int a){

        int aInversr=1;
        while ((a*aInversr)%26!=1){
            aInversr++;
        }
        return aInversr;
    }
}

