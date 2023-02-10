package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;




import edu.wpi.first.apriltag.AprilTag;
import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.math.geometry.Pose2d;

import edu.wpi.first.math.geometry.Rotation2d;
import java.util.ArrayList;
import java.util.Optional;
import org.photonvision.EstimatedRobotPose;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.PhotonPoseEstimator.PoseStrategy;





import org.photonvision.PhotonCamera;
import org.photonvision.PhotonUtils;
import org.photonvision.targeting.PhotonTrackedTarget;

import java.util.Optional;

public class Limelight extends SubsystemBase {
    public  final PhotonCamera camera;
    private final NetworkTable table;
    NetworkTableEntry tx;
    NetworkTableEntry ty;
    NetworkTableEntry ta;

    public Limelight() {
        table = NetworkTableInstance.getDefault().getTable("limelight");
        camera = new PhotonCamera("limelight");
        tx = table.getEntry("tx");
        ty = table.getEntry("ty");
        ta = table.getEntry("ta");
    }

    @Override
    public void periodic() {
        var result = camera.getLatestResult();

        boolean hasTargets = result.hasTargets();
        SmartDashboard.putBoolean("Target Detected", hasTargets);

        // TODO: Just saving variable values for now
        if( hasTargets ) {
            SmartDashboard.putNumber("Tag ID", result.getBestTarget().getFiducialId());
            SmartDashboard.putNumber("Pose Ambiguity", result.getBestTarget().getPoseAmbiguity());
            
            Transform3d bestCameraToTarget = result.getBestTarget().getBestCameraToTarget();
            SmartDashboard.putNumber("X (roll)", Units.radiansToDegrees(bestCameraToTarget.getRotation().getX()));
            SmartDashboard.putNumber("Y (pitch)", Units.radiansToDegrees(bestCameraToTarget.getRotation().getY()));
            SmartDashboard.putNumber("Z (yaw)", Units.radiansToDegrees(bestCameraToTarget.getRotation().getZ()));
            SmartDashboard.putNumber("x", bestCameraToTarget.getX());
            SmartDashboard.putNumber("y", bestCameraToTarget.getY());
            SmartDashboard.putNumber("z", bestCameraToTarget.getZ());
        }
    }
    /*
     * TODO: Need to update the april-tag-layout.json file
     */
    private void update() {
        
    }

    public static Pose3d getRobotPoseFromTarget(PhotonTrackedTarget target) {
        Transform3d cameraToTarget = target.getBestCameraToTarget();

        Optional<Pose3d> feducialPos = Constants.kVision.APRIL_TAG_FIELD_LAYOUT.getTagPose(target.getFiducialId());

        if (feducialPos.isEmpty()) {
            return null;
        }

        return PhotonUtils.estimateFieldToRobotAprilTag(cameraToTarget, feducialPos.get(), Constants.kVision.CAMERA_TO_ROBOT_METERS_DEGREES);
    }
}
