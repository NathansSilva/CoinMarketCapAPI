import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;

import java.util.Properties;
import java.util.ResourceBundle;

public class CoinMarketCapService {



    ResourceBundle bundle = ResourceBundle.getBundle("application-dev");
    private final String API_KEY = bundle.getString("api_key");
    private final String URL = bundle.getString("url");

    public void fetchAndDisplayCryptoInfo(String symbol) {
        OkHttpClient client = new OkHttpClient(); //Conectar na API

        try {
            // Montando URL
            String urlWithParams = URL + "?symbol=" + symbol + "&convert=USD"; //URL + simbolo + moeda

            // Cria a requisição, urlWithParams, Header, get e build.
            Request request = new Request.Builder()
                    .url(urlWithParams)
                    .addHeader("X-CMC_PRO_API_KEY", API_KEY)
                    .get()
                    .build();

            // Executa a requisição
            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful() && response.body() != null) {
                    // Processa a resposta JSON
                    JSONObject json = new JSONObject(response.body().string());
                    JSONObject data = json.getJSONObject("data").getJSONObject(symbol);

                    String name = data.getString("name");
                    double price = data.getJSONObject("quote").getJSONObject("USD").getDouble("price");
                    double marketCap = data.getJSONObject("quote").getJSONObject("USD").getDouble("market_cap");

                    // Exibe os resultados
                    System.out.println("Moeda: " + name);
                    System.out.printf("Preço: R$ %.2f\n", price);
                    System.out.printf("MarketCap: R$ %.2f", marketCap);
                    System.out.println("-------------");
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar dados para: " + symbol);
            e.printStackTrace();
        }
    }
}