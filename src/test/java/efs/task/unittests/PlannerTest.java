package efs.task.unittests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;

class PlannerTest {

    private Planner planner;

    @BeforeEach
    void setUp() {
        planner = new Planner();
    }

    @ParameterizedTest(name = "ActivityLevel={0}")
    @EnumSource(ActivityLevel.class)
    void shouldReturnCorrectCaloriesDemandForUser(ActivityLevel level) {
        // Given
        User user = TestConstants.TEST_USER;

        // When
        int caloriesDemand = planner.calculateDailyCaloriesDemand(user, level);

        // Then
        assertEquals(TestConstants.CALORIES_ON_ACTIVITY_LEVEL.get(level), caloriesDemand);
    }

    @Test
    void shouldCalculateDailyIntake() {
        // Given
        User user = TestConstants.TEST_USER;
        DailyIntake expectedIntake = TestConstants.TEST_USER_DAILY_INTAKE;

        // When
        DailyIntake actualIntake = planner.calculateDailyIntake(user);

        // Then
        assertEquals(expectedIntake.getCalories(), actualIntake.getCalories());
        assertEquals(expectedIntake.getProtein(), actualIntake.getProtein());
        assertEquals(expectedIntake.getFat(), actualIntake.getFat());
        assertEquals(expectedIntake.getCarbohydrate(), actualIntake.getCarbohydrate());
    }
}