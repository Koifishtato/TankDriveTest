package org.usfirst.frc.team5940.robot;

import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team5940.robot.Components.Camera;

import org.usfirst.frc.team5940.robot.Components.Joy;
import org.usfirst.frc.team5940.robot.Components.MotorGroup;
import org.usfirst.frc.team5940.robot.Components.*;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;

/**
 * This is a demo program showing the use of the RobotDrive class, specifically
 * it contains the code necessary to operate a robot with tank drive.
 *
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SampleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 *
 * WARNING: While it may look like a good choice to use for your code if you're
 * inexperienced, don't. Unless you know what you are doing, complex code will
 * be much more difficult under this system. Use IterativeRobot or Command-Based
 * instead if you're new.
 */
public class Robot extends SampleRobot {
	public Thread camera;
	public Robot() {
		
	}

	/**
	 * Runs the motors with tank steering.
	 */
	public void operatorControl() {
		/**MotorGroup Motors = new MotorGroup();
		Joy Joys = new Joy(0);
		double[] pos;
		while (isOperatorControl() && isEnabled()) {
			pos = Joys.get(0, 6);
			Motors.goL(pos[1]);
			Motors.goR(pos[5]);
		
		}
		*/
		
		this.camera = new Thread(new Camera(this));
		MotorGroup Motors = new MotorGroup();
		Joy Joys = new Joy(0);
		double[] pos;
		//speed [1] is Left, and [0] is Right Motor group
		double[] speeds={0,0};
		double pro=0.75;
		this.camera.start();
		while (isOperatorControl() && isEnabled() ){
			
			pos=Joys.get(0, 6);
			
			if((pos[1]>0.1||pos[1]<-0.1)&&(0.1>pos[4]&&pos[4]>-0.1)){
				speeds[0]=pos[1];
				speeds[1]=pos[1];
				SmartDashboard.putString("Status ", "Foward");
			}
			else if((pos[4]>0.1||pos[4]<-0.1)&&(0.1>pos[1]&&pos[1]>-0.1)){
				speeds[0]=pos[4];
				speeds[1]=-pos[4];
				SmartDashboard.putString("Status ", "Rotate");
			}
			else if((pos[4]>0.1||pos[4]<-0.1)&&(0.1<pos[1]||pos[1]<-0.1)){
				if (pos[4]<-0.1){
					if(pos[1]>-pro||pos[1]<pro){
						speeds[0]=(pos[1]);
						speeds[1]=(pos[1])-(pos[4]/2);
						SmartDashboard.putString("Status ", "Turn Right Fast");
					}
					else{
						speeds[0]=(pos[1]);
						speeds[1]=(pos[1])-(pos[4]/10);
						SmartDashboard.putString("Status ", "Turn Right Slow");
					}
				}
				else if(pos[4]>0.1){
					if(pos[1]>-pro||pos[1]<pro){
						speeds[0]=(pos[1])-(-pos[4]/2);
						speeds[1]=(pos[1]);
						SmartDashboard.putString("Status ", "Turn Left Fast");
					}
					else{
						speeds[0]=(pos[1])-(-pos[4]/10);
						speeds[1]=(pos[1]);
						SmartDashboard.putString("Status ", "Turn Left Slow");
					}
				}
			}
			else{
				speeds[0]=0;
				speeds[1]=0;
				SmartDashboard.putString("Status ", "Stop");
			}
			Motors.goL(speeds[1]);
			Motors.goR(speeds[0]);
			
	}
		
	}
}
	
