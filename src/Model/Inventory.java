package Model;

import java.util.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The Inventory class holds lists of both Parts and Products that
 * are currently in stock. All members belonging to the Inventory
 * class are static.
 *
 * @author Sakae Watanabe
 */
public class Inventory {

  //===========================================================================
  // Class members
  //===========================================================================
  /** An ObservableList containing all Parts currently in stock */
  private static ObservableList<Part> allParts;
  /** An ObservableList containing all Products currently in stock */
  private static ObservableList<Product> allProducts;

  // TODO Add static methods for loading test data using public method. We can either
  // place this in main before launch(args) OR wrap it in a static call within this class
  // and it will be called the first time the data is attempted to be accessed by the tableView.

  //===========================================================================
  // Part list methods
  //===========================================================================

  /**
   * @param newPart A Part to be added to list of available Parts.
   */
  public static void addPart(Part newPart) {
    allParts.add(newPart);
  }

  /**
   * The updatePart method will set the given index to a reference to the
   * provided selectedPart.
   *
   * @param index The index location of the selected part in allParts.
   * @param selectedPart A Part object representing the new object for given index.
   */
  public static void updatePart(int index, Part selectedPart) {
    allParts.set(index, selectedPart);
  }

  /**
   * The deletePart method will remove the first instance of selectedPart
   * from the allParts list.
   *
   * @param selectedPart A Part to be removed from the allParts list.
   * @return A boolean indicating whether or not part was removed from the list.
   */
  public boolean deletePart(Part selectedPart) {
    boolean deleted = false;
    if(!allParts.isEmpty()) {
      for (Part p : allParts) {
        if (p.getId() == selectedPart.getId()) {
          allParts.remove(p);
          deleted = true;
          break;
        }
      }
    }
    return deleted;
  }

  /**
   * @return An ObservableList containing all Parts currently in stock.
   */
  public static ObservableList<Part> getAllParts() {
    return allParts;
  }

  /**
   * The lookup part method will lookup part based on ID and return a
   * reference to the Part.
   *
   * @param partId An integer value representing the partID.
   * @return part A reference to the matching Part object belonging to list. Returns
   *  null if the part id not found.
   */
  public static Part lookupPart(int partId){

    if (!allParts.isEmpty()) {
      for (Part p : allParts) {
        if (p.getId() == partId) {
          return p;
        }
      }
    }
    return null;
  }

  /**
   * The lookupPart method will lookup part based on name and return an ObservableList
   * containing the matches to the given name.
   * TODO: Experienced issue with FXCollections().observableArrayList -> syntax error.
   * @param partName A String value representing the partName.
   * @return foundParts An observable list of parts matching the name. Returns null if no matches.
   */
  public static ObservableList<Part> lookupPart(String partName){
    ObservableList<Part> foundParts = FXCollections.observableArrayList();
    if (!allParts.isEmpty()) {
      for (Part p : allParts) {
        if (p.getName().equals(partName)) {
          foundParts.add(p);
        }
      }
      return foundParts;
    }
    return null;
  }

  //================================================================================
  // Product list methods
  //================================================================================
  /**
   * @param newProduct A Product to be added to list of available Products.
   */
  public static void addProduct(Product newProduct) {
    allProducts.add(newProduct);
  }

  /**
   * The updateProduct method will set the given index to a reference to the
   * provided newProduct.
   *
   * @param index The index location of the selected product in allProducts.
   * @param newProduct A Product object representing the new object for given index.
   */
  public static void updateProduct(int index, Product newProduct) {
    allProducts.set(index, newProduct);
  }

  /**
   * The deleteProduct method will remove the first instance of selectedProduct
   * from the allProducts list. Associated parts list for selectedProduct must be
   * empty to complete the operation.
   *
   * @param selectedProduct A Product to be removed from the allProducts list.
   * @return True if the Product was successfully deleted, False if product was unable to
   *  be deleted.
   * TODO: Consider throwing an exception if parts still in associated parts list.
   */
  public boolean deleteProduct(Product selectedProduct) {
    boolean deleted = false;
    if(!allProducts.isEmpty()) {
      for (Product p : allProducts) {
        if (p.getId() == selectedProduct.getId()) {
          if (p.getAllAssociatedParts().isEmpty()){
            allProducts.remove(p);
            deleted = true;
            break;
          }
          break;
        }
      }
    }
    return deleted;
  }

  /**
   * @return An ObservableList containing all Products currently in stock.
   */
  public ObservableList<Product> getAllProducts() {
    return allProducts;
  }

  /**
   * The lookupProduct method will lookup product based on ID and return a
   * reference to the Product.
   *
   * @param productId An integer value representing the partID.
   * @return part A reference to the matching Part object belonging to list. Returns
   *  null if the product id not found.
   */
  public static Product lookupProduct(int productId){

    if (!allProducts.isEmpty()) {
      for (Product p : allProducts) {
        if (p.getId() == productId) {
          return p;
        }
      }
    }
    return null;
  }

  /**
   * The lookup part method will lookup part based on name and return an ObservableList
   * containing the matches to the given name..
   *
   * @param productName A String value representing the productName.
   * @return foundProducts An observable list of products matching the name. Returns null if no matches.
   */
  public static ObservableList<Product> lookupProduct(String productName){
    ObservableList<Product> foundProducts = FXCollections.observableArrayList();
    if (!allProducts.isEmpty()) {
      for (Product p : allProducts) {
        if (p.getName().equals(productName)) {
          foundProducts.add(p);
        }
      }
      return foundProducts;
    }
    return null;
  }


}
