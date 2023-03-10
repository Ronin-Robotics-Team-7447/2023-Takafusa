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
// frc.robot.commands.TF2AutoBalance;
import frc.robot.commands.lowerArm;
import frc.robot.commands.raiseArm;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Swerve;
import frc.robot.Constants.kSwerve;
import frc.robot.commands.AutoCommands;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  Command auto_name_command;
  public final Joystick driver;
  public final Trigger lTrigger;  
  public final Trigger rTrigger;
  // public final Trigger xButton;  
  // public final Trigger yButton;
  // public final Trigger bButton;  
  public final Trigger aButton;
  public final Trigger lStick;

  public XboxController m_XboxController;

  public final Swerve swerve;
  public final Arm arm;

  private final lowerArm m_lowerArm;
  private final raiseArm m_raiseArm;
  //private final TF2AutoBalance m_AutoBalance;
  private Limelight limelight;
  // public final AutoCommands auto;
  // private final IntakeBall m_IntakeBall;
  // private final OuttakeBall m_OuttakeBall;
  // private final SuperIntake m_SuperIntake;
  // private final SuperOuttake m_SuperOuttake;
  // private final Intake m_intake;

  public RobotContainer() {
    driver = new Joystick(Constants.kControls.DRIVE_JOYSTICK_ID);
    arm = new Arm();
    //limelight = new Limelight();
    lTrigger = new JoystickButton(driver, Constants.kControls.lTrigger);
    rTrigger = new JoystickButton(driver, Constants.kControls.rTrigger);
    // xButton = new JoystickButton(driver, 1);
    // yButton = new JoystickButton(driver, 4);
    // bButton = new JoystickButton(driver, 3);
    aButton = new JoystickButton(driver, XboxController.Button.kX.value);
    lStick = new JoystickButton(driver, Constants.kControls.lStick);


    swerve = Swerve.getInstance();


    // m_intake = new Intake();
    // auto = new AutoCommands(swerve);
    m_raiseArm = new raiseArm(arm);
    m_lowerArm = new lowerArm(arm);
    //m_AutoBalance = new TF2AutoBalance(swerve);
    // m_IntakeBall = new IntakeBall(m_intake);   
    // m_OuttakeBall = new OuttakeBall(m_intake);
    // m_SuperIntake = new SuperIntake(m_intake);
    // m_SuperOuttake = new SuperOuttake(m_intake);

    // This will load the file "FullAuto.path" and generate it with a max velocity of 4 m/s and a max acceleration of 3 m/s^2
    // for every path in the group
    List<PathPlannerTrajectory> pathGroup = PathPlanner.loadPathGroup("inf", new PathConstraints(4, 3));

    // This is just an example event map. It would be better to have a constant, global event map
    // in your code that will be used by all path following commands.
    HashMap<String, Command> eventMap = new HashMap<>();
    //eventMap.put("IntakeBall", m_IntakeBall);

    // Create the AutoBuilder. This only needs to be created once when robot code starts, not every time you want to create an auto command. A good place to put this is in RobotContainer along with your subsystems.
    SwerveAutoBuilder autoBuilder = new SwerveAutoBuilder(
        swerve::getPose, // Pose2d supplier
        swerve::resetOdometry, // Pose2d consumer, used to reset odometry at the beginning of auto
        Constants.kSwerve.KINEMATICS, // SwerveDriveKinematics
        new PIDConstants(Constants.kSwerve.DRIVE_KP, Constants.kSwerve.DRIVE_KI, Constants.kSwerve.DRIVE_KD), // PID constants to correct for translation error (used to create the X and Y PID controllers)
        new PIDConstants(Constants.kSwerve.ANGLE_KP, Constants.kSwerve.ANGLE_KI, Constants.kSwerve.ANGLE_KD), // PID constants to correct for rotation error (used to create the rotation controller)
        swerve::setModuleStates, // Module states consumer used to output to the drive subsystem
        eventMap,
        true, // Should the path be automatically mirrored depending on alliance color. Optional, defaults to true
        swerve // The drive subsystem. Used to properly set the requirements of path following commands
    );

    auto_name_command = autoBuilder.fullAuto(pathGroup);

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
    // swerve.setDefaultCommand(swerve.drive(
    //     () -> filter.calculate(-driver.getRawAxis(Constants.kControls.TRANSLATION_Y_AXIS)),
    //     () -> filter.calculate(driver.getRawAxis(Constants.kControls.TRANSLATION_X_AXIS)),
    //     () -> -driver.getRawAxis(Constants.kControls.ROTATION_AXIS),
    //     true,
    //     false
    // ));

    SlewRateLimiter slew = new SlewRateLimiter(0.5);
    swerve.setDefaultCommand(swerve.drive(
       () -> -driver.getRawAxis(Constants.kControls.TRANSLATION_Y_AXIS),
       () -> driver.getRawAxis(Constants.kControls.TRANSLATION_X_AXIS),
       () -> -driver.getRawAxis(Constants.kControls.ROTATION_AXIS),
       true,
       false,
       Constants.kSwerve.MAX_VELOCITY_DRIVE_METERS_PER_SECOND,
       Constants.kSwerve.MAX_VELOCITY_ROTATE_METERS_PER_SECOND
    ));

    // Put on smartdashboard that speed is slowed or normal
    lStick.onTrue(swerve.drive(
      () -> -driver.getRawAxis(Constants.kControls.TRANSLATION_Y_AXIS),
      () -> driver.getRawAxis(Constants.kControls.TRANSLATION_X_AXIS),
      () -> -driver.getRawAxis(Constants.kControls.ROTATION_AXIS),
      true,
      false,
      Constants.kSwerve.SLOW_SPEED,
      Constants.kSwerve.SLOW_SPEED
    ));

    lStick.onFalse(swerve.drive(
      () -> -driver.getRawAxis(Constants.kControls.TRANSLATION_Y_AXIS),
      () -> driver.getRawAxis(Constants.kControls.TRANSLATION_X_AXIS),
      () -> -driver.getRawAxis(Constants.kControls.ROTATION_AXIS),
      true,
      false,
      Constants.kSwerve.MAX_VELOCITY_DRIVE_METERS_PER_SECOND,
      Constants.kSwerve.MAX_VELOCITY_ROTATE_METERS_PER_SECOND
    ));

    //  BUTTONS

    lTrigger.onTrue(m_lowerArm);
    rTrigger.onTrue(m_raiseArm);
    //aButton.onTrue(m_AutoBalance);

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
    // return auto.getSelectedCommand();
    return auto_name_command;
  }
}