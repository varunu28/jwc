package com.jwc;

import com.jwc.compute.ProcessInput;
import com.jwc.model.WordCountInput;
import com.jwc.model.WordCountOutput;
import picocli.CommandLine;

import java.io.File;
import java.util.concurrent.Callable;

@CommandLine.Command(
        name = "jwc",
        mixinStandardHelpOptions = true,
        version = " jwc 1.0",
        description = "A Java implementation of Linux word count utility.")
public class WordCount implements Callable<Integer> {

    @CommandLine.Parameters(index = "0", description = "file whose word count needs to be calculated")
    private File file;

    @CommandLine.Option(names = {"-w", "--words"}, description = "word count")
    private boolean wordEnabled;

    @CommandLine.Option(names = {"-l", "--lines"}, description = "line count")
    private boolean lineEnabled;

    @CommandLine.Option(names = {"-b", "--bytes"}, description = "byte count")
    private boolean byteEnabled;

    @CommandLine.Option(names = {"-c", "--characters"}, description = "character count")
    private boolean characterEnabled;

    @Override
    public Integer call() {
        WordCountInput wordCountInput = buildWordCountInput();
        WordCountOutput wordCountOutput = new ProcessInput().processFile(file, wordCountInput);
        System.out.println(formatOutput(wordCountOutput, wordCountInput));
        return 0;
    }

    private String formatOutput(WordCountOutput wordCountOutput, WordCountInput wordCountInput) {
        StringBuilder sb = new StringBuilder();
        if (wordCountInput.words()) {
            sb.append(" words: ").append(wordCountOutput.words());
        }
        if (wordCountInput.characters()) {
            sb.append(" characters: ").append(wordCountOutput.characters());
        }
        if (wordCountInput.lines()) {
            sb.append(" lines: ").append(wordCountOutput.lines());
        }
        if (wordCountInput.bytes()) {
            sb.append(" bytes: ").append(wordCountOutput.bytes());
        }
        sb.append(" ").append(file.getPath());
        return sb.toString();
    }

    private WordCountInput buildWordCountInput() {
        if (!wordEnabled && !lineEnabled && !byteEnabled && !characterEnabled) {
            return WordCountInput.builder()
                    .characters(true)
                    .words(true)
                    .bytes(true)
                    .lines(true)
                    .build();
        }
        WordCountInput.WordCountInputBuilder builder = WordCountInput.builder();
        if (wordEnabled) {
            builder.words(true);
        }
        if (lineEnabled) {
            builder.lines(true);
        }
        if (byteEnabled) {
            builder.bytes(true);
        }
        if (characterEnabled) {
            builder.characters(true);
        }
        return builder.build();
    }

    public static void main(String[] args) {
        System.exit(new CommandLine(new WordCount()).execute(args));
    }
}
