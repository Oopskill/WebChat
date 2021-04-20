package org.example.models;

public class Message {
    private String content;
    private Person person;

    public Message() {
    }

    public Message(Person person, String content) {
        this.content = content;
        this.person = person;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
