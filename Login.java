//////////////////////////////////////////////////////////////////////////
// TODO:                                                                //
// Uloha2: Upravte funkciu na prihlasovanie tak, aby porovnavala        //
//         heslo ulozene v databaze s heslom od uzivatela po            //
//         potrebnych upravach.                                         //
// Uloha3: Vlozte do prihlasovania nejaku formu oneskorenia.            //
//////////////////////////////////////////////////////////////////////////
package src.zadanie3;

import java.io.IOException;
import java.util.StringTokenizer;
import src.zadanie3.Database.MyResult;

public class Login
{
    protected static MyResult prihlasovanie(String meno, String heslo) throws IOException, Exception
    {
        MyResult account = Database.find("hesla.txt", meno);
        if (!account.getFirst())
        {
            return new MyResult(false, "Nespravne meno.");
        }
        else
        {
            StringTokenizer st = new StringTokenizer(account.getSecond(), ":");
            st.nextToken();      //prvy token je prihlasovacie meno

            String hash = st.nextToken();
            String salt = st.nextToken();
            boolean rightPassword = Security.verifyPassword(heslo, salt, hash);
            if (!rightPassword)    
                return new MyResult(false, "Nespravne heslo.");
        }

        return new MyResult(true, "Uspesne prihlasenie.");
    }
}
