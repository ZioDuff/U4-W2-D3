import entities.Order;
import entities.Product;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
//        Prezzo generato con un random dentro un supplier
        Supplier<Double> randomNumbersSupplier = () -> {
            Random rndm = new Random();
            return rndm.nextDouble(1.99, 299.99);
        };
//        array di stringhe contenenti categorie
        String[] category = {"Books", "Baby", "Boys", "Man", "Woman"};
//        generiamo le categorie in modo random in base all'array istanziato poco fa
        Supplier<String> categorySupplier = () -> {

            Random rndm = new Random();
            int index = rndm.nextInt(category.length);

            return category[index];
        };

//      tramite il supplier generiamo il prodotto come ci serve
        Supplier<Product> productSupplier = () -> new Product("NOME", categorySupplier.get(), randomNumbersSupplier.get());
//        istanziamo una nuova lista dove verra agiiunti i nostri prodotti
        List<Product> productList = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
//            qui verranno aggiunti i nostri prodotti
            productList.add(productSupplier.get());

        }
//        qui abbiamo la lista stampata
        System.out.println(productList);

        System.out.println("************************************* ES1 ***********************************************");

//        ottenuta questa lista prodotti dobbiamo filtrare tutte i prodotti avente categoria books

        List<Product> expesiveBooks = productList.stream().filter(product -> product.getCategory().equals("Books") && product.getPrice() > 100).toList();
        System.out.println("questi sono i prodotti della categoria Books e che costano piu di 100$");
        System.out.println(expesiveBooks);


        System.out.println("************************************* ES2 ***********************************************");
//        genero date che ci serviranno per il costruttore della classe Order
        LocalDate today = LocalDate.now();
        LocalDate shipDay = today.plusDays(3);
//        filtro la lista generata prima con solo il parametro baby
        List<Product> babyListProduct = productList.stream().filter(product -> product.getCategory().equals("Baby")).toList();
//        creo un supplier con i parametri appena acquisiti
        Supplier<Order> orderSupplier = () -> new Order("da fare", today, shipDay, babyListProduct);
//        e creo un ciclo in modo che stampi 5 ordini contenenti tutti una lista filtrata
        List<Order> orderList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            orderList.add(orderSupplier.get());
            
        }
        System.out.println(orderList);


//        List<Order> babyProduct = productList.stream().filter(product -> product.getCategory().equals("Baby")).toList();
        System.out.println("************************************* ES3 ***********************************************");

//      qua filtriamo la nostra lista prodotti per categoria boys
        List<Product> discountedBoysProduct = productList.stream().filter(product -> product.getCategory().equals("Boys")).toList();
        System.out.println("Lista prodotti prima dello sconto");
        System.out.println(discountedBoysProduct);
//        identificata la lista che ci serve possiamoa ccedere al foreach in modo da assegnare ad ogni elemento l'operazione che ci serve
        discountedBoysProduct.forEach(product -> product.setPrice(product.getPrice() - product.getPrice() * 10 / 100));
        System.out.println("Lista prodotti dopo lo sconto");
        System.out.println(discountedBoysProduct);


    }
}