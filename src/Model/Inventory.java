package Model;

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
  // Member Variables
  //===========================================================================
  /** An ObservableList containing all Parts currently in stock. */
  private static ObservableList<Part> allParts = FXCollections.observableArrayList();
  /** An ObservableList containing all Products currently in stock. */
  private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

  /** An integer value used by Inventory to assign new part ID numbers. */
  private static int nextPartID = 0;
  /** An integer value used by Inventory to assign new product ID numbers. */
  private static int nextProductID = 1000;
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
  public static boolean deletePart(Part selectedPart) {
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
        if (p.getName().toLowerCase().contains(partName.toLowerCase())) {
          foundParts.add(p);
        }
      }
      return foundParts;
    }
    return null;
  }

  /**
   * The getNextPartID method increments the current value of nextPartID and
   * returns an integer.
   *
   * @return The next part ID to be used for part being brought into inventory.
   */
  public static int getNextPartID() {
    return ++nextPartID;
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
  public static boolean deleteProduct(Product selectedProduct) {
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
  public static ObservableList<Product> getAllProducts() {
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
        if (p.getName().toLowerCase().contains(productName.toLowerCase())) {
          foundProducts.add(p);
        }
      }
      return foundProducts;
    }
    return null;
  }

  /**
   * The getNextProductID method increments the current value of nextProductID and
   * returns an integer.
   *
   * @return The next product ID to be used for product being brought into inventory.
   */
  public static int getNextProductID() {
    return ++nextProductID;
  }
  //===========================================================================
  // Test Data Loading Methods
  //===========================================================================

  /*
    Static initializer for loading test data into member lists.
   */
  static {
    loadTestData();
  }

  /**
   * The loadTestData method populates our static lists with parts and products for testing.
   * TODO: Write about how this method can and should be replaced/improved with a database
   *
   */
  public static void loadTestData() {
    InHousePart inPart1 = new InHousePart(getNextPartID(), "inPart1", 9.99, 5, 1, 8, 97);
    InHousePart inPart2 = new InHousePart(getNextPartID(), "inPart2", 10.99, 9, 6, 18, 91);
    OutsourcedPart outPart1 = new OutsourcedPart(getNextPartID(), "outPart1", 9.99, 7, 5, 10, "Wowz");
    OutsourcedPart outPart2 = new OutsourcedPart(getNextPartID(), "outPart2", 19.99, 6, 2, 12, "Nowz");
    allParts.addAll(inPart1, inPart2, outPart1, outPart2);

    Product product1 = new Product(null, getNextProductID(), "Product 1", 9.99, 3, 1, 5);
    Product product2 = new Product(null, getNextProductID(), "Product 2", 9.99, 3, 1, 5);
    product1.addAssociatedPart(inPart1);
    product1.addAssociatedPart(outPart1);
    product2.addAssociatedPart(inPart2);
    product2.addAssociatedPart(outPart2);
    allProducts.addAll(product1, product2);

  }

}