package tn.esprit.studentmanagement;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import tn.esprit.studentmanagement.entities.Student;
import tn.esprit.studentmanagement.repositories.StudentRepository;

import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
@ActiveProfiles("test")
class StudentManagementApplicationTests {

    @Autowired
    private StudentRepository studentRepository;

    @Test
    void testSaveAndFindStudent() {
        // Créer un étudiant
        Student s = new Student();
        s.setFirstName("Ali");
        s.setLastName("Ben");
        s.setEmail("ali.ben@test.com");
        s.setPhone("12345678");
        s.setAddress("Tunis");

        // Sauvegarder dans H2
        studentRepository.save(s);

        // Vérifier que l'étudiant est bien sauvegardé
        List<Student> students = studentRepository.findAll();
        assertThat(students).hasSize(1);
        assertThat(students.get(0).getFirstName()).isEqualTo("Ali");
    }
}