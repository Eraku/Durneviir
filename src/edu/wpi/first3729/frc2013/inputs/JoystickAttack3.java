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
public class JoystickAttack3 extends Input {
    public JoystickAttack3(int joy) {
        super(joy);
    }

    public double get_x() {
        return Utility.expo(Utility.normalize(super.get_x(), -1.0, 1.0), Params.JOYEXPO);
    }

    public double get_y() {
        return Utility.expo(Utility.normalize(super.get_y(), -1.0, 1.0) * -1.0, Params.JOYEXPO);
    }
} 