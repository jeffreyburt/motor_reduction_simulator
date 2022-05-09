import java.io.File;


public class Main {

    public static void main(String[] args){
        File csv = new File("falcon500scrubbed.csv");
	    CSV_parser csv_parser = new CSV_parser(csv);
        double[][] data = csv_parser.parse();

        //////////////////////////////////////
        //stick parameters
        double length_M = 0.5;
        double stick_mass_kg = 3.0;
        double flywheel_moi_kgm2 = 0.001170559;

        Stick_State base_state = new Stick_State(0,length_M, stick_mass_kg, 0, 0, flywheel_moi_kgm2, 0);
        Simulator_Controller simulator_controller= new Simulator_Controller(data);
        simulator_controller.simulator_debug(base_state,30);





    }
}
