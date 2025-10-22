package com.example.service;

import com.example.classes.*;
import com.example.dao.IDao;
import com.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class ProduitService implements IDao<Produit> {

    @Override
    public boolean create(Produit p) {
        Transaction tx = null;
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            tx = s.beginTransaction();
            s.save(p);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Produit p) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = s.beginTransaction();
        s.delete(p);
        tx.commit();
        s.close();
        return true;
    }

    @Override
    public boolean update(Produit p) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = s.beginTransaction();
        s.update(p);
        tx.commit();
        s.close();
        return true;
    }

    @Override
    public Produit findById(int id) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Produit p = s.get(Produit.class, id);
        s.close();
        return p;
    }

    @Override
    public List<Produit> findAll() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        List<Produit> produits = s.createQuery("from Produit", Produit.class).list();
        s.close();
        return produits;
    }

    // 🔍 Méthodes supplémentaires
    public List<Produit> findByCategorie(Categorie cat) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        List<Produit> produits = s.createQuery("from Produit where categorie.id=:id", Produit.class)
                .setParameter("id", cat.getId())
                .list();
        s.close();
        return produits;
    }

    public List<Produit> findPrixSuperieur(float prix) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        List<Produit> produits = s.createQuery("from Produit where prix > :p", Produit.class)
                .setParameter("p", prix)
                .list();
        s.close();
        return produits;
    }
}
