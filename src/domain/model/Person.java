package domain.model;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import domain.DomainException;

public class Person {
	private String userid;
	private String email;
	private String password;
	private String salt;
	private String firstName;
	private String lastName;

	public Person(String userid, String email, String password, String firstName, String lastName) {
		setUserid(userid);
		setEmail(email);
		try {
			setPasswordHashed(password);
		} catch( Exception e ) {
			throw new DomainException( e.getMessage() );
		}
		setFirstName(firstName);
		setLastName(lastName);
	}
	
	public Person( String userid, String email, String password, String firstName, String lastName, String salt ) {
		setUserid(userid);
		setEmail(email);
		setPassword(password);
		setFirstName(firstName);
		setLastName(lastName);
		setSalt(salt);
	}
	
	public Person() {
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		if(userid.isEmpty()){
			throw new IllegalArgumentException("No userid given");
		}
		this.userid = userid;
	}

	public void setEmail(String email) {
		if(email.isEmpty()){
			throw new IllegalArgumentException("No email given");
		}
		String USERID_PATTERN = 
				"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		Pattern p = Pattern.compile(USERID_PATTERN);
		Matcher m = p.matcher(email);
		if (!m.matches()) {
			throw new IllegalArgumentException("Email not valid");
		}
		this.email = email;
	}

	
	
	public String getEmail() {
		return email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public boolean isCorrectPassword(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if(password.isEmpty()){
			throw new IllegalArgumentException("No password given");
		}
		return this.password.equals( hashPassword( password + salt ) );
	}

	public void setPassword(String password) {
		if(password.isEmpty()){
			throw new IllegalArgumentException("No password given");
		}
		this.password = password;
	}
	
	public void setPasswordHashed( String password ) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if( password.isEmpty() ) {
			throw new IllegalArgumentException( "No password given" );
		}
		
		SecureRandom random = new SecureRandom();
		byte[] seed = random.generateSeed( 20 );
		setSalt( (new BigInteger( 1, seed )).toString( 16 ) );
		
		this.password = hashPassword( password + salt );
	}
	
	private String hashPassword( String password ) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest crypt = MessageDigest.getInstance( "SHA-512" );
		crypt.reset();
		
		byte[] passwordBytes = password.getBytes( "UTF-8" );
		crypt.update( passwordBytes );
		
		byte[] digest = crypt.digest();
		
		return (new BigInteger( 1, digest )).toString( 16 );
	}
	
	public void setSalt( String salt ) {
		if( salt == null || salt.trim().isEmpty() ) {
			throw new IllegalArgumentException( "No salt given" );
		}
		
		this.salt = salt;
	}
	
	public String getSalt() {
		return salt;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		if(firstName.isEmpty()){
			throw new IllegalArgumentException("No firstname given");
		}
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		if(lastName.isEmpty()){
			throw new IllegalArgumentException("No last name given");
		}
		this.lastName = lastName;
	}
	
	@Override
	public String toString(){
		return getFirstName() + " " + getLastName() + ": " + getUserid() + ", " + getEmail();
	}	
}
