package frc.robot.commands;

import java.util.List;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.GoalEndState;
import com.pathplanner.lib.path.PathPlannerPath;
import com.pathplanner.lib.path.Waypoint;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.subsystems.Swerve;

public class DriveToPoint extends Command{
    private Swerve swerveSub;
    private Transform2d relativeTransform;
    public DriveToPoint(Swerve swerve, Transform2d relativeTransform){
        this.swerveSub = swerve;
        this.relativeTransform = relativeTransform;
    }

    public PathPlannerPath createPathplannerPath(Transform2d relativeTransform){
        List<Waypoint> pathWaypoints = PathPlannerPath.waypointsFromPoses(
            this.swerveSub.getPose(),
            this.swerveSub.getPose().plus(relativeTransform)
        );
        return new PathPlannerPath(
            pathWaypoints,
            null, 
            null, 
            new GoalEndState(0, Rotation2d.fromDegrees(0)));
    }

    public void initialize(){
        PathPlannerPath path = createPathplannerPath(this.relativeTransform);
        CommandScheduler.getInstance().schedule(
            AutoBuilder.followPath(path)
        );
    }
    public boolean isFinished(){
        return true;
    }
}
