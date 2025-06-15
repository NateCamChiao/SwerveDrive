package frc.robot;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.DriveCommand;
import frc.robot.subsystems.Swerve;
import frc.robot.subsystems.Thing;

public class RobotContainer {
    public static Field2d m_simField = new Field2d();
    private Swerve s_swerve = new Swerve();
    private Joystick joystick = new Joystick(Constants.Joystick.kPort);
    JoystickButton btn = new JoystickButton(joystick, 2);
    public Thing thing = new Thing();

    private SlewRateLimiter joystickRateLimiter = new SlewRateLimiter(Constants.Joystick.kSlewRateLimit);
    private SlewRateLimiter rotDirectionLimiter = new SlewRateLimiter(Constants.Joystick.kSlewRateLimit);
    private SendableChooser<Command> autoChooser = new SendableChooser<Command>();
    private SendableChooser<Double> robotSpeedChooser = new SendableChooser<Double>();

    public RobotContainer(){
        configureAutoOptions();
        configureBindings();
        s_swerve.setDefaultCommand(
            new DriveCommand(
                s_swerve, 
                () -> joystickRateLimiter.calculate(joystick.getRawAxis(Constants.Joystick.kXAxis)), 
                () -> joystickRateLimiter.calculate(joystick.getRawAxis(Constants.Joystick.kYAxis)),
                () -> rotDirectionLimiter.calculate(joystick.getRawAxis(Constants.Joystick.kRotationAxis)), 
                false
            )
        );
        thing.setDefaultCommand(thing.defaultCommand());


        SmartDashboard.putData(RobotContainer.m_simField);
        SmartDashboard.putNumber("test", 0);
    }

    public void configureAutoOptions(){
        autoChooser.setDefaultOption("Default Auto", new InstantCommand());
        autoChooser.addOption("auto1", new InstantCommand());
        SmartDashboard.putData(autoChooser);

        robotSpeedChooser.setDefaultOption("100%", Double.valueOf(1.0));
        robotSpeedChooser.addOption("75%", 0.75);
        robotSpeedChooser.addOption("50%", 0.5);
        robotSpeedChooser.addOption("25%", 0.25);
        SmartDashboard.putData(robotSpeedChooser);
    }

    public void configureBindings(){
        btn.onTrue(new InstantCommand(() -> SmartDashboard.putBoolean("this calls", true)));
    }

    public Command getAutoCommand(){
        return autoChooser.getSelected();
    }
}