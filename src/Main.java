import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private String filePath;

    public Main(String filePath) {
        this.filePath = filePath;
    }

    private List<String> getUrls() throws IOException{
        BufferedReader  fin = new BufferedReader( new InputStreamReader(new FileInputStream(filePath)));
        List<String> fileContent = new ArrayList<String>() ;
        String str;
        while( (str = fin.readLine() ) != null )
            fileContent.add(str) ;
        fin.close() ;
        return fileContent;
    }

    public static void main(String[] args) throws IOException{
        Main main = new Main("files/Urls.txt");
        List<String> res = main.getUrls();
        Thread[] threads = new Thread[res.size()];
        int i = 0;
        for (String s: res){
            Download download = new Download(s);
            threads[i] = new Thread(download);
            threads[i].start();
            i++;
        }
    }
}
