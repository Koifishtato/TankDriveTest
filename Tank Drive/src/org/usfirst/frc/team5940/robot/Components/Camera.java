package org.usfirst.frc.team5940.robot.Components;
import org.usfirst.frc.team5940.robot.Robot;
import org.usfirst.frc.team5940.robot.State;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.DrawMode;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.Point;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.Timer;


public class Camera extends State {
	float val = 0;
	
	/**
	 * Constructor
	 * @param robot RobotBase for super
	 */
    public Camera(RobotBase robot) {
		super(robot);
	}
    
    int session;
    Image frame;
    
    /**
     * Sets up camera session, frame, and grabbing
     */
	@Override
	protected void init() {
		frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);

        // the camera name (ex "cam0") can be found through the roborio web interface
        session = NIVision.IMAQdxOpenCamera("cam0",
                NIVision.IMAQdxCameraControlMode.CameraControlModeController);
        NIVision.IMAQdxConfigureGrab(session);
		
	}

	
	/**
	 * Contains all the normal code in Intermediate vision with drawing and overlays changed. Note, only works in operator control.
	 */
	@Override
	protected void update() {
		NIVision.IMAQdxStartAcquisition(session);

        /**
         * grab an image, draw the circle, and provide it for the camera server
         * which will in turn send it to the dashboard.
         */

        while (robot.isOperatorControl() && robot.isEnabled()) {

            NIVision.IMAQdxGrab(session, frame, 1);
            //NIVision.imaqDrawShapeOnImage(frame, frame, rect, DrawMode.DRAW_VALUE, ShapeMode.SHAPE_RECT, 0.0f);
            
            //NIVision.imaqDrawLineOnImage(frame, frame, DrawMode.DRAW_VALUE, new Point(50, 50), new Point(100, 100), Float.MAX_VALUE);
            /*
            NIVision.imaqDrawShapeOnImage(frame, frame, rect,
                    DrawMode.DRAW_VALUE, ShapeMode.SHAPE_OVAL, 0.0f);*/
     
            
            CameraServer.getInstance().setImage(frame);

            /** robot code here! **/
            Timer.delay(0.005);		// wait for a motor update time
        }
        NIVision.IMAQdxStopAcquisition(session);
		
	}
	
	


}