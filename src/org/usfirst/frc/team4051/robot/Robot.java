
package org.usfirst.frc.team4051.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.command.WaitCommand;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4051.robot.commands.ExampleCommand;
import org.usfirst.frc.team4051.robot.subsystems.ExampleSubsystem;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {
	public static ExampleSubsystem m_subsystem = new ExampleSubsystem();
	public static OI m_oi;
		
	Command m_autonomousCommand;
	SendableChooser<Command> m_chooser = new SendableChooser<>();
	MecanumDrive myRobot;
    Joystick leftStick;
    Joystick rightStick;
    //SpeedController collectorMotor;
    Victor LiftMotor1 = new Victor(4);
	Victor LiftMotor2 = new Victor(5);
	Talon CollectorMotor1 = new Talon(6);
	Talon CollectorMotor2 = new Talon(7);
	Spark OpenAndCloseMotor = new Spark(8);
	Compressor myComp = new Compressor(0);
	Solenoid openClose = new Solenoid(1);
	Solenoid closeOpen = new Solenoid(2);
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		m_oi = new OI();
		m_chooser.addDefault("Default Auto", new ExampleCommand());
		// chooser.addObject("My Auto", new MyAutoCommand());
		SmartDashboard.putData("Auto mode", m_chooser);
		myRobot = new MecanumDrive(new Victor(0), new Victor(1), new Victor(2), new Victor(3)); //PMW ports 0 & 1
		//CollectorMotor1.setInverted(true);
        leftStick = new Joystick(0); //USB port 0
        rightStick = new Joystick(1); //USB port 1
        myComp.start();
	}

	

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	double startTime;
    Command Command1;
	@Override
	public void autonomousInit() {
		m_autonomousCommand = m_chooser.getSelected();
		//Command1 = new WaitCommand();
		startTime = System.currentTimeMillis();
		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */
		// schedule the autonomous command (example)
		if (m_autonomousCommand != null) {
			m_autonomousCommand.start();
		}
	}
	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		//MotorSafetyHelper.checkMotors();
		if(System.currentTimeMillis() - startTime < 500) {
			OpenAndCloseMotor.set(-1);
		}
		if(System.currentTimeMillis() - startTime < 5000) {
			myRobot.driveCartesian(-0.6, -0.6, 0);
		}
		if(System.currentTimeMillis() - startTime > 10000) {
			OpenAndCloseMotor.set(1);
		}
		Scheduler.getInstance().run();
		
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
        myComp.start();
		if (m_autonomousCommand != null) {
			m_autonomousCommand.cancel();
		}
	}
	
	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		//myRobot.tankDrive(leftStick.getY(), rightStick.getY());
		myRobot.driveCartesian(leftStick.getX(), -leftStick.getY(),  leftStick.getZ());
		LiftMotor1.set(rightStick.getY());
		LiftMotor2.set(rightStick.getY());
		
		if(leftStick.getRawButton(2)) {
			//OpenAndCloseMotor.set(1);
			openClose.set(true);
		}else {
			if(leftStick.getRawButton(1)) {
				closeOpen.set(true);
			}else {
				openClose.set(false);
				closeOpen.set(false);
			}
		}
		
		if(rightStick.getRawButton(2)) {
			CollectorMotor1.set(1);
			CollectorMotor2.set(1);
		}else {
			if(rightStick.getRawButton(1)) {
				CollectorMotor1.set(-1);
				CollectorMotor2.set(-1);
			}else {
				CollectorMotor1.set(0);
				CollectorMotor2.set(0);
			}
		}
		
		
		
		
//		AnalogGyro myGyro = new AnalogGyro(999);	
//		myGyro.reset();
//		double angleValue = myGyro.getAngle();
//		System.out.println(angleValue);

		//Scheduler.getInstance().run();
	}

	//Anything bellow this line is usually useless.

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}
}
