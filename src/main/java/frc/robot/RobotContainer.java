package frc.robot;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.commands.DriveCommand;
import frc.robot.subsystems.Swerve;

public class RobotContainer {
    private Swerve s_swerve = new Swerve();
    private Joystick joystick = new Joystick(Constants.Joystick.kPort);

    private SlewRateLimiter joystickRateLimiter = new SlewRateLimiter(Constants.Joystick.kSlewRateLimit);
    private SlewRateLimiter rotDirectionLimiter = new SlewRateLimiter(Constants.Joystick.kSlewRateLimit);
    private SendableChooser<Command> sendableChooser = new SendableChooser<Command>();

    public RobotContainer(){
        configureAutoOptions();
        configureBindings();
        s_swerve.setDefaultCommand(new DriveCommand(s_swerve, () -> joystick.getRawAxis(1), () -> joystick.getRawAxis(0), () -> 0.0, false));
    }

    public void configureAutoOptions(){
        sendableChooser.setDefaultOption("Default Auto", new InstantCommand());
        sendableChooser.addOption("auto1", new InstantCommand());
        SmartDashboard.putData(sendableChooser);
    }

    public void configureBindings(){

    }

    public Command getAutoCommand(){
        return sendableChooser.getSelected();
    }
}