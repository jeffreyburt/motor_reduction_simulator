
//low level class, contains all stick state data
public class Stick_State {

    //TODO documentation for units
    private final double flywheel_reduction;

    private final double length_M;

    private final double mass_KG;

    private double stick_speed_rad_sec;

    private double stick_deflection_rad;

    private final double flywheel_moi_kgm2;

    private double flywheel_speed_rad_sec;



    public Stick_State(double flywheel_reduction, double length_M, double mass_KG, double stick_speed_rad_sec,
                       double stick_deflection_rad, double flywheel_moi_kgm2, double flywheel_speed_rad_sec){

        this.flywheel_reduction = flywheel_reduction;
        this.length_M = length_M;
        this.mass_KG = mass_KG;
        this.stick_speed_rad_sec = stick_speed_rad_sec;
        this.stick_deflection_rad = stick_deflection_rad;
        this.flywheel_moi_kgm2 = flywheel_moi_kgm2;
        this.flywheel_speed_rad_sec = flywheel_speed_rad_sec;
    }

    public Stick_State(Stick_State copy_state, double flywheel_reduction, double stick_deflection_rad){
        flywheel_speed_rad_sec = 0;
        length_M = copy_state.getLength_M();
        this.flywheel_reduction = flywheel_reduction;
        mass_KG = copy_state.getMass_KG();
        this.stick_deflection_rad = stick_deflection_rad;
        flywheel_moi_kgm2 = copy_state.flywheel_moi_kgm2;
        flywheel_speed_rad_sec = 0;
    }

    public double getFlywheel_reduction(){
        return flywheel_reduction;
    }

    public double getLength_M() {
        return length_M;
    }

    public double getStick_moi_kgm2(){
        return mass_KG * Math.pow(length_M,2);
    }

    public double getFlywheel_moi_kgm2() {
        return flywheel_moi_kgm2;
    }

    public double getFlywheel_speed_rad_sec() {
        return flywheel_speed_rad_sec;
    }

    //TODO magic number
    public double getFlywheel_speed_rpm(){
        return flywheel_speed_rad_sec * 9.5493;
    }

    public double getMotor_speed_rpm(){
        return getFlywheel_speed_rpm() * flywheel_reduction;
    }

    public double getStick_deflection_degrees(){
        return Math.toDegrees(stick_deflection_rad);
    }

    public double getStick_deflection_rad() {
        return stick_deflection_rad;
    }

    public double getMass_KG() {
        return mass_KG;
    }

    public double getStick_speed_rad_sec() {
        return stick_speed_rad_sec;
    }

    public void setFlywheel_speed_rad_sec(double flywheel_speed_rad_sec) {
        this.flywheel_speed_rad_sec = flywheel_speed_rad_sec;
    }

    public void setFlywheel_speed_rpm(double rpm){
        flywheel_speed_rad_sec = rpm / 9.5493;
    }

    public void setStick_deflection_rad(double stick_deflection_rad) {
        this.stick_deflection_rad = stick_deflection_rad;
    }

    public void setStick_deflection_degrees(double degrees){
        stick_deflection_rad = Math.toRadians(degrees);
    }

    public void setStick_speed_rad_sec(double stick_speed_rad_sec) {
        this.stick_speed_rad_sec = stick_speed_rad_sec;
    }


}
