package com.lolcode.app.domain;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

/**
 * @author Mykhailo Balakhon
 * @link <a href="mailto:mykhailo.balakhon@communify.us">mykhailo.balakhon@communify.us</a>
 */
public class FileSourceCode implements SourceCode {
    private final Path path;
    private final List<String> lines;

    public FileSourceCode(String fileName) {
        try {
            path = Paths.get(fileName);
            lines = Collections.unmodifiableList(Files.readAllLines(path));
        } catch (IOException e) {
            throw new IllegalArgumentException("Can't read file: " + fileName, e);
        }
    }

    @Override
    public String getModuleName() {
        return path.getFileName().toString();
    }

    @Override
    public List<String> getLines() {
        return lines;
    }
}
