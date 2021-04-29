package darth.bartenderbot.utils.Image;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Random;

public class ImageUtils {
    public static void saveImage(String imageUrl, String key) throws IOException {
        try {
            String durl = URLDecoder.decode(imageUrl, "UTF-8");
            URL url = new URL(durl);
            //URLConnection urlConnection = url.openConnection();
            //urlConnection.connect();
            InputStream is = url.openStream();
            //InputStream is = new java.io.BufferedInputStream(urlConnection.getInputStream());
            String fileraw = imageUrl.substring(imageUrl.lastIndexOf("/")+1);
            String ext = imageUrl.substring(imageUrl.lastIndexOf("."));
            String name = fileraw.replace(ext, "");
            String file = key + "/" + name;
            File cat = new File(key + "/");
            int count = cat.listFiles().length;
            Random random = new Random();
            OutputStream os = new FileOutputStream(file.replace(name, "")+count+random.nextInt(9999999)+ext);
            byte[] b = new byte[2048];
            int length;
            while ((length = is.read(b)) != -1) {
                os.write(b, 0, length);
            }
            is.close();
            os.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
