/*  ______ ______ ______ ______
 * |__    |      |__    |  __  |
 * |__    |_     |    __|__    |
 * |______| |____|______|______|
 */
package edu.wpi.first3729.frc2013.Movement;

import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;

import edu.wpi.first3729.frc2013.inputs.*;
import edu.wpi.first3729.frc2013.utilities.*;

/**
 *
 * @author teddy
 *
 * Has both shooting and loading mechanisms
 * 
 */
public class Shooter {
    private Victor _shooter;
    private DigitalInput intakelimit0, intakelimit1;
    protected Relay  _intake, _angleadj;
    private Input _input_manager;
    protected JoystickAttack3 _input;
    private DriverStationLCD ds = DriverStationLCD.getInstance(); 
    private double shooterspeed = .65;
    private boolean shooter_state;
    private int angleadj_state, intake_state;
//    private Timer shootertimer = new Timer();
    
    public Shooter() {
        this._input = new JoystickAttack3(Params.shooter_joy);
        this.intakelimit0 = new DigitalInput(Params.intake0_limitswitch_port);
        this.intakelimit1 = new DigitalInput(Params.intake1_limitswitch_port);
        this._intake = new Relay(Params.intake_relayport);
        this._angleadj = new Relay(Params.angleadj_relayport);
        this._shooter = new Victor(Params.shooterport);
    }
    
    public void setup(Input imanager) {
        this._input_manager = imanager;
        this._intake.setDirection(Relay.Direction.kBoth);
        this._angleadj.setDirection(Relay.Direction.kBoth);
        this.intake(0);
        this.adjangle(0);
        this.shoot(0.0);
    }
    public void run() {
        if (Params.testing){System.out.println("in shooter.run");}
        this.getinput();
        this.adjangle(this.angleadj_state);
        this.intake(this.intake_state);
        this.shoot(this.shooter_state);
    }
    public void getinput() {       
        if (Params.testing){System.out.println("in shooter.getInput()");}
        if (this._input_manager.checkbutton(0, 5)) {
            shooter_state = true;
            //shootertimer.start();
        } else if (this._input_manager.checkbutton(0, 4)) {
            shooter_state = false;
        }
//        if (this._input_manager.checkbutton(0, 8)) {
//            shooterspeed = shooterspeed - .05;
//        } else if (this._input_manager.checkbutton(0, 9)) {
//            shooterspeed = shooterspeed + .05;
//       } else {}
               
        if (this._input_manager.checkbutton(0, 8)) {
            intake_state = -1;
        } else if (this._input_manager.checkbutton(0, 9)) {
            intake_state = 1;
        } else { intake_state = 0; }
//        if (this._input_manager.checkbutton(0, 1) && this.intakelimit0.get()) {
//            intake_state = 1;
//        } else if (this._input_manager.checkbutton(0, 1) && this.intakelimit1.get()) {
//            intake_state = -1;
//        } else {
//            while(!intakelimit0.get() && !intakelimit1.get()) {}
//            intake_state = 0;
//        }
        if (Params.testing){System.out.println("joy button 1: " + this._input_manager.checkbutton(0, 1));
        System.out.println("intake0: " + this.intakelimit0.get());
        System.out.println("intake1: " + this.intakelimit1.get());}

        if (this._input_manager.checkbutton(0, 3)) {
            this.angleadj_state = 1;
        } else if (this._input_manager.checkbutton(0, 2)) {
            this.angleadj_state = -1;
        } else {
            this.angleadj_state = 0;
        }
        ds.println(DriverStationLCD.Line.kUser3, 1, "Current Shooter speed: " + shooterspeed);
        ds.updateLCD();
    }
    
    public void adjangle(Relay.Value state) {
        this._angleadj.set(state);
    }
    public void adjangle(int state) {
        if (Params.testing){System.out.println("adjangle" + angleadj_state + " ");}
        switch (state){
            case 1:
                this.adjangle(Relay.Value.kForward);
                break;
            case 0:
                this.adjangle(Relay.Value.kOff);
                break;
            case -1:
                this.adjangle(Relay.Value.kReverse);
                break;
            default:
                this.adjangle(Relay.Value.kOff);
                break;
        }
    }
    
    public void shoot(boolean state) {
        if (Params.testing){System.out.println("shoot state: " + shooter_state + " Shooterspeed: " + shooterspeed);}
        if (state) {
//            for (double st = shootertimer.get(); st < 6.0; st = shootertimer.get()) {
//                this.shoot(shooterspeed);
//                System.out.println("Shooter timer: " + shootertimer.get() + " ");
//            }
            this.shoot(shooterspeed);
        } else {
            this.shoot(0.0);
        }
        //shootertimer.stop();
        //System.out.println("Shooter timer: " + shootertimer.get() + " ");
        //shootertimer.reset();
    }
    public void shoot(double speed) {
        this._shooter.set(speed);
    }
    
    public void intake(Relay.Value state) {
        if (Params.testing){System.out.println("intake relay value: " + state);}
        //this._intake.set(Relay.Value.kForward);
        //this._intake.set(Relay.Value.kReverse);
        this._intake.set(state);
    }
    public void intake(int state) {
        if (Params.testing){System.out.println("intake_state: " + intake_state);}
        switch (state){
            case 1:
                if (Params.testing){System.out.println("in shooter.intake, case 1");}
                this.intake(Relay.Value.kForward);
                break;
            case 0:
                if (Params.testing){System.out.println("in shooter.intake, case 0");}
                this.intake(Relay.Value.kOff);
                break;
            case -1:
                if (Params.testing){System.out.println("in shooter.intake, case -1");}
                this.intake(Relay.Value.kReverse);
                break;
            default:
                this.intake(Relay.Value.kOff);
                break;
        }
    }    
}
//        this.intake_sensor = new DigitalInput(Params.intake_sensor_digin_port);
        /* If intake limit switch is hit, turn off intake relay
        if (!this.intake_limit.get()) {
            for (int i = 0; i < 10000; i++) { continue; }
            this._manipulator.intake(Relay.Value.kOff);
            * */