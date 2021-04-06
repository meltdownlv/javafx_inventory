package Model;
// TODO: Make final decision on if this is a necessary part of the project.
/**
 * The ItemNotFoundException exceptions are thrown by the Inventory class
 * when a Part or Product lookup fail to find a match in the current stock.
 *
 * @author Sakae Watanabe
 */
public class ItemNotFoundException extends Exception {

  /**
   * No-arg constructor passes a generic message for the ItemNotFoundException.
   */
  public ItemNotFoundException() {
    super("Error: Item was not found in your inventory.");
  }

  /**
   * This constructor returns a formatted string using the object's simple class
   * name and lets user know item was not found at the given index.
   * @param objectType A string value for the object type being searched.
   * @param id An integer value of the associated with search.
   */
  public ItemNotFoundException(String objectType, int id) {
    super(String.format("Error: %s with ID: %d not found in your inventory.",
                        objectType, id));
  }


  /**
   * This constructor returns a formatted string using the object's simple class
   * name and lets user know item was not found with the given name.
   * @param objectType A string value for the object type being searched.
   * @param name A string value for the name of the object not found.
   */
  public ItemNotFoundException(String objectType, String name) {
    super(String.format("Error: %s with ID: %s not found in your inventory.",
                        objectType, name));
  }


}
