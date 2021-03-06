/*  ______ ______ ______ ______
 * |__    |      |__    |  __  |
 * |__    |_     |    __|__    |
 * |______| |____|______|______|
 */
package edu.wpi.first3729.frc2013.Gamemode;

import edu.wpi.first.wpilibj.DriverStationLCD;

import edu.wpi.first3729.frc2013.Movement.*;
import edu.wpi.first3729.frc2013.inputs.*;
import edu.wpi.first3729.frc2013.utilities.*;

/**
 *
 * @author teddy
 */
public class Teleoperated {
    public int drive_mode;
    private Input _input_manager;
    private Drive _drive;
    private Shooter _shooter;
    private double x = 0.0, y = 0.0, scalefactor = 0.0;
    private boolean polarity = false;
    private DriverStationLCD ds = DriverStationLCD.getInstance();
           
    public Teleoperated(Input imanager, Shooter shoot, Drive drv) {
        this._input_manager = imanager;
        this._drive = drv;
        this._shooter = shoot;
    }

    /**
     * @brief locks drive, locks input
     */
    public void setup() {
        this._input_manager.setdrivemode(Params.default_drive_mode);
        this._drive.setup();
        this._drive.locked();
        this._shooter.setup(_input_manager);
    }
    public void changedrivemode() {
        // Button 6, arcade drive 2 joysticks
        if (this._input_manager.checkbutton(0, 6)) {
            this._input_manager.setdrivemode(Input.arcade);
        }
        // Button 7, arcade drive 1 joystick 1 controller
        else if (this._input_manager.checkbutton(0, 7)) {
            this._input_manager.setdrivemode(Input.arcadecontroller);
        }
        // Button 10, tank drive 3 joysticks
        else if (this._input_manager.checkbutton(0, 10)) {
            this._input_manager.setdrivemode(Input.tank);
        }
        // Button 11, lock all controls
        else if (this._input_manager.checkbutton(0, 11)) {
            this._input_manager.setdrivemode(Input.locked);
        } else {
            this._input_manager.setdrivemode(Params.default_drive_mode);
        }
        ds.println(DriverStationLCD.Line.kUser2, 1, "Drive mode: " + drive_mode);
        ds.updateLCD();
    }

    public void run() {
        if (Params.testing){System.out.println("in teleop.run");}
        // Update input fields
        this.getInput();
        this._drive.run();
        this._shooter.run();
    }
    public void getInput() {
        this.changedrivemode();
        drive_mode = this._input_manager.getdrivemode();
        if (this._input_manager.checkbutton(2, 2)) {
            this.scalefactor = Params.drive_creep_scale_factor;
        } else {
            this.scalefactor = 1.0;
        }
        this.x = this._input_manager.get_x() * scalefactor;
        if (polarity) {
            this.y = this._input_manager.get_y() * scalefactor * -1.0;
        }
        else {
            this.y = this._input_manager.get_y() * scalefactor;
        }
        if (Params.testing){System.out.println("X: " + _drive.x + ", Y: " + _drive.y);}
        //this.polarity = this._input_manager.checkbutton(2, 2);
    }
}