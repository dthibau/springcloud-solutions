package org.formation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for Member data implemented using Spring Data JPA.
 * 
 * @author Paul Chapman
 */
public interface RoleRepository extends JpaRepository<Role, Long> {


	/**
	 * Find Members whose owner name contains the specified string
	 * 
	 * @param partialName
	 *            Any alphabetic string.
	 * @return The list of matching Members - always non-null, but may be
	 *         empty.
	 */
	public Role findByName(String name);
	
	
	

}
