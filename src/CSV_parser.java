import java.io.*;
import java.util.ArrayList;

//low level class, parses csv containing motor data
public class CSV_parser {

    private final File file;

    public CSV_parser(File file){
        this.file = file;
    }

    //returns motor data in order of ascending motor speed from data[0][]
    //each sub-array contains in ascending order from data[][0]:
    //speed(RPM), torque(NM), current(A), Supplied power(W), mechanical power(W), efficiency(%), power dissipation (W)
    public double[][] parse(){
        try (BufferedReader br = new BufferedReader(new FileReader(file))){
            ArrayList<double[]> cols = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                double[] row = new double[values.length];
                for (int i = 0; i < values.length; i++) {
                    row[i] = Double.parseDouble(values[i]);
                }
                cols.add(row);
            }
            double[][] data = new double[cols.size()][cols.get(0).length];
            for (int i = 0; i < cols.size(); i++) {
                data[i] = cols.get(i);
            }

//            for (double[] array :
//                    data) {
//                System.out.println();
//                for (double num :
//                        array) {
//                    System.out.print(num);
//                }
//            }

            return data;
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return null;
    }

}
