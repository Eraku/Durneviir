/*  ______ ______ ______ ______
 * |__    |      |__    |  __  |
 * |__    |_     |    __|__    |
 * |______| |____|______|______|
 */
package edu.wpi.first3729.frc2013.Gamemode;

import edu.wpi.first3729.frc2013.Movement.*;
import edu.wpi.first3729.frc2013.inputs.*;
import edu.wpi.first3729.frc2013.Robot;

import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.image.*;

/**
 *
 * @author teddy
 */
public class Autonomous extends GameMode {
    private AxisCamera _camera;
    private BinaryImage _image;

    /**
     *
     * @param drv
     * @param cam
     */
    public Autonomous(Drive drv, AxisCamera cam, Robot robot) {
        super(robot);
        this._drive = drv;
        this._camera = cam;
    }
    //Will shoot three frisbees then move forward ish
    // well maybe...    
    public void setup() {    
    }
    
    public void run() {
    }
}
