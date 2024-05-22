package com.lolcode.app.test.model;

import com.lolcode.app.domain.SourceCode;

import java.util.List;

public class TestSourceCode implements SourceCode {
    private final List<String> lines;
    
    public TestSourceCode(String code) {
        lines = List.of(code.split("\n"));
    }

    @Override
    public String getModuleName() {
        return "test_module";
    }

    @Override
    public List<String> getLines() {
        return lines;
    }
}
