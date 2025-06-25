// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.MotionMagicTorqueCurrentFOC;
import com.ctre.phoenix6.controls.NeutralOut;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.TestConstants;

public class TestSubsystem extends SubsystemBase {

  private final TalonFX test = new TalonFX(51, "rio");

  private TalonFXConfiguration testConfigs = new TalonFXConfiguration();

  private MotionMagicTorqueCurrentFOC testMotionMagic = new MotionMagicTorqueCurrentFOC(0.0);

  private VoltageOut testVoltage = new VoltageOut(0.0);
  
  private NeutralOut neutral = new NeutralOut();

  NetworkTableInstance inst = NetworkTableInstance.getDefault();
  NetworkTable table = inst.getTable("elevatortable");

  private boolean homeRequest = false;
  private boolean upRequest = false;
  private boolean downRequest = false;

  public TestSubsystem() {
    // config neutralmode
    testConfigs.MotorOutput.withNeutralMode(
        TestConstants.test_Neutalmode_Coast
            ? NeutralModeValue.Coast
            : NeutralModeValue.Brake);
    
    // config direction
    // POSITIVE for test moving up and NEGATIVE for follower moving up
    testConfigs.MotorOutput.withInverted(
        TestConstants.test_Inverted_CounterClockwisePositive
            ? InvertedValue.CounterClockwise_Positive
            : InvertedValue.Clockwise_Positive);
    // config PIDSAV
    testConfigs.Slot0.kP = TestConstants.test_kP;
    testConfigs.Slot0.kI = TestConstants.test_kI;
    testConfigs.Slot0.kD = TestConstants.test_kD;
    testConfigs.Slot0.kS = TestConstants.test_kS;
    testConfigs.Slot0.kA = TestConstants.test_kA;
    testConfigs.Slot0.kV = TestConstants.test_kV;

    // config motionmagic
    testConfigs.MotionMagic.MotionMagicCruiseVelocity = TestConstants.test_CruiseVelocity;
    testConfigs.MotionMagic.MotionMagicAcceleration = TestConstants.test_CruiseAcceleration;
  
    // config ducy cycle limit
    testConfigs.MotorOutput.withPeakForwardDutyCycle(TestConstants.forwardDutyCycleLimit);
    testConfigs.MotorOutput.withPeakReverseDutyCycle(TestConstants.reverseDutyCycleLimit);
    
    // config softlimit
    // hall sensor is used as the reverse limit
    testConfigs.SoftwareLimitSwitch.ForwardSoftLimitEnable = TestConstants.test_forwardSoftLimitEnable;
    testConfigs.SoftwareLimitSwitch.ForwardSoftLimitThreshold = TestConstants.test_forwardSoftLimitThreshold;
    testConfigs.SoftwareLimitSwitch.ReverseSoftLimitEnable = TestConstants.test_reverseSoftLimitEnable;
    testConfigs.SoftwareLimitSwitch.ReverseSoftLimitThreshold = TestConstants.test_reverseSoftLimitThreshold;  
  }

  /**
   * Example command factory method.
   *
   * @return a command
   */

  public Command exampleMethodCommand() {
    // Inline construction of command goes here.
    // Subsystem::RunOnce implicitly requires `this` subsystem.
    return runOnce(
        () -> {
          /* one-time action goes here */
        });
  }

  /**
   * An example method querying a boolean state of the subsystem (for example, a
   * digital sensor).
   *
   * @return value of some boolean subsystem state, such as a digital sensor.
   */
  public boolean exampleCondition() {
    // Query some boolean state, such as a digital sensor.
    return false;
  }

  @Override
  public void periodic() {
    SmartDashboard.putBoolean("HomeRequest", homeRequest);
    SmartDashboard.putBoolean("DownRequest", downRequest);
    SmartDashboard.putBoolean("UpRequest", upRequest);
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }

  public void setPosition(double position) {
    test.setControl(testMotionMagic.withPosition(position));
  }

  public boolean isAtPosition(double position){
    return (test.getPosition().getValueAsDouble() - position) < TestConstants.positionDeadBand;
  }

  public void setVoltage(double voltage) {
    test.setControl(testVoltage.withOutput(voltage));
  }

  public void hold() {
    test.setControl(testMotionMagic.withPosition(test.getPosition().getValueAsDouble()));
  }

  public void setNeutral() {
    test.setControl(neutral);

  }

  public void upDateNetworkTable(){
  }

  public void setHomeRequest(boolean request){
    homeRequest = request;
  }
  public boolean getHomeRequest(){
    return homeRequest;
  }

  public void setDownRequest(boolean request){
    downRequest = request;
  }
  public boolean getDownRequest(){
    return downRequest;
  }

  public void setUpRequest(boolean request){
    upRequest = request;
  }
  public boolean getUpRequest(){
    return upRequest;
  }
}
