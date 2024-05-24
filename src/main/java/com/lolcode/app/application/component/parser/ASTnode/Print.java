package com.lolcode.app.application.component.parser.ASTnode;

import com.lolcode.app.application.component.parser.ParseType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class Print extends ASTNode {
    private ASTNode value;

    public Print(ASTNode value) {
        super(ParseType.Print);
        this.value = value;
    }

    @Override
    public String toString() {
        return "Print{" +
                "value=" + value +
                '}';
    }
}
