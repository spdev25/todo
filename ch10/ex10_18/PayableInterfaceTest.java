import javax.swing.*;

interface CompensationModel {
	public abstract double earnings();
}

interface Payable {
	public abstract double getPaymentAmount(); // no implementation
}

class Employee implements Payable {
	private final String firstName;
	private final String lastName;
	private final String socialSecurityNumber;
	private CompensationModel compensationModel;

	public Employee(String firstName, String lastName, String socialSecurityNumber,
			CompensationModel compensationModel) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.socialSecurityNumber = socialSecurityNumber;
		this.compensationModel = compensationModel;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getSocialSecurityNumber() {
		return socialSecurityNumber;
	}

	public void setCompensationModel(CompensationModel compensationModel) {
		this.compensationModel = compensationModel;
	}

	public double earnings() {
		return compensationModel.earnings();
	}

	@Override
	public double getPaymentAmount() {
		return earnings();
	}

	@Override
	public String toString() {
		return String.format("%s: %s %s%n%s: %s%n%s %.2f", "employee", firstName, lastName, "social security number",
				socialSecurityNumber, "earnings", earnings());
	}
}

class Invoice implements Payable {
	private final String partNumber;
	private final String partDescription;
	private final int quantity;
	private final double pricePerItem;

	// constructor
	public Invoice(String partNumber, String partDescription, int quantity, double pricePerItem) {
		if (quantity < 0) { // validate quantity
			throw new IllegalArgumentException("Quantity must be >= 0");
		}

		if (pricePerItem < 0.0) { // validate pricePerItem
			throw new IllegalArgumentException("Price per item must be >= 0");
		}

		this.quantity = quantity;
		this.partNumber = partNumber;
		this.partDescription = partDescription;
		this.pricePerItem = pricePerItem;
	}

	// get part number
	public String getPartNumber() {
		return partNumber;
	}

	// get description
	public String getPartDescription() {
		return partDescription;
	}

	// get quantity
	public int getQuantity() {
		return quantity;
	}

	// get price per item
	public double getPricePerItem() {
		return pricePerItem;
	}

	// return String representation of Invoice object
	@Override
	public String toString() {
		return String.format("%s: %n%s: %s (%s) %n%s: %d %n%s: $%,.2f", "invoice", "part number", getPartNumber(),
				getPartDescription(), "quantity", getQuantity(), "price per item", getPricePerItem());
	}

	// method required to carry out contract with interface Payable
	@Override
	public double getPaymentAmount() {
		return getQuantity() * getPricePerItem(); // calculate total cost
	}
}

class SalariedCompensationModel implements CompensationModel {
	private double weeklySalary;

	public SalariedCompensationModel(double weeklySalary) {
		this.weeklySalary = weeklySalary;
	}

	@Override
	public double earnings() {
		return weeklySalary;
	}
}

public class PayableInterfaceTest {
	public static void main(String[] args) {
		// create array of Payable objects
		Payable[] payableObjects = new Payable[4];

		// get input from user to create Invoice objects
		String partNumber = JOptionPane.showInputDialog("Enter part number:");
		String partDescription = JOptionPane.showInputDialog("Enter part description:");
		int quantity = Integer.parseInt(JOptionPane.showInputDialog("Enter quantity:"));
		double pricePerItem = Double.parseDouble(JOptionPane.showInputDialog("Enter price per item:"));
		payableObjects[0] = new Invoice(partNumber, partDescription, quantity, pricePerItem);

		partNumber = JOptionPane.showInputDialog("Enter part number:");
		partDescription = JOptionPane.showInputDialog("Enter part description:");
		quantity = Integer.parseInt(JOptionPane.showInputDialog("Enter quantity:"));
		pricePerItem = Double.parseDouble(JOptionPane.showInputDialog("Enter price per item:"));
		payableObjects[1] = new Invoice(partNumber, partDescription, quantity, pricePerItem);

		// get input from user to create Employee objects
		String firstName = JOptionPane.showInputDialog("Enter first name:");
		String lastName = JOptionPane.showInputDialog("Enter last name:");
		String socialSecurityNumber = JOptionPane.showInputDialog("Enter social security number:");
		double weeklySalary = Double.parseDouble(JOptionPane.showInputDialog("Enter weekly salary:"));
		payableObjects[2] = new Employee(firstName, lastName, socialSecurityNumber, new SalariedCompensationModel(weeklySalary));

		firstName = JOptionPane.showInputDialog("Enter first name:");
		lastName = JOptionPane.showInputDialog("Enter last name:");
		socialSecurityNumber = JOptionPane.showInputDialog("Enter social security number:");
		weeklySalary = Double.parseDouble(JOptionPane.showInputDialog("Enter weekly salary:"));
		payableObjects[3] = new Employee(firstName, lastName, socialSecurityNumber, new SalariedCompensationModel(weeklySalary));

		// process each Payable object in array payableObjects
		for (Payable payable : payableObjects) {
			// get payment amount and format output message
			double paymentAmount = payable.getPaymentAmount();
			String outputMessage = String.format("%n%s %npayment due: $%,.2f%n", payable.toString(), paymentAmount);

			// display output message in a JOptionPane message dialog
			JOptionPane.showMessageDialog(null, outputMessage);
		}
	}
}