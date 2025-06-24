package frc.robot.constants;

public class TestConstants{
    // initial: 0
    // homeup: 5
    // homedown: 6
    // homed: 7
    // L1: 1
    // L2: 2
    // L3: 3
    // L4: 4

    public static final int kTestTalonID = 10;
    public static final int kFollowerTalonID = 11;
    public static final int kHallSensorID = 12;

    public static final boolean test_Neutalmode_Coast = true;

    public static final boolean test_Inverted_CounterClockwisePositive = true;

    public static final double test_kP = 0.1;
    public static final double test_kI = 0.0;
    public static final double test_kD = 0.0;
    public static final double test_kS = 0.0;
    public static final double test_kA = 0.0;
    public static final double test_kV = 0.0;
    
    public static final int test_CruiseVelocity = 1000;
    public static final int test_CruiseAcceleration = 1000;
    
    public static final double forwardDutyCycleLimit = 1.0;
    public static final double reverseDutyCycleLimit = -1.0;

    public static final boolean test_forwardSoftLimitEnable = false;
    public static final boolean test_reverseSoftLimitEnable = false;
    
    public static final double test_forwardSoftLimitThreshold = 0.0;
    public static final double test_reverseSoftLimitThreshold = 0.0;
    

    public static final double home_upTime = 0.5;
    public static final double home_upVoltage = 2.0;
    public static final double home_downTime = 3.0;
    public static final double home_downVoltage = -2.0;

    public static final double HomePosition = 0.0;
    public static final double DownPosition = 0.0;
    public static final double UpPosition = 0.0;
    
    public static final double positionDeadBand = 0.1;
}
