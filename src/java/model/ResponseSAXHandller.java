package model;

import java.sql.SQLException;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.*;

public class ResponseSAXHandller extends DefaultHandler {

    BookItem[] bookItems = new BookItem[100];
    int i = 0;
    private static ResponseSAXHandller instance = new ResponseSAXHandller();

    public static ResponseSAXHandller getInstance() {
        return instance;
    }
    public String tableName;
    private  DataBaseHandler db;
    private StringBuilder skuString = new StringBuilder();
    private StringBuilder itemIDSt = new StringBuilder();
    private StringBuilder tempString = new StringBuilder();
    private StringBuilder priceStr = new StringBuilder();
    private StringBuilder quantityStr = new StringBuilder();

//    /** the parser will ignore any parsed data until PARSING status is set. */
//    private enum ParseStatus {
//
//        /**Indicates that this class is not ignoring tags and it is parsing*/
//        PARSING,
//        /**Indicates that this class is ignoring tags and it is NOT parsing*/
//        IGNORE
//    }
    protected ResponseSAXHandller() {
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setDatabase( DataBaseHandler db) {
        this.db = db;
    }

    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {

        tempString.setLength(0);

    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {

        tempString.append(ch, start, length);

    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
        if (localName.equals("SKUDetails")) {
//            try {
            //insert data to data base
//                //System.out.println(" sku = "+skuString + " price = "+ priceStr +"qty = "+ quantityStr);
            //System.out.println("before insert in bookItem i = " + i);
            bookItems[i] = new BookItem();
            bookItems[i].setIsbn(skuString.substring(3));
            //System.out.println("after insert isbn in bookItem sku = " + skuString.substring(3));
            bookItems[i].setPrice(Float.parseFloat(priceStr.toString()));
            bookItems[i].setQty(Integer.parseInt(quantityStr.toString()));
            bookItems[i].setSellerSku(skuString.toString());
//                Start.getStartInstance().getDBHandler().excuteUpdate("insert into "+tableName+" (SellerSku,ISBN13,QTY,Price) values ('"+skuString+"',"+skuString.substring(3)+","+quantityStr+","+priceStr+")");
            i++;
            if (i == 100) {
                insertDataIntoDatabase();
                i = 0;
                bookItems = new BookItem[100];
            }
            skuString.setLength(0);
            priceStr.setLength(0);
            quantityStr.setLength(0);
//            } catch (Exception ex) {
//                //System.out.println("exception in ResponseSaxHandler :endElement " + ex);
//            }
        } else if (localName.equals("SKU")) {
//            //System.out.println(" sku before = " + skuString);
            skuString.append(tempString);
//            //System.out.println(" sku = " + skuString);
        } else if (localName.equals("Price")) {
            priceStr.append(tempString);
        } else if (localName.equals("Quantity")) {
            quantityStr.append(tempString);
        } else if (localName.equals("ItemID")) {
            itemIDSt.append(tempString);
        } else if (localName.equals("ActiveInventoryReport") && i > 0) {
            //insert data into db
            insertDataIntoDatabase();
            bookItems = new BookItem[100];
        }
    }

    public void insertDataIntoDatabase() {
        String values = "";
        for (int j = 0; j < i; j++) {
            values += "('" + bookItems[j].getSellerSku() + "'," + bookItems[j].getIsbn() + "," + bookItems[j].getQty() + "," + bookItems[j].getPrice() + "),";
        }
        i = 0;
        values = values.substring(0, values.length() - 1);
        try {
            db.excuteUpdate("insert into " + tableName + " (SellerSku,ISBN13,QTY,Price) values " + values);
        } catch (SQLException ex) {
            ex.printStackTrace();;
        }
    }
}
