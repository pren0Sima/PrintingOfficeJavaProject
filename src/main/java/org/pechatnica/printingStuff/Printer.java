package org.pechatnica.printingStuff;

import org.pechatnica.printingOfficeStuff.PrintingOffice;
import org.pechatnica.exceptions.PrinterLoadedWithWrongPaper;
import org.pechatnica.exceptions.PrinterNotCompatibleByColor;
import org.pechatnica.exceptions.PrinterNotLoadedWithEnoughPaperException;
import org.pechatnica.exceptions.TryingToLoadPrinterWithTooManyPagesException;
import org.pechatnica.prints.Print;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

public class Printer implements Comparable <Printer> {
    private UUID factoryNumber;
    private int maxNumberOfPages;
    private boolean colored;
    private int pagesPerMinute;
    private int currentNumberOfPages;
    private Page pageVariationLoaded;
    private PrintingOffice printingOffice;
    public Printer(int maxNumberOfPages, boolean colored, int pagesPerMinute, Page pageVariationLoaded) {
        factoryNumber = UUID.randomUUID();
        this.maxNumberOfPages = maxNumberOfPages;
        this.colored = colored;
        this.pagesPerMinute = pagesPerMinute;
        this.currentNumberOfPages = 0;
        this.pageVariationLoaded = pageVariationLoaded;
        this.printingOffice = null;
    }
    public Printer(int maxNumberOfPages, boolean colored, int pagesPerMinute) {
        this.maxNumberOfPages = maxNumberOfPages;
        this.colored = colored;
        this.pagesPerMinute = pagesPerMinute;
        factoryNumber = UUID.randomUUID();
    }

    public int getMaxNumberOfPages() {
        return maxNumberOfPages;
    }

    public void setMaxNumberOfPages(int maxNumberOfPages) {
        this.maxNumberOfPages = maxNumberOfPages;
    }

    public boolean isColored() {
        return colored;
    }

    public void setColored(boolean colored) {
        this.colored = colored;
    }

    public int getPagesPerMinute() {
        return pagesPerMinute;
    }

    public void setPagesPerMinute(int pagesPerMinute) {
        this.pagesPerMinute = pagesPerMinute;
    }

    public int getCurrentNumberOfPages() {
        return currentNumberOfPages;
    }

    public void setCurrentNumberOfPages(int currentNumberOfPages) {
        this.currentNumberOfPages = currentNumberOfPages;
    }

    public PrintingOffice getPrintingOffice() {
        return printingOffice;
    }

    public void setPrintingOffice(PrintingOffice printingOffice) {
        this.printingOffice = printingOffice;
    }

    public UUID getFactoryNumber() {
        return factoryNumber;
    }

    public void setFactoryNumber(UUID factoryNumber) {
        this.factoryNumber = factoryNumber;
    }

    public Page getPageVariationLoaded() {
        return pageVariationLoaded;
    }
    public void setPageVariationLoaded(Page pageVariationLoaded) {
        this.pageVariationLoaded = pageVariationLoaded;
    }

    public void loadPrinterWithPages(Page pageVariation, int quantity)
            throws TryingToLoadPrinterWithTooManyPagesException {
        // 1 variant : the same page (pageVariation = this.pageVariation)
        if (pageVariation.equals(this.pageVariationLoaded)){
            // 1.1. variant - trying to add too many pages
            // (currentNumberOfPages + this.currentNumberOfPages > maxNumberOfPages)
            if (quantity + this.currentNumberOfPages > maxNumberOfPages){
                throw new TryingToLoadPrinterWithTooManyPagesException("Trying to load too many pages. " +
                        "Maximum amount that could be added is " + (maxNumberOfPages - this.currentNumberOfPages) + " pages.");
            }
            // 1.2. variant - adding not too many pages
            // (currentNumberOfPages + this.currentNumberOfPages <= maxNumberOfPages)
            else {
                printingOffice.takePagesFromInventory(pageVariation, quantity);
                setCurrentNumberOfPages(quantity + this.currentNumberOfPages);
            }
        }
        // 2 variant : different page variations (pageVariation != this.pageVariation)
        else {
            // we return these pages to the inventory ->
            printingOffice.returnPagesToInventory(this.pageVariationLoaded, this.currentNumberOfPages);
            // 2.1. - not too many pages (currentNumberOfPages <= maxNumberOfPages)
            if(quantity <= maxNumberOfPages){
                // we set the current number of pages in the printer to 0 - can do without it.
                this.setCurrentNumberOfPages(0);
                // we take the correct page variation we need and needed amount
                printingOffice.takePagesFromInventory(pageVariation, quantity);
                this.setCurrentNumberOfPages(quantity);
                this.setPageVariationLoaded(pageVariation);
            }
            // 2.2. - too many pages (currentNumberOfPages > maxNumberOfPages)
            if (quantity > maxNumberOfPages){
                throw new TryingToLoadPrinterWithTooManyPagesException("Trying to load too many pages. " +
                        "Maximum amount that could be added is " + maxNumberOfPages + " pages.");
            }
        }
    }

    // if there are enough pages, the printer prints. After that the number of pages is decreased
    public void letPrinterPrint(Print print, Page pageVariation, boolean isColored)
            throws PrinterNotLoadedWithEnoughPaperException, PrinterLoadedWithWrongPaper, PrinterNotCompatibleByColor, IOException {
        if (this.isColored() != isColored){
            throw new PrinterNotCompatibleByColor("This printer does" + (isColored?" ":" not ") + "print in color.");
        }
        // successful prints are here
        if (currentNumberOfPages >= print.getNumberOfPages() && pageVariation.equals(this.pageVariationLoaded)) {
            getPrintingOffice().addToIncome(getPrintingOffice().calculatePrintPriceWithOverchargeAndDiscount(print, pageVariation));
            currentNumberOfPages -= print.getNumberOfPages();
            getPrintingOffice().setNumberOfPrintedPrints(getPrintingOffice().getNumberOfPrintedPrints() + 1);
            // add print to printedPrintsReport - yes
            getPrintingOffice().addPrintToPrintedPrintsReport(print, pageVariation);
        }
        else if (currentNumberOfPages >= print.getNumberOfPages() && !pageVariation.equals(this.pageVariationLoaded)){
            throw new PrinterLoadedWithWrongPaper("This printer is not loaded with " + pageVariation + ". " +
                    "You may try reloading this printer with the correct variation using loadPrinterWithPages.");
        }
        else {
            throw new PrinterNotLoadedWithEnoughPaperException("Not enough paper in the printer. You may refill it with "
                    + (print.getNumberOfPages() - currentNumberOfPages) + " pieces of paper.");
        }
    }
    @Override
    public int hashCode() {
        return Objects.hash(factoryNumber);
    }

    @Override
    public boolean equals(Object o) {
         if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Printer printer = (Printer) o;
        return factoryNumber.equals(printer.factoryNumber);
    }

    @Override
    public int compareTo(Printer o) {
        int fNComparison = this.factoryNumber.compareTo(o.factoryNumber);
        // if they aren't equal, return;
        if (fNComparison != 0) {
            return fNComparison;
        }
        return 0;
    }

    @Override
    public String toString() {
        return "Printer{" +
                "factoryNumber=" + factoryNumber +
                ", maxNumberOfPages=" + maxNumberOfPages +
                ", colored=" + colored +
                ", pagesPerMinute=" + pagesPerMinute +
                ", currentNumberOfPages=" + currentNumberOfPages +
                ", pageVariationLoaded=" + pageVariationLoaded +
                '}';
    }
}

