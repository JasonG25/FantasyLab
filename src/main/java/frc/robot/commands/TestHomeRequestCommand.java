// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.TestSubsystem.TestState;
import frc.robot.subsystems.TestSubsystem;
import frc.robot.constants.TestConstants;

/** An example command that uses an example subsystem. */
public class TestHomeRequestCommand extends Command {
  private final TestSubsystem m_subsystem;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public TestHomeRequestCommand(TestSubsystem subsystem) {
    m_subsystem = subsystem;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_subsystem.setHomeRequest(true);
    SmartDashboard.putBoolean("Home Triggered", true);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrHometed) {
    m_subsystem.setHomeRequest(false);
    SmartDashboard.putBoolean("Home Triggered", false);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_subsystem.isAtPosition(TestConstants.HomePosition);
  }
}
