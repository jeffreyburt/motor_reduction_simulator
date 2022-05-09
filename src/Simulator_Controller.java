
//mid level class, manages all interaction with stick Simulator
public class Simulator_Controller {

    private final double[][] motor_data;

    private final double angle_step_rad = 0.002;
    private final double ratio_step = 0.25;

    public Simulator_Controller(double[][] motor_data){
        this.motor_data = motor_data;
    }

    public void simulate_state(Stick_State stickState, double max_ratio){
        Simulator simulator = new Simulator(motor_data);
        for (double ratio = 1; ratio <= max_ratio; ratio += ratio_step) {
            double max_recoverable_angle_rad = 0;
            while (true){
                if(simulator.simulate(new Stick_State(stickState, ratio, max_recoverable_angle_rad + angle_step_rad))){
                    max_recoverable_angle_rad += angle_step_rad;
                }else break;
            }
            System.out.println("A gear ratio of: " + ratio + ", was able to recover from a maximum angle of: " + Math.toDegrees(max_recoverable_angle_rad) + " degrees.");
        }
    }

    public void simulator_debug(Stick_State stickState, double max_ratio){
        Simulator simulator = new Simulator(motor_data);
        double ratio = 1;
            double max_recoverable_angle_rad = 0;
            while (true){
                if(simulator.simulate(new Stick_State(stickState, ratio, max_recoverable_angle_rad + angle_step_rad))){
                    max_recoverable_angle_rad += angle_step_rad;
                }else break;
            }
            System.out.println("A gear ratio of: " + ratio + ", was able to recover from a maximum angle of: " + Math.toDegrees(max_recoverable_angle_rad) + " degrees.");
    }

}
