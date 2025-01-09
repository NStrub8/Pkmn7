package strubalin.pkmn.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import strubalin.pkmn.controllers.CardController;
import strubalin.pkmn.controllers.StudentController;

@Component
@RequiredArgsConstructor
public class DataTesting {

    private final ObjectMapper objectMapper;
    private final CardController cardController;
    private final StudentController studentController;

    @PostConstruct
    @SneakyThrows
    public void init() {
          System.out.println("Post construct init");
//
//        // Чтение данных из JSON файлов
//        File jsonFile = new File("C:\\Users\\123\\Downloads\\p\\madePkmn\\pkmn\\src\\main\\resources\\cards.json");
//        CardEntity card = objectMapper.readValue(jsonFile, CardEntity.class);
//
//        File jsonFile1 = new File("C:\\Users\\123\\Downloads\\p\\madePkmn\\pkmn\\src\\main\\resources\\students.json");
//        StudentEntity student = objectMapper.readValue(jsonFile1, StudentEntity.class);
//
//        // Тестирование getCardsByOwner
//        List<CardEntity> cardsByOwner = cardController.getCardsByOwner(student);
//        System.out.println("Cards by Owner: " + cardsByOwner);
//
//        // Тестирование createCard
//        CardEntity createdCard = cardController.createCard(card).getBody();
//        System.out.println("Created Card: " + createdCard);
//
//        // Тестирование getCardImageByName
//        String cardName = createdCard.getName();
//        String imageUrl = cardController.getCardImageByName(cardName).getBody();
//        if (imageUrl != null) {
//            System.out.println("Image URL: " + imageUrl);
//        } else {
//            System.out.println("Image not found for card: " + cardName);
//        }
//
////        // Тестирование getAllCards
////        List<CardEntity> allCards = cardController.getAllCards();
////        System.out.println("All Cards: " + allCards);
//
//        // Тестирование getCardById
//        if (createdCard != null) {
//            ResponseEntity<CardEntity> cardById = cardController.getCardById(createdCard.getId());
//            System.out.println("Card by ID: " + cardById.getBody());
//        }
//
////        // Тестирование updateCard
////        if (createdCard != null) {
////            createdCard.setName("Updated Card Name"); // Пример изменения имени
////            CardEntity updatedCard = cardController.updateCard(createdCard.getId(), createdCard);
////            System.out.println("Updated Card: " + updatedCard);
////        }
////
////        // Тестирование getAllStudents
////        List<StudentEntity> allStudents = studentController.getAllStudents();
////        System.out.println("All Students: " + allStudents);
////
//        // Тестирование getStudentById
//        if (student != null) {
//            ResponseEntity<StudentEntity> studentById = studentController.getStudentById(createdCard.getPokemonOwner().getId());
//            System.out.println("Student by ID: " + studentById.getBody());
//        }
//
////        // Тестирование createStudent
////        StudentEntity createdStudent = studentController.createStudent(student);
////        System.out.println("Created Student: " + createdStudent);
//
////        // Тестирование getStudentsByGroup
////        String group = "ExampleGroup"; // Укажите вашу группу
////        List<StudentEntity> studentsByGroup = studentController.getStudentsByGroup(group);
////        System.out.println("Students by Group: " + studentsByGroup);
//
//        // Тестирование getStudentByFullName
//        StudentEntity studentByFullName = studentController.getStudentByFullName(student);
//        System.out.println("Student by Full Name: " + studentByFullName);
    }
}