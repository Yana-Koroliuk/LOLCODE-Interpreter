package com.lolcode.app.application.component.parser.ASTnode;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EndProgram extends ASTNode {
    @Override
    public String toString() {
        return "EndProgram{}";
    }
}

