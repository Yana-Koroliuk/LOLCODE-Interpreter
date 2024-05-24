package com.lolcode.app.application.component.parser.ASTnode;

import com.lolcode.app.application.component.parser.ParseType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class ASTNode {
    private ParseType type;
}
