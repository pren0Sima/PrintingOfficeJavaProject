package org.pechatnica.printingStuff;

import org.pechatnica.enumerations.PageSize;
import org.pechatnica.enumerations.PaperType;

import java.math.BigDecimal;
import java.util.Objects;

public class Page implements Comparable<Page> {
    private PageSize pageSize;
    private PaperType paperType;
    private BigDecimal pagePrice;

    public Page(PageSize pageSize, PaperType paperType) {
        this.pageSize = pageSize;
        this.paperType = paperType;
        this.pagePrice = pageSize.getSheetPrice().add(paperType.getPaperTypePrice());
    }

    public PageSize getPageSize() {
        return pageSize;
    }

    public void setPageSize(PageSize pageSize) {
        this.pageSize = pageSize;
    }

    public PaperType getPaperType() {
        return paperType;
    }

    public void setPaperType(PaperType paperType) {
        this.paperType = paperType;
    }

    public BigDecimal getPagePrice() {
        return pagePrice;
    }

    public void setPagePrice(BigDecimal pagePrice) {
        this.pagePrice = pagePrice;
    }

    @Override
    public int compareTo(Page o) {
        int pageSizeComparison = this.pageSize.compareTo(o.pageSize);
        // if they aren't equal, return;
        if (pageSizeComparison != 0) {
            return pageSizeComparison;
        }
        // pageSize are equal, compare paperType
        return this.paperType.compareTo(o.paperType);

    }
    // Necessary for manipulating the hash map with the pages!!!
    @Override
    public int hashCode() {
        return Objects.hash(pageSize, paperType);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Page page = (Page) o;
        return pageSize.equals(page.pageSize) &&
                paperType.equals(paperType);
    }

    @Override
    public String toString() {
        return "Page{" +
                "pageSize=" + pageSize +
                ", paperType=" + paperType +
                ", pagePrice=" + pagePrice +
                '}';
    }
}


