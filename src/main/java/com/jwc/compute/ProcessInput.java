package com.jwc.compute;

import com.jwc.exception.WordCountException;
import com.jwc.model.WordCountInput;
import com.jwc.model.WordCountOutput;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ProcessInput {

    public WordCountOutput processFile(File file, WordCountInput wordCountInput) {
        try {
            String content = new String(Files.readAllBytes(Paths.get(file.getPath())));
            return WordCountOutput.builder()
                    .words(wordCountInput.words() ? content.split("\\s+").length : 0)
                    .lines(wordCountInput.lines() ? content.split("\n").length : 0)
                    .bytes(wordCountInput.bytes() ? content.getBytes().length : 0)
                    .characters(wordCountInput.characters() ? content.length() : 0)
                    .build();
        } catch (IOException e) {
            throw new WordCountException("error while processing the file: " + e);
        }
    }
}
