package org.pechatnica.enumerations;

import java.math.BigDecimal;

public enum PageSize {
    A1(BigDecimal.valueOf(0.05)), A2(BigDecimal.valueOf(0.04)),
    A3(BigDecimal.valueOf(0.03)), A4(BigDecimal.valueOf(0.02)), A5(BigDecimal.valueOf(0.01));
    private BigDecimal sheetPrice;
    // constructor
    PageSize(BigDecimal sheetPrice) {
        this.sheetPrice = sheetPrice;
    }
    // getter and setter
    public BigDecimal getSheetPrice() {
        return sheetPrice;
    }
    public void setSheetPrice(BigDecimal sheetPrice) {
        this.sheetPrice = sheetPrice;
    }

}
