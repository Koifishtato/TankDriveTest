package org.usfirst.frc.team5940.robot.Components;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.wpilibj.CANTalon;

public class MotorGroup {
	public CANTalon[] talons=new CANTalon[5];
	public boolean inverted = false;
	
	public MotorGroup(int M1, int M2){
		// Motors Def
		this.talons[0] = new CANTalon(M1);
		this.talons[1] = new CANTalon(M2);
	}
	
	public MotorGroup(int M1){
		this.talons[0] = new CANTalon(M1);
	}
	
	public MotorGroup(){
		this.talons[0] = new CANTalon(1);
		this.talons[1] = new CANTalon(2);
		this.talons[2]= new CANTalon(3);
		this.talons[3]= new CANTalon(4);
		
	}

	// Right Motors Speed set
	public void goR(double speed) {
		if (this.inverted)
			speed*=-1;
		this.talons[0].set(-speed);
		this.talons[1].set(-speed);
		SmartDashboard.putNumber("R Motor Speed: ", speed);
	}

	// Left Motors Speed Set
	public void goL(double speed) {
		if (this.inverted)
			speed*=-1;
		this.talons[2].set(speed);
		this.talons[3].set(speed);
		SmartDashboard.putNumber("L Motor Speed: ", speed);
	}
}