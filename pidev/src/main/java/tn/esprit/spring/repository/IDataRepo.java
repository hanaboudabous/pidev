package tn.esprit.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.entity.Data;

@Repository

public interface IDataRepo extends JpaRepository<Data, Integer>{

}