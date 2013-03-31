/*  ______ ______ ______ ______
 * |__    |      |__    |  __  |
 * |__    |_     |    __|__    |
 * |______| |____|______|______|
 */
package edu.wpi.first3729.frc2013.Movement;

import edu.wpi.first.wpilibj.Talon;

import edu.wpi.first3729.frc2013.inputs.*;
import edu.wpi.first3729.frc2013.utilities.*;
/**
 *
 * @author teddy
 */
public class Drive {
    private Talon left_drive0;
    private Talon left_drive1;
    private Talon right_drive0;
    private Talon right_drive1;
    private Input _input_manager;
    protected ControllerInterlink _input;
    protected Talon _drive;
    private double _x_prev;
    private double _y_prev;
    private double _x,_y;
    public double x = 0.0, y = 0.0, left = 0.0, right = 0.0;

    public void run() {
        // Drive robot based on drive mode
        switch (this._input_manager.getdrivemode()) {
            case Input.arcade:
                this.arcadedrive(this.x, this.y);
                break;
            case Input.arcadecontroller:
                this.arcadedrive(this.x, this.y);
                break;
            case Input.tank:
                this.tankdrive(this.left, this.right);
                break;
            case Input.locked:
                this.locked();
                break;
        }
    }
    public void setup() {
        this._input = new ControllerInterlink(Params.drive_joy);
        this.getinput();
    }
    
    public Drive() {
        _y_prev = _x_prev = 0.0;
        left_drive0 = new Talon(Params.left0_drive_port);
        left_drive1 = new Talon(Params.left1_drive_port);
        right_drive0 = new Talon(Params.right0_drive_port);
        right_drive1 = new Talon(Params.right1_drive_port);
    }
    public void getinput() {
        this._x = this._input.get_x();
        this._y = this._input.get_y();
        //this._input_manager.getdrivemode();
    }
      
    public void tankdrive(double left, double right) {
       left = Utility.ramp(left, _x_prev, Params.x_ramp_increment);
       right = Utility.ramp(right, _y_prev, Params.y_ramp_increment);

       left_drive0.set(-left);
       left_drive1.set(-left);
       right_drive0.set(right);
       right_drive1.set(right);
      _x_prev = left;
      _y_prev = right;
    }
    
    public void arcadedrive(double x, double y) {
//        x = Utility.ramp(x, _x_prev, Params.x_ramp_increment);
//        y = Utility.ramp(y, _y_prev, Params.y_ramp_increment);
        if ((y <= 0.1 && y > 0) || (y >= -0.1 && y < 0)) {
            this.tankdrive(x * 0.75, -x * 0.75);
        }
        else {
            double left = x+y;
            double right = y-x;
            left = Utility.clamp(left, -1.0, 1.0);
            right = Utility.clamp(right, -1.0, 1.0);        
            left_drive0.set(-left);
            left_drive1.set(-left);
            right_drive0.set(right);
            right_drive1.set(right);
            if (Params.testing){System.out.println("Left: " + left + "Right: " + right + " .");}
        }
        _x_prev = x;
        _y_prev = y;
    }
    public void locked() {
        left_drive0.set(0.0);
        left_drive1.set(0.0);
        right_drive0.set(0.0);
        right_drive1.set(0.0);
    }
}