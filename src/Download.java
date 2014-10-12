import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class Download implements Runnable{
    private String url;

    public Download(String url) {
        this.url = url;
    }

    @Override
    public void run() {
        downloadFile(url);
    }

    public static void downloadFile(String address) {
        downloadFile(address, null);
    }

    public static void downloadFile(String address, File dest) {
        BufferedReader reader = null;
        FileOutputStream fos = null;
        InputStream in = null;
        try {

            URL url = new URL(address);
            URLConnection conn = url.openConnection();

            int FileLength = conn.getContentLength();
            if (FileLength == -1) {
                throw new IOException("File not valid.");
            }

            in = conn.getInputStream();
            reader = new BufferedReader(new InputStreamReader(in));
            if (dest == null) {
                String FileName = url.getFile();
                FileName = "downloads/"+FileName.substring(FileName.lastIndexOf('/') + 1);
                dest = new File(FileName);
            }
            fos = new FileOutputStream(dest);
            byte[] buff = new byte[1024];
            int l = in.read(buff);
            while (l > 0) {
                fos.write(buff, 0, l);
                l = in.read(buff);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.flush();
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Download download = new Download("http://www.un.org/depts/los/convention_agreements/texts/unclos/unclos_e.pdf");
        Thread thread = new Thread(download);
        thread.start();
    }
}