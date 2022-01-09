package guru.qa.restbackend.service;

import guru.qa.restbackend.domain.HouseInfo;
import guru.qa.restbackend.domain.StudentInfo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {

    public static List<StudentInfo> students = new ArrayList<>();

    public static StudentInfo addStudent(StudentInfo studentInfo) {
        StudentInfo student = new StudentInfo(studentInfo.getStudentName(), studentInfo.getStudentHouse());
        students.add(student);
        return student;
    }

    public static List<StudentInfo> getAll() {
        return students;
    }

    public static List<StudentInfo> searchStudentByName(String name) {
        List<StudentInfo> result = new ArrayList<>();
        for (StudentInfo student : students) {
            if (student.getStudentName().contains(name)) {
                result.add(student);
            }
        }
        return result;
    }

    public static List<StudentInfo> getHouseStudentsList(HouseInfo houseInfo) {
        List<StudentInfo> houseList = new ArrayList<>();
        for (StudentInfo student : students) {
            if (student.getStudentHouse().equals(houseInfo.getHouseName())) {
                houseList.add(student);
            }
        }
        return houseList;
    }

}
