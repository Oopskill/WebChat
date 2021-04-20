package org.example.dao;

import org.example.models.Message;
import org.example.models.Person;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {

    @Value(value = "${url}")
    private String URL;
    @Value(value = "${user_name}")
    private String USERNAME;
    @Value(value = "${password}")
    private String PASSWORD;

    @PostConstruct
    public void setup() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(URL, USERNAME,PASSWORD);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private Connection connection;

    public Person personGetId(int id){
        Person person = null;

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Person WHERE id=?");

            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();

            person = new Person();
            person.setId(resultSet.getInt("id"));
            person.setName(resultSet.getString("name"));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return person;
    }

    public List<Person> getPeople() {
        List<Person> people = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Person");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                Person person = new Person();
                person.setId(resultSet.getInt("id"));
                person.setName(resultSet.getString("name"));
                person.setPassword(resultSet.getString("password"));
                people.add(person);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return people;
    }

    public List<Message> messagesList(){
        List<Message> messages = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Messages");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                Message message = new Message();
                message.setContent(resultSet.getString("content"));
                message.setPerson(personGetId(resultSet.getInt("person_id")));
                messages.add(message);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return messages;
    }

    public void addPerson(Person person){
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT Person(name,password) VALUES (?,?)");
            statement.setString(1,person.getName());
            statement.setString(2,person.getPassword());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void addMessage(String message, Person person){
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT Messages(content,person_id) VALUES (?,?)");
            statement.setString(1,message);
            statement.setLong(2,person.getId());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void deletePerson(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM Person WHERE id=?");

            statement.setInt(1,id);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
