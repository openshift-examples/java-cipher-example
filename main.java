// Source:
//   - https://stackoverflow.com/questions/62841553/how-can-i-configure-java-cryptography-extension-jce-in-openjdk-11

import java.net.*;
import java.io.*;
import javax.net.ssl.*;
import java.util.TreeMap;
import java.util.Map.Entry;
import javax.crypto.Cipher;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

public class Main {
    public static void main(String[] args) throws NoSuchAlgorithmException {

        // Security.setProperty("crypto.policy", "limited"); // uncomment to switch to limited crypto policies
        System.out.println("Check for unlimited crypto policies");
        System.out.println("Java version: " + Runtime.version());
        //Security.setProperty("crypto.policy", "limited"); // muss ganz am anfang gesetzt werden !
        System.out.println("restricted cryptography: " + restrictedCryptography() + " Notice: 'false' means unlimited policies"); // false mean unlimited crypto
        System.out.println("Security properties: (crypto.policy) " + Security.getProperty("crypto.policy"));
        System.out.println("Security properties (jdk.tls.disabledAlgorithms): " + Security.getProperty("jdk.tls.disabledAlgorithms"));
        int maxKeyLen = Cipher.getMaxAllowedKeyLength("AES");
        System.out.println("Max AES key length = " + maxKeyLen);

        SSLServerSocketFactory ssf = (SSLServerSocketFactory)SSLServerSocketFactory.getDefault();

        TreeMap<String, Boolean> ciphers = new TreeMap<>();
        for (String cipher : ssf.getSupportedCipherSuites())
            ciphers.put(cipher, Boolean.FALSE);
        for (String cipher : ssf.getDefaultCipherSuites())
            ciphers.put(cipher, Boolean.TRUE);

        System.out.println("Default Cipher");
        for (Entry<String, Boolean> cipher : ciphers.entrySet())
            System.out.printf("   %-5s%s%n", (cipher.getValue() ? '*' : ' '), cipher.getKey());
    }

    /**
     * Determines if cryptography restrictions apply.
     * Restrictions apply if the value of {@link Cipher#getMaxAllowedKeyLength(String)} returns a value smaller than {@link Integer#MAX_VALUE} if there are any restrictions according to the JavaDoc of the method.
     * This method is used with the transform <code>"AES/CBC/PKCS5Padding"</code> as this is an often used algorithm that is <a href="https://docs.oracle.com/javase/8/docs/technotes/guides/security/StandardNames.html#impl">an implementation requirement for Java SE</a>.
     *
     * @return <code>true</code> if restrictions apply, <code>false</code> otherwise
     * https://stackoverflow.com/posts/33849265/edit, author Maarten Bodewes
     */
    public static boolean restrictedCryptography() {
        try {
            return Cipher.getMaxAllowedKeyLength("AES/CBC/PKCS5Padding") < Integer.MAX_VALUE;
        } catch (final NoSuchAlgorithmException e) {
            throw new IllegalStateException("The transform \"AES/CBC/PKCS5Padding\" is not available (the availability of this algorithm is mandatory for Java SE implementations)", e);
        }
    }

}
