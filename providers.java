
import java.util.Collections;
import java.util.List;
import java.security.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        for (Provider provider : Security.getProviders()) {
            System.out.println(provider.getName());
            List<String> resultAsList = new ArrayList<>(provider.stringPropertyNames());
            Collections.sort(resultAsList);
            for (String key : resultAsList){
                if( key.startsWith("Signature") || key.startsWith("Alg.Alias.Signature")){
                    System.out.println("\t" + key + "\t" + provider.getProperty(key));
                }
            }
        }
    }
}

