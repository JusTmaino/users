package fr.supralog.users.repositories;

import fr.supralog.users.entities.UserDb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserDb, Long>, JpaSpecificationExecutor<UserDb> {
}
