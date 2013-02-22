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
    public void setmode(int m) {
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
    
    public int getmode() {
        return drive_mode;
    }
    
    public double get_x() {
        switch (drive_mode) {
            case arcade:
                return Utility.expo(Utility.normalize(this._joy1.getX(), -1.0, 1.0), Params.JOYEXPO);
            case arcadecontroller:
                return Utility.expo(Utility.normalize(this._controller0.getX(), Params.XMIN, Params.XMAX), Params.XEXPO) * -1.0;
            case tank:
                return Utility.expo(Utility.normalize(this._joy0.getY(), -1.0, 1.0) * -1.0, Params.JOYEXPO);
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
                return Utility.expo(Utility.normalize(this._joy1.getY(), -1.0, 1.0) * -1.0, Params.JOYEXPO);
            case arcadecontroller:
                return Utility.expo(Utility.normalize(this._controller0.getY(), Params.YMIN, Params.YMAX), Params.YEXPO);
            case tank:
                return Utility.expo(Utility.normalize(this._joy1.getY(), -1.0, 1.0) * -1.0, Params.JOYEXPO);
            case locked:
                return 0;
            default:
                drive_mode = Params.default_drive_mode;
                return get_y();
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
                        return this._controller0.getTwist();  // 'Controller' is actually 3rd joystick
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
