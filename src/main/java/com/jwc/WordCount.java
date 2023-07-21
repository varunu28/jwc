package com.jwc;

import com.jwc.model.WordCountInput;
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

    @CommandLine.Option(names = {"-w"}, description = "word count")
    private boolean wordEnabled;

    @CommandLine.Option(names = {"-l"}, description = "line count")
    private boolean lineEnabled;

    @CommandLine.Option(names = {"-b"}, description = "byte count")
    private boolean byteEnabled;

    @CommandLine.Option(names = {"-c"}, description = "character count")
    private boolean characterEnabled;

    @Override
    public Integer call() throws Exception {
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
        WordCountInput wordCountInput = builder.build();
        System.out.println(wordCountInput);
        return 0;
    }

    public static void main(String[] args) {
        System.exit(new CommandLine(new WordCount()).execute(args));
    }
}
