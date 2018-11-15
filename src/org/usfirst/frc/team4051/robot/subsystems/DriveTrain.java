package org.usfirst.frc.team4051.robot.subsystems;

import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import org.usfirst.frc.team4051.robot.OI;

/**
 *
 */

public class DriveTrain extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	MecanumDrive myRobot = new MecanumDrive(driveMotorRF, driveMotorRR, driveMotorLF, driveMotorLR);
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    
    // REMINDER: Might need to change the ports.
    private static Victor 
    driveMotorRF = new Victor(0),
    driveMotorRR = new Victor(1),
    driveMotorLF = new Victor(2),
    driveMotorLR = new Victor(3);
    
    public void teleopDrive() {
    	OI myOI = new OI();
    	myRobot.driveCartesian(myOI.rightStick.getX(), myOI.rightStick.getY(), myOI.rightStick.getZ());
    }

    public void autonomousDrive() {
    
    }
}

