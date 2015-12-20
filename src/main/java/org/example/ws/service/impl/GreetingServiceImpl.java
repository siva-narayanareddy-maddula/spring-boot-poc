package org.example.ws.service.impl;

import java.math.BigInteger;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.example.ws.model.Greeting;
import org.example.ws.repository.GreetingRepository;
import org.example.ws.service.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class GreetingServiceImpl implements GreetingService {

	@Autowired
	private GreetingRepository greetingRepo;
	
	@Override
	public Collection<Greeting> findAll() {
		Collection<Greeting> greetings = greetingRepo.findAll();
		return greetings;
	}

	@Override
	public Greeting findOne(Long id) {
		Greeting greeting = greetingRepo.findOne(id);
		return greeting;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public Greeting create(Greeting greeting) {
		if(greeting.getId() != null) {
			return null;
		}
		Greeting savedGreeting = greetingRepo.save(greeting);
		
		// Illustrate Tx rollback
		if(savedGreeting.getId() == 4L) {
			throw new RuntimeException("Roll me back");
		}
		return savedGreeting;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public Greeting update(Greeting greeting) {
		Greeting greetingPersisted = findOne(greeting.getId());
		if(greetingPersisted == null) {
			return null;
		}
		Greeting updatedGreeting = greetingRepo.save(greeting);
		return updatedGreeting;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void delete(Long id) {
		greetingRepo.delete(id);
	}

}
