package org.pechatnica.prints;

import org.pechatnica.enumerations.PageSize;
import org.pechatnica.enumerations.PrintType;

import java.math.BigDecimal;

public class Print {
    private String title;
    private int numberOfPages;
    // A1, A2, A3, A4, A5
    private PageSize pageSize;
    // BOOK, NEWSPAPER, POSTER
    private PrintType printType;
    // constructor
    public Print() {
        // title = null; numberOfPages = 0; pageSize = null;
    }

    public Print(String title, int numberOfPages, PageSize pageSize, PrintType printType) {
        this.title = title;
        this.numberOfPages = numberOfPages;
        this.pageSize = pageSize;
        this.printType = printType;
    }

    // getters and setters
    public String getTitle() {
        return title;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public PageSize getPageSize() {
        return pageSize;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public void setPageSize(PageSize pageSize) {
        this.pageSize = pageSize;
    }

    public PrintType getPrintType() {
        return printType;
    }

    public void setPrintType(PrintType printType) {
        this.printType = printType;
    }

    public BigDecimal calculatePriceByPagesAndPageSize() {
        return pageSize.getSheetPrice().multiply(BigDecimal.valueOf(numberOfPages));
    }

    @Override
    public String toString() {
        return "Print{" +
                "title='" + title + '\'' +
                ", numberOfPages=" + numberOfPages +
                ", pageSize=" + pageSize +
                ", printType=" + printType +
                '}';
    }
}

