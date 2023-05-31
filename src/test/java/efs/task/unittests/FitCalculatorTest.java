package efs.task.unittests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FitCalculatorTest {

    @Test
    void shouldReturnTrue_whenDietRecommended() {
        //given
        double weight = 89.2;
        double height = 1.72;

        //when
        boolean recommended = FitCalculator.isBMICorrect(weight, height);

        //then
        assertTrue(recommended);
    }

    @Test
    void shouldReturnFalse_whenBMICorrectIsFalse() {
        // Given
        double weight = 69.5;
        double height = 1.72;

        // When
        boolean bmiCorrect = FitCalculator.isBMICorrect(weight, height);

        // Then
        assertFalse(bmiCorrect);
    }

    @Test
    void shouldThrowException_whenHeightIsZero() {
        // Given
        double weight = 69.5;
        double height = 0.0;

        // Then
        assertThrows(IllegalArgumentException.class, () -> {
            // When
            FitCalculator.isBMICorrect(weight, height);
        });
    }

    @ParameterizedTest(name = "Weight={0}, BMI correct")
    @ValueSource(doubles = {100.0, 150.0, 200.0})
    void shouldReturnTrue_whenBMICorrectForDifferentWeights(double weight) {
        // Given
        double height = 1.72;

        // When
        boolean bmiCorrect = FitCalculator.isBMICorrect(weight, height);

        // Then
        assertTrue(bmiCorrect);
    }

    @ParameterizedTest(name = "Weight={0}, BMI incorrect")
    @CsvSource({
            "50.0, 1.5",
            "60.0, 1.6",
            "70.0, 1.7"
    })
    void shouldReturnFalse_whenBMICorrectForDifferentWeightAndHeight(double weight, double height) {
        // When
        boolean bmiCorrect = FitCalculator.isBMICorrect(weight, height);

        // Then
        assertFalse(bmiCorrect);
    }

    @ParameterizedTest(name = "Weight={0}, Height={1}, BMI incorrect")
    @CsvFileSource(resources = "/data.csv", numLinesToSkip = 1)
    void shouldReturnFalse_whenBMICorrectForDifferentWeightAndHeightFromFile(double weight, double height) {
        // When
        boolean bmiCorrect = FitCalculator.isBMICorrect(weight, height);

        // Then
        assertFalse(bmiCorrect);
    }

    @Test
    void shouldReturnUserWithWorstBMI_whenListOfUsersProvided() {
        // Given
        User user1 = new User(1.8, 85.0);
        User user2 = new User(1.65, 72.5);
        User user3 = new User(1.79, 97.3);
        List<User> userList = Arrays.asList(user1, user2, user3);

        // When
        User userWithWorstBMI = FitCalculator.findUserWithTheWorstBMI(userList);

        // Then
        assertEquals(97.3, userWithWorstBMI.getWeight());
        assertEquals(1.79, userWithWorstBMI.getHeight());
    }

    @Test
    void shouldReturnNull_whenEmptyListOfUsersProvided() {
        // Given
        List<User> userList = Collections.emptyList();

        // When
        User userWithWorstBMI = FitCalculator.findUserWithTheWorstBMI(userList);

        // Then
        assertNull(userWithWorstBMI);
    }

    @Test
    void shouldReturnExpectedBMIScore_whenCalculatingForListOfUsers() {
        // Given
        List<User> userList = TestConstants.TEST_USERS_LIST;

        // When
        double[] bmiScore = FitCalculator.calculateBMIScore(userList);

        // Then
        assertArrayEquals(TestConstants.TEST_USERS_BMI_SCORE, bmiScore);
    }
}