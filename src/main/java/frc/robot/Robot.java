package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot {
  private RobotContainer robotContainer = new RobotContainer();
  private Command m_autoCommand;
  

  @Override
  public void robotPeriodic() {}

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
  }
  @Override
  public void robotInit(){
    CommandScheduler.getInstance().run();
  }

}
