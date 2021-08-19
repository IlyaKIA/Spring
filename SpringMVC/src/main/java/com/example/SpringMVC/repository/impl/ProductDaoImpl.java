package com.example.SpringMVC.repository.impl;

import com.example.SpringMVC.domain.Product;
import com.example.SpringMVC.repository.ProductDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.List;

@Repository
public class ProductDaoImpl implements ProductDAO {
    public static SessionFactory factory;

    @PostConstruct
    public void init(){
        factory = new Configuration()
                .configure("config/hibernate.cfg.xml")
                .buildSessionFactory();

//        Product product = saveOrUpdate(new  Product(3L, "Bred", 45));

//        System.out.println(findById(2L));

//        System.out.println(findAll());

//        deleteById(1L);

    }

    @Override
    public void shutdown(){
        factory.close();
    }

    @Override
    public Product findById(Long id){
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            Product product = session.get(Product.class, id);
            session.getTransaction().commit();
            return product;
        }
    }

    @Override
    public List<Product> findAll(){
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            List<Product> resultList = session.createQuery("select s from Product s", Product.class)
                    .getResultList();
            session.getTransaction().commit();
            return resultList;
        }
    }

    @Override
    public void deleteById(Long id){
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            Product product = session.get(Product.class, id);
            session.delete(product);
            session.getTransaction().commit();
        }
    }

    @Override
    public Product saveOrUpdate(Product product){
        try(Session session = factory.getCurrentSession()){
            boolean isNew = true;
            session.beginTransaction();
            List<Product> resultList = session.createQuery("select s from Product s", Product.class)
                    .getResultList();
            for(Product p : resultList){
                if(p.getId() == product.getId()) {
                    p.setTitle(product.getTitle());
                    p.setPrice(product.getPrice());
                    session.persist(p);
                    isNew = false;
                }
            }
            if(isNew){
                session.save(product);
            }
            session.getTransaction().commit();
        }
        return product;
    }
}