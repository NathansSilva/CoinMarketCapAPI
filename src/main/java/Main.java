import okhttp3.OkHttpClient;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        List<String> coins = List.of("BTC", "SOL");

        CoinMarketCapService service = new CoinMarketCapService();
        for (String coin : coins) {
            System.out.println("Rodando...");
            service.fetchAndDisplayCryptoInfo(coin);
        }

    }
}