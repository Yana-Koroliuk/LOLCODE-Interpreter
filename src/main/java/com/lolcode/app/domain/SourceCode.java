package com.lolcode.app.domain;

import java.util.List;

/**
 * @author Mykhailo Balakhon
 * @link <a href="mailto:mykhailo.balakhon@communify.us">mykhailo.balakhon@communify.us</a>
 */
public interface SourceCode {

    String getModuleName();
    
    List<String> getLines();
}
