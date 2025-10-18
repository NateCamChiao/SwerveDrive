package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Pivot;

public class Robot extends TimedRobot {
  private RobotContainer robotContainer = new RobotContainer();
  private Command m_autoCommand;
  private Pivot pivot;
  

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
    // SmartDashboard.putNumber("Limelight z", LimelightHelpers.getTargetPose_RobotSpace("limelight-one")[2]);
  }

  @Override
  public void autonomousInit() {
    m_autoCommand = robotContainer.getAutoCommand();
    if(m_autoCommand != null){
      m_autoCommand.schedule();
    }
  }
  

  @Override
  public void teleopInit(){
    if(m_autoCommand != null){
      m_autoCommand.cancel();
    }
    new InstantCommand( () -> pivot.setMotor(Constants.PivotConstants.idleAngle));

  }

  @Override
  public void robotInit(){
    
  }

  @Override 
  public void simulationPeriodic(){}

  @Override
  public void teleopPeriodic(){}

}
