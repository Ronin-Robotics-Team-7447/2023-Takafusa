package frc.robot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import com.pathplanner.lib.auto.PIDConstants;
import com.pathplanner.lib.auto.SwerveAutoBuilder;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.IntakeBall;
import frc.robot.commands.OuttakeBall;
import frc.robot.commands.SuperIntake;
import frc.robot.commands.SuperOuttake;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Swerve;
import frc.robot.commands.AutoCommands;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  public final Joystick driver;
  // public final Trigger lTrigger;  
  // public final Trigger rTrigger;
  // public final Trigger xButton;  
  // public final Trigger yButton;
  // public final Trigger bButton;  
  // public final Trigger aButton;

  public XboxController m_XboxController;

  public final Swerve swerve;
  public final AutoCommands auto;
  // private final IntakeBall m_IntakeBall;
  // private final OuttakeBall m_OuttakeBall;
  // private final SuperIntake m_SuperIntake;
  // private final SuperOuttake m_SuperOuttake;
  // private final Intake m_intake;

  public RobotContainer() {
    driver = new Joystick(Constants.kControls.DRIVE_JOYSTICK_ID);
    // lTrigger = new JoystickButton(driver, Constants.kControls.lTrigger);
    // rTrigger = new JoystickButton(driver, Constants.kControls.rTrigger);
    // xButton = new JoystickButton(driver, 1);
    // yButton = new JoystickButton(driver, 4);
    // bButton = new JoystickButton(driver, 3);
    // aButton = new JoystickButton(driver, 2);

    swerve = Swerve.getInstance();
    // m_intake = new Intake();
    auto = new AutoCommands(swerve);
    // m_IntakeBall = new IntakeBall(m_intake);   
    // m_OuttakeBall = new OuttakeBall(m_intake);
    // m_SuperIntake = new SuperIntake(m_intake);
    // m_SuperOuttake = new SuperOuttake(m_intake);

    // This will load the file "FullAuto.path" and generate it with a max velocity of 4 m/s and a max acceleration of 3 m/s^2
    // for every path in the group
    List<PathPlannerTrajectory> pathGroup = PathPlanner.loadPathGroup("FullAuto", new PathConstraints(4, 3));

    // This is just an example event map. It would be better to have a constant, global event map
    // in your code that will be used by all path following commands.
    HashMap<String, Command> eventMap = new HashMap<>();
    //eventMap.put("IntakeBall", m_IntakeBall);

    // Create the AutoBuilder. This only needs to be created once when robot code starts, not every time you want to create an auto command. A good place to put this is in RobotContainer along with your subsystems.
    // SwerveAutoBuilder autoBuilder = new SwerveAutoBuilder(
    //     swerve::getPose, // Pose2d supplier
    //     swerve::resetOdometry, // Pose2d consumer, used to reset odometry at the beginning of auto
    //     Constants.kSwerve.KINEMATICS, // SwerveDriveKinematics
    //     new PIDConstants(5.0, 0.0, 0.0), // PID constants to correct for translation error (used to create the X and Y PID controllers)
    //     new PIDConstants(0.5, 0.0, 0.0), // PID constants to correct for rotation error (used to create the rotation controller)
    //     swerve::setModuleStates, // Module states consumer used to output to the drive subsystem
    //     eventMap,
    //     true, // Should the path be automatically mirrored depending on alliance color. Optional, defaults to true
    //     swerve // The drive subsystem. Used to properly set the requirements of path following commands
    // );

    // Command auto_name_command = autoBuilder.fullAuto(pathGroup);

    // Configure button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    SlewRateLimiter filter = new SlewRateLimiter(0.5);

    // swerve.setDefaultCommand(swerve.drive(
    //     () -> filter.calculate(-driver.getRawAxis(Constants.kControls.TRANSLATION_Y_AXIS)),
    //     () -> filter.calculate(driver.getRawAxis(Constants.kControls.TRANSLATION_X_AXIS)),
    //     () -> -driver.getRawAxis(Constants.kControls.ROTATION_AXIS),
    //     true,
    //     false
    // ));

    swerve.setDefaultCommand(swerve.drive(
       () -> -driver.getRawAxis(Constants.kControls.TRANSLATION_Y_AXIS),
       () -> driver.getRawAxis(Constants.kControls.TRANSLATION_X_AXIS),
       () -> -driver.getRawAxis(Constants.kControls.ROTATION_AXIS),
       true,
       false
    ));

    //  BUTTONS

    // lTrigger.onTrue(m_IntakeBall);
    // rTrigger.onTrue(m_OuttakeBall);
    // bButton.onTrue(m_SuperIntake);
    // xButton.onTrue(m_SuperOuttake);

    // new JoystickButton(driver, Constants.kControls.GYRO_RESET_BUTTON)
    //  .onTrue(swerve.zeroGyroCommand());
  }

    /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return auto.getSelectedCommand();
    // return null;
  }
}