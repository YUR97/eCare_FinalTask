package ru.example.demo.model;

import javax.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ROLES")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Client> clients;

    public Role(){}

    public Role(String name) {
        this.name = name;
        clients = new HashSet<>();
    }

    public void addClient(Client client){
        client.setRole(this);
        clients.add(client);
    }
    public void removeClient(Client client){
        clients.remove(client);
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setClients(Set<Client> clients) { this.clients = clients; }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public Set<Client> getClients() { return clients; }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
