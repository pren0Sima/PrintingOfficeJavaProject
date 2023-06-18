package org.pechatnica.enumerations;

import java.math.BigDecimal;

public enum PaperType {
    STANDARD_PAPER(BigDecimal.valueOf(0.1)),
    GLOSSY_PAPER(BigDecimal.valueOf(0.2)),
    NEWSPAPER_PAPER(BigDecimal.valueOf(0.3));
    private BigDecimal paperTypePrice;

    PaperType(BigDecimal paperTypePrice) {
        this.paperTypePrice = paperTypePrice;
    }

    public BigDecimal getPaperTypePrice() {
        return paperTypePrice;
    }

    public void setPaperTypePrice(BigDecimal paperTypePrice) {
        this.paperTypePrice = paperTypePrice;
    }

}
