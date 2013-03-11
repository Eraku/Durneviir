/*  ______ ______ ______ ______
 * |__    |      |__    |  __  |
 * |__    |_     |    __|__    |
 * |______| |____|______|______|
 */
package edu.wpi.first3729.frc2013.inputs;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first3729.frc2013.utilities.*;

/*
 *
 * @author teddy
 */
public class Input{
    protected Joystick _controller0;
    protected Joystick _controller1;
    protected Joystick _joy0;
    protected Joystick _joy1;
    protected int drive_mode = Params.default_drive_mode;
    public static final int arcade = 0;
    public static final int arcadecontroller = 3;
    public static final int tank = 1;
    public static final int locked = 2;
    
    public Input(int number) {
        this._joy0 = new Joystick(1);
        this._joy1 = new Joystick(2);
        this._controller0 = new Joystick(3);
        this._controller1 = new Joystick(4);   
    }

    public void setdrivemode(int m) {
        this.drive_mode = m;
    }
    public boolean checkbutton(int joystick, int buttonid) {
        switch (joystick) {
            case 0:
                return this._joy0.getRawButton(buttonid);
            case 1:
                return this._joy1.getRawButton(buttonid);
            case 2:
                return this._controller0.getRawButton(buttonid);
            case 3:
                return this._controller1.getRawButton(buttonid);
            default:
                return checkbutton(2, buttonid);
        }
    }
    
    public int getdrivemode() {
        return drive_mode;
    }
    
    public double get_x() {
        switch (drive_mode) {
            case arcade:
                return this._joy1.getX();
            case arcadecontroller:
                return this._controller1.getX()*-1.0;
            case tank:
                return this._joy0.getX()*-1.0;
            case locked:
                return 0;
            default:
                drive_mode = Params.default_drive_mode;
                return get_x();
        }
    }

    public double get_y() {
        switch (drive_mode) {
            case arcade:
                return this._joy1.getY();
            case arcadecontroller:
                return this._controller0.getY();
            case tank:
                return this._joy1.getY();
            case locked:
                return 0;
            default:
                drive_mode = Params.default_drive_mode;
                return get_y();
        }
    }
     public double getthrottle(int joy) {
        switch (drive_mode) {
            case arcade:
                switch (joy) {
                    case 0:
                        return this._joy0.getThrottle();
                    case 1:
                        return this._joy1.getThrottle();
                    default:
                        joy = 0;
                        return getthrottle(joy);
                }
            case arcadecontroller:
                return this._joy0.getThrottle();
            case tank:
                switch (joy) {
                    case 0:
                        return this._joy0.getThrottle();
                    case 1:
                        return this._joy1.getThrottle();
                    case 2:
                        return this._controller0.getThrottle();  // 'Controller' is actually 3rd joystick in this case
                    case 3:
                        return this._controller1.getThrottle();
                    default:
                        joy = 0;
                        return getthrottle(joy);
                }
            case locked:
                return 0;
            default:
                drive_mode = Params.default_drive_mode;
                return getthrottle(0);
        }
     }
    public double gettwist(int joy) {
        switch (drive_mode) {
            case arcade:
                switch (joy) {
                    case 0:
                        return this._joy0.getTwist();
                    case 1:
                        return this._joy1.getTwist();
                    default:
                        return gettwist(0);
                }
            case arcadecontroller:
                return this._joy0.getTwist();
            case tank:
                switch (joy) {
                    case 0:
                        return this._joy0.getTwist();
                    case 1:
                        return this._joy1.getTwist();
                    case 2:
                        return this._controller0.getTwist();  // 'Controller' is actually 3rd joystick in this case
                    case 3:
                        return this._controller1.getTwist();
                    default:
                        return gettwist(0);
                }
            case locked:
                return 0;
            default:
                drive_mode = Params.default_drive_mode;
                return gettwist(0);
        }
    }
}