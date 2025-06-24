package frc.robot.subsystems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.Map;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.ElevatorConstants;
import frc.robot.constants.TestConstants;

public class TestStatemachine extends SubsystemBase {
    //define the functions of the elevator
    
    // Define the states for the elevator
    protected enum TestState {
        INITIAL,
        HOME,
        UP,
        DOWN
    }

    private TestState currentState;

    private boolean isStateUpdating = false;

    private final Map<TestState, List<TestTransition>> transitionMap = new HashMap<>();

    protected TestSubsystem subsystem;

    private TestTransition moveDown = new TestTransition(
        TestState.DOWN, 
        subsystem::getDownRequest, 
        () -> {subsystem.setPosition(TestConstants.DownPosition);}, 
        () -> subsystem.isAtPosition(TestConstants.DownPosition), 
        () -> false, 
        30
    );

    private TestTransition moveUp = new TestTransition(
        TestState.UP, 
        subsystem::getUpRequest, 
        () -> {subsystem.setPosition(TestConstants.UpPosition);}, 
        () -> subsystem.isAtPosition(TestConstants.UpPosition), 
        () -> false, 
        30
    );

    private TestTransition moveHome = new TestTransition(
        TestState.HOME, 
        subsystem::getHomeRequest, 
        () -> {subsystem.setPosition(TestConstants.HomePosition);}, 
        () -> subsystem.isAtPosition(TestConstants.HomePosition), 
        () -> false, 
        30
    );

    public TestStatemachine() {
        currentState = TestState.INITIAL;
        for (TestState state : TestState.values()) {
            transitionMap.putIfAbsent(state, new ArrayList<>());
        }
        //transitionMap.get(TestState.INITIAL).add();
        //TODO: Implement commands in each stage
    }

    public TestState getCurrentState() {
        return currentState;
    }

    public void setState(TestState newState) {
        currentState = newState;
    }

    public void update() {
        this.isStateUpdating = false;
        List<TestTransition> transitions = transitionMap.getOrDefault(currentState, List.of());
        for (TestTransition t : transitions) {
            if (t.isTriggered()) {
                this.isStateUpdating = true;
                t.startTimer();
                t.performTransitionAction();

                if (t.isSuccess()) {
                    this.currentState = t.getNextState();
                }
                if (t.isExpired()) {
                    this.currentState = t.getNextState();
                }
            }
        }
    }

    @Override
    public void periodic() {
    update();
    if(!isStateUpdating){
        switch (currentState) {
            case INITIAL:
                subsystem.setNeutral();
                break;
            
            case UP:
                subsystem.setNeutral();
                break;

            case DOWN:
                subsystem.setNeutral();
                break;

            default:
                subsystem.setNeutral();
                break;
            }
        }
    }
}

/*
class Transition05 extends ElevatorTransition {

    private ElevatorSubsystem elevator;
    private double startTime = -1.0;

    public Transition05(ElevatorSubsystem elevator) {
        super(ElevatorState.INITIAL);
        this.elevator = elevator;
        startTime = Timer.getFPGATimestamp();
    }

    @Override
    public boolean isTriggered() {
        return elevator.isHallSensorTriggered() && elevator.getHomeRequest();
    }

    @Override
    public void startTimer() {
        startTime = Timer.getFPGATimestamp();
    }

    @Override
    public void performTransitionAction() {
        elevator.setVoltage(ElevatorConstants.home_upVoltage);
    }

    @Override
    public boolean isSuccess() {
        return !elevator.isHallSensorTriggered();
    }

    @Override
    public boolean isExpired() {
        return (Timer.getFPGATimestamp() - startTime) > ElevatorConstants.home_upTime;
    }

    @Override
    public ElevatorState getNextState() {
        if (isSuccess()) {
            return ElevatorState.HOME_DOWN;
        } else {
            return ElevatorState.INITIAL;
        }
    }
}
    */