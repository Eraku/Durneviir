/*  ______ ______ ______ ______
 * |__    |      |__    |  __  |
 * |__    |_     |    __|__    |
 * |______| |____|______|______|
 */
package edu.wpi.first3729.frc2013.Gamemode;

import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.Timer;

import edu.wpi.first3729.frc2013.Movement.*;
import edu.wpi.first3729.frc2013.Robot;
import edu.wpi.first3729.frc2013.utilities.Params;


/**
 *
 * @author teddy
 */
public class Autonomous extends GameMode {
    private Timer _timer;
    private int state = 0;

    /**
     *
     * @param drv
     * @param cam
     */
    public Autonomous(Drive drv, Shooter shoot, Robot robot) {
        super(robot);
        this._drive = drv;
        this._shooter = shoot;
    }
    //Will shoot three frisbees then move forward ish
    // well maybe...    
    public void setup() {
        this._timer = new Timer();
        _timer.stop();
        _timer.reset();
        _timer.start();
    }
    
    public void run() {
        if (this._timer.get() < 750) {
            state = 1;
        } else if (this._timer.get() < 1000) {
            state = 2;
        } else if (this._timer.get() < 1250) {
            state = 3;
        } else if (this._timer.get() < 1500) {
            state = 4;
        } else if (this._timer.get() < 1750) {
            state = 5;
        } else if (this._timer.get() < 2000) {
            state = 6;
        } else if (this._timer.get() < 2250) {
            state = 7;
        } else if (this._timer.get() < 12250) {
            state = 8;
        } else if (this._timer.get() < 12500) {
            state = 9;
        } else if (this._timer.get() < 12750) {
            state = 10;
        } else {
            state = 0;
        }
        
        switch (state) {
            case 1:
                this._shooter.shoot(Params.auto_shooterspeed);
                break;
            case 2:
                this._shooter.intake(1);
                break;
            case 3:
                //this._shooter.load(1);
                this._shooter.intake(-1);
                break;
            case 4:
                this._shooter.intake(1);
                //this._shooter.load(0);
                break;
            case 5:
                this._shooter.intake(-1);
                //this._shooter.load(1);
                break;
            case 6:
                this._shooter.intake(1);
                break;
            case 7:
                this._shooter.shoot(0);
                //this._shooter.load(0);
                this._drive.tankdrive(-Params.auto_drivespeed, -Params.auto_drivespeed);
                break;
            case 8:
                this._drive.tankdrive(0, 0);
                this._shooter.intake(-1);
                break;
            case 9:
                this._shooter.intake(0);
                break;
            default:
                //this._shooter.load(0);
                this._shooter.shoot(0);
                this._drive.tankdrive(0, 0);
        }
    }
}
