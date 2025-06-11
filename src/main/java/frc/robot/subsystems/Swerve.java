package frc.robot.subsystems;
import static frc.robot.Constants.Swerve.*;
import frc.robot.subsystems.SwerveModule;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Swerve extends SubsystemBase{
    private final SwerveModule[] swerveModules = {
        new SwerveModule(FLModule.kSwerveConstants),
        new SwerveModule(FRModule.kSwerveConstants),
        new SwerveModule(BLModule.kSwerveConstants),
        new SwerveModule(BRModule.kSwerveConstants)
    };
    SwerveDriveKinematics swerveKinematics = new SwerveDriveKinematics(
        
    );

}
