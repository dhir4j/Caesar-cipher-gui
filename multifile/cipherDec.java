package multifile;

public class cipherDec{
    public static final String alpha = "abcdefghijklmnopqrstuvwxyz";
    public String dec(String cipherText, int shiftKey) {
        cipherText = cipherText.toLowerCase();
        String message = "";
        for (int ii = 0; ii < cipherText.length(); ii++) {
          int charPosition = alpha.indexOf(cipherText.charAt(ii));
          int keyVal = (charPosition - shiftKey) % 26;
          if (keyVal < 0) {
            keyVal = alpha.length() + keyVal;
          }
          char replaceVal = alpha.charAt(keyVal);
          message += replaceVal;
        }
        return message;
      }
}
//@dhir4j <3!