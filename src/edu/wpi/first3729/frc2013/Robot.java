/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
/*  ______ ______ ______ ______
 * |__    |      |__    |  __  |
 * |__    |_     |    __|__    |
 * |______| |____|______|______|
 */
package edu.wpi.first3729.frc2013;


import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.camera.AxisCamera;

import edu.wpi.first3729.frc2013.Gamemode.*;
import edu.wpi.first3729.frc2013.Movement.*;
import edu.wpi.first3729.frc2013.inputs.*;
import edu.wpi.first3729.frc2013.utilities.Params;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
    
    private Input input_manager;
    private DriverStationLCD screen;
    private Drive drive;
    private GameMode _mode;
    private Teleoperated teleop;
    private Autonomous auto;
//    private Climber climb;
//    private Shooter shoot;
    private AxisCamera camera;
    private DriverStationLCD ds = DriverStationLCD.getInstance();
    /* Line 1 = initalizing reports then current Game mode
     * Line 2 = current Drive mode
     * Line 3 = current Shooter speed - TODO
     */
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        // Initialization timer
        Timer setuptimer = new Timer();       
        // Print banner
        System.out.println(" ______ ______ ______ ______\n|__    |      |__    |  __  |\n|__    |_     |    __|__    |\n|______| |____|______|______|\n");
        // For the lulz
        System.out.println("This robot complies with Asimov's Laws of Robotics:");
        System.out.println("\t~> 1. A robot may not injure a human being or,\n\t      through inaction, allow a human being to come to harm.");
        System.out.println("\t~> 2. A robot must obey the orders given to it by human beings,\n\t      except where such orders would conflict with the First Law.");
        System.out.println("\t~> 3. A robot must protect its own existence as long as\n\t      such protection does not conflict with the First or Second Laws.");
        // Initialize stuff
        ds.println(DriverStationLCD.Line.kUser1, 1,"=== INITIALIZING ROBOT ===");       
        // Start timer
        setuptimer.start();
        this.input_manager = new Input();
        this.input_manager.setdrivemode(Params.default_drive_mode);
        this.drive = new Drive();
        this.drive.locked();
        this.teleop = new Teleoperated(input_manager, drive, null);
        this.auto = new Autonomous(drive, camera, null);
        this.camera = AxisCamera.getInstance(Params.cameraIP);
        this.camera.writeResolution(Params.cameraresolution);
        this.camera.writeMaxFPS(Params.cameraFPS);       
        // Set up Watchdog
        this.getWatchdog().setExpiration(Params.default_watchdog_time);        
        // Stop timer
        setuptimer.stop();      
        ds.println(DriverStationLCD.Line.kUser2, 1,"=== DONE IN " + setuptimer.get() * .001 + " MS ===");
    }

    public void disabledInit() {
        ds.println(DriverStationLCD.Line.kUser1, 1,"Going disabled.");
        this._mode = GameMode.todisabled(this._mode, this);
    }
    public void disabledPeriodic() {
        // Nothing
    }

    public void teleopInit() {
        ds.println(DriverStationLCD.Line.kUser1, 1,"Going teleoperated.");
        this._mode = GameMode.toteleoperated(this._mode, this);
        this._mode.setup();
    }
    public void teleopPeriodic() {
        this.getWatchdog().feed();
        this.teleop.run();
    }
    
    public void autonomousInit() {
        ds.println(DriverStationLCD.Line.kUser1, 1,"Going autonomous.");
        this._mode = GameMode.toautonomous(this._mode, this);
        this._mode.setup();
    }   
    public void autonomousPeriodic() {
        this.getWatchdog().feed();
        this.auto.run();
    }

    public void testPeriodic() {
    } 
}