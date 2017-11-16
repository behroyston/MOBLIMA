
package model;

/**
 * Represents an abstract Person and some of its common attributes in the booking system.
 * @author Amos
 * @version 1.0
 * @since 2017-11-06
 */
public abstract class Person {
	/**
	 * Name of the person.
	 */
	protected String name;

	/**
	 * Mobile Number of the person.
	 */	
	protected String mobileNumber;

	/**
	 * Password of the person account.
	 */
	protected String password;

	/**
	 * Email address of the person account.
	 */
	protected String email;

	/**
	 * Creates a new Person object with its common attributes.
	 * @param password		Password of the person account
	 * @param name			Name of the person
	 * @param mobileNumber	Mobile number of the person
	 * @param email			Email of the person
	 */
	public Person(String password, String name, String mobileNumber, String email) {
		this.name = name;
		this.mobileNumber = mobileNumber;
		this.password = password;
		this.email = email;
	}

	/**
	 * Forces the specialisation of person to implement the way the account is validated.
	 * @param email		Email of the person
	 * @param password	Password of the person
	 * @return			Result of the validation. True if success. False if failed.
	 */
	public abstract boolean validateIdentity(String email, String password);

	/**
	 * Gets the password of the person.
	 * @return	Password of the person.
	 */
	public String getPassword() {
		return this.password;
	}

	/**
	 * Sets the password of the person.
	 * @param password New password of the person.
	 */
	public void setPassword(String password) {
		this.password = password;
		return;
	}

	/**
	 * Gets the name of the person.
	 * @return	Name of the person.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Sets the name of the person.
	 * @param name	New Name of the person.
	 */
	public void setName(String name) {
		this.name = name;
		return;
	}

	/**
	 * Gets the mobile number of the person.
	 * @return	Mobile number of the person.
	 */
	public String getMobileNumber() {
		return this.mobileNumber;
	}

	/**
	 * Set the mobile number of the person.
	 * @param mobileNumber	Mobile number of the person.
	 */
	public void setMobleNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
		return;
	}

	/**
	 * Gets the email of the person.
	 * @return	Email of the person.
	 */
	public String getEmail() {
		return this.email;
	}

	/**
	 * Sets the email of the person.
	 * @param email	New email of the person.
	 */
	public void setEmail(String email) {
		this.email = email;
		return;
	}
}
