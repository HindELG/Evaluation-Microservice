package com.example.test;

import com.example.classes.*;
import com.example.service.*;
import java.util.Date;

public class TestMain {
    public static void main(String[] args) {
        ProduitService produitService = new ProduitService();
        CategorieService categorieService = new CategorieService();
        CommandeService commandeService = new CommandeService();
        LigneCommandeService ligneService = new LigneCommandeService();

        // 1️⃣ Création d’une catégorie
        Categorie cat1 = new Categorie("INF", "Informatique");
        categorieService.create(cat1);

        // 2️⃣ Création de produits
        Produit p1 = new Produit("ES12", 120, cat1);
        Produit p2 = new Produit("ZR85", 100, cat1);
        produitService.create(p1);
        produitService.create(p2);

        // 3️⃣ Création d’une commande
        Commande c1 = new Commande(new Date());
        commandeService.create(c1);

        // 4️⃣ Ajout de lignes de commande
        ligneService.create(new LigneCommandeProduit(p1, c1, 7));
        ligneService.create(new LigneCommandeProduit(p2, c1, 14));

        // 🔍 Afficher les produits d’une commande donnée
        System.out.println("Commande : " + c1.getId());
        ligneService.findByCommande(c1).forEach(l -> {
            System.out.println(l.getProduit().getReference() + "  " +
                    l.getProduit().getPrix() + "DH  " +
                    l.getQuantite());
        });
    }
}
