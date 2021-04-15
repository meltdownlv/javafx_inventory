package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The Inventory class holds lists of both Parts and Products that are currently
 * in stock. All members belonging to the Inventory class are static.
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
   * @param selectedPart A Part object representing the new object for given
   *                     index.
   */
  public static void updatePart(int index, Part selectedPart) {
    allParts.set(index, selectedPart);
  }

  /**
   * The deletePart method will remove the first instance of selectedPart from
   * the allParts list.
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
   * The lookup part method will lookup part based on ID and return a reference
   * to the Part.
   *
   * @param partId An integer value representing the partID.
   * @return A reference to the matching Part object belonging to list.
   *         Returns null if the part id not found.
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
   * The lookupPart method will lookup part based on name and return an
   * ObservableList containing the matches to the given name.
   *
   * @param partName A String value representing the partName.
   * @return foundParts An observable list of parts matching the name.
   *                    Returns null if no matches.
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
   * @param newProduct A Product object representing the new object for given
   *                   index.
   */
  public static void updateProduct(int index, Product newProduct) {
    allProducts.set(index, newProduct);
  }

  /**
   * The deleteProduct method will remove the first instance of selectedProduct
   * from the allProducts list. Associated parts list for selectedProduct must
   * be empty to complete the operation.
   *
   * @param selectedProduct A Product to be removed from the allProducts list.
   * @return True if the Product was successfully deleted, False if product
   *         was unable to be deleted.
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
   * @return p A reference to the matching Part object belonging to list.
   *              Returns null if the product id not found.
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
   * The lookup part method will lookup part based on name and return an
   * ObservableList containing the matches to the given name.
   *
   * @param productName A String value representing the productName.
   * @return foundProducts An observable list of products matching the name.
   *                       Returns null if no matches.
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
   * The getNextProductID method increments the current value of nextProductID
   * and returns an integer.
   *
   * @return The next product ID to be used for product being brought into
   *         inventory.
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
   * The loadTestData method populates our static lists with parts and products
   * for testing.
   */
  public static void loadTestData() {
    InHousePart inPart1 = new InHousePart(getNextPartID(), "Elementium Ore",
        10.00, 6, 1, 10, 1);
    InHousePart inPart2 = new InHousePart(getNextPartID(), "Thorium Bar",
        2.50, 10, 4, 60, 1);
    InHousePart inPart3 = new InHousePart(getNextPartID(), "Arcanite Bar",
        10.00, 80, 12, 100, 2);
    OutsourcedPart outPart1 = new OutsourcedPart(getNextPartID(), "Elemental Flux",
        10.00, 7, 5, 10, "Barron's Blessings");
    OutsourcedPart outPart2 = new OutsourcedPart(getNextPartID(), "Fiery Core",
        100.00, 6, 2, 12, "Barron's Blessings");
    OutsourcedPart outPart3 = new OutsourcedPart(getNextPartID(), "Dense Grinding Stone",
        10.00, 6, 2, 12, "Stones & More");
    allParts.addAll(inPart1, inPart2, inPart3, outPart1, outPart2);

    Product product1 = new Product(null, getNextProductID(), "Elementium Bar",
        150.00, 3, 1, 10);
    Product product2 = new Product(null, getNextProductID(), "Arcanite Skeleton Key",
        25.00, 3, 1, 5);
    product1.addAssociatedPart(inPart1);
    product1.addAssociatedPart(inPart3);
    product1.addAssociatedPart(outPart1);
    product1.addAssociatedPart(outPart2);
    product2.addAssociatedPart(inPart3);
    product2.addAssociatedPart(outPart3);
    allProducts.addAll(product1, product2);

  }

}