package tn.esprit.spring.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.entity.Contribution;

@Repository
public interface IContributionRepo extends JpaRepository<Contribution, Integer> {

	@Query(value = "SELECT sum(nbtickets) FROM contribution WHERE userss_user_id=:w", nativeQuery = true)
	public int ISommeTickets(@Param("w") int w);
	
	@Query(value = "SELECT sum(nbtickets) FROM contribution where paye=0", nativeQuery = true)
	public int allSommeTickets();
}
