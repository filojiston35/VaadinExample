package com.imona.assigment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Customers> findAll() {
        return jdbcTemplate.query("SELECT id, first_name, last_name FROM customers",
                (rs, rowNum) -> new Customers(rs.getLong("id"), rs.getString("first_name"), rs.getString("last_name")));
    }

    public void update(Customers customer) {
        jdbcTemplate.update("UPDATE customers SET first_name=?, last_name=? WHERE id=?",
                customer.getFirstName(), customer.getLastName(), customer.getId());
    }
}
