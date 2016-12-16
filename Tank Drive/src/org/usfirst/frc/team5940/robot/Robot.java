package org.usfirst.frc.team5940.robot;
import java.lang.Math;
import java.util.Random;
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

public class Robot extends SampleRobot {
	public Thread camera;
	public Thread rand;
	public boolean istank=false;
	public boolean iswest=true;
	public Joy Joys = new Joy(0);
	public Timer time = new Timer();
	public Robot() {
		
	}

	/**
	 * Runs the motors with tank steering.
	 */
	//This tells the code "This is a ball sensor on port 9"
	public static DigitalInput ballDetector = new DigitalInput(9);
	
	//This is the Operator Control code. This runs when we start the robot.
	public void operatorControl() {
		//This loops until 
		while(this.isOperatorControl()&& this.isEnabled()){
		SmartDashboard.putBoolean("IsTank", this.istank);
		if(this.istank){
			drivetank();
		}
		else
			driveWest();
		}
		time.delay(0.5);
	}
	boolean getCorrectedDetector() {
		return ballDetector.get();
	}
	public void switchy(){
			this.istank=!this.istank;
			this.iswest=!this.iswest;
	}
	//This is a Method to run The tank drive code.
	public void drivetank(){
		//This tells the code to make a motor group called Motors. This is where the wheel motors are stored.
		MotorGroup Motors = new MotorGroup();
				
		//This makes an Random generator.
		this.rand= new Thread(new RandInt(this));
			
		//This tells the code there is a Motor to be called Roller on port 0.
		MotorGroup Roller = new MotorGroup(0);
		
		//This tells the code there is a joystick on port 0.
				
		//This makes an array of numbers to store the joystick positions.
		double[] pos;
				
		//This starts the random number generator
		this.rand.start();
		while (this.istank) {
			if(this.Joys.get(1))
				switchy();
			
			//This makes the array of numbers we made eariler be set to the positions of the joysticks.
			pos = Joys.get(0, 6);
			
			
			//Checks if the left or right sticks are up or down
			if(Math.abs(pos[1])>0.1||Math.abs(pos[5])>0.1){
				//This tells the drive motors to go at the inverse of the speed of the joysticks.
				Motors.goL(-(pos[1]));
				Motors.goR(-(pos[5]));
			}
			else{
				//This tells the motors to stop if there is no joystick input.
				Motors.goL(0);
				Motors.goR(0);
			}
			//This checks if the trigger is pushed down.
			if(pos[3]>0.4){
				//This tells the roller motors to go at half the speed of the trigger.
				Roller.go(-pos[3]/2, 0);
			} 
			
			//This checks if the trigger is pressed, and the ball detector is not activated.
			else if(pos[2]>0.4 & getCorrectedDetector()){
				Roller.go(pos[2]/2, 0);
			}
			//This stops the Roller.
			else{
				Roller.go(0, 0);
			}
			//This just puts the value of the sensor on the dashboard
			SmartDashboard.putBoolean("Sensor", getCorrectedDetector());
		}
	}
	public void driveWest(){
		this.camera = new Thread(new Camera(this));
		MotorGroup Motors = new MotorGroup();
		Joy Joys = new Joy(0);
		double[] pos;
		//speed [1] is Left, and [0] is Right Motor group
		double[] speeds={0,0};
		int [] turn = {1,4};
		double pro=0.2;
		this.camera.start();
		while (this.iswest ){
			if(this.Joys.get(1))
				switchy();
			
			pos=Joys.get(0, 6);
			
			if((pos[turn[0]]>pro||pos[turn[0]]<-pro)&&(pro>pos[turn[1]]&&pos[turn[1]]>-pro)){
				speeds[0]=-pos[turn[0]];
				speeds[1]=-pos[turn[0]];
				SmartDashboard.putString("Status ", "Foward");
			}
			else if((pos[turn[1]]>pro||pos[turn[1]]<-pro)&&(pro>pos[turn[0]]&&pos[turn[0]]>-pro)){
				speeds[0]=-pos[turn[1]];
				speeds[1]=pos[turn[1]];
				SmartDashboard.putString("Status ", "Rotate");
			}
			else if((pos[turn[1]]>pro||pos[turn[1]]<-pro)&&(pro<pos[turn[0]]||pos[turn[0]]<-pro)){
				if (pos[turn[1]]<-pro){
					speeds[0]=(-pos[turn[0]]);
					speeds[1]=-((pos[turn[0]])-(pos[turn[1]]/(Math.abs(pos[turn[0]])-0.2)));
					SmartDashboard.putString("Status ", "Turn Right");
				}
				else if(pos[turn[1]]>pro){
					speeds[0]=(pos[turn[0]])-(pos[turn[1]]*(Math.abs(pos[turn[0]])-0.2));
					speeds[1]=-(pos[turn[0]]);
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
	}
}
	
