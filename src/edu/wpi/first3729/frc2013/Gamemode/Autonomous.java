/*  ______ ______ ______ ______
 * |__    |      |__    |  __  |
 * |__    |_     |    __|__    |
 * |______| |____|______|______|
 */
package edu.wpi.first3729.frc2013.Gamemode;

import edu.wpi.first3729.frc2013.Movement.*;
import edu.wpi.first3729.frc2013.Robot;

import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.image.*;

/**
 *
 * @author teddy
 */
public class Autonomous extends GameMode {
    private Drive _drive;
    private AxisCamera _camera;
    private BinaryImage _image;
//    private final Drive drv;
//    private final AxisCamera cam;

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

    Autonomous(Robot robot) {
        super(robot);
    }

    public void run() {
    }
}
