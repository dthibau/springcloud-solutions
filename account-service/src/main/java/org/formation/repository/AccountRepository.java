package org.formation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for Member data implemented using Spring Data JPA.
 * 
 * @author Paul Chapman
 */
public interface AccountRepository extends JpaRepository<Account, Long> {


	/**
	 * Find Members whose owner name contains the specified string
	 * 
	 * @param partialName
	 *            Any alphabetic string.
	 * @return The list of matching Members - always non-null, but may be
	 *         empty.
	 */
	public List<Account> findByNomContainingIgnoreCase(String partialNom);
	
	
	
	/**
	 * Find Members with email and password
	 * @param emailAddress
	 * @param lastname
	 * @return
	 */
	public Account findByEmailAndPassword(String email, String password);


}
