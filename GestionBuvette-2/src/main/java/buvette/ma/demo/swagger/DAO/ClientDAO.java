package buvette.ma.demo.swagger.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import buvette.ma.demo.swagger.Beans.Client;

@Repository
public interface ClientDAO extends JpaRepository<Client, Long>{

	public Client findByEmail(String email);
    public Client findByPassword(String password);
	public Client findByUsername(String username);
	
	
}
