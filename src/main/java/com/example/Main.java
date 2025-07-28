package com.example;

import com.example.model.Employee;
import com.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Employee emp1 = new Employee("Alice", 50000);
        Employee emp2 = new Employee("Bob", 60000);

        save(emp1);
        save(emp2);

        Employee fetched = getEmployee(1);
        System.out.println("Fetched: " + fetched);

        fetched.setSalary(55000);
        update(fetched);

        delete(2);

        List<Employee> employees = getAllEmployees();
        employees.forEach(System.out::println);
    }

    public static void save(Employee emp) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.save(emp);
        tx.commit();
        session.close();
    }

    public static Employee getEmployee(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Employee emp = session.get(Employee.class, id);
        session.close();
        return emp;
    }

    public static void update(Employee emp) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.update(emp);
        tx.commit();
        session.close();
    }

    public static void delete(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Employee emp = session.get(Employee.class, id);
        if (emp != null) session.delete(emp);
        tx.commit();
        session.close();
    }

    public static List<Employee> getAllEmployees() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query<Employee> query = session.createQuery("FROM Employee", Employee.class);
        List<Employee> list = query.list();
        session.close();
        return list;
    }
}