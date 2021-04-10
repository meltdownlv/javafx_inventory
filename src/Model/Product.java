package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The Product class holds information about products and the list
 * of associated parts.
 *
 * @author Sakae Watanabe
 */
public class Product {

  //===========================================================================
  // Member Variables
  //===========================================================================
  /** An ObservableList of parts associated with the product. */
  private ObservableList<Part> associatedParts;
  /** ID number for the product. */
  private int id;
  /** Name for the product. */
  private String name;
  /** Price for the product */
  private double price;
  /** Current stock level for the product */
  private int stock;
  /** Minimum quantity stock level for the product */
  private int min;
  /** Maximum quantity stock level for the product */
  private int max;

  //===========================================================================
  // Constructor
  //===========================================================================
  /**
   * Constructs a new Product using the specified parameters.
   *
   * @param associatedParts An ObservableList of Parts associated with the Product.
   * @param id Integer value representing the product ID number.
   * @param name The name for the product.
   * @param price The price for the product.
   * @param stock Current stock level for the product.
   * @param min Minimum stock level for the product.
   * @param max Maximum stock level for the product.
   */
  public Product(ObservableList<Part> associatedParts, int id, String name, double price, int stock,
      int min, int max) {

    if (associatedParts != null){
      this.associatedParts = associatedParts;
    } else {
      this.associatedParts = FXCollections.observableArrayList();
    }
    this.id = id;
    this.name = name;
    this.price = price;
    this.stock = stock;
    this.min = min;
    this.max = max;
  }

  //===========================================================================
  // Getters & Setters
  //===========================================================================
  /**
   * @return An integer value for the ID of the Product.
   */
  public int getId() {
    return id;
  }

  /**
   * @param id An integer value to be set as the ID for the Product.
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * @return An String representing the name of the Product.
   */
  public String getName() {
    return name;
  }

  /**
   * @param name A String value to set Name for the Product.
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return An double representing the price of the Product.
   */
  public double getPrice() {
    return price;
  }

  /**
   * @param price A double value to set price for the Product.
   */
  public void setPrice(double price) {
    this.price = price;
  }

  /**
   * @return An integer value representing the current stock level.
   */
  public int getStock() {
    return stock;
  }

  /**
   * @param stock An integer value to set current stock level.
   */
  public void setStock(int stock) {
    this.stock = stock;
  }

  /**
   * @return An integer value representing the minimum stock level.
   */
  public int getMin() {
    return min;
  }

  /**
   * @param min An integer value to set minimum stock level.
   */
  public void setMin(int min) {
    this.min = min;
  }

  /**
   * @return An integer value representing the maximum stock level.
   */
  public int getMax() {
    return max;
  }

  /**
   * @param max An integer value to set maximum stock level.
   */
  public void setMax(int max) {
    this.max = max;
  }

  /**
   * @param part A Part added to associated parts list.
   */
  
  //===========================================================================
  // Associated Part List Methods
  //===========================================================================
  public void addAssociatedPart(Part part) {
    this.associatedParts.add(part);
  }

  /**
   * The deleteAssociatedPart method will attempt to remove the supplied part from
   * the associated parts list.
   * @param selectedAssociatedPart Part to be searched and removed from associated parts list.
   * @return A boolean value indicating if the part was successfully removed.
   */
  public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
    boolean deleted = false;
    if (!associatedParts.isEmpty()){
      for (Part p : associatedParts) {
        if (p.getId() == selectedAssociatedPart.getId()) {
          associatedParts.remove(p);
          deleted = true;
          break;
        }
      }
    }
    return deleted;
  }

  /**
   * @return An ObsservableList containing all associated parts.
   */
  public ObservableList<Part> getAllAssociatedParts() {
    return this.associatedParts;
  }

}
