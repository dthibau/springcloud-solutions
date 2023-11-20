package org.formation.repository;

import java.util.List;

import org.aspectj.lang.annotation.Before;
import org.formation.model.Livraison;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.ApplicationContext;

@DataJpaTest
public class LivraisonRepositoryTest {

	@Autowired
	ApplicationContext context;
	
	@Autowired
    private TestEntityManager entityManager;

    @Autowired
    private LivraisonRepository repository;
    
    
    @BeforeEach
    public void setup() {
      
    	Livraison l = new Livraison();
    	l.setNoCommande("1234");
    	entityManager.persist(l);
      
        
    }
    @Test
    public void testFindByOwner() throws Exception {
    	
    	List<Livraison> livraisons = repository.findAll();
    	assert !livraisons.isEmpty();
    }
    
    
}
