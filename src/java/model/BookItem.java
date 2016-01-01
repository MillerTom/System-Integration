package model;


/*
 * Due to Parsing details each BookItem must be initialized with ISBN before
 * BookItem set to ResponseSaxHandler, first Model create instances from
 * BookItem when its constructor is called, when URL is built
 * each BookItem is initialized with one of the requested isbns
 *
 */
// <editor-fold defaultstate="collapsed" desc=" UML Marker ">
// #[regen=yes,id=DCE.59C73350-4D52-43E6-B503-4BD7141AC496]
// </editor-fold>
public class BookItem {

    private String sellerSku = null;
    private String isbn = null;
    private float price = 0;
    private int qty = 0;

    public BookItem() {
    }

    //====== Setters =======
    public void setSellerSku(String sellerSku) {
        this.sellerSku = sellerSku;
    }

    public void setQty(int q) {
        this.qty = q;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setPrice(float price) {
        this.price = price;
    }   

    //====== Getters =======
    public String getSellerSku() {
        return this.sellerSku;
    }

     public String getIsbn() {
        return this.isbn;
    }

    public int getQty() {
        return qty;
    }

    public float getPrice() {
        return price;
    }
       
}
