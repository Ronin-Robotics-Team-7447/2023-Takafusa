// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.io.IOException;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.apriltag.AprilTag;
import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.subsystems.SwerveModuleConstants;

/**
 * This class contains values that remain constant while the robot is running.
 * 
 * It's split into categories using subclasses, preventing too many members from
 * being defined on one class.
 */
public class Constants {
    /** All joystick, button, and axis IDs. */
    public static class kControls {
      public static final double AXIS_DEADZONE = 0.35;

      // Please test numbers 
      public static final int rTrigger = 5;
      public static final int lTrigger = 6;
      public static final int lStick = 9;
  
      public static final int DRIVE_JOYSTICK_ID = 0;
  
      public static final int TRANSLATION_X_AXIS = XboxController.Axis.kLeftX.value;
      public static final int TRANSLATION_Y_AXIS = XboxController.Axis.kLeftY.value;
      public static final int ROTATION_AXIS = XboxController.Axis.kRightX.value;
  
      //public static final int GYRO_RESET_BUTTON = XboxController.Button.kY.value;
    }
  
    /** All swerve constants. */
    public static class kSwerve {
      /** Constants that apply to the whole drive train. */
      public static final double TRACK_WIDTH = Units.inchesToMeters(25); // Width of the drivetrain measured from the middle of the wheels.
      public static final double WHEEL_BASE = Units.inchesToMeters(25); // Length of the drivetrain measured from the middle of the wheels.
      public static final double WHEEL_DIAMETER = Units.inchesToMeters(4);
      public static final double WHEEL_CIRCUMFERENCE = WHEEL_DIAMETER * Math.PI;
  
      public static final SwerveDriveKinematics KINEMATICS = new SwerveDriveKinematics(
        new Translation2d(WHEEL_BASE / 2.0, -TRACK_WIDTH / 2.0),
        new Translation2d(WHEEL_BASE / 2.0, TRACK_WIDTH / 2.0),
        new Translation2d(-WHEEL_BASE / 2.0, -TRACK_WIDTH / 2.0),
        new Translation2d(-WHEEL_BASE / 2.0, TRACK_WIDTH / 2.0)
      );
  
      public static final double DRIVE_GEAR_RATIO = 7.13 / 1.0; // 6.75:1
      public static final double DRIVE_ROTATIONS_TO_METERS = WHEEL_CIRCUMFERENCE / DRIVE_GEAR_RATIO;
      public static final double DRIVE_RPM_TO_METERS_PER_SECOND = DRIVE_ROTATIONS_TO_METERS / 60.0;
      public static final double ANGLE_GEAR_RATIO = 13.71 / 1.0; // 12.8:1
      public static final double ANGLE_ROTATIONS_TO_RADIANS = (Math.PI * 2) / ANGLE_GEAR_RATIO;
      public static final double ANGLE_RPM_TO_RADIANS_PER_SECOND = DRIVE_ROTATIONS_TO_METERS / 60.0;
  
      // Joystick Port Constants
      public static final int joystickPort = 0;

      /** Speed ramp. */
      public static final double OPEN_LOOP_RAMP = 0.25;
      public static final double CLOSED_LOOP_RAMP = 0.0;
  
      /** Current limiting. */
      public static final int DRIVE_CURRENT_LIMIT = 20;
      public static final int ANGLE_CURRENT_LIMIT = 10;
  
      /** Drive motor PID values. */
      public static final double DRIVE_KP = 0.0;
      public static final double DRIVE_KI = 0.0;
      public static final double DRIVE_KD = 0.0;
      public static final double DRIVE_KF = 0.0;
  
      /** Drive motor characterization. */
      public static final double DRIVE_KS = 0.11937;
      public static final double DRIVE_KV = 2.6335;
      public static final double DRIVE_KA = 0.46034;
  
      /** Angle motor PID values. */
      public static final double ANGLE_KP = 0.1;
      public static final double ANGLE_KI = 0.0;
      public static final double ANGLE_KD = 0.0;
      public static final double ANGLE_KF = 0.0;
      
      /** Swerve constraints. */
      public static final double MAX_VELOCITY_DRIVE_METERS_PER_SECOND = 3.0;
      public static final double MAX_VELOCITY_ROTATE_METERS_PER_SECOND = 3.0;
      public static final double SLOW_SPEED = 1.5;
  
      /** Inversions. */
      public static final boolean DRIVE_MOTOR_INVERSION = true;
      public static final boolean ANGLE_MOTOR_INVERSION = false;
      public static final boolean CANCODER_INVERSION = false;
  
      /** Idle modes. */
      public static final IdleMode DRIVE_IDLE_MODE = IdleMode.kBrake;
      public static final IdleMode ANGLE_IDLE_MODE = IdleMode.kCoast;
  
      /** 
       * Module specific constants.
       * CanCoder offset is in DEGREES, not radians like the rest of the repo.
       * This is to make offset slightly more accurate and easier to measure.
       */
      
       // Front Left 
       public static final SwerveModuleConstants MOD_0_Constants = new SwerveModuleConstants(
        5,
        4,
        1,
        180
      );
  
      public static final SwerveModuleConstants MOD_1_Constants = new SwerveModuleConstants(
        3,
        6,
        0,
        0
      );
  
      public static final SwerveModuleConstants MOD_2_Constants = new SwerveModuleConstants(
        2,
        8,
        2,
        180
      );
  
      public static final SwerveModuleConstants MOD_3_Constants = new SwerveModuleConstants(
        1,
        7,
        3,
        0
      );
    }
  
    public static class kAuto {
      /** PID Values. */
      public static final double X_CONTROLLER_KP = 1.0;
      public static final double Y_CONTROLLER_KP = 1.0;
      public static final double THETA_CONTROLLER_KP = 1.0;
      
      /** Constraints. */
      public static final double MAX_VELOCITY_METERS_PER_SECOND = 10.0;
      public static final double MAX_ACCEL_METERS_PER_SECOND_SQUARED = 9.0;
    }
    //Intakes Constants
    public static class kIntake {
      public static final int takeInOut = 1;
      public static final double speed_in = 0.5;
      public static final double speed_out = -0.5;
      public static final double hold_speed = 0.1;
      public static final double highSpeed_in = 0.8;
      public static final double highSpeed_out = -0.8;
    }

    // Vision Constants
    public static class kVision {
      public static final Translation3d CAMERA_POS_METERS = new Translation3d(
        Units.inchesToMeters(0),
        Units.inchesToMeters(0),
        Units.inchesToMeters(0)
      );

      public static final Rotation3d CAMERA_PITCH_RADIANS = new Rotation3d(
        Units.degreesToRadians(0),
        Units.degreesToRadians(0),
        Units.degreesToRadians(0)
      ).unaryMinus();
      
      public static final Transform3d CAMERA_TO_ROBOT_METERS_DEGREES = new Transform3d(
        CAMERA_POS_METERS.unaryMinus(), 
        CAMERA_PITCH_RADIANS
      ); 

      public static final AprilTagFieldLayout APRIL_TAG_FIELD_LAYOUT = createFieldLayout();

      private static AprilTagFieldLayout createFieldLayout() {
        try {
          return new AprilTagFieldLayout(Filesystem.getDeployDirectory().toPath().resolve("april-tag-layout.json"));
        } catch( IOException e ) {
          throw new Error(e);
        }
      }
    }
  }