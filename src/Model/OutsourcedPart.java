package Model;

/**
 * The OutSourcedPart class holds information about parts purchased
 * from outside vendors.
 *
 * @author Sakae Watanabe
 */
public class OutsourcedPart extends Part{

  /**The company name from which the part was purchased.*/
  private String companyName;

  public OutsourcedPart(int id, String name, double price, int stock, int min, int max,
      String companyName) {
    super(id, name, price, stock, min, max);
    this.companyName = companyName;
  }
}
