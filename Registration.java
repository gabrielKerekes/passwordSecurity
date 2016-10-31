package src.zadanie3;

import java.security.NoSuchAlgorithmException;
import src.zadanie3.Database.MyResult;


public class Registration
{
    protected static MyResult registracia(String meno, String heslo) throws NoSuchAlgorithmException, Exception
    {
        if (Database.exist("hesla.txt", meno))
        {
            System.out.println("Meno je uz zabrate.");
            return new MyResult(false, "Meno je uz zabrate.");
        }
        else if (!Security.isPasswordStrongEnough(heslo))
        {
            System.out.println("Heslo nie je dostatocne silne.");
            return new MyResult(false, "Heslo nie je dostatocne silne.");
        }
        else
        {
            String salt = Security.getSalt();
            String passwordHash = Security.hash(heslo, salt);

            Database.add("hesla.txt", meno + ":" + passwordHash + ":" + salt);
        }

        return new MyResult(true, "");
    }
}
