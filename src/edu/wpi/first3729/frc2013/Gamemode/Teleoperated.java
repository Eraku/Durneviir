/*  ______ ______ ______ ______
 * |__    |      |__    |  __  |
 * |__    |_     |    __|__    |
 * |______| |____|______|______|
 */
package edu.wpi.first3729.frc2013.Gamemode;

import edu.wpi.first3729.frc2013.Movement.*;
import edu.wpi.first3729.frc2013.Robot;
import edu.wpi.first3729.frc2013.inputs.*;
import edu.wpi.first3729.frc2013.utilities.*;

/**
 *
 * @author teddy
 */
public class Teleoperated extends GameMode {
       /*private double x = 0.0, y = 0.0, z = 0.0, left = 0.0, right = 0.0, scale_factor = 0.0;
    private boolean polarity = false, net_down = false, net_up = false, intake = false, bridge_down = false, bridge_up = false;
    private boolean shoot_on = false, shoot_off = false, lift_on = false, lift_off = false;*/
    public int drive_mode;
    private Input _input_manager;
    private Drive drive;
    private double x = 0.0, y = 0.0, left = 0.0, right = 0.0, scalefactor = 0.0;
    private boolean polarity = false;
           
    public Teleoperated(Input imanager, Drive drv, Robot robot) {
        super(robot);
        this._input_manager = imanager;
        this._drive = drv;
    }

    Teleoperated(Robot robot) {
        super(robot);
    }

    /**
     * @brief Initializes manipulator, locks drive, locks input
     */
    public void setup() {
    }
    public void drivemode() {
        switch (drive_mode) {
            
        }
        //case Input.arcade:
    }
    public void init() {
        this._input_manager.setmode(Input.arcadecontroller);
        this._drive.locked();
    }
    public void run() {
        // Update input fields
        this.getInput();
        // Drive robot based on drive mode
        switch (this._input_manager.getmode()) {
            case Input.arcade:
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
        drive_mode = this._input_manager.getmode();
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

        switch (drive_mode) {
            default:
            case Input.arcade:  // Arcade drive, two joysticks
            case Input.arcadecontroller:  // Arcade drive w/ flight controller and joystick - buttons same
//                this.intake = this._input_manager.checkbutton(0, 1);
//                this.lift_on = this._input_manager.checkbutton(0, 3);
//                this.lift_off = this._input_manager.checkbutton(0, 2);
//                this.shoot_on = this._input_manager.checkbutton(0, 4);
//                this.shoot_off = this._input_manager.checkbutton(0, 5);
//                this.bridge_down = this._input_manager.checkbutton(1, 4);
//                this.bridge_up = this._input_manager.checkbutton(1, 5);
//                this.net_up = this._input_manager.checkbutton(1, 3);
//                this.net_down = this._input_manager.checkbutton(1, 2);
//                this.polarity = this._input_manager.checkbutton(2, 2);
                break;
            case Input.tank:  // Tank drive and
            case Input.locked:  // Locked controls, nothing to do here
//                this.intake = this.shoot_on = this.shoot_off = this.bridge_up = this.bridge_down = this.lift_on = this.lift_off = this.net_up = this.net_down = false;
                break;
        }
                // Button 6, arcade drive 2 joysticks
        if (this._input_manager.checkbutton(1, 6)) {
            this._input_manager.setmode(Input.arcade);
        }
        // Button 7, arcade drive 1 joystick 1 controller
        if (this._input_manager.checkbutton(1, 7)) {
            this._input_manager.setmode(Input.arcadecontroller);
        }
        // Button 10, tank drive 3 joysticks
        if (this._input_manager.checkbutton(1, 10)) {
            this._input_manager.setmode(Input.tank);
        }
        // Button 11, lock all controls
        if (this._input_manager.checkbutton(1, 11)) {
            this._input_manager.setmode(Input.locked);
        }
    }
}
    /**
     * @brief Runs FRCGameModeTeleoperated period
     * 
     * Runs FRCGameModeTeleoperated period
     */
        /* Update input fields
        this.getInput();
        
        // FRCDrive teh robot
        this._drive.arcadeDrive(this.x, this.y);

        /* If intake limit switch is hit, turn off intake relay
        if (!this.intake_limit.get()) {
            for (int i = 0; i < 10000; i++) { continue; }
            this._manipulator.intake(Relay.Value.kOff);
        }
        // Intake control   
        if (this.intake) {
            this._manipulator.intake(Relay.Value.kForward);
        }
        
        if (!this._manipulator.get_intake_sensor()) {
            this._manipulator.intake(false);
        }
        if (this.intake) {
            if (!this.shoot_on)
                this._manipulator.shoot(true);
            this._manipulator.intake(true);
        }

        // Gist of the above 2 'if' statements: when intake button pressed,
        // keep relay running until limit switch is pressed.

        // Toggle elevator
        if (lift_on) {
            this._manipulator.lift(true);
        } else if (lift_off) {
            this._manipulator.lift(false);
        }

        // Toggle shooter
        if (shoot_on) {
            this._manipulator.shoot(true);
        } else if (shoot_off) {
            this._manipulator.shoot(false);
        }

        // If up and down both requested, do nothing
        if (bridge_up && bridge_down) {
            this._manipulator.bridge(Relay.Value.kOff);
        }

        // Bridge relay control
        if (bridge_up && !this._manipulator.get_bridge_limit()) {
            this._manipulator.bridge(Relay.Value.kForward);
        } else if (bridge_down) {
            this._manipulator.bridge(Relay.Value.kReverse);
        } else {
            this._manipulator.bridge(Relay.Value.kOff);
        }

        // If up and down both requested, do nothing
        if (bridge_up && bridge_down) {
            this._manipulator.bridge(Relay.Value.kOff);
        }*/
        
