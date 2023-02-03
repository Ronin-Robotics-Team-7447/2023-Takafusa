package frc.robot.subsystems;

public class SwerveModuleConstants {
    public final int driveMotorID;
    public final int angleMotorID;
    public final int absoluteEncoderID;
    public final boolean absoluteEncoderReversed;
    public final double absoluteEncoderOffsetRad;

    public SwerveModuleConstants(int driveMotorID, int angleMotorID, int absoluteEncoderID, double absoluteEncoderOffsetRad, boolean absoluteEncoderReversed) {
        this.driveMotorID = driveMotorID;
        this.angleMotorID = angleMotorID;
        this.absoluteEncoderID = absoluteEncoderID;
        this.absoluteEncoderReversed = absoluteEncoderReversed;
        this.absoluteEncoderOffsetRad = absoluteEncoderOffsetRad;
    }
}