package tn.esprit.studentmanagement.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.studentmanagement.entities.Student;
import tn.esprit.studentmanagement.repositories.StudentRepository;

import java.util.List;

@Service
public class StudentService implements IStudentService {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(StudentService.class);
    @Autowired
    private StudentRepository studentRepository;


    @Override
    public List<Student> getAllStudents() {
        logger.info("Appel de la méthode getAllStudents()");
        List<Student> result = studentRepository.findAll();
        logger.debug("getAllStudents() retourne {} étudiants", result.size());
        return result;
    }
    @Override
    public Student getStudentById(Long id) {
        logger.info("Appel de getStudentById() avec id = {}", id);
        Student student = studentRepository.findById(id).orElse(null);
        if(student != null) {
            logger.debug("Étudiant trouvé : {}", student.getFirstName() + " " + student.getLastName());
        } else {
            logger.warn("Aucun étudiant trouvé avec l'id {}", id);
        }
        return student;
    }
    @Override
    public Student saveStudent(Student student) {
        logger.info("Appel de saveStudent() pour : {}", student.getFirstName() + " " + student.getLastName());
        try {
            Student savedStudent = studentRepository.save(student);
            logger.debug("Étudiant sauvegardé avec id {}", savedStudent.getIdStudent());
            return savedStudent;
        } catch (Exception e) {
            logger.error("Erreur lors de la sauvegarde de l'étudiant", e);
            throw e;
        }
    }
    @Override
    public void deleteStudent(Long id) {
        logger.info("Appel de deleteStudent() pour id = {}", id);
        try {
            studentRepository.deleteById(id);
            logger.debug("Étudiant avec id {} supprimé", id);
        } catch (Exception e) {
            logger.error("Erreur lors de la suppression de l'étudiant", e);
            throw e;
        }
    }
}
