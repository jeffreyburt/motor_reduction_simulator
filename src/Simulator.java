import java.util.concurrent.TimeUnit;

//low level class, returns whether a the stick is capable of recovering from a certain given Stick_State
public class Simulator {

    private Stick_State state;
    private final double[][] motor_data;
    private final double time_delta_sec = 0.000001;
    private final boolean debug_mode = false;


    public Simulator(double[][] motor_data){
        this.motor_data = motor_data;
    }

    public boolean simulate(Stick_State state){
        this.state = state;
        while (true){
            if(debug_mode) print_frame();


            //flywheel calculations and updates
            double flywheel_torque = calculate_flywheel_torque();
            update_flywheel_velocity(flywheel_torque);

            //stick calculations and updates
            double gravity_torque = calculate_gravity_torque();
            update_stick_angle(flywheel_torque, gravity_torque);
            update_stick_velocity(flywheel_torque, gravity_torque);

            //testing if stick has failed at recovering from current state
            if(state.getStick_speed_rad_sec() > 0){
                if(debug_mode) System.out.println("Stick failed");
                return false;
            }

            //testing if stick has successfully recovered
            if(state.getStick_deflection_degrees() <= 0){
                if(debug_mode) System.out.println("Stick Recovered!");
                return true;
            }
            if(debug_mode) System.out.println("next sim frame");
        }
    }

    //expressed as a negative number
    private double calculate_flywheel_torque(){
        double current_motor_speed = state.getMotor_speed_rpm();

        //finding of data points surrounding motor rpm
        int lower_close_index = 0;
        for (int i = 0; i < motor_data.length - 1; i++) {
            if(motor_data[i + 1][0] <= current_motor_speed){
                lower_close_index = i + 1;
            }else{ break; }
        }

        //basic bounds checking
        if(lower_close_index == motor_data.length - 1) lower_close_index = motor_data.length - 2;

        //linear fit between two surrounding points
        double slope = (motor_data[lower_close_index][1] - motor_data[lower_close_index + 1][1]) / (motor_data[lower_close_index][0] - motor_data[lower_close_index + 1][0]);
        double base_torque = ((current_motor_speed - motor_data[lower_close_index][0]) * slope) + motor_data[lower_close_index][1];

        //adjust torque for direction and gear ratio
        return ((double) -1) * state.getFlywheel_reduction() * base_torque;
    }

    private void update_flywheel_velocity(double torque){
        double angular_accel = Math.abs(torque) / state.getFlywheel_moi_kgm2();
        state.setFlywheel_speed_rad_sec(state.getFlywheel_speed_rad_sec() + (angular_accel * time_delta_sec));
        //System.out.println("flywheel velocity "+state.getMotor_speed_rpm());
    }

    //called after new stick angle is calculated
    private void update_stick_velocity(double flywheel_torque, double gravity_torque){
        double net_torque = gravity_torque + flywheel_torque;

        double angular_accel = net_torque / state.getStick_moi_kgm2();
        //System.out.println(angular_accel*time_delta_sec);

        state.setStick_speed_rad_sec(state.getStick_speed_rad_sec() + (angular_accel * time_delta_sec));
        //System.out.println("new stick velocity " + state.getStick_speed_rad_sec());
    }

    //expressed as a positive number
    private double calculate_gravity_torque(){
        return state.getLength_M() * Math.sin(state.getStick_deflection_rad()) * state.getMass_KG() * 9.81;
    }

    private void update_stick_angle(double flywheel_torque, double gravity_torque){
        double net_torque = gravity_torque + flywheel_torque;
        double angular_accel = net_torque / state.getStick_moi_kgm2();

        double new_deflection = state.getStick_deflection_rad() + (state.getStick_speed_rad_sec() * time_delta_sec) +
                (0.5 * angular_accel * Math.pow(time_delta_sec, 2));
        state.setStick_deflection_rad(new_deflection);
    }

    private void print_frame(){
        System.out.println("Stick is at angle: " + state.getStick_deflection_degrees() + ", with motor speed of: " + state.getMotor_speed_rpm()
        + " and stick velocity of " + Math.toDegrees(state.getStick_speed_rad_sec()));
    }

}
