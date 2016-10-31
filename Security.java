//////////////////////////////////////////////////////////////////////////
// TODO:                                                                //
// Uloha1: Vytvorit funkciu na bezpecne generovanie saltu.              //
// Uloha2: Vytvorit funkciu na hashovanie.                              //
// Je vhodne vytvorit aj dalsie pomocne funkcie napr. na porovnavanie   //
// hesla ulozeneho v databaze so zadanym heslom.                        //
//////////////////////////////////////////////////////////////////////////
package src.zadanie3;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Random;

public class Security
{
    private static final Random RANDOM = new SecureRandom();
    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256;

    public static String hash(String password, String salt)
    {
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), DatatypeConverter.parseBase64Binary(salt), ITERATIONS, KEY_LENGTH);
        try
        {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return DatatypeConverter.printBase64Binary(skf.generateSecret(spec).getEncoded());
        }
        catch (NoSuchAlgorithmException | InvalidKeySpecException e)
        {
            throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
        }
        finally
        {
            spec.clearPassword();
        }
    }
    
    protected static String getSalt()
    {
        byte[] salt = new byte[32];
        RANDOM.nextBytes(salt);

        String saltString = DatatypeConverter.printBase64Binary(salt);

        return saltString;
    }

    protected static boolean verifyPassword(String password, String salt, String hash)
    {
        String passwordHash = hash(password, salt);

        if (passwordHash.length() != hash.length())
            return false;

        for (int i = 0; i < passwordHash.length(); i++)
        {
            if (passwordHash.charAt(i) != hash.charAt(i))
                return false;
        }

        return true;
    }

    protected static boolean isPasswordStrongEnough(String password)
    {
        if (password.length() < 8)
        {
            return false;
        }

        if (!password.matches(".*[\\d+]{2}.*"))
        {
            return false;
        }

        if (!password.matches(".*[A-Z]+.*") || !password.matches(".*[a-z]+.*"))
        {
            return false;
        }

        return true;
    }
}

