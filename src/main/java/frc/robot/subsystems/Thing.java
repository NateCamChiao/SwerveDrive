package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Thing extends SubsystemBase {
  /** Creates a new ExampleSubsystem. */
  public Thing() {
    
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }

  public Command defaultCommand(){
    SmartDashboard.putString("thing", "sdf");
    return Commands.run(
      () -> SmartDashboard.putNumber("default", SmartDashboard.getNumber("default", 0) + 1),
      this
    );
  }
}
