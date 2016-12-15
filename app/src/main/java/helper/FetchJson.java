package helper;

import android.os.AsyncTask;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by anurag.yadav on 12/15/16.
 */
public class FetchJson extends AsyncTask<Void, Void, String> {

    @Override
    protected String doInBackground(Void... voids) {
        String responseString = null;
        try {
            URL url = new URL("https://s3.amazonaws.com/media.mobstac.com/test/news.json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            if(conn.getResponseCode() == HttpsURLConnection.HTTP_OK){
                InputStream it = new BufferedInputStream(conn.getInputStream());
                InputStreamReader read = new InputStreamReader(it);
                BufferedReader buff = new BufferedReader(read);
                StringBuilder dta = new StringBuilder();
                String chunks ;
                while((chunks = buff.readLine()) != null)
                {
                    dta.append(chunks);
                }
                responseString = dta.toString();
            }
            else {
                responseString = "FAILED"; // See documentation for more info on response handling
            }
        } catch (IOException e) {
        }
        return responseString;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
