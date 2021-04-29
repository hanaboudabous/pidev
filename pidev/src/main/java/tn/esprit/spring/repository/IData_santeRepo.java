package tn.esprit.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.entity.Data_sante;
@Repository
public interface IData_santeRepo  extends JpaRepository<Data_sante, Integer>{

}