        // Run drive and manipulator loops
//        this._drive.loop_periodic();
//        this._manipulator.loop_periodic();
///*  ______ ______ ______ ______
// * |__    |      |__    |  __  |
// * |__    |_     |    __|__    |
// * |______| |____|______|______|
// */
//package edu.first3729.frc2012;
//
///**
// * @file Teleoperated.java
// */
//import edu.wpi.first.wpilibj.DigitalInput;
//import edu.wpi.first.wpilibj.Relay;
//
///**
// *
// * @class Teleoperated @brief Class that runs robot during Teleoperated period
// *
// * Manages instances of Drive, Input, and Manipulator classes. Gets appropriate
// * input based on controller layout mode and sets outputs accordingly.
// */
//public class Teleoperated {
//
//    private Input _input_manager;
//    private Drive _drive;
//    private Manipulator _manip;
//    private DigitalInput bridge_limit, intake_sensor;
//    private double x = 0.0, y = 0.0, z = 0.0, left = 0.0, right = 0.0, scale_factor = 0.0;
//    private boolean polarity = false, net_down = false, net_up = false, intake = false, bridge_down = false, bridge_up = false;
//    private boolean shoot_on = false, shoot_off = false, lift_on = false, lift_off = false;
//
//    /**
//     * @param imanager instance of Input class passed from Robot
//     * @param drv instance of Drive class passed from Robot
//     * @param manip instance of Manipulator class passed from Robot
//     * @brief Constructor - takes subsystem classes passed from Robot
//     */
//    public Teleoperated(Input imanager, Drive drv, Manipulator manip) {
//        this._input_manager = imanager;
//        this._manip = manip;
//        this._drive = drv;
//        this.intake_sensor = new DigitalInput(Params.intake_sensor_digin_port);
//        this.bridge_limit = new DigitalInput(Params.bridge_limit_digin_port);
//    }
//
//    /**
//     * @brief Initializes manipulator, locks drive, locks input
//     */
//    public void init() {
//        this._input_manager.set_mode(Input.arcade_controller);
//        this._drive.lock_motors();
//        this._manip.init();
//    }
//
//    /**
//     * @brief Runs Teleoperated period Runs Teleoperated period
//     */
//    public void run() {
//        // Update input fields
//        this.getInput();
//        // Drive robot based on drive mode
//        switch (this._input_manager.get_mode()) {
//            case Input.mecanum:
//                this._drive.drive_mecanum(this.x, this.y, this.z);
//                break;
//            case Input.arcade_joy:
//            case Input.arcade_controller:
//                this._drive.drive_arcade(this.x, this.y);
//                break;
//            case Input.tank:
//                this._drive.drive_tank(this.left, this.right);
//                break;
//            case Input.locked:
//                this._drive.lock_motors();
//                break;
//        }
//
//        /* If intake limit switch is hit, turn off intake relay
//        if (!this.intake_limit.get()) {
//            for (int i = 0; i < 10000; i++) { continue; }
//            this._manip.intake(Relay.Value.kOff);
//        }
//        // Intake control   
//        if (this.intake) {
//            this._manip.intake(Relay.Value.kForward);
//        }
//        */
//        if (!this.intake_sensor.get()) {
//            this._manip.intake(false);
//        }
//        if (this.intake) {
//            if (!this.shoot_on)
//                this._manip.shoot(true);
//            this._manip.intake(true);
//        }
//
//        // Gist of the above 2 'if' statements: when intake button pressed,
//        // keep relay running until limit switch is pressed.
//
//        // Toggle elevator
//        if (lift_on) {
//            this._manip.lift(true);
//        } else if (lift_off) {
//            this._manip.lift(false);
//        }
//
//        // Toggle shooter
//        if (shoot_on) {
//            this._manip.shoot(true);
//        } else if (shoot_off) {
//            this._manip.shoot(false);
//        }
//
//        // If up and down both requested, do nothing
//        if (bridge_up && bridge_down) {
//            this._manip.bridge(Relay.Value.kOff);
//        }
//
//        // Bridge relay control
//        if (bridge_up && !this.bridge_limit.get()) {
//            this._manip.bridge(Relay.Value.kForward);
//        } else if (bridge_down) {
//            this._manip.bridge(Relay.Value.kReverse);
//        } else {
//            this._manip.bridge(Relay.Value.kOff);
//        }
//
//        // If up and down both requested, do nothing
//        if (bridge_up && bridge_down) {
//            this._manip.bridge(Relay.Value.kOff);
//        }
//
//        // Ditto for net
//        if (net_up) {
//            this._manip.lift_net(Relay.Value.kForward);
//        } else if (net_down) {
//            this._manip.lift_net(Relay.Value.kReverse);
//        } else {
//            this._manip.lift_net(Relay.Value.kOff);
//        }
//
//        if (net_up && net_down) {
//            this._manip.lift_net(Relay.Value.kOff);
//        }
//    }
