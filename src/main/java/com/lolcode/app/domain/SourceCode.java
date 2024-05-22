package com.lolcode.app.domain;

import java.util.List;

public interface SourceCode {

    String getModuleName();
    
    List<String> getLines();
}
