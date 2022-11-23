package multifile;

public class cipherEnc {
    public static final String alpha = "abcdefghijklmnopqrstuvwxyz";
    public String enc(String plainText, int shiftKey) {
        plainText = plainText.toLowerCase();
        plainText = plainText.replaceAll(" ", "");
        String cipherText = "";
        for (int i = 0; i < plainText.length(); i++) {
        int charPosition = alpha.indexOf(plainText.charAt(i));
        int keyVal = (shiftKey + charPosition) % 26;
        char replaceVal = alpha.charAt(keyVal);
        cipherText += replaceVal;
        }
        return cipherText;
    }
}
//@dhir4j <3!