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
    private Teleoperated teleop;
    private Autonomous auto;
//    private Manipulator manip;
    private AxisCamera camera;
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        // Initialization timer
        Timer init_timer = new Timer();
        
        // Print banner
        System.out.println(" ______ ______ ______ ______\n|__    |      |__    |  __  |\n|__    |_     |    __|__    |\n|______| |____|______|______|\n");
 
//        // For the lulz
//        System.out.println("This robot complies with Asimov's Laws of Robotics:");
//        System.out.println("\t~> 1. A robot may not injure a human being or,\n\t      through inaction, allow a human being to come to harm.");
//        System.out.println("\t~> 2. A robot must obey the orders given to it by human beings,\n\t      except where such orders would conflict with the First Law.");
//        System.out.println("\t~> 3. A robot must protect its own existence as long as\n\t      such protection does not conflict with the First or Second Laws.");
        
        // Initialize stuff
        System.out.println("=== INITIALIZING ROBOT ===");
        
        // Start timer
        init_timer.start();
        this.input_manager = new Input();
        this.input_manager.setmode(Params.default_drive_mode);
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
        init_timer.stop();
        
        System.out.println("=== DONE IN " + init_timer.get() * .001 + " MS ===");

    }

public void disabledInit() {
        System.out.println("Going disabled.");
    }

    public void disabledPeriodic() {
        // Nothing
    }

    public void teleopInit() {
        System.out.println("Going teleoperated.");
        teleop.init();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        this.getWatchdog().feed();
        teleop.run();
    }

    public void teleopContinuous() {
    }
    
    public void autonomousInit()
    {
        System.out.println("Going autonomous.");
        auto.init();
    }
    
    public void autonomousPeriodic()
    {
        this.getWatchdog().feed();
        auto.run();
    }
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
//public class Robot extends IterativeRobot {
    
//    private GameMode _mode = null;
    
//    private Timer auto_timer;
    
//    private int state = 0;

    /**
     * This function is run when the robot is first started up
     */
//    public void robotInit() {
//        // Initialization timer
//       // Timer init_timer = new Timer();
//        
//        // Print banner
//        System.out.println(" ______ ______ ______ ______\n|__    |      |__    |  __  |\n|__    |_     |    __|__    |\n|______| |____|______|______|\n");
// 
//        // For the lulz
//        System.out.println("This robot complies with Asimov's Laws of Robotics:");
//        System.out.println("\t~> 1. A robot may not injure a human being or,\n\t      through inaction, allow a human being to come to harm.");
//        System.out.println("\t~> 2. A robot must obey the orders given to it by human beings,\n\t      except where such orders would conflict with the First Law.");
//        System.out.println("\t~> 3. A robot must protect its own existence as long as\n\t      such protection does not conflict with the First or Second Laws.");
//    }
//
//    public void disabledInit() {
//        System.out.println("Going disabled.");
//        
//        // Disable the watchdog, because we don't need it
//        this.getWatchdog().setEnabled(false);
//        
//        // Go disabled
//        this._mode = GameMode.to_disabled(this._mode, this);
//    }
//
//    public void disabledPeriodic() {
//        this._mode.loop_periodic();
//    }
//    
//    public void disabledContinuous() {
//        this._mode.loop_continuous();
//    }
//
//    public void teleopInit() {
//        System.out.println("Going teleoperated.");
//        
//        // WAtchdog expiration
//        this.getWatchdog().setExpiration(5);
//        
//        // Enable the watchdog
//        this.getWatchdog().setEnabled(true);
//        
//        // Go teleoperated
//        this._mode = FRCGameMode.to_teleoperated(this._mode, this);
//    }
//
//    /**
//     * This function is called periodically during teleoperated mode
//     */
//    public void teleopPeriodic() {
//        // Run one loop
//        this._mode.loop_periodic();
//        
//        // Feed the watchdog
//        this.getWatchdog().feed();
//    }
//
//    public void teleopContinuous() {
//        this._mode.loop_continuous();
//    }
//    
//    public void autonomousInit() {
//        System.out.println("Going autonomous.");
//
//        auto_timer = new Timer();
//        
//        // Up watchdog expiration for autonomous
//        this.getWatchdog().setExpiration(15);
//        
//        // Disable the watchdog
//        this.getWatchdog().setEnabled(false);
//        
//        // Initialize autonomous
//        this._mode = FRCGameMode.to_autonomous(this._mode, this);
//        this._mode.init();
//    }
//    
//    public void autonomousPeriodic() {
//        // Run a loop
//        this._mode.loop_periodic();
//        
//        // Feed the watchdog
//        //this.getWatchdog().feed();
//    }
//    
//    public void autonomousContinuous() {
//        this._mode.loop_continuous();
//    }
//}