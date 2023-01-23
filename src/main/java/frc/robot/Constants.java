// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.util.Units;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    public static final class SwerveTuningValues {
        public static final double kWheelDiameterMeters = Units.inchesToMeters(0);
        public static final double kDriveMotorGearRatio = 0;
        public static final double kTurningMotorGearRatio = 0;
        public static final double kDriveEncoderRot2Meter = kDriveMotorGearRatio * Math.PI * kWheelDiameterMeters;
        public static final double kTurningEncoderRot2Rad = kTurningMotorGearRatio * 2 * Math.PI;
        public static final double kDriveEncoderRPM2MeterPerSec = kDriveEncoderRot2Meter / 60;
        public static final double kTurningEncoderRPM2RadPerSec = kTurningEncoderRot2Rad / 60;
        public static final double kPTurning = 0;

        public static final double kPhysMaxSpeedMetersPerSecond = 4.233672;
        public static final double kTeleDriveMaxSpeedMetersPerSecond = kPhysMaxSpeedMetersPerSecond / 4;
        public static final double kTeleDriveMaxAngularSpeedRadiansPerSecond = kPhysMaxSpeedMetersPerSecond / 4;
        public static final double kTeleDriveMaxAccelerationUnitsPerSecond = 3;
        public static final double kTeleDriveMaxAngularAccelerationUnitsPerSecond = 3;

        // PLEASE FILL OUT DATA
        public static final double kTrackWidth = Units.inchesToMeters(0);
        // Distance between right and left wheels
        public static final double kWheelBase = Units.inchesToMeters(0);
        // Distance between front and back wheels
        public static final SwerveDriveKinematics kDriveKinematics = new SwerveDriveKinematics(
                new Translation2d(kWheelBase / 2, -kTrackWidth / 2),
                new Translation2d(kWheelBase / 2, kTrackWidth / 2),
                new Translation2d(-kWheelBase / 2, -kTrackWidth / 2),
                new Translation2d(-kWheelBase / 2, kTrackWidth / 2));
    }

    public static final class Ports {
        // Motor number assignment for drivebase joystick
        public final static int driverJoystickPort = 1; 

        // Front Left Wheel Ports
        public final static int flDrivePort = 1;
        public final static int flTurningPort = 1;
        public final static int flDriveAbsoluteEncoderPort = 1;
        public static final boolean flDriveEncoderReversed = false;
        public static final boolean flTurningEncoderReversed = false;
        public static final boolean flDriveAbsoluteEncoderReversed = false;
        public static final double flDriveAbsoluteEncoderOffsetRad = 0.0;

        // Front Right Wheel Ports
        public final static int frDrivePort = 2;
        public final static int frTurningPort = 2;
        public final static int frDriveAbsoluteEncoderPort = 2;
        public static final boolean frDriveEncoderReversed = false;
        public static final boolean frTurningEncoderReversed = false;
        public static final boolean frDriveAbsoluteEncoderReversed = false;
        public static final double frDriveAbsoluteEncoderOffsetRad = 0.0;

        // Back Left Wheel Ports
        public final static int blDrivePort = 3;
        public final static int blTurningPort = 3;
        public final static int blDriveAbsoluteEncoderPort = 3;
        public static final boolean blDriveEncoderReversed = false;
        public static final boolean blTurningEncoderReversed = false;
        public static final boolean blDriveAbsoluteEncoderReversed = false;
        public static final double blDriveAbsoluteEncoderOffsetRad = 0.0;

        // Back Right Wheel Ports 
        public final static int brDrivePort = 4;
        public final static int brTurningPort = 4;
        public final static int brDriveAbsoluteEncoderPort = 4;
        public static final boolean brDriveEncoderReversed = false;
        public static final boolean brTurningEncoderReversed = false;
        public static final boolean brDriveAbsoluteEncoderReversed = false;
        public static final double brDriveAbsoluteEncoderOffsetRad = 0.0;
    }

    public static final class JoystickConstants {
        public static final double Deadband = 0.05;

        // Fill out values for controller
        public static final int driverJoystickYAxis = 1;
        public static final int driverJoystickXAxis = 0;
        public static final int driverJoystickRotAxis = 4;
        public static final int driverJoystickFieldOrientedButtonIdx = 1;

        public static final int xButton = 1;
    }
}
