package ru.example.demo.model.DTO;

public class LockClientDTO {

    private String email;

    public LockClientDTO() {
    }

    public LockClientDTO(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
