package kr.co.playplace.repository.location;

import kr.co.playplace.entity.location.State;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StateRepository extends JpaRepository<State, Integer> {
}
