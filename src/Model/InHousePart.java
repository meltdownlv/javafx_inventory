package Model;

/**
 * The InHousePart class holds information about parts produced
 * internally for use in products.
 *
 * @author Sakae Watanabe
 */
public class InHousePart extends Part {

  //===========================================================================
  // Member Variables
  //===========================================================================
  /** The ID of the machine that produced this part. */
  private int machineID;
  
  //===========================================================================
  // Constructor
  //===========================================================================
  /**
   * Constructs a new InHousePart using the specified parameters.
   *
   * @param id The ID number for the InHousePart.
   * @param name The name for the InHousePart.
   * @param price The price for the InHousePart.
   * @param stock Current stock level for the InHousePart.
   * @param min Minimum stock level for the InHousePart.
   * @param max Maximum stock level for the InHousePart.
   * @param machineID The ID of the machine that produced this InHousepart.
   */
  public InHousePart(int id, String name, double price, int stock, int min, int max,
      int machineID) {
    super(id, name, price, stock, min, max);
    this.machineID = machineID;
  }

  //===========================================================================
  // Getters & Setters
  //===========================================================================
  /**
   * @return The int value of the ID for machine that produced this part
   */
  public int getMachineID() {
    return machineID;
  }

  /**
   * @param machineID New int value to set for this machineID.
   */
  public void setMachineID(int machineID) {
    this.machineID = machineID;
  }
}
