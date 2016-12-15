package org.usfirst.frc.team5940.robot.Components;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Joystick;

public class Joy {
	public Joystick[] Controllers = new Joystick[6];
	public Joy(int port){
		this.Controllers[0]=new Joystick(port);
	}
	public Joy(){
		this.Controllers[0]=new Joystick(0);
	}
	public double[] get(int port,int axes){
		double out[]= new double[axes];
		for(int axis=0; axis!=axes; axis++){
			out[axis]=this.Controllers[port].getRawAxis(axis);
		}
		
		return out;
	}
	public boolean get(int button){
		return(this.Controllers[0].getRawButton(button));
	}
}
