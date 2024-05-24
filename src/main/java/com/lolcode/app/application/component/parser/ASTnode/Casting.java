package com.lolcode.app.application.component.parser.ASTnode;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Casting extends ASTNode {
    private ASTNode value;
    private String castTo;

    @Override
    public String toString() {
        return "Casting{" +
                "value=" + value +
                ", castTo='" + castTo + '\'' +
                '}';
    }
}

