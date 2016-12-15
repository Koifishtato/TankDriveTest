package org.usfirst.frc.team5940.robot;
import java.lang.Math;
import edu.wpi.first.wpilibj.DigitalInput;

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
	public static DigitalInput ballDetector = new DigitalInput(9);
	public void operatorControl() {
		MotorGroup Motors = new MotorGroup();
		MotorGroup Roller = new MotorGroup(0);
		Joy Joys = new Joy(0);
		double[] pos;
		while (isOperatorControl() && isEnabled()) {
			pos = Joys.get(0, 6);
			if(Math.abs(pos[1])>0.1||Math.abs(pos[5])>0.1){
				Motors.goL(-(pos[1]));
				Motors.goR(-(pos[5]));
			}
			else{
				Motors.goL(0);
				Motors.goR(0);
			}
			if(pos[3]>0.4){
				Roller.go(-pos[3]/2, 0);
			}
			else if(pos[2]>0.4 & getCorrectedDetector()){
				Roller.go(pos[2]/2, 0);
			}
			else{
				Roller.go(0, 0);
			}
			SmartDashboard.putBoolean("Sensor", getCorrectedDetector());
		}
		
		
		/**this.camera = new Thread(new Camera(this));
		MotorGroup Motors = new MotorGroup();
		Joy Joys = new Joy(0);
		double[] pos;
		//speed [1] is Left, and [0] is Right Motor group
		double[] speeds={0,0};
		int [] turn = {1,0};
		this.camera.start();
		while (isOperatorControl() && isEnabled() ){
			
			pos=Joys.get(0, 6);
			
			if((pos[turn[0]]>0.1||pos[turn[0]]<-0.1)&&(0.1>pos[turn[1]]&&pos[turn[1]]>-0.1)){
				speeds[0]=-pos[turn[0]];
				speeds[1]=-pos[turn[0]];
				SmartDashboard.putString("Status ", "Foward");
			}
			else if((pos[turn[1]]>0.1||pos[turn[1]]<-0.1)&&(0.1>pos[turn[0]]&&pos[turn[0]]>-0.1)){
				speeds[0]=-pos[turn[1]];
				speeds[1]=pos[turn[1]];
				SmartDashboard.putString("Status ", "Rotate");
			}
			else if((pos[turn[1]]>0.1||pos[turn[1]]<-0.1)&&(0.1<pos[turn[0]]||pos[turn[0]]<-0.1)){
				if (pos[turn[1]]<-0.1){
					if(pos[turn[0]]>-pro||pos[turn[0]]<pro){
						speeds[0]=(pos[turn[0]]);
						speeds[1]=(pos[turn[0]])-(pos[turn[1]]/2);
						SmartDashboard.putString("Status ", "Turn Right Fast");
					}
					else{
						speeds[0]=(pos[turn[0]]);
						speeds[1]=(pos[turn[0]])-(pos[turn[1]]/10);
						SmartDashboard.putString("Status ", "Turn Right Slow");
					}
					speeds[0]=(-pos[turn[0]]);
					speeds[1]=(-pos[turn[0]])+(pos[turn[1]]*(Math.abs(pos[turn[0]])-0.2));
					SmartDashboard.putNumber("div ", pos[turn[1]]/Math.abs(pos[turn[0]]-0.1));
				}
				else if(pos[turn[1]]>0.1){
					if(pos[turn[0]]>-pro||pos[turn[0]]<pro){
						speeds[0]=(pos[turn[0]])-(-pos[turn[1]]/2);
						speeds[1]=(pos[turn[0]]);
						SmartDashboard.putString("Status ", "Turn Left Fast");
					}
					else{
						speeds[0]=(pos[turn[0]])-(-pos[turn[1]]/10);
						speeds[1]=(pos[turn[0]]);
						SmartDashboard.putString("Status ", "Turn Left Slow");
					}
					speeds[0]=(-pos[turn[0]])-(pos[turn[1]]*(Math.abs(pos[turn[0]])-0.2));
					speeds[1]=(-pos[turn[0]]);
					SmartDashboard.putNumber("div ", pos[turn[1]]/Math.abs(pos[turn[0]]-0.1));
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
	*/	
	}
	boolean getCorrectedDetector() {
		return ballDetector.get();
	}
}
	
