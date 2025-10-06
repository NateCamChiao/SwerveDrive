package frc.robot.commands;

import java.util.function.Supplier;

import com.pathplanner.lib.auto.AutoBuilder;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.subsystems.Swerve;

public class AlignToReef extends Command{
  private Supplier<Transform2d> tagOffsetSupplier;
  private Swerve drivetrain;
  private boolean alignLeft;

  public AlignToReef(Swerve swerveSub, Supplier<Transform2d> tagOffsetSupplier, boolean alignLeft){
    this.drivetrain = swerveSub;
    this.tagOffsetSupplier = tagOffsetSupplier;
    this.alignLeft = alignLeft;
  }

  public void initialize(){
    //transform to tag from robot pov
    /*
     * This probably isn't right and some trig is probably needed to find target position
     */
    Transform2d robotToTagOffset = tagOffsetSupplier.get();
    if(this.alignLeft){
      robotToTagOffset.plus(new Transform2d(0, 1, Rotation2d.kZero));
    }
    else{
      robotToTagOffset.plus(new Transform2d(0, -1, Rotation2d.kZero));
    }
    Rotation2d endRotation = this.drivetrain.getYaw().plus(robotToTagOffset.getRotation());

    CommandScheduler.getInstance().schedule(
      AutoBuilder.followPath(this.drivetrain.createPathplannerPath(robotToTagOffset, endRotation))
    );
  }
  public boolean isFinished(){
    return true;
  }
}
