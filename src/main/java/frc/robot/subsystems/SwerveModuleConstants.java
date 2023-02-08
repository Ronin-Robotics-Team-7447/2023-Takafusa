package frc.robot.subsystems;

public class SwerveModuleConstants {
    public final int driveMotorID;
    public final int angleMotorID;
    public final int absoluteEncoderID;
    public final double canCoderOffsetDegrees;

    public SwerveModuleConstants(int driveMotorID, int angleMotorID, int absoluteEncoderID, double canCoderOffsetDegrees) {
        this.driveMotorID = driveMotorID;
        this.angleMotorID = angleMotorID;
        this.absoluteEncoderID = absoluteEncoderID;
        this.canCoderOffsetDegrees = canCoderOffsetDegrees;
    }
}
