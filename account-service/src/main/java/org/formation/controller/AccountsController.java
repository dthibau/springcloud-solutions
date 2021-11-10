package org.formation.controller;

import java.util.List;
import java.util.logging.Logger;

import javax.validation.Valid;

import org.formation.notification.Courriel;
import org.formation.notification.NotificationClient;
import org.formation.repository.Account;
import org.formation.repository.AccountRepository;
import org.formation.repository.Role;
import org.formation.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * A RESTFul controller for accessing Account information.
 * 
 * @author David THIBAU
 */
@RestController
@RequestMapping("/api/accounts")
public class AccountsController {

	protected Logger logger = Logger.getLogger(AccountsController.class.getName());
	private final AccountRepository accountRepository;
	private final RoleRepository roleRepository;

	@Autowired
	NotificationClient notificationClient;
	

	public AccountsController(AccountRepository accountRepository, RoleRepository roleRepository) {
		this.accountRepository = accountRepository;
		this.roleRepository = roleRepository;

		logger.info("AccountRepository says system has " + accountRepository.count() + " Accounts");
	}

	/**
	 * Fetch an Member with the specified Member number.
	 * 
	 * @param MemberNumber A numeric, 9 digit Member number.
	 * @return The Member if found.
	 * @throws MemberNotFoundException If the number is not recognised.
	 */
	@GetMapping("/{memberId}")
	public Account byNumber(@PathVariable("memberId") long memberId) {

		logger.info("Members-service byNumber() invoked: " + memberId);
		return accountRepository.findById(memberId).orElseThrow(() -> new MemberNotFoundException("" + memberId));

	}

	/**
	 * Fetch Members with the specified name. A partial case-insensitive match is
	 * supported. So <code>http://.../Members/owner/a</code> will find any Members
	 * with upper or lower case 'a' in their name.
	 * 
	 * @param partialName
	 * @return A non-null, non-empty set of Members.
	 * @throws MemberNotFoundException If there are no matches at all.
	 */
	@RequestMapping("/search/{name}")
	public List<Account> byOwner(@PathVariable("name") String partialName) {
		logger.info(
				"Members-service byOwner() invoked: " + accountRepository.getClass().getName() + " for " + partialName);

		List<Account> members = accountRepository.findByNomContainingIgnoreCase(partialName);
		logger.info("Members-service byOwner() found: " + members);

		if (members == null || members.size() == 0)
			throw new MemberNotFoundException(partialName);
		else {
			return members;
		}

	}

	@RequestMapping(path = "/authenticate", method = RequestMethod.POST)
	public Account authenticate(@Valid @RequestBody User user) {
		logger.info("Members-service authenticate() invoked: " + user);
		Account member = accountRepository.findByEmailAndPassword(user.getEmail(), user.getPassword());

		if (member == null)
			throw new MemberNotFoundException("" + user.getEmail());
		else {
			return member;
		}
	}

	@RequestMapping(path = "/register", method = RequestMethod.POST)
	public Account register(@Valid @RequestBody Account account) {
		if (account.getRoles().isEmpty()) {
			account.addRole(roleRepository.findByName(Role.CUSTOMER));
		}
		account = accountRepository.save(account);
		
		Courriel c = Courriel.builder().to(account.getEmail()).subject("Bienvenue").text("Félicitation vous êtes enregistré").build();
		
		notificationClient.sendSimple(c);
		
		return account;
	}


}
