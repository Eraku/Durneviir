/*  ______ ______ ______ ______
 * |__    |      |__    |  __  |
 * |__    |_     |    __|__    |
 * |______| |____|______|______|
 */
package edu.wpi.first3729.frc2013.Gamemode;

import edu.wpi.first.wpilibj.DriverStationLCD;

import edu.wpi.first3729.frc2013.Movement.*;
import edu.wpi.first3729.frc2013.Robot;
import edu.wpi.first3729.frc2013.inputs.*;
import edu.wpi.first3729.frc2013.utilities.*;

/**
 *
 * @author teddy
 */
public class Teleoperated extends GameMode {
    public int drive_mode;
    private Input _input_manager;
    private double x = 0.0, y = 0.0, left = 0.0, right = 0.0, scalefactor = 0.0;
    private boolean polarity = false;
    private DriverStationLCD ds = DriverStationLCD.getInstance();
           
    public Teleoperated(Input imanager, Shooter shoot, Drive drv, Robot robot) {
        super(robot);
        this._input_manager = imanager;
        this._drive = drv;
        this._shooter = shoot;
    }

    /**
     * @brief locks drive, locks input
     */
    public void setup() {
        this._input_manager.setdrivemode(Input.arcadecontroller);
        this._drive.locked();
        this.changedrivemode();
        this._shooter.setup();
    }
    public void changedrivemode() {
        // Button 6, arcade drive 2 joysticks
        if (this._input_manager.checkbutton(1, 6)) {
            this._input_manager.setdrivemode(Input.arcade);
        }
        // Button 7, arcade drive 1 joystick 1 controller
        if (this._input_manager.checkbutton(1, 7)) {
            this._input_manager.setdrivemode(Input.arcadecontroller);
        }
        // Button 10, tank drive 3 joysticks
        if (this._input_manager.checkbutton(1, 10)) {
            this._input_manager.setdrivemode(Input.tank);
        }
        // Button 11, lock all controls
        if (this._input_manager.checkbutton(1, 11)) {
            this._input_manager.setdrivemode(Input.locked);
        }
        ds.println(DriverStationLCD.Line.kUser2, 1, "Drive mode: " + drive_mode);
    }

    public void run() {
        // Update input fields
        this.getInput();
        // Drive robot based on drive mode
        switch (this._input_manager.getdrivemode()) {
            case Input.arcade:
                this._drive.arcadedrive(this.x, this.y);
                break;
            case Input.arcadecontroller:
                this._drive.arcadedrive(this.x, this.y);
                break;
            case Input.tank:
                this._drive.tankdrive(this.left, this.right);
                break;
            case Input.locked:
                this._drive.locked();
                break;
        }
    }
    public void getInput() {
        drive_mode = this._input_manager.getdrivemode();
        if (this._input_manager.gettwist(1) > 0) {
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
        System.out.println("X: " + this.x + ", Y: " + this.y);
        this.polarity = this._input_manager.checkbutton(2, 2);
    }
}