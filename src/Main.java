import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Main {

    public static void main(String[] args){
        File csv = new File("falcon500scrubbed.csv");
	    CSV_parser csv_parser = new CSV_parser(csv);
	    csv_parser.parse();
    }
}
