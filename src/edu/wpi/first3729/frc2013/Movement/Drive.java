/*  ______ ______ ______ ______
 * |__    |      |__    |  __  |
 * |__    |_     |    __|__    |
 * |______| |____|______|______|
 */
package edu.wpi.first3729.frc2013.Movement;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first3729.frc2013.Gamemode.*;
import edu.wpi.first3729.frc2013.inputs.*;
import edu.wpi.first3729.frc2013.utilities.*;
/**
 *
 * @author teddy
 */
public class Drive implements Movement {
    private Talon left_drive;
    private Talon right_drive;
    private Input _input_manager;
    protected GameMode _mode;
    protected ControllerInterlink _input;
    protected Talon _drive;
    private double _x_prev;
    private double _y_prev;
    private double _x,_y;

    public Drive(GameMode ret) {
    }

    public void run() {
        
    }
    public void setup(GameMode mode, Input imanager) {
        this._input_manager = imanager;
        this._input = new ControllerInterlink(Params.drive_joy);
        this._drive = new Talon(Params.left_drive_port,Params.right_drive_port);
        this._mode = mode;
        this.getinput();
    }
    public Drive() {
        _y_prev = _x_prev = 0.0;
        left_drive = new Talon(Params.left_drive_port);
        right_drive = new Talon(Params.right_drive_port);
    }
    public void getinput() {
        this._x = this._input.get_x();
        this._y = this._input.get_y();
        this._input_manager.getdrivemode();
    }
    private double ramp(double desired_output, double current_output, double increment) {
        if (desired_output <= .1 && desired_output >= -.1) {
            increment /= 2;
        }
        if (desired_output < current_output) {
            return (current_output - increment) < 0.01 && (current_output - increment) > -0.01 ? 0 : current_output - increment;
        } else if (desired_output > current_output) {
            return (current_output + increment) < 0.01 && (current_output + increment) > -0.01 ? 0 : current_output + increment;
        } else {
            return current_output < 0.01 && current_output > -0.01 ? 0 : current_output;
        }
    }
    
    public void tankdrive(double left, double right) {
       left = ramp(left, _x_prev, Params.x_ramp_increment);
       right = ramp(right, _y_prev, Params.y_ramp_increment);

       left_drive.set(-left);
       right_drive.set(right);
      _x_prev = left;
      _y_prev = right;
    }
    
    public void arcadedrive(double x, double y) {
        x = ramp(x, _x_prev, Params.x_ramp_increment);
        y = ramp(y, _y_prev, Params.y_ramp_increment);
        if ((y <= 0.1 && y > 0) || (y >= -0.1 && y < 0)) {
            this.tankdrive(x * 0.75, -x * 0.75);
        }
        else {
            double left = x+y * -1.0;
            double right = y-x;
            left = Utility.clamp(left, -1.0, 1.0);
            right = Utility.clamp(right, -1.0, 1.0);        
            left_drive.set(left);
            right_drive.set(right);
        }
        _x_prev = x;
        _y_prev = y;
    }
    public void locked() {
        left_drive.set(0.0);
        right_drive.set(0.0);
    }

    public void setup() {
    }

}
//   public FRCDrive(FRCGameMode mode) {
//        this._mode = mode;
//    }
//    
//    public void setup() {
//        this._input = new FRCInputInterLink(DRIVE_JOYSTICK);
//        this._drive = new RobotDrive(FL_JAGUAR, RL_JAGUAR, FR_JAGUAR, RR_JAGUAR);
//    }
//    
//    public void loop_periodic() {
//        this.get_input();
//        this.arcade_drive();
//    }
//    
//    public void loop_continuous() {
//        
//    }
//    
//    /**
//     * @brief Updates local input fields with values read from input devices
//     */
//    public void get_input() {
//        // Get X and Y for the right stick
//        this._x = this._input.get_x() * INPUT_SCALE;
//        this._y = -this._input.get_y() * INPUT_SCALE;
//    }
//    
//    public void arcade_drive() {
//        if (Math.abs(this._y) < 0.1)
//            this._drive.tankDrive(-this._x * TANK_SCALE, this._x * TANK_SCALE);
//        else
//            this._drive.arcadeDrive(this._y, this._x);
//    }
//    
//    public void drive() {
//        if (Math.abs(this._y) < 0.05)
//            this._drive.tankDrive(-this._x * TANK_SCALE, this._x * TANK_SCALE);
//        else
//            this._drive.drive(this._y, this._x);
//    }
//    
//    public void tank_drive(double left, double right) {
//        this._drive.tankDrive(-left, -right);
//    }
//    
//    public void arcade_drive(double speed, double turn) {
//        this._drive.arcadeDrive(speed, turn);
//    }
//    public void loop_periodic(int state) { }
