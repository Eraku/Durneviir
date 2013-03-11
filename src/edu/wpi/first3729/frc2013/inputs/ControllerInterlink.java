/*  ______ ______ ______ ______
 * |__    |      |__    |  __  |
 * |__    |_     |    __|__    |
 * |______| |____|______|______|
 */
package edu.wpi.first3729.frc2013.inputs;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first3729.frc2013.utilities.*;

/**
 *
 * @author teddy
 */
public class ControllerInterlink extends Input {
    
    //! Left and right stick
    private static final int LEFT_STICK = 0;
    private static final int RIGHT_STICK = 1;
    
    public ControllerInterlink(int joy) {
        super(joy);
    }
    
    public double get_x() {
        return Utility.expo(Utility.normalize(super.get_x(), Params.XMIN, Params.XMAX), Params.XEXPO) * -1.0;
    }

    public double get_y() {
         return Utility.expo(Utility.normalize(super.get_y(), Params.YMIN, Params.YMAX), Params.YEXPO);
    }
    
    public void left_stick() {
        this._joy0.setAxisChannel(Joystick.AxisType.kX, LEFT_STICK);
        this._joy0.setAxisChannel(Joystick.AxisType.kY, LEFT_STICK);
        this._joy0.setAxisChannel(Joystick.AxisType.kThrottle, LEFT_STICK);
    }
    
    public void right_stick() {
        this._joy1.setAxisChannel(Joystick.AxisType.kX, RIGHT_STICK);
        this._joy1.setAxisChannel(Joystick.AxisType.kY, RIGHT_STICK);
        this._joy1.setAxisChannel(Joystick.AxisType.kThrottle, RIGHT_STICK);
    }
}
