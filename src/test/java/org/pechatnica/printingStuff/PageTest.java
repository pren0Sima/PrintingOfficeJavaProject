package org.pechatnica.printingStuff;

import org.junit.jupiter.api.Test;
import org.pechatnica.enumerations.PageSize;
import org.pechatnica.enumerations.PaperType;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class PageTest {
    @Test
    void getPagePriceTest() {
        // create a page.
        // A4(0.02), Standard_Paper(0.1)
        // the constructor calculates the price for it and saves it in pagePrice
        Page page = new Page(PageSize.A4, PaperType.STANDARD_PAPER);
        // pageSize.getSheetPrice().add(paperType.getPaperTypePrice())
        BigDecimal expectedPrice = BigDecimal.valueOf(0.12);
        BigDecimal actualPrice = page.getPagePrice();
        assertEquals(expectedPrice, actualPrice);
    }
}