package org.pechatnica.printingOfficeStuff;

import org.pechatnica.printingStuff.Page;

import java.util.HashMap;

public class PageInventory {
    protected HashMap<Page, Integer> purchasedPages;
    public PageInventory() {
        // by default, it allocates 16 spaces.
        this.purchasedPages = new HashMap<>();
    }
    public HashMap<Page, Integer> getPurchasedPages() {
        return purchasedPages;
    }
    public void setPurchasedPages(HashMap<Page, Integer> purchasedPages) {
        this.purchasedPages = purchasedPages;
    }
}
