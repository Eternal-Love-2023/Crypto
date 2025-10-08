public class Main {
    public static void main(String[] args) {
        int[] initState={1,1,0,0,1};
        KeyStream keyStream=new KeyStream(initState);

        String plaintext="abc";
        String ctext=keyStream.jm(plaintext);
        System.out.println("明文"+plaintext);
        System.out.println("密文"+ctext);

        String plaintext1="abcabcabcabc";
        String ctext1=keyStream.jm(plaintext1);
        System.out.println("明文"+plaintext1);
        System.out.println("密文"+ctext1);
    }
}

//密钥流初始化
class KeyStream{
    private int[] keystream;
    private int cycle;
    public KeyStream(int[] initState){
        this.cycle=31;
        this.keystream=new int[cycle];
        csh(initState);
    }
    private void csh(int[] initState){
        for (int i=0;i<initState.length;i++){
            keystream[i]=initState[i];
        }
        for (int k=0;k<keystream.length-initState.length-1;k++){
            keystream[5+k]=keystream[3+k]^keystream[k];
        }
    }
    public String jm(String plaintext){
        if (plaintext==null||plaintext.isEmpty()){
            return plaintext;
        }
        byte[] m=plaintext.getBytes();
        byte[] c=new byte[m.length];

        //加密
        for (int i=0;i<m.length;i++){
            int k=0;
            for (int j=0;j<7;j++){
                k=k*2+keystream[(i*7+j)%keystream.length];
            }
            c[i]=(byte) (m[i]^k);//异或
        }

        return new String(c);
    }
}




