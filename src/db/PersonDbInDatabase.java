package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import domain.model.Person;

public class PersonDbInDatabase implements PersonDb {

	Properties properties;

	public PersonDbInDatabase( Properties properties ) throws ClassNotFoundException, SQLException {
		try {
			Class.forName( "org.postgresql.Driver" );
			this.properties = properties;
		} catch( Exception e ) {
			throw new DbException( e.getMessage(), e );
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see db.PersonDb#get(java.lang.String)
	 */
	@Override
	public Person get(String personId) {
		if (personId == null) {
			throw new DbException("No id given");
		}
		Person person = null;
		
		try {
			Connection connection = DriverManager.getConnection( properties.getProperty( "url" ), properties );
			PreparedStatement statement = connection.prepareStatement(
				"SELECT * FROM person WHERE user_id=?"
			);
			statement.setString( 1, personId );
			ResultSet result = statement.executeQuery();

			result.next();
			person = new Person(
				result.getString( "user_id" ),
				result.getString( "email" ),
				result.getString( "password" ),
				result.getString( "first_name" ),
				result.getString( "last_name" ),
				result.getString( "salt" )
			);

			statement.close();
			connection.close();
		} catch( Exception e ) {
			throw new DbException( "Database Error", e );
		}
		
		return person;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see db.PersonDb#getAll()
	 */
	@Override
	public List<Person> getAll() {
		ArrayList<Person> people = new ArrayList<>();
		
		try {
			Connection connection = DriverManager.getConnection( properties.getProperty( "url" ), properties );
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery( "SELECT * FROM person" );

			while( result.next() ) {
				people.add( new Person(
					result.getString( "user_id" ),
					result.getString( "email" ),
					result.getString( "password" ),
					result.getString( "first_name" ),
					result.getString( "last_name" ),
					result.getString( "salt" )
				) );
			}
			
			statement.close();
			connection.close();
		} catch( Exception e ) {
			throw new DbException( "Database Error", e );
		}
		
		return people;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see db.PersonDb#add(domain.model.Person)
	 */
	@Override
	public void add(Person person) {
		if (person == null) {
			throw new DbException("No person given");
		}
		try {
			Connection connection = DriverManager.getConnection( properties.getProperty( "url" ), properties );
			PreparedStatement statement = connection.prepareStatement(
				"INSERT INTO person( user_id, email, password, first_name, last_name, salt )" +
				"VALUES( ?, ?, ?, ?, ?, ? )"
			);
			statement.setString( 1, person.getUserid() );
			statement.setString( 2, person.getEmail() );
			statement.setString( 3, person.getPassword() );
			statement.setString( 4, person.getFirstName() );
			statement.setString( 5, person.getLastName() );
			statement.setString( 6, person.getSalt() );
			statement.executeUpdate();
			
			statement.close();
			connection.close();
		} catch( Exception e ) {
			throw new DbException( "Database Error", e );
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see db.PersonDb#update(domain.model.Person)
	 */
	@Override
	public void update(Person person) {
		if (person == null) {
			throw new DbException("No person given");
		}
		try {
			Connection connection = DriverManager.getConnection( properties.getProperty( "url" ), properties );
			PreparedStatement statement = connection.prepareStatement(
				"UPDATE person SET email=?, password=?, first_name=?, last_name=?, salt=?" +
				"WHERE user_id=?"
			);
			statement.setString( 1, person.getEmail() );
			statement.setString( 2, person.getPassword() );
			statement.setString( 3, person.getFirstName() );
			statement.setString( 4, person.getLastName() );
			statement.setString( 5, person.getSalt() );
			statement.setString( 6, person.getUserid() );
			statement.executeUpdate();
			
			statement.close();
			connection.close();
		} catch( Exception e ) {
			throw new DbException( "Database Error", e );
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see db.PersonDb#delete(java.lang.String)
	 */
	@Override
	public void delete(String personId) {
		if (personId == null) {
			throw new DbException("No id given");
		}
		
		try {
			Connection connection = DriverManager.getConnection( properties.getProperty( "url" ), properties );
			PreparedStatement statement = connection.prepareStatement(
				"DELETE FROM person WHERE user_id=?"
			);
			statement.setString( 1, personId );
			statement.executeUpdate();
			
			statement.close();
			connection.close();
		} catch( Exception e ) {
			throw new DbException( "Database Error", e );
		}
	}
}
