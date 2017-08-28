package tecpoo.instapoo.gui;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

class InputStreamFileLoader {

    public static void copyInputStreamToFile(InputStream inputStream, File file) {
        OutputStream outputStream = null;

        try {
            outputStream = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while((len=inputStream.read(buf))>0){
                outputStream.write(buf,0,len);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {

            try {
                if ( outputStream != null ) {
                    outputStream.close();
                }

                inputStream.close();
            }
            catch ( IOException e ) {
                e.printStackTrace();
            }
        }
    }
}
