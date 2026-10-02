package com.mrsac.lettervalidation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LetterStructure {

    private int dateLine;

    private int referenceLine;

    private int recipientLine;

    private int subjectLine;

    private int salutationLine;

    private int bodyStartLine;

    private int closingLine;

    private int senderLine;

}
