package org.usfirst.frc.team5940.robot.Components;
import java.util.Random;

import org.usfirst.frc.team5940.robot.State;

import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class RandInt extends State {

	public RandInt(RobotBase robot) {
		super(robot);
		// TODO Auto-generated constructor stub
	}
	
	
	protected void update(){
		Timer time = new Timer();
		Random rand = new Random();
		while(robot.isOperatorControl()&&robot.isEnabled()){
			SmartDashboard.putNumber("Random",rand.nextInt(1000));
			time.delay(0.5);
		}
	}


	@Override
	protected void init() {
		// TODO Auto-generated method stub
		
	}

}
