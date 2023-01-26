package frc.robot.subsystems;

public class SwerveModuleConstants {
    public final int driveMotorID;
    public final int angleMotorID;
    public final int absoluteEncoderID;
    public final boolean absoluteEncoderReversed;
    public final double m_absoluteEncoderOFfsetRad;

    public SwerveModuleConstants(int driveMotorID, int angleMotorID, int absoluteEncoderID, double m_absoluteEncoderOFfsetRad, boolean absoluteEncoderReversed) {
        this.driveMotorID = driveMotorID;
        this.angleMotorID = angleMotorID;
        this.absoluteEncoderID = absoluteEncoderID;
        this.absoluteEncoderReversed = absoluteEncoderReversed;
        this.m_absoluteEncoderOFfsetRad = m_absoluteEncoderOFfsetRad;
    }
}