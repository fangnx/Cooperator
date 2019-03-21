package ca.mcgill.ecse321.cooperator.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import ca.mcgill.ecse321.cooperator.model.Student;
import org.springframework.web.bind.annotation.CrossOrigin;

public interface StudentRepository extends CrudRepository<Student, Integer> {

  @CrossOrigin(origins = "*")
  Student findByMcgillID(@Param(value = "id") Integer id);

  Student findByLastName(@Param(value = "name") String name); // For testing only, if used in
                                                              // practice, use list
  // Disable default POST end point
  @Override
  @SuppressWarnings("unchecked")
  @RestResource(exported = false)
  Student save(Student student);
}
