package helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by anurag.yadav on 12/15/16.
 */
public class ImageHelper {

    public void setImageView(final Article expandedListText, final ImageView articleImage) {
        new AsyncTask<Void, Void, Void>() {
            Bitmap bmp = null;
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    InputStream in = new URL(expandedListText.getImage()).openStream();
                    bmp = BitmapFactory.decodeStream(in);
                } catch (Exception e) {
                    // log error
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                if (bmp != null)
                    articleImage.setImageBitmap(bmp);
            }

        }.execute();
    }
}
