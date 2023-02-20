package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;




import edu.wpi.first.apriltag.AprilTag;
import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.HttpCamera;
import edu.wpi.first.cscore.HttpCamera.HttpCameraKind;
import edu.wpi.first.math.geometry.Pose2d;

import edu.wpi.first.math.geometry.Rotation2d;
import java.util.ArrayList;
import java.util.Optional;
import org.photonvision.EstimatedRobotPose;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.PhotonPoseEstimator.PoseStrategy;
import org.photonvision.common.hardware.VisionLEDMode;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonUtils;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

import java.util.Optional;

public class Limelight extends SubsystemBase {
    public  final PhotonCamera camera;
    /*private final NetworkTable table;
    NetworkTableEntry tx;
    NetworkTableEntry ty;
    NetworkTableEntry ta;*/

    public Limelight() {
        camera = new PhotonCamera("OV5647");
        camera.setLED(VisionLEDMode.kOn);
        
        final HttpCamera camera1 = new HttpCamera("OV5647", "http://photonvision.local:1181/?action=stream", HttpCameraKind.kMJPGStreamer);
        CameraServer.addCamera(camera1);

        //camera.setPipelineIndex(3);
    }

    @Override
    public void periodic() {
        /*PhotonPipelineResult res = camera.getLatestResult();
        
        PhotonTrackedTarget bestTarget = res.getBestTarget();
        SmartDashboard.putBoolean("targetDetected", res.hasTargets());
        Pose3d bestPose = getRobotPoseFromTarget(bestTarget);
        SmartDashboard.putNumber("X1", bestPose.getX());
        SmartDashboard.putNumber("Y1", bestPose.getY());
        SmartDashboard.putNumber("Z1", bestPose.getZ());*/
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
