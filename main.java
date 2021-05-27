import java.net.*;
import java.io.*;
import javax.net.ssl.*;
import java.util.TreeMap;
import java.util.Map.Entry;

public class Main {
    public static void main(String[] args) {
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
}
