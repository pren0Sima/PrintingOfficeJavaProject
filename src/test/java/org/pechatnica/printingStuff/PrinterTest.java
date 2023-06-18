package org.pechatnica.printingStuff;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.pechatnica.enumerations.PageSize;
import org.pechatnica.enumerations.PaperType;
import org.pechatnica.enumerations.PrintType;
import org.pechatnica.exceptions.PrinterLoadedWithWrongPaper;
import org.pechatnica.exceptions.PrinterNotCompatibleByColor;
import org.pechatnica.exceptions.PrinterNotLoadedWithEnoughPaperException;
import org.pechatnica.exceptions.TryingToLoadPrinterWithTooManyPagesException;
import org.pechatnica.prints.Print;

import static org.junit.jupiter.api.Assertions.*;

class PrinterTest {
    private Printer printer;
    @BeforeEach
    void init(){
        printer = new Printer(1000, true,
                30, new Page(PageSize.A4, PaperType.STANDARD_PAPER));
    }

    @Test
    @DisplayName("loadPrinterWithPages exception test")
    void loadPrinterWithPages() {
        assertThrows(TryingToLoadPrinterWithTooManyPagesException.class,
                () ->
                printer.loadPrinterWithPages(new Page(PageSize.A4, PaperType.STANDARD_PAPER), 1500));
    }

    @Test
    @DisplayName("letPrinterPrint Color Exception")
    void letPrinterPrintColorExceptionTest() {
        // Create the print
        Print print = new Print("Guest", 200, PageSize.A5, PrintType.BOOK);
        // Call the method and assert the exception
        assertThrows(PrinterNotCompatibleByColor.class, () -> printer.letPrinterPrint(
                print,
                new Page(PageSize.A5, PaperType.STANDARD_PAPER),
                false
        ));
    }
    @Test
    @DisplayName("letPrinterPrint Page variation exception")
    void letPrinterPrintPageVariationExceptionTest() {
        Print print = new Print("Guest", 200, PageSize.A5, PrintType.BOOK);
        printer.setCurrentNumberOfPages(250);
        // Call the method and assert the exception
        assertThrows(PrinterLoadedWithWrongPaper.class, () -> printer.letPrinterPrint(
                print,
                new Page(PageSize.A5, PaperType.STANDARD_PAPER),
                true
        ));
    }
    @Test
    @DisplayName("letPrinterPrint not enough paper in printer exception")
    void letPrinterPrintNotEnoughPaperExceptionTest() {
        Print print = new Print("Guest", 200, PageSize.A4, PrintType.BOOK);
        printer.setCurrentNumberOfPages(150);
        // Call the method and assert the exception
        assertThrows(PrinterNotLoadedWithEnoughPaperException.class, () -> printer.letPrinterPrint(
                print,
                new Page(PageSize.A4, PaperType.STANDARD_PAPER),
                true
        ));
    }
}