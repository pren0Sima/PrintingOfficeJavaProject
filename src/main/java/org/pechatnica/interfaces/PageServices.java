package org.pechatnica.interfaces;

import org.pechatnica.printingStuff.Page;

public interface PageServices {
    // initializes the needed hashMap
    public void purchasedPagesInit();
    public void purchasePages(Page page, int quantity);
    public void takePagesFromInventory(Page page, int quantity);
    public void returnPagesToInventory(Page page, int quantity);
}
