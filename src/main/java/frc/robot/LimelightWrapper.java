package frc.robot;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.networktables.NetworkTableInstance;

public class LimelightWrapper {
    private String limelightName = "";
    public LimelightWrapper(String limelightName){
        this.limelightName = limelightName;
    }

    public Transform2d getNearestTagOffset(){
        double[] offsetData = LimelightHelpers.getTargetPose_RobotSpace(limelightName);
        double xOffset = offsetData[0];
        double yOffset = offsetData[1];
        double rotationOffset = offsetData[4];
        return new Transform2d(
            xOffset,
            yOffset,
            Rotation2d.fromDegrees(rotationOffset)
        );
    }
}
