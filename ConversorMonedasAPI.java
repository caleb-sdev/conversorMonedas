import org.json.JSONObject;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class ConversorMonedasAPI {

    private static final String API_KEY = "TU_API_KEY"; // Reemplaza con tu clave API
    private static final String API_URL = "https://api.unirateapi.com/api/convert?api_key=" + API_KEY;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Mostrar opciones de monedas
        System.out.println("Conversor de Monedas");
        System.out.println("Seleccione la moneda de origen:");
        System.out.println("1. USD - Dólar Estadounidense");
        System.out.println("2. EUR - Euro");
        System.out.println("3. BRL - Real Brasileño");
        System.out.println("4. COP - Peso Colombiano");
        System.out.println("5. CLP - Peso Chileno");
        System.out.println("6. PEN - Sol Peruano");
        System.out.print("Opción: ");
        String fromCurrency = getCurrencyCode(scanner.nextInt());

        System.out.println("Seleccione la moneda de destino:");
        System.out.println("1. USD - Dólar Estadounidense");
        System.out.println("2. EUR - Euro");
        System.out.println("3. BRL - Real Brasileño");
        System.out.println("4. COP - Peso Colombiano");
        System.out.println("5. CLP - Peso Chileno");
        System.out.println("6. PEN - Sol Peruano");
        System.out.print("Opción: ");
        String toCurrency = getCurrencyCode(scanner.nextInt());

        System.out.print("Ingrese la cantidad a convertir: ");
        double amount = scanner.nextDouble();

        try {
            double result = convertirMoneda(fromCurrency, toCurrency, amount);
            System.out.printf("%.2f %s = %.2f %s%n", amount, fromCurrency, result, toCurrency);
        } catch (IOException e) {
            System.out.println("Error al obtener la tasa de cambio: " + e.getMessage());
        }
    }

    private static String getCurrencyCode(int option) {
        switch (option) {
            case 1: return "USD";
            case 2: return "EUR";
            case 3: return "BRL";
            case 4: return "COP";
            case 5: return "CLP";
            case 6: return "PEN";
            default: return "USD";
        }
    }

    private static double convertirMoneda(String fromCurrency, String toCurrency, double amount) throws IOException {
        String urlString = String.format("%s&amount=%.2f&from=%s&to=%s", API_URL, amount, fromCurrency, toCurrency);
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            JSONObject jsonResponse = new JSONObject(response.toString());
            return jsonResponse.getDouble("result");
        }
    }
}
