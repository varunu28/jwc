package com.jwc.compute;

import com.jwc.exception.WordCountException;
import com.jwc.model.WordCountInput;
import com.jwc.model.WordCountOutput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ProcessInputTest {

    private static final File file = new File("data.txt");

    private static final long EXPECTED_BYTE_COUNT = 334694;
    private static final long EXPECTED_WORD_COUNT = 58159;
    private static final long EXPECTED_LINE_COUNT = 7136;

    private static final long EXPECTED_CHARACTER_COUNT = 331980;

    private ProcessInput processInput;

    @BeforeEach
    void setUp() {
        processInput = new ProcessInput();
    }

    @Test
    public void ioExceptionForNonExistentFileSuccess() throws IOException {
        // Arrange
        File nonExistentFile = new File("non_existent_file.txt");

        // Act & assert
        assertThrows(WordCountException.class,
                () -> processInput.processFile(nonExistentFile, WordCountInput.builder().build()));
    }

    @ParameterizedTest
    @MethodSource("provideProcessFileInput")
    public void processSuccess(WordCountInput wordCountInput,
                               WordCountOutput expectedWordCountOutput) {
        // Act
        WordCountOutput wordCountOutput = processInput.processFile(file, wordCountInput);

        // Assert
        assertEquals(expectedWordCountOutput.bytes(), wordCountOutput.bytes());
        assertEquals(expectedWordCountOutput.lines(), wordCountOutput.lines());
        assertEquals(expectedWordCountOutput.characters(), wordCountOutput.characters());
        assertEquals(expectedWordCountOutput.words(), wordCountOutput.words());
    }

    private static Stream<Arguments> provideProcessFileInput() {
        return Stream.of(
                Arguments.of(
                        WordCountInput.builder()
                                .words(true)
                                .lines(true)
                                .characters(true)
                                .bytes(true)
                                .build(),
                        WordCountOutput.builder()
                                .lines(EXPECTED_LINE_COUNT)
                                .words(EXPECTED_WORD_COUNT)
                                .characters(EXPECTED_CHARACTER_COUNT)
                                .bytes(EXPECTED_BYTE_COUNT)
                                .build()),
                Arguments.of(
                        WordCountInput.builder()
                                .words(true)
                                .build(),
                        WordCountOutput.builder().words(EXPECTED_WORD_COUNT).build()),
                Arguments.of(
                        WordCountInput.builder()
                                .lines(true)
                                .build(),
                        WordCountOutput.builder().lines(EXPECTED_LINE_COUNT).build()),
                Arguments.of(
                        WordCountInput.builder()
                                .characters(true)
                                .build(),
                        WordCountOutput.builder().characters(EXPECTED_CHARACTER_COUNT).build()),
                Arguments.of(
                        WordCountInput.builder()
                                .bytes(true)
                                .build(),
                        WordCountOutput.builder().bytes(EXPECTED_BYTE_COUNT).build())
        );
    }
}